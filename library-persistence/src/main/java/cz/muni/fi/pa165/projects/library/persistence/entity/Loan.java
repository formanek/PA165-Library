package cz.muni.fi.pa165.projects.library.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date loanDate;
    
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    
    @NotNull
    /*@Column(nullable = false)*/
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

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
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
        return Objects.equals(loanDate, other.getLoanDate())
                && Objects.equals(returnDate, other.getReturnDate())
                && Objects.equals(member, other.getMember());
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanDate, returnDate, member);
    }  
}
