package cz.muni.fi.pa165.projects.library.dto;

/**
 * Describes condition of a book
 *
 * @author Jaroslav Kaspar
 */
public enum BookCondition {

    AS_NEW("As new"), VERY_GOOD("Very good"), GOOD("Good"), FAIR("Fair"), POOR("Poor");
    
    private String value;
    BookCondition(final String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }    
}
