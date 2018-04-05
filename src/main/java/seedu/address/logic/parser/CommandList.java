//@@author kush1509
package seedu.address.logic.parser;

import java.util.ArrayList;

import seedu.address.logic.commands.ClearHistoryCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.commands.appointment.CalendarCommand;
import seedu.address.logic.commands.appointment.DateCommand;
import seedu.address.logic.commands.appointment.DateTimeCommand;
import seedu.address.logic.commands.appointment.DeleteAppointmentCommand;
import seedu.address.logic.commands.appointment.MonthCommand;
import seedu.address.logic.commands.appointment.WeekCommand;
import seedu.address.logic.commands.appointment.YearCommand;
import seedu.address.logic.commands.job.JobAddCommand;
import seedu.address.logic.commands.job.JobDeleteCommand;
import seedu.address.logic.commands.job.JobMatchCommand;
import seedu.address.logic.commands.person.AddCommand;
import seedu.address.logic.commands.person.ClearCommand;
import seedu.address.logic.commands.person.DeleteCommand;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.LinkedInCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.person.SelectCommand;

/**
 * Contains the list of commands.
 */
public class CommandList {

    /* Prefix definitions */
    public final ArrayList<String> commandList = new ArrayList<String>();

    public CommandList() {

        //add all commands to the list lexicographically
        commandList.add(AddCommand.COMMAND_WORD);
        commandList.add(JobAddCommand.COMMAND_WORD);
        commandList.add(AddAppointmentCommand.COMMAND_WORD);
        commandList.add(CalendarCommand.COMMAND_WORD);
        commandList.add(ClearCommand.COMMAND_WORD);
        commandList.add(ClearHistoryCommand.COMMAND_WORD);
        commandList.add(DateCommand.COMMAND_WORD);
        commandList.add(DateTimeCommand.COMMAND_WORD);
        commandList.add(DeleteAppointmentCommand.COMMAND_WORD);
        commandList.add(DeleteCommand.COMMAND_WORD);
        commandList.add(JobDeleteCommand.COMMAND_WORD);
        commandList.add(EditCommand.COMMAND_WORD);
        commandList.add(EmailCommand.COMMAND_WORD);
        commandList.add(ExitCommand.COMMAND_WORD);
        commandList.add(FindCommand.COMMAND_WORD);
        commandList.add(HelpCommand.COMMAND_WORD);
        commandList.add(HistoryCommand.COMMAND_WORD);
        commandList.add(LinkedInCommand.COMMAND_WORD);
        commandList.add(ListCommand.COMMAND_WORD);
        commandList.add(JobMatchCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(MonthCommand.COMMAND_WORD);
        commandList.add(RedoCommand.COMMAND_WORD);
        commandList.add(SelectCommand.COMMAND_WORD);
        commandList.add(UndoCommand.COMMAND_WORD);
        commandList.add(WeekCommand.COMMAND_WORD);
        commandList.add(YearCommand.COMMAND_WORD);

        //sort
        commandList.sort(String::compareToIgnoreCase);
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

        case FindCommand.COMMAND_WORD:
            return FindCommand.COMMAND_SYNTAX;

        case JobAddCommand.COMMAND_WORD:
            return JobAddCommand.COMMAND_SYNTAX;

        case AddAppointmentCommand.COMMAND_WORD:
            return AddAppointmentCommand.COMMAND_SYNTAX;

        default:
            return matchedCommandWord;
        }
    }
}
