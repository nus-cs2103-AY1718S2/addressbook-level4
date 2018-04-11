package seedu.organizer.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.commons.util.StringUtil;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.UserWithQuestionAnswer;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_WRONG_PART_COUNT = "Number of parts is incorrect";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndex} into an array of {@code Index} and returns it. Leading and trailing whitespaces
     * will be trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseIndexAsArray(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        String[] rawIndex = trimmedIndex.split(" +");
        Index[] result = new Index[rawIndex.length];

        for (int i = 0; i < rawIndex.length; i++) {
            if (!StringUtil.isNonZeroUnsignedInteger(rawIndex[i])) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
            result[i] = Index.fromOneBased(Integer.parseInt(rawIndex[i]));
        }

        return result;
    }

    /**
     * Parses {@code oneBasedIndex} into an array of {@code Index} with length 2 and returns it. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index[] parseSubtaskIndex(String oneBasedIndex) throws IllegalValueException {
        Index[] result = parseIndexAsArray(oneBasedIndex);
        if (result.length != 2) {
            throw new IllegalValueException(MESSAGE_WRONG_PART_COUNT);
        }
        return result;
    }

    //@@author dominickenn
    /**
     * Parses a {@code username} into a {@code String}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code username} is invalid
     */
    public static String parseUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!User.isValidUsername(trimmedUsername)) {
            throw new IllegalValueException(User.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return username;
    }

    /**
     * Parses an {@code Optional<String> username} into an {@code Optional<String>} if {@code username} is present
     * See header comment of this class regarding the use of {@code Optional} parameters
     */
    public static Optional<String> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String password} into a {@code String}
     * Leading and trailing whitespaces will be trimmed
     *
     * @throws IllegalValueException if the given {@code password} is invalid.
     */
    public static String parsePassword(String password) throws IllegalValueException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!User.isValidPassword(trimmedPassword)) {
            throw new IllegalValueException(User.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return password;
    }

    /**
     * Parses a {@code Optional<String> password} into an {@code Optional<String>} if {@code password} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parsePassword(Optional<String> password) throws IllegalValueException {
        requireNonNull(password);
        return password.isPresent() ? Optional.of(parsePassword(password.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String question} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code question} is invalid.
     */
    public static String parseQuestion(String question) throws IllegalValueException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!UserWithQuestionAnswer.isValidQuestion(trimmedQuestion)) {
            throw new IllegalValueException(UserWithQuestionAnswer.MESSAGE_QUESTION_CONSTRAINTS);
        }
        return question;
    }

    /**
     * Parses a {@code Optional<String> question} into an {@code Optional<String>} if {@code question} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseQuestion(Optional<String> question) throws IllegalValueException {
        requireNonNull(question);
        return question.isPresent() ? Optional.of(parseQuestion(question.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String answer} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code answer} is invalid.
     */
    public static String parseAnswer(String answer) throws IllegalValueException {
        requireNonNull(answer);
        String trimmedAnswer = answer.trim();
        if (!UserWithQuestionAnswer.isValidAnswer(trimmedAnswer)) {
            throw new IllegalValueException(UserWithQuestionAnswer.MESSAGE_ANSWER_CONSTRAINTS);
        }
        return answer;
    }

    /**
     * Parses a {@code Optional<String> answer} into an {@code Optional<String>} if {@code answer} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseAnswer(Optional<String> answer) throws IllegalValueException {
        requireNonNull(answer);
        return answer.isPresent() ? Optional.of(parseAnswer(answer.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws IllegalValueException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        requireNonNull(priority);
        return priority.isPresent() ? Optional.of(parsePriority(priority.get())) : Optional.empty();
    }
    //@@author

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    //@@author natania
    /**
     * Parses a {@code String times} into an int.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static int parseTimes(String times) throws IllegalValueException {
        requireNonNull(times);
        String trimmedTimes = times.trim();
        int time = Integer.parseInt(trimmedTimes);
        if (time <= 0) {
            throw new IllegalValueException("This is an invalid number of times.");
        }
        return time;
    }

    /**
     * Parses a {@code Optional<String> times} into an {@code Optional<Integer>} if times is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Integer> parseTimes(Optional<String> times) throws IllegalValueException {
        requireNonNull(times);
        return times.isPresent() ? Optional.of(parseTimes(times.get())) : Optional.empty();
    }

    //@@author
    /**
     * Parses a {@code String organizer} into an {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code Optional<String> organizer} into an {@code Optional<Description>} if
     * {@code organizer} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(parseDescription(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String deadline} into an {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws IllegalValueException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Deadline.isValidDeadline(trimmedDeadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_DEADLINE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }

    /**
     * Parses a {@code Optional<String> deadline} into an {@code Optional<Deadline>} if {@code deadline} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Deadline> parseDeadline(Optional<String> deadline) throws IllegalValueException {
        requireNonNull(deadline);
        return deadline.isPresent() ? Optional.of(parseDeadline(deadline.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
