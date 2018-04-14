package seedu.address.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.CalendarUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.person.TimeTableLink;

//@@author Isaaaca

/**
 * The main DatabaseManager of the app.
 */
public class DatabaseManager {
    public static final String INCOMPATIBLE_LINK_MESSAGE = "One or more Timetable link(s) points to a different"
            + " semester as the one in Fastis.";
    private static final String ACAD_YEAR = Integer.toString(CalendarUtil.getCurrAcadYear()) + "-"
            + Integer.toString(CalendarUtil.getCurrAcadYear() + 1);
    private static final String SEMESTER = Integer.toString(CalendarUtil.getCurrentSemester());

    private static final String DEFAULT_JSON_DATABASE_FILEPATH = "sem" + CalendarUtil.getCurrentSemester()
            + "modules.json";
    private static final String DEFAULT_JSON_DATABASE_URL = "https://api.nusmods.com/"
            + ACAD_YEAR + "/" + SEMESTER + "/modules.json";
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
    public static ArrayList<WeeklyEvent> parseEvents(TimeTableLink link) {
        ArrayList<WeeklyEvent> eventList = new ArrayList<>();

        if (!isCurrentSem(link)) {
            EventsCenter.getInstance().post(new NewResultAvailableEvent(INCOMPATIBLE_LINK_MESSAGE, false));
            logger.warning(INCOMPATIBLE_LINK_MESSAGE);
            return eventList;
        }

        String query = getQuery(link);
        StringBuilder result = new StringBuilder();

        StringTokenizer queryTokenizer = new StringTokenizer(query, "&");
        while (queryTokenizer.hasMoreTokens()) {
            StringTokenizer modTokenizer = new StringTokenizer(queryTokenizer.nextToken(), "=");
            Module module = moduleDatabase.get(modTokenizer.nextToken());

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

                    }
                }
            }
        }
        return eventList;
    }

    /**
     * Takes a shortened link from NUSmods and returns its query
     *
     * @param timeTableLink a TimeTableLink representing an URL to a NUSmods schedule
     */
    public static String getQuery(TimeTableLink timeTableLink) {
        try {
            URL longUrl = getLongUrl(new URL(timeTableLink.toString()));
            return longUrl.getQuery();

        } catch (MalformedURLException e) {
            logger.info("NUSmods URL Invalid.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Takes a shortened link from NUSmods and checks if it corresponds to the semester the app is working with
     *
     * @param timeTableLink a TimeTableLink representing an URL to a NUSmods schedule
     */
    private static boolean isCurrentSem(TimeTableLink timeTableLink) {
        try {
            URL longUrl = getLongUrl(new URL(timeTableLink.toString()));
            String currSem = "sem-" + Integer.toString(CalendarUtil.getCurrentSemester());
            return longUrl.getPath().contains(currSem);
        } catch (MalformedURLException e) {
            logger.info("NUSmods URL Invalid.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Takes a shortened URL and returns its longer form
     *
     * @param shortUrl a TimeTableLink representing an URL to a NUSmods schedule
     */
    private static URL getLongUrl(URL shortUrl) throws IOException {
        URLConnection connection = shortUrl.openConnection();
        return new URL(connection.getHeaderField("Location"));
    }


    /**
     * @param filePath
     * @return hashMap of all modules from jsonfile
     */
    private HashMap<String, Module> parseDatabase(String filePath) {
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
