package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_TAG, PREFIX_SUBJECT, PREFIX_REMARK);

        if (!(argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_NRIC))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            Set<Subject> subjectList = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));
            Remark remark;
            Cca cca = ParserUtil.parseCca("", "");
            InjuriesHistory injuriesHistory = ParserUtil.parseInjuriesHistory(" ");
            NextOfKin nextOfKin = ParserUtil.parseNextOfKin(" ", " ", " ", " ");
            if (!(argMultimap.getValue(PREFIX_REMARK)).isPresent()) {
                remark = ParserUtil.parseRemark(" ");
            } else {
                remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            }
            Person person = new Person(name, nric, tagList, subjectList, remark, cca, injuriesHistory, nextOfKin);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
