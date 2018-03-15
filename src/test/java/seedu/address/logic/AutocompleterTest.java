package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.*;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import java.util.Collections;

public class AutocompleterTest {
    private Model model;
    private Logic logic;
    private Autocompleter autocompleter;

    @Before
    public void setUp() {
        model = new ModelManager();
        logic = new LogicManager(model);
        autocompleter = new Autocompleter(logic);
    }

    @Test
    public void completeCommand() {
        if(SelectCommand.COMMAND_WORD.length() > 2) {
            String commandPrefix = SelectCommand.COMMAND_WORD.substring(0, SelectCommand.COMMAND_WORD.length() - 1);
            assertEquals(SelectCommand.COMMAND_WORD.substring(SelectCommand.COMMAND_WORD.length() - 1), autocompleter.autocomplete(commandPrefix));
        }
    }

    @Test
    public void completeField() {
        try {
            model.addPerson(new Person(new Name("John Doe"), new Phone("98765432"), new Email("johndoe@test.com"), new Address("NUS"), Collections.emptySet()));
        } catch (DuplicatePersonException e) { }

        autocompleter = new Autocompleter(logic);

        String query = "find John Doe";
        String prefix = query.substring(0, query.length() - 6);
        assertEquals(query.substring(query.length() - 6), autocompleter.autocomplete(prefix));
    }
}
