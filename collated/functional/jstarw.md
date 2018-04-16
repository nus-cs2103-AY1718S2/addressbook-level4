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

<?import javafx.scene.control.TextField?>
<?import javafx.scene.control.Button?>
<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         minWidth="450" minHeight="600">
    <scene>
        <Scene>
            <stylesheets>
                <URL value="@ClearTheme.css" />
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
                        <Label styleClass="cell_small_label" >Phone: </Label>
                        <TextField fx:id="phone" styleClass="cell_small_label" text="/$phone"></TextField>
                        <Label styleClass="cell_small_label" >Address: </Label>
                        <TextField fx:id="address" styleClass="cell_small_label" text="\$address" />
                        <Label styleClass="cell_small_label" >Email: </Label>
                        <TextField fx:id="email" styleClass="cell_small_label" text="\$email" />
                        <Label styleClass="cell_small_label" >Age: </Label>
                        <TextField fx:id="age" styleClass="cell_small_label" text="\$age" />
                        <Label styleClass="cell_small_label" >Income: </Label>
                        <TextField fx:id="income" styleClass="cell_small_label" text="\$income" />
                        <Label styleClass="cell_small_label" >Actual Spending: </Label>
                        <TextField fx:id="actualSpending" styleClass="cell_small_label"
                                   text="\$actualSpending" disable="true" />
                        <Label styleClass="cell_small_label" >Is a new client: </Label>
                        <TextField fx:id="isNewClient" styleClass="cell_small_label" text="\$isNewClient" />
                        <Label styleClass="cell_small_label" >Expected Spending: </Label>
                        <TextField fx:id="expectedSpending" styleClass="cell_small_label"
                                   text="\$expectedSpending" disable="true" />
                        <Label styleClass="cell_small_label" >Policy: </Label>
                        <TextField fx:id="policy" styleClass="cell_small_label" text="\$policy" disable="true" />
                        <Button fx:id="submit" styleClass="cell_small_label" text="submit" />
                    </VBox>
                </GridPane>
            </HBox>
        </Scene>
    </scene>
</fx:root>


```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    private void loadPersonDetail(Person person, Integer index) {
        PersonDetail personDetail = new PersonDetail(person, index);
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
    }

```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    @Subscribe
    private void handlePersonCardDoubleClick(PersonCardDoubleClick event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetail(event.getNewSelection(), event.getIndex());
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
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleSubmitEvent(PersonEditEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        try {

            EditCommandParser editCommandParser = new EditCommandParser();
            EditCommand editCommand = editCommandParser.parse(event.getArgs());
            editCommand.setData(model, null, null);
            editCommand.execute();
        } catch (Exception exc) {
            exc.printStackTrace();

        }
    }
}
```
###### /java/seedu/address/ui/PersonCard.java
``` java
    private void setDoubleClickEvent() {
        cardPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        raise(new PersonCardDoubleClick(person, index));
                    }
                }
            }
        });
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
}
```
###### /java/seedu/address/commons/events/ui/PersonEditEvent.java
``` java
/**
 * Represents a edit event of the person detail page
 */
public class PersonEditEvent extends BaseEvent {

    private final String args;

    public PersonEditEvent(String args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getArgs() {
        return args;
    }
}
```
###### /java/seedu/address/commons/events/ui/PersonCardDoubleClick.java
``` java
/**
 * Represents a double click event in the Person Card
 */
public class PersonCardDoubleClick extends BaseEvent {

    private final Person newSelection;
    private final Integer index;

    public PersonCardDoubleClick(Person newSelection, Integer index) {
        this.newSelection = newSelection;
        this.index = index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getNewSelection() {
        return newSelection;
    }

    public Integer getIndex() {
        return index;
    }
}
```
###### /java/seedu/address/logic/parser/ShowCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns an ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ShowCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public void setAge(Age age) {
            this.age = age;
        }
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        public Optional<Age> getAge() {
            return Optional.ofNullable(age);
        }
```
###### /java/seedu/address/logic/commands/ShowCommand.java
``` java
/**
 * Opens up a PersonDetail window
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = "Opens up the details window of a specified person.\n"
            + "Parameters: FULL NAME OF PERSON\n"
            + "Example: " + COMMAND_WORD + " John Doe";;

    public static final String MESSAGE_SUCCESS = "Opened up person detail window";
    public static final String MESSAGE_FAIL = "Failed to open window: person not found.";

    private final NameContainsKeywordsPredicate predicate;

    public ShowCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            Person person = model.findOnePerson(predicate);
            loadPersonDetail(person);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_FAIL);
        }
    }

    private void loadPersonDetail(Person person) {
        PersonDetail personDetail = new PersonDetail(person, 1);
        personDetail.show();
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
###### /java/seedu/address/model/ModelManager.java
``` java
    public Person findOnePerson(Predicate<Person> predicate) throws PersonNotFoundException {
        requireNonNull(predicate);
        ObservableList<Person> persons = addressBook.getPersonList();
        for (Person person : persons) {
            if (predicate.test(person)) {
                return person;
            }
        }
        throw new PersonNotFoundException();
    }
```
