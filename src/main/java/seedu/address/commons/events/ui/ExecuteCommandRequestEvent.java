package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Indicates that a new result is available.
 */
public class ExecuteCommandRequestEvent extends BaseEvent {

    public final String commandPreamble;

    public ExecuteCommandRequestEvent(String command) {
        switch (command) {
        case "clear":
            commandPreamble = ClearCommand.COMMAND_WORD;
            break;
        case "history":
            commandPreamble = HistoryCommand.COMMAND_WORD;
            break;
        case "list":
            commandPreamble = ListCommand.COMMAND_WORD;
            break;
        case "redo":
            commandPreamble = RedoCommand.COMMAND_WORD;
            break;
        case "undo":
            commandPreamble = UndoCommand.COMMAND_WORD;
            break;
        default:
            commandPreamble = UndoCommand.COMMAND_WORD;
            // should be exception
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + commandPreamble;
    }
}
