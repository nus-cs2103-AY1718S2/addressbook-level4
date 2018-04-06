//@@author amad-person
package seedu.address.logic;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.commands.ChangeOrderStatusCommand;
import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEntryCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCalendarEntryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCalendarCommand;

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
        commandSyntaxList.add(AddEntryCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(AddOrderCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(ChangeThemeCommand.COMMAND_WORD);
        commandSyntaxList.add(ChangeOrderStatusCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(ClearCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteEntryCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteGroupCommand.COMMAND_WORD);
        commandSyntaxList.add(DeleteOrderCommand.COMMAND_WORD);
        commandSyntaxList.add(DeletePreferenceCommand.COMMAND_WORD);
        commandSyntaxList.add(EditCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(EditOrderCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(ExitCommand.COMMAND_WORD);
        commandSyntaxList.add(FindCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(FindGroupCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(FindPreferenceCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(HelpCommand.COMMAND_WORD);
        commandSyntaxList.add(HistoryCommand.COMMAND_WORD);
        commandSyntaxList.add(ListCommand.COMMAND_WORD);
        commandSyntaxList.add(ListCalendarEntryCommand.COMMAND_WORD);
        commandSyntaxList.add(ListOrderCommand.COMMAND_WORD);
        commandSyntaxList.add(RedoCommand.COMMAND_WORD);
        commandSyntaxList.add(SelectCommand.COMMAND_SYNTAX);
        commandSyntaxList.add(UndoCommand.COMMAND_WORD);
        commandSyntaxList.add(ViewCalendarCommand.COMMAND_WORD);

        sortCommandList();
    }

    /**
     * Sorts commandSyntaxList in lexicographical order.
     */
    private static void sortCommandList() {
        Collections.sort(commandSyntaxList);
    }
}
