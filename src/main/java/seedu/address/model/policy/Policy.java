package seedu.address.model.policy;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an insurance Policy that a Person can apply to.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Policy {
    public static final String DURATION_CONSTRAINTS = "Beginning date must be before or the same as expiration date.";

    private final Price price;
    private final Coverage coverage;
    private final Date beginning;
    private final Date expiration;

    /**
     * Every field must be present and not null.
     * Beginning date must be before or the same as expiration date.
     */
    public Policy(Price price, Coverage coverage, Date beginning, Date expiration) {
        requireAllNonNull(price, coverage, beginning, expiration);
        checkArgument(isValidDuration(beginning, expiration), DURATION_CONSTRAINTS);

        this.price = price;
        this.coverage = coverage;
        this.beginning = beginning;
        this.expiration = expiration;
    }


    public Price getPrice() {
        return price;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public Date getBeginning() {
        return beginning;
    }

    public Date getExpiration() {
        return expiration;
    }

    /**
     * Returns true if a given (beginningDate, expirationDate) tuple represents a valid duration.
     */
    public static boolean isValidDuration(Date beginning, Date expiration) {
        return beginning.compareTo(expiration) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.policy.Policy)) {
            return false;
        }

        seedu.address.model.policy.Policy otherPolicy = (seedu.address.model.policy.Policy) other;
        return otherPolicy.getPrice().equals(this.getPrice())
                && otherPolicy.getCoverage().equals(this.getCoverage())
                && otherPolicy.getBeginning().equals(this.getBeginning())
                && otherPolicy.getExpiration().equals(this.getExpiration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, coverage, beginning, expiration);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("From ")
                .append(getBeginning())
                .append(" to ")
                .append(getExpiration())
                .append("\n           Price: ")
                .append(getPrice())
                .append(" per month\n           Covers ");
        if(coverage.getIssues().isEmpty()) {
            builder.append("nothing");
        } else {
            builder.append(getCoverage());
        }

        return builder.toString();
    }

}
