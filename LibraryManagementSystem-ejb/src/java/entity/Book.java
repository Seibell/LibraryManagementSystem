/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enume.BookStatus;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ryanl
 */
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private String isbn;
    private String author;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private ArrayList<LendAndReturn> lending;

    public Book() {
    }

    public Book(Long bookId, String title, String isbn, String author) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.lending = new ArrayList<>();
        this.bookStatus = BookStatus.AVAIlABLE;
    }
    
        public Book(String title, String isbn, String author) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.lending = new ArrayList<>();
        this.bookStatus = BookStatus.AVAIlABLE;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookId != null ? bookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.bookId == null && other.bookId != null) || (this.bookId != null && !this.bookId.equals(other.bookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Book[ id=" + bookId + " ]";
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
     * @return the lending
     */
    public ArrayList<LendAndReturn> getLending() {
        return lending;
    }

    /**
     * @param lending the lending to set
     */
    public void setLending(ArrayList<LendAndReturn> lending) {
        this.lending = lending;
    }

    /**
     * @return the bookStatus
     */
    public BookStatus getBookStatus() {
        return bookStatus;
    }

    /**
     * @param bookStatus the bookStatus to set
     */
    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
    
}
