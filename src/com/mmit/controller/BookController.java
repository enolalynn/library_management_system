package com.mmit.controller;

import java.io.IOException;

import com.mmit.LibraryStart;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class BookController {
	@FXML
    void btn_add_click(ActionEvent event) throws IOException {
		LibraryStart.changeScene("view/BookAdd.fxml", "Add New Book");
    }

	@FXML
    void btn_back_click(ActionEvent event) throws IOException {
		LibraryStart.changeScene("view/LibraryStart.fxml", "Library Management System");
    }

    @FXML
    void btn_edit_click(ActionEvent event) throws IOException {
    	LibraryStart.changeScene("view/BookEdit.fxml", "Edit / Delete Book");
    }

    @FXML
    void btn_search_click(ActionEvent event) throws IOException {
    	LibraryStart.changeScene("view/SearchBook.fxml", "Search Book");
    }

    @FXML
    void btn_view_click(ActionEvent event) throws IOException {
    	LibraryStart.changeScene("view/BookView.fxml","View Book");
    }
}
