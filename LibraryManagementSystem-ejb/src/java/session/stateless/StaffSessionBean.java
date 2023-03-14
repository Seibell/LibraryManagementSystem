/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Staff;
import exception.InvalidLoginException;
import exception.StaffNotFoundException;
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
public class StaffSessionBean implements StaffSessionBeanLocal {

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;

    @Override
    public void createStaff(Staff staff) {
        em.persist(staff);
    }

    @Override
    public List<Staff> retrieveAllStaff() {
        Query query = em.createQuery("SELECT s FROM Staff s");
        return query.getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }
    @Override
    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException {
        Query query = em.createQuery("Select s FROM Staff s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);
        
        try {
            return (Staff) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff not found!");
        }
    }
    @Override
    public Staff staffLogin(String username, String password) throws InvalidLoginException {
        try {
            Staff staff = retrieveStaffByUsername(username);
            
            if (staff.getPassword().equals(password)) {
                return staff;
            } else {
                throw new InvalidLoginException("Invalid Password");
            }
        } catch (StaffNotFoundException e) {
            throw new InvalidLoginException("Username does not exist");
        }
    }
}
