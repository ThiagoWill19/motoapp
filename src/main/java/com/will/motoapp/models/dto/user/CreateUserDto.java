package com.will.motoapp.models.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Password é obrigatório")
    @Size(min = 8, message = "Confirm Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Confirm Password é obrigatório")
    private String confirmPassword;

}
