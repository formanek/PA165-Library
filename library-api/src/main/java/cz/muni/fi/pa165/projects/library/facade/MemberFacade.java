package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.MemberDTO;
import cz.muni.fi.pa165.projects.library.dto.NewMemberDTO;
import java.util.Collection;

/**
 * Facade layer for library member
 *
 * @author David Formanek
 */
public interface MemberFacade {

    /**
     * Registers a new library member
     *
     * @param newMemberDTO new member DTO
     * @return assigned id for the new member
     * @throws NullPointerException for null argument or null given name, surname or email
     * @throws IllegalArgumentException for empty given name, surname or email or invalid email
     */
    Long registerMember(NewMemberDTO newMemberDTO);
    
    /**
     * Finds member with specified id
     * 
     * @param id specified id
     * @return found member DTO or null if not found
     * @throws NullPointerException for null argument
     */
    MemberDTO findMemberById(Long id);

    /**
     * Finds member with specified email
     * 
     * @param email specified email
     * @return found member DTO or null if not found
     * @throws NullPointerException for null argument
     * @throws IllegalArgumentException for empty or invalid email
     */
    MemberDTO findMemberByEmail(String email);

    /**
     * Receives all library members
     * 
     * @return all existing member DTOs
     */
    Collection<MemberDTO> getAllMembers();

    /**
     * Updates the specified member
     * 
     * @param memberDTO DTO of member to update
     * @throws NullPointerException for null argument or null given name, surname or email
     * @throws IllegalArgumentException for empty given name, surname or email or invalid email
     */
    void updateMember(MemberDTO memberDTO);
}
