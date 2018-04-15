package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author wenhao53
/**
 * Tests that a {@code Person}'s {@code BodyMassIndexClassification} matches the given search query.
 */
public class NameContainsClassificationPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsClassificationPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(person.getBodyMassIndexClassification(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsClassificationPredicate // instanceof handles nulls
                && this.keywords.equals(((NameContainsClassificationPredicate) other).keywords)); // state check
    }

}
