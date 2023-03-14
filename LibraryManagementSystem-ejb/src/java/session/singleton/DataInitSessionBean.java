/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.singleton;

import entity.Book;
import entity.Member;
import entity.Staff;
import exception.EntityManagerException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.stateless.BookSessionBeanLocal;
import session.stateless.MemberSessionBeanLocal;
import session.stateless.StaffSessionBeanLocal;

/**
 *
 * @author ryanl
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private StaffSessionBeanLocal staffSessionBean;

    @EJB
    private MemberSessionBeanLocal memberSessionBean;
    
    @EJB
    private BookSessionBeanLocal bookSessionBean;
    
    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;


    @PostConstruct
    public void postConstruct() {

        //Create Staff
        if (staffSessionBean.retrieveAllStaff().isEmpty()) {
            Staff staff1 = new Staff((long) 1, "Eric", "Some", "eric", "password");
            staffSessionBean.createStaff(staff1);

            Staff staff2 = new Staff((long) 2, "Sarah", "Brighman", "sarah", "password");
            staffSessionBean.createStaff(staff2);

            em.flush();
        }

        //Create Books
        if (bookSessionBean.retrieveAllBooks().isEmpty()) {
            Book book1 = new Book((long) 1, "Anna Karenina", "0451528611", "Leo Tolstoy");
            Book book2 = new Book((long) 2, "Madame Bovary", "979-8649042031", "Gustave Flaubert");
            Book book3 = new Book((long) 3, "Hamlet", "1980625026", "William Shakespeare");
            Book book4 = new Book((long) 4, "The Hobbit", "9780007458424", "J R R Tolkien");
            Book book5 = new Book((long) 5, "Great Expectations", "1521853592", "Charles Dickens");
            Book book6 = new Book((long) 6, "Pride and Prejudice", "979-8653642272", "Jane Austen");
            Book book7 = new Book((long) 7, "Wuthering Heights", "3961300224", "Emily Brontë");

            bookSessionBean.createBook(book1);
            bookSessionBean.createBook(book2);
            bookSessionBean.createBook(book3);
            bookSessionBean.createBook(book4);
            bookSessionBean.createBook(book5);
            bookSessionBean.createBook(book6);
            bookSessionBean.createBook(book7);

            em.flush();
        }

        //Create Member
        if (memberSessionBean.retrieveAllMember().isEmpty()) {
            Member member1 = new Member((long) 1, "Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East Ave 3");
            Member member2 = new Member((long) 2, "Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Computing Dr");
            
            try {
                memberSessionBean.createMember(member1);
                memberSessionBean.createMember(member2);
            } catch (EntityManagerException e) {
                System.out.println("should never happen");
            }
            
            em.flush();
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
