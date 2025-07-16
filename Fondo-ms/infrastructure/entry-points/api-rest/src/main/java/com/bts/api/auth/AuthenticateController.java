package com.bts.api.auth;

import com.bts.api.auth.requests.LoginRequest;
import com.bts.api.auth.requests.RegisterRequest;
import com.bts.api.auth.response.LoginResponse;
import com.bts.api.auth.response.UserDetail;
import com.bts.model.usuario.RolTipo;
import com.bts.model.usuario.Usuario;
import com.bts.usecase.auth.AuthenticateUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthenticateController {

    private final AuthenticateUseCase authenticateUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) throws Exception {

        String token = authenticateUseCase.login(request.email(), request.password());

        return ResponseEntity.ok(LoginResponse.builder().token(token).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDetail> registerUser(@Valid @RequestBody RegisterRequest request) throws Exception {

        Usuario usuario = authenticateUseCase.save(
                Usuario.builder()
                .nombre(request.nombre())
                .email(request.email())
                .password(request.password())
                .rol(RolTipo.Client.getName())
                .build()
        );

        return ResponseEntity.ok(
                UserDetail.builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .email(usuario.getEmail())
                        .build()
        );
    }


}
