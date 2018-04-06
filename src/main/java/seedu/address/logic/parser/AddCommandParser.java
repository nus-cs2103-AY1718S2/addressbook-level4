package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JERSEY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JerseyNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.TeamName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TEAM_NAME, PREFIX_TAG, PREFIX_JERSEY_NUMBER, PREFIX_POSITION, PREFIX_RATING);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(ParserUtil.parseValue(argMultimap.getValue(PREFIX_PHONE),
                    Phone.MESSAGE_PHONE_CONSTRAINTS)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(ParserUtil.parseValue(argMultimap
                    .getValue(PREFIX_ADDRESS), Address.MESSAGE_ADDRESS_CONSTRAINTS)).get();
            Remark remark = new Remark("");
            TeamName teamName = ParserUtil.parseTeamName(ParserUtil.parseValue(argMultimap.getValue(PREFIX_TEAM_NAME),
                    TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Rating rating = ParserUtil.parseRating(ParserUtil.parseValue(argMultimap.getValue(PREFIX_RATING),
                    Rating.MESSAGE_RATING_CONSTRAINTS)).get();
            Position position = ParserUtil.parsePosition(ParserUtil.parseValue(argMultimap
                    .getValue(PREFIX_POSITION), Position.MESSAGE_POSITION_CONSTRAINTS)).get();
            JerseyNumber jerseyNumber = ParserUtil.parseJerseyNumber(ParserUtil.parseValue(argMultimap
                    .getValue(PREFIX_JERSEY_NUMBER), JerseyNumber.MESSAGE_JERSEY_NUMBER_CONSTRAINTS)).get();

            Person person = new Person(name, phone, email, address, remark, teamName, tagList, rating, position,
                    jerseyNumber);

            return new AddCommand(person);
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
