package ar.edu.unju.fi.poo;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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

	public static void main(String[] args) {
		ManagerEmpleado manager = new ManagerEmpleado();
		manager.inicializarEmpleados();

		titulo("Empleados iniciales (" + manager.getEmpleados().size() + ")");
		for (Empleado empleado : manager.getEmpleados()) {
			System.out.println(empleado);
			System.out.println("    Sueldo neto: " + pesos(empleado.calcularSueldoNeto()));
		}

		// a. Agregar empleados de distintos tipos
		titulo("a) Agregar empleados de distintos tipos");
		agregar(manager, new Limpieza(7, 36827937, "Alejandra Gomez", LocalDate.of(2018, 8, 29), 0));
		agregar(manager, new Administrativo(8, 41836928, "Tobias Perez", LocalDate.of(2018, 10, 20), 2, Categoria.C));
		agregar(manager, new Profesional(9, 33829018, "Soledad Nieva", LocalDate.of(2024, 7, 27), 3,
				new Titulo(2019, "Abogacía", NivelTitulo.UNIVERSITARIO)));
		System.out.println("Regla de negocio: el legajo no se puede repetir");
		agregar(manager, new Limpieza(7, 25478963, "Carlos Ruiz", LocalDate.of(2020, 1, 10), 1));

		// b. Buscar un empleado por legajo y mostrar sus datos y sueldo neto
		titulo("b) Buscar empleado por legajo y mostrar datos + sueldo neto");
		mostrarEmpleadoPorLegajo(manager, 7);
		mostrarEmpleadoPorLegajo(manager, 99);

		// c. Buscar un administrativo, cambiar su categoría y mostrar el sueldo neto
		titulo("c) Buscar administrativo, cambiar categoría y mostrar sueldo neto");
		cambiarCategoria(manager, 4, Categoria.C);
		cambiarCategoria(manager, 1, Categoria.B); // legajo 1 es de limpieza

		// d. Buscar un profesional, agregarle un título y mostrar el sueldo neto
		titulo("d) Buscar profesional, agregar título y mostrar sueldo neto");
		agregarTitulo(manager, 5, new Titulo(2018, "Licenciatura en Administración", NivelTitulo.UNIVERSITARIO));
		agregarTitulo(manager, 3, new Titulo(2015, "Martillero Público", NivelTitulo.TERCIARIO)); // legajo 3 es administrativo

		try (Scanner sc = new Scanner(System.in)) {
			// e. Empleados de una categoría X con totales acumulados
			titulo("e) Empleados de una categoría y totales acumulados");
			Categoria categoria = pedirCategoria(sc);
			if (categoria != null) {
				List<Administrativo> deLaCategoria = manager.obtenerPorCategoria(categoria);
				System.out.println("Empleados de la categoría " + categoria + ":");
				mostrarTabla(manager, deLaCategoria);
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

	// ---------------------------------------------------------------- casos de prueba

	private static void agregar(ManagerEmpleado manager, Empleado empleado) {
		if (manager.agregarEmpleado(empleado)) {
			System.out.println("Agregado: " + empleado.getTipo().getDescripcion() + " legajo " + empleado.getLegajo()
					+ " - " + empleado.getNombre());
		} else {
			System.out.println("Rechazado: ya existe un empleado con legajo " + empleado.getLegajo() + " ("
					+ empleado.getNombre() + " no se agregó)");
		}
	}

	private static void mostrarEmpleadoPorLegajo(ManagerEmpleado manager, int legajo) {
		Empleado empleado = manager.buscarEmpleado(legajo);
		if (empleado == null) {
			System.out.println("No existe un empleado con legajo " + legajo);
			return;
		}
		mostrarFicha(empleado);
	}

	private static void cambiarCategoria(ManagerEmpleado manager, int legajo, Categoria nuevaCategoria) {
		Administrativo administrativo = manager.buscarAdministrativo(legajo);
		if (administrativo == null) {
			System.out.println("El legajo " + legajo + " no corresponde a un empleado administrativo");
			return;
		}
		System.out.println(administrativo.getNombre() + " (legajo " + legajo + ")");
		System.out.println("  Categoría actual " + administrativo.getCategoria() + " -> sueldo neto "
				+ pesos(administrativo.calcularSueldoNeto()));
		manager.cambiarCategoria(legajo, nuevaCategoria);
		System.out.println("  Categoría nueva  " + administrativo.getCategoria() + " -> sueldo neto "
				+ pesos(administrativo.calcularSueldoNeto()));
	}

	private static void agregarTitulo(ManagerEmpleado manager, int legajo, Titulo titulo) {
		Profesional profesional = manager.buscarProfesional(legajo);
		if (profesional == null) {
			System.out.println("El legajo " + legajo + " no corresponde a un empleado profesional");
			return;
		}
		System.out.println(profesional.getNombre() + " (legajo " + legajo + ")");
		System.out.println("  Con " + profesional.getTitulos().size() + " título(s) -> sueldo neto "
				+ pesos(profesional.calcularSueldoNeto()));
		manager.agregarTitulo(legajo, titulo);
		System.out.println("  Se agregó: " + titulo);
		System.out.println("  Con " + profesional.getTitulos().size() + " título(s) -> sueldo neto "
				+ pesos(profesional.calcularSueldoNeto()));
	}

	// ---------------------------------------------------------------- entrada del usuario

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

	// ---------------------------------------------------------------- salida por consola

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
		String formato = "  %-6s %-20s %16s %16s %16s %16s%n";
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