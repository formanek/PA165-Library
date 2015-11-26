package cz.muni.fi.pa165.projects.library.service.facede;

import cz.muni.fi.pa165.projects.library.dto.MemberDTO;
import cz.muni.fi.pa165.projects.library.dto.NewMemberDTO;
import cz.muni.fi.pa165.projects.library.facade.MemberFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.MemberService;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import java.util.ArrayList;
import javax.inject.Inject;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author David Formanek
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MemberFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    MemberService memberService;

    @Inject
    MemberFacade memberFacade;
    
    private Member member;
    private MemberDTO memberDTO;
    private NewMemberDTO newMemberDTO;

    @BeforeClass
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        if (AopUtils.isAopProxy(memberFacade) && memberFacade instanceof Advised) {
            memberFacade = (MemberFacade) ((Advised) memberFacade).getTargetSource().getTarget();
        }
        ReflectionTestUtils.setField(memberFacade, "memberService", memberService);
    }

    @BeforeMethod
    public void setUpMethod() {
        member = new Member();
        member.setGivenName("Jan");
        member.setSurname("Novak");
        member.setEmail("jan@novak.cz");
        memberDTO = new MemberDTO();
        memberDTO.setGivenName(member.getGivenName());
        memberDTO.setSurname(member.getSurname());
        memberDTO.setEmail(member.getEmail());
        newMemberDTO = new NewMemberDTO();
        newMemberDTO.setGivenName(member.getGivenName());
        newMemberDTO.setSurname(member.getSurname());
        newMemberDTO.setEmail(member.getEmail());
    }
    
    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = ".*member.*")
    public void findByInvalidId() {
        memberFacade.findMemberById(null);
    }
    
    @Test
    public void findByValidId() {
        member.setId(10L);
        memberDTO.setId(10L);
        when(memberService.findById(10L)).thenReturn(member);
        MemberDTO memberDTOById = memberFacade.findMemberById(10L);
        assertEquals(memberDTOById, memberDTO);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByInvalidEmail() {
        memberFacade.findMemberByEmail("invalid");
    }
    
    @Test
    public void getAllMembers() {
        ArrayList<Member> members = new ArrayList<>();
        members.add(member);
        ArrayList<MemberDTO> memberDTOs = new ArrayList<>();
        memberDTOs.add(memberDTO);
        when(memberService.findAll()).thenReturn(members);
        assertEquals(memberDTOs, memberFacade.getAllMembers());
    }
    
    @Test
    public void findByValidEmail() {
        member.setId(10L);
        memberDTO.setId(10L);
        when(memberService.findByEmail("jan@novak.cz")).thenReturn(member);
        MemberDTO memberDTOByEmail = memberFacade.findMemberByEmail("jan@novak.cz");
        assertEquals(memberDTOByEmail, memberDTO);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void registerEmptySurname() {
        newMemberDTO.setSurname("");
        memberFacade.registerMember(newMemberDTO);
    }
    
    @Test
    public void registerMember() {
        memberFacade.registerMember(newMemberDTO);
        verify(memberService).create(member);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateInvalidEmail() {
        memberDTO.setId(10L);
        memberDTO.setEmail("invalid");
        memberFacade.updateMember(memberDTO);
    }
    
    @Test
    public void updateMember() {
        memberDTO.setId(10L);
        memberFacade.updateMember(memberDTO);
        verify(memberService).update(member);
    }
}
