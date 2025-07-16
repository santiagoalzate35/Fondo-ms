package com.bts.mongo.usuario;

import com.bts.model.usuario.Usuario;
import com.bts.model.usuario.gateways.UsuarioGateway;
import com.bts.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public class UsuarioRepositoryAdapter extends AdapterOperations<Usuario, UsuarioDocument, String, UsuarioDBRepository>
 implements UsuarioGateway
{

    public UsuarioRepositoryAdapter(UsuarioDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Usuario.class));
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Usuario register(Usuario usuario) {
        usuario.setActivo(Boolean.TRUE);
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setNotificacionPreferencia("EMAIL");
        usuario.setSaldoDisponible(BigDecimal.valueOf(500000));
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return toEntity(repository.save(toData(usuario)));
    }

    @Override
    public Usuario validatePassword(String email, String password) throws Exception {
        Usuario usuario = toEntity(repository.findByEmailAndActivoTrue(email));

        if( !passwordEncoder.matches(password, usuario.getPassword()) ) {
            throw new Exception("La contraseña es incorrecta");
        }

        return usuario;
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmailAndActivoTrue(email);
    }
}
