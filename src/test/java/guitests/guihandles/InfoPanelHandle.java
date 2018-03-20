package guitests.guihandles;

import java.util.Objects;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import seedu.address.commons.events.ui.InfoPanelChangedEvent;
import seedu.address.model.person.Person;

/**
 * A handler for the {@code InfoPanel} of the UI.
 */
public class InfoPanelHandle extends NodeHandle<Node> {

    public static final String INFO_ID = "#infoPaneWrapper";

    private boolean isInfoPanelLoaded = true;
    private Person lastRememberedPerson;
    private AnchorPane infoPaneWrapper;

    public InfoPanelHandle(Node infoPanel) {
        super(infoPanel);
        this.infoPaneWrapper = getChildNode(INFO_ID);

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
}
