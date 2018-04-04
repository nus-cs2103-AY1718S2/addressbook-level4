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
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);

        this.person = person;
        this.index = displayedIndex - 1;
        updatePersonCard();

        registerAsAnEventHandler(this);
    }

    /**
     * Update the person with the latest information
     * It can be updated from address book change or selection change
     */
    private void updatePersonCard() {
        cardPhotoMask.widthProperty().addListener((observable, oldValue, newValue) -> resizePhoto());
        cardPhotoMask.heightProperty().addListener((observable, oldValue, newValue) -> resizePhoto());

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
     * Resize the photo to cover the ImageView
     */
    private void resizePhoto() {
        cardPhoto.setFitWidth(cardPhotoMask.getWidth());
        cardPhoto.setFitHeight(cardPhotoMask.getHeight());
        Image image = cardPhoto.getImage();

        if (image != null) {
            double aspectRatio = cardPhotoMask.getWidth() / cardPhotoMask.getHeight();
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();
            double fitSize = Math.min(imageWidth, imageHeight);
            double actualSize = fitSize * aspectRatio;

            if (imageWidth > imageHeight) {
                double x = (imageWidth - actualSize) / 2.0;
                cardPhoto.setViewport(new Rectangle2D(x, 0, actualSize, fitSize));
            } else {
                double y = (imageHeight - actualSize) / 2.0;
                cardPhoto.setViewport(new Rectangle2D(0, y, fitSize, actualSize));
            }
        }
    }

    @Subscribe
    private void handlePersonChangedEvent(PersonChangedEvent event) {
        ListChangeListener.Change<? extends Person> changes = event.getPersonChanged();
        if (person != null) {
            while (changes.next()) {
                for (int i = changes.getFrom(); i < changes.getTo(); i++) {
                    if (i == index) {
                        person = changes.getList().get(i);
                        updatePersonCard();
                    }
                }
            }
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

    private void setupPersonChangedEvent(ObservableList<Person> personList) {
        personList.addListener((ListChangeListener<Person>) c -> {
            raise(new PersonChangedEvent(c));
        });
    }

    @Subscribe
    private void handleShowPanelRequestEvent(ShowPanelRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        // Hide all panel
        infoPanePlaceholder.setVisible(false);
        resumePanePlaceholder.setVisible(false);

        // Show relevant panel
        if (event.getRequestedPanel().equals(PdfPanel.PANEL_NAME)) {
            pdfPanel.load();
            resumePanePlaceholder.setVisible(true);
        } else if (event.getRequestedPanel().equals(InfoPanel.PANEL_NAME)) {
            infoPanePlaceholder.setVisible(true);
            pdfPanel.unload();
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

        resumePane.widthProperty().addListener((observable, oldValue, newValue) -> handleResizeEvent());
        resumePane.vvalueProperty().addListener((observable, oldValue, newValue) -> handleScrollEvent());

        registerAsAnEventHandler(this);
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
        resumeLoadingLabel.setText("Opening file: " + filePath);
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
        selectedPerson = event.getNewSelection().getPerson();
    }
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
    public final String value;

    /**
     * Constructs a {@code ProfileImage}.
     *
     * @param fileName A valid fileName.
     */
    public ProfileImage(String fileName) {
        if (isNull(fileName)) {
            this.value = null;
        } else {
            checkArgument(isValidFile(fileName), MESSAGE_IMAGE_CONSTRAINTS);
            this.value = fileName;
        }
    }

    /**
     * Return the loaded {@code Image} of the person's Profile Image,
     * resized to 100px for performance issue
     * @return the image in {@code Image}
     */
    public Image getImage() {
        try {
            return new Image(getFile().toURI().toString(),
                    100d, 0d, true, true, false);
        } catch (Exception e) {
            return null;
        }
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

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // Short circuit if same object
                || (other instanceof ProfileImage // instanceof handles nulls
                && Objects.equals(this.value, ((ProfileImage) other).value)); // State check
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

    private final Change<? extends Person> personChanged;

    public PersonChangedEvent(Change<? extends Person> personChanged) {
        this.personChanged = personChanged;
    }

    public Change<? extends Person> getPersonChanged() {
        return personChanged;
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
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

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
                scheduledPerson.getName(), dateTime.toString()));
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
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();

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
<AnchorPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="infoPaneWrapper" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
    <ScrollPane fx:id="infoMainPane" fitToHeight="true" fitToWidth="true" hbarPolicy="NEVER" pannable="true" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" focusTraversable="true" />
    <SplitPane fx:id="infoSplitPane" dividerPositions="0.5" visible="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
        <ScrollPane fx:id="infoSplitMainPane" fitToHeight="true" fitToWidth="true" hbarPolicy="NEVER" pannable="true" focusTraversable="true">
            <VBox fx:id="infoMain">
                <AnchorPane fx:id="infoMainTop">
                    <VBox fx:id="infoMainTopLeft" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                        <Label fx:id="infoMainName"></Label>
                        <Label fx:id="infoMainUniversity">
                            <VBox.margin>
                                <Insets bottom="-2.0" />
                            </VBox.margin>
                        </Label>
                        <Label fx:id="infoMainMajorYear">
                        </Label>
                        <padding>
                            <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                        </padding>
                    </VBox>
                    <HBox fx:id="infoMainTopRight" alignment="CENTER_RIGHT" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                        <Label fx:id="infoMainCgpaLabel" text="cGPA">
                            <HBox.margin>
                                <Insets right="5.0" />
                            </HBox.margin>
                        </Label>
                        <Label fx:id="infoMainCgpa" alignment="CENTER" maxHeight="36.0" maxWidth="36.0" minHeight="36.0" minWidth="36.0" prefHeight="36.0" prefWidth="36.0">
                        </Label>
                    </HBox>
                    <VBox.margin>
                        <Insets bottom="10.0" />
                    </VBox.margin>
                </AnchorPane>
                <VBox fx:id="infoMainContact">
                    <HBox fx:id="infoMainContactEmailPane" alignment="CENTER_LEFT">
                        <VBox fx:id="iconEmailOuter" alignment="CENTER" maxHeight="15.0" maxWidth="15.0" minHeight="15.0" minWidth="15.0" prefHeight="15.0" prefWidth="15.0">
                            <Pane fx:id="iconEmail" styleClass="icon-email" maxHeight="11.4" maxWidth="15.0" minHeight="11.4" minWidth="15.0" prefHeight="11.4" prefWidth="15.0"/>
                            <HBox.margin>
                                <Insets right="5.0" />
                            </HBox.margin>
                        </VBox>
                        <Label fx:id="infoMainEmail"></Label>
                        <VBox.margin>
                            <Insets bottom="6.0" />
                        </VBox.margin>
                    </HBox>
                    <HBox fx:id="infoMainContactAddressPane" alignment="CENTER_LEFT">
                        <VBox fx:id="iconAddressOuter" alignment="CENTER" maxHeight="15.0" maxWidth="15.0" minHeight="15.0" minWidth="15.0" prefHeight="15.0" prefWidth="15.0">
                            <Pane fx:id="iconAddress" styleClass="icon-address" maxHeight="15.0" maxWidth="9.33" minHeight="15.0" minWidth="9.33" prefHeight="15.0" prefWidth="9.33"/>
                            <HBox.margin>
                                <Insets right="5.0" />
                            </HBox.margin>
                        </VBox>
                        <Label fx:id="infoMainAddress"></Label>
                        <VBox.margin>
                            <Insets bottom="6.0" />
                        </VBox.margin>
                    </HBox>
                    <HBox fx:id="infoMainContactPhonePane" alignment="CENTER_LEFT">
                        <VBox fx:id="iconPhoneOuter" alignment="CENTER" maxHeight="15.0" maxWidth="15.0" minHeight="15.0" minWidth="15.0" prefHeight="15.0" prefWidth="15.0">
                            <Pane fx:id="iconPhone" styleClass="icon-phone" maxHeight="15.0" maxWidth="15.0" minHeight="15.0" minWidth="15.0" prefHeight="15.0" prefWidth="15.0"/>
                            <HBox.margin>
                                <Insets right="5.0" />
                            </HBox.margin>
                        </VBox>
                        <Label fx:id="infoMainPhone"></Label>
                    </HBox>
                    <padding>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                    </padding>
                    <VBox.margin>
                        <Insets bottom="5.0" />
                    </VBox.margin>
                </VBox>
                <HBox fx:id="infoMainInterview">
                    <GridPane fx:id="infoMainInterviewDetails" alignment="CENTER" HBox.hgrow="ALWAYS">
                        <columnConstraints>
                            <ColumnConstraints fillWidth="false" hgrow="NEVER" />
                            <ColumnConstraints hgrow="ALWAYS" />
                        </columnConstraints>
                        <rowConstraints>
                            <RowConstraints fillHeight="false" vgrow="NEVER" />
                            <RowConstraints fillHeight="false" vgrow="NEVER" />
                        </rowConstraints>
                        <Label fx:id="infoMainPositionLabel" minWidth="-Infinity" text="Position">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                        </Label>
                        <Label fx:id="infoMainStatusLabel" minWidth="-Infinity" text="Status" GridPane.rowIndex="1">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                        </Label>
                        <Label fx:id="infoMainPosition" GridPane.columnIndex="1"></Label>
                        <Label fx:id="infoMainStatus" GridPane.columnIndex="1" GridPane.rowIndex="1"></Label>
                        <padding>
                            <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                        </padding>
                    </GridPane>
                    <VBox fx:id="infoMainInterviewDatePane" alignment="CENTER" minWidth="-Infinity">
                        <VBox fx:id="infoMainInterviewDateCalendar" minWidth="65.0">
                            <Label fx:id="infoMainInterviewMonth" alignment="CENTER" maxWidth="Infinity" VBox.vgrow="ALWAYS">
                                <VBox.margin>
                                    <Insets />
                                </VBox.margin>
                                <padding>
                                    <Insets bottom="2.0" left="8.0" right="8.0" top="2.0" />
                                </padding>
                            </Label>
                            <Label fx:id="infoMainInterviewDate" alignment="CENTER" maxWidth="Infinity">
                                <VBox.margin>
                                    <Insets bottom="-5.0" />
                                </VBox.margin>
                            </Label>
                            <Label fx:id="infoMainInterviewDay" alignment="CENTER" maxWidth="Infinity">
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
                        <Label fx:id="infoMainInterviewTime" alignment="CENTER" maxWidth="Infinity"></Label>
                    </VBox>
                </HBox>
                <VBox fx:id="infoMainCommentsPane">
                    <Label fx:id="infoMainCommentsLabel" text="Comments">
                        <VBox.margin>
                            <Insets bottom="5.0" left="5.0" />
                        </VBox.margin>
                    </Label>
                    <Label fx:id="infoMainComments" alignment="TOP_LEFT" maxWidth="Infinity" minHeight="60.0" wrapText="true">
                        <padding>
                            <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
                        </padding>
                    </Label>
                    <VBox.margin>
                        <Insets bottom="20.0" />
                    </VBox.margin>
                </VBox>
                <AnchorPane fx:id="infoMainRatings" />
                <padding>
                    <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
                </padding>
            </VBox>
        </ScrollPane>
        <AnchorPane fx:id="infoSplitSidePane" maxWidth="300.0" minWidth="250.0">
            <padding>
                <Insets left="16.0" right="8.0" top="8.0" />
            </padding>
            <VBox fx:id="infoSplitRatings" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                <VBox fx:id="infoSplitSide" alignment="TOP_CENTER">
                    <Pane fx:id="infoSideGraph" maxHeight="150.0" minHeight="150.0" prefHeight="150.0"/>
                    <GridPane fx:id="infoSideRatingDetails">
                        <columnConstraints>
                            <ColumnConstraints hgrow="NEVER" />
                            <ColumnConstraints hgrow="ALWAYS" />
                        </columnConstraints>
                        <rowConstraints>
                            <RowConstraints vgrow="NEVER" />
                            <RowConstraints vgrow="NEVER" />
                            <RowConstraints vgrow="NEVER" />
                            <RowConstraints vgrow="NEVER" />
                        </rowConstraints>
                        <Label fx:id="infoRatingTechnicalLabel" text="Technical" GridPane.halignment="RIGHT">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                            <padding>
                                <Insets bottom="5.0" />
                            </padding>
                        </Label>
                        <ProgressBar fx:id="infoRatingTechnical" styleClass="hr-progress-bar" focusTraversable="false" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" />
                        <Label fx:id="infoRatingCommunicationLabel" text="Communication" GridPane.halignment="RIGHT" GridPane.rowIndex="1">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                            <padding>
                                <Insets bottom="5.0" />
                            </padding>
                        </Label>
                        <ProgressBar fx:id="infoRatingCommunication" styleClass="hr-progress-bar" focusTraversable="false" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" GridPane.rowIndex="1" />
                        <Label fx:id="infoRatingProblemSolvingLabel" text="Problem Solving" GridPane.halignment="RIGHT" GridPane.rowIndex="2">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                            <padding>
                                <Insets bottom="5.0" />
                            </padding>
                        </Label>
                        <ProgressBar fx:id="infoRatingProblemSolving" styleClass="hr-progress-bar" focusTraversable="false" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" GridPane.rowIndex="2" />
                        <Label fx:id="infoRatingExperienceLabel" text="Experience" GridPane.halignment="RIGHT" GridPane.rowIndex="3">
                            <GridPane.margin>
                                <Insets right="5.0" />
                            </GridPane.margin>
                        </Label>
                        <ProgressBar fx:id="infoRatingExperience" styleClass="hr-progress-bar" focusTraversable="false" maxWidth="Infinity" progress="0.0" GridPane.columnIndex="1" GridPane.rowIndex="3" />
                        <padding>
                            <Insets bottom="8.0" top="8.0" />
                        </padding>
                    </GridPane>
                </VBox>
                <VBox fx:id="infoSideButtons" alignment="BOTTOM_CENTER" VBox.vgrow="ALWAYS">
                    <Button fx:id="infoSideButtonResume" onAction="#showResume" maxWidth="Infinity" mnemonicParsing="false" text="View Resume">
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
<HBox xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="topTitle" alignment="CENTER_RIGHT" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
    <Label fx:id="topStatusMessage" maxWidth="Infinity" HBox.hgrow="SOMETIMES"/>
    <Label fx:id="topStatusFile" HBox.hgrow="SOMETIMES"/>
    <HBox fx:id="topControl" minWidth="-Infinity">
        <Pane fx:id="controlHelp" onMouseClicked="#handleHelp" layoutX="21.0" layoutY="21.0" maxHeight="18.0" maxWidth="18.0" minHeight="18.0" minWidth="18.0" prefHeight="18.0" prefWidth="18.0" HBox.hgrow="NEVER">
            <Pane fx:id="controlHelpInner" styleClass="icon-help" layoutX="3.29" maxHeight="18.0" maxWidth="11.42" minHeight="18.0" minWidth="11.42" prefHeight="18.0" prefWidth="11.42" />
            <HBox.margin>
                <Insets right="20.0" />
            </HBox.margin>
        </Pane>
        <Pane fx:id="controlMinimize" onMouseClicked="#handleMinimize" maxHeight="18.0" maxWidth="18.0" minHeight="18.0" minWidth="18.0" prefHeight="18.0" prefWidth="18.0" HBox.hgrow="NEVER">
            <HBox.margin>
                <Insets right="10.0" />
            </HBox.margin>
            <Pane fx:id="controlMinimizeInner" layoutY="16.2" maxHeight="1.8" maxWidth="18.0" minHeight="1.8" minWidth="18.0" prefHeight="1.8" prefWidth="18.0" />
        </Pane>
        <Pane fx:id="controlMaximize" styleClass="icon-expand" onMouseClicked="#handleMaximize" maxHeight="18.0" maxWidth="18.0" minHeight="18.0" minWidth="18.0" prefHeight="18.0" prefWidth="18.0" HBox.hgrow="NEVER">
            <HBox.margin>
                <Insets right="10.0" />
            </HBox.margin>
        </Pane>
        <Pane fx:id="controlClose" styleClass="icon-close" onMouseClicked="#handleExit" maxHeight="18.0" maxWidth="18.0" minHeight="18.0" minWidth="18.0" prefHeight="18.0" prefWidth="18.0" HBox.hgrow="NEVER" />
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
                                <VBox fx:id="welcomePane" alignment="CENTER" fillWidth="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                                    <Label fx:id="welcomeTitle" text="Welcome to HR+"/>
                                    <Pane fx:id="logoWelcome" styleClass="logo-hr" maxWidth="-Infinity" prefHeight="80.0" prefWidth="120.0" VBox.vgrow="NEVER">
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

    <VBox visible="false" fx:id="resumeLoading" alignment="CENTER" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
        <Label fx:id="resumeLoadingLabel">
            <VBox.margin>
                <Insets bottom="5.0" />
            </VBox.margin>
        </Label>
        <ProgressBar fx:id="resumeLoadingBar" styleClass="hr-progress-bar" maxWidth="Infinity" progress="0.5" />
    </VBox>
</AnchorPane>
```
###### /resources/view/PersonListPanel.fxml
``` fxml
<ListView xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="listPersons" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" />
```
###### /resources/view/ResultDisplay.fxml
``` fxml
<AnchorPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="centerPane" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
    <padding>
        <Insets left="15.0" right="15.0" top="8.0" />
    </padding>
    <TextArea fx:id="commandResult" editable="false" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0"/>
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

#bottomPaneSplit > .split-pane-divider {
    -fx-background-color: transparent;
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
    -fx-border-color: #ccc;
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 15, 0, 0, 3);
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



/* Info */

#infoMain, #infoMainPane, #infoSplitPane, #infoSplitMainPane {
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
    -fx-background-color: #eee;
}

#infoRatingTechnicalLabel, #infoRatingCommunicationLabel,
#infoRatingProblemSolvingLabel, #infoRatingExperienceLabel {
    -fx-font-family: "Roboto Medium";
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

#infoSideButtonResume {
    -fx-background-color: #42A5F5;
    -fx-background-insets: 0;

    -fx-border-radius: 0;
    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 0, 3);

    -fx-font-family: "Roboto Medium";
    -fx-font-size: 13;
    -fx-text-fill: white;
    -fx-cursor: hand;
}

#infoSideButtonResume:focused,
#infoSideButtonResume:hover {
    -fx-background-color: #1976D2;
}

#infoSideButtonResume:pressed {
    -fx-background-color: #0D47A1;
}



/* Logos */

#logoTop {
    -fx-background-color: white;
}

#logoWelcome {
    -fx-background-color: linear-gradient(to right, #42A5F5, #1976D2);
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
###### /resources/view/PersonListCard.fxml
``` fxml
<AnchorPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1" fx:id="cardPersonPane" maxHeight="100.0" minHeight="100.0" prefHeight="100.0" VBox.vgrow="ALWAYS" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
    <AnchorPane fx:id="cardPerson" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
        <AnchorPane fx:id="cardPhotoPane" maxHeight="70.0" maxWidth="70.0" minHeight="70.0" minWidth="70.0" prefHeight="70.0" prefWidth="70.0" AnchorPane.bottomAnchor="10.0" AnchorPane.leftAnchor="12.0" AnchorPane.topAnchor="10.0">
            <ImageView fx:id="cardPhoto" fitHeight="70.0" fitWidth="70.0" pickOnBounds="true" preserveRatio="true" AnchorPane.leftAnchor="0.0" AnchorPane.topAnchor="0.0"/>
            <Pane fx:id="cardPhotoMask" styleClass="style-mask-circle" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" />
        </AnchorPane>
        <VBox fx:id="cardPersonInfo" alignment="CENTER_LEFT" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="88.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
            <Label fx:id="cardPersonName">
                <VBox.margin>
                    <Insets bottom="-2.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="cardPersonUniversity">
                <VBox.margin>
                    <Insets bottom="3.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="cardPersonEmail">
                <VBox.margin>
                    <Insets bottom="-2.0" />
                </VBox.margin>
            </Label>
            <Label fx:id="cardPersonContact">
            </Label>
        </VBox>
        <HBox fx:id="cardPersonRatingPane" alignment="CENTER_RIGHT" AnchorPane.rightAnchor="8.0" AnchorPane.topAnchor="8.0">
            <Label fx:id="cardPersonRating">
                <HBox.margin>
                    <Insets bottom="-1.0" right="3.0" top="1.0" />
                </HBox.margin>
            </Label>
            <Pane fx:id="iconRating" styleClass="icon-star" maxHeight="12.0" maxWidth="12.0" minHeight="12.0" minWidth="12.0" prefHeight="12.0" prefWidth="12.0"/>
        </HBox>
        <Label fx:id="cardPersonStatus" AnchorPane.bottomAnchor="8.0" AnchorPane.rightAnchor="8.0">
            <padding>
                <Insets bottom="2.0" left="8.0" right="8.0" top="2.0" />
            </padding>
        </Label>
        <HBox fx:id="cardPersonNumberPane">
            <Label fx:id="cardPersonNumber" maxHeight="20.0" minHeight="20.0" prefHeight="20.0">
                <padding>
                    <Insets bottom="2.0" left="5.0" top="2.0" />
                </padding>
            </Label>
            <Pane fx:id="styleCardPersonNumber" styleClass="style-upper-triangle" maxHeight="20.0" maxWidth="10.0" minHeight="20.0" minWidth="10.0" prefHeight="20.0" prefWidth="10.0"/>
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
