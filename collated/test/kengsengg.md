# kengsengg
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public List<String> getTagStyles(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */

public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String parameter = "name";

        sortCommand = new SortCommand(parameter);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void showsSortedList() throws IOException {
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS_SORT_BY_NAME, expectedModel);
    }
}
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
    /**
     * Asserts that {@code personCard} matches the tag details and color of {@code expectedPerson} correctly
     */
    private static void assertTagsMatching(Person expectedPerson, PersonCardHandle personCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, personCard.getTags());
    }
```
