package seedu.organizer.model.user;

import static com.google.common.base.Preconditions.checkArgument;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

//@@author dominickenn
/**
 * A user class with a question-answer for password retrieval
 */
public class UserWithQuestionAnswer extends User {

    public static final String MESSAGE_QUESTION_ANSWER_CONSTRAINTS =
            "Questions and answers can take any values, but cannot be blank";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     */
    public static final String QUESTION_VALIDATION_REGEX = "^(?=\\s*\\S).*$";
    public static final String ANSWER_VALIDATION_REGEX = "^(?=\\s*\\S).*$";

    public final String question;
    public final String answer;

    /**
     * Constructs a {@code UserWithQuestionAnswer}.
     *
     * @param username A valid username.
     * @param password A valid password.
     */
    public UserWithQuestionAnswer(String username, String password) {
        super(username, password);
        question = null;
        answer = null;
    }

    /**
     * Constructs a {@code UserWithQuestionAnswer}.
     *
     * @param username A valid username.
     * @param password A valid password.
     * @param question A valid question.
     * @param answer A valid answer.
     */
    public UserWithQuestionAnswer(String username, String password, String question, String answer) {
        super(username, password);
        requireAllNonNull(question, answer);
        checkArgument(isValidQuestion(question), MESSAGE_QUESTION_ANSWER_CONSTRAINTS);
        checkArgument(isValidAnswer(answer), MESSAGE_QUESTION_ANSWER_CONSTRAINTS);
        this.question = question;
        this.answer = answer;
    }

    /**
     * Returns true if a given string is a valid question.
     */
    public static boolean isValidQuestion(String test) {
        return test.matches(QUESTION_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid answer.
     */
    public static boolean isValidAnswer(String test) {
        return test.matches(ANSWER_VALIDATION_REGEX);
    }
}
