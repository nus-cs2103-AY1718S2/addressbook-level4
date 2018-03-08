package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.book.Category;

public class UniqueListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueList<Category> uniqueList = new UniqueList<>();
        thrown.expect(UnsupportedOperationException.class);
        uniqueList.asObservableList().remove(0);
    }
}
