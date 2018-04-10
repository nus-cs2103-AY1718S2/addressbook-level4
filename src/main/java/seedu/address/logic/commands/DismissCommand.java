package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.exceptions.NotificationNotFoundException;
import seedu.address.ui.NotificationCard;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DismissCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "dismiss";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Dismisses the notification card identified by the index number used in Notification Center.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DISMISS_SUCCESS = "Dismissed notification card: %1$s";
    public static final String MESSAGE_ERROR = "Error occurred. Please try again later.";

    private final Index targetIndex;

    private Notification notificationToDelete;

    public DismissCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        NotificationCard toDelete;
        try {
            toDelete = model.deleteNotificationByIndex(targetIndex);
        } catch (NotificationNotFoundException e) {
            //should not happen, because id is obtained from NotificationCenter
            e.printStackTrace();
            return new CommandResult(MESSAGE_ERROR);
        }
        return new CommandResult(String.format(MESSAGE_DISMISS_SUCCESS, toDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (targetIndex.getZeroBased() >= model.getNotificationCenter().getTotalUndismmissedNotificationCards()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTIFICATION_CARD_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DismissCommand // instanceof handles nulls
                && this.targetIndex.equals(((DismissCommand) other).targetIndex)); // state check
    }
}
