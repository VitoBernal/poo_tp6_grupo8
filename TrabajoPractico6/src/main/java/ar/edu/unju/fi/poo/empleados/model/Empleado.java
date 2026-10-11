package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Clase base de todos los empleados.
 *
 * El cálculo del sueldo es común a todos los tipos y se implementa una sola vez acá.
 * Lo único que cambia según el tipo es el adicional: calcularAdicional() devuelve 0
 * por defecto y cada subclase que tiene un adicional lo redefine.
 */
public abstract class Empleado {
	public static final double SUELDO_BASICO = 400000;
	public static final double MONTO_POR_ANIO_ANTIGUEDAD = 6500;
	public static final double MONTO_POR_HIJO = 15000;
	public static final double PORCENTAJE_DESCUENTO = 0.18;

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final int legajo;
	private int documento;
	private String nombre;
	private LocalDate fechaIngreso;
	private int cantidadHijos;

	protected Empleado(int legajo, int documento, String nombre, LocalDate fechaIngreso, int cantidadHijos) {
		if (legajo <= 0) {
			throw new IllegalArgumentException("El legajo debe ser mayor a 0");
		}
		if (documento <= 0) {
			throw new IllegalArgumentException("El documento debe ser mayor a 0");
		}
		if (nombre == null || nombre.isBlank()) {
			throw new IllegalArgumentException("El nombre es obligatorio");
		}
		if (fechaIngreso == null || fechaIngreso.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("La fecha de ingreso es obligatoria y no puede ser futura");
		}
		if (cantidadHijos < 0) {
			throw new IllegalArgumentException("La cantidad de hijos no puede ser negativa");
		}
		this.legajo = legajo;
		this.documento = documento;
		this.nombre = nombre;
		this.fechaIngreso = fechaIngreso;
		this.cantidadHijos = cantidadHijos;
	}

	/** Cada subclase indica de qué tipo de empleado se trata. */
	public abstract TipoEmpleado getTipo();

	/**
	 * Adicional propio del tipo de empleado. Por defecto un empleado no tiene adicional (0).
	 * Profesional, Administrativo y Limpieza lo redefinen.
	 */
	public double calcularAdicional() {
		return 0;
	}

	/** Años completos de antigüedad a la fecha actual. */
	public int calcularAntiguedad() {
		return Period.between(fechaIngreso, LocalDate.now()).getYears();
	}

	public double calcularMontoAntiguedad() {
		return calcularAntiguedad() * MONTO_POR_ANIO_ANTIGUEDAD;
	}

	/** Sueldo básico + adicional (si corresponde) + antigüedad. */
	public double calcularRemunerativosBonificables() {
		return SUELDO_BASICO + calcularAdicional() + calcularMontoAntiguedad();
	}

	public double calcularSalarioFamiliar() {
		return cantidadHijos * MONTO_POR_HIJO;
	}

	public double calcularDescuentos() {
		return calcularRemunerativosBonificables() * PORCENTAJE_DESCUENTO;
	}

	/** Remunerativos bonificables + salario familiar - descuentos. */
	public double calcularSueldoNeto() {
		return calcularRemunerativosBonificables() + calcularSalarioFamiliar() - calcularDescuentos();
	}

	public int getLegajo() {
		return legajo;
	}

	public int getDocumento() {
		return documento;
	}

	public void setDocumento(int documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public int getCantidadHijos() {
		return cantidadHijos;
	}

	public void setCantidadHijos(int cantidadHijos) {
		if (cantidadHijos < 0) {
			throw new IllegalArgumentException("La cantidad de hijos no puede ser negativa");
		}
		this.cantidadHijos = cantidadHijos;
	}

	@Override
	public String toString() {
		return "Legajo " + legajo + " | " + nombre + " | DNI " + documento + " | " + getTipo().getDescripcion()
				+ " | Ingreso " + fechaIngreso.format(FORMATO_FECHA) + " (" + calcularAntiguedad() + " años)"
				+ " | Hijos: " + cantidadHijos;
	}
}