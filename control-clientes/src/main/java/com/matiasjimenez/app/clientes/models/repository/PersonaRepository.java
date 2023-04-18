package com.matiasjimenez.app.clientes.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matiasjimenez.app.clientes.models.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long>{

}
