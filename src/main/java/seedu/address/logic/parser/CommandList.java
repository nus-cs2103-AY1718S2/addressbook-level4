package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Contains the list of commands
 */
public class CommandList {

    /* Prefix definitions */
    public final ArrayList<String> commandList = new ArrayList<String>();

    public CommandList() {

        //add all commands to the list lexicographically
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
    }

    /**
     * Returns the added cli-syntax(if needed) {@code String} for the
     * auto-completed command {@code matchedCommandWord}
     */
    public String getSyntax(String matchedCommandWord) {

        switch (matchedCommandWord) {

            case AddCommand.COMMAND_WORD:
                return AddCommand.COMMAND_SYNTAX;

            case EditCommand.COMMAND_WORD:
                return EditCommand.COMMAND_SYNTAX;

            default: return matchedCommandWord;
        }
    }
}
