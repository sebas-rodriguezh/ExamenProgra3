package org.example.examenprograsrh;

import org.example.examenprograsrh.Logica.ProyectoLogica;
import org.example.examenprograsrh.Model.Proyecto;
import org.example.examenprograsrh.Model.Tarea;
import org.example.examenprograsrh.Model.Usuario;

import java.time.LocalDate;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBAS DEL SISTEMA ===");

        ProyectoLogica logica = new ProyectoLogica();
        System.out.println("\n1. Usuarios disponibles:");
        logica.findAllUsuarios().forEach(usuario ->
                System.out.println("   - " + usuario.getNombre() + " " + usuario.getApellido()));
    }
}