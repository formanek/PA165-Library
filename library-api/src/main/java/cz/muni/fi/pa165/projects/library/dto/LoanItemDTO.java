package cz.muni.fi.pa165.projects.library.dto;


import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Jaroslav Kaspar
 */
public class LoanItemDTO {

    @NotNull
    private Long id;

    @NotNull
    private BookDTO book;

    @NotNull
    private Long loanId;

    private BookCondition conditionBefore;

    private BookCondition conditionAfter;

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public BookCondition getConditionAfter() {
        return conditionAfter;
    }

    public void setConditionAfter(BookCondition conditionAfter) {
        this.conditionAfter = conditionAfter;
    }

    public BookCondition getConditionBefore() {
        return conditionBefore;
    }

    public void setConditionBefore(BookCondition conditionBefore) {
        this.conditionBefore = conditionBefore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanItemDTO) || getId() == null) return false;
        LoanItemDTO that = (LoanItemDTO) o;
        return Objects.equals(getId(), that.getId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
