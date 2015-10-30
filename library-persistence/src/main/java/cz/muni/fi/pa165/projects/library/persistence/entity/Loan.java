package cz.muni.fi.pa165.projects.library.persistence.entity;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


/**
 * Class that represents Loan which can contain multiple Loan items
 *
 * @author Jan Mosat
 */
@Entity
public class Loan {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false,unique=true)
    private Timestamp loanTimestamp;
    
    private Timestamp returnTimestamp;
    
    @NotNull
    @ManyToOne
    private Member member;

    @NotNull
    @Column(nullable = false)
    @OneToMany(orphanRemoval = true, mappedBy = "loan")
    private Set<LoanItem> loanItems;
    
    public Loan()
    {
    }
    
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getLoanTimestamp() {
        return loanTimestamp;
    }

    public void setLoanTimestamp(Timestamp loanTimestamp) {
        this.loanTimestamp = loanTimestamp;
    }

    public Timestamp getReturnTimestamp() {
        return returnTimestamp;
    }

    public void setReturnTimestamp(Timestamp returnTimestamp) {
        this.returnTimestamp = returnTimestamp;
    }

    public Set<LoanItem> getLoanItems() {
        return Collections.unmodifiableSet(loanItems);
    }

    public void setLoanItems(Set<LoanItem> loanItems) {
        this.loanItems = loanItems;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Loan)) {
            return false;
        }
        final Loan other = (Loan) obj;
        return Objects.equals(loanTimestamp, other.getLoanTimestamp())
                && Objects.equals(returnTimestamp, other.getReturnTimestamp())
                && Objects.equals(member, other.getMember());
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanTimestamp, returnTimestamp, member);
    }  
}
