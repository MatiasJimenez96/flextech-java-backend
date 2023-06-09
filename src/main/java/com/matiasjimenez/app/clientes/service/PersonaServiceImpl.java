package com.matiasjimenez.app.clientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matiasjimenez.app.clientes.models.entity.Persona;
import com.matiasjimenez.app.clientes.models.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository repositorio;

	@Override
	@Transactional(readOnly = true)
	public List<Persona> listar() {
		return repositorio.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	@Override
	@Transactional
	public Persona guardar(Persona persona) {
		return repositorio.save(persona);
	}

	@Override
	@Transactional
	public void eliminarPorId(Long id) {
		repositorio.deleteById(id);
	}

	@Override
	@Transactional
	public List<Persona> guardarLista(List<Persona> personas) {
		return repositorio.saveAll(personas);
	}

}
