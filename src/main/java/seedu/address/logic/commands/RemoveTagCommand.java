//@@author chenchongsong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.money.Money;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_SHORTCUT = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the specific tags of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_TAG + "friends";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Remove Tags for Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_NOT_EXIST = "Your Input Contains Non-existent Tag(s)!";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public RemoveTagCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        try {
            personToEdit = lastShownList.get(index.getZeroBased());
            editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
        } catch (IllegalArgumentException iae) {
            throw new CommandException(MESSAGE_TAG_NOT_EXIST);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * Remove all tags within {@code editPersonDescriptor} from the original tag list of personToEdit
     */
    public static Person createEditedPerson(
            Person personToEdit, EditPersonDescriptor toBeRemovedTagsDescriptor) throws IllegalArgumentException {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Money updatedMoney = personToEdit.getMoney();

        Set<Tag> toBeRemovedTags = toBeRemovedTagsDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Tag> originalTags = personToEdit.getTags();
        checkArgument(allTagsExistOriginally(toBeRemovedTags, originalTags), MESSAGE_TAG_NOT_EXIST);

        Set<Tag> updatedTags = getUpdatedTags(toBeRemovedTags, originalTags);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedMoney, updatedTags);
    }

    public static Set<Tag> getUpdatedTags(Set<Tag> toBeRemovedTags, Set<Tag> originalTags) {
        Set<Tag> updatedTags = new HashSet<>();
        for (Tag t: originalTags) {
            if (!toBeRemovedTags.contains(t)) {
                updatedTags.add(t);
            }
        }
        return updatedTags;
    }

    /**
     * make sure all tags originally exist in the person info
     * @param toBeRemovedTags
     * @param originalTags
     * @return
     */
    private static boolean allTagsExistOriginally(Set<Tag> toBeRemovedTags, Set<Tag> originalTags) {
        for (Tag tag: toBeRemovedTags) {
            if (!originalTags.contains(tag)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }
        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

}
