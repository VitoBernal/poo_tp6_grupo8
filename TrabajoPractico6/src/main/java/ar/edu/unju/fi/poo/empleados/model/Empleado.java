package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;

public abstract class Empleado {
    private int legajo;
    private int documento;
    private String nombre;
    private LocalDate fechaIngreso;
    private int hijos;
    private double sueldoBasico;

    public Empleado(int legajo, int documento, String nombre, LocalDate fechaIngreso, int hijos) {
        this.legajo = legajo;
        this.documento = documento;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.hijos = hijos;
        this.sueldoBasico = 400000;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getHijos() {
        return hijos;
    }

    public void setHijos(int hijos) {
        this.hijos = hijos;
    }

    public double getSueldoBasico() {
        return sueldoBasico;
    }

    public void setSueldoBasico(double sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    public int antiguedad() {
        int actual = LocalDate.now().getYear();
        int ingreso = fechaIngreso.getYear();
        return actual - ingreso;
    }

    public double calcularSueldoNeto() {
        double sueldoNeto = remunerativosBonificables() - calcularDescuentos() + calcularSalarioFamiliar();
        return sueldoNeto;
    }

    public double calcularSalarioFamiliar() {
        return hijos * 15000;
    }

    public double calcularDescuentos() {
        double descuentos = remunerativosBonificables() * 0.18;
        return descuentos;
    }

    public abstract double remunerativosBonificables();

    public String toString() {
        return "Empleado: Legajo: " + legajo + ", documento: " + documento + ", nombre: " + nombre
                + ", fechaIngreso: " + fechaIngreso + ", hijos: " + hijos;
    }

    public void mostrarDatos() {
        System.out.println(this.toString());
        System.out.println("Sueldo Neto: " + calcularSueldoNeto());
    }
}
