package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.FindResults;

//@@author tanhengyeow
/**
 * Tests that a {@code Person}'s {@code University} matches the suffix string given.
 */
public class UniversityContainsSuffixesPredicate implements Predicate<Person> {
    private final List<String> suffixKeywords;
    private final String commandPrefix = "u/";

    public UniversityContainsSuffixesPredicate(List<String> suffixKeywords) {
        this.suffixKeywords = suffixKeywords;
    }

    @Override
    public boolean test(Person person) {
        return suffixKeywords.stream()
                .anyMatch(suffix -> FindResults.getInstance().containsSuffixIgnoreCase(
                        person.getUniversity().value, suffix, commandPrefix));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniversityContainsSuffixesPredicate // instanceof handles nulls
                && this.suffixKeywords.equals((
                (UniversityContainsSuffixesPredicate) other).suffixKeywords)); // state check
    }

}
