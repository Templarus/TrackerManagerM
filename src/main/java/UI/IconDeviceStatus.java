package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

// Графический класс иконки, отображающей цветовую индикаю
public class IconDeviceStatus extends javax.swing.JPanel {

    Graphics2D GraphicsReadySpec;
    Shape shape;
    private boolean status=false;
    
    public IconDeviceStatus(boolean status) {
        initComponents();
        this.status = status;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
 @Override
    protected void paintComponent(Graphics g)
    {

        super.paintComponent(g);//Стандартная запись перезачи сформированой графики
        GraphicsReadySpec = (Graphics2D) g;
            GraphicsReadySpec.setPaint(Color.BLACK);
        GraphicsReadySpec.fillOval(0, 0, 25, 25);
       if(status)
       {GraphicsReadySpec.setPaint(Color.GREEN);}
       else
       {
           GraphicsReadySpec.setPaint(Color.GRAY);
       }
        GraphicsReadySpec.fillOval(0, 0, 24, 24);
    
     
      

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
