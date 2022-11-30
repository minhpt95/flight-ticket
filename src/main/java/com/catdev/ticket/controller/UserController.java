package com.catdev.ticket.controller;

import com.catdev.ticket.constant.ErrorConstant;
import com.catdev.ticket.dto.ResponseDto;
import com.catdev.ticket.dto.user.UserDto;
import com.catdev.ticket.entity.UserEntity;
import com.catdev.ticket.exception.ErrorResponse;
import com.catdev.ticket.exception.ProductException;
import com.catdev.ticket.jwt.JwtProvider;
import com.catdev.ticket.jwt.payload.response.TokenRefreshResponse;
import com.catdev.ticket.readable.form.updateForm.UpdateUserForm;
import com.catdev.ticket.readable.request.ChangePasswordReq;
import com.catdev.ticket.security.service.UserPrinciple;
import com.catdev.ticket.service.RefreshTokenService;
import com.catdev.ticket.service.UserService;

import com.catdev.ticket.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@Log4j2
public class UserController {

    final
    PasswordEncoder encoder;
    final
    UserService userService;

    final
    JavaMailSender emailSender;

    final
    JwtProvider jwtProvider;

    final
    RefreshTokenService refreshTokenService;

    @PutMapping(value = "/activateEmail/{id}")
    public  ResponseDto<Boolean> activateEmail(@PathVariable Long id){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();

        responseDto.setContent(userService.activateEmail(id, DateUtil.getInstantNow()));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        
        return responseDto;
    }

    @PutMapping(value = "/changePassword")
    public  ResponseDto<Boolean> changePassword(@Valid @RequestBody ChangePasswordReq changePasswordReq){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        
        responseDto.setContent(userService.changePassword(changePasswordReq));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        
        return responseDto;
    }

    @PutMapping(value = "/updateUser")
    public ResponseDto<UserDto> updateUser (@RequestBody UpdateUserForm form) {
        ResponseDto<UserDto> responseDto = new ResponseDto<>();

        responseDto.setContent(userService.updateUser(form));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);

        return responseDto;
    }

    @PostMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new ProductException(
                    new ErrorResponse()
            );
        }

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        UserEntity userEntity = userService.findUserEntityByEmail(userPrinciple.getEmail());

        userService.clearToken(userEntity);

        new SecurityContextLogoutHandler().logout(request,response,authentication);

        ResponseDto<TokenRefreshResponse> responseDto = new ResponseDto<>();
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        return responseDto;
    }
}
