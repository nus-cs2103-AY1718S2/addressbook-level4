package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT1;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT2;
import static seedu.address.testutil.TypicalActivities.CCA;
import static seedu.address.testutil.TypicalActivities.CIP;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Task;
import seedu.address.model.activity.UniqueActivityList;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

public class UniqueActivityListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void internalListAsObservable_modifyList_throwsUnsupportedOperationException() {
        UniqueActivityList uniqueActivityList = new UniqueActivityList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueActivityList.internalListAsObservable().remove(0);
    }

    //@@author karenfrilya97
    @Test
    public void add_taskWithEarlierDateTimeThanExisting_sortsListAutomatically() throws DuplicateActivityException {
        UniqueActivityList uniqueActivityList = new UniqueActivityList();
        Task earlierTask = ASSIGNMENT1;
        Task laterTask = ASSIGNMENT2;
        uniqueActivityList.add(laterTask);
        uniqueActivityList.add(earlierTask);
        Activity firstActivityOnTheList = uniqueActivityList.internalListAsObservable().get(0);
        assertEquals(firstActivityOnTheList, earlierTask);
    }

    @Test
    public void add_eventWithEarlierStartDateTimeThanExisting_sortsListAutomatically()
            throws DuplicateActivityException {
        UniqueActivityList uniqueActivityList = new UniqueActivityList();
        Event earlierEvent = CCA;
        Event laterEvent = CIP;
        uniqueActivityList.add(laterEvent);
        uniqueActivityList.add(earlierEvent);
        Activity firstActivityOnTheList = uniqueActivityList.internalListAsObservable().get(0);
        assertEquals(firstActivityOnTheList, earlierEvent);
    }
}
