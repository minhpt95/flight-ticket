package com.catdev.project.service;



import com.catdev.project.dto.ListResponseDto;
import com.catdev.project.dto.user.UserDto;
import com.catdev.project.entity.UserEntity;
import com.catdev.project.readable.form.createForm.CreateUserForm;
import com.catdev.project.readable.form.updateForm.UpdateUserForm;
import com.catdev.project.readable.request.ChangePasswordReq;
import com.catdev.project.readable.request.ChangeStatusAccountReq;

import java.time.Instant;

public interface UserService {
    void saveToken(String token, UserEntity userEntity);

    void clearToken(UserEntity userEntity);

    void clearAllToken();

    UserEntity findUserEntityByEmailForLogin(String email);

    UserEntity findUserEntityByEmail(String email);

    UserEntity findUserEntityByTelegramId(Long userTelegramId);

    ListResponseDto<UserDto> getUserList
    (
            int pageIndex,
            int pageSize
    );
    
    UserDto createUser(CreateUserForm form);

    Boolean activateEmail(Long id, Instant timeOut);

    void forgotPassword(String email);

    Boolean changePassword(ChangePasswordReq changePasswordReq);

    Boolean deactivateAccount(ChangeStatusAccountReq changeStatusAccountReq);

    UserDto updateUser(UpdateUserForm form);
}
