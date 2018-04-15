package seedu.address.model.person;
//@@author SuxianAlicia
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person} 's {@code Group}s' names matches any of the keywords given.
 */
public class GroupsContainKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public GroupsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> personGroupsMatchesKeyword(person, keyword));
    }

    /**
     * Checks if person contains group with group tag names matching given keyword.
     * Matching is case-insensitive.
     */
    private boolean personGroupsMatchesKeyword(Person person, String keyword) {
        Set<String> groupNames = person.getGroupTags().stream().map(group -> group.tagName).collect(Collectors.toSet());
        for (String groupName: groupNames) {
            if (StringUtil.containsWordIgnoreCase(groupName, keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupsContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((GroupsContainKeywordsPredicate) other).keywords)); // state check
    }
}
