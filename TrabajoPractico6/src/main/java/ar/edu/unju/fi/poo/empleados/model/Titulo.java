package ar.edu.unju.fi.poo.empleados.model;

public class Titulo {
	private int anio;
	private String carrera;
	private NivelTitulo nivel;

	public Titulo(int anio, String carrera, NivelTitulo nivel) {
		if (anio <= 0) {
			throw new IllegalArgumentException("El año del título debe ser mayor a 0");
		}
		if (carrera == null || carrera.isBlank()) {
			throw new IllegalArgumentException("La carrera es obligatoria");
		}
		if (nivel == null) {
			throw new IllegalArgumentException("El nivel del título es obligatorio");
		}
		this.anio = anio;
		this.carrera = carrera;
		this.nivel = nivel;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public NivelTitulo getNivel() {
		return nivel;
	}

	public void setNivel(NivelTitulo nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		return carrera + " (" + nivel + ", " + anio + ")";
	}
}