package seedu.progresschecker.storage;

import seedu.progresschecker.model.task.SimplifiedTask;

//@@author EdwardKSG
/**
 * Contains information of test tasks.
 */
public class TestTasks {
    public static SimplifiedTask[] getTestTasks() {
        return new SimplifiedTask[] {
            new SimplifiedTask("LO[W3.10][Compulsory][Submission]&#9733;",
                "Work with a 1KLoC code base: checkurlhttps://nus-cs2103-ay1718s2.github.io/website/schedule"
                        + "/week3/outcomes.html",
                "02/01/2018 23:59"),
            new SimplifiedTask("LO[W4.1]&#9733;&#9733;&#9733;",
                "Can explain models: checkurlhttps://nus-cs2103-ay1718s2.github.io/website/schedule/week4"
                        + "/outcomes.html",
                "02/08/2018 23:59"),
            new SimplifiedTask("LO[W5.11][Compulsory][Submission]&#9733;",
                "Work with a 2KLoC code base: checkurlhttps://nus-cs2103-ay1718s2.github.io/website/schedule"
                        + "/week5/outcomes.html",
                "02/15/2018 23:59"),
            new SimplifiedTask("LO[W6.5][Submission]&#9733;&#9733;&#9733;",
                "Can use JavaFX to build a simple GUI: checkurlhttps://nus-cs2103-ay1718s2.github.io/website"
                        + "/schedule/week6/outcomes.html",
                "02/22/2018 23:59")
        };
    }

}
