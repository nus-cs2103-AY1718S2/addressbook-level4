package seedu.address.model.skill;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

//@@author KevinCJH
/**
 * Tests that a {@code Person}'s {@code Skill} matches any of the keywords given.
 */
public class PersonSkillContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonSkillContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    // @@author kush1509
    @Override
    public boolean test(Person person) {
        return SkillUtil.match(keywords, person.getSkills());
    }

    //@@author KevinCJH
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonSkillContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonSkillContainsKeywordsPredicate) other).keywords)); // state check
    }

}
