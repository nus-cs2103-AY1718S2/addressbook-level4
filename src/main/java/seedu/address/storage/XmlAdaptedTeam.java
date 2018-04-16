package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

/**
 * JAXB-friendly adapted version of the Team
 */
/** @@author Codee */
public class XmlAdaptedTeam {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's %s field is missing!";

    @XmlElement(required = true)
    private String teamName;
    @XmlElement
    private List<XmlAdaptedPerson> players = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTeam.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTeam() {}

    /**
     * Constructs a {@code XmlAdaptedTeam} with the given {@code teamName}.
     */
    public XmlAdaptedTeam(String teamName, List<XmlAdaptedPerson> persons) {
        this.teamName = teamName;
        if (persons != null) {
            this.players = new ArrayList<>(persons);
        }
    }

    /**
     * Converts a given Team into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTeam
     */
    public XmlAdaptedTeam(Team source) {
        teamName = source.getTeamName().toString();
        players = new ArrayList<>();
        for (Person person : source.getTeamPlayers()) {
            players.add(new XmlAdaptedPerson(person));
        }
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Team object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Team toModelType() throws IllegalValueException {
        if (this.teamName == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TeamName.class.getSimpleName())));
        }
        if (!TeamName.isValidName(this.teamName)) {
            throw new IllegalValueException(TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
        }
        final TeamName teamName = new TeamName(this.teamName);

        final List<Person> teamPlayers = new ArrayList<>();
        for (XmlAdaptedPerson player : players) {
            teamPlayers.add(player.toModelType());
        }

        return new Team(teamName, teamPlayers);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTeam)) {
            return false;
        }
        XmlAdaptedTeam otherTeam = (XmlAdaptedTeam) other;
        return Objects.equals(teamName, otherTeam.teamName)
                && players.equals(otherTeam.players);

    }
}
