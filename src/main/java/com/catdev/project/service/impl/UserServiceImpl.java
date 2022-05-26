package com.catdev.project.service.impl;

import com.catdev.project.constant.CommonConstant;
import com.catdev.project.constant.ErrorConstant;
import com.catdev.project.dto.ListResponseDto;
import com.catdev.project.dto.user.UserDto;
import com.catdev.project.entity.UserEntity;
import com.catdev.project.exception.ErrorResponse;
import com.catdev.project.exception.ProductException;
import com.catdev.project.readable.form.createForm.CreateUserForm;
import com.catdev.project.readable.form.updateForm.UpdateUserForm;
import com.catdev.project.readable.request.ChangePasswordReq;
import com.catdev.project.readable.request.ChangeStatusAccountReq;
import com.catdev.project.respository.UserRepository;
import com.catdev.project.service.MailService;
import com.catdev.project.service.UserService;
import com.catdev.project.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.catdev.project.util.EmailValidateUtil.isAddressValid;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    final
    PasswordEncoder passwordEncoder;

    final
    UserRepository userRepository;

    final
    MailService mailService;

    final
    Environment env;

    @Override
    public void saveToken(String token, UserEntity userEntity) {
        userEntity.setAccessToken(token);
        userEntity.setTokenStatus(true);
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public void clearToken(UserEntity userEntity) {
        userEntity.setAccessToken(null);
        userEntity.setTokenStatus(false);
        userRepository.save(userEntity);
    }

    @Override
    public void clearAllToken() {
        List<UserEntity> userEntities = userRepository.findAll().stream().peek(x -> {
            x.setTokenStatus(false);
            x.setAccessToken(null);
        }).collect(Collectors.toList());

        if(userEntities.isEmpty()){
            return;
        }

        userRepository.saveAll(userEntities);
    }

    @Override
    public UserEntity findUserEntityByEmailForLogin(String email) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);

        if (optionalUserEntity.isEmpty())
        {
            throw new ProductException(new ErrorResponse(
                    ErrorConstant.Code.LOGIN_INVALID,
                    ErrorConstant.Type.LOGIN_INVALID,
                    ErrorConstant.Message.LOGIN_INVALID
            ));
        }
        return optionalUserEntity.get();
    }


    @Override
    public UserEntity findUserEntityByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserEntity findUserEntityByTelegramId(Long userTelegramId) {
        return userRepository.findUserEntityByUserTelegramId(userTelegramId).orElse(null);
    }

    private UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setCurrentAddress(userEntity.getCurrentAddress());
        userDto.setDescription(userEntity.getDescription());
        userDto.setEmail(userEntity.getEmail());
        userDto.setIdentityCard(userEntity.getIdentityCard());
        userDto.setEnabled(userEntity.isEnabled());
        userDto.setPermanentAddress(userEntity.getPermanentAddress());
        userDto.setPhoneNumber1(userEntity.getPhoneNumber1());
        userDto.setPhoneNumber2(userEntity.getPhoneNumber2());
        return userDto;
    }

    @Override
    public ListResponseDto<UserDto> getUserList(int pageIndex, int pageSize) {
        Page<UserEntity> page = userRepository.findAll(CommonUtil.buildPageable(pageIndex, pageSize));
        Page<UserDto> userDtoPage = page.map(this::convertToDto);
        ListResponseDto<UserDto> result = new ListResponseDto<>();
        return result.buildResponseList(userDtoPage, pageSize, pageIndex);
    }

    @Override
    @Transactional
    @SneakyThrows
    public UserDto createUser(CreateUserForm form) {
        Optional<UserEntity> userEntityList = userRepository.findAllByEmail(form.getEmail());
        if (userEntityList.isPresent()) {
            throw new ProductException(
                    new ErrorResponse(
                            ErrorConstant.Code.ALREADY_EXISTS,
                            ErrorConstant.Type.FAILURE,
                            String.format(ErrorConstant.Message.ALREADY_EXISTS,form.getEmail())
                    )
            );
        }
        UserEntity userEntity = new UserEntity();

        userEntity.setCurrentAddress(form.getCurrentAddress());
        userEntity.setDescription(form.getDescription());

        userEntity.setEmail(form.getEmail());
        userEntity.setIdentityCard(form.getIdentityCard());
        userEntity.setEnabled(false);
        userEntity.setName(form.getName());
        userEntity.setPassword(passwordEncoder.encode(form.getPassword()));
        userEntity.setPermanentAddress(form.getPermanentAddress());
        userEntity.setPhoneNumber1(form.getPhoneNumber1());
        userEntity.setPhoneNumber2(form.getPhoneNumber2());

        userEntity.setCreatedTime(Instant.now());

        userEntity = userRepository.save(userEntity);

        mailService.sendEmail(userEntity.getEmail(),"Confirm your email address on Spring Boot App","Click this link to activate your account : " + env.getProperty("url.activate.account") + userEntity.getId());

        return convertToDto(userEntity);
    }

    @Override
    public Boolean activateEmail(Long id, Instant currentTime) {
        UserEntity userEntity = userRepository.findUserEntityById(id);
        if (userEntity == null) {
            throw new ProductException(
                    new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.Message.NOT_EXISTS, id),
                    ErrorConstant.Type.FAILURE)
            );
        }
        Instant timeCreate = userEntity.getCreatedTime();
        Instant timeExpired = timeCreate.plus(7, ChronoUnit.DAYS);
        if (timeExpired.isBefore(currentTime)) {
            throw new ProductException(
                    new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    ErrorConstant.Message.END_OF_TIME,
                    ErrorConstant.Type.FAILURE)
            );
        }
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    @SneakyThrows
    public void forgotPassword(String email) {

        var isValidEmail = isAddressValid(email);

        if(!isValidEmail){
            log.error("email not valid => {}",() -> email);
            throw new ProductException(
                new ErrorResponse()
            );
        }

        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        if (userEntity == null) {

            log.error("User with email not found in database => {}",() -> email);

            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.Message.NOT_EXISTS, email),
                    ErrorConstant.Type.FAILURE));
        }

        String pwd = RandomStringUtils.random(12, CommonConstant.CHARACTERS);

        log.info("start forgotPassword()");

        mailService.sendEmail(email,"Forgot Password","New password is : " + pwd);

        userEntity.setPassword(passwordEncoder.encode(pwd));
        userRepository.save(userEntity);

        mailService.sendEmail(userEntity.getEmail(),"Forgot Password","New password for " + userEntity.getEmail() + " is : " + pwd);

    }

    @Override
    public Boolean changePassword(ChangePasswordReq changePasswordReq) {
        UserEntity userEntity = userRepository.findUserEntityById(changePasswordReq.getId());
        if (userEntity == null) {
            throw new ProductException(
                    new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.Message.NOT_EXISTS, changePasswordReq.getId()),
                    ErrorConstant.Type.FAILURE)
            );
        }
        String pwd = changePasswordReq.getPassword();
        userEntity.setPassword(passwordEncoder.encode(pwd));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public Boolean deactivateAccount(ChangeStatusAccountReq changeStatusAccountReq) {
        UserEntity userEntity = userRepository.findUserEntityById(changeStatusAccountReq.getId());

        log.info("deactivate account user by {}",changeStatusAccountReq.getId());

        if (userEntity == null) {

            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.Message.NOT_EXISTS, changeStatusAccountReq.getId()),
                    ErrorConstant.Type.FAILURE));

        }

        userEntity.setEnabled(false);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public UserDto updateUser(UpdateUserForm form) {
        UserEntity userEntity = userRepository.findUserEntityById(form.getId());

        log.info("update information user by {}",form.getId());


        if (userEntity == null) {
            throw new ProductException(new ErrorResponse(ErrorConstant.Code.NOT_FOUND,
                    String.format(ErrorConstant.Message.NOT_EXISTS, form.getId()),
                    ErrorConstant.Type.FAILURE));
        }
        userEntity.setEmail(form.getEmail());
        userEntity.setName(form.getName());
        userEntity.setIdentityCard(form.getIdentityCard());
        userEntity.setPhoneNumber1(form.getPhoneNumber1());
        userEntity.setPhoneNumber2(form.getPhoneNumber2());
        userEntity.setCurrentAddress(form.getCurrentAddress());
        userEntity.setPermanentAddress(form.getPermanentAddress());
        userEntity.setDescription(form.getDescription());

        userEntity = userRepository.save(userEntity);
        return convertToDto(userEntity);
    }
}
