package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Format full help instructions for every command for display or command requested
 */
public class HelpCommand extends Command {

    public static final String COMMAND_ALIAS = "man";
    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = "Type '" + COMMAND_WORD + "' OR '" + COMMAND_ALIAS
            + "' to shows program usage instructions.\n"  + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    private String commandRequest = null;


    //@@author jasmoon
    public HelpCommand()    {
    }

    public HelpCommand(String args) {
        commandRequest = args.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (commandRequest == null)   {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else   {
            switch(commandRequest) {

            case TaskCommand.COMMAND_WORD:
                return new CommandResult(TaskCommand.MESSAGE_USAGE);

            case EventCommand.COMMAND_WORD:
                return new CommandResult(EventCommand.MESSAGE_USAGE);

            case CompleteCommand.COMMAND_WORD:
                return new CommandResult(CompleteCommand.MESSAGE_USAGE);

            //case EditCommand.COMMAND_WORD:
                //return new CommandResult(EditCommand.MESSAGE_USAGE);

            //case SelectCommand.COMMAND_WORD:
                //return new CommandResult(SelectCommand.MESSAGE_USAGE);

            case RemoveCommand.COMMAND_WORD:
                return new CommandResult(RemoveCommand.MESSAGE_USAGE);

            case HelpCommand.COMMAND_WORD:
                return new CommandResult(HelpCommand.MESSAGE_USAGE);

            case HelpCommand.COMMAND_ALIAS:
                return new CommandResult(HelpCommand.MESSAGE_USAGE);

            case ListCommand.COMMAND_WORD:
                return new CommandResult(ListCommand.MESSAGE_USAGE);

            case ListCommand.COMMAND_ALIAS:
                return new CommandResult(ListCommand.MESSAGE_USAGE);

            //case FindCommand.COMMAND_WORD:
                //return new CommandResult(FindCommand.MESSAGE_USAGE);

            default:
                throw new CommandException(MESSAGE_USAGE);
            }
        }
    }


}
