package org.example.examenprograsrh.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.examenprograsrh.Logica.ProyectoLogica;
import org.example.examenprograsrh.Model.Proyecto;
import org.example.examenprograsrh.Model.Tarea;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GestorProyectosController implements Initializable {
    @FXML private DatePicker dtpFechaVence;
    @FXML private ComboBox<String> comboBoxResponsableTarea;
    @FXML private Label lblResponsable;
    @FXML private ComboBox <String> comboBoxEstadoTarea;
    @FXML private Label lblEstado;
    @FXML private ComboBox <String> comboBoxPrioridadTarea;
    @FXML private Label lblPrioridad;
    @FXML private Label lblVence;
    @FXML private TextField txtDescripcionTarea;
    @FXML private Label lblDescripcion;
    @FXML private Button btnCrearTarea;
    @FXML private Label lblTareas;
    @FXML private TableColumn <Tarea, String> tableColumnResponsableTarea;
    @FXML private TableColumn <Tarea, String> tableColumnEstadoTarea;
    @FXML private TableColumn <Tarea, String> tableColumnPrioridadTarea;
    @FXML private TableColumn <Tarea, LocalDate> tableColumnVencimientoTarea;
    @FXML private TableColumn <Tarea, String> tableColumnDescripcionTarea;
    @FXML private TableColumn <Proyecto, Integer> tableColumnNumeroTarea;
    @FXML private TableView <Tarea> tableViewTareas;
    @FXML private TableColumn <Proyecto, Integer> tableColumnNumeroTareas;
    @FXML private TableColumn <Proyecto, String> tableColumnEncargadoProyecto;
    @FXML private TableColumn  <Proyecto, String> tableColumnDescripcionProyecto;
    @FXML private TableColumn <Proyecto, String> tableColumnCodigoProyecto;
    @FXML private TableView<Proyecto> tableVIewProyectos;
    @FXML private ComboBox <String> comboBoxEncargadoProyecto;
    @FXML private TextField txtDescripcionProyecto;
    @FXML private Button btnCrearProyecto;

    private final ProyectoLogica proyectoLogica = new ProyectoLogica();
    private final ObservableList<Proyecto> listaProyectos = FXCollections.observableArrayList();
    private final ObservableList<Tarea> listaTareas = FXCollections.observableArrayList();
    private final ObservableList<String> usuarios = FXCollections.observableArrayList();
    private final ObservableList<String> prioridades = FXCollections.observableArrayList("Alta", "Media", "Baja");
    private final ObservableList<String> estados = FXCollections.observableArrayList("Abierta", "En-progreso", "En-revisión", "Resuelta");

    private Proyecto proyectoSeleccionado = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarUsuarios();
        cargarComboboxes();
        configurarTablas();
        cargarProyectos();
        configurarInterfaz();
        configurarEventos();
    }

    private void cargarUsuarios() {
    }

    private void cargarComboboxes() {
    }

    private void configurarTablas() {
    }

    private void cargarProyectos() {
    }

    private void configurarInterfaz() {
    }

    private void configurarEventos() {
    }
}
