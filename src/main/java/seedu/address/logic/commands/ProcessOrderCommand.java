//@@author ZhangYijiong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Address;
import seedu.address.model.person.Halal;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vegetarian;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.queue.TaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Add an order to the application's processing queue, label the corresponding order in the
 * order queue as Processed
 */

public class ProcessOrderCommand extends Command {
    public static final String COMMAND_WORD = "process";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the order identified by the index number into the processing queue\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "New Order added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This order already exists in the processing queue";
    public static final String MESSAGE_FULL_CAPACITY = "Kitchen is at full capacity. No available chef.";

    protected Index targetIndex;

    protected Task toAdd;

    public ProcessOrderCommand() {}

    /**
     *
     * @param targetIndex index of order to be processed in the order queue
     */
    public ProcessOrderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        // inception time of the order will be shown in description
        String orderTime = getCurrentTime();

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Task> taskList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (taskList.size() >= TaskList.getMaxCapacity()) {
            throw new CommandException(MESSAGE_FULL_CAPACITY);
        }

        Person personToAdd = lastShownList.get(targetIndex.getZeroBased());

        toAdd = new Task(personToAdd, orderTime);

        Person personToEdit = personToAdd;
        // labels person with tag "Processing"
        Person editedPerson = createNewTaggedPerson(personToEdit, "Processed");

        addAndTag(toAdd, personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     *
     * @param toAdd task to be added
     * @param personToEdit original person
     * @param editedPerson replacement
     * @throws CommandException
     */
    protected void addAndTag(Task toAdd, Person personToEdit, Person editedPerson) throws CommandException {
        try {
            model.addTask(toAdd);

            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_TASK);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);


        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    protected String getCurrentTime() {
        Date date = new Date();
        date.getTime();

        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY) + ":"
                + cal.get(Calendar.MINUTE) + ":"
                + cal.get(Calendar.SECOND);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessOrderCommand // instanceof handles nulls
                && toAdd.equals(((ProcessOrderCommand) other).toAdd));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     */
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
