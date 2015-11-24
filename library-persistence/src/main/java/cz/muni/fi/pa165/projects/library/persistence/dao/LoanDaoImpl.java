package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Data access object which provides access to Loan entity
 *
 * @author Jan Mosat
 */
@Repository
public class LoanDaoImpl implements LoanDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Loan loan) {
        Objects.requireNonNull(loan, "Null loan can't be created");
        Objects.requireNonNull(loan.getLoanTimestamp(), "Loan date must be specified");
        Objects.requireNonNull(loan.getMember(), "Member must be specified");
        Objects.requireNonNull(loan.getLoanItems(), "Loan items must be specified");
        if (loan.getReturnTimestamp() != null && loan.getLoanTimestamp().after(loan.getReturnTimestamp())) {
            throw new IllegalArgumentException("Return date must be after Loan date");
        }
        em.persist(loan);
    }

    @Override
    public void delete(Loan loan) {
        Objects.requireNonNull(loan, "Null loan can't be deleted");
        //Objects.requireNonNull(loan.getId(), "Deleting loan with null id");
        em.remove(loan);
    }

    @Override
    public Loan findById(Long id) {
        Objects.requireNonNull(id, "null id");
        return em.find(Loan.class, id);
    }

    @Override
    public List<Loan> allLoansOfMember(Member member) {
        Objects.requireNonNull(member, "Member is null");
        Objects.requireNonNull(member.getId(), "null id");
        return em.createQuery("select l from Loan l where l.member = :member", Loan.class)
                .setParameter("member", member).getResultList();
    }

    @Override
    public List<Loan> findAll() {
        return em.createQuery("select l from Loan l", Loan.class).getResultList();
    }

    @Override
    public void update(Loan loan) {
        Objects.requireNonNull(loan, "Null loan can't be updated");
        Objects.requireNonNull(loan.getLoanTimestamp(), "Loan date must be specified");
        Objects.requireNonNull(loan.getMember(), "Member must be specified");
        Objects.requireNonNull(loan.getLoanItems(), "Loan items must be specified");
        if (loan.getReturnTimestamp() != null && loan.getLoanTimestamp().after(loan.getReturnTimestamp())) {
            throw new IllegalArgumentException("Return date must be after Loan date");
        }
        em.merge(loan);
    }

    @Override
    public List<Loan> allUnreturnedLoans() {
        return em.createQuery("select l from Loan l where l.returnTimestamp = null", Loan.class).getResultList();
    }

    @Override
    public Collection<Loan> allUnreturnedLoansOfMember(Member member) {
        Objects.requireNonNull(member, "Member is null");
        Objects.requireNonNull(member.getId(), "null id");
        return em.createQuery("select l from Loan l where l.member = :member and l.returnTimestamp = null", Loan.class)
                .setParameter("member", member).getResultList();
    }
}
