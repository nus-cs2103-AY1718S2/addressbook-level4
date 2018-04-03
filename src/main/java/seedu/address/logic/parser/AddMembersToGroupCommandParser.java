package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddMembersToGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.person.Address;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TimeTableLink;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddMembersToGroupCommand object
 */
public class AddMembersToGroupCommandParser implements Parser<AddMembersToGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMembersToGroupCommand
     * and returns an AddMembersToGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMembersToGroupCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUP, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMembersToGroupCommand.MESSAGE_USAGE));
        }
        try {
            Information information = ParserUtil.parseInformation(argMultimap.getValue(PREFIX_GROUP).get());
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = new Phone("98765432");
            Email email = new Email("johnd@example.com");
            Address address = new Address("311, Clementi Ave 2, #02-25 ");
            TimeTableLink link = new TimeTableLink("http://modsn.us/MYwiD");
            Detail detail = new Detail("Detail");
            Set<Tag> tags = new HashSet<Tag>();

            Group group = new Group(information);
            Person person = new Person(name, phone, email, address, link, detail, tags);
            return new AddMembersToGroupCommand(person, group);
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
