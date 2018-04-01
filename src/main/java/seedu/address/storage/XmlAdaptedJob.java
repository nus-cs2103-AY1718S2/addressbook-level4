package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;

/**
 * JAXB-friendly version of the Job.
 */
public class XmlAdaptedJob {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job's %s field is missing!";

    @XmlElement(required = true)
    private String position;
    @XmlElement(required = true)
    private String team;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String numberOfPositions;

    /**
     * Constructs an XmlAdaptedJob.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJob() {}

    /**
     * Constructs an {@code XmlAdaptedJob} with the given job details.
     */
    public XmlAdaptedJob(String position, String team, String location, String numberOfPositions) {
        this.position = position;
        this.team = team;
        this.location = location;
        this.numberOfPositions = numberOfPositions;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedJob
     */
    public XmlAdaptedJob(Job source) {
        position = source.getPosition().value;
        team = source.getTeam().value;
        location = source.getLocation().value;
        numberOfPositions = source.getNumberOfPositions().value;
    }

    /**
     * Converts this jaxb-friendly adapted job object into the model's Job object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job
     */
    public Job toModelType() throws IllegalValueException {
        if (this.position == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Position.class.getSimpleName()));
        }
        if (!Position.isValidPosition(this.position)) {
            throw new IllegalValueException(Position.MESSAGE_POSITION_CONSTRAINTS);
        }
        final Position position = new Position(this.position);

        if (this.team == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Team.class.getSimpleName()));
        }
        if (!Team.isValidTeam(this.team)) {
            throw new IllegalValueException(Team.MESSAGE_TEAM_CONSTRAINTS);
        }
        final Team team = new Team(this.team);

        if (this.location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(this.location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location location = new Location(this.location);

        if (this.numberOfPositions == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NumberOfPositions.class.getSimpleName()));
        }
        if (!NumberOfPositions.isValidNumberOfPositions(this.numberOfPositions)) {
            throw new IllegalValueException(NumberOfPositions.MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS);
        }
        final NumberOfPositions numberOfPositions = new NumberOfPositions(this.numberOfPositions);

        return new Job(position, team, location, numberOfPositions);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedJob)) {
            return false;
        }

        XmlAdaptedJob otherJob = (XmlAdaptedJob) other;
        return Objects.equals(position, otherJob.position)
                && Objects.equals(team, otherJob.team)
                && Objects.equals(location, otherJob.location)
                && Objects.equals(numberOfPositions, otherJob.numberOfPositions);
    }
}
