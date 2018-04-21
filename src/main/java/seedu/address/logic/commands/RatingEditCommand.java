package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMUNICATION_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPERIENCE_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROBLEM_SOLVING_SKILLS_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TECHNICAL_SKILLS_SCORE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author kexiaowen
/**
 * Edit a student's rating.
 */
public class RatingEditCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "rating-edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the ratings of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TECHNICAL_SKILLS_SCORE + "TECHNICAL SKILLS SCORE] "
            + "[" + PREFIX_COMMUNICATION_SKILLS_SCORE + "COMMUNICATION SKILLS SCORE] "
            + "[" + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE + "PROBLEM SOLVING SKILLS SCORE] "
            + "[" + PREFIX_EXPERIENCE_SCORE + "EXPERIENCE SCORE] \n"
            + "Note: 1. At least one score must be edited.\n"
            + "2. The scores must be numbers in the range of 1 to 5 (inclusive).\n"
            + "3. Scores are rounded to two decimal places.\n"
            + "EXAMPLE: " + COMMAND_WORD + " 1 "
            + PREFIX_TECHNICAL_SKILLS_SCORE + "5 "
            + PREFIX_EXPERIENCE_SCORE + "3.5";

    public static final String MESSAGE_EDIT_RATING_SUCCESS = "Edited rating of %1$s: \n"
            + "Technical skills: %2$s, Communication skills: %3$s, "
            + "Problem solving skills: %4$s, Experience: %5$s \n"
            + "Overall: %6$s";
    public static final String MESSAGE_PERSON_NOT_RATED = "You have not rated %1$s.\n"
            + "Use the rate command to assign new rating scores to the student.";
    public static final String MESSAGE_NOT_EDITED =
            "At least one rating score to edit must be provided.";
    public static final String MESSAGE_MISSING_INDEX =
            "Please provide the INDEX of the student to be rated.";
    public static final String MESSAGE_EMPTY_SCORE =
            "Score(s) must be provided and should not be empty.";

    private final Index index;
    private final EditRatingDescriptor editRatingDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to rate
     * @param editRatingDescriptor details to edit the person's rating with
     */
    public RatingEditCommand(Index index, EditRatingDescriptor editRatingDescriptor) {
        requireNonNull(index);
        requireNonNull(editRatingDescriptor);

        this.index = index;
        this.editRatingDescriptor = new EditRatingDescriptor(editRatingDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(personToEdit);
        requireNonNull(editedPerson);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Changing target person's rating should not result in a duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_RATING_SUCCESS, editedPerson.getName(),
                editedPerson.getRating().getTechnicalSkillsScore(),
                editedPerson.getRating().getCommunicationSkillsScore(),
                editedPerson.getRating().getProblemSolvingSkillsScore(),
                editedPerson.getRating().getExperienceScore(),
                editedPerson.getRating().getOverallScore()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit.getRating().getOverallScore() == Rating.DEFAULT_SCORE) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_RATED, personToEdit.getName()));
        }

        editedPerson = createRatedPerson(personToEdit, editRatingDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code rating}
     */
    private static Person createRatedPerson(Person personToEdit, EditRatingDescriptor editRatingDescriptor) {
        assert personToEdit != null;

        double technicalSkillsScore;
        double communicationSkillsScore;
        double problemSolvingSkillsScore;
        double experienceScore;

        if (editRatingDescriptor.isTechnicalSkillsScoreEdited()) {
            technicalSkillsScore = editRatingDescriptor.getTechnicalSkillsScore().get();
        } else {
            technicalSkillsScore = personToEdit.getRating().technicalSkillsScore;
        }

        if (editRatingDescriptor.isCommunicationSkillsScoreEdited()) {
            communicationSkillsScore = editRatingDescriptor.getCommunicationSkillsScore().get();
        } else {
            communicationSkillsScore = personToEdit.getRating().communicationSkillsScore;
        }

        if (editRatingDescriptor.isProblemSolvingSkillsScoreEdited()) {
            problemSolvingSkillsScore = editRatingDescriptor.getProblemSolvingSkillsScore().get();
        } else {
            problemSolvingSkillsScore = personToEdit.getRating().problemSolvingSkillsScore;
        }

        if (editRatingDescriptor.isExperienceScoreEdited()) {
            experienceScore = editRatingDescriptor.getExperienceScore().get();
        } else {
            experienceScore = personToEdit.getRating().experienceScore;
        }

        Rating rating = new Rating(technicalSkillsScore, communicationSkillsScore,
                problemSolvingSkillsScore, experienceScore);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getUniversity(), personToEdit.getExpectedGraduationYear(),
                personToEdit.getMajor(), personToEdit.getGradePointAverage(), personToEdit.getJobApplied(),
                rating, personToEdit.getResume(), personToEdit.getProfileImage(), personToEdit.getComment(),
                personToEdit.getInterviewDate(), personToEdit.getStatus(), personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RatingEditCommand)) {
            return false;
        }

        // state check
        RatingEditCommand e = (RatingEditCommand) other;
        return index.equals(e.index)
                && editRatingDescriptor.equals(e.editRatingDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

    /**
     * Stores the details to edit the rating with. Each non-empty field value will replace the
     * corresponding field value of the rating.
     */
    public static class EditRatingDescriptor {
        private double technicalSkillsScore = Rating.DEFAULT_SCORE;
        private double communicationSkillsScore = Rating.DEFAULT_SCORE;
        private double problemSolvingSkillsScore = Rating.DEFAULT_SCORE;
        private double experienceScore = Rating.DEFAULT_SCORE;

        public EditRatingDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditRatingDescriptor(EditRatingDescriptor toCopy) {
            setTechnicalSkillsScore(toCopy.technicalSkillsScore);
            setCommunicationSkillsScore(toCopy.communicationSkillsScore);
            setProblemSolvingSkillsScore(toCopy.problemSolvingSkillsScore);
            setExperienceScore(toCopy.experienceScore);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return isTechnicalSkillsScoreEdited() || isCommunicationSkillsScoreEdited()
                    || isProblemSolvingSkillsScoreEdited() || isExperienceScoreEdited();
        }

        /**
         * Returns true if technical skills score is edited.
         */
        public boolean isTechnicalSkillsScoreEdited() {
            return technicalSkillsScore != Rating.DEFAULT_SCORE;
        }

        /**
         * Returns true if communication skills score is edited.
         */
        public boolean isCommunicationSkillsScoreEdited() {
            return communicationSkillsScore != Rating.DEFAULT_SCORE;
        }

        /**
         * Returns true if problem solving skills score is edited.
         */
        public boolean isProblemSolvingSkillsScoreEdited() {
            return problemSolvingSkillsScore != Rating.DEFAULT_SCORE;
        }

        /**
         * Returns true if experience score is edited.
         */
        public boolean isExperienceScoreEdited() {
            return experienceScore != Rating.DEFAULT_SCORE;
        }


        public void setTechnicalSkillsScore(double technicalSkillsScore) {
            this.technicalSkillsScore = technicalSkillsScore;
        }

        public Optional<Double> getTechnicalSkillsScore() {
            return Optional.ofNullable(technicalSkillsScore);
        }

        public void setCommunicationSkillsScore(double communicationSkillsScore) {
            this.communicationSkillsScore = communicationSkillsScore;
        }

        public Optional<Double> getCommunicationSkillsScore() {
            return Optional.ofNullable(communicationSkillsScore);
        }

        public void setProblemSolvingSkillsScore(double problemSolvingSkillsScore) {
            this.problemSolvingSkillsScore = problemSolvingSkillsScore;
        }

        public Optional<Double> getProblemSolvingSkillsScore() {
            return Optional.ofNullable(problemSolvingSkillsScore);
        }

        public void setExperienceScore(double experienceScore) {
            this.experienceScore = experienceScore;
        }

        public Optional<Double> getExperienceScore() {
            return Optional.ofNullable(experienceScore);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRatingDescriptor)) {
                return false;
            }

            // state check
            EditRatingDescriptor e = (EditRatingDescriptor) other;

            return getTechnicalSkillsScore().equals(e.getTechnicalSkillsScore())
                    && getCommunicationSkillsScore().equals(e.getCommunicationSkillsScore())
                    && getProblemSolvingSkillsScore().equals(e.getProblemSolvingSkillsScore())
                    && getExperienceScore().equals(e.getExperienceScore());
        }
    }
}
