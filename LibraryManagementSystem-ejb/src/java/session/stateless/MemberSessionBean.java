/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Member;
import exception.EntityManagerException;
import exception.MemberNotFoundException;
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
public class MemberSessionBean implements MemberSessionBeanLocal {

    @PersistenceContext(unitName = "LibraryManagementSystem-ejbPU")
    private EntityManager em;

    @Override
    public void createMember(Member member) throws EntityManagerException {
        try {
            em.persist(member);
        } catch (Exception e) {
            throw new EntityManagerException("id exists");
        }
    }

    @Override
    public Member retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException {
        Query query = em.createQuery("SELECT m FROM Member m WHERE m.identityNo = :inIdentityNo");
        query.setParameter("inIdentityNo", identityNo);

        try {
            return (Member) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new MemberNotFoundException("Member not found!");
        }
    }

    @Override
    public List<Member> retrieveAllMember() {
        Query query = em.createQuery("SELECT m FROM Member m");
        return query.getResultList();
    }
    
    @Override
    public void updateMember(Member member) {
        em.merge(member);
    }
}
