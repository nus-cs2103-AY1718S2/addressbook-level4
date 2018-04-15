package seedu.address.commons.util;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

//@@author Isaaaca
public class CalendarUtilTest {

    @Test
    public void getSem() {
        Assert.assertEquals(2, CalendarUtil.getSem(LocalDate.MIN));
        Assert.assertEquals(1, CalendarUtil.getSem(LocalDate.MAX));
    }

    @Test
    public void getCurrentSemester() {
        Assert.assertEquals(CalendarUtil.getSem(LocalDate.now()), CalendarUtil.getCurrentSemester());
    }

    @Test
    public void getAcadYear() {
        Assert.assertEquals(2016, CalendarUtil.getAcadYear(LocalDate.of(2017, 1, 1)));
        Assert.assertEquals(2017, CalendarUtil.getAcadYear(LocalDate.of(2017, 7, 1)));
    }

    @Test
    public void getCurrAcadYear() {
        Assert.assertEquals(CalendarUtil.getAcadYear(LocalDate.now()), CalendarUtil.getCurrAcadYear());
    }
}
