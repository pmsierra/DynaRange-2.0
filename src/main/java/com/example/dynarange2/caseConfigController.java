package com.example.dynarange2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import dbManagement.Caso;
import dbManagement.Nodo;
import dbManagement.SQLiteManager;
import dbManagement.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class caseConfigController  implements Initializable {

    private static User current_user;
    @FXML
    private Label nombreNodo_label;

    @FXML
    private JFXTextArea nombre_textArea;

    @FXML
    private JFXTextArea notas_textArea;

    @FXML
    private JFXButton guardarCaso_button;

    @FXML
    private JFXButton volver_button;

    @FXML
    private ImageView munecaDchNode;

    @FXML
    private ImageView codoDchNode;

    @FXML
    private ImageView hombroDchNode;

    @FXML
    private ImageView cervicalNode;

    @FXML
    private ImageView hombroIzqNode;

    @FXML
    private ImageView codoIzqNode;

    @FXML
    private ImageView munecaIzqNode;

    @FXML
    private ImageView lumbarNode;

    @FXML
    private ImageView caderaDchNode;

    @FXML
    private ImageView caderaIzqNode;

    @FXML
    private ImageView rodillaDchNode;

    @FXML
    private ImageView tobilloDchNode;

    @FXML
    private ImageView rodillaIzqNode;

    @FXML
    private ImageView tobilloIzqNode;

    private static Stage primary_Stage;

    private static Stage stage;

    private SQLiteManager SQL_manager_object;

    private Nodo nodo;
    public static void setValues(Stage primaryStage, User user) {
        primary_Stage = primaryStage;
        current_user=user;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void volver(MouseEvent mouseEvent) throws IOException {

        casosSujetoController.setValues(primary_Stage, current_user);
        Parent root = FXMLLoader.load(getClass().getResource("casosSujetoView.fxml"));

        primary_Stage.setTitle("Casos Sujeto");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }


    public void guardarCaso(MouseEvent mouseEvent) throws IOException {
        SQL_manager_object = new SQLiteManager();
        boolean everything_ok = SQL_manager_object.Connect();
        System.out.println(everything_ok);
        String nombre = nombre_textArea.getText();

        String notas = notas_textArea.getText();
        Nodo nodo = SQL_manager_object.getMethods().Search_nodo_by_name(nombreNodo_label.getText());
        Caso caso = new Caso(nombre,nodo,current_user,notas);
        SQL_manager_object.getMethods().Insertar_nuevo_caso(caso);
        abrirSesionesCaso(caso);

    }

    private void abrirSesionesCaso(Caso caso) throws IOException {
        sesionesCasoController.setValues(primary_Stage, caso);
        Parent root = FXMLLoader.load(getClass().getResource("sesionesCasoView.fxml"));

        primary_Stage.setTitle("Sesiones de Caso");
        Scene scene = new Scene(root);
        //primary_Stage.initStyle(StageStyle.UNDECORATED);
        primary_Stage.setScene(scene);
        stage = primary_Stage;
        primary_Stage.show();
    }

    public void munecaDchSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Muñeca - Derecha");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        munecaDchNode.setImage(image);

    }
    public void codoDchSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Codo - Derecha");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        codoDchNode.setImage(image);
    }
    public void hombroDchSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Hombro - Derecha");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        hombroDchNode.setImage(image);
    }
    public void cervicalSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Cervical");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        cervicalNode.setImage(image);
    }
    public void hombroIzqSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Hombro - Izquierda");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        hombroIzqNode.setImage(image);
    }
    public void codoIzqSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Codo - Izquierda");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        codoIzqNode.setImage(image);
    }
    public void munecaIzqSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Muñeca - Izquierda");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        munecaIzqNode.setImage(image);
    }
    public void lumbarSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Lumbar");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        lumbarNode.setImage(image);
    }
    public void caderaDchSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Cadera - Derecha");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        caderaDchNode.setImage(image);
    }
    public void caderaIzqSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Cadera - Izquierda");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        caderaIzqNode.setImage(image);
    }
    public void rodillaDchSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Rodilla - Derecha");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        rodillaDchNode.setImage(image);
    }
    public void tobilloDchSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Tobillo - Derecha");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        tobilloDchNode.setImage(image);
    }
    public void rodillaIzqSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Rodilla - Izquierda");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        rodillaIzqNode.setImage(image);
    }
    public void tobilloIzqSelection(MouseEvent mouseEvent) throws IOException {
        nombreNodo_label.setText("Tobillo - Izquierda");
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/yellowDot.png"));
        setAllNodesUnselected();
        tobilloIzqNode.setImage(image);
    }
    private void setAllNodesUnselected(){
        Image image = new Image(getClass().getResourceAsStream("src.IconPictures/dot.png"));
        munecaDchNode.setImage(image);
        codoDchNode.setImage(image);
        hombroDchNode.setImage(image);
        cervicalNode.setImage(image);
        hombroIzqNode.setImage(image);
        codoIzqNode.setImage(image);
        munecaIzqNode.setImage(image);
        lumbarNode.setImage(image);
        caderaDchNode.setImage(image);
        caderaIzqNode.setImage(image);
        rodillaDchNode.setImage(image);
        tobilloDchNode.setImage(image);
        rodillaIzqNode.setImage(image);
        tobilloIzqNode.setImage(image);
    }
    @FXML
    private void close_app(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }
}
