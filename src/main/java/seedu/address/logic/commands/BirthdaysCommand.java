package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.BirthdayListEvent;

/**
 * Format full help instructions for every command for display.
 */
public class BirthdaysCommand extends Command {

    public static final String COMMAND_WORD = "birthdays";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list containing all persons' birthdays.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_BIRTHDAY_MESSAGE = "Displaying birthday list";

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        EventsCenter.getInstance().post(new BirthdayListEvent(model.getAddressBook().getPersonList()));
        return new CommandResult(SHOWING_BIRTHDAY_MESSAGE);
    }
}
