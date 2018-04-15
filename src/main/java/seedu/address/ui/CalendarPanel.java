package seedu.address.ui;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_CALENDAR_VIEW;
import static seedu.address.model.ModelManager.DAY_VIEW_PAGE;
import static seedu.address.model.ModelManager.MONTH_VIEW_PAGE;
import static seedu.address.model.ModelManager.WEEK_VIEW_PAGE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.page.DayPage;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeCalendarRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarViewPageRequestEvent;
import seedu.address.commons.events.ui.ShowCalendarBasedOnDateEvent;
import seedu.address.commons.events.ui.ShowCombinedCalendarViewRequestEvent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.CelebCalendar;

//@@author muruges95
/**
 * The panel for the Calendar. Constructs a calendar view and attaches to it a CalendarSource.
 * The view is then returned by calling getCalendarView in MainWindow to attach it to the
 * calendarPlaceholder.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private CalendarView celebCalendarView;

    private final CalendarSource celebCalendarSource;

    public CalendarPanel(CalendarSource celebCalendarSource) {
        super(FXML);
        this.celebCalendarView = new CalendarView();
        this.celebCalendarSource = celebCalendarSource;

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        registerAsAnEventHandler(this);

        // To set up the calendar view.
        setUpCelebCalendarView();
    }

    private void setUpCelebCalendarView() {
        celebCalendarView.getCalendarSources().clear(); // there is an existing default source when creating the view
        celebCalendarView.getCalendarSources().add(celebCalendarSource);
        celebCalendarView.setRequestedTime(LocalTime.now());
        celebCalendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
        celebCalendarView.showDayPage();

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        celebCalendarView.setToday(LocalDate.now());
                        celebCalendarView.setTime(LocalTime.now());
                    });
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
        };
        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        celebCalendarView.setLayout(DateControl.Layout.SWIMLANE);
        hideButtons();
    }

    //@@author WJY-norainu
    /** Hide all buttons in the calendar */
    private void hideButtons() {
        celebCalendarView.setShowSearchField(false);
        celebCalendarView.setShowSourceTrayButton(false);
        celebCalendarView.setShowAddCalendarButton(false);
        celebCalendarView.setShowPrintButton(false);
        celebCalendarView.setShowPageToolBarControls(false);
        celebCalendarView.setShowPageSwitcher(false);
        celebCalendarView.setShowToolBar(false);
        celebCalendarView.setLayout(DateControl.Layout.SWIMLANE);
    }

    public CalendarView getCalendarView() {
        return celebCalendarView;
    }

    /**
     * Method to handle the event for changing calendar view. Changes to either day,
     * week, month or year view.
     * @param event
     */
    @Subscribe
    private void handleChangeCalendarViewPageRequestEvent(ChangeCalendarViewPageRequestEvent event) {
        celebCalendarView.getCalendarSources().clear();
        celebCalendarView.getCalendarSources().add(celebCalendarSource);
        String calendarViewPage = event.calendarViewPage;

        Platform.runLater(() -> {
            switch (calendarViewPage) {

            case DAY_VIEW_PAGE:
                celebCalendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
                celebCalendarView.showDayPage();
                break;
            case WEEK_VIEW_PAGE:
                celebCalendarView.showWeekPage();
                break;
            case MONTH_VIEW_PAGE:
                celebCalendarView.showMonthPage();
                break;

            default:
                try {
                    throw new ParseException(MESSAGE_UNKNOWN_CALENDAR_VIEW);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /** Shows the calendar of the specified {@code celebrity} */
    private void showCalendarOf(CelebCalendar celebCalendarToShow) {
        Platform.runLater(() -> {
            for (Calendar calendar: celebCalendarSource.getCalendars()) {
                if (calendar != celebCalendarToShow) {
                    celebCalendarView.getSourceView().setCalendarVisibility(calendar, false);
                } else {
                    celebCalendarView.getSourceView().setCalendarVisibility(calendar, true);
                }
            }
        });
    }

    @Subscribe
    private void handleChangeCalendarRequestEvent(ChangeCalendarRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showCalendarOf(event.celebCalendar);
    }

    /** Shows a combined calendar that contains {@code appointment}s for all {@code celebrity}s */
    private void showAllCalendars() {
        Platform.runLater(() -> {
            for (Calendar calendar: celebCalendarSource.getCalendars()) {
                celebCalendarView.getSourceView().setCalendarVisibility(calendar, true);
            }
        });
    }

    @Subscribe
    private void handleShowCombinedCalendarViewRequestEvent(ShowCombinedCalendarViewRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showAllCalendars();
    }

    private void showDate(LocalDate date) {
        Platform.runLater(() -> {
            celebCalendarView.setDate(date);
        });
    }

    /** Shows calendar with the specified date as its base */
    @Subscribe
    private void handleShowCalendarBasedOnDateEvent(ShowCalendarBasedOnDateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showDate(event.getDate());
    }
}
