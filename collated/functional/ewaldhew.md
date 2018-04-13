# ewaldhew
###### \java\seedu\address\commons\events\model\CoinChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.coin.Coin;

/**
 * Indicates some coin data in the model has changed.
 */
public class CoinChangedEvent extends BaseEvent {

    public final Coin oldCoin;
    public final Coin newCoin;

    /** Pseudo-coin record that represents the change made.
     *  @see Coin#getChangeFrom(Coin)} */
    public final Coin delCoin;

    public CoinChangedEvent(Coin oldCoin, Coin newCoin) {
        this.oldCoin = oldCoin;
        this.newCoin = newCoin;
        this.delCoin = newCoin.getChangeFrom(oldCoin);
    }

    @Override
    public String toString() {
        return "coin changed ";
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowNotificationRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to spawn a pop-up notification with the given message.
 */
public class ShowNotificationRequestEvent extends BaseEvent {

    private final String message;

    public ShowNotificationRequestEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notifying about: " + message;
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowNotifManRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.rule.Rule;

/**
 * An event requesting to view the notification manager.
 */
public class ShowNotifManRequestEvent extends BaseEvent {

    public final ObservableList<Rule> data;

    public ShowNotifManRequestEvent(ObservableList<Rule> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Show notification manager: " + data.toString();
    }

}
```
###### \java\seedu\address\logic\commands\BuyCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Adds value to an existing coin in the book.
 */
public class BuyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "buy";
    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add value to the coin account identified "
            + "by the index number used in the last coin listing or its code. "
            + "Parameters: TARGET "
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_AMOUNT + "50.0";

    public static final String MESSAGE_BUY_COIN_SUCCESS = "Bought: %1$s";
    public static final String MESSAGE_NOT_BOUGHT = "Invalid code or amount entered.";

    private final CommandTarget target;
    private final Amount amountToAdd;

    private Coin coinToEdit;
    private Coin editedCoin;

    /**
     * @param target      in the filtered coin list to change
     * @param amountToAdd to the coin
     */
    public BuyCommand(CommandTarget target, Amount amountToAdd) {
        requireNonNull(target);

        this.target = target;
        this.amountToAdd = amountToAdd;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateCoin(coinToEdit, editedCoin);
        } catch (DuplicateCoinException dpe) {
            throw new CommandException("Unexpected code path!");
        } catch (CoinNotFoundException pnfe) {
            throw new AssertionError("The target coin cannot be missing");
        }
        model.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
        return new CommandResult(String.format(MESSAGE_BUY_COIN_SUCCESS, editedCoin));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Coin> lastShownList = model.getFilteredCoinList();

        try {
            Index index = target.toIndex(model.getFilteredCoinList());
            coinToEdit = lastShownList.get(index.getZeroBased());
            editedCoin = createEditedCoin(coinToEdit, amountToAdd);
        } catch (IndexOutOfBoundsException oobe) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_TARGET);
        }
    }

    /**
     * Creates and returns a {@code Coin} with the details of {@code coinToEdit}
     */
    private static Coin createEditedCoin(Coin coinToEdit, Amount amountToAdd) {
        assert coinToEdit != null;

        Coin editedCoin = new Coin(coinToEdit);
        editedCoin.addTotalAmountBought(amountToAdd);

        return editedCoin;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BuyCommand)) {
            return false;
        }

        // state check
        BuyCommand e = (BuyCommand) other;
        return target.equals(e.target)
                && amountToAdd.equals(e.amountToAdd)
                && Objects.equals(coinToEdit, e.coinToEdit);
    }
}
```
###### \java\seedu\address\logic\commands\CommandTarget.java
``` java
package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;

/**
 * Represents a command target specified in one of the available modes (union type).
 */
public class CommandTarget {

    /**
     * All possible target representation modes
     */
    private enum Mode {
        INDEX,
        CODE
    }

    private final Mode mode;

    private Index index;
    private Code code;

    public CommandTarget(Index index) {
        mode = Mode.INDEX;
        this.index = index;
    }

    public CommandTarget(Code code) {
        mode = Mode.CODE;
        this.code = code;
    }

    /**
     * @param coinList to obtain index information from
     * @return
     */
    public Index toIndex(ObservableList<Coin> coinList) throws IndexOutOfBoundsException {
        switch (mode) {

        case CODE:
            // Also throws IndexOutOfBoundsException if code isn't found.
            return Index.fromZeroBased(coinList.filtered(coin -> coin.getCode().equals(code)).getSourceIndex(0));
        case INDEX:
            if (index.getZeroBased() >= coinList.size()) {
                throw new IndexOutOfBoundsException();
            }
            return index;

        default:
            throw new RuntimeException("Unexpected code path!");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandTarget)) {
            return false;
        }

        // state check
        CommandTarget e = (CommandTarget) other;
        return mode.equals(e.mode)
                && ((mode == Mode.INDEX && index.equals(e.index))
                || (mode == Mode.CODE && code.equals(e.code)));
    }
}
```
###### \java\seedu\address\logic\commands\NotifyCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.exceptions.DuplicateRuleException;

/**
 * Adds a new notification with the specified conditions.
 */
public class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";
    public static final String COMMAND_ALIAS = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new notification to be triggered "
            + "upon the specified rule. Rules are provided in the following format:\n"
            + "Parameters: TARGET OPTION/VALUE [...] \n"
            + "Example: " + COMMAND_WORD + " BTC p/15000";

    public static final String MESSAGE_SUCCESS = "Added: %1$s";
    public static final String MESSAGE_DUPLICATE_RULE = "This notification rule already exists!";

    private final NotificationRule rule;

    public NotifyCommand(NotificationRule rule) {
        this.rule = rule;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addRule(rule);
            return new CommandResult(String.format(MESSAGE_SUCCESS, rule));
        } catch (DuplicateRuleException e) {
            throw new CommandException(MESSAGE_DUPLICATE_RULE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifyCommand) // instanceof handles nulls
                && this.rule.equals(((NotifyCommand) other).rule); // state check

    }
}
```
###### \java\seedu\address\logic\commands\SellCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Amount;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Removes value from an existing coin in the book.
 */
public class SellCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sell";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes value from the coin account identified "
            + "by the index number used in the last coin listing or its code. "
            + "Parameters: TARGET "
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_AMOUNT + "50.0";

    public static final String MESSAGE_SELL_COIN_SUCCESS = "Sold: %1$s";

    private final CommandTarget target;
    private final Amount amountToSell;

    private Coin coinToEdit;
    private Coin editedCoin;

    /**
     * @param target of the coin in the filtered coin list to change
     * @param amountToSell of the coin
     */
    public SellCommand(CommandTarget target, Amount amountToSell) {
        requireNonNull(target);

        this.target = target;
        this.amountToSell = amountToSell;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateCoin(coinToEdit, editedCoin);
        } catch (DuplicateCoinException dpe) {
            throw new CommandException("Unexpected code path!");
        } catch (CoinNotFoundException pnfe) {
            throw new AssertionError("The target coin cannot be missing");
        }
        model.updateFilteredCoinList(PREDICATE_SHOW_ALL_COINS);
        return new CommandResult(String.format(MESSAGE_SELL_COIN_SUCCESS, editedCoin));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Coin> lastShownList = model.getFilteredCoinList();

        try {
            Index index = target.toIndex(model.getFilteredCoinList());
            coinToEdit = lastShownList.get(index.getZeroBased());
            editedCoin = createEditedCoin(coinToEdit, amountToSell);
        } catch (IndexOutOfBoundsException oobe) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_TARGET);
        }
    }

    /**
     * Creates and returns a {@code Coin} with the details of {@code coinToEdit}
     */
    private static Coin createEditedCoin(Coin coinToEdit, Amount amountToSell) {
        assert coinToEdit != null;

        Coin editedCoin = new Coin(coinToEdit);
        editedCoin.addTotalAmountSold(amountToSell);

        return editedCoin;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SellCommand)) {
            return false;
        }

        // state check
        SellCommand e = (SellCommand) other;
        return target.equals(e.target)
                && amountToSell == e.amountToSell
                && Objects.equals(coinToEdit, e.coinToEdit);
    }
}
```
###### \java\seedu\address\logic\commands\SpawnNotificationCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowNotificationRequestEvent;

/**
 * Spawns a pop-up notification in the corner of the screen.
 */
public class SpawnNotificationCommand extends Command {

    private final String message;

    public SpawnNotificationCommand(String message) {
        this.message = message;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowNotificationRequestEvent(message));
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SpawnNotificationCommand // instanceof handles nulls
                && this.message.equals(((SpawnNotificationCommand) other).message)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\BuyCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coin.Amount;

/**
 * Parses input arguments and creates a new BuyCommand object
 */
public class BuyCommandParser implements Parser<BuyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BuyCommand
     * and returns an BuyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenizeToArgumentMultimap(args, PREFIX_AMOUNT);
        if (!argMultimap.arePrefixesPresent(PREFIX_AMOUNT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

        try {
            CommandTarget target = ParserUtil.parseTarget(argMultimap.getPreamble());
            Amount amountToAdd = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            return new BuyCommand(target, amountToAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

    }

}
```
###### \java\seedu\address\logic\parser\NotifyCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.logic.parser.TokenType.PREFIX_BOUGHT;
import static seedu.address.logic.parser.TokenType.PREFIX_BOUGHT_FALL;
import static seedu.address.logic.parser.TokenType.PREFIX_BOUGHT_RISE;
import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.PREFIX_HELD;
import static seedu.address.logic.parser.TokenType.PREFIX_HELD_FALL;
import static seedu.address.logic.parser.TokenType.PREFIX_HELD_RISE;
import static seedu.address.logic.parser.TokenType.PREFIX_MADE;
import static seedu.address.logic.parser.TokenType.PREFIX_MADE_FALL;
import static seedu.address.logic.parser.TokenType.PREFIX_MADE_RISE;
import static seedu.address.logic.parser.TokenType.PREFIX_NAME;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE_FALL;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE_RISE;
import static seedu.address.logic.parser.TokenType.PREFIX_SOLD;
import static seedu.address.logic.parser.TokenType.PREFIX_SOLD_FALL;
import static seedu.address.logic.parser.TokenType.PREFIX_SOLD_RISE;
import static seedu.address.logic.parser.TokenType.PREFIX_TAG;
import static seedu.address.logic.parser.TokenType.PREFIX_WORTH;
import static seedu.address.logic.parser.TokenType.PREFIX_WORTH_FALL;
import static seedu.address.logic.parser.TokenType.PREFIX_WORTH_RISE;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NotifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coin.Coin;
import seedu.address.model.rule.NotificationRule;

/**
 * Parses input arguments and creates a new NotifyCommand object
 */
public class NotifyCommandParser implements Parser<NotifyCommand> {

    private static final TokenType[] EXPECTED_TOKEN_TYPES = {
        PREFIX_AMOUNT,
        PREFIX_BOUGHT_RISE, PREFIX_BOUGHT_FALL, PREFIX_BOUGHT,
        PREFIX_CODE,
        PREFIX_HELD_RISE, PREFIX_HELD_FALL, PREFIX_HELD,
        PREFIX_MADE_RISE, PREFIX_MADE_FALL, PREFIX_MADE,
        PREFIX_NAME,
        PREFIX_PRICE_RISE, PREFIX_PRICE_FALL, PREFIX_PRICE,
        PREFIX_SOLD_RISE, PREFIX_SOLD_FALL, PREFIX_SOLD,
        PREFIX_TAG,
        PREFIX_WORTH_RISE, PREFIX_WORTH_FALL, PREFIX_WORTH
    };

    /**
     * Parses the given {@code String} of arguments in the context of the NotifyCommand
     * and returns an NotifyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NotifyCommand parse(String args) throws ParseException {
        try {
            NotificationRule notifRule = new NotificationRule(args);
            return new NotifyCommand(notifRule);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NotifyCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses a string representation of a notification condition
     * @see ParserUtil#parseCondition(TokenStack)
     */
    public static Predicate<Coin> parseNotifyCondition(String args)
            throws IllegalValueException {
        requireNonNull(args);
        return ParserUtil.parseCondition(ArgumentTokenizer.tokenizeToTokenStack(args, EXPECTED_TOKEN_TYPES));
    }

}
```
###### \java\seedu\address\logic\parser\SellCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coin.Amount;

/**
 * Parses input arguments and creates a new SellCommand object
 */
public class SellCommandParser implements Parser<SellCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BuyCommand
     * and returns an BuyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SellCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenizeToArgumentMultimap(args, PREFIX_AMOUNT);
        if (!argMultimap.arePrefixesPresent(PREFIX_AMOUNT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

        try {
            CommandTarget target = ParserUtil.parseTarget(argMultimap.getPreamble());
            Amount amountToSell = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            return new SellCommand(target, amountToSell);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

    }

}
```
###### \java\seedu\address\logic\RuleChecker.java
``` java
package seedu.address.logic;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CoinChangedEvent;
import seedu.address.commons.events.model.RuleBookChangedEvent;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;
import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.Rule;

/**
 * Receives events to check against the rule book triggers.
 */
public class RuleChecker {
    private static final Logger logger = LogsCenter.getLogger(RuleChecker.class);
    private final RuleBook rules;

    public RuleChecker(ReadOnlyRuleBook rules) {
        this.rules = new RuleBook(rules);
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    public void handleRuleBookChangedEvent(RuleBookChangedEvent rbce) {
        logger.info(LogsCenter.getEventHandlingLogMessage(rbce, "Reloading rule book..."));
        this.rules.resetData(rbce.data);
    }

    @Subscribe
    public void handleCoinChangedEvent(CoinChangedEvent cce) {
        for (Rule r : rules.getRuleList()) {
            switch (r.type) {
            case NOTIFICATION:
                NotificationRule nRule = (NotificationRule) r;
                nRule.checkAndFire(cce.newCoin);
                break;
            default:
                throw new RuntimeException("Unexpected code path!");
            }
        }
    }

}
```
###### \java\seedu\address\model\coin\Amount.java
``` java
package seedu.address.model.coin;

import java.math.BigDecimal;
import java.math.RoundingMode;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents the amount of the coin held in the address book.
 */
public class Amount implements Comparable<Amount> {

    private static final String[] MAGNITUDE_CHAR = { "", "K", "M", "B", "T", "Q", "P", "S", "H" };
    private static final String MESSAGE_TOO_BIG = "This value can't be displayed as it is too big, "
            + "total amount far exceeds circulating supply!\n"
            + "Unfortunately, CoinBook cannot yet handle unorthodox usage [Coming in v2.0]";
    private static final String DISPLAY_TOO_BIG = "Err (see log)";

    private BigDecimal value;

    /**
     * Constructs an {@code Amount}.
     */
    public Amount() {
        this.value = new BigDecimal(0);
    }

    /**
     * Constructs an {@code Amount} with given value.
     */
    public Amount(Amount amount) {
        this.value = amount.value;
    }

    /**
     * Constructs an {@code Amount} with given value.
     */
    public Amount(String value) {
        this.value = new BigDecimal(value).setScale(8, RoundingMode.UP);
    }

    /**
     * Constructs an {@code Amount} with given value.
     * For internal use only.
     */
    private Amount(BigDecimal value) {
        this.value = value;
    }


    /**
     * Adds two amounts together and returns a new object.
     * @return the sum of the two arguments
     */
    public static Amount getSum(Amount first, Amount second) {
        return new Amount(first.value.add(second.value));
    }

    /**
     * Subtracts second from first and returns a new object.
     * @return the difference of the two arguments
     */
    public static Amount getDiff(Amount first, Amount second) {
        return new Amount(first.value.subtract(second.value));
    }

    /**
     * Multiplus two amounts together and returns a new object.
     * @return the product of the two arguments
     */
    public static Amount getMult(Amount first, Amount second) {
        return new Amount(first.value.multiply(second.value));
    }

    /**
     * Adds addAmount to the current value.
     *
     * @param addAmount amount to be added.
     */
    public void addValue(Amount addAmount) {
        value = value.add(addAmount.value);
    }

    /**
     * Subtracts subtractAmount to the current value.
     *
     * @param subtractAmount amount to be subtracted.
     */
    public void subtractValue(Amount subtractAmount) {
        value = value.subtract(subtractAmount.value);
    }

    /**
     * Gets the string representation of the full value.
     * Use {@code toString} instead for display purposes.
     * @see Amount#toString
     */
    public String getValue() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && this.value.compareTo(((Amount) other).value) == 0); // state check
    }

    /**
     * Gets the display string of the value. Displays up to 4 d.p.
     * @return
     */
    @Override
    public String toString() {
        // Calculate the magnitude, which is the nearest lower multiple of three, of digits
        final int magnitude = value.compareTo(BigDecimal.ZERO) == 0
                              ? 0
                              : (value.precision() - value.scale()) / 3;

        if (magnitude < MAGNITUDE_CHAR.length) {
            // Shift the decimal point to keep the string printed at 7 digits max
            return value.movePointLeft(magnitude * 3)
                    .setScale(4, RoundingMode.UP)
                    .toPlainString()
                    + MAGNITUDE_CHAR[magnitude];
        } else {
            // We don't handle absurd cases specially for now
            LogsCenter.getLogger(Amount.class).warning(MESSAGE_TOO_BIG);
            return DISPLAY_TOO_BIG;
        }

    }

    @Override
    public int compareTo(Amount other) {
        return value.compareTo(other.value);
    }
}
```
###### \java\seedu\address\model\coin\Coin.java
``` java
    /**
     * Gets the difference between two coins and makes a new coin record with that change.
     * @return (final minus initial) as a coin, where the final coin is this
     */
    public Coin getChangeFrom(Coin initialCoin) {
        return null;
    }
```
###### \java\seedu\address\model\ReadOnlyRuleBook.java
``` java
package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.rule.Rule;

/**
 * Unmodifiable view of a rule book
 */
public interface ReadOnlyRuleBook {

    /**
     * Returns an unmodifiable view of the rules list.
     * This list will not contain any duplicate rules.
     */
    ObservableList<Rule> getRuleList();

}
```
###### \java\seedu\address\model\rule\exceptions\DuplicateRuleException.java
``` java
package seedu.address.model.rule.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Rule objects.
 */
public class DuplicateRuleException extends DuplicateDataException {
    public DuplicateRuleException() {
        super("Operation would result in duplicate rule");
    }
}
```
###### \java\seedu\address\model\rule\NotificationRule.java
``` java
package seedu.address.model.rule;

import seedu.address.logic.commands.SpawnNotificationCommand;
import seedu.address.logic.parser.NotifyCommandParser;
import seedu.address.model.coin.Coin;

/**
 * Represents a rule trigger for spawning notifications.
 * The target object type is Coins.
 */
public class NotificationRule extends Rule<Coin> {

    private static final ActionParser parseAction = SpawnNotificationCommand::new;
    private static final ConditionParser<Coin> parseCondition = NotifyCommandParser::parseNotifyCondition;

    public NotificationRule(String value) {
        super(value, RuleType.NOTIFICATION, parseAction, parseCondition);
    }

}
```
###### \java\seedu\address\model\rule\Rule.java
``` java
package seedu.address.model.rule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a Rule in the rule book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Rule<T> {

    public static final String MESSAGE_RULE_INVALID = "Rule description is invalid";
    private static final String RULE_FORMAT_STRING = "[%1$s]%2$s";

    public final RuleType type;
    public final String description;

    private final Command action;
    private final Predicate<T> condition;

    protected Rule(String description, RuleType type, ActionParser actionParser, ConditionParser<T> conditionParser) {
        requireAllNonNull(description, type, actionParser, conditionParser);
        this.description = description;
        this.type = type;
        this.action = actionParser.parse(description);
        this.condition = validateAndCreateCondition(description, conditionParser);
    }

    /**
     * Uses the given parser to validate the condition string and create the condition object.
     */
    private static <T> Predicate<T> validateAndCreateCondition(String conditionArgs, ConditionParser<T> parser) {
        try {
            return parser.parse(conditionArgs);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(MESSAGE_RULE_INVALID);
        }
    }

    /**
     * Checks the trigger condition against the provided object, then
     * executes the command tied to it if it matches
     * @param t The object to check against
     * @return Whether the command was successful.
     */
    public boolean checkAndFire(T t) {
        if (!condition.test(t)) {
            return false;
        }

        try {
            action.execute();
            return true;
        } catch (CommandException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format(RULE_FORMAT_STRING, type, description);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Rule)) {
            return false;
        }

        Rule otherRule = (Rule) other;
        return otherRule.description.equals(this.description)
                && otherRule.action.equals(this.action);
    }

    /**
     * Represents a function type used to generate the action for this rule.
     */
    @FunctionalInterface
    protected interface ActionParser {
        Command parse(String args);
    }

    /**
     * Represents a function type used to generate the trigger condition for this rule.
     */
    @FunctionalInterface
    protected interface ConditionParser<T> {
        Predicate<T> parse(String args) throws IllegalValueException;
    }

}
```
###### \java\seedu\address\model\rule\RuleType.java
``` java
package seedu.address.model.rule;

/**
 * Enumerates the possible types of rules in the RuleBook
 */
public enum RuleType {
    NOTIFICATION
}
```
###### \java\seedu\address\model\RuleBook.java
``` java
package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.UniqueRuleList;
import seedu.address.model.rule.exceptions.DuplicateRuleException;
import seedu.address.model.rule.exceptions.RuleNotFoundException;

/**
 * Stores a list of rules that can be used for notifications, etc.
 * Duplicates are not allowed (by .equals comparison)
 */
public class RuleBook implements ReadOnlyRuleBook {

    private final UniqueRuleList rules;

    public RuleBook() {
        rules = new UniqueRuleList();
    }

    public RuleBook(ReadOnlyRuleBook toBeCopied) {
        rules = new UniqueRuleList();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setRules(List<Rule> rules) throws DuplicateRuleException {
        this.rules.setRules(rules);
    }

    /**
     * Resets the existing data of this {@code RuleBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRuleBook newData) {
        requireNonNull(newData);
        List<Rule> ruleList = newData.getRuleList();

        try {
            setRules(ruleList);
        } catch (DuplicateRuleException e) {
            throw new AssertionError("RuleBooks should not have duplicate rules");
        }
    }

    //// rule-level operations

    /**
     * Adds a rule to the address book.
     *
     * @throws DuplicateRuleException if an equivalent rule already exists.
     */
    public void addRule(Rule rule) throws DuplicateRuleException {
        rules.add(rule);
    }

    /**
     * Replaces the given rule {@code target} in the list with {@code editedRule}.
     *
     * @throws DuplicateRuleException if updating the rule's details causes the rule to be equivalent to
     *      another existing rule in the list.
     * @throws RuleNotFoundException if {@code target} could not be found in the list.
     */
    public void updateRule(Rule target, Rule editedRule)
            throws DuplicateRuleException, RuleNotFoundException {
        requireNonNull(editedRule);
        rules.setRule(target, editedRule);
    }

    /**
     * Removes {@code key} from this {@code RuleBook}.
     * @throws RuleNotFoundException if the {@code key} is not in this {@code RuleBook}.
     */
    public boolean removeRule(Rule key) throws RuleNotFoundException {
        if (rules.remove(key)) {
            return true;
        } else {
            throw new RuleNotFoundException();
        }
    }

    //// util methods

    @Override
    public String toString() {
        return rules.asObservableList().size() + " rules registered";
    }

    @Override
    public ObservableList<Rule> getRuleList() {
        return rules.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RuleBook // instanceof handles nulls
                && this.rules.equals(((RuleBook) other).rules));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(rules);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedRule.java
``` java
package seedu.address.storage;

import static seedu.address.model.rule.RuleType.NOTIFICATION;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.RuleType;

/**
 * JAXB-friendly version of the Rule.
 */
public class XmlAdaptedRule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Rule's %s field is missing!";

    @XmlElement(required = true)
    private String value;

    @XmlElement(required = true)
    private String type;

    /**
     * Constructs an XmlAdaptedRule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRule() {}

    /**
     * Constructs an {@code XmlAdaptedRule} with the given rule details.
     */
    public XmlAdaptedRule(String value, String type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Converts a given Rule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRule
     */
    public XmlAdaptedRule(Rule source) {
        value = source.description;
        type = source.type.toString();
    }

    /**
     * Converts this jaxb-friendly adapted rule object into the model's Rule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted rule
     */
    public Rule toModelType() throws IllegalValueException {
        if (this.type == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, RuleType.class.getSimpleName()));
        }

        try {
            switch (RuleType.valueOf(type)) {
            case NOTIFICATION:
                return new NotificationRule(value);
            default:
                throw new IllegalValueException(Rule.MESSAGE_RULE_INVALID);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Rule.MESSAGE_RULE_INVALID);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRule)) {
            return false;
        }

        XmlAdaptedRule otherRule = (XmlAdaptedRule) other;
        return value.equals(otherRule.value);
    }
}
```
###### \java\seedu\address\ui\ChartsPanel.java
``` java
package seedu.address.ui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Region;

/**
 * The charts panel used to display graphs
 */
public class ChartsPanel extends UiPart<Region> {

    public static final String FXML = "ChartsPanel.fxml";

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String, Double> priceChart;

    private final ArrayList<Date> testDataX = new ArrayList<>(Arrays.asList(
            new Date(1452592800000L),
            new Date(1452596400000L),
            new Date(1452600000000L),
            new Date(1452603600000L),
            new Date(1452607200000L),
            new Date(1452610800000L),
            new Date(1452614400000L),
            new Date(1452618000000L),
            new Date(1452621600000L)
    ));
    private final ArrayList<Double> testDataY = new ArrayList<>(Arrays.asList(
            0.002591,
            0.002580,
            0.002617,
            0.002563,
            0.002597,
            0.002576,
            0.002555,
            0.002575,
            0.002719
    ));

    public ChartsPanel() {
        super(FXML);
        addPlot(testDataX, testDataY);
    }

    /**
     * Add a new plot to the graph
     */
    private void addPlot(ArrayList<Date> xAxis, ArrayList<Double> yAxis) {
        Series<String, Double> dataSeries = new Series<>();
        dataSeries.setName("Price History Series");
        populateData(dataSeries, xAxis, yAxis);

        priceChart.getData().add(dataSeries);
        priceChart.setCreateSymbols(false);
    }

    /**
     * Add the data from the provided lists to the data series
     * @param dataSeries
     * @param xAxis
     * @param yAxis
     */
    private void populateData(Series<String, Double> dataSeries, ArrayList<Date> xAxis, ArrayList<Double> yAxis) {
        assert (xAxis.size() == yAxis.size());
        for (int i = 0; i < xAxis.size(); i++) {
            final String date = DateFormat.getInstance().format(xAxis.get(i));
            dataSeries.getData().add(new Data<>(date, yAxis.get(i)));
        }
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowNotifManEvent(ShowNotifManRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        notificationsWindow = new NotificationsWindow(secondaryStage, event.data);
        notificationsWindow.show();
    }
```
###### \java\seedu\address\ui\NotificationsWindow.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.rule.Rule;

/**
 * Controller for the notification manager
 */
public class NotificationsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(BrowserWindow.class);
    private static final Image WINDOW_ICON = new Image("/images/address_book_32.png");
    private static final String WINDOW_TITLE = "Notifications";
    private static final String FXML = "NotificationsWindow.fxml";

    private RuleListPanel ruleListPanel;

    @FXML
    private StackPane ruleListPanelPlaceholder;

    public NotificationsWindow(Stage stage, ObservableList<Rule> data) {
        super(FXML, stage);

        // Configure the UI
        setTitle();
        setWindowDefaultSize();

        registerAsAnEventHandler(this);

        ruleListPanel = new RuleListPanel(data);
        ruleListPanelPlaceholder.getChildren().add(ruleListPanel.getRoot());
    }

    private void setTitle() {
        this.getRoot().setTitle(WINDOW_TITLE);
    }

    /**
     * Sets the default size.
     */
    private void setWindowDefaultSize() {
        this.getRoot().setHeight(300);
        this.getRoot().setWidth(500);
    }

    /**
     * Shows the notification window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing notification manager.");
        getRoot().show();
        setWindowDefaultSize();
    }

}
```
###### \resources\view\ChartsPanel.fxml
``` fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.chart.LineChart?>
<?import javafx.scene.layout.StackPane?>

<?import javafx.scene.chart.NumberAxis?>
<?import javafx.scene.chart.CategoryAxis?>

<StackPane styleClass="chart-pane" xmlns:fx="http://javafx.com/fxml/1">
  <LineChart fx:id="priceChart" title="Prices">
    <xAxis>
      <CategoryAxis label="Price History (time)" fx:id="xAxis" />
    </xAxis>
    <yAxis>
      <NumberAxis label="Price (USD)" fx:id="yAxis" />
    </yAxis>
  </LineChart>
</StackPane>
```
###### \resources\view\NotificationsWindow.fxml
``` fxml

<?import javafx.scene.Scene?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.layout.VBox?>

<?import javafx.scene.layout.StackPane?>
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         title="Help" maximized="true">
    <icons>
        <Image url="@/images/address_book_32.png" />
    </icons>
    <scene>
        <Scene>
            <StackPane fx:id="ruleListPanelPlaceholder" VBox.vgrow="ALWAYS"/>
        </Scene>
    </scene>
</fx:root>
```
