package seedu.address.commons.events.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.events.BaseEvent;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.LocateCommand;
import seedu.address.logic.commands.SelectCommand;

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
        case "delete":
            commandPreamble = DeleteCommand.COMMAND_WORD;
            commandUsageMessage = DeleteCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble + " ";
            caretIndex = commandTemplate.length();
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
        case "find":
            commandPreamble = FindCommand.COMMAND_WORD;
            commandUsageMessage = FindCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble + " -";
            caretIndex = commandTemplate.length();
            break;
        case "locate":
            commandPreamble = LocateCommand.COMMAND_WORD;
            commandUsageMessage = LocateCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble + " ";
            caretIndex = commandTemplate.length();
            break;
        default:
            commandPreamble = SelectCommand.COMMAND_WORD;
            commandUsageMessage = SelectCommand.MESSAGE_USAGE;
            commandTemplate = commandPreamble + " ";
            caretIndex = commandTemplate.length();
            // should be exception
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + commandPreamble;
    }
}
