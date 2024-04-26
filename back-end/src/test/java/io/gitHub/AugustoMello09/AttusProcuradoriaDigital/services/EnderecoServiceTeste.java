package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.EnderecoDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Endereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Usuario;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.enumerations.TipoEndereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.repositories.EnderecoRepository;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class EnderecoServiceTeste {
	
	private static final TipoEndereco TIPOENDERECO = TipoEndereco.PRINCIPAL;

	private static final String CIDADE = "Assis";

	private static final String ESTADO = "São Paulo";

	private static final int NUMERO = 0;

	private static final String CEP = "12345-678";

	private static final String RUA = "Rua das Flores";

	private static final LocalDate DATA = LocalDate.of(2003, 04, 1);

	private static final String NOME = "José";

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	private EnderecoService service;

	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private EnderecoRepository repository; 

	private Optional<Usuario> optionalUsuario;

	private Usuario usuario;

	private Endereco endereco;
	
	private EnderecoDTO enderecoDTO;
	
	private Optional<Endereco> enderecoOptional;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new EnderecoService(repository, usuarioRepository);
		startEndereco();
	}

	@Test
	@DisplayName("Deve retornar um endereço com sucesso. ")
	public void ShouldReturnAAdressWithSuccess() {
		when(repository.findById(ID)).thenReturn(enderecoOptional);
		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(EnderecoDTO.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(RUA, response.getLogradouro());
		assertEquals(CEP, response.getCep());
		assertEquals(NUMERO, response.getNumero());
		assertEquals(ESTADO, response.getEstado());
		assertEquals(CIDADE, response.getCidade());
		assertEquals(TIPOENDERECO, response.getTipoEndereco());
	}

	@DisplayName("Deve retornar endereço não encontrado. ")
	@Test
	public void shouldReturnAdressNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}

	@DisplayName("Deve retornar uma lista de endereços. ")
	@Test
	public void shouldReturnAlistOfAdress() {
		usuario = new Usuario(ID, NOME, DATA);
		List<Endereco> enderecos = Arrays.asList(new Endereco(UUID.randomUUID(), "Rua das Flores", "12345-678", 106, "São Paulo",
				"São Paulo", TipoEndereco.PRINCIPAL, usuario), new Endereco(UUID.randomUUID(), "Rua das Flores", "12345-678", 106, "São Paulo",
						"São Paulo", TipoEndereco.PRINCIPAL, usuario));
		when(repository.findAll()).thenReturn(enderecos);
		var response = service.findAll();
		assertNotNull(response);
		assertEquals(ArrayList.class, response.getClass());
	}

	@DisplayName("Deve crair um endereco. ")
	@Test
	public void shouldCreateAAdress() {
		when(usuarioRepository.findById(ID)).thenReturn(optionalUsuario);
		when(repository.save(any(Endereco.class))).thenReturn(endereco);
		var response = service.create(enderecoDTO);
		assertNotNull(response);
		assertEquals(EnderecoDTO.class, response.getClass());
		assertEquals(CEP, response.getCep());
		assertEquals(NUMERO, response.getNumero());
		assertEquals(ESTADO, response.getEstado());
		assertEquals(CIDADE, response.getCidade());
		assertEquals(TIPOENDERECO, response.getTipoEndereco());
	}
	
	@DisplayName("Deve retornar endereço não encontrado ao tentar criar. ")
	@Test
	public void shouldReturnAdressNotFoundWhenTryToCreate() {
		when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.create(enderecoDTO));
	}

	@DisplayName("Deve atualizar um Endereço. ")
	@Test
	public void shouldUpdateAAdress() {
		when(repository.findById(ID)).thenReturn(enderecoOptional);
		when(repository.save(any(Endereco.class))).thenReturn(endereco);
		var response = service.update(enderecoDTO, ID);
		assertNotNull(response);
		assertEquals(EnderecoDTO.class, response.getClass());
		assertEquals(ID, response.getId());
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Endereco.class));
	}

	@DisplayName("Deve retornar endereço não encontrado ao tentar atualizar. ")
	@Test
	public void shouldReturnAdressNotFoundWhenTryToUpdate() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.update(enderecoDTO, ID));
	}

	@DisplayName("Deve aplicar com sucesso o patch de atualização em um usuário existente.")
	@Test
	public void shouldApplyPatchToUpdateAdress() {
		Map<String, Object> fields = new HashMap<>();
		fields.put("rua", RUA);
		fields.put("cep", CEP);
		fields.put("estado", ESTADO);
		fields.put("cidade", CIDADE);
		fields.put("tipoEndereco", TipoEndereco.PRINCIPAL.getCod());
		when(repository.findById(ID)).thenReturn(enderecoOptional);
		when(repository.save(any(Endereco.class))).thenReturn(endereco);
		var response = service.updatePatch(ID, fields);
		assertNotNull(response);
		assertEquals(EnderecoDTO.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(RUA, response.getLogradouro());
		assertEquals(CEP, response.getCep());
		assertEquals(ESTADO, response.getEstado());
		assertEquals(CIDADE, response.getCidade());
		assertEquals(TipoEndereco.PRINCIPAL, response.getTipoEndereco());
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Endereco.class));
	}

	@DisplayName("Deve retornar endereço não encontrado ao tentar aplicar patch de atualização.")
	@Test
	public void shouldReturnAdressNotFoundWhenApplyingPatchToUpdate() {
		Map<String, Object> fields = new HashMap<>();
		fields.put("rua", RUA);
		fields.put("cep", CEP);
		fields.put("estado", ESTADO);
		fields.put("cidade", CIDADE);
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.updatePatch(ID, fields));
	}

	
	private void startEndereco() {
		usuario = new Usuario(ID, NOME, DATA);
		optionalUsuario = Optional.of(usuario);
		endereco = new Endereco(ID, RUA, CEP, NUMERO, ESTADO, CIDADE, TIPOENDERECO, usuario);
		enderecoOptional = Optional.of(endereco);
		enderecoDTO = new EnderecoDTO(ID, ID, NOME, CEP, NUMERO, ESTADO, CIDADE, TIPOENDERECO);
	}

}
