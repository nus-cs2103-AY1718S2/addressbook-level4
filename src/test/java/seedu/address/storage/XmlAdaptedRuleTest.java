package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.rule.NotificationRule;
import seedu.address.model.rule.RuleType;

public class XmlAdaptedRuleTest {

    @Test
    public void toModelType_validRuleDetails_returnsRule() throws Exception {
        XmlAdaptedRule rule = new XmlAdaptedRule("c/TEST", RuleType.NOTIFICATION.toString());
        assertEquals(new NotificationRule("c/TEST"), rule.toModelType());
    }

}
