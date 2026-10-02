package ar.edu.unju.fi.poo.empleados.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unju.fi.poo.empleados.model.Administrativo;
import ar.edu.unju.fi.poo.empleados.model.Categoria;
import ar.edu.unju.fi.poo.empleados.model.Empleado;
import ar.edu.unju.fi.poo.empleados.model.Limpieza;
import ar.edu.unju.fi.poo.empleados.model.Profesional;


public class ManagerEmpleado {
	private List<Empleado> empleados = new ArrayList<>(); 
	
	public void agregarEmpleado (Empleado emp) {
		empleados.add(emp);
	}
	
	public void inicializarEmpleados () {
		Limpieza emp1 = new Limpieza (1, 49312843, "Roberto Cardozo", LocalDate.of(2015, 5, 16),3);
		Administrativo emp2 = new Administrativo (2, 34828945, "Luciana Narvaez", LocalDate.of(2023, 9, 27), 2, Categoria.A);
		Profesional emp3 = new Profesional (3, 389238763, "Juan Vargas", LocalDate.of(2019, 11, 23), 4);
		
		Limpieza emp4 = new Limpieza (4, 49312843, "Josefina Cruz", LocalDate.of(2016, 7, 8),1);
		Administrativo emp5 = new Administrativo (5, 34828945, "Yanina Lopez", LocalDate.of(2021, 3, 2), 0, Categoria.B);
		Profesional emp6 = new Profesional (6, 389238763, "Nicolas Gonzalez", LocalDate.of(2024, 4, 29), 0);
		
		agregarEmpleado(emp1);
		agregarEmpleado(emp2);
		agregarEmpleado(emp3);
		agregarEmpleado(emp4);
		agregarEmpleado(emp5);
		agregarEmpleado(emp6);
	}
	
	public Empleado buscarEmpleado(int legajo) {	
		for (Empleado empleado : empleados) {
			if(empleado.getLegajo()== legajo) {
				return empleado;
			}
		}
		return null;
	}
	
	public List<Administrativo> obtenerAdministrativos() {
		List<Administrativo> administrativos = new ArrayList<>();
		for (Empleado empleado : empleados) {
			if(empleado instanceof Administrativo) {
				administrativos.add((Administrativo) empleado);
			}
		}
		return administrativos;
	}
	
	public List<Profesional> obtenerProfesionales() {
		List<Profesional> profesionales = new ArrayList<>();
		for (Empleado empleado : empleados) {
			if(empleado instanceof Profesional) {
				profesionales.add((Profesional) empleado);
			}
		}
		return profesionales;
	}
	
	public List<Empleado> obtenerEmpleados (){
		return empleados;
	}

}
