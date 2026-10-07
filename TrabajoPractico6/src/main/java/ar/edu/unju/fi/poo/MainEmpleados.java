package ar.edu.unju.fi.poo;

import java.time.LocalDate;
import java.util.Scanner;

import ar.edu.unju.fi.poo.empleados.manager.ManagerEmpleado;
import ar.edu.unju.fi.poo.empleados.model.Administrativo;
import ar.edu.unju.fi.poo.empleados.model.Categoria;
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
		Administrativo emp8 = new Administrativo(8, 41836928, "Tobias Perez", LocalDate.of(2018, 10, 20), 2,Categoria.C);
		Profesional emp9 = new Profesional(9, 33829018, "Soledad Nieva", LocalDate.of(2024, 7, 27), 3);
		manager.agregarEmpleado(emp7);
		manager.agregarEmpleado(emp8);
		manager.agregarEmpleado(emp9);

		// b. Buscar un empleado por legajo y mostrar datos + sueldo neto
		manager.buscarYMostrarEmpleado(7);

		// c. Buscar administrativo por legajo, cambiar categoría y mostrar sueldo neto
		manager.cambiarCategoriaAdministrativo(5, Categoria.C);

		// d. Buscar profesional por legajo, agregar titulo y mostrar sueldo neto
		Titulo nuevoTitulo = new Titulo(2023, "APU", NivelTitulo.UNIVERSITARIO);
		manager.agregarTituloProfesional(3, nuevoTitulo);

		// e. Obtener empleados de categoria X y mostrar acumulados
		manager.mostrarAcumuladosPorCategoria(Categoria.C);

		// f. Calcular importe neto acumulado por tipo de empleado
		System.out.println("Importe Neto Acumulados de Empledos de cierto Tipo");
		Scanner sc = new Scanner(System.in);
		System.out.println("Seleccione tipo de empleado: A/P/L");
		System.out.println("A. Administrativo");
		System.out.println("P. Profesionales");
		System.out.println("L. Limpieza");

		System.out.print("Ingrese opcion: ");
		char opcion = sc.next().charAt(0);

		double importeNeto = manager.calcularImporteNetoPorTipo(opcion);
		System.out.println("Importe Neto Acumulado: " + importeNeto);
		sc.close();

	}

}
