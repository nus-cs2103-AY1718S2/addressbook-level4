package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

//@@author tanhengyeow
/**
 * Tests that a {@code Person}'s {@code Address} matches the substring given.
 */
public class AddressContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "a/";

    public AddressContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getAddress().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                        (AddressContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
