package UI;

import trekermanager.Device;

public class PanelDevice extends javax.swing.JPanel {

    IconDeviceStatus ConnectedIcon;
    IconDeviceStatus OnlineIcon;
    Device device;

    public PanelDevice(Device device) {
        initComponents();
        this.device = device;
        drawStatuses();
    }

    private void drawStatuses() {
        LabelDeviceID.setText(device.getId());
        ConnectedIcon = new IconDeviceStatus(device.isConnected());
        OnlineIcon = new IconDeviceStatus(device.isOnline());

        this.add(ConnectedIcon);
        ConnectedIcon.setBounds(240, 29, 30, 30);

        this.add(OnlineIcon);
        OnlineIcon.setBounds(330, 29, 30, 30);
        System.out.println("PanelDevice: drawStatuses executed");
    }
    public void redrawPanel() {
        this.remove(ConnectedIcon);
        this.remove(OnlineIcon);
        drawStatuses();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlab1 = new javax.swing.JLabel();
        jlab3 = new javax.swing.JLabel();
        jlab4 = new javax.swing.JLabel();
        LabelDeviceID = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setPreferredSize(new java.awt.Dimension(380, 70));

        jlab1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlab1.setText("Device ID");
        jlab1.setAutoscrolls(true);

        jlab3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlab3.setText("Connection");
        jlab3.setAutoscrolls(true);

        jlab4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlab4.setText("Status");
        jlab4.setAutoscrolls(true);

        LabelDeviceID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelDeviceID.setText("123456789012345");
        LabelDeviceID.setAutoscrolls(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LabelDeviceID, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jlab1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49)
                .addComponent(jlab3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlab4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlab3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlab4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlab1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelDeviceID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelDeviceID;
    private javax.swing.JLabel jlab1;
    private javax.swing.JLabel jlab3;
    private javax.swing.JLabel jlab4;
    // End of variables declaration//GEN-END:variables
}
