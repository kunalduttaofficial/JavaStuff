package tw.conference.manager.service;

import tw.conference.manager.domain.input.Talk;
import tw.conference.manager.domain.output.Track;
import tw.conference.manager.exception.InvalidTalkException;
import tw.conference.manager.util.ConferenceUtil;

import java.util.List;

public class ConferenceOrganiser {

    private TalksParser parser = new TalksParser();

    private TrackScheduler scheduler = new TrackScheduler();

    public void scheduleTracks(String fileName) {
        //Getting the talks to be planned from the input text file
        List<Talk> plannedTalks = parser.getPlannedTalks(fileName);

        if(plannedTalks != null && plannedTalks.size() > 0){
            //Passing the sorted list of talks to create Tracks
            List<Track> scheduledTracks = null;
            try {
                scheduledTracks = scheduler.scheduleTracks(plannedTalks);
            } catch (InvalidTalkException e) {
                System.out.println("The input talk is invalid, reason: " + e.getMessage());
                e.printStackTrace();
            }

            if(scheduledTracks != null && scheduledTracks.size() > 0){
                //Printing the result Tracks
                ConferenceUtil.printScheduledTracks(scheduledTracks);
            }
        }
    }
}
