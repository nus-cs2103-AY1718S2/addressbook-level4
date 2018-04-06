package seedu.address.testutil;

import seedu.address.model.rule.Rule;

/**
 * A utility class to help with building Rule objects.
 */
public class RuleBuilder {

    public static final String DEFAULT_VALUE = "testing";

    private String value;

    public RuleBuilder() {
        value = DEFAULT_VALUE;
    }

    /**
     * Initializes the RuleBuilder with the data of {@code ruleToCopy}.
     */
    public RuleBuilder(Rule ruleToCopy) {
        value = ruleToCopy.toString();
    }

    /**
     * Sets the {@code Name} of the {@code Rule} that we are building.
     */
    public RuleBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public Rule build() {
        return new Rule(value);
    }

}
