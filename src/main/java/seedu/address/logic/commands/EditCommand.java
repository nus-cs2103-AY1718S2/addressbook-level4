package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.AttributeTagSetter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.pair.PairHash;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Price;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Subject;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonMatchedCannotEditException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_WORD_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_MATCHED_CANNOT_EDIT =
            "This person is currently matched. Unmatch before editing.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
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
        } catch (PersonMatchedCannotEditException e) {
            throw new CommandException(MESSAGE_MATCHED_CANNOT_EDIT);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(
            Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = getUpdatedName(personToEdit, editPersonDescriptor);
        Phone updatedPhone = getUpdatedPhone(personToEdit, editPersonDescriptor);
        Email updatedEmail = getUpdatedEmail(personToEdit, editPersonDescriptor);
        Address updatedAddress = getUpdatedAddress(personToEdit, editPersonDescriptor);
        Price updatedPrice = getUpdatedPrice(personToEdit, editPersonDescriptor);
        Subject updatedSubject = getUpdatedSubject(personToEdit, editPersonDescriptor);
        Level updatedLevel = getUpdatedLevel(personToEdit, editPersonDescriptor);
        Status updatedStatus = getUpdatedStatus(personToEdit, editPersonDescriptor);
        Role updatedRole = getUpdatedRole(personToEdit, editPersonDescriptor);
        Remark remark = getRemark(personToEdit);
        Rate rate = getRate(personToEdit);
        Set<PairHash> pairHashes = getPairHashes(personToEdit);
        Set<Tag> updatedTags = getUpdatedTags(personToEdit, editPersonDescriptor);
        Set<Tag> attributeTags = getAttributeTags(personToEdit, updatedPrice, updatedSubject, updatedLevel,
                updatedStatus, updatedRole, updatedTags);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedPrice, updatedSubject, updatedLevel, updatedStatus, updatedRole, attributeTags,
                remark, rate, pairHashes);
    }

    private static Set<PairHash> getPairHashes(Person personToEdit) {
        return personToEdit.getPairHashes();
    }

    private static Remark getRemark(Person personToEdit) {
        return personToEdit.getRemark();
    }

    private static Rate getRate(Person personToEdit) {
        return personToEdit.getRate();
    }

    private static Set<Tag> getUpdatedTags(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getTags().orElse(new HashSet<>(personToEdit.getTags()));
    }

    private static Address getUpdatedAddress(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
    }

    private static Email getUpdatedEmail(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
    }

    private static Phone getUpdatedPhone(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
    }

    private static Name getUpdatedName(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getName().orElse(personToEdit.getName());
    }

    //@@author aussiroth
    private static Role getUpdatedRole(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getRole().orElse(personToEdit.getRole());
    }

    private static Status getUpdatedStatus(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getStatus().orElse(personToEdit.getStatus());
    }

    private static Level getUpdatedLevel(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getLevel().orElse(personToEdit.getLevel());
    }

    private static Subject getUpdatedSubject(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getSubject().orElse(personToEdit.getSubject());
    }

    private static Price getUpdatedPrice(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        return editPersonDescriptor.getPrice().orElse(personToEdit.getPrice());
    }

    private static Set<Tag> getAttributeTags(Person personToEdit, Price updatedPrice,
        Subject updatedSubject, Level updatedLevel, Status updatedStatus, Role updatedRole, Set<Tag> updatedTags) {
        //create a new modifiable set of tags
        Set<Tag> attributeTags = new HashSet<>(updatedTags);
        //clean out old person's attribute tags, then add the new ones
        attributeTags = AttributeTagSetter.removePresentAttributeTags(personToEdit, attributeTags);
        attributeTags = AttributeTagSetter.addNewAttributeTags(attributeTags, updatedPrice, updatedSubject,
                updatedLevel, updatedStatus, updatedRole);
        return attributeTags;
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Price price;
        private Subject subject;
        private Level level;
        private Status status;
        private Role role;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setPrice(toCopy.price);
            setSubject(toCopy.subject);
            setLevel(toCopy.level);
            setStatus(toCopy.status);
            setRole(toCopy.role);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address,
                    this.price, this.subject, this.level, this.status, this.role, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setSubject(Subject subject) {
            this.subject = subject;
        }

        public Optional<Subject> getSubject() {
            return Optional.ofNullable(subject);
        }

        public void setLevel(Level level) {
            this.level = level;
        }

        public Optional<Level> getLevel() {
            return Optional.ofNullable(level);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        /*
         *  Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getPrice().equals(e.getPrice())
                    && getRole().equals(e.getRole())
                    && getLevel().equals(e.getLevel())
                    && getStatus().equals(e.getStatus())
                    && getTags().equals(e.getTags());
        }
    }
}
