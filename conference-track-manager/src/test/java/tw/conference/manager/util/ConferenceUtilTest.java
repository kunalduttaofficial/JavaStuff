package tw.conference.manager.util;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tw.conference.manager.domain.input.Talk;
import tw.conference.manager.domain.output.Session;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConferenceUtilTest {

    private List<Talk> testTalkList;

    private static final LocalTime NINE_O_CLOCK = LocalTime.parse("09:00");

    @BeforeMethod
    public void setUp() {
        testTalkList = new ArrayList<>();
        testTalkList.add(new Talk("Introduction to Springboot", 60));
        testTalkList.add(new Talk("Introduction to ReactJS", 45));
        testTalkList.add(new Talk("Clean Coding Practices", 30));
        testTalkList.add(new Talk("Why Data Science?", 5));
    }

    @Test
    public void shouldReturnCorrectDurationForValidInput() {
        int numericDuration = ConferenceUtil.getDuration("A World Without HackerNews 30min");

        Assert.assertEquals(numericDuration, 30);
    }

    @Test
    public void shouldReturnFiveMinDurationForLightningInput() {
        int numericDuration = ConferenceUtil.getDuration("Rails for Python Developers lightning");

        Assert.assertEquals(numericDuration, 5);
    }

    @Test
    public void shouldReturnZeroMinutesDurationForLightningInput() {
        int numericDuration = ConferenceUtil.getDuration("Batman does not kill anyone");

        Assert.assertEquals(numericDuration, 0);
    }

    @Test
    public void shouldReturnCorrectSumOfDuration() {
        int numericDuration = ConferenceUtil.getPlannedTalksDuration(testTalkList);

        Assert.assertEquals(numericDuration, 140);
    }

    @Test
    public void shouldReturnCorrectSessionMinutesForMorningSession() {
        int numericDuration = ConferenceUtil.getSessionMinutes(Session.MORNING, 480);

        Assert.assertEquals(numericDuration, 180);
    }

    @Test
    public void shouldReturnCorrectSessionMinutesForLunchBreak() {
        int numericDuration = ConferenceUtil.getSessionMinutes(Session.LUNCH, 300);

        Assert.assertEquals(numericDuration, 60);
    }

    @Test
    public void shouldReturnCorrectSessionMinutesForAfternoonSession() {
        int numericDuration = ConferenceUtil.getSessionMinutes(Session.AFTERNOON, 240);

        Assert.assertEquals(numericDuration, 240);
    }

    @Test
    public void shouldReturnCorrectSessionMinutesForNetworkingEvent() {
        int numericDuration = ConferenceUtil.getSessionMinutes(Session.NETWORKING, 0);

        Assert.assertEquals(numericDuration, 60);
    }

    @Test
    public void shouldReturn180MinutesForAfternoonSessionIfRemainingTalkLastsLessThan3Hours() {
        int numericDuration = ConferenceUtil.getSessionMinutes(Session.AFTERNOON, 160);

        Assert.assertEquals(numericDuration, 180);
    }

    @Test
    public void shouldReturnExactTimeForAfternoonSessionIfRemainingTimeLastsBetween3And4Hours() {
        int numericDuration = ConferenceUtil.getSessionMinutes(Session.AFTERNOON, 200);

        Assert.assertEquals(numericDuration, 200);
    }

    @Test
    public void shouldReturnCorrectTimeOfTheDay() {
        String timeOfTheDay = ConferenceUtil.getNextScheduledTime(NINE_O_CLOCK, 180);

        Assert.assertEquals(timeOfTheDay.trim(), "12:00PM");
    }

}
