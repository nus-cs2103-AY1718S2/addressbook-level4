//@@author ZhangYijiong
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Implementation follows {@code DeleteCommand}
 * Deletes an order in the processing queue identified by its index
 */
public class CompleteOneOrderCommand extends Command {

    public static final String COMMAND_WORD = "completeOne";
    public static final String COMMAND_ALIAS = "cOne";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the order identified by the index number in the processing queue.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Completed Order: %1$s";

    protected Index targetIndex;

    public CompleteOneOrderCommand() {}

    public CompleteOneOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDelete = lastShownList.get(targetIndex.getZeroBased());

        deleteSelectedTask(taskToDelete);

        List<Person> personList = model.getFilteredPersonList();

        int editIndex = CommandHelper.findIndexOfMatchingPerson(taskToDelete, personList);

        updateDeletedPersonTag(personList, editIndex);

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    /**
     * Deletes the selected task from taskList
     * @param taskToDelete task to be deleted
     */
    protected void deleteSelectedTask(Task taskToDelete) {
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException enfe) {
            assert false : "The target task cannot be missing";
        }
    }

    /**
     * Marks matching person in the personList as "Cooked"
     * @param personList list of person
     * @param editIndex index of the person in the personList to be edited
     * @throws CommandException throws various exceptions
     */
    protected void updateDeletedPersonTag(List<Person> personList, int editIndex) throws CommandException {
        try {
            Person personToEdit = personList.get(editIndex);
            // labels order with tag "Cooked"
            Person editedPerson = CommandHelper.createNewTaggedPerson(personToEdit, "Cooked");

            model.updatePerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (NullPointerException npe) {
            throw new CommandException("No matching order in order queue");
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(ProcessOrderCommand.MESSAGE_DUPLICATE_TASK);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteOneOrderCommand // instanceof handles nulls
                && this.targetIndex.equals(((CompleteOneOrderCommand) other).targetIndex)); // state check
    }
}
