package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String nome;
	
	private LocalDate dataNascimento;
	
	@OneToMany(mappedBy = "usuario")
	private List<Endereco> enderecos = new ArrayList<>();
	
	public Usuario() {}
	
	public Usuario(UUID id, String nome, LocalDate dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

}
