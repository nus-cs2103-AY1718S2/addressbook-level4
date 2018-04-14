package seedu.address.testutil;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.rule.NotificationRule;

/**
 * A utility class to help with building {@code NotificationRule} objects.
 */
public class NotificationRuleBuilder {

    public static final String DEFAULT_VALUE = "h/>0";

    private String value;
    private Model model;

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

    /**
     * Sets the model data of the {@code Rule} action that we are building.
     */
    public NotificationRuleBuilder withModel(Model model) {
        this.model = model;
        return this;
    }

    /**
     * Builds the notification rule.
     */
    public NotificationRule build() {
        NotificationRule rule = new NotificationRule(value);
        rule.action.setData(model, new CommandHistory(), new UndoRedoStack());
        return rule;
    }

}
