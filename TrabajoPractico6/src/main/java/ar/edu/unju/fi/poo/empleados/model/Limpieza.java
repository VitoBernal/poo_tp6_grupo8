package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;

public class Limpieza extends Empleado {
    private int adicional = 25000;

    public Limpieza(int legajo, int documento, String nombre, LocalDate fechaIngreso, int hijos) {
        super(legajo, documento, nombre, fechaIngreso, hijos);
    }

    @Override
    public double remunerativosBonificables() {
        double remunerativosBonificables = getSueldoBasico() + (antiguedad() * 6500) + adicional;
        return remunerativosBonificables;
    }

    public int getAdicional() {
        return adicional;
    }

    public void setAdicional(int adicional) {
        this.adicional = adicional;
    }
}
