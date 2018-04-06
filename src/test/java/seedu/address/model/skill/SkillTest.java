package seedu.address.model.skill;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SkillTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Skill(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Skill(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null skill name
        Assert.assertThrows(NullPointerException.class, () -> Skill.isValidSkillName(null));
    }

}
