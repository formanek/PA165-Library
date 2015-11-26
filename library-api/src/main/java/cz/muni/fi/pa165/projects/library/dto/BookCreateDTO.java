package cz.muni.fi.pa165.projects.library.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Milan Skipala
 */
public class BookCreateDTO {
    
    private String isbn;

    private String author;

    private String title;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getAuthor(), getTitle());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BookCreateDTO)) {
            return false;
        }
        final BookCreateDTO other = (BookCreateDTO) obj;
        return Objects.equals(getIsbn(), other.getIsbn()) 
                && Objects.equals(getAuthor(), other.getAuthor())
                && Objects.equals(getTitle(), other.getTitle());
    }
}
