//@@author ewaldhew
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;
import static seedu.address.logic.parser.TokenType.PREFIX_BOUGHT;
import static seedu.address.logic.parser.TokenType.PREFIX_CODE;
import static seedu.address.logic.parser.TokenType.PREFIX_HELD;
import static seedu.address.logic.parser.TokenType.PREFIX_MADE;
import static seedu.address.logic.parser.TokenType.PREFIX_NAME;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE_FALL;
import static seedu.address.logic.parser.TokenType.PREFIX_PRICE_RISE;
import static seedu.address.logic.parser.TokenType.PREFIX_SOLD;
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
        PREFIX_BOUGHT,
        PREFIX_CODE,
        PREFIX_HELD,
        PREFIX_MADE,
        PREFIX_NAME,
        PREFIX_PRICE_RISE, PREFIX_PRICE_FALL, PREFIX_PRICE,
        PREFIX_SOLD,
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
