package cz.muni.fi.pa165.projects.library.service.facade;

import cz.muni.fi.pa165.projects.library.dto.LoanDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanItemCreateDTO;
import cz.muni.fi.pa165.projects.library.dto.LoanItemDTO;
import cz.muni.fi.pa165.projects.library.facade.LoanItemFacade;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.service.BeanMappingService;
import cz.muni.fi.pa165.projects.library.service.LoanItemService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * @author Jaroslav Kaspar
 */
public class LoanItemFacadeImpl implements LoanItemFacade {

    @Inject
    private LoanItemService loanItemService;

    @Inject
    private BeanMappingService beanMappingService;

    @Override
    public Long createLoanItem(LoanItemCreateDTO loanItemCreateDTO) {
        LoanItem loanItem = beanMappingService.mapTo(loanItemCreateDTO, LoanItem.class);
        loanItemService.create(loanItem);
        return loanItem.getId();
    }

    @Override
    public void deleteLoanItem(LoanItemDTO loanItemDTO) {
        loanItemService.delete(beanMappingService.mapTo(loanItemDTO, LoanItem.class));
    }

    @Override
    public void updateLoanItem(LoanItemDTO loanItemDTO) {
        loanItemService.update(beanMappingService.mapTo(loanItemDTO, LoanItem.class));
    }

    @Override
    public LoanItemDTO findLoanItemById(Long loanItemId) {
        LoanItem loanItem = loanItemService.findById(loanItemId);
        return (loanItem == null) ? null : beanMappingService.mapTo(loanItem, LoanItemDTO.class);
    }

    @Override
    public List<LoanItemDTO> findByLoan(LoanDTO loan) {
        List<LoanItem> loanItems = loanItemService.findByLoan(beanMappingService.mapTo(loan, Loan.class));
        if (loanItems == null) {
            return Collections.emptyList();
        }
        return beanMappingService.mapTo(loanItems, LoanItemDTO.class);
    }

    @Override
    public List<LoanItemDTO> findAll() {
        List<LoanItem> loanItems = loanItemService.findAll();
        if (loanItems == null) {
            return Collections.emptyList();
        }
        return beanMappingService.mapTo(loanItems, LoanItemDTO.class);
    }
}
