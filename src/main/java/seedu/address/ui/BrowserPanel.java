package seedu.address.ui;

import static seedu.address.ui.util.DateTimeUtil.getDisplayedDateTime;
import static seedu.address.ui.util.DateTimeUtil.getDisplayedEndDateTime;
import static seedu.address.ui.util.DateTimeUtil.getDisplayedStartDateTime;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeselectEventListCellEvent;
import seedu.address.commons.events.ui.DeselectTaskListCellEvent;
import seedu.address.commons.events.ui.PanelSelectionChangedEvent;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Task;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";

    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private HBox browser;

    private Text name;

    private Text dateTime;

    private Text locationEvent;

    private Text remark;

    private FlowPane tags;

    public BrowserPanel() {
        super(FXML);

        browser = new HBox();
        browser.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.BLACK, 10, 0, 0, 0));
        browser.setStyle("-fx-background-color: #e2ce9e; -fx-border-width: 1 0 1 0; "
                + " -fx-border-color: #f2cb80; ");
        browser.managedProperty().bind(browser.visibleProperty());
        browser.setVisible(false);
        registerAsAnEventHandler(this);
    }

    /**
     * Configures the default layout of the browswerPanel
     */
    private void defaultSetup() {
        browser.setPadding(new Insets(8, 0, 8, 20));
        browser.setId("browser");
        name.setId("name");
        dateTime.setId("emphasizeText");
        remark.setId("text");

        tags.setTranslateY(7.0);
        dateTime.setTranslateY(47.0);
    }

    /**
     * Set up and shows the selected task on browserPanel.
     * @param task
     */
    private void showBrowserPanelTask(Task task) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                browser.setVisible(true);
                browser.getChildren().setAll(setupBrowserPanelTask(task));
            }
        });
    }

    private Group setupBrowserPanelTask(Task task) {
        name = new Text(task.getName().fullName);
        tags = new FlowPane();
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        dateTime = new Text(getDisplayedDateTime(task));
        if (task.getRemark() != null) {
            remark = new Text(task.getRemark().value);
            remark.setTranslateY(64.0);
            defaultSetup();
            return new Group(name, dateTime, remark, tags);
        } else {
            remark = new Text();
            defaultSetup();
            return new Group(name, dateTime, tags);
        }
    }

    /**
     * Set up and shows the selected event on browserPanel.
     * @param event
     */
    private void showBrowserPanelEvent(Event event)   {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                browser.setVisible(true);
                browser.getChildren().setAll(setupBrowserPanelEvent(event));
            }
        });
    }

    private Group setupBrowserPanelEvent(Event event) {
        name = new Text(event.getName().fullName);
        tags = new FlowPane();
        event.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        dateTime = new Text(getDisplayedStartDateTime(event) + " - " + getDisplayedEndDateTime(event));
        if (event.getLocation() != null) {
            locationEvent = new Text(event.getLocation().toString());
            locationEvent.setTranslateY(70.0);
            locationEvent.setId("emphasizeText");
        } else {
            locationEvent = new Text();
        }
        if (event.getRemark() != null) {
            remark = new Text(event.getRemark().value);
            remark.setTranslateY(88.0);
        } else {
            remark = new Text();
        }
        defaultSetup();
        return new Group(name, dateTime, locationEvent, remark, tags);
    }

    @Subscribe
    private void handlePanelSelectionChangedEvent(PanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getActivityType().equals("TaskCard")) {
            TaskCard taskCard = (TaskCard) event.getNewSelection();
            Task task = taskCard.getTask();
            showBrowserPanelTask(task);
        } else if (event.getActivityType().equals("EventCard")) {
            EventCard eventCard = (EventCard) event.getNewSelection();
            Event activityEvent = eventCard.getEvent();
            showBrowserPanelEvent(activityEvent);
        }
    }

    @Subscribe
    private void handleDeselectTaskListCellEvent(DeselectTaskListCellEvent event) {
        browser.setVisible(false);
        browser.getChildren().clear();
    }

    @Subscribe
    private void handleDeselectEventListCellEvent(DeselectEventListCellEvent event) {
        browser.setVisible(false);
        browser.getChildren().clear();
    }

    public HBox getRoot() {
        return browser;

    }
}
