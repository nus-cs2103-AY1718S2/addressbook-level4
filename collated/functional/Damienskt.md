# Damienskt
###### \java\seedu\address\logic\commands\calendar\ViewAppointmentCommand.java
``` java
/**
 * Display the appointment details in the display panel
 * based on the index given
 */
public class ViewAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "viewAppointment";
    public static final String COMMAND_ALIAS = "va";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Show the appointment details based on index "
            + "Parameters: "
            + "Index (Based on most recent appointment list generated). \n"
            + "Example: " + COMMAND_WORD + " "
            + "1";

    public static final String MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS = "List of appointments must be shown "
            + "before viewing an appointment";
    public static final String MESSAGE_SUCCESS = "Selected appointment details:\n";

    public static final String MESSAGE_NO_LOCATION = "No location for selected appointment!";

    private static Appointment selectedAppointment;
    private int chosenIndex;

    /**
     * Takes in a zero-based index {@code index}
     */
    public ViewAppointmentCommand (int index) {
        chosenIndex = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!model.getIsListingAppointments()) {
            throw new CommandException(MESSAGE_MUST_SHOW_LIST_OF_APPOINTMENTS);
        }
        try {
            selectedAppointment = model.getChosenAppointment(chosenIndex);
        } catch (IndexOutOfBoundsException iobe) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        try {
            ShowLocationCommand showLocation = new ShowLocationCommand(
                    new MapAddress(selectedAppointment.getLocation()));
            showLocation.execute();
            return new CommandResult(MESSAGE_SUCCESS + getAppointmentDetailsResult());
        } catch (NullPointerException npe) {
            return new CommandResult(MESSAGE_SUCCESS + getAppointmentDetailsResult());
        }
    }

    public static String getAppointmentDetailsResult () {
        String displayedLocation = (selectedAppointment.getLocation() == null)
                ? MESSAGE_NO_LOCATION
                : selectedAppointment.getLocation();
        return "Appointment Name: " + selectedAppointment.getTitle() + "\n"
                + "Start Date: " + selectedAppointment.getStartDate() + "\n"
                + "Start Time: " + selectedAppointment.getStartTime() + "\n"
                + "End Date: " + selectedAppointment.getEndDate() + "\n"
                + "End Time: " + selectedAppointment.getEndTime() + "\n"
                + "Location: " + displayedLocation + "\n"
                + "Celebrities attending: " + selectedAppointment.getCelebritiesAttending() + "\n"
                + "Points of Contact: " + selectedAppointment.getPointsOfContact();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewAppointmentCommand
                && this.chosenIndex == (((ViewAppointmentCommand) other).chosenIndex));
    }
}
```
###### \java\seedu\address\logic\commands\map\EstimateRouteCommand.java
``` java
/**
 * Estimates the distance and travel time required between two location
 */
public class EstimateRouteCommand extends Command implements DirectionsServiceCallback {

    public static final String COMMAND_WORD = "estimateRoute";
    public static final String COMMAND_ALIAS = "er";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the estimated route in the Map and the distance and time required.\n"
            + "Parameters: "
            + PREFIX_START_MAP_ADDRESS + "START LOCATION (Name of location or postal code) "
            + PREFIX_END_MAP_ADDRESS + "END LOCATION (Name of location or postal code)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_MAP_ADDRESS + "Punggol Central "
            + PREFIX_END_MAP_ADDRESS + "NUS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_START_MAP_ADDRESS + "820296 "
            + PREFIX_END_MAP_ADDRESS + "118420";

    public static final String MESSAGE_SUCCESS = "Route is being shown in Map, with start point at marker A"
            + " and end point at marker B!\n"
            + "Mode of transport: DRIVING\n";

    private static DirectionsRequest directionRequest;
    private static MapAddress startLocation = null;
    private static MapAddress endLocation = null;
    private static String distOfTravel;
    private static String timeOfTravel;

    private final LatLng endLatLng;
    private final LatLng startLatLng;

    private DirectionsService directionService;

    /**
     * Initialises the different class attributes of EstimateRouteCommand
     * @param start
     * @param end
     */
    public EstimateRouteCommand(MapAddress start, MapAddress end) {
        requireNonNull(start);
        requireNonNull(end);
        this.startLocation = start;
        this.endLocation = end;
        this.startLatLng = getLatLong(startLocation);
        this.endLatLng = getLatLong(endLocation);
        setDistAndTimeOfTravel();
    }

    @Override
    public CommandResult execute() {
        directionService = Map.getDirectionService();
        Map.removeExistingMarker();
        Map.clearRoute();
        addRouteToMap();
        return new CommandResult(MESSAGE_SUCCESS + getStringOfDistanceAndTime());
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        status.equals(DirectionStatus.OK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EstimateRouteCommand // instanceof handles nulls
                && this.startLatLng.toString().equals(((EstimateRouteCommand) other).startLatLng.toString())
                && this.endLatLng.toString().equals(((EstimateRouteCommand) other).endLatLng.toString()));
    }

    /**
     * Update {@code MapPanel} to show new route
     */
    private void addRouteToMap() {
        directionRequest = Map.getDirectionRequest();
        directionRequest = new DirectionsRequest(
                startLatLng.toString(),
                endLatLng.toString(),
                TravelModes.DRIVING);
        directionService.getRoute(directionRequest, this, Map.getDirectionRenderer());
    }

    /**
     * Calculates {@code distOfTravel} and {@code timeOfTravel}
     */
    private void setDistAndTimeOfTravel() {
        DistanceEstimate distEstimate = new DistanceEstimate();
        distEstimate.calculateDistanceMatrix(startLatLng, endLatLng, TravelMode.DRIVING);
        distOfTravel = distEstimate.getDistBetweenOriginDest();
        timeOfTravel = distEstimate.getTravelTime();
    }

    /**
     * Converts {@code address} into LatLng form
     * @return LatLng of address
     */
    private LatLng getLatLong(MapAddress address) {
        Geocoding convertToLatLng = new Geocoding();
        convertToLatLng.initialiseLatLngFromAddress(address.toString());
        return convertToLatLng.getLatLng();
    }

    /**
     * Retrieves information of {@code startLocation}, {@code endLocation}, {@code distOfTravel} and
     * {@code timeOfTravel}
     * which is then converted to string format to be shown to user
     * @return information of distance and time of travel
     */
    public static String getStringOfDistanceAndTime() {
        return "Start Location: " + startLocation.toString() + "\n"
                + "End Location: " + endLocation.toString() + "\n"
                + "Estimated Distance of travel: " + distOfTravel + "\n"
                + "Estimated Time of travel: " + timeOfTravel;
    }

    public static MapAddress getStartLocation() {
        return startLocation;
    }

    public static MapAddress getEndLocation() {
        return endLocation;
    }

    public static String getDistOfTravel() {
        return distOfTravel;
    }

    public static String getTimeOfTravel() {
        return timeOfTravel;
    }
}
```
###### \java\seedu\address\logic\commands\map\ShowLocationCommand.java
``` java
/**
 * Update the Map by adding a marker to the location of map selectedLocation
 * and delete existing marker if it exist
 */
public class ShowLocationCommand extends Command {

    public static final String COMMAND_WORD = "showLocation";
    public static final String COMMAND_ALIAS = "sl";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the location of selectedLocation in the Map.\n"
            + "Parameters: "
            + PREFIX_MAP_ADDRESS + "LOCATION (Name of location or postal code)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MAP_ADDRESS + "Punggol Central\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MAP_ADDRESS + "820296";

    public static final String MESSAGE_SUCCESS = "Location is being shown in Map (identified by marker)!";

    private MapAddress selectedLocation;

    /**
     * Creates an AddAppointmentCommand with the following parameters
     * @param address The created appointment
     */
    public ShowLocationCommand (MapAddress address) {
        requireNonNull(address);
        this.selectedLocation = address;
    }

    @Override
    public CommandResult execute() {
        Map.removeExistingMarker();
        Map.clearRoute();
        addNewMarkerToMap();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowLocationCommand // instanceof handles nulls
                && this.selectedLocation.equals(((ShowLocationCommand) other).selectedLocation));
    }

    /**
     * Remove any existing marker and add new marker {@code location} to Map
     */
    public void addNewMarkerToMap() {

        LatLong center = getLatLong();

        Map.setLocation(getMarkerOptions(center));

        Map.setMarkerOnMap(center);
    }

    /**
     * Initialise new marker with selected location {@code center} options
     * @return Marker with initialised options
     */
    private Marker getMarkerOptions(LatLong center) {
        MarkerOptions markOptions = new MarkerOptions();
        markOptions.animation(Animation.DROP)
                .position(center)
                .visible(true);
        return new Marker(markOptions);
    }

    /**
     * Converts selected location {@code selectedLocation} to LatLng form
     * @return LatLong
     */
    private LatLong getLatLong() {
        Geocoding convertToLatLng = new Geocoding();
        convertToLatLng.initialiseLatLngFromAddress(selectedLocation.toString());
        return new LatLong(convertToLatLng.getLat(), convertToLatLng.getLong());
    }

    public MapAddress getLocation() {
        return this.selectedLocation;
    }
}
```
###### \java\seedu\address\logic\map\DistanceEstimate.java
``` java
/**
 * Calculates distance and travel duration between two location.
 */
public class DistanceEstimate extends GoogleWebServices {

    private String distBetweenOriginDest;
    private String travelTime;

    /**
     * Extract time duration details from {@code matrixDetails}
     * @return time needed to travel between two location
     */
    private static Duration extractDurationDetailsToString(DistanceMatrix matrixDetails) {
        DistanceMatrixRow[] dataRow = matrixDetails.rows;
        DistanceMatrixElement[] dataElements = dataRow[0].elements;

        return dataElements[0].duration;
    }

    /**
     * Extract travel distance details from {@code matrixDetails}
     * @return distance between two location
     */
    private static Distance extractDistanceDetailsToString(DistanceMatrix matrixDetails) {
        DistanceMatrixRow[] dataRow = matrixDetails.rows;
        DistanceMatrixElement[] dataElements = dataRow[0].elements;

        return dataElements[0].distance;
    }

    /**
     * Request new approval for each access
     * @return successful approval from google server
     */
    private static DistanceMatrixApiRequest getApprovalForRequest(GeoApiContext context) {
        return DistanceMatrixApi.newRequest(context);
    }

    /**
     * Initialises the calculation of time and distance between two location by sending request with
     * {@code startPostalCode},{@code endPostalCode} and {@code modeOfTravel} to google server, details
     * extracted from result {@code estimate} and stored into {@code distBetweenOriginDest} and {@code travelTime}
     */
    public void calculateDistanceMatrix(LatLng startLocation, LatLng endLocation, TravelMode modeOfTravel) {
        DistanceMatrixApiRequest request = getApprovalForRequest(GoogleWebServices.getGeoApiContext());
        DistanceMatrix estimate = null;
        try {
            estimate = request.origins(startLocation)
                    .destinations(endLocation)
                    .mode(modeOfTravel)
                    .language("en-EN")
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        distBetweenOriginDest = String.valueOf(extractDistanceDetailsToString(estimate));
        travelTime = String.valueOf(extractDurationDetailsToString(estimate));
    }

    public String getTravelTime() {
        return travelTime;
    }

    public String getDistBetweenOriginDest() {
        return distBetweenOriginDest;
    }
}
```
###### \java\seedu\address\logic\map\Geocoding.java
``` java
/**
 * Converts address to LatLng form.
 */
public class Geocoding extends GoogleWebServices {

    private static LatLng location;

    /**
     * Send request to google server to obtain {@code results}
     * Where that {@code location} is extracted in LatLng form.
     * @param address
     */
    public void initialiseLatLngFromAddress(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(GoogleWebServices.getGeoApiContext(),
                    address).await();
            getLocation(results);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getLocation(GeocodingResult[] results) {
        try {
            location = results[0].geometry.location;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        }
    }

    public double getLat() {
        return location.lat;
    }

    public double getLong() {
        return location.lng;
    }

    public LatLng getLatLng() {
        return location;
    }

    /**
     * Checks if the {@code address} can
     * be found in google server
     * @param address
     * @return boolean
     */
    public boolean checkIfAddressCanBeFound(String address) {

        try {
            GeocodingResult[] results = GeocodingApi.geocode(GoogleWebServices.getGeoApiContext(),
                    address).await();
            getLocation(results);
        } catch (ApiException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }
}
```
###### \java\seedu\address\logic\map\GoogleWebServices.java
``` java
/**
 * Initialise connection to google server to use its API
 */
public class GoogleWebServices {

    // 6 API keys for accessing google server
    public static final String API_KEY_1 = "AIzaSyB78sjE5UtuPn9T7u1WHowRkWj2KFo92rI";
    public static final String API_KEY_2 = "AIzaSyAplrsZatzM_d2ynML097uqXd1-usgscOA";
    public static final String API_KEY_3 = "AIzaSyDdJMB6Jug8D_45K72FpbEL8S5XQF_98Oc";
    public static final String API_KEY_4 = "AIzaSyA-gBgtvaQU4NMEmO37UJEyx5YHnuFU30E";
    public static final String API_KEY_5 = "AIzaSyAD8_oIBJlzOp30VA9mOvQKp6GZe8SFsYY";
    public static final String API_KEY_6 = "AIzaSyBKemZo4WFMDaJ1q_vWxquZxTyYF24skCI";

    public static final String MESSAGE_FAIL_CONNECTION = "Api key reached max daily usage, "
            + "please wait till 3pm SGT for it to be reset";

    private static GeoApiContext context;
    private boolean isInitialised;

    /**
     * Initialises access to google server using Api keys
     */
    public GoogleWebServices() {
        initialiseConnection();
    }

    /**
     * Initialise with valid Api key and test connection to google server
     */
    private void initialiseConnection() {
        isInitialised = true;
        context = new GeoApiContext.Builder()
                .apiKey(API_KEY_2)
                .build();
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    "Punggol").await();
            LatLng location = results[0].geometry.location;
        } catch (ApiException | InterruptedException | IOException | IndexOutOfBoundsException e) {
            isInitialised = false;
        }
    }

    public boolean checkInitialisedConnection() {
        return isInitialised;
    }

    public static GeoApiContext getGeoApiContext() {
        return context;
    }
}
```
###### \java\seedu\address\logic\parser\calendar\ViewAppointmentCommandParser.java
``` java
/**
 * Reads {@code args} and checks if the input has all the necessary values
 */
public class ViewAppointmentCommandParser implements Parser<ViewAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAppointmentCommand
     * and returns an ViewAppointmentCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ViewAppointmentCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewAppointmentCommand(index.getZeroBased());
        } catch (IllegalValueException ive) {
            Map.removeExistingMarker();
            Map.clearRoute();
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
```
###### \java\seedu\address\logic\parser\map\EstimateRouteCommandParser.java
``` java
/**
 * Reads {@code args} and checks if the input has all the necessary values
 */
public class EstimateRouteCommandParser implements Parser<EstimateRouteCommand> {

    private GoogleWebServices initialiseConnection;
    /**
     * Parses the given {@code String} of arguments in the context of the EstimateRouteCommand
     * and returns an EstimateRouteCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public EstimateRouteCommand parse(String args) throws ParseException {

        initialiseConnection = new GoogleWebServices();

        if (!initialiseConnection.checkInitialisedConnection()) {
            throw new ParseException(GoogleWebServices.MESSAGE_FAIL_CONNECTION);
        }

        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_MAP_ADDRESS, PREFIX_END_MAP_ADDRESS);
        if (!arePrefixesPresent(argMultiMap, PREFIX_START_MAP_ADDRESS, PREFIX_END_MAP_ADDRESS)
                || !argMultiMap.getPreamble().isEmpty()) {
            Map.removeExistingMarker();
            Map.clearRoute();
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EstimateRouteCommand.MESSAGE_USAGE));
        }

        try {
            MapAddress start = ParserUtil.parseMapAddress(argMultiMap.getValue(PREFIX_START_MAP_ADDRESS)).get();
            MapAddress end = ParserUtil.parseMapAddress(argMultiMap.getValue(PREFIX_END_MAP_ADDRESS)).get();
            if (!MapAddress.isValidAddressForEstimatingRoute(start.toString(), end.toString())) {
                Map.removeExistingMarker();
                Map.clearRoute();
                throw new InvalidAddress("");
            }
            return new EstimateRouteCommand(start, end);
        } catch (IllegalValueException ive) {
            Map.removeExistingMarker();
            Map.clearRoute();
            throw new ParseException(MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);
        } catch (InvalidAddress ia) {
            Map.removeExistingMarker();
            Map.clearRoute();
            throw new ParseException(MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS_ROUTE_ESTIMATION);
        }
    }
}
```
###### \java\seedu\address\logic\parser\map\ShowLocationCommandParser.java
``` java
/**
 * Reads {@code args} and checks if the input has all the necessary values
 */
public class ShowLocationCommandParser implements Parser<ShowLocationCommand> {
    private GoogleWebServices initialiseConnection;
    /**
     * Parses the given {@code String} of arguments in the context of the ShowLocationCommand
     * and returns an ShowLocationCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ShowLocationCommand parse(String args) throws ParseException {
        initialiseConnection = new GoogleWebServices();
        if (!initialiseConnection.checkInitialisedConnection()) {
            throw new ParseException(GoogleWebServices.MESSAGE_FAIL_CONNECTION);
        }
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_MAP_ADDRESS);
        if (!arePrefixesPresent(argMultiMap, PREFIX_MAP_ADDRESS)
                || !argMultiMap.getPreamble().isEmpty()) {
            Map.removeExistingMarker();
            Map.clearRoute();
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowLocationCommand.MESSAGE_USAGE));
        }

        try {
            MapAddress address = ParserUtil.parseMapAddress(argMultiMap.getValue(PREFIX_MAP_ADDRESS)).get();
            return new ShowLocationCommand(address);
        } catch (IllegalValueException ive) {
            Map.removeExistingMarker();
            Map.clearRoute();
            throw new ParseException(MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static MapAddress parseMapAddress(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!MapAddress.isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(MapAddress.MESSAGE_ADDRESS_MAP_CONSTRAINTS);
        }
        return new MapAddress(trimmedAddress);
    }
    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<MapAddress> parseMapAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(parseMapAddress(address.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\appointment\Appointment.java
``` java
    /**
     * Returns list of celebrities attending the appointment in string format.
     */
    public String getCelebritiesAttending () {
        if (celebrityList.size() == 0) {
            return "No celebrities attending";
        }
        String celebritiesAttending = new String(celebrityList.get(0).getName().toString());
        for (int i = 1; i < celebrityList.size(); i++) {
            celebritiesAttending = celebritiesAttending + ", " + celebrityList.get(i).getName().toString();
        }
        return celebritiesAttending;
    }

    /**
     * Returns list of points of contact for the appointment in string format.
     */
    public String getPointsOfContact () {
        if (pointOfContactList.size() == 0) {
            return "No point of contact";
        }
        String pointOfContacts = new String(pointOfContactList.get(0).getName().toString());
        for (int i = 1; i < pointOfContactList.size(); i++) {
            pointOfContacts = pointOfContacts + ", " + pointOfContactList.get(i).getName().toString();
        }
        return pointOfContacts;
    }
}
```
###### \java\seedu\address\model\map\Map.java
``` java
/**
 * Map model which allows updating of map state
 */
public class Map extends MapPanel {

    private static Marker location;

    public static GoogleMap getMap() {
        return actualMap;
    }

    public static DirectionsRequest getDirectionRequest() {
        return directionRequest;
    }

    public static DirectionsService getDirectionService() {
        return directionService;
    }

    public static DirectionsRenderer getDirectionRenderer() {
        if (renderer == null) {
            renderer = new DirectionsRenderer(true, actualMap, directions);
        }
        return renderer;
    }

    /**
     * Clear any existing route in Map by clearing {@code renderer}
     */
    public static void clearRoute() {
        if (renderer != null) {
            renderer.clearDirections();
            renderer = new DirectionsRenderer(true, actualMap, directions);
        }
    }

    /**
     * Remove any existing marker {@code location} to Map
     */
    public static void removeExistingMarker() {
        if (location != null) {
            actualMap.removeMarker(location);
        }
    }

    /**
     * Set new marker with {@code center} LatLong on map
     * @param center
     */
    public static void setMarkerOnMap(LatLong center) {
        actualMap.addMarker(Map.location);
        actualMap.setCenter(center);
        actualMap.setZoom(15);
    }

    public static void setLocation(Marker location) {
        Map.location = location;
    }
}
```
###### \java\seedu\address\model\map\MapAddress.java
``` java
/**
 * Represents a map address in the CelebManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class MapAddress {
    public static final String MESSAGE_ADDRESS_MAP_CONSTRAINTS =
            "Address should be in location name, road name, block and road name or postal code format.\n"
                    + "Note:(Person address may not be valid as it consist of too many details like unit number)";
    public static final String MESSAGE_ADDRESS_MAP_CONSTRAINTS_ROUTE_ESTIMATION =
            "Both address cannot be reached by Driving!\n"
            + "Tips: \n"
            + "1.Be more specific with location name.\n"
            + "2.Insert Country name in front of location name (e.g Botanic Gardens -> Singapore Botanic Gardens).\n"
            + "3.Use Postal Code instead.";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_MAP_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid MapAddress.
     */
    public MapAddress(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_ADDRESS_MAP_CONSTRAINTS);
        this.value = address;
    }

    /**
     * Returns true if a given string is a valid map address.
     */
    public static boolean isValidAddress(String test) {
        boolean isValid;
        Geocoding testAddress = new Geocoding();
        isValid = testAddress.checkIfAddressCanBeFound(test);
        return test.matches(ADDRESS_MAP_VALIDATION_REGEX) && isValid;
    }
    /**
     * Returns true if a given string is a valid map address.
     */
    public static boolean isValidAddressForEstimatingRoute(String start, String end) {
        boolean isValid = true;
        LatLng startLatLng;
        LatLng endLatLng;

        DistanceEstimate checkValid = new DistanceEstimate();
        Geocoding latLong = new Geocoding();

        latLong.initialiseLatLngFromAddress(start);
        startLatLng = latLong.getLatLng(); //Get LatLong of start location

        latLong.initialiseLatLngFromAddress(end);
        endLatLng = latLong.getLatLng(); //Get LatLong of end location

        checkValid.calculateDistanceMatrix(startLatLng, endLatLng, TravelMode.DRIVING);

        if (checkValid.getTravelTime().equals("null")) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return (other == this) // short circuit if same object
                || (other instanceof MapAddress // instanceof handles nulls
                && this.value.equals(((MapAddress) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\ui\MapPanel.java
``` java
/**
 * The panel for google maps. Construct the maps view which is return by calling
 * getMapView() to MainWindow which attaches it to mapPanelPlaceHolder. After which it initialises the Map contents
 * mapInitialised()
 */
public class MapPanel extends UiPart<Region> implements MapComponentInitializedListener {

    public static final double LATITUDE_SG = 1.3607962;
    public static final double LONGITUDE_SG = 103.8109208;
    public static final int DEFAULT_ZOOM_LEVEL = 10;

    protected static DirectionsPane directions;
    protected static DirectionsRenderer renderer;
    protected static DirectionsService directionService;
    protected static DirectionsRequest directionRequest;
    protected static GoogleMap actualMap;

    private static final String FXML = "MapsPanel.fxml";

    protected GoogleMapView mapView;

    public MapPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        mapView = new GoogleMapView();
        mapView.setDisableDoubleClick(true);
        mapView.addMapInializedListener(this);
        mapView.getWebview().getEngine().setOnAlert((WebEvent<String> event) -> {
        });
    }

    @Override
    public void mapInitialized() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> mapView.getMap().hideDirectionsPane());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        t.start();
        directionService = new DirectionsService();
        actualMap = setMapOptions();
        directions = mapView.getDirec();
    }

    /**
     * Set the Map options for initialisation of {@code actualMap}
     */
    private GoogleMap setMapOptions() {
        LatLong center = new LatLong(LATITUDE_SG, LONGITUDE_SG);
        MapOptions options = new MapOptions();
        options.center(center)
                .zoom(DEFAULT_ZOOM_LEVEL)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.ROADMAP);
        return mapView.createMap(options);
    }

    public GoogleMapView getMapView() {
        return mapView;
    }
}
```
###### \resources\view\MainWindow.fxml
``` fxml
        <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4" VBox.vgrow="ALWAYS">
          <VBox fx:id="personList" minWidth="340" prefWidth="340" SplitPane.resizableWithParent="false">
            <padding>
              <Insets top="10" right="10" bottom="10" left="10" />
            </padding>
            <StackPane fx:id="personListPanelPlaceholder" prefHeight="270.0" VBox.vgrow="ALWAYS"
                         styleClass="pane-with-thick-border"/>
            <padding>
              <Insets top="10" right="10" bottom="10" left="10" />
            </padding>
            <StackPane fx:id="mapPanelPlaceholder" prefHeight="270.0" VBox.vgrow="ALWAYS"
                         styleClass="pane-with-thick-border">
              <padding>
                <Insets top="10" right="10" bottom="10" left="10" />
              </padding>
            </StackPane>
          </VBox>
```
###### \resources\view\MapsPanel.fxml
``` fxml
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
  <Pane fx:id="mapView" prefWidth="248.0" VBox.vgrow="ALWAYS" />
</VBox>
```
