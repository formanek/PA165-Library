package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.MemberDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 *
 * @author David Formanek
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MemberServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MemberDao memberDao;

    @Inject
    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeMethod
    public void setUpMethod() {
        MockitoAnnotations.initMocks(this);
        member = new Member();
        member.setGivenName("Jan");
        member.setSurname("Novak");
        member.setEmail("jan@novak.cz");
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = ".*member.*")
    public void createNullMember() {
        memberService.create(null);
        verifyZeroInteractions(memberDao);
    }

    @Test
    public void createValidMember() {
        memberService.create(member);
        verify(memberDao).create(member);
        verifyNoMoreInteractions(memberDao);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = ".*member.*")
    public void deleteNullMember() {
        memberService.delete(null);
        verifyZeroInteractions(memberDao);
    }

    @Test
    public void deleteValidMember() {
        member.setId(10L);
        memberService.delete(member);
        verify(memberDao).delete(member);
        verifyNoMoreInteractions(memberDao);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = ".*member.*")
    public void updateNullMember() {
        memberService.update(null);
        verifyZeroInteractions(memberDao);
    }

    @Test
    public void updateValidMember() {
        member.setId(10L);
        memberService.update(member);
        verify(memberDao).update(member);
        verifyNoMoreInteractions(memberDao);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = ".*member.*")
    public void findMemberWithNullId() {
        memberService.findById(null);
        verifyZeroInteractions(memberDao);
    }

    @Test
    public void findMemberById() {
        member.setId(10L);
        when(memberDao.findById(10L)).thenReturn(member);
        assertEquals(memberService.findById(10L), member);
        verify(memberDao).findById(10L);
        verifyNoMoreInteractions(memberDao);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = ".*member.*")
    public void findMemberWithNullEmail() {
        memberService.findByEmail(null);
        verifyZeroInteractions(memberDao);
    }

    @Test
    public void findMemberByEmail() {
        member.setId(10L);
        when(memberDao.findByEmail("jan@novak.cz")).thenReturn(member);
        assertEquals(memberService.findByEmail("jan@novak.cz"), member);
        verify(memberDao).findByEmail("jan@novak.cz");
        verifyNoMoreInteractions(memberDao);
    }

    @Test
    public void findAllMembers() {
        memberService.findAll();
        verify(memberDao).findAll();
        verifyNoMoreInteractions(memberDao);
    }
}
