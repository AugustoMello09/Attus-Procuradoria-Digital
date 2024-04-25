package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Endereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String nome;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;

	private List<EnderecoDTO> enderecos = new ArrayList<>();
	
	public UsuarioDTO() {}
	
	public UsuarioDTO(Usuario entity) {
		id = entity.getId();
		nome = entity.getNome();
		dataNascimento = entity.getDataNascimento();
	}
	
	public UsuarioDTO(Usuario entity, List<Endereco> enderecos) {
		this(entity);
		enderecos.forEach(endereco -> this.enderecos.add(new EnderecoDTO(endereco)));
	}

	public UsuarioDTO(UUID id, String nome, LocalDate dataNascimento) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}
	
	

}
