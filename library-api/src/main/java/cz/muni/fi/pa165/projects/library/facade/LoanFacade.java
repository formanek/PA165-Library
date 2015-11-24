package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.LoanCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanDTO;
import cz.muni.fi.pa165.projects.library.dto.ReturnLoanDTO;

import java.util.List;

/**
 * Created by lajci on 15.11.2015.
 */
public interface LoanFacade {
    List<LoanDTO> getAllLoans();
    List<LoanDTO> getLoansByMember(long memberId);
    LoanDTO getLoanById(long id);
    long loan(LoanCreateDTO loanCreateDTO);
    void deleteLoan(Long loanId);
    List<LoanDTO> getAllUnreturnedLoans();
    List<LoanDTO> getAllUnreturnedLoansOfMember(long memberId);
    void returnLoan(ReturnLoanDTO returnLoanDTO);
}
