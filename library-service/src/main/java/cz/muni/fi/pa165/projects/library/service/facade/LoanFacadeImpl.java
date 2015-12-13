package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.LoanCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanItemCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.ReturnLoanDTO;
import cz.muni.fi.pa165.projects.library.dto.ReturnLoanItemDTO;
import cz.muni.fi.pa165.projects.library.facade.LoanFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.BookService;
import cz.muni.fi.pa165.projects.library.service.LoanService;
import cz.muni.fi.pa165.projects.library.service.MemberService;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jan Mosat
 */
@Service
@Transactional
public class LoanFacadeImpl implements LoanFacade {

    @Inject
    private LoanService loanService;

    @Inject
    private MemberService memberService;

    @Inject
    private BookService bookService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public List<LoanDTO> findAll() {
        return beanMappingService.mapTo(loanService.findAll(), LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findAllUnreturnedLoans() {
        return beanMappingService.mapTo(loanService.findAllUnreturnedLoans(), LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findLoansOfMember(Long memberId) {
        Objects.requireNonNull(memberId, "Member with null id");
        Member member = memberService.findById(memberId);
        List<Loan> loans = loanService.findAllLoansOfMember(member);
        return beanMappingService.mapTo(loans, LoanDTO.class);
    }

    @Override
    public List<LoanDTO> findAllUnreturnedLoansOfMember(Long memberId) {
        Objects.requireNonNull(memberId, "Member with null id");
        Member member = memberService.findById(memberId);
        List<Loan> loans = loanService.findAllUnreturnedLoansOfMember(member);
        return beanMappingService.mapTo(loans, LoanDTO.class);
    }

    @Override
    public void returnLoan(ReturnLoanDTO returnLoanDTO) {
        Objects.requireNonNull(returnLoanDTO, "Null loan can't be returned");
        Objects.requireNonNull(returnLoanDTO.getLoanItems(), "Loan items must be specified");
        Loan loan = loanService.findById(returnLoanDTO.getLoanId());
        for (ReturnLoanItemDTO returnLoanItemDTO : returnLoanDTO.getLoanItems()) {
            for (LoanItem loanItem : loan.getLoanItems()) {
                if (loanItem.getId().equals(returnLoanItemDTO.getLoanItemId())) {
                    loanItem.setConditionAfter(returnLoanItemDTO.getCondition());
                }
            }
        }
        loanService.returnLoan(loan);
    }

    @Override
    public LoanDTO findLoanById(Long loanId) {
        Objects.requireNonNull(loanId, "Loan with null id");
        return beanMappingService.mapTo(loanService.findById(loanId), LoanDTO.class);
    }

    @Override
    public Long loan(LoanCreateDTO loanCreateDTO) {
        Objects.requireNonNull(loanCreateDTO, "Null loan can't be created");
        Objects.requireNonNull(loanCreateDTO.getMember(), "Member must be specified");
        Objects.requireNonNull(loanCreateDTO.getLoanItems(), "Loan items must be specified");
        Loan loan = beanMappingService.mapTo(loanCreateDTO, Loan.class);
        Set<LoanItem> items = new HashSet<>();
        for (LoanItemCreateDTO itemCreate : loanCreateDTO.getLoanItems()) {
            LoanItem item = new LoanItem();
            item.setConditionAfter(itemCreate.getConditionAfter());
            item.setConditionBefore(itemCreate.getConditionBefore());
            item.setBook(bookService.findById(itemCreate.getBookId()));
            items.add(item);
        }
        loan.setLoanItems(items);
        loanService.create(loan);
        return loan.getId();
    }

    @Override
    public void deleteLoan(final Long loanId) {
        Objects.requireNonNull(loanId, "Loan with null id");
        loanService.delete(new Loan() {
            {
                setId(loanId);
            }
        });
    }
}
