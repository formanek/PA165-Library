package cz.muni.fi.pa165.projects.library.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Library member entity
 *
 * @author David Formanek
 */
@Entity
@Table(name = "members") // because "member" is a keyword
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String givenName;

    @NotNull
    @Column(nullable = false)
    private String surname;

    @NotNull
    @Column(nullable = false, unique = true)
    @Pattern(regexp = ".+@.+\\...+")
    private String email;

    @OneToMany(orphanRemoval = true, mappedBy = "member")
    private Set<Loan> loans = new HashSet<>();

    public Member() {
    }

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

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    public Set<Loan> getLoans() {
        return Collections.unmodifiableSet(loans);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Member)) {
            return false;
        }
        final Member other = (Member) obj;
        return Objects.equals(getGivenName(), other.getGivenName())
                && Objects.equals(getSurname(), other.getSurname())
                && Objects.equals(getEmail(), other.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGivenName(), getSurname(), getEmail());
    }
}
