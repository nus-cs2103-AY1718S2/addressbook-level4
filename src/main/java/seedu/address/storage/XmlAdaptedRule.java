//@@author ewaldhew
package seedu.address.storage;

import static seedu.address.model.rule.RuleType.NOTIFICATION;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.RuleType;

/**
 * JAXB-friendly version of the Rule.
 */
public class XmlAdaptedRule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Rule's %s field is missing!";

    @XmlElement(required = true)
    private String value;

    @XmlElement(required = true)
    private String type;

    /**
     * Constructs an XmlAdaptedRule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRule() {}

    /**
     * Constructs an {@code XmlAdaptedRule} with the given rule details.
     */
    public XmlAdaptedRule(String value, String type) {
        this.value = value;
        this.type = type;
    }

    /**
     * Converts a given Rule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRule
     */
    public XmlAdaptedRule(Rule source) {
        value = source.description;
        type = source.type.toString();
    }

    /**
     * Converts this jaxb-friendly adapted rule object into the model's Rule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted rule
     */
    public Rule toModelType() throws IllegalValueException {
        if (this.type == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, RuleType.class.getSimpleName()));
        }

        try {
            switch (RuleType.valueOf(type)) {
            case NOTIFICATION:
                return new NotificationRule(value);
            default:
                throw new IllegalValueException(Rule.MESSAGE_RULE_INVALID);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Rule.MESSAGE_RULE_INVALID);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRule)) {
            return false;
        }

        XmlAdaptedRule otherRule = (XmlAdaptedRule) other;
        return value.equals(otherRule.value);
    }
}
