package seedu.recipe.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.model.recipe.UniquePersonList;

public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }
}
