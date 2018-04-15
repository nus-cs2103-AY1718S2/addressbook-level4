package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeOrderStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.OrderStatus;

/**
 * Parses input arguments and creates a new ChangeOrderStatusCommand object.
 */
public class ChangeOrderStatusCommandParser implements Parser<ChangeOrderStatusCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeOrderStatusCommand
     * and returns an ChangeOrderStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ChangeOrderStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ORDER_STATUS);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeOrderStatusCommand.MESSAGE_USAGE)
            );
        }

        try {
            OrderStatus orderStatus = ParserUtil.parseOrderStatus(argMultimap
                    .getValue(PREFIX_ORDER_STATUS)).get();
            return new ChangeOrderStatusCommand(index, orderStatus.getOrderStatusValue());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeOrderStatusCommand.MESSAGE_USAGE)
            );
        }
    }
}
