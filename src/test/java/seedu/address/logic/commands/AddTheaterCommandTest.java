package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMOFNEWTHEATERS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMOFTHEATERS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showCinemaAtIndex;
import static seedu.address.testutil.TypicalCinemas.getTypicalMoviePlanner;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CINEMA;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CINEMA;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.email.EmailManager;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.MoviePlanner;
import seedu.address.model.UserPrefs;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Theater;
import seedu.address.testutil.CinemaBuilder;
//@@author tinyjy94
/**
 * Contains integration tests (interaction with the Model, UndoCommand) and unit tests for AddTheaterCommand.
 */

public class AddTheaterCommandTest {

    private Model model = new ModelManager(getTypicalMoviePlanner(), new UserPrefs(), new EmailManager());

    @Test
    public void execute_invalidCinemaIndexFilteredList_failure() {
        showCinemaAtIndex(model, INDEX_FIRST_CINEMA);
        Index outOfBoundIndex = INDEX_SECOND_CINEMA;
        // ensures that outOfBoundIndex is still in bounds of movie planner list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMoviePlanner().getCinemaList().size());
        Cinema resizedCinema = new CinemaBuilder().build();

        AddTheaterCommand addTheaterCommand = prepareCommand(outOfBoundIndex, resizedCinema);

        assertCommandFailure(addTheaterCommand, model, Messages.MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        Cinema resizedCinema = new CinemaBuilder().build();
        AddTheaterCommand addTheaterCommand = prepareCommand(INDEX_FIRST_CINEMA, resizedCinema);
        Model expectedModel = new ModelManager(new MoviePlanner(model.getMoviePlanner()), new UserPrefs(),
                                              new EmailManager());

        // edit -> first cinema edited
        addTheaterCommand.execute();
        undoRedoStack.push(addTheaterCommand);

        // undo -> reverts movieplanner back to previous state and filtered cinema list to show all cinemas
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ArrayList<Theater> firstTheater = new ArrayList<>(VALID_NUMOFTHEATERS);
        ArrayList<Theater> secondTheater = new ArrayList<>(VALID_NUMOFNEWTHEATERS);
        ArrayList<Theater> thirdTheater = new ArrayList<>(VALID_NUMOFTHEATERS);
        AddTheaterCommand firstTheaterCommand = new AddTheaterCommand(INDEX_FIRST_CINEMA, firstTheater.size());
        AddTheaterCommand secondTheaterCommand = new AddTheaterCommand(INDEX_SECOND_CINEMA, secondTheater.size());
        AddTheaterCommand thirdTheaterCommand = new AddTheaterCommand(INDEX_FIRST_CINEMA, thirdTheater.size());

        // null -> returns false
        assertFalse(firstTheaterCommand.equals(null));

        // not equals -> returns false
        assertFalse(firstTheaterCommand.equals(secondTheaterCommand));

        // same value -> returns true
        assertTrue(firstTheaterCommand.equals(thirdTheaterCommand));

        // same object -> returns true
        assertTrue(firstTheaterCommand.equals(firstTheaterCommand));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private AddTheaterCommand prepareCommand (Index index, Cinema cinema) {
        AddTheaterCommand addTheaterCommand = new AddTheaterCommand(index, cinema.getTheaters().size());
        addTheaterCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTheaterCommand;
    }
}
