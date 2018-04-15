package seedu.address.logic.predicates;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

//**@@author aussiroth
/**
 * Tests that a {@code Person}'s specified {@code Attribute} as given in {@code keyword} is an empty string.
 */
public class FindMissingPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a new FindMissingPredicate based off given list.
     * @param keywords A non-empty ArrayList of keywords containing attributes.
     */
    public FindMissingPredicate(List<String> keywords) {
        assert(keywords.size() >= 1);
        //sort to ensure equality check passes
        Collections.sort(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        for (String keyword : keywords) {
            if (keyword.equals("email") && person.getEmail().value.equals("")) {
                return true;
            } else if (keyword.equals("phone") && person.getPhone().value.equals("")) {
                return true;
            } else if (keyword.equals("address") && person.getAddress().value.equals("")) {
                return true;
            } else if (keyword.equals("price") && person.getPrice().value.equals("")) {
                return true;
            } else if (keyword.equals("level") && person.getLevel().value.equals("")) {
                return true;
            } else if (keyword.equals("subject") && person.getSubject().value.equals("")) {
                return true;
            } else if (keyword.equals("role") && person.getRole().value.equals("")) {
                return true;
            } else if (keyword.equals("status") && person.getStatus().value.equals("")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMissingPredicate // instanceof handles nulls
                && this.keywords.equals(((FindMissingPredicate) other).keywords)); // state check
    }
}
