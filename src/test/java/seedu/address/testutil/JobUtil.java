// @@author kush1509
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_POSITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.job.JobAddCommand;
import seedu.address.model.job.Job;

/**
 * A utility class for Job.
 */
public class JobUtil {

    /**
     * Returns an add command string for adding the {@code job}.
     */
    public static String getJobAddCommand(Job job) {
        return JobAddCommand.COMMAND_WORD + " " + getJobDetails(job);
    }

    /**
     * Returns the part of command string for the given {@code job}'s details.
     */
    public static String getJobDetails(Job job) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_POSITION + job.getPosition().value + " ");
        sb.append(PREFIX_TEAM + job.getTeam().value + " ");
        sb.append(PREFIX_LOCATION + job.getLocation().value + " ");
        sb.append(PREFIX_NUMBER_OF_POSITIONS + job.getNumberOfPositions().value + " ");
        job.getSkills().stream().forEach(s -> sb.append(PREFIX_SKILL + s.skillName + " "));
        return sb.toString();
    }
}
