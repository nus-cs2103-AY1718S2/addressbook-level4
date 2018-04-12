//@@author ZhangYijiong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.dish.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Order;
import seedu.address.model.task.Count;
import seedu.address.model.task.Distance;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an AddOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER, PREFIX_ADDRESS, PREFIX_PRICE,
                        PREFIX_DISTANCE, PREFIX_COUNT, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_ORDER, PREFIX_ADDRESS, PREFIX_PRICE,
                PREFIX_DISTANCE, PREFIX_COUNT, PREFIX_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        try {
            Order order = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).get();
            Distance distance = ParserUtil.parseDistance(argMultimap.getValue(PREFIX_DISTANCE)).get();
            Count count = ParserUtil.parseCount(argMultimap.getValue(PREFIX_COUNT)).get();
            String description = argMultimap.getValue(PREFIX_DESCRIPTION).orElse("");

            Task task = new Task(order, address, price, distance, count, description);

            return new AddOrderCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
