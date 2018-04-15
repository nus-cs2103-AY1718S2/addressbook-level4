# chantiongley
###### /java/seedu/address/logic/parser/DeleteAccountCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAccountCommand object
 */
public class DeleteAccountCommandParser implements Parser<DeleteAccountCommand> {
    /**
     * Parses input arguments and creates a new DeleteAccountCommand object
     * <p>
     * /**
     * Parses the given {@code String} of arguments in the context of the DeleteAccountCommand
     * and returns an DeleteAccountCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public DeleteAccountCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAccountCommand.MESSAGE_USAGE));
        } else {
            return new DeleteAccountCommand(trimmedArgs);
        }
    }
}
```
###### /java/seedu/address/logic/parser/AddAccountCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIVILEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Credential;
import seedu.address.model.account.MatricNumber;
import seedu.address.model.account.Name;
import seedu.address.model.account.Password;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.Username;
import seedu.address.model.account.exceptions.AccountNotFoundException;

/**
 * Parses input arguments and creates a new AddAccountCommand object
 */
public class AddAccountCommandParser implements Parser<AddAccountCommand> {
    /**
     * Returns true if none of the prefixes contains empty(@code Optional) value in the given
     * (@code ArgumentMultimap).
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch((prefix -> argumentMultimap.getValue(prefix).isPresent()));
    }

    /**
     * +     * Parses the given (@code String) of arguments in the context of the AddAccountCommand
     * +     * and returns an AddAccountCommand object for execution.
     * +     *
     * +     * @throws ParseException if the user input does not conform to the expected format
     * +
     */
    @Override
    public AddAccountCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_MATRICNUMBER,
                PREFIX_PRIVILEGE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_MATRICNUMBER,
            PREFIX_PRIVILEGE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAccountCommand.MESSAGE_USAGE));
        }
        try {
            Name name = ParserUtil.parseAccountName(argMultimap.getValue(PREFIX_NAME).get());
            Username username = ParserUtil.parseAccountUsername(argMultimap.getValue(PREFIX_USERNAME).get());
            Password password = ParserUtil.parseAccountPassword(argMultimap.getValue(PREFIX_PASSWORD).get());
            Credential credential = new Credential(username.getUsername(), password.getPassword());
            MatricNumber matricNumber = ParserUtil.parseAccountMatricNumber
                (argMultimap.getValue(PREFIX_MATRICNUMBER).get());
            PrivilegeLevel privilegeLevel = ParserUtil.parseAccountPrivilegeLevel
                (argMultimap.getValue(PREFIX_PRIVILEGE).get());

            Account account = new Account(name, credential, matricNumber, privilegeLevel);

            return new AddAccountCommand(account);
        } catch (AccountNotFoundException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws AccountNotFoundException if the given {@code name} is invalid.
     */
    public static Name parseAccountName(String name) throws AccountNotFoundException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new AccountNotFoundException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseAccountName(Optional<String> name) throws AccountNotFoundException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseAccountName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String matricNumber} into a {@code MatricNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws AccountNotFoundException if the given {@code matricNumber} is invalid.
     */
    public static MatricNumber parseAccountMatricNumber(String matricNumber) throws AccountNotFoundException {
        requireNonNull(matricNumber);
        String trimmedMatricNumber = matricNumber.trim();
        if (!MatricNumber.isValidMatricNumber(trimmedMatricNumber)) {
            throw new AccountNotFoundException(MatricNumber.MESSAGE_MATRIC_NUMBER_CONSTRAINTS);
        }
        return new MatricNumber(trimmedMatricNumber);
    }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<MatricNumber> parseAccountMatricNumber(Optional<String> matricNumber)
        throws AccountNotFoundException {
        requireNonNull(matricNumber);
        return matricNumber.isPresent() ? Optional.of(parseAccountMatricNumber(matricNumber.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String username} into a {@code Username}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws AccountNotFoundException if the given {@code username} is invalid.
     */
    public static Username parseAccountUsername(String username) throws AccountNotFoundException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new AccountNotFoundException (Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<Username>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Username> parseAccountUsername(Optional<String> username) throws AccountNotFoundException {
        requireNonNull(username);
        return username.isPresent() ? Optional.of(parseAccountUsername(username.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String password} into a {@code Password}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws AccountNotFoundException if the given {@code password} is invalid.
     */
    public static Password parseAccountPassword(String password) throws AccountNotFoundException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new AccountNotFoundException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(trimmedPassword);
    }

    /**
     * Parses a {@code Optional<String> password} into an {@code Optional<Password>} if {@code password} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Password> parseAccountPassword(Optional<String> password) throws AccountNotFoundException {
        requireNonNull(password);
        return password.isPresent() ? Optional.of(parseAccountPassword(password.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String privilegeLevel} into a {@code PrivilegeLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws AccountNotFoundException if the given {@code privilegeLevel} is invalid.
     */
    public static PrivilegeLevel parseAccountPrivilegeLevel(String privilegeLevel) throws AccountNotFoundException {
        requireNonNull(privilegeLevel);
        int input = Integer.parseInt(privilegeLevel);

        if (!PrivilegeLevel.isValidPrivilegeLevel(input)) {
            throw new AccountNotFoundException(PrivilegeLevel.MESSAGE_PRIVILEGE_LEVEL_CONSTRAINTS);
        }
        return new PrivilegeLevel(input);
    }

    //============================== Book Level Parse Commands ==============================

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
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Title> parseTitle(Optional<String> title) throws IllegalValueException {
        requireNonNull(title);
        return title.isPresent() ? Optional.of(parseTitle(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String isbn} into a {@code Isbn}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code isbn} is invalid.
     */
    public static Isbn parseIsbn(String isbn) throws IllegalValueException {
        requireNonNull(isbn);
        String trimmedIsbn = isbn.trim();
        if (!Isbn.isValidIsbn(trimmedIsbn)) {
            throw new IllegalValueException(Isbn.MESSAGE_ISBN_CONSTRAINTS);
        }
        return new Isbn(trimmedIsbn);
    }

    /**
     * Parses a {@code Optional<String> isbn} into an {@code Optional<Isbn>} if {@code isbn} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Isbn> parseIsbn(Optional<String> isbn) throws IllegalValueException {
        requireNonNull(isbn);
        return isbn.isPresent() ? Optional.of(parseIsbn(isbn.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String address} into an {@code Author}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Author parseAuthor(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAuthor = address.trim();
        if (!Author.isValidAuthor(trimmedAuthor)) {
            throw new IllegalValueException(Author.MESSAGE_AUTHOR_CONSTRAINTS);
        }
        return new Author(trimmedAuthor);
    }

    /**
     * Parses a {@code Optional<String> author} into an {@code Optional<Author>} if {@code author} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Author> parseAuthor(Optional<String> author) throws IllegalValueException {
        requireNonNull(author);
        return author.isPresent() ? Optional.of(parseAuthor(author.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String avail} into an {@code Avail}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code avail} is invalid.
     */
    public static Avail parseAvail(String avail) throws IllegalValueException {
        requireNonNull(avail);
        String trimmedAvail = avail.trim();
        if (!Avail.isValidAvail(trimmedAvail)) {
            throw new IllegalValueException(Avail.MESSAGE_AVAIL_CONSTRAINTS);
        }
        return new Avail(trimmedAvail);
    }

    /**
     * Parses a {@code Optional<String> avail} into an {@code Optional<Avail>} if {@code avail} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Avail> parseAvail(Optional<String> avail) throws IllegalValueException {
        requireNonNull(avail);
        return avail.isPresent() ? Optional.of(parseAvail(avail.get())) : Optional.empty();
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
```
###### /java/seedu/address/logic/commands/AddAccountCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRICNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIVILEGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 * Adds an account
 */
public class AddAccountCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addAccount";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an account to the system. "
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_USERNAME + "USERNAME "
        + PREFIX_PASSWORD + "PASSWORD "
        + PREFIX_MATRICNUMBER + "MATRICULATION NUMBER "
        + PREFIX_PRIVILEGE + "PRIVILEGE LEVEL \n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_USERNAME + "johndoe "
        + PREFIX_PASSWORD + "johndoe123 "
        + PREFIX_MATRICNUMBER + "A0123456B "
        + PREFIX_PRIVILEGE + "1";

    public static final String MESSAGE_SUCCESS = "New account added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "This account already exists in the system";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private final Account toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Book}
     */
    public AddAccountCommand(Account account) {
        requireNonNull(account);
        toAdd = account;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addAccount(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAccountException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOUNT);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }

}
```
###### /java/seedu/address/logic/commands/DeleteAccountCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.Username;
import seedu.address.model.account.exceptions.AccountNotFoundException;

/**
 * Deletes a account using it's last displayed index from the account list
 */
public class DeleteAccountCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteAccount";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the account identified by the name of the user stored in account list.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " John";
    public static final String MESSAGE_DELETE_ACCOUNT_SUCCESS = "Deleted Account: %1$s";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private String username;
    private Account accountToDelete;

    public DeleteAccountCommand(String username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.deleteAccount(accountToDelete);
        } catch (AccountNotFoundException anfe) {
            throw new CommandException("Account does not exist");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_ACCOUNT_SUCCESS, accountToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        accountToDelete = model.getAccountList().searchByUsername(new Username(username));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteAccountCommand // instanceof handles nulls
            && accountToDelete.equals(((DeleteAccountCommand) other).accountToDelete));
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}

```
