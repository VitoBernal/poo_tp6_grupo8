package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;

public class Administrativo extends Empleado {
	private Categoria categoria;

	public Administrativo(int legajo, int documento, String nombre, LocalDate fechaIngreso, int cantidadHijos,
			Categoria categoria) {
		super(legajo, documento, nombre, fechaIngreso, cantidadHijos);
		if (categoria == null) {
			throw new IllegalArgumentException("La categoría es obligatoria para un administrativo");
		}
		this.categoria = categoria;
	}

	/** El adicional del administrativo es el monto de su categoría. */
	@Override
	public double calcularAdicional() {
		return categoria.getMonto();
	}

	public void cambiarCategoria(Categoria nuevaCategoria) {
		if (nuevaCategoria == null) {
			throw new IllegalArgumentException("La categoría es obligatoria");
		}
		this.categoria = nuevaCategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	@Override
	public TipoEmpleado getTipo() {
		return TipoEmpleado.ADMINISTRATIVO;
	}

	@Override
	public String toString() {
		return super.toString() + " | Categoría " + categoria;
	}
}
