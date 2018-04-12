# Isaaaca
###### \java\seedu\address\commons\util\JsonUtil.java
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
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static int containsWordFuzzyIgnoreCase(String sentence, String word) {
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
###### \java\seedu\address\database\DatabaseManager.java
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
###### \java\seedu\address\database\module\Module.java
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
###### \java\seedu\address\database\module\Schedule.java
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
        this.classNo = "";
        this.lessonType = "";
        this.weekText = "";
        this.dayText = "";
        this.startTime = "";
        this.endTime = "";
        this.venue = "";
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
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setTimeTableLink(TimeTableLink link) {
            this.link = link;
        }

        public Optional<TimeTableLink> getTimeTableLink() {
            return Optional.ofNullable(link);
        }
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
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
            SelectCommand.COMMAND_WORD,
            SwitchCommand.COMMAND_WORD,
            UnCheckToDoCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
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
###### \java\seedu\address\logic\parser\ParserUtil.java
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
###### \java\seedu\address\model\AddressBook.java
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
###### \java\seedu\address\model\person\TimeTableLink.java
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
###### \java\seedu\address\storage\XmlAdaptedPerson.java
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
