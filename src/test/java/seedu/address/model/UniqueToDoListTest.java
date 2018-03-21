package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.todo.UniqueToDoList;

public class UniqueToDoListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueToDoList uniqueToDoList = new UniqueToDoList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueToDoList.asObservableList().remove(0);
    }
}
