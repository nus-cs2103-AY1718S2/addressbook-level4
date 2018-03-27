package seedu.progresschecker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueBuilder;
import org.kohsuke.github.GHMilestone;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.issues.Labels;

/**
 * Adds a person to the ProgressChecker.
 */
public class CreateIssue extends Command {

    public static final String COMMAND_WORD = "createissue";
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
            + PREFIX_LABEL + "LABELS"
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
            GitHub github = GitHub.connectUsingPassword("AdityaA1998", "Aditya@123");
            GHRepository repository = github.getRepository("AdityaA1998/samplerepo-pr-practice");
            GHIssueBuilder issueBuilder = repository.createIssue(toBeCreated.getTitle().toString());
            issueBuilder.body(toBeCreated.getBody().toString());
            //issueBuilder.label("shag");

            List<Assignees> assigneesList = toBeCreated.getAssignees();
            List<Labels> labelsList = toBeCreated.getLabelsList();

            ArrayList<GHUser> listOfUsers = new ArrayList<>();
            ArrayList<String> listOfLabels = new ArrayList<>();

            for (int i = 0; i < assigneesList.size(); i++) {
                listOfUsers.add(github.getUser(assigneesList.get(i).toString()));
            }

            for (int i = 0; i < labelsList.size(); i++) {
                listOfLabels.add(labelsList.get(i).toString());
            }

            GHMilestone check = repository.getMilestone(1);
            GHIssue issue = issueBuilder.create();
            issue.setAssignees(listOfUsers);
            issue.setLabels(listOfLabels.toArray(new String[0]));
            issue.setMilestone(check);
        } catch (IOException e) {
            throw new CommandException("Impossible de cr�er le ticket gitHub");
        }
        return new CommandResult(COMMAND_FORMAT);
    }

}
