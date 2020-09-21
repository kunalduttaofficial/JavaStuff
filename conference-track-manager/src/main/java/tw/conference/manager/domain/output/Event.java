package tw.conference.manager.domain.output;

public class Event {

    private EventType type;
    private int       duration;
    private String    title;
    private String    startTime;

    public Event(EventType type, int duration, String title, String startTime) {
        this.type = type;
        this.duration = duration;
        this.title = title;
        this.startTime = startTime;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", duration=" + duration +
                ", title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}
