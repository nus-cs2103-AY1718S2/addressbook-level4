package seedu.address.commons.util.timetable;

import com.fasterxml.jackson.databind.ObjectMapper;
import seedu.address.commons.exceptions.NoInternetConnectionException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.timetable.TimetableData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.timetable.Timetable.MESSAGE_INVALID_URL;
import static seedu.address.model.person.timetable.Timetable.MESSAGE_URL_CONSTRAINTS;
import static seedu.address.model.person.timetable.Timetable.isValidUrl;

public class TimetableParserUtil {

    public static TimetableData parseUrl(String url) throws ParseException {
        checkArgument(isValidUrl(url), MESSAGE_URL_CONSTRAINTS);

        try {
            String longUrl = parseShortUrl(url);
            return parseLongUrl(longUrl);
        } catch (NoInternetConnectionException ie) {
            return new TimetableData();
        }

    }

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
        } catch (IOException ioe){
            throw new ParseException(MESSAGE_INVALID_URL);
        }
    }

    public static TimetableData parseLongUrl(String url) throws ParseException {

        String[] urlParts = url.split("/");
        ArrayList<Lesson> totalLessonList = new ArrayList<Lesson>();
        ArrayList<Lesson> lessonsToAddFromModule;

        String semNum = urlParts[4];
        String[] toParse = urlParts[5].split("\\?");
        String[] modules = toParse[1].split("&");

        for(String module: modules){
            lessonsToAddFromModule = parseModule(module, semNum);
            for (Lesson lessonToAdd: lessonsToAddFromModule){
                totalLessonList.add(lessonToAdd);
            }
        }
        return new TimetableData(totalLessonList);
    }

    public static ArrayList<Lesson> parseModule(String module, String semNum) throws ParseException {
        ArrayList<Lesson> lessonListFromApi, lessonsTakenList;
        lessonsTakenList = new ArrayList<Lesson>();
        String[] moduleInfo = module.split("=");
        String[] semNumToParse = semNum.split("-");
        int semNumInt = Integer.parseInt(semNumToParse[1]);
        String moduleCode = moduleInfo[0];
        String[] lessonsTaken = moduleInfo[1].split(",");

        lessonListFromApi = obtainModuleInfoFromApi(moduleCode, semNumInt);

        for (String lessonTaken : lessonsTaken) {
            String[] lessonToParse = lessonTaken.split(":");
            String lessonType = convertShortFormToLong(lessonToParse[0]);
            String lessonNum = lessonToParse[1];

            for (Lesson lessonFromApi: lessonListFromApi){
                if (lessonType.equalsIgnoreCase(lessonFromApi.getLessonType())
                        && lessonNum.equalsIgnoreCase(lessonFromApi.getClassNo())) {
                    lessonsTakenList.add(lessonFromApi);
                }
            }
        }

        return lessonsTakenList;
    }

    public static ArrayList<Lesson> obtainModuleInfoFromApi(String moduleCode, int semNum) throws ParseException {
        LocalDate currentDate = LocalDate.now();
        String acadYear;

        // Calculate current academic year
        if (currentDate.getMonthValue() <= 6) {
            acadYear = (currentDate.getYear()-1) + "-" + (currentDate.getYear());
        } else {
            acadYear = currentDate.getYear() + "-" + (currentDate.getYear() + 1);
        }

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

            // Parse info from API and creates an Arraylist of all lessons
            ArrayList<Lesson> lessons = new ArrayList<>();
            for (HashMap<String, String> lesson : lessonInfo) {
                Lesson lessonToAdd = new Lesson(lesson.get("ClassNo"), lesson.get("LessonType"),
                        lesson.get("WeekText"), lesson.get("DayText"),
                        lesson.get("StartTime"), lesson.get("EndTime"));
                lessons.add(lessonToAdd);
            }

            return lessons;
        } catch (IOException exception) {
            throw new ParseException("Cannot retrieve module information");
        }
    }

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
