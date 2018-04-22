package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.association.ClientOwnPet;
import seedu.address.model.pet.Pet;

//@@author purplepers0n-reused
/**
 * An UI component that displays information of a {@code clientOwnPet}.
 */
public class PetCard extends UiPart<Region> {

    private static final String FXML = "PetListCard.fxml";
    private static final String[] TAG_COLOR = {"red", "yellow", "blue", "orange", "green",
        "pink", "navy", "teal", "purple", "peach", "lightblue", "darkpurple",
        "green2", "white", "wine", "fuchsia", "sea"};

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ClientOwnPet clientOwnPet;
    public final Pet pet;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label gender;
    @FXML
    private Label age;
    @FXML
    private Label client;
    //species and breed included in tags
    @FXML
    private FlowPane tags;

    public PetCard(ClientOwnPet clientOwnPet, int displayedIndex) {
        super(FXML);
        this.clientOwnPet = clientOwnPet;
        pet = clientOwnPet.getPet();
        id.setText(displayedIndex + ". ");
        name.setText(pet.getPetName().fullPetName);
        gender.setText("Gender: " + pet.getPetGender().fullGender);
        age.setText(pet.getPetAge().value + " years old");
        client.setText("Owner: " + clientOwnPet.getClient().getName());
        initTags(pet);
    }

    /**
     * @return the color for {@code tagName}'s label
     */
    private String getTagColorFor(String tagName) {
        return TAG_COLOR[Math.abs(tagName.hashCode()) % TAG_COLOR.length];
    }

    /**
     * Creates the tag labels for {@code pet}.
     */
    private void initTags(Pet pet) {
        pet.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PetCard)) {
            return false;
        }

        // state check
        PetCard card = (PetCard) other;
        return id.getText().equals(card.id.getText())
                && pet.equals(card.pet);
    }
}
