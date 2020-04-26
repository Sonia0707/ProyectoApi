/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiproyecto;

import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author vidal
 */
public class LoginAdmin extends javax.swing.JFrame {

  

    /**
     * Creamos ventana de Login, le ponemos un color y 
     * la centramos y le damos la propiedad para que no se pueda maximizar
     */
    
    public LoginAdmin() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.CYAN);
        
        
    }
    //Variable publicas para recoger el usuario y el nombre y usarlos en varias clases:

    private static String idUsu="";
       

    public static String getIdUsu() {
        return idUsu;
    }

    public static void setIdUsu(String aIdUsu) {
        idUsu = aIdUsu;
    }
    private static String nomLogin="";

    public static String getNomLogin() {
        return nomLogin;
    }

    public static void setNomLogin(String nomLogin) {
        LoginAdmin.nomLogin = nomLogin;
    }
       

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombreUsu = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        contraseña = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        nombreUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreUsuActionPerformed(evt);
            }
        });

        jLabel1.setText("Usuario: ");

        jLabel2.setText("Password: ");

        jButton1.setText("Entrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("LOGUEATE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nombreUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jButton1)))
                .addContainerGap(102, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(87, 87, 87))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nombreUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jButton1)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombreUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreUsuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreUsuActionPerformed
    
    //Boton de aceptar en el logeo:
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        String usuario = nombreUsu.getText();
        String pass = contraseña.getText();
        
        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if(status.isSuccess()){
                    try {
                        //Recogemos el JSON de PHP:
                        JSONObject jsonObject = new JSONObject(status.getResult());
                        String respuesta= jsonObject.getString("respuesta");
                        int idUsuario= jsonObject.getInt("idUsuario");
                        

                        //Hacemos una serie de restricciones de logueo:

                        //A) El usuario existe por lo tanto si su rol es 1 es administrador:
                        if (respuesta.equals("1")){
                             JOptionPane.showMessageDialog(null, "Login correcto: " + usuario, "Administrador", JOptionPane.INFORMATION_MESSAGE);
                //Si lo es le damos le bienvenida y abrimos la api de administrador donde estaran las opcioines de administrador: 

                            Configuracion menu = new Configuracion();
                            menu.setVisible(true);
                            menu.nombreUsu.setText(usuario);
                            nomLogin = nombreUsu.getText();
                            setNomLogin(nomLogin);
                            idUsu = String.valueOf(idUsuario) ;
                            setIdUsu(idUsu);
                            dispose();
                 

                            //B) El usuario existe pero su su rol es 2 es usuario normal y no tiene permiso:
                        }else if (respuesta.equals("2")){
                          JOptionPane.showMessageDialog(null, "No tienes permiso para loguearte.", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);
                          
                            //C) El nombre del usuario es correcto pero la contraseña no: -2
                        }else if (respuesta.equals("noClave")){
                            JOptionPane.showMessageDialog(null, "Contraseña incorrecta.", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);

                            //D) El usuraio no existe en la base de datos:
                        }else if (respuesta.equals("noUsuario")){
                             JOptionPane.showMessageDialog(null, "El usuario no exite", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    
                }
            }
        });
        
        //Url Login, mismo PHP que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Login/loginAndroid.php?usuario="+usuario+"&pass="+pass);
             
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(LoginAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField contraseña;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField nombreUsu;
    // End of variables declaration//GEN-END:variables
}
