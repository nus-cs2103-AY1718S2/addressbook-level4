package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILLS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.PostJobCommand;
import seedu.address.model.job.Job;

//@@author ChengSashankh
/***
 * A utility class for Job.
 */
public class JobUtil {

    /**
     * Returns an addjob command string for adding the {@code job}.
     */
    public static String getPostJobCommand(Job job) {
        return PostJobCommand.COMMAND_WORD + " " + getJobDetails(job);
    }

    /**
     * Returns the part of command string for the given {@code job}'s details.
     */
    public static String getJobDetails(Job job) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_JOBTITLE + job.getJobTitle().fullTitle + " ");
        sb.append(PREFIX_LOCATION + job.getLocation().value + " ");
        sb.append(PREFIX_SKILLS + job.getSkills().value + " ");
        job.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }
}
//@@author
