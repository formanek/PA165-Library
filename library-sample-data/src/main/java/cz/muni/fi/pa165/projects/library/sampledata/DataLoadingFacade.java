package cz.muni.fi.pa165.projects.library.sampledata;

import cz.muni.fi.pa165.projects.library.dto.BookCondition;
import cz.muni.fi.pa165.projects.library.persistence.entity.Book;
import cz.muni.fi.pa165.projects.library.persistence.entity.Loan;
import cz.muni.fi.pa165.projects.library.persistence.entity.LoanItem;
import cz.muni.fi.pa165.projects.library.persistence.entity.Member;
import cz.muni.fi.pa165.projects.library.service.BookService;
import cz.muni.fi.pa165.projects.library.service.LoanService;
import cz.muni.fi.pa165.projects.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author jkaspar
 */
@Transactional
@Component
public class DataLoadingFacade {

    @Autowired
    private BookService bookService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private MemberService memberService;

    public void loadData() {

        // Members

        Member jan = new Member();
        jan.setGivenName("Jan");
        jan.setSurname("Novak");
        jan.setEmail("jan@novak.cz");
        memberService.create(jan);

        Member petr = new Member();
        petr.setGivenName("Petr");
        petr.setSurname("Novak");
        petr.setEmail("petr@novak.cz");
        memberService.create(petr);

        // Books

        Book book1 = new Book();
        book1.setAuthor("Pasi Ilmari Jääskeläinen");
        book1.setIsbn("9788074325915");
        book1.setTitle("Literární spolek Laury Sněžné");
        bookService.create(book1);

        Book book2 = new Book();
        book2.setAuthor("Gregory David Roberts");
        book2.setIsbn("9788088130000");
        book2.setTitle("Šantaram");
        bookService.create(book2);

        Book book3 = new Book();
        book3.setAuthor("Simon Bond");
        book3.setIsbn("9788074072864");
        book3.setTitle("101 způsobů, jak použít mrtvou kočku");
        bookService.create(book3);

        Book book4 = new Book();
        book4.setAuthor("David Lagercrantz");
        book4.setIsbn("9788074915239");
        book4.setTitle("Dívka v pavoučí síti");
        bookService.create(book4);

        Book book5 = new Book();
        book5.setAuthor("Jo Nesbo");
        book5.setIsbn("9788074733574");
        book5.setTitle("Syn");
        bookService.create(book5);

        // Unreturned loan - Jan

        LoanItem unreturnedLoanItemJan = new LoanItem();
        unreturnedLoanItemJan.setBook(book1);
        unreturnedLoanItemJan.setConditionBefore(BookCondition.AS_NEW);

        Set<LoanItem> unreturnedLoanItemsJan = new HashSet<>();
        unreturnedLoanItemsJan.add(unreturnedLoanItemJan);

        Loan unreturnedLoanJan = new Loan();
        unreturnedLoanJan.setMember(jan);
        unreturnedLoanJan.setLoanItems(unreturnedLoanItemsJan);
        loanService.create(unreturnedLoanJan);

        // Returned loan - Jan

        LoanItem returnedLoanItemJan1 = new LoanItem();
        returnedLoanItemJan1.setBook(book2);
        returnedLoanItemJan1.setConditionBefore(BookCondition.AS_NEW);
        returnedLoanItemJan1.setConditionAfter(BookCondition.FAIR);

        LoanItem returnedLoanItemJan2 = new LoanItem();
        returnedLoanItemJan2.setBook(book3);
        returnedLoanItemJan2.setConditionBefore(BookCondition.GOOD);
        returnedLoanItemJan2.setConditionAfter(BookCondition.POOR);

        Set<LoanItem> returnedLoanItemsJan = new HashSet<>();
        returnedLoanItemsJan.add(returnedLoanItemJan1);
        returnedLoanItemsJan.add(returnedLoanItemJan2);

        Loan returnedLoanJan = new Loan();
        returnedLoanJan.setMember(jan);
        returnedLoanJan.setLoanItems(returnedLoanItemsJan);
        returnedLoanJan.setReturnTimestamp(new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)));
        loanService.create(returnedLoanJan);

        // Unreturned loan - Petr

        LoanItem unreturnedLoanItemPetr1 = new LoanItem();
        unreturnedLoanItemPetr1.setBook(book4);
        unreturnedLoanItemPetr1.setConditionBefore(BookCondition.AS_NEW);

        LoanItem unreturnedLoanItemPetr2 = new LoanItem();
        unreturnedLoanItemPetr2.setBook(book5);
        unreturnedLoanItemPetr2.setConditionBefore(BookCondition.GOOD);

        Set<LoanItem> unreturnedLoanItemsPetr = new HashSet<>();
        unreturnedLoanItemsPetr.add(unreturnedLoanItemPetr1);
        unreturnedLoanItemsPetr.add(unreturnedLoanItemPetr2);

        Loan unreturnedLoanPetr = new Loan();
        unreturnedLoanPetr.setMember(petr);
        unreturnedLoanPetr.setLoanItems(unreturnedLoanItemsPetr);
        loanService.create(unreturnedLoanPetr);

        // Returned loan - Petr

        LoanItem returnedLoanItemPetr = new LoanItem();
        returnedLoanItemPetr.setBook(book1);
        returnedLoanItemPetr.setConditionBefore(BookCondition.VERY_GOOD);
        returnedLoanItemPetr.setConditionAfter(BookCondition.GOOD);

        Set<LoanItem> returnedLoanItemsPetr = new HashSet<>();
        returnedLoanItemsPetr.add(returnedLoanItemPetr);

        Loan returnedLoanPetr = new Loan();
        returnedLoanPetr.setMember(petr);
        returnedLoanPetr.setLoanItems(returnedLoanItemsPetr);
        returnedLoanPetr.setReturnTimestamp(new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(8)));
        loanService.create(returnedLoanPetr);
    }
}
