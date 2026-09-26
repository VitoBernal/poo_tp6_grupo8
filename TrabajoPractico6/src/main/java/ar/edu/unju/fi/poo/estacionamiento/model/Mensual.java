package ar.edu.unju.fi.poo.estacionamiento.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Mensual extends RegistroIngresoSalida {
	private Cliente cliente;

	public Mensual(Vehiculo vehiculo, LocalDate fecha, LocalTime hora, Cliente cliente) {
		super(vehiculo, fecha, hora);
		if (cliente == null)
			throw new IllegalArgumentException("cliente requerido para tarifa mensual");
		this.cliente = cliente;
	}

	@Override
	public Double obtenerImporte() {
		return 0.0;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}