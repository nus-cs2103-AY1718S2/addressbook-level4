package seedu.address.testutil;

import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * A utility class to help with building a TeamList.
 */
//@@author Codee
public class TeamBuilder {

    public static final String DEFAULT_TEAM_NAME = "Arsenal";

    private TeamName teamName;


    public TeamBuilder() {
        teamName = new TeamName(DEFAULT_TEAM_NAME);
    }

    /**
     * Initializes the TeamBuilder with the data of {@code teamToCopy}.
     */
    public TeamBuilder(Team teamToCopy) {
        teamName = teamToCopy.getTeamName();
    }

    /**
     * Sets the {@code TeamName} of the {@code Team} that we are building.
     */
    public TeamBuilder withTeamName(String teamName) {
        this.teamName = new TeamName(teamName);
        return this;
    }

    public Team build() {
        return new Team(teamName);
    }
}
