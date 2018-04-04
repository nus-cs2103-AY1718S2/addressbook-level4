package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.module.Module;
import seedu.address.model.module.Schedule;

/**
 * The weekly Timetable of the App.
 */
public class Timetable extends UiPart<Region> {

    public static boolean isViewed = false;
    private static final String FXML = "Timetable.fxml";

    private ArrayList<TimetableSlot> allTimetableSlots = new ArrayList<>(72);
    private VBox timetableView;
    private Text timetableHeader;
    private ObservableList<WeeklyEvent> events;

    @FXML
    private ListView<TimetableSlot> timetableListView;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public Timetable() {
        this(FXCollections.observableArrayList());
    }

    public Timetable(ObservableList<WeeklyEvent> eventList) {
        super(FXML);
        events = eventList;
        initTimetable();

        registerAsAnEventHandler(this);
    }

    /**
     * Create the timetableView for the timetable
     */
    private void initTimetable() {
        // Create the timetable grid pane
        GridPane timetable = new GridPane();
        timetable.setPrefSize(1250, 1000);
        timetable.setMaxWidth(1400);
        timetable.setGridLinesVisible(false);
        // Create rows and columns with anchor panes for the timetable
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                TimetableSlot apn = new TimetableSlot();
                timetable.add(apn.getBox(), j, i);
                allTimetableSlots.add(apn);
            }
        }
        // Slots of the week
        Text[] dayNames = new Text[]{new Text(""), new Text("Mon"), new Text("Tue"),
                new Text("Wed"), new Text("Thu"), new Text("Fri")};
        GridPane dayLabels = new GridPane();
        dayLabels.setPrefWidth(600);
        dayLabels.setGridLinesVisible(false);
        int col = 0;
        for (Text txt : dayNames) {
            txt.getStyleClass().add("dayName");
            HBox box = new HBox(txt);
            box.setPrefSize(200, 20);
            box.setAlignment(Pos.BASELINE_CENTER);
            if (col == 0) {
                box.getStyleClass().add("timecell");
            } else {
                box.getStyleClass().add("weekday");
            }
            dayLabels.add(box, col++, 0);
        }
        // Create timetableHeader and navigation
        timetableHeader = new Text();
        timetableHeader.setText("Timetable");
        timetableHeader.getStyleClass().add("yearMonth");
        HBox titleBar = new HBox(timetableHeader);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate timetable with the appropriate day numbers
        clearTimetable();
        showSlots();
        // Create the timetable timetableView
        timetableView = new VBox(titleBar, dayLabels, timetable);
    }

    /**
     * Clear the timetable to blank
     */
    private void clearTimetable() {
        // Fill the timetable
        boolean white = true;
        for (TimetableSlot slot : allTimetableSlots) {
            if (white) {
                slot.initStyle("blank1");
            } else {
                slot.initStyle("blank2");
            }
            white = !white;
        }
        for (int hour = 800; hour <= 1800; hour += 100) {
            TimetableSlot node = getSlotNode("time", hour);
            node.getNode().setPadding(new Insets(-7, 15, 5, 5));
            node.getNode().setAlignment(Pos.TOP_RIGHT);
            if (hour < 1000) {
                node.setText("0" + hour + "");
            } else {
                node.setText(hour + "");
            }
        }
    }

    /**
     * Show all events in eventList onto Timetable
     */
    private void showSlots() {
        for (WeeklyEvent mod : events) {
            String day = mod.getDay();
            int startTime = Integer.parseInt(mod.getStartTime());
            int endTime = Integer.parseInt(mod.getEndTime());
            //Right now, app doesn't support modules starting from 6pm onwards
            if (startTime > 1700) {
                continue;
            }
            if (endTime - startTime <= 100) {
                TimetableSlot node = getSlotNode(day, startTime);
                node.setModule("module1hr", mod);
            } else {
                TimetableSlot node = getSlotNode(day, startTime);
                node.setModule("module2hrtop", mod);
                WeeklyEvent blank = new WeeklyEvent(mod.getName(), "", "", "", "", "");
                node = getSlotNode(day, startTime + 100);
                node.setModule("module2hrbottom", blank);
                node.getModule().setText("");
            }
        }
    }

    /**
     * Return the Timetable to view in application
     */
    public VBox getTimetableView() {
        return timetableView;
    }

    /**
     * Return the list of all visible day in the current month
     */
    public ArrayList<TimetableSlot> getAllTimetableSlots() {
        return allTimetableSlots;
    }

    /**
     * Set all currently visible days to {@code allTimetableSlots}
     */
    public void setAllTimetableSlots(ArrayList<TimetableSlot> allTimetableSlots) {
        this.allTimetableSlots = allTimetableSlots;
    }

    public TimetableSlot getSlotNode(String day, int hour) {
        int column = 0;
        switch (day) {
        case "Monday":
            column = 1;
            break;
        case "Tuesday":
            column = 2;
            break;
        case "Wednesday":
            column = 3;
            break;
        case "Thursday":
            column = 4;
            break;
        case "Friday":
            column = 5;
            break;
        }
        int row = hour / 100 - 8;
        return allTimetableSlots.get(row * 6 + column);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Timetable)) {
            return false;
        }

        // state check
        Timetable theOther = (Timetable) other;
        return allTimetableSlots.equals(theOther.getAllTimetableSlots());
    }
}
