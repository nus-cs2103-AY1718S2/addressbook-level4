package seedu.address.ui;

import guitests.guihandles.BirthdaysListHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.EventsUtil.postNow;

public class BirthdaysListTest extends GuiUnitTest {

    private List<Person> personListStub;
    private ObservableList<Person> personObservableListStub;

    private BirthdayListEvent birthdayListEventStub;

    private BirthdaysListHandle birthdaysListHandle;

    @Before
    public void setUp() {
        BirthdayList birthdayList = new BirthdayList(null);

        uiPartRule.setUiPart(birthdayList);

        birthdaysListHandle = new BirthdaysListHandle(getChildNode(birthdayList.getRoot(),
                BirthdaysListHandle.BIRTHDAYS_LIST_ID));
    }

    @Test
    public void display() {
        String expectedResult = "1/1/1995 Alice Pauline\n2/2/1993 Alice Pauline\n";
        birthdayListEventStub = new BirthdayListEvent("1/1/1995 Alice Pauline\n2/2/1993 Alice Pauline\n");

        // default birthday list text
        guiRobot.pauseForHuman();
        assertEquals(null, birthdaysListHandle.getText());

        // new result received
        postNow(birthdayListEventStub);
        guiRobot.pauseForHuman();
        assertEquals(expectedResult, birthdaysListHandle.getText());
    }

}
