package ar.edu.unju.fi.poo.empleados.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ar.edu.unju.fi.poo.empleados.exception.EmpleadoException;
import ar.edu.unju.fi.poo.empleados.model.Administrativo;
import ar.edu.unju.fi.poo.empleados.model.Categoria;
import ar.edu.unju.fi.poo.empleados.model.Empleado;
import ar.edu.unju.fi.poo.empleados.model.Limpieza;
import ar.edu.unju.fi.poo.empleados.model.NivelTitulo;
import ar.edu.unju.fi.poo.empleados.model.Profesional;
import ar.edu.unju.fi.poo.empleados.model.TipoEmpleado;
import ar.edu.unju.fi.poo.empleados.model.Titulo;

/**
 * Gestiona la lista de empleados de la empresa.
 *
 * Reglas de negocio que controla: - El legajo y el documento no se pueden
 * repetir. - El legajo se genera con el formato NNNN-AA (4 dígitos aleatorios y
 * los últimos 2 dígitos del año). - Solo a un administrativo se le puede
 * cambiar la categoría. - Solo a un profesional se le puede agregar un título.
 */
public class ManagerEmpleado {
	private static final Random RANDOM = new Random();

	private final List<Empleado> empleados = new ArrayList<>();

	/** Carga 6 empleados iniciales, dos de cada tipo, con legajos generados. */
	public void inicializarEmpleados() throws EmpleadoException {
		agregarEmpleado(new Limpieza(generarLegajo(), 30125478, "Roberto Cardozo", LocalDate.of(2015, 5, 16), 3));
		agregarEmpleado(new Limpieza(generarLegajo(), 35874123, "Josefina Cruz", LocalDate.of(2016, 7, 8), 1));
		agregarEmpleado(new Administrativo(generarLegajo(), 34828945, "Luciana Narvaez", LocalDate.of(2023, 9, 27), 2,
				Categoria.A));
		agregarEmpleado(new Administrativo(generarLegajo(), 38452147, "Yanina Lopez", LocalDate.of(2021, 3, 2), 0,
				Categoria.B));
		agregarEmpleado(new Profesional(generarLegajo(), 28923876, "Juan Vargas", LocalDate.of(2019, 11, 23), 4,
				new Titulo(2012, "Contador Público", NivelTitulo.UNIVERSITARIO)));
		Profesional gonzalez = new Profesional(generarLegajo(), 40123987, "Nicolas Gonzalez", LocalDate.of(2024, 4, 29),
				0, new Titulo(2020, "Técnico Superior en Programación", NivelTitulo.TERCIARIO));
		gonzalez.agregarTitulo(new Titulo(2023, "Licenciatura en Sistemas", NivelTitulo.UNIVERSITARIO));
		agregarEmpleado(gonzalez);
	}

	/**
	 * Genera un legajo nuevo con el formato NNNN-AA: un número aleatorio de 4
	 * dígitos (1000 a 9999) y los últimos 2 dígitos del año actual. Nunca devuelve
	 * un legajo que ya exista.
	 */
	public String generarLegajo() {
		String legajo;
		do {
			int numero = 1000 + RANDOM.nextInt(9000);
			legajo = numero + "-" + Empleado.sufijoAnioActual();
		} while (existeLegajo(legajo));
		return legajo;
	}

	/** Agrega un empleado validando que su legajo y su documento no existan. */
	public void agregarEmpleado(Empleado empleado) throws EmpleadoException {
		if (empleado == null) {
			throw new EmpleadoException("El empleado es obligatorio");
		}
		if (existeLegajo(empleado.getLegajo())) {
			throw new EmpleadoException("Ya existe un empleado con legajo " + empleado.getLegajo());
		}
		if (existeDocumento(empleado.getDocumento())) {
			throw new EmpleadoException("Ya existe un empleado con documento " + empleado.getDocumento());
		}
		empleados.add(empleado);
	}

	public boolean existeLegajo(String legajo) {
		return buscarEmpleado(legajo) != null;
	}

	public boolean existeDocumento(int documento) {
		return buscarPorDocumento(documento) != null;
	}

	/** @return el empleado con ese legajo, o null si no existe */
	public Empleado buscarEmpleado(String legajo) {
		for (Empleado empleado : empleados) {
			if (empleado.getLegajo().equals(legajo)) {
				return empleado;
			}
		}
		return null;
	}

	/** @return el empleado con ese documento, o null si no existe */
	public Empleado buscarPorDocumento(int documento) {
		for (Empleado empleado : empleados) {
			if (empleado.getDocumento() == documento) {
				return empleado;
			}
		}
		return null;
	}

	/**
	 * @return el administrativo con ese legajo, o null si no existe o no es
	 *         administrativo
	 */
	public Administrativo buscarAdministrativo(String legajo) {
		if (buscarEmpleado(legajo) instanceof Administrativo administrativo) {
			return administrativo;
		}
		return null;
	}

	/**
	 * @return el profesional con ese legajo, o null si no existe o no es
	 *         profesional
	 */
	public Profesional buscarProfesional(String legajo) {
		if (buscarEmpleado(legajo) instanceof Profesional profesional) {
			return profesional;
		}
		return null;
	}

	public List<Empleado> obtenerPorTipo(TipoEmpleado tipo) {
		List<Empleado> resultado = new ArrayList<>();
		for (Empleado empleado : empleados) {
			if (empleado.getTipo() == tipo) {
				resultado.add(empleado);
			}
		}
		return resultado;
	}

	public List<Administrativo> obtenerPorCategoria(Categoria categoria) {
		List<Administrativo> resultado = new ArrayList<>();
		for (Empleado empleado : empleados) {
			if (empleado instanceof Administrativo administrativo && administrativo.getCategoria() == categoria) {
				resultado.add(administrativo);
			}
		}
		return resultado;
	}

	/**
	 * @return true si se cambió, false si el legajo no corresponde a un
	 *         administrativo
	 */
	public boolean cambiarCategoria(String legajo, Categoria nuevaCategoria) throws EmpleadoException {
		Administrativo administrativo = buscarAdministrativo(legajo);
		if (administrativo == null) {
			return false;
		}
		administrativo.cambiarCategoria(nuevaCategoria);
		return true;
	}

	/**
	 * @return true si se agregó, false si el legajo no corresponde a un profesional
	 */
	public boolean agregarTitulo(String legajo, Titulo titulo) throws EmpleadoException {
		Profesional profesional = buscarProfesional(legajo);
		if (profesional == null) {
			return false;
		}
		profesional.agregarTitulo(titulo);
		return true;
	}

	public double calcularTotalRemunerativos(List<? extends Empleado> lista) {
		double total = 0;
		for (Empleado empleado : lista) {
			total += empleado.calcularRemunerativosBonificables();
		}
		return total;
	}

	public double calcularTotalSalarioFamiliar(List<? extends Empleado> lista) {
		double total = 0;
		for (Empleado empleado : lista) {
			total += empleado.calcularSalarioFamiliar();
		}
		return total;
	}

	public double calcularTotalDescuentos(List<? extends Empleado> lista) {
		double total = 0;
		for (Empleado empleado : lista) {
			total += empleado.calcularDescuentos();
		}
		return total;
	}

	public double calcularTotalNeto(List<? extends Empleado> lista) {
		double total = 0;
		for (Empleado empleado : lista) {
			total += empleado.calcularSueldoNeto();
		}
		return total;
	}

	/** Importe neto acumulado de todos los empleados del tipo indicado. */
	public double calcularImporteNetoPorTipo(TipoEmpleado tipo) {
		return calcularTotalNeto(obtenerPorTipo(tipo));
	}

	public List<Empleado> getEmpleados() {
		return Collections.unmodifiableList(empleados);
	}
}