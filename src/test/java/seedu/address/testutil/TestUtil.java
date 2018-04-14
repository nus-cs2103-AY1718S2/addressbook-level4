package seedu.address.testutil;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.CoinSubredditList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.Token;
import seedu.address.logic.parser.TokenType;
import seedu.address.model.Model;
import seedu.address.model.coin.Coin;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    //@@author Eldon-Chung
    /**
     * Tokens and strings for ArgumentTokenizer, Parsing and TokenStack testing.
     */
    public static final String AND_STRING = " AND ";
    public static final Token AND_TOKEN = new Token(TokenType.BINARYBOOL, AND_STRING);
    public static final String OR_STRING = " OR ";
    public static final Token OR_TOKEN = new Token(TokenType.BINARYBOOL, OR_STRING);
    public static final String NOT_STRING = "NOT ";
    public static final Token NOT_TOKEN = new Token(TokenType.UNARYBOOL, NOT_STRING);

    public static final String LEFT_PAREN_STRING = "(";
    public static final Token LEFT_PAREN_TOKEN = new Token(TokenType.LEFTPARENTHESES, LEFT_PAREN_STRING);
    public static final String RIGHT_PAREN_STRING = ")";
    public static final Token RIGHT_PAREN_TOKEN = new Token(TokenType.RIGHTPARENTHESES, RIGHT_PAREN_STRING);

    public static final String GREATER_STRING = ">";
    public static final Token GREATER_TOKEN = new Token(TokenType.COMPARATOR, GREATER_STRING);
    public static final String LESS_STRING = "<";
    public static final Token LESS_TOKEN = new Token(TokenType.COMPARATOR, LESS_STRING);
    public static final String EQUALS_STRING = "=";
    public static final Token EQUALS_TOKEN = new Token(TokenType.COMPARATOR, EQUALS_STRING);

    public static final String PREFIX_AMOUNT_STRING = "a/";
    public static final Token PREFIX_AMOUNT_TOKEN = new Token(TokenType.PREFIX_AMOUNT, PREFIX_AMOUNT_STRING);

    public static final String PREFIX_BOUGHT_STRING = "b/";
    public static final Token PREFIX_BOUGHT_TOKEN = new Token(TokenType.PREFIX_BOUGHT, PREFIX_BOUGHT_STRING);
    public static final String PREFIX_BOUGHT_RISE_STRING = "b/+";
    public static final Token PREFIX_BOUGHT_RISE_TOKEN = new Token(TokenType.PREFIX_BOUGHT, PREFIX_BOUGHT_RISE_STRING);
    public static final String PREFIX_BOUGHT_FALL_STRING = "b/-";
    public static final Token PREFIX_BOUGHT_FALL_TOKEN = new Token(TokenType.PREFIX_BOUGHT, PREFIX_BOUGHT_FALL_STRING);

    public static final String PREFIX_CODE_STRING = "c/";
    public static final Token PREFIX_CODE_TOKEN = new Token(TokenType.PREFIX_CODE, PREFIX_CODE_STRING);

    public static final String PREFIX_HELD_STRING = "h/";
    public static final Token PREFIX_HELD_TOKEN = new Token(TokenType.PREFIX_HELD, PREFIX_HELD_STRING);
    public static final String PREFIX_HELD_RISE_STRING = "h/+";
    public static final Token PREFIX_HELD_RISE_TOKEN = new Token(TokenType.PREFIX_HELD, PREFIX_HELD_RISE_STRING);
    public static final String PREFIX_HELD_FALL_STRING = "h/-";
    public static final Token PREFIX_HELD_FALL_TOKEN = new Token(TokenType.PREFIX_HELD, PREFIX_HELD_FALL_STRING);

    public static final String PREFIX_MADE_STRING = "m/";
    public static final Token PREFIX_MADE_TOKEN = new Token(TokenType.PREFIX_MADE, PREFIX_MADE_STRING);
    public static final String PREFIX_MADE_RISE_STRING = "m/+";
    public static final Token PREFIX_MADE_RISE_TOKEN = new Token(TokenType.PREFIX_MADE, PREFIX_MADE_RISE_STRING);
    public static final String PREFIX_MADE_FALL_STRING = "m/-";
    public static final Token PREFIX_MADE_FALL_TOKEN = new Token(TokenType.PREFIX_MADE, PREFIX_MADE_FALL_STRING);

    public static final String PREFIX_NAME_STRING = "n/";
    public static final Token PREFIX_NAME_TOKEN = new Token(TokenType.PREFIX_NAME, PREFIX_NAME_STRING);

    public static final String PREFIX_PRICE_STRING = "p/";
    public static final Token PREFIX_PRICE_TOKEN = new Token(TokenType.PREFIX_PRICE, PREFIX_PRICE_STRING);
    public static final String PREFIX_PRICE_RISE_STRING = "p/+";
    public static final Token PREFIX_PRICE_RISE_TOKEN = new Token(TokenType.PREFIX_PRICE, PREFIX_PRICE_RISE_STRING);
    public static final String PREFIX_PRICE_FALL_STRING = "p/-";
    public static final Token PREFIX_PRICE_FALL_TOKEN = new Token(TokenType.PREFIX_PRICE, PREFIX_PRICE_STRING);

    public static final String PREFIX_SOLD_STRING = "s/";
    public static final Token PREFIX_SOLD_TOKEN = new Token(TokenType.PREFIX_SOLD, PREFIX_SOLD_STRING);
    public static final String PREFIX_SOLD_RISE_STRING = "s/+";
    public static final Token PREFIX_SOLD_RISE_TOKEN = new Token(TokenType.PREFIX_SOLD, PREFIX_SOLD_RISE_STRING);
    public static final String PREFIX_SOLD_FALL_STRING = "s/-";
    public static final Token PREFIX_SOLD_FALL_TOKEN = new Token(TokenType.PREFIX_SOLD, PREFIX_SOLD_FALL_STRING);

    public static final String PREFIX_TAG_STRING = "t/";
    public static final Token PREFIX_TAG_TOKEN = new Token(TokenType.PREFIX_TAG, PREFIX_TAG_STRING);

    public static final String PREFIX_WORTH_STRING = "w/";
    public static final Token PREFIX_WORTH_TOKEN = new Token(TokenType.PREFIX_WORTH, PREFIX_WORTH_STRING);
    public static final String PREFIX_WORTH_RISE_STRING = "w/+";
    public static final Token PREFIX_WORTH_RISE_TOKEN = new Token(TokenType.PREFIX_WORTH, PREFIX_WORTH_RISE_STRING);
    public static final String PREFIX_WORTH_FALL_STRING = "w/-";
    public static final Token PREFIX_WORTH_FALL_TOKEN = new Token(TokenType.PREFIX_WORTH, PREFIX_WORTH_FALL_STRING);

    public static final String NUM_STRING = "999";
    public static final Token NUM_TOKEN = new Token(TokenType.NUM, NUM_STRING);
    public static final String DECIMAL_STRING = "9.99";
    public static final Token DECIMAL_TOKEN = new Token(TokenType.DECIMAL, DECIMAL_STRING);
    public static final String STRING_ONE_STRING = "TESTINGONE";
    public static final Token STRING_ONE_TOKEN = new Token(TokenType.STRING, STRING_ONE_STRING);
    public static final String STRING_TWO_STRING = "TESTINGTWO";
    public static final Token STRING_TWO_TOKEN = new Token(TokenType.STRING, STRING_TWO_STRING);
    public static final String STRING_THREE_STRING = "TESTINGTHREE";
    public static final Token STRING_THREE_TOKEN = new Token(TokenType.STRING, STRING_THREE_STRING);
    public static final String SLASH_STRING = "/";
    public static final Token SLASH_TOKEN = new Token(TokenType.SLASH, SLASH_STRING);
    public static final Token EOF_TOKEN = new Token(TokenType.EOF, "");
    public static final Token WHITESPACE_TOKEN = new Token(TokenType.WHITESPACE, " ");

    /**
     * Tokens with all possible combinations of 3 tags, for truth table testing when evaluating composed tag conditions.
     */

    public static final Coin COIN_0 = new CoinBuilder().withName("COIN ZERO")
            .withTags().build();
    public static final Coin COIN_1 = new CoinBuilder().withName("COIN ONE")
            .withTags(STRING_THREE_STRING).build();
    public static final Coin COIN_2 = new CoinBuilder().withName("COIN TWO")
            .withTags(STRING_TWO_STRING).build();
    public static final Coin COIN_3 = new CoinBuilder().withName("COIN THREE")
            .withTags(STRING_TWO_STRING, STRING_THREE_STRING).build();
    public static final Coin COIN_4 = new CoinBuilder().withName("COIN FOUR")
            .withTags(STRING_ONE_STRING).build();
    public static final Coin COIN_5 = new CoinBuilder().withName("COIN FIVE")
            .withTags(STRING_ONE_STRING, STRING_THREE_STRING).build();
    public static final Coin COIN_6 = new CoinBuilder().withName("COIN SIX")
            .withTags(STRING_ONE_STRING, STRING_TWO_STRING).build();
    public static final Coin COIN_7 = new CoinBuilder().withName("COIN SEVEN")
            .withTags(STRING_ONE_STRING, STRING_TWO_STRING, STRING_THREE_STRING).build();
    //@@author
    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting string.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    /**
     * Returns the middle index of the coin in the {@code model}'s coin list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getCoinBook().getCoinList().size() / 2);
    }

    /**
     * Returns the last index of the coin in the {@code model}'s coin list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getCoinBook().getCoinList().size());
    }

    /**
     * Returns the coin in the {@code model}'s coin list at {@code index}.
     */
    public static Coin getCoin(Model model, Index index) {
        return model.getCoinBook().getCoinList().get(index.getZeroBased());
    }

    /**
     * Generates a new {@code successMessage} based on the {@code coin}.
     */
    public static String getAddCommandSuccessMessage(Coin coin) {
        return String.format(AddCommand.MESSAGE_SUCCESS
                + (CoinSubredditList.isRecognized(coin) ? "" : AddCommand.MESSAGE_COIN_CODE_NOT_REGISTERED), coin);
    }
}
