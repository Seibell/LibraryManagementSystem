/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import exception.BookNotAvailableException;
import exception.LendingNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ryanl
 */
@Local
public interface LendAndReturnSessionBeanLocal {

    public void createLendAndReturn(Book b, Member m) throws BookNotAvailableException;

    public void updateLendAndReturn(LendAndReturn lnr);

    public LendAndReturn retrieveLendAndReturnWithBookAndMember(Book b, Member m) throws LendingNotFoundException;

    public List<LendAndReturn> retrieveLendAndReturnWithBook(String isbn);

    public List<LendAndReturn> retrieveLendAndReturnWithMember(String identityNo);
    
}
