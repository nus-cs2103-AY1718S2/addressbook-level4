package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;

/**
 * An Immutable RuleBook that is serializable to XML format
 */
@XmlRootElement(name = "rulebook")
public class XmlSerializableRuleBook {

    @XmlElement
    private List<XmlAdaptedRule> rules;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableRuleBook() {
        rules = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableRuleBook(ReadOnlyRuleBook src) {
        this();
        rules.addAll(src.getRuleList().stream().map(XmlAdaptedRule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code RuleBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRule} or {@code XmlAdaptedTag}.
     */
    public RuleBook toModelType() throws IllegalValueException {
        RuleBook ruleBook = new RuleBook();
        for (XmlAdaptedRule r : rules) {
            ruleBook.addRule(r.toModelType());
        }
        return ruleBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableRuleBook)) {
            return false;
        }

        XmlSerializableRuleBook otherAb = (XmlSerializableRuleBook) other;
        return rules.equals(otherAb.rules);
    }
}
