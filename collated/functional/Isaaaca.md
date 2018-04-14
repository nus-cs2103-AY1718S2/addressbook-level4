# Isaaaca
###### \main\java\seedu\address\commons\util\JsonUtil.java
``` java
    /**
     * Returns the Json Array from the given file or an empty ArrayList if the file is not found.
     * If any values are missing from the file, default values will be used, as long as the file is a valid json file.
     * @param filePath cannot be null.
     * @param classOfObjectToDeserialize Json file has to correspond to the structure in the class given here.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static <T> ArrayList<T> readJsonArrayFromFile(
            String filePath, Class<T> classOfObjectToDeserialize) throws DataConversionException {
        requireNonNull(filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            logger.info("Json file "  + file + " not found");
            return new ArrayList<T>();
        }

        ArrayList<T> jsonFile;

        try {
            JavaType javaType = objectMapper.getTypeFactory()
                    .constructCollectionType(ArrayList.class, classOfObjectToDeserialize);
            jsonFile = objectMapper.readValue(file, javaType);
        } catch (IOException e) {
            logger.warning("Error reading from jsonFile file " + file + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(jsonFile).orElse(new ArrayList<T>());
    }
```
###### \main\java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns the partial ratio bewteen the {@code sentence} and the {@code word}.
     * Ignores case.
     * <br>examples:<pre>
     *       getPartialRatioFuzzyIgnoreCase("ABc def", "abc") == 100
     *       getPartialRatioFuzzyIgnoreCase("ABc def", "DEF") == 100
     *       getPartialRatioFuzzyIgnoreCase("ABc def", "AB") == 100
     *       getPartialRatioFuzzyIgnoreCase("ABc def", "ABcD") == 75
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static int getPartialRatioFuzzyIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();
        System.out.println("word: " + preppedWord + " | Name: " + sentence + "/n ratio: "
                + FuzzySearch.partialRatio(preppedWord, preppedSentence));
        return FuzzySearch.partialRatio(preppedWord, preppedSentence);
    }
```
###### \main\java\seedu\address\database\DatabaseManager.java
``` java
/**
 * The main DatabaseManager of the app.
 */
public class DatabaseManager {

    private static final String DEFAULT_JSON_DATABASE_FILEPATH = "modules.json";
    private static final String DEFAULT_JSON_DATABASE_URL = "https://api.nusmods.com/2017-2018/2/modules.json";
    private static final Logger logger = LogsCenter.getLogger(DatabaseManager.class);

    private static final Map<String, String> lessonAbbrev = Collections.unmodifiableMap(
            new HashMap<String, String>() {{
                put("Design Lecture", "DLEC");
                put("Laboratory", "LAB");
                put("Lecture", "LEC");
                put("Packaged Lecture", "PLEC");
                put("Packaged Tutorial", "PTUT");
                put("Recitation", "REC");
                put("Sectional Teaching", "SEC");
                put("Seminar-Style Module Class", "SEM");
                put("Tutorial", "TUT");
                put("Tutorial Type 2", "TUT2");
                put("Tutorial Type 2", "TUT3");
                put("Workshop", "WS");
            }});

    private static DatabaseManager databaseManager = null;
    private static HashMap<String, Module> moduleDatabase;

    private DatabaseManager() {
        File jsonFile = new File(DEFAULT_JSON_DATABASE_FILEPATH);
        if (jsonFile.exists()) {
            try {
                URL databaseUrl = new URL(DEFAULT_JSON_DATABASE_URL);
                URLConnection databaseConnection = databaseUrl.openConnection();
                if (databaseConnection.getLastModified() > jsonFile.lastModified()) {
                    downloadFile(databaseUrl, jsonFile);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                logger.warning("Problem updating Module database. Existing database will be used.");
            }
        } else {
            try {
                URL databaseUrl = new URL(DEFAULT_JSON_DATABASE_URL);
                downloadFile(databaseUrl, jsonFile);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                logger.severe("Unable to download Module Database. Scheduling functionality will no be available.");
            }
        }
        moduleDatabase = parseDatabase(DEFAULT_JSON_DATABASE_FILEPATH);
    }

    /**
     * Downloads the file from the specified {@code url} saves it in the given {@code file}
     * Creates a new file if it does not exist, and attempts to overwrite if it does.
     *
     * @param url
     * @param file
     */
    private void downloadFile(URL url, File file) throws IOException {
        logger.info("Retrieving Module Database...");
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        if (file.createNewFile()) {
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } else {
            File temp = new File("temp.json");
            FileOutputStream fos = new FileOutputStream(temp);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            file.delete();
            fos.close();
            temp.renameTo(file);
        }
    }

    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    /**
     * Parses a string query into a list of module and schedules
     *
     * @param link TimeTableLinkto be parsed
     */
    public ArrayList<WeeklyEvent> parseEvents(TimeTableLink link) { //todo return list of events when available
        String query = getQuery(link);
        StringBuilder result = new StringBuilder();

        ArrayList<WeeklyEvent> eventList = new ArrayList<>();

        StringTokenizer queryTokenizer = new StringTokenizer(query, "&");
        while (queryTokenizer.hasMoreTokens()) {
            StringTokenizer modTokenizer = new StringTokenizer(queryTokenizer.nextToken(), "=");
            Module module = moduleDatabase.get(modTokenizer.nextToken());
            result.append(module.getModuleCode() + "\n"); //TODO: remove after integrating with Events class

            String[] lessons = modTokenizer.nextToken().split(",");
            List<Schedule> scheduleList = module.getScheduleList();

            for (Schedule schedule : scheduleList) {
                for (String lesson : lessons) {
                    StringTokenizer lessonTokenizer = new StringTokenizer(lesson, ":");
                    String queryAbbrev = lessonTokenizer.nextToken();
                    String queryLessonNum = lessonTokenizer.nextToken();
                    if (queryAbbrev.equals(lessonAbbrev.get(schedule.getLessonType()))
                            && queryLessonNum.equals(schedule.getClassNo())) {
                        eventList.add(new WeeklyEvent(module, schedule));
                        result.append(schedule.getLessonType() + " " + schedule.getClassNo() + "\n"); //TODO: remove
                        result.append("\t" + schedule.getDayText() + "\n");
                        result.append("\t" + schedule.getStartTime() + " to " + schedule.getEndTime() + "\n");
                        result.append("\t" + schedule.getVenue() + "\n");

                    }
                }
            }
        }
        logger.info(result.toString());
        return eventList;
    }

    /**
     * Connects to the timeTableLink given and returns a list of modules
     *
     * @param timeTableLink a TimeTableLink representing an URL to a NUSmods schedule
     */
    public static String getQuery(TimeTableLink timeTableLink) {
        try {
            URL shortUrl = new URL(timeTableLink.toString());
            URLConnection connection = shortUrl.openConnection();
            URL longUrl = new URL(connection.getHeaderField("Location"));
            return longUrl.getQuery();

        } catch (MalformedURLException e) {
            logger.info("NUSmods URL Invalid.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param filePath
     * @return module from jsonfile
     */
    public Optional<Module> parseModule(String filePath) {
        try {
            return JsonUtil.readJsonFile(filePath, Module.class);
        } catch (DataConversionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param filePath
     * @return hashMap of all modules from jsonfile
     */
    public HashMap<String, Module> parseDatabase(String filePath) {
        List<Module> moduleList;
        try {
            moduleList = JsonUtil.readJsonArrayFromFile(filePath, Module.class);

        } catch (DataConversionException e) {
            e.printStackTrace();
            return new HashMap<>();
        }

        HashMap<String, Module> hashMap = new HashMap<>();

        for (Module m : moduleList) {
            hashMap.put(m.getModuleCode(), m);
        }

        return hashMap;
    }

}
```
###### \main\java\seedu\address\database\module\Module.java
``` java
/**
 * Represents a Module from NUSmods
 */
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Module {
    private String moduleCode = "";
    private String moduleTitle = "";
    private ArrayList<Schedule> timetable = new ArrayList<>();

    public Module(){}

    public Module(String moduleCode, String moduleTitle, ArrayList<Schedule> timetable) {
        requireAllNonNull(moduleCode, moduleTitle);
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
        this.timetable = timetable;
    }

    public Module(String moduleCode, String moduleTitle) {
        requireAllNonNull(moduleCode, moduleTitle);
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public ArrayList<Schedule> getScheduleList() {
        return timetable;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModuleCode().equals(this.getModuleCode())
                && otherModule.getModuleTitle().equals(this.getModuleTitle());
    }

    @Override
    public String toString() {
        return "moduleCode: " + moduleCode + " moduleTitle: " + moduleTitle + "\n" + timetable.toString();
    }
}
```
###### \main\java\seedu\address\database\module\Schedule.java
``` java
/**
 * Represents a schedule for a class in a module
 */
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Schedule {
    private String classNo;

    private String lessonType;
    private String weekText;
    private String dayText;
    private String startTime;
    private String endTime;
    private String venue;

    public Schedule() {
        this.classNo = "1";
        this.lessonType = "Lecture";
        this.weekText = "1";
        this.dayText = "Monday";
        this.startTime = "0000";
        this.endTime = "2359";
        this.venue = "COM1 01-01";
    }

    public Schedule(String classNo, String lessonType, String weekText, String dayText,
             String startTime, String endTime, String venue) {
        this.classNo = classNo;
        this.lessonType = lessonType;
        this.weekText = weekText;
        this.dayText = dayText;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getWeekText() {
        return weekText;
    }

    public String getDayText() {
        return dayText;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getVenue() {
        return venue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return otherSchedule.getClassNo().equals(this.getClassNo())
                && otherSchedule.getLessonType().equals(this.getLessonType())
                && otherSchedule.getWeekText().equals(this.getWeekText())
                && otherSchedule.getDayText().equals(this.getDayText())
                && otherSchedule.getStartTime().equals(this.getStartTime())
                && otherSchedule.getEndTime().equals(this.getEndTime())
                && otherSchedule.getVenue().equals(this.getVenue());
    }

    @Override
    public String toString() {
        return "ClassNo: " + classNo
                + "\nLessonType: " + lessonType
                + "\nWeekText: " + weekText
                + "\nDayText: " + dayText
                + "\nStartTime: " + startTime
                + "\nEndTime: " + endTime
                + "\nVenue: " + venue + "\n";
    }
}
```
###### \main\java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setTimeTableLink(TimeTableLink link) {
            this.link = link;
        }

        public Optional<TimeTableLink> getTimeTableLink() {
            return Optional.ofNullable(link);
        }
```
###### \main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
    private static final List<String> COMMAND_WORDS = Arrays.asList(AddCommand.COMMAND_WORD,
            AddEventCommand.COMMAND_WORD,
            AddGroupCommand.COMMAND_WORD,
            AddMemberToGroupCommand.COMMAND_WORD,
            AddToDoCommand.COMMAND_WORD,
            ChangeTagColorCommand.COMMAND_WORD,
            CheckToDoCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            DeleteToDoCommand.COMMAND_WORD,
            DeleteGroupCommand.COMMAND_WORD,
            AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            EditToDoCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            HistoryCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            ListTagMembersCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD,
            ScheduleGroupCommand.COMMAND_WORD,
            SelectCommand.COMMAND_WORD,
            SwitchCommand.COMMAND_WORD,
            UnCheckToDoCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD);
```
###### \main\java\seedu\address\logic\parser\AddressBookParser.java
``` java
        default:
            ExtractedResult guess = FuzzySearch.extractOne(commandWord, COMMAND_WORDS);
            if (guess.getScore() >= 75) {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND + "\n Did you mean: " + guess.getString());
            } else {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }

}
```
###### \main\java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String link} into an {@code TimeTableLink}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code link} is invalid.
     */
    public static TimeTableLink parseTimeTableLink(String link) throws IllegalValueException {
        requireNonNull(link);
        String trimmedLink = link.trim();
        if (!TimeTableLink.isValidLink(trimmedLink)) {
            throw new IllegalValueException(TimeTableLink.MESSAGE_TIMETABLE_LINK_CONSTRAINTS);
        }
        return new TimeTableLink(trimmedLink);
    }

    /**
     * Parses a {@code Optional<String> link} into an {@code Optional<TimeTableLink>} if {@code link} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TimeTableLink> parseTimeTableLink(Optional<String> link) throws IllegalValueException {
        requireNonNull(link);
        return  link.isPresent() ? Optional.of(parseTimeTableLink(link.get())) : Optional.empty();
    }
```
###### \main\java\seedu\address\model\AddressBook.java
``` java
    /**
     * Removes {@code tag} from all {@code persons} in the {@code AddressBook}.
     */
    private void removeTagFromPerson(Tag tag, Person person) throws PersonNotFoundException {
        Set<Tag> tagList = new HashSet<>(person.getTags());

        //Terminate if tag is not is tagList
        if (!tagList.remove(tag)) {
            return;
        }
        Person updatedPerson = new Person(person.getName(), person.getPhone(),
                person.getEmail(), person.getAddress(), person.getTimeTableLink(), person.getDetail(), tagList);

        try {
            updatePerson(person, updatedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Modifying a person's tags only should not result in a duplicate. "
                    + "See Person#equals(Object).");
        }
    }
}
```
###### \main\java\seedu\address\model\group\Group.java
``` java
    /**
     * Adds a person to the group's personList
     * @param toAdd The Person to add.
     * @throws DuplicatePersonException
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException {
        if (getPersonList().contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        this.personList.add(toAdd);
    }

```
###### \main\java\seedu\address\model\person\TimeTableLink.java
``` java
/**
 * Represents the link to a Person's TimeTable.
 * Guarantees: immutable; is valid as declared in {@link #isValidLink(String)}
 */
public class TimeTableLink {

    public static final String MESSAGE_TIMETABLE_LINK_CONSTRAINTS = "Timetable Links should "
            + "adhere to the following constraints:\n"
            + "1. Begin with \"http://modsn.us/\". \n"
            + "2. This is followed by a string of alphanumeric characters. ";
    // alphanumeric and special characters
    private static final String SHORT_URL_FROINT_REGEX = "http://modsn\\.us/";
    private static final String SHORT_URL_TRAIL_REGEX = "[a-zA-Z0-9\\-]+"; // alphanumeric and hyphen
    public static final String SHORT_URL_VALIDATION_REGEX = SHORT_URL_FROINT_REGEX + SHORT_URL_TRAIL_REGEX;

    public final String value;

    /**
     * Constructs an {@code TimeTableLink}.
     *
     * @param link A valid email address.
     */
    public TimeTableLink(String link) {
        requireNonNull(link);
        checkArgument(isValidLink(link), MESSAGE_TIMETABLE_LINK_CONSTRAINTS);
        this.value = link;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidLink(String test) {
        return test.matches(SHORT_URL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeTableLink // instanceof handles nulls
                && this.value.equals(((TimeTableLink) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \main\java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        if (this.link == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeTableLink.class.getSimpleName()));
        }
        if (!TimeTableLink.isValidLink(this.link)) {
            throw new IllegalValueException(TimeTableLink.MESSAGE_TIMETABLE_LINK_CONSTRAINTS);
        }
        final TimeTableLink link = new TimeTableLink(this.link);
```
###### \test\java\seedu\address\commons\util\JsonUtilTest.java
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
###### \test\java\seedu\address\commons\util\StringUtilTest.java
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
###### \test\java\seedu\address\database\DatabaseManagerTest.java
``` java
public class DatabaseManagerTest {

    private DatabaseManager test = DatabaseManager.getInstance();

    @Test
    public void getQuery() {
        String actual = test.getQuery(new TimeTableLink("http://modsn.us/MYwiD"));
        assertEquals("CS2101=SEC:3&CS2103T=TUT:T3&CS2105=LEC:1,TUT:7&CS3242=LEC:1,TUT:3&ST2334=LEC:SL1,TUT:T4",
                actual);
        actual = test.getQuery(new TimeTableLink("http://modsn.us/MYwid"));
        assertEquals(null,
                actual);
    }

    @Test
    public void parseQuery() {
        test.parseEvents(new TimeTableLink("http://modsn.us/MYwiD"));
    }
}
```
###### \test\java\seedu\address\database\module\ModuleTest.java
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
###### \test\java\seedu\address\database\module\ScheduleTest.java
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
###### \test\java\seedu\address\model\AddressBookTest.java
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
###### \test\java\seedu\address\model\ModelManagerTest.java
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
###### \test\java\seedu\address\model\person\TimeTableLinkTest.java
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
###### \test\java\seedu\address\storage\XmlAdaptedPersonTest.java
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
###### \test\java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code TimeTableLink} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withTimeTableLink(String link) {
        descriptor.setTimeTableLink(new TimeTableLink(link));
        return this;
    }
```
###### \test\java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code TimeTableLink} of the {@code Person} that we are building.
     */
    public PersonBuilder withTimeTableLink(String link) {
        this.link = new TimeTableLink(link);
        return this;
    }
```
