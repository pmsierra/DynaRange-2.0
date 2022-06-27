package com.example.dynarange2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dbManagement.Caso;
import dbManagement.SQLiteManager;
import dbManagement.Sesion;
import dbManagement.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class sesionesCasoController implements Initializable {
    @FXML
    private JFXButton volver_button;
    @FXML
    private JFXButton borrarSesion_button;
    @FXML
    private JFXButton nuevoSesion_button;
    @FXML
    private JFXButton seleccionarSesion_button;
    @FXML
    private Label idCaso_label;

    @FXML
    private Pane main_pane;
    @FXML
    private AnchorPane sesion_anchorPane;
    private static Caso selected_caso;
    private  Sesion selected_sesion;
    private static SQLiteManager manager_object;
    private static Stage primary_Stage;

    @FXML
    private JFXTreeTableView<Sesiones> sesiones_treeView;
    @FXML
    private final ObservableList<Sesiones> sesiones_objects = FXCollections.observableArrayList();
    
    private medicionesSesionController mediciones_controller;
    private static Stage stage;
    public static void setValues(Stage primaryStage, Caso caso) {
        primary_Stage = primaryStage;
        selected_caso=caso;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




        idCaso_label.setText("Sesiones del caso " + selected_caso.getCaso_id());
        manager_object = new SQLiteManager();
        boolean everything_ok = manager_object.Connect();
        System.out.println(everything_ok);

        JFXTreeTableColumn<Sesiones, String> sesion_id = new JFXTreeTableColumn<>("Id de la Sesión");
        sesion_id.setPrefWidth(100);

        sesion_id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sesiones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sesiones, String> param) {
                return param.getValue().getValue().sesion_id;
            }
        });
        sesion_id.setResizable(false);


        JFXTreeTableColumn<Sesiones, String> path = new JFXTreeTableColumn<>("Carpeta");
        path.setPrefWidth(700);
        path.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sesiones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sesiones, String> param) {
                return param.getValue().getValue().path;
            }
        });
        path.setResizable(false);

        JFXTreeTableColumn<Sesiones, String> fecha = new JFXTreeTableColumn<>("Fecha del caso");
        fecha.setPrefWidth(400);
        fecha.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Sesiones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Sesiones, String> param) {
                return param.getValue().getValue().fecha;
            }
        });
        fecha.setResizable(false);


        List<Sesion> sesionList = manager_object.getMethods().List_sesion_by_caso(selected_caso.getCaso_id());
        selected_caso.setSesionList((LinkedList<Sesion>) sesionList);
        for (Sesion sesion: sesionList) {
            sesiones_objects.add(new Sesiones(sesion.getSesion_id().toString(), sesion.getPath(), sesion.getFecha()));
        }
        TreeItem<Sesiones> root = new RecursiveTreeItem<Sesiones>(sesiones_objects, RecursiveTreeObject::getChildren);
        sesiones_treeView.setPlaceholder(new Label("No hay ninguna sesión para este caso"));
        sesiones_treeView.getColumns().setAll(sesion_id, path, fecha);
        sesiones_treeView.setRoot(root);
        sesiones_treeView.setShowRoot(false);

    }
    public void refreshSesionListView() throws IOException {
        sesionesCasoController.setValues(primary_Stage, selected_caso);
        Parent root = FXMLLoader.load(getClass().getResource("sesionesCasoView.fxml"));

        primary_Stage.setTitle("Sesiones del Caso");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }
    public void volver(MouseEvent mouseEvent) throws IOException {

        User user =selected_caso.getUser();
        casosSujetoController.setValues(primary_Stage, user);
        Parent root = FXMLLoader.load(getClass().getResource("casosSujetoView.fxml"));

        primary_Stage.setTitle("Casos Sujeto");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }



    private Sesion getSelectedSesion() {

        String stringValue=String.valueOf(sesiones_treeView.getSelectionModel().getSelectedItem().getValue().sesion_id);
        String id_string = stringValue.replaceAll("[^0-9]","");
        Integer sesion_id = Integer.valueOf(id_string);
        Sesion sesion = manager_object.getMethods().Search_sesion_by_id(sesion_id);
        return sesion;
    }





    @FXML
    private void close_app(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }

    public void borrarSesion(MouseEvent mouseEvent) throws IOException {
        Sesion sesion = getSelectedSesion();
        manager_object.getMethods().Delete_sesion(sesion);
        File sesionpath = new File (sesion.getPath());
        deleteDir(sesionpath);
        refreshSesionListView();
    }
    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

    public void nuevaSesion(MouseEvent mouseEvent) throws IOException {

        sesionConfigController.setValues(primary_Stage, selected_caso, manager_object);
        Parent root = FXMLLoader.load(getClass().getResource("sesionConfigView.fxml"));

        primary_Stage.setTitle("Nueva Sesión");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    public void seleccionarSesion(MouseEvent mouseEvent) throws IOException {


        Sesion sesion = getSelectedSesion();
        medicionesSesionController.setValues(primary_Stage, manager_object, sesion);
        Parent root = FXMLLoader.load(getClass().getResource("medicionesSesionView.fxml"));

        primary_Stage.setTitle("Mediciones de Sesion");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();


    }
    public void seleccionarSesion2(MouseEvent mouseEvent) throws IOException {


        if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
            Sesion sesion = getSelectedSesion();
            medicionesSesionController.setValues(primary_Stage, manager_object, sesion);
            Parent root = FXMLLoader.load(getClass().getResource("medicionesSesionView.fxml"));

            primary_Stage.setTitle("Mediciones de Sesion");
            Scene scene = new Scene(root);
            //primary_Stage.initStyle(StageStyle.UNDECORATED);
            primary_Stage.setScene(scene);
            stage = primary_Stage;
            primary_Stage.show();
        }


    }
}
class Sesiones extends RecursiveTreeObject<Sesiones> {

    StringProperty sesion_id;
    StringProperty path;
    StringProperty fecha;

    public Sesiones(String sesion_id, String path, String fecha) {
        this.sesion_id = new SimpleStringProperty(sesion_id);
        this.path = new SimpleStringProperty(path);
        this.fecha = new SimpleStringProperty(fecha);

    }
}