# software-1234
###### /java/seedu/address/logic/commands/CurrencyCommand.java
``` java
package seedu.address.logic.commands;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONException;

import com.ritaja.xchangerate.api.CurrencyConverter;
import com.ritaja.xchangerate.api.CurrencyConverterBuilder;
import com.ritaja.xchangerate.api.CurrencyNotSupportedException;
import com.ritaja.xchangerate.endpoint.EndpointException;
import com.ritaja.xchangerate.service.ServiceException;
import com.ritaja.xchangerate.storage.StorageException;
import com.ritaja.xchangerate.util.Currency;
import com.ritaja.xchangerate.util.Strategy;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class CurrencyCommand extends Command {

    public static final String COMMAND_WORD = "convert";
    public static final String COMMAND_SHORTCUT = "cv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Converts the balance of the person identified by the index number into a new "
            + "currency chosen by the user. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[Current Currency Symbol]  "
            + "[New Currency Symbol]\n"
            + "Example: " + COMMAND_WORD + " 1" + " SGD" + " USD";
    public static final String MESSAGE_CURRENCY_NOT_SUPPORTED = "The currency you have provided is not supported.";
    public static final String MESSAGE_SUCCESS = "Here is your balance in the new currency";
    private String fromCurrency;
    private String toCurrency;
    private Index index;
    private Person convertedPerson;
    private Double convertedPersonBalance = 0.0;
    private BigDecimal newAmount;

    private CurrencyConverter converter = new CurrencyConverterBuilder()
            .strategy(Strategy.YAHOO_FINANCE_FILESTORE)
            .buildConverter();

    public CurrencyCommand(Index index, String fromCurrency, String toCurrency) {
        this.index = index;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        converter.setRefreshRateSeconds(86400);

        if (index.getZeroBased() > lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (index.getZeroBased() == 0) {
            lastShownList = model.getFilteredPersonList();
            for (Person person : lastShownList) {
                double currentPersonBalance = person.getMoney().balance;
                convertedPersonBalance = convertedPersonBalance + currentPersonBalance;
            }
        } else {
            convertedPerson = lastShownList.get(index.getZeroBased() - 1);
            convertedPersonBalance = convertedPerson.getMoney().balance;
        }

        try {
            newAmount = converter.convertCurrency(new BigDecimal(convertedPersonBalance),
            Currency.get(fromCurrency), Currency.get(toCurrency));
        } catch (CurrencyNotSupportedException cnse) {
            throw new CommandException("Currency not supported");
        } catch (JSONException jsone) {
            throw new AssertionError("JSON Exception");
        } catch (StorageException se) {
            throw new AssertionError("Storage Exception");
        } catch (EndpointException ee) {
            throw new AssertionError("Endpoint Exception");
        } catch (ServiceException se) {
            throw new AssertionError("Service Exception");
        } catch (NullPointerException npe) {
            throw new CommandException("Invalid currency");
        }

        if (index.getZeroBased() == 0) {
            return new CommandResult("Your total balance in " + toCurrency + " is: " + newAmount);
        } else {
            return new CommandResult(convertedPerson.getName() + "'s balance in " + toCurrency + " is: " + newAmount);
        }

    }
}
```
###### /java/seedu/address/logic/commands/ListPositiveBalanceCommand.java
``` java
package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Lists all persons with positive balances in the address book to the user.
 */
public class ListPositiveBalanceCommand extends Command {

    public static final String COMMAND_WORD = "lend";
    public static final String COMMAND_SHORTCUT = "le";

    public static final String MESSAGE_SUCCESS = "Listed all persons who owe you money";

    @Override
    public CommandResult execute() {

        model.updateFilteredPersonList(isPositiveBalance());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public Predicate<Person> isPositiveBalance() {
        return a -> a.getMoney().balance > 0;
    }
}
```
###### /java/seedu/address/logic/commands/WipeBalancesCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.money.Money;
import seedu.address.model.person.Person;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Handles the balance command.
 */
public class WipeBalancesCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "wipe";
    public static final String COMMAND_SHORTCUT = "w";

    public static final String MESSAGE_SUCCESS = "Wiped all balances";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Wipes all balances";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private Money cleared = new Money("0.0");
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Person oldPerson;
        try {
            for (Person p : model.getFilteredPersonList()) {
                oldPerson = p;
                p.setMoney(cleared);
                p.clearItems();
                model.updatePerson(oldPerson, p);
            }
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
```
###### /java/seedu/address/logic/parser/CurrencyCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import com.ritaja.xchangerate.util.Currency;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CurrencyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CurrencyCommandParser implements Parser<CurrencyCommand> {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    /**
     * Parses the given {@code String} of arguments in the context of the CurrencyCommand
     * and returns an CurrencyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CurrencyCommand parse(String args) throws ParseException {

        String fromCurrency;
        String toCurrency;
        Index currencyIndex;
        try {
            String trimmedArgs = args.trim();
            String[] currencyKeywords = trimmedArgs.split(" ");
            if (Integer.parseInt(currencyKeywords[0]) == 0) {
                currencyIndex = Index.fromZeroBased(0);
            } else {
                currencyIndex = Index.fromZeroBased(Integer.parseInt(currencyKeywords[0]));
            }

            if (currencyIndex.getZeroBased() < 0) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
            fromCurrency = currencyKeywords[1].toUpperCase();
            toCurrency = currencyKeywords[2].toUpperCase();

```
###### /java/seedu/address/model/money/Money.java
``` java
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
