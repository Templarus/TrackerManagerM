package EventManagment;

import java.util.LinkedList;
import java.util.List;

public class EventLogger {

    private LinkedList<Event> eventLog;
    private List<String> eventTexts;
    
    public LinkedList<Event> getEventLog() {
        return eventLog;
    }

    public EventLogger() {

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

    
    protected DeviceEvent createDeviceEvent(int eventSeverity, int deviceId, int eventTextId) {
        int num = eventLog.size();
        return new DeviceEvent(num, eventSeverity, deviceId, eventTexts.get(eventTextId));

    }

    protected AppEvent createAppEvent(int eventSeverity, int someId, int eventTextId) {
        int num = eventLog.size();
        return new AppEvent(num, eventSeverity, -1, eventTexts.get(eventTextId));

    }
    
    protected void loadDataFromDb() // сюда нужно добить загрузку данных из БД + загрузку текстов в eventTexts
    {
        
    }
    
   
}
