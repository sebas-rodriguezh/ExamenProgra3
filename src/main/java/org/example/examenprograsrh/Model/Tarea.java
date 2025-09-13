package org.example.examenprograsrh.Model;

import java.time.LocalDate;

public class Tarea {
    private String numero;
    private String descripcion;
    private LocalDate vence;
    private String prioridad;
    private String estado;
    private Usuario responsable;

    public Tarea() {}

    public Tarea(String numero, String descripcion, LocalDate vence, String prioridad, String estado, Usuario responsable) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.vence = vence;
        this.prioridad = prioridad;
        this.estado = estado;
        this.responsable = responsable;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getVence() { return vence; }
    public void setVence(LocalDate vence) { this.vence = vence; }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getResponsable() { return responsable; }
    public void setResponsable(Usuario responsable) { this.responsable = responsable; }
}