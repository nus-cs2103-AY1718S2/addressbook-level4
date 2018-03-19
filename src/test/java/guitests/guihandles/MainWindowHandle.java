package guitests.guihandles;

import guitests.guihandles.exceptions.StylesheetNotFoundException;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final Scene scene;
    private final BookListPanelHandle bookListPanel;
    private final SearchResultsPanelHandle searchResultsPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BookDetailsPanelHandle bookDetailsPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        scene = stage.getScene();
        bookListPanel = new BookListPanelHandle(getChildNode(BookListPanelHandle.BOOK_LIST_VIEW_ID));
        searchResultsPanel =
                new SearchResultsPanelHandle(getChildNode(SearchResultsPanelHandle.SEARCH_RESULTS_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        bookDetailsPanel = new BookDetailsPanelHandle(getChildNode(BookDetailsPanelHandle.BOOK_DETAILS_PANE_ID));
    }

    public BookListPanelHandle getBookListPanel() {
        return bookListPanel;
    }

    public SearchResultsPanelHandle getSearchResultsPanel() {
        return searchResultsPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BookDetailsPanelHandle getBookDetailsPanel() {
        return bookDetailsPanel;
    }

    public String getActiveStylesheet() {
        if (scene.getStylesheets().size() == 0) {
            throw new StylesheetNotFoundException();
        }
        return scene.getStylesheets().get(0);
    }
}
