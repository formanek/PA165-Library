package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.LoanDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanItemCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanItemDTO;

import java.util.List;

/**
 * @author Jaroslav Kaspar
 */
public interface LoanItemFacade {

    Long createLoanItem(LoanItemCreateDTO loanItemCreateDTO);

    void deleteLoanItem(LoanItemDTO loanItemDTO);

    void updateLoanItem(LoanItemDTO loanItemDTO);

    LoanItemDTO findLoanItemById(Long loanItemId);

    public List<LoanItemDTO> findByLoan(LoanDTO loan);

    public List<LoanItemDTO> findAll();
}
