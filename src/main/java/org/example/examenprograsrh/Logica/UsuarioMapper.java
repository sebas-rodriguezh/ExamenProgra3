package org.example.examenprograsrh.Logica;

import org.example.examenprograsrh.Datos.UsuarioEntity;
import org.example.examenprograsrh.Model.Usuario;

public class UsuarioMapper {

    public static Usuario toModel(UsuarioEntity entity) {
        if (entity == null) return null;
        return new Usuario(entity.getNombre(), entity.getApellido());
    }

    public static UsuarioEntity toEntity(Usuario model) {
        if (model == null) return null;

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombre(model.getNombre());
        entity.setApellido(model.getApellido());

        return entity;
    }
}