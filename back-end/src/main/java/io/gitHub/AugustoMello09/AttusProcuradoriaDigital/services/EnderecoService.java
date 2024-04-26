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

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.EnderecoDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Endereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Usuario;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.enumerations.TipoEndereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.repositories.EnderecoRepository;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnderecoService {

	private final EnderecoRepository repository;

	private final UsuarioRepository usaRepository;
	
	@Transactional(readOnly = true)
	public EnderecoDTO findById(UUID id) {
		Optional<Endereco> entity = repository.findById(id);
		Endereco obj = entity.orElseThrow(() -> new ObjectNotFoundException("Endereço não encontrado! "));
		return new EnderecoDTO(obj);
	}
	
	@Transactional(readOnly = true)
	public List<EnderecoDTO> findAll(){
		List<Endereco> enderecos = repository.findAll();
		return enderecos.stream().map(x -> new EnderecoDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public EnderecoDTO create(EnderecoDTO enderecoDTO) {
		Endereco entity = new Endereco();
		Usuario usuario = usaRepository.findById(enderecoDTO.getUsuarioId())
				.orElseThrow(() -> new ObjectNotFoundException("Endereço não encontrado! "));
		entity.setUsuario(usuario);
		entity.setLogradouro(enderecoDTO.getLogradouro());
		entity.setCep(enderecoDTO.getCep());
		entity.setNumero(enderecoDTO.getNumero());
		entity.setEstado(enderecoDTO.getEstado());
		entity.setCidade(enderecoDTO.getCidade());
		entity.setTipoEndereco(enderecoDTO.getTipoEndereco());
		repository.save(entity);
		return new EnderecoDTO(entity);
	}
	
	@Transactional
	public EnderecoDTO update(EnderecoDTO enderecoDTO, UUID id) {
		Endereco entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Endereço não encontrado! "));
		entity.setLogradouro(enderecoDTO.getLogradouro());
		entity.setCep(enderecoDTO.getCep());
		entity.setNumero(enderecoDTO.getNumero());
		entity.setEstado(enderecoDTO.getEstado());
		entity.setCidade(enderecoDTO.getCidade());
		entity.setTipoEndereco(enderecoDTO.getTipoEndereco());
		repository.save(entity);
		return new EnderecoDTO(entity);
	}
	
	@Transactional
	public EnderecoDTO updatePatch(UUID id, Map<String, Object> fields) {
	    Endereco endereco = repository.findById(id)
	            .orElseThrow(() -> new ObjectNotFoundException("Endereço não encontrado! "));
	    merge(fields, endereco);
	    endereco = repository.save(endereco);
	    return new EnderecoDTO(endereco);
	}

	private void merge(Map<String, Object> fields, Endereco endereco) {
	    fields.forEach((propertyName, propertyValue) -> {
	        Field field = ReflectionUtils.findField(Endereco.class, propertyName);
	        if (field != null) {
	            field.setAccessible(true);
	            Object newValue = propertyValue;
	            if (field.getType().equals(TipoEndereco.class)) {
	                newValue = TipoEndereco.valueOf((String) propertyValue);
	            }

	            ReflectionUtils.setField(field, endereco, newValue);
	        }
	    });
	}

}
