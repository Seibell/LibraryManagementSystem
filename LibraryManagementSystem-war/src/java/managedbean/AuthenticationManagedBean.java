/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Staff;
import exception.StaffNotFoundException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import session.stateless.StaffSessionBeanLocal;

/**
 *
 * @author ryanl
 */
@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB
    private StaffSessionBeanLocal staffSessionBean;

    private String username;
    private String password;
    private Long staffId = (long) -1;

    /**
     * Creates a new instance of AuthenticationManagedBean
     */
    public AuthenticationManagedBean() {
    }

    public String registerMember() {
        return "/secret/register_member.xhtml?faces-redirect=true";
    }

    public String lendBook() {
        return "/secret/lend_book.xhtml?faces-redirect=true";
    }

    public String returnBook() {
        return "/secret/return_book.xhtml?faces-redirect=true";
    }

    public String viewFineAmount() {
        return "/secret/view_fine_amount.xhtml?faces-redirect=true";
    }

    public String viewAllBooks() {
        return "/secret/view_all_books.xhtml?faces-redirect=true";
    }

    public String homepage() {
        return "/secret/logged_in_homepage.xhtml?faces-redirect=true";
    }

    public String login() {
        //by right supposed to use a session bean to
        //do validation here
        //...

        Staff staff = null;

        try {
            staff = staffSessionBean.staffLogin(username, password);
            setStaffId(staff.getStaffId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (staff != null) {

            //Login success
            return "/secret/logged_in_homepage.xhtml?faces-redirect=true";
        } else {
            username = null;
            password = null;
            setStaffId((long) -1);
            FacesMessage message = new FacesMessage("Invalid username/password");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("loginGrowl", message);
            return "login.xhtml";
        }
    }

    public String logout() {
        username = null;
        password = null;
        staffId = (long) -1;

        return "/login.xhtml?faces-redirect=true";
    } //end logout

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the staffId
     */
    public Long getStaffId() {
        return staffId;
    }

    /**
     * @param staffId the staffId to set
     */
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

}
