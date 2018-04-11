# software-1234
###### /java/seedu/address/logic/commands/ListNegativeBalanceCommand.java
``` java
package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Lists all persons with negative balances in the address book to the user.
 */
public class ListNegativeBalanceCommand extends Command {

    public static final String COMMAND_WORD = "debt";
    public static final String COMMAND_SHORTCUT = "de";

    public static final String MESSAGE_SUCCESS = "Listed all persons who you owe money";

    @Override
    public CommandResult execute() {

        model.updateFilteredPersonList(isNegativeBalance());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    public Predicate<Person> isNegativeBalance() {
        return p -> p.getMoney().balance < 0;
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
        return a -> a.getMoney().balance >= 0;
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
public class WipeBalancesCommand extends Command {

    public static final String COMMAND_WORD = "wipe";
    public static final String COMMAND_SHORTCUT = "w";

    public static final String MESSAGE_SUCCESS = "Wiped all balances";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Wipes all balances";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private Money cleared = new Money("0.0");
    @Override
    public CommandResult execute() throws CommandException {
        Person oldPerson;
        try {
            for (Person p : model.getFilteredPersonList()) {
                oldPerson = p;
                p.setMoney(cleared);
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

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
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

        try {
            String trimmedArgs = args.trim();
            String[] currencyKeywords = trimmedArgs.split(" ");
            Index currencyIndex = Index.fromOneBased(Integer.parseInt(currencyKeywords[0]));

            if (!StringUtil.isNonZeroUnsignedInteger(currencyKeywords[0])) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }

            fromCurrency = currencyKeywords[1].toUpperCase();
            toCurrency = currencyKeywords[2].toUpperCase();
            return new CurrencyCommand(currencyIndex, fromCurrency, toCurrency);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CurrencyCommand.MESSAGE_USAGE));
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CurrencyCommand.MESSAGE_USAGE));
        }

    }

}


```
