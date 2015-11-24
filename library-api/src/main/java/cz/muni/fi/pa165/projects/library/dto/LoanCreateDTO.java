package cz.muni.fi.pa165.projects.library.dto;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by lajci on 15.11.2015.
 */
public class LoanCreateDTO {

    @NotNull
    private MemberDTO member;

    @NotEmpty
    private Set<LoanItemCreateDTO> loanItems = new HashSet<>();

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public Set<LoanItemCreateDTO> getLoanItems() {
        return Collections.unmodifiableSet(loanItems);
    }

    public void setLoanItems(Set<LoanItemCreateDTO> loanItems) {
        this.loanItems = loanItems;
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
        return Objects.equals(getMember(), other.getMember());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMember());
    }
}
