package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import seedu.address.model.person.Person;
import seedu.address.model.smplatform.Link;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private VBox socialMediaIconPane;
    @FXML
    private FlowPane tags;

    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        addIconsToSocialMediaIconPane();
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }

    //@@author Nethergale
    /**
     * Adds the various social media icon tab to the social media icon pane.
     */
    private void addIconsToSocialMediaIconPane() {
        for (String key : person.getSocialMediaPlatformMap().keySet()) {
            if (!person.getSocialMediaPlatformMap().get(key).getLink().value.isEmpty()) {
                socialMediaIconPane.getChildren().add(createSocialMediaIconTab(key));
            }
        }
    }

    /**
     * Creates the icon tab for the specific social media platform.
     *
     * @param type social media platform type
     * @return stack pane representing an icon tab
     */
    private StackPane createSocialMediaIconTab(String type) {
        StackPane socialMediaIconTab = new StackPane();
        Region tabBackground = new Region();
        tabBackground.setPrefSize(30, 32);
        tabBackground.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #29323C, #485563); "
                + "-fx-background-radius: 6 0 0 6; -fx-border-color: #242C35; "
                + "-fx-border-radius: 6 0 0 6; -fx-border-width: 2 0 2 2;");
        ImageView tabIcon = new ImageView();
        String url = imageUrl(type);
        if (!url.isEmpty()) {
            tabIcon.setFitWidth(20);
            tabIcon.setFitHeight(20);
            tabIcon.setImage(new Image(url));
            socialMediaIconTab.getChildren().addAll(tabBackground, tabIcon);
        }
        return socialMediaIconTab;
    }

    /**
     * Returns the location of the icon for the specific social media platform {@code type}.
     */
    private String imageUrl(String type) {
        if (type.equals(Link.FACEBOOK_LINK_TYPE)) {
            return "images/facebook_icon.png";
        } else if (type.equals(Link.TWITTER_LINK_TYPE)) {
            return "images/twitter_icon.png";
        } else {
            return "";
        }
    }
}
