//@@author LeonidAgarth
package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
        requireAllNonNull(mod, schedule);
        this.name = mod.getModuleCode();
        this.venue = schedule.getClassNo();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.day = schedule.getDayText();
        this.details = new String[]{};
    }

    public String getDay() {
        return day;
    }

    public ObservableList<String> getDetails() {
        ArrayList<String> temp = new ArrayList<String>(Arrays.asList(details));
        return FXCollections.observableArrayList(temp);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof WeeklyEvent)) {
            return false;
        }

        WeeklyEvent otherEvent = (WeeklyEvent) other;
        return otherEvent.getName().equals(this.getName())
                && otherEvent.getVenue().equals(this.getVenue())
                && otherEvent.getDate().equals(this.getDate())
                && otherEvent.getStartTime().equals(this.getStartTime())
                && otherEvent.getEndTime().equals(this.getEndTime())
                && otherEvent.getDay().equals(this.getDay())
                && otherEvent.getDetails().equals(this.getDetails());
    }
}
