package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.rule.Rule;

public class XmlAdaptedRuleTest {

    @Test
    public void toModelType_validRuleDetails_returnsRule() throws Exception {
        XmlAdaptedRule rule = new XmlAdaptedRule("n/TEST");
        assertEquals(new Rule("n/TEST"), rule.toModelType());
    }

}
