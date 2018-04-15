//@@author chenchongsong
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SplitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executes_splitBalance() throws Exception {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);

        double expectedBalance1 = model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()).getMoney().toDouble() + 50.0;
        double expectedBalance2 = model.getFilteredPersonList()
                .get(INDEX_SECOND_PERSON.getZeroBased()).getMoney().toDouble() + 50.0;

        SplitCommand splitCommand = new SplitCommand(indices, 100.0);
        splitCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        splitCommand.execute();

        assertEquals(expectedBalance1,
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getMoney().toDouble(), 0.001);

        assertEquals(expectedBalance2,
                model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()).getMoney().toDouble(), 0.001);
    }
}
