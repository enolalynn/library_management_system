package com.mmit.controller;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mmit.LibraryStart;
import com.mmit.model.entity.Author;
import com.mmit.model.entity.Book;
import com.mmit.model.entity.Category;
import com.mmit.model.entity.DBHandler;
import com.mmit.model.entity.Member;
import com.mmit.model.entity.Transaction;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class TransactionController implements Initializable{
	@FXML
    private TableColumn<Author, String> col_author;

    @FXML
    private TableColumn<Author, String> col_author_info;

    @FXML
    private TableColumn<Book, String> col_available;

    @FXML
    private TableColumn<Transaction, LocalDate> col_borrowDate_info;

    @FXML
    private TableColumn<Category, String> col_category;

    @FXML
    private TableColumn<Category, String> col_category_info;

    @FXML
    private TableColumn<Book, Integer> col_code;

    @FXML
    private TableColumn<Book, Integer> col_code_info;

    @FXML
    private TableColumn<Transaction, LocalDate> col_dueDate_info;

    @FXML
    private TableColumn<Transaction, Double> col_fee_info;

    @FXML
    private TableColumn<Transaction, Double> col_fine_info;

    @FXML
    private TableColumn<Member, String> col_member_info;

    @FXML
    private TableColumn<Book, LocalDate> col_publishDate;

    @FXML
    private TableColumn<Transaction, LocalDate> col_returnDate_info;

    @FXML
    private TableColumn<Transaction, Integer> col_srNo_info;

    @FXML
    private TableColumn<Book, String> col_title;

    @FXML
    private TableColumn<Book, String> col_title_info;

    @FXML
    private Tab tab_borrow;

    @FXML
    private Tab tab_return;

    @FXML
    private Tab tab_transactionInfo;

    @FXML
    private TableView<Book> tbl_bookList;

    @FXML
    private TableView<Transaction> tbl_transaction_info;

    @FXML
    private TabPane tbp_transaction;

    @FXML
    private DatePicker txt_borrowDate;

    @FXML
    private DatePicker txt_borrowDate_return;

    @FXML
    private TextField txt_code;

    @FXML
    private TextField txt_code_return;

    @FXML
    private DatePicker txt_dueDate_return;

    @FXML
    private TextField txt_fees;

    @FXML
    private TextField txt_fees_return;

    @FXML
    private TextField txt_fine_return;

    @FXML
    private TextField txt_memberId;

    @FXML
    private TextField txt_memberId_return;

    @FXML
    private DatePicker txt_returnDate_return;

    @FXML
    private TextField txt_totalFee_return;
    
    private Book select_book = null;
    private Transaction transaction = null;
    Double isFine = null;
    Double isFees = null;
    Integer codeRet = null;

    
    @FXML
    void btn_borrow_click(ActionEvent event) {
    	try {
	    	var code = txt_code.getText();
	    	var memberId = txt_memberId.getText();
	    	var borrowDate = txt_borrowDate.getValue();
	    	var fee = txt_fees.getText();

	    	if(code.isEmpty()) {
	    		showAlert(AlertType.ERROR, "Code is requried");
	    		return;
	    	}
	    	if(memberId.isEmpty()) {
	    		showAlert(AlertType.ERROR, "Member ID is requried");
	    		return;
	    	}
	    	if(borrowDate == null) {
	    		showAlert(AlertType.ERROR, "Borrow Date is requried");
	    		return;
	    	}
	    	if(fee.isEmpty()) {
	    		showAlert(AlertType.ERROR, "Fee is requried");
	    		return;
	    	}
	    	
	    	var dueDate = txt_borrowDate.getValue().plusWeeks(1);
	    	transaction = new Transaction();
	    	transaction.setCode(Integer.parseInt(code));
	    	transaction.setMemberId(Integer.parseInt(memberId));
	    	transaction.setBorrowDate(borrowDate);
	    	transaction.setDueDate(dueDate);
	    	transaction.setFee(Double.parseDouble(fee));
	    	transaction.setLibId(LibraryStart.login_user);
	    	int fine = DBHandler.isBorrowed(transaction);
	    	if(fine != 0) {
	    		showAlert(AlertType.INFORMATION, "Please, return previous borrowed books!");
	    		return;
	    	}
	    	DBHandler.saveTransaction(transaction);
	    	DBHandler.changeAvailable(transaction, "No");
	    	
	    	showAlert(AlertType.INFORMATION, "Success");
	    	resetForm();
	    	showData();
	    	showTransactionData();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
			showAlert(AlertType.ERROR, e.getMessage());
		}
    }

    private void resetForm() {
    	txt_code.setText(null);
		txt_memberId.setText(null);
		txt_borrowDate.setValue(null);
		txt_fees.setText(null);
		
	}
    
    private void resetReturnForm() {
    	txt_code_return.setText(null);
		txt_memberId_return.setText(null);
		txt_borrowDate_return.setValue(null);
		txt_dueDate_return.setValue(null);
		txt_returnDate_return.setValue(null);
		txt_fine_return.setText("0");
		txt_fees_return.setText(null);
		txt_totalFee_return.setText(null);
		
	}

	private Optional<ButtonType> showAlert(AlertType type, String msg) {
    	Alert alert = new Alert(type);
		alert.setContentText(msg);
		alert.setHeaderText(null);
		alert.setTitle("Message");

		return alert.showAndWait();
		
	}

	@FXML
    void btn_home_click(ActionEvent event) throws IOException {
    	LibraryStart.changeScene("view/LibraryStart.fxml", "Library Management System");
    }
	@FXML
    void txt_bookCode_pressed(KeyEvent event) {
		codeRet = Integer.parseInt(txt_code_return.getText());
    	if(codeRet == null) {
    		showAlert(AlertType.ERROR, "Code is required");
    		return;
    	}
    	var search_book = DBHandler.findByCode(codeRet);
     	if(search_book==null) {
     		showAlert(AlertType.ERROR, codeRet + "is invalid !");
     		return ;
     	}
     	
     	txt_memberId_return.setText(String.valueOf(search_book.getMemberId()));
     	txt_borrowDate_return.setValue(search_book.getBorrowDate());
     	txt_fees_return.setText(String.valueOf(search_book.getFee()));
     	txt_dueDate_return.setValue(search_book.getDueDate());
     	
     	isFine = Double.parseDouble(txt_fine_return.getText());
  		isFees = Double.parseDouble(String.valueOf(search_book.getFee()));
		double totalAmount = isFine + isFees;
		txt_totalFee_return.setText(String.valueOf(totalAmount)); 
    }
	
	private void returnData() throws Exception {
		
		var code = txt_code_return.getText();
    	var memberId = txt_memberId_return.getText();
    	var borrowDate = txt_borrowDate_return.getValue();
    	var returnDate = txt_returnDate_return.getValue();
    	var fine = txt_fine_return.getText();
    	
		if(code.isEmpty()) {
    		showAlert(AlertType.ERROR, "Code is requried");
    		return;
    	}
    	if(memberId.isEmpty()) {
    		showAlert(AlertType.ERROR, "Member ID is requried");
    		return;
    	}
    	if(borrowDate == null) {
    		showAlert(AlertType.ERROR, "Borrow Date is requried");
    		return;
    	}
    	if(fine.isEmpty()) {
    		showAlert(AlertType.ERROR, "Fee is requried");
    		return;
    	}
    	if(returnDate == null) {
    		showAlert(AlertType.ERROR, "Return Date is requried");
    		return;
    	}
    	
	  	transaction = new Transaction();
    	transaction.setCode(Integer.parseInt(code));
    	transaction.setMemberId(Integer.parseInt(memberId));
    	transaction.setBorrowDate(borrowDate);
    	transaction.setReturnDate(returnDate);
    	transaction.setFine(Double.parseDouble(fine));
    	
    	DBHandler.returnBook(transaction);
    	DBHandler.changeAvailable(transaction, "Yes");
    	
    	showAlert(AlertType.INFORMATION, "Success");
    	resetReturnForm();
    	showTransactionData();
    	showData();
    	
	}

  	@FXML
    void txt_fine_typed(KeyEvent event) {	  
  		isFine = Double.parseDouble(txt_fine_return.getText());
  		isFees = Double.parseDouble(txt_fees_return.getText());
		double totalAmount = isFine + isFees;
		txt_totalFee_return.setText(String.valueOf(totalAmount)); 
    }


	@FXML
	  void txt_returnDate_onAction(ActionEvent event) {
		  	var dueDate = txt_borrowDate_return.getValue().plusWeeks(1);
	    	var reDate = txt_returnDate_return.getValue();
	    	txt_returnDate_return.setValue(reDate);
	        checkOverDue();
	  }

    private void checkOverDue() {
    	var dueDate = txt_borrowDate_return.getValue().plusWeeks(1);
    	var reDate = txt_returnDate_return.getValue();
    	txt_returnDate_return.setValue(reDate);
    	
	  	  if(reDate.isAfter(dueDate) == true) {
	  		showAlert(AlertType.INFORMATION, "Book is over due, please pay a fine!");
			return;	
	  	}
	  	
		
	}

	@FXML
    void btn_return_click(ActionEvent event) throws Exception {

    	returnData();
    	

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			col_code.setCellValueFactory(new PropertyValueFactory<>("code"));
			col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
			col_publishDate.setCellValueFactory(new PropertyValueFactory<>("publishDate"));
			col_category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
			col_author.setCellValueFactory(new PropertyValueFactory<>("authorName"));
			col_available.setCellValueFactory(new PropertyValueFactory<>("available"));
			tbl_bookList.getSelectionModel().selectedItemProperty()
			.addListener(
					(obs,old_select,new_select)->{
						if(new_select != null) {
							select_book = tbl_bookList.getSelectionModel().getSelectedItem();
							txt_code.setText(String.valueOf(select_book.getCode()));
						}							
			});
			

			showData();
			
			col_srNo_info.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_code_info.setCellValueFactory(new PropertyValueFactory<>("code"));
			col_member_info.setCellValueFactory(new PropertyValueFactory<>("memberName"));
			col_borrowDate_info.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
			col_dueDate_info.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
			col_returnDate_info.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
			col_fine_info.setCellValueFactory(new PropertyValueFactory<>("fine"));
			col_fee_info.setCellValueFactory(new PropertyValueFactory<>("fee"));
			

			showTransactionData();
			
			isFine = Double.parseDouble(txt_fine_return.getText());
	  		isFees = Double.parseDouble(txt_fees_return.getText());
			double totalAmount = isFine + isFees;
			txt_totalFee_return.setText(String.valueOf(totalAmount)); 
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}
		
	}

	private void showTransactionData() {
		List <Transaction> list = DBHandler.findAllTransaction();	

		tbl_transaction_info.setItems(FXCollections.observableArrayList(list));
		
	}

	private void showData() {
		List <Book> list = DBHandler.findAvailableBook();	

		tbl_bookList.setItems(FXCollections.observableArrayList(list));
		
	}

}
