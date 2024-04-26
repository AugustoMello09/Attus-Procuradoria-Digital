package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Usuario;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;

	@Transactional(readOnly = true)
	public UsuarioDTO findById(UUID id) {
		Optional<Usuario> usuario = repository.findById(id);
		Usuario entity = usuario.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! "));
		return new UsuarioDTO(entity, entity.getEnderecos());
	}

	@Transactional(readOnly = true)
	public List<UsuarioDTO> listAll() {
		List<Usuario> usuarios = repository.findAll();
		List<UsuarioDTO> usuariosDTO = usuarios.stream().map(x -> new UsuarioDTO(x)).collect(Collectors.toList());
		return usuariosDTO;
	}

	@Transactional
	public UsuarioDTO create(UsuarioDTO usuarioDTO) {
		Usuario entity = new Usuario();
		entity.setNome(usuarioDTO.getNome());
		entity.setDataNascimento(usuarioDTO.getDataNascimento());
		repository.save(entity);
		return new UsuarioDTO(entity);
	}

	@Transactional
	public UsuarioDTO update(UsuarioDTO usuarioDTO, UUID id) {
		Usuario entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! "));
		entity.setNome(usuarioDTO.getNome());
		entity.setDataNascimento(usuarioDTO.getDataNascimento());
		repository.save(entity);
		return new UsuarioDTO(entity);
	}

	@Transactional
	public UsuarioDTO patch(UUID id, Map<String, Object> fields) {
	    Usuario usuario = repository.findById(id)
	            .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado! "));
	    merge(fields, usuario);
	    usuario = repository.save(usuario);
	    return new UsuarioDTO(usuario);
	}
	
	private void merge(Map<String, Object> fields, Usuario usuario) {
	    fields.forEach((propertyName, propertyValue) -> {
	        Field field = ReflectionUtils.findField(Usuario.class, propertyName);
	        if (field != null) {
	            field.setAccessible(true);
	            Object newValue = propertyValue;
	            ReflectionUtils.setField(field, usuario, newValue);
	        }
	    });
	}
}
