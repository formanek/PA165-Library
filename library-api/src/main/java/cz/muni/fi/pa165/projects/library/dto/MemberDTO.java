package cz.muni.fi.pa165.projects.library.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Library member DTO
 *
 * @author David Formanek
 */
public class MemberDTO {

    private Long id;
    private String givenName;
    private String surname;
    private String email;
    private Set<LoanDTO> loans = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<LoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(Set<LoanDTO> loans) {
        this.loans = loans;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MemberDTO)) {
            return false;
        }
        return Objects.equals(email, ((MemberDTO) obj).email);
    }

    @Override
    public int hashCode() {
        return email == null ? 0 : email.hashCode();
    }

    @Override
    public String toString() {
        return "MemberDTO{" + "id=" + id + ", givenName=" + givenName + ", surname=" + surname
                + ", email=" + email + ", loans=" + loans + '}';
    }
}
