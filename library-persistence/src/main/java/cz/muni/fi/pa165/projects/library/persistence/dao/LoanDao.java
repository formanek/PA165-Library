package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.Collection;

/**
 * DAO for loans
 * 
 * @author Jan Mosat
 */
public interface LoanDao {

    /**
     * Persists loan in database. All loans attributes except returnDate and id must be specified
     *
     * @param loan to be persisted
     */
    public void create(Loan loan);

    /**
     * Deletes loan
     *
     * @param loan to be deleted
     */
    public void delete(Loan loan);

    /**
     * Returns loan with specified Id from database if id is valid
     *
     * @param id of loan to be returned
     * @return loan with specified Id or null if non existent
     */
    public Loan findById(Long id);

    /**
     * Returns all loans for specified member if member is valid
     *
     * @param member for witch loans should be returned
     * @return all loans for specified member (empty list if specified member is not persisted)
     */
    public Collection<Loan> allLoansOfMember(Member member);

    /**
     * Returns all persisted loans
     *
     * @return all loans (empty list if none are persisted)
     */
    public Collection<Loan> findAll();

    /**
     * Updates specified persisted loan
     *
     * @param loan to be updated
     */
    public void update(Loan loan);
}
