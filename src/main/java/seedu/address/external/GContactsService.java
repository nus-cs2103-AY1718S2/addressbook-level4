package seedu.address.external;

import java.io.IOException;
import java.net.URL;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
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
        String groupAtomId = "http://www.google.com/m8/feeds/groups/codeducatorapp%40gmail.com/base/6";
        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        printAllContacts(myService);
//        for (Student student : addressBook.getStudentList()) {
//            ContactEntry toBeInserted = studentToContactEntry(student);
//            toBeInserted.addGroupMembershipInfo(new GroupMembershipInfo(false, groupAtomId));
//
//            myService.insert(postUrl, toBeInserted);
//        }
    }

    public static void printAllContacts(ContactsService myService)
            throws ServiceException, IOException {
        // Request the feed
        URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
        System.out.println(resultFeed.getTitle().getPlainText());
        for (ContactEntry entry : resultFeed.getEntries()) {
            if (entry.hasName()) {
                Name name = entry.getName();
                if (name.hasFullName()) {
                    String fullNameToDisplay = name.getFullName().getValue();
                    if (name.getFullName().hasYomi()) {
                        fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
                    }
                    System.out.println("\t\t" + fullNameToDisplay);
                } else {
                    System.out.println("\t\t (no full name found)");
                }
                if (name.hasNamePrefix()) {
                    System.out.println("\t\t" + name.getNamePrefix().getValue());
                } else {
                    System.out.println("\t\t (no name prefix found)");
                }
                if (name.hasGivenName()) {
                    String givenNameToDisplay = name.getGivenName().getValue();
                    if (name.getGivenName().hasYomi()) {
                        givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
                    }
                    System.out.println("\t\t" + givenNameToDisplay);
                } else {
                    System.out.println("\t\t (no given name found)");
                }
                if (name.hasAdditionalName()) {
                    String additionalNameToDisplay = name.getAdditionalName().getValue();
                    if (name.getAdditionalName().hasYomi()) {
                        additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
                    }
                    System.out.println("\t\t" + additionalNameToDisplay);
                } else {
                    System.out.println("\t\t (no additional name found)");
                }
                if (name.hasFamilyName()) {
                    String familyNameToDisplay = name.getFamilyName().getValue();
                    if (name.getFamilyName().hasYomi()) {
                        familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
                    }
                    System.out.println("\t\t" + familyNameToDisplay);
                } else {
                    System.out.println("\t\t (no family name found)");
                }
                if (name.hasNameSuffix()) {
                    System.out.println("\t\t" + name.getNameSuffix().getValue());
                } else {
                    System.out.println("\t\t (no name suffix found)");
                }
            } else {
                System.out.println("\t (no name found)");
            }
            System.out.println("Email addresses:");
            for (Email email : entry.getEmailAddresses()) {
                System.out.print(" " + email.getAddress());
                if (email.getRel() != null) {
                    System.out.print(" rel:" + email.getRel());
                }
                if (email.getLabel() != null) {
                    System.out.print(" label:" + email.getLabel());
                }
                if (email.getPrimary()) {
                    System.out.print(" (primary) ");
                }
                System.out.print("\n");
            }
            System.out.println("Groups:");
            for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
                String groupHref = group.getHref();
                System.out.println("  Id: " + groupHref);
            }
            System.out.println("Contact's ETag: " + entry.getEtag());
        }
    }
}

