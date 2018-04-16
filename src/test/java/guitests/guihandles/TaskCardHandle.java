package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DURATION_FIELD_ID = "#duration";
    private static final String DATEANDTIME_FIELD_ID = "#dateandtime";

    private final Label idLabel;
    private final Label descriptionLabel;
    private final Label durationLabel;
    private final Label dateAndTimeLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.durationLabel = getChildNode(DURATION_FIELD_ID);
        this.dateAndTimeLabel = getChildNode(DATEANDTIME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getDuration() {
        return durationLabel.getText();
    }

    public String getDateAndTime() {
        return dateAndTimeLabel.getText();
    }

}
