// @@author kush1509
package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.skill.Skill;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class JobBuilder {

    public static final String DEFAULT_POSITION = "Hardware Engineer";
    public static final String DEFAULT_TEAM = "Microprocessor";
    public static final String DEFAULT_LOCATION = "Singapore";
    public static final String DEFAULT_NUMBER_OF_POSITIONS = "2";
    public static final String DEFAULT_TAGS = "C++";

    private Position position;
    private Team team;
    private Location location;
    private NumberOfPositions numberOfPositions;
    private Set<Skill> skills;

    public JobBuilder() {
        position = new Position(DEFAULT_POSITION);
        team = new Team(DEFAULT_TEAM);
        location = new Location(DEFAULT_LOCATION);
        numberOfPositions = new NumberOfPositions(DEFAULT_NUMBER_OF_POSITIONS);
        skills = SampleDataUtil.getSkillSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the JobBuilder with the data of {@code jobToCopy}.
     */
    public JobBuilder(Job jobToCopy) {
        position = jobToCopy.getPosition();
        team = jobToCopy.getTeam();
        location = jobToCopy.getLocation();
        numberOfPositions = jobToCopy.getNumberOfPositions();
        skills = new HashSet<>(jobToCopy.getSkills());
    }

    /**
     * Sets the {@code Position} of the {@code Job} that we are building.
     */
    public JobBuilder withPosition(String position) {
        this.position = new Position(position);
        return this;
    }

    /**
     * Sets the {@code Team} of the {@code Job} that we are building.
     */
    public JobBuilder withTeam(String team) {
        this.team = new Team(team);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Job} that we are building.
     */
    public JobBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code NumberOfPositions} of the {@code Job} that we are building.
     */
    public JobBuilder withNumberOfPositions(String numberOfPositions) {
        this.numberOfPositions = new NumberOfPositions(numberOfPositions);
        return this;
    }

    /**
     * Parses the {@code skills} into a {@code Set<Skill>} and set it to the {@code Job} that we are building.
     */
    public JobBuilder withSkills(String ... skills) {
        this.skills = SampleDataUtil.getSkillSet(skills);
        return this;
    }

    public Job build() {
        return new Job(position, team, location, numberOfPositions, skills);
    }
}
