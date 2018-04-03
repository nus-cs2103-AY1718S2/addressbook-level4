package seedu.address.ui;

import static org.junit.Assert.assertEquals;
//import static seedu.address.testutil.EventsUtil.postNow;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BirthdayListHandle;

import javafx.collections.ObservableList;

import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.model.person.Person;

//@@author AzuraAiR
public class BirthdayListTest extends GuiUnitTest {

    private List<Person> personListStub;
    private ObservableList<Person> personObservableListStub;

    private BirthdayList birthdayList;

    private BirthdayListEvent birthdayListEventStub;

    private BirthdayListHandle birthdaysListHandle;

    @Before
    public void setUp() {
        birthdayList = new BirthdayList();
        uiPartRule.setUiPart(birthdayList);

        birthdaysListHandle = new BirthdayListHandle(getChildNode(birthdayList.getRoot(),
                BirthdayListHandle.BIRTHDAYS_LIST_ID));
    }

    @Test
    public void display() {
        String expectedResult = "1/1/1995 Alice Pauline\n2/2/1993 Alice Pauline\n";
        birthdayListEventStub = new BirthdayListEvent("1/1/1995 Alice Pauline\n2/2/1993 Alice Pauline\n");

        // default birthday list text
        guiRobot.pauseForHuman();
        assertEquals("", birthdaysListHandle.getText());

        // new event received
        //postNow(birthdayListEventStub);
        //birthdayList.loadList(birthdayListEventStub.getBirthdayList()); // Manual loading
        //guiRobot.pauseForHuman();
        //assertEquals(expectedResult, birthdaysListHandle.getText());
    }

}
