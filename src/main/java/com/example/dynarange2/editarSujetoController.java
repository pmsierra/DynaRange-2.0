package com.example.dynarange2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dbManagement.SQLiteManager;
import dbManagement.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class editarSujetoController implements Initializable {

    private SQLiteManager SQL_manager_object;
    @FXML
    private JFXButton modificarSujeto_button;
    @FXML
    private JFXButton volver_button;
    @FXML
    private TextField nombre_textField;
    @FXML
    private TextField edad_textField;
    @FXML
    private JFXComboBox<String> sexo_comboBox;
    private static Stage primary_Stage;
    private static User selected_user;

    private static Stage stage;
    public static void setValues(Stage primaryStage, User user) {
        primary_Stage = primaryStage;
        selected_user = user;

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sexo_comboBox.getItems().add("Hombre");
        sexo_comboBox.getItems().add("Mujer");
        sexo_comboBox.getItems().add("NA");

        nombre_textField.setText(selected_user.getUserName());
        edad_textField.setText(selected_user.getEdad().toString());
        String obtained_sex = selected_user.getSexo().toString().toLowerCase();
        String formated_sex = obtained_sex.substring(0, 1).toUpperCase() + obtained_sex.substring(1);
        sexo_comboBox.setValue(formated_sex);

        modificarSujeto_button.setOnAction((ActionEvent event) -> {




            SQL_manager_object = new SQLiteManager();
            boolean everything_ok = SQL_manager_object.Connect();
            System.out.println(everything_ok);
            try {
                // Code to open charging window
                if (!(nombre_textField.getText().equals(""))){
                    String user_name = nombre_textField.getText();
                    if (!(edad_textField.getText().equals(""))){
                        Integer edad = Integer.valueOf(edad_textField.getText());
                        if (!(sexo_comboBox.getSelectionModel().getSelectedItem().equals(null))){
                            String sexo = sexo_comboBox.getSelectionModel().getSelectedItem();
                            selected_user.setUserName(user_name);
                            selected_user.setEdad(edad);
                            selected_user.setSexo(sexo.toUpperCase());
                            SQL_manager_object.getMethods().Update_user(selected_user);

                            cargarHistorico();

                        }
                        else{
                            sexo_comboBox.setPromptText("Por favor seleccione este campo");
                        }
                    }
                    else{
                        edad_textField.setPromptText("Por favor rellene este campo");
                    }
                }
                else{
                    nombre_textField.setPromptText("Por favor rellene este campo");
                }




            } catch (Exception error_occur) {
                error_occur.printStackTrace();
                SQL_manager_object.Close_connection();
            }


        });
    }

    @FXML
    private void close_app(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }

    public void modificar_sujeto(MouseEvent mouseEvent) {


    }

    private void cargarHistorico () throws IOException {
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
        historicoController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("historicoView.fxml"));

        primary_Stage.setTitle("Historico");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

}