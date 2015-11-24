package cz.muni.fi.pa165.projects.library.dto;

import java.util.Objects;

/**
 * @author Jaroslav Kaspar
 */
public class ReturnLoanItemDTO {

    private Long loanItemId;
    
    private BookCondition condition;

    public BookCondition getCondition() {
        return condition;
    }

    public void setCondition(BookCondition condition) {
        this.condition = condition;
    }

    public Long getLoanItemId() {
        return loanItemId;
    }

    public void setLoanItemId(Long loanItemId) {
        this.loanItemId = loanItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnLoanItemDTO)) return false;
        ReturnLoanItemDTO that = (ReturnLoanItemDTO) o;
        return Objects.equals(getLoanItemId(), that.getLoanItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanItemId());
    }
}
