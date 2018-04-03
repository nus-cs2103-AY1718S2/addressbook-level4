package seedu.address.external;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.Service;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.Version;
import seedu.address.external.exceptions.CredentialsException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class GContactsManager {
    private static final String CLIENT_ID = "126472549776-8cd9bk56sfubm9rkacjivecikppte982.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "nyBpzm1OjnKNZOd0-kT1uo7W";

    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    private Credential credential;

    private static final String APPLICATION_NAME = "codeducator/v1.4";
    String[] scopes = new String[]{
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.google.com/m8/feeds/"};
    /** OAuth 2.0 scopes. */
    private final List<String> SCOPES = Arrays.asList(scopes);
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    public GContactsManager() {

    }

    public void login() throws CredentialsException {
        if (credential != null) {
            throw new CredentialsException("You are already logged in.");
        }
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID,CLIENT_SECRET, SCOPES)
                        .setAccessType("offline")
                        .build();
        try {
            credential = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize("user");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }

    public void logout() throws CredentialsException {
        // Delete credentials from data store directory
        if (credential == null) {
            throw new CredentialsException("You are not logged in");
        }
        credential = null;
    }

    public void printAllContacts()
            throws ServiceException, IOException {
        // Request the feed
        ContactsService myService = new ContactsService(APPLICATION_NAME);
        myService.setOAuth2Credentials(credential);

        System.out.println("FUCKKK" +myService.getServiceVersion());
        URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
        // Print the results
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
            System.out.println("IM addresses:");
            for (Im im : entry.getImAddresses()) {
                System.out.print(" " + im.getAddress());
                if (im.getLabel() != null) {
                    System.out.print(" label:" + im.getLabel());
                }
                if (im.getRel() != null) {
                    System.out.print(" rel:" + im.getRel());
                }
                if (im.getProtocol() != null) {
                    System.out.print(" protocol:" + im.getProtocol());
                }
                if (im.getPrimary()) {
                    System.out.print(" (primary) ");
                }
                System.out.print("\n");
            }
            System.out.println("Groups:");
            for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
                String groupHref = group.getHref();
                System.out.println("  Id: " + groupHref);
            }
            System.out.println("Extended Properties:");
            for (ExtendedProperty property : entry.getExtendedProperties()) {
                if (property.getValue() != null) {
                    System.out.println("  " + property.getName() + "(value) = " +
                            property.getValue());
                } else if (property.getXmlBlob() != null) {
                    System.out.println("  " + property.getName() + "(xmlBlob)= " +
                            property.getXmlBlob().getBlob());
                }
            }
            Link photoLink = entry.getContactPhotoLink();
            String photoLinkHref = photoLink.getHref();
            System.out.println("Photo Link: " + photoLinkHref);
            if (photoLink.getEtag() != null) {
                System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
            }
            System.out.println("Contact's ETag: " + entry.getEtag());
        }
    }
    public ContactEntry createContact()
            throws ServiceException, IOException {
        ContactEntry contact = new ContactEntry();
        // Set the contact's name.
        ContactsService myService = new ContactsService(APPLICATION_NAME);
        myService.setOAuth2Credentials(credential);


        Name name = new Name();
        final String NO_YOMI = null;
        name.setFullName(new FullName("Elizabeth Bennet", NO_YOMI));
        name.setGivenName(new GivenName("Elizabeth", NO_YOMI));
        name.setFamilyName(new FamilyName("Bennet", NO_YOMI));
        contact.setName(name);
        contact.setContent(new PlainTextConstruct("Notes"));
        // Set contact's e-mail addresses.
        Email primaryMail = new Email();
        primaryMail.setAddress("liz@gmail.com");
        primaryMail.setDisplayName("E. Bennet");
        primaryMail.setRel("http://schemas.google.com/g/2005#home");
        primaryMail.setPrimary(true);
        contact.addEmailAddress(primaryMail);
        Email secondaryMail = new Email();
        secondaryMail.setAddress("liz@example.com");
        secondaryMail.setRel("http://schemas.google.com/g/2005#work");
        secondaryMail.setPrimary(false);
        contact.addEmailAddress(secondaryMail);
        // Set contact's phone numbers.
        PhoneNumber primaryPhoneNumber = new PhoneNumber();
        primaryPhoneNumber.setPhoneNumber("(206)555-1212");
        primaryPhoneNumber.setRel("http://schemas.google.com/g/2005#work");
        primaryPhoneNumber.setPrimary(true);
        contact.addPhoneNumber(primaryPhoneNumber);
        PhoneNumber secondaryPhoneNumber = new PhoneNumber();
        secondaryPhoneNumber.setPhoneNumber("(206)555-1213");
        secondaryPhoneNumber.setRel("http://schemas.google.com/g/2005#home");
        contact.addPhoneNumber(secondaryPhoneNumber);
        // Set contact's IM information.
        Im imAddress = new Im();
        imAddress.setAddress("liz@gmail.com");
        imAddress.setRel("http://schemas.google.com/g/2005#home");
        imAddress.setProtocol("http://schemas.google.com/g/2005#GOOGLE_TALK");
        imAddress.setPrimary(true);
        contact.addImAddress(imAddress);
        // Ask the service to insert the new entry
        URL postUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactEntry createdContact = myService.insert(postUrl, contact);
        System.out.println("Contact's ID: " + createdContact.getId());
        return createdContact;
    }
}
