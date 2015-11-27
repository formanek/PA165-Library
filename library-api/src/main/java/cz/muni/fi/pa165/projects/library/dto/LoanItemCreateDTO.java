package cz.muni.fi.pa165.projects.library.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Jaroslav Kaspar
 */
public class LoanItemCreateDTO {

    @NotNull
    private Long bookId;

    private BookCondition conditionBefore;

    private BookCondition conditionAfter;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public BookCondition getConditionAfter() {
        return conditionAfter;
    }

    public void setConditionAfter(BookCondition conditionAfter) {
        this.conditionAfter = conditionAfter;
    }

    public BookCondition getConditionBefore() {
        return conditionBefore;
    }

    public void setConditionBefore(BookCondition conditionBefore) {
        this.conditionBefore = conditionBefore;
    }
}
