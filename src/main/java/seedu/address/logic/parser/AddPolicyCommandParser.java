package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGINNING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Date;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.Price;

/**
 * Parses input arguments and creates a new AddPolicyCommand object
 */
public class AddPolicyCommandParser implements Parser<AddPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPolicyCommand
     * and returns an AddPolicyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BEGINNING_DATE,
                PREFIX_EXPIRATION_DATE, PREFIX_PRICE, PREFIX_ISSUE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_BEGINNING_DATE, PREFIX_EXPIRATION_DATE, PREFIX_PRICE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        Price price;
        Date beginningDate;
        Date expirationDate;
        Coverage coverage;

        try {
            price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
            beginningDate = ParserUtil.parsePolicyDate(argMultimap.getValue(PREFIX_BEGINNING_DATE).get());
            expirationDate = ParserUtil.parsePolicyDate(argMultimap.getValue(PREFIX_EXPIRATION_DATE).get());
            coverage = new Coverage(ParserUtil.parseIssues(argMultimap.getAllValues(PREFIX_ISSUE)));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!Policy.isValidDuration(beginningDate, expirationDate)) {
            throw new ParseException(Policy.DURATION_CONSTRAINTS);
        }

        Policy policy = new Policy(price, coverage, beginningDate, expirationDate);

        return new AddPolicyCommand(index, policy);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
