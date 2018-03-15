package seedu.address.model.tag;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

import java.util.logging.Logger;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    private static final Logger logger = LogsCenter.getLogger(TagContainsKeywordsPredicate.class);


    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        boolean matchedTag = keywords.stream()
                                .anyMatch(keyword -> person.getTags().contains(keyword));
        logger.info("Keyword: " + keywords.get(0));
        return matchedTag;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
