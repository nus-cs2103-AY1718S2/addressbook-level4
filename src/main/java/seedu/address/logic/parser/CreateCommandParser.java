package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.team.TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CreateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

//@@author jordancjq
/**
 * Parses input arguments and creates a new CreateCommand object
 */
public class CreateCommandParser implements Parser<CreateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns an CreateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
        }

        TeamName teamName;
        try {
            teamName = ParserUtil.parseTeamName(ParserUtil.parseValue(Optional.of(trimmedArgs),
                    MESSAGE_TEAM_NAME_CONSTRAINTS)).get();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new CreateCommand(new Team(teamName));
    }
}
