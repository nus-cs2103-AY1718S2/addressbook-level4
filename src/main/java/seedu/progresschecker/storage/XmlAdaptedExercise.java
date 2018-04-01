package seedu.progresschecker.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.exercise.ModelAnswer;
import seedu.progresschecker.model.exercise.Question;
import seedu.progresschecker.model.exercise.QuestionIndex;
import seedu.progresschecker.model.exercise.QuestionType;
import seedu.progresschecker.model.exercise.StudentAnswer;

//@@author iNekox3
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
