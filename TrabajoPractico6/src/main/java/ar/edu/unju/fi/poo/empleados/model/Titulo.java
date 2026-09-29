package ar.edu.unju.fi.poo.empleados.model;

public class Titulo {
    private int anioObtencion;
    private String nombre;
    private NivelTitulo nivel;

    public int getAnioObtencion() {
        return anioObtencion;
    }

    public void setAnioObtencion(int anioObtencion) {
        this.anioObtencion = anioObtencion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public NivelTitulo getNivel() {
        return nivel;
    }

    public void setNivel(NivelTitulo nivel) {
        this.nivel = nivel;
    }
}
