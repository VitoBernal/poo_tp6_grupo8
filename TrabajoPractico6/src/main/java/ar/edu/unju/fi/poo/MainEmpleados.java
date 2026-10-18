package ar.edu.unju.fi.poo;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import ar.edu.unju.fi.poo.empleados.exception.EmpleadoException;
import ar.edu.unju.fi.poo.empleados.manager.ManagerEmpleado;
import ar.edu.unju.fi.poo.empleados.model.Administrativo;
import ar.edu.unju.fi.poo.empleados.model.Categoria;
import ar.edu.unju.fi.poo.empleados.model.Empleado;
import ar.edu.unju.fi.poo.empleados.model.Limpieza;
import ar.edu.unju.fi.poo.empleados.model.NivelTitulo;
import ar.edu.unju.fi.poo.empleados.model.Profesional;
import ar.edu.unju.fi.poo.empleados.model.TipoEmpleado;
import ar.edu.unju.fi.poo.empleados.model.Titulo;

public class MainEmpleados {
	private static final Locale AR = Locale.forLanguageTag("es-AR");

	/**
	 * Crea un empleado; puede lanzar EmpleadoException si algún dato no es válido.
	 */
	@FunctionalInterface
	private interface CreadorEmpleado {
		Empleado crear() throws EmpleadoException;
	}

	public static void main(String[] args) {
		ManagerEmpleado manager = new ManagerEmpleado();
		try {
			manager.inicializarEmpleados();
		} catch (EmpleadoException e) {
			System.out.println("Error al inicializar los empleados: " + e.getMessage());
			return;
		}

		titulo("Empleados iniciales (" + manager.getEmpleados().size() + ")");
		for (Empleado empleado : manager.getEmpleados()) {
			System.out.println(empleado);
			System.out.println("    Sueldo neto: " + pesos(empleado.calcularSueldoNeto()));
		}

		// a. Agregar empleados de distintos tipos
		titulo("a) Agregar empleados de distintos tipos");
		agregar(manager,
				() -> new Limpieza(manager.generarLegajo(), 36827937, "Alejandra Gomez", LocalDate.of(2018, 8, 29), 0));
		agregar(manager, () -> new Administrativo(manager.generarLegajo(), 41836928, "Tobias Perez",
				LocalDate.of(2018, 10, 20), 2, Categoria.C));
		agregar(manager, () -> new Profesional(manager.generarLegajo(), 33829018, "Soledad Nieva",
				LocalDate.of(2024, 7, 27), 3, new Titulo(2019, "Abogacía", NivelTitulo.UNIVERSITARIO)));

		// Validaciones del TP8: cada caso debe ser rechazado con EmpleadoException
		titulo("Validaciones (TP8)");
		String legajoExistente = legajoDe(manager, 30125478);
		System.out.println("Legajo repetido:");
		agregar(manager, () -> new Limpieza(legajoExistente, 25478963, "Carlos Ruiz", LocalDate.of(2020, 1, 10), 1));
		System.out.println("a. Documento repetido (30125478 ya pertenece a Roberto Cardozo):");
		agregar(manager,
				() -> new Limpieza(manager.generarLegajo(), 30125478, "Carlos Ruiz", LocalDate.of(2020, 1, 10), 1));
		System.out.println("b. Legajo con formato inválido:");
		agregar(manager, () -> new Limpieza("123-45", 25478963, "Carlos Ruiz", LocalDate.of(2020, 1, 10), 1));
		System.out.println("c. Fecha de ingreso posterior a hoy:");
		agregar(manager,
				() -> new Limpieza(manager.generarLegajo(), 25478963, "Carlos Ruiz", LocalDate.now().plusDays(1), 1));
		System.out.println("d. Cantidad de hijos fuera de rango (6):");
		agregar(manager,
				() -> new Limpieza(manager.generarLegajo(), 25478963, "Carlos Ruiz", LocalDate.of(2020, 1, 10), 6));
		System.out.println("e. Profesional sin título:");
		agregar(manager, () -> new Profesional(manager.generarLegajo(), 25478963, "Carlos Ruiz",
				LocalDate.of(2020, 1, 10), 1, null));

		// b. Buscar un empleado por legajo y mostrar sus datos y sueldo neto
		titulo("b) Buscar empleado por legajo y mostrar datos + sueldo neto");
		mostrarEmpleadoPorLegajo(manager, legajoDe(manager, 36827937));
		mostrarEmpleadoPorLegajo(manager, "0000-" + Empleado.sufijoAnioActual());

		// c. Buscar un administrativo, cambiar su categoría y mostrar el sueldo neto
		titulo("c) Buscar administrativo, cambiar categoría y mostrar sueldo neto");
		cambiarCategoria(manager, legajoDe(manager, 38452147), Categoria.C);
		cambiarCategoria(manager, legajoDe(manager, 30125478), Categoria.B); // es de limpieza

		// d. Buscar un profesional, agregarle un título y mostrar el sueldo neto
		titulo("d) Buscar profesional, agregar título y mostrar sueldo neto");
		agregarTitulo(manager, legajoDe(manager, 28923876), 2018, "Licenciatura en Administración",
				NivelTitulo.UNIVERSITARIO);
		agregarTitulo(manager, legajoDe(manager, 34828945), 2015, "Martillero Público", NivelTitulo.TERCIARIO); // es
																												// administrativo

		try (Scanner sc = new Scanner(System.in)) {
			// e. Empleados de una categoría X con totales acumulados
			titulo("e) Empleados de una categoría y totales acumulados");
			Categoria categoria = pedirCategoria(sc);
			if (categoria != null) {
				System.out.println("Empleados de la categoría " + categoria + ":");
				mostrarTabla(manager, manager.obtenerPorCategoria(categoria));
			}

			// f. Importe neto acumulado de un tipo de empleado elegido por el usuario
			titulo("f) Importe neto acumulado por tipo de empleado");
			TipoEmpleado tipo = pedirTipo(sc);
			if (tipo != null) {
				System.out.println("Empleados de tipo " + tipo.getDescripcion() + ":");
				mostrarTabla(manager, manager.obtenerPorTipo(tipo));
				System.out.println("Importe neto acumulado (" + tipo.getDescripcion() + "): "
						+ pesos(manager.calcularImporteNetoPorTipo(tipo)));
			}
		}
	}

	// ---------------------------------------------------------------- casos de
	// prueba

	private static void agregar(ManagerEmpleado manager, CreadorEmpleado creador) {
		try {
			Empleado empleado = creador.crear();
			manager.agregarEmpleado(empleado);
			System.out.println("  Agregado: " + empleado.getTipo().getDescripcion() + " legajo " + empleado.getLegajo()
					+ " - " + empleado.getNombre());
		} catch (EmpleadoException e) {
			System.out.println("  Rechazado: " + e.getMessage());
		}
	}

	/** Legajo del empleado con ese documento (los legajos se generan al azar). */
	private static String legajoDe(ManagerEmpleado manager, int documento) {
		Empleado empleado = manager.buscarPorDocumento(documento);
		return empleado != null ? empleado.getLegajo() : null;
	}

	private static void mostrarEmpleadoPorLegajo(ManagerEmpleado manager, String legajo) {
		Empleado empleado = manager.buscarEmpleado(legajo);
		if (empleado == null) {
			System.out.println("No existe un empleado con legajo " + legajo);
			return;
		}
		mostrarFicha(empleado);
	}

	private static void cambiarCategoria(ManagerEmpleado manager, String legajo, Categoria nuevaCategoria) {
		Administrativo administrativo = manager.buscarAdministrativo(legajo);
		if (administrativo == null) {
			System.out.println("El legajo " + legajo + " no corresponde a un empleado administrativo");
			return;
		}
		try {
			System.out.println(administrativo.getNombre() + " (legajo " + legajo + ")");
			System.out.println("  Categoría actual " + administrativo.getCategoria() + " -> sueldo neto "
					+ pesos(administrativo.calcularSueldoNeto()));
			manager.cambiarCategoria(legajo, nuevaCategoria);
			System.out.println("  Categoría nueva  " + administrativo.getCategoria() + " -> sueldo neto "
					+ pesos(administrativo.calcularSueldoNeto()));
		} catch (EmpleadoException e) {
			System.out.println("  No se pudo cambiar la categoría: " + e.getMessage());
		}
	}

	private static void agregarTitulo(ManagerEmpleado manager, String legajo, int anio, String carrera,
			NivelTitulo nivel) {
		Profesional profesional = manager.buscarProfesional(legajo);
		if (profesional == null) {
			System.out.println("El legajo " + legajo + " no corresponde a un empleado profesional");
			return;
		}
		try {
			Titulo titulo = new Titulo(anio, carrera, nivel);
			System.out.println(profesional.getNombre() + " (legajo " + legajo + ")");
			System.out.println("  Con " + profesional.getTitulos().size() + " título(s) -> sueldo neto "
					+ pesos(profesional.calcularSueldoNeto()));
			manager.agregarTitulo(legajo, titulo);
			System.out.println("  Se agregó: " + titulo);
			System.out.println("  Con " + profesional.getTitulos().size() + " título(s) -> sueldo neto "
					+ pesos(profesional.calcularSueldoNeto()));
		} catch (EmpleadoException e) {
			System.out.println("  No se pudo agregar el título: " + e.getMessage());
		}
	}

	// ---------------------------------------------------------------- entrada del
	// usuario

	private static Categoria pedirCategoria(Scanner sc) {
		while (true) {
			System.out.print("Ingrese la categoría a consultar (A = Auxiliar, B = Ventas, C = Gerencia): ");
			if (!sc.hasNextLine()) {
				System.out.println("\nNo se ingresó ninguna categoría.");
				return null;
			}
			String opcion = sc.nextLine().trim().toUpperCase();
			try {
				return Categoria.valueOf(opcion);
			} catch (IllegalArgumentException e) {
				System.out.println("Opción inválida, intente nuevamente.");
			}
		}
	}

	private static TipoEmpleado pedirTipo(Scanner sc) {
		while (true) {
			System.out.print("Ingrese el tipo de empleado (P = Profesional, A = Administrativo, L = Limpieza): ");
			if (!sc.hasNextLine()) {
				System.out.println("\nNo se ingresó ningún tipo.");
				return null;
			}
			switch (sc.nextLine().trim().toUpperCase()) {
			case "P":
				return TipoEmpleado.PROFESIONAL;
			case "A":
				return TipoEmpleado.ADMINISTRATIVO;
			case "L":
				return TipoEmpleado.LIMPIEZA;
			default:
				System.out.println("Opción inválida, intente nuevamente.");
			}
		}
	}

	// ---------------------------------------------------------------- salida por
	// consola

	private static void mostrarFicha(Empleado empleado) {
		System.out.println(empleado);
		linea("Sueldo básico", Empleado.SUELDO_BASICO);
		linea(descripcionAdicional(empleado), empleado.calcularAdicional());
		linea("Antigüedad (" + empleado.calcularAntiguedad() + " años)", empleado.calcularMontoAntiguedad());
		linea("= Remunerativos bonificables", empleado.calcularRemunerativosBonificables());
		linea("+ Salario familiar (" + empleado.getCantidadHijos() + " hijos)", empleado.calcularSalarioFamiliar());
		linea("- Descuentos (18%)", empleado.calcularDescuentos());
		linea("= SUELDO NETO", empleado.calcularSueldoNeto());
	}

	private static String descripcionAdicional(Empleado empleado) {
		if (empleado instanceof Profesional profesional) {
			return "Adicional por títulos (" + profesional.getTitulos().size() + ")";
		}
		if (empleado instanceof Administrativo administrativo) {
			return "Adicional categoría " + administrativo.getCategoria().name();
		}
		if (empleado instanceof Limpieza) {
			return "Adicional por insalubridad";
		}
		return "Adicional";
	}

	private static void mostrarTabla(ManagerEmpleado manager, List<? extends Empleado> lista) {
		if (lista.isEmpty()) {
			System.out.println("  (no hay empleados)");
			return;
		}
		String formato = "  %-8s %-20s %16s %16s %16s %16s%n";
		System.out.printf(formato, "Legajo", "Nombre", "Remunerativos", "Sal. familiar", "Descuentos", "Neto");
		for (Empleado e : lista) {
			System.out.printf(formato, e.getLegajo(), e.getNombre(), pesos(e.calcularRemunerativosBonificables()),
					pesos(e.calcularSalarioFamiliar()), pesos(e.calcularDescuentos()), pesos(e.calcularSueldoNeto()));
		}
		System.out.printf(formato, "", "TOTAL", pesos(manager.calcularTotalRemunerativos(lista)),
				pesos(manager.calcularTotalSalarioFamiliar(lista)), pesos(manager.calcularTotalDescuentos(lista)),
				pesos(manager.calcularTotalNeto(lista)));
	}

	private static void linea(String concepto, double monto) {
		System.out.printf("    %-34s %16s%n", concepto, pesos(monto));
	}

	private static String pesos(double monto) {
		return String.format(AR, "$ %,.2f", monto);
	}

	private static void titulo(String texto) {
		System.out.println("\n========== " + texto + " ==========");
	}
}