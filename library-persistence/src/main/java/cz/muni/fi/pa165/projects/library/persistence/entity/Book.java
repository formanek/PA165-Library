package cz.muni.fi.pa165.projects.library.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

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
    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$")
    @Column(nullable = false)
    private String isbn;

    @NotNull
    @Column(nullable = false)
    private String author;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private Boolean loanable;

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

    public Boolean getLoanable() {
        return loanable;
    }

    public void setLoanable(Boolean loanable) {
        this.loanable = loanable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, author, title, loanable);
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
                    && Objects.equals(getTitle(), other.getTitle())
                    && Objects.equals(getLoanable(), other.getLoanable());
        }
        return false;
    }
}
