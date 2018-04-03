package seedu.club.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.club.testutil.EventsUtil.postNow;
import static seedu.club.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static seedu.club.testutil.TypicalMembers.getTypicalMembers;
import static seedu.club.ui.testutil.GuiTestAssert.assertCardDisplaysMember;
import static seedu.club.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.MemberCardHandle;
import guitests.guihandles.MemberListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.club.commons.events.ui.CompressMembersRequestEvent;
import seedu.club.commons.events.ui.DecompressMembersRequestEvent;
import seedu.club.commons.events.ui.JumpToListRequestEvent;
import seedu.club.model.member.Member;

public class MemberListPanelTest extends GuiUnitTest {
    private static final ObservableList<Member> TYPICAL_MEMBERS =
            FXCollections.observableList(getTypicalMembers());

    private static final CompressMembersRequestEvent COMPRESS_MEMBERS_REQUEST_EVENT = new CompressMembersRequestEvent();
    private static final DecompressMembersRequestEvent DECOMPRESS_MEMBERS_REQUEST_EVENT =
            new DecompressMembersRequestEvent();
    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_MEMBER);


    private MemberListPanelHandle memberListPanelHandle;
    private MemberListPanel memberListPanel;

    @Before
    public void setUp() {
        memberListPanel = new MemberListPanel(TYPICAL_MEMBERS);
        uiPartRule.setUiPart(memberListPanel);

        memberListPanelHandle = new MemberListPanelHandle(getChildNode(memberListPanel.getRoot(),
                MemberListPanelHandle.MEMBER_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_MEMBERS.size(); i++) {
            memberListPanelHandle.navigateToCard(TYPICAL_MEMBERS.get(i));
            Member expectedMember = TYPICAL_MEMBERS.get(i);
            MemberCardHandle actualCard = memberListPanelHandle.getMemberCardHandle(i);

            assertCardDisplaysMember(expectedMember, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        MemberCardHandle expectedCard = memberListPanelHandle.getMemberCardHandle(INDEX_SECOND_MEMBER.getZeroBased());
        MemberCardHandle selectedCard = memberListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }

    @Test
    public void handleCompressMembersRequestEvent() {
        postNow(COMPRESS_MEMBERS_REQUEST_EVENT);
        assertTrue(memberListPanel.isDisplayingCompressedMembers());
    }

    @Test
    public void handleDecompressMembersRequestEvent() {
        postNow(DECOMPRESS_MEMBERS_REQUEST_EVENT);
        assertFalse(memberListPanel.isDisplayingCompressedMembers());
    }
}
