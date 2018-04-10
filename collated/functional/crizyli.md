# crizyli
###### \java\seedu\address\commons\events\logic\FileChoosedEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An event to inform AddPhotoCommand the photo is chosen.
 */
public class FileChoosedEvent extends BaseEvent {

    private final String filePath;

    public FileChoosedEvent(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getFilePath() {
        return filePath;
    }
}
```
###### \java\seedu\address\commons\events\logic\PasswordEnteredEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An event to inform UnlockCommand password is entered.
 */
public class PasswordEnteredEvent extends BaseEvent {

    private final String password;

    public PasswordEnteredEvent(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getPassword() {
        return password;
    }
}
```
###### \java\seedu\address\commons\events\logic\SetPasswordEnteredEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An event to inform UnlockCommand password is entered.
 */
public class SetPasswordEnteredEvent extends BaseEvent {

    private final String mixPsw;

    public SetPasswordEnteredEvent(String mixPsw) {
        this.mixPsw = mixPsw;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getPassword() {
        return mixPsw;
    }
}
```
###### \java\seedu\address\commons\events\model\AddressBookPasswordChangedEvent.java
``` java
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Indicates password changed
 * */
public class AddressBookPasswordChangedEvent extends BaseEvent {

    public final String password;

    public final ReadOnlyAddressBook data;

    public AddressBookPasswordChangedEvent(String password, ReadOnlyAddressBook data) {
        this.password = password;
        this.data = data;
    }

    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
```
###### \java\seedu\address\commons\events\ui\HideDetailPanelEvent.java
``` java
import seedu.address.commons.events.BaseEvent;


/**
 * Represents an event to free resources in Browser Panel
 */
public class HideDetailPanelEvent extends BaseEvent {

    public HideDetailPanelEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ResetPersonCardsEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * Represents an event indicate person card changed.
 */
public class ResetPersonCardsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowFileChooserEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show the file chooser.
 */
public class ShowFileChooserEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowMyCalendarEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view my calendar page.
 */
public class ShowMyCalendarEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\ShowPasswordFieldEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show the password input dialog.
 */
public class ShowPasswordFieldEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowSetPasswordDialogEvent.java
``` java
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show the set password dialog.
 */
public class ShowSetPasswordDialogEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowTodoListDisplayContentEvent.java
``` java
import java.util.ArrayList;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.listevent.ListEvent;

/**
 * Indicates a request to load calendar events to to do list window.
 */
public class ShowTodoListDisplayContentEvent extends BaseEvent {

    private final ArrayList<ListEvent> eventList;

    public ShowTodoListDisplayContentEvent(ArrayList<ListEvent> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ArrayList<ListEvent> getListEvent() {
        return this.eventList;
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowTodoListEvent.java
``` java
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TodoListWindow;

/**
 * An event requesting to show to do list window.
 */
public class ShowTodoListEvent extends BaseEvent {

    private TodoListWindow todoListWindow;

    public ShowTodoListEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TodoListWindow getTodoListWindow() {
        return this.todoListWindow;
    }

}
```
###### \java\seedu\address\logic\Authentication.java
``` java
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;

import seedu.address.logic.commands.TestAddEventCommand;

/**
 * To create an authorized google calendar service to be used in commands that require this service.
 *
 */
public class Authentication {

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory dataStoreFactory;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    /** Application name. */
    private static final String APPLICATION_NAME = "Employees Tracker";

    static {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                TestAddEventCommand.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * authenticate the service and return its state.
     * @return authentication success or not
     */
    public static boolean authen() {
        com.google.api.services.calendar.Calendar service =
                null;
        try {
            service = getCalendarService();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
```
###### \java\seedu\address\logic\commands\AddPhotoCommand.java
``` java
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.logic.FileChoosedEvent;
import seedu.address.commons.events.ui.PersonEditedEvent;
import seedu.address.commons.events.ui.ResetPersonCardsEvent;
import seedu.address.commons.events.ui.ShowFileChooserEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.photo.Photo;

/**
 * Adds a photo to an employee.
 */
public class AddPhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addPhoto";

    public static final String IMAGE_FOLDER_WINDOWS = "\\src\\main\\resources\\images\\personphoto\\";

    public static final String IMAGE_FOLDER_OTHER = "/src/main/resources/images/personphoto/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a photo to an employee.\n"
            + "Choose a photo in the file chooser. Acceptable photo file type are jpg, jprg, png, bmp."
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "New photo added!";

    public static final String MESSAGE_INVALID_PHOTO_TYPE = "The photo type is unacceptable!";

    public static final String MESSAGE_PHOTO_NOT_CHOSEN = "You have not chosen one photo!";

    private final Index targetIndex;

    private Person targetPerson;

    private Person editedPerson;

    private String path;

    private String photoNameWithExtension;

    private boolean isTestMode;

    private int osType;

    /**
     * Creates an AddPhotoCommand to add the specified {@code Photo}
     */
    public AddPhotoCommand(Index index) {
        this.targetIndex = index;
        isTestMode = false;
        registerAsAnEventHandler(this);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        //check if it is test mode.
        if (!isTestMode) {
            EventsCenter.getInstance().post(new ShowFileChooserEvent());
        } else {
            String currentDir = System.getProperty("user.dir");
            path = currentDir + "\\src\\main\\java\\resources\\images\\personphoto\\DefaultPerson.png";
        }

        //check if the photo is chosen.
        if (path.equals("NoFileChoosed")) {
            return new CommandResult(MESSAGE_PHOTO_NOT_CHOSEN);
        }

        //check if the photo is of right type.
        if (!Photo.isValidPhotoName(path)) {
            return new CommandResult(MESSAGE_INVALID_PHOTO_TYPE);
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        targetPerson = lastShownList.get(targetIndex.getZeroBased());


        if (!path.contains("/"))  { //windows
            this.osType = 1;
            photoNameWithExtension = path.substring(path.lastIndexOf("\\") + 1);
        } else {
            this.osType = 0;
            photoNameWithExtension = path.substring(path.lastIndexOf("/") + 1);
        }

        if (!model.getPhotoList().contains(new Photo(photoNameWithExtension))) {
            copyPhotoFileToStorage(photoNameWithExtension);
        }

        editedPerson = createEditedPerson(targetPerson, photoNameWithExtension);

        try {
            model.updatePerson(targetPerson, editedPerson);
        } catch (DuplicatePersonException e) {
            e.printStackTrace();
        } catch (PersonNotFoundException e) {
            e.printStackTrace();
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new ResetPersonCardsEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        targetPerson = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = createEditedPerson(targetPerson, photoNameWithExtension);
        EventsCenter.getInstance().post(new PersonEditedEvent(editedPerson));
    }

    /**
     * create a person with photo updated.
     * @param targetPerson
     * @param photoNameWithExtension
     * @return editedPerson
     */
    private Person createEditedPerson(Person targetPerson, String photoNameWithExtension) {
        Photo newPhoto = new Photo(photoNameWithExtension);
        targetPerson.setPhotoName(newPhoto.getName());
        Person editedPerson = new Person(targetPerson.getName(), targetPerson.getPhone(), targetPerson.getEmail(),
                targetPerson.getAddress(), targetPerson.getTags(), targetPerson.getCalendarId());
        editedPerson.setPhotoName(newPhoto.getName());
        return editedPerson;
    }

    /**
     * copy the file chosen by user to application's storage.
     * @param photoNameWithExtension
     */
    private void copyPhotoFileToStorage(String photoNameWithExtension) {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        String src = path;
        String dest;
        if (osType == 1) {
            dest = s + IMAGE_FOLDER_WINDOWS + photoNameWithExtension;
        } else {
            dest = s + IMAGE_FOLDER_OTHER + photoNameWithExtension;
        }

        byte[] buffer = new byte[1024];
        try {
            FileInputStream fis = new FileInputStream(src);
            BufferedInputStream bis = new BufferedInputStream(fis);


            FileOutputStream fos = new FileOutputStream(dest);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int len;
            while ((len = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }

            bis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPhotoCommand // instanceof handles nulls
                && targetIndex.equals(((AddPhotoCommand) other).targetIndex)
                && (path == null || path.equals(((AddPhotoCommand) other).path)));
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handleFileChoosedEvent(FileChoosedEvent event) {
        this.path = event.getFilePath();
    }

    public void setTestMode() {
        this.isTestMode = true;
    }
}
```
###### \java\seedu\address\logic\commands\AuthenCommand.java
``` java
import seedu.address.logic.Authentication;

/**
 * To authorize ET.
 */
public class AuthenCommand extends Command {

    public static final String COMMAND_WORD = "authenET";

    public static final String MESSAGE_SUCCESS = "You have authorized ET!";

    public static final String MESSAGE_FAILURE = "You haven't authorized ET successfully,"
            + " please try it again later";


    @Override
    public CommandResult execute() {
        boolean isSuccessful = Authentication.authen();

        if (isSuccessful) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }

    }
}
```
###### \java\seedu\address\logic\commands\DeleteEventCommand.java
``` java
import java.io.IOException;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Authentication;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Deletes an event with specified title in a person's calendar.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Delete an event specified by title of the person identified by the index number used "
            + "in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + " TITLE (event title)\n"
            + "Example: " + COMMAND_WORD + " 1 Weekly Meeting";

    public static final String MESSAGE_SUCCESS = "Event deleted!";
    public static final String MESSAGE_NO_SUCH_EVENT = "There is no such event!";
    public static final String MESSAGE_FAILURE = "Unable to delete event, please try again later.";


    private final Index targetIndex;
    private final String title;

    public DeleteEventCommand(Index index, String title) {
        this.targetIndex = index;
        this.title = title;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());

        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                null;
        try {
            service = Authentication.getCalendarService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String calendarId = targetPerson.getCalendarId();
        String pageToken = null;
        String eventId = null;
        do {
            Events events = null;
            try {
                events = service.events().list(calendarId).setPageToken(pageToken).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Event> items = events.getItems();
            for (Event event : items) {
                if (event.getSummary().compareTo(title) == 0) {
                    eventId = event.getId();
                    break;
                }
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);

        if (eventId != null) {
            try {
                service.events().delete(calendarId, eventId).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return new CommandResult(MESSAGE_NO_SUCH_EVENT);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public Index getTargetIndex() {
        return this.targetIndex;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteEventCommand) other).getTargetIndex())
                && this.title.equals(((DeleteEventCommand) other).getTitle())); // state check
    }
}
```
###### \java\seedu\address\logic\commands\LockCommand.java
``` java
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideDetailPanelEvent;
import seedu.address.logic.LogicManager;
import seedu.address.model.person.HideAllPersonPredicate;


/**
 * Locks the app with a password
 * */
public class LockCommand extends Command {

    public static final String COMMAND_WORD = "lock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been locked!";

    private final HideAllPersonPredicate predicate = new HideAllPersonPredicate();

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new HideDetailPanelEvent());
        LogicManager.lock();

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LockCommand);
    }
}
```
###### \java\seedu\address\logic\commands\MyCalendarCommand.java
``` java
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMyCalendarEvent;

/**
 * Show my own calendar
 * */
public class MyCalendarCommand extends Command {

    public static final String COMMAND_WORD = "myCalendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": show my own calendar. ";

    public static final String MESSAGE_SUCCESS = "Your calendar is loaded.";

    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(new ShowMyCalendarEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\SetPasswordCommand.java
``` java
import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.SetPasswordEnteredEvent;
import seedu.address.commons.events.ui.ShowSetPasswordDialogEvent;
import seedu.address.logic.LogicManager;

/**
 * Set the application password
 * */
public class SetPasswordCommand extends Command {

    public static final String COMMAND_WORD = "setPassword";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set new application password, and old "
            + "password required. \n";

    public static final String MESSAGE_SUCCESS = "New password has been set!";

    public static final String MESSAGE_INCORRECT_OLDPASSWORD = "Incorrect old password!";

    public static final String MESSAGE_INCOMPLETE_FIELD = "Input field(s) not complete!";

    private String oldPassword;

    private String newPassword;

    private boolean isComplete;

    private boolean isTestMode;

    public SetPasswordCommand() {
        isComplete = true;
        registerAsAnEventHandler(this);
    }

    @Override
    public CommandResult execute() {

        if (!isTestMode) {
            EventsCenter.getInstance().post(new ShowSetPasswordDialogEvent());
        } else {
            this.oldPassword = "admin";
            this.newPassword = "newpsw";
        }

        if (!isComplete) {
            return new CommandResult(MESSAGE_INCOMPLETE_FIELD);
        }

        if (this.oldPassword.compareTo(LogicManager.getPassword()) == 0) {
            LogicManager.setPassword(this.newPassword);
            model.setPassword(this.newPassword);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_INCORRECT_OLDPASSWORD);
        }
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPasswordCommand);
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handleSetPasswordEnteredEvent(SetPasswordEnteredEvent event) {

        if (event.getPassword().equals("incomplete")) {
            isComplete = false;
        }

        this.oldPassword = event.getPassword().substring(0, event.getPassword().lastIndexOf(","));
        this.newPassword = event.getPassword().substring(event.getPassword().lastIndexOf(",") + 1);
    }

    public void setTestMode() {
        this.isTestMode = true;
    }
}
```
###### \java\seedu\address\logic\commands\TestAddEventCommand.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Authentication;
import seedu.address.logic.CreateNewCalendar;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.exceptions.DuplicateTimetableEntryException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

```
###### \java\seedu\address\logic\commands\TestAddEventCommand.java
``` java
/**
 * Adds an event to a person.
 */
public class TestAddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add an event to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + PREFIX_TITLE + "TITLE "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_STARTTIME + "STARTTIME (must follow given format) "
            + PREFIX_ENDTIME + "ENDTIME (must follow given format)\n"
            + "Example: " + COMMAND_WORD + " 1 title/Project Meeting loca/NUS, Singapore stime/2017-03-19T08:00:00"
            + " etime/2017-03-19T10:00:00 descrip/discuss about v1.4 milestone";

    public static final String MESSAGE_SUCCESS = "Event added!";
    public static final String MESSAGE_FAILURE = "Unable to add event, please try again later.";



    private final Index targetIndex;
    private final String title;
    private final String location;
    private final String startTime;
    private final String endTime;
    private final String description;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public TestAddEventCommand(Index index, String title, String location, String startTime,
                               String endTime, String description) {
        this.title = title;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.targetIndex = index;
    }



    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToAddEvent = lastShownList.get(targetIndex.getZeroBased());

        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                null;
        try {
            service = Authentication.getCalendarService();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Event event = new Event()
                .setSummary(title)
                .setLocation(location)
                .setDescription(description);

        String startTimeFormat = startTime + "+08:00";
        DateTime startDateTime = new DateTime(startTimeFormat);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Singapore");
        event.setStart(start);

        String endTimeFormat = endTime + "+08:00";
        DateTime endDateTime = new DateTime(endTimeFormat);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Singapore");
        event.setEnd(end);

        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        //event.setRecurrence(Arrays.asList(recurrence));

        /*EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("jjjsss@example.com"),
                new EventAttendee().setEmail("dzzzssss@example.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);*/

        String calendarId = personToAddEvent.getCalendarId();
        Logger logger = LogsCenter.getLogger(TestAddEventCommand.class);
```
###### \java\seedu\address\logic\commands\TestAddEventCommand.java
``` java

        try {
            event = service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("failed to add event to calendarId");
            return new CommandResult(MESSAGE_FAILURE);
        }

```
###### \java\seedu\address\logic\commands\TestAddEventCommand.java
``` java

        System.out.printf("Event created: %s\n", event.getHtmlLink());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TestAddEventCommand // instanceof handles nulls
                && targetIndex.equals(((TestAddEventCommand) other).targetIndex)
                && title.equals(((TestAddEventCommand) other).title)
                && location.equals(((TestAddEventCommand) other).location)
                && startTime.equals(((TestAddEventCommand) other).startTime)
                && endTime.equals(((TestAddEventCommand) other).endTime)
                && description.equals(((TestAddEventCommand) other).description));
    }
}
```
###### \java\seedu\address\logic\commands\TodoListCommand.java
``` java
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowTodoListDisplayContentEvent;
import seedu.address.commons.events.ui.ShowTodoListEvent;
import seedu.address.logic.Authentication;
import seedu.address.model.listevent.ListEvent;

/**
 * Show to do list window.
 */
public class TodoListCommand extends Command {

    public static final String COMMAND_WORD = "todoList";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Show the To Do List in a seperate window."
            + "\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "To do list window is loaded.";


    private ArrayList<ListEvent> eventList;

    public TodoListCommand() {
        eventList = new ArrayList<>();
    }


    @Override
    public CommandResult execute() {

        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                null;
        try {
            service = Authentication.getCalendarService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String calendarId = "primary";
        String pageToken = null;
        do {
            Events events = null;
            try {
                events = service.events().list(calendarId).setPageToken(pageToken).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Event> items = events.getItems();
            for (Event event : items) {
                eventList.add(new ListEvent(event.getSummary(), event.getLocation(), event.getStart().getDateTime()));
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);

        EventsCenter.getInstance().post(new ShowTodoListEvent());
        EventsCenter.getInstance().post(new ShowTodoListDisplayContentEvent(eventList));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\UnlockCommand.java
``` java
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.AddressBookUnlockedEvent;
import seedu.address.commons.events.logic.PasswordEnteredEvent;
import seedu.address.commons.events.ui.ShowPasswordFieldEvent;
import seedu.address.logic.LogicManager;

/**
 * Unlocks ET
 */
public class UnlockCommand extends Command {

    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unlock the address book. ";

    public static final String MESSAGE_SUCCESS = "Address book has been unlocked!";

    public static final String MESSAGE_INCORRECT_PASSWORD = "Incorrect unlock password!";

    public static final String MESSAGE_MISSING_PASSWORD = "Password is missing!";

    private String password;

    private boolean isTestMode;

    public UnlockCommand() {
        isTestMode = false;
        registerAsAnEventHandler(this);
    }

    @Override
    public CommandResult execute() {
        if (!LogicManager.isLocked()) {
            return new CommandResult("Employees Tracker is already unlocked!");
        }

        if (!isTestMode) {
            EventsCenter.getInstance().post(new ShowPasswordFieldEvent());
        } else {
            this.password = "admin";
        }

        if (this.password.equals("nopassword")) {
            return new CommandResult(MESSAGE_MISSING_PASSWORD);
        }

        if (this.password.compareTo(LogicManager.getPassword()) == 0) {
            LogicManager.unLock();
            EventsCenter.getInstance().post(new AddressBookUnlockedEvent());
```
###### \java\seedu\address\logic\commands\UnlockCommand.java
``` java
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_INCORRECT_PASSWORD);
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnlockCommand);
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handlePasswordEnteredEvent(PasswordEnteredEvent event) {
        this.password = event.getPassword();
    }

    public void setTestMode() {
        isTestMode = true;
    }
}
```
###### \java\seedu\address\logic\CreateNewCalendar.java
``` java
import java.io.IOException;

import com.google.api.services.calendar.model.Calendar;

/**
 * Create a calendar for a person.
 */
public class CreateNewCalendar {

    /**
     * Create a new calendar for person with personName.
     *
     */
    public static String execute(String personName) throws IOException {
        // Build a new authorized API client service.
        com.google.api.services.calendar.Calendar service =
                null;

        service = Authentication.getCalendarService();

        // Create a new calendar
        com.google.api.services.calendar.model.Calendar calendar = new Calendar();
        calendar.setSummary(personName);
        calendar.setTimeZone("Asia/Singapore");


        // Insert the new calendar
        String calendarId = "primary";

        try {
            Calendar createdCalendar = service.calendars().insert(calendar).execute();
            calendarId = createdCalendar.getId();
            System.out.println(calendarId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calendarId;
    }

}
```
###### \java\seedu\address\logic\DeleteCalendar.java
``` java
import java.io.IOException;


/**
 * Delete calendar of a person.
 */
public class DeleteCalendar {

    /**
     * Delete a calendar specified by calendarId.
     *
     */
    public static void execute(String calendarId) throws IOException {
        // Build a new authorized API client service.
        com.google.api.services.calendar.Calendar service =
                null;

        service = Authentication.getCalendarService();

        // Delete the calendar.
        service.calendars().delete(calendarId).execute();

    }

}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    /**
     * Constructor for test use.
     */
    public LogicManager(Model model, boolean initialLock) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        isLocked = initialLock;
        password = model.getPassword();
        timetableEntriesStatus = new HashMap<>();
        scheduledTimerTasks = new HashMap<>();
        timerTaskToTimetableEntryMap = new HashMap<>();
    }
```
###### \java\seedu\address\logic\LogicManager.java
``` java

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command;
            CommandResult result = new CommandResult("");
            command = addressBookParser.parseCommand(commandText);
            if (isLocked && !(command instanceof HelpCommand)) {
                command.setData(model, history, undoRedoStack);
                if (command instanceof UnlockCommand) {
                    UnlockCommand unlockCommand = (UnlockCommand) command;
                    result = unlockCommand.execute();
                } else {
                    result = new CommandResult("Addressbook has been locked, please unlock it first!");
                }
            } else {
                command.setData(model, history, undoRedoStack);
                result = command.execute();
                undoRedoStack.push(command);
            }
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    public static String getPassword() {
        return password;
    }

    public static void setPassword(String psw) {
        password = psw;
    }

    public static void unLock() {
        isLocked = false;
    }

    public static void lock() {
        isLocked = true;
    }

    public static boolean isLocked() {
        return isLocked;
    }

```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            String calendarId;
            if (!LogicManager.isLocked()) {
                try {
                    calendarId = CreateNewCalendar.execute(name.fullName);
                } catch (IOException e) { //not signed in
                    calendarId = "";
                }
            } else {
                calendarId = "";
            }
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
    /**
     * overload parse method for test use.
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args, boolean isTest) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            String calendarId;
            if (!isTest) {
                try {
                    calendarId = CreateNewCalendar.execute(name.fullName);
                } catch (IOException e) { //not signed in
                    calendarId = "";
                }
            } else {
                calendarId = "";
            }

            Person person = new Person(name, phone, email, address, tagList, calendarId);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
```
###### \java\seedu\address\logic\parser\AddPhotoCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for the AddPhotoCommand'
 */
public class AddPhotoCommandParser implements Parser<AddPhotoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddPhotoCommand
     * and returns an AddPhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPhotoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AddPhotoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\DeleteEventCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Scanner;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for the DeleteEventCommand'
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns an DeleteEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        Scanner sc = new Scanner(trimmedArgs);
        if (!sc.hasNextInt()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(((Integer) sc.nextInt()).toString());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }

        if (!sc.hasNextLine()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }
        String tilte = sc.nextLine().trim();

        return new DeleteEventCommand(index, tilte);
    }
}
```
###### \java\seedu\address\logic\parser\LockCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse arguments for LockCommand
 */
public class LockCommandParser implements Parser<LockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LockCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
        }

        return new LockCommand();
    }
}
```
###### \java\seedu\address\logic\parser\SetPasswordCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for the SetPasswordCommand'
 */
public class SetPasswordCommandParser implements Parser<SetPasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetPasswordCommand
     * and returns an SetPasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetPasswordCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE));
        }

        return new SetPasswordCommand();
    }
}
```
###### \java\seedu\address\logic\parser\TestAddEventCommandParser.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Scanner;
import java.util.stream.Stream;

import com.google.api.client.util.DateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TestAddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TestAddEventCommand object
 */
public class TestAddEventCommandParser implements Parser<TestAddEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the TestAddEventCommand
     * and returns a TestAddEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TestAddEventCommand parse(String args) throws ParseException {

        requireNonNull(args);
        Scanner sc = new Scanner(args);
        if (!sc.hasNextInt()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(((Integer) sc.nextInt()).toString());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE));
        }

        String temp = args.trim();
        int i;
        for (i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == 32) {
                break;
            }
        }
        String behindArgs = temp.substring(i);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(behindArgs, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_STARTTIME,
                        PREFIX_ENDTIME, PREFIX_DESCCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_STARTTIME, PREFIX_ENDTIME,
                PREFIX_DESCCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestAddEventCommand.MESSAGE_USAGE));
        }


        String title = argMultimap.getValue(PREFIX_TITLE).get();
        String location = argMultimap.getValue(PREFIX_LOCATION).get();
        String stime = argMultimap.getValue(PREFIX_STARTTIME).get();
        String etime = argMultimap.getValue(PREFIX_ENDTIME).get();
        try {
            DateTime.parseRfc3339(stime);
        } catch (NumberFormatException n) {
            throw new ParseException("Invalid date/time format: " + stime);
        }

        try {
            DateTime.parseRfc3339(etime);
        } catch (NumberFormatException n) {
            throw new ParseException("Invalid date/time format: " + etime);
        }

        String decription = argMultimap.getValue(PREFIX_DESCCRIPTION).get();

        return new TestAddEventCommand(index, title, location, stime, etime, decription);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\UnlockCommandParser.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for the UnlockCommand'
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnlockCommand
     * and returns an UnlockCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnlockCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
        }

        return new UnlockCommand();
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
        Photo toAdd = new Photo(person.getPhotoName());
        if (!photos.contains(toAdd)) {
            try {
                photos.add(toAdd);
            } catch (UniquePhotoList.DuplicatePhotoException e) {
                e.printStackTrace();
            }
        }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void addPhoto(Photo p) throws UniquePhotoList.DuplicatePhotoException {
        photos.add(p);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<Photo> getPhotoList() {
        return photos.asObservableList();
    }

    @Override
    public LinkedList<Notification> getNotificationsList() {
        return notifications;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags))
                && this.notifications.equals(((AddressBook) other).notifications);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

```
###### \java\seedu\address\model\listevent\ListEvent.java
``` java
import com.google.api.client.util.DateTime;

/**
 * Represents a event that is to be loaded in to do list window.
 */
public class ListEvent {

    private String title;
    private String location;
    private DateTime startTime;

    public ListEvent(String title, String location, DateTime startTime) {
        this.setTitle(title);
        this.setLocation(location);
        this.setStartTime(startTime);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        String toReturn = "";
        if (title != null) {
            toReturn += "EVENT: " + title + "  ||  ";
        }
        if (location != null) {
            toReturn += " LOCATION: " + location + "  ||  ";
        }
        if (startTime != null) {
            toReturn += " START AT: "
                    + startTime.toString().substring(0, startTime.toString()
                    .lastIndexOf("+")).replaceAll("T", " ");
        }
        return toReturn;
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given password */
    void setPassword(String e);

    /** Gets the password */
    String getPassword();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void setPassword(String password) {
        addressBook.setPassword(password);
        indicatePasswordChangedEvent(password);
    }

    public String getPassword() {
        return addressBook.getPassword();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public String getCalendarId() {
        return calendarId;
    }

    public String getPhotoName() {
        return photoName;
    }
```
###### \java\seedu\address\model\photo\Photo.java
``` java

/**
 * Represents a Person's photo.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhotoName(String)}
 */
public class Photo {

    public static final String DEFAULT_PHOTO_FOLDER = "/images/personphoto/";

    public static final String MESSAGE_PHOTO_CONSTRAINTS = "only accepts jpg, jpeg, png and bmp.";

    public final String path;

    public final String name;

    /**
     * Constructs a {@code Photo}.
     *
     * @param name A photo name in images folder.
     */
    public Photo(String name) {
        this.name = name;
        this.path = DEFAULT_PHOTO_FOLDER + name;
    }

    /**
     * Returns true if a given string is a valid photo path.
     */
    public static boolean isValidPhotoName(String test) {
        String extension = test.substring(test.lastIndexOf(".") + 1);
        return extension.compareToIgnoreCase("jpg") == 0
                || extension.compareToIgnoreCase("png") == 0
                || extension.compareToIgnoreCase("jpeg") == 0
                || extension.compareToIgnoreCase("bmp") == 0;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.path.equals(((Photo) other).path)); // state check
    }

}
```
###### \java\seedu\address\model\photo\UniquePhotoList.java
``` java
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of photos that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Photo#equals(Object)
 */
public class UniquePhotoList implements Iterable<Photo> {

    private final ObservableList<Photo> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty PhotoList.
     */
    public UniquePhotoList() {}

    /**
     * Creates a UniquePhotoList using given photos.
     * Enforces no nulls.
     */
    public UniquePhotoList(Set<Photo> photos) {
        requireAllNonNull(photos);
        internalList.addAll(photos);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all photos in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Photo> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Photos in this list with those in the argument photo list.
     */
    public void setPhotos(Set<Photo> photos) {
        requireAllNonNull(photos);
        internalList.setAll(photos);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every photo in the argument list exists in this object.
     */
    public void mergeFrom(UniquePhotoList from) {
        final Set<Photo> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(photo -> !alreadyInside.contains(photo))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Photo as the given argument.
     */
    public boolean contains(Photo toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Photo to the list.
     *
     * @throws UniquePhotoList.DuplicatePhotoException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Photo toAdd) throws UniquePhotoList.DuplicatePhotoException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new UniquePhotoList.DuplicatePhotoException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Photo> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Photo> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniquePhotoList // instanceof handles nulls
                && this.internalList.equals(((UniquePhotoList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniquePhotoList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePhotoException extends DuplicateDataException {
        protected DuplicatePhotoException() {
            super("Operation would result in duplicate photos");
        }
    }

}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Photo> getPhotoList();

```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleAddressBookPasswordChangedEvent(AddressBookPasswordChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Password changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java

        final Photo photo = new Photo(this.photoName);

```
###### \java\seedu\address\storage\XmlAdaptedPhoto.java
``` java
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.photo.Photo;

/**
 * JAXB-friendly adapted version of the Photo.
 */
public class XmlAdaptedPhoto {
    @XmlValue
    private String photoName;

    /**
     * Constructs an XmlAdaptedPhoto.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPhoto() {}

    /**
     * Constructs a {@code XmlAdaptedPhoto} with the given {@code photoName}.
     */
    public XmlAdaptedPhoto(String photoName) {
        this.photoName = photoName;
    }

    /**
     * Converts a given Photo into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPhoto(Photo source) {
        photoName = source.name;
    }

    /**
     * Converts this jaxb-friendly adapted photo object into the model's Photo object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Photo toModelType() throws IllegalValueException {
        if (!Photo.isValidPhotoName(photoName)) {
            throw new IllegalValueException(Photo.MESSAGE_PHOTO_CONSTRAINTS);
        }
        return new Photo(photoName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPhoto)) {
            return false;
        }

        return photoName.equals(((XmlAdaptedPhoto) other).photoName);
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        for (XmlAdaptedPhoto p : photos) {
            addressBook.addPhoto(p.toModelType());
        }
```
###### \java\seedu\address\ui\CalendarBrowser.java
``` java
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A pop-up browser window of the user's own calendar
 */
class CalendarBrowser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public CalendarBrowser() {
        webEngine.load("https://calendar.google.com");
        getChildren().add(browser);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height) {
        return 750;
    }

    @Override
    protected double computePrefHeight(double width) {
        return 600;
    }
}
```
###### \java\seedu\address\ui\DetailPanel.java
``` java
    @Subscribe
    private void handleHideDetailPanelEvent(HideDetailPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        name.setText("");
        address.setText("");
        reviews.getChildren().clear();
        loadDefaultPage();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens calendar web page window.
     */
    public void handleShowMyCalendar() {
        MyCalendarView myCalendarView = new MyCalendarView();
        myCalendarView.start(new Stage());
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowMyCalendarEvent(ShowMyCalendarEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleShowMyCalendar();
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    @Subscribe
    protected void showFileChooser(ShowFileChooserEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose a Photo");
        File file = chooser.showOpenDialog(new Stage());
        String filePath;
        if (file != null) {
            filePath = file.getPath();
        } else {
            filePath = "NoFileChoosed";
        }
        raise(new FileChoosedEvent(filePath));
    }

    @FXML
    @Subscribe
    protected void handleShowPasswordFieldEvent(ShowPasswordFieldEvent event) {
        PasswordDialog passwordDialog = new PasswordDialog();
        Optional<String> input = passwordDialog.showAndWait();
        if (input.isPresent()) {
            raise(new PasswordEnteredEvent(input.get()));
        } else {
            raise(new PasswordEnteredEvent("nopassword"));
        }
    }

    @FXML
    @Subscribe
    protected void handleShowSetPasswordDialogEvent(ShowSetPasswordDialogEvent event) {
        SetPasswordDialog setPasswordDialog = new SetPasswordDialog();
        Optional<String> input = setPasswordDialog.showAndWait();
        if (input.isPresent() && !input.get().equals("incomplete")) {
            raise(new SetPasswordEnteredEvent(input.get()));
        } else {
            raise(new SetPasswordEnteredEvent("incomplete"));
        }
    }
```
###### \java\seedu\address\ui\MyCalendarView.java
``` java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * View the pop-uped browser window of user's calendar.
 */
public class MyCalendarView extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) {
        stage.setTitle("My Calendar");
        CalendarBrowser calendarBrowser = new CalendarBrowser();
        scene = new Scene(calendarBrowser, calendarBrowser.computePrefWidth(750),
                calendarBrowser.computePrefHeight(600));
        stage.setScene(scene);
        stage.show();
    }
}
```
###### \java\seedu\address\ui\PasswordDialog.java
``` java
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Pop-up dialog to prompt user to enter unlock password.
 */
public class PasswordDialog extends Dialog<String> {

    private PasswordField passwordField;

    public PasswordDialog() {

        setTitle("Unlock ET");
        setHeaderText("Please Enter Unlock Password");
        setHeight(200.0);
        setWidth(350.0);

        ButtonType unlockButton = new ButtonType("Unlock", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(unlockButton);

        passwordField = new PasswordField();
        passwordField.setPromptText("password");
        passwordField.setMinWidth(350.0);

        HBox hBox = new HBox();
        hBox.getChildren().add(passwordField);
        hBox.setMinHeight(200.0);
        hBox.setMaxWidth(350.0);


        HBox.setHgrow(passwordField, Priority.ALWAYS);

        getDialogPane().setContent(hBox);

        Platform.runLater(() -> passwordField.requestFocus());
        setResultConverter(dialogButton -> {
            if (dialogButton == unlockButton) {
                return passwordField.getText();
            }
            return null;
        });
    }

}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        File file = new File(s + AddPhotoCommand.IMAGE_FOLDER_OTHER + person.getPhotoName());
        Image image = null;
        try {
            image = new Image(file.toURI().toURL().toExternalForm(),
                    88, 88, false, false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        photo.setImage(image);
        photo.preserveRatioProperty().set(true);
```
###### \java\seedu\address\ui\SetPasswordDialog.java
``` java
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Pop-up dialog to prompt user to enter old and new password for SetPasswordCommand.
 */
public class SetPasswordDialog extends Dialog<String> {

    private PasswordField oldPsw;

    private PasswordField newPsw;

    public SetPasswordDialog() {

        setTitle("Set New Password");
        setHeaderText("Please Enter old Password and new password below");
        setHeight(200.0);
        setWidth(350.0);

        ButtonType setButton = new ButtonType("set", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(setButton);

        oldPsw = new PasswordField();
        oldPsw.setPromptText("old password");
        oldPsw.setMinWidth(350.0);

        newPsw = new PasswordField();
        newPsw.setPromptText("new password");
        newPsw.setMinWidth(350.0);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(oldPsw, newPsw);
        hBox.setMinHeight(200.0);
        hBox.setMaxWidth(350.0);


        HBox.setHgrow(oldPsw, Priority.ALWAYS);

        getDialogPane().setContent(hBox);

        Platform.runLater(() -> newPsw.requestFocus());
        setResultConverter(dialogButton -> {
            if (dialogButton == setButton) {
                String oldp = oldPsw.getText();
                String newp = newPsw.getText();
                if (oldp.isEmpty() || newp.isEmpty()) {
                    return "incomplete";
                }
                String toReturn = oldp.concat(",").concat(newp);
                return toReturn;
            }
            return null;
        });
    }
}
```
###### \java\seedu\address\ui\TodoListWindow.java
``` java
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowTodoListDisplayContentEvent;
import seedu.address.model.listevent.ListEvent;


/**
 * Controller for to do list window
 */
public class TodoListWindow {

    private static final Logger logger = LogsCenter.getLogger(TodoListWindow.class);
    private static final String FXML = "TodoListWindow.fxml";

    private ObservableList<ListEvent> list = FXCollections.observableArrayList();

    @FXML
    private ListView<ListEvent> eventList;

    /**
     * Creates a new TodoListWindow.
     */
    public TodoListWindow() {
        registerAsAnEventHandler(this);
    }

    public void show() {
        logger.fine("Showing to do list.");
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handleShowTodoListDisplayContentEvent(ShowTodoListDisplayContentEvent e) {

        ArrayList<ListEvent> allEvents = e.getListEvent();
        for (int i = 0; i < allEvents.size(); i++) {
            list.add(allEvents.get(i));
        }
        eventList.setItems(list);
    }

}
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Subscribe
    private void handleShowTodoListEvent(ShowTodoListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TodoListWindow.fxml"));
            fxmlLoader.setLocation(getFxmlFileUrl("TodoListWindow.fxml"));
            try {
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e2) {
            System.out.println(e2.getMessage());
        }
    }
```
###### \resources\view\TodoListWindow.fxml
``` fxml
<?import javafx.collections.FXCollections?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>

<VBox prefHeight="400.0" prefWidth="649.0" xmlns:fx="http://javafx.com/fxml/1" xmlns="http://javafx.com/javafx/8.0.121" fx:controller="seedu.address.ui.TodoListWindow">
<children>
    <ListView fx:id="eventList">
        <items>
            <FXCollections fx:factory="observableArrayList">
            </FXCollections>
        </items>
    </ListView>
</children>
</VBox>
```
