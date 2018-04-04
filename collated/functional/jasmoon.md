# jasmoon
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String MESSAGE_INVALID_HELP_REQUEST = "Help for '%s' is unknown or not available.";
    public static final String MESSAGE_INVALID_LIST_REQUEST = "List for '%s' is invalid";

}
```
###### \java\seedu\address\commons\events\ui\PanelSelectionChangedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Panel
 */
public class PanelSelectionChangedEvent extends BaseEvent {


    private final Object newSelection;

    public PanelSelectionChangedEvent(Object newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Object getNewSelection() {
        return newSelection;
    }

}
```
###### \java\seedu\address\logic\commands\HelpCommand.java
``` java
    public HelpCommand()    {
    }

    public HelpCommand(String args) {
        commandRequest = args.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (commandRequest == null)   {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else   {
            switch(commandRequest) {

            case TaskCommand.COMMAND_WORD:
                return new CommandResult(TaskCommand.MESSAGE_USAGE);

            case EventCommand.COMMAND_WORD:
                return new CommandResult(EventCommand.MESSAGE_USAGE);

            case CompleteCommand.COMMAND_WORD:
                return new CommandResult(CompleteCommand.MESSAGE_USAGE);

            //case EditCommand.COMMAND_WORD:
                //return new CommandResult(EditCommand.MESSAGE_USAGE);

            //case SelectCommand.COMMAND_WORD:
                //return new CommandResult(SelectCommand.MESSAGE_USAGE);

            case RemoveCommand.COMMAND_WORD:
                return new CommandResult(RemoveCommand.MESSAGE_USAGE);

            //case FindCommand.COMMAND_WORD:
                //return new CommandResult(FindCommand.MESSAGE_USAGE);

            default:
                throw new CommandException(MESSAGE_USAGE);
            }
        }
    }


}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of tasks */
    public ObservableList<Activity> getFilteredTaskList();

    /** Returns an unmodifiable view of the filtered list of events*/
    public ObservableList<Activity> getFilteredEventList();

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    public ObservableList<Activity> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    public ObservableList<Activity> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
```
###### \java\seedu\address\logic\parser\HelpCommandParser.java
``` java
/**
 * Parses input arguments and create a new HelpCommand object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public final ArrayList<String> availableCommands;

    /**
     * HelpCommandParser constructor - creates an ArrayList which contains all commands open to the help function.
     */
    public HelpCommandParser()  {
        availableCommands = new ArrayList<>();
        availableCommands.add(RemoveCommand.COMMAND_WORD);
        availableCommands.add(EditCommand.COMMAND_WORD);
        availableCommands.add(FindCommand.COMMAND_WORD);
        availableCommands.add(SelectCommand.COMMAND_WORD);
        availableCommands.add(TaskCommand.COMMAND_WORD);
        availableCommands.add(EventCommand.COMMAND_WORD);
        availableCommands.add(CompleteCommand.COMMAND_WORD);
        availableCommands.add(HelpCommand.COMMAND_WORD);
        availableCommands.add(HelpCommand.COMMAND_ALIAS);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the HelpCommand
     * and returns an HelpCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public HelpCommand parse(String args) throws ParseException {

        String commandRequest = args.trim();
        if (commandRequest.length() == 0) {
            return new HelpCommand();
        } else {
            if (availableCommands.contains(commandRequest)) {
                return new HelpCommand(args);
            } else {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_HELP_REQUEST, commandRequest));
            }
        }
    }
}
```
###### \java\seedu\address\logic\parser\ListCommandParser.java
``` java

/**
 * Parses input arguments and create a new ListCommand object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    public final ArrayList<String> availableCommands;

    public ListCommandParser()  {
        availableCommands = new ArrayList<String>();
        availableCommands.add("task");
        availableCommands.add("event");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String commandRequest = args.trim();

        if (commandRequest.length() == 0)  {
            return new ListCommand();
        } else  {
            if (availableCommands.contains(commandRequest))   {
                return new ListCommand(commandRequest);
            } else  {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_LIST_REQUEST, commandRequest));
            }
        }
    }

}
```
###### \java\seedu\address\model\activity\UniqueActivityList.java
``` java
        } else  {
            internalList.remove(toRemove);
        }
        return activityFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Activity> internalListAsObservable() {
        return FXCollections.unmodifiableObservableList(internalList);
    }



    @Override
    public Iterator<Activity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                        && this.internalList.equals(((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Activity> getFilteredTaskList();

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Activity> getFilteredEventList();

    /**
     * Updates the filter of the filtered activity list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredActivityList(Predicate<Activity> predicate);

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the task list of
     * {@code deskBoard}
     */
    @Override
    public ObservableList<Activity> getFilteredTaskList()   {
        FilteredList<Activity> taskList =  new FilteredList<>(filteredActivities, new TaskOnlyPredicate());
        ObservableList<Activity> result = FXCollections.unmodifiableObservableList(taskList);
        return result;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the event list of
     * {@code deskBoard}
     */
    @Override
    public ObservableList<Activity> getFilteredEventList() {
        FilteredList<Activity> eventList =  new FilteredList<>(filteredActivities, new EventOnlyPredicate());
        ObservableList<Activity> result = FXCollections.unmodifiableObservableList(eventList);
        return result;
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        requireNonNull(predicate);
        filteredActivities.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return deskBoard.equals(other.deskBoard)
                && filteredActivities.equals(other.filteredActivities);
    }

}
```
###### \java\seedu\address\ui\EventListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeselectListCellEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PanelSelectionChangedEvent;
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<EventCard> eventListView;

    private Label emptyLabel = new Label("Event List is empty!");

    public EventListPanel(ObservableList<Activity> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
        setUpPlaceHolder();
        //maybe do not need this
        //eventListView.managedProperty().bind(eventListView.visibleProperty());
    }

    private void setConnections(ObservableList<Activity> eventList) {
        ObservableList<EventCard> mappedList = EasyBind.map(
                eventList, (event) -> new EventCard(event, eventList.indexOf(event) + 1));
        eventListView.setItems(mappedList);
        linkCell();
        setEventHandlerForSelectionChangeEvent();
    }

    private void setUpPlaceHolder()   {
        eventListView.setPlaceholder(emptyLabel);
        emptyLabel.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 25px; ");
    }

    /**
     * Links eventListView to eventListViewCell as its custom ListCell
     */
    private void linkCell() {
        eventListView.setCellFactory(listView -> {
            EventListViewCell cell = new EventListViewCell();
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                eventListView.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (eventListView.getSelectionModel().getSelectedIndices().contains(index))  {
                        logger.fine("Selection in event list panel with index '" + index
                                + "' has been deselected");
                        raise(new DeselectListCellEvent(eventListView, index));
                    } else {
                        eventListView.getSelectionModel().select(index);
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new PanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code EventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<EventCard> {

        @Override
        protected void updateItem(EventCard event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(event.getRoot());
            }
        }
    }

```
###### \java\seedu\address\ui\EventListPanel.java
``` java
    /**
     * Getter method for eventListView
     * @return eventListView
     */
    public ListView<EventCard> getEventListView()   {
        return eventListView;
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        eventListPanel = new EventListPanel(logic.getFilteredEventList());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowActivityRequestEvent(ShowActivityRequestEvent event)    {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        taskListPanel.getTaskListView().setVisible(true);
        eventListPanel.getEventListView().setVisible(true);
    }

    @Subscribe
    private void handleShowEventOnlyRequestEvent(ShowEventOnlyRequestEvent event)   {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        taskListPanel.getTaskListView().setVisible(false);
        eventListPanel.getEventListView().setVisible(true);
    }

    @Subscribe
    private void handleShowTaskOnlyRequestEvent(ShowTaskOnlyRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        taskListPanel.getTaskListView().setVisible(true);
        eventListPanel.getEventListView().setVisible(false);
    }
}
```
###### \java\seedu\address\ui\TaskCard.java
``` java
package seedu.address.ui;

import static seedu.address.ui.util.DateTimeUtil.getDisplayedDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Task;

/**
 * An UI component that displays information of a {@code Task}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label dateTime;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane status;

    public TaskCard(Activity task, int displayedIndex) {
        super(FXML);
        this.task = (Task) task;
        id.setText(displayedIndex + ". ");
        name.setText(this.task.getName().fullName);
        dateTime.setText(getDisplayedDateTime(this.task));
        remark.setText(this.task.getRemark().value);
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (task.isCompleted()) {
            status.getChildren().add(new Label("Completed"));
        } else {
            status.getChildren().add(new Label("Uncompleted"));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
```
###### \java\seedu\address\ui\TaskListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeselectListCellTask;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PanelSelectionChangedEvent;
import seedu.address.model.activity.Activity;

/**
 * Panel containing the list of activities.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    private Label emptyLabel = new Label("Task List is empty!");

    public TaskListPanel(ObservableList<Activity> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
        setUpPlaceholder();
    }

    private void setUpPlaceholder() {
        taskListView.setPlaceholder(emptyLabel);
        emptyLabel.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 25px; ");
    }

    private void setConnections(ObservableList<Activity> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (activity) -> new TaskCard(activity, taskList.indexOf(activity) + 1));
        taskListView.setItems(mappedList);
        linkCell();
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Links taskListView to taskListViewCell as its custom ListCell
     */
    private void linkCell() {
        taskListView.setCellFactory(listView -> {
            TaskListViewCell cell = new TaskListViewCell();
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                taskListView.requestFocus();
                if (!cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (taskListView.getSelectionModel().getSelectedIndices().contains(index))  {
                        logger.fine("Selection in task list panel with index '" + index
                                + "' has been deselected");
                        raise(new DeselectListCellTask(taskListView, index));
                    } else {
                        taskListView.getSelectionModel().select(index);
                    }
                    event.consume();
                }
            });
            return cell;
        });
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new PanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code TaskCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }



    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard activity, boolean empty) {
            super.updateItem(activity, empty);

            if (empty || activity == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(activity.getRoot());
            }
        }
    }

```
###### \java\seedu\address\ui\TaskListPanel.java
``` java
    /**
     * Getter method for taskListView
     * @return taskListView
     */
    public ListView<TaskCard> getTaskListView()   {
        return taskListView;
    }
}
```
###### \resources\view\ClindarStyler.css
``` css
@font-face  {
    font-family: 'Open Sans Bold Italic';
    src: url('../fonts/open-sans/OpenSans-BoldItalic.ttf');
}

@font-face  {
    font-family: 'Open Sans Italic';
    src: url('../fonts/open-sans/OpenSans-Italic.ttf');
}

@font-face  {
    font-family: 'Open Sans';
    src: url('../fonts/open-sans/OpenSans-Regular.ttf');
}

@font-face  {
    font-family: 'Arvo';
    src: url('../fonts/arvo/Arvo-Regular.ttf');
}

@font-face  {
    font-family: 'Arvo Bold';
    src: url('../fonts/arvo/Arvo-Bold.ttf');
}

/* menu bar*/
.menu-bar {
    -fx-background-color: #f7e399;
    -fx-padding: 0 2 0 2;
    -fx-border-width: 0 0 2 0;
    -fx-border-color: #dbdad9;
}

.menu-bar .label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.context-menu {
    -fx-background-color: #ffd177;
    -fx-border-width: 2 0 0 0;
    -fx-border-color: #969594;
}

/* taskList and eventList */

.list-view   {
    -fx-background-color: transparent;
    -fx-border-insets: 2 2 2 2;
    -fx-background-insets: 2 2 2 2;
    -fx-focus-color: transparent;
    -fx-faint-focus-color: transparent;
}

.list-cell:empty    {
    -fx-background-color: transparent;
    -fx-border-color: transparent;
}

.list-cell:filled:hover {
    -fx-border-width: 1 1 1 1;
    -fx-border-color: #2dc6c6;
    -fx-border-insets: 0 0 0 1;
    -fx-background-insets: 0 0 0 1;
}

#taskListView .list-cell:filled:even {
    -fx-background: linear-gradient(to right, #e8e8e8 0%, #e8e8e8 82.5%, #b2eaff 82.5%, #b2eaff 100%);
}

#taskListView .list-cell:filled:odd   {
    -fx-background: linear-gradient(to right, #d1d1d1 0%, #d1d1d1 82.5%, #8ccef2 82.5%, #8ccef2 100%);
}

#eventListView .list-cell:filled:even {
    -fx-background: linear-gradient(to right, #e8e8e8 0%, #e8e8e8 82.5%, #ffd6d8 82.5%, #ffd6d8 100%);
}

#eventListView .list-cell:filled:odd {
    -fx-background: linear-gradient(to right, #d1d1d1 0%, #d1d1d1 82.5%, #ffb2b5 82.5%, #ffb2b5 100%);
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Open Sans Bold Italic";
    -fx-font-size: 14pt;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Open Sans Italic";
    -fx-font-size: 10pt;
    -fx-text-fill: #010504;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-font-family: "Open Sans";
    -fx-text-fill: white;
    -fx-background-color: #854c99;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

.scroll-bar .thumb  {
    -fx-background-color: linear-gradient(to right, #f4f4f4, #bcbcbc);
    -fx-background-insets: 2;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: "|";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

/* resultBox */
.text-area  {
    -fx-text-fill: black;
}

#resultBox {
    -fx-background-color: transparent;
    -fx-opacity: 0.45;
    -fx-background-radius: 2px 2px 2px 2px;
}

/* commandBox */

#commandBoxPlaceholder {
    -fx-background-color: white;
    -fx-opacity: 0.45;
    -fx-background-radius: 4px 4px 4px 4px;
}

.text-field {
    -fx-background-color: transparent;
    -fx-border-width: 1 1 1 1;
    -fx-border-color: grey;
    -fx-border-radius: 4px 4px 4px 4px;
    -fx-prompt-text-fill: black;
    -fx-text-fill: black;
}

/* statusBar */

#statusBarPlaceholder   {
    -fx-background-color: #f7e399 /*silver #d8d7d0*/;
    -fx-font-color: black;
    -fx-border-width: 1 0 0 0;
    -fx-border-color: #dbdad9;

}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.grid-pane .anchor-pane {
    -fx-background-color: #f7e399;
    -fx-border-width: 1 0 0 0;
    -fx-border-color: #dbdad9;
}

/* miscs */
.background {
    -fx-background-image: url("../images/light-veneer.png");
    -fx-background-repeat: repeat;
}

.split-pane-divider {
   -fx-background-color: transparent;
}
```
###### \resources\view\EventListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
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
            <Label fx:id="startDateTime" styleClass="cell_small_label" text="\$startDateTime" />
            <Label fx:id="endDateTime" styleClass="cell_small_label" text="\$endDateTime" />
            <Label fx:id="locationEvent" styleClass="cell_small_label" text="\$locationEvent" />
            <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" />
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\EventListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView id="eventListView" fx:id="eventListView" VBox.vgrow="ALWAYS" />
</VBox>

```
###### \resources\view\MainWindow.fxml
``` fxml
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         minWidth="600" minHeight="350">
    <scene>
        <Scene>
            <stylesheets>
                <URL value="@ClindarStyler.css"/>
                <URL value="@Extensions.css"/>
            </stylesheets>

            <VBox styleClass="background">
                <children>

                    <MenuBar fx:id="menuBar" prefHeight="21.0" prefWidth="600.0" VBox.vgrow="NEVER">
                        <menus>
                            <Menu mnemonicParsing="false" text="File">
                                <items>
                                    <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit"/>
                                </items>
                            </Menu>
                            <Menu mnemonicParsing="false" text="Help">
                                <items>
                                    <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp"
                                              text="Help"/>
                                </items>
                            </Menu>
                        </menus>
                    </MenuBar>

                    <SplitPane dividerPositions="0.5" prefWidth="600.0" styleClass="background" VBox.vgrow="ALWAYS">
                        <items>
                            <VBox fx:id="personList" prefWidth="100.0" styleClass="VBox" VBox.vgrow="ALWAYS">
                                <children>
                                    <StackPane id="taskListPanelPlaceholder" fx:id="taskListPanelPlaceholder"
                                               prefHeight="150.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
                                        <VBox.margin>
                                            <Insets bottom="8.0" left="30.0" right="30.0" top="10.0"/>
                                        </VBox.margin>
                                    </StackPane>
                                </children>
                            </VBox>

                            <VBox fx:id="eventList" prefWidth="100.0" styleClass="VBox" VBox.vgrow="ALWAYS">
                                <children>
                                    <StackPane id="eventListPanelPlaceholder" fx:id="eventListPanelPlaceholder" prefHeight="150.0"
                                               prefWidth="200.0" VBox.vgrow="ALWAYS">
                                        <VBox.margin>
                                            <Insets bottom="8.0" left="30.0" right="30.0" top="10.0"/>
                                        </VBox.margin>
                                    </StackPane>
                                </children>
                            </VBox>
                        </items>
                    </SplitPane>

                    <StackPane id="resultBox" fx:id="resultDisplayPlaceholder" prefHeight="85.0" prefWidth="600.0">
                        <VBox.margin>
                            <Insets bottom="3.0" left="3.0" right="3.0" top="3.0"/>
                        </VBox.margin>
                    </StackPane>

                    <StackPane id="commandBoxPlaceholder" fx:id="commandBoxPlaceholder" prefWidth="589.0"
                               VBox.vgrow="NEVER">
                        <VBox.margin>
                            <Insets bottom="5.0" left="5.0" right="5.0"/>
                        </VBox.margin>
                    </StackPane>

                    <StackPane id="statusBarPlaceholder" fx:id="statusbarPlaceholder" VBox.vgrow="NEVER"/>
                </children>
            </VBox>
        </Scene>
    </scene>
</fx:root>

```
###### \resources\view\TaskListCard.fxml
``` fxml
<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
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
            <FlowPane fx:id="status" />
            <Label fx:id="dateTime" styleClass="cell_small_label" text="\$dateTime" />
            <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" />
        </VBox>
    </GridPane>
</HBox>
```
###### \resources\view\TaskListPanel.fxml
``` fxml

<VBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <ListView id="taskListView" fx:id="taskListView" VBox.vgrow="ALWAYS" />
</VBox>
```
