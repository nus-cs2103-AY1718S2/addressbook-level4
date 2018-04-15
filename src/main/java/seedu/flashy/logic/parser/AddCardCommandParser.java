package seedu.flashy.logic.parser;

import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_FRONT;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.flashy.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.flashy.commons.exceptions.IllegalValueException;
import seedu.flashy.logic.commands.AddCardCommand;
import seedu.flashy.logic.parser.exceptions.ParseException;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.card.FillBlanksCard;
import seedu.flashy.model.card.McqCard;
import seedu.flashy.model.tag.Tag;

//@@author shawnclq
/**
 * Parses input arguments and creates a new AddCardCommand object
 */
public class AddCardCommandParser implements Parser<AddCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCardCommand
     * and returns an AddCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCardCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FRONT, PREFIX_BACK, PREFIX_OPTION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_FRONT, PREFIX_BACK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand.MESSAGE_USAGE));
        }

        try {
            String front = ParserUtil.parseCard(argMultimap.getValue(PREFIX_FRONT).get());
            String back = ParserUtil.parseCard(argMultimap.getValue(PREFIX_BACK).get());
            List<String> options = argMultimap.getAllValues(PREFIX_OPTION);
            Optional<Set<Tag>> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Card card;

            if (options.isEmpty()) {
                if (FillBlanksCard.containsBlanks(front)) {
                    card = ParserUtil.parseFillBlanksCard(front, back);
                } else {
                    card = new Card(front, back);
                }
            } else {
                for (String option: options) {
                    ParserUtil.parseMcqOption(option);
                }
                card = ParserUtil.parseMcqCard(front, back, options);
                card.setType(McqCard.TYPE);
            }

            return new AddCardCommand(card, tags);
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
//@@author
