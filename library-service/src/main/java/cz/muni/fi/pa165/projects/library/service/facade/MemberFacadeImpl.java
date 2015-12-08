package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.MemberDTO;
import cz.muni.fi.pa165.projects.library.dto.NewMemberDTO;
import cz.muni.fi.pa165.projects.library.facade.MemberFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.MemberService;
import java.util.Collection;
import java.util.Objects;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of facade layer for library member
 * 
 * @author David Formanek
 */
@Service
@Transactional
public class MemberFacadeImpl implements MemberFacade {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long registerMember(NewMemberDTO newMemberDTO) {
        Objects.requireNonNull(newMemberDTO, "null argument member");
        Objects.requireNonNull(newMemberDTO.getGivenName(), "member given name is null");
        requireNotEmpty(newMemberDTO.getGivenName(), "member given name");
        Objects.requireNonNull(newMemberDTO.getSurname(), "member surname is null");
        requireNotEmpty(newMemberDTO.getSurname(), "member surname");
        Objects.requireNonNull(newMemberDTO.getEmail(), "member email is null");
        requireNotEmpty(newMemberDTO.getEmail(), "member email");
        if (!newMemberDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("member email is not valid");
        }
        Member member = beanMappingService.mapTo(newMemberDTO, Member.class);
        memberService.create(member);
        return member.getId();
    }

    @Override
    public MemberDTO findMemberById(Long id) {
        Objects.requireNonNull(id, "member id is null");
        Member member = memberService.findById(id);
        return (member == null) ? null : beanMappingService.mapTo(member, MemberDTO.class);
    }

    @Override
    public MemberDTO findMemberByEmail(String email) {
        Objects.requireNonNull(email, "member email is null");
        if (email.isEmpty()) {
            throw new IllegalArgumentException("member email is empty");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("member email is not valid");
        }
        Member member = memberService.findByEmail(email);
        return (member == null) ? null : beanMappingService.mapTo(member, MemberDTO.class);
    }

    @Override
    public Collection<MemberDTO> getAllMembers() {
        return beanMappingService.mapTo(memberService.findAll(), MemberDTO.class);
    }

    @Override
    public void updateMember(MemberDTO memberDTO) {
        Objects.requireNonNull(memberDTO, "null argument member");
        Objects.requireNonNull(memberDTO.getGivenName(), "member given name is null");
        requireNotEmpty(memberDTO.getGivenName(), "member given name");
        Objects.requireNonNull(memberDTO.getSurname(), "member surname is null");
        requireNotEmpty(memberDTO.getSurname(), "member surname");
        Objects.requireNonNull(memberDTO.getEmail(), "member email is null");
        requireNotEmpty(memberDTO.getEmail(), "member email");
        if (!memberDTO.getEmail().contains("@")) {
            throw new IllegalArgumentException("member email is not valid");
        }
        memberService.update(beanMappingService.mapTo(memberDTO, Member.class));
    }

    private void requireNotEmpty(String str, String message) {
        if (str.trim().isEmpty()) {
            throw new IllegalArgumentException(message + " is empty");
        }
    }
}
