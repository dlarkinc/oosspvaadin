package ie.cit.oossp.vaadinlab;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.Button.ClickEvent;

import ie.cit.oossp.vaadinlab.domain.Book;
import ie.cit.oossp.vaadinlab.repository.BookRepository;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	@Autowired
	BookRepository bookRepo;
	
	Grid<Book> bookGrid;
	HorizontalLayout vl;
	Button delButton;
	
	public VaadinUI() {
		this.bookGrid = new Grid<>(Book.class);
		this.vl = new HorizontalLayout();
		this.delButton = new Button("Delete Selected");
	}
	
	@Override
	protected void init(VaadinRequest request) {
	
		List<Book> books = bookRepo.findAll();
		bookGrid.setItems(books);
		
		delButton.setEnabled(false);
	
		// Change the caption for a single column
		bookGrid.getColumn("isbn").setCaption("ISBN");
		
		// Rearrange columns by column name
		bookGrid.setColumnOrder("isbn", "title", "author");
		
		// Multi-selection
		bookGrid.setSelectionMode(SelectionMode.MULTI);
		bookGrid.addSelectionListener(event -> 
			{
				Set<Book> selected = event.getAllSelectedItems();
				Notification.show(selected.size() + " items selected");
				delButton.setEnabled(selected.size() > 0);
			});
		
		// Delete method 1
		delButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Set<Book> selected = bookGrid.getSelectedItems();
				for (Book b: selected) {
					bookRepo.delete(b.getIsbn());
				}
				bookGrid.setItems(bookRepo.findAll());
			}
		});
		
		vl.addComponent(bookGrid);
		vl.addComponent(delButton);
		setContent(vl);
	}

}
