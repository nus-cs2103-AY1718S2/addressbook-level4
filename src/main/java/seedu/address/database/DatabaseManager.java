package seedu.address.database;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.module.Module;
import seedu.address.model.module.Schedule;
import seedu.address.model.person.TimeTableLink;

/**
 * The main DatabaseManager of the app.
 */
public class DatabaseManager {

    private static final String DEFAULT_JSON_DATABASE_FILEPATH = "modules.json";
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
        moduleDatabase = parseDatabase(DEFAULT_JSON_DATABASE_FILEPATH);
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
        return  eventList;
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
