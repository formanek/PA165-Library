package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.MemberDTO;
import cz.muni.fi.pa165.projects.library.dto.NewMemberDTO;
import java.util.Collection;

/**
 *
 * @author David Formanek
 */
public interface MemberFacade {
    MemberDTO findMemberById(Long id);
    MemberDTO findMemberByEmail(String email);
    Collection<MemberDTO> getAllMembers();
    Long registerMember(NewMemberDTO newMemberDTO);
    void updateMember(MemberDTO memberDTO);
}
