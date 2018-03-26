package seedu.address.model.person;

import static java.util.Calendar.MARCH;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.model.person.customer.Customer;
import seedu.address.model.person.customer.LateInterest;
import seedu.address.model.person.customer.MoneyBorrowed;
import seedu.address.model.person.customer.StandardInterest;
import seedu.address.model.person.runner.Runner;
import seedu.address.model.tag.Tag;

public class CustomerTest {

    @Test
    public void test_getMoneyCurrentlyOwed_returnsCorrect() {

        //get a Date from a GregorianCalendar
        int year = 2018;
        int month = MARCH;
        int dayOfMonth = 1;
        int hourOfDay = 0;
        int minute = 0;
        int second = 0;
        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
        Date date = calendar.getTime();

        MoneyBorrowed moneyBorrowed = new MoneyBorrowed(10);
        Date oweStartDate = date;
        Date oweDueDate = new Date();
        StandardInterest standardInterest = new StandardInterest(1);
        LateInterest lateInterest = new LateInterest(0);

        Customer customer = createCustomer(moneyBorrowed, oweStartDate, oweDueDate, standardInterest,
                lateInterest);

        double moneyOwed = customer.getMoneyCurrentlyOwed();
        assertEquals(10.303, moneyOwed, 0.001);
    }

    /**
     *
     * @param moneyBorrowed moneyBorrowed
     * @param oweStartDate oweStartDate
     * @param oweDueDate oweDueDate
     * @param standardInterest standardInterest
     * @param lateInterest lateInterest
     * @return customer
     */
    private Customer createCustomer(MoneyBorrowed moneyBorrowed, Date oweStartDate, Date oweDueDate, StandardInterest
            standardInterest, LateInterest lateInterest) {
        Name name = new Name("name");
        Phone phone = new Phone("1234");
        Email email = new Email("abc@example.com");
        Address address = new Address("address");
        Set<Tag> tags = new HashSet<>();
        Runner runner = new Runner();

        return new  Customer(name, phone, email, address, tags, moneyBorrowed, oweStartDate,
                oweDueDate, standardInterest, lateInterest, runner);
    }

}
