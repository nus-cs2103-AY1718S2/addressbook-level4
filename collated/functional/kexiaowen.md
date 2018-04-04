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
        Collections.sort(internalList, Person::compareByOverallRating);
        Collections.reverse(internalList);
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
        Collections.sort(internalList, Person::compareByGradePointAverage);
        Collections.reverse(internalList);
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
        Collections.sort(internalList, Person::compareByName);
        Collections.reverse(internalList);
    }

```
###### /java/seedu/address/logic/commands/RateCommand.java
``` java
/**
 * Rates a candidate after an interview.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " :Rates a candidate.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TECHNICAL_SKILLS_SCORE + "TECHNICAL SKILLS SCORE "
            + PREFIX_COMMUNICATION_SKILLS_SCORE + "COMMUNICATION SKILLS SCORE "
            + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE + "PROBLEM SOLVING SKILLS SCORE "
            + PREFIX_EXPERIENCE_SCORE + "EXPERIENCE SCORE \n"
            + "EXAMPLE: " + COMMAND_WORD + " 1 "
            + PREFIX_TECHNICAL_SKILLS_SCORE + "5 "
            + PREFIX_COMMUNICATION_SKILLS_SCORE + "4.5 "
            + PREFIX_PROBLEM_SOLVING_SKILLS_SCORE + "4 "
            + PREFIX_EXPERIENCE_SCORE + "3.5";

    public static final String MESSAGE_RATE_PERSON_SUCCESS = "Rated %1$s: \n"
            + "Technical skills: %2$s, Communication skills: %3$s, "
            + "Problem solving skills: %4$s, Experience: %5$s \n"
            + "Overall: %6$s";

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
        String trimmedSortField = sortField.trim();
        if (!SortCommand.isValidSortField(trimmedSortField)) {
            throw new IllegalValueException(SortCommand.MESSAGE_INVALID_SORT_FIELD);
        }
        if (trimmedSortField.equals(SortCommand.SORT_FIELD_GPA)) {
            return SortCommand.SortField.GPA;
        } else if (trimmedSortField.equals(SortCommand.SORT_FIELD_RATING)) {
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
     * Parses a {@code String technicalSkillsScore} into an {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code technicalSkillsScore} is invalid.
     */
    public static double parseTechnicalSkillsScore(String technicalSkillsScore)
        throws IllegalValueException {
        requireNonNull(technicalSkillsScore);
        String trimmedTechnicalSkillsScore = technicalSkillsScore.trim();
        double technicalSkillsScoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedTechnicalSkillsScore);
        if (!Rating.isValidScore(technicalSkillsScoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return technicalSkillsScoreValue;
    }

    /**
     * Parses a {@code Optional<String> technicalSkillsScore} into an {@code Optional<Double>}
     * if {@code technicalSkillsScore} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseTechnicalSkillsScore(Optional<String> technicalSkillsScore)
            throws IllegalValueException {
        requireNonNull(technicalSkillsScore);
        if (technicalSkillsScore.get().trim().equals("")) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return technicalSkillsScore.isPresent() ? Optional.of(parseTechnicalSkillsScore(
                technicalSkillsScore.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String communicationSkillsScore} into an {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code communicationSkillsScore} is invalid.
     */
    public static double parseCommunicationSkillsScore(String communicationSkillsScore)
            throws IllegalValueException {
        requireNonNull(communicationSkillsScore);
        String trimmedCommunicationSkillsScore = communicationSkillsScore.trim();
        double communicationSkillsScoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedCommunicationSkillsScore);
        if (!Rating.isValidScore(communicationSkillsScoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return communicationSkillsScoreValue;
    }

    /**
     * Parses a {@code Optional<String> communicationSkillsScore} into an {@code Optional<Double>}
     * if {@code communicationSkillsScore} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseCommunicationSkillsScore(Optional<String> communicationSkillsScore)
            throws IllegalValueException {
        requireNonNull(communicationSkillsScore);
        if (communicationSkillsScore.get().trim().equals("")) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return communicationSkillsScore.isPresent() ? Optional.of(parseCommunicationSkillsScore(
                communicationSkillsScore.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String problemSolvingSkillsScore} into an {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code problemSolvingSkillsScore} is invalid.
     */
    public static double parseProblemSolvingSkillsScore(String problemSolvingSkillsScore)
            throws IllegalValueException {
        requireNonNull(problemSolvingSkillsScore);
        String trimmedProblemSolvingSkillsScore = problemSolvingSkillsScore.trim();
        double problemSolvingSkillsScoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedProblemSolvingSkillsScore);
        if (!Rating.isValidScore(problemSolvingSkillsScoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return problemSolvingSkillsScoreValue;
    }

    /**
     * Parses a {@code Optional<String> problemSolvingSkillsScore} into an {@code Optional<Double>}
     * if {@code problemSolvingSkillsScore} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseProblemSolvingSkillsScore(Optional<String> problemSolvingSkillsScore)
            throws IllegalValueException {
        requireNonNull(problemSolvingSkillsScore);
        if (problemSolvingSkillsScore.get().trim().equals("")) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return problemSolvingSkillsScore.isPresent() ? Optional.of(parseProblemSolvingSkillsScore(
                problemSolvingSkillsScore.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String experienceScore} into an {@code double}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if given {@code experienceScore} is invalid.
     */
    public static double parseExperienceScore(String experienceScore)
            throws IllegalValueException {
        requireNonNull(experienceScore);
        String trimmedExperienceScore = experienceScore.trim();
        double experienceScoreValue = DoubleUtil.roundToTwoDecimalPlaces(trimmedExperienceScore);
        if (!Rating.isValidScore(experienceScoreValue)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return experienceScoreValue;
    }

    /**
     * Parses a {@code Optional<String> experienceScore} into an {@code Optional<Double>}
     * if {@code experienceScore} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Double> parseExperienceScore(Optional<String> experienceScore)
            throws IllegalValueException {
        requireNonNull(experienceScore);
        if (experienceScore.get().trim().equals("")) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return experienceScore.isPresent() ? Optional.of(parseExperienceScore(
                experienceScore.get())) : Optional.empty();
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
            double technicalSkillsScore = ParserUtil.parseTechnicalSkillsScore(
                    argMultimap.getValue(PREFIX_TECHNICAL_SKILLS_SCORE)).get();
            double communicationSkillsScore = ParserUtil.parseCommunicationSkillsScore(
                    argMultimap.getValue(PREFIX_COMMUNICATION_SKILLS_SCORE)).get();
            double problemSolvingSkillsScore = ParserUtil.parseProblemSolvingSkillsScore(
                    argMultimap.getValue(PREFIX_PROBLEM_SOLVING_SKILLS_SCORE)).get();
            double experienceScore = ParserUtil.parseExperienceScore(
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
