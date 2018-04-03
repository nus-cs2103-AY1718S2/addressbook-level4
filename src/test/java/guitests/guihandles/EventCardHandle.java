package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.stream.Collectors;

public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String STARTDATE_FIELD_ID = "#startDate";
    private static final String ENDDATE_FIELD_ID = "#endDate";
    private static final String LOCATION_FIELD_ID = "#location";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label startDateLabel;
    private final Label endDateLabel;
    private final Label locationLabel;
    private final Label remarkLabel;
    private final List<Label> tagLabels;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.startDateLabel = getChildNode(STARTDATE_FIELD_ID);
        this.endDateLabel = getChildNode(ENDDATE_FIELD_ID);
        this.locationLabel = getChildNode(LOCATION_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getStartDateTime() {
        return startDateLabel.getText();
    }

    public String getEndDateTime() {
        return endDateLabel.getText();
    }

    public String getLocation() {
        return locationLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
