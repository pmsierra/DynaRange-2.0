package com.example.dynarange2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dbManagement.SQLiteManager;
import dbManagement.User;
import com.example.dynarange2.addSujetoController;
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
import java.util.List;
import java.util.ResourceBundle;

public class historicoController implements Initializable {
    @FXML
    private JFXButton volver_button;
    @FXML
    private JFXButton borrarSujeto_button;
    @FXML
    private JFXButton editarSujeto_button;
    @FXML
    private JFXButton nuevoSujeto_button;
    @FXML
    private JFXButton seleccionarSujeto_button;

    private static SQLiteManager manager_object;
    private static Stage primary_Stage;

    @FXML
    private JFXTreeTableView<Users> historico_treeView;
    @FXML
    private ObservableList<Users> users_objects = FXCollections.observableArrayList();

    private static Stage stage;
    public static void setValues(Stage primaryStage) {
        primary_Stage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager_object = new SQLiteManager();
        boolean everything_ok = manager_object.Connect();
        System.out.println(everything_ok);

        JFXTreeTableColumn<Users, String> user_id = new JFXTreeTableColumn<>("Id del Sujeto");
        user_id.setPrefWidth(300);

        user_id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Users,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Users, String> param) {
                return param.getValue().getValue().user_id;
            }
        });
        user_id.setResizable(false);

        JFXTreeTableColumn<Users, String> user_name = new JFXTreeTableColumn<>("Nombre del Sujeto");
        user_name.setPrefWidth(300);
        user_name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Users,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Users, String> param) {
                return param.getValue().getValue().user_name;
            }
        });
        user_name.setResizable(false);

        JFXTreeTableColumn<Users, String> user_age = new JFXTreeTableColumn<>("Edad del Sujeto");
        user_age.setPrefWidth(300);
        user_age.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Users,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Users, String> param) {
                return param.getValue().getValue().user_age;
            }
        });
        user_age.setResizable(false);

        JFXTreeTableColumn<Users, String> user_sex = new JFXTreeTableColumn<>("Sexo del Sujeto");
        user_sex.setPrefWidth(300);
        user_sex.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Users,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Users, String> param) {
                return param.getValue().getValue().user_sex;
            }
        });
        user_sex.setResizable(false);

        List<User> usersList = manager_object.getMethods().List_all_users();
        for (User user: usersList) {
            users_objects.add(new Users(user.getUserId().toString(), user.getUserName(), user.getEdad().toString(), user.getSexo().toString()));
        }
        TreeItem<Users> root = new RecursiveTreeItem<Users>(users_objects, RecursiveTreeObject::getChildren);
        historico_treeView.setPlaceholder(new Label("No hay ningún sujeto en la base de datos"));
        historico_treeView.getColumns().setAll(user_id, user_name, user_age, user_sex);
        historico_treeView.setRoot(root);
        historico_treeView.setShowRoot(false);

    }
    public void refreshUserListView() throws IOException {
        historicoController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("historicoView.fxml"));

        primary_Stage.setTitle("Historico");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }
    public void volver(MouseEvent mouseEvent) throws IOException {
        mainPaneController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("mainPaneView.fxml"));

        primary_Stage.setTitle("Main Pane");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    public void editarSujeto(MouseEvent mouseEvent) throws IOException {
        User user = getSelectedUser();
        editarSujetoController.setValues(primary_Stage, user);
        Parent root = FXMLLoader.load(getClass().getResource("editarSujetoView.fxml"));

        primary_Stage.setTitle("Editar Sujeto");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    private User getSelectedUser() {

        String stringValue=String.valueOf(historico_treeView.getSelectionModel().getSelectedItem().getValue().user_id);
        String id_string = stringValue.replaceAll("[^0-9]","");
        Integer user_id = Integer.valueOf(id_string);
        System.out.println(user_id);
        User user = manager_object.getMethods().Search_user_by_id(user_id);
        return user;
    }

    public void borrarSujeto(MouseEvent mouseEvent) throws IOException {
        User user = getSelectedUser();
        manager_object.getMethods().Delete_user(user);
        refreshUserListView();
    }

    public void nuevoSujeto(MouseEvent mouseEvent) throws IOException {
        addSujetoController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("addSujetoView.fxml"));

        primary_Stage.setTitle("Add Sujeto");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();

    }

    public void seleccionarSujeto(MouseEvent mouseEvent) throws IOException {
        casosSujetoController.setValues(primary_Stage, getSelectedUser());
        Parent root = FXMLLoader.load(getClass().getResource("casosSujetoView.fxml"));

        primary_Stage.setTitle("Casos Sujeto");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();

    }
    public void seleccionarSujeto2(MouseEvent mouseEvent) throws IOException {


        if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
            casosSujetoController.setValues(primary_Stage, getSelectedUser());
            Parent root = FXMLLoader.load(getClass().getResource("casosSujetoView.fxml"));

            primary_Stage.setTitle("Casos Sujeto");
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
}
class Users extends RecursiveTreeObject<Users> {

    StringProperty user_id;
    StringProperty user_name;
    StringProperty user_age;
    StringProperty user_sex;

    public Users(String user_id, String user_name, String user_age, String user_sex) {
        this.user_id = new SimpleStringProperty(user_id);
        this.user_name = new SimpleStringProperty(user_name);
        this.user_age = new SimpleStringProperty(user_age);
        this.user_sex = new SimpleStringProperty(user_sex);

    }
}