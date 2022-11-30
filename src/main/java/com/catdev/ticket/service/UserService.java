package com.catdev.ticket.service;



import com.catdev.ticket.dto.ListResponseDto;
import com.catdev.ticket.dto.user.UserDto;
import com.catdev.ticket.entity.UserEntity;
import com.catdev.ticket.readable.form.createForm.CreateUserForm;
import com.catdev.ticket.readable.form.updateForm.UpdateUserForm;
import com.catdev.ticket.readable.request.ChangePasswordReq;
import com.catdev.ticket.readable.request.ChangeStatusAccountReq;

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
