package cz.muni.fi.pa165.projects.library.persistence.entity;

import cz.muni.fi.pa165.projects.library.dto.BookCondition;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Represents an item in a loan
 *
 * @author Jaroslav Kaspar
 */
@Entity
public class LoanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Book book;

    @NotNull
    @ManyToOne
    private Loan loan;

    @Enumerated(EnumType.STRING)
    private BookCondition conditionBefore;

    @Enumerated(EnumType.STRING)
    private BookCondition conditionAfter;

    public LoanItem() {
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookCondition getConditionBefore() {
        return conditionBefore;
    }

    public void setConditionBefore(BookCondition conditionBefore) {
        this.conditionBefore = conditionBefore;
    }

    public BookCondition getConditionAfter() {
        return conditionAfter;
    }

    public void setConditionAfter(BookCondition conditionAfter) {
        this.conditionAfter = conditionAfter;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanItem) || getId() == null) return false;
        LoanItem loanItem = (LoanItem) o;
        return Objects.equals(getId(), loanItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
