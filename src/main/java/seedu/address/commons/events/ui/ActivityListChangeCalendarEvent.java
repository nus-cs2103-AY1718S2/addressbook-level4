package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.activity.Activity;

//@@author jasmoon
/**
 * Indicates the an activity has been removed in the model.
 */
public class ActivityListChangeCalendarEvent extends BaseEvent {

    public final Activity activityChange;

    public ActivityListChangeCalendarEvent(Activity activityToRemove)   {
        this.activityChange = activityToRemove;
    }

    @Override
    public String toString()    {
        String activityType = activityChange.getActivityType();
        return activityType + " removed from Calendar: " + activityChange.getName().fullName;
    }
}
