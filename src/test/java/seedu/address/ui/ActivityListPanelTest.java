//package seedu.address.ui;
//
//import static org.junit.Assert.assertEquals;
//import static seedu.address.testutil.EventsUtil.postNow;
//import static seedu.address.testutil.TypicalActivities.getTypicalActivitiess;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;
//import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;
//import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import guitests.guihandles.PersonCardHandle;
//import guitests.guihandles.PersonListPanelHandle;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import seedu.address.commons.events.ui.JumpToListRequestEvent;
//import seedu.address.model.activity.Activity;
//
//public class ActivityListPanelTest extends GuiUnitTest {
//    private static final ObservableList<Activity> TYPICAL_ACTIVITIES =
//            FXCollections.observableList(getTypicalActivitiess());
//
//    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT =
//            new JumpToListRequestEvent(INDEX_SECOND_ACTIVITY);
//
//    private PersonListPanelHandle personListPanelHandle;
//
//    @Before
//    public void setUp() {
//        ActivityListPanel activityListPanel = new ActivityListPanel(TYPICAL_ACTIVITIES);
//        uiPartRule.setUiPart(activityListPanel);
//
//        personListPanelHandle = new PersonListPanelHandle(getChildNode(activityListPanel.getRoot(),
//                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
//    }
//
//    @Test
//    public void display() {
//        for (int i = 0; i < TYPICAL_ACTIVITIES.size(); i++) {
//            personListPanelHandle.navigateToCard(TYPICAL_ACTIVITIES.get(i));
//            Activity expectedActivity = TYPICAL_ACTIVITIES.get(i);
//            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);
//
//            assertCardDisplaysTask(expectedActivity, actualCard);
//            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
//        }
//    }
//
//    @Test
//    public void handleJumpToListRequestEvent() {
//        postNow(JUMP_TO_SECOND_EVENT);
//        guiRobot.pauseForHuman();
//
//        PersonCardHandle expectedCard = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_ACTIVITY
//                .getZeroBased());
//        PersonCardHandle selectedCard = personListPanelHandle.getHandleToSelectedCard();
//        assertCardEquals(expectedCard, selectedCard);
//    }
//}
