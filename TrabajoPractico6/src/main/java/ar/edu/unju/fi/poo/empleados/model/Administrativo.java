package ar.edu.unju.fi.poo.empleados.model;

import java.time.LocalDate;

public class Administrativo extends Empleado {

    private Categoria categoria; // A,B,C

    public Administrativo(int legajo, int documento, String nombre, LocalDate fechaIngreso, int hijos,
            Categoria categoria) {
        super(legajo, documento, nombre, fechaIngreso, hijos);
        this.categoria = categoria;
    }

    @Override
    public double remunerativosBonificables() {
        double remunerativosBonificables = getSueldoBasico() + (antiguedad() * 6500) + adicionalPorCategoria();
        return remunerativosBonificables;
    }

    public int adicionalPorCategoria() {
        switch (categoria) {
            case A:
                return 30000;
            case B:
                return 45000;
            case C:
                return 55000;
            default:
                return 0;
        }
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void cambiarCategoria(Categoria nuevaCategoria) {
        this.categoria = nuevaCategoria;
    }
}
