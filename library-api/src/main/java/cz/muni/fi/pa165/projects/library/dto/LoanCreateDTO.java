package cz.muni.fi.pa165.projects.library.dto;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by lajci on 15.11.2015.
 */
public class LoanCreateDTO {

    /*@NotNull
    private Timestamp loanTimestamp;*/

    private Timestamp returnTimestamp;

    @NotNull
    private MemberDTO member;

    @NotEmpty
    private Set<LoanItemDTO> loanItems = new HashSet<>();

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public Set<LoanItemDTO> getLoanItems() {
        return Collections.unmodifiableSet(loanItems);
    }

    public void setLoanItems(Set<LoanItemDTO> loanItems) {
        this.loanItems = loanItems;
    }

    public Timestamp getReturnTimestamp() {
        return (returnTimestamp == null) ? null : new Timestamp(returnTimestamp.getTime());
    }

    public void setReturnTimestamp(Timestamp returnTimestamp) {
        this.returnTimestamp = (returnTimestamp == null) ? null : new Timestamp(returnTimestamp.getTime());
    }

    //TODO check equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LoanCreateDTO)) {
            return false;
        }
        final LoanCreateDTO other = (LoanCreateDTO) obj;
        return Objects.equals(getReturnTimestamp(), other.getReturnTimestamp()) && Objects.equals(getMember(), other.getMember());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReturnTimestamp(), getMember());
    }
}
