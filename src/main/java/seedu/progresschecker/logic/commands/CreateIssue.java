package seedu.progresschecker.logic.commands;

import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_ASSIGNEES;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TITLE;

/**
 * Adds a person to the ProgressChecker.
 */
public class CreateIssue {

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

}
