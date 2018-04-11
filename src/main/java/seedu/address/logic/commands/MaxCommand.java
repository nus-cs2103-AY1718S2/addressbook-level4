//@@author pkuhanan
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;

import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Person;

/**
 * Finds the person that owes the most money
 */
public class MaxCommand extends Command {
    public static final String COMMAND_WORD = "max";
    public static final String COMMAND_SHORTCUT = "mx";
    public static final String MESSAGE_SUCCESS = "The contact who owes you the most money is: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person that owes the most money ";

    @Override
    public CommandResult execute() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = Index.fromZeroBased(0);
        Double highestDebt = 0.0;

        for (int i = 0; i < lastShownList.size(); i++) {
            Person person = lastShownList.get(i);
            if (person.getMoney().balance > highestDebt) {
                index = Index.fromZeroBased(i);
                highestDebt = person.getMoney().balance;
            }
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(MESSAGE_SUCCESS + lastShownList.get(index.getZeroBased()).getName());
    }
}
