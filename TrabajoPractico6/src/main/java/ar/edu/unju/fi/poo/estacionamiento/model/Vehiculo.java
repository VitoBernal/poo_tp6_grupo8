package ar.edu.unju.fi.poo.estacionamiento.model;

import java.util.Objects;

public class Vehiculo {
	private String patente;
	private String marca;
	private String color;

	public Vehiculo(String patente, String marca, String color) {
		this.patente = Objects.requireNonNull(patente, "patente requerida").toUpperCase();
		this.marca = Objects.requireNonNull(marca, "marca requerida");
		this.color = Objects.requireNonNull(color, "color requerido");
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente.toUpperCase();
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Vehiculo v))
			return false;
		return Objects.equals(patente, v.patente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(patente);
	}

	@Override
	public String toString() {
		return "Vehiculo{patente='%s', marca='%s', color='%s'}".formatted(patente, marca, color);
	}
}