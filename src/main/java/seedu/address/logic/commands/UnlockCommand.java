package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.LockManager;
import seedu.address.model.Model;

//@@author 592363789
/**
 * Unlocks the app.
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD  + ": Unlock the app.\n"
            + "Parameters: PASSWORD\n"
            + "Example: " + COMMAND_WORD + " 123456";

    public static final String MESSAGE_SUCCESS = "Successfully unlocked the app.";
    public static final String MESSAGE_NOT_LOCKED = "The app is not locked.";
    public static final String MESSAGE_WRONG_PASSWORD = "Incorrect password. Please try again.";

    private String key;

    public UnlockCommand(String key) {
        this.key = key.trim();
    }

    @Override
    public CommandResult execute() {
        requireAllNonNull(model, key);

        if (!LockManager.getInstance().isLocked()) {
            return new CommandResult(MESSAGE_NOT_LOCKED);
        }

        if (LockManager.getInstance().unlock(key)) {
            model.updateBookListFilter(Model.PREDICATE_SHOW_ALL_BOOKS);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnlockCommand // instanceof handles nulls
                && this.key.equals(((UnlockCommand) other).key)); // state check
    }
}
