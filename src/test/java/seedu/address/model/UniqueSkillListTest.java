package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.skill.UniqueSkillList;

public class UniqueSkillListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueSkillList uniqueSkillList = new UniqueSkillList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueSkillList.asObservableList().remove(0);
    }
}
