# iNekox3
###### \java\seedu\progresschecker\logic\commands\AnswerCommandTest.java
``` java
public class AnswerCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullExercise_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AnswerCommand(null, null);
    }

    @Test
    public void equals() {
        Exercise exercise1 = new ExerciseBuilder().withStudentAnswer("a b c").build();
        Exercise exercise2 = new ExerciseBuilder().withStudentAnswer("d e f").build();
        AnswerCommand answerExercise1Command = new AnswerCommand(exercise1.getQuestionIndex(),
                new StudentAnswer("a b c"));
        AnswerCommand answerExercise2Command = new AnswerCommand(exercise2.getQuestionIndex(),
                new StudentAnswer("d e f"));

        // same object -> returns true
        assertTrue(answerExercise1Command.equals(answerExercise1Command));

        // different types -> returns false
        assertFalse(answerExercise1Command.equals(1));

        // null -> returns false
        assertFalse(answerExercise1Command.equals(null));

        // different person -> returns false
        assertFalse(answerExercise1Command.equals(answerExercise2Command));
    }
}
```
###### \java\seedu\progresschecker\logic\commands\ViewCommandTest.java
``` java
/**
 * Contains assertion tests for {@code ViewCommand}.
 */
public class ViewCommandTest {
    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(TYPE_TASK, -1, false);
        ViewCommand viewSecondCommand = new ViewCommand(TYPE_EXERCISE, 11, true);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(TYPE_TASK, -1, false);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different type -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }
}
```
###### \java\seedu\progresschecker\logic\parser\AnswerCommandParserTest.java
``` java
public class AnswerCommandParserTest {

    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_invalidArgsIndex_throwsParseException() {
        assertParseFailure(parser, "ans 11.50.80 b", MESSAGE_INVALID_WEEK_NUMBER
                + " \n" + AnswerCommand.MESSAGE_USAGE);
    }
}
```
###### \java\seedu\progresschecker\logic\parser\ProgressCheckerParserTest.java
``` java
    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + TYPE_EXERCISE + " 11 true");
        assertEquals(new ViewCommand(TYPE_EXERCISE, 11, true), command);
    }

```
###### \java\seedu\progresschecker\logic\parser\ViewCommandParserTest.java
``` java
public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgsType_returnsViewCommand() {
        assertParseSuccess(parser, "exercise", new ViewCommand(TYPE_EXERCISE, -1,
                false));
    }

    @Test
    public void parse_validArgsWeekNumber_returnsViewCommand() {
        assertParseSuccess(parser, "exercise 5", new ViewCommand(TYPE_EXERCISE, 5,
                true));
    }

    @Test
    public void parse_invalidArgsType_throwsParseException() {
        assertParseFailure(parser, "invalid type", MESSAGE_INVALID_TAB_TYPE
                + " \n" + ViewCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidArgsWeekNumber_throwsParseException() {
        assertParseFailure(parser, "exercise 0", MESSAGE_INVALID_WEEK_NUMBER
                + " \n" + ViewCommand.MESSAGE_USAGE);
    }
}
```
###### \java\seedu\progresschecker\testutil\ExerciseBuilder.java
``` java
/**
 * A utility class to help with building Exercise objects.
 */
public class ExerciseBuilder {

    public static final String DEFAULT_QUESTION_INDEX = "11.1.1";
    public static final String DEFAULT_QUESTION_TYPE = "choice";
    public static final String DEFAULT_QUESTION = "What is the main difference between"
            + "a class diagram and and an OO domain model?\n"
            + "a. One is about the problem domain while the other is about the solution domain.\n"
            + "b. One has more classes than the other.\n"
            + "c. One shows more details than the other.\n"
            + "d. One is a UML diagram, while the other is not a UML diagram.";
    public static final String DEFAULT_STUDENT_ANSWER = "";
    public static final String DEFAULT_MODEL_ANSWER = "a. Both are UML diagrams, and use the class diagram notation. "
            + "While it is true that often a class diagram may have more classes and more details, "
            + "the main difference is that the OO domain model describes the problem domain "
            + "while the class diagram describes the solution.";

    private QuestionIndex questionIndex;
    private QuestionType questionType;
    private Question question;
    private StudentAnswer studentAnswer;
    private ModelAnswer modelAnswer;

    public ExerciseBuilder() {
        questionIndex = new QuestionIndex(DEFAULT_QUESTION_INDEX);
        questionType = new QuestionType(DEFAULT_QUESTION_TYPE);
        question = new Question(DEFAULT_QUESTION);
        studentAnswer = new StudentAnswer(DEFAULT_STUDENT_ANSWER);
        modelAnswer = new ModelAnswer(DEFAULT_MODEL_ANSWER);
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code exerciseToCopy}.
     */
    public ExerciseBuilder(Exercise exerciseToCopy) {
        questionIndex = exerciseToCopy.getQuestionIndex();
        questionType = exerciseToCopy.getQuestionType();
        question = exerciseToCopy.getQuestion();
        studentAnswer = exerciseToCopy.getStudentAnswer();
        modelAnswer = exerciseToCopy.getModelAnswer();
    }

    /**
     * Sets the {@code StudentAnswer} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withStudentAnswer(String studentAnswer) {
        this.studentAnswer = new StudentAnswer(studentAnswer);
        return this;
    }

    public Exercise build() {
        return new Exercise(questionIndex, questionType, question, studentAnswer, modelAnswer);
    }

}
```
###### \java\seedu\progresschecker\testutil\TypicalTabTypes.java
``` java
/**
 * A utility class containing a list of {@code String} objects to be used in tests.
 */
public class TypicalTabTypes {
    public static final String TYPE_PROFILE = "profile";
    public static final String TYPE_TASK = "task";
    public static final String TYPE_EXERCISE = "exercise";
    public static final String TYPE_ISSUES = "issues";
}
```
