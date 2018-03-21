package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Update status of an existing person in HR+.
 */
public class StatusCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update status for the person "
            + "by the index number used in the last person listing. "
            + "Existing status will be overwritten by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "STATUS_INDEX(by the list below)\n"
            + "1. " + Status.STATUS_NEW + "\n"
            + "2. " + Status.STATUS_INTERVIEW_FIRST_ROUND + "\n"
            + "3. " + Status.STATUS_INTERVIEW_SECOND_ROUND + "\n"
            + "4. " + Status.STATUS_REJECTED + "\n"
            + "5. " + Status.STATUS_WAITLIST + "\n"
            + "6. " + Status.STATUS_OFFERED + "\n"
            + "7. " + Status.STATUS_ACCEPTED + "\n"
            + "8. " + Status.STATUS_WITHDRAWN + "\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_STATUS_SUCCESS =
            "Status of person named %1$s has been updated to '%2$s'";

    private final Index index;
    private final Status updatedStatus;

    private Person personToUpdateStatus;
    private Person updatedPerson;

    /**
     * @param index of the person in the filtered person list to update status
     * @param updatedStatus updatedStatus
     */
    public StatusCommand(Index index, Status updatedStatus) {
        requireNonNull(index);
        requireNonNull(updatedStatus);

        this.index    = index;
        this.updatedStatus = updatedStatus;
    }

    public Index getIndex() {
        return index;
    }

    public Status getUpdatedStatus() {
        return updatedStatus;
    }

    public Person getPersonToUpdateStatus() {
        return personToUpdateStatus;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        try {
            model.updatePerson(personToUpdateStatus, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target person cannot become a duplicate of another person "
                    + "via updating status.");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing.");
        }

        return new CommandResult(String.format(MESSAGE_STATUS_SUCCESS,
                updatedPerson.getName(), updatedStatus.value));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToUpdateStatus = lastShownList.get(index.getZeroBased());
        updatedPerson = createUpdatedPerson(personToUpdateStatus, updatedStatus);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUpdateStatus}
     * with updated with {@code statusIndex}.
     */
    private static Person createUpdatedPerson(Person personToUpdateStatus, Status updatedStatus) {
        requireAllNonNull(personToUpdateStatus);

        return new Person(personToUpdateStatus.getName(), personToUpdateStatus.getPhone(),
                personToUpdateStatus.getEmail(), personToUpdateStatus.getAddress(),
                personToUpdateStatus.getExpectedGraduationYear(), personToUpdateStatus.getMajor(),
                personToUpdateStatus.getRating(), personToUpdateStatus.getResume(),
                personToUpdateStatus.getInterviewDate(), updatedStatus, personToUpdateStatus.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StatusCommand)) {
            return false;
        }

        // State check
        StatusCommand i = (StatusCommand) other;
        return getIndex().equals(i.getIndex())
                && getUpdatedStatus().equals(i.getUpdatedStatus())
                && Objects.equals(getPersonToUpdateStatus(), i.getPersonToUpdateStatus());
    }
}
