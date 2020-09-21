package tw.conference.manager.util;

import tw.conference.manager.domain.input.Talk;
import tw.conference.manager.domain.output.Event;
import tw.conference.manager.domain.output.Session;
import tw.conference.manager.domain.output.Track;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConferenceUtil {

    private static final int MORNING_SESSION_DURATION_MINUTES   = 180;
    private static final int EVENT_DURATION_MINUTES             = 60;
    private static final int AFTERNOON_SESSION_DURATION_MINUTES = 240;
    private static final int LIGHTNING_TALK_DURATION_MINUTES    = 5;

    public static final LocalTime NINE_O_CLOCK = LocalTime.parse("09:00");

    /**
     * To get the duration of the given talk.
     *
     * @param talkTitleWithDuration
     * @return
     */
    public static int getDuration(String talkTitleWithDuration) {
        int duration = 0;
        String talkDuration =
                talkTitleWithDuration.substring(talkTitleWithDuration.length() - 5, talkTitleWithDuration.length() - 3);
        try {
            duration = Integer.parseInt(talkDuration);
        } catch (NumberFormatException e) {
            if (talkTitleWithDuration.length() > 9) {
                talkDuration = talkTitleWithDuration.substring(talkTitleWithDuration.length() - 9);
                if ("lightning".equals(talkDuration)) {
                    duration = LIGHTNING_TALK_DURATION_MINUTES;
                }
            }
        }
        return duration;
    }

    /**
     * To get total duration of talks of the given list.
     *
     * @param talksList
     * @return
     */
    public static int getPlannedTalksDuration(List<Talk> talksList) {
        int totalTime = 0;

        if (talksList == null || talksList.isEmpty())
            return totalTime;

        totalTime = talksList.stream().mapToInt(t -> t.getDuration()).sum();

        return totalTime;
    }

    /**
     * To print all the tracks in the conference.
     *
     * @param scheduledTracks
     * @return
     */
    public static void printScheduledTracks(List<Track> scheduledTracks) {
        int count = 1;
        for (Track track : scheduledTracks) {
            System.out.println("Track " + count + ":");
            for (Event event : track.getEventList()) {
                System.out.println(event.getStartTime() + ": " + event.getTitle());
            }
            count++;
        }
    }

    /**
     * To get the duration of a session in minutes
     *
     * @param session
     * @param minutesRemaining
     * @return
     */
    public static int getSessionMinutes(Session session, int minutesRemaining) {
        int sessionMinutes = 0;

        switch (session) {
            case MORNING:
                sessionMinutes = MORNING_SESSION_DURATION_MINUTES;
                break;
            case LUNCH:
            case NETWORKING:
                sessionMinutes = EVENT_DURATION_MINUTES;
                break;
            case AFTERNOON:
                sessionMinutes = (minutesRemaining >= AFTERNOON_SESSION_DURATION_MINUTES) ? AFTERNOON_SESSION_DURATION_MINUTES :
                        ((minutesRemaining < MORNING_SESSION_DURATION_MINUTES) ? MORNING_SESSION_DURATION_MINUTES :
                                minutesRemaining);
                break;
            default:
                break;
        }

        return sessionMinutes;
    }

    /**
     * To get next scheduled time in form of String.
     *
     * @param startTime
     * @param timeDuration
     * @return
     */
    public static String getNextScheduledTime(LocalTime startTime, int timeDuration) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter //
                .ofPattern("hh:mma ") //
                .withZone(ZoneId.systemDefault());

        LocalTime newStartTime = startTime.plusMinutes(timeDuration);

        startTime = newStartTime;

        String timeString = DATE_TIME_FORMATTER.format(startTime);

        return timeString;
    }
}
