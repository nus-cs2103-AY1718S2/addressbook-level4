package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonChangedEvent;
import seedu.address.commons.util.UiUtil;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final double MAX_ANIMATION_TIME_MS = 150;

    public final int index;
    private Person person;

    private final Logger logger = LogsCenter.getLogger(PersonCard.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private AnchorPane cardPersonPane;

    @FXML
    private ImageView cardPhoto;
    @FXML
    private Pane cardPhotoMask;

    @FXML
    private Label cardPersonName;
    @FXML
    private Label cardPersonUniversity;
    @FXML
    private Label cardPersonEmail;
    @FXML
    private Label cardPersonContact;
    @FXML
    private Label cardPersonRating;
    @FXML
    private Pane iconRating;
    @FXML
    private Label cardPersonStatus;
    @FXML
    private Label cardPersonNumber;

    //@@author Ang-YC
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
    //@@author

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return cardPersonNumber.getText().equals(card.cardPersonNumber.getText())
                && person.equals(card.person);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
