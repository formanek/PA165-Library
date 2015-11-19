package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.MemberDTO;
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
    public void registerMember(MemberDTO memberDTO) {
        Member member = beanMappingService.mapTo(memberDTO, Member.class);
        memberService.create(member);
        memberDTO.setId(member.getId());
    }
}
