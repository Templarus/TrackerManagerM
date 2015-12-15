package EventManagment;

public class DeviceEvent extends Event {

    private final int eventId;
    private int eventSeverity; // важность события
    private String eventText; // собственно текст который отображается пользователю
    private int deviceId; // если >=0 - это устройство и тут лежит deviceID, если меньше = это другой источник(и может быть простая заглушка в виде -1)
    private boolean eventStatus; // заранее для Acknowledge.

    /**
     * для устройств
     *
     * @param eventId
     * @param eventSeverity
     * @param deviceId
     * @param eventText
     */
    public DeviceEvent(int eventId, int eventSeverity, int deviceId, String eventText) {
        this.eventId = eventId;
        this.eventSeverity = eventSeverity;
        this.deviceId = deviceId;
        this.eventText = eventText;
    }

    public DeviceEvent() {
        this.eventId = -1;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEventStatus() {
        return eventStatus;
    }

    /**
     *
     * @return
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     *
     * @return
     */
    @Override
    public int getEventId() {
        return eventId;
    }

    /**
     *
     * @return
     */
    @Override
    public int getEventSeverity() {
        return eventSeverity;
    }

    /**
     *
     * @return
     */
    @Override
    public String getEventText() {
        return eventText;
    }

}
