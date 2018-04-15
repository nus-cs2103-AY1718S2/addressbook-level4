package guitests.guihandles;
//@@author SuxianAlicia
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a calendar entry card in the calendar entry list panel.
 */
public class CalendarEntryCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String ENTRY_TITLE_ID = "#entryTitle";
    private static final String START_DATE_ID = "#startDate";
    private static final String END_DATE_ID = "#endDate";
    private static final String TIME_DURATION_ID = "#timeDuration";

    private final Label idLabel;
    private final Label entryTitleLabel;
    private final Label startDateLabel;
    private final Label endDateLabel;
    private final Label timeDurationLabel;

    public CalendarEntryCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.entryTitleLabel = getChildNode(ENTRY_TITLE_ID);
        this.startDateLabel = getChildNode(START_DATE_ID);
        this.endDateLabel = getChildNode(END_DATE_ID);
        this.timeDurationLabel = getChildNode(TIME_DURATION_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEntryTitle() {
        return entryTitleLabel.getText();
    }

    public String getStartDate() {
        return startDateLabel.getText();
    }

    public String getEndDate() {
        return endDateLabel.getText();
    }

    public String getTimeDuration() {
        return timeDurationLabel.getText();
    }
}
