package ar.edu.unju.fi.poo.empleados.model;

/**
 * Categorías de los empleados administrativos.
 * Cada categoría tiene una descripción y el monto que suma a los remunerativos bonificables.
 */
public enum Categoria {
	A("Auxiliar", 30000),
	B("Ventas", 45000),
	C("Gerencia", 55000);

	private final String descripcion;
	private final double monto;

	Categoria(String descripcion, double monto) {
		this.descripcion = descripcion;
		this.monto = monto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getMonto() {
		return monto;
	}

	@Override
	public String toString() {
		return name() + " - " + descripcion;
	}
}