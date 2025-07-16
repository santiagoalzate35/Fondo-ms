package com.bts.api.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record RegisterRequest(
    @NotBlank(message = "El nombre no puede estar vacío")
    String nombre,

    @Email
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    String email,

    @NotBlank(message = "La contraseña no puede estar vacío")
    @Length(max = 20, min = 8,message = "La contraseña debe tener entre 8 y 20 caracteres.")
    @Pattern( regexp = "^(?!.*\\s)(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$",
            message = """
                    Recuerda que la contraseña debe tener al menos un carácter especial,
                    una letra mayúscula, una letra minúscula y un número.
                    una mayúscula, una minúscula y un número.
                    """
    )
    String password
){}
