//@@author kush1509
package seedu.address.model.job;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.skill.Skill;
import seedu.address.model.skill.UniqueSkillList;

/**
 * Represents a Job in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Job {

    private final Position position;
    private final Team team;
    private final Location location;
    private final NumberOfPositions numberOfPositions;

    private final UniqueSkillList skills;

    /**
     * Every field must be present and not null.
     */
    public Job(Position position, Team team, Location location, NumberOfPositions numberOfPositions,
               Set<Skill> skills) {
        requireAllNonNull(position, team, location);
        this.position = position;
        this.team = team;
        this.location = location;
        this.numberOfPositions = numberOfPositions;

        // protect internal skills from changes in the arg list
        this.skills = new UniqueSkillList(skills);
    }


    public Position getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }

    public Location getLocation() {
        return location;
    }

    public NumberOfPositions getNumberOfPositions() {
        return numberOfPositions;
    }

    /**
     * Returns an immutable skill set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(skills.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Job)) {
            return false;
        }
        Job otherJob = (Job) other;
        return otherJob.getPosition().equals(this.getPosition())
                && otherJob.getTeam().equals(this.getTeam())
                && otherJob.getLocation().equals(this.getLocation())
                && otherJob.getNumberOfPositions().equals(this.getNumberOfPositions());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(position, team, location, numberOfPositions);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPosition())
                .append(" Team: ")
                .append(getTeam())
                .append(" Location: ")
                .append(getLocation())
                .append(" Number of Positions: ")
                .append(getNumberOfPositions())
                .append(" Skills: ");
        getSkills().forEach(builder::append);
        return builder.toString();
    }
}
