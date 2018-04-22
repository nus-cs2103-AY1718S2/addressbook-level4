package seedu.address.commons.events.ui;
//@@author crizyli
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TodoListWindow;

/**
 * An event requesting to show to do list window.
 */
public class ShowTodoListEvent extends BaseEvent {

    private TodoListWindow todoListWindow;

    public ShowTodoListEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TodoListWindow getTodoListWindow() {
        return this.todoListWindow;
    }

}
