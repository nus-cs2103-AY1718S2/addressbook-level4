package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetailsWithoutStatus(person);
    }

    /**
     * Returns an add command string, using the add command alias, for adding the (@code person).
     */
    public static String getAddCommandAliased(Person person) {
        return AddCommand.COMMAND_WORD_ALIAS + " " + getPersonDetailsWithoutStatus(person);
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
        sb.append(PREFIX_PRICE + person.getPrice().value + " ");
        sb.append(PREFIX_SUBJECT + person.getSubject().value + " ");
        sb.append(PREFIX_LEVEL + person.getLevel().value + " ");
        sb.append(PREFIX_STATUS + person.getStatus().value + " ");
        sb.append(PREFIX_ROLE + person.getRole().value + " ");

        person.getTags().stream().forEach(
            s -> {
                if (s.tagType == Tag.AllTagTypes.DEFAULT) {
                    sb.append(PREFIX_TAG + s.tagName + " ");
                }
            });
        return sb.toString();
    }

    public static String getPersonDetailsWithoutStatus(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_PRICE + person.getPrice().value + " ");
        sb.append(PREFIX_SUBJECT + person.getSubject().value + " ");
        sb.append(PREFIX_LEVEL + person.getLevel().value + " ");
        sb.append(PREFIX_ROLE + person.getRole().value + " ");

        person.getTags().stream().forEach(
            s -> {
                if (s.tagType == Tag.AllTagTypes.DEFAULT) {
                    sb.append(PREFIX_TAG + s.tagName + " ");
                }
            });
        return sb.toString();
    }
}
