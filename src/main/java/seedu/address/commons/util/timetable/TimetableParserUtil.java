package seedu.address.commons.util.timetable;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.timetable.Timetable.MESSAGE_INVALID_URL;
import static seedu.address.model.person.timetable.Timetable.MESSAGE_URL_CONSTRAINTS;
import static seedu.address.model.person.timetable.Timetable.isValidUrl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.exceptions.NoInternetConnectionException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.timetable.TimetableData;

//@@author AzuraAiR
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
    public static final int SEMESTER_NUMBER_URL_INDEX = 4;
    public static final int SEMESTER_NUMBER_INTEGER_INDEX = 1;
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
     * @throws ParseException if the parsing fails and returns the default NUSMods URL
     * @throws NoInternetConnectionException when the app timeouts in obtaining the long url
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

        // Separate semester number normally found at the fifth part
        String semNum = urlParts[SEMESTER_NUMBER_URL_INDEX];
        // Separate "share" out of last part of url
        String[] toParse = urlParts[LAST_STRING_URL_INDEX].split(URL_QUESTION_MARK_REGEX);
        // Separate the modules in last part of url
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
        // Separate the module code
        String[] moduleInfo = module.split(URL_EQUALS_SIGN_REGEX);
        // Separate the number from SEM-NUM
        String[] semNumToParse = semNum.split(URL_MINUS_SIGN_REGEX);
        int semNumInt = Integer.parseInt(semNumToParse[SEMESTER_NUMBER_INTEGER_INDEX]);
        String moduleCode = moduleInfo[MODULE_CODE_INDEX];
        //Separate the individual lessons
        String[] lessonsTaken = moduleInfo[LESSON_STRING_INDEX].split(URL_COMMA_SIGN_REGEX);

        lessonListFromApi = obtainModuleInfoFromApi(moduleCode, semNumInt);

        // Main loop to add lessons to list
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
