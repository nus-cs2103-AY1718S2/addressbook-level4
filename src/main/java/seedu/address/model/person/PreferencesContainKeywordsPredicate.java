package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person} 's {@code Preference}s' names matches any of the keywords given.
 */
//@@author SuxianAlicia
public class PreferencesContainKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PreferencesContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> personGroupsMatchesKeyword(person, keyword));
    }

    /**
     * Checks if person contains preferences with  preference tag names matching given keyword.
     * Matching is case-insensitive.
     */
    private boolean personGroupsMatchesKeyword(Person person, String keyword) {
        Set<String> prefNames = person.getPreferenceTags().stream().map(pref -> pref.tagName)
                .collect(Collectors.toSet());

        for (String prefName: prefNames) {
            if (StringUtil.containsWordIgnoreCase(prefName, keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PreferencesContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PreferencesContainKeywordsPredicate) other).keywords)); // state check
    }
}
