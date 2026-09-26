package ar.edu.unju.fi.poo.estacionamiento.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class PorHora extends RegistroIngresoSalida {
	public static final double VALOR_HORA_DEFECTO = 1000.0;
	public static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
	public static final LocalTime HORA_ULTIMO_INGRESO = LocalTime.of(21, 0);

	private Cupon cupon;
	private double valorHora;

	public PorHora(Vehiculo vehiculo, LocalDate fecha, LocalTime hora) {
		this(vehiculo, fecha, hora, null, VALOR_HORA_DEFECTO);
	}

	public PorHora(Vehiculo vehiculo, LocalDate fecha, LocalTime hora, Cupon cupon, double valorHora) {
		super(vehiculo, fecha, hora);
		if (hora.isBefore(HORA_APERTURA) || hora.isAfter(HORA_ULTIMO_INGRESO)) {
			throw new IllegalArgumentException("Ingreso fuera de horario. Permitido de 08:00 a 21:00. Hora: " + hora);
		}
		this.cupon = cupon;
		this.valorHora = valorHora;
	}

	@Override
	public Double obtenerImporte() {
		LocalDateTime ingreso = LocalDateTime.of(getFecha(), getHora());
		LocalDateTime salida = LocalDateTime.now();

		long minutos = ChronoUnit.MINUTES.between(ingreso, salida);
		if (minutos < 0)
			minutos = 0;
		long horas = (long) Math.ceil(minutos / 60.0);
		if (horas < 1)
			horas = 1;

		double importe = horas * valorHora;
		if (cupon != null && cupon.esValido()) {
			importe -= importe * cupon.getPorcentajeDescuento() / 100.0;
		}
		return importe;
	}

	public Cupon getCupon() {
		return cupon;
	}

	public void setCupon(Cupon cupon) {
		this.cupon = cupon;
	}

	public double getValorHora() {
		return valorHora;
	}

	public void setValorHora(double valorHora) {
		this.valorHora = valorHora;
	}
}