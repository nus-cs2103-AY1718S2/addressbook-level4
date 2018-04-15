package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RefreshReportPanelEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Link;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";
    public static final String COMMAND_OPTION_ADD_TAG = "add-tag";
    public static final String COMMAND_OPTION_DELETE_TAG = "delete-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_SKILLS + "SKILLS] "
            + "[" + PREFIX_TAG + "TAG]... | "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "To modify tags (case-sensitive): \n"
            + COMMAND_WORD + " -" + COMMAND_OPTION_ADD_TAG + " INDEX t/[" + PREFIX_TAG + "TAG] to add one tag \n"
            + COMMAND_WORD + " -" + COMMAND_OPTION_DELETE_TAG + " INDEX t/[" + PREFIX_TAG + "TAG] to delete one tag";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

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
            model.refreshReport();
            EventsCenter.getInstance().post(new RefreshReportPanelEvent());
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
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
        editedPerson = editPersonDescriptor.createEditedPerson(personToEdit);
    }

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
    // @@author anh2111
    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Link link;
        private Skill skill;
        private Set<Tag> tags;
        private Set<Tag> newTags;
        private Set<Tag> deletedTags;

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
            setLink(toCopy.link);
            setSkill(toCopy.skill);
            setTags(toCopy.tags);
            setNewTags(toCopy.newTags);
            setDeletedTags(toCopy.deletedTags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.skill,
                    this.tags, this.newTags, this.deletedTags);
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

        public void setSkill(Skill skill) {
            this.skill = skill;
        }

        public Optional<Skill> getSkill() {
            return Optional.ofNullable(skill);
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public Optional<Link> getLink() {
            return Optional.ofNullable(link);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Add  {@code newTags} to this object's {@code newTags}.
         * A defensive copy of {@code newTags} is used internally.
         */
        public void setNewTags(Set<Tag> newTags) {
            this.newTags = (newTags != null) ? new HashSet<>(newTags) : null;
        }

        /**
         * Add  {@code newTags} to this object's {@code tags}.
         * A defensive copy of {@code newTags} is used internally.
         */
        public void setDeletedTags(Set<Tag> deletedTags) {
            this.deletedTags = (deletedTags != null) ? new HashSet<>(deletedTags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code newTags} is null.
         */
        public Optional<Set<Tag>> getNewTags() {
            return (newTags != null) ? Optional.of(Collections.unmodifiableSet(newTags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code deletedTags} is null.
         */
        public Optional<Set<Tag>> getDeletedTags() {
            return (deletedTags != null) ? Optional.of(Collections.unmodifiableSet(deletedTags)) : Optional.empty();
        }

        /**
         * Creates and returns a {@code Person} with the details of {@code personToEdit}
         * edited with {@code editPersonDescriptor}.
         */
        public Person createEditedPerson(Person personToEdit) {
            boolean isTagsChanged = getTags().isPresent();
            boolean isNewTagsChanged = getNewTags().isPresent();
            boolean isDeletedTagsChanged = getDeletedTags().isPresent();

            boolean isMultipleTagsChanged = (isTagsChanged && isNewTagsChanged)
                    || (isTagsChanged && isDeletedTagsChanged) || (isDeletedTagsChanged && isNewTagsChanged);
            assert !isMultipleTagsChanged;

            assert personToEdit != null;

            Name updatedName = getName().orElse(personToEdit.getName());
            Phone updatedPhone = getPhone().orElse(personToEdit.getPhone());
            Email updatedEmail = getEmail().orElse(personToEdit.getEmail());
            Skill updatedSkill = getSkill().orElse(personToEdit.getSkills());
            Remark updatedRemark = personToEdit.getRemark();
            Link updatedLink = personToEdit.getLink();
            Address updatedAddress = getAddress().orElse(personToEdit.getAddress());

            Set<Tag> updatedTags;
            Set<Tag> personTags = new HashSet<>(personToEdit.getTags());
            if (isTagsChanged) {
                updatedTags = getTags().orElse(null);
            } else if (isNewTagsChanged) {
                if (personTags.isEmpty()) {
                    updatedTags = getNewTags().orElse(null);
                } else {
                    updatedTags = personTags;
                    updatedTags.addAll(getNewTags().orElse(null));
                }
            } else if (isDeletedTagsChanged) {
                updatedTags = personTags;
                if (!personTags.isEmpty()) {
                    updatedTags.removeAll(getDeletedTags().orElse(null));
                }
            } else {
                updatedTags = personTags;
            }


            return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedRemark,
                    updatedLink, updatedSkill, updatedTags);
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
                    && getLink().equals(e.getLink())
                    && getTags().equals(e.getTags());
        }
    }
    // @@author
}
