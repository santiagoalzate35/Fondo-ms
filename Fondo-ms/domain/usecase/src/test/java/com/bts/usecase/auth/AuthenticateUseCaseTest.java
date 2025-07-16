package com.bts.usecase.auth;

import com.bts.model.common.exception.CodeError;
import com.bts.model.common.exception.ErrorException;
import com.bts.model.common.jwt.JwtUtilGateway;
import com.bts.model.usuario.Usuario;
import com.bts.model.usuario.gateways.UsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticateUseCaseTest {

    private UsuarioGateway usuarioGateway;
    private JwtUtilGateway jwtUtil;
    private AuthenticateUseCase authenticateUseCase;

    @BeforeEach
    void setUp() {
        usuarioGateway = mock(UsuarioGateway.class);
        jwtUtil = mock(JwtUtilGateway.class);
        authenticateUseCase = new AuthenticateUseCase(usuarioGateway, jwtUtil);
    }

    @Test
    void save_deberiaRegistrarUsuarioSiNoExiste() throws ErrorException {
        Usuario usuario = Usuario.builder().email("test@mail.com").build();
        when(usuarioGateway.existByEmail("test@mail.com")).thenReturn(false);
        when(usuarioGateway.register(usuario)).thenReturn(usuario);

        Usuario result = authenticateUseCase.save(usuario);

        assertEquals(usuario, result);
        verify(usuarioGateway, times(1)).existByEmail("test@mail.com");
        verify(usuarioGateway, times(1)).register(usuario);
    }

    @Test
    void save_deberiaLanzarErrorSiElCorreoYaExiste() {
        Usuario usuario = Usuario.builder().email("test@mail.com").build();
        when(usuarioGateway.existByEmail("test@mail.com")).thenReturn(true);

        ErrorException ex = assertThrows(ErrorException.class, () -> authenticateUseCase.save(usuario));
        assertEquals("El correo ya existe", ex.getMessage());
        assertEquals(CodeError.FOUND, ex.getCode());
        verify(usuarioGateway, times(1)).existByEmail("test@mail.com");
        verify(usuarioGateway, never()).register(any());
    }

    @Test
    void login_deberiaRetornarTokenSiLoginEsExitoso() throws Exception {
        String email = "test@mail.com";
        String password = "1234";
        Usuario usuario = Usuario.builder().email(email).id("1").rol("CLIENTE").build();

        when(usuarioGateway.existByEmail(email)).thenReturn(true);
        when(usuarioGateway.validatePassword(email, password)).thenReturn(usuario);
        when(jwtUtil.generateToken(eq(email), any(Map.class))).thenReturn("token123");

        String token = authenticateUseCase.login(email, password);

        assertEquals("token123", token);
        verify(usuarioGateway, times(1)).existByEmail(email);
        verify(usuarioGateway, times(1)).validatePassword(email, password);
        verify(jwtUtil, times(1)).generateToken(eq(email), any(Map.class));
    }

    @Test
    void login_deberiaLanzarErrorSiUsuarioNoExiste() throws Exception {
        String email = "test@mail.com";
        String password = "1234";
        when(usuarioGateway.existByEmail(email)).thenReturn(false);

        ErrorException ex = assertThrows(ErrorException.class, () -> authenticateUseCase.login(email, password));
        assertEquals("El usuario no fue encontrado", ex.getMessage());
        assertEquals(CodeError.NOT_FOUND, ex.getCode());
        verify(usuarioGateway, times(1)).existByEmail(email);
        verify(usuarioGateway, never()).validatePassword(any(), any());
        verify(jwtUtil, never()).generateToken(any(), any());
    }

    @Test
    void login_deberiaPropagarExcepcionSiFallaValidacionPassword() throws Exception {
        String email = "test@mail.com";
        String password = "1234";
        when(usuarioGateway.existByEmail(email)).thenReturn(true);
        when(usuarioGateway.validatePassword(email, password)).thenThrow(new RuntimeException("Credenciales inválidas"));

        assertThrows(RuntimeException.class, () -> authenticateUseCase.login(email, password));
        verify(usuarioGateway, times(1)).existByEmail(email);
        verify(usuarioGateway, times(1)).validatePassword(email, password);
        verify(jwtUtil, never()).generateToken(any(), any());
    }
}