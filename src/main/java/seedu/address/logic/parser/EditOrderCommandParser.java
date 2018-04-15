//@@author amad-person
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditOrderCommand object.
 */
public class EditOrderCommandParser implements Parser<EditOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditOrderCommand
     * and returns an EditOrderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER_INFORMATION, PREFIX_PRICE, PREFIX_QUANTITY,
                        PREFIX_DELIVERY_DATE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));
        }

        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        try {
            ParserUtil.parseOrderInformation(argMultimap.getValue(PREFIX_ORDER_INFORMATION))
                    .ifPresent(editOrderDescriptor::setOrderInformation);
            ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).ifPresent(editOrderDescriptor::setPrice);
            ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY)).ifPresent(editOrderDescriptor::setQuantity);
            ParserUtil.parseDeliveryDate(argMultimap.getValue(PREFIX_DELIVERY_DATE))
                    .ifPresent(editOrderDescriptor::setDeliveryDate);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditOrderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditOrderCommand(index, editOrderDescriptor);
    }
}
