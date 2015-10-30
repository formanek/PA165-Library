package cz.muni.fi.pa165.projects.library.persistence;

import cz.muni.fi.pa165.projects.library.LibraryApplicationContext;
import cz.muni.fi.pa165.projects.library.persistence.dao.BookDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanItemDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.MemberDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.BookCondition;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Mosat
 */
@ContextConfiguration(classes = LibraryApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class LoanItemDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    public EntityManager em;

    @Autowired
    public LoanItemDao loanItemDao;
    
    @Autowired
    public BookDao bookDao;
    
    @Autowired
    public LoanDao loanDao;
    
    @Autowired 
    public MemberDao memberDao;

    private LoanItem loanItem1;
    private LoanItem loanItem2;
    private LoanItem loanItem3;
    
    private Member member1;
    
    private Loan loan1;
    private Loan loan2;
    
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;
    
    @BeforeMethod
    public void setUpMethod() {
        
        member1 = new Member();
        member1.setGivenName("Joshua");
        member1.setSurname("Bloch");
        member1.setEmail("effective@java.com");
        memberDao.create(member1);
        
        book1 = new Book();
        book1.setAuthor("author1");
        book1.setIsbn("0321356683");
        book1.setTitle("title1");
        
        book2 = new Book();
        book2.setAuthor("author2");
        book2.setIsbn("0596009208");
        book2.setTitle("title2");
        
        bookDao.create(book1);
        bookDao.create(book2);
        
        loanItem1 = new LoanItem();      
        Set<LoanItem> items1 = new HashSet<>();
        loanItem1.setBook(book1);
        loanItem1.setConditionBefore(BookCondition.AS_NEW);
        
        loanItem2 = new LoanItem();
        loanItem2.setBook(book2);
        loanItem2.setConditionBefore(BookCondition.FAIR);
        loanItem2.setConditionBefore(BookCondition.FAIR);
        
        loan1 = new Loan();
        loan1.setLoanDate(new Date());
        loan1.setMember(member1);
        loan1.setLoanItems(items1);
        
        items1.add(loanItem1);
        items1.add(loanItem2);

        loanDao.create(loan1);
        loanItem1.setLoan(loan1);
        loanItem2.setLoan(loan1);
        loanItemDao.create(loanItem1);
        loanItemDao.create(loanItem2);
        
        book3 = new Book();
        book3.setAuthor("author3");
        book3.setIsbn("0321857683");
        book3.setTitle("title3");
        
        bookDao.create(book3);
        
        book4 = new Book();
        book4.setAuthor("author4");
        book4.setIsbn("7229467683");
        book4.setTitle("title4");
        
        bookDao.create(book4);
        
        loanItem3 = new LoanItem();      
        Set<LoanItem> items2 = new HashSet<>();
        loanItem3.setBook(book3);
        loanItem3.setConditionBefore(BookCondition.VERY_GOOD);
        
        loan2 = new Loan();
        loan2.setLoanDate(new Date());
        loan2.setMember(member1);
        loan2.setLoanItems(items2);
        
        items2.add(loanItem3);

        loanDao.create(loan2);
        loanItem3.setLoan(loan2);
        loanItemDao.create(loanItem3);      
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void createNullLoanItemTest() {
        loanItemDao.create(null);
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void createLoanItemWithNullLoanTest() {
        LoanItem item = new LoanItem();
        item.setLoan(loanItem1.getLoan());      
        loanItemDao.create(item);
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void createLoanItemWithNullBookTest() {
        LoanItem item = new LoanItem();
        item.setBook(loanItem1.getBook());
        loanItemDao.create(item);
    }
    
    @Test(expectedExceptions = {PersistenceException.class})
    public void createLoanItemWithIdTest() {
        LoanItem item = new LoanItem();
        item.setId(loanItem1.getId());
        item.setBook(book4);
        item.setLoan(loanItem1.getLoan());
        item.setConditionBefore(BookCondition.AS_NEW);
        item.setConditionAfter(BookCondition.POOR);
        loanItemDao.create(item);
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void deleteNullLoanItemTest()
    {
        loanItemDao.delete(null);
    }
    
    @Test
    public void deleteLoanItemValidTest()
    {
        loanItemDao.delete(loanItem1);
        assertNull(loanItemDao.findById(loanItem1.getId()));
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void findLoanItemByIdNullTest(){
        loanItemDao.findById(null);      
    }
    
    @Test
    public void findLoanItemByIdNonExistentTest()
    {
        assertNull(loanItemDao.findById(42L));
    }
    
    @Test
    public void findLoanItemByIdValidTest(){
        LoanItem item = loanItemDao.findById(loanItem1.getId());
        assertEquals(item, loanItem1);
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void findLoanItemByLoanNullTest()
    {
        loanItemDao.findByLoan(null);
    }
    
    //@Test(expectedExceptions = {NullPointerException.class})
    public void findLoanItemByLoanWithLoanWithoutIdTest()
    {
        Loan loan = new Loan();
        loan.setMember(member1);
        loan.setLoanDate(new Date());
        loan.setLoanItems(new HashSet<LoanItem>());
        loanItemDao.findByLoan(loan);
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void findLoanItemByLoanNullest()
    {
        loanItemDao.findByLoan(null);
    }
    
    @Test
    public void findLoanItemByLoanValidTest()
    {
        List<LoanItem> items = loanItemDao.findByLoan(loan1);
        assertTrue(items.size() == 2);
        List<LoanItem> l = new LinkedList<>();
        l.add(loanItem1);
        l.add(loanItem2);
        assertTrue(items.containsAll(l));
    }

    
    @Test
    public void findLoanItemAllLoansTest()
    {
        List<LoanItem> items = loanItemDao.findAll();
        assertTrue(items.size() == 3);
        List<LoanItem> l = new LinkedList<>();
        l.add(loanItem1);
        l.add(loanItem2);
        l.add(loanItem3);
        assertTrue(items.containsAll(l));
    }
    
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void updateLoanItemWithNullTest()
    {
        loanItemDao.update(null);
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void updateLoanItemWithNullBookTest()
    {
        loanItem1.setBook(null);
        loanItemDao.update(loanItem1);
    }
    
    @Test(expectedExceptions = {NullPointerException.class})
    public void updateLoanItemWithNullLoanTest()
    {
        loanItem1.setLoan(null);
        loanItemDao.update(loanItem1);
    }
    
    @Test
    public void updateLoanItemWithValidBookTest()
    {
        loanItem2.setBook(book4);
        loanItemDao.update(loanItem2);
        assertEquals(book4, loanItemDao.findById(loanItem2.getId()).getBook());
    }
    
    @Test
    public void updateLoanItemWithNullBookConditionsTest()
    {
        loanItem2.setConditionAfter(null);
        loanItem2.setConditionBefore(null);
        loanItemDao.update(loanItem2);
        assertNull(loanItemDao.findById(loanItem2.getId()).getConditionAfter());
        assertNull(loanItemDao.findById(loanItem2.getId()).getConditionBefore());
    }
    
    @Test
    public void findAllLoanItemsAfterDeleteTest()
    { 
        loanItemDao.delete(loanItem1);
        loanItemDao.delete(loanItem2);
        loanItemDao.delete(loanItem3);       
        assertTrue(loanItemDao.findAll().isEmpty());
    }
    
    @Test
    public void updateLoanItemComplexTest() {
        Loan loan = loanItem1.getLoan();
        Set <LoanItem> loanItems = new HashSet<>(loan.getLoanItems());
        loan.getLoanItems();
        LoanItem item = new LoanItem();
        item.setBook(book4);
        item.setConditionBefore(BookCondition.VERY_GOOD);
        loan.setLoanDate(new Date());
        loan.setMember(member1);
        loan.setLoanItems(loanItems);
        
        loanItems.add(item);
        
        loanDao.update(loan);
        item.setLoan(loan);
        loanItemDao.update(item);
        
        List<LoanItem> items = loanItemDao.findByLoan(loan1);
        assertTrue(items.size() == 3);
        assertTrue(items.contains(item));
    }
}
