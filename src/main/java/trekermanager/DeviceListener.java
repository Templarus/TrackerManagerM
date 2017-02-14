package trekermanager;

import UI.Start;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

//РљР»Р°СЃСЃ РїСЂРµРґРЅР°Р·РЅР°С‡РµРЅРЅС‹Р№ РґР»СЏ СЃРѕР·РґР°РЅРёСЏ Рё РѕР±СЂР°Р±РѕС‚РєРё СЃРѕРµРґРёРЅРµРЅРёСЏ СЃ РѕРґРЅРёРј СѓСЃС‚СЂРѕР№СЃС‚РІРѕРј
public class DeviceListener implements Runnable {

    private Device device;
    private ByteBuffer buffer;
    private SocketChannel channel;
    private Selector sel;
    private boolean read;
    private String response;

    private boolean status = true;  // СЃС‚Р°С‚СѓСЃ СЃРѕРµРґРёРЅРµРЅРёСЏ\РѕС‚РєСЂС‹С‚РѕРіРѕ ClientSocker

    public DeviceListener(SocketChannel ch) throws IOException {
        channel = ch;
        sel = Selector.open();
        System.out.println("DeviceListener: Devlist started");
    }

    private void makeConnection() throws ClosedChannelException {

        channel.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        while (channel.isConnected()) {
           // System.err.println("Chanel "+ device.getId()+" state ="+channel.);
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

                        channel.write((ByteBuffer) DeviceServer.CS.encode(CharBuffer.wrap(response)));
                        if (response.indexOf("END") != -1) {
                            status = true;
                        }
                        buffer.clear();
                        read = false;
                    }
                }

            } catch (IOException e) {
                // Р±СѓРґРµС‚ РїРѕР№РјР°РЅРѕ Worker.java Рё Р·Р°Р»РѕРіРёСЂРѕРІР°РЅРѕ.
                // РќРµРѕР±С…РѕРґРёРјРѕ РІС‹Р±СЂРѕСЃРёС‚СЊ РёСЃРєР»СЋС‡РµРЅРёРµ РІСЂРµРјРµРЅРё РІС‹РїРѕР»РЅРµРЅРёСЏ, С‚Р°Рє РєР°Рє РјС‹ РЅРµ
                // РјРѕР¶РµРј
                // РѕСЃС‚Р°РІРёС‚СЊ IOException
                System.err.println("DeviceListener: Exception " + e);
                throw new RuntimeException(e);

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

                    if (registerDevice(message[2].split(";"))) {
                        return "#AL#1\r\n";
                    } else {
                        return "END";
                    }

                case "D": // РїР°РєРµС‚ СЃ РґР°РЅРЅС‹РјРё - С‚СЂРµР±СѓРµС‚СЃСЏ РµРіРѕ СЂР°Р·Р±РѕСЂРєР°, РІС‹Р·С‹РІР°РµС‚СЃСЏ РјРµС‚РѕРґ getData, РІ РєРѕС‚РѕСЂРѕРј РїСЂРѕРёСЃС…РѕРґРёС‚ СЃРѕР·РґР°РЅРёРµ СЌР»РµРјРµРЅС‚Р° РєР»Р°СЃСЃР° PackageData( Р°Р±СЃС‚СЂ Pack)
                    //System.err.println("2");

                    if (getData(message[2]) != null) {
                        return "#AD#1\r\n";
                    } else {
                        return "END";
                    }

                case "P":
                    // System.err.println("3");
                    return "#AP#\r\n";
            }
        }
        System.err.println("4");
        return "END";

    }
// СЂР°Р·Р±РѕСЂРєР° РїР°РєРµС‚Р° СЃ РґР°РЅРЅС‹РјРё РЅР° СЃРѕР±СЃС‚РІРµРЅРЅРѕ РґР°РЅРЅС‹Рµ

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
            return null;
        }
        System.out.println("DeviceListener: getData executed");

        return D;
    }

    private Boolean registerDevice(String[] message) {
        Set<String> keys = Start.mf.deviceList.keySet();
        String login = message[0];
        String pass = message[1];
        if (keys.contains(login)) {
            System.out.println("Login=" + login);
            Start.mf.deviceConnection(login, true);
            this.device = (Device) Start.mf.getDeviceList().get(login);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        System.err.println("DeviceListener: run");
        response = null;
        buffer = ByteBuffer.allocate(2048);
        read = false;
        //    System.out.println("DeviceListener: Listener " + device.getId() + "__" + " started");
        try {
            System.err.println("DeviceListener: makeConnection");
            makeConnection();
        } catch (IOException e) {
            // Р±СѓРґРµС‚ РїРѕР№РјР°РЅРѕ Worker.java Рё Р·Р°Р»РѕРіРёСЂРѕРІР°РЅРѕ.
            // РќРµРѕР±С…РѕРґРёРјРѕ РІС‹Р±СЂРѕСЃРёС‚СЊ РёСЃРєР»СЋС‡РµРЅРёРµ РІСЂРµРјРµРЅРё РІС‹РїРѕР»РЅРµРЅРёСЏ, С‚Р°Рє РєР°Рє РјС‹ РЅРµ
            // РјРѕР¶РµРј
            // РѕСЃС‚Р°РІРёС‚СЊ IOException
            throw new RuntimeException(e);
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                System.out.println("Channel not closed.");
                // Р’С‹Р±СЂР°СЃС‹РІР°РµРј СЌС‚Рѕ, С‡С‚РѕР±С‹ СЂР°Р±РѕС‡Р°СЏ РЅРёС‚СЊ РјРѕРіР»Р° Р·Р°Р»РѕРіРёСЂРѕРІР°С‚СЊ.
                throw new RuntimeException(e);
            }
        }
    }

}
