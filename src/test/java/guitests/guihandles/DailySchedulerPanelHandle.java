//@@author jaronchan
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class DailySchedulerPanelHandle extends NodeHandle<Node> {

    public static final String DAILY_SCHEDULER_PLACEHOLDER = "#dailySchedulerPlaceholder";
    private static final String EVENTS_LIST_STACK_FIELD_ID = "#eventsListStack";
    private static final String BUTTON_STACK_FIELD_ID = "#buttonStack";

    private final VBox eventsListStack;
    private final VBox buttonStack;

    private int numOfEventsShown;
    private int numOfButtons;

    public DailySchedulerPanelHandle(Node dailySchedulerPanelNode) {
        super(dailySchedulerPanelNode);

        this.eventsListStack = getChildNode(EVENTS_LIST_STACK_FIELD_ID);
        this.buttonStack = getChildNode(BUTTON_STACK_FIELD_ID);

        this.numOfEventsShown = this.eventsListStack.getChildren().size();
        this.numOfButtons = this.buttonStack.getChildren().size();
    }

    public int getNumOfEventsShown() {
        return numOfEventsShown;
    }

    public int getNumOfButtons() {
        return numOfButtons;
    }
}
