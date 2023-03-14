/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Book;
import exception.BookNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ryanl
 */
@Local
public interface BookSessionBeanLocal {

    public void createBook(Book book);

    public List<Book> retrieveAllBooks();

    public Book retrieveBookByISBN(String isbn) throws BookNotFoundException ;

    public Book retrieveBookById(Long id)throws BookNotFoundException ;

    public void updateBook(Book book);
    
}
