//@@author nhatquang3112
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_B;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_E;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;
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

    //@@author LeonidAgarth
    @Test
    public void equals_sameList_true() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(VALID_CONTENT)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList2.add(new ToDo(new Content(VALID_CONTENT)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void equals_differentList_false() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertNotEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        assertNotEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        assertEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(VALID_CONTENT)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList2.add(new ToDo(new Content(VALID_CONTENT)));
        assertEquals(uniqueToDoList1, uniqueToDoList2);
    }

    @Test
    public void hashCode_differentList_differentResult() throws Exception {
        UniqueToDoList uniqueToDoList1 = new UniqueToDoList();
        UniqueToDoList uniqueToDoList2 = new UniqueToDoList();
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_E)));
        assertNotEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());

        uniqueToDoList1.add(new ToDo(new Content(CONTENT_B)));
        uniqueToDoList1.add(new ToDo(new Content(CONTENT_E)));
        uniqueToDoList2.add(new ToDo(new Content(CONTENT_B)));
        assertNotEquals(uniqueToDoList1.hashCode(), uniqueToDoList2.hashCode());
    }
}
