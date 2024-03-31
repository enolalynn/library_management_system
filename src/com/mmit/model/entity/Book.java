package com.mmit.model.entity;
import java.time.LocalDate;
public class Book {
	private int code;
	private String title;
	private String available;
	private LocalDate publishDate;
	private Category category;
	private Author author;
	private Librarian created_by;
	public String isAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}
	public Category getCategory() {
		return category;
	}

	public String getCategoryName() {
		return category.getName();
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Author getAuthor() {
		return author;
	}

	public String getAuthorName() { 
		return author.getName();
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Librarian getCreated_by() {
		return created_by;
	}
	public void setCreated_by(Librarian created_by) {
		this.created_by = created_by;
		
	}
	public String getEntryName() {
		return created_by.getEmail();
	}
	
	
	
}
