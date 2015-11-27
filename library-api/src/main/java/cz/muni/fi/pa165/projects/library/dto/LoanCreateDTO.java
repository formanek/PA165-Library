package cz.muni.fi.pa165.projects.library.dto;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jan Mosat
 */
public class LoanCreateDTO {

    private MemberDTO member;

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
}
