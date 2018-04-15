package seedu.progresschecker.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.logic.commands.ViewTaskListCommand.ALL_WEEK;
import static seedu.progresschecker.logic.commands.ViewTaskListCommand.COMPULSORY;
import static seedu.progresschecker.logic.commands.ViewTaskListCommand.MAX_TITLE_LENGTH;
import static seedu.progresschecker.logic.commands.ViewTaskListCommand.MESSAGE_TITLE_CONSTRAINTS;
import static seedu.progresschecker.logic.commands.ViewTaskListCommand.SUBMISSION;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.commons.util.StringUtil;
import seedu.progresschecker.model.credentials.Passcode;
import seedu.progresschecker.model.credentials.Repository;
import seedu.progresschecker.model.credentials.Username;
import seedu.progresschecker.model.exercise.QuestionIndex;
import seedu.progresschecker.model.exercise.StudentAnswer;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Body;
import seedu.progresschecker.model.issues.Labels;
import seedu.progresschecker.model.issues.Milestone;
import seedu.progresschecker.model.issues.Title;
import seedu.progresschecker.model.person.Email;
import seedu.progresschecker.model.person.GithubUsername;
import seedu.progresschecker.model.person.Major;
import seedu.progresschecker.model.person.Name;
import seedu.progresschecker.model.person.Phone;
import seedu.progresschecker.model.person.Year;
import seedu.progresschecker.model.tag.Tag;

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
    public static final String MESSAGE_INVALID_INDEX_OR_FORMAT = "Wrong format or index is not a positive integer.";
    public static final String MESSAGE_INVALID_TASK_FILTER = "Filter keyword is not an integer 1~13 "
            + "or * or \"submission\" or \"sub\" or \"compulsory\" or \"com\".";
    public static final String MESSAGE_INVALID_TAB_TYPE = "Invalid command format!";
    public static final String MESSAGE_INVALID_WEEK_NUMBER = "Invalid command format!";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    //@@author EdwardKSG
    /**
     * Parses {@code String} into an {@code int} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static int parseTaskIndex(String index) throws IllegalValueException {
        String trimmedIndex = index.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Integer.parseInt(trimmedIndex);
    }

    /**
     * Parses {@code String} into an {@code int} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified week number is invalid (neither an integer ranging from 1 to 13
     * nor an asterisk(*)).
     */
    public static int parseTaskWeek(String week) throws IllegalValueException {
        String trimmedWeek = week.trim();
        if (trimmedWeek.equals("*")) {
            return ALL_WEEK;
        } else if (trimmedWeek.equals("sub") || trimmedWeek.equals("submission")) {
            return SUBMISSION;
        } else if (trimmedWeek.equals("com") || trimmedWeek.equals("compulsory")) {
            return COMPULSORY;
        } else if (!StringUtil.isNonZeroUnsignedInteger(trimmedWeek)) {
            throw new IllegalValueException(MESSAGE_INVALID_TASK_FILTER);
        }
        int intWeek = Integer.parseInt(trimmedWeek);
        if (intWeek >= 1 && intWeek <= 13) {
            return intWeek;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TASK_FILTER);
        }
    }
    //@@author

    //@@author iNekox3
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

    //@@author Livian1107
    /**
     * Parses {@code type} into a {@code String} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified theme is invalid (not of string "day" or "night").
     */
    public static String parseTheme(String theme) throws IllegalValueException {
        String trimmedType = theme.trim();
        if (!trimmedType.equals("day") && !trimmedType.equals("night")) {
            throw new IllegalValueException(MESSAGE_INVALID_TAB_TYPE);
        }
        return trimmedType;
    }

    //@@author EdwardKSG
    /**
     * Parses a {@code String Title} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code Title} is invalid.
     */
    public static String parseTaskTitle(String title) throws IllegalValueException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (trimmedTitle.length() > MAX_TITLE_LENGTH) {
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        return trimmedTitle;
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

    //@@author adityaa1998
    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code title} is invalid.
     */

    public static Title parseTitle(String title) throws IllegalValueException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new IllegalValueException(Title.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Name>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        requireNonNull(title);
        return title.isPresent() ? Optional.of(parseTitle(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String assignees} into a {@code Assignees}.
     * Leading and trailing whitespaces will be trimmed.
     */

    public static Assignees parseAssignees(String assignees) throws IllegalValueException {
        requireNonNull(assignees);
        String trimmedAssignees = assignees.trim();
        if (!Assignees.isValidAssignee(trimmedAssignees)) {
            throw new IllegalValueException(Assignees.MESSAGE_ASSIGNEES_CONSTRAINTS);
        }
        return new Assignees(trimmedAssignees);
    }

    /**
     * Parses {@code Collection<String> assignees} into a {@code Set<Assignees>}.
     */
    public static Set<Assignees> parseAssignees(Collection<String> assignees) throws IllegalValueException {
        requireNonNull(assignees);
        final Set<Assignees> assigneesSet = new HashSet<>();
        for (String assigneeName : assignees) {
            assigneesSet.add(parseAssignees(assigneeName));
        }
        return assigneesSet;
    }

    /**
     * Parses a {@code String labels} into a {@code Labels}.
     * Leading and trailing whitespaces will be trimmed.
     */

    public static Labels parseLabels(String labels) throws IllegalValueException {
        requireNonNull(labels);
        String trimmedLabels = labels.trim();
        if (!Labels.isValidLabel(trimmedLabels)) {
            throw new IllegalValueException(Labels.MESSAGE_LABEL_CONSTRAINTS);
        }
        return new Labels(trimmedLabels);
    }

    /**
     * Parses {@code Collection<String> labels} into a {@code Set<Labels>}.
     */
    public static Set<Labels> parseLabels(Collection<String> labels) throws IllegalValueException {
        requireNonNull(labels);
        final Set<Labels> labelsSet = new HashSet<>();
        for (String labelName : labels) {
            labelsSet.add(parseLabels(labelName));
        }
        return labelsSet;
    }


    /**
     * Parses a {@code String milestone} into a {@code Milestone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code milestone} is invalid.
     */

    public static Milestone parseMilestone(String milestone) throws IllegalValueException {
        requireNonNull(milestone);
        String trimmedMilestone = milestone.trim();
        return new Milestone(trimmedMilestone);
    }

    /**
     * Parses a {@code Optional<String> milestone} into an {@code Optional<Milestone>} if {@code milestone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Milestone> parseMilestone(Optional<String> milestone) throws IllegalValueException {
        requireNonNull(milestone);
        return milestone.isPresent() ? Optional.of(parseMilestone(milestone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String body} into a {@code Body}.
     * Leading and trailing whitespaces will be trimmed.
     */

    public static Body parseBody(String body) throws IllegalValueException {
        requireNonNull(body);
        String trimmedBody = body.trim();
        if (!Body.isValidBody(trimmedBody)) {
            throw new IllegalValueException(Body.MESSAGE_BODY_CONSTRAINTS);
        }
        return new Body(trimmedBody);
    }

    /**
     * Parses a {@code Optional<String> body} into an {@code Optional<Body>} if {@code body} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Body> parseBody(Optional<String> body) throws IllegalValueException {
        requireNonNull(body);
        return body.isPresent() ? Optional.of(parseBody(body.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String username} into a {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Username parseGitUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new IllegalValueException(Username.MESSAGE_GITUSERNAME_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     Parses a {@code Optional<String> username} into an {@code Optional<Username>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Username> parseGitUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseGitUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String passcode} into a {@code Passcode}.
     */
    public static Passcode parsePasscode(String passcode) throws IllegalValueException {
        requireNonNull(passcode);
        if (!Passcode.isValidPasscode(passcode)) {
            throw new IllegalValueException(Passcode.MESSAGE_PASSCODE_CONSTRAINTS);
        }
        return new Passcode(passcode);
    }

    /**
     Parses a {@code Optional<String> Passcode} into an {@code Optional<Passcode>} if {@code passcpde} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Passcode> parsePasscode(Optional<String> passcode) throws IllegalValueException {
        requireNonNull(passcode);
        return passcode.isPresent() ? Optional.of(parsePasscode(passcode.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String repositroy} into a {@code Repository}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Repository parseRepository(String repository) throws IllegalValueException {
        requireNonNull(repository);
        String trimmedRepository = repository.trim();
        if (!Repository.isValidRepository(trimmedRepository)) {
            throw new IllegalValueException(Repository.MESSAGE_REPOSITORY_CONSTRAINTS);
        }
        return new Repository(trimmedRepository);
    }

    /**
     Parses a {@code Optional<String> Repository} into an {@code Optional<Repository>} if {@code repository} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Repository> parseRepository(Optional<String> repository) throws IllegalValueException {
        requireNonNull(repository);
        return repository.isPresent() ? Optional.of(parseRepository(repository.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String state} into a trimmed string.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String parseStateType(String state) throws IllegalValueException {
        requireNonNull(state);
        String trimmedState = state.trim();
        return trimmedState;
    }
    //@@author

    //@@author EdwardKSG
    /**
     * Parses a {@code String username} into a {@code GithubUsername}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code username} is invalid.
     */

    public static GithubUsername parseUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!GithubUsername.isValidUsername(trimmedUsername)) {
            throw new IllegalValueException(GithubUsername.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new GithubUsername(trimmedUsername);
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<GithubUsername>}
     *     if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<GithubUsername> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseUsername(username.get())) : Optional.empty();
    }
    //@@author

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    //@@author EdwardKSG
    /**
     * Parses a {@code String major} into an {@code Major}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code major} is invalid.
     */
    public static Major parseMajor(String major) throws IllegalValueException {
        requireNonNull(major);
        String trimmedMajor = major.trim();
        if (!Major.isValidMajor(trimmedMajor)) {
            throw new IllegalValueException(Major.MESSAGE_MAJOR_CONSTRAINTS);
        }
        return new Major(trimmedMajor);
    }

    /**
     * Parses a {@code Optional<String> major} into an {@code Optional<Major>} if {@code major} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Major> parseMajor(Optional<String> major) throws IllegalValueException {
        requireNonNull(major);
        return major.isPresent() ? Optional.of(parseMajor(major.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String year} into an {@code Year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws IllegalValueException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new IllegalValueException(Year.MESSAGE_YEAR_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    /**
     * Parses a {@code Optional<String> year} into an {@code Optional<Year>} if {@code year} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Year> parseYear(Optional<String> year) throws IllegalValueException {
        requireNonNull(year);
        return year.isPresent() ? Optional.of(parseYear(year.get())) : Optional.empty();
    }
    //@@author

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(parseEmail(email.get())) : Optional.empty();
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

    //@@author iNekox3
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
