//@@author kush1509
package seedu.address.model.skill;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Skill} matches any of the tags given.
 */
public class TagMatchesPredicate implements Predicate<Person> {
    private final Set<Skill> jobSkills;

    public TagMatchesPredicate(Set<Skill> jobSkills) {
        this.jobSkills = jobSkills;
    }

    @Override
    public boolean test(Person person) {
        List<String> jobTags = getTagsAsList(this.jobSkills);
        String personTags = getTagsAsString(person.getSkills());
        return jobTags.stream()
                .anyMatch(jobTag -> StringUtil.containsWordIgnoreCase(personTags, jobTag));
    }

    private String getTagsAsString(Set<Skill> skills) {
        Iterator tagsIterator = skills.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(tagsIterator.next());
        while (tagsIterator.hasNext()) {
            sb.append(" " + tagsIterator.next());
        }
        return (sb.toString()
                .replace("[", "")
                .replace("]", ""));
    }

    private List<String> getTagsAsList(Set<Skill> skills) {
        Iterator tagsIterator = skills.iterator();
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
                && this.jobSkills.equals(((TagMatchesPredicate) other).jobSkills)); // state check
    }
}
