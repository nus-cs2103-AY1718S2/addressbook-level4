//@@author IzHoBX
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class RateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().withRating("1").withEmail("alice@example.com")
                .withReviews("supervisor@example.com\nLazy")
                .build();
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withRating("1").build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code RateCommand} with parameters {@code index} and {@code descriptor}
     */
    private RateCommand prepareCommand(Index index, EditCommand.EditPersonDescriptor descriptor) {
        RateCommand rateCommand = new RateCommand(index, descriptor);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return rateCommand;
    }
}
