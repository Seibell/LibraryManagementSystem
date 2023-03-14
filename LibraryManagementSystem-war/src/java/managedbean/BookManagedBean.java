/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.MemberNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.stateless.BookSessionBeanLocal;
import session.stateless.LendAndReturnSessionBeanLocal;
import session.stateless.MemberSessionBeanLocal;

/**
 *
 * @author ryanl
 */
@Named(value = "bookManagedBean")
@RequestScoped
public class BookManagedBean implements Serializable {

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBean;

    @EJB
    private MemberSessionBeanLocal memberSessionBean;

    @EJB
    private BookSessionBeanLocal bookSessionBean;

    private List<Book> books = new ArrayList<>();
    private Long bookId;
    private String identityNo;
    private String author;
    private String isbn;
    private String title;

    /**
     * Creates a new instance of BookManagedBean
     */
    public BookManagedBean() {
    }

    public List<Book> retrieveAllBooks() {
        return bookSessionBean.retrieveAllBooks();
    }

    /**
     * @return the books
     */
    public List<Book> getBooks() {
        return books;
    }

    public String lendBook() {
        Book b = null;
        Member m = null;

        try {
            b = bookSessionBean.retrieveBookById(bookId);
            m = memberSessionBean.retrieveMemberByIdentityNo(identityNo);

        } catch (BookNotFoundException e) {
            System.out.println("Book not found!");
        } catch (MemberNotFoundException e) {
            System.out.println("Member not found!");
        } catch (Exception e) {
            System.out.println("Error occured!");
        }
        FacesContext context = FacesContext.getCurrentInstance();
        String redirectLink = "/secret/lend_book.xhtml";

        try {
            lendAndReturnSessionBean.createLendAndReturn(b, m);
            FacesMessage message = new FacesMessage("Successfully lent book: " + b.getTitle() + " to member: " + m.getFirstName());
            context.addMessage("lendBookGrowl", message);
            redirectLink = "/secret/logged_in_homepage.xhtml?faces-redirect=true";
        } catch (BookNotAvailableException e) {
            FacesMessage message = new FacesMessage("Book not available for lending");
            context.addMessage("lendBookGrowl", message);
        } catch (Exception e) {
            System.out.println("Error");
            FacesMessage message = new FacesMessage("Cant find book/Cant find member");
            context.addMessage("lendBookGrowl", message);
        }

        return redirectLink;
    }

    public String addBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        String redirectLink = "/secret/add_book.xhtml";

        if (this.author.isEmpty() || this.isbn.isEmpty() || this.getTitle().isEmpty()) {
            FacesMessage message = new FacesMessage("Fields are empty");
            context.addMessage("addBookGrowl", message);
        }

        try {
            bookSessionBean.retrieveBookByISBN(isbn);
            FacesMessage message = new FacesMessage("ISBN already exists!");
            context.addMessage("addBookGrowl", message);
            
        } catch (BookNotFoundException ex) {
            bookSessionBean.createBook(new Book(title, isbn, author));
            redirectLink = "/secret/logged_in_homepage.xhtml?faces-redirect=true";
            
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Error has occured");
            context.addMessage("addBookGrowl", message);
        }

        return redirectLink;
    }

    /**
     * @param books the books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * @return the bookId
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * @return the identityNo
     */
    public String getIdentityNo() {
        return identityNo;
    }

    /**
     * @param identityNo the identityNo to set
     */
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
