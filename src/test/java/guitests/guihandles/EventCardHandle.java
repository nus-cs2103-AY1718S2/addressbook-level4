package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author x3tsunayh

/**
 * Provides a handle to an event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String LOCATION_FIELD_ID = "#eventLocation";
    private static final String DATETIME_FIELD_ID = "#datetime";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label descriptionLabel;
    private final Label locationLabel;
    private final Label datetimeLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.locationLabel = getChildNode(LOCATION_FIELD_ID);
        this.datetimeLabel = getChildNode(DATETIME_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getLocation() {
        return locationLabel.getText();
    }

    public String getDatetime() {
        return datetimeLabel.getText();
    }

}
