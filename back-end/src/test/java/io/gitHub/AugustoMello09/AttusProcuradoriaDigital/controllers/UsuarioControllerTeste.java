package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.UsuarioService;

@SpringBootTest
public class UsuarioControllerTeste {

	private static final LocalDate DATA = LocalDate.of(2003, 04, 1);

	private static final String NOME = "José";

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	@Mock
	private UsuarioService usuarioService;

	private UsuarioController controller;

	private UsuarioDTO usuarioDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		controller = new UsuarioController(usuarioService);
		startUsuario();
	}
	
	@DisplayName("Deve encontrar um usuário. ")
	@Test
	public void shouldFindUserById() {
		when(usuarioService.findById(ID)).thenReturn(usuarioDTO);
		ResponseEntity<UsuarioDTO> response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UsuarioDTO.class, response.getBody().getClass());
		assertEquals(ID, response.getBody().getId());
		assertEquals(NOME, response.getBody().getNome());
		assertEquals(DATA, response.getBody().getDataNascimento());
		
	}
	
	@DisplayName("Deve retornar uma lista de usuários. ")
	@Test
	public void shouldFindAllUsers() {
		List<UsuarioDTO> usuariosDTO = Arrays.asList(new UsuarioDTO(ID, NOME, DATA));
		when(usuarioService.listAll()).thenReturn(usuariosDTO);
		ResponseEntity<List<UsuarioDTO>> response = controller.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}
	
	@DisplayName("Deve criar um usuário. ")
	@Test
	public void shouldCreateUser() {
		when(usuarioService.create(any(UsuarioDTO.class))).thenReturn(usuarioDTO);
		ResponseEntity<UsuarioDTO> response = controller.create(usuarioDTO);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UsuarioDTO.class, response.getBody().getClass());
	}
	
	@DisplayName("Deve conseguir atualizar completamente um usuário. ")
	@Test
	public void shouldUpdateUser() {
		when(usuarioService.update(usuarioDTO, ID)).thenReturn(usuarioDTO);
		ResponseEntity<UsuarioDTO> response = controller.update(usuarioDTO, ID);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}
	
	@DisplayName("Deve conseguir atualizar parcialmete um usuário. ")
	@Test
	public void shouldPatchUpdateUser() {
		Map<String, Object> fields = new HashMap<>();
	    fields.put("nome", NOME);
	    fields.put("dataNascimento", DATA);
	    when(usuarioService.patch(ID, fields)).thenReturn(usuarioDTO);
	    ResponseEntity<UsuarioDTO> response = controller.patchUpdate(fields, ID);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}

	private void startUsuario() {
		usuarioDTO = new UsuarioDTO(ID, NOME, DATA);
	}

}
