/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trekermanager;

/**
 *
 * @author Tester
 */
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;

class ServeOneJabber implements Runnable {

    private SocketChannel channel;
    private Selector sel;

    public ServeOneJabber(SocketChannel ch) throws IOException {
        channel = ch;
        sel = Selector.open();
    }

    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        boolean read = false, done = false;
        String response = null;
        try {
            channel.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            while (!done) {
                sel.select();
                Iterator it = sel.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();
                    if (key.isReadable() && !read) {
                        if (channel.read(buffer) > 0) {
                            read = true;
                        }
                        CharBuffer cb = DeviceServer.CS
                                .decode((ByteBuffer) buffer.flip());
                        response = cb.toString();
                    }
                    if (key.isWritable() && read) {
                        System.out.print("Echoing : " + response);
                        channel.write((ByteBuffer) buffer.rewind());
                        if (response.indexOf("END") != -1) {
                            done = true;
                        }
                        buffer.clear();
                        read = false;
                    }
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
