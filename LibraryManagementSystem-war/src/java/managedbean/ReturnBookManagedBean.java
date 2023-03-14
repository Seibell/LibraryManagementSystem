/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import enume.BookStatus;
import exception.BookNotFoundException;
import exception.LendingNotFoundException;
import exception.MemberNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
@Named(value = "returnBookManagedBean")
@RequestScoped
public class ReturnBookManagedBean {

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBean;

    @EJB
    private MemberSessionBeanLocal memberSessionBean;

    @EJB
    private BookSessionBeanLocal bookSessionBean;

    private Long bookId;
    private String identityNo;

    /**
     * Creates a new instance of ReturnBookManagedBean
     */
    public ReturnBookManagedBean() {
    }

    public String returnBook() {
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
        String redirectLink = "/secret/return_book.xhtml";

        LendAndReturn lnr = null;

        try {
            lnr = lendAndReturnSessionBean.retrieveLendAndReturnWithBookAndMember(b, m);

//            For testing
//            if (lnr.getBook().getTitle().equals("The Hobbit") || lnr.getBook().getTitle().equals("Great Expectations") || lnr.getBook().getTitle().equals("Hamlet")) {
//                SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
//                lnr.setLendDate(df.parse("2023-02-01"));
//            }

            lnr.setReturnDate(new Date());

            int daysOverdue = (int) ChronoUnit.DAYS.between(lnr.getLendDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), lnr.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) - 14;

            if (daysOverdue > 0 && lnr.isFinePaid() == false) {
                double fine = daysOverdue * 0.5;
                lnr.setFineAmount(BigDecimal.valueOf(fine));
            }

            lendAndReturnSessionBean.updateLendAndReturn(lnr);

            if (lnr.getFineAmount().equals(BigDecimal.ZERO) || lnr.isFinePaid() == true) {
                b.setBookStatus(BookStatus.AVAIlABLE);
                bookSessionBean.updateBook(b);
                FacesMessage message = new FacesMessage("Successfully returned book: " + b.getTitle());
                context.addMessage("returnGrowl", message);

                redirectLink = "/secret/logged_in_homepage.xhtml?faces-redirect=true";
            } else {
                FacesMessage message = new FacesMessage("Failed to return book, need to pay fine of: " + lnr.getFineAmount());
                context.addMessage("returnGrowl", message);
            }
        } catch (LendingNotFoundException e) {
            FacesMessage message = new FacesMessage("Cant find lending via bookId/identityNo");
            context.addMessage("returnGrowl", message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Error has occured!");
            context.addMessage("returnGrowl", message);
        }

        return redirectLink;
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

}
