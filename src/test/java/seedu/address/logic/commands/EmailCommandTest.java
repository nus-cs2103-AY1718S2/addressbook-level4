package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.DelivDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author mattbuot
public class EmailCommandTest {

    private EmailCommand command;
    private Model model;

    @Before
    public void setup() {
        model = new ModelManager();
        command = new EmailCommand();
        command.setData(model, null, null);
    }

    @Test
    public void executeWithEmptyModel() {

        assertCommandFailure(command, "Empty filtered list!");
    }

    @Test
    public void executeWithInvalidAddress()
        throws DuplicatePersonException {

        model.addPerson(new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Email("alice@gmail.com"),
                new Address("apskefzjozdked"),
                new DelivDate("2018-03-24"),
                Collections.emptySet()));


        command.setData(model, null, null);
        assertCommandFailure(command, EmailCommand.MESSAGE_ERROR);
    }

    @Test
    public void executeWithoutFilter()
            throws DuplicatePersonException {

        model.addPerson(new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Email("alice@gmail.com"),
                new Address("NUS"),
                new DelivDate("2018-03-24"),
                Collections.emptySet()));
        model.addPerson(new Person(
                new Name("Bob"),
                new Phone("98765432"),
                new Email("bob@gmail.com"),
                new Address("NTU"),
                new DelivDate("2018-03-25"),
                Collections.emptySet()));
        command.setData(model, null, null);
        assertCommandFailure(command, "The list is not filtered!");
    }

    @Test
    public void execute()
            throws DuplicatePersonException, CommandException {

        model.addPerson(new Person(
                new Name("Alice"),
                new Phone("98765432"),
                new Email("alice@gmail.com"),
                new Address("NUS"),
                new DelivDate("2018-03-24"),
                Collections.emptySet()));
        model.addPerson(new Person(
                new Name("Bob"),
                new Phone("98765432"),
                new Email("bob@gmail.com"),
                new Address("NTU"),
                new DelivDate("2018-03-24"),
                Collections.emptySet()));

        command.setData(model, null, null);
        assertCommandResult(command, EmailCommand.MESSAGE_SUCCESS);
    }

    //@@author
    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(EmailCommand emailCommand, String expectedMessage) throws CommandException {
        assertEquals(expectedMessage, emailCommand.execute().feedbackToUser);
    }

    /**
     * Asserts that an exception is thrown and that the message is the same as {@code expectedMessage}.
     */
    public static void assertCommandFailure(EmailCommand command, String expectedMessage) {
        try {
            command.execute();
        } catch (CommandException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
