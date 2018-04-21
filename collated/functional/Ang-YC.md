# Ang-YC
###### /java/seedu/address/ui/UiResizer.java
``` java
/**
 * Ui Resizer, a utility to manage resize event of Stage such as resizable window
 */
public class UiResizer {

    private Stage stage;

    private double lastX;
    private double lastY;
    private double lastWidth;
    private double lastHeight;

    public UiResizer(Stage stage, GuiSettings guiSettings, double minWidth, double minHeight, int cornerSize) {
        this.stage = stage;

        // Set listeners
        ResizeListener resizeListener = new ResizeListener(stage, minWidth, minHeight, cornerSize);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_RELEASED, resizeListener);

        // Set last value
        lastX = guiSettings.getWindowCoordinates().x;
        lastY = guiSettings.getWindowCoordinates().y;
        lastWidth = guiSettings.getWindowWidth();
        lastHeight = guiSettings.getWindowHeight();
    }

    private Rectangle2D getScreenBound() {
        return Screen.getPrimary().getVisualBounds();
    }

    /**
     * Maximize / Un-maximize the stage, polyfill for native {@link Stage#setMaximized} feature
     */
    public void toggleMaximize() {
        Rectangle2D screenBound = getScreenBound();
        double stageX = stage.getX();
        double stageY = stage.getY();
        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        if (stageWidth == screenBound.getWidth() && stageHeight == screenBound.getHeight()) {
            stage.setX(lastX);
            stage.setY(lastY);
            stage.setWidth(lastWidth);
            stage.setHeight(lastHeight);
        } else {
            lastX = stageX;
            lastY = stageY;
            lastWidth = stageWidth;
            lastHeight = stageHeight;
            stage.setX(screenBound.getMinX());
            stage.setY(screenBound.getMinY());
            stage.setWidth(screenBound.getWidth());
            stage.setHeight(screenBound.getHeight());
        }
    }

    /**
     * Manage the resize event during mouse move and drag
     */
    static class ResizeListener implements EventHandler<MouseEvent> {
        private Stage stage;

        private boolean holding = false;
        private int cornerSize;

        // Starting position of resizing
        private double startX = 0;
        private double startY = 0;

        // Min sizes for stage
        private double minWidth;
        private double minHeight;

        public ResizeListener(Stage stage, double minWidth, double minHeight, int borderSize) {
            this.stage = stage;
            this.minWidth = minWidth;
            this.minHeight = minHeight;
            this.cornerSize = borderSize;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            String eventType = mouseEvent.getEventType().getName();
            Scene scene = stage.getScene();

            double mouseX = mouseEvent.getSceneX();
            double mouseY = mouseEvent.getSceneY();


            switch (eventType) {

            case "MOUSE_MOVED":
                scene.setCursor((isResizePosition(mouseX, mouseY) || holding) ? Cursor.SE_RESIZE : Cursor.DEFAULT);
                break;

            case "MOUSE_RELEASED":
                holding = false;
                scene.setCursor(Cursor.DEFAULT);
                break;

            case "MOUSE_PRESSED":
                // Left click only
                if (MouseButton.PRIMARY.equals(mouseEvent.getButton())) {
                    holding = isResizePosition(mouseX, mouseY);
                    startX = stage.getWidth() - mouseX;
                    startY = stage.getHeight() - mouseY;
                }
                break;

            case "MOUSE_DRAGGED":
                if (holding) {
                    setStageWidth(mouseX + startX);
                    setStageHeight(mouseY + startY);
                }
                break;

            default:

            }
        }

        /**
         * Check if the X and Y coordinate of the mouse are in the range of draggable position
         *
         * @param x coordinate of the {@code MouseEvent}
         * @param y coordinate of the {@code MouseEvent}
         * @return {@code true} if the coordinate is in the range of draggable position, {@code false} otherwise
         */
        private boolean isResizePosition(double x, double y) {
            Scene scene = stage.getScene();
            return (x > scene.getWidth() - cornerSize && y > scene.getHeight() - cornerSize);
        }

        /**
         * Set the width of the stage, with validation to be larger than {@code minWidth}
         *
         * @param width of the stage
         */
        private void setStageWidth(double width) {
            stage.setWidth(Math.max(width, minWidth));
        }

        /**
         * Set the height of the stage, with validation to be larger than {@code minHeight}
         *
         * @param height of the stage
         */
        private void setStageHeight(double height) {
            stage.setHeight(Math.max(height, minHeight));
        }

    }
}
```
###### /java/seedu/address/ui/TitleBar.java
``` java
    /**
     * Opens the help window.
     */
    @FXML
    public void handleHelp(MouseEvent event) {
        if (MouseButton.PRIMARY.equals(event.getButton())) {
            HelpWindow helpWindow = new HelpWindow();
            helpWindow.show();
        }
    }

    /**
     * Minimizes the application.
     */
    @FXML
    private void handleMinimize(MouseEvent event) {
        if (MouseButton.PRIMARY.equals(event.getButton())) {
            raise(new MinimizeAppRequestEvent());
        }
    }

    /**
     * Maximizes the application.
     */
    @FXML
    private void handleMaximize(MouseEvent event) {
        if (MouseButton.PRIMARY.equals(event.getButton())) {
            raise(new MaximizeAppRequestEvent());
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit(MouseEvent event) {
        if (MouseButton.PRIMARY.equals(event.getButton())) {
            raise(new ExitAppRequestEvent());
        }
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        HelpWindow helpWindow = new HelpWindow();
        helpWindow.show();
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }
}
```
###### /java/seedu/address/ui/InfoPanel.java
``` java
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
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);

        this.person = person;
        this.index = displayedIndex - 1;

        cardPersonPane.setOpacity(0);
        updatePersonCard();

        registerAsAnEventHandler(this);
    }

    /**
     * Update the person with the latest information
     * It can be updated from address book change or selection change
     */
    private void updatePersonCard() {
        if (person == null) {
            return;
        }

        cardPhoto.fitWidthProperty().bind(cardPhotoMask.widthProperty());
        cardPhoto.fitHeightProperty().bind(cardPhotoMask.heightProperty());

        Image profileImage = person.getProfileImage().getImage();
        if (profileImage == null) {
            cardPhoto.setImage(null);
        } else {
            try {
                cardPhoto.setImage(profileImage);
            } catch (Exception e) {
                logger.info("Failed to load image file");
            }
        }

        cardPersonName.setText(person.getName().fullName);
        cardPersonUniversity.setText(person.getUniversity().value);
        cardPersonEmail.setText(person.getEmail().value);
        cardPersonContact.setText(person.getPhone().value);
        cardPersonStatus.setText(person.getStatus().value);
        cardPersonStatus.setStyle("-fx-background-color: " + UiUtil.colorToHex(person.getStatus().color));
        cardPersonNumber.setText(String.valueOf(index + 1));

        double rating = person.getRating().overallScore;
        if (rating < 1e-3) {
            cardPersonRating.setText("");
            iconRating.setVisible(false);
        } else {
            cardPersonRating.setText(UiUtil.toFixed(rating, 2));
            iconRating.setVisible(true);
        }
    }

    /**
     * Play animation (Fade is done on MainWindow)
     */
    public void play() {
        Animation fadeIn = UiUtil.fadeNode(cardPersonPane, true, MAX_ANIMATION_TIME_MS, 0, e -> {});
        fadeIn.setDelay(Duration.millis(index * 50));
        fadeIn.play();
    }

    /**
     * Show without animation (Fade is done on MainWindow)
     */
    public void show() {
        cardPersonPane.setOpacity(1);
    }

    @Subscribe
    private void handlePersonChangedEvent(PersonChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        Person source = event.getSource();
        Person target = event.getTarget();

        if (person != null && person.equals(source)) {
            person = target;
            updatePersonCard();
        }
    }
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    public void requestFocus() {
        primaryStage.requestFocus();
    }

    /**
     * Handle responsiveness by fixing the width of {@code bottomListPane}
     * when increasing the width of {@code bottomPaneSplit}
     */
    private void handleSplitPaneResponsive() {
        int splitHandleSize = 5;

        bottomPaneSplit.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (bottomInfoPane.getWidth() > bottomInfoPane.getMinWidth() - splitHandleSize) {
                bottomPaneSplit.setDividerPosition(0, (
                        bottomListPane.getWidth() + splitHandleSize) / newValue.doubleValue());
            }
        });
    }

    /**
     * Sets the accelerator of help button pane.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(Pane pane, KeyCombination keyCombination) {
        primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                pane.getOnMouseClicked().handle(new javafx.scene.input.MouseEvent(
                        javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0,
                        MouseButton.PRIMARY, 0, false, false, false,
                        false, false, false, false,
                        false, false, false, null));
                event.consume();
            }
        });
    }

    private void setBorderlessWindow() {
        // StageStyle.UNDECORATED is buggy
        primaryStage.initStyle(StageStyle.TRANSPARENT);
    }

    private void setDoubleClickMaximize() {
        topPane.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton()) && event.getClickCount() == 2) {
                raise(new MaximizeAppRequestEvent());
            }
        });
    }

    private void setDraggableTitleBar() {
        double minY = Screen.getPrimary().getVisualBounds().getMinY();

        topPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        topPane.setOnMouseDragged(event -> {
            // Only allow in title bar (Blue area)
            if (xOffset > 120 && yOffset > 40 && xOffset + yOffset - 200 > 0) {
                return;
            }

            double newY = event.getScreenY() - yOffset;
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(Math.max(newY, minY));
        });
    }

    @Subscribe
    private void handleShowPanelRequestEvent(ShowPanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        String requested = event.getRequestedPanel();
        Node toHide = activeNode;
        Animation fadeIn;
        Animation fadeOut;

        // Don't animate if the currently active panel is what requested
        if (!Objects.equals(activePanel, requested)) {
            // Pause all current running animation
            allAnimation.forEach(Animation::pause);
            allAnimation.clear();

            activePanel = requested;

            // Show relevant panel
            if (PdfPanel.PANEL_NAME.equals(requested)) {
                pdfPanel.load();
                activeNode = resumePanePlaceholder;
            } else if (InfoPanel.PANEL_NAME.equals(requested)) {
                infoPanel.hide();
                activeNode = infoPanePlaceholder;
                infoPanel.show();
            } else if ("WelcomePane".equals(requested)) {
                activeNode = welcomePane;
            }

            if (activeNode == null) {
                return;
            }

            if (animated) {
                // Show currently requested panel
                activeNode.setOpacity(0);
                activeNode.setVisible(true);

                fadeIn = UiUtil.fadeNode(activeNode, true, MAX_ANIMATION_TIME_MS, ev -> { });
                allAnimation.add(fadeIn);
                fadeIn.play();

                // Hide the previously selected panel
                if (toHide != null) {
                    fadeOut = UiUtil.fadeNode(toHide, false,
                            MAX_ANIMATION_TIME_MS, ev -> onFinishAnimation(toHide));
                    allAnimation.add(fadeOut);
                    fadeOut.play();
                }
            } else {
                // Show currently requested panel
                activeNode.setOpacity(1);
                activeNode.setVisible(true);

                // Hide the previously selected panel
                onFinishAnimation(toHide);
            }
        }
    }

    /**
     * Hide and unload relevant nodes when animation is done playing
     * @param toHide The window to hide
     */
    private void onFinishAnimation(Node toHide) {
        // Hide the previously selected panel
        if (toHide != null) {
            toHide.setVisible(false);
            if (toHide.equals(resumePanePlaceholder)) {
                pdfPanel.unload();
            }
        }
    }

    @Subscribe
    public void handleMinimizeAppRequestEvent(MinimizeAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        primaryStage.setIconified(true);
    }

    @Subscribe
    public void handleMaximizeAppRequestEvent(MaximizeAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        uiResizer.toggleMaximize();
    }
}
```
###### /java/seedu/address/ui/PdfPanel.java
``` java
/**
 * The PDF Panel of the App
 */
public class PdfPanel extends UiPart<Region> {

    public static final String PANEL_NAME = "PdfPanel";
    private static final String FXML = "PdfPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private PDDocument pdfDocument;
    private ArrayList<Image> pdfPages;

    private boolean forceUnload = false;
    private boolean isLoaded = false;
    private boolean loading = false;

    private Person selectedPerson = null;

    @FXML
    private ScrollPane resumePane;

    @FXML
    private VBox resumePanePages;

    @FXML
    private Label resumePageLabel;

    @FXML
    private VBox resumeLoading;

    @FXML
    private Label resumeLoadingLabel;

    @FXML
    private ProgressBar resumeLoadingBar;



    public PdfPanel() {
        super(FXML);
        setupEscKey();

        resumePane.widthProperty().addListener((observable, oldValue, newValue) -> handleResizeEvent());
        resumePane.vvalueProperty().addListener((observable, oldValue, newValue) -> handleScrollEvent());

        registerAsAnEventHandler(this);
    }

    /**
     * Setup binding for escape key to hide PDF panel
     */
    private void setupEscKey() {
        resumePane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                cancelResume();
                event.consume();
            }
        });
    }

    @FXML
    private void cancelResume() {
        raise(new ShowPanelRequestEvent(InfoPanel.PANEL_NAME));
    }

    /**
     * Load the resume of currently selected person
     */
    public void load() {
        // Retry later if loading
        if (loading) {
            Platform.runLater(this::load);
            return;
        }

        // Unload to make sure it is in a clean state
        unload();
        if (selectedPerson == null) {
            return;
        }

        // Check if the user have resume
        String filePath = selectedPerson.getResume().value;
        if (filePath == null) {
            return;
        }

        // Start loading
        loading = true;

        // Show progress to user
        resumePane.setVisible(false);
        resumeLoadingLabel.setText("Opening " + selectedPerson.getName().fullName + "'s resume");
        resumeLoadingBar.setProgress(0);
        resumeLoading.setVisible(true);

        // Start loading in new thread
        Thread pdfThread = new Thread(() -> loadInNewThread(filePath));
        pdfThread.start();
    }

    /**
     * Load resume and render into images in a new thread to prevent thread blocking
     * @param filePath of the resume
     */
    private void loadInNewThread(String filePath) {
        ArrayList<Image> pages = new ArrayList<>();

        try {
            // PDF renderer
            PDDocument pdfDocument = PDDocument.load(new File(filePath));
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
            BufferedImage bufferedImage;

            int totalPages = pdfDocument.getNumberOfPages();

            // Generate all images
            for (int currentPage = 0; currentPage < totalPages; currentPage++) {
                try {
                    bufferedImage = pdfRenderer.renderImageWithDPI(currentPage, 150, ImageType.RGB);
                    pages.add(SwingFXUtils.toFXImage(bufferedImage, null));
                } catch (IOException e) {
                    logger.info("PdfPanel: Page " + currentPage + " render failed");
                    pages.add(null);
                }

                // Stop thread and pass back if force unload
                if (forceUnload) {
                    Platform.runLater(() -> imageLoaded(null, null));
                    return;
                }

                // Update on main thread
                int currentLoaded = currentPage + 1;
                Platform.runLater(() -> update(currentLoaded, totalPages));
            }

            // Pass back to main thread
            Platform.runLater(() -> imageLoaded(pdfDocument, pages));

        } catch (IOException e) {
            logger.info("PdfPanel: Load of file " + filePath + " failed");
            // Pass back to main thread
            Platform.runLater(() -> imageLoaded(null, null));
        }
    }

    /**
     * Update status of the rendering so user know the status
     * @param currentLoaded number of pages loaded
     * @param totalPages of the PDF document
     */
    private void update(int currentLoaded, int totalPages) {
        resumeLoadingLabel.setText("Loading page " + currentLoaded + " of " + totalPages);
        resumeLoadingBar.setProgress((double) currentLoaded / (double) totalPages);
    }

    /**
     * Callback from separate thread indicates all pages are loaded and rendered
     * @param pdfDocument of the opened document
     * @param pages An array of images of all the pages
     */
    private void imageLoaded(PDDocument pdfDocument, ArrayList<Image> pages) {
        this.pdfDocument = pdfDocument;
        pdfPages = pages;

        if (pages == null) {
            isLoaded = false;

        } else {
            int totalPages = pdfPages.size();

            // Setup all blank pages
            for (int i = 0; i < totalPages; i++) {
                // Wrap inside VBox for styling
                VBox vBox = new VBox();
                vBox.getStyleClass().add("pdf-page");
                VBox.setMargin(vBox, new Insets(0, 0, 20, 0));

                // Setup VBox children (ImageView)
                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(true);
                imageView.setCache(true);

                // Add into view
                vBox.getChildren().add(imageView);
                resumePanePages.getChildren().add(vBox);
            }

            // Initialize size and scroll detection
            handleResizeEvent();
            handleScrollEvent();

            // Set label to first page
            resumePageLabel.setText(1 + " / " + totalPages);
            resumePageLabel.setVisible(true);

            isLoaded = true;
        }

        resumePane.setVisible(true);
        resumeLoading.setVisible(false);
        loading = false;
    }

    /**
     * Unload the PDFPanel to free up resources
     */
    public void unload() {
        // Force unload enabled
        forceUnload = true;

        // Retry later if loading
        if (loading) {
            Platform.runLater(this::unload);
            return;
        }

        // Only unload when it is imageLoaded
        if (isLoaded) {
            isLoaded = false;

            // Clear all array
            pdfPages.clear();
            resumePanePages.getChildren().clear();

            // Hide page
            resumePageLabel.setVisible(false);
            resumePane.setVisible(false);

            try {
                if (pdfDocument != null) {
                    pdfDocument.close();
                }
            } catch (IOException e) {
                logger.info("PdfPanel: Unload failed");
            }
        }

        forceUnload = false;
    }

    /**
     * Handle the resize event by resizing all pages
     */
    private void handleResizeEvent() {
        // Fit all images to width of the viewport
        double width = resumePane.getWidth() - 40;
        ObservableList<Node> childrens = resumePanePages.getChildren();

        // Resize all the images
        for (int i = 0; i < childrens.size(); i++) {
            VBox vBox = (VBox) childrens.get(i);
            ImageView imageView = (ImageView) vBox.getChildren().get(0);
            Image page = pdfPages.get(i);

            // Size have to be fixed so it can maintain the size even when images outside of viewport are cleared
            double aspectRatio = page.getWidth() / page.getHeight();
            imageView.setFitWidth(width);
            imageView.setFitHeight(width / aspectRatio);
        }
    }

    /**
     * Handle the scroll event to lazy load the images into ImageView for performance
     */
    private void handleScrollEvent() {
        ObservableList<Node> childrens = resumePanePages.getChildren();
        int totalPages = childrens.size();

        // Compute view boundary (Only display image if visible)
        Bounds viewBound = resumePane.localToScene(resumePane.getBoundsInLocal());
        double viewMinY = viewBound.getMinY();
        double viewMaxY = viewBound.getMaxY();
        double viewMid = (viewMinY + viewMaxY) / 2;

        for (int i = 0; i < totalPages; i++) {

            // Compute page boundary
            VBox vBox = (VBox) childrens.get(i);
            Bounds bounds = vBox.localToScene(vBox.getBoundsInLocal());

            ImageView imageView = (ImageView) vBox.getChildren().get(0);

            // Check if page is visible in viewport
            if (bounds.getMinY() < viewMaxY && bounds.getMaxY() > viewMinY) {
                if (imageView.getImage() == null) {
                    imageView.setImage(pdfPages.get(i));
                }
            } else {
                imageView.setImage(null);
            }

            // Update page number label
            if (bounds.getMinY() < viewMid && bounds.getMaxY() > viewMid) {
                resumePageLabel.setText((i + 1) + " / " + totalPages);
            }
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        PersonCard selectedCard = event.getNewSelection();
        selectedPerson = (selectedCard == null) ? null : selectedCard.getPerson();
    }

    @Subscribe
    private void handlePersonChangedEvent(PersonChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person source = event.getSource();
        Person target = event.getTarget();

        if (selectedPerson != null && selectedPerson.equals(source)) {
            selectedPerson = target;
        }
    }
}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    private void setupInputChange() {
        commandInput.textProperty().addListener((obs, old, inputText) -> {
            String result = "Enter a command...";

            floatParseRealTime.getStyleClass().remove(PARSE_VALID);
            floatParseRealTime.getStyleClass().remove(PARSE_INVALID);

            if (!inputText.equals("")) {
                Command command = logic.parse(inputText);

                if (command == null) {
                    floatParseRealTime.getStyleClass().add(PARSE_INVALID);
                    result = "Invalid command";

                } else {
                    floatParseRealTime.getStyleClass().add(PARSE_VALID);
                    result = command.getParsedResult();
                    if (result == null) {
                        result = "Valid command";
                    }
                }
            }
            floatParseLabel.setText(result);
        });

        commandInput.focusedProperty().addListener((obs, old, focused) -> {
            if (animated) {
                Animation animation;
                allAnimation.forEach(Animation::pause);
                allAnimation.clear();

                if (focused) {
                    floatParseRealTime.setOpacity(0);
                    floatParseRealTime.setVisible(true);
                    animation = UiUtil.fadeNode(floatParseRealTime, true, 100, (e) -> {
                    });
                } else {
                    animation = UiUtil.fadeNode(floatParseRealTime, false, 100, (e) -> {
                        floatParseRealTime.setVisible(false);
                    });
                }

                allAnimation.add(animation);
                animation.play();
            } else {
                floatParseRealTime.setOpacity(focused ? 1 : 0);
                floatParseRealTime.setVisible(focused);
            }
        });
    }
```
###### /java/seedu/address/model/person/InterviewDate.java
``` java
/**
 * Represents a Person's interview date in the address book.
 * Guarantees: immutable
 */
public class InterviewDate {
    public static final String MESSAGE_INTERVIEW_DATE_XML_ERROR =
            "Interview date must be in epoch format, failed to parse from XML";
    public static final ZoneOffset LOCAL_ZONE_OFFSET = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now());

    public final LocalDateTime dateTime;
    public final String value;

    /**
     * Constructs a {@code InterviewDate}.
     */
    public InterviewDate() {
        this((LocalDateTime) null);
    }

    /**
     * Constructs a {@code InterviewDate}.
     * @param timestamp A epoch timestamp
     */
    public InterviewDate(Long timestamp) {
        this(LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC));
    }

    /**
     * Constructs a {@code InterviewDate}.
     * @param dateTime of the person
     */
    public InterviewDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        if (dateTime != null) {
            this.value = String.valueOf(dateTime.toEpochSecond(ZoneOffset.UTC));
        } else {
            this.value = null;
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InterviewDate)) {
            return false;
        }

        InterviewDate i = (InterviewDate) other;
        return Objects.equals(getDateTime(), i.getDateTime());
    }

    @Override
    public int hashCode() {
        return getDateTime().hashCode();
    }
}
```
###### /java/seedu/address/model/person/ProfileImage.java
``` java
/**
 * Represents a Person's profile image in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFile(String)}
 */
public class ProfileImage {
    public static final String MESSAGE_IMAGE_CONSTRAINTS =
            "Profile image file should be at least 1 character long, exist in the same directory "
                    + "as the jar executable, smaller than 1MB and readable";

    private static final int ONEMEGABYTE = 1 * 1024 * 1024;
    private static final String IMAGE_VALIDATION_REGEX = ".*\\S.*";
    private static final int CROP_DIMENSION = 100;

    public final String value;
    public final String userInput;
    private boolean isHashed;
    private final Image image;

    /**
     * Constructs a {@code ProfileImage}.
     *
     * @param fileName A valid fileName.
     */
    public ProfileImage(String fileName) {
        isHashed = false;
        if (isNull(fileName)) {
            this.value = null;
            this.userInput = null;
            this.image = null;
        } else {
            checkArgument(isValidFile(fileName), MESSAGE_IMAGE_CONSTRAINTS);
            this.value = fileName;
            this.userInput = fileName;
            this.image = loadImage();
        }
    }

    public ProfileImage(String storageFileName, String userFileName) {
        isHashed = true;
        if (isNull(storageFileName)) {
            this.value = null;
            this.userInput = null;
            this.image = null;
        } else {
            checkArgument(isValidFile(storageFileName), MESSAGE_IMAGE_CONSTRAINTS);
            this.value = storageFileName;
            this.userInput = userFileName;
            this.image = loadImage();
        }
    }

    public Image getImage() {
        return image;
    }

    /**
     * Return the loaded {@code Image} of the person's Profile Image,
     * resized to 100px for performance issue
     * @return the image in {@code Image}
     */
    private Image loadImage() {
        try {
            File file = getFile();
            if (file != null) {
                //Image image = new Image(file.toURI().toString(), 0, 0, true, true, true);

                // Load image
                BufferedImage image = ImageIO.read(file);

                // Scaling amd resizing calculation
                int width = image.getWidth();
                int height = image.getHeight();
                int shorter = Math.min(width, height);
                double scale = (double) shorter / (double) CROP_DIMENSION;
                int x = 0;
                int y = 0;

                if (width < height) {
                    width = CROP_DIMENSION;
                    height = (int) Math.round((double) height / scale);
                    y = (CROP_DIMENSION - height) / 2;
                } else {
                    height = CROP_DIMENSION;
                    width = (int) Math.round((double) width / scale);
                    x = (CROP_DIMENSION - width) / 2;
                }

                // Resize start
                BufferedImage resized = new BufferedImage(CROP_DIMENSION, CROP_DIMENSION,
                        BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D g2d = resized.createGraphics();
                g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_SPEED));
                g2d.drawImage(image, x, y, width, height, null);

                // Output
                WritableImage output = new WritableImage(CROP_DIMENSION, CROP_DIMENSION);
                SwingFXUtils.toFXImage(resized, output);

                // Clean up
                image.flush();
                resized.flush();
                g2d.dispose();

                return output;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Return the {@code File} of the image
     * @return the image in {@code File}
     */
    private File getFile() {
        if (this.value == null) {
            return null;
        }
        return getFileFromPath(this.value);
    }

    /**
     * Return the {@code File} representation of the path
     * @param path of the image
     * @return the {@code File} representation
     */
    private static File getFileFromPath(String path) {
        String userDir = System.getProperty("user.dir");
        return new File(userDir + File.separator + path);
    }

    /**
     * Returns true if a given string is a valid file path,
     * however it doesn't validate if it is a valid image file
     * due to there are too many different image types
     */
    public static boolean isValidFile(String test) {
        requireNonNull(test);

        if (!test.matches(IMAGE_VALIDATION_REGEX)) {
            return false;
        }

        File imageFile = getFileFromPath(test);

        if (imageFile.isDirectory() || !imageFile.exists() || imageFile.length() > ONEMEGABYTE) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isHashed() {
        return isHashed;
    }

    @Override
    public String toString() {
        return userInput;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof ProfileImage // instanceof handles nulls
                && ((this.value == null && ((ProfileImage) other).value == null) //both value are null
                || (isHashed && ((ProfileImage) other).isHashed) ? isHashEqual(this.value, ((ProfileImage) other).value)
                : this.userInput.equals(((ProfileImage) other).userInput))); // state check
    }
    /**
     * Checks whether the hash of two resume are the same
     * @param first resume
     * @param second resume
     * @return same as true or false otherwise
     */
    private boolean isHashEqual(String first, String second) {
        assert(first.split("_").length == 2);
        String firstHash = first.split("_")[1];
        String secondHash = second.split("_")[1];
        return firstHash.equals(secondHash);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/model/person/Comment.java
``` java
/**
 * Represents a Person's comment in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidComment(String)}
 */
public class Comment {

    public static final String MESSAGE_COMMENT_CONSTRAINTS = "Person comment can take any values";
    public static final String COMMENT_VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Constructs a {@code Comment}.
     *
     * @param comment A valid comment.
     */
    public Comment(String comment) {
        if (isNull(comment)) {
            this.value = null;
        } else {
            checkArgument(isValidComment(comment), MESSAGE_COMMENT_CONSTRAINTS);
            this.value = comment;
        }
    }

    /**
     * Returns true if a given string is a valid comment.
     * By default any string are valid
     */
    public static boolean isValidComment(String test) {
        return test.matches(COMMENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof Comment // instanceof handles nulls
                && Objects.equals(this.value, ((Comment) other).value)); // State check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/commons/events/ui/InfoPanelChangedEvent.java
``` java
/**
 * Indicates a change in Info Panel (Used for automated testing purpose)
 */
public class InfoPanelChangedEvent extends Event {
    public static final EventType<InfoPanelChangedEvent> INFO_PANEL_EVENT =
            new EventType<>("InfoPanelChangedEvent");

    public InfoPanelChangedEvent() {
        this(INFO_PANEL_EVENT);
    }

    public InfoPanelChangedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
```
###### /java/seedu/address/commons/events/ui/MinimizeAppRequestEvent.java
``` java
/**
 * Indicates a request for App minimize
 */
public class MinimizeAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/MaximizeAppRequestEvent.java
``` java
/**
 * Indicates a request for App minimize
 */
public class MaximizeAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/PersonChangedEvent.java
``` java
/**
 * Indicates a person change in address book
 */
public class PersonChangedEvent extends BaseEvent {

    private final Person source;
    private final Person target;

    public PersonChangedEvent(Person source, Person target) {
        this.source = source;
        this.target = target;
    }

    public Person getSource() {
        return source;
    }

    public Person getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/ShowPanelRequestEvent.java
``` java
/**
 * Indicates a request for panel show
 */
public class ShowPanelRequestEvent extends BaseEvent {

    private final String panel;

    public ShowPanelRequestEvent(String panel) {
        this.panel = panel;
    }

    public String getRequestedPanel() {
        return panel;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/util/UiUtil.java
``` java
/**
 * Helper functions for handling UI information
 */
public class UiUtil {
    public static final Interpolator EASE_OUT_CUBIC = Interpolator.SPLINE(0.215, 0.61, 0.355, 1);
    private static final String FORMAT_DATE = "d MMM y";
    private static final String FORMAT_TIME = "hh:mm:ssa";

    /**
     * Convert double into string with {@code points} amount of decimal places
     * @param decimal The double to be formatted
     * @param points Number of decimal places
     * @return the formatted string with {@code points} number of decimal places
     */
    public static String toFixed(double decimal, int points) {
        return toFixed(String.valueOf(decimal), points);
    }

    /**
     * Convert string representation of decimal into string with {@code points} amount of decimal places
     * @param decimal The string representation of decimal to be formatted
     * @param points Number of decimal places
     * @return the formatted string with {@code points} number of decimal places
     */
    public static String toFixed(String decimal, int points) {
        double value = Double.parseDouble(decimal);
        String pattern = "0";

        if (points > 0) {
            pattern += ".";
            pattern += StringUtils.repeat("0", points);
        }

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * Convert JavaFX color into web hex color
     * @param color to be converted
     * @return the web hex String representation of the color
     */
    public static String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }

    /**
     * Fade in or fade out the node, then callback
     * @param node to be faded in or out
     * @param fadeIn If set, the fade will be fade in, otherwise it will be fade out
     * @param from The opacity to start fading from
     * @param maxDuration of the transition should be
     * @param callback after the transition is done
     * @return the {@code Animation} of the transition
     */
    public static Animation fadeNode(Node node, boolean fadeIn, double from,
                                     double maxDuration, EventHandler<ActionEvent> callback) {
        Interpolator easing = fadeIn ? Interpolator.EASE_IN : Interpolator.EASE_OUT;
        double to = fadeIn ? 1 : 0;
        double duration = Math.max(1, Math.abs(from - to) * maxDuration);

        FadeTransition fade = new FadeTransition(Duration.millis(duration), node);
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.setCycleCount(1);
        fade.setAutoReverse(false);
        fade.setInterpolator(easing);
        fade.setOnFinished(event -> {
            if (Math.abs(node.getOpacity() - to) < 1e-3) {
                callback.handle(event);
            }
        });

        return fade;
    }

    /**
     * Fade in or fade out the node, then callback
     * @param node to be faded in or out
     * @param fadeIn If set, the fade will be fade in, otherwise it will be fade out
     * @param maxDuration of the transition should be
     * @param callback after the transition is done
     * @return the {@code Animation} of the transition
     */
    public static Animation fadeNode(Node node, boolean fadeIn,
                                     double maxDuration, EventHandler<ActionEvent> callback) {
        double from = node.getOpacity();
        return fadeNode(node, fadeIn, from, maxDuration, callback);
    }

    /**
     * Format date time to more readable format
     * @param dateTime to be formatted
     * @return the formatted date time
     */
    public static String formatDate(LocalDateTime dateTime) {
        String date = DateTimeFormatter.ofPattern(FORMAT_DATE, Locale.ENGLISH).format(dateTime);
        String time = DateTimeFormatter.ofPattern(FORMAT_TIME, Locale.ENGLISH).format(dateTime).toLowerCase();
        return date + " " + time;
    }
}
```
###### /java/seedu/address/logic/commands/ShowCommand.java
``` java
/**
 * Shows a specific panel
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a specific panel. The panel can be either 'info' or 'resume'.\n"
            + "Make sure person are selected before calling this command.\n"
            + "When resume is requested, it will only shows when it is available.\n"
            + "Parameters: PANEL (must be either 'info' or 'resume', case sensitive)\n"
            + "Example: " + COMMAND_WORD + " info";

    public static final String PANEL_INFO = "info";
    public static final String PANEL_RESUME = "resume";

    public static final String MESSAGE_NOT_SELECTED = "A person must be selected before showing a panel.";
    public static final String MESSAGE_RESUME_NA = "The selected person doesn't have a resume";
    public static final String MESSAGE_SHOW_SUCCESS = "Showing the requested panel";

    /**
     * Enumeration of acceptable panel
     */
    public enum Panel {
        INFO, RESUME
    }

    private final Panel panel;

    public ShowCommand(Panel panel) {
        requireNonNull(panel);
        this.panel = panel;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Person selectedPerson = model.getSelectedPerson();
        if (selectedPerson == null) {
            throw new CommandException(MESSAGE_NOT_SELECTED);

        } else {
            switch (panel) {
            case INFO:
                EventsCenter.getInstance().post(new ShowPanelRequestEvent(InfoPanel.PANEL_NAME));
                break;
            case RESUME:
                if (selectedPerson.getResume().value != null) {
                    EventsCenter.getInstance().post(new ShowPanelRequestEvent(PdfPanel.PANEL_NAME));
                } else {
                    throw new CommandException(MESSAGE_RESUME_NA);
                }
                break;
            default:
                break;
            }

            return new CommandResult(MESSAGE_SHOW_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && this.panel.equals(((ShowCommand) other).panel)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/InterviewCommand.java
``` java
/**
 * Schedule interview of an existing person in the address book.
 */
public class InterviewCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "interview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule interview for the person "
            + "by the index number used in the last person listing. "
            + "Existing scheduled date will be overwritten by the input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "DATETIME (parse by natural language)\n"
            + "Example: " + COMMAND_WORD + " 1 next Friday at 3pm";

    public static final String MESSAGE_INTERVIEW_PERSON_SUCCESS =
            "Interview of person named %1$s has been scheduled on %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in HR+.";
    public static final String PARSED_RESULT = "Parsed date: %1$s";

    private final Index index;
    private final LocalDateTime dateTime;

    private Person personToInterview;
    private Person scheduledPerson;

    /**
     * @param index of the person in the filtered person list to schedule interview
     * @param dateTime of the interview
     */
    public InterviewCommand(Index index, LocalDateTime dateTime) {
        requireNonNull(index);
        requireNonNull(dateTime);

        this.index    = index;
        this.dateTime = dateTime;
    }

    public Index getIndex() {
        return index;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Person getPersonToInterview() {
        return personToInterview;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToInterview, scheduledPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_INTERVIEW_PERSON_SUCCESS,
                scheduledPerson.getName(), UiUtil.formatDate(dateTime)));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToInterview = lastShownList.get(index.getZeroBased());
        scheduledPerson = createScheduledPerson(personToInterview, dateTime);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToInterview}
     * with updated with {@code dateTime}.
     */
    private static Person createScheduledPerson(Person personToInterview, LocalDateTime dateTime) {
        requireAllNonNull(personToInterview, dateTime);

        return new Person(personToInterview.getName(), personToInterview.getPhone(), personToInterview.getEmail(),
                personToInterview.getAddress(), personToInterview.getUniversity(),
                personToInterview.getExpectedGraduationYear(),
                personToInterview.getMajor(), personToInterview.getGradePointAverage(),
                personToInterview.getJobApplied(), personToInterview.getRating(),
                personToInterview.getResume(), personToInterview.getProfileImage(), personToInterview.getComment(),
                new InterviewDate(dateTime), personToInterview.getStatus(),
                personToInterview.getTags());
    }

    @Override
    public String getParsedResult() {
        return String.format(PARSED_RESULT, UiUtil.formatDate(dateTime));
    }

    @Override
    public boolean equals(Object other) {
        // Short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InterviewCommand)) {
            return false;
        }

        // State check
        InterviewCommand i = (InterviewCommand) other;
        return getIndex().equals(i.getIndex())
                && getDateTime().equals(i.getDateTime())
                && Objects.equals(getPersonToInterview(), i.getPersonToInterview());
    }
}
```
###### /java/seedu/address/logic/parser/ShowCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Parse the arguments
        String requestedString = args.trim();
        ShowCommand.Panel requestedPanel = parsePanel(requestedString);
        return new ShowCommand(requestedPanel);
    }

    /**
     * Parses {@code panel} into a {@code ShowCommand.Panel} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified panel is invalid (not info or resume).
     */
    private ShowCommand.Panel parsePanel(String panel) throws ParseException {
        String trimmed = panel.trim();

        switch (trimmed) {
        case ShowCommand.PANEL_INFO:
            return ShowCommand.Panel.INFO;
        case ShowCommand.PANEL_RESUME:
            return ShowCommand.Panel.RESUME;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses a {@code String profileImage} into a {@code ProfileImage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code profileImage} is invalid.
     */
    public static ProfileImage parseProfileImage(String profileImage) throws IllegalValueException {
        requireNonNull(profileImage);
        String trimmedProfileImage = profileImage.trim();
        if (!ProfileImage.isValidFile(trimmedProfileImage)) {
            throw new IllegalValueException(ProfileImage.MESSAGE_IMAGE_CONSTRAINTS);
        }
        return new ProfileImage(trimmedProfileImage);
    }

    /**
     * Parses a {@code Optional<String> profileImage} into an {@code Optional<ProfileImage>}
     * if {@code profileImage} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProfileImage> parseProfileImage(Optional<String> profileImage) throws IllegalValueException {
        requireNonNull(profileImage);
        return profileImage.isPresent() ? Optional.of(parseProfileImage(profileImage.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String comment} into a {@code Comment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code comment} is invalid.
     */
    public static Comment parseComment(String comment) throws IllegalValueException {
        requireNonNull(comment);
        String trimmedComment = comment.trim();
        if (!Comment.isValidComment(trimmedComment)) {
            throw new IllegalValueException(Comment.MESSAGE_COMMENT_CONSTRAINTS);
        }
        return new Comment(trimmedComment);
    }

    /**
     * Parses a {@code Optional<String> comment} into an {@code Optional<Comment>} if {@code comment} is present.
     * See header comment of this class regarding the use of {@code Comment} parameters.
     */
    public static Optional<Comment> parseComment(Optional<String> comment) throws IllegalValueException {
        requireNonNull(comment);
        return comment.isPresent() ? Optional.of(parseComment(comment.get())) : Optional.empty();
    }
}
```
###### /java/seedu/address/logic/parser/InterviewCommandParser.java
``` java
/**
 * Parses input arguments and creates a new InterviewCommand object
 */
public class InterviewCommandParser implements Parser<InterviewCommand> {

    public static final String MESSAGE_DATETIME_PARSE_FAIL = "Failed to parse the date time from the string: %1$s";
    private static final com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();

    /**
     * Parses the given {@code String} of arguments in the context of the InterviewCommand
     * and returns an InterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InterviewCommand parse(String args) throws ParseException {
        try {
            // Parse the arguments
            String[] arguments = args.trim().split("\\s+", 2);
            if (arguments.length != 2) {
                throw new IllegalValueException("Invalid command, expected 2 arguments");
            }

            // Parse the index
            Index index = ParserUtil.parseIndex(arguments[0]);

            // Parse the date time
            LocalDateTime dateTime = parseDateFromNaturalLanguage(arguments[1]);

            return new InterviewCommand(index, dateTime);

        } catch (ParseException pe) {
            throw pe;

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given natural language {@code String} and returns a {@code LocalDateTime} object
     * that represents the English representation of the date and time
     * @throws ParseException if the phrase cannot be converted to date and time
     */
    private LocalDateTime parseDateFromNaturalLanguage(String naturalLanguage) throws ParseException {
        List<DateGroup> groups = parser.parse(naturalLanguage);
        if (groups.size() < 1) {
            throw new ParseException(String.format(MESSAGE_DATETIME_PARSE_FAIL, naturalLanguage));
        }

        List<Date> dates = groups.get(0).getDates();
        if (dates.size() < 1) {
            throw new ParseException(String.format(MESSAGE_DATETIME_PARSE_FAIL, naturalLanguage));
        }

        Date date = dates.get(0);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
```
###### /java/seedu/address/logic/parser/EditCommandParser.java
``` java
    /**
     * Parses {@code Optional<ProfileImage> profileImage} into a {@code Optional<ProfileImage>}
     * if {@code profileImage} is non-empty.
     * If profile image is present and equals to empty string, it will be parsed into a
     * {@code ProfileImage} containing null value.
     */
    private Optional<ProfileImage> parseProfileImageForEdit(Optional<String> profileImage)
            throws IllegalValueException {
        assert profileImage != null;
        if (!profileImage.isPresent()) {
            return Optional.empty();
        }
        if (profileImage.get().equals("")) {
            return Optional.of(new ProfileImage(null));
        } else {
            return ParserUtil.parseProfileImage(profileImage);
        }
    }

    /**
     * Parses {@code Optional<Comment> comment} into a {@code Optional<Comment>} if {@code comment} is non-empty.
     */
    private Optional<Comment> parseCommentForEdit(Optional<String> comment) throws IllegalValueException {
        assert comment != null;
        if (!comment.isPresent()) {
            return Optional.empty();
        }
        if (comment.get().equals("")) {
            return Optional.of(new Comment(null));
        } else {
            return ParserUtil.parseComment(comment);
        }
    }
```
###### /resources/view/InfoPanel.fxml
``` fxml
<AnchorPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="infoPaneWrapper" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
    <ScrollPane fx:id="infoMainPane" fitToHeight="true" fitToWidth="true" hbarPolicy="NEVER" pannable="true" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" focusTraversable="true" cache="true" cacheHint="SPEED"/>
    <SplitPane fx:id="infoSplitPane" dividerPositions="1" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
        <ScrollPane fx:id="infoSplitMainPane" fitToHeight="true" fitToWidth="true" hbarPolicy="NEVER" pannable="true" focusTraversable="true" cache="true" cacheHint="SPEED">
            <VBox fx:id="infoMain" cache="true" cacheHint="SPEED">
                <VBox fx:id="infoMainPart" opacity="0" cache="true" cacheHint="SPEED">
                    <AnchorPane fx:id="infoMainTop" cache="true" cacheHint="SPEED">
                        <VBox fx:id="infoMainTopLeft" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
                            <Label fx:id="infoMainName" opacity="0" cache="true" cacheHint="SPEED"/>
                            <Label fx:id="infoMainUniversity" opacity="0" cache="true" cacheHint="SPEED">
                                <VBox.margin>
                                    <Insets bottom="-2.0" />
                                </VBox.margin>
                            </Label>
                            <Label fx:id="infoMainMajorYear" opacity="0" cache="true" cacheHint="SPEED"/>
                            <padding>
                                <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                            </padding>
                        </VBox>
                        <HBox fx:id="infoMainTopRight" opacity="0" alignment="CENTER_RIGHT" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
                            <Label fx:id="infoMainCgpaLabel" text="cGPA" cache="true" cacheHint="SPEED">
                                <HBox.margin>
                                    <Insets right="5.0" />
                                </HBox.margin>
                            </Label>
                            <Label fx:id="infoMainCgpa" alignment="CENTER" maxHeight="36.0" maxWidth="36.0" minHeight="36.0" minWidth="36.0" prefHeight="36.0" prefWidth="36.0" cache="true" cacheHint="SPEED">
                            </Label>
                        </HBox>
                        <VBox.margin>
                            <Insets bottom="10.0" />
                        </VBox.margin>
                    </AnchorPane>
                    <VBox fx:id="infoMainContact" cache="true" cacheHint="SPEED">
                        <HBox fx:id="infoMainContactEmailPane" opacity="0" alignment="CENTER_LEFT" cache="true" cacheHint="SPEED">
                            <VBox fx:id="iconEmailOuter" alignment="CENTER" maxHeight="15.0" maxWidth="15.0" minHeight="15.0" minWidth="15.0" prefHeight="15.0" prefWidth="15.0" cache="true" cacheHint="SPEED">
                                <Pane fx:id="iconEmail" styleClass="icon-email" maxHeight="11.4" maxWidth="15.0" minHeight="11.4" minWidth="15.0" prefHeight="11.4" prefWidth="15.0" cache="true" cacheHint="SPEED"/>
                                <HBox.margin>
                                    <Insets right="5.0" />
                                </HBox.margin>
                            </VBox>
                            <Label fx:id="infoMainEmail" cache="true" cacheHint="SPEED"/>
                            <VBox.margin>
                                <Insets bottom="6.0" />
                            </VBox.margin>
                        </HBox>
                        <HBox fx:id="infoMainContactAddressPane" opacity="0" alignment="CENTER_LEFT" cache="true" cacheHint="SPEED">
                            <VBox fx:id="iconAddressOuter" alignment="CENTER" maxHeight="15.0" maxWidth="15.0" minHeight="15.0" minWidth="15.0" prefHeight="15.0" prefWidth="15.0" cache="true" cacheHint="SPEED">
                                <Pane fx:id="iconAddress" styleClass="icon-address" maxHeight="15.0" maxWidth="9.33" minHeight="15.0" minWidth="9.33" prefHeight="15.0" prefWidth="9.33" cache="true" cacheHint="SPEED"/>
                                <HBox.margin>
                                    <Insets right="5.0" />
                                </HBox.margin>
                            </VBox>
                            <Label fx:id="infoMainAddress" cache="true" cacheHint="SPEED"/>
                            <VBox.margin>
                                <Insets bottom="6.0" />
                            </VBox.margin>
                        </HBox>
                        <HBox fx:id="infoMainContactPhonePane" opacity="0" alignment="CENTER_LEFT" cache="true" cacheHint="SPEED">
                            <VBox fx:id="iconPhoneOuter" alignment="CENTER" maxHeight="15.0" maxWidth="15.0" minHeight="15.0" minWidth="15.0" prefHeight="15.0" prefWidth="15.0" cache="true" cacheHint="SPEED">
                                <Pane fx:id="iconPhone" styleClass="icon-phone" maxHeight="15.0" maxWidth="15.0" minHeight="15.0" minWidth="15.0" prefHeight="15.0" prefWidth="15.0" cache="true" cacheHint="SPEED"/>
                                <HBox.margin>
                                    <Insets right="5.0" />
                                </HBox.margin>
                            </VBox>
                            <Label fx:id="infoMainPhone" cache="true" cacheHint="SPEED"/>
                        </HBox>
                        <padding>
                            <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                        </padding>
                        <VBox.margin>
                            <Insets bottom="5.0" />
                        </VBox.margin>
                    </VBox>
                    <HBox fx:id="infoMainInterview" cache="true" cacheHint="SPEED">
                        <GridPane fx:id="infoMainInterviewDetails" alignment="CENTER" HBox.hgrow="ALWAYS" cache="true" cacheHint="SPEED">
                            <columnConstraints>
                                <ColumnConstraints fillWidth="false" hgrow="NEVER" />
                                <ColumnConstraints hgrow="ALWAYS" />
                            </columnConstraints>
                            <rowConstraints>
                                <RowConstraints fillHeight="false" vgrow="NEVER" />
                                <RowConstraints fillHeight="false" vgrow="NEVER" />
                            </rowConstraints>
                            <Label fx:id="infoMainPositionLabel" opacity="0" minWidth="-Infinity" text="Position" cache="true" cacheHint="SPEED">
                                <GridPane.margin>
                                    <Insets right="5.0" />
                                </GridPane.margin>
                            </Label>
                            <Label fx:id="infoMainStatusLabel" opacity="0" minWidth="-Infinity" text="Status" GridPane.rowIndex="1" cache="true" cacheHint="SPEED">
                                <GridPane.margin>
                                    <Insets right="5.0" />
                                </GridPane.margin>
                            </Label>
                            <Label fx:id="infoMainPosition" opacity="0" GridPane.columnIndex="1" cache="true" cacheHint="SPEED"/>
                            <Label fx:id="infoMainStatus" opacity="0" GridPane.columnIndex="1" GridPane.rowIndex="1" cache="true" cacheHint="SPEED"/>
                            <padding>
                                <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                            </padding>
                        </GridPane>
                        <VBox fx:id="infoMainInterviewDatePane" opacity="0" alignment="CENTER" minWidth="-Infinity" cache="true" cacheHint="SPEED">
                            <VBox fx:id="infoMainInterviewDateCalendar" minWidth="65.0" cache="true" cacheHint="SPEED">
                                <Label fx:id="infoMainInterviewMonth" alignment="CENTER" maxWidth="Infinity" VBox.vgrow="ALWAYS" cache="true" cacheHint="SPEED">
                                    <VBox.margin>
                                        <Insets />
                                    </VBox.margin>
                                    <padding>
                                        <Insets bottom="2.0" left="8.0" right="8.0" top="2.0" />
                                    </padding>
                                </Label>
                                <Label fx:id="infoMainInterviewDate" alignment="CENTER" maxWidth="Infinity" cache="true" cacheHint="SPEED">
                                    <VBox.margin>
                                        <Insets bottom="-5.0" />
                                    </VBox.margin>
                                </Label>
                                <Label fx:id="infoMainInterviewDay" alignment="CENTER" maxWidth="Infinity" cache="true" cacheHint="SPEED">
                                    <VBox.margin>
                                        <Insets />
                                    </VBox.margin>
                                </Label>
                                <padding>
                                    <Insets bottom="8.0" />
                                </padding>
                                <VBox.margin>
                                    <Insets bottom="3.0" />
                                </VBox.margin>
                            </VBox>
                            <Label fx:id="infoMainInterviewTime" alignment="CENTER" maxWidth="Infinity" cache="true" cacheHint="SPEED"/>
                        </VBox>
                    </HBox>
                    <VBox fx:id="infoMainCommentsPane" opacity="0" cache="true" cacheHint="SPEED">
                        <Label fx:id="infoMainCommentsLabel" text="Comments" cache="true" cacheHint="SPEED">
                            <VBox.margin>
                                <Insets bottom="5.0" left="5.0" />
                            </VBox.margin>
                        </Label>
                        <Label fx:id="infoMainComments" alignment="TOP_LEFT" maxWidth="Infinity" minHeight="60.0" wrapText="true" cache="true" cacheHint="SPEED">
                            <padding>
                                <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
                            </padding>
                        </Label>
                        <VBox.margin>
                            <Insets bottom="20.0" />
                        </VBox.margin>
                    </VBox>
                </VBox>
                <AnchorPane fx:id="infoMainRatings" cache="true" cacheHint="SPEED"/>
                <padding>
                    <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
                </padding>
            </VBox>
        </ScrollPane>
        <AnchorPane fx:id="infoSplitSidePane" maxWidth="300.0" minWidth="250.0" cache="true" cacheHint="SPEED">
            <padding>
                <Insets left="16.0" right="8.0" top="8.0" />
            </padding>
            <VBox fx:id="infoSplitRatings" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
                <VBox fx:id="infoSplitSide" alignment="TOP_CENTER" cache="true" cacheHint="SPEED">
                    <AnchorPane fx:id="infoSideGraph" maxHeight="150.0" minHeight="150.0" prefHeight="150.0" cache="true" cacheHint="SPEED"/>
                    <GridPane fx:id="infoSideRatingDetails" cache="true" cacheHint="SPEED">
                        <columnConstraints>
                            <ColumnConstraints hgrow="NEVER" />
                            <ColumnConstraints hgrow="ALWAYS" />
                            <ColumnConstraints hgrow="NEVER" />
                        </columnConstraints>
                        <rowConstraints>
                            <RowConstraints vgrow="NEVER" />
                            <RowConstraints vgrow="NEVER" />
                            <RowConstraints vgrow="NEVER" />
                            <RowConstraints vgrow="NEVER" />
                            <RowConstraints vgrow="NEVER" />
                        </rowConstraints>
                        <Label fx:id="infoRatingTechnicalLabel" text="Technical" GridPane.halignment="RIGHT" cache="true" cacheHint="SPEED">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                            <padding>
                                <Insets bottom="5.0" />
                            </padding>
                        </Label>
                        <ProgressBar fx:id="infoRatingTechnical" styleClass="hr-progress-bar" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" cache="true" cacheHint="SPEED"/>
                        <Label fx:id="infoRatingTechnicalValue" GridPane.halignment="LEFT" GridPane.columnIndex="2" GridPane.rowIndex="0" cache="true" cacheHint="SPEED">
                            <padding>
                                <Insets left="5.0" />
                            </padding>
                        </Label>
                        <Label fx:id="infoRatingCommunicationLabel" text="Communication" GridPane.halignment="RIGHT" GridPane.rowIndex="1" cache="true" cacheHint="SPEED">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                            <padding>
                                <Insets bottom="5.0" />
                            </padding>
                        </Label>
                        <ProgressBar fx:id="infoRatingCommunication" styleClass="hr-progress-bar" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" GridPane.rowIndex="1" cache="true" cacheHint="SPEED"/>
                        <Label fx:id="infoRatingCommunicationValue" GridPane.halignment="LEFT" GridPane.columnIndex="2" GridPane.rowIndex="1" cache="true" cacheHint="SPEED">
                            <padding>
                                <Insets left="5.0" />
                            </padding>
                        </Label>
                        <Label fx:id="infoRatingProblemSolvingLabel" text="Problem Solving" GridPane.halignment="RIGHT" GridPane.rowIndex="2" cache="true" cacheHint="SPEED">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                            <padding>
                                <Insets bottom="5.0" />
                            </padding>
                        </Label>
                        <ProgressBar fx:id="infoRatingProblemSolving" styleClass="hr-progress-bar" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" GridPane.rowIndex="2" cache="true" cacheHint="SPEED"/>
                        <Label fx:id="infoRatingProblemSolvingValue" GridPane.halignment="LEFT" GridPane.columnIndex="2" GridPane.rowIndex="2" cache="true" cacheHint="SPEED">
                            <padding>
                                <Insets left="5.0" />
                            </padding>
                        </Label>
                        <Label fx:id="infoRatingExperienceLabel" text="Experience" GridPane.halignment="RIGHT" GridPane.rowIndex="3" cache="true" cacheHint="SPEED">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                            <padding>
                                <Insets bottom="5.0" />
                            </padding>
                        </Label>
                        <ProgressBar fx:id="infoRatingExperience" styleClass="hr-progress-bar" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" GridPane.rowIndex="3" cache="true" cacheHint="SPEED"/>
                        <Label fx:id="infoRatingExperienceValue" GridPane.halignment="LEFT" GridPane.columnIndex="2" GridPane.rowIndex="3" cache="true" cacheHint="SPEED">
                            <padding>
                                <Insets left="5.0" />
                            </padding>
                        </Label>
                        <Label fx:id="infoRatingOverallLabel" text="Average" GridPane.halignment="RIGHT" GridPane.rowIndex="4" cache="true" cacheHint="SPEED">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                        </Label>
                        <ProgressBar fx:id="infoRatingOverall" styleClass="hr-progress-bar" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" GridPane.rowIndex="4" cache="true" cacheHint="SPEED"/>
                        <Label fx:id="infoRatingOverallValue" GridPane.halignment="LEFT" GridPane.columnIndex="2" GridPane.rowIndex="4" cache="true" cacheHint="SPEED">
                            <padding>
                                <Insets left="5.0" />
                            </padding>
                        </Label>
                        <padding>
                            <Insets bottom="8.0" top="8.0" />
                        </padding>
                    </GridPane>
                </VBox>
                <VBox fx:id="infoSideButtons" alignment="BOTTOM_CENTER" VBox.vgrow="ALWAYS" cache="true" cacheHint="SPEED">
                    <Button fx:id="infoSideButtonResume" styleClass="hr-button" onAction="#showResume" maxWidth="Infinity" mnemonicParsing="false" cache="true" cacheHint="SPEED">
                        <padding>
                            <Insets bottom="6.0" left="8.0" right="8.0" top="6.0" />
                        </padding>
                    </Button>
                    <padding>
                        <Insets bottom="10.0" left="2.0" right="2.0" top="10.0" />
                    </padding>
                </VBox>
            </VBox>
        </AnchorPane>
    </SplitPane>
</AnchorPane>
```
###### /resources/view/TitleBar.fxml
``` fxml
<HBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="topTitle" alignment="CENTER_RIGHT" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
    <Label fx:id="topStatusMessage" maxWidth="Infinity" HBox.hgrow="SOMETIMES" cache="true" cacheHint="SPEED"/>
    <Label fx:id="topStatusFile" HBox.hgrow="SOMETIMES" cache="true" cacheHint="SPEED"/>
    <HBox fx:id="topControl" minWidth="-Infinity" cache="true" cacheHint="SPEED">
        <Pane fx:id="controlHelp" onMouseClicked="#handleHelp" layoutX="21.0" layoutY="21.0" maxHeight="18.0" maxWidth="18.0" minHeight="18.0" minWidth="18.0" prefHeight="18.0" prefWidth="18.0" HBox.hgrow="NEVER" cache="true" cacheHint="SPEED">
            <Pane fx:id="controlHelpInner" styleClass="icon-help" layoutX="3.29" maxHeight="18.0" maxWidth="11.42" minHeight="18.0" minWidth="11.42" prefHeight="18.0" prefWidth="11.42" cache="true" cacheHint="SPEED"/>
            <HBox.margin>
                <Insets right="20.0" />
            </HBox.margin>
        </Pane>
        <Pane fx:id="controlMinimize" onMouseClicked="#handleMinimize" maxHeight="18.0" maxWidth="18.0" minHeight="18.0" minWidth="18.0" prefHeight="18.0" prefWidth="18.0" HBox.hgrow="NEVER" cache="true" cacheHint="SPEED">
            <HBox.margin>
                <Insets right="10.0" />
            </HBox.margin>
            <Pane fx:id="controlMinimizeInner" layoutY="16.2" maxHeight="1.8" maxWidth="18.0" minHeight="1.8" minWidth="18.0" prefHeight="1.8" prefWidth="18.0" cache="true" cacheHint="SPEED"/>
        </Pane>
        <Pane fx:id="controlMaximize" styleClass="icon-expand" onMouseClicked="#handleMaximize" maxHeight="18.0" maxWidth="18.0" minHeight="18.0" minWidth="18.0" prefHeight="18.0" prefWidth="18.0" HBox.hgrow="NEVER" cache="true" cacheHint="SPEED">
            <HBox.margin>
                <Insets right="10.0" />
            </HBox.margin>
        </Pane>
        <Pane fx:id="controlClose" styleClass="icon-close" onMouseClicked="#handleExit" maxHeight="18.0" maxWidth="18.0" minHeight="18.0" minWidth="18.0" prefHeight="18.0" prefWidth="18.0" HBox.hgrow="NEVER" cache="true" cacheHint="SPEED"/>
        <padding>
            <Insets bottom="11.0" left="11.0" right="11.0" top="11.0" />
        </padding>
    </HBox>
</HBox>
```
###### /resources/view/MainWindow.fxml
``` fxml
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" minWidth="800" minHeight="600">
    <scene>
        <Scene>
            <stylesheets>
                <URL value="@HRTheme.css"/>
            </stylesheets>
            <AnchorPane fx:id="mainWindow">
                <AnchorPane fx:id="topPane" maxHeight="80.0" minHeight="80.0" prefHeight="80.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                    <AnchorPane fx:id="logoTop" styleClass="logo-hr" maxWidth="75.0" minWidth="75.0" prefWidth="75.0" AnchorPane.bottomAnchor="15.0" AnchorPane.leftAnchor="30.0" AnchorPane.topAnchor="15.0" />
                    <AnchorPane fx:id="topTitlePlaceholder" AnchorPane.bottomAnchor="40.0" AnchorPane.leftAnchor="160.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0"/>
                    <AnchorPane fx:id="topDownPane" maxHeight="40.0" minHeight="40.0" prefHeight="40.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="120.0" AnchorPane.rightAnchor="0.0">
                        <AnchorPane fx:id="styleTopPane" styleClass="style-lower-triangle" maxWidth="40.0" minWidth="40.0" prefWidth="40.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.topAnchor="0.0" />
                        <AnchorPane fx:id="topCommandPlaceholder" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="40.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0"/>
                    </AnchorPane>
                </AnchorPane>
                <AnchorPane fx:id="bottomWrapper" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="80.0">
                    <AnchorPane fx:id="centerPanePlaceholder" maxHeight="95.0" minHeight="95.0" prefHeight="95.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0"/>
                    <AnchorPane fx:id="bottomPane" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="95.0">
                        <SplitPane fx:id="bottomPaneSplit" dividerPositions="0.5" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                            <AnchorPane fx:id="bottomListPane" minWidth="300.0" prefWidth="300.0">
                                <AnchorPane fx:id="listPersonsPlaceholder" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0"/>
                            </AnchorPane>
                            <AnchorPane fx:id="bottomInfoPane" minWidth="400.0">
                                <VBox fx:id="welcomePane" visible="false" alignment="CENTER" fillWidth="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                                    <Label fx:id="welcomeTitle" text="Welcome to HR+"/>
                                    <Pane fx:id="logoWelcome" styleClass="logo-hr" maxWidth="-Infinity" prefHeight="80.0" prefWidth="110.0" VBox.vgrow="NEVER">
                                        <VBox.margin>
                                            <Insets bottom="15.0" left="10.0" top="15.0" />
                                        </VBox.margin>
                                    </Pane>
                                    <Label fx:id="welcomeMadeWith" text="Made with love by"/>
                                    <Label fx:id="welcomeName" text="Yee Chin, Hong Qiang, Xiao Wen, Heng Yeow"/>
                                </VBox>
                                <AnchorPane fx:id="resumePanePlaceholder" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" />
                                <AnchorPane fx:id="infoPanePlaceholder" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0"/>
                            </AnchorPane>
                        </SplitPane>
                        <padding>
                            <Insets left="15.0" right="15.0" bottom="10.0" top="10.0"/>
                        </padding>
                    </AnchorPane>
                </AnchorPane>
                <HBox fx:id="floatParseRealTime" visible="false" opacity="0.0" alignment="CENTER_LEFT" AnchorPane.leftAnchor="172.0" AnchorPane.topAnchor="79.0" minWidth="50.0" cache="true" cacheHint="SPEED">
                    <Pane fx:id="floatParseIcon" styleClass="style-circle" maxHeight="6.0" maxWidth="6.0" minHeight="6.0" minWidth="6.0" prefHeight="6.0" prefWidth="6.0" cache="true" cacheHint="SPEED">
                        <HBox.margin>
                            <Insets right="4.0" />
                        </HBox.margin>
                    </Pane>
                    <Label fx:id="floatParseLabel" text="Enter a command..." cache="true" cacheHint="SPEED"/>
                    <padding>
                        <Insets left="10.0" right="10.0" bottom="5.0" top="5.0"/>
                    </padding>
                </HBox>
            </AnchorPane>
        </Scene>
    </scene>
</fx:root>
```
###### /resources/view/PdfPanel.fxml
``` fxml
<AnchorPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="resumePaneOuter" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
    <AnchorPane fx:id="resumePaneWrapper" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
        <ScrollPane fx:id="resumePane" fitToHeight="true" fitToWidth="true" hbarPolicy="NEVER" pannable="true" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" focusTraversable="true">
            <VBox fx:id="resumePanePages">
                <padding>
                    <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
                </padding>
            </VBox>
        </ScrollPane>
    </AnchorPane>

    <Label fx:id="resumePageLabel" visible="false" AnchorPane.rightAnchor="16.0" AnchorPane.topAnchor="6.0">
        <padding>
            <Insets bottom="3.0" left="10.0" right="10.0" top="3.0" />
        </padding>
    </Label>

    <VBox visible="false" fx:id="resumeLoading" alignment="CENTER" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="10.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
        <Label fx:id="resumeLoadingLabel">
            <VBox.margin>
                <Insets bottom="8.0" />
            </VBox.margin>
        </Label>
        <ProgressBar fx:id="resumeLoadingBar" styleClass="hr-progress-bar" maxWidth="Infinity" progress="0">
            <VBox.margin>
                <Insets bottom="20.0" />
            </VBox.margin>
        </ProgressBar>
        <Button fx:id="resumeCancelButton" styleClass="hr-button" onAction="#cancelResume" mnemonicParsing="false" text="Cancel">
            <padding>
                <Insets bottom="6.0" left="15.0" right="15.0" top="6.0" />
            </padding>
        </Button>
    </VBox>
</AnchorPane>
```
###### /resources/view/PersonListPanel.fxml
``` fxml
<ListView xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="listPersons" opacity="0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED"/>
```
###### /resources/view/ResultDisplay.fxml
``` fxml
<AnchorPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="centerPane" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
    <padding>
        <Insets left="15.0" right="15.0" top="8.0" />
    </padding>
    <TextArea fx:id="commandResult" editable="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED"/>
</AnchorPane>
```
###### /resources/view/HRTheme.css
``` css

/* Fonts */

@font-face {
    src: url("../fonts/Roboto-Thin.ttf");
}

@font-face {
    src: url("../fonts/Roboto-ThinItalic.ttf");
}

@font-face {
    src: url("../fonts/Roboto-Light.ttf");
}

@font-face {
    src: url("../fonts/Roboto-LightItalic.ttf");
}

@font-face {
    src: url("../fonts/Roboto-Regular.ttf");
}

@font-face {
    src: url("../fonts/Roboto-Italic.ttf");
}

@font-face {
    src: url("../fonts/Roboto-Medium.ttf");
}

@font-face {
    src: url("../fonts/Roboto-MediumItalic.ttf");
}

@font-face {
    src: url("../fonts/Roboto-Bold.ttf");
}

@font-face {
    src: url("../fonts/Roboto-BoldItalic.ttf");
}

@font-face {
    src: url("../fonts/Roboto-Black.ttf");
}

@font-face {
    src: url("../fonts/Roboto-BlackItalic.ttf");
}


/* Reset / General */

* {
    -fx-box-border: transparent !important;
}

/* Split bar */

.split-pane > .split-pane-divider {
    -fx-padding: 4;
    -fx-background-color: #E8E8E8;
    -fx-background-insets: 5 3 5 3;
}

/* Scroll bar */

.scroll-bar {
    -fx-background-color: #E8E8E8;
    -fx-background-insets: 0;
    -fx-background-radius: 10;
    -fx-padding: 0;
}

.scroll-bar > .thumb {
    -fx-background-color: #D7D7D7;
    -fx-background-insets: 0;
    -fx-background-radius: 10;
}

.scroll-bar:horizontal .increment-button,
.scroll-bar:horizontal .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 5 0 5 0;
}

.scroll-bar:vertical .increment-button,
.scroll-bar:vertical .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 5 0 5;
}

.scroll-bar .increment-arrow,
.scroll-bar .decrement-arrow {
    -fx-shape: "";
    -fx-padding: 0;
}

.corner {
    -fx-background-color: transparent;
}

/* Dialog pane */

.dialog-pane {
    -fx-background-color: white;
}

.dialog-pane:header .header-panel {
    -fx-background-color: linear-gradient(to right, #42A5F5, #1976D2);
    -fx-background-insets: 0;
}

.dialog-pane:header .header-panel .label {
    -fx-text-fill: white;
    -fx-font-family: "Roboto";
}



/* Root */

#mainWindow {
    -fx-background-color: white;
}

/* Window border */

#bottomWrapper {
    -fx-border-color: #ccc;
    -fx-border-width: 0 1 1 1;
}

#topCommandPlaceholder {
    -fx-border-color: #ccc;
    -fx-border-width: 0 1 0 0;
}



/* Top Wrapper + Pane */

#topPane {
    -fx-background-color: linear-gradient(to right, #42A5F5, #1976D2);
}

#styleTopPane {
    -fx-background-color: white;
}

/* Title Bar */

#topStatusMessage {
    -fx-font-family: "Roboto Light";
    -fx-font-size: 14;
    -fx-opacity: 0.8;
    -fx-text-fill: white;
}

#topStatusFile {
    -fx-background-color: white;
    -fx-background-radius: 8;

    -fx-border-color: white;
    -fx-border-width: 4 10 4 10;
    -fx-border-radius: 8;

    -fx-font-family: "Roboto Light";
    -fx-font-size: 13;
    -fx-text-fill: #616161;
    -fx-text-overrun: leading-ellipsis;
}

/* Controls */

#controlHelpInner, #controlMinimizeInner,
#controlMaximize, #controlClose {
    -fx-background-color: white;
}

#controlHelp, #controlMinimize,
#controlMaximize, #controlClose {
    -fx-opacity: 0.5;
}

#controlHelp:hover, #controlMinimize:hover,
#controlMaximize:hover, #controlClose:hover {
    -fx-opacity: 0.9;
}

/* Command Box */

#topCommand {
    -fx-background-color: white;
}

#commandInput {
    -fx-border-color: white;
    -fx-background-color: #E0E0E0;
    -fx-background-radius: 8;
    -fx-font-family: "Roboto";
    -fx-font-size: 13;
    -fx-text-fill: #616161;
}

#commandInput.command-error {
    -fx-text-fill: #F44336;
}

#commandInput:focused {
    -fx-background-color: #D8D8D8;
}

/* Result Box */

#commandResult {
    -fx-background-color: white;
    -fx-border-color: #BCBCBC;
    -fx-background-radius: 0;
    -fx-font-family: "Roboto";
    -fx-font-size: 13;
}

#commandResult .content {
    -fx-cursor: text;
    -fx-background-color: white;
}

#commandResult:focused {
    -fx-border-color: #42A5F5;
}

#commandResult:focused .content {
    -fx-background-color: #F5F5F5;
}

/* Bottom */

#welcomePane, #bottomPaneSplit, #resumePane {
    -fx-background-color: white;
}

/* List */

#listPersons {
    -fx-background-color: white;
}

#listPersons .list-cell,
#listPersons .list-cell:selected {
    -fx-background-color: transparent;
}

/* Card */

#cardPersonPane {
    -fx-padding: 5 5 5 0;
}

#cardPerson {
    -fx-background-color: white;
    -fx-border-color: white;
    -fx-border-width: 1;
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 12, 0, 0, 0);
}

#cardPerson > * {
    -fx-opacity: 0.5;
}

#listPersons .list-cell:selected #cardPerson > * {
    -fx-opacity: 1;
}

#listPersons .list-cell:selected #cardPerson {
    -fx-border-color: #42A5F5;
}

#listPersons:focused #cardPerson {
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 14, 0, 0, 0);
}

#listPersons:focused .list-cell:selected #cardPerson {
    -fx-effect: dropshadow(gaussian, #64B5F6, 14, 0, 0, 0);
}

#cardPhotoPane {
    -fx-background-color: #E8E8E8;
}

#cardPhotoMask {
    -fx-background-color: white;
}

/* Card Info */

#cardPersonInfo .label,
#listPersons .list-cell:selected #cardPersonInfo .label {
    -fx-text-fill: black;
}

#cardPersonName {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 15;
}

#cardPersonUniversity {
    -fx-font-family: "Roboto Light";
    -fx-font-size: 11;
    -fx-opacity: 0.9;
}

#cardPersonEmail {
    -fx-font-family: "Roboto Light";
    -fx-font-size: 10;
    -fx-opacity: 0.8;
}

#cardPersonContact {
    -fx-font-family: "Roboto Light";
    -fx-font-size: 10;
    -fx-opacity: 0.8;
}

/* Card Rating */

#cardPersonRatingPane .label,
#listPersons .list-cell:selected #cardPersonRatingPane .label {
    -fx-text-fill: black;
}

#cardPersonRating {
    -fx-font-family: "Roboto Light";
    -fx-font-size: 11;
    -fx-opacity: 0.8;
}

#iconRating {
    -fx-background-color: #FFC107;
}

/* Card Status */

#cardPersonStatus {
    -fx-background-color: #8BC34A;
    -fx-background-radius: 50;

    -fx-font-family: "Roboto Medium";
    -fx-font-size: 10;
    -fx-text-fill: white;
}

/* Card Number */

#cardPersonNumberPane .label,
#listPersons .list-cell:selected #cardPersonNumberPane .label {
    -fx-text-fill: white;
}

#cardPersonNumber {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 13;
}

#cardPersonNumber, #styleCardPersonNumber {
    -fx-background-color: #BCBCBC;
}

#listPersons .list-cell:selected #cardPersonNumber,
#listPersons .list-cell:selected #styleCardPersonNumber {
    -fx-background-color: #42A5F5;
}

/* Welcome */

#welcomeTitle {
    -fx-font-family: "Roboto Bold";
    -fx-font-size: 18;
}

#welcomeMadeWith {
    -fx-font-family: "Roboto Light";
    -fx-font-size: 13;
    -fx-opacity: 0.5;
}

#welcomeName {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 13;
    -fx-opacity: 0.8;
}



/* Resume */

#resumePanePages {
    -fx-background-color: white;
}

.pdf-page {
    -fx-background-color: white;
    -fx-border-width: 1;
    -fx-border-color: #ddd;
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 12, 0, 0, 2);
}

#resumePane:focused .pdf-page {
    -fx-border-color: #aaa;
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.35), 18, 0, 0, 3);
}

#resumePageLabel {
    -fx-background-color: #2196F3;
    -fx-text-fill: white;
    -fx-background-radius: 100;
    -fx-opacity: 0.6;
}

#resumeLoading {
    -fx-background-color: white;
}

#resumePageLabel, #resumeLoadingLabel {
    -fx-font-family: "Roboto";
}



/* Info */

#infoMain, #infoMainPart, #infoMainPane, #infoSplitPane, #infoSplitMainPane {
    -fx-background-color: white;
}

#infoMain {
    -fx-border-width: 1 0 1 1;
    -fx-border-color: white;
}

#infoMainPane:focused #infoMain,
#infoSplitMainPane:focused #infoMain {
    -fx-border-color: #90CAF9;
}

#infoMainName {
    -fx-font-family: "Roboto Black";
    -fx-font-size: 20;
    -fx-text-fill: #64b5f6;
}

#infoMainUniversity, #infoMainMajorYear {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 11;
}

/* Info CGPA */

#infoMainCgpaLabel {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 11;
}

#infoMainCgpa {
    -fx-background-color: #64B5F6;
    -fx-background-radius: 100;

    -fx-font-family: "Roboto Bold";
    -fx-font-size: 13;
    -fx-text-fill: white;
}

/* Info Contact */

#iconEmail, #iconAddress, #iconPhone {
    -fx-background-color: #BDBDBD;
}

#infoMainEmail, #infoMainAddress, #infoMainPhone {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 11;
}

/* Info Interview */

#infoMainPositionLabel, #infoMainStatusLabel {
    -fx-font-family: "Roboto Light";
    -fx-font-size: 11;
}

#infoMainPosition, #infoMainStatus {
    -fx-font-family: "Roboto Bold";
    -fx-font-size: 12;
}

#infoMainStatus {
    -fx-text-fill: #8bc34a;
}

#infoMainInterviewDateCalendar {
    -fx-border-color: #B4B4B4;
    -fx-border-radius: 10;
}

#infoMainInterviewMonth {
    -fx-background-color: #64B5F6;
    -fx-background-radius: 9 9 0 0;

    -fx-font-family: "Roboto Bold";
    -fx-font-size: 12;
    -fx-text-fill: white;
}

#infoMainInterviewDate {
    -fx-font-family: "Roboto Bold";
    -fx-font-size: 32;
}

#infoMainInterviewDay {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 11;
}

#infoMainInterviewTime {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 11;
}

/* Info comments */

#infoMainCommentsLabel {
    -fx-font-family: "Roboto Light";
    -fx-font-size: 12;
}

#infoMainComments {
    -fx-background-color: #E8E8E8;
    -fx-background-radius: 8;

    -fx-font-family: "Roboto Medium";
    -fx-font-size: 12;
}

/* Side graph */

#infoSideGraph {
    -fx-background-color: white;
}

#infoRatingTechnicalLabel, #infoRatingCommunicationLabel,
#infoRatingProblemSolvingLabel, #infoRatingExperienceLabel,
#infoRatingOverallLabel {
    -fx-font-family: "Roboto Medium";
    -fx-font-size: 11;
}

#infoRatingTechnicalValue, #infoRatingCommunicationValue,
#infoRatingProblemSolvingValue, #infoRatingExperienceValue,
#infoRatingOverallValue {
    -fx-font-family: "Roboto";
    -fx-font-size: 11;
}

.hr-progress-bar > .bar {
    -fx-background-color: #2196F3;
    -fx-background-insets: 0;
    -fx-background-radius: 0;
    -fx-padding: 2;
}

.hr-progress-bar > .track {
    -fx-background-color: #BBDEFB;
    -fx-background-insets: 0;
    -fx-background-radius: 0;
}

/* Side button */

.hr-button {
    -fx-background-color: #42A5F5;
    -fx-background-insets: 0;

    -fx-border-radius: 0;
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 0, 3);

    -fx-font-family: "Roboto Medium";
    -fx-font-size: 13;
    -fx-text-fill: white;
    -fx-cursor: hand;
}

.hr-button:focused,
.hr-button:hover {
    -fx-background-color: #1976D2;
}

.hr-button:pressed {
    -fx-background-color: #0D47A1;
}

.hr-button:disabled {
    -fx-background-color: #616161;
}



/* Logos */

#logoTop {
    -fx-background-color: white;
}

#logoWelcome {
    -fx-background-color: linear-gradient(to right, #42A5F5, #1976D2);
}



/* Floating */

#floatParseIcon {
    -fx-background-color: #999;
}

#floatParseLabel {
    -fx-text-fill: #999;
    -fx-font-family: "Roboto";
    -fx-font-size: 13;
}

#floatParseRealTime {
    -fx-background-color: rgba(248, 248, 248, 0.95);
    -fx-border-width: 1;
    -fx-border-color: #ddd;
}

#floatParseRealTime.parse-valid #floatParseIcon {
    -fx-background-color: "#64B5F6";
}

#floatParseRealTime.parse-invalid #floatParseIcon {
    -fx-background-color: "#E57373";
}

#floatParseRealTime.parse-valid #floatParseLabel {
    -fx-text-fill: "#64B5F6";
}

#floatParseRealTime.parse-invalid #floatParseLabel {
    -fx-text-fill: "#E57373";
}



/* SVG Graphics */

.icon-help {
    -fx-shape: "M4.4,7.6c0-0.2,0-0.3,0-0.4c0-0.5,0.1-0.9,0.2-1.2c0.1-0.3,0.3-0.5,0.5-0.8C5.2,5,5.5,4.7,5.9,4.4C6.3,4,6.6,3.7,6.7,3.5C6.9,3.3,6.9,3,6.9,2.8c0-0.5-0.2-0.9-0.5-1.2C6,1.2,5.6,1,5,1C4.5,1,4.1,1.2,3.7,1.5S3.2,2.3,3.1,3L1.8,2.8c0.1-0.9,0.4-1.6,1-2.1S4.1,0,5,0c1,0,1.7,0.3,2.3,0.8s0.9,1.2,0.9,1.9c0,0.4-0.1,0.8-0.3,1.2C7.7,4.2,7.3,4.7,6.7,5.2C6.3,5.5,6,5.8,5.9,6C5.8,6.1,5.7,6.3,5.7,6.5c-0.1,0.2-0.1,0.6-0.1,1H4.4z M4.3,10V8.6h1.4V10H4.3z";
}

.icon-expand {
    -fx-shape: "M0,0v10h10V0H0z M9,9H1V1h8V9z";
}

.icon-close {
    -fx-shape: "M5.9,5L10,9.1L9.1,10L5,5.9L0.9,10L0,9.1L4.1,5L0,0.9L0.9,0L5,4.1L9.1,0L10,0.9L5.9,5z";
}

.icon-star {
    -fx-shape: "M4,0.2l0.9,2.9H8L5.5,4.9l1,2.9L4,6L1.5,7.8l1-2.9L0,3.1h3.1L4,0.2z";
}

.icon-email {
    -fx-shape: "M9.1,2.6L10,1.9v6.4c0,0.3-0.2,0.5-0.5,0.5H0.4C0.2,8.8,0,8.6,0,8.3V1.9l0.9,0.7l3.8,2.8c0.1,0,0.2,0.1,0.3,0.1s0.2,0,0.3-0.1L9.1,2.6z M5,4.4l4.4-3.2H0.6L5,4.4z";
}

.icon-address {
    -fx-shape: "M5,0C3.3,0,1.9,1.4,1.9,3.1S3.4,7.2,5,10c1.6-2.8,3.1-5.1,3.1-6.9S6.7,0,5,0z M5,4.4c-0.7,0-1.2-0.6-1.2-1.2S4.3,1.9,5,1.9s1.2,0.6,1.2,1.2S5.7,4.4,5,4.4z";
}

.icon-phone {
    -fx-shape: "M10,7.9c0,0.1,0,0.3-0.1,0.5S9.8,8.8,9.8,8.9C9.7,9.1,9.4,9.4,8.9,9.6C8.5,9.9,8,10,7.6,10c-0.1,0-0.3,0-0.4,0c-0.1,0-0.3,0-0.4-0.1c-0.2,0-0.3-0.1-0.3-0.1c-0.1,0-0.2-0.1-0.4-0.1S5.8,9.5,5.7,9.5C5.3,9.3,4.9,9.1,4.5,8.9C3.9,8.5,3.3,8,2.6,7.4S1.5,6.1,1.1,5.5C0.9,5.1,0.7,4.7,0.5,4.3c0,0-0.1-0.2-0.1-0.3S0.2,3.6,0.2,3.5c0-0.1-0.1-0.2-0.1-0.3S0,2.9,0,2.8c0-0.1,0-0.2,0-0.4C0,2,0.1,1.5,0.4,1.1c0.3-0.5,0.5-0.8,0.8-0.9c0.1-0.1,0.3-0.1,0.5-0.1S2,0,2.1,0c0.1,0,0.1,0,0.1,0c0.1,0,0.2,0.2,0.4,0.5c0.1,0.1,0.1,0.2,0.2,0.4C2.9,1.1,3,1.3,3.1,1.4s0.1,0.3,0.2,0.4c0,0,0.1,0.1,0.1,0.2c0.1,0.1,0.1,0.2,0.2,0.3c0,0.1,0,0.1,0,0.2c0,0.1-0.1,0.2-0.2,0.4C3.3,2.9,3.2,3,3,3.2C2.8,3.3,2.7,3.4,2.6,3.5C2.4,3.7,2.4,3.8,2.4,3.9c0,0,0,0.1,0,0.2c0,0.1,0,0.1,0.1,0.1c0,0,0,0.1,0.1,0.2c0,0.1,0.1,0.1,0.1,0.1C3,5.1,3.4,5.7,3.9,6.1c0.5,0.5,1,0.9,1.7,1.2c0,0,0.1,0,0.1,0.1c0.1,0,0.1,0.1,0.2,0.1c0,0,0.1,0,0.1,0.1c0.1,0,0.1,0,0.2,0c0.1,0,0.2-0.1,0.3-0.2S6.7,7.2,6.8,7s0.2-0.3,0.4-0.4c0.1-0.1,0.3-0.2,0.4-0.2c0.1,0,0.1,0,0.2,0c0.1,0,0.2,0.1,0.3,0.2c0.1,0.1,0.2,0.1,0.2,0.1c0.1,0.1,0.2,0.1,0.4,0.2s0.3,0.2,0.5,0.2s0.3,0.2,0.4,0.2C9.8,7.5,10,7.7,10,7.7C10,7.8,10,7.8,10,7.9z";
}

.style-lower-triangle {
    -fx-shape: "M1,0v1H0L1,0z";
}

.style-upper-triangle {
    -fx-shape: "M0,1V0h1L0,1z";
}

.style-circle {
    -fx-shape: "M2,1c0,0.6-0.4,1-1,1S0,1.6,0,1s0.4-1,1-1S2,0.4,2,1z";
}

.style-mask-circle {
    -fx-shape: "M1,2H0V1C0,1.6,0.4,2,1,2z M1,2h1V1C2,1.6,1.6,2,1,2z M1,0H0v1C0,0.4,0.4,0,1,0z M1,0c0.6,0,1,0.4,1,1V0H1z";
}

.logo-hr {
    -fx-shape: "M106.5,0.3v181.5H86.2v-80.6H20.3v80.6H0V0.3h20.3v80.6h65.9V0.3H106.5z M124,2.1v21.4c5.3-2.2,11.1-3.4,17.2-3.4c25,0,45.3,20.3,45.3,45.3c0,6-1.2,11.8-3.3,17C180,84.8,175.3,87,169,87c-14.2,0-19.5-12-19.7-12.6c-0.7-1.8-2.8-2.7-4.6-1.9c-1.8,0.7-2.7,2.8-1.9,4.6c0.3,0.7,7.1,17,26.2,17c2.9,0,5.6-0.4,8-1c-8.3,10.6-21.2,17.5-35.7,17.5c-6.1,0-11.9-1.2-17.2-3.4v21.4c5.5,1.5,11.3,2.3,17.2,2.3c8,0,15.7-1.4,22.8-4.1c5.7,7.6,16.4,25.3,20.7,55.1h20.5c-4.2-33.1-15.6-54.2-23.4-65.2c15.2-12,25-30.6,25-51.4c0-33.9-25.9-61.9-58.9-65.2h-13.4C131,0.5,127.4,1.1,124,2.1z M164,59.5c-3.9,0-7.1-3.2-7.1-7.1c0-3.9,3.2-7.1,7.1-7.1s7.1,3.2,7.1,7.1C171.1,56.4,167.9,59.5,164,59.5z M256,112.3h-15.2V97.1h-15.2v15.2h-15.2v15.2h15.2v15.2h15.2v-15.2H256V112.3z";
}
```
###### /resources/view/RadarChart.fxml
``` fxml
<VBox fx:id="radarChart" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" alignment="CENTER" fillWidth="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED"/>

```
###### /resources/view/PersonListCard.fxml
``` fxml
<AnchorPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="cardPersonPane" opacity="0.0" maxHeight="100.0" minHeight="100.0" prefHeight="100.0" VBox.vgrow="ALWAYS" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
    <AnchorPane fx:id="cardPerson" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
        <AnchorPane fx:id="cardPhotoPane" maxHeight="70.0" maxWidth="70.0" minHeight="70.0" minWidth="70.0" prefHeight="70.0" prefWidth="70.0" AnchorPane.bottomAnchor="10.0" AnchorPane.leftAnchor="12.0" AnchorPane.topAnchor="10.0" cache="true" cacheHint="SPEED">
            <ImageView fx:id="cardPhoto" fitHeight="70.0" fitWidth="70.0" pickOnBounds="true" AnchorPane.leftAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED"/>
            <Pane fx:id="cardPhotoMask" styleClass="style-mask-circle" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED"/>
        </AnchorPane>
        <VBox fx:id="cardPersonInfo" alignment="CENTER_LEFT" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="88.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" cache="true" cacheHint="SPEED">
            <Label fx:id="cardPersonName" cache="true" cacheHint="SPEED">
                <VBox.margin>
                    <Insets bottom="-2.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="cardPersonUniversity" cache="true" cacheHint="SPEED">
                <VBox.margin>
                    <Insets bottom="3.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="cardPersonEmail" cache="true" cacheHint="SPEED">
                <VBox.margin>
                    <Insets bottom="-2.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="cardPersonContact" cache="true" cacheHint="SPEED">
            </Label>
        </VBox>
        <HBox fx:id="cardPersonRatingPane" alignment="CENTER_RIGHT" AnchorPane.rightAnchor="8.0" AnchorPane.topAnchor="8.0" cache="true" cacheHint="SPEED">
            <Label fx:id="cardPersonRating" cache="true" cacheHint="SPEED">
                <HBox.margin>
                    <Insets bottom="-1.0" right="3.0" top="1.0" />
                </HBox.margin>
            </Label>
            <Pane fx:id="iconRating" styleClass="icon-star" maxHeight="12.0" maxWidth="12.0" minHeight="12.0" minWidth="12.0" prefHeight="12.0" prefWidth="12.0" cache="true" cacheHint="SPEED"/>
        </HBox>
        <Label fx:id="cardPersonStatus" AnchorPane.bottomAnchor="8.0" AnchorPane.rightAnchor="8.0" cache="true" cacheHint="SPEED">
            <padding>
                <Insets bottom="2.0" left="8.0" right="8.0" top="2.0" />
            </padding>
        </Label>
        <HBox fx:id="cardPersonNumberPane" cache="true" cacheHint="SPEED">
            <Label fx:id="cardPersonNumber" maxHeight="20.0" minHeight="20.0" prefHeight="20.0" cache="true" cacheHint="SPEED">
                <padding>
                    <Insets bottom="2.0" left="5.0" top="2.0" />
                </padding>
            </Label>
            <Pane fx:id="styleCardPersonNumber" styleClass="style-upper-triangle" maxHeight="20.0" maxWidth="10.0" minHeight="20.0" minWidth="10.0" prefHeight="20.0" prefWidth="10.0" cache="true" cacheHint="SPEED"/>
        </HBox>
    </AnchorPane>
    <padding>
        <Insets left="10.0" right="10.0" top="10.0" />
    </padding>
</AnchorPane>

```
###### /resources/view/CommandBox.fxml
``` fxml
<AnchorPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="topCommand" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
    <TextField fx:id="commandInput" onAction="#handleCommandInputChanged" onKeyPressed="#handleKeyPress" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" promptText="Enter command here..." />
    <padding>
        <Insets left="8.0" right="15.0" top="8.0" />
    </padding>
</AnchorPane>
```
