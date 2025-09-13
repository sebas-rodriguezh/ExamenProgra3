package org.example.examenprograsrh.Logica;

import org.example.examenprograsrh.Datos.SistemaConector;
import org.example.examenprograsrh.Datos.SistemaDatos;
import org.example.examenprograsrh.Model.Proyecto;
import org.example.examenprograsrh.Model.Tarea;
import org.example.examenprograsrh.Model.Usuario;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProyectoLogica {
    private final SistemaDatos store;
    private static final String RUTA_XML = java.nio.file.Paths
            .get(System.getProperty("user.dir"), "bd", "sistema.xml")
            .toString();

    public ProyectoLogica() {
        this.store = new SistemaDatos(RUTA_XML);
    }

    // --------- Operaciones de Proyectos ---------
    public List<Proyecto> findAllProyectos() {
        SistemaConector data = store.load();
        return data.getProyectos().stream()
                .map(ProyectoMapper::toModel)
                .collect(Collectors.toList());
    }

    public Proyecto createProyecto(Proyecto proyecto) {
        validarProyecto(proyecto);
        SistemaConector data = store.load();

        // Verificar si ya existe el código
        boolean existeCodigo = data.getProyectos().stream()
                .anyMatch(p -> p.getCodigo().equals(proyecto.getCodigo()));
        if (existeCodigo) {
            throw new IllegalArgumentException("Ya existe un proyecto con el código: " + proyecto.getCodigo());
        }

        data.getProyectos().add(ProyectoMapper.toEntity(proyecto));
        store.save(data);
        return proyecto;
    }

    public void updateProyecto(Proyecto proyecto) {
        validarProyecto(proyecto);
        SistemaConector data = store.load();

        for (int i = 0; i < data.getProyectos().size(); i++) {
            if (data.getProyectos().get(i).getCodigo().equals(proyecto.getCodigo())) {
                data.getProyectos().set(i, ProyectoMapper.toEntity(proyecto));
                store.save(data);
                return;
            }
        }
        throw new NoSuchElementException("No existe proyecto con código: " + proyecto.getCodigo());
    }

    public boolean deleteProyecto(String codigo) {
        SistemaConector data = store.load();
        boolean removed = data.getProyectos().removeIf(p -> p.getCodigo().equals(codigo));
        if (removed) store.save(data);
        return removed;
    }

    // --------- Operaciones de Tareas ---------
    public void agregarTareaAProyecto(String codigoProyecto, Tarea tarea) {
        validarTarea(tarea);
        SistemaConector data = store.load();

        Optional<org.example.examenprograsrh.Datos.ProyectoEntity> proyectoOpt = data.getProyectos().stream()
                .filter(p -> p.getCodigo().equals(codigoProyecto))
                .findFirst();

        if (proyectoOpt.isPresent()) {
            proyectoOpt.get().getTareas().add(TareaMapper.toEntity(tarea));
            store.save(data);
        } else {
            throw new NoSuchElementException("No existe proyecto con código: " + codigoProyecto);
        }
    }

    public void actualizarTarea(String codigoProyecto, Tarea tarea) {
        validarTarea(tarea);
        SistemaConector data = store.load();

        Optional<org.example.examenprograsrh.Datos.ProyectoEntity> proyectoOpt = data.getProyectos().stream()
                .filter(p -> p.getCodigo().equals(codigoProyecto))
                .findFirst();

        if (proyectoOpt.isPresent()) {
            var proyecto = proyectoOpt.get();
            for (int i = 0; i < proyecto.getTareas().size(); i++) {
                if (proyecto.getTareas().get(i).getNumero().equals(tarea.getNumero())) {
                    proyecto.getTareas().set(i, TareaMapper.toEntity(tarea));
                    store.save(data);
                    return;
                }
            }
            throw new NoSuchElementException("No existe tarea con número: " + tarea.getNumero());
        } else {
            throw new NoSuchElementException("No existe proyecto con código: " + codigoProyecto);
        }
    }

    // --------- Operaciones de Usuarios ---------
    public List<Usuario> findAllUsuarios() {
        SistemaConector data = store.load();
        return data.getUsuarios().stream()
                .map(UsuarioMapper::toModel)
                .collect(Collectors.toList());
    }

    // --------- Validaciones ---------
    private void validarProyecto(Proyecto proyecto) {
        if (proyecto == null) throw new IllegalArgumentException("Proyecto no puede ser nulo");
        if (proyecto.getCodigo() == null || proyecto.getCodigo().isBlank())
            throw new IllegalArgumentException("Código del proyecto es obligatorio");
        if (proyecto.getDescripcion() == null || proyecto.getDescripcion().isBlank())
            throw new IllegalArgumentException("Descripción del proyecto es obligatoria");
        if (proyecto.getEncargado() == null)
            throw new IllegalArgumentException("Encargado del proyecto es obligatorio");
    }

    private void validarTarea(Tarea tarea) {
        if (tarea == null) throw new IllegalArgumentException("Tarea no puede ser nula");
        if (tarea.getNumero() == null || tarea.getNumero().isBlank())
            throw new IllegalArgumentException("Número de tarea es obligatorio");
        if (tarea.getDescripcion() == null || tarea.getDescripcion().isBlank())
            throw new IllegalArgumentException("Descripción de tarea es obligatoria");
        if (tarea.getResponsable() == null)
            throw new IllegalArgumentException("Responsable de tarea es obligatorio");
    }
}