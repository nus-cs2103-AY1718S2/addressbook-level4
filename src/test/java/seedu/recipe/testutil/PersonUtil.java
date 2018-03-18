package seedu.recipe.testutil;

import static seedu.recipe.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.model.recipe.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code recipe}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code recipe}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_INSTRUCTION + person.getInstruction().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
