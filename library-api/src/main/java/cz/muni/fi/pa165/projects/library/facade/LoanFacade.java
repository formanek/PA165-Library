package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.LoanCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanDTO;

import java.util.List;

/**
 * Created by lajci on 15.11.2015.
 */
public interface LoanFacade {
    List<LoanDTO> getAllLoans();
    List<LoanDTO> getLoansByMember(long memberId);
    LoanDTO getLoanById(long id);
    long addLoan(LoanCreateDTO loanCreateDTO);
    void deleteLoan(Long loanId);
    List<LoanDTO> getAllUnreturnedLoans(long memberId);
    List<LoanDTO> getAllUnreturnedLoansOfMember(long memberId);
}
