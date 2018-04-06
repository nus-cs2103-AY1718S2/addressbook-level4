package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;
import static seedu.address.model.team.TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS;

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
        if (!TeamName.isValidName(trimmedArgs) || trimmedArgs.equals(UNSPECIFIED_FIELD)) {
            throw new ParseException(MESSAGE_TEAM_NAME_CONSTRAINTS);
        }

        TeamName teamName = new TeamName(trimmedArgs);

        return new CreateCommand(new Team(teamName));
    }
}
