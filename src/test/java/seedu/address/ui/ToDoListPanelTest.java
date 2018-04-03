package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalToDos.getTypicalToDos;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysToDo;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ToDoCardHandle;
import guitests.guihandles.ToDoListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.todo.ToDo;

public class ToDoListPanelTest extends GuiUnitTest {
    private static final ObservableList<ToDo> TYPICAL_TODOS =
            FXCollections.observableList(getTypicalToDos());

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
