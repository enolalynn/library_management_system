package com.mmit.controller;

import java.io.IOException;

import com.mmit.LibraryStart;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
public class LibraryStartController {

    @FXML
    void btn_logout_click(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void panel_author_click(MouseEvent event) throws IOException {
    	LibraryStart.changeScene("view/Author.fxml","Author");
    }

    @FXML
    void panel_book_click(MouseEvent event) throws IOException {
    	LibraryStart.changeScene("view/Book.fxml","Book");
    }

    @FXML
    void panel_category_click(MouseEvent event) throws IOException {
    	LibraryStart.changeScene("view/LibCategory.fxml","Category");
    }

    @FXML
    void panel_librarian_click(MouseEvent event) throws IOException {
    	LibraryStart.changeScene("view/Librarian.fxml","Librarian");
    }

    @FXML
    void panel_member_click(MouseEvent event) throws IOException {
    	LibraryStart.changeScene("view/Member.fxml","Member");
    }

    @FXML
    void panel_transaction_click(MouseEvent event) throws IOException {
    	LibraryStart.changeScene("view/Transaction.fxml","Transaction");
    }

}


