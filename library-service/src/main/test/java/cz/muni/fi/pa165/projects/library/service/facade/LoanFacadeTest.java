package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.LoanCreateDTO;
import cz.muni.fi.pa165.projects.library.facade.LoanFacade;
import cz.muni.fi.pa165.projects.library.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * Created by lajci on 15.11.2015.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LoanFacadeTest extends AbstractTestNGSpringContextTests {

    @Inject
    @InjectMocks
    private LoanFacade loanFacade;

    @Test
    public void addLoanTest()
    {
        LoanCreateDTO loan = new LoanCreateDTO();
        //TODO Add other attributes after creating others DTOs
        //loanFacade.addLoan(loan);
    }

}
