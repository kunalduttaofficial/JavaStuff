package tw.conference.manager.service;

import tw.conference.manager.domain.input.Talk;
import tw.conference.manager.domain.output.Event;
import tw.conference.manager.domain.output.EventType;
import tw.conference.manager.domain.output.Session;
import tw.conference.manager.domain.output.Track;
import tw.conference.manager.exception.InvalidTalkException;
import tw.conference.manager.util.ConferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class EventOrganiser {

    public Track getScheduledTrack(List<Talk> plannedTalks) throws InvalidTalkException {
        Track currentTrack = new Track();
        //Creates morning session
        createSession(Session.MORNING, plannedTalks, currentTrack);
        //Creates lunch break
        createSession(Session.LUNCH, plannedTalks, currentTrack);
        //Creates afternoon session
        createSession(Session.AFTERNOON, plannedTalks, currentTrack);
        //Creates networking event
        createSession(Session.NETWORKING, plannedTalks, currentTrack);

        return currentTrack;
    }

    private void createSession(Session session, List<Talk> plannedTalks, Track currentTrack) throws InvalidTalkException {

        List<Talk> scheduledTalks = new ArrayList<>(); //List to remove from the main list after each session creation
        List<Event> scheduledEvents = new ArrayList<>(); //Event list to add into Track
        int remainingTalkDuration = ConferenceUtil.getPlannedTalksDuration(plannedTalks);//Get the sum of duration of the remaining talks
        int sessionMinutesRemaining = ConferenceUtil.getSessionMinutes(session, remainingTalkDuration);//Get session length based on remaining talk duration
        //To get the time of the day in string format for output
        String eventStartTime = ConferenceUtil.getNextScheduledTime(ConferenceUtil.NINE_O_CLOCK, currentTrack.getMinutesCovered());

        switch (session) {
            case LUNCH:
                if (currentTrack.getMinutesCovered() < 180) { //in case the remaining talks doesn't cover till 12:00 PM
                    currentTrack.setMinutesCovered(180);
                    sessionMinutesRemaining = 0;
                    eventStartTime = "12:00PM";
                }
                scheduledEvents.add(new Event(EventType.LUNCH, sessionMinutesRemaining, "Lunch Break", eventStartTime));
                currentTrack.setMinutesCovered(currentTrack.getMinutesCovered() + sessionMinutesRemaining);
                break;
            case NETWORKING:
                if (currentTrack.getMinutesCovered() < 420) { //in case the remaining talks doesn't cover till 04:00 PM
                    currentTrack.setMinutesCovered(420);
                    sessionMinutesRemaining = 0;
                    eventStartTime = "04:00PM";
                }
                scheduledEvents.add(new Event(EventType.NETWORKING, sessionMinutesRemaining, "Networking Event", eventStartTime));
                currentTrack.setMinutesCovered(currentTrack.getMinutesCovered() + sessionMinutesRemaining);
                break;
            default:
                for (int i = plannedTalks.size() - 1; i >= 0; i--) {

                    Talk currentTalk = plannedTalks.get(i);

                    if(currentTalk.getDuration() < 5){
                        throw new  InvalidTalkException("Talk length more than 5 minutes");
                    }

                    //If remaining time in session is more than or equal to incoming talk length, add it.
                    //Break the loop if they are equal as the session will be full
                    if (sessionMinutesRemaining >= currentTalk.getDuration()) {
                        sessionMinutesRemaining =
                                addNewEvent(scheduledTalks, scheduledEvents, sessionMinutesRemaining, currentTalk,
                                        currentTrack.getMinutesCovered());
                        currentTrack.setMinutesCovered(currentTrack.getMinutesCovered() + currentTalk.getDuration());
                        if(sessionMinutesRemaining == 0){
                            break;
                        }
                    } else {
                        //If remaining time in session is less than the incoming talk length, do nothing and move to next
                        //as the list is sorted in descending order, gradually there will be a smaller talk which will match
                        //the remaining time and break the loop
                        continue;
                    }
                }
        }

        plannedTalks.removeAll(scheduledTalks); //Remove the talks that are already scheduled
        currentTrack.getEventList().addAll(scheduledEvents); //Add the events to current Track
    }

    private int addNewEvent(List<Talk> scheduledTalks,
            List<Event> scheduledEvents,
            int sessionMinutesRemaining,
            Talk currentTalk,
            int trackMinutesCovered) {
        //Add a new event(talk) into a session and also deduct the duration from remaining minutes in the session
        String eventStartTime = ConferenceUtil.getNextScheduledTime(ConferenceUtil.NINE_O_CLOCK, trackMinutesCovered);
        scheduledTalks.add(currentTalk);
        scheduledEvents.add(new Event(EventType.TALK, currentTalk.getDuration(), currentTalk.getTitle(), eventStartTime));
        sessionMinutesRemaining -= currentTalk.getDuration();

        return sessionMinutesRemaining;
    }

}
