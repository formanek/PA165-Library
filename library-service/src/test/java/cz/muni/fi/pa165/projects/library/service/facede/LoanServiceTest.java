package cz.muni.fi.pa165.projects.library.service.facede;

import cz.muni.fi.pa165.projects.library.dto.LoanDTO;
import cz.muni.fi.pa165.projects.library.persistence.entity.*;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.LoanService;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lajci on 15.11.2015.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanServiceTest extends AbstractTestNGSpringContextTests
{
    @Inject
    private BeanMappingService beanMappingService;

    @Inject
    @InjectMocks
    private LoanService loanService;

    private Loan loan1;

    @BeforeMethod
    public void setUpMethod(){
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

        //TODO Exception: javax.persistence.TransactionRequiredException: No transactional EntityManager available
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
