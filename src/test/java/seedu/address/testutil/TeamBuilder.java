package seedu.address.testutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * A utility class to help with building a TeamList.
 */
/** @@author Codee */
public class TeamBuilder {

    public static final String DEFAULT_TEAMNAME_ONE = "Arsenal";
    public static final String DEFAULT_TEAMNAME_TWO = "Chelsea";

    private ObservableList<Team> teams;

    private Team teamOne;
    private Team teamTwo;
    private TeamName teamNameOne;
    private TeamName teamNameTwo;


    public TeamBuilder() {
        teams = FXCollections.observableArrayList();
        teamNameOne = new TeamName(DEFAULT_TEAMNAME_ONE);
        teamNameTwo = new TeamName(DEFAULT_TEAMNAME_TWO);
        teamOne = new Team(teamNameOne);
        teamTwo = new Team(teamNameTwo);
        teams.add(teamOne);
        teams.add(teamTwo);
    }

    public ObservableList<Team> build() {
        return teams;
    }




}
