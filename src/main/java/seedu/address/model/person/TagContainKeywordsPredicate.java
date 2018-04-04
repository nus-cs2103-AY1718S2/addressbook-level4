package seedu.address.model.person;

//@@author jas5469

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags();
        for (Tag t : tags) {
            for (String key : keywords) {
                if (t.name.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainKeywordsPredicate) other).keywords)); // state check
    }
}
