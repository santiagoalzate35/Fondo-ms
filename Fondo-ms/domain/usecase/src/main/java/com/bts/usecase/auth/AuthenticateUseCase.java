package com.bts.usecase.auth;

import com.bts.model.common.exception.ErrorException;
import com.bts.model.common.exception.CodeError;
import com.bts.model.common.jwt.JwtUtilGateway;
import com.bts.model.usuario.Usuario;
import com.bts.model.usuario.gateways.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class AuthenticateUseCase {

    private final UsuarioGateway usuarioGateway;

    private final JwtUtilGateway jwtUtil;

    public Usuario save(Usuario usuario) throws ErrorException {
        boolean existingUser = this.usuarioGateway.existByEmail(usuario.getEmail());

        if (existingUser) {
            throw new ErrorException("El correo ya existe", CodeError.FOUND);
        }

        return this.usuarioGateway.register(usuario);
    }

    public String login(String email, String password) throws Exception {
        boolean existingUser = this.usuarioGateway.existByEmail(email);

        if (!existingUser)
            throw new ErrorException("El usuario no fue encontrado", CodeError.NOT_FOUND);

        Usuario usuario = this.usuarioGateway.validatePassword(email, password);

        Map<String, Object> map = new HashMap<>();

        map.put("rol", List.of(usuario.getRol()));
        map.put("id", usuario.getId());

        return jwtUtil.generateToken(usuario.getEmail(), map);
    }

}
