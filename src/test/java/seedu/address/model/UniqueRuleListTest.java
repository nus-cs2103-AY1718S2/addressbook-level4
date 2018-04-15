package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.rule.UniqueRuleList;

public class UniqueRuleListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRuleList uniqueRuleList = new UniqueRuleList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueRuleList.asObservableList().remove(0);
    }
}
