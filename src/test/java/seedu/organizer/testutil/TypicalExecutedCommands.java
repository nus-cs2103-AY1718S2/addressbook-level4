package seedu.organizer.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.CurrentMonthCommand;
import seedu.organizer.logic.commands.ListCommand;
import seedu.organizer.logic.commands.NextMonthCommand;
import seedu.organizer.logic.commands.PreviousMonthCommand;

/**
 * A utility class containing a list of commands to be used in tests.
 */
public class TypicalExecutedCommands {

    public static final String LIST_COMMAND_WORD = ListCommand.COMMAND_WORD;
    public static final String ADD_COMMAND_WORD = AddCommand.COMMAND_WORD;
    public static final String PREVIOUS_MONTH_COMMAND_WORD = PreviousMonthCommand.COMMAND_WORD;
    public static final String PREVIOUS_MONTH_COMMAND_ALIAS = PreviousMonthCommand.COMMAND_ALIAS;
    public static final String NEXT_MONTH_COMMAND_WORD = NextMonthCommand.COMMAND_WORD;
    public static final String NEXT_MONTH_COMMAND_ALIAS = NextMonthCommand.COMMAND_ALIAS;
    public static final String CURRENT_MONTH_COMMAND_WORD = CurrentMonthCommand.COMMAND_WORD;
    public static final String CURRENT_MONTH_COMMAND_ALIAS = CurrentMonthCommand.COMMAND_ALIAS;

    private TypicalExecutedCommands() {
    } // prevents instantiation

    public static List<String> getTypicalExecutedCommands() {
        return new ArrayList<>(Arrays.asList(LIST_COMMAND_WORD, ADD_COMMAND_WORD));
    }
}
