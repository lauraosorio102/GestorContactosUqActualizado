package com.lauradev.gestorcontactosuq.controllers;

import com.lauradev.gestorcontactosuq.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
public class GestorContactosController implements Initializable {
    @FXML
    private TextField campoTxtNombre;
    @FXML
    private TextField campoTxtApellido;
    @FXML
    private TextField campoTxtID;
    @FXML
    private DatePicker Datepuckerfecha;
    @FXML
    private TextField campoTxtEmail;
    @FXML
    private TextField campoTxtTelefono;
    @FXML
    private ImageView img_imagenPerfil;
    @FXML
    private ComboBox<String> cbOpciones;

    //TextFileds
    @FXML
    public TextField txtFiltro;
    @FXML
    private TableView<Contacto> tablaUsuario;
    @FXML
    private TableColumn<Contacto, String> cl_nombre;
    @FXML
    private TableColumn<Contacto, String> cl_apellido;
    @FXML
    private TableColumn<Contacto, String> cl_id;
    @FXML
    private TableColumn<Contacto, String> cl_fecha;
    @FXML
    private TableColumn<Contacto, String> cl_email;
    @FXML
    private TableColumn<Contacto, String> cl_telefono;

    private final GestorContactos gestorContactos;
    private Contacto contactoSeleccionado;
    private ObservableList<Contacto> listaContactos;

    public GestorContactosController() {
        gestorContactos = new GestorContactos();
    }

    Contacto contacto = Contacto.builder().nombre("santiago").apellido("Gonsalez").id("5236541256").fechaCumpleanios(LocalDate.of(1990, 5, 15)).email("santigonza@jahsj.com").telefono("2554136520").imagenPefil(null).build();
    Image imagenDeFault = new Image(getClass().getResource("/imagenproyecto.jpg").toExternalForm());

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cl_nombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        cl_apellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        cl_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        cl_fecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaCumpleanios().toString()));
        cl_email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        cl_telefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

        listaContactos = FXCollections.observableArrayList();
        listaContactos.add(contacto);
        img_imagenPerfil.setImage(imagenDeFault);

        tablaUsuario.setItems(listaContactos);


        List<String> opciones = new ArrayList<>();
        opciones.add("Nombre");
        opciones.add("ID");

        cbOpciones.setItems(FXCollections.observableList(opciones));

        tablaUsuario.setOnMouseClicked(event -> {
            //Obtener el contacto seleccionado
            contactoSeleccionado = tablaUsuario.getSelectionModel().getSelectedItem();

            if (contactoSeleccionado != null) {
                campoTxtNombre.setText(contactoSeleccionado.getNombre());
                campoTxtApellido.setText(contactoSeleccionado.getApellido());
                campoTxtID.setText(contactoSeleccionado.getId());
                campoTxtEmail.setText(contactoSeleccionado.getEmail());
                Datepuckerfecha.setValue(contactoSeleccionado.getFechaCumpleanios());
                if (contactoSeleccionado.getImagenPefil() != null) {
                    img_imagenPerfil.setImage(contactoSeleccionado.getImagenPefil());
                } else {
                    img_imagenPerfil.setImage(imagenDeFault);
                }
                campoTxtTelefono.setText(contactoSeleccionado.getTelefono());
            }
        });
    }

    @FXML
    private void crearActionButton() {
        try {
            gestorContactos.agregarContacto(
                    campoTxtNombre.getText(),
                    campoTxtApellido.getText(),
                    campoTxtID.getText(),
                    campoTxtEmail.getText(),
                    campoTxtTelefono.getText(),
                    Datepuckerfecha.getValue(),
                    img_imagenPerfil.getImage()
            );
            cargarContactos();
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void eliminarActionButton() {
        Contacto seleccionado = tablaUsuario.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Selecciona un contacto para eliminar.", Alert.AlertType.INFORMATION);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Deseas eliminar este contacto?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> respuesta = confirmacion.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.YES) {
            listaContactos.remove(seleccionado);
            limpiarCampos();
        }
    }

    @FXML
    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File archivoSeleccionado = fileChooser.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            Image imagen = new Image(archivoSeleccionado.toURI().toString());
            img_imagenPerfil.setImage(imagen);
        }
    }

    private void limpiarCampos() {
        campoTxtNombre.clear();
        campoTxtApellido.clear();
        campoTxtID.clear();
        Datepuckerfecha.setValue(null);
        campoTxtEmail.clear();
        campoTxtTelefono.clear();
        img_imagenPerfil.setImage(imagenDeFault);
    }


    private void mostrarAlerta(String mensaje, Alert.AlertType type) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void cargarContactos() {
        listaContactos.setAll(gestorContactos.listarContactos());
        tablaUsuario.setItems(listaContactos);
    }

    @FXML
    private void editarActionButton() {
        try {
            if (contactoSeleccionado == null) {
                mostrarAlerta("Seleccione un contacto para editar", Alert.AlertType.ERROR);
                return;
            }

            // Validar campos
            if (!validarCampos()) return;

            // Editar el contacto
            gestorContactos.editarContacto(
                    contactoSeleccionado.getId(),
                    campoTxtNombre.getText(),
                    campoTxtApellido.getText(),
                    campoTxtEmail.getText(),
                    campoTxtTelefono.getText(),
                    Datepuckerfecha.getValue(),
                    img_imagenPerfil.getImage()
            );

            mostrarAlerta("Contacto editado correctamente", Alert.AlertType.INFORMATION);
            cargarContactos();
            limpiarCampos();

        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (campoTxtNombre.getText().isEmpty() || campoTxtApellido.getText().isEmpty() ||
                campoTxtEmail.getText().isEmpty() || campoTxtTelefono.getText().isEmpty() ||
                Datepuckerfecha.getValue() == null) {
            mostrarAlerta("Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    @FXML
    public void btnActionButtonSeleccionarImg() {

    }

    @FXML
    public void eliminarFoto(ActionEvent actionEvent) {
    }

    @FXML
    public void mostrarFoto(ActionEvent actionEvent) {
    }


    public void buscarContactos(ActionEvent actionEvent) {
        String seleccion = cbOpciones.getValue();

        if (seleccion != null) {

            if (seleccion.equals("ID")) {
                String texto = txtFiltro.getText();

                if (!texto.matches("^\\d+$")) {
                    mostrarAlerta("Solo se puede escribir numeros", Alert.AlertType.ERROR);
                } else {

                }
            }

        }
    }

    public void filtrarTabla (ActionEvent actionEvent){
            try {
                if (!verificarIngresado()) {
                    mostrarAlerta("Ingrese un formato valido", Alert.AlertType.ERROR);
                }
            } catch (Exception ex) {
                mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
            }

            String valor = txtFiltro.getText();
            String tipo = cbOpciones.getSelectionModel().getSelectedItem();

            List<Contacto> contactos = new ArrayList<>();

            if (!valor.isEmpty()) {

                contactos = switch (tipo) {
                    case "Nombre" -> gestorContactos.buscarContactoNombre(valor);
                    case "Telefono" -> gestorContactos.buscarContactoTelefonolista(valor);
                    default -> throw new IllegalStateException("Unexpected value: " + tipo);
                };

                actualizarContactos(contactos);
            }
        }
        public boolean verificarIngresado() throws Exception {

            String valor = txtFiltro.getText();
            String tipo = cbOpciones.getSelectionModel().getSelectedItem();

            boolean validacion = false;

            if (tipo == null || tipo.isEmpty()) {
                throw new Exception("Seleccione una opcion");
            } else if (valor == null || valor.isEmpty()) {
                validacion = false;
            } else {
                switch (tipo) {
                    case "Nombre":
                        validacion = gestorContactos.validarTexto(valor);
                        break;
                    case "Telefono":
                        validacion = gestorContactos.validarTelefono(valor);
                        break;
                    default:
                        throw new Exception("Seleccione una opcion");
                }
            }
            return validacion;
        }

        public void actualizarContactos(List<Contacto> contactos){
            listaContactos.setAll(contactos);
        }
}

