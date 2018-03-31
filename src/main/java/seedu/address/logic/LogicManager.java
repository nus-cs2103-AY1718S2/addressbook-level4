package seedu.address.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.NotificationAddedEvent;
import seedu.address.commons.events.model.NotificationDeletedEvent;
import seedu.address.commons.events.ui.ShowNotificationEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.NotificationTime;
import seedu.address.model.person.Person;
import seedu.address.storage.NotificationTimeParserUtil;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private static boolean isLocked = false;
    private static String password;
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;
    private HashMap<TimerTask, Boolean> timetableEntriesStatus;
    private HashMap<TimerTask, Notification> timerTaskToTimetableEntryMap;
    private HashMap<String, TimerTask> scheduledTimerTasks;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        undoRedoStack = new UndoRedoStack();
        isLocked = true;
        password = model.getPassword();
        timetableEntriesStatus = new HashMap<>();
        scheduledTimerTasks = new HashMap<>();
        timerTaskToTimetableEntryMap = new HashMap<>();
    }

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

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command;
            CommandResult result = new CommandResult("");
            if (isLocked) {
                command = addressBookParser.parseCommand(commandText);
                command.setData(model, history, undoRedoStack);
                if (command instanceof UnlockCommand) {
                    UnlockCommand unlockCommand = (UnlockCommand) command;
                    result = unlockCommand.execute();
                } else {
                    result = new CommandResult("Addressbook has been locked, please unlock it first!");
                }
            } else {
                command = addressBookParser.parseCommand(commandText);
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

    @Subscribe
    private void handleTimetableEntryAddedEvent(NotificationAddedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        NotificationTime parsedTime = NotificationTimeParserUtil.parseTime(event.notification.getEndDate());
        Calendar c = Calendar.getInstance();
        c.set(parsedTime.getYear(), parsedTime.getMonth(), parsedTime.getDate(), parsedTime.getHour(),
                parsedTime.getMinute());
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (timetableEntriesStatus.get(this)) {
                    System.out.println("An event ended at: " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format
                            (Calendar.getInstance().getTimeInMillis()));
                } else {
                    System.out.println("A cancelled event ended at: " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                            .format(Calendar.getInstance().getTimeInMillis()));
                }
                Notification notification = timerTaskToTimetableEntryMap.get(this);
                String ownerName = ((ModelManager) model).getNameById(notification.getOwnerId());
                raise(new ShowNotificationEvent(ownerName, notification));
                //raise(new RequestToDeleteNotificationEvent(timerTaskToTimetableEntryMap.get(this).getId()));
            }
        };
        timetableEntriesStatus.put(task, true);
        scheduledTimerTasks.put(event.notification.getId(), task);
        timerTaskToTimetableEntryMap.put(task, event.notification);
        System.out.println("An event scheduled at " + c.getTime() + " " + (c.getTimeInMillis() - System
                .currentTimeMillis()));
        long duration = c.getTimeInMillis() - System.currentTimeMillis();
        if (duration >= 0) {
            if (parsedTime.isToday()) {
                String ownerName = ((ModelManager) model).getNameById(event.notification.getOwnerId());
                raise(new ShowNotificationEvent(ownerName, event.notification, true));
            }
            timer.schedule(task, duration);
        } else {
            task.run();
        }
    }

    @Subscribe
    private void handleTimetableEntryDeletedEvent(NotificationDeletedEvent event) {
        TimerTask associatedTimerTask = scheduledTimerTasks.get(event.id);
        timetableEntriesStatus.put(associatedTimerTask, false);
        scheduledTimerTasks.remove(event.id);
    }
}
