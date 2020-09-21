package tw.conference.manager.domain.output;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private List<Event> eventList;
    private int minutesCovered;

    public Track() {
        this.eventList = new ArrayList<>();
        this.minutesCovered = 0;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public int getMinutesCovered() {
        return minutesCovered;
    }

    public void setMinutesCovered(int minutesCovered) {
        this.minutesCovered = minutesCovered;
    }
}
