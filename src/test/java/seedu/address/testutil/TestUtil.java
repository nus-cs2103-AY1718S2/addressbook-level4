package seedu.address.testutil;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.parser.Token;
import seedu.address.logic.parser.TokenType;
import seedu.address.model.Model;
import seedu.address.model.coin.Coin;

/**
 * A utility class for test cases.
 */
public class TestUtil {

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
    public static final Token RIGHT_PARENT_TOKEN = new Token(TokenType.RIGHTPARENTHESES, RIGHT_PAREN_STRING);
    public static final String GREATER_STRING = ">";
    public static final Token GREATER_TOKEN = new Token(TokenType.COMPARATOR, GREATER_STRING);
    public static final String LESS_STRING = "<";
    public static final Token LESS_TOKEN = new Token(TokenType.COMPARATOR, LESS_STRING);
    public static final String EQUALS_STRING = "=";
    public static final Token EQUALS_TOKEN = new Token(TokenType.COMPARATOR, EQUALS_STRING);
    public static final String PREFIX_STRING = "a/";
    public static final Token PREFIX_TOKEN = new Token(TokenType.PREFIXAMOUNT, PREFIX_STRING);
    public static final String NUM_STRING = "999";
    public static final Token NUM_TOKEN = new Token(TokenType.NUM, NUM_STRING);
    public static final String STRING_STRING = "TESTING";
    public static final Token STRING_TOKEN = new Token(TokenType.STRING, STRING_STRING);
    public static final String SLASH_STRING = "/";
    public static final Token SLASH_TOKEN = new Token(TokenType.SLASH, SLASH_STRING);
    public static final Token EOF_TOKEN = new Token(TokenType.EOF, "");
    public static final Token WHITESPACE_TOKEN = new Token(TokenType.WHITESPACE, " ");

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
}
