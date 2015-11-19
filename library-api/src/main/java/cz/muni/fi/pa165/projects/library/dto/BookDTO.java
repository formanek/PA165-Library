package cz.muni.fi.pa165.projects.library.dto;

import java.util.Objects;

/**
 *
 * @author Milan Skipala
 */
public class BookDTO {
    
    private Long id;

    private String isbn;

    private String author;

    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return Objects.hash(getId(), getIsbn(), getAuthor(), getTitle());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BookDTO)) {
            return false;
        }
        final BookDTO other = (BookDTO) obj;
        
        return Objects.equals(getIsbn(), other.getIsbn()) 
                && Objects.equals(getAuthor(), other.getAuthor())
                && Objects.equals(getTitle(), other.getTitle());
    }
    
}
