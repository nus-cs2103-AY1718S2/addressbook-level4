//@@author chenchongsong
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.person.Person;

/**
 * Show all items related to a specified person.
 * The person is identified using it's last displayed index from the TravleBanker.
 */
public class ItemShowCommand extends Command {

    public static final String COMMAND_WORD = "itemshow";
    public static final String COMMAND_SHORTCUT = "is";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Show all items related to a person (specified by INDEX).\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_ITEM_SUCCESS = "Items Showed for Person: %d.\n"
            + "Money Due to Unknown Items: %.2f\n";

    private final Index targetIndex;

    public ItemShowCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());
        UniqueItemList items = targetPerson.getUniqueItemList();
        Double reasonUnknownAmount = targetPerson.getReasonUnknownAmount();
        return new CommandResult(getResultString(items, reasonUnknownAmount));
    }

    private String getResultString(UniqueItemList items, Double reasonUnknownAmount) {
        return String.format(MESSAGE_SHOW_ITEM_SUCCESS, targetIndex.getOneBased(), reasonUnknownAmount)
                + items.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemShowCommand // instanceof handles nulls
                && this.targetIndex.equals(((ItemShowCommand) other).targetIndex)); // state check
    }
}
