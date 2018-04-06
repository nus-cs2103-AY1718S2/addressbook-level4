package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RenameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.TeamName;

//@@author jordancjq
/**
 * Parses the input arguments and creates a new RenameCommand object
 */
public class RenameCommandParser implements Parser<RenameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RenameCommand
     * and returns an RenameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_TEAM_NAME);

        if (!argMultiMap.getValue(PREFIX_TEAM_NAME).isPresent() || argMultiMap.getPreamble().isEmpty()
                || argMultiMap.getAllValues(PREFIX_TEAM_NAME).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCommand.MESSAGE_USAGE));
        }

        TeamName target;
        TeamName toRename;

        try {
            target = ParserUtil.parseTeamName(argMultiMap.getPreamble());
            toRename = ParserUtil.parseTeamName(argMultiMap.getValue(PREFIX_TEAM_NAME)).get();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (target.equals(toRename)) {
            throw new ParseException(RenameCommand.MESSAGE_NO_CHANGE);
        }

        return new RenameCommand(target, toRename);
    }
}
