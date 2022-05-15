/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package project.unn.lab_java;
import com.google.gson.Gson;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
/**
 *
 * @author nniks
 */
public class ClientJFrame extends javax.swing.JFrame {
    /**
     * Creates new form JFrameApp
     */
    Message prev_alarm = new Message();
    Message next_alarm = new Message();
    Message time = new Message();
    int port = 3192;
    int ID;
    InetAddress ip;
    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;
    Gson gson = new Gson();
    JLabel label;
    String obj;
    Message msg = new Message();
    String reminder;
    String reminder2;
    String my_time;
    public ClientJFrame() {
        initComponents();
        connect_to_server();
        server_time();
    }
    
    public void server_time(){
        label = new JLabel();
        label.setForeground(Color.BLACK);
        clientJPanel1.setLayout(new GridLayout(1, 1));
        clientJPanel1.add(label);
        add(clientJPanel1);
        Thread thread = new Thread(() ->{
            while(true){
                my_time = "";
                my_time += time.time_string();
//                            if(time.get_hour() < 10){
//                                my_time += "0";
//                            }
//                            my_time += time.get_hour() + ":";
//                            if(time.get_minute() < 10){
//                                my_time += "0";
//                            }
//                            my_time += time.get_minute() + ":";
//                            if(time.get_second() < 10){
//                                my_time += "0";
//                            }
//                            my_time += time.get_second() + "\n"; 
                            
                reminder = "";
                reminder += prev_alarm.time_string();
//                            if(prev_alarm.get_hour() < 10){
//                                reminder += "0";
//                            }
//                            reminder += prev_alarm.get_hour() + ":";
//                            if(prev_alarm.get_minute() < 10){
//                                reminder += "0";
//                            }
//                            reminder += prev_alarm.get_minute() + ":";
//                            if(prev_alarm.get_second() < 10){
//                                reminder += "0";
//                            }
//                            reminder += prev_alarm.get_second() + " " + prev_alarm.get_description() + "\n"; 
                            
                reminder2 = "";
                reminder2 += next_alarm.time_string();
//                            if(next_alarm.get_hour() < 10){
//                                reminder2 += "0";
//                            }
//                            reminder2 += next_alarm.get_hour() + ":";
//                            if(next_alarm.get_minute() < 10){
//                                reminder2 += "0";
//                            }
//                            reminder2 += next_alarm.get_minute() + ":";
//                            if(next_alarm.get_second() < 10){
//                                reminder2 += "0";
//                            }
//                            reminder2 += next_alarm.get_second() + " " + next_alarm.get_description() + "\n"; 
                label.setText("<html>Текущее время " + my_time + "<br>Последний будильник : <br>" + reminder + "<br>Следующий будильник : <br>" + reminder2 + "</html>");
            }
        }); 
        thread.start();
    }
    
    public void get_story(ArrayList<Message> prev){
        String rem = "";
        for(Message m : prev){
            rem += m.time_string();
//                            if(m.get_hour() < 10){
//                                reminder += "0";
//                            }
//                            reminder += m.get_hour() + ":";
//                            if(m.get_minute() < 10){
//                                reminder += "0";
//                            }
//                            reminder += m.get_minute() + ":";
//                            if(m.get_second() < 10){
//                                reminder += "0";
//                            }
//                            reminder += m.get_second() + " " + m.get_description(); 
        }
        jTextArea1.setFont(new Font("Liberation Sans", Font.ROMAN_BASELINE, 16));
        jTextArea1.setText(rem);
    }

    public void connect_to_server(){
        try {
            ip = InetAddress.getLocalHost();
            cs = new Socket(ip, port);
            
            dos = new DataOutputStream(cs.getOutputStream());
            
            Thread thread = new Thread(() -> {
                try {
                    dis = new DataInputStream(cs.getInputStream());
                    obj = dis.readUTF();
                    msg = gson.fromJson(obj, Message.class);
                    ID = msg.get_ID();
                    jLabel4.setFont(new Font("Liberation Sans", Font.ROMAN_BASELINE, 16));
                    jLabel4.setText("ID : " + ID);
                    jLabel4.setHorizontalAlignment(JTextField.CENTER);
                    jTextArea1.setEditable(false);
                    get_story(msg.get_descriptions());
                    
                    while(true){
                        obj = dis.readUTF();
                        msg = gson.fromJson(obj, Message.class);
                        String str4 = gson.toJson(msg);
                        System.out.println("4" + str4);
                        switch (msg.get_ID()) {
                            case -3 -> time = msg;
                            case -2 -> prev_alarm = msg;
                            case -1 -> next_alarm = msg;
                            default -> get_story(msg.get_descriptions());
                        }
                    }
                    } catch (IOException ex) {
                        Logger.getLogger(ClientJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                try {
                    dos.close();
                    dis.close();
                    cs.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            thread.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jSpinner4 = new javax.swing.JSpinner();
        jSpinner5 = new javax.swing.JSpinner();
        jSpinner6 = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        clientJPanel1 = new project.unn.lab_java.ClientJPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton7.setText("➢");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextField1.setForeground(new java.awt.Color(102, 102, 102));
        jTextField1.setText("Напишите сообщение ...");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jSpinner5.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));

        jSpinner6.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));

        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout clientJPanel1Layout = new javax.swing.GroupLayout(clientJPanel1);
        clientJPanel1.setLayout(clientJPanel1Layout);
        clientJPanel1Layout.setHorizontalGroup(
            clientJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 475, Short.MAX_VALUE)
        );
        clientJPanel1Layout.setVerticalGroup(
            clientJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSpinner6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 43, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clientJPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//        if(ID == 0)
//        connect_to_server();
//        else {
//            jLabel4.setFont(new Font("Liberation Sans", Font.ROMAN_BASELINE, 16));
//            jLabel4.setText("Connect");
//            Thread th = new Thread(() -> {
//            try {
//            Thread.sleep(3000);
//            jLabel4.setText("");
//            } catch (InterruptedException ex) {
//        Logger.getLogger(JFrameApp.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        });  
//        th.start();
//        }
    
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if((int)jSpinner4.getValue() * 3600  + (int)jSpinner5.getValue() * 60 + (int)jSpinner6.getValue() - time.get_hour() * 3600 - time.get_minute() * 60 - time.get_second() > 0 ){
            if(dos != null){
                Message m = new Message();
                m.set_ID(ID);
                m.set_hour((int)jSpinner4.getValue());
                m.set_minute((int)jSpinner5.getValue());
                m.set_second((int)jSpinner6.getValue());
                m.set_description(jTextField1.getText());
                String str = gson.toJson(m);
                System.out.println("1" + str);
                try {
                    dos.writeUTF(str);
                } catch (IOException ex) {
                    Logger.getLogger(ClientJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                
            jTextField1.setText("");
        }
        else{System.out.println("error");}  
    }//GEN-LAST:event_jButton7ActionPerformed

    
    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        jTextField1.addMouseListener(new MouseAdapter(){
           @Override
           public void mouseClicked(MouseEvent e){
               if(e.getButton() == MouseEvent.BUTTON1){
                   jTextField1.setText("");
               }
           }
       });
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private project.unn.lab_java.ClientJPanel clientJPanel1;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JSpinner jSpinner6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    private void Exception(String error_descriptor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
