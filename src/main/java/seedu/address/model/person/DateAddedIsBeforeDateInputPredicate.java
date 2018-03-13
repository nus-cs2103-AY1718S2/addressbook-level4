package seedu.address.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code DateAdded} is before the date input.
 */

public class DateAddedIsBeforeDateInputPredicate implements Predicate<Person> {
    private final String dateInputString;

    public DateAddedIsBeforeDateInputPredicate(String dateInputString) {
        this.dateInputString = dateInputString;
    }

    @Override
    public boolean test(Person person) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateAddedString = person.getDateAdded().dateAdded;
            assert DateAdded.isValidDate(dateAddedString);
            Date dateAdded = dateFormatter.parse(dateAddedString);
            Date dateInput = dateFormatter.parse(dateInputString);
            return dateAdded.compareTo(dateInput) <= 0;
        } catch (ParseException e) {
            return false; //need to resolve this part
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateAddedIsBeforeDateInputPredicate
                && this.dateInputString.equals(((DateAddedIsBeforeDateInputPredicate) other).dateInputString));
    }

}
