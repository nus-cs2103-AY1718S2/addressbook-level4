//@@author emer7
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import guitests.GuiRobot;
import guitests.guihandles.ReviewDialogHandle;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.commands.UnlockCommand;

public class ReviewCommandSystemTest extends AddressBookSystemTest {

    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void openReviewDialog() {
        String password = getModel().getPassword();
        UnlockCommand testUnlockCommand = new UnlockCommand();
        testUnlockCommand.setTestMode();
        testUnlockCommand.setData(getModel(), new CommandHistory(), new UndoRedoStack());
        testUnlockCommand.execute();
        showAllPersons();

        String command = "     " + ReviewCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        executeCommand(command);
        assertReviewDialogOpen();
    }

    /**
     * Asserts that the help window is open, and closes it after checking.
     */
    private void assertReviewDialogOpen() {
        assertTrue(ERROR_MESSAGE, ReviewDialogHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new ReviewDialogHandle(guiRobot.getStage(ReviewDialogHandle.REVIEW_DIALOG_WINDOW_TITLE)).close();
        getMainWindowHandle().focus();
    }
}
