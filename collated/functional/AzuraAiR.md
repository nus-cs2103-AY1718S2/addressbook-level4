# AzuraAiR
###### \java\seedu\address\commons\events\ui\BirthdayListEvent.java
``` java
/**
 * Represents a selection change in the Person List Panel
 */
public class BirthdayListEvent extends BaseEvent {

    private final String birthdayList;

    public BirthdayListEvent(String newList) {
        this.birthdayList = newList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getBirthdayList() {
        return birthdayList;
    }
}
```
###### \java\seedu\address\commons\events\ui\BirthdayNotificationEvent.java
``` java
/**
 * Represents a call for the Birthday Notification
 */
public class BirthdayNotificationEvent extends BaseEvent {

    private final String birthdayList;
    private final LocalDate currentDate;

    public BirthdayNotificationEvent(String newList, LocalDate today) {
        this.birthdayList = newList;
        this.currentDate = today;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getBirthdayList() {
        return birthdayList;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

}
```
###### \java\seedu\address\commons\exceptions\NoInternetConnectionException.java
``` java
/**
 * Represents an error in trying to obtain the long url from the shortened Timetable URL
 */
public class NoInternetConnectionException extends Exception {
    public NoInternetConnectionException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\commons\util\timetable\Lesson.java
``` java
/**
 * Represents a lesson that a module has
 */
public class Lesson {

    private final String moduleCode;
    private final String classNo;
    private final String lessonType;
    private final String weekType;
    private final String day;
    private final String startTime;
    private final String endTime;

    public Lesson(String moduleCode, String classNo, String lessonType, String weekType,
                  String day, String startTime, String endTime) {
        this.moduleCode = moduleCode;
        this.classNo = classNo;
        this.lessonType = lessonType;
        this.weekType = weekType;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getWeekType() {
        return weekType;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return moduleCode + "\n" + lessonType.substring(0, 3).toUpperCase() + "[" + classNo + "]\n";
    }
}
```
###### \java\seedu\address\commons\util\timetable\TimetableParserUtil.java
``` java
/**
 * Utility functions to parse a shortened NUSMods url to create TimetableData
 */
public class TimetableParserUtil {

    public static final String URL_BACKSLASH_REGEX = "/";
    public static final String URL_QUESTION_MARK_REGEX = "\\?";
    public static final String URL_AND_SIGN_REGEX = "&";
    public static final String URL_EQUALS_SIGN_REGEX = "=";
    public static final String URL_MINUS_SIGN_REGEX = "-";
    public static final String URL_COMMA_SIGN_REGEX = ",";
    public static final String URL_COLON_REGEX = ":";
    public static final int SEMSTER_NUMBER_URL_INDEX = 4;
    public static final int SEMSTER_NUMBER_INTEGER_INDEX = 1;
    public static final int LAST_STRING_URL_INDEX = 5;
    public static final int MODULE_STRING_INDEX = 1;
    public static final int MODULE_CODE_INDEX = 0;
    public static final int LESSON_STRING_INDEX = 1;
    public static final int LESSON_TYPE_INDEX = 0;
    public static final int LESSON_NUMBER_INDEX = 1;

    /**
     * Parses a shortened NUSMods url and creates TimetableData
     * @param url shortened NUSMods url
     * @return TimetableData of the NUSMods timetable
     * @throws ParseException when an error in parsing is encountered
     */
    public static TimetableData parseUrl(String url) throws ParseException {
        checkArgument(isValidUrl(url), MESSAGE_URL_CONSTRAINTS);

        try {
            String longUrl = parseShortUrl(url);
            return parseLongUrl(longUrl);
        } catch (NoInternetConnectionException ie) {
            return new TimetableData();
        }

    }

    /**
     * Attempts to access NUSMods to obtain the full NUSMods url
     * @param url shortened NUSMods url
     * @return long url
     * @throws ParseException when the long
     * @throws NoInternetConnectionException when
     */
    public static String parseShortUrl (String url) throws ParseException, NoInternetConnectionException {
        try {
            URL shortUrl = new URL(url);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) shortUrl.openConnection(Proxy.NO_PROXY);

            httpUrlConnection.setReadTimeout(500);
            httpUrlConnection.setInstanceFollowRedirects(false);

            String longUrl = httpUrlConnection.getHeaderField("Location");
            httpUrlConnection.disconnect();

            if (longUrl == null) {
                throw new NoInternetConnectionException("No internet connection, starting with empty timetables");
            } else if (longUrl.equals("http://modsn.us")) {
                throw new ParseException(MESSAGE_INVALID_URL);
            }

            return longUrl;
        } catch (IOException ioe) {
            throw new ParseException(MESSAGE_INVALID_URL);
        }
    }

    /**
     * Parses the full NUSMods link into TimetableData format
     * @param url Full NUSMods link
     * @return TimetableData parsed from link
     * @throws ParseException
     */
    public static TimetableData parseLongUrl(String url) throws ParseException {
        String[] urlParts = url.split(URL_BACKSLASH_REGEX); // Obtain the last part of url
        ArrayList<Lesson> totalLessonList = new ArrayList<Lesson>();
        ArrayList<Lesson> lessonsToAddFromModule;

        // Seperate semester number normally found at the fifth part
        String semNum = urlParts[SEMSTER_NUMBER_URL_INDEX];
        // Seperate "share" out of last part of url
        String[] toParse = urlParts[LAST_STRING_URL_INDEX].split(URL_QUESTION_MARK_REGEX);
        // Seperate the modules in last part of url
        String[] modules = toParse[MODULE_STRING_INDEX].split(URL_AND_SIGN_REGEX);

        // Loop to find the modules taken and create the Lessons taken to add into Timetable
        for (String module: modules) {
            lessonsToAddFromModule = parseModule(module, semNum);
            for (Lesson lessonToAdd: lessonsToAddFromModule) {
                totalLessonList.add(lessonToAdd);
            }
        }
        return new TimetableData(totalLessonList);
    }

    /**
     * Parses the module string and returns the lessons taken
     * @param module String that contains module code and lessons selected
     * @param semNum Semester number
     * @return ArrayList of lessons taken
     * @throws ParseException when invalid lessons are received
     */
    public static ArrayList<Lesson> parseModule(String module, String semNum) throws ParseException {
        ArrayList<Lesson> lessonListFromApi;
        ArrayList<Lesson> lessonsTakenList;
        lessonsTakenList = new ArrayList<Lesson>();
        String[] moduleInfo = module.split(URL_EQUALS_SIGN_REGEX);
        String[] semNumToParse = semNum.split(URL_MINUS_SIGN_REGEX);
        int semNumInt = Integer.parseInt(semNumToParse[SEMSTER_NUMBER_INTEGER_INDEX]);
        String moduleCode = moduleInfo[MODULE_CODE_INDEX];
        String[] lessonsTaken = moduleInfo[LESSON_STRING_INDEX].split(URL_COMMA_SIGN_REGEX);

        lessonListFromApi = obtainModuleInfoFromApi(moduleCode, semNumInt);

        for (String lessonTaken : lessonsTaken) {
            String[] lessonToParse = lessonTaken.split(URL_COLON_REGEX);
            String lessonType = convertShortFormToLong(lessonToParse[LESSON_TYPE_INDEX]);
            String lessonNum = lessonToParse[LESSON_NUMBER_INDEX];

            for (Lesson lessonFromApi: lessonListFromApi) {
                if (lessonType.equalsIgnoreCase(lessonFromApi.getLessonType())
                        && lessonNum.equalsIgnoreCase(lessonFromApi.getClassNo())) {
                    lessonsTakenList.add(lessonFromApi);
                }
            }
        }

        return lessonsTakenList;
    }

    /**
     * Accesses NUSMods API and obtains and parses the json file
     * @param moduleCode the module
     * @param semNum current semester number
     * @return Total list of lessons a module has
     * @throws ParseException when information from NUSMods API cannot be retrieved successfully
     */
    public static ArrayList<Lesson> obtainModuleInfoFromApi(String moduleCode, int semNum) throws ParseException {
        LocalDate currentDate = LocalDate.now();
        String acadYear;

        // Calculate current academic year
        if (currentDate.getMonthValue() <= 6) {
            acadYear = (currentDate.getYear() - 1) + "-" + (currentDate.getYear());
        } else {
            acadYear = currentDate.getYear() + "-" + (currentDate.getYear() + 1);
        }

        // Link format is correct as of 3/4/2018
        String link = "http://api.nusmods.com/" + acadYear + "/" + semNum + "/modules/" + moduleCode + ".json";
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Grab lesson info from API and store as a map
            URL url = new URL(link);
            @SuppressWarnings("unchecked")
            Map<String, Object> mappedJson = mapper.readValue(url, HashMap.class);
            @SuppressWarnings("unchecked")
            ArrayList<HashMap<String, String>> lessonInfo = (ArrayList<HashMap<String, String>>)
                    mappedJson.get("Timetable");

            // Parse the information from API and creates an Arraylist of all possible lessons
            ArrayList<Lesson> lessons = new ArrayList<>();
            for (HashMap<String, String> lesson : lessonInfo) {
                Lesson lessonToAdd = new Lesson(moduleCode, lesson.get("ClassNo"), lesson.get("LessonType"),
                        lesson.get("WeekText"), lesson.get("DayText"), lesson.get("StartTime"), lesson.get("EndTime"));
                lessons.add(lessonToAdd);
            }

            return lessons;
        } catch (IOException exception) {
            throw new ParseException("Cannot retrieve module information");
        }
    }

    /**
     * Converts the short form (in url) to the lengthened form (in json file)
     * @param shortform shortform in url
     * @return String Full form of shortform
     * @throws ParseException when invalid shortform
     */
    private static String convertShortFormToLong(String shortform) throws ParseException {
        switch (shortform) {
        case "LEC":
            return "Lecture";

        case "TUT":
            return "Tutorial";

        case "LAB":
            return "Laboratory";

        case "SEM":
            return "Seminar-Style Module Class";

        case "SEC":
            return "Sectional Teaching";

        case "REC":
            return "Recitation";

        case "TUT2":
            return "Tutorial Type 2";

        case "TUT3":
            return "Tutorial Type 3";

        default:
            throw new ParseException("Error converting invalid shortform");
        }
    }
}
```
###### \java\seedu\address\logic\commands\BirthdaysCommand.java
``` java
/**
 * Shows either the birthday list or notification of Persons in StardyTogether
 */
public class BirthdaysCommand extends Command {

    public static final String COMMAND_WORD = "birthdays";

    public static final String ADDITIONAL_COMMAND_PARAMETER = "today";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list containing all persons' birthdays."
            + "Optional Parameters: "
            + ADDITIONAL_COMMAND_PARAMETER
            + "Example: " + COMMAND_WORD + ", " + COMMAND_WORD + " " + ADDITIONAL_COMMAND_PARAMETER;

    public static final String SHOWING_BIRTHDAY_MESSAGE = "Displaying birthday list";

    public static final String SHOWING_BIRTHDAY_NOTIFICATION = "Displaying today's birthdays";

    public static final String MESSAGE_NO_BIRTHDAY_TODAY = "No one is celebrating their birthdays today";

    private boolean isToday;

    public BirthdaysCommand(boolean todays) {
        requireNonNull(todays);
        this.isToday = todays;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BirthdaysCommand // instanceof handles nulls
                && isToday == ((BirthdaysCommand) other).isToday);
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);

        if (isToday) {
            LocalDate currentDate = LocalDate.now();

            EventsCenter.getInstance().post(new BirthdayNotificationEvent(parseBirthdaysForNotification(
                    model.getAddressBook().getPersonList(), currentDate), currentDate));
            return new CommandResult(SHOWING_BIRTHDAY_NOTIFICATION);
        } else {
            EventsCenter.getInstance().post(new BirthdayListEvent(parseBirthdaysForList(
                    model.getAddressBook().getPersonList())));
        }

        return new CommandResult(SHOWING_BIRTHDAY_MESSAGE);
    }

    /**
     * Parses the given list into their respective birthdays into a sorted string
     * @param observablelist given list of current addressBook
     * @return String to be displayed
     */
    public static String parseBirthdaysForList(ObservableList<Person> observablelist) {
        StringBuilder string = new StringBuilder();
        List<Person> list = new ArrayList<Person>();

        if (observablelist == null) {
            return "";
        }

        for (Person person: observablelist) {
            list.add(person);
        }

        list.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {

                int o1Days = o1.getBirthday().getDay();
                int o1Month = o1.getBirthday().getMonth();
                int o2Days = o2.getBirthday().getDay();
                int o2Month = o2.getBirthday().getMonth();

                if (o1Month != o2Month) {
                    return o1Month - o2Month;
                }

                return o1Days - o2Days;
            }
        });

        for (Person person: list) {
            string.append(person.getBirthday().getDay());
            string.append("/");
            string.append(person.getBirthday().getMonth());
            string.append("/");
            string.append(person.getBirthday().getYear());
            string.append(" ");
            string.append(person.getName().toString());
            string.append("\n");
        }

        return string.toString();
    }

    /**
     * Parses the given list into their respective birthdays into a sorted string
     * @param observablelist given list of current addressBook
     * @return String to be displayed
     */
    public static String parseBirthdaysForNotification(ObservableList<Person> observablelist, LocalDate currentDate) {
        StringBuilder string = new StringBuilder();
        List<Person> listOfPersonWithBirthdayToday = new ArrayList<Person>();

        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        if (observablelist == null) {
            return " ";
        } else if (observablelist.size() <= 0) {
            string.append("StardyTogether has no contacts :(\n");
            return string.toString();
        }

        for (Person person: observablelist) {
            if (person.getBirthday().getDay() == currentDay
                    && person.getBirthday().getMonth() == currentMonth) {
                listOfPersonWithBirthdayToday.add(person);
            }
        }

        if (listOfPersonWithBirthdayToday.size() == 0) {
            return MESSAGE_NO_BIRTHDAY_TODAY;
        }

        for (Person person: listOfPersonWithBirthdayToday) {
            int age;
            age = currentYear - person.getBirthday().getYear();
            string.append(person.getName().toString());
            string.append(" (");
            string.append(age);
            if (age != 1) {
                string.append(" years old)");
            } else if (age > 0) {
                string.append(" years old)");
            }
            string.append("\n");
        }

        return string.toString();
    }
}
```
###### \java\seedu\address\logic\commands\TimetableUnionCommand.java
``` java
/**
 * Retrieves the unified timetable of the persons identified using it's last displayed index from StardyTogether
 */
public class TimetableUnionCommand extends Command {
    public static final String COMMAND_WORD = "union";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the unified timetable of the persons entered\n"
            + "Parameters: INDEX ODD/EVEN INDEX1 INDEX2 INDEX3..."
            + "(indexes must be unique positive integers and separated by one space)\n"
            + "Example: " + COMMAND_WORD + "Odd " + "1 " + "2 " + "3";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "%1$s Combined Timetable: %2$s";

    private final ArrayList<Index> targetIndexes;
    private final String oddEven;
    private ArrayList<Person> personsToShow;
    private ArrayList<Timetable> timetablesToShow;

    public TimetableUnionCommand(ArrayList<Index> targetIndexes, String oddEven) {
        requireNonNull(targetIndexes);
        requireNonNull(oddEven);
        this.targetIndexes = targetIndexes;
        this.oddEven = oddEven;
        personsToShow = new ArrayList<Person>();
        timetablesToShow = new ArrayList<Timetable>();
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        preprocess();

        int oddEvenIndex = StringUtil.getOddEven(oddEven).getZeroBased();
        ArrayList<ArrayList<String>> commonTimeTable = Timetable.unionTimetable(timetablesToShow).get(oddEvenIndex);
        ObservableList<ArrayList<String>> timeTableList = FXCollections.observableArrayList(commonTimeTable);
        EventsCenter.getInstance().post(new TimeTableEvent(timeTableList));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, StringUtil.capitalize(oddEven),
                printNames()));
    }

    /**
     * Preprocess the required data for execution.
     * @throws CommandException when index out of bound
     */
    protected void preprocess() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index targetIndex : targetIndexes) {
            personsToShow.add(lastShownList.get(targetIndex.getZeroBased()));
        }

        for (Person person : personsToShow) {
            timetablesToShow.add(person.getTimetable());
        }

    }

    /**
     * Prints the names in personsToShow
     */
    protected String printNames() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < personsToShow.size(); i++) {
            sb.append(personsToShow.get(i).getName());
            if (i != personsToShow.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimetableUnionCommand // instanceof handles nulls
                && this.targetIndexes.equals(((TimetableUnionCommand) other).targetIndexes) // state check
                && this.oddEven.equals(((TimetableUnionCommand) other).oddEven)
                && Objects.equals(this.personsToShow, ((TimetableUnionCommand) other).personsToShow));

    }

    @Override
    public String toString() {
        return targetIndexes.toString() + " " + oddEven + " " + personsToShow;
    }
}
```
###### \java\seedu\address\logic\parser\BirthdaysCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BirthdaysCommand object
 */
public class BirthdaysCommandParser implements Parser<BirthdaysCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BirthdaysCommand
     * and returns an BirthdaysCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BirthdaysCommand parse(String args) throws ParseException {
        args = args.trim();
        String[] trimmedArgs = args.split("\\s+");

        if (trimmedArgs.length == 1) {   // Only birthdays alone is called
            if (trimmedArgs[0].equalsIgnoreCase(BirthdaysCommand.ADDITIONAL_COMMAND_PARAMETER)) {  // Check if valid
                return new BirthdaysCommand(true);
            } else if (trimmedArgs[0].equalsIgnoreCase("")) {
                return new BirthdaysCommand(false);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdaysCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BirthdaysCommand.MESSAGE_USAGE));
        }

    }

}
```
###### \java\seedu\address\logic\parser\TimetableUnionCommandParser.java
``` java
/**
 * Parses input arguments and creates a new TimetableUnionCommand object
 */
public class TimetableUnionCommandParser implements Parser<TimetableUnionCommand> {
    private static final String SPLIT_TOKEN = " ";
    private static final int ODD_EVEN_INDEX = 0;
    /**
     * Parses the given {@code String} of arguments in the context of the TimetableUnionCommand
     * and returns an TimetableUnionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TimetableUnionCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] splitArgs = trimmedArgs.split(SPLIT_TOKEN);
            if (splitArgs.length < 3) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableUnionCommand.MESSAGE_USAGE));
            }
            ArrayList<Index> indexes = new ArrayList<Index>();
            String oddEven = ParserUtil.parseOddEven(splitArgs[ODD_EVEN_INDEX]);

            for (int i = ODD_EVEN_INDEX + 1; i < splitArgs.length; i++) {
                Index indexToAdd = ParserUtil.parseIndex(splitArgs[i]);
                if (!indexes.contains(indexToAdd)) {
                    indexes.add(indexToAdd);
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            TimetableUnionCommand.MESSAGE_USAGE));
                }

            }

            return new TimetableUnionCommand(indexes, oddEven);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableUnionCommand.MESSAGE_USAGE));
        }
    }

}

```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday date can only contain numbers, and should follow the DDMMYYYY format";
    public static final String MESSAGE_INVALID_BIRTHDAY =
            "Birthday day is invalid";
    public static final String MESSAGE_INVALID_BIRTHMONTH =
            "Birthday month is invalid";
    public static final String MESSAGE_FUTURE_BIRTHDAY =
            "Birthday is set in the future";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d{8,8}";
    public final String value;

    private int day;
    private int month;
    private int year;

    /**
     * Constructs a {@code Birthday}.
     *  @param birthday A birthday number.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);

        if (isValidBirthday(birthday)) {
            this.value = birthday;
            this.day = parseDay(birthday);
            this.month = parseMonth(birthday);
            this.year = parseYear(birthday);
        } else {
            this.value = null;
        }
    }

    /**
     * Checks if given birthday string is valid
     * @param test Birthday to be tested
     * @return true if birthday is valid
     * @throws IllegalArgumentException if birthday is invalid
     */
    public static boolean isValidBirthday(String test) throws IllegalArgumentException {
        LocalDate today = LocalDate.now();
        int testDay;
        int testMonth;
        int testYear;

        // Check for DDMMYYYY format
        if (test.matches(BIRTHDAY_VALIDATION_REGEX)) {
            testDay = parseDay(test);
            testMonth = parseMonth(test);
            testYear = parseYear(test);
        } else {
            throw new IllegalArgumentException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        // Check for valid year
        if (today.getYear() < testYear) {
            throw new IllegalArgumentException(MESSAGE_FUTURE_BIRTHDAY);
        }
        // Check for valid day
        if (testDay == 0) {
            throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHDAY);
        }

        // Check for valid month and day
        switch (testMonth) {
        case 1:     // Jan
        case 3:     // Mar
        case 5:     // May
        case 7:     // Jul
        case 8:     // Aug
        case 10:    // Oct
        case 12:    // Dec
            if (testDay > 31) {
                throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHDAY);
            }
            break;
        case 4: // Apr
        case 6: // Jun
        case 9: // Sep
        case 11: // Nov
            if (testDay > 30) {
                throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHDAY);
            }
            break;
        case 2: // Feb
            if (testDay > 28) {
                throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHDAY);
            }
            break;
        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHMONTH);
        }

        // Check for future date
        if (today.getYear() == testYear) {
            if (today.getMonthValue() < testMonth) {
                throw new IllegalArgumentException(MESSAGE_FUTURE_BIRTHDAY);
            } else if (today.getMonthValue() == testMonth && today.getDayOfMonth() < testDay) {
                throw new IllegalArgumentException(MESSAGE_FUTURE_BIRTHDAY);
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Parses Day from Birthday string
     * isValidBirthday() should be called before this method
     * @param birthday assumed to be of format DDMMYYYY
     * @return int Day
     */
    private static int parseDay(String birthday) {
        return Integer.parseInt(birthday.substring(0, 2));
    }

    /**
     * Parses Month from Birthday string
     * isValidBirthday() should be called before this method
     * @param birthday assumed to be of format DDMMYYYY
     * @return integer Month
     */
    private static int parseMonth(String birthday) {
        return Integer.parseInt(birthday.substring(2, 4));
    }

    /**
     * Parses Year from Birthday string
     * isValidBirthday() should be called before this method
     * @param birthday assumed to be of format DDMMYYYY
     * @return integer Year
     */
    private static int parseYear(String birthday) {
        return Integer.parseInt(birthday.substring(4, 8));
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}

```
###### \java\seedu\address\model\person\timetable\Timetable.java
``` java
/**
 * Represents a Person's timetable in the address book
 */
public class Timetable {

    public static final String EMPTY_LINK = "";
    public static final String DUMMY_LINK_ONE = "http://modsn.us/aaaaa";
    public static final String DUMMY_LINK_TWO = "http://modsn.us/bbbbb";
    public static final String NUSMODS_SHORT = "modsn.us";
    public static final String URL_HOST_REGEX = "\\/\\/.*?\\/";
    public static final String MESSAGE_URL_CONSTRAINTS =
            "Timetable URL should only be NUSMods shortened URLs";
    public static final String MESSAGE_INVALID_URL =
            "The given NUSMods URL is invalid";

    public final String value;

    private TimetableData data;

    public Timetable(String url) {
        requireNonNull(url);
        String trimmedUrl = url.trim();
        this.value = trimmedUrl;
        // Create new empty timetable if url is empty or a dummy link
        if (trimmedUrl.equals(EMPTY_LINK) || trimmedUrl.equals(DUMMY_LINK_ONE) || trimmedUrl.equals(DUMMY_LINK_TWO)) {
            this.data = new TimetableData();
            return;
        }

        checkArgument(isValidUrl(trimmedUrl), MESSAGE_URL_CONSTRAINTS);

        try {
            this.data = parseUrl(trimmedUrl);
        } catch (ParseException pe) {
            this.data = new TimetableData(); // Create new empty timetable if url fails
        }
    }

    /**
     * Checks if string is a valid shortened NUSMods url
     * @param test
     * @return true if it follows the format of a valid shortened NUSMods url
     *         false if it doesn't
     */
    public static boolean isValidUrl(String test) {
        Matcher matcher = Pattern.compile(URL_HOST_REGEX).matcher(test);
        if (!matcher.find()) {
            return false;
        }

        String hostName = matcher.group()
                .substring(2, matcher.group().length() - 1);
        return hostName.equals(NUSMODS_SHORT);
    }

    /**
     * Returns the lesson at the specified slot, null if empty
     * @param week the week the lesson is found at
     * @param day the day the lesson is found at
     * @param timeSlot the timeslot the lesson is found at
     * @return Lesson found at that slot, null if slot is empty
     * @throws IllegalValueException when week,day,timeslot are invalid values
     */
    public Lesson getLessonFromSlot(String week, String day, int timeSlot) throws IllegalValueException {
        return data.getLessonFromSlot(week, day, timeSlot);
    }

    /**
     * Adds a lesson to the timetable
     * @param lessonToAdd lesson to be added
     * @throws IllegalValueException when week,day,timeslot are invalid values
     */
    public void addLessonToSlot(Lesson lessonToAdd) throws IllegalValueException {
        data.addLessonToSlot(lessonToAdd);
    }

    /**
     * Returns the timetable
     * @return Timetable as an Array
     */
    public ArrayList<ArrayList<ArrayList<String>>> getTimetable() {
        return data.getTimeTable();
    }

    /**
     * Returns the unified timetable
     * @return Timetable as an Array
     */
    public static ArrayList<ArrayList<ArrayList<String>>> unionTimetable(ArrayList<Timetable> timetables) {
        ArrayList<TimetableData> t = new ArrayList<TimetableData>();

        for (Timetable timetable : timetables) {
            t.add(timetable.data);
        }
        return TimetableData.unionTimeTable(t);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timetable // instanceof handles nulls
                && this.value.equals(((Timetable) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\timetable\TimetableData.java
``` java
/**
 * Represents the data of the timetable
 */
public class TimetableData {

    public static final int NUM_OF_WEEKS = 2;
    public static final int EVEN_WEEK_INDEX = 0;
    public static final int ODD_WEEK_INDEX = 1;
    public static final String EVEN_WEEK_IDENTIFIER = "Even Week";
    public static final String ODD_WEEK_IDENTIFIER = "Odd Week";
    public static final String EVERY_WEEK_IDENTIFIER = "Every Week";
    public static final String MESSAGE_INVALID_WEEK = "Week is invalid";

    private TimetableWeek[] timetableWeeks;

    // Constructs empty data
    public TimetableData() {
        constructEmptyData();
    }

    // Constructs with ArrayList of {@Lesson}
    public TimetableData(ArrayList<Lesson> lessonsToAdd) throws ParseException {
        constructEmptyData();
        requireNonNull(lessonsToAdd);

        // Immediate adding of lessons
        for (Lesson lessonToAdd: lessonsToAdd) {
            try {
                addLessonToSlot(lessonToAdd);
            } catch (IllegalValueException ie) {
                throw new ParseException(ie.getMessage());
            }
        }
    }

    /**
     * Constructs a empty structure for Timetable
     */
    private void constructEmptyData() {
        timetableWeeks = new TimetableWeek[NUM_OF_WEEKS];

        for (int i = 0; i < NUM_OF_WEEKS; i++) {
            timetableWeeks[i] = new TimetableWeek();
        }
    }

    /**
     * Returns the lesson at the specified slot, null if slot is empty
     * @param week
     * @param day
     * @param timeSlot
     * @return Lesson at the specified week, day and slot, null if slot is empty
     * @throws IllegalValueException when week, day, timeslot are invalid values
     */
    public Lesson getLessonFromSlot(String week, String day, int timeSlot) throws IllegalValueException {
        if (week.equalsIgnoreCase(EVEN_WEEK_IDENTIFIER)) {
            return timetableWeeks[EVEN_WEEK_INDEX].getLessonFromSlot(day, timeSlot);
        } else if (week.equalsIgnoreCase(ODD_WEEK_IDENTIFIER)) {
            return timetableWeeks[ODD_WEEK_INDEX].getLessonFromSlot(day, timeSlot);
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_WEEK);
        }
    }

    /**
     * Adds a lesson at its respective week
     * @param lessonToAdd lesson to be added
     * @throws IllegalValueException when week, day, timeslot are invalid values
     */
    public void addLessonToSlot(Lesson lessonToAdd) throws IllegalValueException {

        if (lessonToAdd.getWeekType().equalsIgnoreCase(EVEN_WEEK_IDENTIFIER)) {
            timetableWeeks[EVEN_WEEK_INDEX].addLessonToWeek(lessonToAdd);
        } else if (lessonToAdd.getWeekType().equalsIgnoreCase(ODD_WEEK_IDENTIFIER)) {
            timetableWeeks[ODD_WEEK_INDEX].addLessonToWeek(lessonToAdd);
        } else if (lessonToAdd.getWeekType().equalsIgnoreCase(EVERY_WEEK_IDENTIFIER)) {
            timetableWeeks[EVEN_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            timetableWeeks[ODD_WEEK_INDEX].addLessonToWeek(lessonToAdd);
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_WEEK);
        }
    }

    /**
     * Returns the Time Table
     * @return ArrayList with the  Time Table
     */
    public ArrayList<ArrayList<ArrayList<String>>> getTimeTable() {
        ArrayList<ArrayList<ArrayList<String>>> timetable = new ArrayList<>();
        for (TimetableWeek t : timetableWeeks) {
            timetable.add(t.getWeeklyTimeTable());
        }
        return timetable;
    }

    /**
     * Returns the unified Time Table
     * @return ArrayList with the  Time Table
     */
    public static ArrayList<ArrayList<ArrayList<String>>> unionTimeTable(ArrayList<TimetableData> timetables) {
        ArrayList<ArrayList<ArrayList<String>>> commonTimetable = new ArrayList<>();


        for (int i = 0; i < NUM_OF_WEEKS; i++) {
            ArrayList<TimetableWeek> t = new ArrayList<TimetableWeek>();
            for (TimetableData timetable : timetables) {
                t.add(timetable.timetableWeeks[i]);
            }
            commonTimetable.add(TimetableWeek.unionTimetableWeek(t));
        }

        return commonTimetable;
    }

}
```
###### \java\seedu\address\model\person\timetable\TimetableDay.java
``` java
/**
 * Represents a day in the timetable
 */
public class TimetableDay {

    public static final int NUM_OF_SLOTS = 24;
    public static final String MESSAGE_INVALID_TIMESLOT = "Time slot is invalid";
    public static final String TITLE_OCCUPIED = "Occupied";
    public static final String EMPTY_SLOT_STRING = "";

    // Cut into 24-h slots. 0000 being timetableSlots[0] and 2300 being timetableSlots[23]
    private TimetableSlot[] timetableSlots;

    public TimetableDay() {
        timetableSlots = new TimetableSlot[NUM_OF_SLOTS];
        for (int i = 0; i < NUM_OF_SLOTS; i++) {
            timetableSlots[i] = new TimetableSlot();
        }
    }

    /**
     * Adds a lesson at its respective slot
     * @param lessonToAdd lesson to be added
     * @throws IllegalValueException when slot is invalid
     */
    public void addLessonToDay(Lesson lessonToAdd) throws IllegalValueException {
        int startTimeIndex = parseStartEndTime(lessonToAdd.getStartTime());
        int endTimeIndex = parseStartEndTime(lessonToAdd.getEndTime());

        for (int i = startTimeIndex; i < endTimeIndex; i++) {
            timetableSlots[i].addLessonToSlot(lessonToAdd);
        }
    }

    /**
     * Parses the start and time parsed from NUSMods to a index to be used for array of slots
     * @param time timing from NUSMods
     * @return index for slot array
     */
    public static int parseStartEndTime(String time) throws IllegalValueException {
        int value = Integer.parseInt(time) / 100;

        if (isValidTimeSlot(value)) {
            return value;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TIMESLOT);
        }
    }

    /**
     * Returns the lesson at the specified slot, null if slot is empty
     * @param timeSlot
     * @return Lesson at the specified slot, null if slot is empty
     * @throws IllegalValueException when timeslot is invalid value
     */
    public Lesson getLessonFromSlot(int timeSlot) throws IllegalValueException {
        if (timeSlot > 0 && timeSlot <= 23) {
            return timetableSlots[timeSlot].getLesson();
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TIMESLOT);
        }
    }

    /**
     * Checks if the given index is valid
     * @param index
     * @return true or false
     */
    private static boolean isValidTimeSlot(int index) {
        return (index < NUM_OF_SLOTS && index >= 0);
    }

    /**
     * Returns the Time Table for the day
     * @return ArrayList with the  Time Table
     */
    public ArrayList<String> getDailyTimeTable() {
        ArrayList<String> timetable = new ArrayList<>();
        for (int i = 8; i < 22; i++) {
            TimetableSlot t = timetableSlots[i];
            timetable.add(t.toString());
        }
        return timetable;
    }

    /**
     * Returns the unified Timetable
     * @return ArrayList with the Time Table
     */
    public static ArrayList<String> unionTimetableDay(ArrayList<TimetableDay> timetables) {
        ArrayList<String> commonTimetable = new ArrayList<>();
        boolean checker;

        for (int i = 8; i < 22; i++) {
            checker = false;
            for (TimetableDay timetable : timetables) {
                TimetableSlot t = timetable.timetableSlots[i];
                if (!t.toString().equals(EMPTY_SLOT_STRING)) {
                    checker = true;
                    break;
                }
            }

            if (checker) {
                commonTimetable.add(TITLE_OCCUPIED);
            } else {
                commonTimetable.add(EMPTY_SLOT_STRING);
            }
        }
        return commonTimetable;
    }
}
```
###### \java\seedu\address\model\person\timetable\TimetableSlot.java
``` java
/**
 * Represents a slot in the timetable
 */
public class TimetableSlot {

    private Lesson lesson;

    /**
     * Fills up the slot with the given lesson
     * @param lesson
     */
    public void addLessonToSlot(Lesson lesson) {
        requireNonNull(lesson);
        this.lesson = lesson;
    }

    public Lesson getLesson() {
        return lesson;
    }

    @Override
    public String toString() {
        if (lesson == null) {
            return "";
        }
        return lesson.toString();
    }
}
```
###### \java\seedu\address\model\person\timetable\TimetableWeek.java
``` java
/**
 * Represents a week in the timetable
 */
public class TimetableWeek {

    public static final int NUM_OF_DAYS = 5;
    public static final int MONDAY_INDEX = 0;
    public static final int TUESDAY_INDEX = 1;
    public static final int WEDNESDAY_INDEX = 2;
    public static final int THURSDAY_INDEX = 3;
    public static final int FRIDAY_INDEX = 4;

    public static final String MONDAY_IDENTIFIER = "Monday";
    public static final String TUESDAY_IDENTIFIER = "Tuesday";
    public static final String WEDNESDAY_IDENTIFIER = "Wednesday";
    public static final String THURSDAY_IDENTIFIER = "Thursday";
    public static final String FRIDAY_IDENTIFIER = "Friday";
    public static final String MESSAGE_INVALID_DAY = "Day is invalid";

    private TimetableDay[] timetableDays;

    public TimetableWeek() {
        timetableDays = new TimetableDay[NUM_OF_DAYS];
        for (int i = 0; i < NUM_OF_DAYS; i++) {
            timetableDays[i] = new TimetableDay();
        }
    }

    /**
     * Add lesson to its respective day
     * @param lesson Lesson to be added
     * @throws IllegalValueException when Day is invalid
     */
    public void addLessonToWeek(Lesson lesson) throws IllegalValueException {
        try {
            switch (lesson.getDay()) {
            case MONDAY_IDENTIFIER:
                timetableDays[MONDAY_INDEX].addLessonToDay(lesson);
                break;

            case TUESDAY_IDENTIFIER:
                timetableDays[TUESDAY_INDEX].addLessonToDay(lesson);
                break;

            case WEDNESDAY_IDENTIFIER:
                timetableDays[WEDNESDAY_INDEX].addLessonToDay(lesson);
                break;

            case THURSDAY_IDENTIFIER:
                timetableDays[THURSDAY_INDEX].addLessonToDay(lesson);
                break;

            case FRIDAY_IDENTIFIER:
                timetableDays[FRIDAY_INDEX].addLessonToDay(lesson);
                break;

            default:
                throw new IllegalValueException(MESSAGE_INVALID_DAY);
            }
        } catch (IllegalValueException ie) {
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
    }

    /**
     * Returns the lesson at the specified slot, null if slot is empty
     * @param day
     * @param timeSlot
     * @return the Lesson at the specified day and timeslot
     * @throws IllegalValueException when day, timeslot are invalid values
     */
    public Lesson getLessonFromSlot(String day, int timeSlot) throws IllegalValueException {
        switch (day) {
        case MONDAY_IDENTIFIER:
            return timetableDays[MONDAY_INDEX].getLessonFromSlot(timeSlot);

        case TUESDAY_IDENTIFIER:
            return timetableDays[TUESDAY_INDEX].getLessonFromSlot(timeSlot);

        case WEDNESDAY_IDENTIFIER:
            return timetableDays[WEDNESDAY_INDEX].getLessonFromSlot(timeSlot);

        case THURSDAY_IDENTIFIER:
            return timetableDays[THURSDAY_INDEX].getLessonFromSlot(timeSlot);

        case FRIDAY_IDENTIFIER:
            return timetableDays[FRIDAY_INDEX].getLessonFromSlot(timeSlot);

        default:
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
    }

    /**
     * Returns the Time Table for the week
     * @return ArrayList with the  Time Table
     */
    public ArrayList<ArrayList<String>> getWeeklyTimeTable() {
        ArrayList<ArrayList<String>> timetable = new ArrayList<>();
        for (int i = 0; i < timetableDays.length; i++) {
            TimetableDay t = timetableDays[i];
            ArrayList<String> dailyTimeTable = t.getDailyTimeTable();
            switch (i) {
            case 0:
                dailyTimeTable.add(0, MONDAY_IDENTIFIER);
                break;
            case 1:
                dailyTimeTable.add(0, TUESDAY_IDENTIFIER);
                break;
            case 2:
                dailyTimeTable.add(0, WEDNESDAY_IDENTIFIER);
                break;
            case 3:
                dailyTimeTable.add(0, THURSDAY_IDENTIFIER);
                break;
            case 4:
                dailyTimeTable.add(0, FRIDAY_IDENTIFIER);
                break;
            default:
                break;
            }
            timetable.add(dailyTimeTable);
        }
        return timetable;
    }

    /**
     * Returns the unified Time Table for the week
     * @return ArrayList with the Time Table
     */
    public static ArrayList<ArrayList<String>> unionTimetableWeek(ArrayList<TimetableWeek> timetables) {
        ArrayList<ArrayList<String>> commonTimetable = new ArrayList<>();

        for (int i = 0; i < NUM_OF_DAYS; i++) {
            ArrayList<TimetableDay> t = new ArrayList<TimetableDay>();
            for (TimetableWeek timetable : timetables) {
                t.add(timetable.timetableDays[i]);
            }

            ArrayList<String> dailyTimeTable = TimetableDay.unionTimetableDay(t);
            switch (i) {
            case 0:
                dailyTimeTable.add(0, MONDAY_IDENTIFIER);
                break;
            case 1:
                dailyTimeTable.add(0, TUESDAY_IDENTIFIER);
                break;
            case 2:
                dailyTimeTable.add(0, WEDNESDAY_IDENTIFIER);
                break;
            case 3:
                dailyTimeTable.add(0, THURSDAY_IDENTIFIER);
                break;
            case 4:
                dailyTimeTable.add(0, FRIDAY_IDENTIFIER);
                break;
            default:
                break;
            }
            commonTimetable.add(dailyTimeTable);
        }
        return commonTimetable;
    }


}
```
###### \java\seedu\address\ui\BirthdayList.java
``` java
/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BirthdayList extends UiPart<Region> {

    private static final String FXML = "BirthdayList.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea birthdayList;

    public BirthdayList() {
        super(FXML);
        birthdayList.textProperty().bind(displayed);
    }

    public void loadList(String list) {
        Platform.runLater(() -> displayed.setValue(list));
    }

}
```
###### \java\seedu\address\ui\BirthdayNotification.java
``` java
/**
 * A ui for the notification dialog that is displayed at the start of the application and
 * after `birthdays today` is called.
 */
public class BirthdayNotification extends UiPart<Region> {

    public static final String NOTIFICATION_TITLE = "Birthdays Today";
    public static final String NOTIFICATION_HEADER_TEXT = "Birthday(s) on ";

    private static final String FXML = "BirthdayList.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public BirthdayNotification() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleBirthdayNotificationEvent(BirthdayNotificationEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.setTitle(NOTIFICATION_TITLE);
        alert.setHeaderText(NOTIFICATION_HEADER_TEXT + dtf.format(event.getCurrentDate()));
        alert.setContentText(event.getBirthdayList());
        alert.getDialogPane().setId("birthdayDialogPane");
        alert.showAndWait();
    }
}
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
    @Subscribe
    private void handleBirthdayListEvent(BirthdayListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        birthdayPlaceholder.getChildren().removeAll();
        birthdayList.loadList(event.getBirthdayList());
        birthdayPlaceholder.getChildren().add(birthdayList.getRoot());
        birthdayPlaceholder.toFront();
    }
```
###### \java\seedu\address\ui\TimetableUnionPanel.java
``` java

/**
 * A ui for the info panel that is displayed when the timetable command is called.
 */
public class TimetableUnionPanel extends UiPart<Region> {
    private static final String COLUMNHEADER_STYLE_CLASS = "column-header";
    private static final String TABLECELL_STYLE_CLASS = "table-cell";
    private static final String EMPTY_STYLE_CLASS = "timetable-cell-empty";
    private static final String[] MOD_COLOR_STYLES = { "modteal", "modsandybrown", "modplum", "modyellow",
                                                         "modyellow"};
    private static final String FXML = "TimetableUnionPanel.fxml";

    private ArrayList<TableColumn<ArrayList<String>, String>> columns;
    @FXML
    private TableView timetableUnion;
    @FXML
    private TableColumn<ArrayList<String>, String> day;
    @FXML
    private TableColumn<ArrayList<String>, String> eightAm;
    @FXML
    private TableColumn<ArrayList<String>, String> nineAm;
    @FXML
    private TableColumn<ArrayList<String>, String> tenAm;
    @FXML
    private TableColumn<ArrayList<String>, String> elevenAm;
    @FXML
    private TableColumn<ArrayList<String>, String> twelvePm;
    @FXML
    private TableColumn<ArrayList<String>, String> onePm;
    @FXML
    private TableColumn<ArrayList<String>, String> twoPm;
    @FXML
    private TableColumn<ArrayList<String>, String> threePm;
    @FXML
    private TableColumn<ArrayList<String>, String> fourPm;
    @FXML
    private TableColumn<ArrayList<String>, String> fivePm;
    @FXML
    private TableColumn<ArrayList<String>, String> sixPm;
    @FXML
    private TableColumn<ArrayList<String>, String> sevenPm;
    @FXML
    private TableColumn<ArrayList<String>, String> eightPm;


    public TimetableUnionPanel() {
        super(FXML);
        timetableUnion.setItems(null);
    }

    public TimetableUnionPanel(ObservableList<ArrayList<String>> schedules) {
        super(FXML);
        timetableUnion.setItems(schedules);
        timetableUnion.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initializeColumns();
        initializeTableColumns();
        day.setMinWidth(100);
        day.setMaxWidth(100);
    }

    /**
     * Initializes columns
     */
    private void initializeColumns() {
        columns = new ArrayList<>();
        columns.add(day);
        columns.add(eightAm);
        columns.add(nineAm);
        columns.add(tenAm);
        columns.add(elevenAm);
        columns.add(twelvePm);
        columns.add(onePm);
        columns.add(twoPm);
        columns.add(threePm);
        columns.add(fourPm);
        columns.add(fivePm);
        columns.add(sixPm);
        columns.add(sevenPm);
        columns.add(eightPm);
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() {
        for (int i = 0; i < columns.size(); i++) {
            final int j = i;
            columns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(j)));
            columns.get(i).impl_setReorderable(false);
            if (j != 0) {
                columns.get(i).setMinWidth(75);
                columns.get(i).setMaxWidth(200);
            }

        }
    }

    /**
     * Sets the command box style to indicate free or having lesson
     */
    public void setStyle() {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellFactory(column -> {
                return new TableCell<ArrayList<String>, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {

                            setText(item);
                            removeAllStyle(this);
                            if ("".equals(getItem())) {
                                getStyleClass().add(EMPTY_STYLE_CLASS);
                            } else if (StringUtil.isDay(getItem())) {
                                getStyleClass().add(COLUMNHEADER_STYLE_CLASS);
                            } else {
                                getStyleClass().add(getColorStyleFor(getItem()));
                            }
                        }
                    }
                };
            });
        }
    }

    /**
     * Removes all styles present in cell
     * @param tableCell Cell with its style to be removed
     */
    private static void removeAllStyle(TableCell<ArrayList<String>, String> tableCell) {
        for (String color : MOD_COLOR_STYLES) {
            tableCell.getStyleClass().remove(color);
        }
        tableCell.getStyleClass().remove(EMPTY_STYLE_CLASS);
        tableCell.getStyleClass().remove(TABLECELL_STYLE_CLASS);
    }

    /**
     * Returns a Color Style for the Module based on its hashcode.
     * @param modName
     * @return colorStyle for {@code modName}'s label.
     */
    private static String getColorStyleFor(String modName) {
        return MOD_COLOR_STYLES[Math.abs(modName.hashCode()) % MOD_COLOR_STYLES.length];
    }
}
```
###### \resources\view\BirthdayList.fxml
``` fxml
<StackPane fx:id="birthdayPlaceholder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <TextArea fx:id="birthdayList" editable="false" />
   </children>
</StackPane>
```
###### \resources\view\TimetableUnionPanel.fxml
``` fxml

<StackPane fx:id="timetableUnionPlaceholder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <TableView fx:id="timetableUnion" stylesheets="@DarkTheme.css">
         <columns>
             <TableColumn fx:id="day" prefWidth="100.0" text="" />
             <TableColumn fx:id="eightAm" prefWidth="60.0" text="0800" />
             <TableColumn fx:id="nineAm" prefWidth="60.0" text="0900" />
             <TableColumn fx:id="tenAm" prefWidth="60.0" text="1000" />
             <TableColumn fx:id="elevenAm" prefWidth="60.0" text="1100" />
             <TableColumn fx:id="twelvePm" prefWidth="60.0" text="1200" />
             <TableColumn fx:id="onePm" prefWidth="60.0" text="1300" />
             <TableColumn fx:id="twoPm" prefWidth="60.0" text="1400" />
             <TableColumn fx:id="threePm" prefWidth="60.0" text="1500" />
             <TableColumn fx:id="fourPm" prefWidth="60.0" text="1600" />
             <TableColumn fx:id="fivePm" prefWidth="60.0" text="1700" />
             <TableColumn fx:id="sixPm" prefWidth="60.0" text="1800" />
             <TableColumn fx:id="sevenPm" prefWidth="60.0" text="1900" />
             <TableColumn fx:id="eightPm" prefWidth="60.0" text="2000" />
         </columns></TableView>
    </children>
</StackPane>
```
