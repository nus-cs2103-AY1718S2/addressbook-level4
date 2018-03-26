package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.issues.Issue;

/**
 * Adds a person to the ProgressChecker.
 */
public class CreateIssue extends Command {

    public static final String COMMAND_WORD = "createissue";
    public static final String COMMAND_ALIAS = "ci";
    public static final String COMMAND_FORMAT = COMMAND_WORD
            + PREFIX_TITLE + "TITLE "
            + PREFIX_ASSIGNEES + "ASSIGNEES "
            + PREFIX_MILESTONE + "MILESTONE ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create an issue in your team organisation. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_ASSIGNEES + "ASSIGNEES "
            + PREFIX_MILESTONE + "MILESTONE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Add new create issue command "
            + PREFIX_ASSIGNEES + "johndoe "
            + PREFIX_MILESTONE + "v1.1 ";
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
            GitHub github = GitHub.connect();
            GHRepository repository = github.getRepository("AdityaA1998/samplerepo-pr-practice");
            GHIssueBuilder issueBuilder = repository.createIssue("Ayushi");
            issueBuilder.body("Test ISSUE");
            issueBuilder.label("shag");
            GHIssue issue = issueBuilder.create();
        } catch (IOException e) {
            throw new CommandException("Impossible de cr�er le ticket gitHub");
        }
        return new CommandResult(COMMAND_FORMAT);
    }

}
