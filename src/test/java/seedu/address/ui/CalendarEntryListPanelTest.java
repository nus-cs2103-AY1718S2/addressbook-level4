package seedu.address.ui;
//@@author SuxianAlicia
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarEntries;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarEntryCardHandle;
import guitests.guihandles.CalendarEntryListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.CalendarEntry;

public class CalendarEntryListPanelTest extends GuiUnitTest {
    private static final ObservableList<CalendarEntry> TYPICAL_CAL_ENTRIES =
            FXCollections.observableList(getTypicalCalendarEntries());

    private CalendarEntryListPanelHandle calendarEntryListPanelHandle;

    @Before
    public void setUp() {
        CalendarEntryListPanel calendarEntryListPanel = new CalendarEntryListPanel(TYPICAL_CAL_ENTRIES);
        uiPartRule.setUiPart(calendarEntryListPanel);

        calendarEntryListPanelHandle = new CalendarEntryListPanelHandle(getChildNode(calendarEntryListPanel.getRoot(),
                calendarEntryListPanelHandle.CALENDAR_ENTRY_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_CAL_ENTRIES.size(); i++) {
            calendarEntryListPanelHandle.navigateToCard(TYPICAL_CAL_ENTRIES.get(i));
            CalendarEntry expectedCalEntry = TYPICAL_CAL_ENTRIES.get(i);
            CalendarEntryCardHandle actualCard = calendarEntryListPanelHandle.getCalendarEntryCardHandle(i);

            assertCardDisplaysEntry(expectedCalEntry, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
