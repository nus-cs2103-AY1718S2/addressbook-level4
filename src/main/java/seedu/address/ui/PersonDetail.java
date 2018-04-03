//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.person.Person;

public class PersonDetail extends UiPart<Region> {
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

    public PersonDetail(Person person, int displayedIndex) {

        super("PersonDetail.fxml");
        this.person = person;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(person.getName().fullName);
        this.phone.setText(person.getPhone().value);
        this.address.setText(person.getAddress().value);
        this.income.setText(person.getIncome().toString());
        this.age.setText(person.getAge().toString());
        this.email.setText(person.getEmail().value);
        this.actualSpending.setText(person.getActualSpending().toString());
        this.expectedSpending.setText(person.getExpectedSpending().toString());
        person.getTags().forEach((tag) -> {
            this.tags.getChildren().add(new Label(tag.tagName));
        });
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof PersonDetail)) {
            return false;
        } else {
            PersonDetail detail = (PersonDetail)other;
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
        Stage stage = new Stage();
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(getRoot(), 450, 450));
        stage.show();
    }
}
