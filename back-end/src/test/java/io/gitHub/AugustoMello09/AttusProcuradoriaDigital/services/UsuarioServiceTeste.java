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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Endereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Usuario;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.enumerations.TipoEndereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class UsuarioServiceTeste {

	private static final LocalDate DATA = LocalDate.of(2003, 04, 1);

	private static final String NOME = "José";

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	private UsuarioService service;

	@Mock
	private UsuarioRepository repository;

	private Optional<Usuario> optionalUsuario;

	private Usuario usuario;

	private UsuarioDTO usuarioDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new UsuarioService(repository);
		startUsuario();
	}

	@Test
	@DisplayName("Deve retornar um usuário com sucesso. ")
	public void ShouldReturnAUserWithSuccess() {
		when(repository.findById(ID)).thenReturn(optionalUsuario);
		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(UsuarioDTO.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getNome());
		assertEquals(DATA, response.getDataNascimento());
	}

	@DisplayName("Deve retornar usuário não encontrado. ")
	@Test
	public void shouldReturnUserNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}

	@DisplayName("Deve retornar uma lista de usuários. ")
	@Test
	public void shouldReturnAlistOfUsers() {
		List<Usuario> usuarios = Arrays.asList(new Usuario(ID, NOME, DATA), new Usuario(UUID.randomUUID(), NOME, DATA));
		when(repository.findAll()).thenReturn(usuarios);
		var response = service.listAll();
		assertNotNull(response);
		assertEquals(ArrayList.class, response.getClass());
	}

	@DisplayName("Deve crair um usuário. ")
	@Test
	public void shouldCreateAUser() {
		when(repository.save(any(Usuario.class))).thenReturn(usuario);
		var response = service.create(usuarioDTO);
		assertNotNull(response);
		assertEquals(UsuarioDTO.class, response.getClass());
		assertEquals(NOME, response.getNome());
		assertEquals(DATA, response.getDataNascimento());
	}

	@DisplayName("Deve atualizar um usuário. ")
	@Test
	public void shouldUpdateAUser() {
		when(repository.findById(ID)).thenReturn(optionalUsuario);
		when(repository.save(any(Usuario.class))).thenReturn(usuario);
		var response = service.update(usuarioDTO, ID);
		assertNotNull(response);
		assertEquals(UsuarioDTO.class, response.getClass());
		assertEquals(ID, response.getId());
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Usuario.class));
	}

	@DisplayName("Deve retornar usuário não encontrado ao tentar atualizar. ")
	@Test
	public void shouldReturnUserNotFoundWhenTryToUpdate() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.update(usuarioDTO, ID));
	}

	@DisplayName("Deve aplicar com sucesso o patch de atualização em um usuário existente.")
	@Test
	public void shouldApplyPatchToUpdateUser() {
		when(repository.findById(ID)).thenReturn(optionalUsuario);
		when(repository.save(any(Usuario.class))).thenReturn(usuario);
		var response = service.patch(usuarioDTO, ID);
		assertNotNull(response);
		assertEquals(UsuarioDTO.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NOME, response.getNome());
		assertEquals(DATA, response.getDataNascimento());
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Usuario.class));
	}

	@DisplayName("Deve retornar usuário não encontrado ao tentar aplicar patch de atualização.")
	@Test
	public void shouldReturnUserNotFoundWhenApplyingPatchToUpdate() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.patch(usuarioDTO, ID));
	}

	@Test
	@DisplayName("Deve criar um UsuarioDTO com enderecos com sucesso.")
	public void shouldCreateUsuarioDTOWithEnderecos() {
		usuario = new Usuario(ID, NOME, DATA);
		Endereco endereco1 = new Endereco(UUID.randomUUID(), "Rua das Flores", "12345-678", 106, "São Paulo",
				"São Paulo", TipoEndereco.PRINCIPAL, usuario);
		endereco1.setUsuario(usuario);
		Endereco endereco2 = new Endereco(UUID.randomUUID(), "Rua das Flores", "12345-678", 106, "São Paulo",
				"São Paulo", TipoEndereco.PRINCIPAL, usuario);
		endereco2.setUsuario(usuario);
		List<Endereco> enderecos = Arrays.asList(endereco1, endereco2);
		usuario.setEnderecos(enderecos);
		UsuarioDTO usuarioDTOWithEnderecos = new UsuarioDTO(usuario, enderecos);
		assertNotNull(usuarioDTOWithEnderecos);
		assertEquals(UsuarioDTO.class, usuarioDTOWithEnderecos.getClass());
		assertEquals(ID, usuarioDTOWithEnderecos.getId());
		assertEquals(NOME, usuarioDTOWithEnderecos.getNome());
		assertEquals(DATA, usuarioDTOWithEnderecos.getDataNascimento());
		assertNotNull(usuarioDTOWithEnderecos.getEnderecos());
		assertEquals(2, usuarioDTOWithEnderecos.getEnderecos().size());
	}

	private void startUsuario() {
		usuario = new Usuario(ID, NOME, DATA);
		usuarioDTO = new UsuarioDTO(ID, NOME, DATA);
		optionalUsuario = Optional.of(usuario);
	}

}
