# ncaminh
###### \java\seedu\address\commons\events\ui\ShowDefaultPageEvent.java
``` java
/**
 * Show default page
 */
public class ShowDefaultPageEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowMultiLocationFromHeadQuarterEvent.java
``` java
/**
 * Show Google map route from HQ to many locations
 */
public class ShowMultiLocationFromHeadQuarterEvent extends BaseEvent {

    public final List<String> sortedList;

    public ShowMultiLocationFromHeadQuarterEvent(List<String> sortedList) {
        this.sortedList = sortedList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowRouteFromHeadQuarterToOneEvent.java
``` java
/**
 * Show Google map route from HQ to many locations
 */
public class ShowRouteFromHeadQuarterToOneEvent extends BaseEvent {

    public final String destination;

    public ShowRouteFromHeadQuarterToOneEvent(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowRouteFromOneToAnotherEvent.java
``` java
/**
 * Show Google map route from HQ to many locations
 */
public class ShowRouteFromOneToAnotherEvent extends BaseEvent {

    public final List<String> sortedList;

    public ShowRouteFromOneToAnotherEvent(List<String> sortedList) {
        this.sortedList = sortedList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\DistanceCommand.java
``` java
/**
 * Finds the distance from the headquarter to a person address
 * using his or her last displayed index from the address book.
 */
public class DistanceCommand extends Command {

    public static final String COMMAND_WORD = "distance";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds the distance from the headquarter to a person address "
            + "or the distance from a person address to another person address "
            + "by the index number(s) used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_DISTANCE_FROM_HQ_FAILURE = "Unable to find %1$s's address";
    public static final String MESSAGE_DISTANCE_FROM_HQ_SUCCESS = "Distance from Head quarter to %1$s: %2$s km";
    public static final String MESSAGE_DISTANCE_FROM_PERSON_FAILURE = "Unable to find at least one person's address";
    public static final String MESSAGE_DISTANCE_FROM_PERSON_SUCCESS = "Distance from %1$s to %2$s: %3$s km";

    private Index targetIndexOrigin = null;
    private Index targetIndexDestination;

    /**
     * constructor for DistanceCommand calculating distance from HQ to a person
     * @param targetIndex
     */
    public DistanceCommand(Index targetIndex) {
        this.targetIndexDestination = targetIndex;
    }

    /**
     * constructor for DistanceCommand calculating distance from a person to another person
     * @param targetIndexOrigin
     * @param targetIndexDestination
     */
    public DistanceCommand(Index targetIndexOrigin, Index targetIndexDestination) {
        this.targetIndexOrigin = targetIndexOrigin;
        this.targetIndexDestination = targetIndexDestination;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        String origin;
        String destination;
        String personNameOrigin = "";
        String personNameDestination = "";

        //case 1: get distance from HQ to a person address
        if (targetIndexOrigin == null) {
            if (targetIndexDestination.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            int indexZeroBasedDestination = targetIndexDestination.getZeroBased();
            Person person = lastShownList.get(indexZeroBasedDestination);
            String personName = person.getName().toString();
            origin = "Kent Ridge MRT";
            destination = person.getAddress().toString();

            //Trim address
            destination = trimAddress(destination);

            GetDistance route = new GetDistance();
            Double distance = route.getDistance(origin, destination);

            EventsCenter.getInstance().post(new ShowRouteFromHeadQuarterToOneEvent(destination));

            if (distance == -1) {
                return new CommandResult(String.format(MESSAGE_DISTANCE_FROM_HQ_FAILURE,
                        personName));
            }

            return new CommandResult(String.format
                    (MESSAGE_DISTANCE_FROM_HQ_SUCCESS, personName, distance));

        } else {
            //case 2: get distance from a person address to another person address
            if (targetIndexOrigin.getZeroBased() >= lastShownList.size()
                    || targetIndexDestination.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            int indexZeroBasedOrigin = targetIndexOrigin.getZeroBased();
            int indexZeroBasedDestination = targetIndexDestination.getZeroBased();
            Person personOrigin = lastShownList.get(indexZeroBasedOrigin);
            Person personDestination = lastShownList.get(indexZeroBasedDestination);
            origin = personOrigin.getAddress().toString();
            destination = personDestination.getAddress().toString();

            //Trim addresses
            origin = trimAddress(origin);

            destination = trimAddress(destination);

            personNameOrigin = personOrigin.getName().toString();
            personNameDestination = personDestination.getName().toString();

            GetDistance route = new GetDistance();
            Double distance = route.getDistance(origin, destination);

            List<String> addressesList = new ArrayList<>();
            addressesList.add(origin);
            addressesList.add(destination);
            EventsCenter.getInstance().post(new ShowRouteFromOneToAnotherEvent(addressesList));

            if (distance == -1) {
                return new CommandResult(String.format(MESSAGE_DISTANCE_FROM_PERSON_FAILURE));
            }

            return new CommandResult(String.format(
                    MESSAGE_DISTANCE_FROM_PERSON_SUCCESS, personNameOrigin, personNameDestination, distance));

        }
    }

    private String trimAddress(String address) {
        if (address.indexOf('#') > 2) {
            int stringCutIndex;
            stringCutIndex = address.indexOf('#') - 2;
            address = address.substring(0, stringCutIndex);
        }
        return address;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DistanceCommand // instanceof handles nulls
                && this.targetIndexDestination.equals(((DistanceCommand) other).targetIndexDestination)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
        int numberOfPersonsListed = model.getFilteredPersonList().size();

        //no person matches date
        if (numberOfPersonsListed == 0) {
            EventsCenter.getInstance().post(new ShowDefaultPageEvent());
            return new CommandResult(getMessageForPersonListShownSummary(numberOfPersonsListed));
        }

        //all addresses cannot be found
        if (optimizedRoute.size() == 0) {
            EventsCenter.getInstance().post(new ShowDefaultPageEvent());
            String shown = getMessageForPersonListShownSummary(numberOfPersonsListed)
                    + "\nAll the addresses on "
                    + model.getFilteredPersonList().get(0).getDate().toString()
                    + " cannot be found.";
            return new CommandResult(shown);
        }

        //some addresses are invalid
        if (optimizedRoute.size() < numberOfPersonsListed) {
            String shown = getMessageForPersonListShownSummary(numberOfPersonsListed)
                    + "\nAt least one address on "
                    + model.getFilteredPersonList().get(0).getDate().toString()
                    + " cannot be found.";
            EventsCenter.getInstance().post(new ShowMultiLocationFromHeadQuarterEvent(optimizedRoute));
            return new CommandResult(shown);
        }

        //all addresses can be found
        EventsCenter.getInstance().post(new ShowMultiLocationFromHeadQuarterEvent(optimizedRoute));
        return new CommandResult(getMessageForPersonListShownSummary(numberOfPersonsListed));
    }

```
###### \java\seedu\address\logic\GetDistance.java
``` java
    public DistanceMatrix getMatrix(String origin, String destination) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBWyCJkCym1dSouzHX_FxLk6Tj11C7F0Ao")
                .build();

        String[] origins = {origin};
        String[] destinations = {destination};

        DistanceMatrix matrix = null;
        try {
            matrix = DistanceMatrixApi.getDistanceMatrix(context, origins, destinations).await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    /**
     * get driving distance from origin to destination
     */
    public double getDistance(String origin, String destination) {
        if (origin.equals(destination)) {
            return 0;
        }
        String distanceWithoutUnit = "";
        DistanceMatrix matrix = null;
        matrix = getMatrix(origin, destination);
        String distance;

        try {
            distance = matrix.rows[0].elements[0].distance.toString();
        } catch (NullPointerException e) {
            return -1.0;
        }
```
###### \java\seedu\address\logic\parser\DistanceCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class DistanceCommandParser implements Parser<DistanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DistanceCommand
     * and returns an DistanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DistanceCommand parse(String args) throws ParseException {
        String[] selectedIndexes = args.split(" ");
        try {
            if (selectedIndexes.length == 3) {
                Index firstIndex = ParserUtil.parseIndex(selectedIndexes[1].trim());
                Index secondIndex = ParserUtil.parseIndex(selectedIndexes[2].trim());
                return new DistanceCommand(firstIndex, secondIndex);
            } else {
                Index index = ParserUtil.parseIndex(args);
                return new DistanceCommand(index);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DistanceCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\RouteOptimization.java
``` java
    /**
     * Check whether the address can be found by Google Map API or not
     * @param address
     * @return
     */
    private boolean isFindableAddress(Address address) {
        String addressUnderCheck = address.toString();
        GetDistance distance = new GetDistance();
        if (distance.getDistance(HQ_ADDRESS, addressUnderCheck) == -1.0) {
            return false;
        }
        return true;
    }

```
###### \java\seedu\address\MainApp.java
``` java
    /**
     * read welcome "username" message
     */
    private void readWelcomeMessage() {
        try {
            readWelcomeScriptForMac();
        } catch (IOException notMac){
            try {
                createFolderIfNeeded();
                createScriptIfNeeded();
                readWelcomeScript();
            } catch (IOException e) {
                logger.warning("Unable to read Welcome script");
            }
        }
    }

    /**
     * Read welcome script for Mac
     */
    private void readWelcomeScriptForMac() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String[] argument = { "osascript", "-e", "say \"Welcome user\" using \"Alex\" "
                + "speaking rate 180 pitch 42 modulation 60" };

        Process process = runtime.exec(argument);
        logger.info("Running welcome script on Mac");
    }

    /**
     * Read welcome script for Window
     */
    private void readWelcomeScript() throws IOException {
        Runtime.getRuntime().exec("wscript.exe script\\Welcome.vbs");
        logger.info("Running welcome script on Window");
    }

    /**
     * create script file if not exist
     */
    private void createScriptIfNeeded() throws IOException {
        File f = new File("script\\Welcome.vbs");
        if (!f.exists()) {
            File file1 = new File("script\\Welcome.txt");
            logger.info("Creating script Welcome.txt");
            file1.createNewFile();
            logger.info("Writing to Welcome.txt");
            PrintWriter writer = new PrintWriter("script\\Welcome.txt", "UTF-8");
            writer.println("CreateObject(\"sapi.spvoice\").Speak \"Welcome back user\"");
            writer.close();
            logger.info("Converting Welcome.txt to Welcome.vbs");
            File file2 = new File("script\\Welcome.vbs");
            file1.renameTo(file2);
        }
    }

    /**
     * create script folder if not exist
     */
    private void createFolderIfNeeded() {
        File dir = new File("script");
        if (!dir.exists()) {
            logger.info("Creating script directory");
            boolean successful = dir.mkdirs();
        }
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Show direction from Kent Ridge MRT to the person address
     */
    private void loadPersonDirection(Person person) {
        String addressValue = person.getAddress().value.trim();
        int stringCutIndex;
        String addressWithoutUnit;

        if (addressValue.indexOf('#') > 2) {
            stringCutIndex = addressValue.indexOf('#') - 2;
            addressWithoutUnit = addressValue.substring(0, stringCutIndex);
        } else {
            addressWithoutUnit = addressValue;
        }

        readPersonName(person);
        loadPage(SEARCH_PAGE_URL + addressWithoutUnit.replaceAll(" ", "%20") + "?dg=dbrw&newdg=1");
    }

    /**
     * Run script that read person name
     * @param person
     */
    private void readPersonName(Person person) {
        try {
            readPersonNameScriptForMac(person);
        } catch (IOException notMac) {
            try {
                createFolderIfNeeded();
                createScriptIfNeeded();
                readPersonNameScript(person);
            } catch (IOException e) {
                logger.warning("Unable to read person name script");
            }
        }
    }

    /**
     * Read script for Mac
     */
    private void readPersonNameScriptForMac(Person person) throws IOException {
        String personName = person.getName().toString();
        String script = "say \"" + personName + "\" using \"Alex\" speaking rate 150 pitch 42 modulation 60";

        Runtime runtime = Runtime.getRuntime();
        String[] argument = { "osascript", "-e", script };

        Process process = runtime.exec(argument);
        logger.info("Running read person name script on Mac");
    }

    /**
     * Read script for Window
     */
    private void readPersonNameScript(Person person) throws IOException {
        logger.info("Running read person name script on Window");
        Runtime.getRuntime().exec("wscript.exe script\\ReadPersonName.vbs"
                + " " + person.getName().fullName);
    }

    /**
     * create script file if not exist
     */
    private void createScriptIfNeeded() throws IOException {
        File f = new File("script\\ReadPersonName.vbs");
        if (!f.exists()) {
            File file1 = new File("script\\ReadPersonName.txt");
            logger.info("Creating script ReadPersonName.txt");
            file1.createNewFile();
            logger.info("Writing to ReadPersonName.txt");
            PrintWriter writer = new PrintWriter("script\\ReadPersonName.txt", "UTF-8");
            writer.println("name = WScript.Arguments(0)");
            writer.println("speaks=\"This is \" + name");
            writer.println("CreateObject(\"sapi.spvoice\").Speak speaks");
            writer.close();
            logger.info("Converting ReadPersonName.txt to ReadPersonName.vbs");
            File file2 = new File("script\\ReadPersonName.vbs");
            file1.renameTo(file2);
        }
    }

    /**
     * create script folder if not exist
     */
    private void createFolderIfNeeded() {
        File dir = new File("script");
        if (!dir.exists()) {
            logger.info("Creating script directory");
            boolean successful = dir.mkdirs();
        }
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    public void handleShowMultiLocationEvent(ShowMultiLocationFromHeadQuarterEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        StringBuilder url = new StringBuilder(SEARCH_PAGE_URL);
        for (String address: event.sortedList) {
            url.append(address);
            url.append("/");
        }

        List<String> temp = event.sortedList;
        temp.add(0, HQ_ADDRESS);
        additionalInfo.setText("Estimated Required Time for Deliveries: "
                + FilterCommand.getDuration(event.sortedList));
        loadPage(url.toString().replaceAll(" ", "%20"));
    }

    @Subscribe
    public void handleShowDefaultPageEvent(ShowDefaultPageEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDefaultPage();
    }

    @Subscribe
    public void handleShowFromHeadQuaterToOneEvent(ShowRouteFromHeadQuarterToOneEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        StringBuilder url = new StringBuilder(SEARCH_PAGE_URL);
        url.append(event.destination);
        loadPage(url.toString().replaceAll(" ", "%20") + "?dg=dbrw&newdg=1");
    }

    @Subscribe
    public void handleShowFromOneToAnotherEvent(ShowRouteFromOneToAnotherEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        StringBuilder url = new StringBuilder("https://www.google.com.sg/maps/dir/");
        for (String address: event.sortedList) {
            url.append(address);
            url.append("/");
        }
        url.deleteCharAt(url.length() - 1);
        additionalInfo.setText("Estimated Required Time for Deliveries: "
                + FilterCommand.getDuration(event.sortedList));
        loadPage(url.toString().replaceAll(" ", "%20") + "?dg=dbrw&newdg=1");
    }

    @Subscribe
    public void handleGameEvent(GameEvent event) {

        URL gamePath = MainApp.class.getResource("/unused/games/Snake.html");
        loadPage(gamePath.toExternalForm());
        additionalInfo.setText("+ Additional information will be displayed here.");
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Set the label for the tag
     * @param p
     */
    private void labelTag(Person p) {

        p.getTags().forEach(tag -> {
            Label showLabel = new Label(tag.tagName);
            showLabel.getStyleClass().add(fromTagNameToColor(tag.tagName));
            //Zoom effect on the label when the mouse is on the label
            labelZoomEffect(showLabel);
            tags.getChildren().add(showLabel);
        });
    }

    /**
     * Zoom effect on the label when the mouse is on the label
     * @param label
     */
    private void labelZoomEffect(Label label) {
        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setScaleX(1.5);
                label.setScaleY(1.5);
            }
        });

        label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setScaleX(1);
                label.setScaleY(1);
            }
        });
    }

    /**
     *
     * @param tagName
     * @return the color for the label.
     */
    private String fromTagNameToColor(String tagName) {
        // use hashCode to hash the tagName into an integer
        // this help keeping the label colors consistency through every run
        return TAG_COLOR[Math.abs(tagName.toLowerCase().hashCode()) % TAG_COLOR.length];
    }
```
###### \resources\view\DarkTheme.css
``` css
.context-menu {
    -fx-background-color: white;
    -fx-background-insets: 0, 1, 2;
    -fx-background-radius: 0 0 0 0, 0 0 0 0, 0 0 0 0;
    -fx-padding: 0.5em 0em 0.5em 0em; /* 6 0 6 0 */

    -fx-border-color: #212121;
    -fx-border-style: solid;
}

.context-menu .separator {
    -fx-padding: 0.58333275em 1.333332em 0.58333275em 1.333332em;  /*7 16 7 16 */
}


.menu-item {
    -fx-background-color: transparent;
    -fx-padding: 0.666666em 1.333332em 0.666666em 1.333332em;   /*8 16 8 16 */
}

.menu-item .label {
    -fx-padding: 0em 1.333332em 0em 0em;
    -fx-text-fill: #212121;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
}

.menu-item:focused {
     -fx-background: -fx-accent;
     -fx-background-color: #dedede;
}

.menu-item:pressed
{
    -fx-background-color: #212121;
    -fx-text-fill: white;
}

.menu-item:pressed .label
{
    -fx-text-fill: white;
}

```
