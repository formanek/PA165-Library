package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;

import java.util.List;

/**
 * @author Jaroslav Kaspar
 */
public interface LoanItemService {

    public void create(LoanItem loanItem);

    public void delete(LoanItem loanItem);

    public void update(LoanItem loanItem);

    public LoanItem findById(Long id);

    public List<LoanItem> findByLoan(Loan loan);

    public List<LoanItem> findAll();
}
