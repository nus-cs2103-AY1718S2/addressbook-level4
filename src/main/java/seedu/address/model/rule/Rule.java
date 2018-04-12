//@@author ewaldhew
package seedu.address.model.rule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a Rule in the rule book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Rule<T> {

    public static final String MESSAGE_RULE_INVALID = "Rule description is invalid";
    private static final String RULE_FORMAT_STRING = "[%1$s]%2$s";

    public final RuleType type;
    public final String description;

    private final Command action;
    private final Predicate<T> condition;

    protected Rule(String description, RuleType type, ActionParser actionParser, ConditionParser<T> conditionParser) {
        requireAllNonNull(description, type, actionParser, conditionParser);
        this.description = description;
        this.type = type;
        this.action = actionParser.parse(description);
        this.condition = validateAndCreateCondition(description, conditionParser);
    }

    /**
     * Uses the given parser to validate the condition string and create the condition object.
     */
    private static <T> Predicate<T> validateAndCreateCondition(String conditionArgs, ConditionParser<T> parser) {
        try {
            return parser.parse(conditionArgs);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(MESSAGE_RULE_INVALID);
        }
    }

    /**
     * Checks the trigger condition against the provided object, then
     * executes the command tied to it if it matches
     * @param t The object to check against
     * @return Whether the command was successful.
     */
    public boolean checkAndFire(T t) {
        if (!condition.test(t)) {
            return false;
        }

        try {
            action.execute();
            return true;
        } catch (CommandException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return String.format(RULE_FORMAT_STRING, type, description);
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
        return otherRule.description.equals(this.description)
                && otherRule.action.equals(this.action);
    }

    /**
     * Represents a function type used to generate the action for this rule.
     */
    @FunctionalInterface
    protected interface ActionParser {
        Command parse(String args);
    }

    /**
     * Represents a function type used to generate the trigger condition for this rule.
     */
    @FunctionalInterface
    protected interface ConditionParser<T> {
        Predicate<T> parse(String args) throws IllegalValueException;
    }

}
