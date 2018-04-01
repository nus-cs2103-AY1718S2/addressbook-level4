package seedu.address.testutil;

import static java.util.Objects.isNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE_POINT_AVERAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB_APPLIED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESUME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIVERSITY;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_UNIVERSITY + person.getUniversity().value + " ");
        sb.append(PREFIX_EXPECTED_GRADUATION_YEAR + person.getExpectedGraduationYear().value + " ");
        sb.append(PREFIX_MAJOR + person.getMajor().value + " ");
        sb.append(PREFIX_GRADE_POINT_AVERAGE + person.getGradePointAverage().value + " ");
        sb.append(PREFIX_JOB_APPLIED + person.getJobApplied().value + " ");

        if (!isNull(person.getResume().value)) {
            sb.append(PREFIX_RESUME + person.getResume().value + " ");
        }
        if (!isNull(person.getProfileImage().value)) {
            sb.append(PREFIX_IMAGE + person.getProfileImage().value + " ");
        }

        sb.append(PREFIX_COMMENT + person.getComment().value + " ");

        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    public static String getFilterCommand(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilterCommand.COMMAND_WORD).append(" ");
        sb.append(PREFIX_EXPECTED_GRADUATION_YEAR).append(person.getExpectedGraduationYear().value + " ");
        return sb.toString();
    }
}
