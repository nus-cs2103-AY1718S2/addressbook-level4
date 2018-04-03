package seedu.address.commons.events.ui;


import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TodoListWindow;

/**
 * An event requesting to show to do list window.
 */
public class ShowTodoListEvent extends BaseEvent{

    public TodoListWindow todoListWindow;

    public ShowTodoListEvent(TodoListWindow todoListWindow) {
        this.todoListWindow = todoListWindow;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TodoListWindow getTodoListWindow() {
        return this.todoListWindow;
    }

}
