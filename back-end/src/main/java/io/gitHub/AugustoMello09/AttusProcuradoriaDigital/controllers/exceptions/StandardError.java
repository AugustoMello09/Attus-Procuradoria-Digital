package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.controllers.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime timestemp;
	private Integer status;
	private String error;
	private String path;

}
