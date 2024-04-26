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

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(value = "/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable UUID id){
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll(){
		var response = service.listAll();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO dto) {
		var newObj = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> update(@Valid @RequestBody UsuarioDTO usuarioDTO, @PathVariable UUID id) {
		service.update(usuarioDTO, id);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> patchUpdate(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {
		service.patch(id, fields);
		return ResponseEntity.ok().build();
	}
	
	

}
