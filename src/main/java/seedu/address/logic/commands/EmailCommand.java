package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.ui.NotificationCard;

/**
 * Email to the person associated with the notification card identified using index in Notification Center.
 */
public class EmailCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Email to remind the corresponding person about the notification card identified by the index number "
            + "used in Notification Center.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Directing to email app...";
    public static final String MESSAGE_ERROR = "Error occurred. Please try again later.";

    public static final String[] EMAIL_TEMPLATE = {"Dear%20",
        ",%0D%0A%0D%0AThis%20email%20is%20to%20remind%20you%20about%20",
        "%20happening%20at%20",
        ".%0D%0A%0D%0A"};

    private final Index targetIndex;

    private Person owner;

    private NotificationCard notificationCard;

    public EmailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        Desktop desktop = Desktop.getDesktop();
        String message = "mailto:" + owner.getEmail().toString() + "?subject=" + "Reminder%20for%20"
                + replaceSpaceWithHexa(notificationCard.getTitle()) + "&body="
                + EMAIL_TEMPLATE[0] + replaceSpaceWithHexa(notificationCard.getOwnerName())
                + EMAIL_TEMPLATE[1] + replaceSpaceWithHexa(notificationCard.getTitle())
                + EMAIL_TEMPLATE[2] + replaceSpaceWithHexa(notificationCard.getEndTime())
                + EMAIL_TEMPLATE[3];
        URI uri = URI.create(message);
        try {
            desktop.mail(uri);
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_ERROR);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (targetIndex.getZeroBased() >= model.getNotificationCenter().getTotalUndismmissedNotificationCards() - 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTIFICATION_CARD_INDEX);
        }
        notificationCard = model.getNotificationCenter().getNotificationCard(targetIndex);
        String ownerId = model.getNotificationCenter().getOwnerIdByIndex(targetIndex);
        owner = model.getAddressBook().findPersonById(Integer.parseInt(ownerId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }

    /**
     * Replaces all space characters with URI space character
     */
    public String replaceSpaceWithHexa(String input) {
        String[] parts = input.split(" ");
        String toReturn = "";
        for (int i = 0; i < parts.length; i++) {
            toReturn += parts[i];
            toReturn += "%20";
        }
        return toReturn;
    }
}
