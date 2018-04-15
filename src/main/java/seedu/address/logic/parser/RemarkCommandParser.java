package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

//@@author sherlynng
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} with remark in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        boolean isEditRemark;

        isEditRemark = argMultimap.getPreamble().contains("edit");

        if (!isEditRemark && !arePrefixesPresent(argMultimap, PREFIX_REMARK)) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT + MESSAGE_USAGE);
        }

        try {
            if (isEditRemark) {
                String replacedPreamble = argMultimap.getPreamble().replace("edit", "");
                index = ParserUtil.parseIndex(replacedPreamble);
            } else {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage());
        }

        Remark remark;
        if (isEditRemark) {
            remark = ParserUtil.parseRemark("");
            return new RemarkCommand(index, remark, isEditRemark);
        } else {
            remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).orElse(new Remark(""));
        }

        return new RemarkCommand(index, remark);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
