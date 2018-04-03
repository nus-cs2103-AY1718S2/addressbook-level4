package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowTodoListDisplayContentEvent;
import seedu.address.commons.events.ui.ShowTodoListEvent;
import seedu.address.model.event.Event;
import seedu.address.ui.TodoListWindow;

import com.google.api.client.util.DateTime;

/**
 * Show to do list window.
 */
public class TodoListCommand extends Command {

    public static final String COMMAND_WORD = "todoList";

    public static final String MESSAGE_SUCCESS = "To do list window is loaded.";

    @Override
    public CommandResult execute() {
        TodoListWindow todoListWindow = new TodoListWindow();
        EventsCenter.getInstance().post(new ShowTodoListDisplayContentEvent(
                new Event("Test Event", "NUS", new DateTime("2018-04-09T10:00:00"))));
        //EventsCenter.getInstance().post(new ShowTodoListEvent(todoListWindow));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
