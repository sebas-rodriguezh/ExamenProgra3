package org.example.examenprograsrh.Datos;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "sistemaData")
@XmlAccessorType(XmlAccessType.FIELD)
public class SistemaConector {

    @XmlElementWrapper(name = "usuarios")
    @XmlElement(name = "usuario")
    private List<UsuarioEntity> usuarios = new ArrayList<>();

    @XmlElementWrapper(name = "proyectos")
    @XmlElement(name = "proyecto")
    private List<ProyectoEntity> proyectos = new ArrayList<>();

    public List<UsuarioEntity> getUsuarios() { return usuarios; }
    public void setUsuarios(List<UsuarioEntity> usuarios) { this.usuarios = usuarios; }

    public List<ProyectoEntity> getProyectos() { return proyectos; }
    public void setProyectos(List<ProyectoEntity> proyectos) { this.proyectos = proyectos; }
}