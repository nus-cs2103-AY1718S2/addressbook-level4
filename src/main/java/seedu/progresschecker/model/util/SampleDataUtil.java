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
            new Exercise(new QuestionIndex("3.1.1"), new QuestionType("choice"),
                new Question("Choose the correct statements\n"
                    + "\n"
                    + "a. Refactoring can improve understandability\n"
                    + "b. Refactoring can uncover bugs\n"
                    + "c. Refactoring can result in better performance\n"
                    + "d. Refactoring can change the number of methods/classes"),
                new StudentAnswer(""),
                new ModelAnswer("a b c d. (a, b, c) Although the primary aim of refactoring is to improve"
                    + "internal code structure, there are other secondary benefits. (d) Some refactorings"
                    + "result in adding/removing methods/classes.")),
            new Exercise(new QuestionIndex("3.1.2"), new QuestionType("text"),
                new Question("Do you agree with the following statement? Justify your answer.\n"
                    + "\n"
                    + "Statement: Whenever we refactor code to fix bugs,"
                    + "we need not do regression testing if the bug fix was minor."),
                new StudentAnswer(""),
                new ModelAnswer("DISAGREE. Even a minor change can have major repercussions on the system."
                    + "We MUST do regression testing after each change, no matter how minor it is."
                    + "Fixing bugs is technically not refactoring.")),
            new Exercise(new QuestionIndex("3.1.3"), new QuestionType("text"),
                new Question("Explain what is refactoring and "
                    + "why it is not the same as rewriting, bug fixing, or adding features."),
                new StudentAnswer(""),
                new ModelAnswer(""))
        };
    }

}
