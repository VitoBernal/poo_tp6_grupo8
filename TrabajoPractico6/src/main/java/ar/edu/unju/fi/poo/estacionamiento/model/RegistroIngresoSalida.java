package ar.edu.unju.fi.poo.estacionamiento.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class RegistroIngresoSalida {
	public static final String ESTADO_INGRESADO = "INGRESADO";
	public static final String ESTADO_AFUERA = "AFUERA";

	private static final AtomicInteger CONTADOR = new AtomicInteger(0);

	private int id;
	private LocalDate fecha;
	private LocalTime hora;
	private Vehiculo vehiculo;
	private String estado;

	public RegistroIngresoSalida(Vehiculo vehiculo, LocalDate fecha, LocalTime hora) {
		if (vehiculo == null)
			throw new IllegalArgumentException("vehiculo requerido");
		if (fecha == null)
			fecha = LocalDate.now();
		if (hora == null)
			hora = LocalTime.now();
		this.id = CONTADOR.incrementAndGet();
		this.fecha = fecha;
		this.hora = hora;
		this.vehiculo = vehiculo;
		this.estado = ESTADO_AFUERA;
	}

	/** Cada subclase define cómo calcula el importe a pagar. */
	public abstract Double obtenerImporte();

	public void cambiarEstado(String estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "%s{id=%d, patente=%s, fecha=%s, hora=%s, estado=%s, importe=%.2f}".formatted(getClass().getSimpleName(),
				id, vehiculo.getPatente(), fecha, hora, estado, obtenerImporte());
	}
}