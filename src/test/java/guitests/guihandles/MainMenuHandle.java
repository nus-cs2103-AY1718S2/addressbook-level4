package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * Provides a handle to the main menu of the app.
 */
public class MainMenuHandle extends NodeHandle<Node> {
    public static final String MENU_BAR_ID = "#menuBar";

    public MainMenuHandle(Node mainMenuNode) {
        super(mainMenuNode);
    }

    /**
     * Opens the {@code HelpWindow} using the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingMenu() {
        clickOnMenuItemsSequentially("Help", "F1");
    }

    /**
     * Populates the {@code CommandBox} with the FindCommand template
     * using the menu bar in {@code MainWindow}.
     */
    public void populateFindCommandUsingMenu() {
        clickOnMenuItemsSequentially("View", "Find...");
    }

    /**
     * Populates the {@code CommandBox} with the AddCommand template
     * using the menu bar in {@code MainWindow}.
     */
    public void populateAddCommandUsingMenu() {
        clickOnMenuItemsSequentially("Actions", "Add a Person...");
    }

    /**
     * Populates the {@code CommandBox} with the DeleteCommand template
     * using the menu bar in {@code MainWindow}.
     */
    public void populateDeleteCommandUsingMenu() {
        clickOnMenuItemsSequentially("Actions", "Delete a Person...");
    }

    /**
     * Populates the {@code CommandBox} with the EditCommand template
     * using the menu bar in {@code MainWindow}.
     */
    public void populateEditCommandUsingMenu() {
        clickOnMenuItemsSequentially("Actions", "Edit a Person...");
    }

    /**
     * Populates the {@code CommandBox} with the LocateCommand template
     * using the menu bar in {@code MainWindow}.
     */
    public void populateLocateCommandUsingMenu() {
        clickOnMenuItemsSequentially("Actions", "Locate a Person...");
    }

    /**
     * Populates the {@code CommandBox} with the SelectCommand template
     * using the menu bar in {@code MainWindow}.
     */
    public void populateSelectCommandUsingMenu() {
        clickOnMenuItemsSequentially("Actions", "Select a Person...");
    }

    /**
     * Opens the {@code HelpWindow} by pressing the shortcut key associated
     * with the menu bar in {@code MainWindow}.
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }

    /**
     * Populates the {@code CommandBox} with the FindCommand template
     * by pressing the shortcut key associated with the menu bar in {@code MainWindow}.
     */
    public void populateFindCommandUsingAccelerator() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.F);
    }

    /**
     * Populates the {@code CommandBox} with the AddCommand template
     * by pressing the shortcut key associated with the menu bar in {@code MainWindow}.
     */
    public void populateAddCommandUsingAccelerator() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.SPACE);
    }

    /**
     * Populates the {@code CommandBox} with the DeleteCommand template
     * by pressing the shortcut key associated with the menu bar in {@code MainWindow}.
     */
    public void populateDeleteCommandUsingAccelerator() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.D);
    }

    /**
     * Populates the {@code CommandBox} with the EditCommand template
     * by pressing the shortcut key associated with the menu bar in {@code MainWindow}.
     */
    public void populateEditCommandUsingAccelerator() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.E);
    }

    /**
     * Populates the {@code CommandBox} with the LocateCommand template
     * by pressing the shortcut key associated with the menu bar in {@code MainWindow}.
     */
    public void populateLocateCommandUsingAccelerator() {
        guiRobot.push(KeyCode.CONTROL, KeyCode.L);
    }

    /**
     * Clicks on {@code menuItems} in order.
     */
    public void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(guiRobot::clickOn);
    }

    //@@author jonleeyz
    /**
     * Simulates press of given keyboard shortcut
     */
    public void useAccelerator(KeyCode... combination) {
        guiRobot.push(combination);
    }
    //@@author
}
