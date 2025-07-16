package com.bts.model.usuario.gateways;


import com.bts.model.usuario.Usuario;

public interface UsuarioGateway {

    Usuario save(Usuario usuario);

    Usuario register(Usuario usuario);

    Usuario validatePassword(String email, String password) throws Exception;

    boolean existByEmail(String email);

    Usuario findById(String id);

}
