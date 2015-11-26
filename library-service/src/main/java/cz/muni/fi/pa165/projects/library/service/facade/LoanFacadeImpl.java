package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.LoanCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanDTO;
import cz.muni.fi.pa165.projects.library.dto.ReturnLoanDTO;
import cz.muni.fi.pa165.projects.library.dto.ReturnLoanItemDTO;
import cz.muni.fi.pa165.projects.library.facade.LoanFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.BookCondition;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.LoanService;
import cz.muni.fi.pa165.projects.library.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lajci on 15.11.2015.
 */

@Service
@Transactional
public class LoanFacadeImpl implements LoanFacade {
    @Inject
    private LoanService loanService;

    @Inject
    private MemberService memberService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public List<LoanDTO> getAllLoans() {
        return beanMappingService.mapTo(loanService.findAll(),
                LoanDTO.class);
    }

    @Override
    public List<LoanDTO> getAllUnreturnedLoans() {
        return beanMappingService.mapTo(loanService.findAllUnreturnedLoans(),
                LoanDTO.class);
    }

    @Override
    public List<LoanDTO> getLoansByMember(long memberId) {
        Member member = memberService.findById(memberId);
        List<Loan> loans = loanService.allLoansOfMember(member);
        return beanMappingService.mapTo(loans, LoanDTO.class);
    }

    @Override
    public List<LoanDTO> getAllUnreturnedLoansOfMember(long memberId) {
        Member member = memberService.findById(memberId);
        List<Loan> loans = loanService.allUnreturnedLoansOfMember(member);
        return beanMappingService.mapTo(loans,
                LoanDTO.class);
    }

    @Override
    public void returnLoan(ReturnLoanDTO returnLoanDTO) {
        Loan loan = loanService.findById(returnLoanDTO.getLoanId());
        for (ReturnLoanItemDTO returnLoanItemDTO : returnLoanDTO.getLoanItems())
        {
            for(LoanItem loanItem : loan.getLoanItems()) {
                if (loanItem.getId().equals(returnLoanItemDTO.getLoanItemId()))
                {
                    loanItem.setConditionAfter(beanMappingService.mapTo(returnLoanItemDTO.getCondition(), BookCondition.class));
                }
            }
        }
    }

    @Override
    public LoanDTO getLoanById(long id) {
        return beanMappingService.mapTo(loanService.findById(id),
                LoanDTO.class);
    }

    @Override
    public long loan(LoanCreateDTO loanCreateDTO) {
        Loan loan = beanMappingService.mapTo(loanCreateDTO, Loan.class);
        loanService.create(loan);
        return  loan.getId();
    }

    @Override
    public void deleteLoan(Long loanId) {
        loanService.delete(beanMappingService.mapTo(getLoanById(loanId), Loan.class));
    }
}
