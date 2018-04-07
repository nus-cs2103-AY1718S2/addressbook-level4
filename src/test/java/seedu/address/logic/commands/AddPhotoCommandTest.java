package seedu.address.logic.commands;
//@@author crizyli
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddPhotoCommand}.
 */
public class AddPhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());





    /**
     * Returns a {@code AddPhotoCommand} with the parameter {@code index}.
     */
    private AddPhotoCommand prepareCommand(Index index) {
        AddPhotoCommand addPhotoCommand = new AddPhotoCommand(index);
        addPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPhotoCommand;
    }
}
