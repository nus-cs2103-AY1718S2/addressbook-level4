# Wuhao-ooo
###### \java\seedu\address\commons\events\model\CustomerStatsChangedEvent.java
``` java
package seedu.address.commons.events.model;

import java.util.HashMap;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCustomerStats;

/** Indicates the CustomerStats in the model has changed*/
public class CustomerStatsChangedEvent extends BaseEvent {

    public final ReadOnlyCustomerStats data;

    public CustomerStatsChangedEvent(ReadOnlyCustomerStats data) {
        this.data = data;
    }

    @Override
    public String toString() {
        HashMap<String, Integer> ordersCount = data.getOrdersCount();
        return ordersCount.toString();
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
        try {
            customerStatsOptional = storage.readCustomerStats();
            if (!customerStatsOptional.isPresent()) {
                logger.info("Data file for customer stats not found. Will be starting with an empty file");
            }
            initialCustomerStats = new CustomerStats();
        } catch (DataConversionException e) {
            logger.warning("Data file for customer stats in wrong format. Will be starting with an empty file");
            initialCustomerStats = new CustomerStats();
        }  catch (IOException e) {
            logger.warning("Problem while reading from the customer stats file. Will be starting with an empty file");
            initialCustomerStats = new CustomerStats();
        }

        return new ModelManager(initialData, userPrefs, initialCustomerStats);
    }

```
###### \java\seedu\address\model\CustomerStats.java
``` java
package seedu.address.model;

import java.util.HashMap;


/**
 * Wraps all data at the customer-stats level
 */
public class CustomerStats implements ReadOnlyCustomerStats {

    private HashMap<String, Integer> ordersCount;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        ordersCount = new HashMap<>();
    }

    public CustomerStats() {}

    public void setOrdersCount(HashMap<String, Integer> ordersCountHashMap) {
        ordersCount = ordersCountHashMap;
    }

    /**
     * Update the count of a given phone number in the customers' stats.
     * If the phone does not exist in the key set, put it to the key set with corresponding value 1
     * Otherwise add 1 to the corresponding value
     */
    public void addCount(String phone) {
        if (!ordersCount.containsKey(phone)) {
            ordersCount.put(phone, 1);
        } else {
            ordersCount.put(phone, ordersCount.get(phone) + 1);
        }
    }


    @Override
    public HashMap<String, Integer> getOrdersCount() {
        return new HashMap<>(ordersCount);
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate customer stats has changed */
    private void indicateCustomerStatsChanged() {
        raise(new CustomerStatsChangedEvent(customerStats));
    }

```
###### \java\seedu\address\model\ReadOnlyCustomerStats.java
``` java
package seedu.address.model;

import java.util.HashMap;

/**
 * Unmodifiable view of customers statistics
 */
public interface ReadOnlyCustomerStats {

    /**
     * Returns a copy of the data in CustomerStats.
     * Modifications on this copy will not affect the original CustomerStats data
     */
    HashMap<String, Integer> getOrdersCount();
}
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getCustomerStatsFilePath() {
        return customerStatsFilePath;
    }

```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getCustomerStatsName() {
        return customerStatsName;
    }

```
###### \java\seedu\address\storage\CustomerStatsStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCustomerStats;

/**
 * Represents a storage for customers' orders count.
 */
public interface CustomerStatsStorage {

    /**
     * Returns the file path of the data file.
     */
    String getCustomerStatsFilePath();

    /**
     * Returns CustomerStats data as a {@link ReadOnlyCustomerStats}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCustomerStats> readCustomerStats() throws DataConversionException, IOException;

    /**
     * @see #getCustomerStatsFilePath()
     */
    Optional<ReadOnlyCustomerStats> readCustomerStats(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCustomerStats} to the storage.
     * @param customerStats cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCustomerStats(ReadOnlyCustomerStats customerStats) throws IOException;

    /**
     * @see #saveCustomerStats(ReadOnlyCustomerStats)
     */
    void saveCustomerStats(ReadOnlyCustomerStats customerStats, String filePath) throws IOException;

}
```
###### \java\seedu\address\storage\Storage.java
``` java
    @Override
    String getCustomerStatsFilePath();

```
###### \java\seedu\address\storage\Storage.java
``` java
    /**
     * Saves the current CustomerStats to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleCustomerStatsChangedEvent(CustomerStatsChangedEvent csce);
}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    // ================ CustomerStats methods ==============================

    @Override
    public String getCustomerStatsFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyCustomerStats> readCustomerStats() throws DataConversionException, IOException {
        return readCustomerStats(customerStatsStorage.getCustomerStatsFilePath());
    }

    @Override
    public Optional<ReadOnlyCustomerStats> readCustomerStats(String filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return customerStatsStorage.readCustomerStats(filePath);
    }

    @Override
    public void saveCustomerStats(ReadOnlyCustomerStats customerStats) throws IOException {
        saveCustomerStats(customerStats, customerStatsStorage.getCustomerStatsFilePath());
    }

    @Override
    public void saveCustomerStats(ReadOnlyCustomerStats customerStats, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        customerStatsStorage.saveCustomerStats(customerStats, filePath);
    }

    @Override
    @Subscribe
    public void handleCustomerStatsChangedEvent(CustomerStatsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveCustomerStats(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
```
###### \java\seedu\address\storage\XmlCustomerStatsStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyCustomerStats;

/**
 * A class to access CustomerStats data stored as an xml file on the hard disk.
 */
public class XmlCustomerStatsStorage implements CustomerStatsStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCustomerStatsStorage.class);

    private String filePath;

    public XmlCustomerStatsStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getCustomerStatsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCustomerStats> readCustomerStats() throws DataConversionException, IOException {
        return readCustomerStats(filePath);
    }

    /**
     * Similar to {@link #readCustomerStats()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCustomerStats> readCustomerStats(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File customerStatsFile = new File(filePath);

        if (!customerStatsFile.exists()) {
            logger.info("CustomerStats file "  + customerStatsFile + " not found");
            return Optional.empty();
        }

        XmlSerializableCustomerStats xmlCustomerStats = XmlFileStorage.loadCustomerDataFromSaveFile(new File(filePath));
        return Optional.of(xmlCustomerStats.toModelType());
    }

    @Override
    public void saveCustomerStats(ReadOnlyCustomerStats customerStats) throws IOException {
        saveCustomerStats(customerStats, filePath);
    }

    /**
     * Similar to {@link #saveCustomerStats(ReadOnlyCustomerStats)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveCustomerStats(ReadOnlyCustomerStats customerStats, String filePath) throws IOException {
        requireNonNull(customerStats);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveCustomerDataToFile(file, new XmlSerializableCustomerStats(customerStats));
    }
}
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    /**
     * Saves the given customer stats data to the specified file.
     */
    public static void saveCustomerDataToFile(File file, XmlSerializableCustomerStats customerStats)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, customerStats);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns customer stats in the file or an empty address book
     */
    public static XmlSerializableCustomerStats loadCustomerDataFromSaveFile(File file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCustomerStats.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
```
###### \java\seedu\address\storage\XmlSerializableCustomerStats.java
``` java
package seedu.address.storage;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CustomerStats;
import seedu.address.model.ReadOnlyCustomerStats;

/**
 * An Immutable CustomerStats that is serializable to XML format
 */
@XmlRootElement(name = "customerstats")
public class XmlSerializableCustomerStats {

    @XmlElement
    private HashMap<String, Integer> orderCount;

    /**
     * Creates an empty XmlSerializableCustomerStats.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCustomerStats() {
        orderCount = new HashMap<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCustomerStats(ReadOnlyCustomerStats src) {
        this();
        orderCount = src.getOrdersCount();
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public CustomerStats toModelType() {
        CustomerStats customerStats = new CustomerStats();
        customerStats.setOrdersCount(orderCount);
        return customerStats;
    }
}
```
###### \resources\view\LightTheme.css
``` css
 */
.background {
    -fx-background-color: derive(#FFC947, 20%);
    background-color: white; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #FFC947;
    -fx-control-inner-background: #FFC947;
    -fx-background-color: #FFC947;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#FFC947, 20%);
    -fx-border-color: transparent transparent transparent transparent;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#FFC947, 20%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#FFC947, 20%);
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #FFF2A3;
}

.list-cell:filled:odd {
    -fx-background-color: #FFFFDA;
}

.list-cell:filled:selected {
    -fx-background-color: #789CAE;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#FFC947, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#FFC947, 20%);
     -fx-border-color: derive(#FFC947, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#FFC947, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#FFC947, 30%);
    -fx-border-color: derive(#FFC947, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#FFC947, 30%);
    -fx-border-color: derive(#FFC947, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#FFC947, 30%);
}

.context-menu {
    -fx-background-color: derive(#FFC947, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#FFC947, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #FFC947;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: white;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #FFC947;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #FFC947;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #FFC947;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #FFC947;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: black;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#FFC947, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: black;
    -fx-text-fill: black;
}

.scroll-bar {
    -fx-background-color: derive(#FFC947, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#FFC947, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: white white white white;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, white, transparent, white;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: black;
    -fx-background-color: white;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
