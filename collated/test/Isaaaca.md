# Isaaaca
###### \java\seedu\address\commons\util\CalendarUtilTest.java
``` java
public class CalendarUtilTest {

    @Test
    public void getSem() {
        Assert.assertEquals(2, CalendarUtil.getSem(LocalDate.MIN));
        Assert.assertEquals(1, CalendarUtil.getSem(LocalDate.MAX));
    }

    @Test
    public void getCurrentSemester() {
        Assert.assertEquals(CalendarUtil.getSem(LocalDate.now()), CalendarUtil.getCurrentSemester());
    }

    @Test
    public void getAcadYear() {
        Assert.assertEquals(2016, CalendarUtil.getAcadYear(LocalDate.of(2017, 1, 1)));
        Assert.assertEquals(2017, CalendarUtil.getAcadYear(LocalDate.of(2017, 7, 1)));
    }

    @Test
    public void getCurrAcadYear() {
        Assert.assertEquals(CalendarUtil.getAcadYear(LocalDate.now()), CalendarUtil.getCurrAcadYear());
    }
}
```
###### \java\seedu\address\commons\util\JsonUtilTest.java
``` java
    @Test
    public void deserializeArrayFromJsonFile_noExceptionThrown() throws DataConversionException, IOException {
        FileUtil.writeToFile(SERIALIZATION_ARRAY_FILE, "[" + SerializableTestClass.JSON_STRING_REPRESENTATION
                + "," + SerializableTestClass.JSON_STRING_REPRESENTATION + "]");

        List<SerializableTestClass> serializableTestClassList = JsonUtil
                .readJsonArrayFromFile(SERIALIZATION_ARRAY_FILE.getPath(), SerializableTestClass.class);
        List<SerializableTestClass> expected = new ArrayList<>();

        SerializableTestClass serializableTestClass1 = new SerializableTestClass();
        SerializableTestClass serializableTestClass2 = new SerializableTestClass();
        serializableTestClass1.setTestValues();
        serializableTestClass2.setTestValues();
        expected.add(serializableTestClass1);
        expected.add(serializableTestClass2);

        assertEquals(expected.size(), serializableTestClassList.size());

        for (int i = 0; i < expected.size(); i++) {
            SerializableTestClass expectedObject = expected.get(i);
            SerializableTestClass actualObject = serializableTestClassList.get(i);

            assertEquals(expectedObject.getName(), actualObject.getName());
            assertEquals(expectedObject.getListOfLocalDateTimes(), actualObject.getListOfLocalDateTimes());
            assertEquals(expectedObject.getMapOfIntegerToString(), actualObject.getMapOfIntegerToString());
        }
    }
```
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
    //---------------- Tests for containsWordFuzzyIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordFuzzyIgnoreCase_nullWord_throwsNullPointerException() {
        assertExceptionThrownFuzzy(NullPointerException.class, "typical sentence", null, Optional.empty());
    }

    private void assertExceptionThrownFuzzy(Class<? extends Throwable> exceptionClass, String sentence, String word,
            Optional<String> errorMessage) {
        thrown.expect(exceptionClass);
        errorMessage.ifPresent(message -> thrown.expectMessage(message));
        StringUtil.getPartialRatioFuzzyIgnoreCase(sentence, word);
    }

    @Test
    public void containsWordFuzzyIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertExceptionThrownFuzzy(IllegalArgumentException.class, "typical sentence", "  ",
                Optional.of("Word parameter cannot be empty"));
    }

    @Test
    public void containsWordFuzzyIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertExceptionThrownFuzzy(IllegalArgumentException.class, "typical sentence", "aaa BBB",
                Optional.of("Word parameter should be a single word"));
    }

    @Test
    public void containsWordFuzzyIgnoreCase_nullSentence_throwsNullPointerException() {
        assertExceptionThrown(NullPointerException.class, null, "abc", Optional.empty());
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordFuzzyIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertEquals(0, StringUtil.getPartialRatioFuzzyIgnoreCase("", "abc")); // Boundary case
        assertEquals(0, StringUtil.getPartialRatioFuzzyIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertEquals(100, StringUtil.getPartialRatioFuzzyIgnoreCase(
                "aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertEquals(75, StringUtil.getPartialRatioFuzzyIgnoreCase(
                "aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertEquals(100, StringUtil.getPartialRatioFuzzyIgnoreCase(
                "aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertEquals(100, StringUtil.getPartialRatioFuzzyIgnoreCase(
                "aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertEquals(100, StringUtil.getPartialRatioFuzzyIgnoreCase(
                "  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertEquals(100, StringUtil.getPartialRatioFuzzyIgnoreCase(
                "Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertEquals(100, StringUtil.getPartialRatioFuzzyIgnoreCase(
                "aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }
```
###### \java\seedu\address\database\DatabaseManagerTest.java
``` java
public class DatabaseManagerTest {

    private DatabaseManager test = DatabaseManager.getInstance();

    @Test
    public void getQuery() {
        String actual = DatabaseManager.getQuery(new TimeTableLink("http://modsn.us/MYwiD"));
        assertEquals("CS2101=SEC:3&CS2103T=TUT:T3&CS2105=LEC:1,TUT:7&CS3242=LEC:1,TUT:3&ST2334=LEC:SL1,TUT:T4",
                actual);
        actual = DatabaseManager.getQuery(new TimeTableLink("http://modsn.us/MYwid"));
        assertEquals(null,
                actual);
    }

    @Test
    public void parseEvents_success() {
        ArrayList<WeeklyEvent> expected = new ArrayList<>();
        Module cs2103t = new Module("CS2103T", "Software Engineering");
        Schedule tutT3 = new Schedule("T3", "Tutorial", "Every Week", "Wednesday",
                "1500", "1600", "COM1-B103");
        WeeklyEvent weeklyEvent = new WeeklyEvent(cs2103t, tutT3);
        expected.add(weeklyEvent);
        ArrayList<WeeklyEvent> actual = DatabaseManager.parseEvents(new TimeTableLink("http://modsn.us/Oy25S"));
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void parseEvents_invalidLink_returnsEmptyList() {
        ArrayList<WeeklyEvent> expected = new ArrayList<>();
        ArrayList<WeeklyEvent> actual = DatabaseManager.parseEvents(new TimeTableLink("http://modsn.us/abcde"));
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void parseEvents_incompatibleLink_returnsEmptyList() {
        ArrayList<WeeklyEvent> expected = new ArrayList<>();
        ArrayList<WeeklyEvent> actual = DatabaseManager.parseEvents(new TimeTableLink("http://modsn.us/oEZ0r"));
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }



}
```
###### \java\seedu\address\database\module\ModuleTest.java
``` java
public class ModuleTest {

    private static final String DEFAULT_MODULE_CODE = "CS2013T";
    private static final String DEFAULT_MODULE_TITLE = "Software Engineering";
    private static final Module test = new Module(DEFAULT_MODULE_CODE, DEFAULT_MODULE_TITLE, new ArrayList<>());

    @Test
    public void constructor_nullArgument_throwsNullPointerException() {
        String invalidArg = null;
        Assert.assertThrows(NullPointerException.class, () -> new Module(invalidArg, invalidArg, null));

    }

    @Test
    public void getModuleCode() {
        assertEquals(test.getModuleCode(), DEFAULT_MODULE_CODE);
    }

    @Test
    public void getModuleTitle() {
        assertEquals(test.getModuleTitle(), DEFAULT_MODULE_TITLE);
    }

    @Test
    public void getScheduleList() {
        assertEquals(test.getScheduleList(), new ArrayList<>());
    }

    @Test
    public void testToString() {
        assertEquals(test.toString(),
                "moduleCode: " + DEFAULT_MODULE_CODE + " moduleTitle: " + DEFAULT_MODULE_TITLE + "\n"
                        + new ArrayList<>().toString());

    }

    @Test
    public void testEquals() {
        assertTrue(test.equals(test));
        assertTrue(test.equals(new Module(DEFAULT_MODULE_CODE, DEFAULT_MODULE_TITLE)));
        assertFalse(test.equals(new Module("CS1101", DEFAULT_MODULE_TITLE)));
        assertFalse(test.equals(DEFAULT_MODULE_CODE));
    }
}
```
###### \java\seedu\address\database\module\ScheduleTest.java
``` java
public class ScheduleTest {
    private static final String DEFAULT_CLASSNO = "9";
    private static final String DEFAULT_LESSON_TYPE = "Tutorial";
    private static final String DEFAULT_WEEK_TEXT = "EVERY WEEK";
    private static final String DEFAULT_DAY_TEXT = "WEDNESDAY";
    private static final String DEFAULT_START_TIME = "1500";
    private static final String DEFAULT_END_TIME = "1600";
    private static final String DEFAULT_VENUE = "COM1 B1-04";


    private Schedule testBlank = new Schedule();
    private Schedule test = new Schedule(DEFAULT_CLASSNO, DEFAULT_LESSON_TYPE, DEFAULT_WEEK_TEXT, DEFAULT_DAY_TEXT,
            DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_VENUE);

    @Test
    public void getClassNo() {
        Assert.assertEquals("1", testBlank.getClassNo());
        Assert.assertEquals(DEFAULT_CLASSNO, test.getClassNo());
    }

    @Test
    public void getLessonType() {
        Assert.assertEquals("Lecture", testBlank.getLessonType());
        Assert.assertEquals(DEFAULT_LESSON_TYPE, test.getLessonType());
    }

    @Test
    public void getWeekText() {
        Assert.assertEquals("1", testBlank.getWeekText());
        Assert.assertEquals(DEFAULT_WEEK_TEXT, test.getWeekText());
    }

    @Test
    public void getDayText() {
        Assert.assertEquals("Monday", testBlank.getDayText());
        Assert.assertEquals(DEFAULT_DAY_TEXT, test.getDayText());
    }

    @Test
    public void getStartTime() {
        Assert.assertEquals("0000", testBlank.getStartTime());
        Assert.assertEquals(DEFAULT_START_TIME, test.getStartTime());
    }

    @Test
    public void getEndTime() {
        Assert.assertEquals("2359", testBlank.getEndTime());
        Assert.assertEquals(DEFAULT_END_TIME, test.getEndTime());
    }

    @Test
    public void getVenue() {
        Assert.assertEquals("COM1 01-01", testBlank.getVenue());
        Assert.assertEquals(DEFAULT_VENUE, test.getVenue());
    }

    @Test
    public void testToString() {
        String expected = "ClassNo: " + DEFAULT_CLASSNO
                + "\nLessonType: " + DEFAULT_LESSON_TYPE
                + "\nWeekText: " + DEFAULT_WEEK_TEXT
                + "\nDayText: " + DEFAULT_DAY_TEXT
                + "\nStartTime: " + DEFAULT_START_TIME
                + "\nEndTime: " + DEFAULT_END_TIME
                + "\nVenue: " + DEFAULT_VENUE + "\n";
        String expectedBlank = "ClassNo: 1"
                + "\nLessonType: Lecture"
                + "\nWeekText: 1"
                + "\nDayText: Monday"
                + "\nStartTime: 0000"
                + "\nEndTime: 2359"
                + "\nVenue: COM1 01-01" + "\n";
        Assert.assertEquals(expected, test.toString());
        Assert.assertEquals(expectedBlank, testBlank.toString());
    }

    @Test
    public void testEquals() {
        Assert.assertTrue(test.equals(test));
        Assert.assertTrue(test.equals(new Schedule(DEFAULT_CLASSNO, DEFAULT_LESSON_TYPE, DEFAULT_WEEK_TEXT,
                DEFAULT_DAY_TEXT, DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_VENUE)));
        Assert.assertFalse(test.equals(DEFAULT_LESSON_TYPE));
        Assert.assertFalse(test.equals(new Schedule("4", DEFAULT_LESSON_TYPE, DEFAULT_WEEK_TEXT,
                DEFAULT_DAY_TEXT, DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_VENUE)));

    }

}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void removeTag_removeNonexistentTag_addressBookUnchanged() throws Exception {
        amyNBobAddressBook.removeTag(new Tag(VALID_TAG_UNUSED));

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(expectedAddressBook, amyNBobAddressBook);
    }

    @Test
    public void removeTag_tagUsedByMultiplePersons_tagRemoved() throws Exception {
        amyNBobAddressBook.removeTag(new Tag(VALID_TAG_FRIEND));

        Person expectedAmy = new PersonBuilder(AMY).withTags().build();
        Person expectedBob = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder()
                .withPerson(expectedAmy).withPerson(expectedBob).build();

        assertEquals(expectedAddressBook, amyNBobAddressBook);
    }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void deleteTag_nonExistent_modelUnchanged() throws Exception {
        AddressBook addressBook = new AddressBookBuilder().withPerson(BOB).withPerson(AMY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.removeTag(new Tag(VALID_TAG_UNUSED));
        assertEquals(new ModelManager(addressBook, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagUsedByMultiplePersons_tagRemoved() throws Exception {
        AddressBook addressBook = new AddressBookBuilder().withPerson(BOB).withPerson(AMY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        modelManager.removeTag(new Tag(VALID_TAG_FRIEND));

        Person expectedAmy = new PersonBuilder(AMY).withTags().build();
        Person expectedBob = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder()
                .withPerson(expectedBob).withPerson(expectedAmy).build();

        ModelManager expectedModelManager = new ModelManager(expectedAddressBook, userPrefs);
        assertEquals(expectedModelManager, modelManager);

    }
```
###### \java\seedu\address\model\person\TimeTableLinkTest.java
``` java
public class TimeTableLinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TimeTableLink(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidLink = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TimeTableLink(invalidLink));
    }

    @Test
    public void isValidLink() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> TimeTableLink.isValidLink(null));

        // blank email
        assertFalse(TimeTableLink.isValidLink("")); // empty string
        assertFalse(TimeTableLink.isValidLink(" ")); // spaces only

        // missing parts
        assertFalse(TimeTableLink.isValidLink("MYwiD")); // missing http://modn.us/ URL head
        assertFalse(TimeTableLink.isValidLink("http://modn.nus/")); // missing trailing part

        // invalid parts
        assertFalse(TimeTableLink.isValidLink("https://modsn.us/MYwiD")); // https instead of http
        assertFalse(TimeTableLink.isValidLink("http://mods.nus/MYwiD")); // incorrect URL
        assertFalse(TimeTableLink.isValidLink("http:// modsn.us/MYwiD")); // spaces in URL
        assertFalse(TimeTableLink.isValidLink("http://modsn.us/MYw iD")); // spaces in trailing part
        assertFalse(TimeTableLink.isValidLink(" http://modsn.us/MYwiD")); // leading space
        assertFalse(TimeTableLink.isValidLink("http://modsn.us/MYwiD ")); // trailing space
        assertFalse(TimeTableLink.isValidLink("http://modsn.us//MYwiD")); // double '/' symbol
        assertFalse(TimeTableLink.isValidLink("http://modsn.us/MYw.iD")); // '.' symbol in trailing part

        // valid email
        assertTrue(TimeTableLink.isValidLink("http://modsn.us/MYwiD"));
    }

```
###### \java\seedu\address\storage\XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidLink_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_LINK, VALID_DETAIL,
                        VALID_TAGS);
        String expectedMessage = TimeTableLink.MESSAGE_TIMETABLE_LINK_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLink_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null,
                VALID_DETAIL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeTableLink.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code TimeTableLink} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withTimeTableLink(String link) {
        descriptor.setTimeTableLink(new TimeTableLink(link));
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code TimeTableLink} of the {@code Person} that we are building.
     */
    public PersonBuilder withTimeTableLink(String link) {
        this.link = new TimeTableLink(link);
        return this;
    }
```
