package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class RateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().withRating("1").withEmail("alice@example.com").build();
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().build();
        descriptor.setRating(new Rating("1"));

        RateCommand rateCommand = new RateCommand(INDEX_FIRST_PERSON, descriptor);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }
}
