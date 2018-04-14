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

    /**
     * Set the node to contain the {@code mod}
     * with a specified {@code style} and a random color
     *
     * @return index of the color chosen
     */
    public int setModule(String style, WeeklyEvent mod) {
        int colorIndex = Math.abs(mod.getName().hashCode()) % AVAILABLE_COLORS.length;
        setStyleClass(style, AVAILABLE_COLORS[colorIndex]);
        module.setText(mod.getName());
        if (!mod.getDetails().isEmpty() && mod.getDetails().size() >= 2) {
            lectureType.setText(mod.getDetails().get(0));
        }
        venue.setText(mod.getVenue());
        return colorIndex;
    }

    /**
     * Randomly change the color of the node while keeping the {@code style}
     *
     * @return index of the color randomized
     */
    public int randomizeColor(String style) {
        int randomColor = (int) Math.floor(Math.random() * AVAILABLE_COLORS.length);
        setStyleClass(style, AVAILABLE_COLORS[randomColor]);
        return randomColor;
    }

    /**
     * Set the node to have a color specified at {@code colorIndex}
     */
    public void setColor(int colorIndex) {
        node.getStyleClass().set(1, AVAILABLE_COLORS[colorIndex]);
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
