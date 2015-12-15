package trekermanager;

import UI.Start;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

// класс с пустым конструктором - предусмотрен для наблюдением за статусом Listenerов в коллекции WatcherList в static объекте MainFrom(Start.mf)
// функционал для перезапуска при ручном добавлении нового устройства не доделан
public class Watcher implements Runnable {

    private Map DeviceList = new HashMap<String, Device>();
    private Map timeMap = new HashMap<Device, Long>();
    private Set<String> keys;
    private long time1;
    private long time2;
    private final int timeLimit = 240000;

    public Watcher() {

    }

    private void Check() {

        while (true) {
            keys = Start.mf.getDevicesKeySet();
            DeviceList = Start.mf.getDeviceList(); // заполняем локальные коллекциями элементами родительских
            timeMap = Start.mf.getTimeMap();
            System.out.println("-----------------------------------------------");
            for (String key : keys) {
                Device device = (Device) DeviceList.get(key); // вытаскиваем device из DeviceList по ключу, чтобы затем использовать в качестве ключа в коллекции WatcherList и вернуть статус

                time1 = (Long) timeMap.get(device);
                time2 = System.currentTimeMillis();
                if (Start.mf.getWatcherStatus(device)) { // СЮДА НУЖНО БЫ ДОБАВИТЬ ПРОВЕРКУ "ВКЛЮЧЕННОСТИ" УСТРОЙСТВА
                    System.out.println("Wathcer: device=" + key + " time difference=" + (time2 - time1));
                    if (time2 - time1 > timeLimit) {
                        System.err.println("Wathcer: TIMELIMIT OVERLAPTED :" + key);
                        Start.mf.setWatcherStatus(device, false);
                        closeListener(device);
                    }
                } else {
                    System.out.println("Wathcer: recreating DeviceListener for device " + key);
                  
                    Start.mf.createListener(device);// если false - создаём listener с этим устройством
                }
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

            try {
                Thread.sleep(5000); // чтобы не вешать систему проверка проходит раз в 10 секунд
            } catch (InterruptedException ex) {
                System.err.println("Watcher: Threading in close( sleep(5000) : " + ex.getMessage());

            }
        }
    }

    @Override
    public void run() {
        Check();
    }

    private void closeListener(Device device) {//метод закрывающий текущий листенер
        Start.mf.deviceStatus(device.getId(), false);
        Start.mf.deviceConnection(device.getId(), false);
        System.err.println("Watcher:  close( ss.close()) " + device.getId());
        //System.err.println("Watcher: IOException in close( ss.close()) " + device.getId() + " : " + ex.getMessage());

    }
}
