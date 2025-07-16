package com.bts.api.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(
    @Email
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    String email,

    @NotBlank(message = "La contraseña no puede estar vacío")
    String password
){}
