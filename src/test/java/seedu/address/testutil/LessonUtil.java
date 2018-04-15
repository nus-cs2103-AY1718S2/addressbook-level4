package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.model.lesson.Lesson;

//@@author demitycho
/**
 * A utility class for Lesson.
 */
public class LessonUtil {

    /**
     * Returns an add command string for adding the {@code lesson}.
     */
    public static String getAddLessonCommand(Lesson lesson, Index targetIndex) {
        return AddLessonCommand.COMMAND_WORD + " " + getLessonDetails(lesson, targetIndex);
    }

    /**
     * Returns the part of command string for the given {@code lesson}'s details.
     */
    public static String getLessonDetails(Lesson lesson, Index targetIndex) {
        StringBuilder sb = new StringBuilder();
        sb.append(targetIndex + "  ");
        sb.append(PREFIX_DAY + lesson.getDay().value + " ");
        sb.append(PREFIX_START_TIME + lesson.getStartTime().value + " ");
        sb.append(PREFIX_END_TIME + lesson.getEndTime().value + " ");
        return sb.toString();
    }
}
