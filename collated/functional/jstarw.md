# jstarw
###### /resources/view/PersonDetail.fxml
``` fxml
<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.Scene?>

<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         minWidth="450" minHeight="600">
    <scene>
        <Scene>
            <stylesheets>
                <URL value="@DarkTheme.css" />
                <URL value="@Extensions.css" />
            </stylesheets>

            <HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
                <GridPane HBox.hgrow="ALWAYS">
                    <columnConstraints>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
                    </columnConstraints>
                    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
                        <padding>
                            <Insets top="5" right="5" bottom="5" left="15" />
                        </padding>
                        <HBox spacing="5" alignment="CENTER_LEFT">
                            <Label fx:id="id" styleClass="cell_big_label">
                                <minWidth>
                                    <!-- Ensures that the label text is never truncated -->
                                    <Region fx:constant="USE_PREF_SIZE" />
                                </minWidth>
                            </Label>
                            <Label fx:id="name" text="\$first" styleClass="cell_big_label" />
                        </HBox>
                        <FlowPane fx:id="tags" />
                        <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
                        <Label fx:id="address" styleClass="cell_small_label" text="\$address" />
                        <Label fx:id="email" styleClass="cell_small_label" text="\$email" />
                        <Label fx:id="income" styleClass="cell_small_label" text="\$income" />
                        <Label fx:id="actualSpending" styleClass="cell_small_label" text="\$actualSpending" />
                        <Label fx:id="expectedSpending" styleClass="cell_small_label" text="\$expectedSpending" />
                        <Label fx:id="age" styleClass="cell_small_label" text="\$age" />
                    </VBox>
                </GridPane>
            </HBox>
        </Scene>
    </scene>
</fx:root>


```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    private void loadPersonDetail(Person person) {
        PersonDetail personDetail = new PersonDetail(person, 1);
        personDetail.show();
    }
    private void loadPersonPage(Person person) {
        loadPage(SEARCH_PAGE_URL + person.getName().fullName);
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
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
        loadPersonDetail(event.getNewSelection().person);
    }
}
```
###### /java/seedu/address/ui/PersonDetail.java
``` java
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

    public PersonDetail(Person person, int displayedIndex) {
        super("PersonDetail.fxml", new Stage());
        this.person = person;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(person.getName().fullName);
        this.phone.setText(person.getPhone().value);
        this.address.setText(person.getAddress().value);
        this.income.setText(person.getIncome().toString());
        this.age.setText("Age: " + person.getAge().toString());
        this.email.setText(person.getEmail().value);
        this.actualSpending.setText("Actual Spending: " + person.getActualSpending().toString());
        this.expectedSpending.setText("Expected Spending: " + person.getExpectedSpending().toString());
        person.getTags().forEach((tag) -> {
            this.tags.getChildren().add(new Label(tag.tagName));
        });
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
```
###### /java/seedu/address/model/person/Age.java
``` java
/**
 * Represents a Person's age in the address book.
 * Represents a Person's value in the address book
 * Guarantees: immutable; is valid as declare in {@link #isValidAge(Integer)}}
 */
public class Age {
    public static final String AGE_CONSTRAINTS =
            "Persons age must be above 0 years old and under 150.";

    public final Integer value;

    /**
     * @param age a valid value
     */
    public Age(Integer age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), AGE_CONSTRAINTS);
        this.value = age;
    }

    /**
     * checks if the age is valid
     * @param age
     * @return
     */
    public static boolean isValidAge(Integer age) {
        return age >= 0 && age < 150;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Age
                && this.value == ((Age) other).value);
    }

}
```
