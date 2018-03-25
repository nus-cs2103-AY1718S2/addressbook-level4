package seedu.address.model.job;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Jon in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Job {
    
    private final Position position;
    private final Team team;
    private final Location location;

    /**
     * Every field must be present and not null.
     */
    public Job(Position position, Team team, Location location) {
        requireAllNonNull(position, team, location);
        this.position = position;
        this.team = team;
        this.location = location;
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
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, profilePicture, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPosition())
                .append(" Team: ")
                .append(getTeam())
                .append(" Location: ")
                .append(getLocation());
        return builder.toString();
    }
}
