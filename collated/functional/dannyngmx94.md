# dannyngmx94
###### \java\seedu\address\commons\events\ui\ShowChartsEvent.java
``` java
/**
 * An event requesting to view charts in person panel.
 */
public class ShowChartsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter all persons whose tag contain any of "
            + "the specified predicate and displays them as a list with index numbers.\n"
            + "Parameters: TAG\n"
            + "Example: " + COMMAND_WORD + " math";

    private final Predicate<Person> predicate;

    public FilterCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ViewStatsCommand.java
``` java
/**
 * Show statistical data from the address book.
 */
public class ViewStatsCommand extends Command {

    public static final String COMMAND_WORD = "viewStats";

    public static final String MESSAGE_VIEW_STATS_SUCCESS = "Show chart";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowChartsEvent());
        return new CommandResult(MESSAGE_VIEW_STATS_SUCCESS);

    }
}
```
###### \java\seedu\address\logic\parser\FilterCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) {
        List<Predicate<Person>> predicateList = new ArrayList<>();
        predicateList.add(new KeywordPredicate(args.trim()));
        Predicate<Person> allPredicates = combineAllPredicates(predicateList);
        return new FilterCommand(allPredicates);
    }

    /**
     * Combines all the predicates in the predicateList into a single Predicate
     * @param predicateList a list of non-empty predicates
     * @return a single Predicate combining all the predicates in the predicateList
     */
    private Predicate<Person> combineAllPredicates(List<Predicate<Person>> predicateList) {
        assert(predicateList.size() >= 1);
        Predicate<Person> allPredicates = predicateList.get(0);
        for (int i = 1; i < predicateList.size(); i++) {
            allPredicates.and(predicateList.get(i));
        }
        return allPredicates;
    }
}
```
###### \java\seedu\address\model\person\KeywordPredicate.java
``` java
/**
 * Tests that a {@code Person}'s {@code Attribute} matches any of the keywords given.
 */
public class KeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public KeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return (person.getSubject().toString().equalsIgnoreCase(keyword)
                || person.getLevel().toString().equalsIgnoreCase(keyword)
                || person.getStatus().toString().equalsIgnoreCase(keyword)
                || person.getRole().toString().equalsIgnoreCase(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof KeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((KeywordPredicate) other).keyword)); // state check
    }

}
```
###### \java\seedu\address\ui\ChartsPanel.java
``` java
/**
 * The Chart Panel of the App.
 */
public class ChartsPanel extends UiPart<Region> {

    private static final String FXML = "ChartsPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<Person> personList;

    @FXML
    private PieChart roleDistribution;
    @FXML
    private BarChart<?, ?> tutorSubject;
    @FXML
    private BarChart<?, ?> studentSubject;


    public ChartsPanel(ObservableList<Person> personList) {
        super(FXML);
        this.personList = personList;
        loadChartsDetails();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads chart details into the chart panel.
     */
    public void loadChartsDetails() {
        ObservableList<Person> tutorList = personList.filtered(person -> person.getRole().equals(new Role("Tutor")));
        ObservableList<Person> studentList =
                personList.filtered(person -> person.getRole().equals(new Role("Student")));
        int numTutor = tutorList.size();
        int numStudent = studentList.size();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Tutor: " + numTutor, numTutor),
                new PieChart.Data("Student: " + numStudent, numStudent));
        roleDistribution.setData(pieChartData);

        ObservableList<Person> tutorEngList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("English")));
        ObservableList<Person> tutorChiList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("Chinese")));
        ObservableList<Person> tutorMathList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("Math")));
        ObservableList<Person> tutorPhyList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("Physics")));
        ObservableList<Person> tutorChemList =
                tutorList.filtered(person -> person.getSubject().equals(new Subject("Chemistry")));
        int numEngTutor = tutorEngList.size();
        int numChiTutor = tutorChiList.size();
        int numMathTutor = tutorMathList.size();
        int numPhyTutor = tutorPhyList.size();
        int numChemTutor = tutorChemList.size();

        XYChart.Series set1 = new XYChart.Series<>();
        set1.getData().add(new XYChart.Data("English", numEngTutor));
        set1.getData().add(new XYChart.Data("Chinese", numChiTutor));
        set1.getData().add(new XYChart.Data("Math", numMathTutor));
        set1.getData().add(new XYChart.Data("Physics", numPhyTutor));
        set1.getData().add(new XYChart.Data("Chemistry", numChemTutor));
        tutorSubject.getData().clear();
        tutorSubject.layout();
        tutorSubject.getData().addAll(set1);

        ObservableList<Person> studentEngList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("English")));
        ObservableList<Person> studentChiList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("Chinese")));
        ObservableList<Person> studentMathList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("Math")));
        ObservableList<Person> studentPhyList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("Physics")));
        ObservableList<Person> studentChemList =
                studentList.filtered(person -> person.getSubject().equals(new Subject("Chemistry")));
        int numEngStudent = studentEngList.size();
        int numChiStudent = studentChiList.size();
        int numMathStudent = studentMathList.size();
        int numPhyStudent = studentPhyList.size();
        int numChemStudent = studentChemList.size();

        XYChart.Series set2 = new XYChart.Series<>();
        set2.getData().add(new XYChart.Data("English", numEngStudent));
        set2.getData().add(new XYChart.Data("Chinese", numChiStudent));
        set2.getData().add(new XYChart.Data("Math", numMathStudent));
        set2.getData().add(new XYChart.Data("Physics", numPhyStudent));
        set2.getData().add(new XYChart.Data("Chemistry", numChemStudent));
        studentSubject.getData().clear();
        studentSubject.layout();
        studentSubject.getData().addAll(set2);

    }

    @Subscribe
    private void handleShowChartsEvent(ShowChartsEvent event) {
        System.out.println(this);
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadChartsDetails();
    }
}
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
/**
 * Container for both details panel and charts panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private DetailsPanel detailsPanel;
    private ChartsPanel pieChart;

    @FXML
    private StackPane infoPlaceHolder;
    @FXML
    private StackPane detailsPlaceholder;
    @FXML
    private StackPane chartPlaceholder;

    public InfoPanel(ObservableList<Person> personList) {
        super(FXML);

        fillInnerParts(personList);

        detailsPlaceholder.toFront();
        registerAsAnEventHandler(this);
    }

    /**
     * Helper method to fill UI placeholders
     */
    public void fillInnerParts(ObservableList<Person> personList) {
        detailsPanel = new DetailsPanel();
        detailsPlaceholder.getChildren().add(detailsPanel.getRoot());

        pieChart = new ChartsPanel(personList);
        chartPlaceholder.getChildren().add(pieChart.getRoot());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        detailsPanel.loadPersonDetails(event.getNewSelection().person);

        detailsPlaceholder.toFront();
    }

    @Subscribe
    private void handleShowChartsEvent(ShowChartsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        chartPlaceholder.toFront();
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Person person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag));
            tags.getChildren().add(tagLabel);
        });
    }

```
###### \resources\view\ChartsPanel.fxml
``` fxml

<StackPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="606.0" style="-fx-background-color: #ffffff;" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <AnchorPane maxHeight="-Infinity" maxWidth="900" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="606.0" style="-fx-background-color: #ffffff;">
         <children>
            <PieChart fx:id="roleDistribution" prefHeight="195.0" prefWidth="606.0" title="Role Distribution" AnchorPane.bottomAnchor="205.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" />
            <BarChart fx:id="tutorSubject" animated="false" layoutY="200.0" prefHeight="203.0" prefWidth="298.0" title="Tutor Subjects" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="308.0" AnchorPane.topAnchor="200.0">
              <xAxis>
                <CategoryAxis side="BOTTOM" />
              </xAxis>
              <yAxis>
                <NumberAxis side="LEFT" />
              </yAxis>
            </BarChart>
            <BarChart fx:id="studentSubject" animated="false" layoutX="305.0" layoutY="200.0" prefHeight="203.0" prefWidth="298.0" title="Student Subjects" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="308.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="200.0">
              <xAxis>
                <CategoryAxis side="BOTTOM" />
              </xAxis>
              <yAxis>
                <NumberAxis side="LEFT" />
              </yAxis>
            </BarChart>
         </children>
      </AnchorPane>
   </children>
</StackPane>
```
###### \resources\view\DarkTheme.css
``` css
#tags .purple {
    -fx-text-fill: white;
    -fx-background-color: #AB51FF;
}

#tags .red {
    -fx-text-fill: black;
    -fx-background-color: red;
}

#tags .yellow {
    -fx-text-fill: black;
    -fx-background-color: yellow;
}

#tags .blue {
    -fx-text-fill: white;
    -fx-background-color: blue;
}

#tags .orange {
    -fx-text-fill: black;
    -fx-background-color: orange;
}

#tags .brown {
    -fx-text-fill: white;
    -fx-background-color: brown;
}

#tags .green {
    -fx-text-fill: black;
    -fx-background-color: green;
}

#tags .pink {
    -fx-text-fill: black;
    -fx-background-color: pink;
}

#tags .black {
    -fx-text-fill: white;
    -fx-background-color: black;
    -fx-background-color: black;
}

#tags .grey {
    -fx-text-fill: black;
    -fx-background-color: grey;
}
```
###### \resources\view\InfoPanel.fxml
``` fxml
<StackPane prefHeight="700.0" prefWidth="800.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <StackPane fx:id="detailsPlaceholder" prefHeight="150.0" prefWidth="200.0" />
    <StackPane fx:id="chartPlaceholder" prefHeight="150.0" prefWidth="200.0" />
</StackPane>
```
