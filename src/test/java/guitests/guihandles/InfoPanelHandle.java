package guitests.guihandles;

import java.util.Objects;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;
import seedu.address.commons.events.ui.InfoPanelChangedEvent;
import seedu.address.model.person.Person;

/**
 * A handler for the {@code InfoPanel} of the UI.
 */
public class InfoPanelHandle extends NodeHandle<Node> {

    public static final String INFO_ID = "#infoPaneWrapper";

    private static final String SPLIT_PANE_ID = "#infoSplitPane";
    private static final String MAIN_PANE_ID = "#infoMainPane";
    private static final String SPLIT_MAIN_PANE_ID = "#infoSplitMainPane";
    private static final String MAIN_ID = "#infoMain";
    private static final String MAIN_RATINGS_ID = "#infoMainRatings";
    private static final String SPLIT_SIDE_PANE_ID = "#infoSplitSidePane";
    private static final String SPLIT_RATINGS_ID = "#infoSplitRatings";

    private boolean isInfoPanelLoaded = true;
    private boolean isWidthChanged = false;
    private Person lastRememberedPerson;

    private AnchorPane infoPaneWrapper;
    private SplitPane infoSplitPane;
    private ScrollPane infoMainPane;
    private ScrollPane infoSplitMainPane;
    private VBox infoMain;
    private AnchorPane infoMainRatings;
    private AnchorPane infoSplitSidePane;
    private VBox infoSplitRatings;

    public InfoPanelHandle(Node infoPanel) {
        super(infoPanel);

        this.infoPaneWrapper   = getChildNode(INFO_ID);
        this.infoSplitPane     = getChildNode(SPLIT_PANE_ID);
        this.infoMainPane      = getChildNode(MAIN_PANE_ID);
        this.infoSplitMainPane = getChildNode(SPLIT_MAIN_PANE_ID);
        this.infoMain          = getChildNode(MAIN_ID);
        this.infoMainRatings   = getChildNode(MAIN_RATINGS_ID);
        this.infoSplitSidePane = getChildNode(SPLIT_SIDE_PANE_ID);
        this.infoSplitRatings  = getChildNode(SPLIT_RATINGS_ID);

        new GuiRobot().interact(() -> this.infoPaneWrapper.addEventHandler(
            InfoPanelChangedEvent.INFO_PANEL_EVENT,
            event -> isInfoPanelLoaded = true
        ));
    }

    /**
     * Returns the {@code Person} of the currently previewed person.
     */
    public Person getLoadedPerson() {
        return (Person) infoPaneWrapper.getUserData();
    }

    /**
     * Remembers the {@code Person} of the currently previewed person.
     */
    public void rememberPerson() {
        lastRememberedPerson = getLoadedPerson();
    }

    /**
     * Returns true if the current {@code Person} is different from the value remembered by the most recent
     * {@code rememberPerson()} call.
     */
    public boolean isPersonChanged() {
        return !Objects.equals(lastRememberedPerson, getLoadedPerson());
    }

    /**
     * Returns true if the panel is done loading a person, with current person retrievable via {@code getUserData}
     */
    public boolean isLoaded() {
        return isInfoPanelLoaded;
    }

    /**
     * Reset the loaded status to wait for next loaded event
     */
    public void resetLoadedStatus() {
        isInfoPanelLoaded = false;
    }

    /**
     * Returns true if the panel is done changing the width
     */
    public boolean isWidthChanged() {
        return isWidthChanged;
    }

    /**
     * Reset the width changed status to wait for next resize event
     */
    public void resetWidthStatus() {
        isWidthChanged = false;
    }

    /**
     * Set the width of info panel and wait until its width changed
     * @param width of the InfoPanel
     */
    public void setWidthAndWait(int width) {
        resetWidthStatus();

        infoPaneWrapper.widthProperty().addListener((observable, oldValue, newValue) -> {
            isWidthChanged = true;
        });

        infoPaneWrapper.getScene().getWindow().setWidth(width);
        new GuiRobot().waitForEvent(this::isWidthChanged);
    }

    /**
     * Returns true if it is split due to responsive, false otherwise
     */
    public boolean isResponsiveSplit() {
        return infoSplitPane.isVisible()
                && !infoMainPane.isVisible()
                && !infoMainRatings.getChildren().contains(infoSplitRatings)
                && infoSplitSidePane.getChildren().contains(infoSplitRatings)
                && infoMainPane.getContent() == null
                && infoSplitMainPane.getContent().equals(infoMain);
    }

    /**
     * Returns true if it is single scroll pane due to responsive, false otherwise
     */
    public boolean isResponsiveSingle() {
        return !infoSplitPane.isVisible()
                && infoMainPane.isVisible()
                && infoMainRatings.getChildren().contains(infoSplitRatings)
                && !infoSplitSidePane.getChildren().contains(infoSplitRatings)
                && infoMainPane.getContent().equals(infoMain)
                && infoSplitMainPane.getContent() == null;
    }
}
