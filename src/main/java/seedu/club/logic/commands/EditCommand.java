package seedu.club.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.club.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.club.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.club.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.club.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.club.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.club.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.club.model.Model.PREDICATE_SHOW_ALL_MEMBERS;
import static seedu.club.model.group.Group.GROUP_EXCO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.club.commons.core.EventsCenter;
import seedu.club.commons.core.Messages;
import seedu.club.commons.core.index.Index;
import seedu.club.commons.events.ui.HideResultsRequestEvent;
import seedu.club.commons.events.ui.UpdateSelectionPanelEvent;
import seedu.club.commons.util.CollectionUtil;
import seedu.club.logic.commands.exceptions.CommandException;
import seedu.club.model.group.Group;
import seedu.club.model.member.Credentials;
import seedu.club.model.member.Email;
import seedu.club.model.member.MatricNumber;
import seedu.club.model.member.Member;
import seedu.club.model.member.Name;
import seedu.club.model.member.Phone;
import seedu.club.model.member.ProfilePhoto;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.member.exceptions.MemberNotFoundException;
import seedu.club.model.tag.Tag;
import seedu.club.model.task.exceptions.DuplicateTaskException;

/**
 * Edits the details of an existing member in the club book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final ArrayList<String> COMMAND_ALIASES = new ArrayList<>(
            Arrays.asList(COMMAND_WORD, "e", "update")
    );
    public static final String COMMAND_FORMAT = "edit [n/ ] [p/ ] [e/ ] [m/ ] [g/ ] [t/ ]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the member identified by the index number used in the last member listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_MATRIC_NUMBER + "MATRIC NUMBER] "
            + "[" + PREFIX_GROUP + "GROUP] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_MEMBER_SUCCESS = "Edited member: %1$s\n"
            + "Number of tasks updated = %2$d";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MATRIC_NUMBER = "A member with the same matriculation number already "
            + "exists in Club Connect.";
    public static final String MESSAGE_DUPLICATE_TASK = "A task with the same attributes already "
            + "exists in Club Connect.";

    private final Index index;
    private final EditMemberDescriptor editMemberDescriptor;

    private Member memberToEdit;
    private Member editedMember;
    private int numberOfTasksUpdated;

    /**
     * @param index of the member in the filtered member list to edit
     * @param editMemberDescriptor details to edit the member with
     */
    public EditCommand(Index index, EditMemberDescriptor editMemberDescriptor) {
        requireNonNull(index);
        requireNonNull(editMemberDescriptor);

        this.index = index;
        this.editMemberDescriptor = new EditMemberDescriptor(editMemberDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        requireExcoLogIn();
        try {
            numberOfTasksUpdated = model.updateMember(memberToEdit, editedMember);
        } catch (DuplicateMatricNumberException dmne) {
            throw new CommandException(MESSAGE_DUPLICATE_MATRIC_NUMBER);
        } catch (MemberNotFoundException mnfe) {
            throw new AssertionError("The target member cannot be missing");
        } catch (DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
        EventsCenter.getInstance().post(new UpdateSelectionPanelEvent(model.getLoggedInMember(), false));
        if (!(GROUP_EXCO.equalsIgnoreCase(editedMember.getGroup().toString()))) {
            EventsCenter.getInstance().post(new HideResultsRequestEvent());
        }
        return new CommandResult(String.format(MESSAGE_EDIT_MEMBER_SUCCESS, editedMember, numberOfTasksUpdated));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        requireToSignUp();
        requireToLogIn();
        List<Member> lastShownList = model.getFilteredMemberList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        memberToEdit = lastShownList.get(index.getZeroBased());
        editedMember = createEditedMember(memberToEdit, editMemberDescriptor);
    }

    /**
     * Creates and returns a {@code member} with the details of {@code memberToEdit}
     * edited with {@code editMemberDescriptor}.
     */
    private static Member createEditedMember(Member memberToEdit, EditMemberDescriptor editMemberDescriptor) {
        assert memberToEdit != null;

        Name updatedName = editMemberDescriptor.getName().orElse(memberToEdit.getName());
        Phone updatedPhone = editMemberDescriptor.getPhone().orElse(memberToEdit.getPhone());
        Email updatedEmail = editMemberDescriptor.getEmail().orElse(memberToEdit.getEmail());
        MatricNumber updatedMatricNumber = editMemberDescriptor.getMatricNumber()
                .orElse(memberToEdit.getMatricNumber());
        Group updatedGroup = editMemberDescriptor.getGroup().orElse(memberToEdit.getGroup());
        Set<Tag> updatedTags = editMemberDescriptor.getTags().orElse(memberToEdit.getTags());
        ProfilePhoto updatedProfilePhoto = editMemberDescriptor.getProfilePhoto()
                .orElse(memberToEdit.getProfilePhoto());
        Credentials updatedCredentials = editMemberDescriptor.getCredentials().orElse(memberToEdit.getCredentials());

        return new Member(updatedName, updatedPhone, updatedEmail, updatedMatricNumber, updatedGroup,
                updatedTags, updatedCredentials, updatedProfilePhoto);
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
                && editMemberDescriptor.equals(e.editMemberDescriptor)
                && numberOfTasksUpdated == e.numberOfTasksUpdated
                && Objects.equals(memberToEdit, e.memberToEdit);
    }

    /**
     * Stores the details to edit the member with. Each non-empty field value will replace the
     * corresponding field value of the member.
     */
    public static class EditMemberDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private MatricNumber matricNumber;
        private Group group;
        private Set<Tag> tags;
        private Credentials credentials;
        private ProfilePhoto profilePhoto;

        public EditMemberDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMemberDescriptor(EditMemberDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setMatricNumber(toCopy.matricNumber);
            setGroup(toCopy.group);
            setTags(toCopy.tags);
            setCredentials(toCopy.credentials);
            setProfilePhoto(toCopy.profilePhoto);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email,
                    this.matricNumber, this.group, this.tags);
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

        public void setMatricNumber(MatricNumber matricNumber) {
            this.matricNumber = matricNumber;
        }

        public Optional<MatricNumber> getMatricNumber() {
            return Optional.ofNullable(matricNumber);
        }

        public Optional<Group> getGroup() {
            return Optional.ofNullable(group);
        }

        public void setGroup(Group group) {
            this.group = group;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public Optional<Credentials> getCredentials() {
            return Optional.ofNullable(credentials);
        }

        public void setProfilePhoto(ProfilePhoto profilePhoto) {
            this.profilePhoto = profilePhoto;
        }

        public Optional<ProfilePhoto> getProfilePhoto() {
            return Optional.ofNullable(profilePhoto);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         *
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
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
            if (!(other instanceof EditMemberDescriptor)) {
                return false;
            }

            // state check
            EditMemberDescriptor e = (EditMemberDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getMatricNumber().equals(e.getMatricNumber())
                    && getTags().equals(e.getTags())
                    && getCredentials().equals(e.getCredentials());
        }
    }
}
