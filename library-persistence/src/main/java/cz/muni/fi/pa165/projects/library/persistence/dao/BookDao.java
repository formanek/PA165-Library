/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import java.util.Collection;

/**
 * Interface for data access objects which provide access to Book entity
 * 
 * @author Milan Skipala
 */
public interface BookDao {
    public void create(Book book);
    public void delete(Book book);
    public Book findById(Long id);
    public Collection<Book> find(Book book);
    public Collection<Book> findAll();
    
}
