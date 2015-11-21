package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.LoanItemDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;

import javax.inject.Inject;
import java.util.List;

/**
 * @author jkaspar
 */
public class LoanItemServiceImpl implements LoanItemService {

    @Inject
    private LoanItemDao loanItemDao;

    @Override
    public void create(LoanItem loanItem) {
        loanItemDao.create(loanItem);
    }

    @Override
    public void delete(LoanItem loanItem) {
        loanItemDao.delete(loanItem);
    }

    @Override
    public void update(LoanItem loanItem) {
        loanItemDao.update(loanItem);
    }

    @Override
    public LoanItem findById(Long id) {
        return loanItemDao.findById(id);
    }

    @Override
    public List<LoanItem> findByLoan(Loan loan) {
        return loanItemDao.findByLoan(loan);
    }

    @Override
    public List<LoanItem> findAll() {
        return loanItemDao.findAll();
    }
}
