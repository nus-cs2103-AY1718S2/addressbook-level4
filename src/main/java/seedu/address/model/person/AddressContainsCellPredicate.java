//@@author sarahgoh97
package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that the person is in the cell
 */
public class AddressContainsCellPredicate implements Predicate<Person> {
    private final String cellAddress;

    public AddressContainsCellPredicate(String cellAddress) {
        this.cellAddress = cellAddress;
    }

    @Override
    public boolean test(Person person) {
        if (person.getIsInCell()) {
            if (person.getCellAddress().toString().equals(cellAddress)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsCellPredicate // instanceof handles nulls
                && this.cellAddress.equals(((AddressContainsCellPredicate) other).cellAddress)); // state check
    }
}
