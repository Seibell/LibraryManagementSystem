/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Book;
import exception.BookNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ryanl
 */
@Stateless
public class BookSessionBean implements BookSessionBeanLocal {

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;

    @Override
    public void createBook(Book book) {
        em.persist(book);
    }
    
    @Override
    public List<Book> retrieveAllBooks() {
        Query query = em.createQuery("SELECT b FROM Book b");
        return query.getResultList();
    }

    @Override
    public Book retrieveBookByISBN(String isbn) throws BookNotFoundException {
        Query query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :inIsbn");
        query.setParameter("inIsbn", isbn);

        try {
            return (Book) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new BookNotFoundException("Book not found!");
        }
    }

    @Override
    public Book retrieveBookById(Long id) throws BookNotFoundException {
        Query query = em.createQuery("SELECT b FROM Book b WHERE b.bookId = :inId");
        query.setParameter("inId", id);

        try {
            return (Book) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new BookNotFoundException("Book not found!");
        }
    }
    
    @Override
    public void updateBook(Book book) {
        em.merge(book);
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
