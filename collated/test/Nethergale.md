# Nethergale
###### \java\seedu\address\logic\commands\AddPlatformCommandTest.java
``` java
public class AddPlatformCommandTest {
    public static final String LINK_STUB = "www.facebook.com/carl.kz";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPlatformLink_failure() {
        String invalidLink = "www.google.com";
        Map<String, SocialMediaPlatform> smpMap = new HashMap<>();
        smpMap.put(Link.FACEBOOK_LINK_TYPE, new Facebook(new Link(invalidLink)));

        AddPlatformCommand addPlatformCommand = prepareCommand(INDEX_THIRD_PERSON, smpMap);

        assertCommandFailure(addPlatformCommand, model, SocialMediaPlatformBuilder.MESSAGE_BUILD_ERROR);
    }

    @Test
    public void execute_addPlatformUnfilteredList_success() throws Exception {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).withPlatforms(LINK_STUB).build();

        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_THIRD_PERSON, editedPerson.getSocialMediaPlatformMap());

        String expectedMessage = String.format(AddPlatformCommand.MESSAGE_ADD_PLATFORM_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(thirdPerson, editedPerson);

        assertCommandSuccess(addPlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_retainAndAddPlatformsUnfilteredList_success() throws Exception {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Set<String> linkSet = new LinkedHashSet<>();
        linkSet.add(LINK_STUB);
        for (String key : secondPerson.getSocialMediaPlatformMap().keySet()) {
            linkSet.add(secondPerson.getSocialMediaPlatformMap().get(key).getLink().value);
        }
        Person editedPerson = new PersonBuilder(secondPerson)
                .withPlatforms(linkSet.toArray(new String[linkSet.size()])).build();

        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_SECOND_PERSON, editedPerson.getSocialMediaPlatformMap());

        String expectedMessage = String.format(AddPlatformCommand.MESSAGE_ADD_PLATFORM_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(secondPerson, editedPerson);

        assertCommandSuccess(addPlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearAllPlatformsUnfilteredList_success() throws Exception {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).withPlatforms("").build();

        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_THIRD_PERSON, editedPerson.getSocialMediaPlatformMap());

        String expectedMessage =
                String.format(AddPlatformCommand.MESSAGE_ADD_PLATFORM_CLEAR_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(thirdPerson, editedPerson);

        assertCommandSuccess(addPlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addPlatformFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withPlatforms(LINK_STUB).build();

        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_FIRST_PERSON, editedPerson.getSocialMediaPlatformMap());

        String expectedMessage = String.format(AddPlatformCommand.MESSAGE_ADD_PLATFORM_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(addPlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPlatformCommand addPlatformCommand = prepareCommand(outOfBoundIndex, SMP_MAP_BOB);

        assertCommandFailure(addPlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book.
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        //Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddPlatformCommand addPlatformCommand = prepareCommand(outOfBoundIndex, SMP_MAP_AMY);

        assertCommandFailure(addPlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms(LINK_STUB).build();
        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_FIRST_PERSON, editedPerson.getSocialMediaPlatformMap());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // addplatform -> first person platforms changed
        addPlatformCommand.execute();
        undoRedoStack.push(addPlatformCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPlatformCommand addPlatformCommand = prepareCommand(outOfBoundIndex, SMP_MAP_AMY);

        // execution failed -> addPlatformCommand not pushed into undoRedoStack
        assertCommandFailure(addPlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies a person's social media platform from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonModified() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<String> linkSet = new LinkedHashSet<>();
        linkSet.add(LINK_STUB);
        for (String key : firstPerson.getSocialMediaPlatformMap().keySet()) {
            linkSet.add(firstPerson.getSocialMediaPlatformMap().get(key).getLink().toString());
        }

        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        AddPlatformCommand addPlatformCommand = prepareCommand(INDEX_FIRST_PERSON,
                SampleDataUtil.getSocialMediaPlatformMap(linkSet.toArray(new String[linkSet.size()])));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit)
                .withPlatforms(linkSet.toArray(new String[linkSet.size()])).build();

        // addplatform -> modifies second person in unfiltered person list / first person in filtered person list
        addPlatformCommand.execute();
        undoRedoStack.push(addPlatformCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(personToEdit, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        Map<String, Link> amyLinkMap = new HashMap<>();
        Map<String, Link> bobLinkMap = new HashMap<>();
        for (String key : SMP_MAP_AMY.keySet()) {
            amyLinkMap.put(key, SMP_MAP_AMY.get(key).getLink());
        }
        for (String key : SMP_MAP_BOB.keySet()) {
            amyLinkMap.put(key, SMP_MAP_BOB.get(key).getLink());
        }

        final AddPlatformCommand standardCommand = new AddPlatformCommand(INDEX_FIRST_PERSON, amyLinkMap);

        // same values -> returns true
        AddPlatformCommand commandWithSameValues = new AddPlatformCommand(INDEX_FIRST_PERSON, amyLinkMap);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddPlatformCommand(INDEX_SECOND_PERSON, amyLinkMap)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddPlatformCommand(INDEX_FIRST_PERSON, bobLinkMap)));
    }

    /**
     * Returns an {@code AddPlatformCommand} with parameters {@code index} and {@code Map<String, SocialMediaPlatform>}.
     */
    private AddPlatformCommand prepareCommand(Index index, Map<String, SocialMediaPlatform> smpMap) {
        Map<String, Link> linkMap = new HashMap<>();
        for (String key : smpMap.keySet()) {
            linkMap.put(key, smpMap.get(key).getLink());
        }
        AddPlatformCommand addPlatformCommand = new AddPlatformCommand(index, linkMap);
        addPlatformCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPlatformCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemovePlatformCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemovePlatformCommand}.
 */
public class RemovePlatformCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removePlatformsWithPlatformFields_success() throws Exception {
        Set<String> platformSet = new HashSet<>();
        platformSet.add(Link.FACEBOOK_LINK_TYPE);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removePlatformsWithPlatformFieldsDifferentCasing_success() throws Exception {
        Set<String> platformSet = new HashSet<>();
        platformSet.add(Link.FACEBOOK_LINK_TYPE.toUpperCase());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removePlatformsWithSomeUnrecognisedPlatformFields_success() throws Exception {
        Set<String> platformSet = new HashSet<>();
        platformSet.add("random");
        platformSet.add(Link.FACEBOOK_LINK_TYPE);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removePlatformsWithAllUnrecognisedPlatformFields_failure() {
        Set<String> platformSet = new HashSet<>();
        platformSet.add("");
        platformSet.add("hello");
        platformSet.add("tester");
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);

        assertCommandFailure(removePlatformCommand, model, RemovePlatformCommand.MESSAGE_PLATFORM_MAP_NOT_EDITED);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemovePlatformCommand removePlatformCommand = prepareCommand(outOfBoundIndex, new HashSet<>());

        assertCommandFailure(removePlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        //Update expectedModel to be filtered
        String[] splitName = personToEdit.getName().fullName.split("\\s+");
        Predicate<Person> predicate = new NameContainsKeywordsPredicate(Arrays.asList(splitName[0]));
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemovePlatformCommand removePlatformCommand = prepareCommand(outOfBoundIndex, new HashSet<>());

        assertCommandFailure(removePlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // removeplatform -> first person's platforms removed
        removePlatformCommand.execute();
        undoRedoStack.push(removePlatformCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person's platforms deleted again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemovePlatformCommand removePlatformCommand = prepareCommand(outOfBoundIndex, new HashSet<>());

        // execution failed -> removePlatformCommand not pushed into undoRedoStack
        assertCommandFailure(removePlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies a {@code Person} from a filtered list by removing the stated social media platform.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonModified() throws Exception {
        Set<String> platformSet = new HashSet<>();
        platformSet.add(Link.TWITTER_LINK_TYPE);
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        // removeplatform -> removes the Twitter platform from the second person in unfiltered person list /
        // first person in filtered person list
        removePlatformCommand.execute();
        undoRedoStack.push(removePlatformCommand);

        // undo -> reverts address book back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(personToEdit, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> edits the same second person in unfiltered person list and removing the Twitter platform
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        RemovePlatformCommand removePlatformFirstCommand = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());
        RemovePlatformCommand removePlatformSecondCommand = prepareCommand(INDEX_SECOND_PERSON, new HashSet<>());

        // same object -> returns true
        assertTrue(removePlatformFirstCommand.equals(removePlatformFirstCommand));

        // same values -> returns true
        RemovePlatformCommand removePlatformFirstCommandCopy = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());
        assertTrue(removePlatformFirstCommand.equals(removePlatformFirstCommandCopy));

        // different types -> returns false
        assertFalse(removePlatformFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removePlatformFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(removePlatformFirstCommand.equals(removePlatformSecondCommand));
    }

    /**
     * Returns a {@code RemovePlatformCommand} with the parameters {@code index} and {@code platformSet}.
     */
    private RemovePlatformCommand prepareCommand(Index index, Set<String> platformSet) {
        RemovePlatformCommand removePlatformCommand = new RemovePlatformCommand(index, platformSet);
        removePlatformCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removePlatformCommand;
    }

}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model customModel;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        customModel = new ModelManager(generateModelWithPersons(generatePersonList(
                TypicalPersons.JOHN3, TypicalPersons.JOHN2, TypicalPersons.JANE, TypicalPersons.BLAKE,
                TypicalPersons.HOB2, TypicalPersons.JOHN1, TypicalPersons.LEONARD, TypicalPersons.HOB1
        )).getAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(generateModelWithPersons(generatePersonList(
                TypicalPersons.BLAKE, TypicalPersons.HOB1, TypicalPersons.HOB2, TypicalPersons.JANE,
                TypicalPersons.JOHN1, TypicalPersons.JOHN2, TypicalPersons.JOHN3, TypicalPersons.LEONARD
        )).getAddressBook(), new UserPrefs());
        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_unfilteredListAlreadySorted_failure() {
        assertCommandFailure(sortCommand, model, SortCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_filteredListAlreadySorted_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandFailure(sortCommand, model, SortCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_emptyList_failure() {
        model.resetData(new AddressBook());
        assertCommandFailure(sortCommand, model, Messages.MESSAGE_ADDRESS_BOOK_EMPTY);
    }

    @Test
    public void execute_unfilteredListUnsorted_success() {
        model.resetData(customModel.getAddressBook());
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_filteredListUnsorted_success() {
        String[] keywords = {"jane", "blake", "hob"};
        Predicate<Person> predicate = new NameContainsKeywordsPredicate(Arrays.asList(keywords));

        model.resetData(customModel.getAddressBook());
        model.updateFilteredPersonList(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_unfilteredList_success() throws Exception {
        model.resetData(customModel.getAddressBook());
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        // sort -> sorts all persons in address book
        sortCommand.execute();
        undoRedoStack.push(sortCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, customModel);

        // redo -> sorts all persons in address book again
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * 1. Sorts all persons from a filtered list, such that the list is kept.
     * 2. Undo the sorting.
     * 3. The unfiltered list should be shown now. Verify that the list is reverted to before it is sorted.
     * 4. Redo the sorting. The list shown should still be unfiltered.
     */
    @Test
    public void executeUndoRedo_filteredList_success() {
        model.resetData(customModel.getAddressBook());
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        showPersonAtIndex(model, INDEX_THIRD_PERSON);
        showPersonAtIndex(expectedModel, Index.fromZeroBased(
                expectedModel.getFilteredPersonList().indexOf(model.getFilteredPersonList().get(0))));

        // sort -> sorts all persons in address book
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
        undoRedoStack.push(sortCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, customModel);

        // redo -> sorts all persons in address book again
        expectedModel.updateFilteredPersonList(unused -> true);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
    /**
     * Creates a model with all persons found in the list added.
     */
    private Model generateModelWithPersons(List<Person> personList) throws Exception {
        Model m = new ModelManager();
        for (Person p : personList) {
            m.addPerson(p);
        }
        return m;
    }
}
```
###### \java\seedu\address\logic\parser\AddPlatformCommandParserTest.java
``` java
public class AddPlatformCommandParserTest {

    private static final String FACEBOOK_LINK_FIELD_AMY = " " + PREFIX_LINK + VALID_FACEBOOK_LINK_AMY;
    private static final String TWITTER_LINK_FIELD_AMY = " " + PREFIX_LINK + VALID_TWITTER_LINK_AMY;
    private static final String TWITTER_LINK_FIELD_BOB = " " + PREFIX_LINK + VALID_TWITTER_LINK_BOB;

    private static final String INVALID_LINK_1 = " " + PREFIX_LINK + "www.google.com";
    private static final String INVALID_LINK_2 = " " + PREFIX_LINK + "www.facebook.com";
    private static final String INVALID_LINK_3 = " " + PREFIX_LINK + "www.twitter.com";

    private static final String LINK_EMPTY = " " + PREFIX_LINK;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPlatformCommand.MESSAGE_USAGE);

    private AddPlatformCommandParser parser = new AddPlatformCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_FACEBOOK_LINK_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", AddPlatformCommand.MESSAGE_LINK_COLLECTION_EMPTY);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + FACEBOOK_LINK_FIELD_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + FACEBOOK_LINK_FIELD_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_LINK_1, Link.MESSAGE_INVALID_LINK); // unrecognised link
        assertParseFailure(parser, "1" + INVALID_LINK_2, Link.MESSAGE_INVALID_LINK); // invalid facebook link
        assertParseFailure(parser, "1" + INVALID_LINK_3, Link.MESSAGE_INVALID_LINK); // invalid twitter link

        // valid facebook link followed by invalid facebook link
        assertParseFailure(parser, "1" + FACEBOOK_LINK_FIELD_AMY + INVALID_LINK_2, Link.MESSAGE_INVALID_LINK);
        // invalid facebook link followed by valid facebook link
        assertParseFailure(parser, "1" + INVALID_LINK_2 + FACEBOOK_LINK_FIELD_AMY, Link.MESSAGE_INVALID_LINK);

        // valid twitter link followed by invalid twitter link
        assertParseFailure(parser, "1" + TWITTER_LINK_FIELD_AMY + INVALID_LINK_3, Link.MESSAGE_INVALID_LINK);
        // invalid twitter link followed by valid twitter link
        assertParseFailure(parser, "1" + INVALID_LINK_3 + TWITTER_LINK_FIELD_AMY, Link.MESSAGE_INVALID_LINK);

        //multiple empty link fields
        assertParseFailure(parser, "1" + LINK_EMPTY + LINK_EMPTY, Link.MESSAGE_INVALID_LINK);
    }

    @Test
    public void parse_multipleLinksForSamePlatform_failure() {
        assertParseFailure(parser,
                "1" + TWITTER_LINK_FIELD_AMY + TWITTER_LINK_FIELD_BOB, Link.MESSAGE_LINK_CONSTRAINTS);
    }

    @Test
    public void parse_oneLinkFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + FACEBOOK_LINK_FIELD_AMY;
        Map<String, Link> linkMap = new HashMap<>();
        linkMap.put(Link.getLinkType(VALID_FACEBOOK_LINK_AMY), new Link(VALID_FACEBOOK_LINK_AMY));
        AddPlatformCommand expectedCommand = new AddPlatformCommand(targetIndex, linkMap);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + TWITTER_LINK_FIELD_AMY;
        linkMap.clear();
        linkMap.put(Link.getLinkType(VALID_TWITTER_LINK_AMY), new Link(VALID_TWITTER_LINK_AMY));
        expectedCommand = new AddPlatformCommand(targetIndex, linkMap);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleLinkFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + FACEBOOK_LINK_FIELD_AMY + TWITTER_LINK_FIELD_AMY;
        Map<String, Link> linkMap = new HashMap<>();
        linkMap.put(Link.getLinkType(VALID_FACEBOOK_LINK_AMY), new Link(VALID_FACEBOOK_LINK_AMY));
        linkMap.put(Link.getLinkType(VALID_TWITTER_LINK_AMY), new Link(VALID_TWITTER_LINK_AMY));
        AddPlatformCommand expectedCommand = new AddPlatformCommand(targetIndex, linkMap);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_clearPlatforms_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + LINK_EMPTY;

        AddPlatformCommand expectedCommand = new AddPlatformCommand(targetIndex, Collections.emptyMap());

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }

    @Test
    public void parseCommand_addPlatform() throws Exception {
        String testLink = "www.facebook.com/example";
        Map<String, Link> linkMap = new HashMap<>();
        linkMap.put(Link.getLinkType(testLink), new Link(testLink));
        AddPlatformCommand command = (AddPlatformCommand) parser.parseCommand(AddPlatformCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + CliSyntax.PREFIX_LINK + testLink);

        assertEquals(new AddPlatformCommand(INDEX_FIRST_PERSON, linkMap), command);
    }

    @Test
    public void parseCommand_removePlatform() throws Exception {
        String platform = "facebook";
        Set<String> platformSet = new HashSet<>();
        platformSet.add(platform);
        RemovePlatformCommand command = (RemovePlatformCommand) parser.parseCommand(RemovePlatformCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + CliSyntax.PREFIX_SOCIAL_MEDIA_PLATFORM + platform);

        assertEquals(new RemovePlatformCommand(INDEX_FIRST_PERSON, platformSet), command);
    }

```
###### \java\seedu\address\logic\parser\RemovePlatformCommandParserTest.java
``` java
public class RemovePlatformCommandParserTest {

    private static final String FACEBOOK_PLATFORM_FIELD = " " + PREFIX_SOCIAL_MEDIA_PLATFORM + Link.FACEBOOK_LINK_TYPE;
    private static final String TWITTER_PLATFORM_FIELD = " " + PREFIX_SOCIAL_MEDIA_PLATFORM + Link.TWITTER_LINK_TYPE;

    private RemovePlatformCommandParser parser = new RemovePlatformCommandParser();
    private Set<String> platformSet = new HashSet<>();

    @Test
    public void parse_validArgsNoPlatformFieldsSpecified_returnsRemovePlatformCommand() {
        assertParseSuccess(parser, "1", new RemovePlatformCommand(INDEX_FIRST_PERSON, platformSet));
    }

    @Test
    public void parse_validArgsPlatformFieldsSpecified_returnsRemovePlatformCommand() {
        platformSet.add(Link.FACEBOOK_LINK_TYPE);
        platformSet.add(Link.TWITTER_LINK_TYPE);
        assertParseSuccess(parser, "1" + FACEBOOK_PLATFORM_FIELD + TWITTER_PLATFORM_FIELD,
                new RemovePlatformCommand(INDEX_FIRST_PERSON, platformSet));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePlatformCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
        // with platforms
        String[] links = {"www.facebook.com/examplepage", "www.twitter.com/examplepage"};
        Person personWithPlatforms = new PersonBuilder().withPlatforms(links).build();
        personCard = new PersonCard(personWithPlatforms, 3);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithPlatforms, 3);

        // with platforms purposely put into wrong key, should not display any icons
        Map<String, SocialMediaPlatform> customSmpMap = new HashMap<String, SocialMediaPlatform>();
        customSmpMap.put(Link.UNKNOWN_LINK_TYPE, new Facebook(new Link("www.facebook.com/testlink")));
        Set<Tag> defaultTags = new HashSet<>();
        defaultTags.add(new Tag("friends"));

        Person personWithIncorrectSmpMap = new Person(
                new Name("Alice Pauline"), new Phone("85355255"),
                new Email("alice@gmail.com"), new Address("123, Jurong West Ave 6, #08-111"),
                customSmpMap, defaultTags);
        personCard = new PersonCard(personWithIncorrectSmpMap, 4);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithIncorrectSmpMap, 4);
```
