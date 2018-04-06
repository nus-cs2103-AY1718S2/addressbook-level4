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
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Adds value to an existing coin in the book.
 */
public class BuyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "buy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add value to the coin account identified "
            + "by the index number used in the last coin listing or its code. "
            + "Parameters: TARGET "
            + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + "50.0";

    public static final String MESSAGE_BUY_COIN_SUCCESS = "Bought: %1$s";
    public static final String MESSAGE_NOT_BOUGHT = "Invalid code or amount entered.";

    private final CommandTarget target;
    private final double amountToAdd;

    private Coin coinToEdit;
    private Coin editedCoin;

    /**
     * @param target      in the filtered coin list to change
     * @param amountToAdd to the coin
     */
    public BuyCommand(CommandTarget target, double amountToAdd) {
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
    private static Coin createEditedCoin(Coin coinToEdit, double amountToAdd) {
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
                && amountToAdd == e.amountToAdd
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
            return Index.fromOneBased(coinList.filtered(coin -> coin.getCode().equals(code)).size());

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
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.exceptions.DuplicateRuleException;

/**
 * Adds a new notification with the specified conditions.
 */
public class NotifyCommand extends Command {

    public static final String COMMAND_WORD = "notify";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new notification to be triggered "
            + "upon the specified rule. Rules are provided in the following format:\n"
            + "Parameters: TARGET OPTION/VALUE [...] \n"
            + "Example: " + COMMAND_WORD + " BTC p/15000";

    public static final String MESSAGE_SUCCESS = "Will notify when: %1$s";
    public static final String MESSAGE_DUPLICATE_RULE = "This notification rule already exists!";

    //private final Predicate predicate;
    private final String value;

    public NotifyCommand(String args) {
        this.value = args;
        //this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addRule(new Rule(value));
            return new CommandResult(String.format(MESSAGE_SUCCESS, value));
        } catch (DuplicateRuleException e) {
            throw new CommandException(MESSAGE_DUPLICATE_RULE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifyCommand); // instanceof handles nulls
                //&& this.predicate.equals(((NotifyCommand) other).predicate)); // state check

    }
}
```
###### \java\seedu\address\logic\commands\SellCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COINS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.coin.Coin;
import seedu.address.model.coin.exceptions.CoinNotFoundException;
import seedu.address.model.coin.exceptions.DuplicateCoinException;

/**
 * Removes value from an existing coin in the book.
 */
public class SellCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sell";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes value from the coin account identified "
            + "by the index number used in the last coin listing or its code. "
            + "Parameters: TARGET "
            + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + "50.0";

    public static final String MESSAGE_SELL_COIN_SUCCESS = "Sold: %1$s";

    private final CommandTarget target;
    private final double amountToSell;

    private Coin coinToEdit;
    private Coin editedCoin;

    /**
     * @param target of the coin in the filtered coin list to change
     * @param amountToSell of the coin
     */
    public SellCommand(CommandTarget target, double amountToSell) {
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
    private static Coin createEditedCoin(Coin coinToEdit, double amountToSell) {
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
###### \java\seedu\address\logic\parser\BuyCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIXAMOUNT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.parser.exceptions.ParseException;

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
                ArgumentTokenizer.tokenizeToArgumentMultimap(args, PREFIXAMOUNT);
        if (!argMultimap.arePrefixesPresent(PREFIXAMOUNT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

        try {
            CommandTarget target = ParserUtil.parseTarget(argMultimap.getPreamble());
            double amountToAdd = ParserUtil.parseDouble(argMultimap.getValue(PREFIXAMOUNT).get());
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

import seedu.address.logic.commands.NotifyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NotifyCommand object
 */
public class NotifyCommandParser implements Parser<NotifyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NotifyCommand
     * and returns an NotifyCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NotifyCommand parse(String args) throws ParseException {
        return new NotifyCommand(args);
    }
}
```
###### \java\seedu\address\logic\parser\SellCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIXAMOUNT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
                ArgumentTokenizer.tokenizeToArgumentMultimap(args, PREFIXAMOUNT);
        if (!argMultimap.arePrefixesPresent(PREFIXAMOUNT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

        try {
            CommandTarget target = ParserUtil.parseTarget(argMultimap.getPreamble());
            double amountToSell = ParserUtil.parseDouble(argMultimap.getValue(PREFIXAMOUNT).get());
            return new SellCommand(target, amountToSell);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

    }

}
```
###### \java\seedu\address\model\coin\Coin.java
``` java
    /**
     * Gets the difference between two coins and makes a new coin record with that change.
     * @return (final - initial) as a coin, where the final coin is this
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
###### \java\seedu\address\model\rule\Rule.java
``` java
package seedu.address.model.rule;

/**
 * Represents a Rule in the rule book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Rule {

    private final String value;

    public Rule(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return value;
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
        return otherRule.value.equals(this.value);
    }

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

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.rule.Rule;

/**
 * JAXB-friendly version of the Rule.
 */
public class XmlAdaptedRule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Rule's %s field is missing!";

    @XmlElement(required = true)
    private String value;

    /**
     * Constructs an XmlAdaptedRule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRule() {}

    /**
     * Constructs an {@code XmlAdaptedRule} with the given rule details.
     */
    public XmlAdaptedRule(String value) {
        this.value = value;
    }

    /**
     * Converts a given Rule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRule
     */
    public XmlAdaptedRule(Rule source) {
        value = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted rule object into the model's Rule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted rule
     */
    public Rule toModelType() throws IllegalValueException {
        return new Rule(value);
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
