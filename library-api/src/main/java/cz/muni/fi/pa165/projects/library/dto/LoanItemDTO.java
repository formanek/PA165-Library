package cz.muni.fi.pa165.projects.library.dto;

import cz.muni.fi.pa165.projects.library.persistence.entity.BookCondition;

import javax.validation.constraints.NotNull;

/**
 * @author Jaroslav Kaspar
 */
public class LoanItemDTO {

    @NotNull
    private Long id;

    @NotNull
    private BookDTO book;

    @NotNull
    private Long loanId;

    private BookCondition conditionBefore;

    private BookCondition conditionAfter;
}
