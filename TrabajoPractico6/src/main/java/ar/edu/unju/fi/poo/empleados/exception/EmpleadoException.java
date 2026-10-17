package ar.edu.unju.fi.poo.empleados.exception;

/**
 * Excepción personalizada para las validaciones del modelo de empleados
 * (legajo, documento, fecha de ingreso, cantidad de hijos, títulos, etc.).
 */
public class EmpleadoException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmpleadoException(String mensaje) {
		super(mensaje);
	}
}