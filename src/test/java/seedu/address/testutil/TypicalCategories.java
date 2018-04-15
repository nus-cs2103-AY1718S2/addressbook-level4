package seedu.address.testutil;

import seedu.address.model.person.Category;

//@@author Zhu-Jiahui
/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalCategories {
    public static final Category CATEGORY_FIRST_PERSON = TypicalClients.getTypicalStudents().get(0).getCategory();
    public static final Category CATEGORY_SECOND_PERSON = TypicalClients.getTypicalStudents().get(1).getCategory();
    public static final Category CATEGORY_THIRD_PERSON = TypicalClients.getTypicalStudents().get(2).getCategory();
}
