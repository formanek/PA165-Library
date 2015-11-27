package cz.muni.fi.pa165.projects.library.facade;

import cz.muni.fi.pa165.projects.library.dto.LoanCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanDTO;
import cz.muni.fi.pa165.projects.library.dto.ReturnLoanDTO;

import java.util.List;

/**
 *
 * @author Jan Mosat
 */
public interface LoanFacade {
    /**
     * retrieves list of all Loans in system
     * @return list of all Loans in system
     */
    List<LoanDTO> findAll();

    /**
     * retrieves list of all Loans of specified Member in system
     * @param memberId id of Member
     * @throws NullPointerException when param memberId is null
     * @return list of all Loans of specified Member in system
     */
    List<LoanDTO> findLoansOfMember(Long memberId);

    /**
     * retrieves the Loan with specified id
     * @param loanId id of Loan
     * @throws NullPointerException when param memberId is null
     * @return Loan with specified id
     */
    LoanDTO findLoanById(Long loanId);

    /**
     * Adds Loan to system
     * @param loanCreateDTO new Loan DTO
     * @throws NullPointerException when param loanId is null
     * @return id of created Loan
     */
    Long loan(LoanCreateDTO loanCreateDTO);

    /**
     * Removes Loan from system
     * @throws NullPointerException when param loanCreateDTO is null or when member or LoanItem list is null
     * @param loanId id of loan to be removed
     */
    void deleteLoan(Long loanId);

    /**
     * retrieves list of all Loans which are not returned in system
     * @throws NullPointerException when param loanId is null
     * @return list of all Loans which are not returned in system
     */
    List<LoanDTO> findAllUnreturnedLoans();

    /**
     * retrieves list of all Loans which are not returned in system and belongs to specified Member
     * @param memberId if of Member
     * @throws NullPointerException when param memberId is null
     * @return list of unreturned Loans of member
     */
    List<LoanDTO> findAllUnreturnedLoansOfMember(Long memberId);

    /**
     * Returns Loon back to library
     * @throws NullPointerException when return param returnLoanDTO is null, or list of returned items is null
     * @param returnLoanDTO return Loan DTO
     */
    void returnLoan(ReturnLoanDTO returnLoanDTO);
}
