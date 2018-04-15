# iNekox3
###### \java\seedu\progresschecker\commons\events\ui\TabLoadChangedEvent.java
``` java
/**
 * Represents a tab change in Main Window.
 */
public class TabLoadChangedEvent extends BaseEvent {

    public final String type;

    public TabLoadChangedEvent(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getTabName() {
        return type;
    }
}
```
###### \java\seedu\progresschecker\commons\util\StringUtil.java
``` java
    /**
     * Returns true if {@code s} is within the range 2 to 11.
     * e.g. 2, 3, 4, ..., 11 <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isWithinRange(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value >= ViewCommand.MIN_WEEK_NUMBER && value <= ViewCommand.MAX_WEEK_NUMBER;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
```
###### \java\seedu\progresschecker\logic\commands\AnswerCommand.java
``` java
/**
 * Edits details of student answer of an exercise in the ProgressChecker.
 */
public class AnswerCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "answer";
    public static final String COMMAND_ALIAS = "ans";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " QUESTION-INDEX" + " ANSWER";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Answer an exercise "
            + "identified by the index number shown. "
            + "Existing answer will be overwritten by the input value.\n"
            + "Parameters: INDEX (must be in the format of WEEK.SECTION.QUESTION number "
            + "where WEEK range from " + MIN_WEEK_NUMBER + " to " + MAX_WEEK_NUMBER + ") "
            + "ANSWER\n"
            + "Example: " + COMMAND_WORD + " 2.1.1 "
            + "Procedural languages work at simple data structures and functions level.";

    public static final String MESSAGE_EDIT_EXERCISE_SUCCESS = "Answered Exercise: %1$s";

    private final QuestionIndex questionIndex;
    private final StudentAnswer studentAnswer;

    private Exercise exerciseToEdit;
    private Exercise editedExercise;

    /**
     * @param questionIndex of the question in the filtered exercise list to edit
     * @param studentAnswer answer to edit the exercise with
     */
    public AnswerCommand(QuestionIndex questionIndex, StudentAnswer studentAnswer) {
        requireNonNull(questionIndex);

        this.questionIndex = questionIndex;
        this.studentAnswer = studentAnswer;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateExercise(exerciseToEdit, editedExercise);
        } catch (ExerciseNotFoundException enfe) {
            throw new AssertionError("The target exercise cannot be missing");
        }
        model.updateFilteredExerciseList(exercise -> exercise.getQuestionIndex().getWeekNumber()
                == editedExercise.getQuestionIndex().getWeekNumber());
        return new CommandResult(String.format(MESSAGE_EDIT_EXERCISE_SUCCESS, questionIndex));
    }

    //TODO: store mapping of questionIndex to exercise's index in exerciseList in a separate data structure
    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Exercise> exerciseList = model.getFilteredExerciseList();
        boolean isFound = false;

        if (!questionIndex.isValidIndex(questionIndex.toString())) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_INVALID_EXERCISE_INDEX, questionIndex.toString()));
        }

        for (Exercise e : exerciseList) {
            if (e.getQuestionIndex().toString().equals(questionIndex.toString())) {
                exerciseToEdit = exerciseList.get(exerciseList.indexOf(e));
                editedExercise = createEditedExercise(exerciseToEdit, studentAnswer);
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_INVALID_EXERCISE_INDEX, questionIndex.toString()));
        }
    }

    /**
     * Creates and returns a {@code Exercise} with the details of {@code exerciseToEdit}
     * edited with {@code editExerciseDescriptor}.
     */
    private static Exercise createEditedExercise(Exercise exerciseToEdit, StudentAnswer studentAnswer) {
        assert exerciseToEdit != null;

        QuestionIndex questionIndex = exerciseToEdit.getQuestionIndex();
        QuestionType questionType = exerciseToEdit.getQuestionType();
        Question question = exerciseToEdit.getQuestion();
        StudentAnswer updatedStudentAnswer = studentAnswer;
        ModelAnswer modelAnswer = exerciseToEdit.getModelAnswer();

        return new Exercise(questionIndex, questionType, question,
                updatedStudentAnswer, modelAnswer);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnswerCommand)) {
            return false;
        }

        // state check
        AnswerCommand e = (AnswerCommand) other;
        return questionIndex.equals(e.questionIndex)
                && studentAnswer.equals(e.studentAnswer)
                && Objects.equals(exerciseToEdit, e.exerciseToEdit);
    }

}
```
###### \java\seedu\progresschecker\logic\commands\ViewCommand.java
``` java
/**
 * Change view of the tab pane in main window based on categories.
 */
public class ViewCommand extends Command {

    public static final int MIN_WEEK_NUMBER = 2;
    public static final int MAX_WEEK_NUMBER = 11;

    public static final String COMMAND_WORD = "view";
    public static final String COMMAND_ALIAS = "v";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " TYPE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the tab view to profiles, tasks, exercises, or issues.\n"
            + "Parameters: TYPE (must be either 'profile', 'task', 'exercise', or 'issues')\n"
            + "INDEX (if TYPE is exercise and must be within " + MIN_WEEK_NUMBER
            + " and " + MAX_WEEK_NUMBER + " (boundary numbers inclusive)\n"
            + "Example: " + COMMAND_WORD + " exercise\n"
            + COMMAND_WORD + "exercise 5";

    public static final String MESSAGE_SUCCESS_TAB = "Viewing tab %1$s";
    public static final String MESSAGE_SUCCESS_WEEK = "Viewing week %1$s's exercises";

    private final String type;
    private final int weekNumber;
    private final boolean isToggleExerciseByWeek;

    public ViewCommand(String type, int weekNumber, boolean isToggleExerciseByWeek) {
        this.type = type;
        this.weekNumber = weekNumber;
        this.isToggleExerciseByWeek = isToggleExerciseByWeek;
    }

    @Override
    public CommandResult execute() {
        if (!isToggleExerciseByWeek) {
            EventsCenter.getInstance().post(new TabLoadChangedEvent(type));
            return new CommandResult(String.format(MESSAGE_SUCCESS_TAB, type));
        } else {
            model.updateFilteredExerciseList(exercise -> exercise.getQuestionIndex().getWeekNumber() == weekNumber);
            EventsCenter.getInstance().post(new TabLoadChangedEvent(type));
            return new CommandResult(String.format(MESSAGE_SUCCESS_WEEK, weekNumber));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.type.equals(((ViewCommand) other).type)) // state check
                && this.weekNumber == ((ViewCommand) other).weekNumber
                && this.isToggleExerciseByWeek == ((ViewCommand) other).isToggleExerciseByWeek;
    }
}
```
###### \java\seedu\progresschecker\logic\LogicManager.java
``` java
    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return model.getFilteredExerciseList();
    }

```
###### \java\seedu\progresschecker\logic\parser\AnswerCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AnswerCommand object
 */
public class AnswerCommandParser implements Parser<AnswerCommand> {

    private static final int QUESTION_INDEX_INDEX = 0;
    private static final int ANSWER_INDEX = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the AnswerCommand
     * and returns an AnswerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnswerCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            String[] content = args.trim().split(" ", 2);

            QuestionIndex questionIndex;
            questionIndex = ParserUtil.parseQuestionIndex(content[QUESTION_INDEX_INDEX]);
            StudentAnswer studentAnswer = ParserUtil.parseStudentAnswer(content[ANSWER_INDEX]);

            return new AnswerCommand(questionIndex, studentAnswer);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\progresschecker\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code type} into a {@code String[]} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified type is invalid, that is:
     * i. type is not of string "profile", "task", or "exercise".
     * ii. type is of "exercise" and comes with an index specified
     *     in which the index does not fall between the accepted range.
     */
    public static String[] parseTabType(String type) throws IllegalValueException {
        String trimmedType = type.trim();
        String[] trimmedTypeArray = trimmedType.split(" ");
        if (!trimmedTypeArray[0].equals("profile")
                && !trimmedTypeArray[0].equals("task")
                && !trimmedTypeArray[0].equals("exercise")
                && !trimmedTypeArray[0].equals("issues")) {
            throw new IllegalValueException(MESSAGE_INVALID_TAB_TYPE);
        }

        if (trimmedTypeArray.length > 1
                && trimmedTypeArray[0].equals("exercise")
                && !StringUtil.isWithinRange(trimmedTypeArray[1])) {
            throw new IllegalValueException(MESSAGE_INVALID_WEEK_NUMBER);
        }

        return trimmedTypeArray;
    }

```
###### \java\seedu\progresschecker\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String questionIndex} into a {@code QuestionIndex}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code questionIndex} is invalid.
     */
    public static QuestionIndex parseQuestionIndex(String questionIndex) throws IllegalValueException {
        requireNonNull(questionIndex);
        String trimmedQuestionIndex = questionIndex.trim();
        if (!QuestionIndex.isValidIndex(trimmedQuestionIndex)) {
            throw new IllegalValueException(QuestionIndex.MESSAGE_INDEX_CONSTRAINTS);
        }
        return new QuestionIndex(trimmedQuestionIndex);
    }

    /**
     * Parses a {@code String studentAnswer} into a {@code StudentAnswer}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static StudentAnswer parseStudentAnswer(String studentAnswer) {
        requireNonNull(studentAnswer);
        String trimmedStudentAnswer = studentAnswer.trim();

        return new StudentAnswer(trimmedStudentAnswer);
    }
}
```
###### \java\seedu\progresschecker\logic\parser\ViewCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        try {
            String[] typeArray = ParserUtil.parseTabType(args);
            String type = typeArray[0];
            int weekNumber = -1;
            boolean isToggleExerciseByWeek = false;
            if (typeArray.length > 1) {
                weekNumber = Integer.parseInt(typeArray[1]);
                isToggleExerciseByWeek = true;
            }
            return new ViewCommand(type, weekNumber, isToggleExerciseByWeek);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\progresschecker\model\exercise\exceptions\DuplicateExerciseException.java
``` java
/**
 * Signals that the operation will result in duplicate Exercise objects.
 */
public class DuplicateExerciseException extends DuplicateDataException {
    public DuplicateExerciseException() {
        super("Operation would result in duplicate exercises");
    }
}
```
###### \java\seedu\progresschecker\model\exercise\exceptions\ExerciseNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified exercise.
 */
public class ExerciseNotFoundException extends Exception {}
```
###### \java\seedu\progresschecker\model\exercise\Exercise.java
``` java
/**
 * Represents an Exercise in the ProgressChecker.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Exercise {

    private final QuestionIndex questionIndex;
    private final QuestionType questionType;
    private final Question question;
    private final StudentAnswer studentAnswer;
    private final ModelAnswer modelAnswer;

    /**
     * Every field must be present and not null.
     */
    public Exercise(QuestionIndex questionIndex, QuestionType questionType, Question question,
                    StudentAnswer studentAnswer, ModelAnswer modelAnswer) {
        requireAllNonNull(questionIndex, questionType, question);
        this.questionIndex = questionIndex;
        this.questionType = questionType;
        this.question = question;
        this.studentAnswer = studentAnswer;
        this.modelAnswer = modelAnswer;
    }

    public QuestionIndex getQuestionIndex() {
        return questionIndex;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Question getQuestion() {
        return question;
    }

    public StudentAnswer getStudentAnswer() {
        return studentAnswer;
    }

    public ModelAnswer getModelAnswer() {
        return modelAnswer;
    }

    @Override
    public String toString() {
        return "Q" + questionIndex + " " + question + "\n\n"
                + "Your Answer: " + studentAnswer + "\n\n"
                + "Suggested Answer: " + modelAnswer;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\ModelAnswer.java
``` java
/**
 * Represents an Exercise's model answer in the ProgressChecker.
 */
public class ModelAnswer {

    public final String value;

    /**
     * Constructs a {@code ModelAnswer}.
     *
     * @param answer An answer of any word and character.
     */
    public ModelAnswer(String answer) {
        requireNonNull(answer);
        this.value = answer;
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\Question.java
``` java
/**
 * Represents an Exercise's question in the ProgressChecker.
 */
public class Question {

    public final String value;

    /**
     * Constructs a {@code Question}.
     *
     * @param question A question of any word and character.
     */
    public Question(String question) {
        requireNonNull(question);
        this.value = question;
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\QuestionIndex.java
``` java
/**
 * Represents an Exercise's question index in the ProgressChecker.
 */
public class QuestionIndex {

    public static final int WEEK_NUMBER_INDEX = 0;

    public static final String MESSAGE_INDEX_CONSTRAINTS =
            "Indices can only contain numbers, and should be in the format of "
            + "SECTION NUMBER.PART NUMBER.QUESTION NUMBER";
    public static final String INDEX_VALIDATION_REGEX = "([2-9]|1[0-3])\\.([0-9]|[0-9]{2})\\.([0-9]|[0-9]{2})";
    public final String value;

    /**
     * Constructs a {@code QuestionIndex}.
     *
     * @param index A valid index number.
     */
    public QuestionIndex(String index) {
        requireNonNull(index);
        checkArgument(isValidIndex(index), MESSAGE_INDEX_CONSTRAINTS);
        this.value = index;
    }

    /**
     * Returns true if a given string is a valid index number.
     */
    public static boolean isValidIndex(String test) {
        return test.matches(INDEX_VALIDATION_REGEX);
    }

    /**
     * Returns the question number in the whole question index.
     */
    public int getWeekNumber() {
        return Integer.parseInt(value.split(Pattern.quote("."))[WEEK_NUMBER_INDEX]);
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\QuestionType.java
``` java
/**
 * Represents an Exercise's question type in the ProgressChecker.
 */
public class QuestionType {

    public static final String MESSAGE_TYPE_CONSTRAINTS =
            "Type can only be 'text' or 'choice'";
    public static final String TYPE_VALIDATION_REGEX = "text|choice";
    public final String value;

    /**
     * Constructs a {@code QuestionType}.
     *
     * @param type A valid type.
     */
    public QuestionType(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_TYPE_CONSTRAINTS);
        this.value = type;
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return test.matches(TYPE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\StudentAnswer.java
``` java
/**
 * Represents an Exercise's student answer in the ProgressChecker.
 */
public class StudentAnswer {

    public final String value;

    /**
     * Constructs a {@code StudentAnswer}.
     *
     * @param answer An answer of any word and character.
     */
    public StudentAnswer(String answer) {
        requireNonNull(answer);
        this.value = answer;
    }

    @Override
    public String toString() {
        return value;
    }
}
```
###### \java\seedu\progresschecker\model\exercise\UniqueExerciseList.java
``` java
/**
 * A list of exercises that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Exercise#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueExerciseList implements Iterable<Exercise> {

    private final ObservableList<Exercise> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent exercise as the given argument.
     */
    public boolean contains(Exercise toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an exercise to the list.
     *
     * @throws DuplicateExerciseException if the exercise to add is a duplicate of an existing exercise in the list.
     */
    public void add(Exercise toAdd) throws DuplicateExerciseException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExerciseException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the exercise {@code target} in the list with {@code editedExercise}.
     *
     * @throws ExerciseNotFoundException if {@code target} could not be found in the list.
     */
    public void setExercise(Exercise target, Exercise editedExercise)
            throws ExerciseNotFoundException {
        requireNonNull(editedExercise);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ExerciseNotFoundException();
        }

        internalList.set(index, editedExercise);
    }

    public void setExercises(UniqueExerciseList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setExercises(List<Exercise> exercises) throws DuplicateExerciseException {
        requireAllNonNull(exercises);
        final UniqueExerciseList replacement = new UniqueExerciseList();
        for (final Exercise exercise : exercises) {
            replacement.add(exercise);
        }
        setExercises(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Exercise> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Exercise> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueExerciseList // instanceof handles nulls
                && this.internalList.equals(((UniqueExerciseList) other).internalList));
    }
}
```
###### \java\seedu\progresschecker\model\Model.java
``` java
    /**
     * Replaces the given exercise {@code target} with {@code editedExercise}.
     *
     * @throws ExerciseNotFoundException if {@code target} could not be found in the list.
     */
    void updateExercise(Exercise target, Exercise editedExercise)
            throws ExerciseNotFoundException;

    /** Returns an unmodifiable view of the filtered exercise list */
    ObservableList<Exercise> getFilteredExerciseList();

    /**
     * Updates the filter of the filtered exercise list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExerciseList(Predicate<Exercise> predicate);

```
###### \java\seedu\progresschecker\model\ModelManager.java
``` java
    @Override
    public void updateExercise(Exercise target, Exercise editedExercise)
            throws ExerciseNotFoundException {
        requireAllNonNull(target, editedExercise);

        progressChecker.updateExercise(target, editedExercise);
        indicateProgressCheckerChanged();
    }

```
###### \java\seedu\progresschecker\model\ModelManager.java
``` java
    //=========== Filtered Exercise List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Exercise} backed by the internal list of
     * {@code progressChecker}
     */
    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return FXCollections.unmodifiableObservableList(filteredExercises);
    }

    @Override
    public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        filteredExercises.setPredicate(predicate);
    }
}
```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java
    public void setExercises(List<Exercise> exercises) throws DuplicateExerciseException {
        this.exercises.setExercises(exercises);
    }

```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java
    //// exercise-level operations

    /**
     * Adds an exercise to the ProgressChecker.
     *
     * @throws DuplicateExerciseException if an equivalent exercise already exists.
     */
    public void addExercise(Exercise e) throws DuplicateExerciseException {
        Exercise exercise = new Exercise(
                e.getQuestionIndex(), e.getQuestionType(), e.getQuestion(),
                e.getStudentAnswer(), e.getModelAnswer());
        exercises.add(exercise);
    }

    /**
     * Replaces the given exercise {@code target} in the list with {@code editedExercise}.
     *
     * @throws ExerciseNotFoundException if {@code target} could not be found in the list.
     */
    public void updateExercise(Exercise target, Exercise editedExercise)
            throws ExerciseNotFoundException {
        requireNonNull(editedExercise);

        exercises.setExercise(target, editedExercise);
    }

```
###### \java\seedu\progresschecker\model\ProgressChecker.java
``` java
    @Override
    public ObservableList<Exercise> getExerciseList() {
        return exercises.asObservableList();
    }
```
###### \java\seedu\progresschecker\model\util\SampleDataUtil.java
``` java
    public static Exercise[] getSampleExercises() {
        return new Exercise[] {
            // week 2
            new Exercise(new QuestionIndex("2.2.1"), new QuestionType("choice"),
                new Question("Which one of these is not a feature available in IDEs?\n"
                        + "\n"
                        + "a. Compiling.\n"
                        + "b. Syntax error highlighting.\n"
                        + "c. Debugging.\n"
                        + "d. Code navigation e.g., to navigate from a method call to the method implementation.\n"
                        + "e. Simulation e.g., run a mobile app in a simulator.\n"
                        + "f. Code analysis e.g. to find unreachable code.\n"
                        + "g. Reverse engineering design/documentation e.g. generate diagrams from code\n"
                        + "h. Visual programming e.g. Write programs using ‘drag and drop’ actions "
                        + "instead of typing code.\n"
                        + "i. Syntax assistance e.g., show hints as you type.\n"
                        + "j. Code generation e.g., to generate the code required "
                        + "by simply specifying which component/structure you want to implement.\n"
                        + "k. Extension. i.e. ability add more functionality to the IDE using plugins."),
                new StudentAnswer(""),
                new ModelAnswer("All. While all of these features may not be present in some IDEs, "
                        + "most do have these features in some form or other.")),
            new Exercise(new QuestionIndex("2.5.1"), new QuestionType("text"),
                new Question("Explain how the concepts of testing, test case, test failure, "
                        + "and defect are related to each other."),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("2.5.2"), new QuestionType("choice"),
                new Question("Regression testing is the automated re-testing of a software "
                        + "after it has been modified.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False\n"
                        + "c. Partially true"),
                new StudentAnswer(""),
                new ModelAnswer("c. Regression testing need not be automated but automation is highly recommended.")),
            new Exercise(new QuestionIndex("2.5.3"), new QuestionType("text"),
                new Question("Explain why and when you would do regression testing in a software project."),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("2.6.1"), new QuestionType("text"),
                new Question("What does RCS stand for?"),
                new StudentAnswer(""),
                new ModelAnswer("Revision Control Software.")),
            new Exercise(new QuestionIndex("2.6.2"), new QuestionType("text"),
                new Question("In the context of RCS, what is a Revision? Give an example."),
                new StudentAnswer(""),
                new ModelAnswer("Versions of a piece of information. For example, "
                        + "take a file containing program code. "
                        + "If you modify the code and save the file, "
                        + "you have a new version of that file.")),
            new Exercise(new QuestionIndex("2.6.3"), new QuestionType("choice"),
                new Question("Which of these is not considered a benefit of a typical RCS?\n"
                        + "a. Help a single user manage revisions of a single file\n"
                        + "b. Help a developer recover from a incorrect modification to a code file\n"
                        + "c. Makes it easier for a group of developers to collaborate on a project\n"
                        + "d. Manage the drift between multiple versions of your project\n"
                        + "e. Detect when multiple developers make incompatible changes to the same file\n"
                        + "f. All of them are benefits of RCS"),
                new StudentAnswer(""),
                new ModelAnswer("f.")),
            new Exercise(new QuestionIndex("2.6.4"), new QuestionType("text"),
                new Question("Suppose You are doing a team project with Tom, Dick, and Harry "
                        + "but those three have not even heard the term RCS. "
                        + "How do you explain RCS to them as briefly as possible, "
                        + "using the project as an example?"),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("2.6.5"), new QuestionType("text"),
                new Question("In the context of RCS, what is a repo?"),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),

            // week 3
            new Exercise(new QuestionIndex("3.1.1"), new QuestionType("choice"),
                new Question("Choose the correct statements\n"
                        + "\n"
                        + " a. Refactoring can improve understandability\n"
                        + " b. Refactoring can uncover bugs\n"
                        + " c. Refactoring can result in better performance\n"
                        + " d. Refactoring can change the number of methods/classes"),
                new StudentAnswer(""),
                new ModelAnswer("a b c d. (a, b, c) Although the primary aim of refactoring "
                        + "is to improve internal code structure, there are other secondary benefits. "
                        + "(d) Some refactorings result in adding/removing methods/classes.")),
            new Exercise(new QuestionIndex("3.1.2"), new QuestionType("text"),
                new Question("Do you agree with the following statement? Justify your answer.\n"
                        + "\n"
                        + "Statement: Whenever we refactor code to fix bugs, "
                        + "we need not do regression testing if the bug fix was minor."),
                new StudentAnswer(""),
                new ModelAnswer("DISAGREE. Even a minor change can have major repercussions on the system. "
                        + "We MUST do regression testing after each change, no matter how minor it is. "
                        + "Fixing bugs is technically not refactoring.")),
            new Exercise(new QuestionIndex("3.1.3"), new QuestionType("text"),
                new Question("Explain what is refactoring and why it is not the same as rewriting, "
                        + "bug fixing, or adding features."),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("3.1.4"), new QuestionType("choice"),
                new Question("‘Extract method’ and ‘Inline method’ refactorings\n"
                        + "\n"
                        + "a. are opposites of each other.\n"
                        + "b. sounds like opposites but they are not."),
                new StudentAnswer(""),
                new ModelAnswer("a.")),
            new Exercise(new QuestionIndex("3.2.1"), new QuestionType("choice"),
                new Question("What is the recommended approach regarding coding standards?\n"
                        + "\n"
                        + "a. Each developer should find a suitable coding standard and follow it in their coding.\n"
                        + "b. A developer should understand the importance of following a coding standard. "
                        + "However, there is no need to follow one.\n"
                        + "c. A developer should find out the coding standards currently used by the project "
                        + "and follow that closely.\n"
                        + "d. Coding standards are lame. Real programmers develop their own individual styles."),
                new StudentAnswer(""),
                new ModelAnswer("c.")),
            new Exercise(new QuestionIndex("3.2.2"), new QuestionType("text"),
                new Question("What is the aim of using a coding standard? How does it help?"),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("3.2.3"), new QuestionType("choice"),
                new Question("According to the given Java coding standard, which one of these is not a good name?\n"
                        + "\n"
                        + "a. integer variable name: totalPeople\n"
                        + "b. boolean variable name: checkWeight\n"
                        + "c. method name (returns integer): getPeopleCount\n"
                        + "d. method name (returns boolean): isValidAddress\n"
                        + "e. String variable name: description"),
                new StudentAnswer(""),
                new ModelAnswer("b. checkWeight is an action. "
                        + "Naming variables as actions makes the code harder to follow. "
                        + "isWeightValid may be a better name.")),
            new Exercise(new QuestionIndex("3.3.1"), new QuestionType("choice"),
                new Question("Putting all details in one place can create lengthy methods, "
                        + "but it is preferred over creating many small methods "
                        + "because it makes the code easier to understand.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False. If you are using abstraction properly, "
                        + "you DON’T need to see all details to understand something. "
                        + "The whole point of using abstraction is to be able to understand things "
                        + "without knowing as little details as possible. "
                        + "This is why we recommend single level of abstraction per method and top-down coding.")),
            new Exercise(new QuestionIndex("3.3.2"), new QuestionType("choice"),
                new Question("What are the drawbacks of trying to optimize code too soon?\n"
                        + "\n"
                        + "a. We may not know which parts are the real performance bottleneck\n"
                        + "b. When we optimize code manually, it becomes harder for the compiler to optimize\n"
                        + "c. Optimizing can complicate code\n"
                        + "d. Optimizing can lead to more error-prone code"),
                new StudentAnswer(""),
                new ModelAnswer("All.")),
            new Exercise(new QuestionIndex("3.3.3"), new QuestionType("choice"),
                new Question("This is a common saying among programmers\n"
                        + "\n"
                        + "a. Make it fast, make it right, make it work\n"
                        + "b. Make it work, make it right, make it fast\n"
                        + "c. Make it fast, make it right, now make it faster"),
                new StudentAnswer(""),
                new ModelAnswer("b.")),
            new Exercise(new QuestionIndex("3.6.1"), new QuestionType("choice"),
                new Question("In general, comments should describe,\n"
                        + "\n"
                        + "a. WHAT the code does\n"
                        + "b. WHY the code does something\n"
                        + "c. HOW the code does something"),
                new StudentAnswer(""),
                new ModelAnswer("a b. How the code does something should be apparent from the code itself. "
                        + "However, comments can help the reader in describing WHAT and WHY aspects of the code.")),

            // week 4
            new Exercise(new QuestionIndex("4.1.1"), new QuestionType("choice"),
                new Question("Choose the correct statements about models.\n"
                        + "\n"
                        + "a. Models are abstractions.\n"
                        + "b. Models can be used for communication.\n"
                        + "c. Models can be used for analysis of a problem.\n"
                        + "d. Generating models from code is useless.\n"
                        + "e. Models can be used as blueprints for generating code."),
                new StudentAnswer(""),
                new ModelAnswer("a b c e. Models generated from code can be used for understanding, analysing, "
                        + "and communicating about the code.")),
            new Exercise(new QuestionIndex("4.1.2"), new QuestionType("text"),
                new Question("Explain how models (e.g. UML diagrams) can be used in a class project."),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("4.2.1"), new QuestionType("choice"),
                new Question("A) Choose the correct statements\n"
                        + "\n"
                        + "a. OO is a programming paradigm\n"
                        + "b. OO guides us in how to structure the solution\n"
                        + "c. OO is mainly an abstraction mechanism\n"
                        + "d. OO is a programming language\n"
                        + "e. OO is modeled after how the objects in real world work"),
                new StudentAnswer(""),
                new ModelAnswer("a b c e. While many languages support the OO paradigm, OO is not a language itself.")),
            new Exercise(new QuestionIndex("4.2.2"), new QuestionType("choice"),
                new Question("Choose the correct statements\n"
                        + "\n"
                        + "a. Java and C++ are OO languages\n"
                        + "b. C language follows the Functional Programming paradigm\n"
                        + "c. Java can be used to write procedural code\n"
                        + "d. Prolog follows the Logic Programming paradigm"),
                new StudentAnswer(""),
                new ModelAnswer("a c d. C follows the procedural paradigm. "
                        + "Yes, we can write procedural code using OO languages e.g., AddressBook-level1.")),
            new Exercise(new QuestionIndex("4.2.3"), new QuestionType("choice"),
                new Question("OO is a higher level mechanism than the procedural paradigm.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("a. True. Procedural languages work at simple data structures (e.g., integers, arrays) "
                        + "and functions level. Because an object is an abstraction over data+related functions, "
                        + "OO works at a higher level.")),
            new Exercise(new QuestionIndex("4.2.4"), new QuestionType("choice"),
                new Question("Choose the correct statement\n"
                        + "\n"
                        + "a. An object is an encapsulation because it packages data and behavior into one bundle.\n"
                        + "b. An object is an encapsulation because it lets us think in terms of higher level concepts "
                        + "such as Students rather than student-related functions and data separately."),
                new StudentAnswer(""),
                new ModelAnswer("a. The second statement should be: An object is an abstraction encapsulation "
                        + "because it lets ...")),
            new Exercise(new QuestionIndex("4.5.1"), new QuestionType("choice"),
                new Question("Which are benefits of exceptions?\n"
                        + "+\n"
                        + " a. Exceptions allow us to separate normal code from error handling code.\n"
                        + " b. Exceptions can prevent problems that happen in the environment.\n"
                        + " c. Exceptions allow us to handle in one location an error raised in another location."),
                new StudentAnswer(""),
                new ModelAnswer("a c. Exceptions cannot prevent problems in the environment. "
                        + "They can only be used to handle and recover from such problems.")),
            new Exercise(new QuestionIndex("4.6.1"), new QuestionType("text"),
                new Question("Show (in UML notation) an enumeration called WeekDay "
                        + "to use when the value can only be Monday ... Friday."),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("4.7.1"), new QuestionType("text"),
                new Question("In the context of RCS, what is the branching? What is the need for branching?"),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("4.7.2"), new QuestionType("text"),
                new Question("In the context of RCS, what is the merging branches? "
                        + "How can it lead to merge conflicts?"),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),

            // week 5
            new Exercise(new QuestionIndex("5.4.1"), new QuestionType("choice"),
                new Question("Which of these are suitable as class-level variables?\n"
                        + "\n"
                        + "a. system: multi-player Pac Man game, Class: Player, variable: totalScore\n"
                        + "b. system: eLearning system, class: Course, variable: totalStudents\n"
                        + "c. system: ToDo manager, class: Task, variable: totalPendingTasks\n"
                        + "d. system: any, class: ArrayList, variable: total "
                        + "(i.e., total items in a given ArrayList object)"),
                new StudentAnswer(""),
                new ModelAnswer("c. totalPendingTasks should not be managed by individual Task objects "
                        + "and therefore suitable to be maintained as a class-level variable. "
                        + "The other variables should be managed at instance level "
                        + "as their value varies from instance to instance. "
                        + "e.g., totalStudents for one Course object will differ from totalStudents of another.")),
            new Exercise(new QuestionIndex("5.6.1"), new QuestionType("choice"),
                new Question("Which one of these is recommended not to use in UML diagrams "
                        + "because it adds more confusion than clarity?\n"
                        + "\n"
                        + "a. Composition symbol\n"
                        + "b. Aggregation symbol"),
                new StudentAnswer(""),
                new ModelAnswer("b.")),
            new Exercise(new QuestionIndex("5.8.1"), new QuestionType("choice"),
                new Question("Given below are some requirements of TEAMMATES "
                        + "(an online peer evaluation system for education). "
                        + "Which one of these are non-functional requirements?\n"
                        + "\n"
                        + "a. The response to any use action should become visible within 5 seconds.\n"
                        + "b. The application admin should be able to view a log of user activities.\n"
                        + "c. The source code should be open source.\n"
                        + "d. A course should be able to have up to 2000 students.\n"
                        + "e. As a student user, I can view details of my team members "
                        + "so that I can know who they are.\n"
                        + "f. The user interface should be intuitive enough for users who are not IT-savvy.\n"
                        + "g. The product is offered as a free online service."),
                new StudentAnswer(""),
                new ModelAnswer("a c d f g. (b) are (e) are functions available for a specific user types. "
                        + "Therefore, they are functional requirements. "
                        + "(a), (c), (d), (f) and (g) are either constraints on functionality "
                        + "or constraints on how the project is done, "
                        + "both of which are considered non-functional requirements.")),
            new Exercise(new QuestionIndex("5.9.1"), new QuestionType("choice"),
                new Question("What is the key characteristic about brainstorming?\n"
                        + "\n"
                        + " a. There should be at least 5 participants.\n"
                        + " b. All ideas are welcome. There are no bad ideas.\n"
                        + " c. Only the best people in the team should take part.\n"
                        + " d. They are a good way to eliminate bad ideas."),
                new StudentAnswer(""),
                new ModelAnswer("b.")),

            // week 6
            new Exercise(new QuestionIndex("6.1.1"), new QuestionType("text"),
                new Question("Discuss pros and cons of developers testing their own code."),
                new StudentAnswer(""),
                new ModelAnswer("Pros:\n"
                        + "\n"
                        + "Can be done early (the earlier we find a bug, the cheaper it is to fix).\n"
                        + "Can be done at lower levels, for examples, at operation and class level "
                        + "(testers usually test the system at UI level).\n"
                        + "It is possible to do more thorough testing because "
                        + "developers know the expected external behavior "
                        + "as well as the internal structure of the component.\n"
                        + "It forces developers to take responsibility for their own work "
                        + "(they cannot claim that \"testing is the job of the testers\").\n"
                        + "\n"
                        + "Cons:\n"
                        + "\n"
                        + "A developer may unconsciously test only situations that he knows to work "
                        + "(i.e. test it too 'gently').\n"
                        + "A developer may be blind to his own mistakes"
                        + "(if he did not consider a certain combination of input while writing code, "
                        + "it is possible for him to miss it again during testing).\n"
                        + "A developer may have misunderstood what the SUT is supposed to do in the first place.\n"
                        + "A developer may lack the testing expertise.")),
            new Exercise(new QuestionIndex("6.1.2"), new QuestionType("choice"),
                new Question("The cost of fixing a bug goes down as we reach the product release.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False. The cost goes up over time.")),
            new Exercise(new QuestionIndex("6.1.3"), new QuestionType("text"),
                new Question("Explain why early testing by developers is important."),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("6.4.1"), new QuestionType("choice"),
                new Question("Choose the correct statements about abstract classes and concrete classes.\n"
                        + "\n"
                        + "a. A concrete class can contain an abstract method.\n"
                        + "b. An abstract class can contain concrete methods.\n"
                        + "c. An abstract class need not contain any concrete methods.\n"
                        + "d. An abstract class cannot be instantiated."),
                new StudentAnswer(""),
                new ModelAnswer("b c d. A concrete class cannot contain even a single abstract method.")),

            // week 7
            new Exercise(new QuestionIndex("7.2.1"), new QuestionType("choice"),
                new Question("Choose the correct statement\n"
                        + "\n"
                        + "a. The architecture of a system should be simple enough "
                        + "for all team members to understand it.\n"
                        + "b. The architecture is primarily a high-level design of the system.\n"
                        + "c. The architecture is usually decided by the project manager.\n"
                        + "d. The architecture can contain details private to a component."),
                new StudentAnswer(""),
                new ModelAnswer("a b. Not (c) because architecture is usually designed by the Architect. "
                        + "Not (d) because ... private details of elements—details having to do solely "
                        + "with internal implementation—are not architectural.")),
            new Exercise(new QuestionIndex("7.3.1"), new QuestionType("choice"),
                new Question("Choose the correct statements\n"
                        + "\n"
                        + "a. A software component can have an API.\n"
                        + "b. Any method of a class is part of its API.\n"
                        + "c. Private methods of a class are not part of its API.\n"
                        + "d. The API forms the contract between the component developer and the component user.\n"
                        + "e. Sequence diagrams can be used to show how components interact "
                        + "with each other via APIs."),
                new StudentAnswer(""),
                new ModelAnswer("a c d e. (b) is incorrect because private methods cannot be a part of the API.")),
            new Exercise(new QuestionIndex("7.3.2"), new QuestionType("choice"),
                new Question("Defining component APIs early is useful for developing components in parallel.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("a. True. Yes, once we know the precise behavior expected of each component, "
                        + "we can start developing them in parallel.\n")),
            new Exercise(new QuestionIndex("7.6.1"), new QuestionType("choice"),
                new Question("A Calculator program crashes with an ‘assertion failure’ message "
                        + "when you try to find the square root of a negative number.\n"
                        + "\n"
                        + "a. This is a correct use of assertions.\n"
                        + "b. The application should have terminated with an exception instead.\n"
                        + "c. The program has a bug.\n"
                        + "d. All statements above are incorrect."),
                new StudentAnswer(""),
                new ModelAnswer("c. An assertion failure indicates a bug in the code. "
                        + "(b) is not acceptable because of the word \"terminated\". "
                        + "The application should not fail at all for this input. "
                        + "But it could have used an exception to handle the situation internally.")),
            new Exercise(new QuestionIndex("7.6.2"), new QuestionType("choice"),
                new Question("Which statements are correct?\n"
                        + "\n"
                        + " a. Use assertions to indicate the programmer messed up; "
                        + "Use exceptions to indicate the user or the environment messed up.\n"
                        + " b. Use exceptions to indicate the programmer messed up; "
                        + "Use assertions to indicate the user or the environment messed up."),
                new StudentAnswer(""),
                new ModelAnswer("a.")),
            new Exercise(new QuestionIndex("7.8.1"), new QuestionType("choice"),
                new Question("Gradle_is used used for,\n"
                        + "\n"
                        + "a. better revision control\n"
                        + "b. build automation\n"
                        + "c. UML diagramming\n"
                        + "d. project collaboration"),
                new StudentAnswer(""),
                new ModelAnswer("b.")),

            // week 8
            new Exercise(new QuestionIndex("8.4.1"), new QuestionType("text"),
                new Question("Explain the link (if any) between regressions and coupling."),
                new StudentAnswer(""),
                new ModelAnswer("When the system is highly-coupled, the risk of regressions is higher too  "
                        + "e.g. when component A is modified, all components ‘coupled’ to component A "
                        + "risk ‘unintended behavioral changes’.\n")),
            new Exercise(new QuestionIndex("8.4.2"), new QuestionType("text"),
                new Question("Discuss the relationship between coupling and testability.\n"),
                new StudentAnswer(""),
                new ModelAnswer("Coupling decreases testability because if the SUT is coupled to many other components "
                        + "it becomes difficult to test the SUI in isolation of its dependencies.")),
            new Exercise(new QuestionIndex("8.4.3"), new QuestionType("choice"),
                new Question("Choose the correct statements.\n"
                        + "\n"
                        + "a. As coupling increases, testability decreases.\n"
                        + "b. As coupling increases, the risk of regression increases.\n"
                        + "c. As coupling increases, the value of automated regression testing increases.\n"
                        + "d. As coupling increases, integration becomes easier as everything is connected together.\n"
                        + "e. As coupling increases, maintainability decreases."),
                new StudentAnswer(""),
                new ModelAnswer("a b c e. High coupling means either more components require to be integrated at once "
                        + "in a big-bang fashion (increasing the risk of things going wrong) or more drivers "
                        + "and stubs are required when integrating incrementally.")),
            new Exercise(new QuestionIndex("8.4.4"), new QuestionType("choice"),
                new Question("Which of these indicate a coupling between components A and B?\n"
                        + "\n"
                        + "a. component A has access to internal structure of component B.\n"
                        + "b. component A and B are written by the same developer.\n"
                        + "c. component A calls component B.\n"
                        + "d. component A receives an object of component B as a parameter.\n"
                        + "e. component A inherits from component B.\n"
                        + "f. components A and B have to follow the same data format or communication protocol."),
                new StudentAnswer(""),
                new ModelAnswer("a c d e f. Being written by the same developer does not imply a coupling.")),
            new Exercise(new QuestionIndex("8.4.5"), new QuestionType("choice"),
                new Question("“Only the GUI class should interact with the user. "
                        + "The GUI class should only concern itself with user interactions�?. "
                        + "This statement follows from,\n"
                        + "\n"
                        + "a. A software design should promote separation of concerns in a design.\n"
                        + "b. A software design should increase cohesion of its components.\n"
                        + "c. A software design should follow single responsibility principle."),
                new StudentAnswer(""),
                new ModelAnswer("a b c. By making ‘user interaction’ GUI class’ sole responsibility, "
                        + "we increase its cohesion. This is also in line with separation of concerns "
                        + "(i.e., we separated the concern of user interaction) "
                        + "and single responsibility principle (GUI class has only one responsibility).")),
            new Exercise(new QuestionIndex("8.4.6"), new QuestionType("choice"),
                new Question("Which of these is closest to the meaning of the open-closed principle?\n"
                        + "\n"
                        + "a. We should be able to change a software module’s behavior without modifying its code.\n"
                        + "b. A software module should remain open to modification as long as possible.\n"
                        + "c. A software module should be open to modification and closed to extension.\n"
                        + "d. Open source software rocks. Closed source software sucks."),
                new StudentAnswer(""),
                new ModelAnswer("a. Please refer the handout for the definition of OCP.")),
            new Exercise(new QuestionIndex("8.7.1"), new QuestionType("choice"),
                new Question("Stubs help us to test a component in isolation from its dependencies.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("a. True.")),
            new Exercise(new QuestionIndex("8.7.2"), new QuestionType("choice"),
                new Question("Choose correct statement about dependency injection\n"
                        + "\n"
                        + "a. It is a technique for increasing dependencies\n"
                        + "b. It is useful for unit testing\n"
                        + "c. It can be done using polymorphism\n"
                        + "d. It can be used to substitute a component with a stub"),
                new StudentAnswer(""),
                new ModelAnswer("b c d. "
                        + "It is a technique we can use to substitute an existing dependency with another, "
                        + "not increase dependencies. It is useful when you want to test a component in isolation "
                        + "but the SUT depends on other components. Using dependency injection, "
                        + "we can substitute those other components with test-friendly stubs. "
                        + "This is often done using polymorphism.")),

            // week 9
            new Exercise(new QuestionIndex("9.2.1"), new QuestionType("choice"),
                new Question("Which one of these is least related to how OO programs achieve polymorphism?\n"
                        + "\n"
                        + "a. substitutability\n"
                        + "b. dynamic binding\n"
                        + "c. operation overloading\n"
                        + "d. interfaces\n"
                        + "e. abstract classes"),
                new StudentAnswer(""),
                new ModelAnswer("c. Operation overriding is the one that is related, not operation overloading. "
                        + "Interfaces and abstract classes, although not required, "
                        + "can be used in achieving polymorphism.")),
            new Exercise(new QuestionIndex("9.2.2"), new QuestionType("choice"),
                new Question("Top-down design is better than bottom-up design.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False. Not necessarily. It depends on the situation. "
                        + "Bottom-up design may be preferable when there are lot of existing components "
                        + "we want to reuse.")),
            new Exercise(new QuestionIndex("9.2.3"), new QuestionType("choice"),
                new Question("Agile design camp expects the design to change over the product’s lifetime.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("a. True. Yes, that is why they do not believe in spending too much time "
                        + "creating a detailed and full design at the very beginning. "
                        + "However, the architecture is expected to remain relatively stable "
                        + "even in the agile design approach.")),
            new Exercise(new QuestionIndex("9.2.4"), new QuestionType("choice"),
                new Question("If a subclass imposes more restrictive conditions than its parent class, "
                        + "it violates Liskov Substitution Principle.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("a. True. If the subclass is more restrictive than the parent class, "
                        + "code that worked with the parent class may not work with the child class. "
                        + "Hence, the substitutability does not exist and LSP has been violated.")),
            new Exercise(new QuestionIndex("9.2.5"), new QuestionType("choice"),
                new Question("Which of these statements is true about the Dependency Inversion Principle.\n"
                        + "\n"
                        + "a. It can complicate the design/implementation by introducing extra abstractions, "
                        + "but it has some benefits.\n"
                        + "b. It is often used during testing, to replace dependencies with mocks.\n"
                        + "c. It reduces dependencies in a design.\n"
                        + "d. It advocates making higher level classes to depend on lower level classes."),
                new StudentAnswer(""),
                new ModelAnswer("a. Replacing dependencies with mocks is Dependency Injection, not DIP. "
                        + "DIP does not reduce dependencies, rather, it changes the direction of dependencies. "
                        + "Yes, it can introduce extra abstractions "
                        + "but often the benefit can outweigh the extra complications.")),
            new Exercise(new QuestionIndex("9.3.1"), new QuestionType("choice"),
                new Question("Bidirectional associations, if not implemented properly, "
                        + "can result in referential integrity violations.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("a. True. Bidirectional associations require two objects to link to each other. "
                        + "When one of these links is not consistent with the other, "
                        + "we have a referential integrity violation.")),
            new Exercise(new QuestionIndex("9.3.2"), new QuestionType("choice"),
                new Question("Defensive programming,\n"
                        + "\n"
                        + "a. can make the program slower.\n"
                        + "b. can make the code longer.\n"
                        + "c. can make the code more complex.\n"
                        + "d. can make the code less susceptible to misuse.\n"
                        + "e. can require extra effort."),
                new StudentAnswer(""),
                new ModelAnswer("All. Defensive programming requires a more checks, "
                        + "possibly making the code longer, more complex, and possibly slower. "
                        + "Use it only when benefits outweigh costs, which is often.")),
            new Exercise(new QuestionIndex("9.3.3"), new QuestionType("choice"),
                new Question("Which statements are correct?\n"
                        + "\n"
                        + "a. It is not natively supported by Java and C++.\n"
                        + "b. It is an alternative to OOP.\n"
                        + "c. It assumes the caller of a method is responsible for "
                        + "ensuring all preconditions are met."),
                new StudentAnswer(""),
                new ModelAnswer("a c. DbC is not an alternative to OOP. We can use DbC in an OOP solution.")),
            new Exercise(new QuestionIndex("9.4.1"), new QuestionType("choice"),
                new Question("Choose correct statements about API documentation.\n"
                        + "\n"
                        + "a. They are useful for both developers who use the API "
                        + "and developers who maintain the API implementation.\n"
                        + "b. There are tools that can generate API documents from code comments.\n"
                        + "d. API documentation may contain code examples."),
                new StudentAnswer(""),
                new ModelAnswer("All.")),
            new Exercise(new QuestionIndex("9.4.2"), new QuestionType("choice"),
                new Question("It is recommended for developer documents,\n"
                        + "\n"
                        + "a. to have separate sections for each type of diagrams "
                        + "such as class diagrams, sequence diagrams, use case diagrams etc.\n"
                        + "b. to give a high priority to comprehension too, not stop at comprehensiveness only."),
                new StudentAnswer(""),
                new ModelAnswer("b. (a) Use diagrams when they help to understand the text descriptions. "
                        + "Text and diagrams should be used in tandem. "
                        + "Having separate sections for each diagram type is a sign of generating diagrams "
                        + "for the sake of having them.\n"
                        + "\n"
                        + "(b) Both are important, but lengthy, complete, accurate yet hard to understand documents "
                        + "are not that useful.")),
            new Exercise(new QuestionIndex("9.5.1"), new QuestionType("choice"),
                new Question("Which of these gives us the highest intensity of testing?\n"
                        + "\n"
                        + " a. 100% statement coverage\n"
                        + " b. 100% path coverage\n"
                        + " c. 100% branch coverage\n"
                        + " d. 100% condition coverage"),
                new StudentAnswer(""),
                new ModelAnswer("b. 100% path coverage implies all possible execution paths "
                        + "through the SUT have been tested. This is essentially ‘exhaustive testing’. "
                        + "While this is very hard to achieve for a non-trivial SUT, "
                        + "it technically gives us the highest intensity of testing. "
                        + "If all tests pass at 100% path coverage, the SUT code can be considered ‘bug free’. "
                        + "However, note that path coverage does not include paths that are missing from the code "
                        + "altogether because the programmer left them out by mistake.")),
            new Exercise(new QuestionIndex("9.5.2"), new QuestionType("choice"),
                new Question("In TDD, we write all the test cases before we start writing functional code.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False. No, not all. We proceed in small steps, "
                        + "writing tests and functional code in tandem, "
                        + "but writing the test before we write the corresponding functional code.")),
            new Exercise(new QuestionIndex("9.5.3"), new QuestionType("choice"),
                new Question("Testing tools such as Junit require us to follow TDD.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False. They can be used for TDD, but they can be used without TDD too.")),
            new Exercise(new QuestionIndex("9.6.1"), new QuestionType("choice"),
                new Question("GUI testing is usually easier than API testing because "
                        + "it doesn’t require any extra coding.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False.")),
            new Exercise(new QuestionIndex("9.6.2"), new QuestionType("choice"),
                new Question("Choose the correct statements about system testing and acceptance testing.\n"
                        + "\n"
                        + "a. Both system testing and acceptance testing typically involve the whole system.\n"
                        + "b. System testing is typically more extensive than acceptance testing.\n"
                        + "c. System testing can include testing for non-functional qualities.\n"
                        + "d. Acceptance testing typically has more user involvement than system testing.\n"
                        + "e. In smaller projects, the developers may do system testing as well, "
                        + "in addition to developer testing.\n"
                        + "f. If system testing is adequately done, we need not do acceptance testing."),
                new StudentAnswer(""),
                new ModelAnswer("a b c d e. (b) is correct because system testing can aim to cover all "
                        + "specified behaviors and can even go beyond the system specification. "
                        + "Therefore, system testing is typically more extensive than acceptance testing.\n"
                        + "\n"
                        + "(f) is incorrect because it is possible for a system to pass system tests "
                        + "but fail acceptance tests.")),

            // week 10
            new Exercise(new QuestionIndex("10.1.1"), new QuestionType("choice"),
                new Question("Pick the odd one out.\n"
                        + "\n"
                        + "a. Law of Demeter.\n"
                        + "b. Don’t add people to a late project.\n"
                        + "c. Don’t talk to strangers.\n"
                        + "d. Principle of least knowledge.\n"
                        + "e. Coupling."),
                new StudentAnswer(""),
                new ModelAnswer("b. Law of Demeter, which aims to reduce coupling, "
                        + "is also known as ‘Don’t talk to strangers’ and ‘Principle of least knowledge’.")),
            new Exercise(new QuestionIndex("10.1.2"), new QuestionType("text"),
                new Question("Do the Brook’s Law apply to a school project? Justify."),
                new StudentAnswer(""),
                new ModelAnswer("Yes. Adding a new student to a project team "
                        + "can result in a slow-down of the project for a short period. "
                        + "This is because the new member needs time to learn the project "
                        + "and existing members will have to spend time helping the new guy get up to speed. "
                        + "If the project is already behind schedule and near a deadline, "
                        + "this could delay the delivery even further.")),
            new Exercise(new QuestionIndex("10.1.3"), new QuestionType("choice"),
                new Question("Which one of these (all attributed to Fred Brooks, "
                        + "the author of the famous SE book The Mythical Man-Month), is called the Brook’s law?\n"
                        + "\n"
                        + " a. All programmers are optimists.\n"
                        + " b. Good judgement comes from experience, and experience comes from bad judgement.\n"
                        + " c. The bearing of a child takes nine months, no matter how many women are assigned.\n"
                        + " d. Adding more manpower to an already late project makes it even later."),
                new StudentAnswer(""),
                new ModelAnswer("d.")),
            new Exercise(new QuestionIndex("10.3.1"), new QuestionType("choice"),
                new Question("Which one of these describes the ‘software design patterns’ concept best?\n"
                        + "\n"
                        + " a. Designs that appear repetitively in software.\n"
                        + " b. Elegant solutions to recurring problems in software design.\n"
                        + " c. Architectural styles used in applications.\n"
                        + " d. Some good design techniques proposed by the Gang of Four"),
                new StudentAnswer(""),
                new ModelAnswer("b.")),
            new Exercise(new QuestionIndex("10.3.2"), new QuestionType("choice"),
                new Question("When we describe a pattern, we must also specify anti-patterns.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False. Anti-patterns are related to patterns, "
                        + "but they are not a ‘must have’ component of a pattern description.")),
            new Exercise(new QuestionIndex("10.3.3"), new QuestionType("choice"),
                new Question("We use the Singleton pattern when\n"
                        + "\n"
                        + "a. we want an a class with a private constructor.\n"
                        + "b. we want a single class to hold all functionality of the system.\n"
                        + "c. we want a class with no more than one instance.\n"
                        + "d. we want to hide internal structure of a component from its clients."),
                new StudentAnswer(""),
                new ModelAnswer("c.")),
            new Exercise(new QuestionIndex("10.4.1"), new QuestionType("choice"),
                new Question("Choose correct statements about software frameworks.\n"
                        + "\n"
                        + "a. They follow the hollywood principle, otherwise known as ‘inversion of control’\n"
                        + "b. They come with full or partial implementation.\n"
                        + "c. They are more concrete than patterns or principles.\n"
                        + "d. They are often configurable.\n"
                        + "e. They are reuse mechanisms.\n"
                        + "f. They are similar to reusable libraries but bigger."),
                new StudentAnswer(""),
                new ModelAnswer("a b c d e. While both libraries and frameworks are reuse mechanisms, "
                        + "and both more concrete than principles and patterns, "
                        + "libraries differ from frameworks in some key ways. "
                        + "One of them is the ‘inversion of control’ used by frameworks but not libraries. "
                        + "Furthermore, frameworks do not have to be bigger than libraries all the time.")),
            new Exercise(new QuestionIndex("10.4.2"), new QuestionType("choice"),
                new Question("Which one of these are frameworks ?\n"
                        + "\n"
                        + "a. JUnit\n"
                        + "b. Eclipse\n"
                        + "c. Drupal\n"
                        + "d. Ruby on Rails"),
                new StudentAnswer(""),
                new ModelAnswer("All. These are frameworks.")),

            // week 11
            new Exercise(new QuestionIndex("11.1.1"), new QuestionType("choice"),
                new Question("What is the main difference between a class diagram and and an OO domain model?\n"
                        + "\n"
                        + "a. One is about the problem domain while the other is about the solution domain.\n"
                        + "b. One has more classes than the other.\n"
                        + "c. One shows more details than the other.\n"
                        + "d. One is a UML diagram, while the other is not a UML diagram."),
                new StudentAnswer(""),
                new ModelAnswer("a. Both are UML diagrams, and use the class diagram notation. "
                        + "While it is true that often a class diagram may have more classes and more details, "
                        + "the main difference is that the OO domain model describes the problem domain "
                        + "while the class diagram describes the solution.")),
            new Exercise(new QuestionIndex("11.3.1"), new QuestionType("text"),
                new Question("Here are some common elements of a design pattern: "
                        + "Name, Context, Problem, Solution, Anti-patterns (optional), Consequences (optional), "
                        + "other useful information (optional).\n"
                        + "\n"
                        + "Using similar elements, describe a pattern that is not a design pattern. "
                        + "It must be a pattern you have noticed, not a pattern already documented by others. "
                        + "You may also give a pattern not related to software.\n"
                        + "\n"
                        + "Some examples:\n"
                        + "- A pattern for testing textual UIs.\n"
                        + "- A pattern for striking a good bargain at a mall such as Sim-Lim Square."),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("11.4.1"), new QuestionType("choice"),
                new Question("Applying the heuristics covered so far, we can determine the precise number of "
                        + "test cases required to test any given SUT effectively.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False. These heuristics are, well, heuristics only. "
                        + "They will help you to make better decisions about test case design. "
                        + "However, they are speculative in nature (especially, when testing in black-box fashion) "
                        + "and cannot give you precise number of test cases.")),
            new Exercise(new QuestionIndex("11.4.2"), new QuestionType("choice"),
                new Question("Which of these contradict the heuristics recommended "
                        + "when creating test cases with multiple inputs?\n"
                        + "\n"
                        + "a. All invalid test inputs must be tested together.\n"
                        + "b. It is ok to combine valid values for different inputs.\n"
                        + "c. No more than one invalid test input should be in a given test case.\n"
                        + "d. Each valid test input should appear at least once "
                        + "in a test case that doesn’t have any invalid inputs."),
                new StudentAnswer(""),
                new ModelAnswer("a. If you test all invalid test inputs together, "
                        + "you will not know if each one of the invalid inputs are handled correctly by the SUT. "
                        + "This is because most SUTs return an error message "
                        + "upon encountering the first invalid input.")),
            new Exercise(new QuestionIndex("11.6.1"), new QuestionType("choice"),
                new Question("Choose the correct statements about agile processes.\n"
                        + "\n"
                        + "a. They value working software over comprehensive documentation.\n"
                        + "b. They value responding to change over following a plan.\n"
                        + "c. They may not be suitable for some type of projects.\n"
                        + "d. XP and Scrum are agile processes."),
                new StudentAnswer(""),
                new ModelAnswer("a b c d.")),
            new Exercise(new QuestionIndex("11.7.1"), new QuestionType("choice"),
                new Question("Choose the correct statements about the unified process.\n"
                        + "\n"
                        + "a. It was conceived by the three amigos who also created UML.\n"
                        + "b. The Unified process requires the use of UML.\n"
                        + "c. The Unified process is actually a process framework rather than a fixed process.\n"
                        + "d. The Unified process can be iterative and incremental"),
                new StudentAnswer(""),
                new ModelAnswer("a c d. Although UP was created by the same three amigos who created UML, "))
        };
    }

}
```
###### \java\seedu\progresschecker\storage\XmlAdaptedExercise.java
``` java
/**
 * JAXB-friendly version of the Exercise.
 */
public class XmlAdaptedExercise {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Exercise's %s field is missing!";

    @XmlElement(required = true)
    private String questionIndex;
    @XmlElement(required = true)
    private String questionType;
    @XmlElement(required = true)
    private String question;
    @XmlElement(required = true)
    private String studentAnswer;
    @XmlElement(required = true)
    private String modelAnswer;

    /**
     * Constructs an XmlAdaptedExercise.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedExercise() {}

    /**
     * Constructs an {@code XmlAdaptedExercise} with the given exercise details.
     */
    public XmlAdaptedExercise(
            String questionIndex, String questionType, String question,
            String studentAnswer, String modelAnswer) {
        this.questionIndex = questionIndex;
        this.questionType = questionType;
        this.question = question;
        this.studentAnswer = studentAnswer;
        this.modelAnswer = modelAnswer;
    }

    /**
     * Converts a given Exercise into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedExercise
     */
    public XmlAdaptedExercise(Exercise source) {
        questionIndex = source.getQuestionIndex().value;
        questionType = source.getQuestionType().value;
        question = source.getQuestion().value;
        studentAnswer = source.getStudentAnswer().value;
        modelAnswer = source.getModelAnswer().value;
    }

    /**
     * Converts this jaxb-friendly adapted exercise object into the model's Exercise object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted exercise
     */
    public Exercise toModelType() throws IllegalValueException {
        if (this.questionIndex == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuestionIndex.class.getSimpleName()));
        }
        if (!QuestionIndex.isValidIndex(this.questionIndex)) {
            throw new IllegalValueException(QuestionIndex.MESSAGE_INDEX_CONSTRAINTS);
        }
        final QuestionIndex questionIndex = new QuestionIndex(this.questionIndex);

        if (this.questionType == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    QuestionType.class.getSimpleName()));
        }
        if (!QuestionType.isValidType(this.questionType)) {
            throw new IllegalValueException(QuestionType.MESSAGE_TYPE_CONSTRAINTS);
        }
        final QuestionType questionType = new QuestionType(this.questionType);

        if (this.question == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Question.class.getSimpleName()));
        }
        final Question question = new Question(this.question);

        if (this.studentAnswer == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentAnswer.class.getSimpleName()));
        }
        final StudentAnswer studentAnswer = new StudentAnswer(this.studentAnswer);

        if (this.modelAnswer == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModelAnswer.class.getSimpleName()));
        }
        final ModelAnswer modelAnswer = new ModelAnswer(this.modelAnswer);

        return new Exercise(questionIndex, questionType, question, studentAnswer, modelAnswer);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedExercise)) {
            return false;
        }

        XmlAdaptedExercise otherExercise = (XmlAdaptedExercise) other;
        return Objects.equals(questionIndex, otherExercise.questionIndex)
                && Objects.equals(questionType, otherExercise.questionType)
                && Objects.equals(question, otherExercise.question);
    }
}
```
###### \java\seedu\progresschecker\ui\ExerciseCard.java
``` java
/**
 * An UI component that displays information of an {@code Exercise}.
 */
public class ExerciseCard extends UiPart<Region> {

    private static final String FXML = "ExerciseListCard.fxml";

    public final Exercise exercise;

    @FXML
    private Label questionIndex;
    @FXML
    private Label questionType;
    @FXML
    private Label question;
    @FXML
    private Label studentAnswer;
    @FXML
    private Label modelAnswerHeader;
    @FXML
    private Label modelAnswer;

    public ExerciseCard(Exercise exercise) {
        super(FXML);
        this.exercise = exercise;
        questionIndex.setText(exercise.getQuestionIndex().value);
        question.setText(exercise.getQuestion().value);
        studentAnswer.setText(exercise.getStudentAnswer().value);
        modelAnswer.setText("");

        if (!exercise.getStudentAnswer().value.equals("")) {
            questionIndex.getStyleClass().add("answered");
            modelAnswerHeader.setText("Suggested Answer: ");
            modelAnswer.setText(exercise.getModelAnswer().value);
        }
    }
}
```
###### \java\seedu\progresschecker\ui\ExerciseListPanel.java
``` java
/**
 * Panel containing the list of exercises.
 */
public class ExerciseListPanel extends UiPart<Region> {
    private static final String FXML = "ExerciseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExerciseListPanel.class);

    @FXML
    private ListView<ExerciseCard> exerciseListView;

    public ExerciseListPanel(ObservableList<Exercise> exerciseList) {
        super(FXML);
        setConnections(exerciseList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Exercise> exerciseList) {
        ObservableList<ExerciseCard> mappedList = EasyBind.map(
                exerciseList, (exercise) -> new ExerciseCard(exercise));
        exerciseListView.setItems(mappedList);
        exerciseListView.setCellFactory(listView -> new ExerciseListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ExerciseCard}.
     */
    class ExerciseListViewCell extends ListCell<ExerciseCard> {

        @Override
        protected void updateItem(ExerciseCard exercise, boolean empty) {
            super.updateItem(exercise, empty);

            if (empty || exercise == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(exercise.getRoot());
            }
        }
    }

}
```
###### \java\seedu\progresschecker\ui\MainWindow.java
``` java
    @Subscribe
    private void handleTabLoadChangedEvent(TabLoadChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        SingleSelectionModel<Tab> selectionModel = tabPlaceholder.getSelectionModel();
        switch (event.getTabName()) {
        case "profile":
            selectionModel.select(profilePlaceholder);
            break;
        case "task":
            selectionModel.select(taskPlaceholder);
            break;
        case "exercise":
            selectionModel.select(exercisePlaceholder);
            break;
        case "issues":
            selectionModel.select(issuePlaceholder);
            break;
        default:
            selectionModel.select(selectionModel.getSelectedItem());
        }
    }
```
###### \java\seedu\progresschecker\ui\PersonCard.java
``` java
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
###### \java\seedu\progresschecker\ui\ProfilePanel.java
``` java
    /**
     * Get a deterministic tag color based off tag's name value.
     */
    private String getTagColor(String tagName) {
        int index = getValueOfString(tagName) % TAG_COLORS.length;
        return TAG_COLORS[index];
    }

    /**
     * Adds each letter of given string into an integer.
     */
    private int getValueOfString(String tagName) {
        int sum = 0;
        for (char c : tagName.toCharArray()) {
            sum += c;
        }
        return sum;
    }
```
###### \java\seedu\progresschecker\ui\ProfilePanel.java
``` java
        person.getTags().forEach(tag -> {
            Label label = new Label(tag.tagName);
            label.getStyleClass().add(getTagColor(tag.tagName));
            tags.getChildren().add(label);
        });
```
###### \resources\view\ExerciseListCard.fxml
``` fxml
<VBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
    </padding>
    <VBox prefWidth="325.0" styleClass="exercise">
        <children>
            <Label fx:id="questionIndex" text="\$questionIndex" wrapText="true" styleClass="question-number"/>
            <Label fx:id="question" text="\$question" wrapText="true"/>
            <Text/>
            <Separator/>
            <Text/>

            <Label text="Your Answer: "/>
            <Label fx:id="studentAnswer" text="\$studentAnswer" wrapText="true"/>
            <Text/>

            <Label fx:id="modelAnswerHeader" styleClass="exercise-header, suggested-answer"/>
            <Label fx:id="modelAnswer" text="\$modelAnswer" wrapText="true" styleClass="suggested-answer"/>
        </children>
    </VBox>
</VBox>
```
###### \resources\view\ExerciseListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView fx:id="exerciseListView" VBox.vgrow="ALWAYS" />
</VBox>
```
