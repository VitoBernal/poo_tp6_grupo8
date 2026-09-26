package ar.edu.unju.fi.poo.estacionamiento.model;

import java.time.LocalDate;

public class Cupon {
	private String codigo;
	private LocalDate fechaVencimiento;
	private double porcentajeDescuento;

	public Cupon(String codigo, LocalDate fechaVencimiento, double porcentajeDescuento) {
		if (codigo == null || codigo.isBlank())
			throw new IllegalArgumentException("codigo requerido");
		if (fechaVencimiento == null)
			throw new IllegalArgumentException("fechaVencimiento requerida");
		if (porcentajeDescuento <= 0 || porcentajeDescuento > 100)
			throw new IllegalArgumentException("porcentajeDescuento debe estar entre 0 y 100");
		this.codigo = codigo;
		this.fechaVencimiento = fechaVencimiento;
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public boolean esValido() {
		return !LocalDate.now().isAfter(fechaVencimiento);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(double p) {
		this.porcentajeDescuento = p;
	}

	@Override
	public String toString() {
		return "Cupon{codigo='%s', vence=%s, descuento=%.2f%%}".formatted(codigo, fechaVencimiento,
				porcentajeDescuento);
	}
}