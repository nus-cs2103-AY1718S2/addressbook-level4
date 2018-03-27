package seedu.address.model.event;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.Schedule;

/**
 * Events, such as lectures, tutorial slots, to appear in timetable
 */
public class WeeklyEvent extends Event {
    private String day;
    private String[] details;

    public WeeklyEvent(String name, String venue, String start, String end, String... details) {
        super(name, venue, "NA", start, end);
        this.day = details[0];          //Placeholder command
        this.details = details;
    }

    public WeeklyEvent(Module mod, Schedule schedule) {
        this.name = mod.getModuleCode();
        //This part of the code is for after Schedule's getter methods are implemented
        /*this.venue = schedule.getClassNo();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.day = schedule.getDayText();*/
        this.details = null;
    }

    public String getDay() {
        return day;
    }

    public ObservableList<String> getDetails() {
        ArrayList<String> temp = new ArrayList<String>(Arrays.asList(details));
        return FXCollections.observableArrayList(temp);
    }
}
