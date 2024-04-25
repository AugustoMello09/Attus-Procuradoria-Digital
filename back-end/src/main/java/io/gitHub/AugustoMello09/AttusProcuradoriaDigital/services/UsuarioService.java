package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Usuario;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
	
	private final UsuarioRepository repository;
	
	@Transactional(readOnly = true)
	public UsuarioDTO findById(UUID id) {
		Optional<Usuario> usuario = repository.findById(id);
		Usuario entity = usuario.orElse(null);
		return new UsuarioDTO(entity, entity.getEnderecos());
	}
	
	@Transactional(readOnly = true)
	public List<UsuarioDTO> listAll(){
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
				.orElse(null);
		entity.setNome(usuarioDTO.getNome());
		entity.setDataNascimento(usuarioDTO.getDataNascimento());
		repository.save(entity);
		return new UsuarioDTO(entity);
	}
	
	@Transactional
	public UsuarioDTO patch(UsuarioDTO usuarioDTO, UUID id) {
		Usuario entity = repository.findById(id)
				.orElse(null);
		if (usuarioDTO.getNome() != null) {
            entity.setNome(usuarioDTO.getNome());
        }
        if (usuarioDTO.getDataNascimento() != null) {
            entity.setDataNascimento(usuarioDTO.getDataNascimento());
        }
		repository.save(entity);
		return new UsuarioDTO(entity);
	}

}
