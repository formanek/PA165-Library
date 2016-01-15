package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.dto.BookCondition;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanItemDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 *
 * @author Jan Mosat
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanServiceTest extends AbstractTestNGSpringContextTests
{
    @Inject
    private BeanMappingService beanMappingService;

    @Mock
    LoanDao loanDao;
    
    @Mock
    LoanItemDao loanItemDao;
    
    @Inject
    @InjectMocks
    private LoanService loanService;
    
    private Loan loan;

    private Member member;

    private LoanItem loanItem;

    private Timestamp loanTimestamp = Timestamp.valueOf("2014-10-23 10:10:10.0");

    @BeforeMethod
    public void setUpMethod(){
        MockitoAnnotations.initMocks(this);
        loan = new Loan();
        member = new Member();
        member.setGivenName("Joshua");
        member.setSurname("Bloch");
        member.setEmail("effective@java.com");

        Book book = new Book();
        book.setAuthor("author1");
        book.setIsbn("0321356683");
        book.setTitle("title1");

        loanItem = new LoanItem();
        Set<LoanItem> items = new HashSet<>();
        loanItem.setBook(book);
        loanItem.setConditionBefore(BookCondition.AS_NEW);

        loan = new Loan();
        loan.setLoanTimestamp(loanTimestamp);
        loan.setMember(member);
        loan.setLoanItems(items);

        items.add(loanItem);
        loanItem.setLoan(loan);
    }

    @Test
    public void createLoanBasicTest() {
        loanService.create(loan);
        ArgumentCaptor<Loan> argument = ArgumentCaptor.forClass(Loan.class);
        verify(loanDao).create(argument.capture());
        assertNotEquals(loanTimestamp, argument.getValue().getLoanTimestamp());
        verify(loanItemDao).create(loanItem);
        verifyNoMoreInteractions(loanItemDao);
        verifyNoMoreInteractions(loanDao);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Loan.*")
    public void createNullLoan() {
        loanService.create(null);
        verifyZeroInteractions(loanDao);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Loan.*")
    public void deleteNullMember() {
        loanService.delete(null);
        verifyZeroInteractions(loanDao);
    }

    @Test
    public void deleteValidMember() {
        loanService.delete(loan);
        verify(loanDao).delete(loan);
        verifyNoMoreInteractions(loanDao);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Id.*")
    public void findLoanWithNullId() {
        loanService.findById(null);
        verifyZeroInteractions(loanDao);
    }

    @Test
    public void findMemberById() {
        loan.setId(9999L);
        when(loanDao.findById(9999L)).thenReturn(loan);
        assertEquals(loanService.findById(9999L), loan);
        verify(loanDao).findById(9999L);
        verifyNoMoreInteractions(loanDao);
    }

    @Test
    public void findAllLoans() {
        loanService.findAll();
        verify(loanDao).findAll();
        verifyNoMoreInteractions(loanDao);
    }

    @Test
    public void findAllUnreturnedLoans() {
        when(loanService.findAll()).thenReturn(new ArrayList<Loan>(){{
            add(loan);
        }});
        List<Loan> loans = loanService.findAllUnreturnedLoans();
        assertEquals(loans.size(), 1);

        loan.setReturnTimestamp(new Timestamp(System.currentTimeMillis()));
        when(loanService.findAllLoansOfMember(member)).thenReturn(new ArrayList<Loan>(){{
            add(loan);
        }});
        loans = loanService.findAllUnreturnedLoans();
        assertTrue(loans.isEmpty());
    }

    @Test
    public void allLoansOfMember() {
        when(loanDao.allLoansOfMember(member)).thenReturn(new ArrayList<Loan>(){{
            add(loan);
        }});
        List<Loan> testedLoans = loanService.findAllLoansOfMember(member);

        assertTrue(testedLoans.contains(loan));
        assertEquals(testedLoans.size(), 1);

        verify(loanDao).allLoansOfMember(member);
        verifyNoMoreInteractions(loanDao);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Member.*")
    public void allLoansOfMemberNull() {
        loanService.findAllLoansOfMember(null);
        verifyZeroInteractions(loanDao);
    }

    @Test
    public void allUnreturnedLoansOfMember() {
        when(loanService.findAllLoansOfMember(member)).thenReturn(new ArrayList<Loan>(){{
            add(loan);
        }});
        List<Loan> loans = loanService.findAllUnreturnedLoansOfMember(member);
        assertEquals(loans.size(), 1);


        loan.setReturnTimestamp(new Timestamp(System.currentTimeMillis()));
        when(loanService.findAllLoansOfMember(member)).thenReturn(new ArrayList<Loan>(){{
            add(loan);
        }});
        loans = loanService.findAllUnreturnedLoansOfMember(member);
        assertTrue(loans.isEmpty());
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Member.*")
    public void allUnreturnedLoansOfMemberNull() {
        loanService.findAllUnreturnedLoansOfMember(null);
        verifyZeroInteractions(loanDao);
    }

    @Test
    public void returnLoan() {
        loanService.returnLoan(loan);
        ArgumentCaptor<Loan> argument = ArgumentCaptor.forClass(Loan.class);
        verify(loanDao).update(argument.capture());
        assertNotNull(argument.getValue().getReturnTimestamp());
        verifyNoMoreInteractions(loanDao);
        verify(loanItemDao).update(loanItem);
        verifyNoMoreInteractions(loanItemDao);
    }
}
