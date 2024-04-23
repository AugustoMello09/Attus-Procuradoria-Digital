package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.model.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEndereco {

	PRINCIPAL(0, "Principal"), SECUNDARIO(1, "Secundário");

	private Integer cod;
	private String descricao;

	public static TipoEndereco toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (TipoEndereco x : TipoEndereco.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Código inválido" + cod);
	}

}
