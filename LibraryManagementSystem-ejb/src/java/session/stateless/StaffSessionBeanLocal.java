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
import javax.ejb.Local;

/**
 *
 * @author ryanl
 */
@Local
public interface StaffSessionBeanLocal {

    public void createStaff(Staff staff);

    public List<Staff> retrieveAllStaff();

    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException;

    public Staff staffLogin(String username, String password) throws InvalidLoginException;
    
}
