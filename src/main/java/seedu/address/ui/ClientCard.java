package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Client;

//@@author shookshire
/**
 * An UI component that displays information of a {@code Client}.
 */
public class ClientCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    private static final String[] TAGS_COLOUR_STYLES =
        { "red" , "blue" , "green" , "yellow" , "purple" , "lightpink" , "gold" , "wheat" };

    private static final String MATCH_COLOUR_STYLE = "orange";

    private static final String UNMATCH_COLOUR_STYLE = "noFill";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Client client;

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
    private Label places;
    @FXML
    private Label grades;
    @FXML
    private Label subjects;
    @FXML
    private FlowPane tags;


    public ClientCard(Client client, int displayedIndex) {
        super(FXML);
        this.client = client;
        id.setText(displayedIndex + ". ");
        name.setText(client.getName().fullName);
        phone.setText(client.getPhone().value);
        address.setText(client.getAddress().value);
        email.setText(client.getEmail().value);
        intplaces(client);
        intGrades(client);
        intSubjects(client);
        intTags(client);
    }

    /**
     *@author olimhc-reused
     *Reused from https://github.com/se-edu/addressbook-level4/pull/798/commits with minor modification
     * Initialises tags
     * @param client
     */
    private void intTags(Client client) {
        client.getTags().forEach(tag -> {
            Label newLabel = new Label(tag.tagName);
            newLabel.getStyleClass().add(getColour(tag.tagName));
            tags.getChildren().add(newLabel);
        });
    }

    //@@author Zhu-Jiahui
    /**
     * Initialises Location
     * If Location is matched with the client, Location field will be highlighted.
     * @param client
     */

    private void intplaces(Client client) {

        places.setText(client.getLocation().value);

        if (client.getMatchedLocation() == true) {
            places.getStyleClass().add(MATCH_COLOUR_STYLE);
        } else {
            places.getStyleClass().add(UNMATCH_COLOUR_STYLE);
        }
    }

    /**
     * Initialises Grade
     * If Grade is matched with the client, Grade field will be highlighted.
     * @param client
     */

    private void intGrades(Client client) {

        grades.setText(client.getGrade().value);

        if (client.getMatchedGrade() == true) {
            grades.getStyleClass().add(MATCH_COLOUR_STYLE);
        } else {
            grades.getStyleClass().add(UNMATCH_COLOUR_STYLE);
        }
    }

    /**
     *@author Zhu-Jiahui
     * Initialises Subject
     * If Subject is matched with the client, Subject field will be highlighted.
     * @param client
     */

    private void intSubjects(Client client) {
        subjects.setText(client.getSubject().value);

        if (client.getMatchedSubject() == true) {
            subjects.getStyleClass().add(MATCH_COLOUR_STYLE);
        } else {
            subjects.getStyleClass().add(UNMATCH_COLOUR_STYLE);
        }
    }
    //@@author

    /**
     * @param name
     * @return String colour
     */
    private String getColour(String name) {
        return TAGS_COLOUR_STYLES[(Math.abs(name.hashCode() % TAGS_COLOUR_STYLES.length))];
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientCard)) {
            return false;
        }

        // state check
        ClientCard card = (ClientCard) other;
        return id.getText().equals(card.id.getText())
                && client.equals(card.client);
    }
}
