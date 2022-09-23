package com.mmit.model.entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBHandler {
	public static Connection createConnection() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/assignment","root","");
		
		return conn;
	}
	
	public static Librarian login(String email, String pass) {
		Librarian user = null;
		try(var con = createConnection()) {
			var query = "SELECT * FROM librarians WHERE email = ? AND password = ?";
			var pstm = con.prepareStatement(query);
			pstm.setString(1, email);
			pstm.setString(2, pass);
			
			var rs = pstm.executeQuery();
			if(rs.next()) { 
				user = new Librarian();
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setId(rs.getInt("id"));
			}
			
			
		} catch (Exception e) {
			user = null;
		}
		
		return user;

	}
	public static void saveAuthor(Author obj) throws Exception {
		try (var con = createConnection()){
			var query = "INSERT INTO authors (name,city,birthday)VALUES(?,?,?)";
			var pstm = con.prepareStatement(query);
			pstm.setString(1, obj.getName());
			pstm.setString(2, obj.getCity());
			pstm.setDate(3, Date.valueOf(obj.getBirthday())); //convert localdate to sql date
			pstm.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	public static void updateAuthor(Author selected_author) throws Exception {
		try (var con = createConnection()){
			var query = """
					UPDATE authors 
					SET name = ? , city =?, birthday = ?
					WHERE id = ?
					""";
			
			var pstm = con.prepareStatement(query);
			
			pstm.setString(1, selected_author.getName());
			pstm.setString(2, selected_author.getCity());
			pstm.setDate(3, Date.valueOf(selected_author.getBirthday()));
			pstm.setInt(4, selected_author.getId());
			
			pstm.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	public static void deleteAuthor(int id) {
		try(var con = createConnection()) {
			var query = "DELETE FROM authors WHERE id = ?";
			var pstm = con.prepareStatement(query);
			pstm.setInt(1, id);
			
			pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static List<Author> findAllAuthor() {
		List<Author> list = new ArrayList<>();
		try (var con = createConnection()){
			var query = "SELECT * FROM authors";
			var pstm = con.prepareStatement(query);
			
			var rs = pstm.executeQuery();

			while(rs.next()) {
				
				var auth = new Author();
				auth.setBirthday(LocalDate.parse(rs.getString("birthday")));
				auth.setCity(rs.getString("city"));
				auth.setId(rs.getInt("id"));
				auth.setName(rs.getString("name"));
				
				list.add(auth);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public static void saveCategory(Category obj) throws Exception {
		try (var con = createConnection()){
			var query = "INSERT INTO categories (name)VALUES(?)";
			var pstm = con.prepareStatement(query);
			pstm.setString(1, obj.getName());
			pstm.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	public static void deleteCategory(int id) {
		try(var con = createConnection()) {
			var query = "DELETE FROM categories WHERE id = ?";
			var pstm = con.prepareStatement(query);
			pstm.setInt(1, id);
			
			pstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void updateCategory(Category cat) throws Exception {
		try (var con = createConnection()){
			var query = "UPDATE categories SET name = ?  WHERE id = ?";
			
			var pstm = con.prepareStatement(query);
			
			pstm.setString(1, cat.getName());	
			pstm.setInt(2, cat.getId());
			
			pstm.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}		
	}
	public static List<Category> findAllCategory() throws SQLException {
		List<Category> list = new ArrayList<>();
		try (var con = createConnection()){
			var query = "SELECT * FROM categories ORDER BY id";
			var pstm = con.prepareStatement(query);	
			ResultSet rs = pstm.executeQuery();

			while(rs.next()) {			
				var cat = new Category();
				cat.setId(rs.getInt("id"));
				cat.setName(rs.getString("name"));
				
				list.add(cat);
	}
}
		return list;
	}

	

	public static void registerLibrarian(Librarian librarian) throws Exception {
		try(var con = createConnection()) {
			var query = """
					INSERT INTO librarians(email,password,nrcno, phone )
					VALUES(?,?,?,?)
					""";
			var pstm = con.prepareStatement(query);
			
			pstm.setString(1,librarian.getEmail());
			pstm.setString(2,librarian.getPassword());
			pstm.setString(3,librarian.getNrc());
			pstm.setInt(4,librarian.getPhone());
			
			pstm.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public static List<Librarian> findAllLibrarian() throws SQLException {
		List<Librarian> list = new ArrayList<>();
		try (var con = createConnection()){
			var query = "SELECT * FROM librarians";
			var pstm = con.prepareStatement(query);
			
			var rs = pstm.executeQuery();

			while(rs.next()) {
				
				var lib = new Librarian();
				
				lib.setEmail(rs.getString("email"));
				lib.setId(rs.getInt("id"));
				lib.setNrc(rs.getString("nrcno"));
				lib.setPhone(rs.getInt("phone"));
				
				
				list.add(lib);
	}
}
		return list;
	}

	public static void saveMember(Member obj) throws Exception {
		try (var con = createConnection()){
			var query = "INSERT INTO members (name,roll_no,year,academic_year,created_date,expired_date)VALUES(?,?,?,?,?,?)";
			var pstm = con.prepareStatement(query);
			pstm.setString(1, obj.getName());
			pstm.setInt(2, obj.getRollNo());
			pstm.setString(3, obj.getYear());
			pstm.setInt(4, obj.getAcademicYear());
			pstm.setDate(5, Date.valueOf(obj.getCreatedDate()));
			pstm.setDate(6, Date.valueOf(obj.getExpiredDate()));
			
			pstm.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public static List<Member> findAllMember() {
		List<Member> list = new ArrayList<>();
		try (var con = createConnection()){
			var query = "SELECT * FROM members";
			var pstm = con.prepareStatement(query);			
			var rs = pstm.executeQuery();
			while(rs.next()) {
				var member = new Member();			
				member.setCardId(rs.getInt(("card_id")));
				member.setName(rs.getString("name"));
				member.setRollNo(rs.getInt("roll_no"));
				member.setYear(rs.getString("year"));
				member.setAcademicYear(rs.getInt("academic_year"));
				member.setCreatedDate(LocalDate.parse(rs.getString("created_date")));
				member.setExpiredDate(LocalDate.parse(rs.getString("expired_date")));
				
				list.add(member);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void updateMember(Member s_member) throws Exception {
		try (var con = createConnection()){
			var query = """
					UPDATE members 
					SET name = ? , roll_no =?, year = ?,academic_year = ?, created_date = ? , expired_date = ?
					WHERE card_id = ?
					""";
			
			var pstm = con.prepareStatement(query);
			
			pstm.setString(1, s_member.getName());
			pstm.setInt(2, s_member.getRollNo());
			pstm.setString(3, s_member.getYear());
			pstm.setInt(4, s_member.getAcademicYear());
			pstm.setDate(5, Date.valueOf(s_member.getCreatedDate()));
			pstm.setDate(6, Date.valueOf(s_member.getExpiredDate()));
			pstm.setInt(7, s_member.getCardId());
			System.out.println(s_member.getCardId());
			pstm.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public static void saveBook(Book book) throws Exception {
		try(var con = createConnection()) {
			var query = """
					INSERT INTO books(code,title,publish_date,author_id, category_id,  created_by )
					VALUES(?,?,?,?,?,?)
					""";
			var pstm = con.prepareStatement(query);
			pstm.setInt(1, book.getCode());
			pstm.setString(2, book.getTitle());
			pstm.setDate(3,Date.valueOf(book.getPublishDate()));
			pstm.setInt(4, book.getAuthor().getId());
			pstm.setInt(5, book.getCategory().getId());
			pstm.setInt(6, book.getCreated_by().getId());
			pstm.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
	}

	public static List<Book> findAllBook() {
		List<Book> list = new ArrayList<>();
		try (var con = createConnection()){
			var query = """
					 SELECT b.code, b.title, b.publish_date, c.name 'categoryName', a.name 'authorName',b.available, l.email  
					 FROM books b, categories c, authors a, librarians l
					 WHERE b.category_id = c.id AND b.author_id = a.id AND b.created_by = l.id
					""";
			var pstm = con.prepareStatement(query);			
			var rs = pstm.executeQuery();
			while(rs.next()) {
				var book = new Book();
				//bind column to field
				book.setCode(rs.getInt("code"));
				book.setTitle(rs.getString("title"));
				book.setAvailable(rs.getString("available"));
				book.setPublishDate(LocalDate.parse(rs.getString("publish_date")));
				
				var createdBy = new Librarian();
				createdBy.setEmail(rs.getString("email"));
				book.setCreated_by(createdBy);
				
				var category = new Category();
				category.setName(rs.getString("categoryName"));				
				book.setCategory(category);
				
				var author = new Author();
				author.setName(rs.getString("authorName"));
				book.setAuthor(author);
					
				list.add(book);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void saveTransaction(Transaction transaction) throws Exception {
		try(var con = createConnection()) {
			var query = """
					INSERT INTO transactions(card_id,book_id,borrow_date,due_date,fees,lib_id )
					VALUES(?,?,?,?,?,?)
					""";
			var pstm = con.prepareStatement(query);
			pstm.setInt(1, transaction.getMemberId());
			pstm.setInt(2, transaction.getCode());
			pstm.setDate(3,Date.valueOf(transaction.getBorrowDate()));
			pstm.setDate(4,Date.valueOf(transaction.getDueDate()));
			pstm.setDouble(5, transaction.getFee());
			pstm.setInt(6, transaction.getLibId().getId());
			
			pstm.executeUpdate();
			
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public static void changeAvailable(Transaction transaction, String available) throws Exception {
		try (var con = createConnection()){
			var query = "UPDATE books SET available = ? WHERE code = ?";
			var pstm = con.prepareStatement(query);
			pstm.setString(1, available);
			pstm.setInt(2, transaction.getCode());
			pstm.executeUpdate();
			
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public static List<Book> findAvailableBook() {
		List<Book> list = new ArrayList<>();
		try (var con = createConnection()){
			var query = """
					 SELECT b.code, b.title, b.publish_date, c.name 'categoryName', a.name 'authorName',b.available, l.email  
					 FROM books b, categories c, authors a, librarians l
					 WHERE b.category_id = c.id AND b.author_id = a.id AND b.created_by = l.id AND 
					 b.available = 'Yes'
					""";
			var pstm = con.prepareStatement(query);			
			var rs = pstm.executeQuery();
			while(rs.next()) {
				var book = new Book();
				
				book.setCode(rs.getInt("code"));
				book.setTitle(rs.getString("title"));
				book.setAvailable(rs.getString("available"));
				book.setPublishDate(LocalDate.parse(rs.getString("publish_date")));
				
				var createdBy = new Librarian();
				createdBy.setEmail(rs.getString("email"));
				book.setCreated_by(createdBy);
				
				var category = new Category();
				category.setName(rs.getString("categoryName"));				
				book.setCategory(category);
				
				var author = new Author();
				author.setName(rs.getString("authorName"));
				book.setAuthor(author);
					
				list.add(book);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int isBorrowed(Transaction transaction) {
		int fine = 0;
		try (var con = createConnection()){
			var query = """
					 SELECT t.due_date, m.card_id , t.id
					 FROM transactions t, members m 
					 WHERE t.card_id = m.card_id AND CURDATE() >= t.due_date
					 AND t.card_id = ?
					 GROUP BY t.id		 
					""";
			
			var pstm = con.prepareStatement(query);
			pstm.setInt(1,transaction.getMemberId());
	
			var rs = pstm.executeQuery();
			while (rs.next()) {
				fine = rs.getInt("card_id");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fine;
		
	}

	public static Transaction findByCode(int bookCode) {
		Transaction trans = null;
		try(var con = createConnection()) {
			
			
			var query = """
			 		SELECT * FROM `transactions` t , books b
							WHERE t.book_id = b.code 
							and t.book_id = ? 
							and b.available = 'No'
			 		""";
			var pstm = con.prepareStatement(query);
			pstm.setInt(1, bookCode);
			var rs = pstm.executeQuery();
			if(rs.next()) {
				trans = new Transaction();
				trans.setCode(rs.getInt("book_id"));
				trans.setMemberId(rs.getInt("card_id"));
				trans.setBorrowDate(LocalDate.parse(rs.getString("borrow_date")));
				trans.setDueDate(LocalDate.parse(rs.getString("due_date")));
				trans.setFee(Double.parseDouble(rs.getString("fees")));
			}
		}
		catch (Exception e) {
			trans = null;
		}
		return trans;	}

	public static void saveReturn(Transaction transaction) throws Exception {
		try(var con = createConnection()) {
			var query = """
					INSERT INTO transactions(book_id,card_id,borrow_date,due_date,return_date,fine,fees,lib_id )
					VALUES(?,?,?,?,?,?,?,?)
					""";
			var pstm = con.prepareStatement(query);
			
			pstm.setInt(1, transaction.getCode());
			pstm.setInt(2, transaction.getMemberId());
			pstm.setDate(3,Date.valueOf(transaction.getBorrowDate()));
			pstm.setDate(4,Date.valueOf(transaction.getDueDate()));
			pstm.setDate(5,Date.valueOf(transaction.getReturnDate()));
			pstm.setDouble(6, transaction.getFine());
			pstm.setDouble(7, transaction.getFee());
			pstm.setInt(8, transaction.getLibId().getId());
			
			pstm.executeUpdate();
			
			
			
		} catch (Exception e) {
			throw e;
		}
		

		
	}

	public static void returnBook(Transaction transaction) throws Exception {
		try (var con = createConnection()){
			var query = "UPDATE transactions SET return_date = ? , fine = ? WHERE card_id = ? and book_id = ? and borrow_date = ?";
			var pstm = con.prepareStatement(query);
			System.out.println("hello" + Date.valueOf(transaction.getReturnDate()));
			pstm.setDate(1, Date.valueOf(transaction.getReturnDate()));
			pstm.setDouble(2, transaction.getFine());
			pstm.setInt(3, transaction.getMemberId());
			pstm.setInt(4, transaction.getCode());
			pstm.setDate(5, Date.valueOf(transaction.getBorrowDate()));
			pstm.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	public static List<Transaction> findAllTransaction() {
		List<Transaction> list = new ArrayList<>();
		try (var con = createConnection()){
			var query = """
					 SELECT t.id, t.card_id, t.book_id, t.borrow_date, t.due_date, t.return_date, t.fine, t.fees,
					 m.name, m.card_id  
					 FROM transactions t, members m
					 WHERE t.card_id = m.card_id 
					""";
			var pstm = con.prepareStatement(query);			
			var rs = pstm.executeQuery();
			while(rs.next()) {
				var transaction = new Transaction();
				
				transaction.setId(rs.getInt("id"));
				transaction.setCode(rs.getInt("book_id"));
				transaction.setBorrowDate(LocalDate.parse(rs.getString("borrow_date")));
				transaction.setDueDate(LocalDate.parse(rs.getString("due_date")));
	
				var re =LocalDate.parse(rs.getString("return_date"));
				if(re == LocalDate.parse("0001-01-01")) {
					transaction.setReturnDate(LocalDate.parse("0000-00-00"));
				}else {
					transaction.setReturnDate(re);
				}
				
				transaction.setFine(rs.getDouble("fine"));
				transaction.setFee(rs.getDouble("fees"));
				
				var member = new Member();
				member.setName(rs.getString("name"));
				transaction.setMember(member);
				
				list.add(transaction);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
}
