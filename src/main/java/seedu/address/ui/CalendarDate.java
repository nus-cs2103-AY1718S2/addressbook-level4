package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

/**
 * Node to display each cell/day of the calendar
 */
public class CalendarDate extends UiPart<Region> {

    private static final String FXML = "CalendarDate.fxml";

    public final LocalDate date;

    @FXML
    public AnchorPane top;
    @FXML
    public GridPane box;
    @FXML
    public VBox node;
    @FXML
    public Text day;
    @FXML
    public Text event1;
    @FXML
    public Text event2;
    @FXML
    public Text event3;

    /**
     * Create a anchor pane node containing all of the {@code children}.
     * The date of the node is not set in the constructor
     */
    public CalendarDate() {
        super(FXML);
        this.date = LocalDate.now();
        initStyle();
    }

    /**
     * Create a anchor pane node containing all of the {@code children}.
     * The date of the node is not set in the constructor
     */
    public CalendarDate(LocalDate date) {
        super(FXML);
        this.date = date;
        initStyle();
    }

    public CalendarDate(LocalDate date, Event event) {
        super(FXML);
        this.date = date;
        initStyle();
        String[] dayMonthYear = event.getDate().split("/");
        int day = Integer.parseInt(dayMonthYear[0]);
        int month = Integer.parseInt(dayMonthYear[1]);
        int year = Integer.parseInt(dayMonthYear[2]);
        LocalDate eventDate = LocalDate.of(year, month, day);
        if (date.equals(eventDate)) {
            event1.setText(event.getName());
        }
    }

    private void initStyle() {
        //node = new VBox();
        node.setPrefSize(200, 50);
        day.setText("" + date.getDayOfMonth());
        event1.setText("");
        event2.setText("");
        event3.setText("");
    }

    public void setDate(LocalDate date, YearMonth currentYM) {
        day.setText("" + date.getDayOfMonth());
        if (date.equals(LocalDate.now())) {
            setStyleClass(node, "weekend");
        } else {
            setStyleClass(node, "date");
        }
        if (date.getMonth().equals(currentYM.getMonth())) {
            setStyleClass(day, "thisMonthDate");
        } else {
            setStyleClass(day, "notThisMonthDate");
        }
    }

    private void setStyleClass(Node node, String... styles) {
        ObservableList<String> styleClass = node.getStyleClass();
        styleClass.clear();
        for (String style : styles) {
            styleClass.add(style);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CalendarDate)) {
            return false;
        }

        // state check
        CalendarDate theOther = (CalendarDate) other;
        return node.equals(theOther.node);
    }

    /**
     * The node to display on the calendar
     */
    private class AnchorPaneNode extends AnchorPane {
        private LocalDate date;

        /**
         * Create a anchor pane node containing all of the {@code children}.
         * The date of the node is not set in the constructor
         */
        public AnchorPaneNode(Node... children) {
            super(children);
            // Add action handler for mouse clicked
            this.setOnMouseClicked(e -> {
                LogsCenter.getLogger(this.getClass()).info(e.toString());
            });
        }

        /**
         * Returns the date of the node
         */
        public LocalDate getDate() {
            return date;
        }

        /**
         * Set the date of the node to {@code date}
         */
        public void setDate(LocalDate date) {
            this.date = date;
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof CalendarDate)) {
                return false;
            }

            // state check
            AnchorPaneNode theOther = (AnchorPaneNode) other;
            return date.toString().equals(theOther.date.toString());
        }
    }
}
