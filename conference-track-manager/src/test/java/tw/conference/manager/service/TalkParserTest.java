package tw.conference.manager.service;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tw.conference.manager.domain.input.Talk;

import java.util.List;

public class TalkParserTest {

    private TalksParser parser;

    @BeforeMethod
    public void setUp() {
        parser = new TalksParser();
    }

    @Test
    public void shouldParseAllEntriesFromTextFile(){
        List<Talk> talkList = parser.getPlannedTalks("src/test/resources/Talks.txt");

        Assert.assertEquals(talkList.size(), 19);
    }

    @Test
    public void shouldParseAllValidEntriesFromTextFile(){
        List<Talk> talkList = parser.getPlannedTalks("src/test/resources/TalksWithInvalidEntries.txt");

        Assert.assertEquals(talkList.size(), 16);
    }

}
