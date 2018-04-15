package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.logic.parser.TokenType.PREFIX_BOUGHT;
import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.PREFIX_HELD;
import static seedu.address.logic.parser.TokenType.PREFIX_MADE;
import static seedu.address.logic.parser.TokenType.PREFIX_NAME;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE;
import static seedu.address.logic.parser.TokenType.PREFIX_SOLD;
import static seedu.address.logic.parser.TokenType.PREFIX_TAG;
import static seedu.address.logic.parser.TokenType.PREFIX_WORTH;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coin.Coin;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final TokenType[] EXPECTED_TOKEN_TYPES = {
        PREFIX_AMOUNT,
        PREFIX_BOUGHT,
        PREFIX_CODE,
        PREFIX_HELD,
        PREFIX_MADE,
        PREFIX_NAME,
        PREFIX_PRICE,
        PREFIX_SOLD,
        PREFIX_TAG,
        PREFIX_WORTH
    };

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        TokenStack tokenStack = ArgumentTokenizer.tokenizeToTokenStack(args, EXPECTED_TOKEN_TYPES);
        try {
            Predicate<Coin> coinCondition = ParserUtil.parseCondition(tokenStack);
            return new FindCommand(args, coinCondition);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
