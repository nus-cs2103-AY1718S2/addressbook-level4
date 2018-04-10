package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.activity.Activity;

/**
 * Indicates the an activity has been removed in the model.
 */
public class CalendarRemoveActivityEvent extends BaseEvent {

    public final Activity activityToRemove;

    public CalendarRemoveActivityEvent(Activity activityToRemove)   {
        this.activityToRemove = activityToRemove;
    }

    @Override
    public String toString()    {
        String activityType = activityToRemove.getActivityType();
        return activityType + " removed from Calendar: " + activityToRemove.getName().fullName;
    }
}
