/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventManagment;

public class AppEvent extends Event {

    private final int eventId;
    private int eventSeverity; // важность события
    private String eventText; // собственно текст который отображается пользователю
    private int sourceId; // если >=0 - это устройство и тут лежит deviceID, если меньше = это другой источник(и может быть простая заглушка в виде -1)
    private boolean eventStatus; // заранее для Acknowledge.

    /**
     * для всех НЕ устройст
     *
     * @param eventId
     * @param eventSeverity
     * @param sourceId
     * @param eventText
     */
    public AppEvent(int eventId, int eventSeverity, int sourceId, String eventText) {
        this.eventId = eventId;
        this.eventSeverity = eventSeverity;
        this.eventText = eventText;
        this.sourceId = sourceId;
    }

    /**
     * this.eventId = -1;
     */
    public AppEvent() {
        this.eventId = -1;
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
