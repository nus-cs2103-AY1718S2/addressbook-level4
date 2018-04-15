package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPairs.getTypicalPairs;
import static seedu.address.ui.testutil.GuiTestAssertPair.assertCardDisplaysPair;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PairCardHandle;
import guitests.guihandles.PairListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.pair.Pair;

public class PairListPanelTest extends GuiUnitTest {
    private static final ObservableList<Pair> TYPICAL_PAIRS =
            FXCollections.observableList(getTypicalPairs());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private PairListPanelHandle pairListPanelHandle;

    @Before
    public void setUp() {
        PairListPanel pairListPanel = new PairListPanel(TYPICAL_PAIRS);
        uiPartRule.setUiPart(pairListPanel);

        pairListPanelHandle = new PairListPanelHandle(getChildNode(pairListPanel.getRoot(),
                PairListPanelHandle.PERSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PAIRS.size(); i++) {
            pairListPanelHandle.navigateToCard(TYPICAL_PAIRS.get(i));
            Pair expectedPair = TYPICAL_PAIRS.get(i);
            PairCardHandle actualCard = pairListPanelHandle.getPairCardHandle(i);

            assertCardDisplaysPair(expectedPair, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
