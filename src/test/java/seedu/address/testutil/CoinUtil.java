package seedu.address.testutil;

import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.PREFIX_TAG;
import static seedu.address.testutil.TestUtil.DECIMAL_STRING;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.SellCommand;
import seedu.address.model.coin.Coin;

/**
 * A utility class for Coin.
 */
public class CoinUtil {

    //@@author Eldon-Chung
    /**
     * Returns a sell command string for buy the {@code coin}.
     */
    public static String getSellCommand(Index index) {
        return SellCommand.COMMAND_WORD + " " + index.getOneBased() + "  " + PREFIX_AMOUNT + " " + DECIMAL_STRING;
    }

    /**
     * Returns a buy command string for buy the {@code coin}.
     */
    public static String getBuyCommand(Index index) {
        return BuyCommand.COMMAND_WORD + " " + index.getOneBased() + "  " + PREFIX_AMOUNT + " " + DECIMAL_STRING;
    }
    //@@author

    /**
     * Returns an add command string for adding the {@code coin}.
     */
    public static String getAddCommand(Coin coin) {
        return AddCommand.COMMAND_WORD + " " + getCoinDetails(coin);
    }
    /**
     * Returns an add aliased command string for adding the {@code coin}.
     */
    public static String getAddAliasCommand(Coin coin) {
        return AddCommand.COMMAND_ALIAS + " " + getCoinDetails(coin);
    }

    /**
     * Returns the part of command string for the given {@code coin}'s details.
     */
    public static String getCoinDetails(Coin coin) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CODE + coin.getCode().fullName + " ");
        coin.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code coin}'s tags.
     */
    public static String getCoinTags(Coin coin) {
        StringBuilder sb = new StringBuilder();
        coin.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
