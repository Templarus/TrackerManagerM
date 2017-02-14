package trekermanager;

import UI.Start;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

public class DeviceServer implements Runnable {

    static Integer PORT = 5601;
    private static String encoding = System.getProperty("file.encoding");
    public static final Charset CS = Charset.forName(encoding);
    private static ThreadPool pool = new ThreadPool(20);
    private Map timeMap = new HashMap<Device, Long>();// содержит тайминги последнего пакета для каждого устройства
    private Map deviceStatusList = new HashMap<Device, Boolean>(); // содержит статусы DeviceListener для каждого устройства, ключ = указатель на устройство
    private Map DeviceList = new HashMap<String, Device>();
    private Set<String> keys;

    public DeviceServer(int port) {
        this.PORT=port;
    }

    public static void main(String[] args) throws IOException {
       
    }

    @Override
    public void run() {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         System.out.println("DeviceServer Started");
        try{
         ServerSocketChannel ssc = ServerSocketChannel.open();
        Selector sel = Selector.open();
        try {
            ssc.configureBlocking(false);
            ssc.socket().bind(new InetSocketAddress(PORT));
            SelectionKey key = ssc.register(sel, SelectionKey.OP_ACCEPT);
            System.out.println("Server on port: " + PORT);
            while (true) {
                sel.select();
                Iterator it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey skey = (SelectionKey) it.next();
                    it.remove();
                    if (skey.isAcceptable()) {
                        SocketChannel channel = ssc.accept();
                        System.out.println("Accepted connection from:"
                                + channel.socket());
                        channel.configureBlocking(false);
                        // Отделяем события и ассоциированное действие
                       // new DeviceListener(channel);
                        pool.addTask(new DeviceListener(channel));
                    }
                }
            }
        } finally {
            ssc.close();
            sel.close();
        }
        }
        catch(IOException ex){
            System.err.println("DeviceServer: exception "+ ex);
            
        }
    }
}
