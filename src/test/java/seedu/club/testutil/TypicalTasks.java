package seedu.club.testutil;

//@@author yash-chowdhary

import static seedu.club.testutil.TypicalMembers.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.club.model.ClubBook;
import seedu.club.model.member.exceptions.DuplicateMatricNumberException;
import seedu.club.model.task.Task;
import seedu.club.model.task.exceptions.DuplicateTaskException;

/**
 * Utility class containing list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task BOOK_AUDITORIUM = new TaskBuilder()
            .withDescription("Book Auditorium")
            .withDate("02/05/2018")
            .withTime("13:00")
            .withAssignor("A8389539B")
            .withAssignee("A8389539B")
            .withStatus("Yet To Begin")
            .build();

    public static final Task BOOK_AUDITORIUM_COPY = new TaskBuilder()
            .withDescription("Book Auditorium")
            .withDate("02/05/2018")
            .withTime("13:00")
            .withAssignor("A8389539B")
            .withAssignee("A9210701B")
            .withStatus("Yet To Begin")
            .build();

    public static final Task BUY_CONFETTI = new TaskBuilder()
            .withDescription("Buy Confetti")
            .withDate("01/05/2018")
            .withTime("17:00")
            .withAssignor("A9210701B")
            .withAssignee("A9210701B")
            .withStatus("Yet To Begin")
            .build();

    public static final Task ADVERTISE_EVENT = new TaskBuilder()
            .withDescription("Advertise event")
            .withDate("12/05/2018")
            .withTime("19:00")
            .withAssignor("A9210701B")
            .withAssignee("A9210701B")
            .withStatus("Yet To Begin")
            .build();

    public static final Task BUY_FOOD = new TaskBuilder()
            .withDescription("Buy Food")
            .withDate("02/05/2018")
            .withTime("19:00")
            .withAssignor("A9210701B")
            .withAssignee("A9210701B")
            .withStatus("Yet To Begin")
            .build();

    private TypicalTasks() {}

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(BOOK_AUDITORIUM, BUY_CONFETTI, ADVERTISE_EVENT, BUY_FOOD));
    }

    /**
     * Returns an {@code AddressBook} with one person and all typical orders.
     */
    public static ClubBook getTypicalClubBookWithTasks() {
        ClubBook clubBook = new ClubBook();

        try {
            clubBook.addMember(ALICE);
        } catch (DuplicateMatricNumberException dmne) {
            throw new AssertionError("not possible");
        }

        for (Task task : getTypicalTasks()) {
            try {
                clubBook.addTaskToTaskList(task);
            } catch (DuplicateTaskException dte) {
                throw new AssertionError("not possible");
            }
        }
        return clubBook;
    }
}
