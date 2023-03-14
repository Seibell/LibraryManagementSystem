/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Member;
import exception.MemberNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.stateless.MemberSessionBeanLocal;

/**
 *
 * @author ryanl
 */
@Named(value = "registerMemberManagedBean")
@RequestScoped
public class RegisterMemberManagedBean {

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the gender
     */
    public Character getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Character gender) {
        this.gender = gender;
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @EJB
    private MemberSessionBeanLocal memberSessionBean;

    private List<Member> members = new ArrayList<>();

    private String firstName;
    private String lastName;
    private Character gender;
    private Integer age;
    private String identityNo;
    private String phone;
    private String address;

    /**
     * Creates a new instance of RegisterMemberManagedBean
     */
    public RegisterMemberManagedBean() {
    }

    public List<Member> retrieveAllMembers() {
        return memberSessionBean.retrieveAllMember();
    }
    
    public List<Member> getMembers() {
        return this.members;
    }

    public String registerMember() {
        Member member = new Member();

        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setGender(gender);
        member.setAge(age);
        member.setIdentityNo(identityNo);
        member.setPhone(phone);
        member.setAddress(address);

        FacesContext context = FacesContext.getCurrentInstance();

        String redirectLink = "/secret/register_member.xhtml";

        try {
            memberSessionBean.createMember(member);
            FacesMessage message = new FacesMessage("Successfully created member: " + firstName + " " + lastName);
            context.addMessage("successfail", message);
            redirectLink = "/secret/logged_in_homepage.xhtml?faces-redirect=true";

        } catch (Exception e) {
            System.out.println("member already exists!");
            FacesMessage message = new FacesMessage("Member ID already exists!");
            context.addMessage("successfail", message);
        }
        return redirectLink;
    }

    public void loadSelectedMember() {
        FacesContext context = FacesContext.getCurrentInstance();
        Member m = null;

        try {
            m = memberSessionBean.retrieveMemberByIdentityNo(identityNo);
            firstName = m.getFirstName();
            lastName = m.getLastName();
            gender = m.getGender();
            age = m.getAge();
            phone = m.getPhone();
            address = m.getAddress();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load member"));
        }
    } //end loadSelectedCustomer

    public String updateMember() {
        FacesContext context = FacesContext.getCurrentInstance();
        String redirectLink = "/secret/edit_member.xhtml";

        try {
            Member m = memberSessionBean.retrieveMemberByIdentityNo(identityNo);
            m.setFirstName(firstName);
            m.setLastName(lastName);
            m.setAge(age);
            m.setAddress(address);
            m.setGender(gender);
            m.setPhone(phone);

            memberSessionBean.updateMember(m);
            redirectLink = "/secret/logged_in_homepage.xhtml?faces-redirect=true";

        } catch (MemberNotFoundException e) {

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to update customer"));
        }

        context.addMessage(null, new FacesMessage("Success", "Successfully updated customer"));
        return redirectLink;
    } //end updateCustomer

    /**
     * @param members the members to set
     */
    public void setMembers(List<Member> members) {
        this.members = members;
    }

}
