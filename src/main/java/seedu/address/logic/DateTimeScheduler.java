package seedu.address.logic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import seedu.address.model.Model;

//@@author Kyomian
/**
 * A scheduler that runs every 30 seconds to check if tasks and events have passed their
 * due dates and end dates respectively.
 */
public class DateTimeScheduler {

    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /**
     * Initialises the scheduler.
     */
    public static void initialise(Model model) {
        OverdueChecker checker = new OverdueChecker(model);
        executor.scheduleWithFixedDelay(checker, 0, 30, TimeUnit.SECONDS);
    }
}
