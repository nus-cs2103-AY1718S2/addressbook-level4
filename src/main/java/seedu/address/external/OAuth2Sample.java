package seedu.address.external;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.util.ServiceException;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Command-line sample for the Google OAuth2 API described at <a
 * href="http://code.google.com/apis/accounts/docs/OAuth2Login.html">Using OAuth 2.0 for Login
 * (Experimental)</a>.
 *
 * @author Yaniv Inbar
 */
public class OAuth2Sample {

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "Codeducator";

    /** Directory to store user credentials. */
    private static final java.io.File DATA_STORE_DIR =
            new java.io.File(System.getProperty("user.home"), "Desktop/oauth2_sample");

    /**
     * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
     * globally shared instance across your application.
     */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    String[] a = new String[]{
        "https://www.googleapis.com/auth/userinfo.profile",
                "https://www.googleapis.com/auth/userinfo.email",
                "https://www.google.com/m8/feeds/"};
    /** OAuth 2.0 scopes. */
    private final List<String> SCOPES = Arrays.asList(a);

    private static Oauth2 oauth2;
    private static GoogleClientSecrets clientSecrets;
    private Credential credential;
    private GoogleCredential credential2;
    /** Authorizes the installed application to access user's protected data. */
    private Credential authorize() throws Exception {
        // load client secrets

        String CLIENT_ID = "126472549776-8cd9bk56sfubm9rkacjivecikppte982.apps.googleusercontent.com";
        String CLIENT_SECRET = "nyBpzm1OjnKNZOd0-kT1uo7W";
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, CLIENT_ID,CLIENT_SECRET, SCOPES).setDataStoreFactory(
                dataStoreFactory).build();
//        credential2 = new GoogleCredential.Builder()
//                .setTransport(httpTransport)
//                .setJsonFactory(JSON_FACTORY)
//                .setServiceAccountId("cliend_ID")
//                .setServiceAccountScopes(SCOPES)
//                .build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public void initialise() {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            System.out.println("HERE1");
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            System.out.println("HERE2");// authorization
            Long time = new Long(10);
            credential = authorize();
            // set up global Oauth2 instance
            System.out.println("HERE3" + credential.getExpiresInSeconds());
            oauth2 = new Oauth2.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
                    APPLICATION_NAME).build();
            // run commands
            System.out.println("HERE4" + oauth2.tokeninfo());
            tokenInfo(credential.getAccessToken());
            System.out.println("HERE5");
            userInfo();

            printAllContacts();
            System.out.println("Lol");
            // success!
            //return;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void tokenInfo(String accessToken) throws IOException {
        header("Validating a token "+accessToken);
        Tokeninfo tokeninfo = oauth2.tokeninfo().setAccessToken(accessToken).execute();
        System.out.println("issit" + tokeninfo.toPrettyString());
    }

    private static void userInfo() throws IOException {
        header("Obtaining User Profile Information");
        Userinfoplus userinfo = oauth2.userinfo().get().execute();
        System.out.println(userinfo.toPrettyString());
    }

    static void header(String name) {
        System.out.println();
        System.out.println("================== " + name + " ==================");
        System.out.println();
    }

    public void printAllContacts()
            throws ServiceException, IOException {
        // Request the feed
        ContactsService myService = new ContactsService(APPLICATION_NAME);
        //myService.setHeader("Authorization", "Bearer" + credential.getAccessToken());
        URL feedUrl = new
                URL("https://www.google.com/m8/feeds/contacts/codeducatorapp@gmail.com/full?access_token=" +credential.getAccessToken());
        System.out.print("print1");
        Query myQuery = new Query(feedUrl);
        myQuery.setMaxResults(1000);
        GoogleCredential gc = new GoogleCredential();
        gc.setAccessToken(credential.getAccessToken());
        myService.setOAuth2Credentials(gc);

        //myService.setOAuth2Credentials(credential);
        //myService.setHeader("User-Agent", APPLICATION_NAME);
        //myService.setHeader("GData-Version", "3.0");
////        myService.setUserCredentials("codeducatorapp@gmail.com", "cs2103tb");
        System.out.print("print2");
        myService.query(myQuery, ContactFeed.class);
        System.out.print("print3synsync");
        // Print the results
        //System.out.println(resultFeed.getTitle().getPlainText());
//
//        for (ContactEntry entry : resultFeed.getEntries()) {
//            if (entry.hasName()) {
//                Name name = entry.getName();
//                if (name.hasFullName()) {
//                    String fullNameToDisplay = name.getFullName().getValue();
//                    if (name.getFullName().hasYomi()) {
//                        fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
//                    }
//                    System.out.println("\t\t" + fullNameToDisplay);
//                } else {
//                    System.out.println("\t\t (no full name found)");
//                }
//                if (name.hasNamePrefix()) {
//                    System.out.println("\t\t" + name.getNamePrefix().getValue());
//                } else {
//                    System.out.println("\t\t (no name prefix found)");
//                }
//                if (name.hasGivenName()) {
//                    String givenNameToDisplay = name.getGivenName().getValue();
//                    if (name.getGivenName().hasYomi()) {
//                        givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
//                    }
//                    System.out.println("\t\t" + givenNameToDisplay);
//                } else {
//                    System.out.println("\t\t (no given name found)");
//                }
//                if (name.hasAdditionalName()) {
//                    String additionalNameToDisplay = name.getAdditionalName().getValue();
//                    if (name.getAdditionalName().hasYomi()) {
//                        additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
//                    }
//                    System.out.println("\t\t" + additionalNameToDisplay);
//                } else {
//                    System.out.println("\t\t (no additional name found)");
//                }
//                if (name.hasFamilyName()) {
//                    String familyNameToDisplay = name.getFamilyName().getValue();
//                    if (name.getFamilyName().hasYomi()) {
//                        familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
//                    }
//                    System.out.println("\t\t" + familyNameToDisplay);
//                } else {
//                    System.out.println("\t\t (no family name found)");
//                }
//                if (name.hasNameSuffix()) {
//                    System.out.println("\t\t" + name.getNameSuffix().getValue());
//                } else {
//                    System.out.println("\t\t (no name suffix found)");
//                }
//            } else {
//                System.out.println("\t (no name found)");
//            }
//            System.out.println("Email addresses:");
//            for (Email email : entry.getEmailAddresses()) {
//                System.out.print(" " + email.getAddress());
//                if (email.getRel() != null) {
//                    System.out.print(" rel:" + email.getRel());
//                }
//                if (email.getLabel() != null) {
//                    System.out.print(" label:" + email.getLabel());
//                }
//                if (email.getPrimary()) {
//                    System.out.print(" (primary) ");
//                }
//                System.out.print("\n");
//            }
//            System.out.println("IM addresses:");
//            for (Im im : entry.getImAddresses()) {
//                System.out.print(" " + im.getAddress());
//                if (im.getLabel() != null) {
//                    System.out.print(" label:" + im.getLabel());
//                }
//                if (im.getRel() != null) {
//                    System.out.print(" rel:" + im.getRel());
//                }
//                if (im.getProtocol() != null) {
//                    System.out.print(" protocol:" + im.getProtocol());
//                }
//                if (im.getPrimary()) {
//                    System.out.print(" (primary) ");
//                }
//                System.out.print("\n");
//            }
//            System.out.println("Groups:");
//            for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
//                String groupHref = group.getHref();
//                System.out.println("  Id: " + groupHref);
//            }
//            System.out.println("Extended Properties:");
//            for (ExtendedProperty property : entry.getExtendedProperties()) {
//                if (property.getValue() != null) {
//                    System.out.println("  " + property.getName() + "(value) = " +
//                            property.getValue());
//                } else if (property.getXmlBlob() != null) {
//                    System.out.println("  " + property.getName() + "(xmlBlob)= " +
//                            property.getXmlBlob().getBlob());
//                }
//            }
//            Link photoLink = entry.getContactPhotoLink();
//            String photoLinkHref = photoLink.getHref();
//            System.out.println("Photo Link: " + photoLinkHref);
//            if (photoLink.getEtag() != null) {
//                System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
//            }
//            System.out.println("Contact's ETag: " + entry.getEtag());
//        }
    }
}