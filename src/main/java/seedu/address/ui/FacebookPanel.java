package seedu.address.ui;

import static seedu.address.logic.commands.FacebookLoginCommand.MESSAGE_FAILURE;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserUrlChangedEvent;
import seedu.address.logic.commands.FacebookLoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author tiny767
/**
 * The Facebook Panel of the App, used for facebook authentication and displaying profile
 */
public class FacebookPanel extends UiPart<Region> {

    private static final String FXML = "FacebookPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    private Label location;


    public FacebookPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        facebookInit();

        registerAsAnEventHandler(this);
    }
    /**
     * Sets the browser up to for Facebook functionality
     */
    private void facebookInit() {
        FacebookLoginCommand.setWebEngine(browser.getEngine());
        location = new Label();
        location.textProperty().bind(browser.getEngine().locationProperty());
        setEventHandlerForBrowserUrlChangedEvent();
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    private void setEventHandlerForBrowserUrlChangedEvent() {
        location.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains("access_token")) {
                logger.info("facebook panel url changed to : '" + newValue + "'");
                raise(new BrowserUrlChangedEvent(newValue));

            }
        });
    }
    @Subscribe
    private void handleBrowserUrlChangedEvent(BrowserUrlChangedEvent event) throws CommandException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        try {
            FacebookLoginCommand.completeAuth(event.getNewUrl());
        } catch (Exception e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
