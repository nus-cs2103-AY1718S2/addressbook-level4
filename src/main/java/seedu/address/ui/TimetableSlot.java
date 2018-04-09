//@@author LeonidAgarth
package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.model.event.WeeklyEvent;

/**
 * Node to display each slot of the Timetable
 */
public class TimetableSlot extends UiPart<Region> {

    private static final String FXML = "TimetableSlot.fxml";

    private static final String[] AVAILABLE_COLORS = new String[] {"red", "orange", "yellow", "blue", "teal",
        "green", "purple", "pink", "brown"};

    @FXML
    private GridPane box;
    @FXML
    private VBox node;
    @FXML
    private Text module;
    @FXML
    private Text lectureType;
    @FXML
    private Text venue;

    /**
     * Create a anchor pane node containing all of the {@code children}.
     * The Slot of the node is not set in the constructor
     */
    public TimetableSlot() {
        super(FXML);
        initStyle("blank1");
    }

    /**
     * Initialize the style of the Slot node
     */
    public void initStyle(String style) {
        node.setPrefSize(250, 60);
        setStyleClass(style);
        module.setText("");
        lectureType.setText("");
        venue.setText("");
    }

    public void setStyleClass(String... styles) {
        ObservableList<String> styleClass = node.getStyleClass();
        styleClass.clear();
        for (String style : styles) {
            styleClass.add(style);
        }
    }

    public GridPane getBox() {
        return box;
    }

    public VBox getNode() {
        return node;
    }

    public Text getModule() {
        return module;
    }

    public void setModule(String style, WeeklyEvent mod) {
        setStyleClass(style, AVAILABLE_COLORS[Math.abs(mod.getName().hashCode()) % AVAILABLE_COLORS.length]);
        module.setText(mod.getName());
        if (!mod.getDetails().isEmpty() && mod.getDetails().size() >= 2) {
            lectureType.setText(mod.getDetails().get(1));
        }
        venue.setText(mod.getVenue());
    }

    public void setText(String text) {
        setStyleClass("time");
        module.setText(text);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimetableSlot)) {
            return false;
        }

        // state check
        TimetableSlot theOther = (TimetableSlot) other;
        return node.equals(theOther.node);
    }
}
