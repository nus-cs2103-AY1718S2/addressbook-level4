package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ILLNESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TREATMENT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RecordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Record;

/**
 * Parses input arguments and creates a new RecordCommand object
 */
public class RecordCommandParser implements Parser<RecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordCommand
     * and returns an RecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DATE,
                        PREFIX_SYMPTOM, PREFIX_ILLNESS, PREFIX_TREATMENT);

        Index patientIndex;
        Index recordIndex;

        try {
            patientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        try {
            recordIndex = ParserUtil.parseIndex((argMultimap.getValue(PREFIX_INDEX)).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_SYMPTOM, PREFIX_ILLNESS, PREFIX_TREATMENT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordCommand.MESSAGE_USAGE));
        }

        //to nest following lines into try once various classes are set up

        String date = (argMultimap.getValue(PREFIX_DATE)).get();
        String symptom = (argMultimap.getValue(PREFIX_SYMPTOM)).get();
        String illness = (argMultimap.getValue(PREFIX_ILLNESS)).get();
        String treatment = (argMultimap.getValue(PREFIX_TREATMENT)).get();

        Record record = new Record(date, symptom, illness, treatment);

        return new RecordCommand(patientIndex, recordIndex.getZeroBased(), record);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
