package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model;

import java.io.Serializable;
import java.util.UUID;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.enumerations.TipoEndereco;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String logradouro;

	private String cep;

	private int numero;

	private String estado;

	private String cidade;

	private Integer tipoEndereco;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Endereco() {}
	
	public Endereco(UUID id, String logradouro, String cep, int numero, String estado, String cidade,
			TipoEndereco tipoEndereco, Usuario usuario) {
		this.id = id;
		this.logradouro = logradouro;
		this.cep = cep;
		this.numero = numero;
		this.estado = estado;
		this.cidade = cidade;
		this.tipoEndereco = (tipoEndereco == null) ? 0 : tipoEndereco.getCod();
		this.usuario = usuario;
	}
	
	public TipoEndereco getTipoEndereco() {
		return TipoEndereco.toEnum(this.tipoEndereco);
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco.getCod();
	}

}
