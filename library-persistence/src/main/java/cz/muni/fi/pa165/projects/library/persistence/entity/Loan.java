package cz.muni.fi.pa165.projects.library.persistence.entity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Class that represents Loan which can contain multiple Loan items
 *
 * @author Jan Mosat
 */
@Entity
@Table(name = "loan")
public class Loan {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private Date loanDate;
    
    @NotNull
    @Column(nullable = false)
    private Date returnDate;
    
    @NotNull
    @Column(nullable = false)
    private long userId;
    
    @NotNull
    @Column(nullable = false)
    private Set<LoanItem> loanItems;
    
    public Loan()
    {
        loanItems = new HashSet<>();
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
                && Objects.equals(userId, other.getUserId())
                && Objects.equals(loanItems, other.getLoanItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanDate, returnDate, userId, loanItems);
    }  
}