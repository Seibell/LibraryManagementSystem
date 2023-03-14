/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Named(value = "lendingManagedBean")
@RequestScoped
public class LendingManagedBean {

    @EJB
    private LendAndReturnSessionBeanLocal lendAndReturnSessionBean;

    @EJB
    private MemberSessionBeanLocal memberSessionBean;

    @EJB
    private BookSessionBeanLocal bookSessionBean;

    private List<LendAndReturn> lending;
    private String identityNo;
    private String isbn;

    public LendingManagedBean() {
    }

    public List<LendAndReturn> retrieveAllLendingFromBook() {
        return lendAndReturnSessionBean.retrieveLendAndReturnWithBook(isbn);
    }

    public List<LendAndReturn> retrieveAllLendingFromMember() {
        return lendAndReturnSessionBean.retrieveLendAndReturnWithMember(identityNo);
    }

    public void loadSelectedMember() {
        FacesContext context = FacesContext.getCurrentInstance();
        Member m = null;

        try {
            m = memberSessionBean.retrieveMemberByIdentityNo(identityNo);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load member"));
        }
    }

    public void loadSelectedBook() {
        FacesContext context = FacesContext.getCurrentInstance();
        Book b = null;

        try {
            b = bookSessionBean.retrieveBookByISBN(isbn);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load book"));
        }
    }

    public String convertDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = formatter.format(date);
        
        return format;
    }

    /**
     * @return the lending
     */
    public List<LendAndReturn> getLending() {
        return lending;
    }

    /**
     * @param lending the lending to set
     */
    public void setLending(List<LendAndReturn> lending) {
        this.lending = lending;
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
}
