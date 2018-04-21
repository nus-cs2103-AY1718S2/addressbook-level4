package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;
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
    private static final double MAX_ANIMATION_TIME_MS = 150;
    private static final double ANIMATION_DELAY_MS = 15;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    // For animation
    private Person currentSelectedPerson;
    private RadarChart radarChart;
    private ArrayList<Animation> allAnimation = new ArrayList<>();
    private LinkedList<Node> nodes = new LinkedList<>();
    private boolean animated;

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

    // Animation
    @FXML
    private VBox infoMainPart;
    @FXML
    private HBox infoMainTopRight;
    @FXML
    private HBox infoMainContactEmailPane;
    @FXML
    private HBox infoMainContactAddressPane;
    @FXML
    private HBox infoMainContactPhonePane;
    @FXML
    private Label infoMainPositionLabel;
    @FXML
    private Label infoMainStatusLabel;
    @FXML
    private VBox infoMainCommentsPane;

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
    private AnchorPane infoSideGraph;
    @FXML
    private ProgressBar infoRatingTechnical;
    @FXML
    private ProgressBar infoRatingCommunication;
    @FXML
    private ProgressBar infoRatingProblemSolving;
    @FXML
    private ProgressBar infoRatingExperience;
    @FXML
    private ProgressBar infoRatingOverall;
    @FXML
    private Label infoRatingTechnicalValue;
    @FXML
    private Label infoRatingCommunicationValue;
    @FXML
    private Label infoRatingProblemSolvingValue;
    @FXML
    private Label infoRatingExperienceValue;
    @FXML
    private Label infoRatingOverallValue;

    // Resume
    @FXML
    private Button infoSideButtonResume;

    public InfoPanel(boolean animated) {
        super(FXML);
        this.animated = animated;
        setupNodes();

        radarChart = new RadarChart(Rating.MAXIMUM_SCORE, animated);
        infoSideGraph.getChildren().add(radarChart.getRoot());

        infoPaneWrapper.widthProperty().addListener((observable, oldValue, newValue) -> {
            handleResize(oldValue.intValue(), newValue.intValue());
        });
        handleResponsive((int) infoPaneWrapper.getWidth());
        registerAsAnEventHandler(this);
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
     * Update the info panel with animation
     * @param fadeOutOnly If set, data will not be loaded and info panel will be faded out
     */
    private void animateUpdateInfoPanel(boolean fadeOutOnly) {
        // Stop previously started animation
        allAnimation.forEach(Animation::pause);
        allAnimation.clear();

        if (animated) {
            ArrayList<Animation> allFadeIn = new ArrayList<>();
            double delay = 0;

            for (Node node : nodes) {
                delay += ANIMATION_DELAY_MS;

                Animation fadeIn = UiUtil.fadeNode(node, true, 0, MAX_ANIMATION_TIME_MS, e -> { });
                fadeIn.setDelay(Duration.millis(delay));

                allAnimation.add(fadeIn);
                allFadeIn.add(fadeIn);
            }

            Animation fadeOut = UiUtil.fadeNode(infoMainPart, false, MAX_ANIMATION_TIME_MS, e -> {
                if (!fadeOutOnly) {
                    nodes.forEach(node -> node.setOpacity(0));
                    infoMainPart.setOpacity(1);
                    updateInfoPanel();
                    allFadeIn.forEach(Animation::play);
                }
            });

            allAnimation.add(fadeOut);
            fadeOut.play();

        } else {
            infoMainPart.setOpacity(fadeOutOnly ? 0 : 1);
            nodes.forEach(node -> node.setOpacity(1));
            updateInfoPanel();
        }
    }

    /**
     * Animate the display of rating (Progress bar and label)
     * @param rating data to be animated
     */
    private void animateRating(Rating rating) {
        // Process Rating info
        LinkedHashMap<String, Double> ratingData = new LinkedHashMap<>();
        ArrayList<Pair<Double, Pair<ProgressBar, Label>>> ratingHelper = new ArrayList<>();

        if (Rating.isValidScore(rating.technicalSkillsScore)
                && Rating.isValidScore(rating.communicationSkillsScore)
                && Rating.isValidScore(rating.problemSolvingSkillsScore)
                && Rating.isValidScore(rating.experienceScore)) {

            ratingHelper.add(new Pair<>(rating.technicalSkillsScore,
                    new Pair<>(infoRatingTechnical, infoRatingTechnicalValue)));
            ratingHelper.add(new Pair<>(rating.communicationSkillsScore,
                    new Pair<>(infoRatingCommunication, infoRatingCommunicationValue)));
            ratingHelper.add(new Pair<>(rating.problemSolvingSkillsScore,
                    new Pair<>(infoRatingProblemSolving, infoRatingProblemSolvingValue)));
            ratingHelper.add(new Pair<>(rating.experienceScore,
                    new Pair<>(infoRatingExperience, infoRatingExperienceValue)));
            ratingHelper.add(new Pair<>(rating.overallScore,
                    new Pair<>(infoRatingOverall, infoRatingOverallValue)));

            ratingData.put("Technical", rating.technicalSkillsScore);
            ratingData.put("Communication", rating.communicationSkillsScore);
            ratingData.put("Problem\nSolving", rating.problemSolvingSkillsScore);
            ratingData.put("Experience", rating.experienceScore);

            for (Pair<Double, Pair<ProgressBar, Label>> entry : ratingHelper) {
                double rateValue = entry.getKey() / Rating.MAXIMUM_SCORE;
                ProgressBar progressBar = entry.getValue().getKey();
                Label label = entry.getValue().getValue();

                if (animated) {
                    DoubleProperty value = new SimpleDoubleProperty(0);

                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.millis(rateValue * RadarChart.MAX_ANIMATION_TIME_MS),
                                    new KeyValue(value, rateValue, UiUtil.EASE_OUT_CUBIC))
                    );
                    timeline.setAutoReverse(false);
                    timeline.setCycleCount(1);
                    timeline.play();

                    value.addListener((observable, oldValue, newValue) -> {
                        progressBar.setProgress(newValue.doubleValue());
                        label.setText(UiUtil.toFixed(newValue.doubleValue() * Rating.MAXIMUM_SCORE, 2));
                    });

                    allAnimation.add(timeline);
                } else {
                    progressBar.setProgress(rateValue);
                    label.setText(UiUtil.toFixed(rateValue * Rating.MAXIMUM_SCORE, 2));
                }
            }

        } else {
            infoRatingTechnical.setProgress(0);
            infoRatingCommunication.setProgress(0);
            infoRatingProblemSolving.setProgress(0);
            infoRatingExperience.setProgress(0);
            infoRatingOverall.setProgress(0);

            infoRatingTechnicalValue.setText("0.00");
            infoRatingCommunicationValue.setText("0.00");
            infoRatingProblemSolvingValue.setText("0.00");
            infoRatingExperienceValue.setText("0.00");
            infoRatingOverallValue.setText("0.00");

            ratingData.put("Technical", 0.0);
            ratingData.put("Communication", 0.0);
            ratingData.put("Problem\nSolving", 0.0);
            ratingData.put("Experience", 0.0);
        }

        radarChart.setData(ratingData);
    }

    /**
     * Setup nodes to be animated, order matters
     */
    private void setupNodes() {
        nodes.add(infoMainName);
        nodes.add(infoMainTopRight);
        nodes.add(infoMainUniversity);
        nodes.add(infoMainMajorYear);
        nodes.add(infoMainContactEmailPane);
        nodes.add(infoMainContactAddressPane);
        nodes.add(infoMainContactPhonePane);
        nodes.add(infoMainInterviewDatePane);
        nodes.add(infoMainPositionLabel);
        nodes.add(infoMainPosition);
        nodes.add(infoMainStatusLabel);
        nodes.add(infoMainStatus);
        nodes.add(infoMainCommentsPane);
    }

    /**
     * Hide the info panel (When fade is done on MainWindow)
     */
    public void hide() {
        infoMainPart.setOpacity(0);
    }

    /**
     * Show the info panel (When fade is done on MainWindow)
     */
    public void show() {
        animateUpdateInfoPanel(false);
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
        boolean resumeAvailable = (person.getResume().value != null);
        infoSideButtonResume.setDisable(!resumeAvailable);
        infoSideButtonResume.setText(resumeAvailable ? "View resume" : "Resume not available");

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

        animateRating(person.getRating());

        // Scroll to top
        infoMainPane.setVvalue(0);
        infoSplitMainPane.setVvalue(0);

        // Set user data for test
        infoPaneWrapper.setUserData(currentSelectedPerson);
        infoPaneWrapper.fireEvent(new InfoPanelChangedEvent());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        PersonCard newSelected = event.getNewSelection();
        Person newSelectedPerson = (newSelected == null) ? null : newSelected.getPerson();

        if (newSelectedPerson == null) {
            raise(new ShowPanelRequestEvent("WelcomePane"));
        } else {
            raise(new ShowPanelRequestEvent(InfoPanel.PANEL_NAME));
        }

        // Update if only the person selected is not what is currently shown
        if (!Objects.equals(newSelectedPerson, currentSelectedPerson)) {
            currentSelectedPerson = newSelectedPerson;
            animateUpdateInfoPanel(currentSelectedPerson == null);
        }
    }

    @Subscribe
    private void handlePersonChangedEvent(PersonChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person source = event.getSource();
        Person target = event.getTarget();

        // Update if the person changed is what is currently shown
        // Don't update if the person is the same (+ quickfix for Rating and Resume)
        if (currentSelectedPerson != null && currentSelectedPerson.equals(source) && target != null
                && !currentSelectedPerson.infoEquals(target)) {
            currentSelectedPerson = target;
            animateUpdateInfoPanel(false);
        }
    }
}
