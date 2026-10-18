package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.format.DateTimeFormatter;

import ar.edu.unju.fi.poo.empleados.exception.EmpleadoException;

/**
 * Clase base de todos los empleados.
 *
 * El cálculo del sueldo es común a todos los tipos y se implementa una sola vez
 * acá. Lo único que cambia según el tipo es el adicional: calcularAdicional()
 * devuelve 0 por defecto y cada subclase que tiene un adicional lo redefine.
 */
public abstract class Empleado {
	public static final double SUELDO_BASICO = 400000;
	public static final double MONTO_POR_ANIO_ANTIGUEDAD = 6500;
	public static final double MONTO_POR_HIJO = 15000;
	public static final double PORCENTAJE_DESCUENTO = 0.18;
	public static final int MAX_HIJOS = 5;

	/** Formato del legajo: 4 dígitos, un guion y 2 dígitos (NNNN-AA). */
	private static final String FORMATO_LEGAJO = "\\d{4}-\\d{2}";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final String legajo;
	private final int documento;
	private String nombre;
	private LocalDate fechaIngreso;
	private int cantidadHijos;

	protected Empleado(String legajo, int documento, String nombre, LocalDate fechaIngreso, int cantidadHijos)
			throws EmpleadoException {
		validarLegajo(legajo);
		if (documento <= 0) {
			throw new EmpleadoException("El documento debe ser mayor a 0");
		}
		validarNombre(nombre);
		validarFechaIngreso(fechaIngreso);
		validarCantidadHijos(cantidadHijos);
		this.legajo = legajo;
		this.documento = documento;
		this.nombre = nombre;
		this.fechaIngreso = fechaIngreso;
		this.cantidadHijos = cantidadHijos;
	}

	// ---------------------------------------------------------------- legajo

	/** Últimos 2 dígitos del año actual, por ejemplo "26" en 2026. */
	public static String sufijoAnioActual() {
		return String.format("%02d", Year.now().getValue() % 100);
	}

	/**
	 * Un legajo es válido si tiene el formato NNNN-AA y AA son los últimos 2
	 * dígitos del año actual.
	 */
	public static boolean esLegajoValido(String legajo) {
		return legajo != null && legajo.matches(FORMATO_LEGAJO) && legajo.endsWith("-" + sufijoAnioActual());
	}

	// ---------------------------------------------------------------- cálculo del
	// sueldo

	/** Cada subclase indica de qué tipo de empleado se trata. */
	public abstract TipoEmpleado getTipo();

	/**
	 * Adicional propio del tipo de empleado. Por defecto un empleado no tiene
	 * adicional (0). Profesional, Administrativo y Limpieza lo redefinen.
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

	// ---------------------------------------------------------------- getters y
	// setters

	public String getLegajo() {
		return legajo;
	}

	public int getDocumento() {
		return documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws EmpleadoException {
		validarNombre(nombre);
		this.nombre = nombre;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public int getCantidadHijos() {
		return cantidadHijos;
	}

	public void setCantidadHijos(int cantidadHijos) throws EmpleadoException {
		validarCantidadHijos(cantidadHijos);
		this.cantidadHijos = cantidadHijos;
	}

	@Override
	public String toString() {
		return "Legajo " + legajo + " | " + nombre + " | DNI " + documento + " | " + getTipo().getDescripcion()
				+ " | Ingreso " + fechaIngreso.format(FORMATO_FECHA) + " (" + calcularAntiguedad() + " años)"
				+ " | Hijos: " + cantidadHijos;
	}

	// ---------------------------------------------------------------- validaciones

	private static void validarLegajo(String legajo) throws EmpleadoException {
		if (!esLegajoValido(legajo)) {
			throw new EmpleadoException("El legajo '" + legajo + "' no cumple el formato NNNN-AA "
					+ "(4 dígitos, un guion y los últimos 2 dígitos del año actual: " + sufijoAnioActual() + ")");
		}
	}

	private static void validarNombre(String nombre) throws EmpleadoException {
		if (nombre == null || nombre.isBlank()) {
			throw new EmpleadoException("El nombre es obligatorio");
		}
	}

	private static void validarFechaIngreso(LocalDate fechaIngreso) throws EmpleadoException {
		if (fechaIngreso == null) {
			throw new EmpleadoException("La fecha de ingreso es obligatoria");
		}
		if (fechaIngreso.isAfter(LocalDate.now())) {
			throw new EmpleadoException("La fecha de ingreso (" + fechaIngreso.format(FORMATO_FECHA)
					+ ") no puede ser posterior a la fecha actual");
		}
	}

	private static void validarCantidadHijos(int cantidadHijos) throws EmpleadoException {
		if (cantidadHijos < 0 || cantidadHijos > MAX_HIJOS) {
			throw new EmpleadoException(
					"La cantidad de hijos debe estar entre 0 y " + MAX_HIJOS + " (se ingresó " + cantidadHijos + ")");
		}
	}
}