package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author kaiyu92

/**
 * Tests that a {@code ReadOnlyEvent}'s {@code Title} matches any of the keywords given.
 */
public class TitleContainsKeywordsPredicate implements Predicate<CalendarEvent> {
    private static String predicateType = "et";
    private final List<String> keywords;

    public TitleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public static void setPredicateType(String predicateType) {
        TitleContainsKeywordsPredicate.predicateType = predicateType;
    }

    @Override
    public boolean test(CalendarEvent event) {
        if (predicateType.equals("et")) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getTitle(), keyword));
        }

        if (predicateType.equals("edt")) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getDatetime(), keyword));
        }

        if (predicateType.equals("ed")) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getDescription(), keyword));
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TitleContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TitleContainsKeywordsPredicate) other).keywords)); // state check
    }
}
