package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PatientPanelSelectionChangedEvent;
import seedu.address.model.patient.Patient;
import seedu.address.model.tag.Tag;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Patient patient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private Label nric;
    @FXML
    private Label dob;
    @FXML
    private Label bloodType;
    @FXML
    private Label recordList;
    @FXML
    private FlowPane tags;

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }
    /**
     * Loads the details of the patient.
     */
    private void loadPersonPage(Patient patient) {
        this.patient = patient;
        name.setText(patient.getName().fullName);
        nric.setText("NRIC: " + patient.getNric().value);
        phone.setText("Phone: " + patient.getPhone().value);
        address.setText("Address: " + patient.getAddress().value);
        dob.setText("Date of Birth: " + patient.getDob().value);
        bloodType.setText("Blood Type: " + patient.getBloodType().value);
        email.setText("Email: " + patient.getEmail().value);
        remark.setText("Remarks: " + patient.getRemark().value);
        recordList.setText("Records: " + patient.getRecordList().toString());

        initTagLabels(patient);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePatientPanelSelectionChangedEvent(PatientPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().patient);
    }

    /**
     * create tag labels for patient, change tag color based on the hex string
     * @param patient
     */
    private void initTagLabels(Patient patient) {
        //Solution below adopted from https://assylias.wordpress.com/2013/12/08/383/ and
        //https://www.javaworld.com/article/2074537/core-java/tostring--
        //hexadecimal-representation-of-identity-hash-codes.html
        tags.getChildren().clear();
        for (Tag tag : patient.getTags()) {
            Label newLabel = new Label(tag.tagName);
            newLabel.setStyle("-fx-background-color: #" + convertHashCodeToHexString(tag.tagName));
            tags.getChildren().add(newLabel);
        }
    }

    private static String convertHashCodeToHexString(String tagName) {
        return Integer.toHexString(tagName.hashCode());
    }
}
