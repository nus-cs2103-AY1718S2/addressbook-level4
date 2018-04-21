package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

/**
 * A handle for the {@code TitleBar} at the top of the application.
 */
public class TitleBarHandle extends NodeHandle<Node> {
    public static final String TITLE_BAR_ID = "#topTitle";

    private static final String SYNC_STATUS_ID = "#topStatusMessage";
    private static final String SAVE_LOCATION_STATUS_ID = "#topStatusFile";
    private static final String CONTROL_HELP_ID = "#controlHelp";
    private static final String CONTROL_MINIMIZE_ID = "#controlMinimize";
    private static final String CONTROL_MAXIMIZE_ID = "#controlMaximize";
    private static final String CONTROL_CLOSE_ID = "#controlClose";

    private final Label syncStatusNode;
    private final Label saveLocationNode;
    private final Pane controlHelp;
    private final Pane controlMinimize;
    private final Pane controlMaximize;
    private final Pane controlClose;

    private String lastRememberedSyncStatus;
    private String lastRememberedSaveLocation;

    //@@author Ang-YC
    public TitleBarHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        this.syncStatusNode = getChildNode(SYNC_STATUS_ID);
        this.saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
        this.controlHelp = getChildNode(CONTROL_HELP_ID);
        this.controlMinimize = getChildNode(CONTROL_MINIMIZE_ID);
        this.controlMaximize = getChildNode(CONTROL_MAXIMIZE_ID);
        this.controlClose = getChildNode(CONTROL_CLOSE_ID);
    }

    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }

    /**
     * Opens the {@code HelpWindow} using the button in {@code TitleBar}.
     */
    public void openHelpWindowUsingControl() {
        new GuiRobot().clickOn(controlHelp, MouseButton.PRIMARY);
    }

    /**
     * Minimize the application using the button in {@code TitleBar}.
     */
    public void minimizeWindow() {
        new GuiRobot().clickOn(controlMinimize, MouseButton.PRIMARY);
    }

    /**
     * Maximize the application using the button in {@code TitleBar}.
     */
    public void maximizeWindow() {
        new GuiRobot().clickOn(controlMaximize, MouseButton.PRIMARY);
    }

    /**
     * Close the application using the button in {@code TitleBar}.
     */
    public void closeWindow() {
        new GuiRobot().clickOn(controlClose, MouseButton.PRIMARY);
    }
    //@@author

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncStatusNode.getText();
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return saveLocationNode.getText();
    }

    /**
     * Remembers the content of the sync status portion of the status bar.
     */
    public void rememberSyncStatus() {
        lastRememberedSyncStatus = getSyncStatus();
    }

    /**
     * Returns true if the current content of the sync status is different from the value remembered by the most recent
     * {@code rememberSyncStatus()} call.
     */
    public boolean isSyncStatusChanged() {
        return !lastRememberedSyncStatus.equals(getSyncStatus());
    }

    /**
     * Remembers the content of the 'save location' portion of the status bar.
     */
    public void rememberSaveLocation() {
        lastRememberedSaveLocation = getSaveLocation();
    }

    /**
     * Returns true if the current content of the 'save location' is different from the value remembered by the most
     * recent {@code rememberSaveLocation()} call.
     */
    public boolean isSaveLocationChanged() {
        return !lastRememberedSaveLocation.equals(getSaveLocation());
    }
}
