package tw.conference.manager.service;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tw.conference.manager.domain.input.Talk;
import tw.conference.manager.domain.output.Track;
import tw.conference.manager.exception.InvalidTalkException;

import java.util.ArrayList;
import java.util.List;

public class TrackSchedulerTest {

    private List<Talk> testTalkList;

    private TrackScheduler scheduler;

    @BeforeMethod
    public void setUp() {
        testTalkList = new ArrayList<>();
        testTalkList.add(new Talk("Introduction to Springboot", 180));
        testTalkList.add(new Talk("Introduction to ReactJS", 180));
        testTalkList.add(new Talk("Clean Coding Practices", 180));
        testTalkList.add(new Talk("Why Data Science?", 180));

        scheduler = new TrackScheduler();
    }

    @Test
    public void shouldReturnMultipleTracksIfTotalDurationExceeds420Minutes(){
        List<Track> trackList = null;
        try {
            trackList = scheduler.scheduleTracks(testTalkList);
        } catch (InvalidTalkException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(trackList.size(), 2);
    }

}
