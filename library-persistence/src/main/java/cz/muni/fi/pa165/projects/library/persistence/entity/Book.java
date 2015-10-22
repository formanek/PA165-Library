/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.projects.library.persistence.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
    @Column(nullable = false)
    private String isbn;

    private String author;

    private String title;

    public Book() {
    }

    public Book(Long id) {
        if (id == null)
            throw new NullPointerException("Id was null");
        this.id = id;
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
        if (id == null)
            throw new NullPointerException("Id was null");
        this.id = id;
    }

    public void setIsbn(String isbn) {
        notNull(isbn,"ISBN");
        notEmpty(isbn,"ISBN");
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        notNull(author, "Author");
        notEmpty(author,"Author");
        this.author = author;
    }

    public void setTitle(String title) {
        notNull(title,"Title");
        notEmpty(title,"Title");
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
            return Objects.equals(getId(), other.getId())
                    && Objects.equals(getIsbn(), other.getIsbn())
                    && Objects.equals(getAuthor(), other.getAuthor())
                    && Objects.equals(getTitle(), other.getTitle());
        }
        return false;
    }
    
    /**
     * Helper method - it checks whether the arg is null. If it is, it throws NullPointerException
     * @param arg - String argument which will be checked
     * @param argName - Name of the argument which will be inserted into exception message
     */
    private void notNull(String arg, String argName) {
        if (arg == null)
            throw new NullPointerException(argName + " was null.");
    }
    /**
     * Helper method - it checks whether the arg is empty string. If it is, it throws IllegalArgumentException
     * @param arg - String argument which will be checked
     * @param argName - Name of the argument which will be inserted into exception message
     */
    private void notEmpty(String arg, String argName) {
        if (arg.trim().equals(""))
            throw new IllegalArgumentException(argName + " was empty.");
    }
}
