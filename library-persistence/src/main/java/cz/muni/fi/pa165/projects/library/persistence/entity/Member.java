package cz.muni.fi.pa165.projects.library.persistence.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Library member entity
 *
 * @author David Formanek
 */
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Member)) {
            return false;
        }
        final Member other = (Member) obj;
        return Objects.equals(givenName, other.getGivenName())
                && Objects.equals(surname, other.getSurname())
                && Objects.equals(email, other.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenName, surname, email);
    }
}
