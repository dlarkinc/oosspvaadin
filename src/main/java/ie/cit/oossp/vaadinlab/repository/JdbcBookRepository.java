package ie.cit.oossp.vaadinlab.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ie.cit.oossp.vaadinlab.domain.Book;
import ie.cit.oossp.vaadinlab.rowmapper.BookRowMapper;

@Repository
public class JdbcBookRepository implements BookRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Book> findAll() {
		String sql = "SELECT * FROM books";
		return jdbcTemplate.query(sql, new BookRowMapper());
	}

	@Override
	public void delete(String isbn) {
		String sql = "DELETE FROM books WHERE isbn = ?";
		jdbcTemplate.update(sql, isbn);
	}

	
}
