//@@author ZhangYijiong
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.ProcessOrderCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Halal;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vegetarian;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Implementation follows {@code DeleteCommand}
 * Deletes n orders at the front of the queue, n is the user input.
 */
public class CompleteOrderCommand extends Command {

    public static final String COMMAND_WORD = "completeOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Complete n orders in the current queue, n be the user input.\n"
            + "Parameters: Number (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = " Order(s) Completed";

    private final Index targetIndex;
    private final Index numberOfTimes;

    public CompleteOrderCommand(Index targetIndex, Index numberOfTimes) {
        this.targetIndex = targetIndex;
        this.numberOfTimes = numberOfTimes;
    }

    @Override
    public CommandResult execute() throws CommandException {
        int number = numberOfTimes.getOneBased();
        while (number-- != 0) {

            List<Task> taskList = model.getFilteredTaskList();

            if (number >= taskList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            Task taskToDelete = taskList.get(targetIndex.getZeroBased());

            try {
                model.deleteTask(taskToDelete);
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be missing";
            }

            List<Person> personList = model.getFilteredPersonList();

            int editIndex=-1;
            for (Person person:personList) {
                if (person.getOrder().equals(taskToDelete.getOrder())
                        && person.getAddress().equals(taskToDelete.getAddress())) {
                    editIndex = personList.indexOf(person);
                    break;
                }
            }

            Person personToEdit = personList.get(editIndex);
            // labels order with tag "Cooked"
            Person editedPerson = createNewTaggedPerson(personToEdit,"Cooked");

            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(ProcessOrderCommand.MESSAGE_DUPLICATE_TASK);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        }
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteOrderCommand // instanceof handles nulls
                && this.targetIndex.equals(((CompleteOrderCommand) other).targetIndex)
                && this.numberOfTimes.equals(((CompleteOrderCommand) other).numberOfTimes)); // state check
    }

    protected Person createNewTaggedPerson(Person personToEdit,String tag) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Order updatedOrder = personToEdit.getOrder();
        Address updatedAddress = personToEdit.getAddress();
        Halal updatedHalal = personToEdit.getHalal();
        Vegetarian updatedVegetarian = personToEdit.getVegetarian();
        UniqueTagList updatedTags = new UniqueTagList(personToEdit.getTags());

        try {
            updatedTags.add(new Tag(tag));
        } catch (UniqueTagList.DuplicateTagException dte){
            //does not add tag "processing" if already exists
        }
        return new Person(updatedName, updatedPhone, updatedOrder, updatedAddress,
                updatedHalal, updatedVegetarian, updatedTags);
    }
}

