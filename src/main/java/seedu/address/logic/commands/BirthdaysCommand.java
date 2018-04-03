package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.commons.events.ui.BirthdayNotificationEvent;
import seedu.address.model.person.Person;

//@@author AzuraAiR
/**
 * Shows either the birthday list or notification of Persons in StardyTogether
 */
public class BirthdaysCommand extends Command {

    public static final String COMMAND_WORD = "birthdays";

    public static final String ADDITIONAL_COMMAND_PARAMETER = "today";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list containing all persons' birthdays."
            + "Optional Parameters: "
            + ADDITIONAL_COMMAND_PARAMETER
            + "Example: " + COMMAND_WORD + ", " + COMMAND_WORD + " " + ADDITIONAL_COMMAND_PARAMETER;

    public static final String SHOWING_BIRTHDAY_MESSAGE = "Displaying birthday list";

    public static final String SHOWING_BIRTHDAY_NOTIFICATION = "Displaying today's birthdays";

    private boolean isToday;

    public BirthdaysCommand(boolean todays) {
        requireNonNull(todays);
        this.isToday = todays;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);

        if (isToday) {
            LocalDate currentDate = LocalDate.now();

            EventsCenter.getInstance().post(new BirthdayNotificationEvent(parseBirthdaysForNotification(
                    model.getAddressBook().getPersonList(), currentDate), currentDate));
            return new CommandResult(SHOWING_BIRTHDAY_NOTIFICATION);
        } else {
            EventsCenter.getInstance().post(new BirthdayListEvent(parseBirthdaysForList(
                    model.getAddressBook().getPersonList())));
        }

        return new CommandResult(SHOWING_BIRTHDAY_MESSAGE);
    }

    /**
     * Parses the given list into their respective birthdays into a sorted string
     * @param observablelist given list of current addressBook
     * @return String to be displayed
     */
    public static String parseBirthdaysForList(ObservableList<Person> observablelist) {
        StringBuilder string = new StringBuilder();
        List<Person> list = new ArrayList<Person>();

        if (observablelist == null) {
            return "";
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
            string.append(person.getBirthday().getDay());
            string.append("/");
            string.append(person.getBirthday().getMonth());
            string.append("/");
            string.append(person.getBirthday().getYear());
            string.append(" ");
            string.append(person.getName().toString());
            string.append("\n");
        }

        return string.toString();
    }

    /**
     * Parses the given list into their respective birthdays into a sorted string
     * @param observablelist given list of current addressBook
     * @return String to be displayed
     */
    public static String parseBirthdaysForNotification(ObservableList<Person> observablelist, LocalDate currentDate) {
        StringBuilder string = new StringBuilder();
        List<Person> listOfPersonWithBirthdayToday = new ArrayList<Person>();

        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        if (observablelist == null) {
            return " ";
        } else if (observablelist.size() <= 0) {
            string.append("StardyTogether has no contacts :(\n");
            return string.toString();
        }

        for (Person person: observablelist) {
            if (person.getBirthday().getDay() == currentDay
                    && person.getBirthday().getMonth() == currentMonth) {
                listOfPersonWithBirthdayToday.add(person);
            }
        }

        for (Person person: listOfPersonWithBirthdayToday) {
            int age;
            age = currentYear - person.getBirthday().getYear();
            string.append(person.getName().toString());
            string.append(" (");
            string.append(age);
            if (age != 1) {
                string.append(" years old)");
            } else if (age > 0) {
                string.append(" years old)");
            }
            string.append("\n");
        }

        return string.toString();
    }
}
