# shadow2496
###### \java\seedu\address\commons\events\ui\NewResultAvailableEvent.java
``` java
    public final boolean hasError;

```
###### \java\seedu\address\commons\events\ui\NewResultAvailableEvent.java
``` java
        this.hasError = hasError;

```
###### \java\seedu\address\commons\events\ui\ShowLoginDialogRequestEvent.java
``` java
/**
 * Indicates a request to view the login dialog.
 */
public class ShowLoginDialogRequestEvent extends BaseEvent {

    public final String loadUrl;

    public ShowLoginDialogRequestEvent(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
/**
 * Logs into a social media platform using the user's account information.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs into a social media platform using "
            + "the user's name and password. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "johndoe "
            + PREFIX_PASSWORD + "jd9876";

    public static final String MESSAGE_SUCCESS = "Logged in account: %1$s";

    private final Account accountToLogin;

    public LoginCommand(Account account) {
        requireNonNull(account);
        accountToLogin = account;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.loginAccount(accountToLogin);
        return new CommandResult(String.format(MESSAGE_SUCCESS, accountToLogin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && this.accountToLogin.equals(((LoginCommand) other).accountToLogin)); // state check
    }
}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Passes the verification code for an access token */
    void passVerificationCode(String code);

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public void passVerificationCode(String code) {
        model.setVerificationCode(code);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

```
###### \java\seedu\address\logic\parser\LoginCommandParser.java
``` java
/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        try {
            Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();

            Account account = new Account(username, password);

            return new LoginCommand(account);
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
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String username} into an {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<Username>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Username> parseUsername(Optional<String> username) throws IllegalValueException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String password} into an {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws IllegalValueException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code Optional<String> password} into an {@code Optional<Password>} if {@code password} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Password> parsePassword(Optional<String> password) throws IllegalValueException {
        requireNonNull(password);
        return password.isPresent() ? Optional.of(parsePassword(password.get())) : Optional.empty();
    }

```
###### \java\seedu\address\model\account\Account.java
``` java
/**
 * Represents an Account in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Account {

    private static final String APP_ID = "366902457122089";
    private static final String APP_SECRET = "9cdbed37c0780e75e995381f0688a8d7";
    private static final String REDIRECT_URL = "https://www.facebook.com/connect/login_success.html";

    private final Username username;
    private final Password password;

    private ScopeBuilder scopeBuilder;
    private FacebookClient client;
    private String code;

    /**
     * Every field must be present and not null.
     */
    public Account(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;

        setScopeBuilder();
        setClient();
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    /**
     * Sets the scope builder which is a set of permissions.
     */
    private void setScopeBuilder() {
        scopeBuilder = new ScopeBuilder();
    }

    /**
     * Sets the client used to get an access token.
     */
    private void setClient() {
        client = new DefaultFacebookClient(Version.VERSION_2_12);
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Returns the login dialog url using {@link #client}.
     */
    public String getLoginDialogUrl() {
        return client.getLoginDialogUrl(APP_ID, REDIRECT_URL, scopeBuilder);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        return otherAccount.getUsername().equals(this.getUsername())
                && otherAccount.getPassword().equals(this.getPassword());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Username: " + getUsername() + " Password: " + getPassword();
    }
}
```
###### \java\seedu\address\model\account\Password.java
``` java
/**
 * Represents an Account's password in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Account passwords can take any values, and it should not be blank";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PASSWORD_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Password}.
     *
     * @param password A valid password.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.value = password;
    }

    /**
     * Returns true if a given string is a valid account password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && this.value.equals(((Password) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\account\Username.java
``` java
/**
 * Represents an Account's username in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Account usernames can take any values, and it should not be blank";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERNAME_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        this.value = username;
    }

    /**
     * Returns true if a given string is a valid account username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && this.value.equals(((Username) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// account-level operations

    /**
     * Logs into a social media platform using {@code account}.
     */
    public void loginAccount(Account account) {
        this.account = account;
        String loadUrl = this.account.getLoginDialogUrl();
        EventsCenter.getInstance().post(new ShowLoginDialogRequestEvent(loadUrl));
    }

    public void setVerificationCode(String code) {
        account.setCode(code);
    }

```
###### \java\seedu\address\model\Model.java
``` java
    /** Logs in with the given account */
    void loginAccount(Account account);

    /** Sets the verification code in an account. */
    void setVerificationCode(String code);

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void loginAccount(Account account) {
        addressBook.loginAccount(account);
        //indicateAddressBookChanged();
    }

    @Override
    public void setVerificationCode(String code) {
        addressBook.setVerificationCode(code);
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Passes a verification code when the login is successful.
     */
    private void passVerificationCode() {
        facebookBrowser.getEngine().getLoadWorker().stateProperty().addListener((
                ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
            if (newValue != Worker.State.SUCCEEDED) {
                return;
            }

            String currentUrl = facebookBrowser.getEngine().getLocation();

            if (currentUrl.endsWith(DEFAULT_PAGE)) {
            } else if (currentUrl.startsWith(SUCCESS_URL)) {
                int pos = currentUrl.indexOf("code=");
                logic.passVerificationCode(currentUrl.substring(pos + "code=".length()));
            }
        });
    }

```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleShowLoginDialogRequestEvent(ShowLoginDialogRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadFacebookBrowserPage(event.loadUrl);
        passVerificationCode();
    }
}
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
        if (event.hasError) {
            setStyleToIndicateResultError();
        } else {
            setStyleToDefault();
        }

```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    /**
     * Sets the result display style to use the default style.
     */
    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the result display style to indicate that a result has an error.
     */
    private void setStyleToIndicateResultError() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }
}
```
