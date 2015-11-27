package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;

import java.util.List;

/**
 *
 * @author Jan Mosat
 */
public interface LoanService {

    /**
     * Creates new Loan in library
     * @param loan to be created
     * @throws NullPointerException when param loan is null
     */
    void create(Loan loan);
    /**
     * Deletes specified loan
     * @param loan to be deleted
     * @throws NullPointerException when param loan is null
     */
    void delete(Loan loan);
    /**
     * Gets loan with specified id
     * @param id of loan to be returned
     * @throws NullPointerException when param id is null
     * @return loan with specified id
     */
    Loan findById(Long id);
    /**
     * Gets all Loans of specified Member in library
     * @param member for which loans should be returned
     * @throws NullPointerException when param member is null
     * @return all Loans of specified Member in library
     */
    List<Loan> findAllLoansOfMember(Member member);
    /**
     * Gets all Loans of library
     * @return all Loans of library
     */
    List<Loan> findAll();
    /**
     * Gets all unreturned Loans of library
     * @return all unreturned Loans of library
     */
    List<Loan> findAllUnreturnedLoans();
    /**
     * Returns Loan back to library
     * @param loan to be returned
     * @throws NullPointerException when param loan is null
     */
    void returnLoan(Loan loan);
    /**
     * Gets all unreturned Loans of specified Member in library
     * @param member for which unreturned loans should be returned
     * @throws NullPointerException when param member is null
     * @return all unreturned Loans of specified Member in library
     */
    List<Loan> findAllUnreturnedLoansOfMember(Member member);

}
