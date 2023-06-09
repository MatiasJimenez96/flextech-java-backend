package com.matiasjimenez.app.clientes.service;

import java.util.List;
import java.util.Optional;

import com.matiasjimenez.app.clientes.models.entity.Persona;

public interface PersonaService {

	public List<Persona> listar();

	public Optional<Persona> buscarPorId(Long id);
	
	public Persona guardar(Persona persona);
	
	public List<Persona> guardarLista(List<Persona> personas);

	public void eliminarPorId(Long id);

}
