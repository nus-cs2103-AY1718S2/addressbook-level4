package seedu.flashy.model.cardtag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.flashy.testutil.TypicalCards.CHEMISTRY_CARD;
import static seedu.flashy.testutil.TypicalCards.COMSCI_CARD;
import static seedu.flashy.testutil.TypicalCards.GEOGRAPHY_CARD;
import static seedu.flashy.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.flashy.testutil.TypicalTags.BIOLOGY_TAG;
import static seedu.flashy.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.flashy.testutil.TypicalTags.PHYSICS_TAG;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.flashy.model.CardBank;
import seedu.flashy.testutil.TypicalCardBank;

//@@author jethrokuan
public class CardTagTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CardBank cardBank = TypicalCardBank.getTypicalCardBank();

    private CardTag cardTag;

    @Before
    public void setUp() throws Exception {
        cardTag = new CardTag();

        cardTag.addEdge(MATHEMATICS_CARD, PHYSICS_TAG);
        cardTag.addEdge(COMSCI_CARD, PHYSICS_TAG);

        cardTag.addEdge(CHEMISTRY_CARD, BIOLOGY_TAG);
        cardTag.addEdge(COMSCI_CARD, BIOLOGY_TAG);
    }

    @Test
    public void addEdge_worksForNewEdges() {
        assertTrue(cardTag.isConnected(MATHEMATICS_CARD, PHYSICS_TAG));
        assertTrue(cardTag.isConnected(CHEMISTRY_CARD, BIOLOGY_TAG));
    }

    @Test
    public void addEdge_whereEdgeExistsThrowsDuplicateEdgeException() throws DuplicateEdgeException {
        thrown.expect(DuplicateEdgeException.class);
        cardTag.addEdge(COMSCI_CARD, PHYSICS_TAG);
    }

    @Test
    public void getCards_withEdges() {
        assertEquals(cardTag.getCards(PHYSICS_TAG, cardBank.getCardList()), Stream.of(MATHEMATICS_CARD, COMSCI_CARD)
                .collect(Collectors.toList()));
        assertEquals(cardTag.getCards(BIOLOGY_TAG, cardBank.getCardList()), Stream.of(CHEMISTRY_CARD, COMSCI_CARD)
                .collect(Collectors.toList()));
    }

    @Test
    public void getCards_withoutEdges() {
        assertEquals(cardTag.getCards(MATHEMATICS_TAG, cardBank.getCardList()),
                Stream.of().collect(Collectors.toList()));
    }

    @Test
    public void getTags_withEdges() {
        assertEquals(cardTag.getTags(MATHEMATICS_CARD, cardBank.getTagList()),
                Stream.of(PHYSICS_TAG).collect(Collectors.toList()));
        assertEquals(cardTag.getTags(COMSCI_CARD, cardBank.getTagList()), Stream.of(PHYSICS_TAG, BIOLOGY_TAG)
                .collect(Collectors.toList()));
    }

    @Test
    public void getTags_withoutEdges() {
        assertEquals(cardTag.getTags(GEOGRAPHY_CARD, cardBank.getTagList()),
                Stream.of().collect(Collectors.toList()));
    }

    @Test
    public void isConnected_works() {
        assertEquals(cardTag.isConnected(MATHEMATICS_CARD, BIOLOGY_TAG), false);
        assertEquals(cardTag.isConnected(COMSCI_CARD, BIOLOGY_TAG), true);
    }

    @Test
    public void removeEdge_works() throws EdgeNotFoundException {
        cardTag.removeEdge(COMSCI_CARD, BIOLOGY_TAG);
        assertTrue(!cardTag.getTags(COMSCI_CARD, cardBank.getTagList()).contains(BIOLOGY_TAG));
        assertTrue(!cardTag.getCards(BIOLOGY_TAG, cardBank.getCardList()).contains(COMSCI_CARD));
    }

    @Test
    public void removeEdge_noTagsLeft_removedFromCardMap() throws EdgeNotFoundException {
        cardTag.removeEdge(MATHEMATICS_CARD, PHYSICS_TAG);
        assertEquals(cardTag.getCardMap().size(), 2);
    }

    @Test
    public void removeEdge_noCardsLeft_removedFromTagMap() throws EdgeNotFoundException {
        // Case: Biology Tag still has 1 card associated -> still in Tag Map
        cardTag.removeEdge(CHEMISTRY_CARD, BIOLOGY_TAG);
        assertEquals(cardTag.getTagMap().size(), 2);

        // Case: Biology Tag has no more cards associated -> removed from Tag Map
        cardTag.removeEdge(COMSCI_CARD, BIOLOGY_TAG);
        assertEquals(cardTag.getTagMap().size(), 1);
    }

    @Test
    public void removeEdge_onNonExistingEdgeThrowsEdgeNotFoundException() throws EdgeNotFoundException {
        thrown.expect(EdgeNotFoundException.class);
        cardTag.removeEdge(MATHEMATICS_CARD, BIOLOGY_TAG);
    }
}
//@@author
