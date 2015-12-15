package UI;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JScrollPane;
import trekermanager.Device;
import trekermanager.DeviceListener;
import Db.ServerDb;
import java.net.ServerSocket;
import trekermanager.Watcher;

public class MainForm extends javax.swing.JFrame {

    private Map DeviceList = new HashMap<String, Device>();  //содержит "список устройств", ключ = id устройства
    private Map PanelDeviceList = new HashMap<String, PanelDevice>(); //содержит "список панелей" для отображения статуса устройств, ключ = id устройства
    private Map WatcherList = new HashMap<Device, Boolean>(); // содержит статусы DeviceListener для каждого устройства, ключ = указатель на устройство
    private Map timeMap = new HashMap<Device, Long>();// содержит тайминги последнего пакета для каждого устройства
    public static FormDeviceParams FDP; // popup для добавления нового устройства в пул "на лету"

    public static ServerDb sdb;
    private javax.swing.JPanel PanelPane;

    public MainForm() {
        initComponents();
        if (load()) {
//            System.out.println("MainForm:Load successfull");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelFont = new javax.swing.JPanel();
        PanelScrolPane = new javax.swing.JScrollPane();
        AddButton = new javax.swing.JButton();
        butTrackerData = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(300, 400));
        setLocationByPlatform(true);
        addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                formAncestorResized(evt);
            }
        });

        PanelFont.setLayout(null);

        PanelScrolPane.setPreferredSize(new java.awt.Dimension(420, 230));
        PanelFont.add(PanelScrolPane);
        PanelScrolPane.setBounds(10, 50, 420, 520);

        AddButton.setText("Add");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });
        PanelFont.add(AddButton);
        AddButton.setBounds(180, 20, 100, 23);

        butTrackerData.setText("Tracker data");
        butTrackerData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTrackerDataActionPerformed(evt);
            }
        });
        PanelFont.add(butTrackerData);
        butTrackerData.setBounds(330, 20, 100, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelFont, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelFont, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 /**
     *
     * @param d - Device object
     * @param status - Listener status ( true= online)
     */
    public void setWatcherStatus(Device d, boolean status) {
        WatcherList.put(d, status);
    }

    /**
     *
     * @param d - Device object
     * @return status of selected Listener
     */
    public boolean getWatcherStatus(Device d) {
        return (Boolean) WatcherList.get(d);
    }

    /**
     *
     * @return keySet of device id`s as Set string from DeviceList
     */
    public Set<String> getDevicesKeySet() {
        Set<String> keys = DeviceList.keySet();
        return keys;
    }

    public Map getDeviceList() {
        return DeviceList;
    }

    public Map getTimeMap() {
        return timeMap;
    }

    public void setTime(Device device, Long time) {
        timeMap.put(device, time);
    }

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        System.out.println("MainForm: AddButtonActionPerformed clicked");
        Point mloc = MouseInfo.getPointerInfo().getLocation();
        FDP = new FormDeviceParams(new javax.swing.JFrame(), true);
        FDP.setBounds(mloc.x, mloc.y, 220, 200);
        FDP.setName(this.getName());
        FDP.setTitle("Добавление нового устройства");
        FDP.setVisible(true);
        System.out.println("MainForm: AddButtonActionPerformed executed");
    }//GEN-LAST:event_AddButtonActionPerformed

    private void formAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formAncestorResized
        int Hgap = this.getPreferredSize().height - this.getSize().height;
        int Wgap = this.getPreferredSize().width - this.getSize().width;
        PanelPane.setPreferredSize(new java.awt.Dimension(Start.mf.PanelPane.getPreferredSize().width + Wgap, Start.mf.PanelPane.getPreferredSize().height + Hgap));
        PanelScrolPane.setPreferredSize(new java.awt.Dimension(Start.mf.PanelScrolPane.getPreferredSize().width + Wgap, Start.mf.PanelScrolPane.getPreferredSize().height + Hgap));
        PanelPane.revalidate();
        PanelScrolPane.revalidate();

    }//GEN-LAST:event_formAncestorResized

    private void butTrackerDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTrackerDataActionPerformed
        TrekerData td = new TrekerData(new javax.swing.JFrame(), false);
        td.setVisible(true);
    }//GEN-LAST:event_butTrackerDataActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

// собственно первый исполняемый метод - первичаная загрузка
    private boolean load() {
        System.out.println("MainForm:Load Started");

        //  sdb = new ServerDb("jdbc:sqlserver://ASUSG46:1433;databaseName=UltraFiolet", "sa", "sa"); // создаём объект ServerDB и собственно устанавливаем коннект к базе
        sdb = new ServerDb("jdbc:sqlserver://MAIN:1433;databaseName=UltraFiolet", "sa", "Zx3d2818!"); // создаём объект ServerDB и собственно устанавливаем коннект к базе
        loadDBdata(); // чтение изначальной конфигурации - создание списка DeviceList
        drawPanels(); // отрисовка внешнего вида согласно прочитанной конфигурации
        createWatcher(); // создание наблюдателей для каждого устройства (созданного listener)
        System.out.println("MainForm: load executed");
        return true;
    }

    private void loadDBdata() {
        for (Device dev : sdb.getDeviceList()) {
            addDevice(dev);
        }

    }

// метод динамического создания вложенного в PanelScrolPane(элемент с полосой прокрутки) элемента Jpanel - в нём отображается набор элементов PanelDevice
    private void createPanelPane() {
        PanelPane = new javax.swing.JPanel(); // вложенная вJpanel
        PanelPane.setPreferredSize(new java.awt.Dimension(418, 228));
        javax.swing.GroupLayout PanelPaneLayout = new javax.swing.GroupLayout(PanelPane);
        PanelPaneLayout.setHorizontalGroup(
                PanelPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 418, Short.MAX_VALUE)
        );
        PanelPaneLayout.setVerticalGroup(
                PanelPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 228, Short.MAX_VALUE)
        );
        PanelPane.setLayout(PanelPaneLayout);
    }

    private void drawPanels() {
        int i = 0; // счётчик, используемый для рассчёта отрисовки
        Set<String> keys = DeviceList.keySet();
        createPanelPane();

        for (String key : keys) {
            Device device = (Device) DeviceList.get(key);
            PanelDevice pd = new PanelDevice(device); // для каждого элемента создаётся своя "панелька", в неё передаётся device, который разбирается внутри
            pd.setBounds(0, 70 * i, pd.getPreferredSize().width, 60);
            pd.setAlignmentX(PanelDevice.CENTER_ALIGNMENT);
            PanelPane.add(pd);// добавляем панельку на Jpanel, который будет встроен в Scroll Pane
            pd.setVisible(true);

            i++;
            PanelPane.setPreferredSize(new java.awt.Dimension(PanelPane.getPreferredSize().width, 70 * i + 15));
            PanelDeviceList.put(device.getId(), pd);
        }
        PanelPane.revalidate();
        PanelScrolPane.setViewportView(PanelPane);
        PanelScrolPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        PanelScrolPane.revalidate();
        PanelPane.repaint();
        PanelScrolPane.repaint();
        System.out.println("MainForm: drawPanels executed");

    }

// создание наблюдталея в отдельном потоке
    private void createWatcher() {
        Watcher watcher = new Watcher();
        Thread t2 = new Thread(watcher);
        t2.start();
        System.out.println("MainForm: watcher created");
    }

// метод перерисовски формы - используется при добавлении\удалении нового устройства - актуализация DeviceList происходит извне и уже выполнена к началу перерисовки
    public void reload() {
        PanelPane.removeAll();
        PanelDeviceList.clear();
        drawPanels();
        System.out.println("MainForm: reload executed");
    }

// метод добавления нового устройсва в Map DeviceList - возвращает false, если устройство уже существует в списке
    public boolean addDevice(Device d) {
        System.out.println("MainForm: addDevice started");
        if (!DeviceList.containsKey(d.getId())) { // если такого устройство существует в списке - возвращает false
            DeviceList.put(d.getId(), d); // добавляем новое устройство в список устройств
            createListener(d); // создаём Listener для устройсва
            System.out.println("MainForm: addDevice executed, Device " + d.getId() + " added, listener awaiting for connections");
            return true;
        } else {
            System.err.println("Device" + d.getId() + "exists");
            return false;
        }
    }

    /**
     * метод подлежит актуализации и переработке
     *
     * @param id -id устройсва
     * @param connection - boolean состояния соединения, true - установлено
     */
//метод используется для актуализации визуального отображения состояния коннекта.
    public void deviceConnection(String id, boolean connection) {
        Device device = (Device) Start.mf.DeviceList.get(id);
        device.setConnection(connection);
        System.out.println("MainForm: deviceConnection() Device " + device.getId() + " connection=" + connection);
        PanelDevice pd = (PanelDevice) PanelDeviceList.get(device.getId());
        pd.redrawPanel(); // метод перерисовки панели (изменение цвета индикатора)
    }

// метод аналогичный предыдущему - используется для актуализации состояния контроллируемого оборудования (true - включено)
    public void deviceStatus(String id, boolean status) {
        Device device = (Device) DeviceList.get(id);
        device.setStatus(status);
        System.out.println("MainForm: deviceStatus() Device " + device.getId() + " Status=" + status);
    }

// метод, в используемый для создания Listener для каждого устройства
    public void createListener(Device device) {
        DeviceListener DL = new DeviceListener(device);
        WatcherList.put(device, true); // добавляем в список для наблюдения 
        timeMap.put(device, System.currentTimeMillis());
        Thread t1 = new Thread(DL);
        t1.start();
        System.out.println("MainForm: createListener for device " + device.getId() + " executed");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JPanel PanelFont;
    private javax.swing.JScrollPane PanelScrolPane;
    private javax.swing.JButton butTrackerData;
    // End of variables declaration//GEN-END:variables
}
