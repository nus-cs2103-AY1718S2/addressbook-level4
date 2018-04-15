# kexiaowen
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void sortPersonListAscOrder(SortCommand.SortField sortField) {
        addressBook.sortAsc(sortField);
        Predicate<? super Person> currPredicate = filteredPersons.getPredicate();
        filteredPersons.setPredicate(currPredicate);
    }

    @Override
    public void sortPersonListDescOrder(SortCommand.SortField sortField) {
        addressBook.sortDesc(sortField);
        Predicate<? super Person> currPredicate = filteredPersons.getPredicate();
        filteredPersons.setPredicate(currPredicate);
    }

```
###### /java/seedu/address/model/AddressBook.java
``` java
    /**
     * Sorts all students in HR+ based on {@code sortField} in ascending order
     */
    public void sortAsc(SortCommand.SortField sortField) {
        switch (sortField) {
        case GPA:
            persons.sortPersonsGradePointAverageAsc();
            break;

        case NAME:
            persons.sortPersonsNameAsc();
            break;

        case RATING:
            persons.sortPersonsRatingAsc();
            break;

        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_SORT_FIELD);
        }
    }

    /**
     * Sorts all students in HR+ based on {@code sortField} in descending order
     */
    public void sortDesc(SortCommand.SortField sortField) {
        switch (sortField) {
        case GPA:
            persons.sortPersonsGradePointAverageDesc();
            break;

        case NAME:
            persons.sortPersonsNameDesc();
            break;

        case RATING:
            persons.sortPersonsRatingDesc();
            break;

        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_SORT_FIELD);
        }
    }

```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts the list based on overall rating in ascending order
     */
    public void sortPersonsRatingAsc() {
        Collections.sort(internalList, Person::compareByOverallRating);
    }

    /**
     * Sorts the list based on overall rating in descending order
     */
    public void sortPersonsRatingDesc() {
        Collections.sort(internalList, Collections.reverseOrder(Person::compareByOverallRating));
    }

    /**
     * Sorts the list based on GPA in ascending order
     */
    public void sortPersonsGradePointAverageAsc() {
        Collections.sort(internalList, Person::compareByGradePointAverage);
    }

    /**
     * Sorts the list based on GPA in descending order
     */
    public void sortPersonsGradePointAverageDesc() {
        Collections.sort(internalList, Collections.reverseOrder(Person::compareByGradePointAverage));
    }

    /**
     * Sorts the list based on name in ascending order
     */
    public void sortPersonsNameAsc() {
        Collections.sort(internalList, Person::compareByName);
    }

    /**
     * Sorts the list based on name in descending order
     */
    public void sortPersonsNameDesc() {
        Collections.sort(internalList, Collections.reverseOrder(Person::compareByName));
    }

```
###### /java/seedu/address/commons/util/DoubleUtil.java
``` java
/**
 * Helper functions for handling doubles.
 */
public class DoubleUtil {

    /**
     * Returns a double rounded to two decimal places.
     * Note: Whole numbers will round to one decimal place.
     */
    public static double roundToTwoDecimalPlaces(String value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Returns a double rounded to two decimal places.
     * Note: Whole numbers will round to one decimal place.
     */
    public static double roundToTwoDecimalPlaces(double value) {
        long valueMultipliedByHundred = Math.round(value * 100);
        return (valueMultipliedByHundred / 100.0);
    }
}
```
###### /java/seedu/address/logic/commands/RateCommand.java
``` java
/**
 * Rates a candidate after an interview.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Rates a student based on technical, communication, problem solving skills and experience.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TECHNICAL_SKILLS_SCORE
            + "TECHNICAL SKILLS SCORE "
            + PREFIX_COMMUNICATION_SKILLS_SCORE
            + "COMMUNICATION SKILLS SCORE "
            + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE
            + "PROBLEM SOLVING SKILLS SCORE "
            + PREFIX_EXPERIENCE_SCORE
            + "EXPERIENCE SCORE \n"
            + "Note: 1. The scores must be numbers in the range of 1 to 5 (inclusive).\n"
            + "2. Scores are rounded to two decimal places.\n"
            + "3. All four scores must be supplied.\n"
            + "EXAMPLE: " + COMMAND_WORD + " 1 "
            + PREFIX_TECHNICAL_SKILLS_SCORE + "5 "
            + PREFIX_COMMUNICATION_SKILLS_SCORE + "4.5 "
            + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE + "4 "
            + PREFIX_EXPERIENCE_SCORE + "3.5";

    public static final String MESSAGE_RATE_PERSON_SUCCESS = "Rated %1$s: \n"
            + "Technical skills: %2$s, Communication skills: %3$s, "
            + "Problem solving skills: %4$s, Experience: %5$s \n"
            + "Overall: %6$s";
    public static final String MESSAGE_PERSON_ALREADY_RATED = "You have already rated %1$s.\n"
            + "Use the rating-edit command to modify rating scores assigned to the student.";

    private final Index index;
    private final Rating rating;

    private Person personToRate;
    private Person ratedPerson;

    /**
     * @param index of the person in the filtered person list to rate
     * @param rating given to the student based on technical skills, communication skills,
     *               problem solving skills and experience
     */
    public RateCommand(Index index, Rating rating) {
        requireNonNull(index);
        requireNonNull(rating);

        this.index = index;
        this.rating = rating;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(personToRate);
        requireNonNull(ratedPerson);

        try {
            model.updatePerson(personToRate, ratedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Changing target person's rating should not result in a duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RATE_PERSON_SUCCESS, ratedPerson.getName(),
                ratedPerson.getRating().getTechnicalSkillsScore(),
                ratedPerson.getRating().getCommunicationSkillsScore(),
                ratedPerson.getRating().getProblemSolvingSkillsScore(),
                ratedPerson.getRating().getExperienceScore(),
                ratedPerson.getRating().getOverallScore()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToRate = lastShownList.get(index.getZeroBased());
        if (personToRate.getRating().getOverallScore() != Rating.DEFAULT_SCORE) {
            throw new CommandException(String.format(MESSAGE_PERSON_ALREADY_RATED, personToRate.getName()));
        }
        ratedPerson = createRatedPerson(personToRate, rating);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code rating}
     */
    private static Person createRatedPerson(Person personToRate, Rating rating) {
        assert personToRate != null;

        return new Person(personToRate.getName(), personToRate.getPhone(), personToRate.getEmail(),
                personToRate.getAddress(), personToRate.getUniversity(), personToRate.getExpectedGraduationYear(),
                personToRate.getMajor(), personToRate.getGradePointAverage(), personToRate.getJobApplied(),
                rating, personToRate.getResume(), personToRate.getProfileImage(), personToRate.getComment(),
                personToRate.getInterviewDate(), personToRate.getStatus(), personToRate.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RateCommand)) {
            return false;
        }

        // state check
        RateCommand e = (RateCommand) other;
        return index.equals(e.index)
                && rating.equals(e.rating);
    }

}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sorts and lists all students in HR+ based on name, rating or gpa
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String SORT_ORDER_ASC = "asc";
    public static final String SORT_ORDER_DESC = "desc";
    public static final String SORT_FIELD_GPA = "gpa";
    public static final String SORT_FIELD_NAME = "name";
    public static final String SORT_FIELD_RATING = "rating";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts current list of students in HR+ based on a specified field in descending or ascending order.\n"
            + "Sorting order can only be either desc or asc. The field must be either gpa, name or rating.\n"
            + "Parameters: FIELD (must be either gpa, name or rating) "
            + PREFIX_SORT_ORDER + "SORTING ORDER\n"
            + "Example: " + COMMAND_WORD + " rating "
            + PREFIX_SORT_ORDER + SORT_ORDER_ASC;

    public static final String MESSAGE_INVALID_SORT_ORDER =
            "The sort order can only be asc or desc";
    public static final String MESSAGE_INVALID_SORT_FIELD =
            "The field must be either gpa, name or rating";
    public static final String MESSAGE_SORT_SUCCESS =
            "Sorted students based on %1$s in %2$s order";
    private static final String SORT_ORDER_ASC_FULL = "ascending";
    private static final String SORT_ORDER_DESC_FULL = "descending";
    private static final String SORT_FIELD_GPA_FULL = "gpa";
    private static final String SORT_FIELD_NAME_FULL = "name";
    private static final String SORT_FIELD_RATING_FULL = "rating";

    /**
     * Enumeration of acceptable sort orders
     */
    public enum SortOrder {
        ASC, DESC
    }

    /**
     * Enumeration of acceptable sort fields
     */
    public enum SortField {
        GPA, NAME, RATING
    }

    private final SortOrder sortOrder;
    private final SortField sortField;

    public SortCommand(SortOrder sortOrder, SortField sortField) {
        requireAllNonNull(sortOrder, sortField);
        this.sortOrder = sortOrder;
        this.sortField = sortField;
    }

    /**
     * Returns true if a given string is a valid sort order
     */
    public static boolean isValidSortOrder(String sortOrder) {
        requireNonNull(sortOrder);
        return sortOrder.equals(SORT_ORDER_DESC) || sortOrder.equals(SORT_ORDER_ASC);
    }

    /**
     * Returns true if a given string is a valid sort field
     */
    public static boolean isValidSortField(String sortField) {
        requireNonNull(sortField);
        return sortField.equals(SORT_FIELD_GPA)
                || sortField.equals(SORT_FIELD_NAME)
                || sortField.equals(SORT_FIELD_RATING);
    }


    /**
     * Returns the string representation of a {@cod SortOrder}
     */
    private String getSortOrderString(SortOrder sortOrder) {
        switch (sortOrder) {
        case ASC:
            return SORT_ORDER_ASC_FULL;

        case DESC:
            return SORT_ORDER_DESC_FULL;

        default:
            return null;
        }
    }

    /**
     * Returns the string representation of a {@cod SortField}
     */
    private String getSortFieldString(SortField sortField) {
        switch (sortField) {
        case GPA:
            return SORT_FIELD_GPA_FULL;

        case NAME:
            return SORT_FIELD_NAME_FULL;

        case RATING:
            return SORT_FIELD_RATING_FULL;

        default:
            return null;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        switch (sortOrder) {
        case ASC:
            model.sortPersonListAscOrder(sortField);
            break;

        case DESC:
            model.sortPersonListDescOrder(sortField);
            break;

        default:
            throw new CommandException(MESSAGE_INVALID_SORT_ORDER);
        }
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS,
                getSortFieldString(sortField), getSortOrderString(sortOrder)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortOrder.equals(((SortCommand) other).sortOrder))
                && this.sortField.equals(((SortCommand) other).sortField); // state check
    }

}
```
###### /java/seedu/address/logic/commands/RatingDeleteCommand.java
``` java
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
```
###### /java/seedu/address/logic/commands/RatingEditCommand.java
``` java
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
```
###### /java/seedu/address/logic/parser/RatingDeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RatingDeleteCommand object
 */
public class RatingDeleteCommandParser implements Parser<RatingDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RatingDeleteCommand
     * and returns a RatingDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RatingDeleteCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingDeleteCommand.MESSAGE_USAGE));
        }
        return new RatingDeleteCommand(index);
    }
}
```
###### /java/seedu/address/logic/parser/SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_ORDER)
                || !areAllFieldsSupplied(argMultimap, PREFIX_SORT_ORDER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        SortCommand.SortField sortField;
        try {
            sortField = ParserUtil.parseSortField(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        SortCommand.SortOrder sortOrder;
        try {
            sortOrder = ParserUtil.parseSortOrder(
                    argMultimap.getValue(PREFIX_SORT_ORDER)).get();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        return new SortCommand(sortOrder, sortField);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areAllFieldsSupplied(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> !Optional.of(argumentMultimap.getValue(prefix)).equals(""));
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code sortField} into a {@code SortCommand.SortField} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the specified sort field is invalid (not gpa, name or rating).
     */
    public static SortCommand.SortField parseSortField(String sortField) throws IllegalValueException {
        String trimmedLowercaseSortField = sortField.trim().toLowerCase();
        if (!SortCommand.isValidSortField(trimmedLowercaseSortField)) {
            throw new IllegalValueException(SortCommand.MESSAGE_INVALID_SORT_FIELD);
        }
        if (trimmedLowercaseSortField.equals(SortCommand.SORT_FIELD_GPA)) {
            return SortCommand.SortField.GPA;
        } else if (trimmedLowercaseSortField.equals(SortCommand.SORT_FIELD_RATING)) {
            return SortCommand.SortField.RATING;
        } else {
            return SortCommand.SortField.NAME;
        }
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String university} into an {@code University}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code University} is invalid.
     */
    public static University parseUniversity(String university) throws IllegalValueException {
        requireNonNull(university);
        String trimmedUniversity = university.trim();
        if (!University.isValidUniversity(trimmedUniversity)) {
            throw new IllegalValueException(University.MESSAGE_UNIVERSITY_CONSTRAINTS);
        }
        return new University(trimmedUniversity);
    }

    /**
     * Parses a {@code Optional<String> university}
     * into an {@code Optional<University>} if {@code university} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<University> parseUniversity(Optional<String> university) throws IllegalValueException {
        requireNonNull(university);
        return university.isPresent() ? Optional.of(parseUniversity(
                university.get())) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String jobApplied} into a {@code JobApplied}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code jobApplied} is invalid.
     */
    public static JobApplied parseJobApplied(String jobApplied) throws IllegalValueException {
        requireNonNull(jobApplied);
        String trimmedJobApplied = jobApplied.trim();
        if (!JobApplied.isValidJobApplied(trimmedJobApplied)) {
            throw new IllegalValueException(JobApplied.MESSAGE_JOB_APPLIED_CONSTRAINTS);
        }
        return new JobApplied(trimmedJobApplied);
    }

    /**
     * Parses a {@code Optional<String> jobApplied} into an {@code Optional<JobApplied>}
     * if {@code jobApplied} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<JobApplied> parseJobApplied(Optional<String> jobApplied) throws IllegalValueException {
        requireNonNull(jobApplied);
        return jobApplied.isPresent() ? Optional.of(parseJobApplied(jobApplied.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String score} into a {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code score} is invalid.
     */
    public static double parseScore(String score)
            throws IllegalValueException {
        requireNonNull(score);
        String trimmedScore = score.trim();
        double scoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedScore);
        if (!Rating.isValidScore(scoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return scoreValue;
    }

    /**
     * Parses a {@code Optional<String> score} into an {@code Optional<Double>}
     * if {@code score} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseScore(Optional<String> score)
            throws IllegalValueException {
        requireNonNull(score);
        if (score.get().trim().equals("")) {
            throw new IllegalValueException(RatingEditCommand.MESSAGE_EMPTY_SCORE);
        }
        return score.isPresent() ? Optional.of(parseScore(
                score.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String score} into a {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code score} is invalid.
     */
    public static double parseEditedScore(String score)
            throws IllegalValueException {
        requireNonNull(score);
        String trimmedScore = score.trim();
        if (trimmedScore.equals("")) {
            throw new IllegalValueException(RatingEditCommand.MESSAGE_EMPTY_SCORE);
        }
        double scoreValue;
        try {
            scoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedScore);
            if (!Rating.isValidScore(scoreValue)) {
                throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(RatingEditCommand.MESSAGE_EMPTY_SCORE);
        }
        return scoreValue;
    }

    /**
     * Parses a {@code Optional<String> score} into an {@code Optional<Double>}
     * if {@code score} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseEditedScore(Optional<String> score)
            throws IllegalValueException {
        requireNonNull(score);
        return score.isPresent() ? Optional.of(parseEditedScore(score.get())) : Optional.empty();
    }


    /**
     * Parses a {@code String sortOrder} into a {@code SortCommand.SortOrder}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code sortOrder} is invalid.
     */
    public static SortCommand.SortOrder parseSortOrder(String sortOrder)
            throws IllegalValueException {
        requireNonNull(sortOrder);
        String trimmedSortOrder = sortOrder.trim();
        if (!SortCommand.isValidSortOrder(trimmedSortOrder)) {
            throw new IllegalValueException(SortCommand.MESSAGE_INVALID_SORT_ORDER);
        }
        if (trimmedSortOrder.equals(SortCommand.SORT_ORDER_ASC)) {
            return SortCommand.SortOrder.ASC;
        } else {
            return SortCommand.SortOrder.DESC;
        }
    }

    /**
     * Parses a {@code Optional<String> sortOrder} into an {@code Optional<SortCommand.SortOrder>}
     * if {@code sortOrder} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<SortCommand.SortOrder> parseSortOrder(Optional<String> sortOrder)
            throws IllegalValueException {
        requireNonNull(sortOrder);
        return sortOrder.isPresent() ? Optional.of(parseSortOrder(sortOrder.get())) : Optional.empty();
    }

```
###### /java/seedu/address/logic/parser/RateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RateCommand object
 */
public class RateCommandParser implements Parser<RateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RateCommand
     * and returns an RateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TECHNICAL_SKILLS_SCORE, PREFIX_COMMUNICATION_SKILLS_SCORE,
                        PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, PREFIX_EXPERIENCE_SCORE);

        Index index;
        Rating rating;

        if (!arePrefixesPresent(argMultimap,
                PREFIX_TECHNICAL_SKILLS_SCORE, PREFIX_COMMUNICATION_SKILLS_SCORE,
                PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, PREFIX_EXPERIENCE_SCORE)
                || argMultimap.getPreamble().isEmpty()
                || !areAllFieldsSupplied(argMultimap,
                PREFIX_TECHNICAL_SKILLS_SCORE, PREFIX_COMMUNICATION_SKILLS_SCORE,
                PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, PREFIX_EXPERIENCE_SCORE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            double technicalSkillsScore = ParserUtil.parseScore(
                    argMultimap.getValue(PREFIX_TECHNICAL_SKILLS_SCORE)).get();
            double communicationSkillsScore = ParserUtil.parseScore(
                    argMultimap.getValue(PREFIX_COMMUNICATION_SKILLS_SCORE)).get();
            double problemSolvingSkillsScore = ParserUtil.parseScore(
                    argMultimap.getValue(PREFIX_PROBLEM_SOLVING_SKILLS_SCORE)).get();
            double experienceScore = ParserUtil.parseScore(
                    argMultimap.getValue(PREFIX_EXPERIENCE_SCORE)).get();
            rating = new Rating(technicalSkillsScore, communicationSkillsScore, problemSolvingSkillsScore,
                    experienceScore);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new RateCommand(index, rating);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areAllFieldsSupplied(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> !Optional.of(argumentMultimap.getValue(prefix)).equals(""));
    }
}
```
###### /java/seedu/address/logic/parser/RatingEditCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RatingEditCommand object
 */
public class RatingEditCommandParser implements Parser<RatingEditCommand> {
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    /**
     * Parses the given {@code String} of arguments in the context of the RatingEditCommand
     * and returns a RatingEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RatingEditCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingEditCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TECHNICAL_SKILLS_SCORE, PREFIX_COMMUNICATION_SKILLS_SCORE,
                        PREFIX_PROBLEM_SOLVING_SKILLS_SCORE, PREFIX_EXPERIENCE_SCORE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingEditCommand.MESSAGE_MISSING_INDEX));
        }

        EditRatingDescriptor editRatingDescriptor = new EditRatingDescriptor();
        try {
            if (argMultimap.getValue(PREFIX_TECHNICAL_SKILLS_SCORE).isPresent()) {
                editRatingDescriptor.setTechnicalSkillsScore(
                        ParserUtil.parseEditedScore(argMultimap.getValue(PREFIX_TECHNICAL_SKILLS_SCORE)).get());
            }
            if (argMultimap.getValue(PREFIX_COMMUNICATION_SKILLS_SCORE).isPresent()) {
                editRatingDescriptor.setCommunicationSkillsScore(
                        ParserUtil.parseEditedScore(argMultimap.getValue(PREFIX_COMMUNICATION_SKILLS_SCORE)).get());
            }
            if (argMultimap.getValue(PREFIX_PROBLEM_SOLVING_SKILLS_SCORE).isPresent()) {
                editRatingDescriptor.setProblemSolvingSkillsScore(
                        ParserUtil.parseEditedScore(argMultimap.getValue(PREFIX_PROBLEM_SOLVING_SKILLS_SCORE)).get());
            }
            if (argMultimap.getValue(PREFIX_EXPERIENCE_SCORE).isPresent()) {
                editRatingDescriptor.setExperienceScore(
                        ParserUtil.parseEditedScore(argMultimap.getValue(PREFIX_EXPERIENCE_SCORE)).get());
            }
            logger.info("Parsed user arguments of rating-edit command");
            logger.info("t/" + editRatingDescriptor.getTechnicalSkillsScore().get());
            logger.info("c/" + editRatingDescriptor.getCommunicationSkillsScore().get());
            logger.info("p/" + editRatingDescriptor.getProblemSolvingSkillsScore().get());
            logger.info("e/" + editRatingDescriptor.getExperienceScore().get());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editRatingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(RatingEditCommand.MESSAGE_NOT_EDITED);
        }

        return new RatingEditCommand(index, editRatingDescriptor);
    }
}
```
