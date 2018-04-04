package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_FOCUS;

import java.time.LocalDate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.CalendarFocusEvent;
//@@author yuxiangSg
/**
 * Look at a specific date give the date to look
 */
public class LookDateCommand extends Command {
    public static final String COMMAND_WORD = "look";

    public static final String DATE_VALIDATION = "d/MM/yyyy";

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in the format of dd/MM/yyyy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Focus on a given date ."
            + "Parameters: "
            + PREFIX_DATE_FOCUS + "DATE TO FOCUS "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_DATE_FOCUS + "11/03/2018";

    public static final String FOCUS_DATE_MESSAGE = "FOCUS ON DATE";

    /**
     * Creates an RemoveAppointmentCommand to remove the specified {@code searchText}
     */
    final LocalDate dateToLook;

    public LookDateCommand(LocalDate dateToLook) {
        requireNonNull(dateToLook);
        this.dateToLook = dateToLook;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarFocusEvent(dateToLook));
        return new CommandResult(FOCUS_DATE_MESSAGE);
    }
}
