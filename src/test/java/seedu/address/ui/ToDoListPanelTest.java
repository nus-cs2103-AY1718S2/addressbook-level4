package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TODO;
import static seedu.address.testutil.TypicalToDos.getTypicalToDos;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysToDo;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ToDoCardHandle;
import guitests.guihandles.ToDoListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.todo.ToDo;

public class ToDoListPanelTest extends GuiUnitTest {
    private static final ObservableList<ToDo> TYPICAL_TODOS =
            FXCollections.observableList(getTypicalToDos());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_TODO);

    private ToDoListPanelHandle toDoListPanelHandle;

    @Before
    public void setUp() {
        ToDoListPanel toDoListPanel = new ToDoListPanel(TYPICAL_TODOS);
        uiPartRule.setUiPart(toDoListPanel);

        toDoListPanelHandle = new ToDoListPanelHandle(getChildNode(toDoListPanel.getRoot(),
                ToDoListPanelHandle.TODO_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TODOS.size(); i++) {
            toDoListPanelHandle.navigateToCard(TYPICAL_TODOS.get(i));
            ToDo expectedToDo = TYPICAL_TODOS.get(i);
            ToDoCardHandle actualCard = toDoListPanelHandle.getToDoCardHandle(i);

            assertCardDisplaysToDo(expectedToDo, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
