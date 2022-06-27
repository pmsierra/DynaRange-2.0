package com.example.dynarange2;

import dbManagement.SQLiteManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LaunchApp extends Application {
	
	private static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		SQLiteManager manager = new SQLiteManager();
		//manager.Stablish_connection();
        //manager.Create_tables();
        //manager.Close_connection();
        //manager = null;
		
		try {
			
			mainPaneController.setValues(primaryStage);
			Parent root = FXMLLoader.load(getClass().getResource("mainPaneView.fxml"));
			primaryStage.setTitle("Main page");
			Scene scene = new Scene(root);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			stage = primaryStage;
			primaryStage.show();
		} catch (IOException fatal_error) {
			fatal_error.printStackTrace();
			System.exit(0);
		}
	}
	
	

	public static void main(String[] args) {
		
		launch(args);
	}
	
	public static Stage getStage() {
		return stage;
	}
}
