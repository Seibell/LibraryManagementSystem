/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import enume.BookStatus;
import exception.BookNotAvailableException;
import exception.LendingNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ryanl
 */
@Stateless
public class LendAndReturnSessionBean implements LendAndReturnSessionBeanLocal {

    @EJB
    private MemberSessionBeanLocal memberSessionBean;

    @EJB
    private BookSessionBeanLocal bookSessionBean;

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;

    @Override
    public void createLendAndReturn(Book b, Member m) throws BookNotAvailableException {

        if (b == null || m == null) {
            return;
        }

        if (b.getBookStatus().equals(BookStatus.AVAIlABLE)) {
            LendAndReturn lnr = new LendAndReturn();

            b.setBookStatus(BookStatus.NOT_AVAILABLE);
            lnr.setBook(b);
            lnr.setMember(m);
            lnr.setLendDate(new Date());
            lnr.setFineAmount(BigDecimal.ZERO);
            lnr.setReturnDate(null);

            b.getLending().add(lnr);
            m.getLending().add(lnr);

            em.persist(lnr);

            //update book status
            em.merge(b);
        } else {
            throw new BookNotAvailableException("book not available for lending!");
        }
    }

    @Override
    public LendAndReturn retrieveLendAndReturnWithBookAndMember(Book b, Member m) throws LendingNotFoundException {

        Query query = em.createQuery("SELECT lnr FROM LendAndReturn lnr WHERE lnr.member = :inMember AND lnr.book = :inBook");
        query.setParameter("inMember", m);
        query.setParameter("inBook", b);

        try {
            LendAndReturn latest = null;
            List<LendAndReturn> allLending = query.getResultList();

            for (LendAndReturn lnr : allLending) {
                latest = lnr;
            }

            return latest;
        } catch (Exception e) {
            throw new LendingNotFoundException("Lending not found!");
        }
    }

    @Override
    public List<LendAndReturn> retrieveLendAndReturnWithBook(String isbn) {
        Query query = em.createQuery("SELECT lnr FROM LendAndReturn lnr WHERE lnr.book.isbn = :inIsbn");
        query.setParameter("inIsbn", isbn);

        return query.getResultList();
    }

    @Override
    public List<LendAndReturn> retrieveLendAndReturnWithMember(String identityNo) {
        Query query = em.createQuery("SELECT lnr FROM LendAndReturn lnr WHERE lnr.member.identityNo = :inId");
        query.setParameter("inId", identityNo);

        return query.getResultList();
    }

    @Override
    public void updateLendAndReturn(LendAndReturn lnr) {
        em.merge(lnr);
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
