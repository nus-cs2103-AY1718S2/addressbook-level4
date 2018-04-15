//@@author pkuhanan
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;


/**
 * Helps the user send a reminder by email to the contact
 */
public class RemindCommand extends Command {
    public static final String COMMAND_WORD = "remind";
    public static final String COMMAND_SHORTCUT = "rm";
    public static final String MESSAGE_SUCCESS = "Generated email for contact: ";
    public static final String MESSAGE_FAILURE = " does not owe you any money";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person that owes the most money ";

    private final Index index;
    private Person person;

    /**
     * @param index of the person in the filtered person list to remind
     */
    public RemindCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        person = lastShownList.get(index.getZeroBased());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));

        if (person.getMoney().balance > 0) {
            try {
                Desktop desktop;
                if (Desktop.isDesktopSupported()
                        && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                    String uri = "mailto:" + person.getEmail()
                            + "?subject=Reminder:%20Please%20pay%20me%20back&body=Please%20pay%20me%20back:%20$"
                            + person.getMoney();
                    URI mailto = new URI(uri);
                    desktop.mail(mailto);
                } else {
                    throw new AssertionError("No email client configured");
                }
                return new CommandResult(MESSAGE_SUCCESS + person.getName() + "<" + person.getEmail() + ">");
            } catch (Exception e) {
                throw new AssertionError("Problem sending email");
            }
        } else {
            return new CommandResult(person.getName() + MESSAGE_FAILURE);
        }
    }
}
