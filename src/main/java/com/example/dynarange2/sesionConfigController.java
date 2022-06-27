package com.example.dynarange2;

import com.jfoenix.controls.JFXButton;
import dbManagement.Caso;
import dbManagement.SQLiteManager;
import dbManagement.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class sesionConfigController implements Initializable {
    @FXML
    private JFXButton volver_button;
    @FXML
    private JFXButton pararMedicion_button;
    @FXML
    private JFXButton empezarMedicion_button;
    @FXML
    private Label sesionNumber_label;
    private static SQLiteManager manager_object;
    private static Stage primary_Stage;

    private static Caso selected_caso;

    private Integer sesionId=0;

    private static Stage stage;
    private String finalPath;
    public static void setValues(Stage primaryStage, Caso caso, SQLiteManager manager) {
        primary_Stage = primaryStage;
        selected_caso=caso;
        manager_object=manager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Integer currentId = 1;
        if(selected_caso.getSesionList().isEmpty() == false){
            currentId=selected_caso.getSesionList().getLast().getSesion_id()+1;
        }
        sesionNumber_label.setText("Sesión " + currentId);
        sesionId=currentId;

    }
    private void executeCommand() throws IOException {


    }
    public void volver(MouseEvent mouseEvent) throws IOException {

        sesionesCasoController.setValues(primary_Stage, selected_caso);
        Parent root = FXMLLoader.load(getClass().getResource("sesionesCasoView.fxml"));

        primary_Stage.setTitle("Sesiones Caso");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    public void empezarMedicion(MouseEvent mouseEvent) throws IOException, InterruptedException {
        String workingDir = System.getProperty("user.dir");
        URL url = getClass().getResource(workingDir+"\\src\\main\\resources\\com\\example\\dynarange2\\folderPath.txt");
        System.out.println(url);

        System.out.println("myworking" + workingDir);
        String fullPath =workingDir+"\\classes\\com\\example\\dynarange2\\folderPath.txt";



        File folderPath = new File(fullPath);


        String currentPath;
        try {
            FileReader fileReader =new FileReader(folderPath);
            BufferedReader reader=new BufferedReader(fileReader);
            currentPath = reader.readLine();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String buildPath = currentPath.replace("mediciones", "");
        //buildPath = buildPath + "build\\bin";
        String cdToPath = "cd " + buildPath;

        File sesionFolder = new File(currentPath + "\\" + selected_caso.getUser().getUserName() +"\\"+sesionId);
        //String command = cdToPath + " && python mediciones.py --write_json " + currentPath  + selected_caso.getUser().getUserName() +"\\"+sesionId;
        String command = cdToPath + " && OpenPoseDemo.exe --write_json " + currentPath  + "\\" + selected_caso.getUser().getUserName() +"\\"+sesionId;
        System.out.println(command);
        //smthlabel.setText(command);
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        while(p.isAlive()) {
            //System.out.println("vivo");
        }
        //TimeUnit.SECONDS.sleep(1);
        //pararMedicion2();

    }

    public void pararMedicion(MouseEvent mouseEvent) throws IOException {
        String workingDir = System.getProperty("user.dir");
        URL url = getClass().getResource(workingDir+"\\src\\main\\resources\\com\\example\\dynarange2\\folderPath.txt");

        String fullPath =workingDir+"\\classes\\com\\example\\dynarange2\\folderPath.txt";
        File folderPath = new File(fullPath);

        String currentPath;
        try {
            FileReader fileReader =new FileReader(folderPath);
            BufferedReader reader=new BufferedReader(fileReader);
            currentPath = reader.readLine();
            System.out.println(currentPath);
            fileReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String buildPath = currentPath.replace("\\mediciones", "");
        //buildPath = buildPath + "build\\bin";
        String cdToPath = "cd " + buildPath;
        File sesionFolder = new File(currentPath + "\\" + selected_caso.getUser().getUserName() +"\\"+sesionId);
        finalPath = currentPath  +"\\" + selected_caso.getUser().getUserName() +"\\"+sesionId;

        String command = cdToPath + " && python EscrituraDatos.py " + finalPath;
        System.out.println(command);
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        loadResultadoSesion();
    }
    public void pararMedicion2() throws IOException, InterruptedException {
        URL url = getClass().getResource("C:\\Users\\pmsie\\git\\DynaRange2\\src\\main\\resources\\com\\example\\dynarange2\\folderPath.txt");
        File folderPath = new File("C:\\Users\\pmsie\\git\\DynaRange2\\src\\main\\resources\\com\\example\\dynarange2\\folderPath.txt");
        String currentPath;
        try {
            FileReader fileReader =new FileReader(folderPath);
            BufferedReader reader=new BufferedReader(fileReader);
            currentPath = reader.readLine();

            fileReader.close();
            reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String buildPath = currentPath.replace("mediciones\\", "");
        buildPath = buildPath + "build\\bin";
        String cdToPath = "cd " + buildPath;
        finalPath = currentPath  + selected_caso.getUser().getUserName() +"\\"+sesionId;

        File sesionFolder = new File(currentPath + "\\" + selected_caso.getUser().getUserName() +"\\"+sesionId);
        String command = cdToPath + " && python mediciones.py --write_json " + finalPath;
        System.out.println(command);
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        TimeUnit.SECONDS.sleep(3);
        loadResultadoSesion();
    }
    private void loadResultadoSesion() throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String fecha = now.toString();

        Sesion sesion = new Sesion (sesionId, finalPath, selected_caso, fecha);
        manager_object.getMethods().Insertar_nueva_sesion(sesion);
        medicionesSesionController.setValues(primary_Stage, manager_object, sesion);
        Parent root = FXMLLoader.load(getClass().getResource("medicionesSesionView.fxml"));

        primary_Stage.setTitle("Mediciones de Sesion");
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
}
