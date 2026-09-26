package ar.edu.unju.fi.poo;

import ar.edu.unju.fi.poo.estacionamiento.manager.Manager;
import ar.edu.unju.fi.poo.estacionamiento.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainEstacionamiento {

	private static void sep(String t) {
		System.out.println("\n========== " + t + " ==========");
	}

	public static void main(String[] args) {
		Manager manager = new Manager();

		// a) Ingreso por hora SIN cupón
		sep("a) Ingreso por hora sin cupón");
		PorHora sinCupon = new PorHora(new Vehiculo("AAA111", "Fiat", "Rojo"), LocalDate.now(), LocalTime.of(9, 0));
		manager.registrarIngreso(sinCupon);
		System.out.println("Ingreso OK -> " + sinCupon);

		// b) Ingreso por hora CON cupón (vencido y no vencido)
		sep("b) Ingreso por hora con cupón");
		Cupon valido = new Cupon("CUP10", LocalDate.now().plusDays(30), 10);
		Cupon vencido = new Cupon("CUP20", LocalDate.now().minusDays(1), 20);

		PorHora conCuponValido = new PorHora(new Vehiculo("BBB222", "Ford", "Azul"), LocalDate.now(),
				LocalTime.of(10, 0), valido, 1000);
		manager.registrarIngreso(conCuponValido);

		PorHora conCuponVencido = new PorHora(new Vehiculo("CCC333", "Chevrolet", "Blanco"), LocalDate.now(),
				LocalTime.of(11, 0), vencido, 1000);
		manager.registrarIngreso(conCuponVencido);

		sinCupon.setHora(LocalTime.now().minusHours(3));
		conCuponValido.setHora(LocalTime.now().minusHours(3));
		conCuponVencido.setHora(LocalTime.now().minusHours(3));

		System.out.printf("Importe sin cupón (3 hs): %.2f%n", manager.registrarSalida(sinCupon));
		System.out.printf("Importe con cupón válido 10%% (3 hs): %.2f%n", manager.registrarSalida(conCuponValido));
		System.out.printf("Importe con cupón vencido (3 hs): %.2f%n", manager.registrarSalida(conCuponVencido));

		// c) Ingreso fuera de horario
		sep("c) Ingreso fuera de horario");
		try {
			PorHora fueraHorario = new PorHora(new Vehiculo("DDD444", "Renault", "Gris"), LocalDate.now(),
					LocalTime.of(22, 30));
			manager.registrarIngreso(fueraHorario);
		} catch (Exception e) {
			System.out.println("Error esperado: " + e.getMessage());
		}

		// d) Ingreso de cliente mensual
		sep("d) Ingreso de cliente mensual");
		Cliente cliente = new Cliente(1, "12345678", "Av. Siempre Viva 123", "3884000000");
		Mensual mensual = new Mensual(new Vehiculo("EEE555", "Toyota", "Negro"), LocalDate.now(), LocalTime.of(14, 0),
				cliente);
		manager.registrarIngreso(mensual);
		System.out.println("Ingreso mensual OK -> " + mensual);
		System.out.printf("Importe mensual: %.2f%n", mensual.obtenerImporte());

		// e) Buscar por ID y mostrar importe actual
		sep("e) Buscar registro por ID y mostrar importe");
		RegistroIngresoSalida r = manager.obtenerRegistro(mensual.getId());
		System.out.println("Registro encontrado: " + r);

		// f) Buscar por ID y registrar salida por hora
		sep("f) Salida por hora");
		PorHora porHoraSalida = new PorHora(new Vehiculo("FFF666", "Honda", "Verde"), LocalDate.now(),
				LocalTime.of(15, 0));
		manager.registrarIngreso(porHoraSalida);
		porHoraSalida.setHora(LocalTime.now().minusHours(2));
		int idSalida = porHoraSalida.getId();
		RegistroIngresoSalida regSalida = manager.obtenerRegistro(idSalida);
		Double importeSalida = manager.registrarSalida(regSalida);
		System.out.printf("Registro %d -> importe: %.2f, estado: %s%n", idSalida, importeSalida, regSalida.getEstado());

		// g) Buscar por ID y registrar salida mensual
		sep("g) Salida cliente mensual");
		RegistroIngresoSalida regMensual = manager.obtenerRegistro(mensual.getId());
		Double importeMensual = manager.registrarSalida(regMensual);
		System.out.printf("Registro %d (mensual) -> importe: %.2f, estado: %s%n", regMensual.getId(), importeMensual,
				regMensual.getEstado());

		// Extra: patente duplicada
		sep("Extra: validar patente duplicada");
		try {
			PorHora duplicado = new PorHora(new Vehiculo("AAA111", "Fiat", "Rojo"), LocalDate.now(),
					LocalTime.of(16, 0));
			manager.registrarIngreso(duplicado);
		} catch (Exception e) {
			System.out.println("Error esperado: " + e.getMessage());
		}
	}
}