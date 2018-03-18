package seedu.progresschecker.testutil;

import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.progresschecker.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.progresschecker.logic.commands.AddCommand;
import seedu.progresschecker.model.person.Person;

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
        sb.append(PREFIX_MAJOR + person.getMajor().value + " ");
        sb.append(PREFIX_YEAR + person.getYear().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
