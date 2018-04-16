package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

//@@author x3tsunayh

/**
 * Stores addressbook data in a CSV file
 */
public class CsvFileStorage {

    // This string forms the header for the Google CSV format.
    public static final String GOOGLE_CSV_FORMAT = "Name,Given Name,Additional Name,Family Name,Yomi Name,"
            + "Given Name Yomi,Additional Name Yomi,Family Name Yomi,Name Prefix,Name Suffix,Initials,Nickname,"
            + "Short Name,Maiden Name,Birthday,Gender,Location,Billing Information,Directory Server,Mileage,"
            + "Occupation,Hobby,Sensitivity,Priority,Subject,Notes,Group Membership,E-mail 1 - Type,E-mail 1 - Value,"
            + "Phone 1 - Type,Phone 1 - Value,Phone 2 - Type,Phone 2 - Value,Organization 1 - Type,"
            + "Organization 1 - Name,Organization 1 - Yomi Name,Organization 1 - Title,Organization 1 - Department,"
            + "Organization 1 - Symbol,Organization 1 - Location,Organization 1 - Job Description";

    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(ReadOnlyAddressBook addressBook, String filePath) throws IOException {

        PrintWriter pw = new PrintWriter(new File(filePath));
        StringBuilder sb = new StringBuilder();

        sb.append(GOOGLE_CSV_FORMAT);
        sb.append('\n');
        for (Person person : addressBook.getPersonList()) {
            sb.append(person.getName());
            sb.append(",,,,,,,,,,,,,,,,,,,,,,,,,,");
            sb.append("* My Contacts");
            sb.append(",,");
            sb.append(person.getEmail());
            sb.append(",,");
            sb.append(person.getPhone());
            sb.append(",,,,,,,,,,");
            sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
    }

}
