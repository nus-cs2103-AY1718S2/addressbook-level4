package seedu.address.logic.commands;

//@@author WoodySIN
/**
 * Lists all tasks stored in the address book in date order.
 */
public class SortTaskCommand extends Command {

    public static final String COMMAND_WORD = "sortTask";
    public static final String COMMAND_ALIAS = "stt";

    public static final String MESSAGE_SUCCESS = "Todo List is sorted";


    @Override
    public CommandResult execute() {
        model.sortTasks();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
