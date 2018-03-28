package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;

import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.issues.Issue;

/**
 * Create an issue on github
 */
public class CreateIssue extends Command {

    public static final String COMMAND_WORD = "+issue";
    public static final String COMMAND_ALIAS = "ci";
    public static final String COMMAND_FORMAT = COMMAND_WORD + " "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_ASSIGNEES + "ASSIGNEES "
            + PREFIX_MILESTONE + "MILESTONE "
            + PREFIX_BODY + "BODY "
            + PREFIX_LABEL + "LABELS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create an issue in your team organisation. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_ASSIGNEES + "ASSIGNEES "
            + PREFIX_MILESTONE + "MILESTONE "
            + PREFIX_BODY + "BODY "
            + PREFIX_LABEL + "LABELS/n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Add new create issue command "
            + PREFIX_ASSIGNEES + "johndoe "
            + PREFIX_MILESTONE + "v1.1 "
            + PREFIX_BODY + "This is a test issue "
            + PREFIX_LABEL + "bug";
    public static final String MESSAGE_SUCCESS = "New issue created: %1$s";

    private final Issue toBeCreated;

    /**
     * Creates an CreateIssue to create the specified {@code Issue}
     */
    public CreateIssue(Issue issue) {
        requireNonNull(issue);
        toBeCreated = issue;
    }
    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.createIssueOnGitHub(toBeCreated);
            return new CommandResult("Issue successfully created on Github");
        } catch (IOException e) {
            throw new CommandException("There is some error in the parameter or authentication");
        }
    }

}
