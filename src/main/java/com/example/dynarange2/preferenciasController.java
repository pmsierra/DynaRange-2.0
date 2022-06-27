package com.example.dynarange2;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class preferenciasController implements Initializable {

    @FXML
    private JFXButton establecerPref_button;
    @FXML
    private JFXButton volver_button;
    @FXML
    private TextField carpeta_textField;

    private static Stage primary_Stage;

    private File folderPath;

    private FileWriter fileWriter;
    private FileReader fileReader;
    private BufferedReader reader;

    private static Stage stage;
    public static void setValues(Stage primaryStage) {
        primary_Stage = primaryStage;

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL url = getClass().getResource("C:\\Users\\pmsie\\git\\DynaRange2\\src\\main\\resources\\com\\example\\dynarange2\\folderPath.txt");
        File file = new File("C:\\Users\\pmsie\\git\\DynaRange2\\src\\main\\resources\\com\\example\\dynarange2\\folderPath.txt");
        folderPath = new File(file.getPath());


        try {
            fileReader =new FileReader(folderPath);
            reader=new BufferedReader(fileReader);
            String currentPath = reader.readLine();
            carpeta_textField.setText(currentPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        establecerPref_button.setOnAction((ActionEvent event) -> {
            File newFolderPath = new File(url.getPath());
            String newPath = carpeta_textField.getText();
            folderPath.delete();


            try {
                fileWriter = new FileWriter(newFolderPath);
                fileWriter.write(newPath);
                fileWriter.close();
                toMainMenu();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }





        });
    }

    @FXML
    private void close_app(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
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

    public void toMainMenu() throws IOException {
        mainPaneController.setValues(primary_Stage);
        Parent root = FXMLLoader.load(getClass().getResource("mainPaneView.fxml"));

        primary_Stage.setTitle("Registration Page");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    public void establecer_preferencias(MouseEvent mouseEvent) {
    }
}
