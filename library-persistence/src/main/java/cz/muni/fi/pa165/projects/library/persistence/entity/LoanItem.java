package cz.muni.fi.pa165.projects.library.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author jkaspar
 */
@Entity
public class LoanItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
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
        if (!(o instanceof LoanItem)) return false;
        LoanItem loanItem = (LoanItem) o;
        return Objects.equals(getBook(), loanItem.getBook()) &&
                Objects.equals(getLoan(), loanItem.getLoan());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBook(), getLoan());
    }
}
