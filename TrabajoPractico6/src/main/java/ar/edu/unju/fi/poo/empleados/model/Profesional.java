package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Profesional extends Empleado {
    private List<Titulo> titulos;

    public Profesional(int legajo, int documento, String nombre, LocalDate fechaIngreso, int hijos) {
        super(legajo, documento, nombre, fechaIngreso, hijos);
        this.titulos = new ArrayList<>();
    }

    public List<Titulo> getTitulos() {
        return titulos;
    }

    public void setTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
    }

    @Override
    public double remunerativosBonificables() {
        double remunerativosBonificables = getSueldoBasico() + (antiguedad() * 6500) + adicionalPorTitulo();
        return remunerativosBonificables;
    }

    public double adicionalPorTitulo() {
        double adicional = 0;
        int cantidadTitulos = titulos.size();
        adicional += cantidadTitulos * 30000;
        return adicional;
    }
}
