package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Group} matches any of the keywords given.
 */

//@@author limzk1994
public class PersonContainsGroupsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public PersonContainsGroupsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getGroup().groupName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsGroupsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsGroupsPredicate) other).keywords)); // state check
    }
}

