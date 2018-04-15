package seedu.address.commons.core;

import seedu.address.logic.commands.AccountCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DisplayCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditDetailsCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GoogleSetLocationCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.LinkedInLoginCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShareToLinkedInCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    //@@author A0155428B
    public static final String[] AUTOCOMPLETE_FIELD = { AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        ConvertCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD,
        LinkedInLoginCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        SelectCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD, AddCommand.COMMAND_AUTO_COMPLETE,
        ImportCommand.COMMAND_WORD, RemarkCommand.COMMAND_WORD, EditDetailsCommand.COMMAND_WORD,
        DisplayCommand.COMMAND_WORD, ShareToLinkedInCommand.COMMAND_WORD, ChangeThemeCommand.COMMAND_WORD,
        EditDetailsCommand.COMMAND_ALIAS, SortCommand.COMMAND_WORD, GoogleSetLocationCommand.COMMAND_WORD,
        AccountCommand.COMMAND_WORD};
}
