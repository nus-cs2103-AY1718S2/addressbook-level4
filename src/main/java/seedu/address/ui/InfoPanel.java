package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InfoPanelChangedEvent;
import seedu.address.commons.events.ui.PersonChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowPanelRequestEvent;
import seedu.address.commons.util.UiUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;

//@@author Ang-YC
/**
 * The Info Panel of the App.
 */
public class InfoPanel extends UiPart<Region> {

    public static final Person DEFAULT_PERSON = null;
    public static final int SPLIT_MIN_WIDTH = 550;

    public static final String PANEL_NAME = "InfoPanel";
    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private int currentSelectedIndex = -1;
    private Person currentSelectedPerson;

    @FXML
    private AnchorPane infoPaneWrapper;
    @FXML
    private SplitPane infoSplitPane;

    // Responsive
    @FXML
    private ScrollPane infoMainPane;
    @FXML
    private ScrollPane infoSplitMainPane;
    @FXML
    private VBox infoMain;
    @FXML
    private AnchorPane infoMainRatings;
    @FXML
    private AnchorPane infoSplitSidePane;
    @FXML
    private VBox infoSplitRatings;

    @FXML
    private Label infoMainName;
    @FXML
    private Label infoMainUniversity;
    @FXML
    private Label infoMainMajorYear;
    @FXML
    private Label infoMainCgpa;
    @FXML
    private Label infoMainEmail;
    @FXML
    private Label infoMainAddress;
    @FXML
    private Label infoMainPhone;
    @FXML
    private Label infoMainPosition;
    @FXML
    private Label infoMainStatus;
    @FXML
    private Label infoMainComments;

    // Interview
    @FXML
    private VBox infoMainInterviewDatePane;
    @FXML
    private Label infoMainInterviewMonth;
    @FXML
    private Label infoMainInterviewDate;
    @FXML
    private Label infoMainInterviewDay;
    @FXML
    private Label infoMainInterviewTime;

    // Rating
    @FXML
    private ProgressBar infoRatingTechnical;
    @FXML
    private ProgressBar infoRatingCommunication;
    @FXML
    private ProgressBar infoRatingProblemSolving;
    @FXML
    private ProgressBar infoRatingExperience;

    // Resume
    @FXML
    private VBox infoSideButtons;
    @FXML
    private Button infoSideButtonResume;

    public InfoPanel() {
        super(FXML);
        registerAsAnEventHandler(this);

        infoPaneWrapper.widthProperty().addListener((observable, oldValue, newValue) -> {
            handleResize(oldValue.intValue(), newValue.intValue());
        });
        handleResponsive((int) infoPaneWrapper.getWidth());
    }

    @FXML
    private void showResume() {
        raise(new ShowPanelRequestEvent(PdfPanel.PANEL_NAME));
    }

    /**
     * Handle resize when width changed event occurred, then decide whether should trigger responsive handler or not
     * @param oldValue of the width property
     * @param newValue of the width property
     */
    private void handleResize(int oldValue, int newValue) {
        // Process only if there are differences
        int smaller = Math.min(oldValue, newValue);
        int larger  = Math.max(oldValue, newValue);

        if (smaller <= SPLIT_MIN_WIDTH && larger >= SPLIT_MIN_WIDTH) {
            handleResponsive(newValue);
        }
    }

    /**
     * Handle responsiveness by checking if window should split into two based on {@code SPLIT_MIN_WIDTH}
     * @param width of {@code InfoPanel}
     */
    private void handleResponsive(int width) {
        if (width >= SPLIT_MIN_WIDTH) {
            infoSplitPane.setVisible(true);
            infoMainPane.setVisible(false);

            infoMainRatings.getChildren().remove(infoSplitRatings);
            infoSplitSidePane.getChildren().remove(infoSplitRatings);
            infoMainPane.setContent(null);

            infoSplitMainPane.setContent(infoMain);
            infoSplitSidePane.getChildren().add(infoSplitRatings);

        } else {
            infoMainPane.setVisible(true);
            infoSplitPane.setVisible(false);

            infoMainRatings.getChildren().remove(infoSplitRatings);
            infoSplitSidePane.getChildren().remove(infoSplitRatings);
            infoSplitMainPane.setContent(null);

            infoMainPane.setContent(infoMain);
            infoMainRatings.getChildren().add(infoSplitRatings);
        }
    }

    /**
     * Update the info panel with the latest information
     * It can be updated from address book change or selection change
     */
    private void updateInfoPanel() {
        if (currentSelectedPerson == null) {
            return;
        }
        Person person = currentSelectedPerson;

        infoMainName.setText(person.getName().fullName);
        infoMainUniversity.setText(person.getUniversity().value);
        infoMainMajorYear.setText(person.getMajor() + " (Expected " + person.getExpectedGraduationYear().value + ")");
        infoMainCgpa.setText(UiUtil.toFixed(person.getGradePointAverage().value, 2));
        infoMainEmail.setText(person.getEmail().value);
        infoMainAddress.setText(person.getAddress().value);
        infoMainPhone.setText(person.getPhone().value);
        infoMainPosition.setText(person.getJobApplied().value);
        infoMainStatus.setText(person.getStatus().value);
        infoMainStatus.setStyle("-fx-text-fill: " + UiUtil.colorToHex(person.getStatus().color));

        // Update comment
        String comment = person.getComment().value;
        infoMainComments.setText(comment == null ? "" : comment);

        // Disable resume if it is null
        boolean resumeVisible = (person.getResume().value != null);
        infoSideButtons.setManaged(resumeVisible);
        infoSideButtonResume.setManaged(resumeVisible);
        infoSideButtons.setVisible(resumeVisible);
        infoSideButtonResume.setVisible(resumeVisible);

        // Process Interview info
        LocalDateTime interviewDate = person.getInterviewDate().getDateTime();
        if (interviewDate != null) {
            infoMainInterviewMonth.setText(interviewDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            infoMainInterviewDate.setText(String.valueOf(interviewDate.getDayOfMonth()));
            infoMainInterviewDay.setText(interviewDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            infoMainInterviewTime.setText(DateTimeFormatter.ofPattern("hh:mma", Locale.ENGLISH)
                    .format(interviewDate).toLowerCase());
            infoMainInterviewDatePane.setVisible(true);
        } else {
            infoMainInterviewDatePane.setVisible(false);
        }

        // Process Rating info
        Rating rating = person.getRating();
        if (Rating.isValidScore(rating.technicalSkillsScore)
                && Rating.isValidScore(rating.communicationSkillsScore)
                && Rating.isValidScore(rating.problemSolvingSkillsScore)
                && Rating.isValidScore(rating.experienceScore)) {
            infoRatingTechnical.setProgress(rating.technicalSkillsScore / 5);
            infoRatingCommunication.setProgress(rating.communicationSkillsScore / 5);
            infoRatingProblemSolving.setProgress(rating.problemSolvingSkillsScore / 5);
            infoRatingExperience.setProgress(rating.experienceScore / 5);
        } else {
            infoRatingTechnical.setProgress(0);
            infoRatingCommunication.setProgress(0);
            infoRatingProblemSolving.setProgress(0);
            infoRatingExperience.setProgress(0);
        }

        // Scroll to top
        infoMainPane.setVvalue(0);
        infoSplitMainPane.setVvalue(0);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        PersonCard currentSelected = event.getNewSelection();
        currentSelectedIndex = currentSelected.index;
        currentSelectedPerson = currentSelected.getPerson();

        updateInfoPanel();

        // Set user data for test
        infoPaneWrapper.setUserData(currentSelectedPerson);
        infoPaneWrapper.fireEvent(new InfoPanelChangedEvent());
    }

    @Subscribe
    private void handlePersonChangedEvent(PersonChangedEvent event) {
        ListChangeListener.Change<? extends Person> changes = event.getPersonChanged();
        if (currentSelectedPerson != null) {
            while (changes.next()) {
                for (int i = changes.getFrom(); i < changes.getTo(); i++) {
                    if (i == currentSelectedIndex) {
                        currentSelectedPerson = changes.getList().get(i);
                        updateInfoPanel();
                    }
                }
            }
        }
    }
}
