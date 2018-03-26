package seedu.address.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BirthdayList extends UiPart<Region> {

    private static final String FXML = "BirthdayList.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");


    @FXML
    private TextArea birthdayList;

    public BirthdayList(ObservableList<Person> list) {
        super(FXML);
        birthdayList.textProperty().bind(displayed);
        Platform.runLater(() -> displayed.setValue(parseBirthdaysFromObservableList(list)));
    }

    /**
     * Helper method to parse the given list into their respective birthdays into a sorted string
     * @param observablelist given list of current addressBook
     * @return String to be displayed
     */
    private String parseBirthdaysFromObservableList(ObservableList<Person> observablelist) {
        StringBuilder string = new StringBuilder();
        List<Person> list = new ArrayList<Person>();

        if (observablelist == null) {
            return "Hello World";
        }

        for (Person person: observablelist) {
            list.add(person);
        }

        list.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {

                int o1Days = o1.getBirthday().getDay();
                int o1Month = o1.getBirthday().getMonth();
                int o2Days = o2.getBirthday().getDay();
                int o2Month = o2.getBirthday().getMonth();

                if (o1Month != o2Month) {
                    return o1Month - o2Month;
                }

                return o1Days - o2Days;
            }
        });

        for (Person person: list) {
            string.append(person.getBirthday().toString());
            string.append(" ");
            string.append(person.getName().toString());
            string.append("\n");
        }

        return string.toString();
    }
}
