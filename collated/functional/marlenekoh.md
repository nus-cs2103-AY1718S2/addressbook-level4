# marlenekoh
###### /resources/view/TimetableStyle.css
``` css
body {
    font-family: "Arial";
}

th,td {
    margin: 0;
    text-align: center;
    border-collapse: collapse;
    -webkit-column-width: 100px; /* Chrome, Safari, Opera */
    -moz-column-width: 100px; /* Firefox */
    column-width: 100px;
    outline: 1px solid #e3e3e3;
}

td {
    padding: 5px 10px;
}

th {
    background: #666;
    color: white;
    padding: 5px 10px;
}
```
###### /java/seedu/address/ui/BrowserPanel.java
``` java
    /**
     * Loads the timetable page of a person into browser panel.
     */
    public void loadTimetablePage() {
        String timetablePageUrl;
        if (runningFromIntelliJ()) {
            timetablePageUrl = FILE_PREFIX + getIntellijRootDir() + INTELLIJ_DATA_FILE_FOLDER + TIMETABLE_PAGE;
        } else {
            timetablePageUrl = getJarDir() + JAR_DATA_FILE_FOLDER + TIMETABLE_PAGE;
        }
        loadPage(timetablePageUrl);
    }

    /**
     * Gets the directory containing the root folder
     * @return a String containing the directory path
     */
    private String getIntellijRootDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Gets the directory containing the executing jar.
     * @return a String containing the directory path
     */
    private String getJarDir() {
        String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm();
        try {
            jarPath = URLDecoder.decode(jarPath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.warning("The Character Encoding is not supported.");
        }
        return jarPath.substring(0, jarPath.lastIndexOf('/'));
    }

    /**
     * Checks if the current code is running from IntelliJ (for debugging) or from the jar file.
     * @return true if running from IntelliJ
     */
    private static boolean runningFromIntelliJ() {
        String classPath = System.getProperty("java.class.path");
        Logger logger = LogsCenter.getLogger(MainApp.class);
        return classPath.contains("idea_rt.jar");
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    /**
     * Replaces the Calendar with Timetable Page in Browser Panel
     */
    public void handleShowTimetable() {
        browserPanel.loadTimetablePage();
        if (!browserPlaceholder.getChildren().contains(browserPanel.getRoot())) {
            browserPlaceholder.getChildren().add(browserPanel.getRoot());
        }
    }

    /**
     * Replaces the Timetable Page with Calendar in Browser Panel
     */
    public void handleHideTimetable() {
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(browserPanel.getCalendarRoot());
    }

```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @Subscribe
    private void handleShowTimetableRequestEvent(ShowTimetableRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleShowTimetable();
    }

    @Subscribe
    private void handleHideTimetableRequestEvent(HideTimetableRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHideTimetable();
    }
}
```
###### /java/seedu/address/ui/ListPanel.java
``` java
    @Subscribe
    private void handleShowTimetableRequestEvent (ShowTimetableRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(PARTNER_INDEX);
    }

    @Subscribe
    private void handleHideTimetableRequestEvent (HideTimetableRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        deselect();
    }

```
###### /java/seedu/address/commons/events/ui/ShowTimetableRequestEvent.java
``` java
/**
 * An event requesting to view the partner's timetable.
 */
public class ShowTimetableRequestEvent extends BaseEvent {

    public ShowTimetableRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/HideTimetableRequestEvent.java
``` java
/**
 * An event requesting to view the partner's timetable.
 */
public class HideTimetableRequestEvent extends BaseEvent {

    public HideTimetableRequestEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/model/TimetableChangedEvent.java
``` java
/** Indicates the Timetable of the partner has changed*/
public class TimetableChangedEvent extends BaseEvent {
    public final Timetable timetable;

    public TimetableChangedEvent(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public String toString() {
        return timetable.value;
    }
}
```
###### /java/seedu/address/logic/commands/ShowCalendarCommand.java
``` java
/**
 * Deselects your partner from NUSCouples.
 */
public class ShowCalendarCommand extends Command {
    public static final String COMMAND_WORD = "cview";
    public static final String COMMAND_ALIAS = "cv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows your calendar view.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DESELECT_PERSON_SUCCESS = "Calendar view displayed.";
    public static final String MESSAGE_DESELECT_PERSON_FAILURE = "Calendar view is already displayed.";

    private ReadOnlyPerson partner;

    public ShowCalendarCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            partner = model.getPartner();
            partner.getTimetable();
            model.requestHideTimetable();
        } catch (NullPointerException npe) {
            throw new CommandException(MESSAGE_DESELECT_PERSON_FAILURE);
        }
        return new CommandResult(MESSAGE_DESELECT_PERSON_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCalendarCommand // instanceof handles nulls
                && this.partner.equals(((ShowCalendarCommand) other).partner)); // state check
    }

}
```
###### /java/seedu/address/storage/TimetableStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.person.timetable}.
 */
public interface TimetableStorage {

    /**
     * Creates TimetableStyle.css file at path {@code timetablePageCssPath}
     */
    void createTimetablePageCssFile();

    /**
     * Updates TimetablePage.html file at path {@code timetablePageHtmlPath} with new timetable module information
     */
    void setUpTimetablePageHtmlFile();

    /**
     * Writes the given string to {@code timetableInfoFilePath}
     * @param toWrite contents to write to {@code timetableInfoFilePath}
     */
    void setUpTimetableDisplayFiles(String toWrite);

    /**
     * Writes a string to the file at {@code path}
     * @param toWrite the String to write
     * @param path the path of the file
     */
    void writeToFile(String toWrite, String path);

    /**
     * Gets file contents from the file at the given path
     * @return String containing file contents
     */
    String getFileContents(String path) throws FileNotFoundException;

    /**
     * Replaces some text of {@code contents} with {@code replace}
     * @param contents original content of the javascript file
     * @param replace new text
     * @param start of text in {@code contents} to replace (exclusive)
     * @param end of text in {@code contents} to replace (exclusive)
     * @return new content
     */
    String replaceLineExcludingStartEnd(String contents, String replace, String start, String end);
}
```
###### /java/seedu/address/storage/Storage.java
``` java

    /**
     * Saves the timetable display info to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTimetableChangedEvent(TimetableChangedEvent event);
}
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    @Override
    public void createTimetablePageCssFile() {
        timetableStorage.createTimetablePageCssFile();
    }

    @Override
    public void setUpTimetablePageHtmlFile() {
        timetableStorage.setUpTimetablePageHtmlFile();
    }

    @Override
    public void setUpTimetableDisplayFiles(String toWrite) {
        timetableStorage.setUpTimetableDisplayFiles(toWrite);
    }

    @Override
    public void writeToFile(String toWrite, String path) {
        timetableStorage.writeToFile(toWrite, path);
    }

    @Override
    public String getFileContents(String path) throws FileNotFoundException {
        return timetableStorage.getFileContents(path);
    }

    @Override
    public String replaceLineExcludingStartEnd(String contents, String replace, String start, String end) {
        return timetableStorage.replaceLineExcludingStartEnd(contents, replace, start, end);
    }

    @Subscribe
    public void handleTimetableChangedEvent(TimetableChangedEvent event) {
        setUpTimetableDisplayFiles(event.timetable.getTimetableDisplayInfo());
        setUpTimetablePageHtmlFile();
        raise(new ShowTimetableRequestEvent());
    }
}
```
###### /java/seedu/address/storage/FileTimetableStorage.java
``` java
/**
 * A class to write and read Timetable data stored on the hard disk.
 */
public class FileTimetableStorage implements TimetableStorage {
    private static final Logger logger = LogsCenter.getLogger(FileTimetableStorage.class);

    private String timetablePageHtmlPath;
    private String timetablePageCssPath;
    private String timetableDisplayInfoFilePath;

    public FileTimetableStorage(String timetablePageHtmlPath, String timetablePageCssPath,
                                String timetableDisplayInfoFilePath) {
        this.timetablePageHtmlPath = timetablePageHtmlPath;
        this.timetablePageCssPath = timetablePageCssPath;
        this.timetableDisplayInfoFilePath = timetableDisplayInfoFilePath;
    }

    public String getTimetablePageHtmlPath() {
        return timetablePageHtmlPath;
    }

    public String getTimetablePageCssPath() {
        return timetablePageCssPath;
    }

    public String getTimetableDisplayInfoFilePath() {
        return timetableDisplayInfoFilePath;
    }

    @Override
    public void setUpTimetableDisplayFiles(String toWrite) {
        writeToFile(toWrite, timetableDisplayInfoFilePath);
        createTimetablePageCssFile();
        setUpTimetablePageHtmlFile();
    }

    @Override
    public void createTimetablePageCssFile() {
        try {
            writeToFile(SampleDataUtil.getDefaultTimetablePageCss(), timetablePageCssPath);
        } catch (IOException e) {
            logger.severe("Unable to get default timetable style");
        }
    }

    @Override
    public void setUpTimetablePageHtmlFile() {
        try {
            writeToFile(SampleDataUtil.getDefaultTimetablePageHtml(), timetablePageHtmlPath);
            String oldContent = getFileContents(timetablePageHtmlPath);
            String toReplace = getFileContents(timetableDisplayInfoFilePath);
            String newContent = replaceLineExcludingStartEnd(oldContent, toReplace, "timetable", "];");
            writeToFile(newContent, timetablePageHtmlPath);
        } catch (FileNotFoundException e) {
            logger.warning("File not found");
        } catch (IOException e) {
            logger.severe("Unable to get default timetable page");
        }
    }

    @Override
    public void writeToFile(String toWrite, String path) {
        File file = new File(path);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(toWrite);
            printWriter.close();
        } catch (FileNotFoundException e) {
            logger.warning("File not found");
        }
    }

    @Override
    public String getFileContents(String path) throws FileNotFoundException {
        File file = new File(path);
        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }
                br.close();
                return sb.toString();
            } else {
                throw new FileNotFoundException("File does not exist");
            }
        } catch (IOException e) {
            logger.warning("Exception in reading file");
        }
        return null;
    }

    @Override
    public String replaceLineExcludingStartEnd(String contents, String replace, String start, String end) {
        StringBuilder sb = new StringBuilder();
        int startPos = contents.indexOf(start);
        int endPos = contents.indexOf(end);
        sb.append(contents.substring(0, startPos));
        sb.append(replace);
        sb.append(contents.substring(endPos + end.length()));
        return sb.toString();
    }
}
```
###### /java/seedu/address/model/util/SampleDataUtil.java
``` java
    public static String getDefaultTimetablePageHtml() throws IOException {
        URL url = MainApp.class.getResource("/view/TimetablePage.html");
        return Resources.toString(url, Charsets.UTF_8);
    }

    public static String getDefaultTimetablePageCss() throws IOException {
        URL url = MainApp.class.getResource("/view/TimetableStyle.css");
        return Resources.toString(url, Charsets.UTF_8);
    }

}
```
###### /java/seedu/address/model/person/timetable/TimetableUtil.java
``` java
/**
 * A class containing utility methods for parsing an NUSMods short URL and setting up a Timetable
 */
public class TimetableUtil {
    public static final int MONDAY_INDEX = 0;
    public static final int TUESDAY_INDEX = 1;
    public static final int WEDNESDAY_INDEX = 2;
    public static final int THURSDAY_INDEX = 3;
    public static final int FRIDAY_INDEX = 4;
    public static final String[] DAYS = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
    public static final String[] TIMES = {
        "0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130",
        "1200", "1230", "1300", "1330", "1400", "1430", "1500", "1530",
        "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930",
        "2000", "2030", "2100", "2130", "2200", "2230", "2300", "2330"
    };
    public static final String[] WEEKS = {"Odd Week", "Even Week", "Every Week"};
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final String SPLIT_QUESTION_MARK = "\\?";
    private static final String SPLIT_AMPERSAND = "&";
    private static final String SPLIT_EQUALS = "=";
    private static final String SPLIT_COMMA = ",";
    private static final String SPLIT_COLON = ":";
    private static final String REPLACE_NON_DIGIT_CHARACTERS = "[^0-9]";
    private static final String INVALID_SHORT_URL_RESULT = "http://modsn.us";
    private static final String MESSAGE_INVALID_SHORT_URL = "Invalid short NUSMods URL provided.";
    private static final String MESSAGE_INVALID_CLASS_TYPE = "Invalid class type";
    private static final String MESSAGE_INVALID_DAY_TYPE = "Invalid day type";
    private static final String HTTP_METHOD_GET = "GET";
    private static final int HTTP_METHOD_RESPONSE_OK = 200;
    private static final int SEM_NUMBER_INDEX = 0;
    private static final int MODULE_INFORMATION_INDEX = 1;
    private static final int MODULE_CODE_INDEX = 0;
    private static final int MODULE_CODE_REMAINING_INDEX = 1;
    private static final int LESSON_TYPE_INDEX = 0;
    private static final int CLASS_TYPE_INDEX = 1;

    /**
     * Sets up attributes of a given {@code Timetable}.
     * @param timetable Timetable to be set up
     */
    public static void setUpTimetableInfo(Timetable timetable) {
        try {
            setExpandedTimetableUrl(timetable);
            setSemNumFromExpandedUrl(timetable);
            setListOfModules(timetable);
            setListOfDays(timetable);
            setTimetableDisplayInfo(timetable);
        } catch (ParseException e) {
            logger.warning(MESSAGE_INVALID_SHORT_URL);
        }
    }

    /**
     * Sets up timetable for viewing.
     * @param timetable to set up
     * @return a timetable containing the timetableDisplayInfo
     */
    public static Timetable setUpTimetableInfoView(Timetable timetable) {
        requireNonNull(timetable);
        ArrayList<TimetableModuleSlot> unsortedModuleSlots =
                TimetableUtil.setUpUnsortedModuleSlotsForViewing(timetable);
        timetable.setAllModulesSlots(unsortedModuleSlots);

        HashMap<String, ArrayList<TimetableModuleSlot>> sortedModuleSlots =
                TimetableUtil.sortModuleSlotsByDay(unsortedModuleSlots);
        timetable.setDaysToTimetableModuleSlots(sortedModuleSlots);

        TimetableUtil.setTimetableDisplayInfo(timetable);
        return timetable;
    }

    /**
     * Sets up timetable for comparing.
     * @param first timetable to compare
     * @param second timetable to compare
     * @return a timetable containing the combined timetableDisplayInfo
     */
    public static Timetable setUpTimetableInfoCompare(Timetable first, Timetable second) {
        ArrayList<TimetableModuleSlot> unsortedModuleSlots =
                setUpUnsortedModuleSlotsForComparing(first, second);
        HashMap<String, ArrayList<TimetableModuleSlot>> sortedModuleSlots =
                sortModuleSlotsByDay(unsortedModuleSlots);
        second.setDaysToTimetableModuleSlots(sortedModuleSlots);
        setTimetableDisplayInfo(second);
        return second;
    }

    /**
     * Sets the expanded URL for {@code timetable}.
     * @param timetable Timetable whose expanded URL is to be set
     */
    public static void setExpandedTimetableUrl(Timetable timetable) throws ParseException {
        String expandedUrl = expandShortTimetableUrl(timetable);
        timetable.setExpandedUrl(expandedUrl);
    }

    /**
     * Expands short NUSMods timetable URL to a long NUSMods timetable URL from {@timetable}.
     * @param timetable whose url is to be parsed
     * @return expanded NUSMods timetable URL
     * @throws ParseException if short url provided is invalid short NUSMods timetable URL
     */
    private static String expandShortTimetableUrl(Timetable timetable) throws ParseException {
        String timetableUrl = timetable.value;
        checkArgument(Timetable.isValidTimetable(timetableUrl), Timetable.MESSAGE_TIMETABLE_CONSTRAINTS);
        String expandedUrl = null;
        try {
            final URL shortUrl = new URL(timetableUrl);
            final HttpURLConnection urlConnection = (HttpURLConnection) shortUrl.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            expandedUrl = urlConnection.getHeaderField("location");

            if (expandedUrl.equals(INVALID_SHORT_URL_RESULT)) {
                throw new ParseException(MESSAGE_INVALID_SHORT_URL);
            }
        } catch (MalformedURLException e) {
            logger.warning("URL provided is malformed");
        } catch (IOException e) {
            logger.warning("Failed to open connection");
        }
        return expandedUrl;
    }

    /**
     * Sets the {@code currentSemester} for {@code timetable}.
     * @param timetable Timetable whose {@code currentSemester} is to be set
     */
    private static void setSemNumFromExpandedUrl(Timetable timetable) {
        timetable.setCurrentSemester(getSemNumFromExpandedUrl(timetable));
    }

    /**
     * Parses for {@code currentSemester} from expandedUrl of {@code timetable}
     * @param timetable whose {@code currentSemester} is to be set
     */
    private static int getSemNumFromExpandedUrl(Timetable timetable) {
        String expandedUrl = timetable.getExpandedUrl();
        requireNonNull(expandedUrl);
        String[] moduleInformation = expandedUrl.split(SPLIT_QUESTION_MARK);
        return Integer.valueOf(moduleInformation[SEM_NUMBER_INDEX]
                .replaceAll(REPLACE_NON_DIGIT_CHARACTERS, ""));
    }

    /**
     * Sets listOfModules in {@code timetable}
     * @param timetable whose long url is to be split
     */
    public static void setListOfModules(Timetable timetable) {
        HashMap<String, TimetableModule> listOfModules = splitExpandedUrl(timetable);
        timetable.setModuleCodeToTimetableModule(listOfModules);
    }

    /**
     * Splits expanded NUSMods timetable URL into the different {@code TimetableModule}s.
     * Expanded timetable URL is in the format ...?[MODULE_CODE]=[LESSON_TYPE]:[CLASS_NUM]&...
     * @param timetable whose long url is to be split
     * @return HashMap containing list of modules
     */
    private static HashMap<String, TimetableModule> splitExpandedUrl(Timetable timetable) {
        String expandedUrl = timetable.getExpandedUrl();
        requireNonNull(expandedUrl);
        String[] moduleInformation = expandedUrl.split(SPLIT_QUESTION_MARK);
        String[] modules = moduleInformation[MODULE_INFORMATION_INDEX].split(SPLIT_AMPERSAND);

        HashMap<String, TimetableModule> listOfModules = new  HashMap<String, TimetableModule>();
        HashMap<String, String> listOfLessons;
        String moduleCode;
        String lessonType;
        String classType;
        String[] lessons;

        for (String currentModule : modules) {
            listOfLessons = new HashMap<String, String>();

            moduleCode = currentModule.split(SPLIT_EQUALS)[MODULE_CODE_INDEX];
            lessons = currentModule.split(SPLIT_EQUALS)[MODULE_CODE_REMAINING_INDEX].split(SPLIT_COMMA);
            for (String currLesson : lessons) {
                lessonType = currLesson.split(SPLIT_COLON)[LESSON_TYPE_INDEX];
                classType = currLesson.split(SPLIT_COLON)[CLASS_TYPE_INDEX];

                try {
                    listOfLessons.put(convertLessonType(lessonType), classType);
                } catch (IllegalValueException e) {
                    logger.warning("Unable to convert lesson type");
                }
            }
            listOfModules.put(moduleCode, new TimetableModule(moduleCode, listOfLessons));
        }
        return listOfModules;
    }

    /**
     * Sets {@code listOfDays} in {@code timetable} given using the NUSMods short url
     * @param timetable timetable to set List of days
     */
    public static void setListOfDays(Timetable timetable) {
        requireNonNull(timetable.getModuleCodeToTimetableModule());
        ArrayList<TimetableModuleSlot> allTimetableModuleSlots = retrieveModuleSlotsFromApi(timetable);
        timetable.setAllModulesSlots(allTimetableModuleSlots);
        HashMap<String, ArrayList<TimetableModuleSlot>> sortedTimetableModuleSlots =
                sortModuleSlotsByDay(allTimetableModuleSlots);
        timetable.setDaysToTimetableModuleSlots(sortedTimetableModuleSlots);
    }

    /**
     * Sets {@code timetableDisplayInfo} in {@code timetable} given using the {@code listOfDays}
     * @param timetable timetable to set timetableDisplayInfo
     */
    public static void setTimetableDisplayInfo(Timetable timetable) {
        timetable.setTimetableDisplayInfo(formatTimetableDisplayInfo(
                convertListOfDaysToString(timetable.getDaysToTimetableModuleSlots())));
    }

    /**
     * Formats timetable display info string for use in {@code TimetablePage.html}.
     * @param timetableDisplayInfo the String to format
     * @return the formatted String
     */
    private static String formatTimetableDisplayInfo(String timetableDisplayInfo) {
        return "timetable = [" + timetableDisplayInfo + "];\n";
    }

    /**
     * Converts the {@code listOfDays} into a String object for parsing.
     */
    private static String convertListOfDaysToString(HashMap<String, ArrayList<TimetableModuleSlot>> listOfDays) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < TIMES.length; i++) {
            if (i < TIMES.length - 1) {
                sb.append(listOfDays.get(DAYS[MONDAY_INDEX]).get(i).toString());
                sb.append(", ");
                sb.append(listOfDays.get(DAYS[TUESDAY_INDEX]).get(i).toString());
                sb.append(", ");
                sb.append(listOfDays.get(DAYS[WEDNESDAY_INDEX]).get(i).toString());
                sb.append(", ");
                sb.append(listOfDays.get(DAYS[THURSDAY_INDEX]).get(i).toString());
                sb.append(", ");
                sb.append(listOfDays.get(DAYS[FRIDAY_INDEX]).get(i).toString());
                sb.append(", ");
            } else {
                sb.append(listOfDays.get(DAYS[MONDAY_INDEX]).get(i).toString());
                sb.append(", ");
                sb.append(listOfDays.get(DAYS[TUESDAY_INDEX]).get(i).toString());
                sb.append(", ");
                sb.append(listOfDays.get(DAYS[WEDNESDAY_INDEX]).get(i).toString());
                sb.append(", ");
                sb.append(listOfDays.get(DAYS[THURSDAY_INDEX]).get(i).toString());
                sb.append(", ");
                sb.append(listOfDays.get(DAYS[FRIDAY_INDEX]).get(i).toString());
            }
        }
        return sb.toString();
    }

    /**
     * Gets module information from nusmods api for the all modules in listOfModules in {@code timetable}.
     * @param timetable containing list of all modules
     */
    private static ArrayList<TimetableModuleSlot> retrieveModuleSlotsFromApi(Timetable timetable) {
        String currentModuleInfo;
        ArrayList<TimetableModuleSlot> allTimetableModuleSlots = new ArrayList<TimetableModuleSlot>();
        Set<Map.Entry<String, TimetableModule>> entrySet = timetable.getModuleCodeToTimetableModule().entrySet();
        String acadYear = getAcadYear();

        for (Map.Entry<String, TimetableModule> currentModule : entrySet) {
            currentModuleInfo = getJsonContentsFromNusModsApiForModule(acadYear,
                    Integer.toString(timetable.getCurrentSemester()), currentModule.getKey());
            allTimetableModuleSlots.addAll(getAllTimetableModuleSlots(currentModuleInfo, timetable,
                    currentModule.getKey()));
        }
        return allTimetableModuleSlots;
    }

    /**
     * Calculates the current Academic Year for NUS students.
     * @return a String containing the current academic year.
     */
    private static String getAcadYear() {
        LocalDate currentDate = LocalDate.now();
        String acadYear;

        if (currentDate.getMonthValue() <= 6) {
            acadYear = (currentDate.getYear() - 1) + "-" + (currentDate.getYear());
        } else {
            acadYear = currentDate.getYear() + "-" + (currentDate.getYear() + 1);
        }
        return acadYear;
    }

    /**
     * Retrieves json file from NUSMods api and converts to String
     * @param acadYear String representing academic year
     * @param semNum String representing semester number
     * @param moduleCode String representing module code
     * Format: http://api.nusmods.com/[acadYear]/[semNum]/modules/[moduleCode].json
     * e.g. http://api.nusmods.com/2017-2018/2/modules/CS3241.json
     * @return String containing contents of json file
     */
    private static String getJsonContentsFromNusModsApiForModule(String acadYear, String semNum, String moduleCode) {
        String contents = null;
        String nusmodsApiUrlString = "http://api.nusmods.com/" + acadYear + "/" + semNum + "/modules/" + moduleCode
                + ".json";
        try {
            URL nusmodsApiUrl = new URL(nusmodsApiUrlString);
            HttpURLConnection urlConnection = (HttpURLConnection) nusmodsApiUrl.openConnection();
            urlConnection.setRequestMethod(HTTP_METHOD_GET);
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HTTP_METHOD_RESPONSE_OK) {
                contents = readStream(urlConnection.getInputStream());
            } else {
                contents = "Error in accessing API - " + readStream(urlConnection.getErrorStream());
            }
        } catch (MalformedURLException e) {
            logger.warning("URL provided is malformed");
        } catch (ProtocolException e) {
            logger.warning("Protocol exception");
        } catch (IOException e) {
            logger.warning("Failed to open connection");
        }
        return contents;
    }

    /**
     * Read the responded result and stores in a String
     * @param inputStream from nusmods api
     * @return String containing contents of nusmods api
     * @throws IOException from readLine()
     */
    private static String readStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;

        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        reader.close();
        return stringBuilder.toString();
    }

    /**
     * Parses contents of json file contents result from {@code readStream()}
     * @param contents contents of json file in String
     * @param timetable timetable to set list of modules slots
     * @param moduleCode current module
     * @return all TimetableModuleSlots for the timetable
     */
    public static ArrayList<TimetableModuleSlot> getAllTimetableModuleSlots(String contents, Timetable timetable,
                                                               String moduleCode) {
        requireNonNull(timetable.getModuleCodeToTimetableModule());

        JSONObject jsonObject = null;
        JSONParser parser = new JSONParser();
        ArrayList<TimetableModuleSlot> listOfModuleSlots = new ArrayList<TimetableModuleSlot>();

        try {
            Object obj = parser.parse(contents);
            jsonObject = (JSONObject) obj;
            JSONArray arrOfClassInformation = null;
            Object object = jsonObject.get("Timetable");
            arrOfClassInformation = (JSONArray) object;

            String tempLessonType;
            String tempClassType;
            String tempWeekFreq;
            String tempDay;
            String tempStartTime;
            String tempEndTime;
            String tempVenue;

            HashMap<String, TimetableModule> listOfModules = timetable.getModuleCodeToTimetableModule();
            TimetableModule timetableModule = listOfModules.get(moduleCode);
            HashMap<String, String> listOfLessons = timetableModule.getListOfLessons();

            for (Object t : arrOfClassInformation) {
                tempLessonType = ((JSONObject) t).get("LessonType").toString();
                tempClassType = ((JSONObject) t).get("ClassNo").toString();

                if (listOfLessons.get(tempLessonType).equals(tempClassType)) {
                    tempWeekFreq = ((JSONObject) t).get("WeekText").toString();
                    tempDay = ((JSONObject) t).get("DayText").toString();
                    tempStartTime = ((JSONObject) t).get("StartTime").toString();
                    tempEndTime = ((JSONObject) t).get("EndTime").toString();
                    tempVenue = ((JSONObject) t).get("Venue").toString();
                    listOfModuleSlots.add(new TimetableModuleSlot(moduleCode, tempLessonType, tempClassType,
                            tempWeekFreq, tempDay, tempVenue, tempStartTime, tempEndTime));
                }
            }
        } catch (Exception e) {
            logger.warning("Exception caught in parsing JSONObject");
        }
        return listOfModuleSlots;
    }

    /**
     * Processes the given timetable for viewing.
     *
     * @param timetable to view
     * @return an ArrayList containing the {@code TimetableModuleSlots} from the Timetable.
     */
    public static ArrayList<TimetableModuleSlot> setUpUnsortedModuleSlotsForViewing(Timetable timetable) {
        ArrayList<TimetableModuleSlot> allUnsortedModulesSlots = timetable.getAllModulesSlots();

        for (TimetableModuleSlot t : allUnsortedModulesSlots) {
            t.setComparing(false);
        }
        return allUnsortedModulesSlots;
    }

    /**
     * Combines the two lists of {@code TimetableModuleSlots} from each timetable and process them for comparing.
     *
     * @param first  Timetable to compare
     * @param second Timetable to compare
     * @return an ArrayList containing the combined {@code TimetableModuleSlots} from both Timetables.
     */
    public static ArrayList<TimetableModuleSlot> setUpUnsortedModuleSlotsForComparing(Timetable first,
                                                                                      Timetable second) {
        ArrayList<TimetableModuleSlot> allUnsortedModulesSlots = new ArrayList<TimetableModuleSlot>();
        allUnsortedModulesSlots.addAll(first.getAllModulesSlots());
        allUnsortedModulesSlots.addAll(second.getAllModulesSlots());

        for (TimetableModuleSlot t : allUnsortedModulesSlots) {
            t.setComparing(true);
        }
        return allUnsortedModulesSlots;
    }

    /**
     * Sorts TimetableModuleSlots
     *
     * @return HashMap of {@code Day}, {@code list of TimetableModuleSlots sorted by time}
     */
    public static HashMap<String, ArrayList<TimetableModuleSlot>> sortModuleSlotsByDay(
            ArrayList<TimetableModuleSlot> unsortedTimetableModuleSlots) {
        ArrayList<ArrayList<TimetableModuleSlot>> listOfDays = new ArrayList<ArrayList<TimetableModuleSlot>>();

        // add ArrayList for Monday to Friday
        listOfDays.add(new ArrayList<TimetableModuleSlot>());
        listOfDays.add(new ArrayList<TimetableModuleSlot>());
        listOfDays.add(new ArrayList<TimetableModuleSlot>());
        listOfDays.add(new ArrayList<TimetableModuleSlot>());
        listOfDays.add(new ArrayList<TimetableModuleSlot>());

        for (TimetableModuleSlot t : unsortedTimetableModuleSlots) {
            try {
                listOfDays.get(convertDayToInteger(t.getDay())).add(t);
            } catch (IllegalValueException e) {
                logger.warning("Invalid day entered");
            }
        }

        for (ArrayList<TimetableModuleSlot> t : listOfDays) {
            ArrayList<TimetableModuleSlot> temp = sortModuleSlotsByTime(t);
            t.clear();
            t.addAll(temp);
        }

        HashMap<String, ArrayList<TimetableModuleSlot>> sortedTimetableModuleSlots =
                new HashMap<String, ArrayList<TimetableModuleSlot>>();
        sortedTimetableModuleSlots.put(DAYS[MONDAY_INDEX], listOfDays.get(MONDAY_INDEX));
        sortedTimetableModuleSlots.put(DAYS[TUESDAY_INDEX], listOfDays.get(TUESDAY_INDEX));
        sortedTimetableModuleSlots.put(DAYS[WEDNESDAY_INDEX], listOfDays.get(WEDNESDAY_INDEX));
        sortedTimetableModuleSlots.put(DAYS[THURSDAY_INDEX], listOfDays.get(THURSDAY_INDEX));
        sortedTimetableModuleSlots.put(DAYS[FRIDAY_INDEX], listOfDays.get(FRIDAY_INDEX));
        return sortedTimetableModuleSlots;
    }

    /**
     * Sorts Module Slots by time
     * @param timetableModuleSlots
     * @return a list of sorted TimetableModuleSlots
     */
    private static ArrayList<TimetableModuleSlot> sortModuleSlotsByTime(
            ArrayList<TimetableModuleSlot> timetableModuleSlots) {
        Collections.sort(timetableModuleSlots);
        return splitIntoHalfHourSlots(timetableModuleSlots);
    }

    /**
     * Splits each TimetableModuleSlots into half hour slots and adds empty slots to represent breaks
     *
     * @param timetableModuleSlots the ArrayList to split into half hour slots
     * @return an ArrayList of TimetableModuleSlot with each slot representing one half-hour slot in the timetable
     */
    private static ArrayList<TimetableModuleSlot> splitIntoHalfHourSlots(
            ArrayList<TimetableModuleSlot> timetableModuleSlots) {
        ArrayList<TimetableModuleSlot> filled = new ArrayList<TimetableModuleSlot>();

        int j = 0;
        for (int i = 0; i < TIMES.length; i++) {
            if (j < timetableModuleSlots.size() && timetableModuleSlots.get(j).getStartTime().equals(TIMES[i])) {
                while (!timetableModuleSlots.get(j).getEndTime().equals(TIMES[i])) {
                    filled.add(timetableModuleSlots.get(j));
                    i++;
                }
                j++;
                i--;
            } else if (j < timetableModuleSlots.size()
                    && Integer.valueOf(timetableModuleSlots.get(j).getStartTime()) < Integer.valueOf(TIMES[i])
                    && Integer.valueOf(timetableModuleSlots.get(j).getEndTime()) > Integer.valueOf(TIMES[i])) {
                while (!timetableModuleSlots.get(j).getEndTime().equals(TIMES[i])) {
                    filled.add(timetableModuleSlots.get(j));
                    i++;
                }
                j++;
                i--;
            } else {
                filled.add(new TimetableModuleSlot());
            }
        }
        return filled;
    }

    // ================ Helper methods ==============================

    /**
     * Converts shortened lesson type from URL to long lesson type in API
     * @param lessonType shortened lesson type from URL
     * @return long lesson type in API
     */
    private static String convertLessonType(String lessonType) throws IllegalValueException {
        switch (lessonType) {
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
            throw new IllegalValueException(MESSAGE_INVALID_CLASS_TYPE);
        }
    }

    /**
     * Converts {@code Day} to Integer for comparative purposes
     * @param day in String
     * @return int representing day
     */
    public static int convertDayToInteger(String day) throws IllegalValueException {
        switch (day.toUpperCase()) {
        case "MONDAY":
            return MONDAY_INDEX;

        case "TUESDAY":
            return TUESDAY_INDEX;

        case "WEDNESDAY":
            return WEDNESDAY_INDEX;

        case "THURSDAY":
            return THURSDAY_INDEX;

        case "FRIDAY":
            return FRIDAY_INDEX;

        default:
            throw new IllegalValueException(MESSAGE_INVALID_DAY_TYPE);
        }
    }
}
```
###### /java/seedu/address/model/person/timetable/Timetable.java
``` java
/**
 * Represents the NUSMODS timetable of the partner
 */
public class Timetable {
    public static final String MESSAGE_TIMETABLE_CONSTRAINTS = "Short NUSMods timetable URL should be of the format "
            + "http://modsn.us/code-part "
            + "and adhere to the following constraints:\n"
            + "1. The URL should start with http://modsn.us/\n"
            + "2. The code-part should not be empty and should only contain alphanumeric characters.";
    private static final String SHORT_NUSMODS_URL_REGEX = "http://modsn.us/";
    private static final String CODE_PART_REGEX = "[\\w]+";
    private static final String TIMETABLE_VALIDATION_REGEX = SHORT_NUSMODS_URL_REGEX + CODE_PART_REGEX;

    public final String value;
    private int currentSemester;
    private HashMap<String, ArrayList<TimetableModuleSlot>>
            daysToTimetableModuleSlots; // HashMap of <Day, Sorted list of TimetableModuleSlots>
    private HashMap<String, TimetableModule> moduleCodeToTimetableModule; // contains all TimetableModules
    private ArrayList<TimetableModuleSlot> allModulesSlots; //ArrayList containing all TimetableModuleSlots
    private String expandedUrl;
    private String timetableDisplayInfo;


    public Timetable(String timetableUrl) {
        requireNonNull(timetableUrl);
        checkArgument(isValidTimetable(timetableUrl), MESSAGE_TIMETABLE_CONSTRAINTS);
        this.value = timetableUrl;
        setUpTimetable();
    }

    private void setUpTimetable() {
        TimetableUtil.setUpTimetableInfo(this);
    }

    // ================ Getter and Setter methods ==============================

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public void setModuleCodeToTimetableModule(HashMap<String, TimetableModule> moduleCodeToTimetableModule) {
        this.moduleCodeToTimetableModule = moduleCodeToTimetableModule;
    }

    public HashMap<String, TimetableModule> getModuleCodeToTimetableModule() {
        return moduleCodeToTimetableModule;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public void setDaysToTimetableModuleSlots(
            HashMap<String, ArrayList<TimetableModuleSlot>> daysToTimetableModuleSlots) {
        this.daysToTimetableModuleSlots = daysToTimetableModuleSlots;
    }

    public HashMap<String, ArrayList<TimetableModuleSlot>> getDaysToTimetableModuleSlots() {
        return daysToTimetableModuleSlots;
    }

    public ArrayList<TimetableModuleSlot> getAllModulesSlots() {
        return allModulesSlots;
    }

    public void setAllModulesSlots(ArrayList<TimetableModuleSlot> allModulesSlots) {
        this.allModulesSlots = allModulesSlots;
    }

    public void setTimetableDisplayInfo(String timetableDisplayInfo) {
        this.timetableDisplayInfo = timetableDisplayInfo;
    }

    public String getTimetableDisplayInfo() {
        return timetableDisplayInfo;
    }

    /**
     * Returns if a given string is a valid short NUSMods timetable URL.
     */
    public static boolean isValidTimetable(String test) {
        return test.matches(TIMETABLE_VALIDATION_REGEX);
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
}
```
###### /java/seedu/address/model/person/timetable/TimetableModule.java
``` java
/**
 * Represents a module from NUSMods timetable.
 */
public class TimetableModule {
    private final String moduleCode;
    private HashMap<String, String> listOfLessons; // Key is lesson type, Value is class type

    public TimetableModule(String moduleCode, HashMap<String, String> listOfLessons) {
        this.moduleCode = moduleCode;
        this.listOfLessons = listOfLessons;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public HashMap<String, String> getListOfLessons() {
        return listOfLessons;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this // short circuit if same object
                || (other instanceof TimetableModule // instanceof handles nulls
                && this.moduleCode.equals(((TimetableModule) other).moduleCode)
                && this.listOfLessons.equals(((TimetableModule) other).listOfLessons))); // state check
    }
}
```
###### /java/seedu/address/model/person/timetable/TimetableModuleSlot.java
``` java
/**
 * Represents the module information of one module slot in one day
 */
public class TimetableModuleSlot implements Comparable<TimetableModuleSlot> {
    private String moduleCode;
    private String lessonType;
    private String classType;
    private String weekFreq;
    private String day;
    private String venue;
    private String startTime;
    private String endTime;
    private boolean isComparing; // for comparing timetables
    private final boolean isEmpty; // for displaying normal timetable

    public TimetableModuleSlot() {
        this.moduleCode = null;
        this.lessonType = null;
        this.classType = null;
        this.weekFreq = null;
        this.day = null;
        this.venue = null;
        this.startTime = null;
        this.endTime = null;
        isEmpty = true;
        isComparing = false;
    }

    public TimetableModuleSlot(String moduleCode, String lessonType, String classType, String weekFreq, String day,
                               String venue, String startTime, String endTime) {
        this.moduleCode = moduleCode;
        this.lessonType = lessonType;
        this.classType = classType;
        this.weekFreq = weekFreq;
        this.day = day;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        isEmpty = false;
        isComparing = false;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getClassType() {
        return classType;
    }

    public String getWeekFreq() {
        return weekFreq;
    }

    public String getDay() {
        return day;
    }

    public String getVenue() {
        return venue;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setComparing(boolean comparing) {
        isComparing = comparing;
    }

    @Override
    public int compareTo(TimetableModuleSlot other) {
        return this.startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        if (isComparing) {
            return isEmpty
                    ? "\"\"" : "\"X\"";
        } else {
            return isEmpty
                    ? "\"\"" : "\"" + moduleCode + "\"";
        }
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void indicateTimetableChanged(Timetable timetable) {
        raise(new TimetableChangedEvent(timetable));
    }

    @Override
    public void requestHideTimetable() {
        raise(new HideTimetableRequestEvent());
    }

    @Override
    public void requestShowTimetable() {
        raise(new ShowTimetableRequestEvent());
    }

```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        TimetableUtil.setUpTimetableInfoView(getPartner().getTimetable());
        indicateTimetableChanged(getPartner().getTimetable());
        raise(new ShowTimetableRequestEvent());
    }

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Raises an event to indicate the timetable storage has changed.
     */
    void indicateTimetableChanged(Timetable timetable);
    /**
     * Raises an event to hide timetable.
     */
    void requestHideTimetable();

    /**
     * Raises an event to show timetable.
     */
    void requestShowTimetable();

```
