package ie.cit.oossp.vaadinlab.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import ie.cit.oossp.vaadinlab.domain.Book;

public interface BookRepository {

	List<Book> findAll();
	
	void delete(String isbn);
	
}
