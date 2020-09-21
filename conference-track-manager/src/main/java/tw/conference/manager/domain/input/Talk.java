package tw.conference.manager.domain.input;


public class Talk {
    
    private String title;
    private Integer duration;

    public Talk(String title, Integer duration) {
        this.title = title;
        this.duration = duration;
    }
    
    public String getTitle() {
        return title;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    @Override
    public String toString() {
        return "Talk [title=" + title + ", duration=" + duration + "]";
    }
}
