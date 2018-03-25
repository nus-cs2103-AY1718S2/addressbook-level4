package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.login.UserPass;
import seedu.address.login.UserPassStorage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

/**
 * A class to access UserPass stored in the hard disk as a json file
 */
public class JsonUserPassStorage implements UserPassStorage {

    private String filePath;
    private HashMap UserPassHashmap;

    public JsonUserPassStorage(String filePath) {
        this.filePath = filePath;
        try {
            UserPassHashmap = readUserPassMap().orElseThrow(NullPointerException::new);
        } catch (DataConversionException|IOException|NullPointerException e) {
            e.printStackTrace();
        }
        if (UserPassHashmap.isEmpty()) {
            UserPassHashmap = new HashMap<>();
        }
    }

    @Override
    public String getUserPassFilePath() {
        return filePath;
    }

    @Override
    public Optional<HashMap> readUserPassMap() throws DataConversionException, IOException {
        return readUserPassMap(filePath);
    }

    /**
     * Similar to {@link #readUserPassMap()}
     * @param userpassFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<HashMap> readUserPassMap(String userpassFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(userpassFilePath, HashMap.class);
    }

    @Override
    public void saveUserPassMap() throws IOException {
        JsonUtil.saveJsonFile(UserPassHashmap, filePath);
    }

    @Override
    public void put(UserPass userPass) {
        UserPassHashmap.put(userPass.getUsername(), userPass.getPassword());
    }

    @Override
    public boolean containsKey(String username) {
        return UserPassHashmap.containsKey(username);
    }

    @Override
    public String get(String username) {
        return UserPassHashmap.get(username).toString();
    }

}
