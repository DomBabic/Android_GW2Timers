package hr.etfos.d1babic.guildwars2timers;

/**
 * Created by DominikZoran on 19.05.2016..
 */
public class WorldEvent {

    private int id;
    private String EventTitle;
    private int IconID;
    private String EventLocation;
    private String EventDescription;
    private String Timer;
    private String TimerShift;
    private int Subscribed;
    private long milliseconds;


    public WorldEvent(int id, String eventTitle, int iconID, String eventLocation, String eventDescription, String timer, String timerShift, int subscribed) {
        this.id = id;
        this.EventTitle = eventTitle;
        this.IconID = iconID;
        this.EventLocation = eventLocation;
        this.EventDescription = eventDescription;
        this.Timer = timer;
        this.TimerShift = timerShift;
        this.Subscribed = subscribed;
    }

    public WorldEvent(String eventTitle, int iconID, String eventLocation, String eventDescription, String timer, String timerShift, int subscribed) {
        this.EventTitle = eventTitle;
        this.IconID = iconID;
        this.EventLocation = eventLocation;
        this.EventDescription = eventDescription;
        this.Timer = timer;
        this.TimerShift = timerShift;
        this.Subscribed = subscribed;
    }


    public int getId() {
        return id;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }

    public int getIconID() {
        return IconID;
    }

    public void setIconID(int iconID) {
        IconID = iconID;
    }

    public String getEventLocation() {
        return EventLocation;
    }

    public void setEventLocation(String eventLocation) {
        EventLocation = eventLocation;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getTimer() {
        return Timer;
    }

    public void setTimer(String timer) {
        Timer = timer;
    }

    public String getTimerShift() {
        return TimerShift;
    }

    public void setTimerShift(String timerShift) {
        TimerShift = timerShift;
    }

    public int getSubscribed() {
        return Subscribed;
    }

    public void setSubscribed(int subscribed) {
        Subscribed = subscribed;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }
}
