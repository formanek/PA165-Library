package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.Collection;


/**
 *
 * @author Jan Mosat
 */
public interface LoanDao {
    /**
     * Creates new loan in database
     * @param loan to be created
     */
    public void create(Loan loan);
    /**
     * Deletes loan from database
     * @param loan to be deleted
     */
    public void delete(Loan loan);
    /**
     * Returns loan with specified Id from database
     * @param id of loan to be returned
     * @return loan with specified Id
     */
    public Loan findById(Long id);
    /**
     * Returns all loans for specified member from database
     * @param member for witch loans should be returned 
     * @return all loans for specified member
     */
    public Collection<Loan> allLoansOfMember(Member member);
    /**
     * Returns all loans from database
     * @return all loans
     */
    public Collection<Loan> findAll();
}