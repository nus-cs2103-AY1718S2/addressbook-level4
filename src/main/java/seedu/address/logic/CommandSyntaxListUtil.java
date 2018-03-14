package seedu.address.logic;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
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

/**
 * Returns the syntax list of existing commands.
 */
public final class CommandSyntaxListUtil {
    private static ArrayList<String> commandSyntaxList;

    public static ArrayList<String> getCommandSyntaxList() {
        commandSyntaxList = new ArrayList<>();
        setCommandSyntaxList();
        return commandSyntaxList;
    }

    /**
     * Constructs commandSyntaxList for existing commands.
     */
    private static void setCommandSyntaxList() {
        commandSyntaxList.add(AddCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(ClearCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteCommand.COMMAND_WORD);
        commandSyntaxList.add(EditCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(ExitCommand.COMMAND_WORD);
        commandSyntaxList.add(FindCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(HelpCommand.COMMAND_WORD);
        commandSyntaxList.add(HistoryCommand.COMMAND_WORD);
        commandSyntaxList.add(ListCommand.COMMAND_WORD);
        commandSyntaxList.add(RedoCommand.COMMAND_WORD);
        commandSyntaxList.add(SelectCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(UndoCommand.COMMAND_WORD);

        sortCommandList();
    }

    /**
     * Sorts commandSyntaxList in lexicographical order.
     */
    private static void sortCommandList() {
        Collections.sort(commandSyntaxList);
    }
}
