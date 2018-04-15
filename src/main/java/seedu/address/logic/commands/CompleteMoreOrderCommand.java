//@@author ZhangYijiong
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Implementation follows {@code CompleteOneOrderCommand}
 * Deletes n orders at the front of the queue, n is the user input.
 */
public class CompleteMoreOrderCommand extends CompleteOneOrderCommand {

    public static final String COMMAND_WORD = "completeMore";
    public static final String COMMAND_ALIAS = "cM";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Complete n orders in the current queue, n be the user input.\n"
            + "Parameters: Number (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Order(s) Completed";

    private final Index targetIndex;
    private final Index numberOfTimes;

    public CompleteMoreOrderCommand(Index targetIndex, Index numberOfTimes) {
        this.targetIndex = targetIndex;
        this.numberOfTimes = numberOfTimes;
    }

    @Override
    public CommandResult execute() throws CommandException {
        int number = numberOfTimes.getOneBased();
        while (number-- != 0) {

            List<Task> taskList = model.getFilteredTaskList();

            checkIsMoreThanFullCapacity(number, taskList);

            Task taskToDelete = taskList.get(targetIndex.getZeroBased());

            deleteSelectedTask(taskToDelete);

            List<Person> personList = model.getFilteredPersonList();

            int editIndex = CommandHelper.findIndexOfMatchingPerson(taskToDelete, personList);

            updateDeletedPersonTag(personList, editIndex);
        }
        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS));
    }

    /**
     * Checks whether given number is more than the orders in the processing queue
     * @param number number of orders to be completed
     * @param taskList list of task
     * @throws CommandException throws exception
     */
    private void checkIsMoreThanFullCapacity(int number, List<Task> taskList) throws CommandException {
        if (number >= taskList.size()) {
            throw new CommandException("There are only " + taskList.size()
                    + " orders being cooking");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteMoreOrderCommand // instanceof handles nulls
                && this.numberOfTimes.equals(((CompleteMoreOrderCommand) other).numberOfTimes)); // state check
    }
}

