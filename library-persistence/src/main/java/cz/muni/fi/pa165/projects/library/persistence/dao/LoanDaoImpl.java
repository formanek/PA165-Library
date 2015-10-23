package cz.muni.fi.pa165.projects.library.persistence.dao;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Data access object which provides access to Loan entity
 *
 * @author Jan Mosat
 */
public class LoanDaoImpl implements LoanDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Loan loan) {
        Objects.requireNonNull(loan, "Null loan can't be created.");
        Objects.requireNonNull(loan.getLoanDate(), "Loan date must be specified");
        Objects.requireNonNull(loan.getReturnDate(), "Return date must be specified");
        if (loan.getLoanDate().before(loan.getReturnDate())){
            throw new IllegalArgumentException("Return date mus be aftert Loan date");
        }
        em.persist(loan);
    }

    @Override
    public void delete(Loan loan) {
        checkId(loan.getId());
        em.remove(loan);
    }

    @Override
    public Loan findById(Long id) {
        checkId(id);
        return em.find(Loan.class, id);
    }

    @Override
    public List<Loan> allLoansOfMember(Member member) {
        checkId(member.getId());
        return em.createQuery("select * from Loan where userId = :userId", Loan.class)
                .setParameter("userId", member.getId()).getResultList();        
    }
    
    @Override
    public List<Loan> findAll() {
        return em.createQuery("select * from Loan", Loan.class).getResultList();
    }
    
    /**
     * Check whether the id parameter can be used as an id. If id is null, NullPointerException
     * is thrown. If id is negative number, IllegalArgumentException is thrown.
     * @param id 
     */
    private void checkId(Long id) {
        if (id == null) {
            throw new NullPointerException("Id can not be null");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id can not be negative number");
        }
    }
}