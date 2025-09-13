package org.example.examenprograsrh.Logica;

import org.example.examenprograsrh.Datos.TareaEntity;
import org.example.examenprograsrh.Model.Tarea;
import org.example.examenprograsrh.Model.Usuario;

public class TareaMapper {

    public static Tarea toModel(TareaEntity entity) {
        if (entity == null) return null;

        Tarea tarea = new Tarea();
        tarea.setNumero(entity.getNumero());
        tarea.setDescripcion(entity.getDescripcion());
        tarea.setVence(entity.getVence());
        tarea.setPrioridad(entity.getPrioridad());
        tarea.setEstado(entity.getEstado());

        String[] nombres = entity.getResponsable().split(" ");
        tarea.setResponsable(new Usuario(nombres[0], nombres.length > 1 ? nombres[1] : ""));

        return tarea;
    }

    public static TareaEntity toEntity(Tarea model) {
        if (model == null) return null;

        TareaEntity entity = new TareaEntity();
        entity.setNumero(model.getNumero());
        entity.setDescripcion(model.getDescripcion());
        entity.setVence(model.getVence());
        entity.setPrioridad(model.getPrioridad());
        entity.setEstado(model.getEstado());
        entity.setResponsable(model.getResponsable().getNombre() + " " + model.getResponsable().getApellido());

        return entity;
    }
}