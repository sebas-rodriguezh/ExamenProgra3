package org.example.examenprograsrh.Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.examenprograsrh.Logica.ProyectoLogica;
import org.example.examenprograsrh.Model.Proyecto;
import org.example.examenprograsrh.Model.Tarea;
import org.example.examenprograsrh.Model.Usuario;

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
        comboBoxPrioridadTarea.setItems(prioridades);
        comboBoxEstadoTarea.setItems(estados);
    }

    private void configurarTablas() {
        // Configurar tabla de proyectos
        tableColumnCodigoProyecto.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getCodigo()));
        tableColumnDescripcionProyecto.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getDescripcion()));
        tableColumnEncargadoProyecto.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getEncargado().getNombre() + " " + cd.getValue().getEncargado().getApellido()));
        tableColumnNumeroTareas.setCellValueFactory(cd ->
                new SimpleIntegerProperty(cd.getValue().getTareas().size()).asObject());

        tableColumnNumeroTarea.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getNumero()));
        tableColumnDescripcionTarea.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getDescripcion()));
        tableColumnVencimientoTarea.setCellValueFactory(cd ->
                new SimpleObjectProperty<>(cd.getValue().getVence()));
        tableColumnPrioridadTarea.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getPrioridad()));
        tableColumnEstadoTarea.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getEstado()));
        tableColumnResponsableTarea.setCellValueFactory(cd ->
                new SimpleStringProperty(cd.getValue().getResponsable().getNombre() + " " + cd.getValue().getResponsable().getApellido()));
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
        //tableViewTareas.setVisible(false);
    }

    private void configurarEventos() {
        // Cuando se selecciona un proyecto, mostrar sus tareas
        tableVIewProyectos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    proyectoSeleccionado = newValue;
                    if (newValue != null) {
                        mostrarTareasProyecto(newValue);
                        mostrarFormularioTareas();
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

    private void editarTarea(Tarea tarea) {
        // Diálogo simple para editar
        ChoiceDialog<String> dialog = new ChoiceDialog<>(tarea.getPrioridad(), prioridades);
        dialog.setTitle("Editar Tarea");
        dialog.setHeaderText("Editando: " + tarea.getNumero());
        dialog.setContentText("Selecciona nueva prioridad:");

        // Mostrar y obtener resultado
        dialog.showAndWait().ifPresent(nuevaPrioridad -> {
            tarea.setPrioridad(nuevaPrioridad);
            try {
                proyectoLogica.actualizarTarea(proyectoSeleccionado.getCodigo(), tarea);
                tableViewTareas.refresh(); // Refrescar tabla
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo actualizar: " + e.getMessage());
            }
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
        lblTareas.setText("Tareas de: " + proyecto.getDescripcion());
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
        try {
            String descripcion = txtDescripcionProyecto.getText().trim();
            String encargadoNombre = comboBoxEncargadoProyecto.getValue();

            if (descripcion.isEmpty()) {
                mostrarAlerta("Error", "La descripción del proyecto es obligatoria");
                return;
            }

            if (encargadoNombre == null) {
                mostrarAlerta("Error", "Debe seleccionar un encargado");
                return;
            }

            // Generar código automáticamente
            String codigo = "PROY-" + String.format("%03d", listaProyectos.size() + 1);

            // Crear usuario encargado
            String[] nombres = encargadoNombre.split(" ");
            Usuario encargado = new Usuario(nombres[0], nombres.length > 1 ? nombres[1] : "");

            // Crear proyecto
            Proyecto nuevoProyecto = new Proyecto(codigo, descripcion, encargado);
            proyectoLogica.createProyecto(nuevoProyecto);

            // Limpiar campos
            txtDescripcionProyecto.clear();
            comboBoxEncargadoProyecto.getSelectionModel().clearSelection();

            // Recargar proyectos
            cargarProyectos();

            mostrarAlerta("Éxito", "Proyecto creado exitosamente");

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al crear proyecto: " + e.getMessage());
        }
    }

    @FXML
    private void crearTarea() {
        try {
            if (proyectoSeleccionado == null) {
                mostrarAlerta("Error", "Debe seleccionar un proyecto primero");
                return;
            }

            String descripcion = txtDescripcionTarea.getText().trim();
            LocalDate vence = dtpFechaVence.getValue();
            String prioridad = comboBoxPrioridadTarea.getValue();
            String estado = comboBoxEstadoTarea.getValue();
            String responsableNombre = comboBoxResponsableTarea.getValue();

            if (descripcion.isEmpty()) {
                mostrarAlerta("Error", "La descripción de la tarea es obligatoria");
                return;
            }

            if (vence == null) {
                mostrarAlerta("Error", "La fecha de vencimiento es obligatoria");
                return;
            }

            if (prioridad == null) {
                mostrarAlerta("Error", "Debe seleccionar una prioridad");
                return;
            }

            if (estado == null) {
                mostrarAlerta("Error", "Debe seleccionar un estado");
                return;
            }

            if (responsableNombre == null) {
                mostrarAlerta("Error", "Debe seleccionar un responsable");
                return;
            }

            // Generar número de tarea automáticamente
            String numeroTarea = proyectoSeleccionado.getCodigo() + "-T" +
                    String.format("%03d", proyectoSeleccionado.getTareas().size() + 1);

            // Crear usuario responsable
            String[] nombres = responsableNombre.split(" ");
            Usuario responsable = new Usuario(nombres[0], nombres.length > 1 ? nombres[1] : "");

            // Crear tarea
            Tarea nuevaTarea = new Tarea(numeroTarea, descripcion, vence, prioridad, estado, responsable);
            proyectoLogica.agregarTareaAProyecto(proyectoSeleccionado.getCodigo(), nuevaTarea);

            // Limpiar campos
            limpiarFormularioTareas();

            // Recargar proyectos y tareas
            cargarProyectos();
            mostrarTareasProyecto(proyectoSeleccionado);

            mostrarAlerta("Éxito", "Tarea creada exitosamente");

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al crear tarea: " + e.getMessage());
        }
    }

    private void limpiarFormularioTareas() {
        txtDescripcionTarea.clear();
        dtpFechaVence.setValue(null);
        comboBoxPrioridadTarea.getSelectionModel().clearSelection();
        comboBoxEstadoTarea.getSelectionModel().clearSelection();
        comboBoxResponsableTarea.getSelectionModel().clearSelection();
    }


}
