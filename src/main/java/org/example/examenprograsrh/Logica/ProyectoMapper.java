package org.example.examenprograsrh.Logica;

import org.example.examenprograsrh.Datos.ProyectoEntity;
import org.example.examenprograsrh.Datos.TareaEntity;
import org.example.examenprograsrh.Model.Proyecto;
import org.example.examenprograsrh.Model.Tarea;
import org.example.examenprograsrh.Model.Usuario;

import java.util.stream.Collectors;

public class ProyectoMapper {

    public static Proyecto toModel(ProyectoEntity entity) {
        if (entity == null) return null;

        Proyecto proyecto = new Proyecto();
        proyecto.setCodigo(entity.getCodigo());
        proyecto.setDescripcion(entity.getDescripcion());

        String[] nombres = entity.getEncargado().split(" ");
        proyecto.setEncargado(new Usuario(nombres[0], nombres.length > 1 ? nombres[1] : ""));

        proyecto.setTareas(entity.getTareas().stream()
                .map(TareaMapper::toModel)
                .collect(Collectors.toList()));

        return proyecto;
    }

    public static ProyectoEntity toEntity(Proyecto model) {
        if (model == null) return null;

        ProyectoEntity entity = new ProyectoEntity();
        entity.setCodigo(model.getCodigo());
        entity.setDescripcion(model.getDescripcion());
        entity.setEncargado(model.getEncargado().getNombre() + " " + model.getEncargado().getApellido());

        entity.setTareas(model.getTareas().stream()
                .map(TareaMapper::toEntity)
                .collect(Collectors.toList()));

        return entity;
    }
}