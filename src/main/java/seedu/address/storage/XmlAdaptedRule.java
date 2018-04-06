//@@author ewaldhew
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.rule.Rule;

/**
 * JAXB-friendly version of the Rule.
 */
public class XmlAdaptedRule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Rule's %s field is missing!";

    @XmlElement(required = true)
    private String value;

    /**
     * Constructs an XmlAdaptedRule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRule() {}

    /**
     * Constructs an {@code XmlAdaptedRule} with the given rule details.
     */
    public XmlAdaptedRule(String value) {
        this.value = value;
    }

    /**
     * Converts a given Rule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRule
     */
    public XmlAdaptedRule(Rule source) {
        value = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted rule object into the model's Rule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted rule
     */
    public Rule toModelType() throws IllegalValueException {
        return new Rule(value);
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
