package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.LibraryStart;
import com.mmit.model.entity.Category;
import com.mmit.model.entity.DBHandler;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;

public class LibCategoryController implements Initializable{
	 	@FXML
	    private TableColumn<Category, Integer> col_id;

	    @FXML
	    private TableColumn<Category, String> col_name;

	    @FXML
	    private TableView<Category> tbl_category;

	    @FXML
	    private TextField txt_name;

	    @FXML
	    void btn_add_click(ActionEvent event) {
	    	try {
	    		if(txt_name.getText().isEmpty()) {
	    			showAlert(AlertType.ERROR, "Name is required ! ");
	    			return;
	    		}
	    		
		    	var obj = new Category();		    	
		    	obj.setName(txt_name.getText());
		    	
		    	DBHandler.saveCategory(obj);
		    	showAlert(AlertType.INFORMATION,"Success");
		    	resetData();
		    	showData();
		    	
			} catch (Exception e) {
				showAlert(AlertType.ERROR,e.getMessage());
			}
	    }
		private void resetData() {
	    	
	    	txt_name.setText(null);
			
		}

		private Optional<ButtonType> showAlert(AlertType type, String msg) {
	    	Alert alert = new Alert(type);
			alert.setContentText(msg);
			alert.setHeaderText(null);
			alert.setTitle("Message");
			return alert.showAndWait();
			
		}

		@FXML
	    void btn_back_click(ActionEvent event) throws IOException {
	    	LibraryStart.changeScene("view/LibraryStart.fxml","Library Management System");
	    }

	    @FXML
	    void btn_delete_click(ActionEvent event) throws SQLException {
	    	Optional<ButtonType> result = showAlert(AlertType.CONFIRMATION, "Are you sure to delete?");
	    	if(result.get() == ButtonType.OK) {
	    		var cat = tbl_category.getSelectionModel().getSelectedItem();
	    		DBHandler.deleteCategory(cat.getId());
	    		showData();
	    	}
	    }

	    @FXML
	    void btn_update_click(ActionEvent event) {
	    	try {
				var new_name = txt_name.getText();
				var cat = tbl_category.getSelectionModel().getSelectedItem();
				cat.setName(new_name);
   			    	
		    	DBHandler.updateCategory(cat);		    	
		    	showAlert(AlertType.INFORMATION, "Success");
		    	resetData();
		    	showData();
		    	
			} catch (Exception e) {
				showAlert(AlertType.ERROR, e.getMessage());
			}
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			try {
				col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
				col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
				
				showData();
				
				tbl_category.getSelectionModel().selectedItemProperty()
				.addListener(
						(obs,old_select,new_select)->{
							if(new_select != null) {
								var cat = tbl_category.getSelectionModel().getSelectedItem();
								txt_name.setText(cat.getName());
								
							}							
				});				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		private void showData() throws SQLException {
	    	List <Category> list = DBHandler.findAllCategory();			
			tbl_category.setItems(FXCollections.observableArrayList(list));
			
		}
}
