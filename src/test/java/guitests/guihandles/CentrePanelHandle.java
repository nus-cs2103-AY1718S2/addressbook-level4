package guitests.guihandles;

import javafx.scene.Node;

/**
 * Provides a handle for {@code CentrePanel}.
 */
public class CentrePanelHandle extends NodeHandle<Node> {
    public static final String CENTRE_PANEL_ID = "#centrePlaceholder";

    public final PersonPanelHandle personPanel;
    // private final CalendarPanelHandle calendarPanel;

    protected CentrePanelHandle(Node rootNode) {
        super(rootNode);

        personPanel = new PersonPanelHandle(getChildNode(PersonPanelHandle.PERSON_PANEL_ID));
    }

    public PersonPanelHandle getPersonPanelHandle() {
        return personPanel;
    }
}
