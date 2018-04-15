//@@author LeonidAgarth
package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.event.Event;

/**
 * Node to display each cell/day of the calendar
 */
public class CalendarDate extends UiPart<Region> {

    private static final String FXML = "CalendarDate.fxml";

    public final LocalDate date;

    @FXML
    private GridPane box;
    @FXML
    private VBox node;
    @FXML
    private Text day;
    @FXML
    private Text eventText;

    /**
     * Create a calendar date for today on the calendar
     */
    public CalendarDate() {
        super(FXML);
        this.date = LocalDate.now();
        initStyle();
    }

    /**
     * Create a calendar date for an event on the calendar
     */
    public CalendarDate(Event event) {
        super(FXML);
        this.date = LocalDate.parse(event.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        initStyle();
        eventText.setText(event.getName());
    }

    /**
     * Initialize the style of the date node
     */
    private void initStyle() {
        //node = new VBox();
        node.setPrefSize(200, 50);
        day.setText("" + date.getDayOfMonth());
        setEventText("");
    }

    /**
     * Set the date of this node to the specified {@code date}
     */
    public void setDate(LocalDate date, YearMonth currentYearMonth) {
        day.setText("" + date.getDayOfMonth());
        if (date.equals(LocalDate.now())) {
            setStyleClass(node, "weekend");
        } else {
            setStyleClass(node, "date");
        }
        if (date.getMonth().equals(currentYearMonth.getMonth())) {
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

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public GridPane getBox() {
        return box;
    }

    public VBox getNode() {
        return node;
    }

    public Text getDay() {
        return day;
    }

    public Text getEventText() {
        return eventText;
    }

    public void setEventText(String text) {
        if (text.length() > 1) {
            eventText.setText("- " + text);
        } else {
            eventText.setText(text);
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
        return date.equals(theOther.date) && eventText.getText().equals(theOther.getEventText().getText());
    }
}
