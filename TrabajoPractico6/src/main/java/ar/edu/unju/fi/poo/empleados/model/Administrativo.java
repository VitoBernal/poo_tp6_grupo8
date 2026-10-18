package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;

import ar.edu.unju.fi.poo.empleados.exception.EmpleadoException;

public class Administrativo extends Empleado {
	private Categoria categoria;

	public Administrativo(String legajo, int documento, String nombre, LocalDate fechaIngreso, int cantidadHijos,
			Categoria categoria) throws EmpleadoException {
		super(legajo, documento, nombre, fechaIngreso, cantidadHijos);
		this.categoria = validarCategoria(categoria);
	}

	/** El adicional del administrativo es el monto de su categoría. */
	@Override
	public double calcularAdicional() {
		return categoria.getMonto();
	}

	public void cambiarCategoria(Categoria nuevaCategoria) throws EmpleadoException {
		this.categoria = validarCategoria(nuevaCategoria);
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

	private static Categoria validarCategoria(Categoria categoria) throws EmpleadoException {
		if (categoria == null) {
			throw new EmpleadoException("La categoría es obligatoria para un administrativo");
		}
		return categoria;
	}
}