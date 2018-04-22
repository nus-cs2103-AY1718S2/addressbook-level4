package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keyphrases given.
 */
public class NameContainsKeyphrasesPredicate implements Predicate<Person> {
    private final List<String> keyphrases;

    public NameContainsKeyphrasesPredicate(List<String> keyphrases) {
        this.keyphrases = keyphrases;
    }

    @Override
    public boolean test(Person person) {
        return keyphrases.isEmpty()
                || keyphrases.stream()
                .anyMatch(keyphrase -> StringUtil.containsWordsIgnoreCase(person.getName().fullName, keyphrase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeyphrasesPredicate // instanceof handles nulls
                && this.keyphrases.equals(((NameContainsKeyphrasesPredicate) other).keyphrases)); // state check
    }

}
