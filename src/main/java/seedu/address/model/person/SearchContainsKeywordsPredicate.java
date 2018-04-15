package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.util.GradeUtil;

/**
 * Tests that a {@code Client}'s attributes matches any of the keywords given.
 */
public class SearchContainsKeywordsPredicate implements Predicate<Client> {
    private final List<String> keywords;

    public SearchContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    //@@author Zhu-Jiahui
    @Override
    public boolean test(Client client) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getName().fullName, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getEmail().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getAddress().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getPhone().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getLocation().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> GradeUtil.containsGradeIgnoreCase(client.getGrade().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getSubject().value, keyword))
                || keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(client.getCategory().value, keyword));
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((SearchContainsKeywordsPredicate) other).keywords)); // state check
    }

}
