package seedu.progresschecker.commons.util;

import java.io.IOException;
import java.util.Optional;

import seedu.progresschecker.commons.core.Config;
import seedu.progresschecker.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    public static void saveConfig(Config config, String configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
