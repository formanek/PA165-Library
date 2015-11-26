package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.dao.LoanDao;
import cz.muni.fi.pa165.projects.library.persistence.dao.LoanItemDao;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(loan, "Loan can't be null");
        loan.setLoanTimestamp(new Timestamp(System.currentTimeMillis()));
        for (LoanItem loanItem : loan.getLoanItems())
        {
            loanItemDao.create(loanItem);
        }
        loanDao.create(loan);
    }

    @Override
    public void delete(Loan loan) {
        Objects.requireNonNull(loan, "Loan can't be null");
        loanDao.delete(loan);
    }

    @Override
    public void returnLoan(Loan loan) {
        Objects.requireNonNull(loan, "Loan can't be null");
        loan.setReturnTimestamp(new Timestamp(System.currentTimeMillis()));
        for (LoanItem item : loan.getLoanItems())
        {
            loanItemDao.update(item);
        }
        loanDao.update(loan);
    }

    @Override
    public Loan findById(Long id) {
        Objects.requireNonNull(id, "Id can't be null");
        return loanDao.findById(id);
    }

    @Override
    public List<Loan> allLoansOfMember(Member member) {
        Objects.requireNonNull(member, "Member can't be null");
        return new ArrayList(loanDao.allLoansOfMember(member));
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loanDao.findAll());
    }

    @Override
    public List<Loan> findAllUnreturnedLoans() {
        return this.unreturnedLoans(this.findAll());
    }

    @Override
    public List<Loan> allUnreturnedLoansOfMember(Member member) {
        Objects.requireNonNull(member, "Member can't be null");
        return this.unreturnedLoans(this.allLoansOfMember(member));
    }

    private List<Loan> unreturnedLoans(List<Loan> loans)
    {
        List<Loan> unreturnedLoans = new ArrayList<>();
        for (Loan loan : loans)
        {
            if (loan.getReturnTimestamp() == null) {
                unreturnedLoans.add(loan);
            }
        }
        return unreturnedLoans;
    }
}
