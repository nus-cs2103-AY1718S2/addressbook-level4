package seedu.address.external;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.util.PreconditionFailedException;
import com.google.gdata.util.ServiceException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Student;

//@@author demitycho
/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 * The upload process is still synchronous, the UI will freeze as data is being uploaded
 * There will be logging here to show state updates as it is difficult to show UI changes.
 */
public class GContactsService {
    public static final String STRING_STUDENT_GROUP_NAME = "Students";
    public static final String STRING_BASE_GROUP_ATOM_ID =
            "http://www.google.com/m8/feeds/groups/codeducatorapp%40gmail.com/base/6";

    private static final Logger logger = LogsCenter.getLogger(GContactsService.class);

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

        contact.addGroupMembershipInfo(
                new GroupMembershipInfo(false, STRING_BASE_GROUP_ATOM_ID));
        contact.setContent(new PlainTextConstruct(student.getUniqueKey().toString()));

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

        clearAllOldContacts(myService);

        ContactGroupEntry studentGroupEntry = createStudentGroupEntry(myService);

        for (Student student : addressBook.getStudentList()) {
            ContactEntry toBeInserted = studentToContactEntry(student);

            toBeInserted.addGroupMembershipInfo(
                    new GroupMembershipInfo(false, studentGroupEntry.getId()));

            logger.info(String.format("Contact created for: %s", student.getName()));
        }
        logger.info("Successfully updated Google Contacts");

    }

    /**
     * Gets the Atomic Id {@code ContactGroupEntry.getId()} of the "Students" group
     * that the contacts will be uploaded to
     * @param myService
     * @return
     * @throws ServiceException
     * @throws IOException
     */
    public static ContactGroupEntry getStudentGroupEntry(ContactsService myService)
            throws ServiceException, IOException, NullPointerException {
        // Request the feed
        URL feedUrl = new URL("https://www.google.com/m8/feeds/groups/default/full");
        ContactGroupFeed resultFeed = myService.getFeed(feedUrl, ContactGroupFeed.class);

        for (ContactGroupEntry groupEntry : resultFeed.getEntries()) {
            if (groupEntry.getTitle().getPlainText().equals(STRING_STUDENT_GROUP_NAME)) {
                return groupEntry;
            }
        }

        return null;
    }

    /**
     * Creates a new ContactGroupEntry called "Students"
     * Gets the Atomic Id {@code ContactGroupEntry.getId()} that the contacts will be uploaded to
     * @param myService
     * @return
     * @throws ServiceException
     * @throws IOException
     */
    public static ContactGroupEntry createStudentGroupEntry(ContactsService myService)
            throws ServiceException, IOException {
        // Create the entry to insert
        ContactGroupEntry newGroup = new ContactGroupEntry();
        newGroup.setTitle(new PlainTextConstruct(STRING_STUDENT_GROUP_NAME));

        // Ask the service to insert the new entry
        URL postUrl = new URL("https://www.google.com/m8/feeds/groups/default/full");
        ContactGroupEntry createdGroup = myService.insert(postUrl, newGroup);

        logger.info("Successfully created Google Contact group: Student");
        return createdGroup;
    }

    /**
     * Clears all old contacts that were uploaded, then overwrites them with current data.
     * @param myService
     * @throws ServiceException
     * @throws IOException
     */
    public static void clearAllOldContacts(ContactsService myService)
            throws ServiceException, IOException {

        ContactGroupEntry studentGroupEntry = getStudentGroupEntry(myService);
        URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);

        if (studentGroupEntry != null) {
            deleteAllStudentContactsWithStudentGroup(
                    studentGroupEntry.getSelfLink().getHref(), resultFeed);
            try {
                studentGroupEntry.delete();
            } catch (PreconditionFailedException e) {
                System.out.println("Student Group does not Exists!");
                e.printStackTrace();
            }
        }
        logger.info("Successfully deleted old contacts");
    }

    /**
     * Returns true if the list of Groups of a Student in GroupMemberShipInfo has the "Student" Group
     * @param link
     * @param groupMembershipInfo
     * @return
     */
    public static boolean isStudentGroup(String link, List<GroupMembershipInfo> groupMembershipInfo) {
        String[] linkParts = link.split("/");

        for (GroupMembershipInfo group : groupMembershipInfo) {
            String[] hrefParts = group.getHref().split("/");

            if (hrefParts[hrefParts.length - 1].equals(linkParts[linkParts.length - 1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all the Student contacts under Contact Group "Students"
     * @param studentGroupHref
     * @param resultFeed
     * @throws ServiceException
     * @throws IOException
     */
    public static void deleteAllStudentContactsWithStudentGroup(
            String studentGroupHref, ContactFeed resultFeed) throws ServiceException, IOException {
        for (ContactEntry entry : resultFeed.getEntries()) {
            if (isStudentGroup(studentGroupHref, entry.getGroupMembershipInfos())) {
                entry.delete();
            }
        }
    }
}

