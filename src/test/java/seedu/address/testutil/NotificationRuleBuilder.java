package seedu.address.testutil;

import seedu.address.model.rule.NotificationRule;

/**
 * A utility class to help with building {@code NotificationRule} objects.
 */
public class NotificationRuleBuilder {

    public static final String DEFAULT_VALUE = "h/>0";

    private String value;

    public NotificationRuleBuilder() {
        value = DEFAULT_VALUE;
    }

    /**
     * Initializes the RuleBuilder with the data of {@code ruleToCopy}.
     */
    public NotificationRuleBuilder(NotificationRule ruleToCopy) {
        value = ruleToCopy.description;
    }

    /**
     * Sets the {@code Name} of the {@code Rule} that we are building.
     */
    public NotificationRuleBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public NotificationRule build() {
        return new NotificationRule(value);
    }

}
