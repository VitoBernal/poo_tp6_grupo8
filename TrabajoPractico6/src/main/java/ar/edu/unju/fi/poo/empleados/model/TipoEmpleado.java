package ar.edu.unju.fi.poo.empleados.model;

public enum TipoEmpleado {
	PROFESIONAL("Profesional"),
	ADMINISTRATIVO("Administrativo"),
	LIMPIEZA("Limpieza");

	private final String descripcion;

	TipoEmpleado(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}
}