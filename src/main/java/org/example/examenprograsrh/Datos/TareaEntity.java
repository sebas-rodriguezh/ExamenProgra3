package org.example.examenprograsrh.Datos;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class TareaEntity {
    private String numero;
    private String descripcion;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate vence;

    private String prioridad;
    private String estado;
    private String responsable;

    public TareaEntity() {}

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getVence() { return vence; }
    public void setVence(LocalDate vence) { this.vence = vence; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
}