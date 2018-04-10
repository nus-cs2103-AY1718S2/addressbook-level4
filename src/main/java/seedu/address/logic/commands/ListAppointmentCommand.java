package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeDayViewRequestEvent;
import seedu.address.commons.events.ui.ChangeMonthViewRequestEvent;
import seedu.address.commons.events.ui.ChangeWeekViewRequestEvent;
import seedu.address.commons.events.ui.ChangeYearViewRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.NoAppointmentInYearException;

//@@author wynonaK
/**
 * Lists appointments based on the specified year, month, week or day.
 */
public class ListAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "listappt";
    public static final String COMMAND_ALIAS = "la";

    public static final String MESSAGE_SUCCESS = "Successfully listed appointments in the %1$s view requested.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": To handle all appointment related listings.\n"
            + "Parameters: OPTION FIELD\n"
            + "Accepted Options: -y (YEAR VIEW), -m (MONTH VIEW), -w (WEEK VIEW), -d (DAY VIEW)\n"
            + "YEAR VIEW accepts a year field in the format of yyyy.\n"
            + "MONTH VIEW accepts a year and month field in the format of yyyy-MM"
            + " or just a month field in the format of MM, of which the year will be defaulted to this current year.\n"
            + "WEEK VIEW accepts a date field in the format of yyyy-MM-dd.\n"
            + "DAY VIEW accepts a date field in the format of yyyy-MM-dd.\n"
            + "If nothing is given as a FIELD, it will return the specified view of the current date.\n"
            + "You can only list past appointments if you had an appointment in the year of the specified field.";

    private int type = 0; //year = 1, month = 2, week = 3, day = 4.
    private Year year = null;
    private YearMonth yearMonth = null;
    private LocalDate date = null;

    public ListAppointmentCommand(int type, Year year) {
        this.type = type;
        this.year = year;
    }

    public ListAppointmentCommand(int type, YearMonth yearMonth) {
        this.type = type;
        this.yearMonth = yearMonth;
    }

    public ListAppointmentCommand(int type, LocalDate date) {
        this.type = type;
        this.date = date;
    }


    private CommandResult getYearView() throws NoAppointmentInYearException {
        if (year.isBefore(Year.now())){
            if (!checkPastAppointment(year.getValue())) {
                throw new NoAppointmentInYearException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        EventsCenter.getInstance().post(new ChangeYearViewRequestEvent(year));
        return new CommandResult(String.format(MESSAGE_SUCCESS, "year"));
    }

    private CommandResult getMonthView() throws NoAppointmentInYearException {
        if (yearMonth.isBefore(YearMonth.now())){
            if (!checkPastAppointment(yearMonth.getYear())) {
                throw new NoAppointmentInYearException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        EventsCenter.getInstance().post(new ChangeMonthViewRequestEvent(yearMonth));
        return new CommandResult(String.format(MESSAGE_SUCCESS, "month"));
    }

    private CommandResult getWeekView() throws NoAppointmentInYearException {
        if (date.isBefore(LocalDate.now())){
            if (!checkPastAppointment(date.getYear())) {
                throw new NoAppointmentInYearException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        EventsCenter.getInstance().post(new ChangeWeekViewRequestEvent(date));
        return new CommandResult(String.format(MESSAGE_SUCCESS, "week"));
    }

    private CommandResult getDayView() throws NoAppointmentInYearException {
        if (date.isBefore(LocalDate.now())){
            if (!checkPastAppointment(date.getYear())) {
                throw new NoAppointmentInYearException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        EventsCenter.getInstance().post(new ChangeDayViewRequestEvent(date));
        return new CommandResult(String.format(MESSAGE_SUCCESS, "day"));
    }

    private boolean checkPastAppointment(int year) {
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        List<Appointment> appointmentList = model.getFilteredAppointmentList();
        for (Appointment appointment : appointmentList){
            if  (appointment.getDateTime().getYear() == year)  {
                return true;
            }
        }
        return false;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            switch (type) {
            case 1:
                return getYearView();
            case 2:
                return getMonthView();
            case 3:
                return getWeekView();
            case 4:
                return getDayView();
            default:
                throw new CommandException(MESSAGE_USAGE);
            }
        } catch (NoAppointmentInYearException e) {
            throw new CommandException("You can only list past appointments if you had an appointment"
                    + " in the year of the specified field!");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListAppointmentCommand // instanceof handles nulls
                && date.equals(((ListAppointmentCommand) other).date)
                && yearMonth.equals(((ListAppointmentCommand) other).yearMonth)
                && year.equals(((ListAppointmentCommand) other).year));
    }
}
