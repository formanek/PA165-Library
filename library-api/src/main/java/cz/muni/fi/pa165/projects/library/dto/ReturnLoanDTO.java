package cz.muni.fi.pa165.projects.library.dto;

import java.util.Objects;
import java.util.Set;

/**
 * @author Jaroslav Kaspar
 */
public class ReturnLoanDTO {

    private Long loanId;

    private Set<ReturnLoanItemDTO> loanItems;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Set<ReturnLoanItemDTO> getLoanItems() {
        return loanItems;
    }

    public void setLoanItems(Set<ReturnLoanItemDTO> loanItems) {
        this.loanItems = loanItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnLoanDTO)) return false;
        ReturnLoanDTO that = (ReturnLoanDTO) o;
        return Objects.equals(getLoanId(), that.getLoanId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanId());
    }
}
