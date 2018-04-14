//@@author ZhangYijiong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.queue.TaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.model.person.Person;

/**
 * Add an order to the application's processing queue
 */

public class ProcessNextCommand extends ProcessOrderCommand {
    public static final String COMMAND_WORD = "processNext";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the first unprocessed order into the processing queue\n"
            + "No parameter needed\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_All_PROCESSING = "All Order have been processed.";
    private static int noOrderToBeProcessed = -1;

    private int targetIndex;

    private Task toAdd;

    public ProcessNextCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        targetIndex = noOrderToBeProcessed;

        List<Person> lastShownList = model.getFilteredPersonList();
        for (Person person:lastShownList) {
            if (person.getTagList().contains(new Tag("Processing")) == false) {
                targetIndex = lastShownList.indexOf(person);
                break;
            }
        }

        if (targetIndex==noOrderToBeProcessed) {
            throw new CommandException(MESSAGE_All_PROCESSING);
        }

        // inception time of the order will be shown in description
        String orderTime = getCurrentTime();

        List<Task> taskList = model.getFilteredTaskList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (taskList.size() >= TaskList.maxCapacity) {
            throw new CommandException(MESSAGE_FULL_CAPACITY);
        }

        Person personToAdd = lastShownList.get(targetIndex);

        toAdd = new Task(personToAdd, orderTime);

        Person personToEdit = personToAdd;
        // labels person with tag "Processing"
        Person editedPerson = createProcessingPerson(personToEdit);

        return addAndTag(toAdd, personToEdit, editedPerson);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessNextCommand // instanceof handles nulls
                && toAdd.equals(((ProcessNextCommand) other).toAdd));
    }
}

