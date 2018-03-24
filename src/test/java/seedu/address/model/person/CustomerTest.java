package seedu.address.model.person;

import static java.util.Calendar.MARCH;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

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

        double moneyBorrowed = 10;
        Date oweStartDate = date;
        Date oweDueDate = new Date();
        double standardInterest = 1;
        double lateInterest = 0;

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
     * @return Customer
     */
    private Customer createCustomer(double moneyBorrowed, Date oweStartDate, Date oweDueDate, double
            standardInterest, double lateInterest) {
        Name name = new Name("name");
        Phone phone = new Phone("123");
        Email email = new Email("abc@example.com");
        Address address = new Address("address");
        Set<Tag> tags = new HashSet<>();

        return new  Customer(name, phone, email, address, tags, moneyBorrowed, oweStartDate,
                oweDueDate, standardInterest, lateInterest);
    }

}
