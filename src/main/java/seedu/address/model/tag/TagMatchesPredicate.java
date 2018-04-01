//@@author kush1509
package seedu.address.model.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the tags given.
 */
public class TagMatchesPredicate implements Predicate<Person> {
    private final Set<Tag> jobTags;

    public TagMatchesPredicate(Set<Tag> jobTags) {
        this.jobTags = jobTags;
    }

    @Override
    public boolean test(Person person) {
        List<String> jobTags = getTagsAsList(this.jobTags);
        String personTags = getTagsAsString(person.getTags());
        return jobTags.stream()
                .anyMatch(jobTag -> StringUtil.containsWordIgnoreCase(personTags, jobTag));
    }
    
    private String getTagsAsString(Set<Tag> tags) {
        Iterator tagsIterator = tags.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(tagsIterator.next());
        while (tagsIterator.hasNext()) {
            sb.append(" " + tagsIterator.next());
        }
        return (sb.toString()
                .replace("[", "")
                .replace("]", ""));
    }

    private List<String> getTagsAsList(Set<Tag> tags) {
        Iterator tagsIterator = tags.iterator();
        List<String> tagList = new ArrayList<String>();
        while (tagsIterator.hasNext()) {
            tagList.add(tagsIterator.next().toString().replace("[", "")
                    .replace("]", ""));
        }
        return tagList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagMatchesPredicate // instanceof handles nulls
                && this.jobTags.equals(((TagMatchesPredicate) other).jobTags)); // state check
    }
}
