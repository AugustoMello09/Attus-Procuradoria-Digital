package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos;

import java.io.Serializable;
import java.util.UUID;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.Endereco;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.enumerations.TipoEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	
	@Size(max = 35, message = "tamanho máximo de 35 caracteres.")
	@NotBlank(message = "Campo Obrigatório")
	private String logradouro;
	
	@Size(max = 10, message = "tamanho máximo de 10 caracteres.")
	@NotBlank(message = "Campo Obrigatório")
	private String cep;
	
	@NotNull(message = "Campo Obrigatório")
	private int numero;
	
	@Size(max = 35, message = "tamanho máximo de 35 caracteres.")
	@NotBlank(message = "Campo Obrigatório")
	private String estado;
	
	@Size(max = 35, message = "tamanho máximo de 35 caracteres.")
	@NotBlank(message = "Campo Obrigatório")
	private String cidade;
	
	private TipoEndereco tipoEndereco;
	
	public EnderecoDTO() {}
	
	public EnderecoDTO(Endereco entity) {
		id = entity.getId();
		usuarioId = entity.getUsuario().getId();
		logradouro = entity.getLogradouro();
		cep = entity.getCep();
		numero = entity.getNumero();
		estado = entity.getEstado();
		cidade = entity.getCidade();
		tipoEndereco = entity.getTipoEndereco();
	}

}
