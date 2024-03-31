package com.mmit;
	
import java.io.IOException;

import com.mmit.model.entity.Librarian;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class LibraryStart extends Application {
	static Stage original_Stage; 
	public static Librarian login_user;
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("view/Main.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Library Management System");
			primaryStage.show();
			original_Stage = primaryStage;
			original_Stage.setResizable(false);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeScene(String input_file, String title) throws IOException {
		original_Stage.hide();
		Parent root = FXMLLoader.load(LibraryStart.class.getResource(input_file));
		Scene scene = new Scene(root);
		original_Stage.setScene(scene);
		original_Stage.setTitle(title);
		original_Stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
