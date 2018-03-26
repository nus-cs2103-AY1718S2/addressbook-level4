package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BirthdayNotificationEvent;
import seedu.address.model.person.Person;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BirthdayNotification extends UiPart<Region> {

    private static final String FXML = "BirthdayList.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private final StringProperty displayed = new SimpleStringProperty("");

    private final LocalDate currentDate;
    private final int currentDay;
    private final int currentMonth;

    @FXML
    private TextArea birthdayNotification;

    public BirthdayNotification(ObservableList<Person> list) {
        super(FXML);

        currentDate = LocalDate.now();
        currentDay = currentDate.getDayOfMonth();
        currentMonth = currentDate.getMonthValue();

        birthdayNotification.textProperty().bind(displayed);
        Platform.runLater(() -> displayed.setValue(parseBirthdaysFromObservableList(list)));
    }

    /**
     * Helper method to parse the given list into their respective birthdays into a sorted string
     * @param observablelist given list of current addressBook
     * @return String to be displayed
     */
    private String parseBirthdaysFromObservableList(ObservableList<Person> observablelist) {
        StringBuilder string = new StringBuilder();
        List<Person> listOfPersonWithBirthdayToday = new ArrayList<Person>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (observablelist == null) {
            return " ";
        }
        else if (observablelist.size() <= 0){
            string.append("StardyTogether has no contacts :(\n");
            return string.toString();
        }

        for (Person person: observablelist) {
            if (person.getBirthday().getDay() == this.currentDay && person.getBirthday().getMonth() == this.currentMonth)
                listOfPersonWithBirthdayToday.add(person);
        }

        string.append("It's their birthdays today (" + dtf.format(currentDate) + ")\n");
        for (Person person: listOfPersonWithBirthdayToday) {
            string.append(person.getBirthday().toString());
            string.append(" ");
            string.append(person.getName().toString());
            string.append("\n");
        }

        return string.toString();
    }

    /**
     * Frees resources allocated to the TextArea.
     */
    public void freeResources() {
        birthdayNotification = null;
    }

}
