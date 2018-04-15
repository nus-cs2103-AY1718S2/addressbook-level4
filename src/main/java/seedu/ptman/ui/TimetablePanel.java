package seedu.ptman.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.DetailedWeekView;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import seedu.ptman.commons.core.LogsCenter;
import seedu.ptman.commons.events.model.PartTimeManagerChangedEvent;
import seedu.ptman.commons.events.ui.EmployeePanelSelectionChangedEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageAndEmailRequestEvent;
import seedu.ptman.commons.events.ui.ExportTimetableAsImageRequestEvent;
import seedu.ptman.commons.events.ui.OutletInformationChangedEvent;
import seedu.ptman.commons.events.ui.TimetableWeekChangeRequestEvent;
import seedu.ptman.commons.events.ui.TimetableWeekChangeRequestEvent.WeekChangeRequest;
import seedu.ptman.commons.services.EmailService;
import seedu.ptman.commons.util.DateUtil;
import seedu.ptman.logic.Logic;
import seedu.ptman.model.employee.Email;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.model.shift.Shift;

//@@author hzxcaryn
/**
 * Displays the GUI Timetable.
 */
public class TimetablePanel extends UiPart<Region> {

    public static final String TIMETABLE_IMAGE_FILE_NAME_DEFAULT = "MyTimetable";
    public static final String TIMETABLE_IMAGE_FILE_FORMAT = "png";

    private static final int TIMETABLE_IMAGE_PIXEL_SCALE = 2;
    private static final String FXML = "TimetablePanel.fxml";
    private static final int MAX_SLOTS_LEFT_RUNNING_OUT = 3;

    private static final Style ENTRY_GREEN_STYLE = Style.STYLE1;
    private static final Style ENTRY_BLUE_STYLE = Style.STYLE2;
    private static final Style ENTRY_YELLOW_STYLE = Style.STYLE3;
    private static final Style ENTRY_RED_STYLE = Style.STYLE5;
    private static final Style ENTRY_BROWN_STYLE = Style.STYLE7;

    private static Calendar timetableAvail;
    private static Calendar timetableEmployee;
    private static Calendar timetableFull;
    private static Calendar timetableOthers;
    private static Calendar timetableRunningOut;

    protected Logic logic;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private VBox timetableBox;

    @FXML
    private CalendarView timetableView;

    @FXML
    private BorderPane navBar;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label monthDisplay;

    private ObservableList<Shift> shiftObservableList;
    private OutletInformation outletInformation;

    private Employee currentEmployee = null;

    public TimetablePanel(Logic logic) {
        super(FXML);

        this.logic = logic;
        this.shiftObservableList = logic.getFilteredShiftList();
        this.outletInformation = logic.getOutletInformation();

        setUpNavBar();
        setTimetableViewStyle();
        showRelevantViewsOnly();

        // disable clicks on timetable view
        timetableView.getWeekPage().setMouseTransparent(true);

        updateTimetableView();

        registerAsAnEventHandler(this);
    }

    public static Calendar getTimetableAvail() {
        return timetableAvail;
    }

    public static Calendar getTimetableRunningOut() {
        return timetableRunningOut;
    }

    public static Calendar getTimetableFull() {
        return timetableFull;
    }

    public static Calendar getTimetableEmployee() {
        return timetableEmployee;
    }

    public static Calendar getTimetableOthers() {
        return timetableOthers;
    }

    /**
     * Sets up the navigation bar and its behavior
     */
    private void setUpNavBar() {
        setMonthDisplay(logic.getCurrentDisplayedDate());
        navBar.setLeft(prevButton);
        navBar.setRight(nextButton);
        navBar.setCenter(monthDisplay);

        prevButton.setOnAction(value -> Platform.runLater(() -> navigateToPreviousWeek()));
        nextButton.setOnAction(value -> Platform.runLater(() -> navigateToNextWeek()));
    }

    /**
     * Sets the displayed month on the {@code monthDisplay}.
     * Month is determined by the majority date displayed in the timetable view.
     * This means that if there are 4 dates that are of month April and 3 dates that are of month May,
     * April will be displayed on the {@code monthDisplay}.
     */
    private void setMonthDisplay(LocalDate date) {
        LocalDate thursdayDate = DateUtil.getThursdayOfDate(date);
        monthDisplay.setText(DateUtil.getMonthYearFromDate(thursdayDate));
    }

    /**
     * Sets the style of the timetable view
     */
    private void setTimetableViewStyle() {
        ObservableList<String> styleClass = timetableView.getStyleClass();
        styleClass.add("timetable-container");
    }

    /**
     * Only show the parts of CalendarFX that we need.
     */
    private void showRelevantViewsOnly() {
        timetableView.showWeekPage();

        timetableView.getWeekPage().setShowNavigation(false);
        timetableView.getWeekPage().setShowDate(false);
        timetableView.weekFieldsProperty().setValue(WeekFields.of(Locale.FRANCE)); // Start week from Monday
        timetableView.setShowToday(true);
        timetableView.setShowPrintButton(true);
        timetableView.setShowAddCalendarButton(false);
        timetableView.setShowSearchField(false);
        timetableView.setShowToolBar(false);
        timetableView.setShowPageSwitcher(false);
        timetableView.setShowPageToolBarControls(false);
        timetableView.setShowSearchResultsTray(false);
        timetableView.setShowSourceTray(false);
        timetableView.setShowSourceTrayButton(false);
        timetableView.getWeekPage().getDetailedWeekView().setShowAllDayView(false);
    }

    /**
     * This ensures that the range of the times shown by the timetable view is constrained to the
     * operating hours of the outlet.
     * Also ensures that no scrolling is required to view the entire timetable.
     */
    private void setTimetableRange() {
        LocalTime startTime = outletInformation.getOperatingHours().getStartTime();
        LocalTime endTime = outletInformation.getOperatingHours().getEndTime();
        timetableView.setStartTime(startTime);
        timetableView.setEndTime(endTime);

        DetailedWeekView detailedWeekView = timetableView.getWeekPage().getDetailedWeekView();
        detailedWeekView.setEarlyLateHoursStrategy(DayViewBase.EarlyLateHoursStrategy.HIDE);
        detailedWeekView.setHoursLayoutStrategy(DayViewBase.HoursLayoutStrategy.FIXED_HOUR_COUNT);
        detailedWeekView.setVisibleHours((int) ChronoUnit.HOURS.between(startTime, endTime));
        detailedWeekView.setShowScrollBar(false);
        detailedWeekView.setEnableCurrentTimeMarker(false);
    }

    private void setCurrentDisplayedDate() {
        timetableView.setDate(logic.getCurrentDisplayedDate());
    }

    /**
     * Takes default outlet shifts and set timetable entries based on these shifts.
     */
    private void setShifts() {
        int index = 1;
        for (Shift shift: shiftObservableList) {
            LocalDate date = shift.getDate().getLocalDate();
            Interval timeInterval = new Interval(date, shift.getStartTime().getLocalTime(),
                    date, shift.getEndTime().getLocalTime());
            Entry<String> shiftEntry = new Entry<>("SHIFT " + index + "\nSlots left: " + shift.getSlotsLeft()
                    + "/" + shift.getCapacity().getCapacity(), timeInterval);
            setEntryType(shift, shiftEntry);
            index++;
        }
    }

    /**
     * Sets the entry type (aka the color) of the shift in the timetable
     * @param shift
     * @param shiftEntry
     */
    private void setEntryType(Shift shift, Entry<String> shiftEntry) {
        Calendar entryType;
        if (currentEmployee != null) {
            entryType = getEntryTypeEmployee(shift);
        } else {
            entryType = getEntryTypeDefault(shift);
        }
        entryType.addEntry(shiftEntry);
    }

    /**
     * @return the entryType (a Calendar object) for the shift in the default timetable view, which reflects
     * the color of the shift in the timetableView.
     */
    private Calendar getEntryTypeDefault(Shift shift) {
        float ratio = (float) shift.getSlotsLeft() / (float) shift.getCapacity().getCapacity();
        if (ratio <= 0) {
            return timetableFull;
        } else if (ratio <= 0.5 || shift.getCapacity().getCapacity() < MAX_SLOTS_LEFT_RUNNING_OUT) {
            return timetableRunningOut;
        } else {
            return timetableAvail;
        }
    }

    /**
     * @return the entryType (a Calendar object) for the shift in the employee timetable view, which reflects
     * the color of the shift in the timetableView.
     */
    private Calendar getEntryTypeEmployee(Shift shift) {
        if (shift.containsEmployee(currentEmployee)) {
            return timetableEmployee;
        } else {
            return timetableOthers;
        }
    }

    /**
     * Navigates the timetable view to the following week.
     */
    private void navigateToNextWeek() {
        logic.setFilteredShiftListToNextWeek();
        shiftObservableList = logic.getFilteredShiftList();
        updateTimetableView();
    }

    /**
     * Navigates the timetable view to the previous week.
     */
    private void navigateToPreviousWeek() {
        logic.setFilteredShiftListToPrevWeek();
        shiftObservableList = logic.getFilteredShiftList();
        updateTimetableView();
    }

    /**
     * Navigates the timetable view to the current week.
     */
    private void navigateToCurrWeek() {
        logic.setFilteredShiftListToCurrentWeek();
        shiftObservableList = logic.getFilteredShiftList();
        updateTimetableView();
    }

    /**
     * Replaces the timetable view with a new timetable, with shifts applied by the employee being highlighted
     * @param employee
     */
    private void loadEmployeeTimetable(Employee employee) {
        currentEmployee = employee;
        updateTimetableView();
    }

    /**
     * Replaces the timetable view with a default timetable without any employee being selected.
     */
    private void loadDefaultTimetable() {
        currentEmployee = null;
        updateTimetableView();
    }

    /**
     * Replaces timetableView with a new timetable with updated shift and outlet information.
     */
    private void updateTimetableView() {
        setCurrentDisplayedDate();
        setMonthDisplay(logic.getCurrentDisplayedDate());
        resetTimetableView();
        setTimetableRange();
    }

    /**
     * Clear current timetable view and resets it to a new timetable view with updated shifts.
     */
    private void resetTimetableView() {
        timetableView.getCalendarSources().clear();
        CalendarSource calendarSource = new CalendarSource("Shifts");
        addCalendars(calendarSource);

        setShifts();
        timetableView.getCalendarSources().add(calendarSource);
    }

    /**
     * Initialises all the Calendar objects (These are our timetable entries)
     */
    private void initialiseEntries() {
        timetableAvail = new Calendar("Available");
        timetableRunningOut = new Calendar("Running Out");
        timetableFull = new Calendar("Full");
        timetableEmployee = new Calendar("Employee's shift");
        timetableOthers = new Calendar("Other shifts");
    }

    /**
     * Sets the color styles of the entries
     */
    private void setEntryStyles() {
        timetableAvail.setStyle(ENTRY_GREEN_STYLE);
        timetableRunningOut.setStyle(ENTRY_YELLOW_STYLE);
        timetableFull.setStyle(ENTRY_RED_STYLE);
        timetableEmployee.setStyle(ENTRY_BLUE_STYLE);
        timetableOthers.setStyle(ENTRY_BROWN_STYLE);
    }

    /**
     * Adds all relevant Calendars (entryTypes) to its source
     */
    private void addCalendars(CalendarSource calendarSource) {
        initialiseEntries();
        setEntryStyles();
        calendarSource.getCalendars().addAll(timetableAvail, timetableRunningOut, timetableFull,
                timetableEmployee, timetableOthers);
    }

    /**
     * Takes a snapshot of the timetable panel
     */
    private WritableImage takeSnapshot() {
        WritableImage timetableWritableImage = new WritableImage(
                (int) (TIMETABLE_IMAGE_PIXEL_SCALE * timetableBox.getWidth()),
                (int) (TIMETABLE_IMAGE_PIXEL_SCALE * timetableBox.getHeight()));
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(TIMETABLE_IMAGE_PIXEL_SCALE, TIMETABLE_IMAGE_PIXEL_SCALE));
        WritableImage snapshot = timetableBox.snapshot(spa, timetableWritableImage);
        return snapshot;
    }

    /**
     * Exports timetable as image and save it locally
     */
    private void exportTimetableAsImage(String filename) {
        File imageFile = new File("." + File.separator + filename + "." + TIMETABLE_IMAGE_FILE_FORMAT);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(takeSnapshot(), null), TIMETABLE_IMAGE_FILE_FORMAT, imageFile);
        } catch (IOException e) {
            logger.warning("Error taking snapshot of timetable.");
        }
    }

    /**
     * Exports timetable as image and email it
     * @param email
     */
    private void exportTimetableAsImageAndEmail(String filename, Email email) {
        String pathName = "." + File.separator + filename + "." + TIMETABLE_IMAGE_FILE_FORMAT;
        File imageFile = new File(pathName);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(takeSnapshot(), null), TIMETABLE_IMAGE_FILE_FORMAT, imageFile);
            EmailService emailService = EmailService.getInstance();
            emailService.sendTimetableAttachment(email.toString(), pathName);
        } catch (IOException e) {
            logger.warning("Error taking snapshot of timetable.");
        } catch (MessagingException e) {
            logger.warning("Error sending timetable as email.");
        }

        try {
            Files.deleteIfExists(Paths.get(pathName));
        } catch (IOException e) {
            logger.warning("Error deleting exported and emailed timetable image.");
        }
    }

    @Subscribe
    private void handlePartTimeManagerChangedEvent(PartTimeManagerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + ": Updating timetable view....");
        Platform.runLater(() -> updateTimetableView());
    }

    @Subscribe
    private void handleOutletInformationChangedEvent(OutletInformationChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + ": Rescaling timetable view....");
        Platform.runLater(() -> updateTimetableView());
    }

    @Subscribe
    private void handleEmployeePanelSelectionChangedEvent(EmployeePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            if (event.hasNewSelection()) {
                loadEmployeeTimetable(event.getNewSelection().employee);
            } else {
                loadDefaultTimetable();
            }
        });
    }

    @Subscribe
    private void handleTimetableWeekChangeRequestEvent(TimetableWeekChangeRequestEvent event) {
        WeekChangeRequest request = event.getRequest();
        Platform.runLater(() -> {
            if (request == WeekChangeRequest.NEXT) {
                logger.info(LogsCenter.getEventHandlingLogMessage(event)
                        + ": Navigating timetable to the next week....");
                navigateToNextWeek();
            } else if (request == WeekChangeRequest.PREVIOUS) {
                logger.info(LogsCenter.getEventHandlingLogMessage(event)
                        + ": Navigating timetable to the previous week....");
                navigateToPreviousWeek();
            } else if (request == WeekChangeRequest.CURRENT) {
                logger.info(LogsCenter.getEventHandlingLogMessage(event)
                        + ": Navigating timetable to the current week....");
                navigateToCurrWeek();
            }
        });
    }

    @Subscribe
    private void handleExportTimetableAsImageRequestEvent(ExportTimetableAsImageRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + ": Exporting timetable as image....");
        Platform.runLater(() -> exportTimetableAsImage(event.filename));
    }

    @Subscribe
    private void handleExportTimetableAsImageAndEmailRequestEvent(ExportTimetableAsImageAndEmailRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event)
                + ": Exporting timetable as image to send email....");
        Platform.runLater(() -> exportTimetableAsImageAndEmail(event.filename, event.email));
    }

}
