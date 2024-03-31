package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.LibraryStart;

import com.mmit.model.entity.DBHandler;

import com.mmit.model.entity.Librarian;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LibrarianController implements Initializable{
	 @FXML
	    private TableColumn<Librarian, String> col_email;

	    @FXML
	    private TableColumn<Librarian, Integer> col_id;

	    @FXML
	    private TableColumn<Librarian, String> col_nrc;

	    @FXML
	    private TableColumn<Librarian, Integer> col_phone;

	    @FXML
	    private TableView<Librarian> tbl_librarian;

	    @FXML
	    private TextField txt_email;

	    @FXML
	    private TextField txt_nrc;

	    @FXML
	    private PasswordField txt_password;

	    @FXML
	    private TextField txt_phone;

	    @FXML
	    void btn_home_click(ActionEvent event) throws IOException {
	    	LibraryStart.changeScene("view/LibraryStart.fxml","Library Management System");
	    }

	    @FXML
	    void btn_register_click(ActionEvent event) {
	    	try {
				var email = txt_email.getText();
				var password = txt_password.getText();
				var nrc = txt_nrc.getText();
				var phone = txt_phone.getText();
				
				if(email.isEmpty()) {
					showAlert(AlertType.ERROR,"Email is required!");
					return;
				}
				if(password.isEmpty()) {
					showAlert(AlertType.ERROR,"Password is required!");
					return;
				}
				if(nrc.isEmpty()) {
					showAlert(AlertType.ERROR,"NRC No. is required!");
					return;
				}
				if(phone.isEmpty()) {
					showAlert(AlertType.ERROR,"Phone is required!");
					return;
				}
				var librarian = new Librarian();
				librarian.setEmail(email);
				librarian.setPassword(password);
				librarian.setNrc(nrc);
				librarian.setPhone(Integer.parseInt(phone));
				DBHandler.registerLibrarian(librarian);
		    	showAlert(AlertType.INFORMATION, "Success!");
		    	resetForm();
		    	showData();
			}
	    	catch (Exception e) {
	    		e.printStackTrace();
				showAlert(AlertType.ERROR, e.getMessage());
			}
	    }

		private void showData() throws SQLException {
			List <Librarian> list = DBHandler.findAllLibrarian();
			
			tbl_librarian.setItems(FXCollections.observableArrayList(list));
			
		}

		private void resetForm() {
			txt_email.setText(null);
			txt_password.setText(null);
			txt_nrc.setText(null);
			txt_phone.setText(null);
			
		}
		

		private Optional<ButtonType> showAlert(AlertType type, String msg) {
			Alert alert = new Alert(type);
			alert.setContentText(msg);
			alert.setHeaderText(null);
			alert.setTitle("Message");
			
			return alert.showAndWait();
			
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			try {
				col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
				col_nrc.setCellValueFactory(new PropertyValueFactory<>("nrc"));
				col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
				col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
				
				showData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

}
