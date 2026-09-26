package ar.edu.unju.fi.poo.estacionamiento.model;

public class Cliente {
	private int id;
	private String dni;
	private String domicilio;
	private String celular;

	public Cliente(int id, String dni, String domicilio, String celular) {
		this.id = id;
		this.dni = dni;
		this.domicilio = domicilio;
		this.celular = celular;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Override
	public String toString() {
		return "Cliente{id=%d, dni='%s', domicilio='%s', celular='%s'}".formatted(id, dni, domicilio, celular);
	}
}