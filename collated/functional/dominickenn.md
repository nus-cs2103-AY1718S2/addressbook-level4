# dominickenn
###### /java/seedu/organizer/logic/commands/AddQuestionAnswerCommand.java
``` java
/**
 * Adds a question-answer to the currently logged in user.
 */
public class AddQuestionAnswerCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addqa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a question and answer to the current user."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "What cats do you like? "
            + PREFIX_ANSWER + "All cats ";

    public static final String MESSAGE_ADD_QUESTION_ANSWER_SUCCESS = "Added question and answer to user: %1$s";

    private User userToEdit;
    private UserWithQuestionAnswer editedUser;
    private String question;
    private String answer;

    /**
     * Updates current user with a user with the given question and answer
     */
    public AddQuestionAnswerCommand(String question, String answer) {
        requireAllNonNull(question, answer, getCurrentlyLoggedInUser());
        this.userToEdit = getCurrentlyLoggedInUser();
        this.question = question;
        this.answer = answer;
        editedUser = createEditedUser();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        model.addQuestionAnswerToUser(userToEdit, editedUser);
        return new CommandResult(String.format(MESSAGE_ADD_QUESTION_ANSWER_SUCCESS, editedUser));
    }

    /**
     * Creates and returns a UserWithQuestionAnswer with the current user, and the question and answer
     */
    private UserWithQuestionAnswer createEditedUser() {
        requireNonNull(userToEdit);
        UserWithQuestionAnswer user = new UserWithQuestionAnswer(
                userToEdit.username,
                userToEdit.password,
                question,
                answer);
        return user;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddQuestionAnswerCommand)) {
            return false;
        }

        // state check
        AddQuestionAnswerCommand a = (AddQuestionAnswerCommand) other;
        return userToEdit.equals(a.userToEdit)
                && question.equals(a.question)
                && answer.equals(a.answer);
    }

}

```
###### /java/seedu/organizer/logic/commands/AnswerCommand.java
``` java
/**
 * Answer a question correctly to retrieve a user's password.
 */
public class AnswerCommand extends Command {

    public static final String COMMAND_WORD = "answer";
    public static final String COMMAND_ALIAS = "ans";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": If the user exists, "
            + "answer a user's question\n"
            + "Parameters: " + PREFIX_USERNAME + "USERNAME"
            + PREFIX_ANSWER + "ANSWER\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_USERNAME + "david"
            + PREFIX_ANSWER + "yes";

    public static final String MESSAGE_SUCCESS = "Answer correct!\nPassword: %1$s";
    public static final String MESSAGE_NO_QUESTION = "User %1$s does not have a question";
    public static final String MESSAGE_WRONG_ANSWER = "Answer is incorrect";

    private String username;
    private String answer;

    public AnswerCommand(String username, String answer) {
        requireAllNonNull(username, answer);
        this.username = username;
        this.answer = answer;
    }

    @Override
    public CommandResult execute() {
        User user;
        try {
            user = model.getUserByUsername(username);
        } catch (UserNotFoundException e) {
            throw new AssertionError("User does not exist");
        }
        if (user instanceof UserWithQuestionAnswer) {
            String answer = ((UserWithQuestionAnswer) user).answer;
            String password = user.password;
            return answerQuestionAndGetResult(answer, password);
        } else {
            return new CommandResult(String.format(MESSAGE_NO_QUESTION, username));
        }
    }

    /**
     * Returns the password if the answer is correct
     */
    private CommandResult answerQuestionAndGetResult(String answer, String password) {
        requireAllNonNull(answer, password);
        if (answer.equals(this.answer)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, password));
        } else {
            return new CommandResult(MESSAGE_WRONG_ANSWER);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AnswerCommand // instanceof handles nulls
                && this.username.equals(((AnswerCommand) other).username)
                && this.answer.equals(((AnswerCommand) other).answer)); // state check
    }
}
```
###### /java/seedu/organizer/logic/commands/ForgotPasswordCommand.java
``` java
/**
 * Finds a user in PrioriTask with the given username.
 */
public class ForgotPasswordCommand extends Command {

    public static final String COMMAND_WORD = "forgotpassword";
    public static final String COMMAND_ALIAS = "fp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves question from user"
            + " with the username, if the user exists. \n"
            + "Parameters: " + PREFIX_USERNAME + " USERNAME\n"
            + "Example: " + COMMAND_WORD + PREFIX_USERNAME + "david";

    public static final String MESSAGE_SUCCESS = "Question: %1$s";
    public static final String MESSAGE_NO_QUESTION = "User %1$s does not have a question";

    private String username;

    public ForgotPasswordCommand(String username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public CommandResult execute() {
        User user;
        try {
            user = model.getUserByUsername(username);
        } catch (UserNotFoundException e) {
            throw new AssertionError("User does not exist");
        }
        if (user instanceof UserWithQuestionAnswer) {
            String question = ((UserWithQuestionAnswer) user).question;
            return new CommandResult(String.format(MESSAGE_SUCCESS, question));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_QUESTION, username));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ForgotPasswordCommand // instanceof handles nulls
                && this.username.equals(((ForgotPasswordCommand) other).username)); // state check
    }
}

```
###### /java/seedu/organizer/logic/commands/LoginCommand.java
``` java
/**
 * Adds a user to the organizer.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "in";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Login to PrioriTask. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "david "
            + PREFIX_PASSWORD + "david1234 ";

    public static final String MESSAGE_SUCCESS = "User log in successful : %1$s";
    public static final String MESSAGE_USER_NOT_FOUND = "This user does not exist";
    public static final String MESSAGE_CURRENTLY_LOGGED_IN = "A user is currently loggd in";

    private final User toLogin;

    /**
     * Creates an LoginCommand to add the specified {@code User}
     */
    public LoginCommand(User user) {
        requireNonNull(user);
        toLogin = user;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.loginUser(toLogin);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin));
        } catch (UserNotFoundException unf) {
            throw new CommandException(MESSAGE_USER_NOT_FOUND);
        } catch (CurrentlyLoggedInException cli) {
            throw new CommandException(MESSAGE_CURRENTLY_LOGGED_IN);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && toLogin.equals(((LoginCommand) other).toLogin));
    }
}

```
###### /java/seedu/organizer/logic/commands/LogoutCommand.java
``` java
/**
 * Logout from organizer.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String COMMAND_ALIAS = "out";

    public static final String MESSAGE_LOGOUT_ACKNOWLEDGEMENT = "Logged out";

    @Override
    public CommandResult execute() {
        model.logout();
        return new CommandResult(MESSAGE_LOGOUT_ACKNOWLEDGEMENT);
    }

}
```
###### /java/seedu/organizer/logic/commands/SignUpCommand.java
``` java
/**
 * Adds a user to the organizer.
 */
public class SignUpCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "signup";
    public static final String COMMAND_ALIAS = "su";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a user account for PrioriTask. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "david "
            + PREFIX_PASSWORD + "david1234 ";

    public static final String MESSAGE_SUCCESS = "New user account created: %1$s";
    public static final String MESSAGE_DUPLICATE_USER = "This user already exists in the organizer";

    private final User toAdd;

    /**
     * Creates an SignUpCommand to add the specified {@code User}
     */
    public SignUpCommand(User user) {
        requireNonNull(user);
        toAdd = user;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addUser(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateUserException e) {
            throw new CommandException(MESSAGE_DUPLICATE_USER);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignUpCommand // instanceof handles nulls
                && toAdd.equals(((SignUpCommand) other).toAdd));
    }
}
```
###### /java/seedu/organizer/logic/parser/AddCommandParser.java
``` java
            Priority priority;
            if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
                priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY)).get();
            } else {
                priority = ParserUtil.parsePriority(Priority.LOWEST_PRIORITY_LEVEL);
            }
            Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE)).get();
```
###### /java/seedu/organizer/logic/parser/AddQuestionAnswerCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddQuestionAnswerCommand object
 */
public class AddQuestionAnswerCommandParser implements Parser<AddQuestionAnswerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddQuestionAnswerCommand
     * and returns an AddQuestionAnswerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddQuestionAnswerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER);
        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddQuestionAnswerCommand.MESSAGE_USAGE));
        }
        try {
            String question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION)).get();
            String answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER)).get();
            return new AddQuestionAnswerCommand(question, answer);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

```
###### /java/seedu/organizer/logic/parser/AnswerCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AnswerCommand object
 */
public class AnswerCommandParser implements Parser<AnswerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AnswerCommand
     * and returns a AnswerCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnswerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_ANSWER);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_ANSWER)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }
        try {
            String username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            String answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER)).get();
            return new AnswerCommand(username, answer);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

```
###### /java/seedu/organizer/logic/parser/ForgotPasswordCommandParser.java
``` java

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.ForgotPasswordCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;

```
###### /java/seedu/organizer/logic/parser/ForgotPasswordCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ForgotPasswordCommand object
 */
public class ForgotPasswordCommandParser implements Parser<ForgotPasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ForgotPasswordCommand
     * and returns a ForgotPasswordCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ForgotPasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ForgotPasswordCommand.MESSAGE_USAGE));
        }
        try {
            String username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            return new ForgotPasswordCommand(username);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### /java/seedu/organizer/logic/parser/LoginCommandParser.java
``` java
/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        try {
            String username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            String password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();
            User user = new User(username, password);

            return new LoginCommand(user);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /java/seedu/organizer/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String username} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code username} is invalid.
     */
    public static String parseUsername(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedUsername = name.trim();
        if (!User.isValidUsername(trimmedUsername)) {
            throw new IllegalValueException(User.MESSAGE_USER_CONSTRAINTS);
        }
        return name;
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<String>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<String> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String password} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code password} is invalid.
     */
    public static String parsePassword(String password) throws IllegalValueException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!User.isValidPassword(trimmedPassword)) {
            throw new IllegalValueException(User.MESSAGE_USER_CONSTRAINTS);
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
            throw new IllegalValueException(UserWithQuestionAnswer.MESSAGE_QUESTION_ANSWER_CONSTRAINTS);
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
            throw new IllegalValueException(UserWithQuestionAnswer.MESSAGE_QUESTION_ANSWER_CONSTRAINTS);
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
```
###### /java/seedu/organizer/logic/parser/SignUpCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SignUpCommand object
 */
public class SignUpCommandParser implements Parser<SignUpCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SignUpCommand
     * and returns an SignUpCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SignUpCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE));
        }

        try {
            String username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            String password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();
            User user = new User(username, password);

            return new SignUpCommand(user);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### /java/seedu/organizer/model/ModelManager.java
``` java

    public static User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }

    @Override
    public synchronized void addUser(User user) throws DuplicateUserException {
        organizer.addUser(user);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
        organizer.loginUser(user);
        currentlyLoggedInUser = organizer.getCurrentLoggedInUser();
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void logout() {
        organizer.logout();
        currentlyLoggedInUser = organizer.getCurrentLoggedInUser();
        updateFilteredTaskList(PREDICATE_SHOW_NO_TASKS);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void addQuestionAnswerToUser(User toRemove, UserWithQuestionAnswer toAdd) {
        requireAllNonNull(toRemove, toAdd);
        organizer.updateUserToUserWithQuestionAnswer(toRemove, toAdd);
        indicateOrganizerChanged();
    }

    @Override
    public synchronized void deleteCurrentUserTasks() {
        organizer.deleteUserTasks(getCurrentlyLoggedInUser());
        indicateOrganizerChanged();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        requireNonNull(username);
        return organizer.getUserbyUsername(username);
    }
```
###### /java/seedu/organizer/model/Organizer.java
``` java
    public void setUsers(List<User> users) {
        this.users.setUsers(users);
    }
```
###### /java/seedu/organizer/model/Organizer.java
``` java
    //// user=level operations
    /**
     * Adds a user to the organizer
     */
    public void addUser(User user) throws DuplicateUserException {
        requireNonNull(user);
        users.add(user);
    }

    /**
     * Sets currentLoggedInUser of the organizer
     */
    public void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
        requireNonNull(user);
        users.setCurrentLoggedInUser(user);
    }

    /**
     * Replaces a user with another user in users
     */
    public void updateUserToUserWithQuestionAnswer(
            User toRemove, UserWithQuestionAnswer toAdd) {
        requireAllNonNull(toRemove, toAdd);
        try {
            users.updateUserToUserWithQuestionAnswer(toRemove, toAdd);
        } catch (UserNotFoundException e) {
            throw new AssertionError("User does not exist");
        }
    }

    public void logout() {
        users.setCurrentLoggedInUserToNull();
    }

    public User getCurrentLoggedInUser() {
        return users.getCurrentLoggedInUser();
    }

    /**
     * Deletes all tasks by {@code user} from tasks
     */
    public void deleteUserTasks(User user) {
        requireNonNull(user);
        tasks.deleteUserTasks(user);
    }

    public User getUserbyUsername(String username) throws UserNotFoundException {
        requireNonNull(username);
        return users.getUserByUsername(username);
    }
```
###### /java/seedu/organizer/model/task/DateAdded.java
``` java
/**
 * Represents a Task's dateAdded in the organizer book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class DateAdded {

    public static final String MESSAGE_DATEADDED_CONSTRAINTS =
            "Dates should be in the format YYYY-MM-DD, and it should not be blank";

    /*
     * The first character must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     * Format of string is YYYY-MM-DD.
     */
    public static final String DATEADDED_VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])";

    public final LocalDate date;

    /**
     * Constructs an {@code DateAdded}.
     *
     * @param dateadded A valid date.
     */
    public DateAdded(String dateadded) {
        requireNonNull(dateadded);
        checkArgument(isValidDateAdded(dateadded), MESSAGE_DATEADDED_CONSTRAINTS);
        //temporary fix for xml file bug due to PrioriTask's dependence on the current date
        if (dateadded.equals("current_date")) {
            this.date = LocalDate.now();
        } else {
            //actual code that is run when tests are not running
            this.date = LocalDate.parse(dateadded);
        }
    }

    /**
     * Constructs a DateAdded based on the current date
     */
    public DateAdded() {
        LocalDate currentDate = LocalDate.now();
        requireNonNull(currentDate);
        this.date = currentDate;
    }

    /**
     * Returns true if a given string is a valid task deadline.
     */
    public static boolean isValidDateAdded(String test) {
        return test.matches("current_date") || test.matches(DATEADDED_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.date.equals(((Deadline) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
```
###### /java/seedu/organizer/model/task/Priority.java
``` java
/**
 * Represents a Task's priority level in the organizer.
 * Lowest Settable Priority : 0
 * Highest Settable Priority : 9
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority numbers can only be 0 to 9, 0 being the lowest priority, and 9 being the highest priority";
    public static final String PRIORITY_VALIDATION_REGEX = "\\d{1}";
    public static final String LOWEST_PRIORITY_LEVEL = "0";
    public static final String HIGHEST_SETTABLE_PRIORITY_LEVEL = "9";
    public final String value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority level.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_PRIORITY_CONSTRAINTS);
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid task priority number.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/organizer/model/task/Task.java
``` java
    /**
     * @return a Task comparator based on priority
     */
    public static Comparator<Task> priorityComparator() {
        return new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return (task2.getPriority().value)
                        .compareTo(task1.getPriority().value);
            }
        };
    }
}
```
###### /java/seedu/organizer/model/task/TaskByUserPredicate.java
``` java
/**
 * Tests that a {@code Task}'s {@code User} matches the given user.
 */
public class TaskByUserPredicate implements Predicate<Task> {

    private final User user;

    public TaskByUserPredicate(User user) {
        this.user = user;
    }

    @Override
    public boolean test(Task task) {
        return task.getUser().equals(user);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskByUserPredicate // instanceof handles nulls
                && this.user.equals(((TaskByUserPredicate) other).user)); // state check
    }
}
```
###### /java/seedu/organizer/model/task/UniqueTaskList.java
``` java
    /**
     * Adds a task to the list.
     * Updates priority level if task is not completed
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        toAdd = updatePriority(toAdd);
        internalList.add(toAdd);
        sortTasks();
    }
```
###### /java/seedu/organizer/model/task/UniqueTaskList.java
``` java
    /**
     * Deletes all tasks by {@code user} from internalList
     */
    public void deleteUserTasks(User user) {
        requireNonNull(user);
        FilteredList<Task> tasksToDelete = new FilteredList<>(internalList);
        tasksToDelete.setPredicate(new TaskByUserPredicate(user));
        internalList.removeAll(tasksToDelete);
    }
```
###### /java/seedu/organizer/model/task/UniqueTaskList.java
``` java
    /**
     * Returns a list of tasks by a user as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Task> userTasksAsObservableList(User user) {
        FilteredList<Task> filteredList = new FilteredList<>(internalList);
        filteredList.setPredicate(new TaskByUserPredicate(user));
        return FXCollections.unmodifiableObservableList(filteredList);
    }
```
###### /java/seedu/organizer/model/task/UniqueTaskList.java
``` java
    /**
     * Sorts all tasks in uniqueTaskList according to priority
     */
    private void sortTasks() {
        internalList.sort(Task.priorityComparator());
    }

    /**
     * Updates task with updated priority level with respect to deadline
     * Priority level remains the same if task has just been created
     * Priority level is at maximum if current date is the deadline
     */
    public Task updatePriority(Task task) {
        Task newTask;
        Priority newPriority;
        LocalDate currentDate = LocalDate.now();
        LocalDate dateAdded = task.getDateAdded().date;
        LocalDate deadline = task.getDeadline().date;
        Priority curPriority = task.getPriority();

        int priorityDifferenceFromMax = Integer.parseInt(Priority.HIGHEST_SETTABLE_PRIORITY_LEVEL)
                                        - Integer.parseInt(curPriority.value);
        long dayDifferenceCurrentToDeadline = Duration.between(currentDate.atStartOfDay(),
                                                            deadline.atStartOfDay()).toDays();
        long dayDifferenceAddedToDeadline = Duration.between(dateAdded.atStartOfDay(),
                                                            deadline.atStartOfDay()).toDays();

        if (dateAdded.isEqual(LocalDate.now())) {
            newTask = new Task(task.getName(), task.getPriority(), task.getDeadline(), task.getDateAdded(),
                    task.getDateCompleted(), task.getDescription(), task.getStatus(), task.getTags(),
                    task.getSubtasks(), task.getUser());
        } else if (currentDate.isBefore(deadline)) {
            newPriority = calculateNewPriority(curPriority,
                    priorityDifferenceFromMax, dayDifferenceCurrentToDeadline, dayDifferenceAddedToDeadline);
            newTask = new Task(task.getName(), newPriority, task.getDeadline(), task.getDateAdded(),
                    task.getDateCompleted(), task.getDescription(), task.getStatus(), task.getTags(),
                    task.getSubtasks(), task.getUser());
        } else {
            newPriority = new Priority(Priority.HIGHEST_SETTABLE_PRIORITY_LEVEL);
            newTask = new Task(task.getName(), newPriority, task.getDeadline(), task.getDateAdded(),
                    task.getDateCompleted(), task.getDescription(), task.getStatus(), task.getTags(),
                    task.getSubtasks(), task.getUser());
        }

        requireNonNull(newTask);
        return newTask;
    }

    /**
     * Calculate a new priority level for updatePriority method
     */
    private Priority calculateNewPriority(Priority curPriority, int priorityDifferenceFromMax,
                                          long dayDifferenceCurrentToDeadline, long dayDifferenceAddedToDeadline) {
        requireAllNonNull(curPriority, priorityDifferenceFromMax,
                dayDifferenceCurrentToDeadline, dayDifferenceAddedToDeadline);
        Priority newPriority;
        int priorityToIncrease = (int) (priorityDifferenceFromMax
                * ((double) (dayDifferenceAddedToDeadline - dayDifferenceCurrentToDeadline)
                / (double) dayDifferenceAddedToDeadline));
        newPriority = new Priority(String.valueOf(Integer.parseInt(curPriority.value) + priorityToIncrease));
        return newPriority;
    }

```
###### /java/seedu/organizer/model/user/exceptions/CurrentlyLoggedInException.java
``` java
/**
 * Signals that a user is currently logged in
 */
public class CurrentlyLoggedInException extends Exception {
    public CurrentlyLoggedInException() {
        super("A user is currently logged in");
    }
}
```
###### /java/seedu/organizer/model/user/exceptions/DuplicateUserException.java
``` java
/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateUserException extends DuplicateDataException {
    public DuplicateUserException() {
        super("Operation would result in duplicate users");
    }
}
```
###### /java/seedu/organizer/model/user/exceptions/UserNotFoundException.java
``` java
/**
 * Signals that an operation could not find the user
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User could not be found");
    }
}
```
###### /java/seedu/organizer/model/user/UniqueUserList.java
``` java
/**
 * A list of users that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see User#equals(Object)
 */
public class UniqueUserList implements Iterable<User> {

    private final ObservableList<User> internalList = FXCollections.observableArrayList();
    private User currentLoggedInUser = null;

    /**
     * Constructs empty UserList.
     */
    public UniqueUserList() {}

    /**
     * Creates a UniqueUserList using given users.
     * Enforces no nulls.
     */
    public UniqueUserList(Set<User> users) {
        requireAllNonNull(users);
        internalList.addAll(users);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Sets currentLoggedInUser to user
     */
    public void setCurrentLoggedInUser(User userToLogIn) throws UserNotFoundException, CurrentlyLoggedInException {
        requireNonNull(userToLogIn);
        if (currentLoggedInUser != null) {
            throw new CurrentlyLoggedInException();
        }
        if (!internalList.contains(userToLogIn)) {
            throw new UserNotFoundException();
        }
        this.currentLoggedInUser = userToLogIn;
    }

    public void setCurrentLoggedInUserToNull() {
        currentLoggedInUser = null;
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    /**
     * Returns all users in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<User> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Users in this list with those in the argument user list.
     */
    public void setUsers(List<User> users) {
        requireAllNonNull(users);
        internalList.setAll(users);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every user in the argument list exists in this object.
     */
    public void mergeFrom(UniqueUserList from) {
        final Set<User> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(user -> !alreadyInside.contains(user))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);

        currentLoggedInUser = from.currentLoggedInUser;
    }

    /**
     * Returns true if the list contains an equivalent User as the given argument.
     */
    public boolean contains(User toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a User to the list.
     *
     * @throws DuplicateUserException if the User to add is a duplicate of an existing User in the list.
     */
    public void add(User toAdd) throws DuplicateUserException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUserException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces a user with another user in internalList
     */
    public void updateUserToUserWithQuestionAnswer(
            User toRemove, UserWithQuestionAnswer toAdd) throws UserNotFoundException {

        requireAllNonNull(toRemove, toAdd);
        if (!internalList.contains(toRemove)) {
            throw new UserNotFoundException();
        }
        internalList.remove(toRemove);
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        requireNonNull(username);
        User userWithUsername = null;
        for (User u : internalList) {
            if (u.username.equals(username)) {
                userWithUsername = u;
            }
        }
        if (userWithUsername == null) {
            throw new UserNotFoundException();
        }
        return userWithUsername;
    }

    @Override
    public Iterator<User> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<User> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueUserList // instanceof handles nulls
                && this.internalList.equals(((UniqueUserList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueUserList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}
```
###### /java/seedu/organizer/model/user/User.java
``` java
/**
 * Represents a User in the organizer.
 * Guarantees: immutable;
 * username is valid as declared in {@link #isValidUsername(String)}
 * password is valid as declared in {@link #isValidPassword(String)}
 */
public class User {

    public static final String MESSAGE_USER_CONSTRAINTS = "Username and password should be alphanumeric"
                                                            + " and must not contain spaces";
    public static final String USERNAME_VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String PASSWORD_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String username;
    public final String password;

    /**
     * Constructs a {@code User}.
     *
     * @param username A valid username.
     * @param password A valid password.
     */
    public User(String username, String password) {
        requireNonNull(username, password);
        checkArgument(isValidUsername(username), MESSAGE_USER_CONSTRAINTS);
        checkArgument(isValidPassword(password), MESSAGE_USER_CONSTRAINTS);
        this.username = username;
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof User // instanceof handles nulls
                && this.username.equals(((User) other).username)); // state check
    }

    @Override
    public int hashCode() {
        return String.join(username, password).hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return username;
    }

}
```
###### /java/seedu/organizer/model/user/UserWithQuestionAnswer.java
``` java
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
```
###### /java/seedu/organizer/storage/XmlAdaptedUser.java
``` java
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
```
###### /java/seedu/organizer/storage/XmlSerializableOrganizer.java
``` java
        users.addAll(src.getUserList().stream().map(user -> {
            if (user instanceof UserWithQuestionAnswer) {
                return new XmlAdaptedUser((UserWithQuestionAnswer) user);
            } else {
                return new XmlAdaptedUser(user);
            }
        }).collect(Collectors.toList()));
```
