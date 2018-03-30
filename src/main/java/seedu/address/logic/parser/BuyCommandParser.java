//@@author ewaldhew
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.EmptyStackException;
import java.util.Stack;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.conditionalparser.Lexer;
import seedu.address.logic.conditionalparser.Token;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BuyCommand object
 */
public class BuyCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the BuyCommand
     * and returns an BuyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Stack<Token> argStack = new Lexer().lex(args).getTokenStack();
        //ArgumentMultimap argMultimap =
        //        ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        Index index;
        double amountToAdd;

        try {
            index = ParserUtil.parseIndex(argStack.pop().getPattern());
            amountToAdd = ParserUtil.parseDouble(argStack.pop().getPattern());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        } catch (EmptyStackException ese) {
            throw new ParseException(BuyCommand.MESSAGE_NOT_BOUGHT);
        }

        return new BuyCommand(index, amountToAdd);
    }

}
