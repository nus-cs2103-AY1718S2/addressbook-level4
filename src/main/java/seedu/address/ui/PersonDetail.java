//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.commons.events.ui.PersonEditEvent;
import seedu.address.model.person.Person;

//@@author jstarw
/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonDetail extends UiPart<Stage> {
    private static final String FXML = "PersonDetail.fxml";
    public final Person person;
    private int index;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private FlowPane tags;
    @FXML
    private TextField income;
    @FXML
    private TextField actualSpending;
    @FXML
    private TextField expectedSpending;
    @FXML
    private TextField age;
    @FXML
    private TextField isNewClient;
    @FXML
    private TextField policy;
    @FXML
    private Button submit;

    public PersonDetail(Person person, int displayedIndex) {
        super("PersonDetail.fxml", new Stage());
        this.person = person;
        index = displayedIndex;
        registerAsAnEventHandler(this);
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        //@@author SoilChang
        income.setText(person.getIncome().toString());
        age.setText(person.getAge().toString());
        email.setText(person.getEmail().value);
        actualSpending.setText(person.getActualSpending().toString());
        expectedSpending.setText(person.getExpectedSpending().toString());
        isNewClient.setText("New Client");
        if (person.getPolicy().isPresent()) {
            policy.setText(person.getPolicy().get().toString());
        } else {
            policy.setText("");
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
        //@@author 
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setSubmitListener();
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

    private void setSubmitListener() {
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String args = index + " " + PREFIX_PHONE + phone.getText() + " "
                        + PREFIX_EMAIL + email.getText() + " " + PREFIX_ADDRESS + address.getText() + " "
                        + PREFIX_INCOME + income.getText().replaceAll("[^\\d.]+", "") + " "
                        + PREFIX_AGE + age.getText();
                raise(new PersonEditEvent(args));
            }
        });
    }
}
