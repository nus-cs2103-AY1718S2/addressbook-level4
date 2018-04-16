# Codee
###### /resources/view/MainWindow.fxml
``` fxml
            <Menu fx:id="mtmLogo" styleClass="mtm-logo"/>
```
###### /resources/view/TeamDisplay.fxml
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
###### /resources/view/LightTheme.css
``` css
.background {
    -fx-background-color: #eaf4ff;
    background-color: #eaf4ff; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #eaf4ff;
    -fx-control-inner-background: #e0eeff;
    -fx-background-color: #e0eeff;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#e0eeff, 30%);
    -fx-border-color: derive(#eaf4ff, 30%);
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: #eaf4ff;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: #eaf4ff;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #9fd5f2;
}

.list-cell:filled:odd {
    -fx-background-color: #b8def2;
}

.list-cell:filled:selected {
    -fx-background-color: #64b7ea;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #92bcef;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: #eaf4ff;
}

.pane-with-border {
     -fx-background-color: #eaf4ff;
     -fx-border-color: derive(#e0eeff, 30%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: #eaf4ff;
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: #eaf4ff;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: #3e5670;
    -fx-border-color: #88c0fc;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: #233351;
}

.status-bar-with-border {
    -fx-background-color: derive(#e0eeff, 70%);
    -fx-border-color: #88c0fc;
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: #e0eeff;
    -fx-border-color: derive(#e0eeff, 30%);
    -fx-border-width: 1px;
    -fx-text-fill: black;
}

.grid-pane .anchor-pane {
    -fx-background-color: #e0eeff;
}

.context-menu {
    -fx-background-color: derive(#e0eeff, 30%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: #eaf4ff;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #eaf4ff;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #b3ddfc;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #eaf4ff;
}

.button:default:hover {
    -fx-background-color: #b3ddfc;
}

.dialog-pane {
    -fx-background-color: derive(#e0eeff, 30%);
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: derive(#e0eeff, 30%);
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: #e0eeff;
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: #c7dffc;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#c7dffc, 30%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent;
    -fx-background-insets: 0;
    -fx-border-color: #badafc #badafc #88c0fc #badafc;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: #3e5670;
}

#filterField, #personList, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: #d9eafc;
    -fx-background-radius: 0;
}

#teams {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#teams .label {
    -fx-font-size: 15;
    -fx-text-fill: #3f7bbf;
}

#teams .selected {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-background-color: #49cbff;
    -fx-text-fill: #1d3a5b;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#tags .teal {
    -fx-text-fill: white;
    -fx-background-color: "#5295a5";
}

#tags .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#tags .yellow {
    -fx-background-color: yellow;
    -fx-text-fill: black;
}

#tags .blue {
    -fx-text-fill: white;
    -fx-background-color: "#416399";
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#tags .brown {
    -fx-text-fill: white;
    -fx-background-color: "#a86d2f";
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: "#44936f";
}

#tags .pink {
    -fx-text-fill: black;
    -fx-background-color: pink;
}

#tags .black {
    -fx-text-fill: white;
    -fx-background-color: black;
}

#tags .grey {
    -fx-text-fill: black;
    -fx-background-color: grey;
}

.team-panel {
    -fx-background-color: transparent;
    -fx-text-fill: white;
}

.team-placeholder {
    -fx-background-color: #b8d8fc;
    -fx-border-color:  #a1c9f4 transparent #a1c9f4 transparent;

}

.player-details-container {
    -fx-background-color: #a0d2f7;
}

.player-details-cell {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 20px;
    -fx-text-fill: #243b4c;
}

.player-details-title {
    -fx-text-fill: #122635;
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 30px;
}

.mtm-logo {
    max-height: 40px;
    max-width: 200px;
}

.mtm-logo:disabled, mtm-logo:disabled > * {
    -fx-opacity: 1.0;
}
```
###### /resources/view/PlayerDetails.fxml
``` fxml
<StackPane xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/9" fx:id="playerDetails" styleClass="player-details-container">
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
###### /java/seedu/address/ui/PlayerDetails.java
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
    private Person personBeforeChange;

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
###### /java/seedu/address/ui/PlayerDetails.java
``` java
    @Subscribe
    private void handlePersonDetailsChangedEvent(PersonDetailsChangedEvent event) {
        if (event.getPerson().getName().fullName.equals(person.getName().fullName)) {
            name.setText((event.getPerson().getName().toString()));
            phone.setText(event.getPerson().getPhone().toString());
            jerseyNumber.setText("Jersey Number: " + event.getPerson().getJerseyNumber().toString());
            address.setText(event.getPerson().getAddress().toString());
            email.setText(event.getPerson().getEmail().toString());
            remark.setText("Remarks: " + event.getPerson().getRemark().toString());
        }
    }
}

```
###### /java/seedu/address/ui/MainWindow.java
``` java
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
        mainWindow.getStylesheets().add("view/Extensions.css");

        final Image image = new Image("images/MyTeamManagerLogo.png", true);
        mtmLogo.setGraphic(new ImageView(image));
        mtmLogo.setDisable(true);

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * @returns the {@code currentTheme}.
     */
    public static String getCurrentTheme() {
        return currentTheme;
    }

    @Subscribe
    public void handleChangeThemeRequestEvent(ChangeThemeEvent event) throws CommandException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getStylesheets().remove(currentTheme);
        prefs.setAddressBookTheme(event.theme + "Theme.css");
        currentTheme = "view/" + prefs.getAddressBookTheme();
        mainWindow.getStylesheets().add(currentTheme);
    }
```
###### /java/seedu/address/ui/TeamDisplay.java
``` java
public class TeamDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(TeamDisplay.class);
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
            teamLabel.setStyle("-fx-text-fill: #3f7bbf");
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

    @Subscribe
    private void handleRemoveSelectedTeamEvent(RemoveSelectedTeamEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        for (int i = 0; i < teams.getChildren().size(); i++) {
            if (teamList.get(i).getTeamName().equals(event.teamName)) {
                teams.getChildren().remove(i);
            }
        }
    }

    @Subscribe
    private void handleClearTeamsEvent(ClearTeamsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        teams.getChildren().clear();
    }

    @Subscribe
    private void handleUndoTeamsEvent(UndoTeamsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        teams.getChildren().clear();
        initTeams();
        getTeams();
    }
}

```
###### /java/seedu/address/ui/PersonListPanel.java
``` java
    @Subscribe
    private void handlePersonDetailsChangedNoParamEvent(PersonDetailsChangedNoParamEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        PersonCard newPersonCard = personListView.getItems().get(selectedCardIndex);
        playerDetailsPlaceholder.getChildren().clear();
        playerDetails = new PlayerDetails(newPersonCard.person);
        playerDetailsPlaceholder.getChildren().add(playerDetails.getRoot());
    }
    //@author

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }
}
```
###### /java/seedu/address/ui/PersonCard.java
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
###### /java/seedu/address/commons/events/ui/PersonDetailsChangedEvent.java
``` java
public class PersonDetailsChangedEvent extends BaseEvent {

    private final Person editedPerson;
    private final Index index;

    public PersonDetailsChangedEvent(Person editedPerson, Index index) {

        this.editedPerson = editedPerson;
        this.index = index;
    }

    public Person getPerson() {
        return this.editedPerson;
    }

    public Index getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/HighlightSelectedTeamEvent.java
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
###### /java/seedu/address/commons/events/ui/ChangeTagColourEvent.java
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
###### /java/seedu/address/commons/events/ui/ClearTeamsEvent.java
``` java
public class ClearTeamsEvent extends BaseEvent {

    public ClearTeamsEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

```
###### /java/seedu/address/commons/events/ui/ShowNewTeamNameEvent.java
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
###### /java/seedu/address/commons/events/ui/UndoTeamsEvent.java
``` java
public class UndoTeamsEvent extends BaseEvent {

    public UndoTeamsEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/ChangeThemeEvent.java
``` java
/**
 * Indicates a request for theme change.
 */
public class ChangeThemeEvent extends BaseEvent {

    public final String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
```
###### /java/seedu/address/commons/events/ui/PersonDetailsChangedNoParamEvent.java
``` java
public class PersonDetailsChangedNoParamEvent extends BaseEvent {

    public PersonDetailsChangedNoParamEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/logic/parser/SetCommandParser.java
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
###### /java/seedu/address/logic/parser/ChangeThemeCommandParser.java
``` java
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {

    /**
     * Parses the given (@code String) in the context of a ThemeCommand.
     * @return ThemeCommand Object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeThemeCommand parse(String userInput) throws ParseException {
        if (userInput.length() == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        return new ChangeThemeCommand(userInput);
    }
}
```
###### /java/seedu/address/logic/commands/SetCommand.java
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
###### /java/seedu/address/logic/commands/RedoCommand.java
``` java
        EventsCenter.getInstance().post(new UndoTeamsEvent());
        EventsCenter.getInstance().post(new PersonDetailsChangedNoParamEvent());
```
###### /java/seedu/address/logic/commands/ChangeThemeCommand.java
``` java
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "changeTheme";
    public static final String COMMAND_ALIAS = "cte";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the theme of MTM.\n"
            + "Parameters: THEME (must be either Light, or Dark)\n"
            + "Examples: changeTheme Light, cte Dark";

    public static final String MESSAGE_THEME_SUCCESS = "Theme updated to: %1$s";

    private final String theme;

    public ChangeThemeCommand(String theme) {
        this.theme = theme.trim();
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!isValidTheme(this.theme)) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME);
        }
        if ((MainWindow.getCurrentTheme()).contains(this.theme)) {
            throw new CommandException("Theme is already set to " + this.theme + "!");
        }
        EventsCenter.getInstance().post(new ChangeThemeEvent(this.theme));
        return new CommandResult(String.format(MESSAGE_THEME_SUCCESS, this.theme));
    }

    private boolean isValidTheme(String theme) {
        return theme.equals("Light") || theme.equals("Dark");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && this.theme.equals(((ChangeThemeCommand) other).theme)); // state check
    }

}
```
###### /java/seedu/address/logic/commands/ClearCommand.java
``` java
        EventsCenter.getInstance().post(new ClearTeamsEvent());
```
###### /java/seedu/address/logic/commands/EditCommand.java
``` java
        EventsCenter.getInstance().post(new PersonDetailsChangedEvent(editedPerson, index));
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
        EventsCenter.getInstance().post(new UndoTeamsEvent());
        EventsCenter.getInstance().post(new PersonDetailsChangedNoParamEvent());
```
###### /java/seedu/address/storage/XmlAdaptedTeam.java
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
###### /java/seedu/address/storage/XmlAdaptedTag.java
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
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @XmlElement
    private List<XmlAdaptedTeam> teams;
    @XmlElement
    private List<XmlAdaptedPerson> persons;
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
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
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
    public static Team[] getSampleTeams()  {
        return new Team[] {
            new Team(new TeamName("Arsenal")),
            new Team(new TeamName("Chelsea"))
        };
    }
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
            for (Team sampleTeam : getSampleTeams()) {
                sampleAb.createTeam(sampleTeam);
            }
```
###### /java/seedu/address/model/UserPrefs.java
``` java
    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    public String getAddressBookTheme() {
        return addressBookTheme;
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void setTagColour(Tag tag, String colour) {
        for (Tag t : tags) {
            if (t.getTagName().equals(tag.getTagName())) {
                t.changeTagColour(colour);
            }
        }
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public boolean setTagColour(Tag tag, String colour) {
        ObservableList<Tag> allTags = addressBook.getTagList();
        boolean isTagValid = false;
        for (Tag t : allTags) {
            if (t.getTagName().equals(tag.getTagName())) {
                isTagValid = true;
                break;
            }
        }
        if (!isTagValid) {
            return false;
        }
        addressBook.setTagColour(tag, colour);
        indicateAddressBookChanged();
        return isTagValid;
    }

```
###### /java/seedu/address/model/tag/Tag.java
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
