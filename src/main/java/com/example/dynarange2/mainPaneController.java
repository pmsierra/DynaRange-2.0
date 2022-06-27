package com.example.dynarange2;

import com.jfoenix.controls.JFXButton;
import dbManagement.SQLiteManager;
import com.example.dynarange2.addSujetoController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainPaneController implements Initializable {

    private SQLiteManager SQL_manager_object;
    @FXML
    private JFXButton nuevo_sujeto;
    @FXML
    private JFXButton historico_sujeto;
    private static Stage primary_Stage;

    private static Stage stage;
    public static void setValues(Stage primaryStage) {
        primary_Stage = primaryStage;

    }


    @FXML
    private void abrir_historico(MouseEvent mouseEvent)throws IOException {
        historicoController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("historicoView.fxml"));

        primary_Stage.setTitle("Historico");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    @FXML
    private void crear_sujeto(MouseEvent mouseEvent) throws IOException {
        addSujetoController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("addSujetoView.fxml"));

        primary_Stage.setTitle("Add Sujeto");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    @FXML
    private void close_app(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void abrirPreferencias(MouseEvent mouseEvent) throws IOException {
        preferenciasController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("preferenciasView.fxml"));

        primary_Stage.setTitle("Preferencias");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }
}
