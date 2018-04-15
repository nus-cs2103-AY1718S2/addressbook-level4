package seedu.address.logic.commands;

import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE;

import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.FilterChangedEvent;
import seedu.address.model.coin.Coin;

/**
 * Finds and lists all coins in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all coins specified by the conditions."
            + "Parameters: CONDITION "
            + "Example: " + COMMAND_WORD + " " + PREFIX_CODE + "BTC AND " + PREFIX_PRICE + ">50";

    private final String description;
    private final Predicate<Coin> coinCondition;

    //@@author Eldon-Chung
    public FindCommand(String description, Predicate<Coin> coinCondition) {
        this.description = description;
        this.coinCondition = coinCondition;
    }

    @Override
    public boolean equals(Object other) {
        /*
         * Note: there isn't a good way to evaluate equality.
         * There are ways around it, but it is not clear whether those drastic measures are needed.
         * So we will always return false instead.
         */
        return false;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredCoinList(coinCondition);
        EventsCenter.getInstance().post(new FilterChangedEvent(description));
        return new CommandResult(getMessageForCoinListShownSummary(model.getFilteredCoinList().size()));
    }
    //@@author

    @Override
    public String toString() {
        return description;
    }
}
