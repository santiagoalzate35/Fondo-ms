package com.bts.api.auth.response;

import com.bts.model.usuario.RolTipo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetail {

    private String id;
    private String nombre;
    private String email;
    private RolTipo rol;

}
