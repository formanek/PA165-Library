package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.List;

/**
 * Service layer for library members
 *
 * @author David Formanek
 */
public interface MemberService {

    /**
     * Creates a new library member
     *
     * @param member to be created
     * @throws NullPointerException for null argument
     */
    void create(Member member);

    /**
     * Deletes specified member
     *
     * @param member member to delete
     * @throws NullPointerException for null argument
     */
    void delete(Member member);

    /**
     * Finds member with given id
     *
     * @param id given id
     * @return found member or null if the member does not exist
     * @throws NullPointerException for null argument
     */
    Member findById(Long id);

    /**
     * Finds member with given email
     *
     * @param email given email
     * @return found member or null if the member does not exist
     * @throws NullPointerException for null argument
     */
    Member findByEmail(String email);

    /**
     * Finds all library members
     *
     * @return all existing members
     */
    List<Member> findAll();

    /**
     * Updates the specified member
     *
     * @param member member to update
     * @throws NullPointerException for null argument
     */
    void update(Member member);
}
