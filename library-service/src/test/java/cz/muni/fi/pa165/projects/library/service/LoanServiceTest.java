package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.dto.LoanDTO;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanItemDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.BookCondition;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.LoanService;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by lajci on 15.11.2015.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
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
    
    private Loan loan1;

    @BeforeMethod
    public void setUpMethod(){
        MockitoAnnotations.initMocks(this);
        loan1 = new Loan();
        Member member1 = new Member();
        member1.setGivenName("Joshua");
        member1.setSurname("Bloch");
        member1.setEmail("effective@java.com");

        Book book1 = new Book();
        book1.setAuthor("author1");
        book1.setIsbn("0321356683");
        book1.setTitle("title1");

        LoanItem loanItem1 = new LoanItem();
        Set<LoanItem> items1 = new HashSet<>();
        loanItem1.setBook(book1);
        loanItem1.setConditionBefore(BookCondition.AS_NEW);

        loan1 = new Loan();
        loan1.setLoanTimestamp(Timestamp.valueOf("2014-10-23 10:10:10.0"));
        loan1.setMember(member1);
        loan1.setLoanItems(items1);

        items1.add(loanItem1);
        loanItem1.setLoan(loan1);
        
        // TODO move to test
        loanService.create(loan1);
    }

    @Test
    public void somethingWorks(){
        //Assert.assertEquals(loanService.findAll().size(),1);
    }

    @Test
    public void MapperTest()
    {
        LoanDTO loanDTO = beanMappingService.mapTo(loan1, LoanDTO.class);
        Assert.assertEquals(loanDTO.getLoanItems().size(), 1);
    }

}
