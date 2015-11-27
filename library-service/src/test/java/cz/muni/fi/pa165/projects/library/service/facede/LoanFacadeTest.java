package cz.muni.fi.pa165.projects.library.service.facede;

import cz.muni.fi.pa165.projects.library.dto.*;
import cz.muni.fi.pa165.projects.library.facade.LoanFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.LoanService;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 *
 * @author Jan Mosat
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    LoanService loanService;

    @Inject
    LoanFacade loanFacade;
    
    private Loan loan;
    private LoanDTO loanDTO;
    private LoanCreateDTO loanCreateDTO;

    private ReturnLoanDTO returnLoanDTO;

    private LoanItem loanItem;

    private ReturnLoanItemDTO returnLoanItemDTO;

    private Timestamp loanTimestamp = Timestamp.valueOf("2014-10-23 10:10:10.0");

    @BeforeClass
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        if (AopUtils.isAopProxy(loanFacade) && loanFacade instanceof Advised) {
            loanFacade = (LoanFacade) ((Advised) loanFacade).getTargetSource().getTarget();
        }
        ReflectionTestUtils.setField(loanFacade, "loanService", loanService);
    }

    @BeforeMethod
    public void setUpMethod() {
        loan = new Loan();
        Member member = new Member();
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
        loanItem.setConditionBefore(cz.muni.fi.pa165.projects.library.persistence.entity.BookCondition.AS_NEW);

        loan = new Loan();
        loan.setLoanTimestamp(loanTimestamp);
        loan.setMember(member);
        loan.setLoanItems(items);

        items.add(loanItem);
        loanItem.setLoan(loan);

        loanDTO = new LoanDTO();

        loanDTO.setLoanTimestamp(loanTimestamp);

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setGivenName(member.getGivenName());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setSurname(member.getSurname());
        loanDTO.setMember(memberDTO);

        LoanItemDTO loanItemDTO = new LoanItemDTO();
        Set<LoanItemDTO> itemsDTO = new HashSet<>();



        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor("author1");
        bookDTO.setIsbn("0321356683");
        bookDTO.setTitle("title1");

        loanItemDTO.setBook(bookDTO);
        loanItemDTO.setConditionBefore(BookCondition.AS_NEW);
        loanDTO.setLoanItems(itemsDTO);

        returnLoanDTO = new ReturnLoanDTO();
        Set<ReturnLoanItemDTO> returnItemsDTO = new HashSet<>();

        returnLoanItemDTO = new ReturnLoanItemDTO();
        returnLoanItemDTO.setCondition(BookCondition.AS_NEW);

        returnItemsDTO.add(returnLoanItemDTO);
        returnLoanDTO.setLoanItems(returnItemsDTO);

        loanCreateDTO = new LoanCreateDTO();
        loanCreateDTO.setMember(memberDTO);
        Set<LoanItemCreateDTO> loanItemsCreate = new HashSet<>();
        LoanItemCreateDTO loanItemCreateDTO = new LoanItemCreateDTO();
        loanItemCreateDTO.setConditionBefore(BookCondition.AS_NEW);
        loanItemCreateDTO.setBookId(1L);

        loanItemsCreate.add(loanItemCreateDTO);

        loanCreateDTO.setLoanItems(loanItemsCreate);
    }
    
    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Loan.*")
    public void findByInvalidId() {
        loanFacade.findLoanById(null);
    }

    @Test
    public void findByValidId() {
        loan.setId(9999L);
        loanDTO.setId(9999L);
        when(loanService.findById(9999L)).thenReturn(loan);
        LoanDTO loanDTOById = loanFacade.findLoanById(9999L);
        assertEquals(loanDTOById, loanDTO);
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Loan.*")
    void deleteBookNullIdTest(){
        loanFacade.deleteLoan(null);
    }

    @Test
    void deleteLoanTest(){
        when(loanService.findById(9999L)).thenReturn(loan);
        loanFacade.deleteLoan(9999L);
        verify(loanService).delete(any(Loan.class));
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Null.*")
    void loanLoanNullTest(){
        loanFacade.loan(null);
    }

    @Test
    void loanLoanTest(){
        loanFacade.loan(loanCreateDTO);
        verify(loanService).create(any(Loan.class));
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Null.*")
    void returnLoanNullTest(){
        loanFacade.loan(null);
    }

    @Test
    void returnLoanTest(){
        loanItem.setId(999L);
        returnLoanItemDTO.setLoanItemId(999L);
        //TODO UNCOMMENT after fixing conversion
        //loanItem.setConditionAfter( cz.muni.fi.pa165.projects.library.persistence.entity.BookCondition.POOR);
        when(loanService.findById(any(Long.class))).thenReturn(loan);
        loanFacade.returnLoan(returnLoanDTO);
        assertEquals(loanItem.getConditionAfter(), cz.muni.fi.pa165.projects.library.persistence.entity.BookCondition.POOR);
    }
    
    @Test
    public void findAllLoans() {
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(loan);
        ArrayList<LoanDTO> loanDTOs = new ArrayList<>();
        loanDTOs.add(loanDTO);
        when(loanService.findAll()).thenReturn(loans);
        assertEquals(loanDTOs, loanFacade.findAll());
    }

    @Test
    public void findAllUnreturnedLoans() {
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(loan);
        ArrayList<LoanDTO> loanDTOs = new ArrayList<>();
        loanDTOs.add(loanDTO);
        when(loanService.findAllUnreturnedLoans()).thenReturn(loans);
        assertEquals(loanDTOs, loanFacade.findAllUnreturnedLoans());
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Member.*")
    void findLoansOfMemberNullTest(){
        loanFacade.findLoansOfMember(null);
    }

    @Test
    void findLoansOfMemberTest(){
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(loan);
        ArrayList<LoanDTO> loanDTOs = new ArrayList<>();
        loanDTOs.add(loanDTO);
        when(loanService.findAllLoansOfMember(any(Member.class))).thenReturn(loans);
        assertEquals(loanDTOs, loanFacade.findLoansOfMember(1L));
    }

    @Test(expectedExceptions = NullPointerException.class, expectedExceptionsMessageRegExp = "Member.*")
    void findAllUnreturnedLoansOfMemberNullTest(){
        loanFacade.findAllUnreturnedLoansOfMember(null);
    }

    @Test
    void findAllUnreturnedLoansOfMemberTest(){
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(loan);
        ArrayList<LoanDTO> loanDTOs = new ArrayList<>();
        loanDTOs.add(loanDTO);
        when(loanService.findAllUnreturnedLoansOfMember(any(Member.class))).thenReturn(loans);
        assertEquals(loanDTOs, loanFacade.findAllUnreturnedLoansOfMember(1L));
    }
}
