# karenfrilya97
###### \java\seedu\address\commons\util\XmlUtilTest.java
``` java
/**
 * Tests {@code XmlUtil} read and save methods
 */
public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validDeskBoard.xml");
    private static final File MISSING_TASK_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingTaskField.xml");
    private static final File INVALID_TASK_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidTaskField.xml");
    private static final File VALID_TASK_FILE = new File(TEST_DATA_FOLDER + "validTask.xml");
    private static final File MISSING_EVENT_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingEventField.xml");
    private static final File INVALID_EVENT_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidEventField.xml");
    private static final File VALID_EVENT_FILE = new File(TEST_DATA_FOLDER + "validEvent.xml");

    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempDeskBoard.xml"));

    private static final String INVALID_DATE_TIME = "9482asf424";
    private static final String INVALID_LOCATION = " michegan ave";

    private static final String VALID_TASK_NAME = "Hans Muster";
    private static final String VALID_TASK_DUE_DATE_TIME = "9482424";
    private static final String VALID_TASK_REMARK = "4th street";
    private static final List<XmlAdaptedTag> VALID_TASK_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    private static final String VALID_EVENT_NAME = "CIP";
    private static final String VALID_EVENT_START_DATE_TIME = "02/04/2018 08:00";
    private static final String VALID_EVENT_END_DATE_TIME = "02/04/2018 12:00";
    private static final String VALID_EVENT_LOCATION = "michegan ave";
    private static final List<XmlAdaptedTag> VALID_EVENT_TAGS = Collections.singletonList(new XmlAdaptedTag("CIP"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, DeskBoard.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, DeskBoard.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, DeskBoard.class);
    }

    //TODO: TEST
    //@Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        DeskBoard dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableDeskBoard.class).toModelType();
        assertEquals(7, dataFromFile.getActivityList().size());
        assertEquals(3, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithMissingTaskField_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                MISSING_TASK_FIELD_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                null, VALID_TASK_DUE_DATE_TIME, VALID_TASK_REMARK, VALID_TASK_TAGS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithInvalidTaskField_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                INVALID_TASK_FIELD_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                VALID_TASK_NAME, INVALID_DATE_TIME, VALID_TASK_REMARK, VALID_TASK_TAGS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void xmlAdaptedTaskFromFile_fileWithValidTask_validResult() throws Exception {
        XmlAdaptedTask actualTask = XmlUtil.getDataFromFile(
                VALID_TASK_FILE, XmlAdaptedTaskWithRootElement.class);
        XmlAdaptedTask expectedTask = new XmlAdaptedTask(
                VALID_TASK_NAME, VALID_TASK_DUE_DATE_TIME, VALID_TASK_REMARK, VALID_TASK_TAGS);
        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithMissingEventField_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                MISSING_EVENT_FIELD_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_START_DATE_TIME,
                null, VALID_EVENT_LOCATION, null, VALID_EVENT_TAGS);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithInvalidEventField_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                INVALID_EVENT_FIELD_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_START_DATE_TIME,
                VALID_EVENT_END_DATE_TIME, INVALID_LOCATION, null, VALID_EVENT_TAGS);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void xmlAdaptedEventFromFile_fileWithValidEvent_validResult() throws Exception {
        XmlAdaptedEvent actualEvent = XmlUtil.getDataFromFile(
                VALID_EVENT_FILE, XmlAdaptedEventWithRootElement.class);
        XmlAdaptedEvent expectedEvent = new XmlAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_START_DATE_TIME,
                VALID_EVENT_END_DATE_TIME, VALID_EVENT_LOCATION, null, VALID_EVENT_TAGS);
        assertEquals(expectedEvent, actualEvent);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new DeskBoard());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new DeskBoard());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableDeskBoard dataToWrite = new XmlSerializableDeskBoard(new DeskBoard());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableDeskBoard dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDeskBoard.class);
        assertEquals(dataToWrite, dataFromFile);

        DeskBoardBuilder builder = new DeskBoardBuilder(new DeskBoard());
        dataToWrite = new XmlSerializableDeskBoard(
                builder.withActivity(new TaskBuilder().build()).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDeskBoard.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data
     * to {@code XmlAdaptedTask} objects.
     */
    @XmlRootElement(name = "task")
    private static class XmlAdaptedTaskWithRootElement extends XmlAdaptedTask {}

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data
     * to {@code XmlAdaptedEvent} objects.
     */
    @XmlRootElement(name = "event")
    private static class XmlAdaptedEventWithRootElement extends XmlAdaptedEvent {}
}
```
###### \java\seedu\address\model\UniqueActivityListTest.java
``` java
    @Test
    public void add_taskWithEarlierDateTimeThanExisting_sortsListAutomatically() throws DuplicateActivityException {
        UniqueActivityList uniqueActivityList = new UniqueActivityList();
        Task earlierTask = ASSIGNMENT1;
        Task laterTask = ASSIGNMENT2;
        uniqueActivityList.add(laterTask);
        uniqueActivityList.add(earlierTask);
        Activity firstActivityOnTheList = uniqueActivityList.internalListAsObservable().get(0);
        assertEquals(firstActivityOnTheList, earlierTask);
    }

    @Test
    public void add_eventWithEarlierStartDateTimeThanExisting_sortsListAutomatically()
            throws DuplicateActivityException {
        UniqueActivityList uniqueActivityList = new UniqueActivityList();
        Event earlierEvent = CCA;
        Event laterEvent = CIP;
        uniqueActivityList.add(laterEvent);
        uniqueActivityList.add(earlierEvent);
        Activity firstActivityOnTheList = uniqueActivityList.internalListAsObservable().get(0);
        assertEquals(firstActivityOnTheList, earlierEvent);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedEventTest.java
``` java
public class XmlAdaptedEventTest {

    private static final Event CIP_EVENT = CIP;

    private static final String INVALID_NAME = "Rachel's Bday";
    private static final String INVALID_DATE_TIME = "23 April 2018";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = CIP_EVENT.getName().toString();
    private static final String VALID_START_DATE_TIME = CIP_EVENT.getStartDateTime().toString();
    private static final String VALID_END_DATE_TIME = CIP_EVENT.getEndDateTime().toString();
    private static final String VALID_LOCATION = CIP_EVENT.getLocation().toString();
    private static final String VALID_REMARK = CIP_EVENT.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = CIP_EVENT.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(CIP_EVENT);
        assertEquals(CIP_EVENT, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(INVALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, INVALID_DATE_TIME, VALID_END_DATE_TIME,
                        VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, null, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "start date/time");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, INVALID_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, null,
                VALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, event.getActivityType(), "end date/time");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                INVALID_LOCATION, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Location.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_NAME, VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_LOCATION, VALID_REMARK, invalidTags);
        Assert.assertThrows(IllegalValueException.class, event::toModelType);
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedTaskTest.java
``` java
public class XmlAdaptedTaskTest {

    private static final Task ASSIGNMENT2_TASK = (Task) ASSIGNMENT2;

    private static final String INVALID_NAME = "Rachel's Bday";
    private static final String INVALID_DATE_TIME = "23 April 2018";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = ASSIGNMENT2_TASK.getName().toString();
    private static final String VALID_DATE_TIME = ASSIGNMENT2_TASK.getDueDateTime().toString();
    private static final String VALID_REMARK = ASSIGNMENT2_TASK.getRemark().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = ASSIGNMENT2_TASK.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        XmlAdaptedTask task = new XmlAdaptedTask(ASSIGNMENT2_TASK);
        assertEquals(ASSIGNMENT2_TASK, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(INVALID_NAME, VALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(null, VALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, task.getActivityType(), "name");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, INVALID_DATE_TIME, VALID_REMARK, VALID_TAGS);
        String expectedMessage = DateTime.MESSAGE_DATETIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        XmlAdaptedTask task = new XmlAdaptedTask(VALID_NAME, null, VALID_REMARK, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, task.getActivityType(), "due date/time");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedTask task =
                new XmlAdaptedTask(VALID_NAME, VALID_DATE_TIME, VALID_REMARK, invalidTags);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }

}
```
