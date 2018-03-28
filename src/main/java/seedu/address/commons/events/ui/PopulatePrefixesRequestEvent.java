package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.*;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Indicates that a new result is available.
 */
public class PopulatePrefixesRequestEvent extends BaseEvent {

    public final String commandUsageMessage;
    public final String commandTemplate;
    public final int caretIndex;
    private final String commandPreamble;

    public PopulatePrefixesRequestEvent(String command) {
        switch (command) {
        case "add":
            commandPreamble = AddCommand.COMMAND_WORD;
            commandUsageMessage = AddCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble
                    + " " + PREFIX_NAME
                    + "  " + PREFIX_PHONE
                    + "  " + PREFIX_EMAIL
                    + "  " + PREFIX_ADDRESS
                    + "  " + PREFIX_TAG;
            caretIndex = (AddCommand.COMMAND_WORD + " " + PREFIX_NAME + " ").length();
            break;
        case "edit":
            commandPreamble = EditCommand.COMMAND_WORD;
            commandUsageMessage = EditCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble
                    + "  " + PREFIX_NAME
                    + "  " + PREFIX_PHONE
                    + "  " + PREFIX_EMAIL
                    + "  " + PREFIX_ADDRESS
                    + "  " + PREFIX_TAG;
            caretIndex = (EditCommand.COMMAND_WORD + " ").length();
            break;
        case "delete":
            commandPreamble = DeleteCommand.COMMAND_WORD;
            commandUsageMessage = DeleteCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble + " ";
            caretIndex = commandTemplate.length();
            break;
        case "locate":
            commandPreamble = LocateCommand.COMMAND_WORD;
            commandUsageMessage = LocateCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble + " ";
            caretIndex = commandTemplate.length();
            break;
        case "find":
            commandPreamble = FindCommand.COMMAND_WORD;
            commandUsageMessage = FindCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble + " -";
            caretIndex = commandTemplate.length();
            break;
        default:
            commandPreamble = FindCommand.COMMAND_WORD;
            commandUsageMessage = FindCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble + " -";
            caretIndex = commandTemplate.length();
            // should be exception
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + commandPreamble;
    }
}
