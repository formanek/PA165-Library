package cz.muni.fi.pa165.projects.library.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(orphanRemoval = true, mappedBy = "book")
    private Set<LoanItem> loanItems = new HashSet<>();

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
    // FIXME unmodifiableSet
    public Set<LoanItem> getLoanItems() {
//        return Collections.unmodifiableSet(loanItems);
        return this.loanItems;
    }

    public void setLoanItems(Set<LoanItem> loanItems) {
        this.loanItems = loanItems;
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
