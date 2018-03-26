package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.commons.events.ui.BirthdayNotificationEvent;

/**
 * Format full help instructions for every command for display.
 */
public class BirthdaysCommand extends Command {

    private boolean isToday;

    public static final String COMMAND_WORD = "birthdays";

    public static final String ADDITIONAL_COMMAND_PARAMETER = "today";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list containing all persons' birthdays."
            + "Optional Parameters: "
            + ADDITIONAL_COMMAND_PARAMETER
            + "Example: " + COMMAND_WORD + ", " + COMMAND_WORD + " " + ADDITIONAL_COMMAND_PARAMETER;

    public static final String SHOWING_BIRTHDAY_MESSAGE = "Displaying birthday list";

    public static final String SHOWING_BIRTHDAY_NOTIFICATION = "Displaying today's birthdays";

    public BirthdaysCommand(boolean todays){
        requireNonNull(todays);
        this.isToday = todays;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);

        if (isToday) {
            EventsCenter.getInstance().post(new BirthdayNotificationEvent(model.getAddressBook().getPersonList()));
            return new CommandResult(SHOWING_BIRTHDAY_NOTIFICATION);
        }
        else {
            EventsCenter.getInstance().post(new BirthdayListEvent(model.getAddressBook().getPersonList()));
        }

        return new CommandResult(SHOWING_BIRTHDAY_MESSAGE);
    }
}
