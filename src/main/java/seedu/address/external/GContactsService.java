package seedu.address.external;

import java.io.IOException;
import java.net.URL;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.util.ServiceException;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Student;

/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 */
public class GContactsService {
    private Credential credential;

    public GContactsService() {}

    public GContactsService(Credential credential) {
        this.credential = credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public Credential getCredential() {
        return credential;
    }

    /**
     * Converts a Student object to a ContactEntry for insertion
     * @param student
     * @return
     */
    private static ContactEntry studentToContactEntry(Student student) {
        ContactEntry contact = new ContactEntry();

        Name name = new Name();
        FullName newFullName = new FullName();
        newFullName.setValue(student.getName().toString());
        name.setFullName(newFullName);
        contact.setName(name);

        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber(student.getPhone().toString());
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);

        Email primaryMail = new Email();
        primaryMail.setAddress(student.getEmail().toString());
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);

        return contact;
    }

    /**
     * Syncs the Addressbook with the user's Google account's Google Contacts.
     * @param addressBook
     * @throws ServiceException
     * @throws IOException
     */
    public void synchronize(ReadOnlyAddressBook addressBook)
            throws ServiceException, IOException {

        ContactsService myService = new ContactsService(GServiceManager.APPLICATION_NAME);
        myService.setOAuth2Credentials(credential);
        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        for (Student student : addressBook.getStudentList()) {
            ContactEntry toBeInserted = studentToContactEntry(student);
            myService.insert(postUrl, toBeInserted);
        }
    }
}
