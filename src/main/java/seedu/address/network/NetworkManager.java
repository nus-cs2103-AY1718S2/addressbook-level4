package seedu.address.network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.module.Module;
import seedu.address.model.person.TimeTableLink;

/**
 * The main NetworkManager of the app.
 */
public class NetworkManager {

    /**
     * Connects to the timeTableLink given and returns a list of modules
     * @param timeTableLink a TimeTableLink representing an URL to a NUSmods schedule
     */
    public String getQuery(TimeTableLink timeTableLink) {
        try {
            URL shortUrl = new URL(timeTableLink.toString());
            URLConnection connection = shortUrl.openConnection();
            URL longUrl = new URL(connection.getHeaderField("Location"));
            return longUrl.getQuery();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     *
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

}
