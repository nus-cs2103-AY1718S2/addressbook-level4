# pkuhanan
###### /resources/view/PersonListCard.fxml
``` fxml
      <Label fx:id="money" styleClass="cell_small_label" text="\$money" />
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    @FXML
    private Label money;
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case MaxCommand.COMMAND_WORD:
            return new MaxCommand();

        case MaxCommand.COMMAND_SHORTCUT:
            return new MaxCommand();
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case SettleCommand.COMMAND_WORD:
            return new SettleCommandParser().parse(arguments);

        case SettleCommand.COMMAND_SHORTCUT:
            return new SettleCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case RemindCommand.COMMAND_WORD:
            return new RemindCommandParser().parse(arguments);
        case RemindCommand.COMMAND_SHORTCUT:
            return new RemindCommandParser().parse(arguments);

        case TransactionCommand.COMMAND_WORD:
            return new TransactionCommandParser().parse(arguments);

        case TransactionCommand.COMMAND_SHORTCUT:
            return new TransactionCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String money} into an {@code Money}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code money} is invalid.
     */
    public static Money parseMoney(String money) throws IllegalValueException {
        requireNonNull(money);
        String trimmedMoney = money.trim();
        if (!Money.isValidMoney(trimmedMoney)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Money(trimmedMoney);
    }

    /**
     * Parses a {@code Optional<String> money} into an {@code Optional<money>} if {@code money} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Money> parseMoney(Optional<String> money) throws IllegalValueException {
        requireNonNull(money);
        return money.isPresent() ? Optional.of(parseMoney(money.get())) : Optional.empty();
    }
```
###### /java/seedu/address/logic/parser/TransactionCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.money.Money.MONEY_VALIDATION_REGEX;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.TransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TransactionCommand object
 */
public class TransactionCommandParser {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    /**
     * Parses the given {@code String} of arguments in the context of the TransactionCommand
     * and returns a TransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TransactionCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] currencyKeywords = trimmedArgs.split(" ");

            Index index = Index.fromOneBased(Integer.parseInt(currencyKeywords[0]));
            String amount = currencyKeywords[1].toUpperCase();

            if (!StringUtil.isNonZeroUnsignedInteger(currencyKeywords[0]) || !amount.matches(MONEY_VALIDATION_REGEX)) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }

            return new TransactionCommand(index, Double.parseDouble(amount));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TransactionCommand.MESSAGE_USAGE));
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TransactionCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/RemindCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemindCommand;
import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemindCommand object
 */
public class RemindCommandParser implements Parser<RemindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SettleCommand
     * and returns a SettleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemindCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemindCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/commands/TransactionCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.money.Money;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Updates the balance according to the reported transaction
 */
public class TransactionCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "transaction";
    public static final String COMMAND_SHORTCUT = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the balance according to the transaction. "
            + "Positive means money received from the contact and negative means money paid to the contact "
            + "Parameters: INDEX (must be a positive integer) TRANSACTION_AMOUNT (must be an double) ";

    public static final String MESSAGE_TRANSACTION_PERSON_SUCCESS = "Balance updated for: ";

    private final Index index;
    private final Double amount;

    private Person personToEdit;
    private Person editedPerson;
    private String newBalance;

    /**
     * @param index of the person in the filtered person list to settle
     * @param amount of money paid in the transaction
     */
    public TransactionCommand(Index index, Double amount) {
        requireNonNull(index);
        requireNonNull(amount);

        this.index = index;
        this.amount = amount;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target person cannot be duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(MESSAGE_TRANSACTION_PERSON_SUCCESS + editedPerson.getName());
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        newBalance = Double.toString(personToEdit.getMoney().balance - amount);
        editedPerson = getPersonAfterTransaction(personToEdit, newBalance);
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * but with a 0 balance
     */
    private static Person getPersonAfterTransaction(Person personToEdit, String newBalance) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Money money = new Money(newBalance);
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, address, money, tags);
    }
}
```
###### /java/seedu/address/logic/commands/MaxCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;

import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Person;

/**
 * Finds the person that owes the most money
 */
public class MaxCommand extends Command {
    public static final String COMMAND_WORD = "max";
    public static final String COMMAND_SHORTCUT = "mx";
    public static final String MESSAGE_SUCCESS = "The contact who owes you the most money is: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person that owes the most money ";

    @Override
    public CommandResult execute() {
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = Index.fromZeroBased(0);
        Double highestDebt = 0.0;

        for (int i = 0; i < lastShownList.size(); i++) {
            Person person = lastShownList.get(i);
            if (person.getMoney().balance > highestDebt) {
                index = Index.fromZeroBased(i);
                highestDebt = person.getMoney().balance;
            }
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(MESSAGE_SUCCESS + lastShownList.get(index.getZeroBased()).getName());
    }
}
```
###### /java/seedu/address/logic/commands/RemindCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;


/**
 * Helps the user send a reminder by email to the contact
 */
public class RemindCommand extends Command {
    public static final String COMMAND_WORD = "remind";
    public static final String COMMAND_SHORTCUT = "rm";
    public static final String MESSAGE_SUCCESS = "Generated email for contact: ";
    public static final String MESSAGE_FAILURE = " does not owe you any money";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person that owes the most money ";

    private final Index index;
    private Person person;

    /**
     * @param index of the person in the filtered person list to remind
     */
    public RemindCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        person = lastShownList.get(index.getZeroBased());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));

        if (person.getMoney().balance > 0) {
            try {
                Desktop desktop;
                if (Desktop.isDesktopSupported()
                        && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                    String uri = "mailto:" + person.getEmail()
                            + "?subject=Reminder:%20Please%20pay%20me%20back&body=Please%20pay%20me%20back:%20$"
                            + person.getMoney();
                    URI mailto = new URI(uri);
                    desktop.mail(mailto);
                } else {
                    throw new AssertionError("No email client configured");
                }
                return new CommandResult(MESSAGE_SUCCESS + person.getName() + "<" + person.getEmail() + ">");
            } catch (Exception e) {
                throw new AssertionError("Problem sending email");
            }
        } else {
            return new CommandResult(person.getName() + MESSAGE_FAILURE);
        }
    }
}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setMoney(Money money) {
            this.money = money;
        }

        public Optional<Money> getMoney() {
            return Optional.ofNullable(money);
        }
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
    @XmlElement
    private String balance;
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        if (!Money.isValidMoney(this.balance)) {
            throw new IllegalValueException(Money.MESSAGE_MONEY_CONSTRAINTS);
        }
        final Money balance = new Money(this.balance);
```
###### /java/seedu/address/model/person/Person.java
``` java
    public Money getMoney() {
        return money;
    }
```
###### /java/seedu/address/model/money/Money.java
``` java
package seedu.address.model.money;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Money Balance in the TravelBanker.
 * Guarantees: immutable; is valid as declared in {@link #isValidMoney(String)}
 */
public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS = "Money values should be numbers";
    public static final String MONEY_VALIDATION_REGEX = "-?\\d+(\\.\\d+)?(E-?\\d+)?";

    public final double balance;
    public final String value;

    /**
     * Constructs a {@code Money}.
     *
     * @param balance A valid money balance.
     */
    public Money(String balance) {
        requireNonNull(balance);
        checkArgument(isValidMoney(balance), MESSAGE_MONEY_CONSTRAINTS);
        this.balance = Double.parseDouble(balance);
        this.value = balance;
    }

    @Override
    public String toString() {
        return value;
    }

    public Double toDouble() {
        return balance;
    }

    /**
     * Returns true if a given string is a valid money balance.
     */
    public static boolean isValidMoney(String test) {
        return test.matches(MONEY_VALIDATION_REGEX);
    }

```
###### /java/seedu/address/model/money/Money.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && this.value.equals(((Money) other).value)); // state check
    }
```
