# Kyholmes
###### \build\resources\main\view\AppointmentCard.fxml
``` fxml
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox alignment="TOP_CENTER" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="100.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane alignment="CENTER" maxHeight="-Infinity" maxWidth="1.7976931348623157E308" minHeight="-Infinity">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <VBox prefHeight="200.0" prefWidth="100.0">
               <children>
                  <HBox prefHeight="100.0" prefWidth="200.0">
                     <children>
                        <Label fx:id="id" minWidth="-Infinity" text="Label" />
                        <Label fx:id="dateTime" text="Label" />
                     </children></HBox>
               </children>
            </VBox>
         </children>
      </GridPane>
   </children>
</VBox>
```
###### \build\resources\main\view\CalendarPanel.fxml
``` fxml
<?import com.calendarfx.view.page.MonthPage?>
<?import javafx.scene.layout.StackPane?>

<StackPane maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <MonthPage fx:id="calendarView" enableHyperlinks="false" prefHeight="200.0" prefWidth="200.0" selectionMode="SINGLE" />
   </children>
</StackPane>
```
###### \build\resources\main\view\MainWindow.fxml
``` fxml
        <StackPane VBox.vgrow="NEVER" fx:id="queueList" styleClass="pane-with-border"
                   minHeight="50" prefHeight="100" maxHeight="60">
          <HBox minHeight="50" prefHeight="60">
            <padding>
              <Insets top="5" right="10" bottom="5" left="10" />
            </padding>
            <StackPane fx:id="queuePanelPlaceholder" HBox.hgrow="ALWAYS"/>
          </HBox>
        </StackPane>

```
###### \build\resources\main\view\PatientAppointmentPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<StackPane maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
        <items>
            <StackPane prefHeight="150.0" prefWidth="200.0">
               <children>
                  <VBox prefHeight="200.0" prefWidth="100.0">
                     <children>
                        <ListView fx:id="pastAppointmentCardListView" prefHeight="200.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
                           <VBox.margin>
                              <Insets right="5.0" />
                           </VBox.margin></ListView>
                     </children>
                     <StackPane.margin>
                        <Insets top="30.0" />
                     </StackPane.margin>
                  </VBox>
                  <Label text="Past Appointments" textFill="WHITE" textOverrun="CLIP" StackPane.alignment="TOP_LEFT">
                     <StackPane.margin>
                        <Insets left="5.0" top="5.0" />
                     </StackPane.margin></Label>
               </children>
            </StackPane>
            <StackPane prefHeight="150.0" prefWidth="200.0">
               <children>
                  <VBox prefHeight="200.0" prefWidth="100.0">
                     <children>
                        <ListView fx:id="upcomingAppointmentCardListView" prefHeight="200.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
                           <VBox.margin>
                              <Insets />
                           </VBox.margin>
                        </ListView>
                     </children>
                     <StackPane.margin>
                        <Insets top="30.0" />
                     </StackPane.margin>
                  </VBox>
                  <Label text="Upcoming Appointments" textFill="WHITE" StackPane.alignment="TOP_LEFT">
                     <StackPane.margin>
                        <Insets left="5.0" top="5.0" />
                     </StackPane.margin>
                  </Label>
               </children>
            </StackPane>
        </items>
      </SplitPane>
   </children>
</StackPane>
```
###### \build\resources\main\view\QueueCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.HBox?>

<HBox prefHeight="45.0" prefWidth="200.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Label fx:id="index" alignment="CENTER_RIGHT" text="\$index">
         <HBox.margin>
            <Insets right="5.0" />
         </HBox.margin></Label>
      <Label fx:id="name" text="\$name" />
   </children>
   <padding>
      <Insets left="5.0" right="5.0" top="5.0" />
   </padding>
</HBox>
```
###### \build\resources\main\view\QueueListPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.HBox?>

<HBox maxHeight="-Infinity" minHeight="-Infinity" prefHeight="45.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ListView fx:id="queueCardListView" orientation="HORIZONTAL" HBox.hgrow="ALWAYS" />
   </children>
</HBox>
```
###### \out\production\resources\view\AppointmentCard.fxml
``` fxml
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox alignment="TOP_CENTER" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="100.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane alignment="CENTER" maxHeight="-Infinity" maxWidth="1.7976931348623157E308" minHeight="-Infinity">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <VBox prefHeight="200.0" prefWidth="100.0">
               <children>
                  <HBox prefHeight="100.0" prefWidth="200.0">
                     <children>
                        <Label fx:id="id" minWidth="-Infinity" text="Label" />
                        <Label fx:id="dateTime" text="Label" />
                     </children></HBox>
               </children>
            </VBox>
         </children>
      </GridPane>
   </children>
</VBox>
```
###### \out\production\resources\view\CalendarPanel.fxml
``` fxml
<?import com.calendarfx.view.page.MonthPage?>
<?import javafx.scene.layout.StackPane?>

<StackPane maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <MonthPage fx:id="calendarView" enableHyperlinks="false" prefHeight="200.0" prefWidth="200.0" selectionMode="SINGLE" />
   </children>
</StackPane>
```
###### \out\production\resources\view\MainWindow.fxml
``` fxml
        <StackPane VBox.vgrow="NEVER" fx:id="queueList" styleClass="pane-with-border"
                   minHeight="50" prefHeight="100" maxHeight="60">
          <HBox minHeight="50" prefHeight="60">
            <padding>
              <Insets top="5" right="10" bottom="5" left="10" />
            </padding>
            <StackPane fx:id="queuePanelPlaceholder" HBox.hgrow="ALWAYS"/>
          </HBox>
        </StackPane>

```
###### \out\production\resources\view\PatientAppointmentPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<StackPane maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
        <items>
            <StackPane prefHeight="150.0" prefWidth="200.0">
               <children>
                  <VBox prefHeight="200.0" prefWidth="100.0">
                     <children>
                        <ListView fx:id="pastAppointmentCardListView" prefHeight="200.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
                           <VBox.margin>
                              <Insets right="5.0" />
                           </VBox.margin></ListView>
                     </children>
                     <StackPane.margin>
                        <Insets top="30.0" />
                     </StackPane.margin>
                  </VBox>
                  <Label text="Past Appointments" textFill="WHITE" textOverrun="CLIP" StackPane.alignment="TOP_LEFT">
                     <StackPane.margin>
                        <Insets left="5.0" top="5.0" />
                     </StackPane.margin></Label>
               </children>
            </StackPane>
            <StackPane prefHeight="150.0" prefWidth="200.0">
               <children>
                  <VBox prefHeight="200.0" prefWidth="100.0">
                     <children>
                        <ListView fx:id="upcomingAppointmentCardListView" prefHeight="200.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
                           <VBox.margin>
                              <Insets />
                           </VBox.margin>
                        </ListView>
                     </children>
                     <StackPane.margin>
                        <Insets top="30.0" />
                     </StackPane.margin>
                  </VBox>
                  <Label text="Upcoming Appointments" textFill="WHITE" StackPane.alignment="TOP_LEFT">
                     <StackPane.margin>
                        <Insets left="5.0" top="5.0" />
                     </StackPane.margin>
                  </Label>
               </children>
            </StackPane>
        </items>
      </SplitPane>
   </children>
</StackPane>
```
###### \out\production\resources\view\QueueCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.HBox?>

<HBox prefHeight="45.0" prefWidth="200.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Label fx:id="index" alignment="CENTER_RIGHT" text="\$index">
         <HBox.margin>
            <Insets right="5.0" />
         </HBox.margin></Label>
      <Label fx:id="name" text="\$name" />
   </children>
   <padding>
      <Insets left="5.0" right="5.0" top="5.0" />
   </padding>
</HBox>
```
###### \out\production\resources\view\QueueListPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.HBox?>

<HBox maxHeight="-Infinity" minHeight="-Infinity" prefHeight="45.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ListView fx:id="queueCardListView" orientation="HORIZONTAL" HBox.hgrow="ALWAYS" />
   </children>
</HBox>
```
###### \src\main\java\seedu\address\commons\events\model\AppointmentChangedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyImdb;
import seedu.address.model.patient.Patient;

/** Indicates the Appointment in the model has changed*/
public class AppointmentChangedEvent extends BaseEvent {

    public final Patient data;
    public final ReadOnlyImdb readOnlyImdb;

    public AppointmentChangedEvent(Patient data, ReadOnlyImdb readOnlyImdb) {
        this.data = data;
        this.readOnlyImdb = readOnlyImdb;
    }

    @Override
    public String toString() {
        return "Appointment records changed";
    }
}
```
###### \src\main\java\seedu\address\commons\events\ui\ShowCalendarViewRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.appointment.AppointmentEntry;

/**
 * An event requesting to view calendar and patients' appointments.
 */
public class ShowCalendarViewRequestEvent extends BaseEvent {

    public final ObservableList<AppointmentEntry> appointmentEntries;

    public ShowCalendarViewRequestEvent() {
        appointmentEntries = null;
    }

    public ShowCalendarViewRequestEvent(ObservableList<AppointmentEntry> appointmentEntries) {
        this.appointmentEntries = appointmentEntries;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \src\main\java\seedu\address\commons\events\ui\ShowPatientAppointmentRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.patient.Patient;

/**
 * An event requesting to view the list of appointments of the patient.
 */
public class ShowPatientAppointmentRequestEvent extends BaseEvent {

    public final Patient data;

    public ShowPatientAppointmentRequestEvent(Patient data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \src\main\java\seedu\address\logic\commands\AddAppointmentCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.DateTime;
import seedu.address.model.appointment.UniqueAppointmentEntryList;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;

/**
 * Add patient appointment
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "addappt";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient appointment. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "DATE TIME (24-hour clock) \n "
            + "*DATE must be after today's date \n"
            + "Example: " + COMMAND_WORD + " 1 2/4/2018 1300";

    public static final String MESSAGE_SUCCESS = "A new appointment is added.\n"
            + "Enter view appointment command to get updated calendar view.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exist.";
    private final Index targetPatientIndex;
    private final DateTime dateTime;

    public AddAppointmentCommand(Index targetPatientIndex, DateTime dateTime) {
        requireAllNonNull(targetPatientIndex, dateTime);
        this.targetPatientIndex = targetPatientIndex;

        this.dateTime = dateTime;
    }

    @Override
    public CommandResult execute() throws CommandException {

        preprocess();

        Patient patientFound = model.getPatientFromListByIndex(targetPatientIndex);

        try {
            model.addPatientAppointment(patientFound, dateTime);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UniqueAppointmentList.DuplicatedAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (UniqueAppointmentEntryList.DuplicatedAppointmentEntryException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
    }

    /**
     * Preprocess checking if index is valid and not out of bound of the patient list
     */
    private void preprocess() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetPatientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAppointmentCommand
                && this.targetPatientIndex.equals(((AddAppointmentCommand) other).targetPatientIndex)
                && this.dateTime.equals(((AddAppointmentCommand) other).dateTime));
    }
}
```
###### \src\main\java\seedu\address\logic\commands\AddPatientQueueCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;

/**
 * Add patient to visiting queue (registration)
 */
public class AddPatientQueueCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addq";
    public static final String COMMAND_ALIAS = "aq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient into visiting queue. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "%1$s is added into visiting queue";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already added into visiting queue.";
    private final Index targetIndex;
    private Index actualSourceIndex;

    /**
     * Creates an AddPatientQueueCommand to add the specified patient by its list index
     */
    public AddPatientQueueCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        Patient toQueue;

        try {
            toQueue = model.addPatientToQueue(actualSourceIndex);
        } catch (DuplicatePatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toQueue.getName().fullName));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        String actualIndexInString = model.getPatientSourceIndexInList(targetIndex.getZeroBased()) + "";

        try {
            actualSourceIndex = ParserUtil.parseIndex(actualIndexInString);
        } catch (IllegalValueException e) {
            throw new AssertionError("The target patient cannot be missing");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddPatientQueueCommand
                && this.targetIndex.equals(((AddPatientQueueCommand) other).targetIndex));
    }
}
```
###### \src\main\java\seedu\address\logic\commands\DeleteAppointmentCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.DateTime;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;

/**
 * Delete a patient's appointment
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "delappt";
    public static final String COMMAND_ALIAS = "da";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a patient's appointment."
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "DATE TIME (24-hour clock) \n "
            + "Example: " + COMMAND_WORD + " 1 2/4/2018 1300";

    public static final String MESSAGE_DELETE_SUCCESS = "The appointment is canceled. \n "
            + "Enter view appointment command to get updated calendar view.";

    public static final String MESSAGE_APPOINTMENT_CANNOT_BE_FOUND = "Appointment cannot be found.";

    private final Index targetPatientIndex;

    private final DateTime targetAppointmentDateTime;

    public DeleteAppointmentCommand(Index targetPatientIndex, DateTime targetAppointmentDateTime) {
        this.targetPatientIndex = targetPatientIndex;
        this.targetAppointmentDateTime = targetAppointmentDateTime;
    }

    @Override
    public CommandResult execute() throws CommandException {

        preprocess();

        Patient patientFound = model.getPatientFromListByIndex(targetPatientIndex);

        try {
            model.deletePatientAppointment(patientFound, targetAppointmentDateTime);
            return new CommandResult(MESSAGE_DELETE_SUCCESS);
        } catch (UniqueAppointmentList.AppoinmentNotFoundException e) {
            throw new CommandException(MESSAGE_APPOINTMENT_CANNOT_BE_FOUND);
        }
    }

    /**
     * Preprocess checking if index is valid and not out of bound of the patient list
     */
    private void preprocess() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetPatientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteAppointmentCommand)
                && this.targetPatientIndex.equals(((DeleteAppointmentCommand) other).targetPatientIndex)
                && this.targetAppointmentDateTime.equals(((DeleteAppointmentCommand) other).targetAppointmentDateTime);
    }
}
```
###### \src\main\java\seedu\address\logic\commands\RemovePatientQueueCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Remove patient from visiting queue (checkout)
 */
public class RemovePatientQueueCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removeq";
    public static final String COMMAND_ALIAS = "rq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the first patient from the visiting queue.";

    public static final String MESSAGE_USAGE_INDEX = COMMAND_WORD
            + ": Removes a specific patient from the visiting queue."
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_SUCCESS = "%1$s is removed from the visiting queue.";
    public static final String MESSAGE_PERSON_NOT_FOUND_QUEUE = "This patient is not in the visiting queue.";
    public static final String MESSAGE_QUEUE_EMPTY = "Visiting queue is empty";

    public final boolean indexArguementIsExist;
    private Index targetIndex;

    public RemovePatientQueueCommand() {
        indexArguementIsExist = false;

    }

    public RemovePatientQueueCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        indexArguementIsExist = true;
        this.targetIndex = targetIndex;
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (indexArguementIsExist) {
            //Unfilter patient list
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

            if (targetIndex.getZeroBased() >= model.getFilteredPersonList().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            Patient patientToRemove;
            if (indexArguementIsExist) {
                patientToRemove = model.removePatientFromQueueByIndex(targetIndex);
            } else {
                patientToRemove = model.removePatientFromQueue();
            }
            return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, patientToRemove.getName().toString()));
        } catch (PatientNotFoundException e) {

            if (model.getVisitingQueue().size() > 0) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND_QUEUE);
            }

            throw new CommandException(MESSAGE_QUEUE_EMPTY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemovePatientQueueCommand)
                && this.indexArguementIsExist == ((RemovePatientQueueCommand) other).indexArguementIsExist
                && this.targetIndex.equals(((RemovePatientQueueCommand) other).targetIndex);
    }
}
```
###### \src\main\java\seedu\address\logic\commands\ViewAppointmentCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowCalendarViewRequestEvent;
import seedu.address.commons.events.ui.ShowPatientAppointmentRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;

/**
 * List all the patient appointments
 */
public class ViewAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "viewappt";
    public static final String COMMAND_ALIAS = "va";

    public static final String MESSAGE_USAGE_PATIENT_WITH_INDEX = COMMAND_WORD
            + ": View list of appointments of a patient. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all patient appointments. ";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";
    public static final String MESSAGE_SUCCESS_PATIENT = "Listed all appointments made by %1$s";
    private Index targetIndex;
    private final boolean indexArgumentIsExist;

    /**
     * Creates an ViewAppointmentCommand to view list of appointments
     */
    public ViewAppointmentCommand() {
        this.indexArgumentIsExist = false;
    }

    /**
     * Creates an ViewAppointmentCommand to view list of appointments the specified {@code Patient}
     */
    public ViewAppointmentCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.indexArgumentIsExist = true;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        preprocess();

        Patient targetPatient;

        if (indexArgumentIsExist) {
            targetPatient = model.getPatientFromListByIndex(targetIndex);
            EventsCenter.getInstance().post(new ShowPatientAppointmentRequestEvent(targetPatient));
            return new CommandResult(String.format(MESSAGE_SUCCESS_PATIENT, targetPatient.getName().fullName));
        } else {
            EventsCenter.getInstance().post(new ShowCalendarViewRequestEvent(model.getAppointmentEntryList()));
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Preprocess checking if index is valid and not out of bound of the patient list
     */
    private void preprocess() throws CommandException {
        if (indexArgumentIsExist) {
            List<Patient> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewAppointmentCommand
                && this.indexArgumentIsExist == ((ViewAppointmentCommand) other).indexArgumentIsExist
                && this.targetIndex.equals(((ViewAppointmentCommand) other).targetIndex));
    }
}
```
###### \src\main\java\seedu\address\logic\parser\AddAppointmentCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.DateTime;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    private static final int NO_OF_ARGUMENTS = 3;
    private static final int PATIENT_INDEX_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final int TIME_INDEX = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommandParser
     * and returns an AddAppointmentCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        String[] argsArray = trimmedArgs.split("\\s");

        if (argsArray.length < NO_OF_ARGUMENTS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            Index targetPatientIndex = ParserUtil.parseIndex(argsArray[PATIENT_INDEX_INDEX]);
            DateTime appointmentDateTime = ParserUtil.parseDateTime(argsArray[DATE_INDEX] + " "
                    + argsArray[TIME_INDEX]);
            if (DateTime.isBefore(argsArray[DATE_INDEX] + " " + argsArray[TIME_INDEX])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddAppointmentCommand.MESSAGE_USAGE));
            }
            return new AddAppointmentCommand(targetPatientIndex, appointmentDateTime);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \src\main\java\seedu\address\logic\parser\AddPatientQueueCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPatientQueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddPatientQueueCommand object
 */
public class AddPatientQueueCommandParser implements Parser<AddPatientQueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPatientQueueCommandParser
     * and returns an AddPatientQueueCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPatientQueueCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new AddPatientQueueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPatientQueueCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \src\main\java\seedu\address\logic\parser\DeleteAppointmentCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.DateTime;

/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {

    private static final int NO_OF_ARGUMENTS = 3;
    private static final int PATIENT_INDEX_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final int TIME_INDEX = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppointmentCommand
     * and returns an DeleteAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE));
        }

        String[] argsArray = trimmedArgs.split("\\s");

        if (argsArray.length < NO_OF_ARGUMENTS) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            Index targetPatientIndex = ParserUtil.parseIndex(argsArray[PATIENT_INDEX_INDEX]);
            DateTime appointmentDateTime = ParserUtil.parseDateTime(argsArray[DATE_INDEX] + " "
                    + argsArray[TIME_INDEX]);
            return new DeleteAppointmentCommand(targetPatientIndex, appointmentDateTime);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \src\main\java\seedu\address\logic\parser\ImdbParser.java
``` java
            case ViewAppointmentCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case ViewAppointmentCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case DeleteAppointmentCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case DeleteAppointmentCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));
            case AddAppointmentCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));
            case AddAppointmentCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case AddPatientQueueCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case AddPatientQueueCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RemovePatientQueueCommand.COMMAND_WORD:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));

            case RemovePatientQueueCommand.COMMAND_ALIAS:
                throw new ParseException(String.format(LoginCommand.MESSAGE_NOT_LOGGED_IN,
                        LoginCommand.MESSAGE_USAGE));
```
###### \src\main\java\seedu\address\logic\parser\ImdbParser.java
``` java
            case ViewAppointmentCommand.COMMAND_WORD:
                return new ViewAppointmentCommandParser().parse(arguments);

            case ViewAppointmentCommand.COMMAND_ALIAS:
                return new ViewAppointmentCommandParser().parse(arguments);
```
###### \src\main\java\seedu\address\logic\parser\ImdbParser.java
``` java
            case ViewAppointmentCommand.COMMAND_WORD:
                return new ViewAppointmentCommandParser().parse(arguments);

            case ViewAppointmentCommand.COMMAND_ALIAS:
                return new ViewAppointmentCommandParser().parse(arguments);

            case DeleteAppointmentCommand.COMMAND_WORD:
                return new DeleteAppointmentCommandParser().parse(arguments);

            case DeleteAppointmentCommand.COMMAND_ALIAS:
                return new DeleteAppointmentCommandParser().parse(arguments);
            case AddAppointmentCommand.COMMAND_WORD:
                return new AddAppointmentCommandParser().parse(arguments);
            case AddAppointmentCommand.COMMAND_ALIAS:
                return new AddAppointmentCommandParser().parse(arguments);

            case AddPatientQueueCommand.COMMAND_WORD:
                return new AddPatientQueueCommandParser().parse(arguments);

            case AddPatientQueueCommand.COMMAND_ALIAS:
                return new AddPatientQueueCommandParser().parse(arguments);

            case RemovePatientQueueCommand.COMMAND_WORD:
                return new RemovePatientQueueCommandParser().parse(arguments);

            case RemovePatientQueueCommand.COMMAND_ALIAS:
                return new RemovePatientQueueCommandParser().parse(arguments);
```
###### \src\main\java\seedu\address\logic\parser\RemovePatientQueueCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemovePatientQueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemovePatientQueueCommand object
 */
public class RemovePatientQueueCommandParser implements Parser<RemovePatientQueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePatientQueueCommand
     * and returns an RemovePatientQueueCommand object for execution.
     * If argument is empty, construct RemovePatientQueueCommand object without parameter and return the object.
     * If arguement is not empty, parse argument to {@Index} object and construct RemovePatientQueueCommand object with
     * parameter and return the object.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemovePatientQueueCommand parse(String args) throws ParseException {

        if (args.isEmpty()) {
            return new RemovePatientQueueCommand();
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemovePatientQueueCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePatientQueueCommand.MESSAGE_USAGE_INDEX));
        }
    }
}
```
###### \src\main\java\seedu\address\logic\parser\ViewAppointmentCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewAppointmentCommand object
 */
public class ViewAppointmentCommandParser implements Parser<ViewAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAppointmentCommandParser
     * and returns an ViewAppointmentCommandParser object for execution.
     * If argument is empty, construct ViewAppointmentCommand object without parameter and return the object.
     * If arguement is not empty, parse argument to {@Index} object and construct ViewAppointmentCommand object with
     * parameter and return the object.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewAppointmentCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new ViewAppointmentCommand();
        }

        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewAppointmentCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAppointmentCommand.MESSAGE_USAGE_PATIENT_WITH_INDEX)));
        }
    }
}
```
###### \src\main\java\seedu\address\model\appointment\Appointment.java
``` java
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Patient appointment in Imdb
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class Appointment {

    private DateTime appointmentDateTime;

    public Appointment(String appointmentDateTime) {
        requireNonNull(appointmentDateTime);
        this.appointmentDateTime = new DateTime(appointmentDateTime);
    }

    public DateTime getAppointmentDateTime() {
        return this.appointmentDateTime;
    }

    public String getAppointmentDateTimeString() {
        return this.appointmentDateTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Appointment
                && this.appointmentDateTime.equals(((Appointment) other).appointmentDateTime));
    }

    @Override
    public int hashCode() {
        return appointmentDateTime.hashCode();
    }
}
```
###### \src\main\java\seedu\address\model\appointment\AppointmentEntry.java
``` java
package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import com.calendarfx.model.Entry;

/**
 * Patient appointment in Imdb, displayed on calendar
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class AppointmentEntry {
    private static final int TIME_INTERVAL = 1800;
    private Entry<String> appointmentEntry;
    private final Appointment appointmennt;
    private final String patientName;

    public AppointmentEntry(Appointment appointment, String patientName) {
        requireAllNonNull(appointment, patientName);
        this.appointmennt = appointment;
        this.patientName = patientName;
        appointmentEntry = new Entry(patientName);
        appointmentEntry.setTitle(patientName);
        appointmentEntry.changeStartDate(appointment.getAppointmentDateTime().getLocalDate());
        appointmentEntry.changeEndDate(appointment.getAppointmentDateTime().getLocalDate());
        appointmentEntry.changeStartTime(appointment.getAppointmentDateTime().getLocalTime());
        appointmentEntry.changeEndTime(appointment.getAppointmentDateTime().getEndLocalTime(TIME_INTERVAL));
    }

    public Entry getAppointmentEntry() {
        return appointmentEntry;
    }

    public Appointment getAppointment() {
        return this.appointmennt;
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof AppointmentEntry && this.appointmentEntry
                .equals(((AppointmentEntry) other).appointmentEntry));
    }

    @Override
    public int hashCode() {
        return appointmentEntry.hashCode();
    }
}
```
###### \src\main\java\seedu\address\model\appointment\DateTime.java
``` java
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Appointment DateTime in Imdb
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class DateTime {
    public static  final String MESSAGE_DATE_TIME_CONSTRAINTS = "Appointment date time should be in this format: \n"
            + "D/M/YYYY HHMM (24-hour clock)";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
    private String dateString;
    private String timeString;

    public DateTime(String appointmentDateTime) {
        requireNonNull(appointmentDateTime);
        String trimmedArgs = appointmentDateTime.trim();

        String[] dateTimeKeys = trimmedArgs.split("\\s");
        this.dateString = dateTimeKeys[0];
        this.timeString = dateTimeKeys[1];
    }

    public String toString() {
        return dateString + " " + timeString;
    }

    /**
     * Check if the date is past compared with today
     * @param dateTimeString
     * @return true if the date is already past
     */
    public static boolean isBefore(String dateTimeString) throws ParseException {
        String trimmedArgs = dateTimeString.trim();

        String[] dateTimeKeys = trimmedArgs.split("\\s");

        LocalDate compareDate;

        try {
            compareDate = LocalDate.parse(dateTimeKeys[0], dateFormatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage());
        }
        return compareDate.isBefore(LocalDate.now());
    }

    /**
     * Check if the date is ahead compared with today
     * @param dateTimeString
     * @return true if the date is ahead
     */
    public static boolean isAfterOrEqual(String dateTimeString) throws ParseException {
        String trimmedArgs = dateTimeString.trim();

        String[] dateTimeKeys = trimmedArgs.split("\\s");

        LocalDate compareDate;

        try {
            compareDate = LocalDate.parse(dateTimeKeys[0], dateFormatter);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage());
        }

        return (compareDate.isAfter(LocalDate.now()) || compareDate.isEqual(LocalDate.now()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateTime
                && this.dateString.equals(((DateTime) other).dateString)
                && this.timeString.equals(((DateTime) other).timeString));
    }

    public LocalDate getLocalDate() {
        return LocalDate.parse(dateString, dateFormatter);
    }

    public LocalTime getLocalTime() {
        return LocalTime.parse(timeString, timeFormatter);
    }

    public LocalTime getEndLocalTime(int minutes) {
        return getLocalTime().plusMinutes(minutes);
    }

    /**
     * Returns true if a given string is a valid date time string.
     */
    public static boolean isValidDateTime(String dateTimeString) {
        String trimmedArgs = dateTimeString.trim();
        String[] dateTimeKeys = trimmedArgs.split("\\s");

        try {
            LocalDate.parse(dateTimeKeys[0], dateFormatter);
            LocalTime.parse(dateTimeKeys[1], timeFormatter);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }
}
```
###### \src\main\java\seedu\address\model\appointment\UniqueAppointmentEntryList.java
``` java
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of appointment entries that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Appointment#equals(Object)
 */
public class UniqueAppointmentEntryList implements Iterable<AppointmentEntry> {

    private final ObservableList internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AppointmentEntryList.
     */
    public UniqueAppointmentEntryList() {}

    /**
     * Returns true if the list contains an equivalent appointment entry as the given argument.
     */
    public boolean contains(AppointmentEntry toCheck) {
        requireAllNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a AppointmentEntry to the list.
     *
     * @throws DuplicatedAppointmentEntryException if the AppointmentEntry to add
     * is a duplicate of an existing appointment in the list.
     */
    public void add(AppointmentEntry toAdd) throws DuplicatedAppointmentEntryException {
        requireAllNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatedAppointmentEntryException();
        }

        if (checkIfAppointmentIsBooked(toAdd)) {
            throw new DuplicatedAppointmentEntryException();
        }

        internalList.addAll(toAdd);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the same appointment has been made by other patient
     */
    public boolean checkIfAppointmentIsBooked(AppointmentEntry toCheck) {
        for (Object apptEntry : internalList) {
            AppointmentEntry current = (AppointmentEntry) apptEntry;
            if (current.getAppointment().getAppointmentDateTime()
                    .equals(toCheck.getAppointment().getAppointmentDateTime())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<AppointmentEntry> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<AppointmentEntry> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this
                || (other instanceof UniqueAppointmentEntryList
                && this.internalList.equals(((UniqueAppointmentEntryList) other).internalList));
    }

    /**
     * Ensures every appointment entry in the argument list exists in this object.
     */
    public void mergeFrom(UniqueAppointmentList from, String patientName) throws DuplicatedAppointmentEntryException {
        final Set<Appointment> alreadyInside = from.toSet();

        for (Appointment appt : alreadyInside) {
            AppointmentEntry newEntry = new AppointmentEntry(appt, patientName);

            boolean sameAppointment = checkIfAnotherAppointmentSameValueExist(appt);

            if (!contains(newEntry) && !sameAppointment) {
                add(newEntry);
            }
        }

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Checks if there is appointment in the list has same values as the given appointment
     */
    public boolean checkIfAnotherAppointmentSameValueExist(Appointment toCheck) {
        for (Object apptEntry : internalList) {
            AppointmentEntry current = (AppointmentEntry) apptEntry;
            if (current.getAppointment().equals(toCheck)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Returns true if the element is deleted..
     */
    public boolean remove(AppointmentEntry toRemove) {
        requireNonNull(toRemove);
        for (Object apptEntry : internalList) {
            AppointmentEntry current = (AppointmentEntry) apptEntry;
            if (current.getAppointment().equals(toRemove.getAppointment())) {
                return internalList.remove(apptEntry);
            }
        }
        return false;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list
     */
    public static class DuplicatedAppointmentEntryException extends DuplicateDataException {
        protected DuplicatedAppointmentEntryException() {
            super("Operation would result in duplicate appointments.");
        }
    }
}
```
###### \src\main\java\seedu\address\model\appointment\UniqueAppointmentList.java
``` java
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A list of appointment that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Appointment#equals(Object)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {
    private final ObservableList internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AppointmentList.
     */
    public UniqueAppointmentList() {}

    /**
     * Creates a UniqueAppointmentList using given appointments
     * Enforces no nulls
     */
    public UniqueAppointmentList(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.addAll(appointments);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all appointments in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Appointment> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Appointment int this list with those in the argument appointment list.
     */
    public void setAppointment(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.setAll(appointments);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every appointment in the argument list exists in this object.
     */
    public void mergeFrom(UniqueAppointmentList from) {
        final Set<Appointment> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(appointment -> !alreadyInside.contains(appointment))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an quivalent Appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireAllNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Appointment to the list.
     *
     * @throws DuplicatedAppointmentException if the Appointment to add
     * is a duplicate of an existing appointment in the list.
     */
    public void add(Appointment toAdd) throws DuplicatedAppointmentException {
        requireAllNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatedAppointmentException();
        }

        internalList.addAll(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this
                || (other instanceof UniqueAppointmentList
                    && this.internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    /**
     * Returns true if the element in this list or equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueAppointmentList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    public ObservableList<Appointment> getPastAppointmentObservableList() throws ParseException {
        Set<Appointment> appointmentSet = toSet();
        ObservableList pastAppointments = FXCollections.observableArrayList();

        for (Appointment appt : appointmentSet) {
            if (DateTime.isBefore(appt.getAppointmentDateTimeString())) {
                pastAppointments.add(appt);
            }
        }
        return FXCollections.unmodifiableObservableList(pastAppointments);
    }

    public ObservableList<Appointment> getUpcomingAppointmentObservableList() throws ParseException {
        Set<Appointment> appointmentSet = toSet();
        ObservableList pastAppointments = FXCollections.observableArrayList();

        for (Appointment appt : appointmentSet) {
            if (DateTime.isAfterOrEqual(appt.getAppointmentDateTimeString())) {
                pastAppointments.add(appt);
            }
        }
        return FXCollections.unmodifiableObservableList(pastAppointments);
    }

    /**
     * Removes an appointment from the appointment list
     * @throws AppoinmentNotFoundException if the appointment does not exist in the list
     */
    public void remove(Appointment targetAppointment) throws AppoinmentNotFoundException {
        requireNonNull(targetAppointment);

        if (!contains(targetAppointment)) {
            throw new AppoinmentNotFoundException();
        }

        internalList.remove(targetAppointment);
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list
     */
    public static class DuplicatedAppointmentException extends DuplicateDataException {
        protected DuplicatedAppointmentException() {
            super("Operation would result in duplicate appointments.");
        }
    }

    /**
     * Signals that an operation cannot be performed object that is not found
     */
    public static class AppoinmentNotFoundException extends Exception {
    }
}
```
###### \src\main\java\seedu\address\model\Imdb.java
``` java
    public void setQueue(Set<Integer> queueNos) {
        this.visitingQueue.setVisitingQueue(queueNos);
    }

```
###### \src\main\java\seedu\address\model\Imdb.java
``` java
    /**
     *  Updates the master appointment list to include appointment in all patients.
     */
    private void syncWithMasterAppointment(List<Patient> patientList) throws
            UniqueAppointmentEntryList.DuplicatedAppointmentEntryException {

        for (Patient p :patientList) {
            final UniqueAppointmentList patientAppt = new UniqueAppointmentList(p.getAppointments());

            appointments.mergeFrom(patientAppt, p.getName().fullName);
        }
    }

```
###### \src\main\java\seedu\address\model\Imdb.java
``` java
    ////appointment-level operations
    /**
     * remove a patient appointment
     * @throws UniqueAppointmentList.DuplicatedAppointmentException
     * @throws UniqueAppointmentEntryList.DuplicatedAppointmentEntryException
     */
    public void addAppointment(Patient patient, DateTime dateTime) throws
            UniqueAppointmentList.DuplicatedAppointmentException,
            UniqueAppointmentEntryList.DuplicatedAppointmentEntryException {
        Appointment newAppt = new Appointment(dateTime.toString());
        patient.addAppointment(newAppt);
        addAppointmentEntry(newAppt, patient.getName().fullName);
    }

    private void addAppointmentEntry(Appointment appt, String patientName) throws
            UniqueAppointmentEntryList.DuplicatedAppointmentEntryException {
        AppointmentEntry appointmentEntry = new AppointmentEntry(appt, patientName);
        appointments.add(appointmentEntry);
    }

    /**
     * Remove a patient's appointment
     */
    public void deletePatientAppointment(Patient patient, Appointment targetAppointment) throws
            UniqueAppointmentList.AppoinmentNotFoundException {
        requireAllNonNull(patient, targetAppointment);
        patient.deletePatientAppointment(targetAppointment);
        deleteAppointmentEntry(targetAppointment, patient.getName().fullName);
    }

    private void deleteAppointmentEntry(Appointment targetAppointment, String patientName) {
        AppointmentEntry appointmentEntry = new AppointmentEntry(targetAppointment, patientName);
        appointments.remove(appointmentEntry);
    }

    //// visiting queue-level operations
    /**
     * Adds a patient to the visiting queue.
     * @throws DuplicatePatientException if an equivalent patient index already exists.
     */
    public void addPatientToQueue(int p) throws DuplicatePatientException {
        requireNonNull(p);
        visitingQueue.add(p);
    }

    /**
     * Removes the first patient from the visiting queue.
     * @throws PatientNotFoundException if visiting queue is empty.
     */
    public int removePatientFromQueue() throws PatientNotFoundException {
        return visitingQueue.removePatient();
    }

    /**
     * Removes a specific patient from the visiting queue by their index in the patient list.
     * @throws PatientNotFoundException if visiting queue is empty or the patient is not in the visiting queue.
     */
    public void removePatientFromQueueByIndex(int p) throws PatientNotFoundException {
        visitingQueue.removePatient(p);
    }

```
###### \src\main\java\seedu\address\model\Imdb.java
``` java
    @Override
    public ObservableList<AppointmentEntry> getAppointmentEntryList() {
        return appointments.asObservableList();
    }

    @Override
    public ObservableList<Patient> getUniquePatientQueue() {
        UniquePatientList patientQueueList = getPatientQueueList();
        return patientQueueList.asObservableList();
    }

    @Override
    public ObservableList<Integer> getUniquePatientQueueNo() {
        return visitingQueue.asObservableList();
    }

    private UniquePatientList getPatientQueueList() {
        UniquePatientList queueList = new UniquePatientList();

        for (int patientIndex : visitingQueue.getVisitingQueue()) {
            try {
                queueList.add(persons.getPatientByIndex(patientIndex));
            } catch (DuplicatePatientException e) {
                e.printStackTrace();
            }
        }

        return queueList;
    }

```
###### \src\main\java\seedu\address\model\Model.java
``` java
    /** Adds patient into visiting queue.
     * @throws NullPointerException if {@code targetIndex} is null.
     * @throws DuplicatePatientException if {@code targetIndex} already exist in the visiting queue.*/
    Patient addPatientToQueue(Index targetIndex) throws DuplicatePatientException;

    /** Remove the first patient from the visiting queue
     * @throws PatientNotFoundException if the visiting queue is empty.*/
    Patient removePatientFromQueue() throws PatientNotFoundException;

    /** Remove a specific patient from the visiting queue
     * @throws PatientNotFoundException if {@code targetIndex} cannot be found in the visiting queue or the visiting
     * queue is empty.*/
    Patient removePatientFromQueueByIndex(Index targetIndex) throws PatientNotFoundException;

    /** Get patient visiting queue (contain patient objects)*/
    ObservableList<Patient> getVisitingQueue();

    /** Get Patient from the patient list */
    Patient getPatientFromList(Predicate<Patient> predicate);

    /** Get patient visiting queue (contain patient index)*/
    ObservableList<Integer> getPatientListIndexInQueue();

    /** Get patient source index in the patient filtered list */
    int getPatientSourceIndexInList(int targetIndex);

    /** Check if the patient is in the queue */
    boolean checkIfPatientInQueue(Patient targetPatient);

    /** Get Patient from the patient list by index */
    Patient getPatientFromListByIndex(Index targetIndex);

    /** Delete a patient's appointment*/
    void deletePatientAppointment(Patient patient, DateTime targetAppointmentDateTime) throws
            UniqueAppointmentList.AppoinmentNotFoundException;

    /** Get appointment entries*/
    ObservableList<AppointmentEntry> getAppointmentEntryList();

    /** Add a patient's appointment*/
    void addPatientAppointment(Patient patient, DateTime dateTime) throws
            UniqueAppointmentList.DuplicatedAppointmentException,
            UniqueAppointmentEntryList.DuplicatedAppointmentEntryException;
}
```
###### \src\main\java\seedu\address\model\ModelManager.java
``` java
    private void indicateAppointmentChanged(Patient patient) {
        raise(new AppointmentChangedEvent(patient, imdb));
    }

```
###### \src\main\java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Patient getPatientFromList(Predicate<Patient> predicate) {
        filteredPatients.setPredicate(predicate);
        if (filteredPatients.size() > 0) {
            Patient targetPatient = filteredPatients.get(0);
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return targetPatient;
        }
        return null;
    }

    @Override
    public Patient getPatientFromListByIndex(Index targetIndex) {
        return filteredPatients.get(targetIndex.getZeroBased());
    }

    @Override
    public int getPatientSourceIndexInList(int targetIndex) {
        return filteredPatients.getSourceIndex(targetIndex) + 1;
    }

    //=========== Visiting Queue Accessors =============================================================

    @Override
    public boolean checkIfPatientInQueue(Patient patientToDelete) {
        int targetIndex = filteredPatients.getSourceIndex(filteredPatients.indexOf(patientToDelete));
        if (imdb.getUniquePatientQueueNo().contains(targetIndex)) {
            return true;
        }

        return false;
    }

    @Override
    public ObservableList<Integer> getPatientListIndexInQueue() {
        return imdb.getUniquePatientQueueNo();
    }

    @Override
    public synchronized Patient addPatientToQueue(Index targetIndex) throws DuplicatePatientException {
        requireNonNull(targetIndex);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        int patientIndex = filteredPatients.getSourceIndex(targetIndex.getZeroBased());
        imdb.addPatientToQueue(patientIndex);
        indicateAddressBookChanged();

        return filteredPatients.get(targetIndex.getZeroBased());
    }

    @Override
    public synchronized Patient removePatientFromQueue() throws PatientNotFoundException {
        int patientIndexToRemove = imdb.removePatientFromQueue();
        indicateAddressBookChanged();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return filteredPatients.get(patientIndexToRemove);
    }

    @Override
    public Patient removePatientFromQueueByIndex(Index targetIndex) throws PatientNotFoundException {
        imdb.removePatientFromQueueByIndex(targetIndex.getZeroBased());
        indicateAddressBookChanged();
        return filteredPatients.get(targetIndex.getZeroBased());
    }

    @Override
    public ObservableList<Patient> getVisitingQueue() {
        return imdb.getUniquePatientQueue();
    }

    //=========== Appointment List Accessors =============================================================

    @Override
    public synchronized void deletePatientAppointment(Patient patient, DateTime targetAppointmentDateTime) throws
            UniqueAppointmentList.AppoinmentNotFoundException {
        requireAllNonNull(patient, targetAppointmentDateTime);
        Appointment targetAppointment = new Appointment(targetAppointmentDateTime.toString());
        imdb.deletePatientAppointment(patient, targetAppointment);
        indicateAppointmentChanged(patient);
    }

    @Override
    public ObservableList<AppointmentEntry> getAppointmentEntryList() {
        return imdb.getAppointmentEntryList();
    }

    @Override
    public synchronized void addPatientAppointment(Patient patient, DateTime dateTime) throws
            UniqueAppointmentList.DuplicatedAppointmentException,
            UniqueAppointmentEntryList.DuplicatedAppointmentEntryException {
        requireAllNonNull(patient, dateTime);
        imdb.addAppointment(patient, dateTime);
        indicateAppointmentChanged(patient);
    }
```
###### \src\main\java\seedu\address\model\ReadOnlyImdb.java
``` java
    /**
     * Returns an unmodifiable view of the appointment list.
     * This list will not contain any duplicate appointment.
     */
    ObservableList<AppointmentEntry> getAppointmentEntryList();

    /**
     * Returns an unmodifiable view of the patient visiting queue.
     * This list will not contain any duplicate patient in the queue.
     */
    ObservableList<Patient> getUniquePatientQueue();

    /**
     * Returns an unmodifiable view of the patient visiting queue indices.
     * This list will not contain any duplicate patient index in the queue.
     */
    ObservableList<Integer> getUniquePatientQueueNo();
}
```
###### \src\main\java\seedu\address\model\UniquePatientVisitingQueue.java
``` java
package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Patient visiting queue in Imdb
 * Gurantees: immutable
 */
public class UniquePatientVisitingQueue implements Iterable<Integer> {

    private LinkedList<Integer> visitingQueue;

    public UniquePatientVisitingQueue() {
        visitingQueue = new LinkedList<Integer>();
    }

    /**
     * Adds a patient into visiting queue.
     *
     * @throws DuplicatePatientException if the patient index to add is a duplicate of an existing patient index in the
     * queue.
     */
    public void add(int patientIndex) throws DuplicatePatientException {
        requireNonNull(patientIndex);

        if (contains(patientIndex)) {
            throw new DuplicatePatientException();
        }

        visitingQueue.add(patientIndex);
    }

    /**
     * Removes the first patient from the visiting queue.
     *
     * @throws PatientNotFoundException if the queue is empty.
     */
    public int removePatient() throws PatientNotFoundException {
        if (visitingQueue.isEmpty()) {
            throw new PatientNotFoundException();
        }

        return visitingQueue.removeFirst();
    }

    /**
     * Removes a specific patient from the visiting queue.
     *
     * @throws PatientNotFoundException if the queue is empty.
     */
    public void removePatient(int index) throws PatientNotFoundException {
        if (visitingQueue.isEmpty()) {
            throw new PatientNotFoundException();
        }

        if (!contains(index)) {
            throw new PatientNotFoundException();
        }

        Integer targetIndex = index;

        visitingQueue.remove(targetIndex);
    }

    public LinkedList<Integer> getVisitingQueue() {
        return visitingQueue;
    }

    /**
     * Returns true if the queue contains an equivalent patient index as the given argument.
     */
    public boolean contains(int toCheck) {
        requireNonNull(toCheck);
        return visitingQueue.contains(toCheck);
    }

    @Override
    public Iterator<Integer> iterator() {
        return visitingQueue.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniquePatientVisitingQueue
                && this.visitingQueue.equals(((UniquePatientVisitingQueue) other).visitingQueue));
    }

    @Override
    public int hashCode() {
        return visitingQueue.hashCode();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Integer> asObservableList() {
        ObservableList<Integer> patientList = FXCollections.observableArrayList(this.visitingQueue);
        return FXCollections.unmodifiableObservableList(patientList);
    }

    /**
     * Replaces the visiting queue in this list with those in the argument visiting queue.
     */
    public void setVisitingQueue(Set<Integer> queueNos) {
        requireAllNonNull(queueNos);
        visitingQueue.clear();
        visitingQueue.addAll(queueNos);
        assert CollectionUtil.elementsAreUnique(visitingQueue);
    }
}
```
###### \src\main\java\seedu\address\storage\Storage.java
``` java
    /**
     * Saves the current version of the IMDB to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAppointmentChangedEvent(AppointmentChangedEvent ace);
}
```
###### \src\main\java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleAppointmentChangedEvent(AppointmentChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.readOnlyImdb);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
```
###### \src\main\java\seedu\address\storage\XmlAdaptedAppointment.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;

/**
 * JAXB-friendly adapted version of the Appointment.
 */
public class XmlAdaptedAppointment {

    @XmlElement
    private String dateTime;

    /**
     * Constructs an XmlAdaptedAppointment
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Constructs a {@code XmlAdaptedAppointment} with the given {@code patientName} and {@code dateTime}.
     */
    public XmlAdaptedAppointment(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAppointment(Appointment source) {
        this.dateTime = source.getAppointmentDateTimeString();
    }

    public Appointment toModelType() throws IllegalValueException {
        return new Appointment(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof  XmlAdaptedAppointment)) {
            return false;
        }
        return (dateTime.equals(((XmlAdaptedAppointment) other).dateTime));
    }
}
```
###### \src\main\java\seedu\address\storage\XmlAdaptedQueue.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

/**
 * JAXB-friendly adapted version of the PatientQueue.
 */
public class XmlAdaptedQueue {

    @XmlValue
    private int queueNo;

    /**
     * Constructs an XmlAdaptedQueue.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedQueue() {}

    public XmlAdaptedQueue(Integer queueNo) {
        this.queueNo = queueNo;
    }

    /**
     * Converts this jaxb-friendly adapted queue object into integer.
     */
    public int toModelType() {
        return queueNo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof  XmlAdaptedQueue)) {
            return false;
        }

        return queueNo == ((XmlAdaptedQueue) other).queueNo;
    }
}
```
###### \src\main\java\seedu\address\ui\AppointmentCard.java
``` java
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

/**
 * An UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {
    private static final String FXML = "AppointmentCard.fxml";

    public final Appointment appointment;

    @FXML
    private Label id;

    @FXML
    private Label dateTime;

    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");
        dateTime.setText(appointment.getAppointmentDateTimeString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        AppointmentCard card = (AppointmentCard) other;

        return id.getText().equals(card.id.getText())
                && appointment.equals(card.appointment);
    }

}
```
###### \src\main\java\seedu\address\ui\CalendarPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.page.MonthPage;
import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AppointmentChangedEvent;
import seedu.address.model.appointment.AppointmentEntry;

/**
 * Panel containing the calendar view displaying patients' appointment.
 */
public class CalendarPanel extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);

    @FXML
    private MonthPage calendarView;

    private Calendar calendar;
    private CalendarSource calendarSource;

    public CalendarPanel() {
        super(FXML);
        calendarSource = new CalendarSource("calendar");
        calendar = new Calendar("calendar");
        calendar.setStyle(Calendar.Style.STYLE2);
        calendarSource.getCalendars().add(calendar);
        setConnections();
        registerAsAnEventHandler(this);
    }

    public CalendarPanel(ObservableList<AppointmentEntry> entries) {
        super(FXML);
        calendarSource = new CalendarSource("calendar");
        calendar = new Calendar("calendar");
        calendar.setStyle(Calendar.Style.STYLE2);
        calendarSource.getCalendars().add(calendar);

        diabledEventHandlerForEntrySelectionEvent();
        disabledCreateEntryWithDoubleClicked();

        setConnections(entries);
        registerAsAnEventHandler(this);
    }

    private void setConnections() {

    }

    private void setConnections(ObservableList<AppointmentEntry> entries) {

        for (AppointmentEntry entry : entries) {
            calendar.addEntry(entry.getAppointmentEntry());
        }
        calendarView.getCalendarSources().add(calendarSource);
    }

    /**
     * Disabled double click to add new entry in calendar
     */
    public void disabledCreateEntryWithDoubleClicked() {
        calendarView.setEntryFactory(new Callback<DateControl.CreateEntryParameter, Entry<?>>() {
            @Override
            public Entry<?> call(DateControl.CreateEntryParameter param) {
                return null;
            }
        });
    }

    /**
     * Disable pop over when clicked on the appointment entry
     */
    public void diabledEventHandlerForEntrySelectionEvent() {
        calendarView.setEntryDetailsPopOverContentCallback(new Callback<DateControl.EntryDetailsPopOverContentParameter,
                Node>() {
            @Override
            public Node call(DateControl.EntryDetailsPopOverContentParameter param) {
                param.getPopOver().hide();
                return null;
            }
        });
    }

    @Subscribe
    public void handleShowCalendar(AppointmentChangedEvent ace) {
        setConnections(ace.readOnlyImdb.getAppointmentEntryList());
    }
}
```
###### \src\main\java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private StackPane queuePanelPlaceholder;

```
###### \src\main\java\seedu\address\ui\MainWindow.java
``` java
        queuePanel = new QueuePanel(logic.getPatientVisitingQueue(), logic.getPatientIndexInQueue());
        queuePanelPlaceholder.getChildren().add(queuePanel.getRoot());
    }

```
###### \src\main\java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowPatientAppointment(ShowPatientAppointmentRequestEvent event) throws ParseException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleShowPatientAppointment(event.data.getPastAppointmentList(),
                event.data.getUpcomingAppointmentList());
    }

    private void handleShowPatientAppointment(ObservableList<Appointment> pastAppointments,
                                              ObservableList<Appointment> upcomingAppointment) {

        patientAppointmentPanel = new PatientAppointmentPanel(pastAppointments, upcomingAppointment);
        browserPlaceholder.getChildren().add(patientAppointmentPanel.getRoot());
    }

    @Subscribe
    private void handlePatientPanelSelectionChangedEvent(PatientPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
        browserPanel.loadPersonPage(event.getNewSelection().patient);
    }


    @Subscribe
    private void handleShowCalendarAppointment(ShowCalendarViewRequestEvent scvre) {
        logger.info(LogsCenter.getEventHandlingLogMessage(scvre));
        calendarPanel = new CalendarPanel(scvre.appointmentEntries);
        browserPlaceholder.getChildren().add(calendarPanel.getRoot());
    }
}
```
###### \src\main\java\seedu\address\ui\PatientAppointmentPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AppointmentChangedEvent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;

/**
 * Panel containing the list of patient's appointments.
 */
public class PatientAppointmentPanel extends UiPart<Region> {
    private static final String FXML = "PatientAppointmentPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientAppointmentPanel.class);

    @FXML
    private ListView<AppointmentCard> pastAppointmentCardListView;

    @FXML
    private ListView<AppointmentCard> upcomingAppointmentCardListView;

    public PatientAppointmentPanel(ObservableList<Appointment> pastAppointmentList,
                                   ObservableList<Appointment> upcomingAppointmentList) {
        super(FXML);
        setConnections(pastAppointmentList, upcomingAppointmentList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Appointment> pastAppointmentList,
                                ObservableList<Appointment> upcomingAppointmentList) {

        int pastAppointmentListSize = pastAppointmentList.size();
        ObservableList<AppointmentCard> pastMappedList = EasyBind.map(
                pastAppointmentList, (appointment -> new AppointmentCard(appointment,
                        pastAppointmentList.indexOf(appointment) + 1)));
        pastAppointmentCardListView.setItems(pastMappedList);
        pastAppointmentCardListView.setCellFactory(listView -> new AppointmentListViewCell());
        ObservableList<AppointmentCard> upcomingMappedList = EasyBind.map(
                upcomingAppointmentList, (appointment -> new AppointmentCard(appointment,
                        pastAppointmentListSize + upcomingAppointmentList.indexOf(appointment) + 1)));
        upcomingAppointmentCardListView.setItems(upcomingMappedList);
        upcomingAppointmentCardListView.setCellFactory(listView-> new AppointmentListViewCell());
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AppointmentChangedEvent ace) throws ParseException {
        try {
            setConnections(ace.data.getPastAppointmentList(), ace.data.getUpcomingAppointmentList());
        } catch (Exception e) {
            throw new ParseException("List is not in observable list");
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<AppointmentCard> {

        @Override
        protected void updateItem(AppointmentCard appointmentCard, boolean empty) {
            super.updateItem(appointmentCard, empty);

            if (empty || appointmentCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(appointmentCard.getRoot());
            }
        }
    }
}
```
###### \src\main\java\seedu\address\ui\QueueCard.java
``` java
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.patient.Patient;

/**
 * An UI component that displays {@code Patient}'s name in the visiting queue.
 */
public class QueueCard extends UiPart<Region> {

    private static final String FXML = "QueueCard.fxml";

    public final Patient patient;

    @FXML
    private Label index;

    @FXML
    private Label name;

    public QueueCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        index.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof QueueCard)) {
            return false;
        }

        QueueCard card = (QueueCard) other;
        return index.getText().equals(card.index.getText())
                && patient.equals(card.patient);
    }
}
```
###### \src\main\java\seedu\address\ui\QueuePanel.java
``` java
package seedu.address.ui;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.ImdbChangedEvent;
import seedu.address.model.patient.Patient;

/**
 * Panel containing the visiting queue.
 */
public class QueuePanel extends UiPart<Region> {

    private static final String FXML = "QueueListPanel.fxml";

    @FXML
    private ListView<QueueCard> queueCardListView;

    public QueuePanel(ObservableList<Patient> queueList, ObservableList<Integer> indexNoList) {
        super(FXML);
        setConnections(queueList, indexNoList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Patient> queueList, ObservableList<Integer> indexNoList) {
        ObservableList<QueueCard> mappedList = EasyBind.map(queueList, (patient) -> new QueueCard(patient,
                indexNoList.get(queueList.indexOf(patient)) + 1));

        queueCardListView.setItems(mappedList);
        queueCardListView.setCellFactory(listView -> new QueueListViewCell());
    }

    @Subscribe
    public void handleImdbChangedEvent(ImdbChangedEvent ice) {
        setConnections(ice.data.getUniquePatientQueue(), ice.data.getUniquePatientQueueNo());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code QueueCard}.
     */
    class QueueListViewCell extends ListCell<QueueCard> {

        @Override
        protected void updateItem(QueueCard queueCard, boolean empty) {
            super.updateItem(queueCard, empty);

            if (empty || queueCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(queueCard.getRoot());
            }
        }
    }
}
```
###### \src\main\resources\view\AppointmentCard.fxml
``` fxml
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>

<VBox alignment="TOP_CENTER" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="100.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane alignment="CENTER" maxHeight="-Infinity" maxWidth="1.7976931348623157E308" minHeight="-Infinity">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <VBox prefHeight="200.0" prefWidth="100.0">
               <children>
                  <HBox prefHeight="100.0" prefWidth="200.0">
                     <children>
                        <Label fx:id="id" minWidth="-Infinity" text="Label" />
                        <Label fx:id="dateTime" text="Label" />
                     </children></HBox>
               </children>
            </VBox>
         </children>
      </GridPane>
   </children>
</VBox>
```
###### \src\main\resources\view\CalendarPanel.fxml
``` fxml
<?import com.calendarfx.view.page.MonthPage?>
<?import javafx.scene.layout.StackPane?>

<StackPane maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <MonthPage fx:id="calendarView" enableHyperlinks="false" prefHeight="200.0" prefWidth="200.0" selectionMode="SINGLE" />
   </children>
</StackPane>
```
###### \src\main\resources\view\MainWindow.fxml
``` fxml
        <StackPane VBox.vgrow="NEVER" fx:id="queueList" styleClass="pane-with-border"
                   minHeight="50" prefHeight="100" maxHeight="60">
          <HBox minHeight="50" prefHeight="60">
            <padding>
              <Insets top="5" right="10" bottom="5" left="10" />
            </padding>
            <StackPane fx:id="queuePanelPlaceholder" HBox.hgrow="ALWAYS"/>
          </HBox>
        </StackPane>

```
###### \src\main\resources\view\PatientAppointmentPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>

<StackPane maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
        <items>
            <StackPane prefHeight="150.0" prefWidth="200.0">
               <children>
                  <VBox prefHeight="200.0" prefWidth="100.0">
                     <children>
                        <ListView fx:id="pastAppointmentCardListView" prefHeight="200.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
                           <VBox.margin>
                              <Insets right="5.0" />
                           </VBox.margin></ListView>
                     </children>
                     <StackPane.margin>
                        <Insets top="30.0" />
                     </StackPane.margin>
                  </VBox>
                  <Label text="Past Appointments" textFill="WHITE" textOverrun="CLIP" StackPane.alignment="TOP_LEFT">
                     <StackPane.margin>
                        <Insets left="5.0" top="5.0" />
                     </StackPane.margin></Label>
               </children>
            </StackPane>
            <StackPane prefHeight="150.0" prefWidth="200.0">
               <children>
                  <VBox prefHeight="200.0" prefWidth="100.0">
                     <children>
                        <ListView fx:id="upcomingAppointmentCardListView" prefHeight="200.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
                           <VBox.margin>
                              <Insets />
                           </VBox.margin>
                        </ListView>
                     </children>
                     <StackPane.margin>
                        <Insets top="30.0" />
                     </StackPane.margin>
                  </VBox>
                  <Label text="Upcoming Appointments" textFill="WHITE" StackPane.alignment="TOP_LEFT">
                     <StackPane.margin>
                        <Insets left="5.0" top="5.0" />
                     </StackPane.margin>
                  </Label>
               </children>
            </StackPane>
        </items>
      </SplitPane>
   </children>
</StackPane>
```
###### \src\main\resources\view\QueueCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.HBox?>

<HBox prefHeight="45.0" prefWidth="200.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Label fx:id="index" alignment="CENTER_RIGHT" text="\$index">
         <HBox.margin>
            <Insets right="5.0" />
         </HBox.margin></Label>
      <Label fx:id="name" text="\$name" />
   </children>
   <padding>
      <Insets left="5.0" right="5.0" top="5.0" />
   </padding>
</HBox>
```
###### \src\main\resources\view\QueueListPanel.fxml
``` fxml

<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.HBox?>

<HBox maxHeight="-Infinity" minHeight="-Infinity" prefHeight="45.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <ListView fx:id="queueCardListView" orientation="HORIZONTAL" HBox.hgrow="ALWAYS" />
   </children>
</HBox>
```
