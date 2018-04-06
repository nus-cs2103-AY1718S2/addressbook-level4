package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COIN;
import static seedu.address.testutil.TypicalRules.getTypicalRules;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRule;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.RuleCardHandle;
import guitests.guihandles.RuleListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.rule.Rule;

public class RuleListPanelTest extends GuiUnitTest {
    private static final ObservableList<Rule> TYPICAL_RULES =
            FXCollections.observableList(getTypicalRules());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_COIN);

    private RuleListPanelHandle ruleListPanelHandle;

    @Before
    public void setUp() {
        RuleListPanel ruleListPanel = new RuleListPanel(TYPICAL_RULES);
        uiPartRule.setUiPart(ruleListPanel);

        ruleListPanelHandle = new RuleListPanelHandle(getChildNode(ruleListPanel.getRoot(),
                RuleListPanelHandle.COIN_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_RULES.size(); i++) {
            ruleListPanelHandle.navigateToCard(TYPICAL_RULES.get(i));
            Rule expectedRule = TYPICAL_RULES.get(i);
            RuleCardHandle actualCard = ruleListPanelHandle.getRuleCardHandle(i);

            assertCardDisplaysRule(expectedRule, actualCard);
            assertEquals(expectedRule.toString(), actualCard.getValue());
        }
    }
}
