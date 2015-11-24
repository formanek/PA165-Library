package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.MemberDTO;
import cz.muni.fi.pa165.projects.library.dto.NewMemberDTO;
import cz.muni.fi.pa165.projects.library.facade.MemberFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.MemberService;
import java.util.Collection;
import javax.inject.Inject;

/**
 *
 * @author David Formanek
 */
public class MemberFacadeImpl implements MemberFacade {
    
    @Inject
    private MemberService memberService;
    
    @Inject
    private BeanMappingService beanMappingService;
    
    @Override
    public MemberDTO findMemberById(Long id) {
        Member member = memberService.findById(id);
        return (member == null) ? null : beanMappingService.mapTo(member, MemberDTO.class);
    }
    
    @Override
    public MemberDTO findMemberByEmail(String email) {
        Member member = memberService.findByEmail(email);
        return (member == null) ? null : beanMappingService.mapTo(member, MemberDTO.class);
    }
    
    @Override
    public Collection<MemberDTO> getAllMembers() {
        return beanMappingService.mapTo(memberService.findAll(), MemberDTO.class);
    }
    
    @Override
    public Long registerMember(NewMemberDTO newMemberDTO) {
        Member member = beanMappingService.mapTo(newMemberDTO, Member.class);
        memberService.create(member);
        return member.getId();
    }

    @Override
    public void updateMember(MemberDTO memberDTO) {
        memberService.update(beanMappingService.mapTo(memberDTO, Member.class));
    }
}
