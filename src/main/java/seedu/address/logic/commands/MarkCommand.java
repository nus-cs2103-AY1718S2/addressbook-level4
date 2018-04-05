package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK_PARTICIPATION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a person to the address book.
 */
//@@author Alaru
public class MarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "markPart";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the participation for a student "
            + "Parameters: [INDEX] (must be a positive integer) "
            + PREFIX_MARK_PARTICIPATION + "[MARKS]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_MARK_PARTICIPATION + "50";

    public static final String MESSAGE_SUCCESS = "Participation marked for %1$s!";

    private final Index targetIndex;
    private final Integer marks;

    private Person personToMark;
    private Person updatedPerson;

    /**
     * Creates an MarkCommand to add the participation marks of {@code marks}
     */
    public MarkCommand(Index index, Integer marks) {
        requireNonNull(index);
        requireNonNull(marks);
        this.targetIndex = index;
        this.marks = marks;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.updatePerson(personToMark, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToMark.getName().toString()));

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToMark = lastShownList.get(targetIndex.getZeroBased());
        updatedPerson = createUpdatedPerson(personToMark);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private Person createUpdatedPerson(Person personToMark) {
        assert personToMark != null;

        Integer newMarks = marks + personToMark.getParticipation().getMarks();

        Participation updatedPart = new Participation(newMarks);

        return new Person(personToMark.getName(), personToMark.getMatricNumber(),
                personToMark.getPhone(), personToMark.getEmail(), personToMark.getAddress(),
                personToMark.getDisplayPic(), updatedPart, personToMark.getTags());

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkCommand // instanceof handles nulls
                && targetIndex.equals(((MarkCommand) other).targetIndex)
                && marks.equals(((MarkCommand) other).marks));
    }
}
