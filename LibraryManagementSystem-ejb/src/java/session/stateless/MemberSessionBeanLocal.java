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
import javax.ejb.Local;

/**
 *
 * @author ryanl
 */
@Local
public interface MemberSessionBeanLocal {

    public List<Member> retrieveAllMember();

    public void createMember(Member member) throws EntityManagerException;

    public Member retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException;

    public void updateMember(Member member);
    
}
