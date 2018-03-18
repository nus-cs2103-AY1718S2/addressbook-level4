package seedu.recipe.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.recipe.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.recipe.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.recipe.testutil.TypicalPersons.getTypicalRecipeBook;

import org.junit.Test;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.person.Person;
import seedu.recipe.model.person.exceptions.PersonNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // undo() should cause the model's filtered list to show all persons
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalRecipeBook(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // redo() should cause the model's filtered list to show all persons
        dummyCommand.redo();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first person in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Person personToDelete = model.getFilteredPersonList().get(0);
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                fail("Impossible: personToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
