package org.example.examenprograsrh.Logica;

import org.example.examenprograsrh.Datos.SistemaConector;
import org.example.examenprograsrh.Datos.SistemaDatos;
import org.example.examenprograsrh.Model.Proyecto;
import org.example.examenprograsrh.Model.Tarea;
import org.example.examenprograsrh.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ProyectoLogica {
    private final SistemaDatos store;
    private static final String RUTA_XML = java.nio.file.Paths
            .get(System.getProperty("user.dir"), "bd", "sistema.xml")
            .toString();

    public ProyectoLogica() {
        this.store = new SistemaDatos(RUTA_XML);
    }

    public List<Proyecto> findAllProyectos() {
        SistemaConector data = store.load();
        List<Proyecto> proyectos = new ArrayList<>();

        for (var entity : data.getProyectos()) {
            proyectos.add(ProyectoMapper.toModel(entity));
        }
        return proyectos;
    }

    public Proyecto createProyecto(Proyecto proyecto) {
        if (proyecto == null) throw new RuntimeException("Proyecto no puede ser nulo");
        if (proyecto.getCodigo() == null || proyecto.getCodigo().isEmpty())
            throw new RuntimeException("Código es obligatorio");
        if (proyecto.getDescripcion() == null || proyecto.getDescripcion().isEmpty())
            throw new RuntimeException("Descripción es obligatoria");
        if (proyecto.getEncargado() == null)
            throw new RuntimeException("Encargado es obligatorio");

        SistemaConector data = store.load();

        for (var p : data.getProyectos()) {
            if (p.getCodigo().equals(proyecto.getCodigo())) {
                throw new RuntimeException("Ya existe proyecto con código: " + proyecto.getCodigo());
            }
        }

        data.getProyectos().add(ProyectoMapper.toEntity(proyecto));
        store.save(data);
        return proyecto;
    }

    public void agregarTareaAProyecto(String codigoProyecto, Tarea tarea) {
        if (tarea == null) throw new RuntimeException("Tarea no puede ser nula");
        if (tarea.getNumero() == null || tarea.getNumero().isEmpty())
            throw new RuntimeException("Número de tarea es obligatorio");
        if (tarea.getDescripcion() == null || tarea.getDescripcion().isEmpty())
            throw new RuntimeException("Descripción es obligatoria");
        if (tarea.getResponsable() == null)
            throw new RuntimeException("Responsable es obligatorio");

        SistemaConector data = store.load();

        for (var proyecto : data.getProyectos()) {
            if (proyecto.getCodigo().equals(codigoProyecto)) {
                proyecto.getTareas().add(TareaMapper.toEntity(tarea));
                store.save(data);
                return;
            }
        }
        throw new RuntimeException("No existe proyecto: " + codigoProyecto);
    }

    public void actualizarTarea(String codigoProyecto, Tarea tarea) {
        if (tarea == null) throw new RuntimeException("Tarea no puede ser nula");
        if (tarea.getNumero() == null || tarea.getNumero().isEmpty())
            throw new RuntimeException("Número de tarea es obligatorio");
        if (tarea.getDescripcion() == null || tarea.getDescripcion().isEmpty())
            throw new RuntimeException("Descripción es obligatoria");
        if (tarea.getResponsable() == null)
            throw new RuntimeException("Responsable es obligatorio");

        SistemaConector data = store.load();
        for (var proyecto : data.getProyectos()) {
            if (proyecto.getCodigo().equals(codigoProyecto)) {
                for (int i = 0; i < proyecto.getTareas().size(); i++) {
                    if (proyecto.getTareas().get(i).getNumero().equals(tarea.getNumero())) {
                        proyecto.getTareas().set(i, TareaMapper.toEntity(tarea));
                        store.save(data);
                        return;
                    }
                }
                throw new RuntimeException("No existe tarea: " + tarea.getNumero());
            }
        }
        throw new RuntimeException("No existe proyecto: " + codigoProyecto);
    }

    public List<Usuario> findAllUsuarios() {
        SistemaConector data = store.load();
        List<Usuario> usuarios = new ArrayList<>();

        for (var entity : data.getUsuarios()) {
            usuarios.add(UsuarioMapper.toModel(entity));
        }
        return usuarios;
    }
}