package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.EnderecoDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Attus Procuradoria Digital Endereço endpoint")
@RestController
@RequestMapping(value = "/v1/enderecos")
@RequiredArgsConstructor
public class EnderecoController {
	
	private final EnderecoService service;
	
	@Operation(summary = "Retorna um Endereço DTO.")
	@GetMapping(value = "/{id}")
	public ResponseEntity<EnderecoDTO> findById(@PathVariable UUID id){
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retorna uma lista de Endereços DTO.")
	@GetMapping
	public ResponseEntity<List<EnderecoDTO>> findAll(){
		var response = service.findAll();
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Cria um endereço DTO.")
	@PostMapping
	public ResponseEntity<EnderecoDTO> create(@Valid @RequestBody EnderecoDTO dto) {
		var newObj = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@Operation(summary = "Atualiza completamente um endereço DTO.")
	@PutMapping(value = "/{id}")
	public ResponseEntity<EnderecoDTO> update(@Valid @RequestBody EnderecoDTO enderecoDTO, @PathVariable UUID id) {
		service.update(enderecoDTO, id);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Atualiza parcialmente um endereço DTO.")
	@PatchMapping(value = "/{id}")
	public ResponseEntity<EnderecoDTO> patchUpdate(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {
	    EnderecoDTO enderecoDTO = service.updatePatch(id, fields);
	    return ResponseEntity.ok().body(enderecoDTO);
	}

}
