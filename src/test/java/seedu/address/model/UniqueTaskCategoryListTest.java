package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.category.UniqueTaskCategoryList;

//@@author CYX28
public class UniqueTaskCategoryListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskCategoryList uniqueTaskCategoryList = new UniqueTaskCategoryList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskCategoryList.asObservableList().remove(0);
    }
}
