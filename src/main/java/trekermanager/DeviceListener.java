package trekermanager;

import UI.Start;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

//Класс предназначенный для создания и обработки соединения с одним устройством
public class DeviceListener implements Runnable {

    private Device device;
    private int length = 0;
    private ByteBuffer buffer;
    private SocketChannel channel;
    private Selector sel;
    private boolean read;
    private String response;

    private boolean status = true;  // статус соединения\открытого ClientSocker

    public DeviceListener(SocketChannel ch) throws IOException {
        channel = ch;
        sel = Selector.open();
    }

    private void makeConnection() throws ClosedChannelException {

        channel.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        //System.out.println("DeviceListener: Listener " + device.getId() + " started. Status=" + clientSocket.toString());
        // status = Start.mf.getWatcherStatus(device); // в локальную переменную записываем значение из MainForm - изначально оно = true ---------------------
        //System.out.println("DeviceListener " + device.getId() + ": client socket got accept  status=" + clientSocket.toString());

        while (status) { //т.к. сокет был закрыт - возвращаем зачение false (которое должно быть уже задано в нужной коллекции
            try {
                sel.select();
                Iterator it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();

                    if (key.isReadable() && !read) {

                        if (channel.read(buffer) > 0) {
                            read = true;
                        }

                        CharBuffer cb = DeviceServer.CS.decode((ByteBuffer) buffer.flip());
                        response = makeAnswer(cb);
                    }
                    if (key.isWritable() && read) {
                        System.out.print("Echoing : " + response);
                        channel.write((ByteBuffer) buffer.rewind());
                        if (response.indexOf("END") != -1) {
                            status = true;
                        }
                        buffer.clear();
                        read = false;
                    }
                }
            } catch (IOException e) {
                // будет поймано Worker.java и залогировано.
                // Необходимо выбросить исключение времени выполнения, так как мы не
                // можем
                // оставить IOException
                throw new RuntimeException(e);
            } finally {
                try {
                    channel.close();
                } catch (IOException e) {
                    System.out.println("Channel not closed.");
                    // Выбрасываем это, чтобы рабочая нить могла залогировать.
                    throw new RuntimeException(e);
                }

            }
        }
    }

    private String makeAnswer(CharBuffer data) {
        String message[] = data.toString().trim().split("#");
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
            System.out.println("DeviceListener: getData exception " + ex);
        }
        System.out.println("DeviceListener: getData executed");

        return D;
    }

    @Override
    public void run() {
        response = null;
        buffer = ByteBuffer.allocate(16);
        read = false;
        String response = null;
        System.out.println("DeviceListener: Listener " + device.getId() + "__" + " started");
        try {
            makeConnection();
        } catch (IOException e) {
            // будет поймано Worker.java и залогировано.
            // Необходимо выбросить исключение времени выполнения, так как мы не
            // можем
            // оставить IOException
            throw new RuntimeException(e);
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                System.out.println("Channel not closed.");
                // Выбрасываем это, чтобы рабочая нить могла залогировать.
                throw new RuntimeException(e);
            }
        }
    }

}
