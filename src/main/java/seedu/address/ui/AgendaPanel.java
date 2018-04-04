package seedu.address.ui;

import java.util.logging.Logger;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.AgendaView;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;

//@@author yuxiangSg
/**
 * The Agenda Panel of the App.
 */
public class AgendaPanel {
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private AgendaView agendaView;

    public AgendaPanel(CalendarSource calendar) {
        agendaView = new AgendaView();
        assignCalendar(calendar);
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Assign ca to Agenda panel GUI
     */
    void assignCalendar(CalendarSource calendar) {
        agendaView.getCalendarSources().setAll(calendar);
    }

    public AgendaView getAgendaView() {
        return agendaView;
    }
}
