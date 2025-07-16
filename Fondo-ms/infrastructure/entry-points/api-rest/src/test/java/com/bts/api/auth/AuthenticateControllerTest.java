package com.bts.api.auth;

import com.bts.api.auth.requests.LoginRequest;
import com.bts.api.auth.requests.RegisterRequest;
import com.bts.api.fondos.FondosController;
import com.bts.model.usuario.RolTipo;
import com.bts.model.usuario.Usuario;
import com.bts.usecase.auth.AuthenticateUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = AuthenticateController.class)
@AutoConfigureMockMvc(addFilters = true)
public class AuthenticateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticateUseCase authenticateUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "cliente", authorities = {"CLIENTE"})
    public void deberiaLoguearseYRetornarToken() throws Exception {
        String email = "usuario@test.com";
        String password = "123456";
        String tokenEsperado = "token.jwt.simulado";

        LoginRequest request = new LoginRequest(email, password);

        when(authenticateUseCase.login(email, password)).thenReturn(tokenEsperado);

        mockMvc.perform(post("/api/v1/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(tokenEsperado)));
    }

    @Test
    @WithMockUser(username = "nuevo", authorities = {"CLIENTE"})
    public void deberiaRegistrarUsuarioYRetornarUserDetail() throws Exception {
        String nombre = "Juan";
        String email = "juan@test.com";
        String password = "passworD123#";

        RegisterRequest request = new RegisterRequest(nombre, email, password);

        Usuario usuarioGuardado = Usuario.builder()
                .id("1L")
                .nombre(nombre)
                .email(email)
                .password(password)
                .rol(RolTipo.Client.getName())
                .build();

        when(authenticateUseCase.save(Mockito.any(Usuario.class))).thenReturn(usuarioGuardado);

        mockMvc.perform(post("/api/v1/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1L")))
                .andExpect(jsonPath("$.nombre", is(nombre)))
                .andExpect(jsonPath("$.email", is(email)));
    }
}
