package seedu.progresschecker.logic.parser;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.stream.Stream;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.logic.commands.CreateIssue;
import seedu.progresschecker.logic.parser.exceptions.ParseException;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.issues.Milestone;
import seedu.progresschecker.model.issues.Title;


/**
 * Parses input arguments and creates a new CreateIssue object
 */
public class CreateIssueParser implements Parser<CreateIssue> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateIssue parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_ASSIGNEES,
                        PREFIX_MILESTONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_ASSIGNEES, PREFIX_MILESTONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateIssue.MESSAGE_USAGE));
        }

        try {
            Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE)).get();
            Assignees assignee = ParserUtil.parseAssignees(argMultimap.getValue(PREFIX_ASSIGNEES)).get();
            Milestone milestone = ParserUtil.parseMilestone(argMultimap.getValue(PREFIX_MILESTONE)).get();

            Issue issue = new Issue(title, assignee, milestone);

            return new CreateIssue(issue);
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
