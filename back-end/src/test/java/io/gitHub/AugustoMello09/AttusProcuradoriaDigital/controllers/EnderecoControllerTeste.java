package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.EnderecoDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.enumerations.TipoEndereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.EnderecoService;

@SpringBootTest
public class EnderecoControllerTeste {

	private static final TipoEndereco TIPOENDERECO = TipoEndereco.PRINCIPAL;

	private static final String CIDADE = "Assis";

	private static final String ESTADO = "São Paulo";

	private static final int NUMERO = 0;

	private static final String CEP = "12345-678";

	private static final String RUA = "Rua das Flores";

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	private EnderecoController controller;

	@Mock
	private EnderecoService service;

	private EnderecoDTO enderecoDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		controller = new EnderecoController(service);
		startEndereco();
	}

	@DisplayName("Deve encontrar um endereço. ")
	@Test
	public void shouldFindAdressById() {
		when(service.findById(ID)).thenReturn(enderecoDTO);
		ResponseEntity<EnderecoDTO> response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(EnderecoDTO.class, response.getBody().getClass());
		assertEquals(ID, response.getBody().getId());
		assertEquals(RUA, response.getBody().getLogradouro());
		assertEquals(CEP, response.getBody().getCep());
		assertEquals(NUMERO, response.getBody().getNumero());
		assertEquals(ESTADO, response.getBody().getEstado());
		assertEquals(CIDADE, response.getBody().getCidade());
		assertEquals(TIPOENDERECO, response.getBody().getTipoEndereco());

	}

	@DisplayName("Deve retornar uma lista de endereços. ")
	@Test
	public void shouldFindAllAdress() {
		List<EnderecoDTO> enderecos = Arrays.asList(
				new EnderecoDTO(UUID.randomUUID(), UUID.randomUUID(), "Rua das Flores", "12345-678", 106, "São Paulo",
						"São Paulo", TipoEndereco.PRINCIPAL),
				new EnderecoDTO(UUID.randomUUID(), UUID.randomUUID(), "Rua das Flores", "12345-678", 106, "São Paulo",
						"São Paulo", TipoEndereco.PRINCIPAL));
		when(service.findAll()).thenReturn(enderecos);
		ResponseEntity<List<EnderecoDTO>> response = controller.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}

	@DisplayName("Deve criar um endereço. ")
	@Test
	public void shouldCreateAdress() {
		when(service.create(any(EnderecoDTO.class))).thenReturn(enderecoDTO);
		ResponseEntity<EnderecoDTO> response = controller.create(enderecoDTO);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(EnderecoDTO.class, response.getBody().getClass());
	}

	@DisplayName("Deve conseguir atualizar completamente um endereço. ")
	@Test
	public void shouldUpdateAdress() {
		when(service.update(enderecoDTO, ID)).thenReturn(enderecoDTO);
		ResponseEntity<EnderecoDTO> response = controller.update(enderecoDTO, ID);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}

	@DisplayName("Deve conseguir atualizar parcialmete um endereço. ")
	@Test
	public void shouldPatchUpdateAdress() {
		Map<String, Object> fields = new HashMap<>();
		fields.put("rua", RUA);
		fields.put("cep", CEP);
		fields.put("estado", ESTADO);
		fields.put("cidade", CIDADE);
		fields.put("tipoEndereco", TipoEndereco.PRINCIPAL.getCod());
		when(service.updatePatch(ID, fields)).thenReturn(enderecoDTO);
		ResponseEntity<EnderecoDTO> response = controller.patchUpdate(fields, ID);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
	}

	private void startEndereco() {
		enderecoDTO = new EnderecoDTO(ID, ID, RUA, CEP, NUMERO, ESTADO, CIDADE, TIPOENDERECO);
	}

}
