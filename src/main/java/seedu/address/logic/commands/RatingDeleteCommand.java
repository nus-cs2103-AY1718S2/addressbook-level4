package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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

//@@author kexiaowen
/**
 * Deletes the rating of a person identified using it's last displayed index from HR+.
 */
public class RatingDeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rating-delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the rating of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RATING_SUCCESS = "Deleted rating of person named %1$s";
    public static final String MESSAGE_PERSON_NOT_RATED = "You have not rated %1$s.";

    private final Index targetIndex;

    private Person targetPerson;
    private Person editedPerson;

    public RatingDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(targetPerson);
        requireNonNull(editedPerson);

        try {
            model.updatePerson(targetPerson, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Deleting a person's rating should not result in a duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_RATING_SUCCESS, targetPerson.getName()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        targetPerson = lastShownList.get(targetIndex.getZeroBased());

        if (targetPerson.getRating().getOverallScore() == Rating.DEFAULT_SCORE) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_RATED, targetPerson.getName()));
        }

        editedPerson = createEditedPerson(targetPerson);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code targetPerson}
     * with rating deleted.
     */
    private static Person createEditedPerson(Person targetPerson) {
        assert targetPerson != null;

        Name name = targetPerson.getName();
        Phone phone = targetPerson.getPhone();
        Email email = targetPerson.getEmail();
        Address address = targetPerson.getAddress();
        University university = targetPerson.getUniversity();
        ExpectedGraduationYear expectedGraduationYear = targetPerson.getExpectedGraduationYear();
        Major major = targetPerson.getMajor();
        GradePointAverage gradePointAverage = targetPerson.getGradePointAverage();
        JobApplied jobApplied = targetPerson.getJobApplied();
        Rating defaultRating = new Rating(Rating.DEFAULT_SCORE, Rating.DEFAULT_SCORE,
                Rating.DEFAULT_SCORE, Rating.DEFAULT_SCORE);
        Resume resume = targetPerson.getResume();
        ProfileImage profileImage = targetPerson.getProfileImage();
        Comment comment = targetPerson.getComment();
        InterviewDate interviewDate = targetPerson.getInterviewDate();
        Status status = targetPerson.getStatus();
        Set<Tag> tags = targetPerson.getTags();

        return new Person(name, phone, email, address, university,
                expectedGraduationYear, major, gradePointAverage, jobApplied,
                defaultRating, resume, profileImage, comment, interviewDate, status, tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingDeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((RatingDeleteCommand) other).targetIndex) // state check
                && Objects.equals(this.targetPerson, ((RatingDeleteCommand) other).targetPerson));
    }
}
