package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Profesional extends Empleado {
	public static final double ADICIONAL_POR_TITULO = 30000;

	private final List<Titulo> titulos = new ArrayList<>();

	/** Un profesional tiene al menos un título (cardinalidad 1..*). */
	public Profesional(int legajo, int documento, String nombre, LocalDate fechaIngreso, int cantidadHijos,
			Titulo tituloInicial) {
		super(legajo, documento, nombre, fechaIngreso, cantidadHijos);
		titulos.add(validarTitulo(tituloInicial));
	}

	public void agregarTitulo(Titulo titulo) {
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

	private static Titulo validarTitulo(Titulo titulo) {
		if (titulo == null) {
			throw new IllegalArgumentException("El título es obligatorio");
		}
		return titulo;
	}
}