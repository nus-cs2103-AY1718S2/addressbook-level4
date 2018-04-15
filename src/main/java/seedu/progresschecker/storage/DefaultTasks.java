package seedu.progresschecker.storage;

import seedu.progresschecker.model.task.SimplifiedTask;

//@@author EdwardKSG
/**
 * Contains information of the default tasks.
 * Using an object to save data to reduce the cost of file I/O.
 */
public class DefaultTasks {
    private static final String SUB = "[Submission]";
    private static final String COM = "[Compulsory]";
    private static final String STAR = "&#9733;";
    public static SimplifiedTask[] getDefaultTasks() {
        return new SimplifiedTask[] {
            // WEEK 2
            new SimplifiedTask("LO[W2.2]" + STAR,
                    "Can use basic features of an IDE (2.2 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week2/outcomes.html",
                    "01/25/2018 23:59"),
            new SimplifiedTask("LO[W2.3]" + SUB + STAR + STAR,
                    "Can use Java Collections: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level1#use-collections-lo-collections",
                    "01/25/2018 23:59"),
            new SimplifiedTask("LO[W2.4]" + SUB + STAR + STAR + STAR,
                    "Can use Java varargs feature: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level1#use-varargs-lo-varargss",
                    "01/25/2018 23:59"),
            new SimplifiedTask("LO[W2.5]" + STAR + STAR,
                    "Can automate simple regression testing of text UIs (2.5 a~d): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week2/outcomes.html",
                    "01/25/2018 23:59"),
            new SimplifiedTask("LO[W2.6]" + SUB + STAR,
                    "Can use Git to save history (2.6 a~f): checkurlhttps://www.sourcetreeapp.com/",
                    "01/25/2018 23:59"),

            // WEEK 3
            new SimplifiedTask("LO[W3.1]" + STAR + STAR,
                    "Can refactor code at a basic level (3.1 a~d, c needs submission): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.2]" + SUB + STAR + STAR,
                    "Can follow a simple style guide (3.2 a~d, c needs submission): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.3]" + STAR + STAR,
                    "Can improve code readability (3.3 a~d): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.4]" + STAR + STAR,
                    "Can use good naming (3.4 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.5]" + STAR + STAR,
                    "Can avoid unsafe coding practices (3.5 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.6]" + STAR + STAR + STAR,
                    "Can write good code comments (3.6 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.7]" + STAR + STAR + STAR,
                    "Can use intermediate level features of an IDE (3.7 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.8]" + SUB + STAR,
                    "Can communicate with a remote repo (3.8 a~d, c&d need submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/samplerepo-things",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.9]" + STAR + STAR + STAR,
                    "Can traverse Git history: checkurlhttps (3.9 a~d):"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week3/outcomes.html",
                    "02/01/2018 23:59"),
            new SimplifiedTask("LO[W3.10]" + COM + SUB + STAR,
                    "Can work with a 1KLoC code base: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level1",
                    "02/01/2018 23:59"),

            // WEEK 4
            new SimplifiedTask("LO[W4.1]" + STAR + STAR + STAR,
                    "Can explain models (4.1 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.2]" + STAR,
                    "Can explain OOP (4.2 a~e): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.3]" + STAR,
                    "Can explain basic object/class structures (4.3 a~c): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.4]" + STAR,
                    "Can implement (4.4 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week4/outcomes.html",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.5]" + SUB + STAR + STAR,
                    "Can do exception handling in code (4.5 a~d, c needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#handle-exceptions-lo-exceptions",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.6]" + SUB + STAR + STAR + STAR,
                    "Can use Java enumerations (4.6 a~b, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level1/#use-enums-lo-enums",
                    "02/08/2018 23:59"),
            new SimplifiedTask("LO[W4.7]" + SUB + STAR,
                    "Can create PRs on GitHub (4.7 a~e, all need submission: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/samplerepo-pr-practice",
                    "02/08/2018 23:59"),

            // WEEK 5
            new SimplifiedTask("LO[W5.1]" + STAR + STAR + STAR,
                    "Can use intermediate-level class diagrams (5.1 a~e): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.2]" + STAR + STAR + STAR,
                    "Can explain single responsibility principle: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#follow-the-single-responsibility-principle-lo-srp",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.3]" + SUB + STAR,
                    "Can implement inheritance (5.3 a~b, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#use-inheritance-to-achieve-code-reuse-lo-inheritance",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.4]" + SUB + STAR + STAR,
                    "Can implement class-level members (5.4 a~b, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#use-class-level-members-lo-classlevel",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.5]" + SUB + STAR + STAR + STAR,
                    "Can implement composition (5.5 a~b, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#implement-a-class-lo-implementclass",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.6]" + STAR + STAR + STAR,
                    "Can implement aggregation (5.6 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.7]" + STAR + STAR + STAR,
                    "Can implement overloading (5.7 a~b): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.8]" + STAR + STAR,
                    "Can explain requirements (5.8 a~d, b needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level3/blob/master/doc"
                            + "/LearningOutcomes.md#use-non-functional-requirements-lo-nfr",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.9]" + STAR + STAR + STAR,
                    "Can explain some techniques for gathering requirements (5.9 a~g): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week5/outcomes.html",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.10]" + SUB + STAR,
                    "Can use some techniques for specifying requirements (5.10 a~k, c needs submission): "
                            + "checkurlhttps://github.com/nus-cs2103-AY1718S2/addressbook-level3/blob/master/doc"
                            + "/LearningOutcomes.md#utilize-user-stories-lo-userstories",
                    "02/15/2018 23:59"),
            new SimplifiedTask("LO[W5.11]" + COM + SUB + STAR,
                    "Can work with a 2KLoC code base: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2",
                    "02/15/2018 23:59"),

            // WEEK 6
            new SimplifiedTask("LO[W6.1]" + SUB + STAR,
                    "Can use simple JUnit tests (6.1 a~e, e needs submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level2/blob/master/doc"
                            + "/LearningOutcomes.md#use-junit-to-implement-unit-tests-lo-junit",
                    "02/22/2018 23:59"),
            new SimplifiedTask("LO[W6.2]" + STAR,
                    "Can follow Forking Workflow (6.2 a~e): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week6/outcomes.html",
                    "02/22/2018 23:59"),
            new SimplifiedTask("LO[W6.3]" + STAR,
                    "Can interpret basic sequence diagrams (6.3 a~f): checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week6/outcomes.html",
                    "02/22/2018 23:59"),
            new SimplifiedTask("LO[W6.4]" + SUB + STAR,
                    "Can implement polymorphism (6.4 a~h, d~h need submission): checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level3/blob/master/doc"
                            + "/LearningOutcomes.md#use-polymorphism-lo-polymorphism",
                    "02/22/2018 23:59"),
            new SimplifiedTask("LO[W6.5]" + SUB + STAR + STAR + STAR,
                    "Can use JavaFX to build a simple GUI: checkurlhttps:"
                            + "//github.com/nus-cs2103-AY1718S2/addressbook-level3/blob/master/doc"
                            + "/LearningOutcomes.md#use-java-fx-for-gui-programming-lo-javafx",
                    "02/22/2018 23:59"),

            // RECESS

            // WEEK 7
            new SimplifiedTask("LO[W7.1]" + STAR,
                    "Can record requirements of a product: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week7/outcomes.html",
                    "03/08/2018 23:59"),

            // WEEK 8
            new SimplifiedTask("LO[W8.1]" + STAR + STAR + STAR,
                    "Can apply basic product design guidelines: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week8/outcomes.html",
                    "03/15/2018 23:59"),

            // WEEK 9
            new SimplifiedTask("LO[W9.1]" + STAR + STAR,
                    "Can use models to conceptualize an OO solution: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week9/outcomes.html",
                    "03/22/2018 23:59"),

            // WEEK 10
            new SimplifiedTask("LO[W10.1]" + STAR + STAR + STAR,
                    "Can explain SE principles: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week10/outcomes.html",
                    "03/29/2018 23:59"),

            // WEEK 11
            new SimplifiedTask("LO[W11.1]" + STAR + STAR + STAR,
                    "Can explain object oriented domain models: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week11/outcomes.html",
                    "04/05/2018 23:59"),

            // WEEK 12
            new SimplifiedTask("LO[W12.1]" + STAR + STAR + STAR,
                    "Can explain some UML models: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week12/outcomes.html",
                    "04/12/2018 23:59"),

            // WEEK 13
            new SimplifiedTask("LO[W13.1]" + STAR,
                    "Can demo a product: checkurlhttps:"
                            + "//nus-cs2103-ay1718s2.github.io/website/schedule/week13/outcomes.html",
                    "04/19/2018 23:59"),

        };
    }
}
