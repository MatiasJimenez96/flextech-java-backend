package com.matiasjimenez.app.clientes.models.entity;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:4200")
@Entity
@Table(name = "personas")
public class Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotEmpty
	private String nombre;
	
	@Column(nullable = false)
	@NotEmpty
	private String apellido;
	
	@Column(name = "fecha_nacimiento", nullable = false)
	@NotNull
	private LocalDate fecha_nacimiento;
	
	@Column(nullable = false)
	@NotEmpty
	private String direccion;
	
	@Column(nullable = false)
	@NotEmpty
	private String telefono;
	
	@Column(nullable = false)
	@NotEmpty
	private String pais;
	
	/* */
	
	public Persona() {
	}
	
	public Persona(String apellido, LocalDate fecha_nacimiento, String direccion, String telefono, String pais) {
		this.apellido = apellido;
		this.fecha_nacimiento = fecha_nacimiento;
		this.direccion = direccion;
		this.telefono = telefono;
		this.pais = pais;
	}
	
	/* */
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	

}
