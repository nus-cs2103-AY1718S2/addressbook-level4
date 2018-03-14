package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person} 's {@code Tag}s' names matches any of the keywords given.
 */
public class TagsContainKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> personTagsMatchesKeyword(person, keyword));
    }

    /**
     * Checks if person contains tags with tag names matching given keyword. Matching is case-insensitive.
     */
    private boolean personTagsMatchesKeyword(Person person, String keyword) {
        Set<String> tagNames = person.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toSet());
        for (String tagName: tagNames) {
            if (StringUtil.containsWordIgnoreCase(tagName, keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagsContainKeywordsPredicate) other).keywords)); // state check
    }
}
