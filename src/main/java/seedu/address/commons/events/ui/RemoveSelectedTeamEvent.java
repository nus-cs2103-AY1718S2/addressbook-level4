package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.team.TeamName;

//@@author jordancjq
/**
 * Indicates a request to remove the selected team name.
 */
public class RemoveSelectedTeamEvent extends BaseEvent {

    public final TeamName teamName;

    public RemoveSelectedTeamEvent(TeamName teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
