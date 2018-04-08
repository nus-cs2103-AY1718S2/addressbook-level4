package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISPLAY_PIC;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.DisplayPic;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Updates a student's display picture in Your TA.
 */
//@@author Alaru
public class UpdateDisplayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "updateDP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the display picture for a student "
            + "Parameters: [INDEX] (must be a positive integer) "
            + PREFIX_DISPLAY_PIC + "[PATH TO IMAGE]\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_DISPLAY_PIC + "C:\\Users\\Name\\Desktop\\John.png";

    public static final String MESSAGE_SUCCESS = "Display Picture successfully updated for %1$s!";

    private final Index targetIndex;
    private final String newFilePath;

    private Person personToUpdate;
    private Person updatedPerson;

    /**
     * Creates an MarkCommand to add the participation marks of {@code marks}
     */
    public UpdateDisplayCommand(Index index, String filePath) {
        requireNonNull(index);
        requireNonNull(filePath);
        this.targetIndex = index;
        this.newFilePath = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.updatePerson(personToUpdate, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        try {
            updatedPerson.getDisplayPic().saveDisplay(updatedPerson.getDetails());
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        model.addDeleteItem(personToUpdate.getDisplayPic().toString());
        model.addDeleteItem(updatedPerson.getDisplayPic().toString());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToUpdate.getName().toString()));

    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToUpdate = lastShownList.get(targetIndex.getZeroBased());
        updatedPerson = createUpdatedPerson(personToUpdate);

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToUpdate}
     * edited with the new Display Pic.
     */
    private Person createUpdatedPerson(Person personToUpdate) throws CommandException {
        assert personToUpdate != null;

        try {
            DisplayPic updatedDisplay = new DisplayPic(newFilePath, personToUpdate.getDetails());
            return new Person(personToUpdate.getName(), personToUpdate.getMatricNumber(),
                    personToUpdate.getPhone(), personToUpdate.getEmail(), personToUpdate.getAddress(),
                    updatedDisplay, personToUpdate.getParticipation(), personToUpdate.getTags());
        } catch (IllegalValueException | IllegalArgumentException ie) {
            throw new CommandException(ie.getMessage());
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateDisplayCommand // instanceof handles nulls
                && targetIndex.equals(((UpdateDisplayCommand) other).targetIndex)
                && newFilePath.equals(((UpdateDisplayCommand) other).newFilePath));
    }
}
