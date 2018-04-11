# TeyXinHui
###### \java\seedu\address\logic\commands\TagDeleteCommandTest.java
``` java
public class TagDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Tag tagToDelete = new Tag("removeTag");

    @Test
    public void execute_validTagToRemoveEntered_success() throws Exception {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        List<Tag> tagList = addressBook.getTagList();
        Tag removeTag = tagList.get(0);
        TagDeleteCommand tagDeleteCommand = prepareCommand(removeTag);

        String expectedMessage = String.format(TagDeleteCommand.MESSAGE_DELETE_TAG_SUCCESS, removeTag);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(removeTag);

        assertCommandSuccess(tagDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagToRemoveEntered_throwsCommandException() throws IOException {
        TagDeleteCommand tagDeleteCommand = prepareCommand(tagToDelete);
        assertCommandFailure(tagDeleteCommand, model, Messages.MESSAGE_INVALID_TAG_ENTERED);
    }

    @Test
    public void executeUndoRedo_invalidTagToRemoveEntered_failure() throws IOException {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        TagDeleteCommand tagDeleteCommand = prepareCommand(tagToDelete);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(tagDeleteCommand, model, Messages.MESSAGE_INVALID_TAG_ENTERED);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private TagDeleteCommand prepareCommand(Tag removeTag) {
        TagDeleteCommand tagDeleteCommand = new TagDeleteCommand(removeTag);
        tagDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tagDeleteCommand;
    }

}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void removeTag_tagNotFound_throwsTagNotFoundException() {
        AddressBook testCase = new AddressBookBuilder().withPerson(BOB).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(BOB).build();
        try {
            testCase.removeTag(new Tag(VALID_TAG_REMOVE));
        } catch (TagNotFoundException error) {
            assertEquals(error.getMessage(), "Specific tag is not used in the address book.");
        }
        assertEquals(expectedAddressBook, testCase);
    }

    @Test
    public void removeTag_tagFoundOnMultiplePersons_tagRemoved() {
        AddressBook testCase = new AddressBookBuilder().withPerson(BOB).withPerson(AMY).build();
        try {
            testCase.removeTag(new Tag(VALID_TAG_FRIEND));
        } catch (TagNotFoundException error) {
            thrown.expect(TagNotFoundException.class);
        }
        Person amyWithoutFriendTag = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(bobWithoutFriendTag)
                .withPerson(amyWithoutFriendTag).build();
        assertEquals(expectedAddressBook, testCase);
    }
```
###### \java\seedu\address\model\subject\SubjectTest.java
``` java
public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructorWithTwoStringParameters_invalidSubjectName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        String validSubjectGrade = "A2";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidTagName, validSubjectGrade));
    }

    @Test
    public void constructorWithTwoStringParameters_invalidSubjectGrade_throwsIllegalArgumentException() {
        String validTagName = "English";
        String invalidSubjectGrade = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(validTagName, invalidSubjectGrade));
    }

    @Test
    public void constructorWithOneStringPararmeter_validSubjectFormatEntered_success() {
        String validSubjectFormat = "English A1";
        Subject testing = new Subject(validSubjectFormat);
        assertEquals("English", testing.subjectName);
        assertEquals("A1", testing.subjectGrade);
    }

    @Test
    public void isValidSubjectName_invalidSubjectNameEntered_exceptionThrown() {
        // null subject name
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubjectName(null));
        // invalid subject name
        assertFalse(Subject.isValidSubjectName("")); // empty string
        assertFalse(Subject.isValidSubjectName("English*")); // contains non-alphanumeric characters
        assertFalse(Subject.isValidSubjectName("2djs22")); //contains alphanumeric characters

        // valid name
        assertTrue(Subject.isValidSubjectName("English")); // alphabets only
    }

    @Test
    public void isValidSubjectGrade_invalidSubjectGradeEntered_exceptionThrown() {
        // null subject grade
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubjectGrade(null));
        // invalid name
        assertFalse(Subject.isValidSubjectGrade("")); // empty string
        assertFalse(Subject.isValidSubjectGrade(" ")); // spaces only
        assertFalse(Subject.isValidSubjectGrade("^")); // only non-alphanumeric characters
        assertFalse(Subject.isValidSubjectGrade("121*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Subject.isValidSubjectGrade("A1")); // alphabets only
        assertTrue(Subject.isValidSubjectGrade("B3")); // numbers only

    }

    @Test
    public void equals_compareTwoDifferentObjects_notEquals() {
        Subject subject1 = new Subject("English A1");
        Subject subject3  = new Subject("EMath A2");

        //Compare two different subjects
        assertNotEquals(subject1, subject3);
    }

}
```
###### \java\seedu\address\model\subject\UniqueSubjectListTest.java
``` java
public class UniqueSubjectListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueSubjectList uniqueSubjectList = new UniqueSubjectList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueSubjectList.asObservableList().remove(0);
    }

    @Test
    public void testEquals_similarObject_returnTrue() {
        UniqueSubjectList uniqueSubjectList = new UniqueSubjectList();
        UniqueSubjectList uniqueSubjectList1 = new UniqueSubjectList();
        assertEquals(uniqueSubjectList, uniqueSubjectList1);
    }

    @Test
    public void hashCode_checkForHashCode_sameHashCode() {
        UniqueSubjectList uniqueSubjectList = new UniqueSubjectList();
        UniqueSubjectList uniqueSubjectList1 = new UniqueSubjectList();
        assertEquals(uniqueSubjectList.hashCode(), uniqueSubjectList1.hashCode());
    }

}
```
