package seedu.address.commons.util;

import java.io.File;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * A class for deleting and managing unwanted storage files
 */
//@@author Alaru
public class DeleteUtil {

    /**
     * Goes through the list of files to be deleted and only deletes those that are not in use
     * @param itemsToDelete List of items (files) to delete
     * @param persons List of Person objects in the addressbook
     */
    public static void clearImageFiles(List<String> itemsToDelete, ObservableList<Person> persons) {
        for (String it : itemsToDelete) {
            boolean notUsed = true;
            for (Person p : persons) {
                if (p.getDisplayPic().toString().equals(it)) {
                    notUsed = false;
                    break;
                }
            }
            if (notUsed) {
                deleteFile(it);
            }
        }
    }

    /**
     * Delete a file at the given filepath.
     * @param filepath where the file is located.
     */
    public static void deleteFile(String filepath) {
        File toDelete = new File(filepath);
        toDelete.delete();
    }
}
