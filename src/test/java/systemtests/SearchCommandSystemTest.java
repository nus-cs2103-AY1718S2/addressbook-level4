package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.ISBN_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ARTEMIS;

import org.junit.Test;

import guitests.GuiRobot;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;

public class SearchCommandSystemTest extends BibliotekSystemTest {
    @Test
    public void search() throws Exception {
        /* ----------------------------------- Perform valid search operations -----==------------------------------- */

        // Note: these tests require network connection.
        assertSearchSuccess(SearchCommand.COMMAND_WORD + " hello");
        assertSearchSuccess(SearchCommand.COMMAND_WORD + TITLE_DESC_ARTEMIS + CATEGORY_DESC_ARTEMIS
                + AUTHOR_DESC_ARTEMIS);
        // Should return 0 results.
        assertSearchSuccess(SearchCommand.COMMAND_WORD + TITLE_DESC_ARTEMIS + CATEGORY_DESC_ARTEMIS
                + ISBN_DESC_ARTEMIS + AUTHOR_DESC_ARTEMIS);

        /* ----------------------------------- Perform invalid search operations ------------------------------------ */

        /* Case: no search term or parameters -> rejected */
        assertCommandFailure(SearchCommand.COMMAND_WORD, SearchCommand.MESSAGE_EMPTY_QUERY);

        /* Case: no search term or parameters -> rejected */
        assertCommandFailure("   " + SearchCommand.COMMAND_WORD + "             ", SearchCommand.MESSAGE_EMPTY_QUERY);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeaRcH hello", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and performs the verifications in {@code assertCommandExecuted(String)},<br>
     * In addition, after the web API has returned a result, also asserts that,
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the search successful message.<br>
     * 4. {@code Storage} and{@code BookListPanel} remain unchanged.<br>
     * 5. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see SearchCommandSystemTest#assertCommandExecuted(String)
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see BibliotekSystemTest#assertSelectedBookListCardChanged(Index)
     */
    private void assertSearchSuccess(String command) throws Exception {
        Model expectedModel = getModel();
        assertCommandExecuted(command);
        String resultMessage = getResultDisplay().getText();
        new GuiRobot().waitForEvent(() -> !resultMessage.equals(getResultDisplay().getText()));

        BookShelf searchResults = new BookShelf();
        searchResults.setBooks(getModel().getSearchResultsList());
        expectedModel.updateSearchResults(searchResults);

        assertApplicationDisplaysExpected("",
                String.format(SearchCommand.MESSAGE_SEARCH_SUCCESS, searchResults.getBookList().size()), expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the search in progress message.<br>
     * 4. {@code Model}, {@code Storage}, and {@code BookListPanel} remain unchanged.<br>
     * 5. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see BibliotekSystemTest#assertSelectedBookListCardChanged(Index)
     */
    private void assertCommandExecuted(String command) {
        Model expectedModel = getModel();
        expectedModel.updateSearchResults(new BookShelf());

        executeCommand(command);
        assertApplicationDisplaysExpected("", SearchCommand.MESSAGE_SEARCHING, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code SearchResultsPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedSearchResultsCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
