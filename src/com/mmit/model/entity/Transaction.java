package com.mmit.model.entity;

import java.time.LocalDate;

public class Transaction {
	private int id;
	private int memberId;
	private int code;
	private LocalDate borrowDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private double fee;
	private Librarian libId;
	private double fine;
	private int srNo;
	private Member member;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public double getFine() {
		return fine;
	}
	public void setFine(double fine) {
		this.fine = fine;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Librarian getLibId() {
		return libId;
	}
	public void setLibId(Librarian libId) {
		this.libId = libId;
	}
	public String getEntryName() { 
		return libId.getEmail();
	}
	
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
		
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	public String getMemberName() { 
		return member.getName();
	}
	
	
}
