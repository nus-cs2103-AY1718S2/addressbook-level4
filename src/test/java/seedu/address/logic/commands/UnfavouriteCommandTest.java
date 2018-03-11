package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UnfavouriteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person unfavouriteTargetPerson = new PersonBuilder(targetPerson).withFavourite(false).build();
        UnfavouriteCommand unfavouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnfavouriteCommand.MESSAGE_SUCCESS, unfavouriteTargetPerson);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(targetPerson, unfavouriteTargetPerson);

        assertCommandSuccess(unfavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnfavouriteCommand unfavouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unfavouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private UnfavouriteCommand prepareCommand(Index index) {
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(index);
        unfavouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unfavouriteCommand;
    }
}
