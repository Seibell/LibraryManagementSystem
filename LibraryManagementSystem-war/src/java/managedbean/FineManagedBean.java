/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotFoundException;
import exception.LendingNotFoundException;
import exception.MemberNotFoundException;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.ejb.EJB;
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
@Named(value = "fineManagedBean")
@RequestScoped
public class FineManagedBean implements Serializable {

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBean;

    @EJB
    private MemberSessionBeanLocal memberSessionBean;

    @EJB
    private BookSessionBeanLocal bookSessionBean;

    private Long bookId;
    private String identityNo;
    private double fine = 0.0;

    /**
     * Creates a new instance of FineManagedBean
     */
    public FineManagedBean() {
    }

    /**
     * @return the bookId
     */
    public Long getBookId() {
        return bookId;
    }

    public double calculateFine() {
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
        String redirectLink = "/secret/view_fine_amount.xhtml";

        LendAndReturn lnr = null;

        try {
            lnr = lendAndReturnSessionBean.retrieveLendAndReturnWithBookAndMember(b, m);

            if (lnr.getReturnDate() == null) {
                if (lnr.getBook().getTitle().equals("The Hobbit") || lnr.getBook().getTitle().equals("Great Expectations") || lnr.getBook().getTitle().equals("Hamlet")) {
                    SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                    lnr.setLendDate(df.parse("2023-02-01"));
                }
                lnr.setReturnDate(new Date());

                int daysOverdue = (int) ChronoUnit.DAYS.between(lnr.getLendDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), lnr.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) - 14;

                if (daysOverdue > 0 && lnr.isFinePaid() == false) {
                    double fine = daysOverdue * 0.5;
                    lnr.setFineAmount(BigDecimal.valueOf(fine));
                }

                lendAndReturnSessionBean.updateLendAndReturn(lnr);
            }
        } catch (LendingNotFoundException e) {
            FacesMessage message = new FacesMessage("Cant find lending via bookId/identityNo");
            context.addMessage(null, message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Error occured");
            context.addMessage(null, message);
        }

        fine = Double.parseDouble(lnr.getFineAmount().toString());

        return fine;
    }

    public String payFine() {
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
        String redirectLink = "/secret/view_fine_amount.xhtml";

        LendAndReturn lnr = null;

        try {
            lnr = lendAndReturnSessionBean.retrieveLendAndReturnWithBookAndMember(b, m);

            if (lnr.isFinePaid() == false && !lnr.getFineAmount().equals(BigDecimal.ZERO)) {
                lnr.setFineAmount(BigDecimal.ZERO);
                lnr.setFinePaid(true);
                lendAndReturnSessionBean.updateLendAndReturn(lnr);
                redirectLink = "/secret/logged_in_homepage.xhtml?faces-redirect=true";
            } else if (lnr.getFineAmount().equals(BigDecimal.ZERO)) {
                FacesMessage message = new FacesMessage("Dont have fine to pay, please return book");
                context.addMessage(null, message);
            } else {
                //Something
            }

        } catch (LendingNotFoundException e) {
            FacesMessage message = new FacesMessage("Cant find lending via bookId/identityNo");
            context.addMessage(null, message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Error occured");
            context.addMessage(null, message);
        }

        return redirectLink;
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
     * @return the fine
     */
    public double getFine() {
        return fine;
    }

    /**
     * @param fine the fine to set
     */
    public void setFine(double fine) {
        this.fine = fine;
    }

}
