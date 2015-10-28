package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.List;

/**
 * @author David Formanek
 */
public interface MemberDao {

    /**
     * Persists the specified library member
     * @param member specified member
     */
    public void create(Member member);

    /**
     * Deletes the specified library member
     * @param member specified member
     */
    public void delete(Member member);

    /**
     * Finds the library member with specified identifier if exists
     * @param id member identifier
     * @return the found member or null if it does not exist
     */
    public Member findById(Long id);
    
    /**
     * Finds the library member with specified email set
     * @param email email of the searched member
     * @return the found member if there is exactly one, null if no such member exists
     */
    public Member findByEmail(String email);
    
    /**
     * Finds all persisted library members
     * @return list of all members (empty list if there are no members)
     */
    public List<Member> findAll();
    
    /**
     * Updates the specified previously persister library member
     * @param member specified member
     */
    public void update(Member member);
}
