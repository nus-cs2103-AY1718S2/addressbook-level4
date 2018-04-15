//@@author deeheenguyen
package seedu.address.model.person;

import java.util.function.Predicate;


/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class EmailFilter implements Predicate<Person> {
    private final String email;

    public EmailFilter (Email email) {
        this.email = email.toString();
    }

    @Override
    public boolean test(Person person) {
        return person.getEmail().toString().equals(this.email);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailFilter // instanceof handles nulls
                && this.email.equals(((EmailFilter) other).email)); // state check
    }

}
