package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos;

import java.io.Serializable;
import java.util.UUID;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnderecoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private UUID usuarioId;
	private String logradouro;
	private String cep;
	private int numero;
	private String estado;
	private String cidade;
	private Integer tipoEndereco;
	
	public EnderecoDTO() {}
	
	public EnderecoDTO(Endereco entity) {
		id = entity.getId();
		usuarioId = entity.getUsuario().getId();
		logradouro = entity.getLogradouro();
		cep = entity.getCep();
		numero = entity.getNumero();
		estado = entity.getEstado();
		cidade = entity.getCidade();
		tipoEndereco = entity.getTipoEndereco().getCod();
	}

}
