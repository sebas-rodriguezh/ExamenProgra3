package org.example.examenprograsrh.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.examenprograsrh.Logica.ProyectoLogica;
import org.example.examenprograsrh.Model.Proyecto;
import org.example.examenprograsrh.Model.Tarea;
import org.example.examenprograsrh.Model.Usuario;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GestorProyectosController implements Initializable {
    @FXML private Label lblProyectoSeleccionado;
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
    @FXML private TableColumn <Tarea, String> tableColumnNumeroTarea;
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
    private final List<String> prioridades = Arrays.asList("Alta", "Media", "Baja");
    private final List<String> estados = Arrays.asList("Abierta", "En-progreso", "En-revisión", "Resuelta");

    private Proyecto proyectoSeleccionado = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarUsuarios();
        cargarComboboxes();
        configurarTablas();
        cargarProyectos();
        configurarInterfaz();
        seguirRastroAlProyecto();
    }

    private void cargarUsuarios() {
        try {
            proyectoLogica.findAllUsuarios().forEach(usuario ->
                    usuarios.add(usuario.getNombre() + " " + usuario.getApellido()));
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los usuarios: " + e.getMessage());
        }
    }

    private void cargarComboboxes() {
        comboBoxEncargadoProyecto.setItems(usuarios);
        comboBoxResponsableTarea.setItems(usuarios);
        comboBoxPrioridadTarea.getItems().addAll(prioridades);
        comboBoxEstadoTarea.getItems().addAll(estados);
    }

    private void configurarTablas() {
        tableColumnCodigoProyecto.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnDescripcionProyecto.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tableColumnEncargadoProyecto.setCellValueFactory(new PropertyValueFactory<>("EncargadoNombre"));
        tableColumnNumeroTareas.setCellValueFactory(new PropertyValueFactory<>("numeroTareas"));
        tableColumnNumeroTarea.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableColumnDescripcionTarea.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tableColumnVencimientoTarea.setCellValueFactory(new PropertyValueFactory<>("vence"));
        tableColumnPrioridadTarea.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        tableColumnEstadoTarea.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tableColumnResponsableTarea.setCellValueFactory(new PropertyValueFactory<>("responsableNombre"));
    }

    private void cargarProyectos() {
        listaProyectos.clear();
        listaProyectos.addAll(proyectoLogica.findAllProyectos());
        tableVIewProyectos.setItems(listaProyectos);
    }

    private void configurarInterfaz() {
        ocultarFormularioTareas();
        tableViewTareas.setItems(listaTareas);
    }

    private void ocultarFormularioTareas() {
        lblTareas.setVisible(false);
        lblDescripcion.setVisible(false);
        lblVence.setVisible(false);
        lblPrioridad.setVisible(false);
        lblEstado.setVisible(false);
        lblResponsable.setVisible(false);
        txtDescripcionTarea.setVisible(false);
        dtpFechaVence.setVisible(false);
        comboBoxPrioridadTarea.setVisible(false);
        comboBoxEstadoTarea.setVisible(false);
        comboBoxResponsableTarea.setVisible(false);
        btnCrearTarea.setVisible(false);
        tableViewTareas.setVisible(false);
        lblProyectoSeleccionado.setText("");
    }

    private void seguirRastroAlProyecto() {
        tableVIewProyectos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    proyectoSeleccionado = newValue;
                    if (newValue != null) {
                        mostrarTareasProyecto(newValue);
                        mostrarFormularioTareas();
                        lblProyectoSeleccionado.setText("Proyecto: " + newValue.getCodigo() + " - " + newValue.getDescripcion());
                    } else {
                        ocultarFormularioTareas();
                    }
                }
        );

        tableViewTareas.setRowFactory(tv -> {
            TableRow<Tarea> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    editarTarea(row.getItem());
                }
            });
            return row;
        });
    }

    private void mostrarFormularioTareas() {
        lblTareas.setVisible(true);
        lblDescripcion.setVisible(true);
        lblVence.setVisible(true);
        lblPrioridad.setVisible(true);
        lblEstado.setVisible(true);
        lblResponsable.setVisible(true);
        txtDescripcionTarea.setVisible(true);
        dtpFechaVence.setVisible(true);
        comboBoxPrioridadTarea.setVisible(true);
        comboBoxEstadoTarea.setVisible(true);
        comboBoxResponsableTarea.setVisible(true);
        btnCrearTarea.setVisible(true);
        tableViewTareas.setVisible(true);
        txtDescripcionTarea.clear();
        dtpFechaVence.setValue(null);
        comboBoxPrioridadTarea.getSelectionModel().clearSelection();
        comboBoxEstadoTarea.getSelectionModel().clearSelection();
        comboBoxResponsableTarea.getSelectionModel().clearSelection();
    }

    private void mostrarTareasProyecto(Proyecto proyecto) {
        listaTareas.clear();
        listaTareas.addAll(proyecto.getTareas());
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void crearProyecto() {
        if (txtDescripcionProyecto.getText().isEmpty() || comboBoxEncargadoProyecto.getValue() == null) {
            mostrarAlerta("Error", "Seleccione todos los campos.");
            return;
        }

        try {
            String codigo = "P" + (listaProyectos.size() + 1);

            String nombreCompleto = comboBoxEncargadoProyecto.getValue();
            String[] partes = nombreCompleto.split(" ");
            Usuario encargado = new Usuario(partes[0], partes.length > 1 ? partes[1] : "");

            Proyecto proyecto = new Proyecto(codigo, txtDescripcionProyecto.getText(), encargado);
            proyectoLogica.createProyecto(proyecto);

            listaProyectos.add(proyecto);
            txtDescripcionProyecto.clear();
            comboBoxEncargadoProyecto.getSelectionModel().clearSelection();

            mostrarAlerta("Listo", "Proyecto creado: " + codigo);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear: " + e.getMessage());
        }
    }

    @FXML
    private void crearTarea() {
        if (proyectoSeleccionado == null) {
            mostrarAlerta("Error", "Primero selecciona un proyecto");
            return;
        }

        if (txtDescripcionTarea.getText().isEmpty() || dtpFechaVence.getValue() == null || comboBoxPrioridadTarea.getValue() == null || comboBoxEstadoTarea.getValue() == null || comboBoxResponsableTarea.getValue() == null) {
            mostrarAlerta("Error", "Completa todos los campos");
            return;
        }
        try {
            String numeroTarea = "T" + (proyectoSeleccionado.getTareas().size() + 1);
            String nombreCompleto = comboBoxResponsableTarea.getValue();
            String[] partes = nombreCompleto.split(" ");
            Usuario responsable = new Usuario(partes[0], partes.length > 1 ? partes[1] : "");

            Tarea tarea = new Tarea(numeroTarea, txtDescripcionTarea.getText(), dtpFechaVence.getValue(), comboBoxPrioridadTarea.getValue(), comboBoxEstadoTarea.getValue(), responsable);
            proyectoLogica.agregarTareaAProyecto(proyectoSeleccionado.getCodigo(), tarea);
            proyectoSeleccionado.agregarTarea(tarea);
            listaTareas.add(tarea);
            txtDescripcionTarea.clear();
            dtpFechaVence.setValue(null);
            comboBoxPrioridadTarea.getSelectionModel().clearSelection();
            comboBoxEstadoTarea.getSelectionModel().clearSelection();
            comboBoxResponsableTarea.getSelectionModel().clearSelection();

            mostrarAlerta("Listo", "Tarea creada: " + numeroTarea);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear: " + e.getMessage());
        }
    }


    private void editarTarea(Tarea tarea) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Tarea");
        dialog.setHeaderText("Editando: " + tarea.getNumero());

        ComboBox<String> comboPrioridad = new ComboBox<>();
        comboPrioridad.getItems().addAll(prioridades);
        comboPrioridad.setValue(tarea.getPrioridad());

        ComboBox<String> comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll(estados);
        comboEstado.setValue(tarea.getEstado());

        GridPane grid = new GridPane();
        grid.add(new Label("Prioridad:"), 0, 0);
        grid.add(comboPrioridad, 1, 0);
        grid.add(new Label("Estado:"), 0, 1);
        grid.add(comboEstado, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        if (dialog.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            tarea.setPrioridad(comboPrioridad.getValue());
            tarea.setEstado(comboEstado.getValue());

            try {
                proyectoLogica.actualizarTarea(proyectoSeleccionado.getCodigo(), tarea);
                tableViewTareas.refresh();
                mostrarAlerta("Listo", "Tarea actualizada");
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo actualizar: " + e.getMessage());
            }
        }
    }
}
