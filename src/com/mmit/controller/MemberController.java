package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.LibraryStart;
import com.mmit.model.entity.DBHandler;

import com.mmit.model.entity.Member;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class MemberController implements Initializable{

	    @FXML
	    private TableColumn<Member, Integer> col_academicYear;

	    @FXML
	    private TableColumn<Member, Integer> col_cardId;

	    @FXML
	    private TableColumn<Member, LocalDate> col_createdDate;

	    @FXML
	    private TableColumn<Member, LocalDate> col_expiredDate;

	    @FXML
	    private TableColumn<Member, String> col_name;

	    @FXML
	    private TableColumn<Member, Integer> col_rollNo;

	    @FXML
	    private TableColumn<Member, String> col_year;

	    @FXML
	    private TableView<Member> tbl_member;

	    @FXML
	    private TextField txt_academicYear;

	    @FXML
	    private DatePicker txt_createdDate;

	    @FXML
	    private TextField txt_name;

	    @FXML
	    private TextField txt_rollNo;
	    
	    @FXML
	    private TextField txt_year;
	    
	    
	   private Member selected_member = null;
	   
	   @FXML
	    void btn_home_click(ActionEvent event) throws IOException {
	    	LibraryStart.changeScene("view/LibraryStart.fxml","Library Management System");
	    }

	    @FXML
	    void btn_register_cllck(ActionEvent event) {
	    	try {
	    		if(txt_name.getText().isEmpty()) {
	    			showAlert(AlertType.ERROR, "Name is required ! ");
	    			return;
	    		}
	    		if(txt_rollNo.getText().isEmpty()) {
	    			showAlert(AlertType.ERROR, "Roll No. is required ! ");
	    			return;
	    		}
	    		if(txt_year.getText().isEmpty()) {
	    			showAlert(AlertType.ERROR, "Year is required ! ");
	    			return;
	    		}
	    		if(txt_academicYear.getText().isEmpty()) {
	    			showAlert(AlertType.ERROR, "Academic year is required ! ");
	    			return;
	    		}
	    		if(txt_createdDate.getValue() == null) {
	    			showAlert(AlertType.ERROR, "Created Date is required ! ");
	    			return;
	    		}
	    		
		    	var obj = new Member();	 
		    	var createdDate = txt_createdDate.getValue();
		    	var expiredDate = createdDate.plusYears(1);
		    	obj.setName(txt_name.getText());
		    	obj.setRollNo(Integer.parseInt(txt_rollNo.getText()));
		    	obj.setYear(txt_year.getText());
		    	obj.setAcademicYear(Integer.parseInt(txt_academicYear.getText()));
		    	obj.setCreatedDate(txt_createdDate.getValue());
		    	obj.setExpiredDate(expiredDate);
		    	
		    	DBHandler.saveMember(obj);
		    	showAlert(AlertType.INFORMATION,"Success");
		    	resetData();
		    	showData();
		    	
			} catch (Exception e) {
				showAlert(AlertType.ERROR,e.getMessage());
			}
	    }

		@FXML
	    void btn_update_click(ActionEvent event) {
			try {
				var createdDate = txt_createdDate.getValue();
		    	var expiredDate = createdDate.plusYears(1);
		    	selected_member.setName(txt_name.getText());  	   	
		    	selected_member.setRollNo(Integer.parseInt(txt_rollNo.getText()));		    	
		    	selected_member.setYear(txt_year.getText());
		    	selected_member.setAcademicYear(Integer.parseInt(txt_academicYear.getText()));		    	
		    	selected_member.setCreatedDate(txt_createdDate.getValue());
		    	selected_member.setExpiredDate(expiredDate);		    	
		    	DBHandler.updateMember(selected_member);		    	
		    	showAlert(AlertType.INFORMATION, "Success");
		    	resetData();
		    	showData();
		    	
			} catch (Exception e) {
				showAlert(AlertType.ERROR, e.getMessage());
			}
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
				col_cardId.setCellValueFactory(new PropertyValueFactory<>("cardId"));
				col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
				col_rollNo.setCellValueFactory(new PropertyValueFactory<>("rollNo"));
				col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
				col_academicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
				col_createdDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
				col_expiredDate.setCellValueFactory(new PropertyValueFactory<>("expiredDate"));
				
				showData();
				
				tbl_member.getSelectionModel().selectedItemProperty()
				.addListener(
						(obs,old_select,new_select)->{
							if(new_select != null) {
								selected_member = tbl_member.getSelectionModel().getSelectedItem();
								txt_name.setText(selected_member.getName());
								txt_rollNo.setText(String.valueOf(selected_member.getRollNo()));
								txt_year.setText(selected_member.getYear());
								txt_academicYear.setText(String.valueOf(selected_member.getAcademicYear()));
								txt_createdDate.setValue(selected_member.getCreatedDate());
								
							}							
				});				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		private void showData() throws SQLException {
			
			List <Member> list = DBHandler.findAllMember();			
			tbl_member.setItems(FXCollections.observableArrayList(list));
			
		}

		private void resetData() {
			txt_name.setText(null);
			txt_rollNo.setText(null);
			txt_year.setText(null);
			txt_academicYear.setText(null);
			txt_createdDate.setValue(null);
			
		}
		
}
