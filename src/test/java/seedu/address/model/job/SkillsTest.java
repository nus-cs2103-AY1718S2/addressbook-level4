package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.skill.Skill;
import seedu.address.testutil.Assert;

//@@author ChengSashankh
public class SkillsTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Skill(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidSkill = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Skill(invalidSkill));
    }

    @Test
    public void isValidSkill() {
        // null skill
        Assert.assertThrows(NullPointerException.class, () -> Skill.isValidSkill(null));

        // invalid skill
        assertFalse(Skill.isValidSkill("")); // empty string
        assertFalse(Skill.isValidSkill(" ")); // spaces only

        // valid skill
        assertTrue(Skill.isValidSkill("peter*")); // contains non-alphanumeric characters
        assertTrue(Skill.isValidSkill("peter jack")); // alphabets only
        assertTrue(Skill.isValidSkill("12345")); // numbers only
        assertTrue(Skill.isValidSkill("probability theory 2")); // alphanumeric characters
        assertTrue(Skill.isValidSkill("Backend Engineering")); // with capital letters
        assertTrue(Skill.isValidSkill("Statistics 2")); // long names
    }
}
//@@author
