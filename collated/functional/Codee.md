# Codee
###### \java\seedu\address\commons\events\ui\ChangeTagColourEvent.java
``` java
public class ChangeTagColourEvent extends BaseEvent {

    public final String tagColour;
    public final String tagName;

    public ChangeTagColourEvent(String tagName, String tagColour) {
        this.tagName = tagName;
        this.tagColour = tagColour;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\HighlightSelectedTeamEvent.java
``` java
public class HighlightSelectedTeamEvent extends BaseEvent {

    public final String teamName;

    public HighlightSelectedTeamEvent(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowNewTeamNameEvent.java
``` java
public class ShowNewTeamNameEvent extends BaseEvent {

    public final String teamName;

    public ShowNewTeamNameEvent(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\SetCommand.java
``` java
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "setTagColour";
    public static final String COMMAND_ALIAS = "stc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the colour of tags to the colour of choice "
            + "Parameters: "
            + PREFIX_TAG + "TAG "
            + PREFIX_TAG_COLOUR + "TAG_COLOUR\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG_COLOUR + "green\n"
            + "Colours to choose from are : teal, red, yellow, blue, orange, brown, \n"
            + "green, pink, black, grey\n";

    public static final String MESSAGE_PARAMETERS = PREFIX_TAG + "TAG " + PREFIX_TAG_COLOUR + "TAG_COLOUR";

    public static final String MESSAGE_INVALID_TAG = "tag is invalid! Please input a valid tag name!";
    public static final String MESSAGE_SUCCESS = "tag %1$s colour changed to %2$s";

    private final Tag tagToSet;
    private final String tagColour;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SetCommand(Tag tag, String colour) {
        requireNonNull(tag);
        tagToSet = tag;
        tagColour = colour;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        boolean isTagValid = model.setTagColour(tagToSet, tagColour);
        if (!tagToSet.isValidTagName(tagToSet.getTagName()) || !isTagValid) {
            return new CommandResult(String.format(MESSAGE_INVALID_TAG));
        }
        EventsCenter.getInstance().post(new ChangeTagColourEvent(tagToSet.getTagName(), tagColour));
        return new CommandResult(String.format(MESSAGE_SUCCESS, tagToSet.toString(), tagColour));
    }

    @Override
    public boolean equals(Object other) {
        // Check if
        // (a) Object is the same object
        // (b) Object is an instance of the object and that toSet, tag and color are the same
        return other == this // short circuit if same object
                || (other instanceof SetCommand // instanceof handles nulls
                && this.tagToSet.getTagName().equals(((SetCommand) other).tagToSet.getTagName()))
                && this.tagColour.equals(((SetCommand) other).tagColour);
    }
}

```
###### \java\seedu\address\logic\parser\SetCommandParser.java
``` java
public class SetCommandParser implements Parser<SetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCommand
     * and returns an SetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_TAG_COLOUR);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG, PREFIX_TAG_COLOUR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        try {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            String colour = ParserUtil.parseTagColour(argMultimap.getValue(PREFIX_TAG_COLOUR).get());
            if (!tag.isValidTagColour(colour)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
            }
            return new SetCommand(tag, colour);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setTagColour(Tag tag, String colour) {
        for (Tag t : tags) {
            if (t.getTagName().equals(tag.getTagName())) {
                t.changeTagColour(colour);
            }
        }
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
    public String getTagColour() {
        return this.tagColour;
    }

    /**
     * Changes the {@code tagColour} for {@code tagName}'s label
     */
    public void changeTagColour(String colour) {
        this.tagColour = colour;
    }


    /**
     * Returns true if a given string is a valid tag colour.
     */
    public static boolean isValidTagColour(String testColour) {
        for (String tcs : TAG_COLOR_STYLES) {
            if (testColour.equals(tcs)) {
                return true;
            }
        }
        return false;
    }
```
###### \java\seedu\address\storage\XmlAdaptedTag.java
``` java
    public XmlAdaptedTag(String tagName, String tagColour) {
        this.tagName = tagName;
        this.tagColour = tagColour;
    }

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Tag source) {
        tagName = source.tagName;
        tagColour = source.getTagColour();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Tag toModelType() throws IllegalValueException {
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        if (!Tag.isValidTagColour(tagColour)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_COLOUR_CONSTRAINTS);
        }
        return new Tag(tagName, tagColour);
    }
```
###### \java\seedu\address\storage\XmlAdaptedTeam.java
``` java
public class XmlAdaptedTeam {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's %s field is missing!";

    @XmlElement(required = true)
    private String teamName;
    @XmlElement
    private List<XmlAdaptedPerson> players = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTeam.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTeam() {}

    /**
     * Constructs a {@code XmlAdaptedTeam} with the given {@code teamName}.
     */
    public XmlAdaptedTeam(String teamName, List<XmlAdaptedPerson> persons) {
        this.teamName = teamName;
        if (persons != null) {
            this.players = new ArrayList<>(persons);
        }
    }

    /**
     * Converts a given Team into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTeam
     */
    public XmlAdaptedTeam(Team source) {
        teamName = source.getTeamName().toString();
        players = new ArrayList<>();
        for (Person person : source.getTeamPlayers()) {
            players.add(new XmlAdaptedPerson(person));
        }
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Team object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Team toModelType() throws IllegalValueException {
        if (this.teamName == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TeamName.class.getSimpleName())));
        }
        if (!TeamName.isValidName(this.teamName)) {
            throw new IllegalValueException(TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
        }
        final TeamName teamName = new TeamName(this.teamName);

        final List<Person> teamPlayers = new ArrayList<>();
        for (XmlAdaptedPerson player : players) {
            teamPlayers.add(player.toModelType());
        }

        return new Team(teamName, teamPlayers);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTeam)) {
            return false;
        }
        XmlAdaptedTeam otherTeam = (XmlAdaptedTeam) other;
        return Objects.equals(teamName, otherTeam.teamName)
                && players.equals(otherTeam.players);

    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @XmlElement
    private List<XmlAdaptedTeam> teams;
    @XmlElement
    private List<XmlAdaptedPerson> persons;
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        teams.addAll(src.getTeamList().stream().map(XmlAdaptedTeam::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedTag t : tags) {
            addressBook.addTag(t.toModelType());
        }
        for (XmlAdaptedPerson p : persons) {
            addressBook.addPerson(p.toModelType());
        }
        for (XmlAdaptedTeam tm : teams) {
            addressBook.createTeam(tm.toModelType());
        }
        return addressBook;
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @Subscribe
    public void handleColourChangeEvent(ChangeTagColourEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Set<Tag> tagSet = person.getTags();
        int i = 0;
        for (Iterator<Tag> it = tagSet.iterator(); it.hasNext();) {
            Tag tag = it.next();
            if (tag.getTagName().equals(event.tagName)) {
                tags.getChildren().remove(i);
                Label newTagLabel = new Label(event.tagName);
                newTagLabel.getStyleClass().add(event.tagColour);
                tags.getChildren().add(i, newTagLabel);
            }
            i++;
        }
    }
}
```
###### \java\seedu\address\ui\PlayerDetails.java
``` java
public class PlayerDetails extends UiPart<Region> {

    private static final String FXML = "PlayerDetails.fxml";

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
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label jerseyNumber;
    @FXML
    private Label remark;
```
###### \java\seedu\address\ui\TeamDisplay.java
``` java
public class TeamDisplay extends UiPart<Region> {

    private static final String FXML = "TeamDisplay.fxml";
    private ObservableList<Team> teamList;

    @FXML
    private FlowPane teams;

    public TeamDisplay(ObservableList<Team> teamList) {
        super(FXML);
        this.teamList = teamList;
        initTeams();
        getTeams();
        registerAsAnEventHandler(this);
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTeams() {
        for (Team t: this.teamList) {
            Label teamLabel = new Label(t.getTeamName().toString());
            teamLabel.setStyle("-fx-text-fill: #9cb3d8");
            teams.getChildren().add(teamLabel);
            teams.setHgap(10);
        }
    }

    public List<String> getTeams() {
        List<String> listOfTeams = FXCollections.observableArrayList();
        for (Team t: teamList) {
            listOfTeams.add(t.getTeamName().toString());
        }
        return listOfTeams;
    }

    @Subscribe
    private void handleShowNewTeamEvent(ShowNewTeamNameEvent event) {
        Label newTeamLabel = new Label(event.teamName);
        newTeamLabel.getStyleClass();
        teams.getChildren().add(newTeamLabel);
    }

    @Subscribe
    private void handleHighlightSelectedTeamEvent(HighlightSelectedTeamEvent event) {
        for (int i = 0; i < teamList.size(); i++) {
            if (event.teamName.equals(teamList.get(i).getTeamName().toString())) {
                teams.getChildren().remove(i);
                Label newTeamLabel = new Label(event.teamName);
                newTeamLabel.getStyleClass().add("selected");
                teams.getChildren().add(i, newTeamLabel);
            } else {
                teams.getChildren().remove(i);
                Label newTeamLabel = new Label(teamList.get(i).getTeamName().toString());
                newTeamLabel.getStyleClass();
                teams.getChildren().add(i, newTeamLabel);
            }
        }
    }

    @Subscribe
    private void handleDeselectTeamEvent(DeselectTeamEvent event) {
        for (int i = 0; i < teamList.size(); i++) {
            teams.getChildren().remove(i);
            Label newTeamLabel = new Label(teamList.get(i).getTeamName().toString());
            newTeamLabel.getStyleClass();
            teams.getChildren().add(i, newTeamLabel);
        }
    }
}

```
###### \resources\view\PlayerDetails.fxml
``` fxml
<StackPane xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/9" styleClass="player-details-container">
    <children>
        <GridPane>
            <columnConstraints>
                <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
            </columnConstraints>
            <children>
                <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
                    <padding>
                        <Insets bottom="5" left="15" right="5" top="5" />
                    </padding>
                    <children>
                        <HBox alignment="CENTER_LEFT" spacing="5">
                            <children>
                                <Label fx:id="name" styleClass="player-details-title" text="\$first" />
                            </children>
                        </HBox>
                        <Label fx:id="phone" styleClass="player-details-cell" text="\$phone" />
                        <Label fx:id="address" styleClass="player-details-cell" text="\$address" />
                        <Label fx:id="email" styleClass="player-details-cell" text="\$email" />
                        <Label fx:id="jerseyNumber" styleClass="player-details-cell" text="\$jerseyNumber" />
                        <Label fx:id="remark" styleClass="player-details-cell" text="\$remark" />
                    </children>
                </VBox>
            </children>
            <rowConstraints>
                <RowConstraints />
            </rowConstraints>
        </GridPane>
    </children>
</StackPane>
```
###### \resources\view\TeamDisplay.fxml
``` fxml
<HBox maxHeight="40.0" minHeight="40.0" prefHeight="40.0" prefWidth="800" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <FlowPane fx:id="teams" alignment="CENTER_LEFT" nodeOrientation="LEFT_TO_RIGHT" prefHeight="40.0" prefWidth="800" styleClass="team-panel" vgap="1.0">
         <HBox.margin>
            <Insets />
         </HBox.margin>
         <padding>
            <Insets left="10.0" right="10.0" />
         </padding></FlowPane>
   </children>
</HBox>
```
