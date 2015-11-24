package cz.muni.fi.pa165.projects.library.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Jaroslav Kaspar
 */
public class LoanItemCreateDTO {

    @NotNull
    private Long bookId;

    @NotNull
    private Long loanId;

    private BookCondition conditionBefore;

    private BookCondition conditionAfter;
}
