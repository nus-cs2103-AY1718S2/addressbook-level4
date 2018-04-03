package seedu.address.logic.commands;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.SwitchTabRequestEvent;
import seedu.address.logic.GmailClient;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

//@@author KevinCJH
/**
 * Send an email to the person identified using it's last displayed index from the address book.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Send an email to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final int TAB_ID_EMAIL = 3;

    public static final String MESSAGE_EMAIL_PERSON_SUCCESS = "Drafting email to: %1$s";

    private final Index targetIndex;

    private Person personToEmail;

    public EmailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEmail = lastShownList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID_EMAIL));
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));

        GmailClient.getInstance();

        return new CommandResult(String.format(MESSAGE_EMAIL_PERSON_SUCCESS, personToEmail.getEmail()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex) // state check
                && Objects.equals(this.personToEmail, ((EmailCommand) other).personToEmail));
    }
}
