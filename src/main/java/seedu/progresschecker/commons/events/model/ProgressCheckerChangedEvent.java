package seedu.progresschecker.commons.events.model;

import seedu.progresschecker.commons.events.BaseEvent;
import seedu.progresschecker.model.ReadOnlyProgressChecker;

/** Indicates the ProgressChecker in the model has changed*/
public class ProgressCheckerChangedEvent extends BaseEvent {

    public final ReadOnlyProgressChecker data;

    public ProgressCheckerChangedEvent(ReadOnlyProgressChecker data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
