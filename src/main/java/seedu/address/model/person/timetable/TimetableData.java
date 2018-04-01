package seedu.address.model.person.timetable;

import seedu.address.commons.util.timetable.Lesson;

import java.util.ArrayList;

public class TimetableData {

    public static final int EVEN_WEEK_INDEX = 0;
    public static final int ODD_WEEK_INDEX = 1;
    public static final String EVEN_WEEK_IDENTIFIER = "Even Week";
    public static final String ODD_WEEK_IDENTIFIER = "Odd Week";

    private TimetableWeek[] evenOddWeek;

    public TimetableData(){
        constructEmptyData();
    }

    public TimetableData(ArrayList<Lesson> lessonsToAdd){
        constructEmptyData();

        // Immediate adding of lessons
        for (Lesson lessonToAdd: lessonsToAdd){
            if (lessonToAdd.getWeekType().equalsIgnoreCase(EVEN_WEEK_IDENTIFIER)){
                evenOddWeek[EVEN_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            } else if (lessonToAdd.getWeekType().equalsIgnoreCase(ODD_WEEK_IDENTIFIER)){
                evenOddWeek[ODD_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            }
        }
    }

    private void constructEmptyData(){
        evenOddWeek = new TimetableWeek[2];
        for (TimetableWeek timetableWeek: evenOddWeek){
            timetableWeek = new TimetableWeek();
        }
    }

}
