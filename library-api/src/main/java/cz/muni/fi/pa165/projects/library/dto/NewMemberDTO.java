package cz.muni.fi.pa165.projects.library.dto;

import java.util.Objects;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

/**
 * DTO for creating a new member
 *
 * @author David Formanek
 */
public class NewMemberDTO {

    @NotBlank(message = "Name has to be filled in")
    private String givenName;
    @NotBlank(message = "Surname has to be filled in")
    private String surname;
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9]+[a-zA-Z0-9-]*\\.)+[a-zA-Z0-9]+$", message = "Valid email address has to be filled in")
    private String email;

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
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NewMemberDTO)) {
            return false;
        }
        return Objects.equals(email, ((NewMemberDTO) obj).email);
    }

    @Override
    public int hashCode() {
        return email == null ? 0 : email.hashCode();
    }

    @Override
    public String toString() {
        return "MemberDTO{givenName=" + givenName + ", surname=" + surname
                + ", email=" + email + '}';
    }
}
