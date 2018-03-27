package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Card;
import seedu.address.model.card.McqCard;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCardCommandParser implements Parser<AddCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCardCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FRONT, PREFIX_BACK, PREFIX_OPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_FRONT, PREFIX_BACK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
        }

        try {
            String front = ParserUtil.parseCard(argMultimap.getValue(PREFIX_FRONT).get());
            String back = ParserUtil.parseCard(argMultimap.getValue(PREFIX_BACK).get());
            Set<String> options = new HashSet<>(argMultimap.getAllValues(PREFIX_OPTION));
            Card card;

            if (options.isEmpty()) {
                card = new Card(front, back);
            } else {
                for (String option: options) {
                    ParserUtil.parseMcqOption(option);
                }

                card = ParserUtil.parseMcqCard(front, back, options);
                card.setType(McqCard.TYPE);
            }

            return new AddCardCommand(card);
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
