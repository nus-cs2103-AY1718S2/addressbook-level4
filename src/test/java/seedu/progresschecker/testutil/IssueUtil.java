package seedu.progresschecker.testutil;

import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.progresschecker.logic.commands.CreateIssueCommand;
import seedu.progresschecker.model.issues.Issue;

/**
 * A utility class for Issue.
 */
public class IssueUtil {

    /**
     * Returns an createIssue command string for adding the {@code issue}.
     */
    public static String getCreateIssueCommand(Issue issue) {
        return CreateIssueCommand.COMMAND_WORD + " " + getIssueDetails(issue);
    }

    /**
     * Returns the part of command string for the given {@code issue}'s details.
     */
    public static String getIssueDetails(Issue issue) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + issue.getTitle().fullMessage + " ");
        sb.append(PREFIX_BODY + issue.getBody().fullBody + " ");
        sb.append(PREFIX_MILESTONE + issue.getMilestone().fullMilestone + " ");
        issue.getAssignees().stream()
                .forEach(a -> sb.append(PREFIX_ASSIGNEES + a.fullAssignees + " "));
        issue.getLabelsList().stream()
                .forEach(l -> sb.append(PREFIX_LABEL + l.fullLabels + " "));
        return sb.toString();
    }
}

