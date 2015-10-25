package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

/**
 * @author jkaspar
 */
public class LoanItemDaoImpl implements LoanItemDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(LoanItem loanItem) {
        Objects.requireNonNull(loanItem.getBook(), "Book is not specified");
        Objects.requireNonNull(loanItem.getLoan(), "Loan is not specified");
        em.persist(loanItem);
    }

    @Override
    public void delete(LoanItem loanItem) {
        em.remove(loanItem);
    }

    @Override
    public LoanItem findById(Long id) {
        return em.find(LoanItem.class, id);
    }

    @Override
    public List<LoanItem> findByLoan(Loan loan) {
        Objects.requireNonNull(loan, "Loan was null");
        return em.createQuery("SELECT l FROM LoanItem WHERE l.loan = :loanid", LoanItem.class)
                .setParameter("loanid", loan)
                .getResultList();
    }

    @Override
    public List<LoanItem> findAll() {
        return em.createQuery("SELECT l FROM LoanItem l", LoanItem.class).getResultList();
    }
}
