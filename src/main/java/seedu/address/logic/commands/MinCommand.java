//@@author Articho28
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;

import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Person;
/**
 * Finds the person to which you owe the most money
 */
public class MinCommand extends Command {

    public static final String COMMAND_WORD = "min";
    public static final String COMMAND_SHORTCUT = "mn";
    public static final String MESSAGE_SUCCESS_FOUND = "The contact to which you owe the most money is: ";
    public static final String MESSAGE_SUCCESS_NO_RESULT = "Good news! You don't owe any money.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person to which you owe the most money ";
    private CommandResult result;

    public CommandResult getResult() {
        return result;
    }

    public void setResult(CommandResult result) {
        this.result = result;
    }
    @Override
    public CommandResult execute() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = Index.fromZeroBased(0);
        double lowestDebt = 0.0;

        for (int i = 0; i < lastShownList.size(); i++) {
            Person person = lastShownList.get(i);
            if (person.getMoney().balance < lowestDebt) {
                index = Index.fromZeroBased(i);
                lowestDebt = person.getMoney().balance;
            }
            if (lowestDebt == 0.0) {
                result = new CommandResult(MESSAGE_SUCCESS_NO_RESULT);
            } else {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
                result = new CommandResult(MESSAGE_SUCCESS_FOUND + lastShownList.get(index.getZeroBased()).getName());
            }
        }
        return result;
    }
}
