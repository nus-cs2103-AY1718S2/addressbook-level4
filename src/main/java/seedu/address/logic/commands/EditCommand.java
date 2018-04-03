package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE_POINT_AVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_APPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESUME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIVERSITY;
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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpectedGraduationYear;
import seedu.address.model.person.GradePointAverage;
import seedu.address.model.person.InterviewDate;
import seedu.address.model.person.JobApplied;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfileImage;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Resume;
import seedu.address.model.person.Status;
import seedu.address.model.person.University;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;


/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_UNIVERSITY + "UNIVERSITY] "
            + "[" + PREFIX_EXPECTED_GRADUATION_YEAR + "EXPECTED GRADUATION YEAR] "
            + "[" + PREFIX_MAJOR + "MAJOR] "
            + "[" + PREFIX_GRADE_POINT_AVERAGE + "GRADE POINT AVERAGE] "
            + "[" + PREFIX_JOB_APPLIED + "JOB APPLIED] "
            + "[" + PREFIX_RESUME + "RESUME] "
            + "[" + PREFIX_IMAGE + "IMAGE] "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

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
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        University updatedUniversity = editPersonDescriptor.getUniversity().orElse(personToEdit.getUniversity());
        ExpectedGraduationYear updatedExpectedGraduationYear = editPersonDescriptor.getExpectedGraduationYear()
                .orElse(personToEdit.getExpectedGraduationYear());
        Major updatedMajor = editPersonDescriptor.getMajor().orElse(personToEdit.getMajor());
        GradePointAverage updatedGradePointAverage = editPersonDescriptor.getGradePointAverage()
                .orElse(personToEdit.getGradePointAverage());
        JobApplied updatedJobApplied = editPersonDescriptor.getJobApplied().orElse(personToEdit.getJobApplied());
        Resume updatedResume = editPersonDescriptor.getResume().orElse(personToEdit.getResume());
        ProfileImage updatedProfileImage = editPersonDescriptor.getProfileImage()
                .orElse(personToEdit.getProfileImage());
        Comment updatedComment = editPersonDescriptor.getComment().orElse(personToEdit.getComment());

        // Doesn't allow editing of rating
        Rating rating = personToEdit.getRating();

        // Doesn't allow editing of interview date
        InterviewDate interviewDate = personToEdit.getInterviewDate();

        // Doesn't allow editing of status
        Status status = personToEdit.getStatus();

        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedUniversity,
                updatedExpectedGraduationYear, updatedMajor, updatedGradePointAverage, updatedJobApplied,
                rating, updatedResume, updatedProfileImage, updatedComment, interviewDate, status, updatedTags);
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

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private University university;
        private ExpectedGraduationYear expectedGraduationYear;
        private Major major;
        private GradePointAverage gradePointAverage;
        private JobApplied jobApplied;
        private Resume resume;
        private ProfileImage profileImage;
        private Comment comment;
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
            setUniversity(toCopy.university);
            setExpectedGraduationYear(toCopy.expectedGraduationYear);
            setMajor(toCopy.major);
            setGradePointAverage(toCopy.gradePointAverage);
            setJobApplied(toCopy.jobApplied);
            setResume(toCopy.resume);
            setProfileImage(toCopy.profileImage);
            setComment(toCopy.comment);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.university,
                    this.expectedGraduationYear, this.major, this.gradePointAverage, this.jobApplied,
                    this.resume, this.profileImage, this.comment, this.tags);
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

        public void setUniversity(University university) {
            this.university = university;
        }

        public Optional<University> getUniversity() {
            return Optional.ofNullable(university);
        }

        public void setExpectedGraduationYear(ExpectedGraduationYear expectedGraduationYear) {
            this.expectedGraduationYear = expectedGraduationYear;
        }

        public Optional<ExpectedGraduationYear> getExpectedGraduationYear() {
            return Optional.ofNullable(expectedGraduationYear);
        }

        public void setMajor(Major major) {
            this.major = major;
        }

        public Optional<Major> getMajor() {
            return Optional.ofNullable(major);
        }

        public void setGradePointAverage(GradePointAverage gradePointAverage) {
            this.gradePointAverage = gradePointAverage;
        }

        public Optional<GradePointAverage> getGradePointAverage() {
            return Optional.ofNullable(gradePointAverage);
        }

        public void setJobApplied(JobApplied jobApplied) {
            this.jobApplied = jobApplied;
        }

        public Optional<JobApplied> getJobApplied() {
            return Optional.ofNullable(jobApplied);
        }

        public void setResume(Resume resume) {
            this.resume = resume;
        }

        public Optional<Resume> getResume() {
            return Optional.ofNullable(resume);
        }

        public void setProfileImage(ProfileImage profileImage) {
            this.profileImage = profileImage;
        }

        public Optional<ProfileImage> getProfileImage() {
            return Optional.ofNullable(profileImage);
        }

        public void setComment(Comment comment) {
            this.comment = comment;
        }

        public Optional<Comment> getComment() {
            return Optional.ofNullable(comment);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
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
                    && getExpectedGraduationYear().equals(e.getExpectedGraduationYear())
                    && getMajor().equals(e.getMajor())
                    && getGradePointAverage().equals(e.getGradePointAverage())
                    && getResume().equals(e.getResume())
                    && getProfileImage().equals(e.getProfileImage())
                    && getComment().equals(e.getComment())
                    && getTags().equals(e.getTags());
        }
    }
}
