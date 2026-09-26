package ar.edu.unju.fi.poo.estacionamiento.manager;

import ar.edu.unju.fi.poo.estacionamiento.model.RegistroIngresoSalida;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Manager {
	private final List<RegistroIngresoSalida> registros = new ArrayList<>();

	/** true si NO hay un registro activo (INGRESADO) con esa patente. */
	public boolean validarPatente(String patente) {
		if (patente == null)
			return false;
		return registros.stream().noneMatch(r -> r.getVehiculo().getPatente().equalsIgnoreCase(patente)
				&& RegistroIngresoSalida.ESTADO_INGRESADO.equals(r.getEstado()));
	}

	public void registrarIngreso(RegistroIngresoSalida registro) {
		if (registro == null)
			throw new IllegalArgumentException("registro requerido");

		if (!validarPatente(registro.getVehiculo().getPatente())) {
			throw new IllegalStateException(
					"El vehículo " + registro.getVehiculo().getPatente() + " ya se encuentra en la playa");
		}

		registro.cambiarEstado(RegistroIngresoSalida.ESTADO_INGRESADO);
		registros.add(registro);
	}

	public Double registrarSalida(RegistroIngresoSalida registro) {
		if (registro == null)
			throw new IllegalArgumentException("registro requerido");
		if (!RegistroIngresoSalida.ESTADO_INGRESADO.equals(registro.getEstado())) {
			throw new IllegalStateException(
					"El registro " + registro.getId() + " no está actualmente dentro de la playa");
		}
		Double importe = registro.obtenerImporte();
		registro.cambiarEstado(RegistroIngresoSalida.ESTADO_AFUERA);
		return importe;
	}

	public RegistroIngresoSalida obtenerRegistro(Integer id) {
		return registros.stream().filter(r -> r.getId() == id).findFirst()
				.orElseThrow(() -> new NoSuchElementException("No existe registro con id " + id));
	}
}