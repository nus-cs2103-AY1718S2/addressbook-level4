package seedu.investigapptor.testutil;

import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.investigapptor.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.investigapptor.logic.commands.RegisterInvestigatorCommand;
import seedu.investigapptor.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an reg command string for adding the {@code person}.
     */
    public static String getRegCommand(Person person) {
        return RegisterInvestigatorCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns an reg command string for adding the {@code person}.
     */
    public static String getAliasRegCommand(Person person) {
        return RegisterInvestigatorCommand.COMMAND_ALIAS + " " + getPersonDetails(person);
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
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
