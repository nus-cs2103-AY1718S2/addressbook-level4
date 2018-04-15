# wayneong95-reused
###### /java/seedu/address/GoogleCalendar/AddEventManager.java
``` java

/**
 * Some code referenced from CS2103AUG2017-W15-B2.
 */

public class AddEventManager {

    private static final Logger logger = LogsCenter.getLogger(AddEventManager.class);

    private static AddEventManager instance = null;

    public AddEventManager() {
        registerAsAnEventHandler(this);
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     */
    private void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Creates an instance of the Google Calendar Api and register it as an event handler.
     */
    public static AddEventManager init() {
        if (instance == null) {
            instance = new AddEventManager();
        }
        return instance;
    }

```
