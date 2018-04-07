package seedu.address.external;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Student;

/**
 * Constructs a new GContactsService object to communicate with Google's APIs
 */
public class GContactsService {
    public static final String STRING_STUDENT_GROUP_NAME = "Students";
    public static final String STRING_BASE_GROUP_ATOM_ID =
            "http://www.google.com/m8/feeds/groups/codeducatorapp%40gmail.com/base/6";

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

    private Credential credential;

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
        System.out.println("here1");
        clearAllOldContacts(myService);
        System.out.println("here2");
        ContactGroupEntry studentGroupEntry = createStudentGroupEntry(myService);
        System.out.println("here3");
        for (Student student : addressBook.getStudentList()) {
            ContactEntry toBeInserted = studentToContactEntry(student);

            toBeInserted.addGroupMembershipInfo(
                    new GroupMembershipInfo(false, studentGroupEntry.getId()));

            myService.insert(postUrl, toBeInserted);
        }
        System.out.println("here4");
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
            throws ServiceException, IOException {
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

        return createdGroup;
    }

    public static void clearAllOldContacts(ContactsService myService)
            throws ServiceException, IOException {
        //ContactGroupEntry group = myService.getEntry(contactGroupURL, ContactGroupEntry.class);
        ContactGroupEntry studentGroupEntry = getStudentGroupEntry(myService);
        URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);

        if (studentGroupEntry != null) {
            for (ContactEntry entry : resultFeed.getEntries()) {
                System.out.println("Entry: " + entry.getName().getFullName().getValue());
//                for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
//                    System.out.println("Print: " + " -- " + group.getHref());
//                }
                if (isStudentGroup(studentGroupEntry.getSelfLink().getHref(),
                        entry.getGroupMembershipInfos())) {
                    try {
                        entry.delete();
                    } catch (PreconditionFailedException e) {
                        // Etags mismatch: handle the exception.
                    }
                }
            }
            try {
                // Print the results
                studentGroupEntry.delete();
                //studentGroupEntry.delete();

            } catch (PreconditionFailedException e) {
                // Etags mismatch: handle the exception.
                System.out.println("Lel");
            }
        }
    }

    public static boolean isStudentGroup(String link, List<GroupMembershipInfo> groupMembershipInfo) {
        String[] linkParts = link.split("/");

        for (GroupMembershipInfo group : groupMembershipInfo) {
            System.out.println("CB: " + link + " -- " + group.getHref());
            String[] hrefParts = group.getHref().split("/");

            if(hrefParts[hrefParts.length -1].equals(linkParts[linkParts.length -1])) {
                return true;
            }
        }
        return false;
    }
}

