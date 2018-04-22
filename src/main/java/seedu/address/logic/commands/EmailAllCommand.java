//@@author IzHoBX
package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;

/**
 * Email to the person associated with the notification card identified using index in Notification Center.
 */
public class EmailAllCommand extends Command {

    public static final String COMMAND_WORD = "emailall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails to remind about all notification cards.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Directing to email app...";
    public static final String MESSAGE_ERROR = "Error occurred. Please try again later.";

    public static final String EMAIL_TEMPLATE = "Dear%20all,"
            + "%0D%0A%0D%0AThis%20email%20is%20to%20remind%20you%20that%20you%20have%20a%20task%20expired.%0D%0A%0D%0A";

    private String allEmails = "";

    @Override
    public CommandResult execute() {
        //notificationCards in notification center is 1-based
        for (int i = 1; i < model.getNotificationCenter().getTotalUndismmissedNotificationCards(); i++) {
            Index targetIndex = Index.fromOneBased(i);
            String ownerId = model.getNotificationCenter().getOwnerIdByIndex(targetIndex);
            allEmails += model.getAddressBook().findPersonById(Integer.parseInt(ownerId)).getEmail().toString() + ",";
        }

        Desktop desktop = Desktop.getDesktop();
        String message = "mailto:" + allEmails + "?subject=" + "Reminder%20for%20Task%20Expiry"
                + "&body=" + EMAIL_TEMPLATE;
        URI uri = URI.create(message);
        try {
            desktop.mail(uri);
        } catch (IOException e) {
            LogsCenter.getLogger(EmailAllCommand.class).info("IOException");
            return new CommandResult(MESSAGE_ERROR);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
