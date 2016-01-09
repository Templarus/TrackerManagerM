package trekermanager;

import UI.Start;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

//Класс предназначенный для создания и обработки соединения с одним устройством
public class DeviceListener implements Runnable {

    private ServerSocket serverSocket;
    private Device device;
    private InputStream in;
    private OutputStream out;
    private int length = 0;
    private Socket clientSocket;
    private byte[] incoming;

    private boolean status = true;  // статус соединения\открытого ClientSocker

    public DeviceListener() {
        this(new Device()); // пустой конструктор Device вернёт элемент с стандартными данными
    }

    public DeviceListener(Device d) {
        this.device = d;
    }

    private void makeConnection() {
        try {
            serverSocket = new ServerSocket(device.getPort());
            serverSocket.setSoTimeout(110000);
            clientSocket = new Socket();
            System.out.println("DeviceListener: Listener " + device.getId() + " started. Port " + serverSocket.getLocalPort() + " status=" + clientSocket.toString());
        } catch (IOException ex) {
            System.err.println("DeviceListener: IOException in listener(clientSocket = serverSocket) " + device.getId() + " : " + ex.getMessage());
        }
        try {
            clientSocket = serverSocket.accept();
            clientSocket.setSoTimeout(80000);
        } catch (IOException ex) {
            System.err.println("DeviceListener: IOException in listener(clientSocket = serverSocket.accept()) " + device.getId() + " : " + ex.getMessage());
        }
        if (clientSocket.isConnected()) {

            status = Start.mf.getWatcherStatus(device); // в локальную переменную записываем значение из MainForm - изначально оно = true
            System.out.println("DeviceListener " + device.getId() + ": client socket got accept  status=" + clientSocket.toString());

            while (status) { //т.к. сокет был закрыт - возвращаем зачение false (которое должно быть уже задано в нужной коллекции

                Start.mf.deviceConnection(device.getId(), true); // если .accept прошёл - TCP соединение установлено
                try {
                    in = clientSocket.getInputStream();
                    out = clientSocket.getOutputStream();
                    incoming = new byte[8192]; // увеличил буффер до килобайта. а лучше конечно до 8
                    length = in.read(incoming);
                } catch (IOException ex) {
                    System.err.println("DeviceListener:IOException in listener(getInputStream) " + device.getId() + " : " + ex.getMessage());
                }

                Start.mf.setTime(device, System.currentTimeMillis()); // считаем, что пакет пришёл - значит нужно обновить время

                System.out.println("Client query(" + length + " bytes):" + new String(incoming).trim());

                Start.mf.sendMessageToBuffer(device, makeAnswer(incoming));

                // String reply = makeAnswer(incoming); // выполняется метод, который возвращает устройству требуемый ответ в зависимости от пришедшего пакета
                //if (reply.equals("empty")) { //если вернулось empty - устройство разорвало соединение со своей стороны.
                if (Start.mf.getMessagesFromBuffer(device).contains("empty")) {
                    System.err.println("DeviceListener: empty answer");
                    Start.mf.setWatcherStatus(device, false); // соединение разорвано и требуется запустить новый поток с новым Listener для этого порта и устройства
                    Start.mf.cleanMessageBuffer(device);
                } else {
                    for (String reply : Start.mf.getMessagesFromBuffer(device)) {
                        try {
                            out.write(reply.getBytes());
                        } catch (IOException ex) {
                            System.err.println("DeviceListener:IOException in listener(out.write) " + device.getId() + " : " + ex.getMessage());

                        }
                    }
                    try {
                        out.flush();
                        // сюда добавить подтверждение выполнения
                        Start.mf.cleanMessageBuffer(device);
                    } catch (IOException ex) {
                        System.err.println("DeviceListener:IOException in listener(out.flush()) " + device.getId() + " : " + ex.getMessage());

                    }
                    System.out.println("DeviceListener: getPacket executed");
                    //       }
                }
                status = Start.mf.getWatcherStatus(device);
            }
        }
        closeListener();
    }

    private void closeListener() {
        Start.mf.deviceStatus(device.getId(), false);
        Start.mf.deviceConnection(device.getId(), false);
        try {
            System.err.println("DeviceListener:листенер закрылся к хуям");
            if (clientSocket.isConnected()) {
                clientSocket.shutdownInput();
                clientSocket.shutdownOutput();
                clientSocket.close();
            }
            serverSocket.close();
        } catch (IOException ex) {
            System.err.println("DeviceListener:IOException in listener(serverSocket.close()) " + device.getId() + " : " + ex.getMessage());
        }
    }

    private String makeAnswer(byte[] data) {
        String message[] = new String(data).trim().split("#");
//        System.out.println("Message. mess[0]=" + message[0] + "mess1="+message[1]+"  mess length=" + message.length);
        if (message.length > 1) {
            switch (message[1]) {
                case "L":
                    // System.err.println("1");
                    return "#AL#1\r\n";

                case "D": // пакет с данными - требуется его разборка, вызывается метод getData, в котором происходит создание элемента класса PackageData( абстр Pack)
                    //System.err.println("2");
                    getData(message[2]);
                    return "#AD#1\r\n";

                case "P":
                    // System.err.println("3");
                    return "#AP#\r\n";
            }
        }
        System.err.println("4");
        return "empty";

    }
// разборка пакета с данными на собственно данные

    private Pack getData(String message) {
//D#020100;030350;NA;NA;NA;NA;NA;NA;NA;NA;NA;NA;NA;;000000000000;IDX:1:108,MCC:1:250,MNC:1:1,LAC:1:407,CID:1:56625,Vext:1:5977,IN1:1:0,IN2:1:0
        String body[] = message.split(";");
        String date = "";
        String time = "";
        String lat = "";
        String lon = "";
        int speed = 0;
        int course = 0;
        int height = 0;
        int sats = 0;
        float hdop = 0;
        int digitinput = 0;
        int digitouput = 0;
        String ads;
        String ibutton;
        String params;

        date = body[0];
        time = body[1];
        lat = body[2] + "," + body[3];
        lon = body[4] + "," + body[5];
        if (!body[6].equals("NA")) {
            speed = Integer.parseInt(body[6]);
        }
        if (!body[7].equals("NA")) {
            course = Integer.parseInt(body[7]);
        }
        if (!body[8].equals("NA")) {
            height = Integer.parseInt(body[8]);
        }
        if (!body[6].equals("NA")) {
            sats = Integer.parseInt(body[9]);
        }
        if (!body[6].equals("NA")) {
            hdop = Float.parseFloat(body[10]);
        }
        if (!body[11].equals("NA")) {
            digitinput = Integer.parseInt(body[11]);
        }
        if (!body[12].equals("NA")) {

            digitouput = Integer.parseInt(body[12]);
        }
        ads = body[13];
        ibutton = body[14];
        params = body[15];

        PackageData D = null;
        try {
            D = new PackageData(device.getId(), date, time, lat, lon, speed, course, height, sats, hdop, digitinput, digitouput, ads, ibutton, params);
        } catch (Exception ex) {
            Logger.getLogger(DeviceListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("DeviceListener: getData executed");

        return D;
    }

    @Override
    public void run() {
        System.out.println("DeviceListener: Listener " + device.getId() + "__" + device.getPort() + " started");
        makeConnection();
    }

}
