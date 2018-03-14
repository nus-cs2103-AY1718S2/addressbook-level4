package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Remove a Person from favourites
 */
public class UnfavouriteCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove a person from favourites. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Person removed from favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private Person personToUnfavourite;
    private Person editedPerson;

    public UnfavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(personToUnfavourite, editedPerson);

        try {
            model.updatePerson(personToUnfavourite, editedPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToUnfavourite = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = createEditedPerson(personToUnfavourite);
    }

    /**
     * Create and return a copy of the target {@Code Person} to favourite with its' Favourite attribute set to false.
     */
    private static Person createEditedPerson(Person target) {
        assert target != null;

        Name name = target.getName();
        Phone phone = target.getPhone();
        Email email = target.getEmail();
        Address address = target.getAddress();
        Set<Tag> tags = target.getTags();
        Subject subject = target.getSubject();
        Favourite fav = new Favourite(false);

        return new Person(name, phone, email, address, subject, tags, fav);
    }
}
