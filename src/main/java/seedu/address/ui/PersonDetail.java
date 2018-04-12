//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

//@@author jstarw
/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonDetail extends UiPart<Stage> {
    private static final String FXML = "PersonDetail.fxml";
    public final Person person;
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
    private FlowPane tags;
    @FXML
    private Label income;
    @FXML
    private Label actualSpending;
    @FXML
    private Label expectedSpending;
    @FXML
    private Label age;
    @FXML
    private Label isNewClient;
    @FXML
    private Label policy;

    public PersonDetail(Person person, int displayedIndex) {
        super("PersonDetail.fxml", new Stage());
        this.person = person;
        registerAsAnEventHandler(this);
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        //@author SoilChang
        income.setText("Income: " + person.getIncome().toString());
        age.setText("Age: " + person.getAge().toString() + " years old");
        email.setText(person.getEmail().value);
        actualSpending.setText("Actual Spending: " + person.getActualSpending().toString());
        expectedSpending.setText("Predicted Spending: " + person.getExpectedSpending().toString());
        isNewClient.setText("New Client");
        if (person.getPolicy().isPresent()) {
            policy.setText("Policy: " + person.getPolicy().get().toString());
        } else {
            policy.setText("Has not applied to any policy");
        }

        if (person.getActualSpending().value != 0.0) {
            // the client has actual income
            actualSpending.setVisible(true);
            isNewClient.setVisible(false);
            expectedSpending.setVisible(false);
        } else {
            actualSpending.setVisible(false);
            isNewClient.setVisible(true);
            expectedSpending.setVisible(true);
        }
        //@author
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Equals function.
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof PersonDetail)) {
            return false;
        } else {
            PersonDetail detail = (PersonDetail) other;
            return this.id.getText().equals(detail.id.getText()) && this.person.equals(detail.person);
        }
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        getRoot().show();
    }
}
