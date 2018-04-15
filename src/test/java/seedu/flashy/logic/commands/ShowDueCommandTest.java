package seedu.flashy.logic.commands;

import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;
import seedu.flashy.model.card.Card;

//@@author pukipuki
public class ShowDueCommandTest {
    private Model model;
    private LocalDateTime todaysDate;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardBank(), new UserPrefs());
        todaysDate = LocalDate.now().atStartOfDay();
    }

    @Test
    public void execute_correctMessage_success() {
        ShowDueCommand showDueCommand = prepareCommand(todaysDate);
        String expectedMessage = String.format(ShowDueCommand.MESSAGE_SUCCESS,
            todaysDate.toLocalDate().toString(), "");
        assertCommandSuccess(showDueCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_listsCorrectly_success() {
        ShowDueCommand showDueCommand = prepareCommand(todaysDate);
        ObservableList<Card> list = model.getFilteredCardList();
        showDueCommand.execute();
        assert(!list.isEmpty());

        model.showAllCards();
        showDueCommand = prepareCommand(todaysDate.minusYears(1L));
        showDueCommand.execute();
        assert(list.isEmpty());

        model.showAllCards();
        showDueCommand = prepareCommand(todaysDate.plusYears(1L));
        showDueCommand.execute();
        assert(!list.isEmpty());
    }

    /**
     * Returns a {@code ShowDueCommand} with parameters {@code date}.
     */
    private ShowDueCommand prepareCommand(LocalDateTime date) {
        ShowDueCommand showDueCommand = new ShowDueCommand(date);
        showDueCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return showDueCommand;
    }
}
