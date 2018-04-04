package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonChangedEvent;
import seedu.address.commons.util.UiUtil;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
