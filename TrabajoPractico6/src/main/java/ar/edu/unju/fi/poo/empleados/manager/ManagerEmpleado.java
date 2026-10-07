package ar.edu.unju.fi.poo.empleados.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.poo.empleados.model.Administrativo;
import ar.edu.unju.fi.poo.empleados.model.Categoria;
import ar.edu.unju.fi.poo.empleados.model.Empleado;
import ar.edu.unju.fi.poo.empleados.model.Limpieza;
import ar.edu.unju.fi.poo.empleados.model.Profesional;
import ar.edu.unju.fi.poo.empleados.model.Titulo;

public class ManagerEmpleado {
	private List<Empleado> empleados = new ArrayList<>();

	public void agregarEmpleado(Empleado emp) {
		empleados.add(emp);
	}

	public void inicializarEmpleados() {
		Limpieza emp1 = new Limpieza(1, 49312843, "Roberto Cardozo", LocalDate.of(2015, 5, 16), 3);
		Administrativo emp2 = new Administrativo(2, 34828945, "Luciana Narvaez", LocalDate.of(2023, 9, 27), 2,Categoria.A);
		Profesional emp3 = new Profesional(3, 389238763, "Juan Vargas", LocalDate.of(2019, 11, 23), 4);

		Limpieza emp4 = new Limpieza(4, 49312843, "Josefina Cruz", LocalDate.of(2016, 7, 8), 1);
		Administrativo emp5 = new Administrativo(5, 34828945, "Yanina Lopez", LocalDate.of(2021, 3, 2), 0, Categoria.B);
		Profesional emp6 = new Profesional(6, 389238763, "Nicolas Gonzalez", LocalDate.of(2024, 4, 29), 0);

		agregarEmpleado(emp1);
		agregarEmpleado(emp2);
		agregarEmpleado(emp3);
		agregarEmpleado(emp4);
		agregarEmpleado(emp5);
		agregarEmpleado(emp6);
	}

	public Empleado buscarEmpleado(int legajo) {
		for (Empleado empleado : empleados) {
			if (empleado.getLegajo() == legajo) {
				return empleado;
			}
		}
		return null;
	}

	public List<Administrativo> obtenerAdministrativos() {
		List<Administrativo> administrativos = new ArrayList<>();
		for (Empleado empleado : empleados) {
			if (empleado instanceof Administrativo) {
				administrativos.add((Administrativo) empleado);
			}
		}
		return administrativos;
	}

	public List<Profesional> obtenerProfesionales() {
		List<Profesional> profesionales = new ArrayList<>();
		for (Empleado empleado : empleados) {
			if (empleado instanceof Profesional) {
				profesionales.add((Profesional) empleado);
			}
		}
		return profesionales;
	}

	// b. Buscar un empleado por legajo y mostrar datos + sueldo neto
	public void buscarYMostrarEmpleado(int legajo) {
		System.out.println("Buscar empleado por legajo");
		Empleado empleado = buscarEmpleado(legajo);
		if (empleado != null) {
			empleado.mostrarDatos();
		} else {
			System.out.println("No se encontro empleado con legajo: " + legajo);
		}
	}

	// c. Buscar administrativo por legajo, cambiar categoria y mostrar sueldo neto
	public void cambiarCategoriaAdministrativo(int legajo, Categoria nuevaCategoria) {
		System.out.println("\nBuscar administrativo cambiar categoria y mostrar sueldo neto");
		Empleado empleado = buscarEmpleado(legajo);
		if (empleado instanceof Administrativo) {
			Administrativo adm = (Administrativo) empleado;
			System.out.println("Sueldo con categoria: " + adm.getCategoria() + " " + adm.calcularSueldoNeto());
			adm.cambiarCategoria(nuevaCategoria);
			System.out.println("Sueldo con categoria: " + adm.getCategoria() + " " + adm.calcularSueldoNeto());
		} else {
			System.out.println("No se encontro empleado administrativo con legajo: " + legajo);
		}
	}

	// d. Buscar profesional por legajo, agregar nuevp titulo y mostrar el sueldo neto
	public void agregarTituloProfesional(int legajo, Titulo nuevoTitulo) {
		System.out.println("\nBuscar profesional agregar titulo y mostrar sueldo neto");
		Empleado empleado = buscarEmpleado(legajo);
		if (empleado instanceof Profesional) {
			Profesional prof = (Profesional) empleado;
			prof.agregarTitulo(nuevoTitulo);
			System.out.println("Sueldo con nuevo titulo: " + prof.calcularSueldoNeto());
		} else {
			System.out.println("No se encontro empleado profesional con legajo: " + legajo);
		}
	}

	public List<Administrativo> obtenerPorCategoria(Categoria categoria) {
		List<Administrativo> lista = new ArrayList<>();
		for (Empleado empleado : empleados) {
			if (empleado instanceof Administrativo && ((Administrativo) empleado).getCategoria() == categoria) {
				lista.add((Administrativo) empleado);
			}
		}
		return lista;
	}

	// e. Obtener empleados de categoria X y mostrar acumulados
	public void mostrarAcumuladosPorCategoria(Categoria categoria) {
		System.out.println("\nObtener empleado de categoria " + categoria + " y mostrar acumulados.");
		double acumRemunerativo = 0d;
		double acumSalario = 0d;
		double acumDescuento = 0d;
		double acumImporteNeto = 0d;

		List<Administrativo> administrativos = obtenerPorCategoria(categoria);
		for (Administrativo administrativo : administrativos) {
			System.out.println("Categoria: " + administrativo.getCategoria() + " - Sueldo Neto: "+ administrativo.calcularSueldoNeto());
			acumRemunerativo += administrativo.remunerativosBonificables();
			acumSalario += administrativo.calcularSalarioFamiliar();
			acumDescuento += administrativo.calcularDescuentos();
			acumImporteNeto += administrativo.calcularSueldoNeto();
		}
		System.out.println("\nTotal acumulado remunerativos bonificables: " + acumRemunerativo);
		System.out.println("Total acumulado salario familiar: " + acumSalario);
		System.out.println("Total acumalado descuentos: " + acumDescuento);
		System.out.println("Total acumulado importe Neto: " + acumImporteNeto);
	}

	// f. Calcular importe neto acumulado por tipo de empleado
	public double calcularImporteNetoPorTipo(char tipo) {
		double acumImporteNeto = 0;
		for (Empleado empleado : empleados) {
			switch (tipo) {
				case 'a', 'A':
					if (empleado instanceof Administrativo) {
						System.out.println(empleado.getNombre() + " sueldo Neto: " + empleado.calcularSueldoNeto());
						acumImporteNeto += empleado.calcularSueldoNeto();
					}
					break;
				case 'p', 'P':
					if (empleado instanceof Profesional) {
						System.out.println(empleado.getNombre() + " sueldo Neto: "+ empleado.calcularSueldoNeto());
						acumImporteNeto += empleado.calcularSueldoNeto();
					}
					break;
				case 'l', 'L':
					if (empleado instanceof Limpieza) {
						System.out.println(empleado.getNombre() + " sueldo Neto: "+ empleado.calcularSueldoNeto());
						acumImporteNeto += empleado.calcularSueldoNeto();
					}
					break;
				default:
					System.out.println("opcion no valida");
					return 0;
			}
		}
		return acumImporteNeto;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}
}
