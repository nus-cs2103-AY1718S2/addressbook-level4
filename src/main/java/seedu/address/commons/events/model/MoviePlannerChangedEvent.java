package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyMoviePlanner;

/** Indicates the MoviePlanner in the model has changed*/
public class MoviePlannerChangedEvent extends BaseEvent {

    public final ReadOnlyMoviePlanner data;

    public MoviePlannerChangedEvent(ReadOnlyMoviePlanner data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of cinemas " + data.getCinemaList().size() + ", number of tags " + data.getTagList().size()
                + ", number of movies " + data.getMovieList().size();
    }
}
