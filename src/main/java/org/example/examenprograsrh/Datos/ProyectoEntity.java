package org.example.examenprograsrh.Datos;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProyectoEntity {
    private String codigo;
    private String descripcion;
    private String encargado;

    @XmlElementWrapper(name = "tareas")
    @XmlElement(name = "tarea")
    private List<TareaEntity> tareas = new ArrayList<>();

    public ProyectoEntity() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEncargado() { return encargado; }
    public void setEncargado(String encargado) { this.encargado = encargado; }

    public List<TareaEntity> getTareas() { return tareas; }
    public void setTareas(List<TareaEntity> tareas) { this.tareas = tareas; }
}