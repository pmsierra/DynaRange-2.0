package com.example.dynarange2;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dbManagement.Caso;
import dbManagement.SQLiteManager;
import dbManagement.Sesion;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class medicionesSesionController implements Initializable {
    @FXML
    private Label rangos_label;
    @FXML
    private Label extension_label;
    @FXML
    private Label flexion_label;
    @FXML
    private Label extension2_label;
    @FXML
    private Label flexion2_label;
    @FXML
    private Label idSesion_label;
    @FXML
    private JFXTreeTableView<Mediciones> mediciones_treeView;
    @FXML
    private final ObservableList<Mediciones> mediciones_objects = FXCollections.observableArrayList();
    private static Sesion selected_sesion;
    private static Stage primary_Stage;
    private static Stage stage;
    private static SQLiteManager manager;

    @FXML
    private ImageView plot_view;

    public static void setValues(Stage primaryStage, SQLiteManager manager_object, Sesion sesion) {
        manager=manager_object;
        selected_sesion=sesion;
        primary_Stage = primaryStage;

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idSesion_label.setText("Mediciones de la Sesión: " + selected_sesion.getSesion_id());

        JFXTreeTableColumn<Mediciones, String> flexion = new JFXTreeTableColumn<Mediciones, String>("Extensión");
        JFXTreeTableColumn<Mediciones, String> extension = new JFXTreeTableColumn<Mediciones, String>("Flexión");

        JFXTreeTableColumn<Mediciones, String> FAMáx = new JFXTreeTableColumn<>("AMáx");
        FAMáx.setPrefWidth(176);

        FAMáx.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Mediciones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Mediciones, String> param) {
                return param.getValue().getValue().FAMáx;
            }
        });
        FAMáx.setResizable(false);

        JFXTreeTableColumn<Mediciones, String> FAMín = new JFXTreeTableColumn<>("AMín");
        FAMín.setPrefWidth(176);

        FAMín.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Mediciones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Mediciones, String> param) {
                return param.getValue().getValue().FAMín;
            }
        });
        FAMín.setResizable(false);

        JFXTreeTableColumn<Mediciones, String> FAMed = new JFXTreeTableColumn<>("AMed");
        FAMed.setPrefWidth(176);

        FAMed.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Mediciones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Mediciones, String> param) {
                return param.getValue().getValue().FAMed;
            }
        });
        FAMed.setResizable(false);


        JFXTreeTableColumn<Mediciones, String> EAMáx = new JFXTreeTableColumn<>("AMáx");
        EAMáx.setPrefWidth(176);
        EAMáx.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Mediciones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Mediciones, String> param) {
                return param.getValue().getValue().EAMáx;
            }
        });
        EAMáx.setResizable(false);

        JFXTreeTableColumn<Mediciones, String> EAMín = new JFXTreeTableColumn<>("AMin");
        EAMín.setPrefWidth(176);
        EAMín.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Mediciones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Mediciones, String> param) {
                return param.getValue().getValue().EAMín;
            }
        });
        EAMín.setResizable(false);

        JFXTreeTableColumn<Mediciones, String> EAMed = new JFXTreeTableColumn<>("AMed");
        EAMed.setPrefWidth(176);
        EAMed.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Mediciones,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Mediciones, String> param) {
                return param.getValue().getValue().EAMed;
            }
        });
        EAMed.setResizable(false);

        flexion.getColumns().addAll(FAMáx, FAMín, FAMed);
        extension.getColumns().addAll(EAMáx, EAMín, EAMed);

        File outputPlot = new File(selected_sesion.getPath()+"/outputFig.png");
        File results = new File(selected_sesion.getPath()+"/results.txt");
        List<Float> numericResults = readResults(results);

        plot_view.setImage(new Image(outputPlot.toURI().toString()));

        mediciones_objects.add(new Mediciones(numericResults.get(0).toString(), numericResults.get(2).toString(), numericResults.get(1).toString(), numericResults.get(3).toString(), numericResults.get(5).toString(), numericResults.get(4).toString()));
        
        
        TreeItem<Mediciones> root = new RecursiveTreeItem<Mediciones>(mediciones_objects, RecursiveTreeObject::getChildren);
        mediciones_treeView.setPlaceholder(new Label("No hay ningún caso para este sujeto"));
        mediciones_treeView.getColumns().setAll(flexion, extension);
        mediciones_treeView.setRoot(root);
        mediciones_treeView.setShowRoot(false);

    }
    public void refreshSesionListView() {
        File outputPlot = new File(selected_sesion.getPath()+"/outputFig.png");
        File results = new File(selected_sesion.getPath()+"/results.txt");
        List<Float> numericResults = readResults(results);

        plot_view.setImage(new Image(outputPlot.toURI().toString()));

        mediciones_objects.add(new Mediciones(numericResults.get(0).toString(), numericResults.get(1).toString(), numericResults.get(2).toString(), numericResults.get(3).toString(), numericResults.get(4).toString(), numericResults.get(5).toString()));


        TreeItem<Mediciones> root = new RecursiveTreeItem<Mediciones>(mediciones_objects, RecursiveTreeObject::getChildren);
        
        mediciones_treeView.refresh();
    }

    private List<Float> readResults(File file){
        List<Float> results = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String readLine =bufferedReader.readLine();
            String maxPeakFString = readLine.replaceAll("Fmax: ","");
            Float maxPeakF = Float.valueOf(maxPeakFString);
            results.add(maxPeakF);

            readLine =bufferedReader.readLine();
            String minPeakFString = readLine.replaceAll("Fmin: ","");
            Float minPeakF = Float.valueOf(minPeakFString);
            results.add(minPeakF);

            readLine =bufferedReader.readLine();
            String avgPeakFString = readLine.replaceAll("Favg: ","");
            Float avgPeakF = Float.valueOf(avgPeakFString);
            results.add(avgPeakF);

            readLine =bufferedReader.readLine();
            String maxPeakEString = readLine.replaceAll("Emax: ","");
            Float maxPeakE = Float.valueOf(maxPeakEString);
            results.add(maxPeakE);

            readLine =bufferedReader.readLine();
            String minPeakEString = readLine.replaceAll("Emin: ","");
            Float minPeakE = Float.valueOf(minPeakEString);
            results.add(minPeakE);

            readLine =bufferedReader.readLine();
            String avgPeakEString = readLine.replaceAll("Eavg: ","");
            Float avgPeakE = Float.valueOf(avgPeakEString);
            results.add(avgPeakE);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public void volver(MouseEvent mouseEvent) throws IOException {
        Caso caso = selected_sesion.getCaso();
        sesionesCasoController.setValues(primary_Stage, caso);
        Parent root = FXMLLoader.load(getClass().getResource("sesionesCasoView.fxml"));

        primary_Stage.setTitle("Casos Sujeto");
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
class Mediciones extends RecursiveTreeObject<Mediciones> {

    StringProperty EAMáx;
    StringProperty EAMed;
    StringProperty EAMín;

    StringProperty FAMáx;
    StringProperty FAMed;
    StringProperty FAMín;

    public Mediciones(String EAMáx, String EAMed, String EAMín,String FAMáx, String FAMed, String FAMín) {
        this.EAMáx = new SimpleStringProperty(EAMáx);
        this.EAMed = new SimpleStringProperty(EAMed);
        this.EAMín = new SimpleStringProperty(EAMín);
        this.FAMáx = new SimpleStringProperty(FAMáx);
        this.FAMed = new SimpleStringProperty(FAMed);
        this.FAMín = new SimpleStringProperty(FAMín);

    }
}
