package seedu.progresschecker.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.ReadOnlyProgressChecker;
import seedu.progresschecker.model.exercise.Exercise;
import seedu.progresschecker.model.exercise.ModelAnswer;
import seedu.progresschecker.model.exercise.Question;
import seedu.progresschecker.model.exercise.QuestionIndex;
import seedu.progresschecker.model.exercise.QuestionType;
import seedu.progresschecker.model.exercise.StudentAnswer;
import seedu.progresschecker.model.exercise.exceptions.DuplicateExerciseException;
import seedu.progresschecker.model.person.Email;
import seedu.progresschecker.model.person.GithubUsername;
import seedu.progresschecker.model.person.Major;
import seedu.progresschecker.model.person.Name;
import seedu.progresschecker.model.person.Person;
import seedu.progresschecker.model.person.Phone;
import seedu.progresschecker.model.person.Year;
import seedu.progresschecker.model.person.exceptions.DuplicatePersonException;
import seedu.progresschecker.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ProgressChecker} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                new GithubUsername("AlexGithub"), new Major("Computer Science"), new Year("2"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@gmail.com"),
                new GithubUsername("BerniceGithub"), new Major("Computer Engineering"), new Year("2"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@gmail.com"),
                new GithubUsername("CharlotteGithub"), new Major("Information Security"), new Year("2"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@gmail.com"),
                new GithubUsername("DavidGithub"), new Major("Computer Engineering"), new Year("2"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@gmail.com"),
                new GithubUsername("IrfanGithub"), new Major("Computer Science"), new Year("2"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@gmail.com"),
                new GithubUsername("RoyGithub"), new Major("Computer Science"), new Year("2"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyProgressChecker getSampleProgressChecker() {
        try {
            ProgressChecker sampleAb = new ProgressChecker();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Exercise sampleExercise : getSampleExercises()) {
                sampleAb.addExercise(sampleExercise);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateExerciseException e) {
            throw new AssertionError("sample data cannot contain duplicate exercises", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    //@@author iNekox3
    public static Exercise[] getSampleExercises() {
        return new Exercise[] {
            // week 11
            new Exercise(new QuestionIndex("11.1.1"), new QuestionType("choice"),
                new Question("What is the main difference between a class diagram and and an OO domain model?\n"
                        + "a. One is about the problem domain while the other is about the solution domain.\n"
                        + "b. One has more classes than the other.\n"
                        + "c. One shows more details than the other.\n"
                        + "d. One is a UML diagram, while the other is not a UML diagram."),
                new StudentAnswer(""),
                new ModelAnswer("a. Both are UML diagrams, and use the class diagram notation."
                        + "While it is true that often a class diagram may have more classes and more details,"
                        + "the main difference is that the OO domain model describes the problem domain"
                        + "while the class diagram describes the solution.")),
            new Exercise(new QuestionIndex("11.3.1"), new QuestionType("text"),
                new Question("Here are some common elements of a design pattern: "
                        + "Name, Context, Problem, Solution, Anti-patterns (optional), Consequences (optional),"
                        + "other useful information (optional).\n"
                        + "\n"
                        + "Using similar elements, describe a pattern that is not a design pattern."
                        + "It must be a pattern you have noticed, not a pattern already documented by others."
                        + "You may also give a pattern not related to software.\n"
                        + "\n"
                        + "Some examples:\n"
                        + "- A pattern for testing textual UIs.\n"
                        + "- A pattern for striking a good bargain at a mall such as Sim-Lim Square."),
                new StudentAnswer(""),
                new ModelAnswer("No suggested answer.")),
            new Exercise(new QuestionIndex("11.4.1"), new QuestionType("choice"),
                new Question("Applying the heuristics covered so far, we can determine the precise number of "
                        + "test cases required to test any given SUT effectively.\n"
                        + "\n"
                        + "a. True\n"
                        + "b. False"),
                new StudentAnswer(""),
                new ModelAnswer("b. False. These heuristics are, well, heuristics only."
                        + "They will help you to make better decisions about test case design."
                        + "However, they are speculative in nature (especially, when testing in black-box fashion)"
                        + "and cannot give you precise number of test cases.")),
            new Exercise(new QuestionIndex("11.4.2"), new QuestionType("choice"),
                new Question("Which of these contradict the heuristics recommended "
                        + "when creating test cases with multiple inputs?\n"
                        + "\n"
                        + "a. All invalid test inputs must be tested together.\n"
                        + "b. It is ok to combine valid values for different inputs.\n"
                        + "c. No more than one invalid test input should be in a given test case.\n"
                        + "d. Each valid test input should appear at least once "
                        + "in a test case that doesn’t have any invalid inputs."),
                new StudentAnswer(""),
                new ModelAnswer("a. If you test all invalid test inputs together,"
                        + "you will not know if each one of the invalid inputs are handled correctly by the SUT. "
                        + "This is because most SUTs return an error message "
                        + "upon encountering the first invalid input.")),
            new Exercise(new QuestionIndex("11.6.1"), new QuestionType("choice"),
                new Question("Choose the correct statements about agile processes.\n"
                        + "\n"
                        + "a. They value working software over comprehensive documentation.\n"
                        + "b. They value responding to change over following a plan.\n"
                        + "c. They may not be suitable for some type of projects.\n"
                        + "d. XP and Scrum are agile processes."),
                new StudentAnswer(""),
                new ModelAnswer("a b c d.")),
            new Exercise(new QuestionIndex("11.7.1"), new QuestionType("choice"),
                new Question("Choose the correct statements about the unified process.\n"
                        + "\n"
                        + "a. It was conceived by the three amigos who also created UML.\n"
                        + "b. The Unified process requires the use of UML.\n"
                        + "c. The Unified process is actually a process framework rather than a fixed process.\n"
                        + "d. The Unified process can be iterative and incremental"),
                new StudentAnswer(""),
                new ModelAnswer("a c d. Although UP was created by the same three amigos who created UML,"
                        + "the UP does not require UML."))
        };
    }

}
