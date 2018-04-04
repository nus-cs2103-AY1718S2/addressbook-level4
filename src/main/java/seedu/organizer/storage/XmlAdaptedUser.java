
package seedu.organizer.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;

//@@author dominickenn
/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedUser {

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement
    private String question;
    @XmlElement
    private String answer;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {}

    /**
     * Constructs a {@code XmlAdaptedUser} with the given {@code usernamename} and {@code password}.
     */
    public XmlAdaptedUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructs a {@code XmlAdaptedUser} with the given
     * {@code usernamename}, {@code password}, {@code question}, {@code answer}.
     */
    public XmlAdaptedUser(String username, String password, String question, String answer) {
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedUser(User source) {
        username = source.username;
        password = source.password;
    }

    /**
     * Converts a given UserWithQuestionAnswer into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedUser(UserWithQuestionAnswer source) {
        username = source.username;
        password = source.password;
        question = source.question;
        answer = source.answer;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Converts this jaxb-friendly adapted user object into the model's User object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public User toUserModelType() throws IllegalValueException {
        if (!User.isValidUsername(username) || !User.isValidPassword(password)) {
            throw new IllegalValueException(User.MESSAGE_USER_CONSTRAINTS);
        }
        return new User(username, password);
    }

    /**
     * Converts this jaxb-friendly adapted user object into the model's UserWithQuestionAnswer object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public UserWithQuestionAnswer toUserQuestionAnswerModelType() throws IllegalValueException {
        if (!User.isValidUsername(username) || !User.isValidPassword(password)) {
            throw new IllegalValueException(User.MESSAGE_USER_CONSTRAINTS);
        }
        if (!UserWithQuestionAnswer.isValidQuestion(question)
                || !UserWithQuestionAnswer.isValidAnswer(answer)) {
            throw new IllegalValueException(UserWithQuestionAnswer.MESSAGE_QUESTION_ANSWER_CONSTRAINTS);
        }
        return new UserWithQuestionAnswer(username, password, question, answer);
    }

    public boolean isUserWithQuestionAnswer() {
        return !(question == null) && !(answer == null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUser)) {
            return false;
        }

        return username.equals(((XmlAdaptedUser) other).username)
                && password.equals(((XmlAdaptedUser) other).password);
    }
}
