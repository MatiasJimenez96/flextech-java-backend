package com.matiasjimenez.app.clientes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matiasjimenez.app.clientes.models.entity.Persona;
import com.matiasjimenez.app.clientes.service.PersonaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/personas/")
public class PersonaController {

	@Autowired
	private PersonaService servicio;

	@GetMapping("/listar")
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok(servicio.listar());
	}

	@GetMapping("/listar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<Persona> p = null;

		try {
			p = servicio.buscarPorId(id);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al realizar la consulta en la base de datos!");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}

		if (!p.isPresent()) {
			respuesta.put("mensaje",
					"La persona con ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
		return ResponseEntity.ok(p.get());
	}

	@PostMapping("/agregar")
	public ResponseEntity<?> guardar(@Valid @RequestBody Persona persona, BindingResult result) {
		Map<String, Object> respuesta = new HashMap<>();
		Persona personaNew = null;
		if (result.hasErrors()) {
			return this.validar(result);
		}
		try {
			personaNew = servicio.guardar(persona);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al realizar el insert en la base de datos!");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}

		return ResponseEntity.ok(personaNew);
	}

	@PostMapping("/agregar-lista")
	public ResponseEntity<?> guardar(@RequestBody List<Persona> persona) {
		Map<String, Object> respuesta = new HashMap<>();
		List<Persona> personasDb = new ArrayList<>();
		try {
			persona.forEach(p -> {
				personasDb.add(servicio.guardar(p));
			});
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al realizar el insert en la base de datos!");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}

		return ResponseEntity.ok(personasDb);
	}

	@PutMapping("/editar")
	public ResponseEntity<?> editar(@Valid @RequestBody Persona persona, BindingResult result) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<Persona> p = null;

		if (result.hasErrors()) {
			return this.validar(result);
		}

		try {
			p = servicio.buscarPorId(persona.getId());
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al realizar la consulta en la base de datos!");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}

		if (!p.isPresent()) {
			respuesta.put("mensaje",
					"La persona con ID: ".concat(persona.getId().toString()).concat(" no existe en la base de datos!"));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}

		Persona personaDb = p.get();
		personaDb.setNombre(persona.getNombre());
		personaDb.setApellido(persona.getApellido());
		personaDb.setFecha_nacimiento(persona.getFecha_nacimiento());
		personaDb.setDireccion(persona.getDireccion());
		personaDb.setTelefono(persona.getTelefono());
		personaDb.setPais(persona.getPais());

		try {
			personaDb = servicio.guardar(personaDb);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al realizar el insert en la base de datos!");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		return ResponseEntity.ok(personaDb);
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable Long id) {
		Map<String, Object> respuesta = new HashMap<>();
		Optional<Persona> p = servicio.buscarPorId(id);
		if (!p.isPresent()) {
			respuesta.put("mensaje",
					"La persona con ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
		}
		try {
			servicio.eliminarPorId(id);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al realizar el delete en la base de datos!");
			respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

		respuesta.put("mensaje", "Persona eliminada correctamente!");
		return ResponseEntity.status(HttpStatus.OK).body(respuesta);
	}

	/* */

	private ResponseEntity<?> validar(BindingResult result) {
		Map<String, Object> respuesta = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			respuesta.put(err.getField(), " El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(respuesta);
	}

}
