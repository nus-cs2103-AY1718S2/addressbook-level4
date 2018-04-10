package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.activity.Activity;

public class CalendarRemoveActivityEvent extends BaseEvent{

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
