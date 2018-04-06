//@@author ewaldhew
package seedu.address.model.rule;

/**
 * Represents a Rule in the rule book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Rule {

    private final String value;

    public Rule(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Rule)) {
            return false;
        }

        Rule otherRule = (Rule) other;
        return otherRule.value.equals(this.value);
    }

}
