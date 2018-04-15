package seedu.address.storage;

import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedRule.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.Rule;
import seedu.address.model.rule.RuleType;
import seedu.address.testutil.Assert;

public class XmlAdaptedRuleTest {

    private static final String INVALID_RULE = "invalid";
    private static final String VALID_RULE = "c/Btc AND p/+>40";

    private static final String INVALID_TYPE = "invalid";
    private static final String VALID_TYPE = RuleType.NOTIFICATION.toString();

    @Test
    public void toModelType_validRuleDetails_returnsRule() throws Exception {
        XmlAdaptedRule rule = new XmlAdaptedRule(VALID_RULE, VALID_TYPE);
        assertTrue(new NotificationRule(VALID_RULE).equals(rule.toModelType()));
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedRule rule = new XmlAdaptedRule(INVALID_RULE, VALID_TYPE);
        String expectedMessage = Rule.MESSAGE_RULE_INVALID;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, rule::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedRule rule = new XmlAdaptedRule(null, VALID_TYPE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rule.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, rule::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        XmlAdaptedRule rule = new XmlAdaptedRule(VALID_RULE, INVALID_TYPE);
        String expectedMessage = Rule.MESSAGE_RULE_INVALID;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, rule::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        XmlAdaptedRule rule = new XmlAdaptedRule(VALID_RULE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RuleType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, rule::toModelType);
    }

}
