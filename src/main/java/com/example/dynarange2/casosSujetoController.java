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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class casosSujetoController implements Initializable {
    @FXML
    private JFXButton volver_button;
    @FXML
    private JFXButton borrarCaso_button;
    @FXML
    private JFXButton nuevoCaso_button;
    @FXML
    private JFXButton seleccionarCaso_button;
    @FXML
    private Label nombreSujeto_label;

    private static User selected_user;

    private static SQLiteManager manager_object;
    private static Stage primary_Stage;

    @FXML
    private JFXTreeTableView<Casos> casos_treeView;
    @FXML
    private final ObservableList<Casos> casos_objects = FXCollections.observableArrayList();

    private static Stage stage;
    public static void setValues(Stage primaryStage, User user) {
        primary_Stage = primaryStage;
        selected_user=user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombreSujeto_label.setText("Casos de " + selected_user.getUserName());
        manager_object = new SQLiteManager();
        boolean everything_ok = manager_object.Connect();
        System.out.println(everything_ok);

        JFXTreeTableColumn<Casos, String> caso_id = new JFXTreeTableColumn<>("Id del Caso");
        caso_id.setPrefWidth(300);

        caso_id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Casos,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Casos, String> param) {
                return param.getValue().getValue().caso_id;
            }
        });
        caso_id.setResizable(false);

        JFXTreeTableColumn<Casos, String> nombre_caso = new JFXTreeTableColumn<>("Caso de estudio");
        nombre_caso.setPrefWidth(300);
        nombre_caso.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Casos,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Casos, String> param) {
                return param.getValue().getValue().nombre_caso;
            }
        });
        nombre_caso.setResizable(false);

        JFXTreeTableColumn<Casos, String> nombre_nodo = new JFXTreeTableColumn<>("Articulación estudiada");
        nombre_nodo.setPrefWidth(300);
        nombre_nodo.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Casos,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Casos, String> param) {
                return param.getValue().getValue().nombre_nodo;
            }
        });
        nombre_nodo.setResizable(false);

        JFXTreeTableColumn<Casos, String> notas = new JFXTreeTableColumn<>("Notas del caso");
        notas.setPrefWidth(300);
        notas.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Casos,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Casos, String> param) {
                return param.getValue().getValue().notas;
            }
        });
        notas.setResizable(false);


        List<Caso> casosList = manager_object.getMethods().List_all_casos_by_user(selected_user.getUserId());
        for (Caso caso: casosList) {
            casos_objects.add(new Casos(caso.getCaso_id().toString(), caso.getNombre(), caso.getNodo().getNombre(), caso.getNotas()));
        }
        TreeItem<Casos> root = new RecursiveTreeItem<Casos>(casos_objects, RecursiveTreeObject::getChildren);
        casos_treeView.setPlaceholder(new Label("No hay ningún caso para este sujeto"));
        casos_treeView.getColumns().setAll(caso_id, nombre_caso, nombre_nodo , notas);
        casos_treeView.setRoot(root);
        casos_treeView.setShowRoot(false);

    }
    public void refreshCasosListView() throws IOException {
        casosSujetoController.setValues(primary_Stage, selected_user);
        Parent root = FXMLLoader.load(getClass().getResource("casosSujetoView.fxml"));

        primary_Stage.setTitle("Casos Sujeto");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }
    public void volver(MouseEvent mouseEvent) throws IOException {
        historicoController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("historicoView.fxml"));

        primary_Stage.setTitle("Historico");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }



    private Caso getSelectedCaso() {

        String stringValue=String.valueOf(casos_treeView.getSelectionModel().getSelectedItem().getValue().caso_id);
        String id_string = stringValue.replaceAll("[^0-9]","");
        Integer caso_id = Integer.valueOf(id_string);
        Caso caso = manager_object.getMethods().Search_caso_by_id(caso_id);
        List<Sesion> sesionList = manager_object.getMethods().List_sesion_by_caso(caso.getCaso_id());
        caso.setSesionList((LinkedList<Sesion>) sesionList);
        return caso;
    }

    public void nuevoCaso(MouseEvent mouseEvent) throws IOException {
        User user = selected_user;
        caseConfigController.setValues(primary_Stage,user);
        Parent root = FXMLLoader.load(getClass().getResource("caseConfigView.fxml"));

        primary_Stage.setTitle("Case Configuration");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();

    }

    public void seleccionarCaso(MouseEvent mouseEvent) throws IOException {
        sesionesCasoController.setValues(primary_Stage, getSelectedCaso());
        Parent root = FXMLLoader.load(getClass().getResource("sesionesCasoView.fxml"));

        primary_Stage.setTitle("Sesiones del Caso");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    public void seleccionarCaso2(MouseEvent mouseEvent) throws IOException {


        if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
            sesionesCasoController.setValues(primary_Stage, getSelectedCaso());
            Parent root = FXMLLoader.load(getClass().getResource("sesionesCasoView.fxml"));

            primary_Stage.setTitle("Sesiones del Caso");
            Scene scene = new Scene(root);
            //primary_Stage.initStyle(StageStyle.UNDECORATED);
            primary_Stage.setScene(scene);
            stage = primary_Stage;
            primary_Stage.show();
        }


    }

    @FXML
    private void close_app(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }

    public void borrarCaso(MouseEvent mouseEvent) throws IOException {
        Caso caso = getSelectedCaso();
        manager_object.getMethods().Delete_caso(caso);
        refreshCasosListView();
    }



}
class Casos extends RecursiveTreeObject<Casos> {

    StringProperty caso_id;
    StringProperty nombre_caso;
    StringProperty nombre_nodo;
    StringProperty notas;

    public Casos(String caso_id, String nombre_caso, String nombre_nodo, String notas) {
        this.caso_id = new SimpleStringProperty(caso_id);
        this.nombre_caso = new SimpleStringProperty(nombre_caso);
        this.nombre_nodo = new SimpleStringProperty(nombre_nodo);
        this.notas = new SimpleStringProperty(notas);

    }
}