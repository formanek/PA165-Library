package cz.muni.fi.pa165.projects.library.persistence.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Class that represents Book which can be lent to the Members
 *
 * @author Milan Skipala
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^(?=.{13}$)\\d{1,5}([- ])\\d{1,7}\\1\\d{1,6}\\1(\\d|X)$")
    @Column(nullable = false)
    private String isbn;

    @NotNull
    @Column(nullable = false)
    private String author;

    @NotNull
    @Column(nullable = false)
    private String title;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, author, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Book) {
            Book other = (Book) obj;
            return Objects.equals(getIsbn(), other.getIsbn())
                    && Objects.equals(getAuthor(), other.getAuthor())
                    && Objects.equals(getTitle(), other.getTitle());
        }
        return false;
        
    }
}
