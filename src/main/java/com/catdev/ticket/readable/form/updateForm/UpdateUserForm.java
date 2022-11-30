package com.catdev.ticket.readable.form.updateForm;

import com.catdev.ticket.readable.form.createForm.CreateUserForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateUserForm extends CreateUserForm {
    @NotNull
    private Long id;
}
