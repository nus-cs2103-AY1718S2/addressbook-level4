//@@author ZhangYijiong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.queue.TaskList;
import seedu.address.model.task.Task;

/**
 * Add the first unprocessed order in the order queue to the application's
 * processing queue, label the corresponding order in the
 * order queue as Processed
 */

public class ProcessNextCommand extends ProcessOrderCommand {
    public static final String COMMAND_WORD = "processNext";
    public static final String COMMAND_ALIAS = "pN";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the first unprocessed order into the processing queue\n"
            + "No parameter needed\n"
            + "Example: " + COMMAND_WORD;

    private static final String MESSAGE_All_PROCESSING = "All Order have been processed.";
    private static int noOrderToBeProcessed = -1;

    protected int targetIndex;

    protected Task toAdd;

    public ProcessNextCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        targetIndex = noOrderToBeProcessed;

        List<Person> lastShownList = model.getFilteredPersonList();

        targetIndex = CommandHelper.findIndexOfPersonToBeProcessed(lastShownList);

        if (targetIndex == noOrderToBeProcessed) {
            throw new CommandException(MESSAGE_All_PROCESSING);
        }

        // inception time of the order will be shown in description
        String orderTime = getCurrentTime();

        List<Task> taskList = model.getFilteredTaskList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (taskList.size() >= TaskList.getMaxCapacity()) {
            throw new CommandException(MESSAGE_FULL_CAPACITY);
        }

        Person personToAdd = lastShownList.get(targetIndex);

        toAdd = new Task(personToAdd, orderTime);

        if (CommandHelper.checkIsProcessed(personToAdd)) {
            throw new CommandException(MESSAGE_ALREADY_PROCESSED);
        }

        //
        Person personToEdit = personToAdd;
        // labels person with tag "Processing"
        Person editedPerson = CommandHelper.createNewTaggedPerson(personToEdit, "Processed");

        addAndTag(toAdd, personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessNextCommand // instanceof handles nulls
                && toAdd.equals(((ProcessNextCommand) other).toAdd));
    }
}

