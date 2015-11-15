package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.LoanDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanItemDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lajci on 15.11.2015.
 */

@Service
public class LoanServiceImpl implements LoanService {

    @Inject
    private LoanDao loanDao;

    @Inject
    private LoanItemDao loanItemDao;

    @Override
    public void create(Loan loan) {
        for (LoanItem loanItem : loan.getLoanItems())
        {
            loanItemDao.create(loanItem);
        }
        loanDao.create(loan);
    }

    @Override
    public void remove(Loan loan) {
        loanDao.delete(loan);
    }

    @Override
    public Loan findById(Long id) {
        return loanDao.findById(id);
    }

    @Override
    public List<Loan> allLoansOfMember(Member member) {
        return new ArrayList(loanDao.allLoansOfMember(member));
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loanDao.findAll());
    }
}
