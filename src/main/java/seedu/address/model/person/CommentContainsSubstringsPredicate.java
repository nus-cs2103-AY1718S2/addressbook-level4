package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

//@@author tanhengyeow
/**
 * Tests that a {@code Person}'s {@code Comment} matches the substring given.
 */
public class CommentContainsSubstringsPredicate implements Predicate<Person> {
    private final List<String> substringKeywords;
    private final String commandPrefix = "c/";

    public CommentContainsSubstringsPredicate(List<String> substringKeywords) {
        this.substringKeywords = substringKeywords;
    }

    @Override
    public boolean test(Person person) {
        return substringKeywords.stream()
                .anyMatch(substring -> FindResults.getInstance().containsSubstringIgnoreCase(
                        person.getComment().value, substring, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommentContainsSubstringsPredicate // instanceof handles nulls
                && this.substringKeywords.equals((
                (CommentContainsSubstringsPredicate) other).substringKeywords)); // state check
    }

}
