package com.example.dynarange2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dbManagement.SQLiteManager;
import dbManagement.User;
import com.example.dynarange2.caseConfigController;
import com.example.dynarange2.mainPaneController;
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

public class addSujetoController implements Initializable {

    private SQLiteManager SQL_manager_object;
    @FXML
    private JFXButton altaSujeto_button;
    @FXML
    private JFXButton volver_button;
    @FXML
    private TextField nombre_textField;
    @FXML
    private TextField edad_textField;
    @FXML
    private JFXComboBox<String> sexo_comboBox;
    private static Stage primary_Stage;

    private static Stage stage;
    public static void setValues(Stage primaryStage) {
        primary_Stage = primaryStage;

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sexo_comboBox.getItems().add("Hombre");
        sexo_comboBox.getItems().add("Mujer");
        sexo_comboBox.getItems().add("NA");

        altaSujeto_button.setOnAction((ActionEvent event) -> {




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
                            User user = new User(user_name,edad,sexo.toUpperCase());
                            SQL_manager_object.getMethods().Insertar_nuevo_usuario(user);
                            cargarCasoConfig(user);

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


    private void cargarCasoConfig (User user) throws IOException {
        caseConfigController.setValues(primary_Stage,user);
        Parent root = FXMLLoader.load(getClass().getResource("caseConfigView.fxml"));

        primary_Stage.setTitle("Case Configuration");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }



    public void volver(MouseEvent mouseEvent) throws IOException {
        mainPaneController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("mainPaneView.fxml"));

        primary_Stage.setTitle("Registration Page");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }



    public void alta_sujeto(MouseEvent mouseEvent) {
    }
}
