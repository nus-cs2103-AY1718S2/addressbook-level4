package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} and {@code Nric} matches any of the keywords given.
 */
public class NameNricContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> nricKeywords;

    public NameNricContainsKeywordsPredicate(List<String> nameKeywords, List<String> nricKeywords) {
        this.nameKeywords = nameKeywords;
        this.nricKeywords = nricKeywords;
    }

    @Override
    public boolean test(Person person) {
        return nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword))
                & nricKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNric().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameNricContainsKeywordsPredicate // instanceof handles nulls
                && this.nameKeywords.equals(((NameNricContainsKeywordsPredicate) other).nameKeywords)
                && this.nricKeywords.equals(((NameNricContainsKeywordsPredicate) other).nricKeywords)); // state check
    }

}
