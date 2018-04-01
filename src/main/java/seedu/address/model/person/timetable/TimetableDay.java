package seedu.address.model.person.timetable;

import seedu.address.commons.util.timetable.Lesson;

public class TimetableDay {

    // Cut into 24-h slots. 0000 being timetableSlots[0] and 2300 being timetableSlots[23]
    private TimetableSlot[] timetableSlots;

    public TimetableDay(){
        timetableSlots = new TimetableSlot[24];
        for (TimetableSlot timetableSlot: timetableSlots){
            timetableSlot = new TimetableSlot();
        }
    }

    public void addLessontoDay(Lesson lesson){
        int startTimeIndex = parseStartEndTime(lesson.getStartTime());
        int endTimeIndex = parseStartEndTime(lesson.getEndTime());

        for (int i = startTimeIndex; i < endTimeIndex; i++){
            timetableSlots[i].addLessonToSlot(lesson);
        }
    }

    private int parseStartEndTime(String time){
        int value = Integer.parseInt(time);

        return value/100;
    }
}
