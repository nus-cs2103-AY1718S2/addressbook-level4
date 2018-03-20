package seedu.address.ui;

import java.util.List;
import java.util.Comparator;
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
import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class BirthdayList extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(BirthdayList.class);
    private static final String FXML = "BirthdayList.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");


    @FXML
    private TextArea birthdayList;

    public BirthdayList(UniquePersonList list) {
        super(FXML);
        birthdayList.textProperty().bind(displayed);
        Platform.runLater(() -> displayed.setValue(parseBirthdaysFromUniquePersonList(list)));
    }

    private String parseBirthdaysFromUniquePersonList(UniquePersonList personsList) {
        ObservableList<Person> list = personsList.asObservableList();
        StringBuilder string = new StringBuilder();

        list.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                int o1Birthday = Integer.parseInt(o1.getBirthday().toString());
                int o1Month = (o1Birthday % 1000) / 10;
                int o1Days = (o1Birthday / 1000);
                int o2Birthday = Integer.parseInt(o2.getBirthday().toString());
                int o2Month = (o2Birthday % 1000) / 10;
                int o2Days = (o2Birthday / 1000);

                if (o1Month != o2Month) {
                    return o1Month - o2Month;
                }

                return o1Days - o2Days;
            }
        });

        for(Person person: list){
            string.append(person.getBirthday().toString());
            string.append(" ");
            string.append(person.getName().toString());
            string.append("\n");
        }

        return string.toString();
    }
}
