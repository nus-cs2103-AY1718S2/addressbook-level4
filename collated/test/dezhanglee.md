# dezhanglee
###### \java\seedu\address\logic\commands\AddPictureCommandTest.java
``` java
public class AddPictureCommandTest {

    private ModelManager model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
    private Index index = INDEX_FIRST_PERSON;

    @Test
    public void execute_validFilepathUnfilteredList_success() throws Exception {


        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.setPicture(VALID_LOCAL_IMAGE_JPG);
        AddPictureCommand addPictureCommand = prepareCommand(index, VALID_LOCAL_IMAGE_JPG);
        String expectedMessage = String.format(AddPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(addPictureCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_validFilepathFilteredList_success() throws Exception {

        showPersonAtIndex(model, index);

        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.setPicture(VALID_LOCAL_IMAGE_JPG);
        AddPictureCommand addPictureCommand = prepareCommand(index, VALID_LOCAL_IMAGE_JPG);
        String expectedMessage = String.format(AddPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(addPictureCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListInvalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPictureCommand addPictureCommand = prepareCommand(outOfBoundIndex, VALID_LOCAL_IMAGE_JPG);

        assertCommandFailure(addPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        AddPictureCommand addPicCommandFirst = prepareCommand(INDEX_FIRST_PERSON, VALID_LOCAL_IMAGE_JPG);
        AddPictureCommand addPicCommandSecond = prepareCommand(INDEX_SECOND_PERSON, VALID_LOCAL_IMAGE_PNG);

        // same object -> returns true
        assertTrue(addPicCommandFirst.equals(addPicCommandFirst));

        // same values -> returns true
        AddPictureCommand addPicCommandFirstCopy = prepareCommand(INDEX_FIRST_PERSON, VALID_LOCAL_IMAGE_JPG);
        assertTrue(addPicCommandFirstCopy.equals(addPicCommandFirst));

        // different types -> returns false
        assertFalse(addPicCommandFirst.equals(1));

        // null -> returns false
        assertFalse(addPicCommandFirst.equals(null));

        // different indexes -> returns false
        AddPictureCommand apc = prepareCommand(INDEX_SECOND_PERSON, VALID_LOCAL_IMAGE_JPG);
        assertFalse(apc.equals(addPicCommandFirst));

        // same indexes, different files -> returns false
        assertFalse(apc.equals(addPicCommandSecond));

        // both index and files different -> returns false
        assertFalse(addPicCommandSecond.equals(addPicCommandFirst));

    }


    /**
     * Returns an {@code AddPictureCommand} with parameters {@code index} and {@code path}
     */
    private AddPictureCommand prepareCommand(Index index, String path) {
        AddPictureCommand addPictureCommand = new AddPictureCommand(index, path);
        addPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPictureCommand;
    }
}
```
###### \java\seedu\address\logic\commands\AddTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddTagCommand.
 */
public class AddTagCommandTest {

    private static final String VALID_TAG_1 = "NUS";
    private static final String VALID_TAG_2 = "CS2103";
    private static final ArrayList<String> toAdd = new ArrayList<String>(Arrays.asList(VALID_TAG_1, VALID_TAG_2));

    private Model model = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), getTypicalEventBook(),
            getTypicalTaskBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        Person editedPerson = new Person(lastPerson);

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        editedPerson.addTags(tags);

        AddTagCommand addTagCommand = prepareCommand(indexLastPerson, tags);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new TaskBook(model.getTaskBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new Person(personInFilteredList);

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, tags);
        editedPerson.addTags(tags);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new TaskBook(model.getTaskBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTagUnfilteredList_failure() throws Exception {

        Person firstPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, firstPerson.getTags());

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_TAGS_MUST_NOT_EXIST);
    }

    @Test
    public void execute_duplicateTagFilteredList_failure() throws Exception {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, personInList.getTags());

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_TAGS_MUST_NOT_EXIST);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, tags);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, tags);
        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        Set<Tag> moreTags = ParserUtil.parseTags(Arrays.asList("qwerty", "uiop"));

        final AddTagCommand standardCommand = new AddTagCommand(INDEX_FIRST_PERSON, tags);

        // same values -> returns true
        Set<Tag> copyTags = tags;
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST_PERSON, copyTags);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_SECOND_PERSON, tags)));

        // different tags -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_FIRST_PERSON, moreTags)));
    }

    /**
     * Returns an {@code AddTagCommand} with parameters {@code index} and {@code tags}
     */
    private AddTagCommand prepareCommand(Index index, Set<Tag> tags) {
        AddTagCommand addTagCommand = new AddTagCommand(index, tags);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }

}
```
###### \java\seedu\address\logic\commands\DeleteByNameCommandTest.java
``` java
public class DeleteByNameCommandTest {

    private Model model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() throws Exception {

        Person personToDelete = ALICE;
        DeleteByNameCommand deleteByNameCommand = prepareCommand(ALICE.getName());

        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {

        Name randomName = new Name("Random Random Random 242neklw");
        DeleteByNameCommand deleteByNameCommand = prepareCommand(randomName);

        assertCommandFailure(deleteByNameCommand, model, DeleteByNameCommand.MESSAGE_NAME_NOT_FOUND);
    }


    @Test
    public void execute_invalidPartialName_throwsCommandException() {

        String aliceFirstName = "pauline";

        DeleteByNameCommand deleteByNameCommand = prepareCommand(new Name(aliceFirstName));

        assertCommandFailure(deleteByNameCommand, model, DeleteByNameCommand.MESSAGE_NAME_NOT_FOUND);

    }


    @Test
    public void execute_multiplePersonsWithSameName_throwsCommandException() throws Exception {

        Person alice1 = new PersonBuilder().withName(ALICE.getName().toString())
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com").withPhone("98765432")
                .withTags("owesMoney", "friends").build();
        model.addPerson(alice1);

        DeleteByNameCommand deleteByNameCommand = prepareCommand(alice1.getName());

        assertCommandFailure(deleteByNameCommand, model, DeleteByNameCommand.MESSAGE_MULTIPLE_SAME_NAME);
    }



    @Test
    public void equals() {
        DeleteByNameCommand deleteFirstCommand = new DeleteByNameCommand(ALICE.getName());
        DeleteByNameCommand deleteSecondCommand = new DeleteByNameCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByNameCommand deleteFirstCommandCopy = new DeleteByNameCommand(ALICE.getName());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteByNameCommand} with the parameter {@code index}.
     */
    private DeleteByNameCommand prepareCommand(Name name) {
        DeleteByNameCommand deleteByNameCommand = new DeleteByNameCommand(name);
        deleteByNameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByNameCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}

```
###### \java\seedu\address\logic\commands\ResetPictureCommandTest.java
``` java
public class ResetPictureCommandTest {

    private ModelManager model =
            new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
    private Index index = INDEX_FIRST_PERSON;

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {


        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.resetPicture();
        ResetPictureCommand resetPictureCommand = prepareCommand(index);
        String expectedMessage = String.format(ResetPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(resetPictureCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {

        showPersonAtIndex(model, index);

        Person toUpdatePerson = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(toUpdatePerson);

        updatedPerson.resetPicture();
        ResetPictureCommand resetPictureCommand = prepareCommand(index);
        String expectedMessage = String.format(ResetPictureCommand.MESSAGE_EDIT_PERSON_SUCCESS, index.getOneBased());
        Model expectedModel =
                new ModelManager(model.getAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPerson);

        assertCommandSuccess(resetPictureCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_filteredListInvalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ResetPictureCommand resetPictureCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(resetPictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        ResetPictureCommand resetPicCommandFirst = prepareCommand(INDEX_FIRST_PERSON);
        ResetPictureCommand resetPicCommandSecond = prepareCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(resetPicCommandFirst.equals(resetPicCommandFirst));

        // same values -> returns true
        ResetPictureCommand resetPicCommandFirstCopy = prepareCommand(INDEX_FIRST_PERSON);
        assertTrue(resetPicCommandFirstCopy.equals(resetPicCommandFirst));

        // different types -> returns false
        assertFalse(resetPicCommandFirst.equals(1));

        // null -> returns false
        assertFalse(resetPicCommandFirst.equals(null));

        // different indexes -> returns false
        assertFalse(resetPicCommandSecond.equals(resetPicCommandFirst));

    }


    /**
     * Returns an {@code ResetPictureCommand} with parameters {@code index}
     */
    private ResetPictureCommand prepareCommand(Index index) {
        ResetPictureCommand resetPictureCommand = new ResetPictureCommand(index);
        resetPictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return resetPictureCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddPictureCommandParserTest.java
``` java
public class AddPictureCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPictureCommand.MESSAGE_USAGE);

    private AddPictureCommandParser parser = new AddPictureCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser, VALID_LOCAL_IMAGE_JPG, MESSAGE_INVALID_FORMAT);

        //no picture specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        //no index or picture specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_LOCAL_IMAGE_JPG, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_LOCAL_IMAGE_JPG, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        //non-image file without image prefix
        assertParseFailure(parser, "1" + INVALID_PIC_DESC_NONIMAGE, MESSAGE_INVALID_FORMAT);
        //non-image file with image prefix
        assertParseFailure(parser, "1" + INVALID_PIC_DESC_NONIMAGE_WITH_IMAGE_FILETYPE,
                MESSAGE_INVALID_FORMAT);
        //image file but bigger than 5MB
        assertParseFailure(parser, "1" + INVALID_PIC_DESC_IMAGE_GREATER_THAN_5MB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validLocalImage_success() {

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = "1" + PICTURE_DESC_LOCAL_IMAGE;
        AddPictureCommand expectedCommand = new AddPictureCommand(targetIndex, VALID_LOCAL_IMAGE_JPG);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws Exception {

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput;
        AddPictureCommand expectedCommand;

        //images for both fields valid
        userInput = targetIndex.getOneBased() + PICTURE_DESC_LOCAL_IMAGE_5MB + PICTURE_DESC_LOCAL_IMAGE;
        expectedCommand = new AddPictureCommand(targetIndex, VALID_LOCAL_IMAGE_JPG);
        assertParseSuccess(parser, userInput, expectedCommand);

        //image for first field invalid, second field valid
        userInput = targetIndex.getOneBased() + INVALID_PIC_DESC_NONIMAGE_WITH_IMAGE_FILETYPE
                + PICTURE_DESC_LOCAL_IMAGE;
        expectedCommand = new AddPictureCommand(targetIndex, VALID_LOCAL_IMAGE_JPG);
        assertParseSuccess(parser, userInput, expectedCommand);

        //image for first field valid, second field invalid
        userInput = targetIndex.getOneBased() + PICTURE_DESC_LOCAL_IMAGE
                + INVALID_PIC_DESC_NONIMAGE_WITH_IMAGE_FILETYPE;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

}
```
###### \java\seedu\address\logic\parser\AddTagCommandParserTest.java
``` java
public class AddTagCommandParserTest {

    private static final String VALID_TAG_1 = "NUS";
    private static final String VALID_TAG_2 = "CS2103";
    private static final ArrayList<String> toAdd = new ArrayList<String>(Arrays.asList(VALID_TAG_1, VALID_TAG_2));

    public static final String TAG_DESC_CS2103 = " " + PREFIX_TAG + VALID_TAG_1;
    public static final String TAG_DESC_NUS = " " + PREFIX_TAG + VALID_TAG_2;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TAG_1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TAG_2, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); //invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC + VALID_TAG_1, Tag.MESSAGE_TAG_CONSTRAINTS); //invalid tag
    }

    @Test
    public void parse_validValue_success() throws Exception {
        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        AddTagCommand expectedCommand = new AddTagCommand(INDEX_FIRST_PERSON, tags);
        assertParseSuccess(parser, "1 " + TAG_DESC_NUS + " " + TAG_DESC_CS2103, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteByNameCommandParserTest.java
``` java
public class DeleteByNameCommandParserTest {

    private DeleteByNameCommandParser parser = new DeleteByNameCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByNameCommand() throws IllegalValueException {

        Name amy = new Name(VALID_NAME_AMY);

        DeleteByNameCommand expected = new DeleteByNameCommand(amy);

        assertParseSuccess(parser, VALID_NAME_AMY, expected);

        //leading whitespaces
        assertParseSuccess(parser, " " + VALID_NAME_AMY, expected);

        //leading and trailing whitespaces
        assertParseSuccess(parser, " " + VALID_NAME_AMY + "  ", expected);

        //trailing whitespaces
        assertParseSuccess(parser, VALID_NAME_AMY + "  ", expected);

        //non alphabatical input
        Name nonAlphabatical = new Name("123");
        assertParseSuccess(parser, "123", new DeleteByNameCommand(nonAlphabatical));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        //blank input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
        //whitespaces as input
        assertParseFailure(parser, "              ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //single special character
        assertParseFailure(parser, "@",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //multiple special characters
        assertParseFailure(parser, "@$%^&*(",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //special character between valid characters
        assertParseFailure(parser, "M@ry J@ne",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Other ascii characters
        assertParseFailure(parser, "ε",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\logic\parser\ResetPictureCommandParserTest.java
``` java
public class ResetPictureCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetPictureCommand.MESSAGE_USAGE);

    private ResetPictureCommandParser parser = new ResetPictureCommandParser();

    @Test
    public void parse_validArgs_returnsResetPictureCommand() {
        assertParseSuccess(parser, "1", new ResetPictureCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidInput_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // multiple repeated index
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_FORMAT);

        // multiple distinct index
        assertParseFailure(parser, "1 2", MESSAGE_INVALID_FORMAT);


        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

    }

}
```
###### \java\seedu\address\model\person\PictureTest.java
``` java
public class PictureTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Picture(null));
    }

    @Test
    public void constructorForXml_invalidPath_setPathToDefaultPath() {
        String invalidName =
                "RandomRandomThisIsTooLongToBeValidButToBeSureLetsMakeThisLonger_MakePicturesGreatAgain!?>>:?"
                        + "SurelyThisCantBeValidRight?WellLifeIsUnpredictableSoToBeSafeIWillMakeThisEvenLonger";
        // if input file invalid, then we will set path to be Picture.DEFAULT_PATH. In this case, both constructors
        // are the same
        assertEquals(new Picture(invalidName), new Picture());
    }

    @Test
    public void constructor_invalidPath_throwsIllegalArgumentException() {
        String invalidName =
                "RandomRandomThisIsTooLongToBeValidButToBeSureLetsMakeThisLonger_MakePicturesGreatAgain!?>>:?"
                        + "SurelyThisCantBeValidRight?WellLifeIsUnpredictableSoToBeSafeIWillMakeThisEvenLonger";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Picture(invalidName, "randomNameDoesntMatter"));
    }



    @Test
    public void isValidPath() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Picture.isValidPath(null));

        // invalid name
        assertFalse(Picture.isValidPath("")); // empty string
        assertFalse(Picture.isValidPath(" ")); // spaces only
        // points to non image file with image filetype (jpg)
        assertFalse(Picture.isValidPath(INVALID_LOCAL_FILE_NONIMAGE_WITH_IMAGE_FILETYPE));
        assertFalse(Picture.isValidPath(INVALID_LOCAL_FILE_NONIMAGE)); // points to non image file
        assertFalse(Picture.isValidPath(VALID_LOCAL_IMAGE_BIGGER_THAN_5MB)); // points to image file bigger than 5mb

        // valid name
        assertTrue(Picture.isValidPath(VALID_LOCAL_IMAGE_PNG)); // valid png file
        assertTrue(Picture.isValidPath(VALID_LOCAL_IMAGE_JPG)); // valid png file
    }

}
```
