package cz.muni.fi.pa165.projects.library.persistence;

import cz.muni.fi.pa165.projects.library.exceptions.EntityNotFoundException;
import cz.muni.fi.pa165.projects.library.exceptions.ReturnedLoanException;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanItemDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.MemberDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Milan Skipala
 */
public class LoanDaoTest {
    
    @PersistenceContext
    public EntityManager em;
    
    @Inject //Or @Autowired if Spring is used
    public LoanDao loanDao;
    
    @Inject //Or @Autowired if Spring is used
    public LoanItemDao loanItemDao;
    
    @Inject //Or @Autowired if Spring is used
    public MemberDao memberDao;
    
    

    private Set<LoanItem> loanItems;
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
        
        
        //l1 has not been returned yet -> return date is null
        l1 = new Loan();
        l1.setLoanItems(items1);
        Calendar c = Calendar.getInstance();
        c.set(2015, 1, 27);
        l1.setLoanDate(c.getTime());
        l1.setUserId(Long.valueOf(1));
        //l1.setMember(m);
        
        
        l2 = new Loan();
        l2.setLoanItems(items2);
        c.set(2015, 2, 25);
        l2.setLoanDate(c.getTime());
        l2.setUserId(Long.valueOf(2));
        c.set(2015, 2, 27);
        l2.setReturnDate(c.getTime());
        //l2.setMember(m);
        
        l3 = new Loan();
        l3.setLoanItems(items3);
        c.set(2015, 3, 24);
        l3.setLoanDate(c.getTime());
        l3.setUserId(Long.valueOf(3));
        c.set(2015, 3, 25);
        l3.setReturnDate(c.getTime());
        //l3.setMember(m);
        
        
        loanDao.create(l1);
        loanDao.create(l2);
        loanDao.create(l3);
        
    }
    private Set<LoanItem> createLoanItems(int count) {
        Set<LoanItem> set = new HashSet<>();
        for (int i = 0; i < count; i++) {
            LoanItem li = new LoanItem();
            set.add(li);
            loanItemDao.create(li);
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
        l4.setLoanDate(c.getTime());
        l4.setUserId(Long.valueOf(4));
        c.set(2015, 4, 25);
        l4.setReturnDate(c.getTime());
        
        int countBefore = loanDao.findAll().size();
        loanDao.create(l4);
        assertEquals(countBefore + 1, loanDao.findAll().size());
    }
    
    @Test(expectedExceptions = {NullPointerException.class, IllegalArgumentException.class})
    public void createNullLoanTest() {
        loanDao.create(null);
    }
    @Test(expectedExceptions = {NullPointerException.class, IllegalArgumentException.class})
    public void createLoanWithNullLoanItemsTest() {
        Loan l = new Loan();
        l.setLoanDate(l1.getLoanDate());
        l.setReturnDate(l1.getReturnDate());
        l.setUserId(123);
        loanDao.create(l);
    }
    @Test(expectedExceptions = {NullPointerException.class, IllegalArgumentException.class})
    public void createLoanWithNullMemberTest() {
        Loan l = new Loan();
        l.setLoanDate(l1.getLoanDate());
        l.setReturnDate(l1.getReturnDate());
        l.setLoanItems(l1.getLoanItems());
        l.setUserId(-1);
        loanDao.create(l);
    }
    @Test(expectedExceptions = {NullPointerException.class, IllegalArgumentException.class})
    public void createLoanWithNullLoanDateTest() {
        Loan l = new Loan();
        l.setReturnDate(l1.getReturnDate());
        l.setLoanItems(l1.getLoanItems());
        l.setUserId(123);
        loanDao.create(l);
    }

    @Test(expectedExceptions = EntityExistsException.class)
    public void createExistingLoanTest() {
        loanDao.create(l1);
    }
    
    @Test
    public void returnLoanBasicTest() {
        //loanDao.returnLoan(l1,l1.getLoanDate());
    }
    @Test(expectedExceptions = {ReturnedLoanException.class})
    public void returnAlreadyReturnedLoanTest() {
        //loanDao.returnLoan(l2,l2.getLoanDate());
    }
    
    @Test
    public void findByIdBasicTest() {
        assertEquals(loanDao.findById(l1.getId()),l1);
    }
    @Test(expectedExceptions = EntityNotFoundException.class)
    public void findByIdNotExistingLoanTest() {
        loanDao.findById(Long.MAX_VALUE);
    }
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void findByInvalidIdTest() {
        loanDao.findById(Long.MIN_VALUE);
    }
    @Test
    public void findAllBasicTest() {
        assertEquals(loanDao.findAll().size(),3);
        assertTrue(loanDao.findAll().contains(l1));
        assertTrue(loanDao.findAll().contains(l2));
        assertTrue(loanDao.findAll().contains(l3));
    }
    @Test
    public void allLoansOfMemberBasicTest() {
        assertEquals(loanDao.allLoansOfMember(m1).size(),3);
    }
    @Test(expectedExceptions = {NullPointerException.class, IllegalArgumentException.class})
    public void allLoansOfNullMemberTest() {
        loanDao.allLoansOfMember(null);
    }
    @Test
    public void allLoansOfNotExistingMemberTest() {
        Member m2 = new Member();
        m2.setGivenName("NotIn");
        m2.setSurname("Database");
        m2.setEmail("m2IsNotIn@database.com");
        assertEquals(loanDao.allLoansOfMember(m2).size(),0);
    }
    
    public void allLoansOfMemberAfterNewLoanWasAddedTest() {
        Collection<Loan> col = loanDao.allLoansOfMember(m1);
        
        Set<LoanItem> items4 = createLoanItems(1);
        Calendar c = Calendar.getInstance();
        Loan l4 = new Loan();
        l4.setLoanItems(items4);
        c.set(2015, 4, 24);
        l4.setLoanDate(c.getTime());
        l4.setUserId(Long.valueOf(4));
        c.set(2015, 4, 25);
        l4.setReturnDate(c.getTime());
        loanDao.create(l4);
        
        Collection<Loan> col2 = loanDao.allLoansOfMember(m1);
        assertEquals(col.size()+1,col2.size());
    }
    
    /*
    createLoanBasicTest
    createNullLoanTest
    createLoanWithNull[LoanItems, UserId, loanDate]Test
    createExistingLoanTest
    
    returnLoanBasicTest
    returnAlreadyReturnedLoanTest
    
    findByIdBasicTest
    findByIdNotExistingLoanTest
    findByInvalidIdTest
    
    findAllBasicTest
    
    allLoansOfMemberBasicTest
    allLoansOfNotExistingMemberTest
    */
}
