package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

//@@author guekling
/**
 * Provides a handle for {@code MonthView}.
 */
public class MonthViewHandle extends NodeHandle<Node> {
    private static final String CALENDAR_TITLE_ID = "#calendarTitle";
    private static final String TASK_CALENDAR_ID = "#taskCalendar";

    private final Text calendarTitleText;
    private final GridPane taskCalendarGrid;

    public MonthViewHandle(Node monthViewNode) {
        super(monthViewNode);

        this.calendarTitleText = getChildNode(CALENDAR_TITLE_ID);
        this.taskCalendarGrid = getChildNode(TASK_CALENDAR_ID);
    }

    public String getCalendarTitleText() {
        return calendarTitleText.getText();
    }

    /**
     * Returns the node of a {@code date}.
     */
    public Node getPrintedDateNode(int date) {
        return taskCalendarGrid.lookup("#date" + String.valueOf(date));
    }

    /**
     * Returns the node of a {@code ListView} containing {@code EntryCard}.
     */
    public Node getListViewEntriesNode(int row, int column) {
        return taskCalendarGrid.lookup("#entry" + String.valueOf(row) + String.valueOf(column));
    }

    /**
     * Returns the row index of the {@code node}.
     */
    public int getRowIndex(Node node) {
        return taskCalendarGrid.getRowIndex(node);
    }

    /**
     * Returns the column index of the {@code node}.
     */
    public int getColumnIndex(Node node) {
        return taskCalendarGrid.getColumnIndex(node);
    }

    /**
     * Checks if the grid lines are visible on the {@code taskCalendar}.
     */
    public boolean isGridLinesVisible() {
        return taskCalendarGrid.isGridLinesVisible();
    }
}
