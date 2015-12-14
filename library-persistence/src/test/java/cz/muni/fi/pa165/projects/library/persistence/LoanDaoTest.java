package cz.muni.fi.pa165.projects.library.persistence;

import cz.muni.fi.pa165.projects.library.LibraryApplicationContext;
import cz.muni.fi.pa165.projects.library.dto.BookCondition;
import cz.muni.fi.pa165.projects.library.persistence.dao.BookDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanItemDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.MemberDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

/**
 *
 * @author Milan Skipala
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class LoanDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Inject
    public LoanDao loanDao;

    @Inject
    public LoanItemDao loanItemDao;

    @Inject
    public MemberDao memberDao;

    @Inject
    public BookDao bookDao;

    private Loan l1;
    private Loan l2;
    private Loan l3;
    private Member m1;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Set<LoanItem> items1 = createLoanItems(4);
        Set<LoanItem> items2 = createLoanItems(5);
        Set<LoanItem> items3 = createLoanItems(0);

        m1 = new Member();
        m1.setGivenName("Joshua");
        m1.setSurname("Bloch");
        m1.setEmail("effective@java.com");
        memberDao.create(m1);

        l1 = new Loan();
        l1.setLoanItems(items1);
        Calendar c = Calendar.getInstance();
        c.set(2015, 1, 27);
        l1.setLoanTimestamp(new Timestamp(c.getTimeInMillis()));
        
        
        l1.setMember(m1);

        l2 = new Loan();
        l2.setLoanItems(items2);
        c.set(2015, 2, 25);
        l2.setLoanTimestamp(new Timestamp(c.getTimeInMillis()));
        c.set(2016, 2, 27);
        l2.setReturnTimestamp(new Timestamp(c.getTimeInMillis()));
        
        l2.setMember(m1);

        l3 = new Loan();
        l3.setLoanItems(items3);
        c.set(2015, 3, 24);
        l3.setLoanTimestamp(new Timestamp(c.getTimeInMillis()));
        
        c.set(2016, 3, 25);
        l3.setReturnTimestamp(new Timestamp(c.getTimeInMillis()));
        
        l3.setMember(m1);

        loanDao.create(l1);
        loanDao.create(l2);
        loanDao.create(l3);

        persistLoanItems(items1, l1);
        persistLoanItems(items2, l2);
        persistLoanItems(items3, l3);

    }

    private void persistLoanItems(Set<LoanItem> items, Loan loan) {
        for (LoanItem item : items) {
            item.setLoan(loan);
            loanItemDao.create(item);
        }
    }

    private Set<LoanItem> createLoanItems(int count) {
        Set<LoanItem> set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            LoanItem li = new LoanItem();
            Book b = new Book();
            b.setAuthor("Author");
            b.setIsbn("0321356683");
            b.setTitle("Title");
            b.setLoanable(true);
            bookDao.create(b);
            li.setBook(b);
            li.setConditionBefore(BookCondition.AS_NEW);
            set.add(li);
        }
        return set;
    }

    @Test
    public void createLoanBasicTest() {
        Set<LoanItem> items4 = createLoanItems(1);
        Calendar c = Calendar.getInstance();
        Loan l4 = new Loan();
        l4.setLoanItems(items4);
        c.set(2015, 4, 24);
        l4.setLoanTimestamp(new Timestamp(c.getTimeInMillis()));
        l4.setMember(m1);
        l4.setReturnTimestamp(new Timestamp(c.getTimeInMillis()));

        int countBefore = loanDao.findAll().size();
        loanDao.create(l4);
        assertEquals(countBefore + 1, loanDao.findAll().size());
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createNullLoanTest() {
        loanDao.create(null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createLoanWithNullLoanItemsTest() {
        Loan l = new Loan();
        l.setLoanTimestamp(l2.getLoanTimestamp());
        l.setReturnTimestamp(l2.getReturnTimestamp());
        l.setMember(m1);
        l.setLoanItems(null);
        loanDao.create(l);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createLoanWithNullMemberTest() {
        Loan l = new Loan();
        l.setLoanTimestamp(l1.getLoanTimestamp());
        l.setReturnTimestamp(l1.getReturnTimestamp());
        l.setLoanItems(l1.getLoanItems());
        loanDao.create(l);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void createLoanWithNullLoanDateTest() {
        Loan l = new Loan();
        l.setReturnTimestamp(l1.getReturnTimestamp());
        l.setLoanItems(l1.getLoanItems());
        l.setMember(m1);
        loanDao.create(l);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void createLoanWithBadReturnDate() {
        Loan l = new Loan();
        l.setLoanTimestamp(l2.getReturnTimestamp());
        l.setReturnTimestamp(l2.getLoanTimestamp());
        l.setLoanItems(l2.getLoanItems());
        l.setMember(m1);
        loanDao.create(l);
    }

    @Test
    public void findByIdBasicTest() {
        Loan result = loanDao.findById(l1.getId());
        assertEquals(result, l1);
    }

    @Test
    public void findByIdNotExistingLoanTest() {
        assertNull(loanDao.findById(Long.MAX_VALUE));
    }

    @Test
    public void findAllBasicTest() {
        assertEquals(loanDao.findAll().size(), 3);
        assertTrue(loanDao.findAll().contains(l1));
        assertTrue(loanDao.findAll().contains(l2));
        assertTrue(loanDao.findAll().contains(l3));
    }

    @Test
    public void allLoansOfMemberBasicTest() {
        assertEquals(loanDao.allLoansOfMember(m1).size(), 3);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void allLoansOfNullMemberTest() {
        loanDao.allLoansOfMember(null);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void allLoansOfNotExistingMemberTest() {
        Member m2 = new Member();
        m2.setGivenName("NotIn");
        m2.setSurname("Database");
        m2.setEmail("m2IsNotIn@database.com");
        loanDao.allLoansOfMember(m2);
    }

    @Test
    public void allLoansOfMemberAfterNewLoanWasAddedTest() {
        Collection<Loan> col = loanDao.allLoansOfMember(m1);

        Set<LoanItem> items4 = createLoanItems(1);
        Calendar c = Calendar.getInstance();
        Loan l4 = new Loan();
        l4.setLoanItems(items4);
        c.set(2015, 4, 24);
        l4.setLoanTimestamp(new Timestamp(c.getTimeInMillis()));
        l4.setMember(m1);
        c.set(2015, 4, 25);
        l4.setReturnTimestamp(new Timestamp(c.getTimeInMillis()));
        assertTrue(l4.getLoanTimestamp().before(l4.getReturnTimestamp()));
        loanDao.create(l4);

        Collection<Loan> col2 = loanDao.allLoansOfMember(m1);
        assertEquals(col.size() + 1, col2.size());
    }
    
    @Test
    public void updateBasicTest() {
        loanDao.update(l1);
        Calendar c = Calendar.getInstance();
        c.set(2011,1,20);
        l1.setLoanTimestamp(new Timestamp(c.getTimeInMillis()));
        loanDao.update(l1);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void updateWithNullTest() {
        loanDao.update(null);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void updateWithWrongLoanDateTest() {
        Calendar c = Calendar.getInstance();
        c.set(2020,1,20);
        l2.setLoanTimestamp(new Timestamp(c.getTimeInMillis()));
        loanDao.update(l2);
    }
    
    @Test(expectedExceptions = DataAccessException.class)
    public void updateWithWrongReturnDateTest() {
        Calendar c = Calendar.getInstance();
        c.set(2011,1,20);
        l1.setReturnTimestamp(new Timestamp(c.getTimeInMillis()));
        loanDao.update(l1);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void updateWithNullMemberTest() {
        l1.setMember(null);
        loanDao.update(l1);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void updateWithNullLoanItemsTest() {
       l1.setLoanItems(null);
       loanDao.update(l1);
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void updateWithNullLoanDateTest() {
        l1.setLoanTimestamp(null);
        loanDao.update(l1);
    }
    
    @Test
    public void updateWithNullReturnDateTest() {
        l1.setReturnTimestamp(null);
        loanDao.update(l1);
    }
    
    @Test
    public void deleteBasicTest() {
        loanDao.delete(l1);
        assertNull(loanDao.findById(l1.getId()));
    }
    
    @Test(expectedExceptions = NullPointerException.class)
    public void deleteNullTest() {
        loanDao.delete(null);
    }
}
