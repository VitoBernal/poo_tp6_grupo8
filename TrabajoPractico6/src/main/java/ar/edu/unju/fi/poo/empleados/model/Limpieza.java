package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;

public class Limpieza extends Empleado {
	public static final double ADICIONAL_INSALUBRIDAD = 25000;

	public Limpieza(int legajo, int documento, String nombre, LocalDate fechaIngreso, int cantidadHijos) {
		super(legajo, documento, nombre, fechaIngreso, cantidadHijos);
	}

	@Override
	public double calcularAdicional() {
		return ADICIONAL_INSALUBRIDAD;
	}

	@Override
	public TipoEmpleado getTipo() {
		return TipoEmpleado.LIMPIEZA;
	}
}