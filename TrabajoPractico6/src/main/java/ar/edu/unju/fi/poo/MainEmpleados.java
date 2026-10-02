package ar.edu.unju.fi.poo;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.fi.poo.empleados.manager.ManagerEmpleado;
import ar.edu.unju.fi.poo.empleados.model.Administrativo;
import ar.edu.unju.fi.poo.empleados.model.Categoria;
import ar.edu.unju.fi.poo.empleados.model.Empleado;
import ar.edu.unju.fi.poo.empleados.model.Limpieza;
import ar.edu.unju.fi.poo.empleados.model.NivelTitulo;
import ar.edu.unju.fi.poo.empleados.model.Profesional;
import ar.edu.unju.fi.poo.empleados.model.Titulo;

public class MainEmpleados {

	public static void main(String[] args) {
		ManagerEmpleado manager = new ManagerEmpleado();

		manager.inicializarEmpleados();

		// a. Agregar empleados de distintos tipos
		Limpieza emp7 = new Limpieza(7, 36827937, "Alejandra Gomez", LocalDate.of(2018, 8, 29), 0);
		Administrativo emp8 = new Administrativo(8, 41836928, "Tobias Perez", LocalDate.of(2018, 10, 20), 2,
				Categoria.C);
		Profesional emp9 = new Profesional(9, 33829018, "Soledad Nieva", LocalDate.of(2024, 7, 27), 3);
		manager.agregarEmpleado(emp7);
		manager.agregarEmpleado(emp8);
		manager.agregarEmpleado(emp9);

		// b. Buscar un empleado por legajo y mostrar los datos personales más
		// relevantes incluyendo el sueldo neto que le corresponde
		System.out.println("Buscar Empleado por Legajo");
		int legajoBuscado = 7;
		Empleado empleadoBuscado = manager.buscarEmpleado(legajoBuscado);
		empleadoBuscado.mostrarDatos();

		// c. Buscar un empleado administrativo por legajo, cambiar su categoría y
		// mostrar el sueldo neto que le corresponde
		System.out.println("\nBuscar Administrativo por legajo, cambiar categoria y mostrar sueldo neto");
		legajoBuscado = 5;
		Administrativo admBuscado = (Administrativo) manager.buscarEmpleado(legajoBuscado);
		System.out.println("Sueldo con categoría: " + admBuscado.getCategoria() + admBuscado.calcularSueldoNeto());
		admBuscado.cambiarCategoria(Categoria.C);

		System.out.println("Sueldo con categoría: " + admBuscado.getCategoria() + admBuscado.calcularSueldoNeto());

		// d. Buscar un empleado profesional por legajo, agregarle un nuevo título y
		// mostrar el sueldo neto que le corresponde
		System.out.println("\nBuscar Profesional, agregar nuevo titulo y mostrar sueldo neto");
		legajoBuscado = 3;
		Profesional profBuscado = (Profesional) manager.buscarEmpleado(legajoBuscado);
		System.out.println("Sueldo sin titulo: " + profBuscado.calcularSueldoNeto());
		Titulo nuevoTitulo = new Titulo(2023, "APU", NivelTitulo.UNIVERSITARIO);

		profBuscado.agregarTitulo(nuevoTitulo);
		System.out.println("Sueldo con titulo: " + profBuscado.calcularSueldoNeto());

		// e. Obtener y mostrar los empleados de un categoría X, al final de todo
		// mostrar el total acumulado de los remunerativos bonificables, salario,
		// descuentos e importe neto.
		System.out.println("\nObtener Empleado de Categoria X y mostrar acumulados.");
		double acumRemunerativo = 0d;
		double acumSalario = 0d;
		double acumDescuento = 0d;
		double acumImporteNeto = 0d;
		Categoria catBuscada = Categoria.C;

		List<Administrativo> administrativos = manager.obtenerAdministrativos();
		for (Administrativo administrativo : administrativos) {
			System.out.println("Categoria: " + administrativo.getCategoria() + " - Suledo Neto: "
					+ administrativo.calcularSueldoNeto());
			if (administrativo.getCategoria().equals(catBuscada)) {
				acumRemunerativo += administrativo.remunerativosBonificables();
				acumSalario += administrativo.calcularSalarioFamiliar();
				acumDescuento += administrativo.calcularDescuentos();
				acumImporteNeto += administrativo.calcularSueldoNeto();
			}
		}
		System.out.println("\nTotal Acumulado Remunerativos Bonificables: " + acumRemunerativo);
		System.out.println("Total Acumulado Salarios: " + acumSalario);
		System.out.println("Total Acumalado Descuentos: " + acumDescuento);
		System.out.println("Total Acumulado Importe Neto: " + acumImporteNeto);

		// f. Calcular el importe neto acumulado de todos los empleados cuyo tipo sea
		// igual a uno solicitado al usuario.
		System.out.println("Importe Neto Acumulados de Empledos de cierto Tipo");
		acumImporteNeto = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Seleccione tipo de empleado: A/P/L");
		System.out.println("A. Administrativo");
		System.out.println("P. Prfoesionales");
		System.out.println("L. Limpieza");

		System.out.print("Ingrese opcion: ");
		char opcion = sc.next().charAt(0);

		for (Empleado empleado : manager.obtenerEmpleados()) {
			switch (opcion) {
			case 'a','A':
				if (empleado instanceof Administrativo) {
					 System.out.println(empleado.calcularSueldoNeto()); 
					acumImporteNeto += empleado.calcularSueldoNeto();
				}
				break;
			case 'p','P':
				if (empleado instanceof Profesional) {
					 System.out.println(empleado.calcularSueldoNeto()); 
					acumImporteNeto += empleado.calcularSueldoNeto();
				}
				break;
			case 'l','L':
				if (empleado instanceof Limpieza) {
					 System.out.println(empleado.calcularSueldoNeto()); 
					acumImporteNeto += empleado.calcularSueldoNeto();
				}
				break;
			default:
				System.out.println("Opción inválida.");
			}
		}
		System.out.println("Importe Neto Acumulado: " + acumImporteNeto);
		sc.close();

	}

}
