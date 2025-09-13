package org.example.examenprograsrh.Model;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private String codigo;
    private String descripcion;
    private Usuario encargado;
    private List<Tarea> tareas = new ArrayList<>();

    public Proyecto() {
    }

    public Proyecto(String codigo, String descripcion, Usuario encargado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.encargado = encargado;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Usuario getEncargado() { return encargado; }
    public void setEncargado(Usuario encargado) { this.encargado = encargado; }

    public List<Tarea> getTareas() { return tareas; }
    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

}