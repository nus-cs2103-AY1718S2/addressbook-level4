package guitests.guihandles;

import java.util.logging.Logger;

import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final PersonListPanelHandle personListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BrowserPanelHandle browserPanel;
    private ReportPanelHandle reportPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
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

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }

    /**
     * Check if ReportPanel is currently open.
     */
    public boolean isReportPanelOpenning() {
        try {
            this.reportPanel = new ReportPanelHandle(getChildNode(ReportPanelHandle.REPORT_ID));
            return true;
        } catch (NodeNotFoundException e) {
            return false;
        }
    }

    public ReportPanelHandle getReportPanel() {
        try {
            this.reportPanel = new ReportPanelHandle(getChildNode(ReportPanelHandle.REPORT_ID));
            return reportPanel;
        } catch (NodeNotFoundException e) {
            logger.warning("Report Panel is not openning.");
            return null;
        }
    }
}
