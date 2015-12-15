package EventManagment;


public abstract class Event {
    private final int eventId;
    private int eventSeverity; // важность события
    private String eventText; // собственно текст который отображается пользователю
    private int eventType; // тип - устройство\служба\косяк в транспорте 1С и тд.
    private int incId; // если >=0 - это устройство и тут лежит deviceID, если меньше = это другой источник(и может быть простая заглушка в виде -1)
    private boolean eventStatus; // заранее для Acknowledge.
    
    public Event() {
        this.eventId = -1;
    }

    public int getEventId() {
        return eventId;
    }

    public int getEventSeverity() {
        return eventSeverity;
    }

    public String getEventText() {
        return eventText;
    }

    public int getEventType() {
        return eventType;
    }

    public boolean isEventStatus() {
        return eventStatus;
    }

}
