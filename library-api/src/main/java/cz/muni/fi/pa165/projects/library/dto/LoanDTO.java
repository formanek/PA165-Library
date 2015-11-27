package cz.muni.fi.pa165.projects.library.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Jan Mosat
 */
public class LoanDTO {

    private Long id;

    private Timestamp loanTimestamp;

    private Timestamp returnTimestamp;

    private MemberDTO member;

    private Set<LoanItemDTO> loanItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public Set<LoanItemDTO> getLoanItems() {
        return loanItems;
    }

    public void setLoanItems(Set<LoanItemDTO> loanItems) {
        this.loanItems = loanItems;
    }

    public Timestamp getLoanTimestamp() {
        return (loanTimestamp == null) ? null : new Timestamp(loanTimestamp.getTime());
    }

    public void setLoanTimestamp(Timestamp loanTimestamp) {
        this.loanTimestamp = (loanTimestamp == null) ? null : new Timestamp(loanTimestamp.getTime());
    }

    public Timestamp getReturnTimestamp() {
        return (returnTimestamp == null) ? null : new Timestamp(returnTimestamp.getTime());
    }

    public void setReturnTimestamp(Timestamp returnTimestamp) {
        this.returnTimestamp = (returnTimestamp == null) ? null : new Timestamp(returnTimestamp.getTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LoanDTO)) {
            return false;
        }
        final LoanDTO other = (LoanDTO) obj;
        return Objects.equals(getLoanTimestamp(), other.getLoanTimestamp())
                && Objects.equals(getReturnTimestamp(), other.getReturnTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanTimestamp(), getReturnTimestamp());
    }
}
