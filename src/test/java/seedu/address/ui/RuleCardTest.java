package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRule;

import org.junit.Test;

import guitests.guihandles.RuleCardHandle;
import seedu.address.model.rule.Rule;
import seedu.address.testutil.RuleBuilder;

public class RuleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Rule testRule = new RuleBuilder().build();
        RuleCard ruleCard = new RuleCard(testRule, 1);
        uiPartRule.setUiPart(ruleCard);
        assertCardDisplay(ruleCard, testRule, testRule.toString());
    }

    @Test
    public void equals() {
        Rule rule = new RuleBuilder().build();
        RuleCard ruleCard = new RuleCard(rule, 0);

        // same rule, same index -> returns true
        RuleCard copy = new RuleCard(rule, 0);
        assertTrue(ruleCard.equals(copy));

        // same object -> returns true
        assertTrue(ruleCard.equals(ruleCard));

        // null -> returns false
        assertFalse(ruleCard.equals(null));

        // different types -> returns false
        assertFalse(ruleCard.equals(0));

        // different rule, same index -> returns false
        Rule differentRule = new RuleBuilder().withValue("Different").build();
        assertFalse(ruleCard.equals(new RuleCard(differentRule, 0)));
    }

    /**
     * Asserts that {@code ruleCard} displays the details of {@code expectedRule} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RuleCard ruleCard, Rule expectedRule, String expected) {
        guiRobot.pauseForHuman();

        RuleCardHandle ruleCardHandle = new RuleCardHandle(ruleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expected, ruleCardHandle.getValue());

        // verify rule details are displayed correctly
        assertCardDisplaysRule(expectedRule, ruleCardHandle);
    }
}
