package EventManagment;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RusTe
 *
 */
public class EventLogger {

    private LinkedList<Event> eventLog;
    private List<String> eventTexts;

    public LinkedList<Event> getEventLog() {
        return eventLog;
    }

    public EventLogger() {
        loadDataFromDb();
    }

    /**
     *
     * @param event
     * @return
     */
    public boolean addDeviceEvent(DeviceEvent event) {

        if (!eventLog.contains(event)) {
            return eventLog.add(event);
        }
        return false;
    }

    /**
     *
     * @param eventID
     * @return
     */
    public boolean removeEvent(int eventID) {
        for (Event ev : eventLog) {
            if (ev.getEventId() == eventID) {
                return eventLog.remove(ev);
            }
        }
        return false;
    }

    /**
     *
     * @param eventType 0- DeviceEvent, 1 -AppEvent
     * @param eventSeverity важность события (0-5) 0-передача сообщений,
     * 1-технические\инфо, 2-4 = важность
     * @param source если >=0 - это устройство и тут лежит deviceID, если меньше
     * = это другой источник(и может быть простая заглушка в виде -1)
     * @param eventTextId собственно текст который отображается пользователю - в
     * данном случае id для выборки
     * @return
     */
    public boolean createNewEvent(int eventType, int eventSeverity, int source, int eventTextId) {

        switch (eventType) {
            case 0:
                DeviceEvent dev = createDeviceEvent(eventSeverity, source, eventTextId);
                if (!eventLog.contains(dev)) {
                    return eventLog.add(dev);
                }
            case 1:
                AppEvent app = createAppEvent(eventSeverity, source, eventTextId);
                if (!eventLog.contains(app)) {
                    return eventLog.add(app);
                }
        }

        return false;
    }

    /**
     *
     * @param eventType 0- DeviceEvent, 1 -AppEvent
     * @param eventSeverity важность события (0-5) 0-передача сообщений,
     * 1-технические\инфо, 2-4 = важность
     * @param source если >=0 - это устройство и тут лежит deviceID, если меньше
     * = это другой источник(и может быть простая заглушка в виде -1)
     * @param eventText собственно текст который отображается пользователю -
     * текст
     * @return
     */
    public boolean createNewEvent(int eventType, int eventSeverity, int source, String eventText) {

        switch (eventType) {
            case 0:
                DeviceEvent dev = createDeviceEvent(eventSeverity, source, eventText);
                if (!eventLog.contains(dev)) {
                    return eventLog.add(dev);
                }
            case 1:
                AppEvent app = createAppEvent(eventSeverity, source, eventText);
                if (!eventLog.contains(app)) {
                    return eventLog.add(app);
                }
        }

        return false;
    }

    /**
     *
     * @param eventSeverity
     * @param deviceId
     * @param eventTextId
     * @return
     */
    protected DeviceEvent createDeviceEvent(int eventSeverity, int deviceId, int eventTextId) {
        int num = eventLog.size();
        return new DeviceEvent(num, eventSeverity, deviceId, eventTexts.get(eventTextId));

    }

    /**
     *
     * @param eventSeverity
     * @param deviceId
     * @param eventText
     * @return
     */
    protected DeviceEvent createDeviceEvent(int eventSeverity, int deviceId, String eventText) {
        int num = eventLog.size();
        return new DeviceEvent(num, eventSeverity, deviceId, eventText);

    }

    /**
     *
     * @param eventSeverity
     * @param someId
     * @param eventTextId
     * @return
     */
    protected AppEvent createAppEvent(int eventSeverity, int someId, int eventTextId) {
        int num = eventLog.size();
        return new AppEvent(num, eventSeverity, -1, eventTexts.get(eventTextId));

    }

    /**
     *
     * @param eventSeverity
     * @param someId
     * @param eventText
     * @return
     */
    protected AppEvent createAppEvent(int eventSeverity, int someId, String eventText) {
        int num = eventLog.size();
        return new AppEvent(num, eventSeverity, -1, eventText);

    }

    /**
     *
     */
    protected void loadDataFromDb() // сюда нужно добить загрузку данных из БД + загрузку текстов в eventTexts
    {
        eventTexts.add("Application loaded successfully");
        eventTexts.add("MainForm: AddButtonActionPerformed clicked");
        eventTexts.add("MainForm: AddButtonActionPerformed executed");
        eventTexts.add("MainForm:Load Started");
        eventTexts.add("MainForm: load executed");
        eventTexts.add("MainForm: drawPanels executed");
        eventTexts.add("MainForm: watcher created");
    }

}
