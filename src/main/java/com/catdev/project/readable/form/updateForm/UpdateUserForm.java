package com.catdev.project.readable.form.updateForm;

import com.catdev.project.readable.form.createForm.CreateUserForm;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class UpdateUserForm extends CreateUserForm {
    @NotNull
    private Long id;
}
