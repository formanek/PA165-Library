package cz.muni.fi.pa165.projects.library.dto;

import java.util.List;
import java.util.Objects;

/**
 * @author Jaroslav Kaspar
 */
public class ReturnLoanDTO {

    private Long loanId;

    private List<ReturnLoanItemDTO> loanItems;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public List<ReturnLoanItemDTO> getLoanItems() {
        return loanItems;
    }

    public void setLoanItems(List<ReturnLoanItemDTO> loanItems) {
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
