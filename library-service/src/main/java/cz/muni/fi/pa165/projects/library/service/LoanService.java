package cz.muni.fi.pa165.projects.library.service;

import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;

import java.util.List;

/**
 * Created by lajci on 15.11.2015.
 */
public interface LoanService {

    void create(Loan loan);

    void remove(Loan loan);

    Loan findById(Long id);

    List<Loan> allLoansOfMember(Member member);

    List<Loan> findAll();

    List<Loan> findAllUnreturnedLoans();

    void returnLoan(Loan loan);

    List<Loan> allUnreturnedLoansOfMember(Member member);

}
