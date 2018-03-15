package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * Schedule interview of an existing person in the address book.
 */
public class InterviewCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "interview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule interview for the person "
            + "by the index number used in the last person listing. "
            + "Existing scheduled date will be overwritten by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "DATETIME (parse by natural language)\n"
            + "Example: " + COMMAND_WORD + " 1 next Friday at 3pm";

    public static final String MESSAGE_INTERVIEW_PERSON_SUCCESS = "Interview of person named %1$s has been scheduled on %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final LocalDateTime dateTime;

    private Person personToInterview;
    private Person scheduledPerson;

    /**
     * @param index of the person in the filtered person list to schedule interview
     * @param dateTime of the interview
     */
    public InterviewCommand(Index index, LocalDateTime dateTime) {
        requireNonNull(index);
        requireNonNull(dateTime);

        this.index    = index;
        this.dateTime = dateTime;
    }

    public Index getIndex() {
        return index;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Person getPersonToInterview() {
        return personToInterview;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToInterview, scheduledPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_INTERVIEW_PERSON_SUCCESS,
                scheduledPerson.getName(), dateTime.toString()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToInterview = lastShownList.get(index.getZeroBased());
        scheduledPerson = createScheduledPerson(personToInterview, dateTime);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToInterview}
     * with updated with {@code dateTime}.
     */
    private static Person createScheduledPerson(Person personToInterview, LocalDateTime dateTime) {
        requireAllNonNull(personToInterview, dateTime);

        return new Person(personToInterview.getName(), personToInterview.getPhone(), personToInterview.getEmail(),
                personToInterview.getAddress(), personToInterview.getExpectedGraduationYear(),
                personToInterview.getResume(), new InterviewDate(dateTime), personToInterview.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InterviewCommand)) {
            return false;
        }

        // State check
        InterviewCommand i = (InterviewCommand) other;
        return getIndex().equals(i.getIndex())
                && getDateTime().equals(i.getDateTime())
                && Objects.equals(getPersonToInterview(), i.getPersonToInterview());
    }
}
