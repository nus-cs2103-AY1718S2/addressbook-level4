# Yoochard
###### \java\seedu\address\commons\core\GuiSettings.java
``` java
    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition, String theme) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
        this.theme = theme;
    }
```
###### \java\seedu\address\commons\core\GuiSettings.java
``` java
    public String getTheme() {
        return theme;
    }
```
###### \java\seedu\address\commons\events\ui\ChangeThemeEvent.java
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
###### \java\seedu\address\commons\events\ui\NewResultAvailableEvent.java
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
###### \java\seedu\address\logic\commands\ChangeThemeCommand.java
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme of the application.\n"
            + "Parameters: " + "THEME\n"
            + "Example: " + COMMAND_WORD + " dark";

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
###### \java\seedu\address\logic\commands\SortCommand.java
``` java

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sorts the employees by any field
 */

public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String[] SORT_FIELD_LIST = {"name", "phone", "email", "address", "tag", "rate"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts employees by a specific field.\n"
            + "Parameters: FIELD\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORT_EMPLOYEE_SUCCESS = "Employees have been sorted.";
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
###### \java\seedu\address\logic\commands\UndoableCommand.java
``` java
        if (this instanceof ChangeThemeCommand) {
            Theme.changeCurrentTheme(previousTheme);
            EventsCenter.getInstance().post(new ChangeThemeEvent(previousTheme));
        }
```
###### \java\seedu\address\logic\parser\ChangeThemeCommandParser.java
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
###### \java\seedu\address\logic\parser\SortCommandParser.java
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
###### \java\seedu\address\model\AddressBook.java
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
###### \java\seedu\address\model\Model.java
``` java
    /** Sort existing employees by any field in alphabetical order */
    void sort(String field);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sort(String field) {
        addressBook.sort(field);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\UniqueEmployeeList.java
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
###### \java\seedu\address\model\theme\Theme.java
``` java

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeEvent;

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
    public static void changeCurrentTheme(String curTheme) {
        EventsCenter.getInstance().post(new ChangeThemeEvent(curTheme));
        requireNonNull(curTheme);
        assert (isValidThemeName(curTheme));
        if (isValidThemeName(curTheme)) {
            Theme.currentTheme = curTheme;
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
                cssFileName = BRIGHT_THEME_CSS_FILE_NAME;
            }

            scene.getStylesheets().add(cssFileName);
            primaryStage.setScene(scene);
        }
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
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
###### \java\seedu\address\ui\MainWindow.java
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
            cssFileName = BRIGHT_THEME_CSS_FILE_NAME;
            //Theme.changeTheme(primaryStage, changeThemeEvent.getTheme());
        }

        scene.getStylesheets().add(cssFileName);
        primaryStage.setScene(scene);
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    //Part of code is referenced to Developer Guide
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        rating.setText(person.getRatingDisplay());
        rating.setTextFill(Color.RED);
        initTags(person);
```
###### \java\seedu\address\ui\ResultDisplay.java
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
###### \resources\view\BrightTheme.css
``` css
.background {
    -fx-background-color: derive(beige, 20%);
    background-color: beige; /* Used in the default.html file */
}

.label {
    -fx-font-size: 13pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: saddlebrown;
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
    -fx-base: saddlebrown;
    -fx-control-inner-background: saddlebrown;
    -fx-background-color: saddlebrown;
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
    -fx-text-fill: white;
}

/* vertical color bar between person card and info*/
.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(beige, 20%);
    -fx-border-color: orange;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(beige, 0%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(beige, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: derive(orange, 70%);
}

.list-cell:filled:odd {
    -fx-background-color: derive(lightsalmon, 80%);
}

.list-cell:filled:selected {
    -fx-background-color: lightskyblue;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: beige;
    -fx-border-width: 5;
}

```
###### \resources\view\BrightTheme.css
``` css

.cell_big_label {
    -fx-font-family: "sans-serif";
    -fx-font-size: 16px;
    -fx-text-fill: black;
}

.cell_small_label {
    -fx-font-family: "cursive";
    -fx-font-size: 13px;
    -fx-text-fill: black;
}

```
###### \resources\view\BrightTheme.css
``` css

.anchor-pane {
    -fx-background-color: derive(Beige, 30%);
}

/*Here changed, color outside of command box*/
.pane-with-border {
    -fx-background-color: derive(Beige, 0%);
    -fx-border-color: derive(orange, 30%);
    -fx-border-top-width: 1px;
}

/* Nothing to change here*/
.status-bar {
    -fx-background-color: derive(beige, 20%);
    -fx-text-fill: #1d1d1d;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: ".SF NS Text";
    -fx-font-size: 13pt;
    -fx-text-fill: darkred;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(beige, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

/*Below changed, pane color.*/
.grid-pane {
    -fx-background-color: derive(Beige, 30%);
    -fx-border-color: derive(Beige, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#d06651, 30%);
}

.context-menu {
    -fx-background-color: derive(salmon, 50%);
}

.context-menu .label {
    -fx-text-fill: salmon;
}

.menu-bar {
    -fx-background-color: derive(orange, 50%);
}

.menu-bar .label {
    -fx-font-size: 16pt;
    -fx-font-family: "Helvetica";
    -fx-text-fill: #0000bf;
    -fx-opacity: 0.95;
}

.menu .left-container {
    -fx-background-color: beige;
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
    -fx-background-color: derive(lightblue, 0%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(floralwhite, 50%);
    -fx-background-insets: 1;
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
    -fx-background-color: transparent;
    -fx-background-insets: 0;
    -fx-border-color: orange orange orange orange;
    -fx-border-insets: 0;
    -fx-border-width: 2;
    -fx-font-family: "Times New Roman";
    -fx-font-size: 17pt;
    -fx-text-fill: #0000bf;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, beige, 10, 0, 0, 0);
}

/*No need to change*/
#resultDisplay .content {
    -fx-background-color: antiquewhite;
    -fx-background-radius: 10;
}

#reviews {
    -fx-vgap: 10;
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
    -fx-text-fill: white;
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
###### \resources\view\DarkTheme.css
``` css
/* Part of css file*/
.background {
    -fx-background-color: derive(beige, 20%);
    background-color: beige; /* Used in the default.html file */
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
    -fx-base: saddlebrown;
    -fx-control-inner-background: saddlebrown;
    -fx-background-color: saddlebrown;
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

/* vertical color bar between person card and info*/
.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#1d1d1d, 20%);
    -fx-border-color: transparent transparent transparent #1d1d1d;
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
    -fx-background-color: #005588;
}

.list-cell:filled:odd {
    -fx-background-color: #3366cc;
}

.list-cell:filled:selected {
    -fx-background-color: darkslateblue;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #4d4d4d;
    -fx-border-width: 5;
}

```
###### \resources\view\DarkTheme.css
``` css

.cell_big_label {
    -fx-font-family: "sans-serif";
    -fx-font-size: 16px;
    -fx-text-fill: white;
}

.cell_small_label {
    -fx-font-family: "cursive";
    -fx-font-size: 13px;
    -fx-text-fill: white;
}

```
###### \resources\view\DarkTheme.css
``` css

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
    -fx-font-family: ".SF NS Text";
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
    -fx-text-fill: darkorange;
    -fx-opacity: 0.95;
}

.menu .left-container {
    -fx-background-color: black;
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
    -fx-background-color: derive(lightslategrey, 50%);
    -fx-background-insets: 1;
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

#reviews {
    -fx-vgap: 10;
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
    -fx-text-fill: white;
    -fx-background-color: red;
}

#tags .yellow {
    -fx-text-fill: black;
    -fx-background-color: yellow;
}

#tags .blue {
    -fx-text-fill: white;
    -fx-background-color: blue;
}

#tags .orange {
    -fx-text-fill: white;
    -fx-background-color: orange;
}

#tags .brown {
    -fx-text-fill: white;
    -fx-background-color: brown;
}

#tags .green {
    -fx-text-fill: white;
    -fx-background-color: green;
}
```
###### \resources\view\PersonListCard.fxml
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
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>

      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
      </padding>
      <HBox alignment="CENTER_LEFT" spacing="5">
        <Label fx:id="id" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
      </HBox>
        <FlowPane fx:id="tags" />
```
