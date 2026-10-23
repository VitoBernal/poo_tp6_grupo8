package ar.edu.unju.fi.poo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

@DisplayName("Pruebas unitarias para la clase Empleado")
public class EmpleadoTest {
	private ManagerEmpleado manager;
	private Empleado empleado1;
	private Empleado empleado2;

	@BeforeEach
	void setUp() throws EmpleadoException {
		manager = new ManagerEmpleado();
		manager.inicializarEmpleados();
		empleado1 = new Limpieza(manager.generarLegajo(), 12345678, "Jose fernandez", LocalDate.of(2026, 5, 10), 0);
		empleado2 = new Limpieza(manager.generarLegajo(), 22929848, "Maria lopez", LocalDate.of(2026, 5, 10), 2);
	}

	/*
	 * Verifica que el método inicializarEmpleados() cargue 6 empleados y que se
	 * carguen dos empleados de cada tipo
	 */
	@Test
	@DisplayName("Cargar 6 empleados, 2 de cada tipo")
	void inicializarEmpleadosTest() {
		assertEquals(6, manager.getEmpleados().size());
		for (TipoEmpleado tipo : TipoEmpleado.values()) {
			assertEquals(2, manager.obtenerPorTipo(tipo).size());
		}
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
		assertEquals(7, manager.getEmpleados().size());
		manager.agregarEmpleado(empleado2);
		assertEquals(8, manager.getEmpleados().size());
	}

	/*
	 * Verifica que el método existeLejago() devuelva VERDADERO cuando se pasa por
	 * parámaetro un legajo que ya fue cargado
	 */
	@Test
	@DisplayName("Existe legajo X?")
	void existeLegajoTest() {
		String legajoExistente = manager.getEmpleados().get(0).getLegajo();
		assertTrue(manager.existeLegajo(legajoExistente));
	}

	@Test
	@DisplayName("Buscar Empleado por Legajo")
	void testBuscarEmpleadoPorLegajo() throws EmpleadoException {
		manager.agregarEmpleado(empleado1);
		manager.agregarEmpleado(empleado2);
		assertEquals(empleado1, manager.buscarEmpleado(empleado1.getLegajo()));
		assertEquals(empleado2, manager.buscarEmpleado(empleado2.getLegajo()));
	}

	/*
	 * Verifica que el método buscarAdministrativo devuelva un empleado de tipo
	 * Administrativo al pasarle como parámetro un legajo, comparando las
	 * categorías.
	 * 
	 * Si se pasa como parámetro un legajo que no sea de un empleado de tipo
	 * Administrativo devuelve Null
	 */
	@Test
	@DisplayName("Buscar empleado de tipo Administrativo")
	void buscarAdministrativoTest() {
		String lejajoAdministrativo = manager.getEmpleados().get(2).getLegajo();
		assertEquals(Categoria.A, manager.buscarAdministrativo(lejajoAdministrativo).getCategoria());

		String legajoLimpieza = manager.getEmpleados().get(0).getLegajo();
		assertNull(manager.buscarAdministrativo(legajoLimpieza));
	}

	/*
	 * Verifica que el método buscarProfesional devuelva un empleado de tipo
	 * Profesional al pasarle como parámetro un legajo, comparando la cantidad de
	 * títulos.
	 * 
	 * Si se pasa como parámetro un legajo que no sea de un empleado de tipo
	 * Profesional devuelve Null.
	 */
	@Test
	@DisplayName("Buscar empleado de tipo Profesional")
	void buscarProfesional() {
		String legajoProfesional = manager.getEmpleados().get(4).getLegajo();
		assertEquals(1, manager.buscarProfesional(legajoProfesional).getTitulos().size());

		String legajoLimpieza = manager.getEmpleados().get(0).getLegajo();
		assertNull(manager.buscarProfesional(legajoLimpieza));
	}

	/*
	 * Verifica que el método obtenerPorTipo devuelva los empleados de un tipo
	 * determinado.
	 */
	@Test
	@DisplayName("Obtener empleados por tipo")
	void obtenerPorTipoTest() throws EmpleadoException {
		assertEquals(TipoEmpleado.LIMPIEZA, manager.obtenerPorTipo(TipoEmpleado.LIMPIEZA).get(0).getTipo());
		assertEquals(TipoEmpleado.ADMINISTRATIVO, manager.obtenerPorTipo(TipoEmpleado.ADMINISTRATIVO).get(0).getTipo());
		assertEquals(TipoEmpleado.PROFESIONAL, manager.obtenerPorTipo(TipoEmpleado.PROFESIONAL).get(0).getTipo());
	}

	/*
	 * Verifica que el método obtenerPorCategoria devuelva los empleados de un tipo
	 * Administrativo de una categoria determinada.
	 */
	@Test
	@DisplayName("Obtener empleado de tipo Administrativo por su categoría")
	void obtenerPorCategoriaTest() {
		assertEquals(Categoria.A, manager.obtenerPorCategoria(Categoria.A).get(0).getCategoria());

		assertEquals(Categoria.B, manager.obtenerPorCategoria(Categoria.B).get(0).getCategoria());

		assertTrue(manager.obtenerPorCategoria(Categoria.C).isEmpty());
	}

	/*
	 * Verifica que el método cambiarCategoria, cambie la categoria de un empleado.
	 * Se pasa por parametro el legajo del empleado y la nueva categoria. También
	 * verfica que se actualice el sueldo de acuerdo a la nueva categoria.
	 */
	@Test
	@DisplayName("Cambiar categoría de administrativo y actualizar sueldo")
	void cambiarCategoriaTest() throws EmpleadoException {
		List<Administrativo> empleados = manager.obtenerPorCategoria(Categoria.A);
		Double sueldoAntes = empleados.get(0).calcularSueldoNeto();

		assertTrue(Categoria.A == empleados.get(0).getCategoria());

		manager.cambiarCategoria(empleados.get(0).getLegajo(), Categoria.B);

		assertFalse(Categoria.A == empleados.get(0).getCategoria());
		assertFalse(sueldoAntes == empleados.get(0).calcularSueldoNeto());

	}

	/*
	 * Verifica que el método agregue un nuevo titulo a un profesional y que se
	 * actualice su sueldo.
	 */
	@Test
	@DisplayName("Agregar un título a un profesional y actualizar sueldo")
	void agregarTituloTest() throws EmpleadoException {
		Profesional profesional = manager.buscarProfesional(manager.getEmpleados().get(4).getLegajo());
		Double sueldoAntes = profesional.calcularSueldoNeto();
		Titulo nuevo = new Titulo(2024, "Licenciatura en Administración", NivelTitulo.UNIVERSITARIO);
		// Cantidad de titulos antes de agregar
		assertEquals(1, profesional.getTitulos().size());

		manager.agregarTitulo(profesional.getLegajo(), nuevo);

		// Cantidad de titulos y sueldo, despues de agregar
		assertFalse(1 == profesional.getTitulos().size());
		assertFalse(sueldoAntes == profesional.calcularSueldoNeto());

	}

	/*
	 * Verifica que se sumen los remunerativos de un grupo de empleados.
	 */
	@Test
	@DisplayName("Calcular total de remunerativos bonificables de un grupo de empleados")
	void calcularTotalRemunerativoTest() {
		List<Administrativo> administrativos = manager.obtenerPorCategoria(Categoria.A);
		List<Empleado> profesionales = manager.obtenerPorTipo(TipoEmpleado.PROFESIONAL);

		assertTrue(manager.calcularTotalRemunerativos(administrativos) > 0);
		assertTrue(manager.calcularTotalRemunerativos(profesionales) > 0);
	}

	/*
	 * Verifica que se sumen los salarios familiares de un grupo de empleados.
	 */
	@Test
	@DisplayName("Calcular total de salario familiar de un grupo de empleados")
	void calcularTotalSalarioFamiliarTest() {
		List<Administrativo> administrativos = manager.obtenerPorCategoria(Categoria.A);
		List<Empleado> limpieza = manager.obtenerPorTipo(TipoEmpleado.LIMPIEZA);

		assertTrue(manager.calcularTotalSalarioFamiliar(administrativos) > 0);
		assertTrue(manager.calcularTotalSalarioFamiliar(limpieza) > 0);
	}

	/*
	 * Verifica que se sumen los descuentos de un grupo de empleados.
	 */
	@Test
	@DisplayName("Calcular total de descuentos de un grupo de empleados")
	void calcularTotalDescuentosTest() {
		List<Administrativo> administrativos = manager.obtenerPorCategoria(Categoria.A);
		List<Empleado> profesionales = manager.obtenerPorTipo(TipoEmpleado.PROFESIONAL);

		assertTrue(manager.calcularTotalDescuentos(administrativos) > 0);
		assertTrue(manager.calcularTotalDescuentos(profesionales) > 0);
	}

	/*
	 * Verifica que se sumen los totales neto de un grupo de empleados.
	 */
	@Test
	@DisplayName("Calcular total de importe neto de un grupo de empleados")
	void calcularTotalNetoTest() {
		List<Administrativo> administrativos = manager.obtenerPorCategoria(Categoria.A);
		List<Empleado> limpieza = manager.obtenerPorTipo(TipoEmpleado.PROFESIONAL);

		assertTrue(manager.calcularTotalNeto(administrativos) > 0);
		assertTrue(manager.calcularTotalNeto(limpieza) > 0);
	}

	/*
	 * Verifica que se sumen los totales neto por tipo de empleado.
	 */
	@Test
	@DisplayName("Calcular total de importe neto por tipo de empleados")
	void calcularTotalNetoPorTipoTest() {

		assertTrue(manager.calcularImporteNetoPorTipo(TipoEmpleado.PROFESIONAL) > 0);
		assertTrue(manager.calcularImporteNetoPorTipo(TipoEmpleado.ADMINISTRATIVO) > 0);
		assertTrue(manager.calcularImporteNetoPorTipo(TipoEmpleado.LIMPIEZA) > 0);
	}

	@Test
	@DisplayName("Obtener el total de empleados")
	void getEmpleadosTest() {
		assertEquals(6, manager.getEmpleados().size());
	}

	// ================================================================
	// Validaciones del TP8 (punto 2) y operaciones que faltaban probar
	// ================================================================

	private static final String ANIO = Empleado.sufijoAnioActual();
	private static final LocalDate HOY = LocalDate.now();

	@RepeatedTest(10)
	@DisplayName("b. Generar legajo varias veces: siempre válido y sin repetir")
	void generarLegajoRepetidoTest() {
		String legajo = manager.generarLegajo();
		assertTrue(Empleado.esLegajoValido(legajo));
		assertFalse(manager.existeLegajo(legajo));
	}

	@DisplayName("b. Legajo con formato inválido lanza excepción")
	@ParameterizedTest(name = "legajo = \"{0}\"")
	@ValueSource(strings = { "", "123-26", "12345-26", "ABCD-26", "1234/26", "1234-2" })
	void legajoInvalidoTest(String legajo) {
		assertThrows(EmpleadoException.class, () -> new Limpieza(legajo, 30000001, "Legajo Invalido", HOY, 0));
	}

	@Disabled("Pendiente de confirmar con el profesor: el ejemplo del enunciado (23123-26) tiene 5 dígitos y el formato NNNN-AA indica 4")
	@Test
	@DisplayName("b. Legajo de 5 dígitos como el ejemplo del enunciado")
	void legajoCincoDigitosTest() {
		assertDoesNotThrow(() -> new Limpieza("23123-" + ANIO, 30000001, "Legajo Cinco Digitos", HOY, 0));
	}

	@Test
	@DisplayName("Agregar empleado con legajo repetido lanza excepción")
	void agregarEmpleadoLegajoRepetidoTest() throws EmpleadoException {
		String legajoExistente = manager.getEmpleados().get(0).getLegajo();
		Empleado repetido = new Limpieza(legajoExistente, 30000001, "Legajo Repetido", HOY, 0);
		assertThrows(EmpleadoException.class, () -> manager.agregarEmpleado(repetido));
		assertEquals(6, manager.getEmpleados().size());
	}

	@Test
	@DisplayName("c. Fecha de ingreso posterior a hoy lanza excepción; la de hoy es válida")
	void fechaIngresoTest() throws EmpleadoException {
		assertThrows(EmpleadoException.class,
				() -> new Limpieza(manager.generarLegajo(), 30000001, "Fecha Futura", HOY.plusDays(1), 0));
		Empleado ingresaHoy = new Limpieza(manager.generarLegajo(), 30000002, "Ingresa Hoy", HOY, 0);
		assertEquals(HOY, ingresaHoy.getFechaIngreso());
	}

	@DisplayName("d. Cantidad de hijos fuera de 0 a 5 lanza excepción")
	@ParameterizedTest(name = "hijos = {0}")
	@ValueSource(ints = { -1, 6, 10 })
	void cantidadHijosInvalidaTest(int hijos) {
		assertThrows(EmpleadoException.class,
				() -> new Limpieza(manager.generarLegajo(), 30000001, "Hijos Invalidos", HOY, hijos));
	}

	@DisplayName("d. Cantidad de hijos entre 0 y 5 es válida")
	@ParameterizedTest(name = "hijos = {0}")
	@ValueSource(ints = { 0, 3, 5 })
	void cantidadHijosValidaTest(int hijos) throws EmpleadoException {
		Empleado empleado = new Limpieza(manager.generarLegajo(), 30000001, "Hijos Validos", HOY, hijos);
		assertEquals(hijos, empleado.getCantidadHijos());
	}

	@Test
	@DisplayName("e. Registrar un profesional sin título lanza excepción")
	void profesionalSinTituloTest() {
		assertThrows(EmpleadoException.class,
				() -> new Profesional(manager.generarLegajo(), 30000001, "Sin Titulo", HOY, 0, null));
		assertThrows(EmpleadoException.class, () -> new Titulo(2020, "Carrera sin nivel", null));
	}

	@Test
	@DisplayName("Existe documento")
	void existeDocumentoTest() {
		assertTrue(manager.existeDocumento(30125478));
		assertFalse(manager.existeDocumento(11111111));
	}

	@Test
	@DisplayName("Buscar empleado por documento: existente e inexistente")
	void buscarPorDocumentoTest() {
		assertEquals("Roberto Cardozo", manager.buscarPorDocumento(30125478).getNombre());
		assertNull(manager.buscarPorDocumento(11111111));
	}

	@Test
	@DisplayName("Buscar un legajo inexistente devuelve null")
	void buscarEmpleadoInexistenteTest() {
		assertNull(manager.buscarEmpleado("0000-" + ANIO));
		assertFalse(manager.existeLegajo("0000-" + ANIO));
	}
}