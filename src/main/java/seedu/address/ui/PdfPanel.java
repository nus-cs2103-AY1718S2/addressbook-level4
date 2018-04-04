package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

//@@author Ang-YC
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
