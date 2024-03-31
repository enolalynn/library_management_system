package com.mmit.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.mmit.LibraryStart;
import com.mmit.model.entity.Author;
import com.mmit.model.entity.Book;
import com.mmit.model.entity.Category;
import com.mmit.model.entity.DBHandler;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookViewController implements Initializable {
	@FXML
    private TableColumn<Author, String> col_author;

    @FXML
    private TableColumn<Book, String> col_available;

    @FXML
    private TableColumn<Category, String> col_category;

    @FXML
    private TableColumn<Book, Integer> col_code;

    @FXML
    private TableColumn<Book, Integer> col_entryBy;

    @FXML
    private TableColumn<Book, LocalDate> col_publishDate;

    @FXML
    private TableColumn<Book, String> col_title;

    @FXML
    private TableView<Book> tbl_book;

    @FXML
    void btn_home_click(ActionEvent event) throws IOException {
    	LibraryStart.changeScene("view/LibraryStart.fxml", "Library Management System");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		col_code.setCellValueFactory(new PropertyValueFactory<>("code"));
		col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
		col_publishDate.setCellValueFactory(new PropertyValueFactory<>("publishDate"));
		col_category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
		col_author.setCellValueFactory(new PropertyValueFactory<>("authorName"));
		col_entryBy.setCellValueFactory(new PropertyValueFactory<>("entryName"));
		col_available.setCellValueFactory(new PropertyValueFactory<>("available"));
		
		try {
			showData();
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}					
	}
	
	private void showData() throws SQLException {
		
		List <Book> list = DBHandler.findAllBook();			
		tbl_book.setItems(FXCollections.observableArrayList(list));
		
	}
		
}


