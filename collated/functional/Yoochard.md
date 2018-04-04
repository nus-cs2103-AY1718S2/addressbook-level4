# Yoochard
###### /test/java/systemtests/AddressBookSystemTest.java
``` java
    /**
     * Asserts that the command box and result display shows the default style.
     */
    protected void assertCommandBoxAndResultDisplayShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
        assertEquals(defaultStyleOfResultDisplay, getResultDisplay().getStyleClass());
    }

    /**
     * Asserts that the command box and result display shows the error style.
     */
    protected void assertCommandBoxAndResultDisplayShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
        assertEquals(errorStyleOfResultDisplay, getResultDisplay().getStyleClass());
    }

```
###### /test/java/seedu/address/ui/ResultDisplayTest.java
``` java
    private static final NewResultAvailableEvent NEW_RESULT_SUCCESS_EVENT_STUB =
            new NewResultAvailableEvent("success", true);
    private static final NewResultAvailableEvent NEW_RESULT_FAILURE_EVENT_STUB =
            new NewResultAvailableEvent("failure", false);
    private List<String> defaultStyleOfResultDisplay;
    private List<String> errorStyleOfResultDisplay;

    private ResultDisplayHandle resultDisplayHandle;
```
###### /test/java/seedu/address/ui/ResultDisplayTest.java
``` java
    @Before
    public void setUp() {
        ResultDisplay resultDisplay = new ResultDisplay();
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle(getChildNode(resultDisplay.getRoot(),
                ResultDisplayHandle.RESULT_DISPLAY_ID));

        defaultStyleOfResultDisplay = new ArrayList<>(resultDisplayHandle.getStyleClass());
        defaultStyleOfResultDisplay.remove(ResultDisplay.SUGGESTION_STYLE_CLASS);

        errorStyleOfResultDisplay = new ArrayList<>(defaultStyleOfResultDisplay);
        errorStyleOfResultDisplay.add(ResultDisplay.ERROR_STYLE_CLASS);
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        resultDisplayHandle.getStyleClass().remove(ResultDisplay.SUGGESTION_STYLE_CLASS);
        assertEquals(ResultDisplay.WELCOME_MESSAGE, resultDisplayHandle.getText());
        assertEquals(defaultStyleOfResultDisplay, resultDisplayHandle.getStyleClass());

        // receiving new results
        assertResultDisplay(NEW_RESULT_SUCCESS_EVENT_STUB);
        assertResultDisplay(NEW_RESULT_FAILURE_EVENT_STUB);
    }

    /**
     * Posts the {@code event} to the {@code EventsCenter}, then verifies that <br>
     *      - the text on the result display matches the {@code event}'s message <br>
     *      - the result display's style is the same as {@code defaultStyleOfResultDisplay} if event is successful,
     *        {@code errorStyleOfResultDisplay} otherwise.
     */
    private void assertResultDisplay(NewResultAvailableEvent event) {
        postNow(event);
        guiRobot.pauseForHuman();

        List<String> expectedStyleClass = event.isSuccessful ? defaultStyleOfResultDisplay : errorStyleOfResultDisplay;
        resultDisplayHandle.getStyleClass().remove(ResultDisplay.SUGGESTION_STYLE_CLASS);

        assertEquals(event.message, resultDisplayHandle.getText());
        assertEquals(expectedStyleClass, resultDisplayHandle.getStyleClass());
    }
}
```
###### /test/java/seedu/address/ui/CommandBoxTest.java
``` java
    /**
     * Runs a command that fails, then verifies that <br>
     *      - the text remains <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForFailedCommand() {
        commandBoxHandle.run(COMMAND_THAT_FAILS);
        assertFalse(((NewResultAvailableEvent) eventsCollectorRule.eventsCollector.getMostRecent()).isSuccessful);
        eventsCollectorRule.eventsCollector.reset();
        assertEquals(COMMAND_THAT_FAILS, commandBoxHandle.getInput());
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that <br>
     *      - the text is cleared <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForSuccessfulCommand() {
        commandBoxHandle.run(COMMAND_THAT_SUCCEEDS);
        assertTrue(((NewResultAvailableEvent) eventsCollectorRule.eventsCollector.getMostRecent()).isSuccessful);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() > 1);
        eventsCollectorRule.eventsCollector.reset();
        assertEquals("", commandBoxHandle.getInput());
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }
```
###### /test/java/seedu/address/logic/parser/SortCommandParserTest.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

/**
 * Test scope: To test SortCommandParser.
 * @see DeleteCommandParserTest
 */
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void no_arguments_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
###### /test/java/seedu/address/logic/commands/SortCommandTest.java
``` java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code LockCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        SortCommand firstSortCommand = new SortCommand("rate");
        SortCommand secondSortCommand = new SortCommand("name");

        // same object -> returns true
        assertTrue(firstSortCommand.equals(firstSortCommand));

        // same values -> returns true
        SortCommand secondSortCommandcopy = new SortCommand("name");
        assertTrue(secondSortCommand.equals(secondSortCommandcopy));

        // different types -> returns false
        assertFalse(secondSortCommand.equals(1));

        // null -> returns false
        assertFalse(firstSortCommand.equals(null));

        // different value -> returns false
        assertFalse(firstSortCommand.equals(secondSortCommand));
    }

    @Test
    public void sortSuccess() {
        SortCommand testSortCommand = new SortCommand("rate");
        testSortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = SortCommand.MESSAGE_SORT_EMPLOYEE_SUCCESS;
        try {
            CommandResult commandResult = testSortCommand.execute();
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }



}
```
###### /test/java/seedu/address/logic/commands/ChangeThemeCommandTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEME_NAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class ChangeThemeCommandTest {

    private UserPrefs userPrefs;
    private Model model;
    private GuiSettings guiSettings;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        userPrefs = new UserPrefs();
        guiSettings = userPrefs.getGuiSettings();
    }

    @Test
    public void executeChangeThemeCommandSuccess() throws Exception {
        ChangeThemeCommand changeThemeCommand = prepareCommand("dark");

        String expectedMessage = String.format(ChangeThemeCommand.MESSAGE_CHANGE_THEME_SUCCESS, "dark");

        UserPrefs expectedUserPrefs = new UserPrefs();
        expectedUserPrefs.setGuiSettings(
                guiSettings.getWindowHeight(),
                guiSettings.getWindowWidth(),
                guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y,
                guiSettings.getTheme());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), expectedUserPrefs);

        assertCommandSuccess(changeThemeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeChangeThemeCommandInvalidThemeNameFailure() throws Exception {
        ChangeThemeCommand changeThemeCommand = prepareCommand(INVALID_THEME_NAME);

        String expectedMessage = String.format(ChangeThemeCommand.MESSAGE_INVALID_THEME_NAME, INVALID_THEME_NAME);

        assertCommandFailure(changeThemeCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeThemeCommand themeDarkTheme = new ChangeThemeCommand("dark");
        ChangeThemeCommand themeBrightTheme = new ChangeThemeCommand("bright");

        // same theme -> returns true
        assertTrue(themeDarkTheme.equals(themeDarkTheme));
        assertTrue(themeBrightTheme.equals(themeBrightTheme));

        // invalid types-> returns false
        assertFalse(themeDarkTheme.equals(1));

        // null -> returns false
        assertFalse(themeDarkTheme.equals(null));

        // different themes -> returns false
        assertFalse(themeDarkTheme.equals(themeBrightTheme));
    }

    /**
     * Returns an {@code ChangeThemeCommand} with parameters {@code theme}
     */
    private ChangeThemeCommand prepareCommand(String theme) {
        ChangeThemeCommand changeThemeCommand = new ChangeThemeCommand(theme);
        changeThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeThemeCommand;
    }

}
```
###### /test/java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sort(String field) {
            fail("This method should not be called.");
        }
```
###### /test/java/guitests/guihandles/ResultDisplayHandle.java
``` java
    /**
     * Returns the list of style classes present in the result display.
     */
    public List<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
```
###### /main/resources/view/DarkTheme.css
``` css
/* Part of css file, not included whole file*/
.background {
    -fx-background-color: derive(#1d1d1d, 20%);
    background-color: #383838; /* Used in the default.html file */
}

.label {
    -fx-font-size: 13pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}


.label-bright {
    -fx-font-size: 13pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
            transparent
            transparent
            derive(-fx-base, 80%)
            transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#1d1d1d, 20%);
}

.list-view {
     -fx-background-insets: 0;
     -fx-padding: 0;
     -fx-background-color: derive(#1d1d1d, 20%);
 }

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #424d5f;
}

.list-cell:filled:odd {
    -fx-background-color: slategrey;
}

.list-cell:filled:selected {
    -fx-background-color: steelblue;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

```
###### /main/resources/view/PersonListCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.co
m/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>

      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets top="5" right="5" bottom="5" left="15" />
      </padding>
      <HBox spacing="5" alignment="CENTER_LEFT">
        <Label fx:id="id" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
      </HBox>
        <FlowPane fx:id="tags" />
```
###### /main/resources/view/BrightTheme
``` 
.background {
    -fx-background-color: derive(floralwhite, 20%);
    background-color: white; /* Used in the default.html file */
}

.label {
    -fx-font-size: 13pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}


.label-bright {
    -fx-font-size: 13pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
            transparent
            transparent
            derive(-fx-base, 80%)
            transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#1d1d1d, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#1d1d1d, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: blue;
}

.list-cell:filled:odd {
    -fx-background-color: cornflowerblue;
}

.list-cell:filled:selected {
    -fx-background-color: royalblue;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.notification-scroll-pane > .viewport {
    -fx-background-color: #3366cc;
}

.notification-scroll-pane {
    -fx-background-color: #3366cc;
}

.notification-card-first-stage {
    -fx-background-color: #3399ff;
    -fx-text-fill: #000000;
    -fx-opacity: 0.8;
}

.notification-card-second-stage {
    -fx-background-color: #cc3300;
    -fx-text-fill: #000000;
    -fx-opacity: 0.8;
}

.notification-card-notification-center-first-stage {
    -fx-background-color: #3399ff;
    -fx-text-fill: #000000;
    -fx-opacity: 1;
}

.notification-card-notification-center-second-stage {
    -fx-background-color: #cc3300;
    -fx-text-fill: #000000;
    -fx-opacity: 1;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: white;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: white;
}

.cell_small_label_rating {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #FFFF00;
}

.anchor-pane {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.pane-with-border {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-border-color: derive(#1d1d1d, 10%);
    -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-text-fill: #1d1d1d;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
}

.context-menu {
    -fx-background-color: derive(#1d1d1d, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.menu-bar .label {
    -fx-font-size: 15pt;
    -fx-font-family: "Times New Roman";
    -fx-text-fill: black;
    -fx-opacity: 0.95;
}

.menu .left-container {
    -fx-background-color: mediumslateblue;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
    -fx-background-color: white;
    -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#1d1d1d, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 13px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 2;
    -fx-font-family: "Courier New";
    -fx-font-size: 15pt;
    -fx-text-fill: darkorange;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #383838, transparent, #383838;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 13;
}

#tags .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#tags .yellow {
    -fx-background-color: yellow;
    -fx-text-fill: black;
}

#tags .blue {
    -fx-text-fill: white;
    -fx-background-color: blue;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#tags .brown {
    -fx-text-fill: white;
    -fx-background-color: brown;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: green;
}
```
###### /main/java/seedu/address/ui/MainWindow.java
``` java
    @FXML
    private void handleChangeDarkTheme() {
        EventsCenter.getInstance().post(new ChangeThemeEvent("dark"));
    }

    @FXML
    private void handleChangeBrightTheme() {
        EventsCenter.getInstance().post(new ChangeThemeEvent("bright"));
    }

    void show() {
        primaryStage.show();
    }

```
###### /main/java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleChangeThemeEvent (ChangeThemeEvent changeThemeEvent) {
        Scene scene = primaryStage.getScene();
        // Clear the original theme
        scene.getStylesheets().clear();

        String newTheme = changeThemeEvent.getTheme();
        String cssFileName = null;

        // Get the associate CSS file path for theme
        switch (newTheme) {
        case "dark":
            cssFileName = DARK_THEME_CSS_FILE_NAME;
            break;
        case "bright":
            cssFileName = BRIGHT_THEME_CSS_FILE_NAME;
            break;
        default:
            cssFileName = DARK_THEME_CSS_FILE_NAME;
            //Theme.changeTheme(primaryStage, changeThemeEvent.getTheme());
        }

        scene.getStylesheets().add(cssFileName);
        primaryStage.setScene(scene);
    }

```
###### /main/java/seedu/address/ui/ResultDisplay.java
``` java

    /**
     * Sets the {@code ResultDisplay} style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the {@code ResultDisplay} style to use the default style.
     */
    private void setStyleToIndicateCommandSuccess() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

```
###### /main/java/seedu/address/ui/PersonCard.java
``` java
    //Part of code is referenced to Developer Guide
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        rating.setText(person.getRatingDisplay());
        rating.setTextFill(Color.RED);
        initTags(person);
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
###### /main/java/seedu/address/commons/core/GuiSettings.java
``` java
    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition, String theme) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
        this.theme = theme;
    }
```
###### /main/java/seedu/address/commons/core/GuiSettings.java
``` java
    public String getTheme() {
        return theme;
    }
```
###### /main/java/seedu/address/commons/events/ui/NewResultAvailableEvent.java
``` java

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final boolean isSuccessful;

    public NewResultAvailableEvent(String message, boolean isSuccessful) {
        this.message = message;
        this.isSuccessful = isSuccessful;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /main/java/seedu/address/commons/events/ui/ChangeThemeEvent.java
``` java

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for Changing theme
 */
public class ChangeThemeEvent extends BaseEvent {
    private String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /main/java/seedu/address/logic/parser/ChangeThemeCommandParser.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 *  Creates a ChangeThemeCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeThemeCommand
     * and returns an ChangeThemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ChangeThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }
        return new ChangeThemeCommand(trimmedArgs);
    }
}
```
###### /main/java/seedu/address/logic/parser/SortCommandParser.java
``` java

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the Sort
     * and returns an SortCommand object for execution.
     * @throws ParseException if the input field does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(trimmedArgs);
    }
}
```
###### /main/java/seedu/address/logic/commands/ChangeThemeCommand.java
``` java

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Change existing theme
 */
public class ChangeThemeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changeTheme";

    public static final String DARK_THEME_CSS_FILE_NAME = "view/DarkTheme.css";
    public static final String BRIGHT_THEME_CSS_FILE_NAME = "view/BrightTheme.css";

    public static final String[] THEME_NAMES = {"dark", "bright"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the theme of the application. "
            + "Parameters: " + COMMAND_WORD + " "
            + "[THEME]\n"
            + "Example: " + COMMAND_WORD + " bright";

    public static final String MESSAGE_INVALID_THEME_NAME = "%1$s theme is not supported. "
            + "You can change your theme to one of these: "
            + Arrays.toString(THEME_NAMES);

    public static final String MESSAGE_CHANGE_THEME_SUCCESS = "You have successfully changed your theme.";

    private final String theme;

    /**
     * Constructor
     * @param theme the new theme
     */
    public ChangeThemeCommand(String theme) {
        requireNonNull(theme);
        this.theme = theme;
    }

    /**
     * Returns true if the input string is valid
     */
    public static boolean isValidThemeName(String theme) {
        return Arrays.asList(THEME_NAMES).contains(theme);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        // Check whether a given theme name is valid
        if (!isValidThemeName(theme)) {
            throw new CommandException(String.format(MESSAGE_INVALID_THEME_NAME, theme));
        }

        EventsCenter.getInstance().post(new ChangeThemeEvent(theme));

        return new CommandResult(String.format(MESSAGE_CHANGE_THEME_SUCCESS, theme));
    }
}
```
###### /main/java/seedu/address/logic/commands/SortCommand.java
``` java

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sorts the employees by any field
 */

public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String[] SORT_FIELD_LIST = {"name", "phone", "email", "address", "tag", "rate"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list of employees by a specific field  "
            + "Parameters: FIELD\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORT_EMPLOYEE_SUCCESS = "Employees has been sorted.";
    public static final String MESSAGE_SORT_INVALID_FIELD = "Your input field is invalid, please check again.";

    private final String sortField;

    public SortCommand(String field) {
        this.sortField = field;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        switch (sortField) {
        case "name":
        case "phone":
        case "email":
        case "address":
        case "tag":
        case "rate":
            model.sort(sortField);
            break;
        default:
            throw new CommandException(MESSAGE_SORT_INVALID_FIELD);
        }
        return new CommandResult(MESSAGE_SORT_EMPLOYEE_SUCCESS);
    }

    public String getField() {
        return sortField;
    }

    @Override
    public boolean equals(Object o) {
        return o == this
                || (o instanceof SortCommand
                && this.getField().equals(((SortCommand) o).getField())); // state check
    }
}
```
###### /main/java/seedu/address/logic/commands/UndoableCommand.java
``` java
    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (this instanceof ChangeThemeCommand) {
            Theme.changeCurrentTheme(previousTheme);
            EventsCenter.getInstance().post(new ChangeThemeEvent(previousTheme));
        }
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
```
###### /main/java/seedu/address/model/person/UniqueEmployeeList.java
``` java
    /**
     * Sorts existing persons, check sort field here
     */
    public void sort(String field) {
        switch (field) {
        case "name":
            sortByName();
            break;
        case "phone":
            sortByPhone();
            break;
        case "email":
            sortByEmail();
            break;
        case "address":
            sortByAddress();
            break;
        case "tag":
            sortByTag();
            break;
        case "rate":
            sortByRate();
            break;
        default:
            throw new AssertionError("Sort field should be name, phone, email, tag, address or rate.");
        }
    }
    /**
     * Specific sort method for every field, sort by name
     */
    public void sortByName() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getName().toString().compareToIgnoreCase(p2.getName().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is phone, sort by phone
     */
    public void sortByPhone() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getPhone().toString().compareToIgnoreCase(p2.getPhone().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is email, sort by email
     */
    public void sortByEmail() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getEmail().toString().compareToIgnoreCase(p2.getEmail().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is address, sort by address
     */
    public void sortByAddress() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getAddress().toString().compareToIgnoreCase(p2.getAddress().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is tag, sort by tag
     */
    public void sortByTag() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p1.getTags().toString().compareToIgnoreCase(p2.getTags().toString());
                return num;
            }
        });
    }

    /**
     * when the input field is rate, sort by rate in descending order
     */
    public void sortByRate() {
        Collections.sort(internalList, new Comparator<Person>() {
            public int compare(Person p1, Person p2) {
                int num = p2.getRating().toString().compareToIgnoreCase(p1.getRating().toString());
                return num;
            }
        });
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### /main/java/seedu/address/model/AddressBook.java
``` java
    /** sort the existing persons in specific field
     *
     * @param field must be String and not null
     *
     * */
    public void sort(String field) {
        persons.sort(field);
    }

```
###### /main/java/seedu/address/model/theme/Theme.java
``` java

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Represents a Tag in the address book.
 * Guarantees: notnull; name is valid as declared in {@link #isValidThemeName(String)} (String)}
 */
public class Theme {
    public static final String[] ALL_THEME = {"dark", "bright"};
    public static final String DARK_THEME_CSS_FILE_NAME = "view/DarkTheme.css";
    public static final String BRIGHT_THEME_CSS_FILE_NAME = "view/BrightTheme.css";

    private static String currentTheme;

    /**
     * Change current theme
     */
    public static void changeCurrentTheme(String currentTheme) {
        requireNonNull(currentTheme);
        assert (isValidThemeName(currentTheme));
        if (isValidThemeName(currentTheme)) {
            Theme.currentTheme = currentTheme;
        }
    }

    /**
     * Get current theme
     */
    public static String getTheme() {
        return currentTheme;
    }

    /**
     * Returns true if input theme is valid
     */
    public static boolean isValidThemeName(String inputTheme) {
        return Arrays.asList(ALL_THEME).contains(inputTheme);
    }

    /**
     * Change theme
     */
    public static void changeTheme(Stage primaryStage, String inputTheme) {

        if (isValidThemeName(inputTheme)) {
            Scene scene = primaryStage.getScene();

            String cssFileName;

            switch (inputTheme) {
            case "dark":
                cssFileName = DARK_THEME_CSS_FILE_NAME;
                break;
            case "bright":
                cssFileName = BRIGHT_THEME_CSS_FILE_NAME;
                break;
            default:
                cssFileName = DARK_THEME_CSS_FILE_NAME;
            }

            scene.getStylesheets().add(cssFileName);
            primaryStage.setScene(scene);
        }
    }
}
```
###### /main/java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void sort(String field) {
        addressBook.sort(field);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### /main/java/seedu/address/model/Model.java
``` java
    /** Sort existing employees by any field in alphabetical order */
    void sort(String field);
```
