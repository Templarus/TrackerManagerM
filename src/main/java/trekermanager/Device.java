package trekermanager;


// класс предназначенный для комплексного хранения информации об устройстве в его полях.(сущность)
public class Device {

    private String id = "";
    private int port = 0;
    private boolean connection = false;
    private boolean status = false;
    private String password = "";

    public Device() {
        this("863591026612469", 5601, "1234", false, false);
    }

    public Device(String id, int port, String password) {
        this(id, port, password, false, false);
    }

    public Device(String id, int port, String password, boolean connected, boolean status) {
        this.id = id;
        this.port = port;
        this.password = password;
        this.connection = connected;
        this.status = status;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isConnected() {
        return connection;
    }

    public boolean isOnline() {
        return status;
    }

    public String getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
