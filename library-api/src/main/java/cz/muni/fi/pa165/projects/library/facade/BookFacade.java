package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.BookCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.BookDTO;
import java.util.List;

/**
 *
 * @author Milan Skipala
 */
public interface BookFacade {
    public Long addBook(BookCreateDTO book);    
    public BookDTO findBookById(Long id);
    public List<BookDTO> findAllBooksOfAuthor(String author);
    public List<BookDTO> findBookByIsbn(String isbn);
    public List<BookDTO> getAllBooks();
    /*public void changeAuthor(ChangeAuthorDTO author);
    public void changeTitle(ChangeTitleDTO title);
    public void changeIsbn(ChangeIsbnDTO isbn);*/
    public void deleteBook(Long id);
}
