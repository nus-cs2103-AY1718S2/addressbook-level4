package seedu.address.model.skill;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashSet;
import java.util.Set;

//@@author ChengSashankh
/***
 * Represents a Job's and Person's required Skills in the Infinity Book.
 * Guarantees: is valid as declared in {@link #isValidSkill(String)}
 */
public class Skill {
    public static final String SKILL_VALIDATION_REGEX = "[^\\s].*";
    public static final String MESSAGE_SKILL_CONSTRAINTS = "Skills can take any values, and should not be blank";
    public final String value;

    private Set<String> skillSet;

    public Skill(String skills) {
        requireNonNull(skills);
        checkArgument(isValidSkill(skills), MESSAGE_SKILL_CONSTRAINTS);
        this.value = skills;
        String[] skillsArray = skills.split(",");

        skillSet = new HashSet<String>();
        for (String entry : skillsArray) {
            skillSet.add(entry.trim());
        }
    }

    public Set<String> getSkillSet() {
        return skillSet;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Skill // instanceof handles nulls
                && this.value.compareTo(((Skill) other).value) == 0); // state check
    }

    public String toString() {
        return value;
    }

    /***
     * Checks if a given string is a valid Skill.
     * @param test is the String to be tested for validity
     * @return true if it is a valid skill.
     */
    public static boolean isValidSkill(String test) {
        return test.matches(SKILL_VALIDATION_REGEX);
    }

}
//@@author
