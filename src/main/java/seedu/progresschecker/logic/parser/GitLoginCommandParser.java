package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_PASSCODE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_REPO;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_GIT_USERNAME;

import java.util.stream.Stream;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.GitLoginCommand;
import seedu.progresschecker.logic.parser.exceptions.ParseException;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.credentials.Passcode;
import seedu.progresschecker.model.credentials.Repository;
import seedu.progresschecker.model.credentials.Username;

/**
 * Parses input arguments and creates a new GitDetails object
 */
public class GitLoginCommandParser implements Parser<GitLoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GitLoginCommand
     * and returns an GitLoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GitLoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GIT_USERNAME, PREFIX_GIT_PASSCODE, PREFIX_GIT_REPO);

        if (!arePrefixesPresent(argMultimap, PREFIX_GIT_USERNAME, PREFIX_GIT_PASSCODE, PREFIX_GIT_REPO)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GitLoginCommand.MESSAGE_USAGE));
        }

        try {
            Username username = ParserUtil.parseGitUsername(argMultimap.getValue(PREFIX_GIT_USERNAME)).get();
            Passcode passcode = ParserUtil.parsePasscode(argMultimap.getValue(PREFIX_GIT_PASSCODE)).get();
            Repository repository = ParserUtil.parseRepository(argMultimap.getValue(PREFIX_GIT_REPO)).get();

            GitDetails details = new GitDetails(username, passcode, repository);

            return new GitLoginCommand(details);
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
