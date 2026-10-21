package ar.edu.unju.fi.poo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.unju.fi.poo.empleados.exception.EmpleadoException;
import ar.edu.unju.fi.poo.empleados.manager.ManagerEmpleado;
import ar.edu.unju.fi.poo.empleados.model.Empleado;
import ar.edu.unju.fi.poo.empleados.model.Limpieza;

@DisplayName("Pruebas unitarias para la clase Empleado")
public class EmpleadoTest {
    private ManagerEmpleado manager;
    private Empleado empleado1;
    private Empleado empleado2;

    @BeforeEach
    void setUp() throws EmpleadoException {
        manager = new ManagerEmpleado();
        empleado1 = new Limpieza(manager.generarLegajo(), 12345678, "Jose fernandez", LocalDate.of(2026, 5, 10), 0);
        empleado2 = new Limpieza(manager.generarLegajo(), 22929848, "Maria lopez", LocalDate.of(2026, 5, 10), 2);
    }

    // El documento no puede repetirse
    @Test
    @DisplayName("Agregar Empleado con Documento Repetido")
    void testAgregarEmpleado() throws EmpleadoException {
        manager.agregarEmpleado(empleado1);
        Empleado empleadoNuevo = new Limpieza(manager.generarLegajo(), 12345678, "Lara Perez",
                LocalDate.of(2025, 8, 11), 0);
        assertThrows(EmpleadoException.class, () -> manager.agregarEmpleado(empleadoNuevo));
    }

    // El legajo debe tener el formato: NNNN-AA donde:
    // NNNN: Número aleatorio de 4 dígitos
    // AA: Últimos 2 dígitos del año actual
    @Test
    @DisplayName("Generar Legajo")
    void testGenerarLegajo() throws EmpleadoException {
        String legajo = manager.generarLegajo();
        assertTrue(legajo.matches("\\d{4}-\\d{2}"));
        String anioActual = String.valueOf(java.time.Year.now().getValue()).substring(2);
        assertTrue(legajo.endsWith(anioActual));
        int numero = Integer.parseInt(legajo.substring(0, 4));
        assertTrue(numero >= 1000 && numero <= 9999);
    }

    // agregar empleado correctamente
    @Test
    @DisplayName("Agregar Empleado Correctamente")
    void testAgregarEmpleadoCorrectamente() throws EmpleadoException {
        manager.agregarEmpleado(empleado1);
        assertEquals(1, manager.getEmpleados().size());
        manager.agregarEmpleado(empleado2);
        assertEquals(2, manager.getEmpleados().size());
    }

    @Test
    @DisplayName("Buscar Empleado por Legajo")
    void testBuscarEmpleadoPorLegajo() throws EmpleadoException {
        manager.agregarEmpleado(empleado1);
        manager.agregarEmpleado(empleado2);
        assertEquals(empleado1, manager.buscarEmpleado(empleado1.getLegajo()));
        assertEquals(empleado2, manager.buscarEmpleado(empleado2.getLegajo()));
    }
}
