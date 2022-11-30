package com.catdev.ticket.readable.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ChangePasswordReq {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String password;
}
