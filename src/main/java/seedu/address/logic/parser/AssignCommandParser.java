package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.TeamName;

//@@author jordancjq
/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent() || argMultimap.getAllValues(PREFIX_INDEX).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        TeamName teamName;
        try {
            if (!argMultimap.getPreamble().isEmpty()) {
                teamName = ParserUtil.parseTeamName(argMultimap.getPreamble());
            } else {
                teamName = new TeamName(UNSPECIFIED_FIELD);
            }
            List<Index> indexList = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_INDEX).get());

            return new AssignCommand(teamName, indexList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
