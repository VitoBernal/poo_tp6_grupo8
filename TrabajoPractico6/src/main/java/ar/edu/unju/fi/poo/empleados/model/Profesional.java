package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.edu.unju.fi.poo.empleados.exception.EmpleadoException;

public class Profesional extends Empleado {
	public static final double ADICIONAL_POR_TITULO = 30000;

	private final List<Titulo> titulos = new ArrayList<>();

	/** Un profesional se registra con al menos un título (cardinalidad 1..*). */
	public Profesional(String legajo, int documento, String nombre, LocalDate fechaIngreso, int cantidadHijos,
			Titulo tituloInicial) throws EmpleadoException {
		super(legajo, documento, nombre, fechaIngreso, cantidadHijos);
		titulos.add(validarTitulo(tituloInicial));
	}

	public void agregarTitulo(Titulo titulo) throws EmpleadoException {
		titulos.add(validarTitulo(titulo));
	}

	/** $30.000 por cada título que posea. */
	@Override
	public double calcularAdicional() {
		return titulos.size() * ADICIONAL_POR_TITULO;
	}

	public List<Titulo> getTitulos() {
		return Collections.unmodifiableList(titulos);
	}

	@Override
	public TipoEmpleado getTipo() {
		return TipoEmpleado.PROFESIONAL;
	}

	@Override
	public String toString() {
		return super.toString() + " | Títulos: " + titulos;
	}

	private static Titulo validarTitulo(Titulo titulo) throws EmpleadoException {
		if (titulo == null) {
			throw new EmpleadoException("Un profesional debe tener al menos un título (Universitario o Terciario)");
		}
		return titulo;
	}
}