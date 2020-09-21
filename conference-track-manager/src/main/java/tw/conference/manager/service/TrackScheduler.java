package tw.conference.manager.service;

import tw.conference.manager.domain.input.Talk;
import tw.conference.manager.domain.output.Track;
import tw.conference.manager.exception.InvalidTalkException;

import java.util.ArrayList;
import java.util.List;

public class TrackScheduler {

    private EventOrganiser eventOrganiser = new EventOrganiser();

    public List<Track> scheduleTracks(List<Talk> plannedTalks) throws InvalidTalkException {

        List<Track> scheduledTracks = new ArrayList<>();

        while(plannedTalks.size() > 0){
            //The organiser keeps on removing talks from the input list which are already scheduled,
            // that's why the while loop runs till the list size is zero i.e. all talks are accommodated in some Track
            scheduledTracks.add(eventOrganiser.getScheduledTrack(plannedTalks));
        }

        return scheduledTracks;
    }

}
