package seedu.progresschecker.testutil;

import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.exercise.ModelAnswer;
import seedu.progresschecker.model.exercise.Question;
import seedu.progresschecker.model.exercise.QuestionIndex;
import seedu.progresschecker.model.exercise.QuestionType;
import seedu.progresschecker.model.exercise.StudentAnswer;

//@@author iNekox3
/**
 * A utility class to help with building Person objects.
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
