package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.DelivDate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;

public class AutocompleterTest {
    private Model model;
    private Autocompleter autocompleter;

    @Before
    public void setUp() {
        model = new ModelManager();
        autocompleter = new Autocompleter(model.getAddressBook().getPersonList());
    }

    @Test
    public void completeCommand() {
        if (SelectCommand.COMMAND_WORD.length() > 2) {
            String commandPrefix = SelectCommand.COMMAND_WORD.substring(0, SelectCommand.COMMAND_WORD.length() - 1);
            assertEquals(SelectCommand.COMMAND_WORD.substring(SelectCommand.COMMAND_WORD.length() - 1),
                    autocompleter.autocomplete(commandPrefix));
        }
    }

    @Test
    public void completeField() throws DuplicatePersonException {

        model.addPerson(
                new Person(
                        new Name("John"),
                        new Phone("98765432"),
                        new Email("johndoe@test.com"),
                        new Address("NUS"),
                        new DelivDate("2018-03-24"),
                        Collections.emptySet()));

        autocompleter = new Autocompleter(model.getAddressBook().getPersonList());

        String query = "find John";
        String prefix = query.substring(0, query.length() - 3);
        assertEquals(query.substring(query.length() - 3), autocompleter.autocomplete(prefix));
    }

    @Test
    public void completeOptions() throws DuplicatePersonException {
        model.addPerson(
                new Person(
                        new Name("John"),
                        new Phone("98765432"),
                        new Email("johndoe@test.com"),
                        new Address("NUS"),
                        new DelivDate("2018-03-24"),
                        Collections.emptySet()));

        autocompleter = new Autocompleter(model.getAddressBook().getPersonList());

        String query = "add n/";
        query += autocompleter.autocomplete(query);
        query += " p/";
        query += autocompleter.autocomplete(query);
        query += " e/";
        query += autocompleter.autocomplete(query);
        query += " a/";
        query += autocompleter.autocomplete(query);
        query += " d/";
        query += autocompleter.autocomplete(query);
        assertEquals("add n/John p/98765432 e/johndoe@test.com a/NUS d/2018-03-24", query);

    }
}
