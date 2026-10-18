package ar.edu.unju.fi.poo.empleados.model;

import ar.edu.unju.fi.poo.empleados.exception.EmpleadoException;

public class Titulo {
	private final int anio;
	private final String carrera;
	private final NivelTitulo nivel;

	public Titulo(int anio, String carrera, NivelTitulo nivel) throws EmpleadoException {
		if (anio <= 0) {
			throw new EmpleadoException("El año del título debe ser mayor a 0");
		}
		if (carrera == null || carrera.isBlank()) {
			throw new EmpleadoException("La carrera del título es obligatoria");
		}
		if (nivel == null) {
			throw new EmpleadoException("El nivel del título es obligatorio (Universitario o Terciario)");
		}
		this.anio = anio;
		this.carrera = carrera;
		this.nivel = nivel;
	}

	public int getAnio() {
		return anio;
	}

	public String getCarrera() {
		return carrera;
	}

	public NivelTitulo getNivel() {
		return nivel;
	}

	@Override
	public String toString() {
		return carrera + " (" + nivel + ", " + anio + ")";
	}
}