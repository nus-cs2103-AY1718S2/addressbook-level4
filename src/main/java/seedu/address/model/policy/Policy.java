package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an insurance Policy that a Person can apply to.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Policy {
    private final Price price;
    private final Coverage coverage;
    private final Date beginning;
    private final Date expiration;

    /**
     * Every field must be present and not null.
     */
    public Policy(Price price, Coverage coverage, Date beginning, Date expiration) {
        requireAllNonNull(price, coverage, beginning, expiration);
        this.price = price;
        this.coverage = coverage;
        this.beginning = beginning;
        this.expiration = expiration;
    }


    public Price getPrice() { return price; }

    public Coverage getCoverage() { return coverage; }

    public Date getBeginning() { return beginning; }

    public Date getExpiration() { return expiration; }

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
        builder.append(" Price: ")
                .append(getPrice())
                .append(" Covering: ")
                .append(getCoverage())
                .append(" Starting: ")
                .append(getBeginning())
                .append(" Ending: ")
                .append(getExpiration());
        return builder.toString();
    }

}
