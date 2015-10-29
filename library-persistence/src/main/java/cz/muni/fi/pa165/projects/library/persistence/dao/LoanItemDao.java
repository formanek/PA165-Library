package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;

import java.util.List;

/**
 * @author jkaspar
 */
public interface LoanItemDao {

    /**
     * Persis new LoanItem into persistence context
     * @param loanItem
     */
    public void create(LoanItem loanItem);

    /**
     * Remove LoanItem from persistence context
     * @param loanItem
     */
    public void delete(LoanItem loanItem);

    /**
     * Update persisted LoanItem
     * @param loanItem
     */
    public void update(LoanItem loanItem);

    /**
     * Find LoanItem by his id
     * @param id
     * @return LoanItem with given id
     */
    public LoanItem findById(Long id);

    /**
     * Find all LoanItems which belongs to given loan
     * @param loan
     * @return list of LoanItems
     */
    public List<LoanItem> findByLoan(Loan loan);


    /**
     * Find all LoanItems from the database
     * @return list of all LoanItems
     */
    public List<LoanItem> findAll();
}
