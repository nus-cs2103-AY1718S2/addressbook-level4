package seedu.progresschecker.model.exercise;

import static seedu.progresschecker.commons.util.CollectionUtil.requireAllNonNull;

//@@author iNekox3
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
