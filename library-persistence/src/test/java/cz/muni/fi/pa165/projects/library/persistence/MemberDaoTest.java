package cz.muni.fi.pa165.projects.library.persistence;

import cz.muni.fi.pa165.projects.library.LibraryApplicationContext;
import cz.muni.fi.pa165.projects.library.persistence.dao.MemberDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import javax.inject.Inject;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.testng.Assert.*;

/**
 * @author jkaspar
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class MemberDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    public MemberDao memberDao;

    private Member member;
    private Member existingMember;

    @BeforeMethod
    public void setUp() throws Exception {
        member = new Member();
        member.setGivenName("Jarek");
        member.setSurname("Kašpar");
        member.setEmail("jarek@kaspar.com");

        existingMember = new Member();
        existingMember.setGivenName("John");
        existingMember.setSurname("Black");
        existingMember.setEmail("john.black@gmail.com");
        memberDao.create(existingMember);
    }

    // Create tests

    @Test(expectedExceptions = NullPointerException.class)
    public void createMemberNullParameter() throws Exception {
        memberDao.create(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createMemberWithNullGivenName() throws Exception {
        member.setGivenName(null);
        memberDao.create(member);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createMemberWithNullSurname() throws Exception {
        member.setSurname(null);
        memberDao.create(member);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createMemberWithNullEmail() throws Exception {
        member.setEmail(null);
        memberDao.create(member);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createMemberWithInvalidEmail() throws Exception {
        member.setEmail("john.black.gmail.com");
        memberDao.create(member);
    }

    @Test
    public void createMember() throws Exception {
        int size = memberDao.findAll().size();
        memberDao.create(member);

        assertEquals(size + 1, memberDao.findAll().size(), "Unexpected number of members");
        assertTrue(memberDao.findAll().contains(member), "Missing member");
    }

    // Delete tests

    @Test(expectedExceptions = NullPointerException.class)
    public void deleteMemberNullParameter() throws Exception {
        memberDao.delete(null);
    }

    @Test
    public void deleteMember() throws Exception {
        memberDao.create(member);
        int size = memberDao.findAll().size();

        memberDao.delete(member);

        assertEquals(size - 1, memberDao.findAll().size(), "Unexpected number of members");
        assertFalse(memberDao.findAll().contains(member), "Missing member");
    }

    // Update tests

    @Test(expectedExceptions = NullPointerException.class)
    public void updateMemberNullParameter() throws Exception {
        memberDao.update(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateMemberWithNullGivenNameTest() throws Exception {
        existingMember.setGivenName(null);
        memberDao.update(existingMember);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateMemberWithNullSurnameTest() throws Exception {
        existingMember.setSurname(null);
        memberDao.update(existingMember);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void updateMemberWithNullEmailTest() throws Exception {
        existingMember.setEmail(null);
        memberDao.update(existingMember);
    }

    @Test
    public void updateMembersGivenName() throws Exception {
        final String newName = "NewName";
        existingMember.setGivenName(newName);
        memberDao.update(existingMember);

        assertEquals(newName, memberDao.findById(existingMember.getId()).getGivenName(), "Unexpected given name");
    }

    @Test
    public void updateMembersSurname() throws Exception {
        final String newName = "NewName";
        existingMember.setSurname(newName);
        memberDao.update(existingMember);

        assertEquals(newName, memberDao.findById(existingMember.getId()).getSurname(), "Unexpected surname");
    }

    @Test
    public void updateMembersEmail() throws Exception {
        final String newEmail = "new@email.com";
        existingMember.setEmail(newEmail);
        memberDao.update(existingMember);

        assertEquals(newEmail, memberDao.findById(existingMember.getId()).getEmail(), "Unexpected email");
    }

    // Find tests

    @Test(expectedExceptions = NullPointerException.class)
    public void findMemberByIdNullParameter() throws Exception {
        memberDao.findById(null);
    }

    @Test
    public void findById() throws Exception {
        assertEquals(existingMember, memberDao.findById(existingMember.getId()), "Unexpected member");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void findMemberByEmailNullParameter() throws Exception {
        memberDao.findByEmail(null);
    }

    @Test
    public void findMemberByEmail() throws Exception {
        assertEquals(existingMember, memberDao.findByEmail(existingMember.getEmail()), "Unexpected member");
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(memberDao.findAll().size(), 1);
        assertTrue(memberDao.findAll().contains(existingMember));
    }
}
