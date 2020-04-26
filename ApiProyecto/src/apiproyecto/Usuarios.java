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
import java.awt.HeadlessException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author vidal
 */
public class Usuarios extends javax.swing.JFrame {

    /**
     * Ventana de Usuarios Variables que usaremos en la las funciones:
     */
    DefaultTableModel listModel;
    String roles;
    Scanner teclado = new Scanner(System.in);
    String idUsuario = LoginAdmin.getIdUsu();

    public Usuarios(DefaultTableModel listModel, JTable Tabla, JTextField email, JTextField emailMod, JButton jButton1, JButton jButton2, JButton jButton3, JButton jButton4, JButton jButton5, JLabel jLabel1, JLabel jLabel11, JLabel jLabel12, JLabel jLabel13, JLabel jLabel14, JLabel jLabel2, JLabel jLabel3, JLabel jLabel4, JLabel jLabel5, JLabel jLabel6, JLabel jLabel7, JLabel jLabel8, JScrollPane jScrollPane1, JTextField jTextField2, JTextField nuevoUsu, JPasswordField password, JPasswordField passwordMod, JTextField rol, JTextField rolMod, JTextField usuarioMod) throws HeadlessException {
        this.listModel = listModel;
        this.Tabla = Tabla;
        this.Guardar = jButton1;
        this.Atras = jButton2;
        this.Modificar = jButton4;
        this.Eliminar = jButton5;
        this.jLabel2 = jLabel2;
        this.jLabel3 = jLabel3;
        this.jLabel4 = jLabel4;
        this.jLabel6 = jLabel6;
      
        this.jScrollPane1 = jScrollPane1;
        this.nuevoUsu = nuevoUsu;
        this.password = password;

    }

    //Como en todas centrada un color de fonco y no se puede maximizar:
    public Usuarios() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.CYAN);

        //lista de Usuarios que cargamos en el metodo:
        listaUsuarios();

    }

    //Metodo para visualizar en la Jtable los usuarios: 
    public void listaUsuarios() {
        //Creamos las cabeceras y las añadimos a la tabla, tambien le añadimos 
        //un codigo para que no se pueda modificar las celdas en la propia Jtable:
        listModel = new DefaultTableModel();

        listModel.addColumn("idUsuario");
        listModel.addColumn("Nombre");
        listModel.addColumn("idRol");
        Tabla.setModel(listModel);
        Tabla.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //Array para recoger los Datos Provinientes del PHP:
        String Datos[] = new String[3];

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        JSONObject jsonObject = new JSONObject(status.getResult());
                        JSONArray respuesta = jsonObject.getJSONArray("usuarios");
                        int booleano = jsonObject.getInt("booleano");
                        if (booleano == 1) {

                            for (int i = 0; i < respuesta.length(); i++) {
                                //Sacamos el idUsurio, el nombre y el rol de los usuarios excepto el nuestro:
                                JSONObject object = respuesta.getJSONObject(i);
                                String idUsuario = object.getString("idUsuario");
                                String nombre = object.getString("nombre");
                                String idRol = object.getString("idRoles");

                                Datos[0] = idUsuario;
                                Datos[1] = nombre;
                                Datos[2] = idRol;

                                //Añadimos a la Jtable:
                                listModel.addRow(Datos);
                            }

                        } else if (booleano == 0) {

                        }
                    } catch (JSONException e) {
                        JOptionPane.showMessageDialog(null, "No hay usuarios en la aplicacion, crea alguno.", "¡Atención!", JOptionPane.ERROR_MESSAGE);

                    }

                }
            }
        });

        //Le mandamos nuestro usuario para que no lo saque:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminApiUsuarios.php?idUsuario=" + idUsuario);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nuevoUsu = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        Guardar = new javax.swing.JButton();
        Atras = new javax.swing.JButton();
        password = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        Modificar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        buscador = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel2.setText("Nombre:");

        jLabel3.setText("Password:");

        jLabel4.setText("Email:");

        Guardar.setText("Guardar");
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });

        Atras.setText("Atras");
        Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtrasActionPerformed(evt);
            }
        });

        Tabla = new javax.swing.JTable(){
            public boolean isCellEditable(int rowTndex, int colIndex){
                return false;
            }
        };
        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabla);

        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("Insertar nuevo usuario:");

        id.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        id.setToolTipText("");

        jLabel1.setText("Buscar por nombre: ");

        buscador.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                buscadorCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Guardar)
                                .addGap(18, 18, 18)
                                .addComponent(Modificar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Eliminar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(id))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(Atras)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(31, 31, 31))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nuevoUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(id)
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nuevoUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Guardar)
                    .addComponent(Modificar)
                    .addComponent(Eliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Atras)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Boton para guardar un Usuario nuevo:
     */
    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        URL url = null;
        InputStream leer;
        BufferedReader br;

        //Recogemos los datos en los TextFiel:
        String name = nuevoUsu.getText();
        String pass = password.getText();
        String Email = email.getText();

        try {
            //Comprobamos que no esten vacios antes de ingresar:
            if (!name.isEmpty() && !pass.isEmpty() && !Email.isEmpty()) {
                url = new URL("http://localhost/Proyecto/insertarAdmin.php?name=" + name + "&pass=" + pass + "&email=" + Email);
                //Conectar Url:
                URLConnection uRLConnection = url.openConnection();
                //Para escribir aparte de la conexion tenemos que poner el urlConection en true:
                uRLConnection.setDoOutput(true);

                leer = uRLConnection.getInputStream();
                br = new BufferedReader(new InputStreamReader(leer));

                String data;
                String cadena = new String();
                while ((data = br.readLine()) != null) {
                    cadena += data;
                    System.out.println(data);
                }

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new ByteArrayInputStream(cadena.getBytes("utf-8"))));
                document.getDocumentElement().normalize();

                String elemento = document.getElementsByTagName("return").item(0).getTextContent();

                //Respuestas del PHP
                if (elemento.equals("user")) {
                    //Si existe no nos deja grabarlo ya que cada usuario el nombre tiene que ser unico: 
                    JOptionPane.showMessageDialog(null, "¡Ya existe ese nombre prueba con otro.", "Inserción Denegada", JOptionPane.ERROR_MESSAGE);
                    nuevoUsu.setText("");
                    password.setText("");
                    email.setText("");

                } else if (elemento.equals("email")) {
                    //Si existe no nos deja grabarlo ya que cada usuario el email tiene que ser unico: 
                    JOptionPane.showMessageDialog(null, "¡Ya existe ese email prueba con otro.", "Inserción Denegada", JOptionPane.ERROR_MESSAGE);
                    nuevoUsu.setText("");
                    password.setText("");
                    email.setText("");
                } else {
                    //Usuario registrado con exito:
                    JOptionPane.showMessageDialog(null, "¡Usuario Registrado con exito!.", "Usuarios", JOptionPane.INFORMATION_MESSAGE);
                    nuevoUsu.setText("");
                    password.setText("");
                    email.setText("");
                    listaUsuarios();
                }

                br.close();
            } else {
                //Problemas con la conexion a MySql:
                JOptionPane.showMessageDialog(null, "¡ No puede haber campo vacios !.", "Usuarios", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_GuardarActionPerformed
    //Volver a Configuracion:
    private void AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtrasActionPerformed
        Configuracion conf = new Configuracion();
        conf.setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_AtrasActionPerformed
    //Boton de Modificación, para convertir al Usuario en administrador o al reves:
    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        int seleccion = Tabla.getSelectedRow();
        if (seleccion >= 0) {
            String idUsuario = Tabla.getValueAt(seleccion, 0).toString();

            //Dialogo para la modificacion: Administrador o Usuario:
            int opciones = JOptionPane.showOptionDialog(
                    Modificar,
                    "¿ Desea cambiar su estado ?",
                    "Selector de opciones",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, // null para icono por defecto.
                    new Object[]{"Administrador", "Usuario", "Cancelar"}, // null para admin, usuario y CANCEL
                    "opcion 1");

            if (opciones != -1) {
                System.out.println("seleccionada opcion " + (opciones + 1));
                if ((opciones + 1) == 1) {
                    roles = "1";
                    //Llamar al metodo mofificar pasandole el id Clicado y el rol 1
                    modificarUsuarios(idUsuario, roles);
                     //Volver a cargar vista para que se vea el resultado:
                    listaUsuarios();

                } else if ((opciones + 1) == 2) {
                    roles = "2";
                    //Llamar al metodo mofificar pasandole el id Clicado y el rol 2
                    modificarUsuarios(idUsuario, roles);
                    //Volver a cargar vista para que se vea el resultado:
                    listaUsuarios();
                }
            }

        } else {
            //Si no elegimos ningún usuaurio nos dirá que tenemos que seleccionar alguno para poder modificar:
            JOptionPane.showMessageDialog(null, "Seleciona algún usuario primero");
        }
    }//GEN-LAST:event_ModificarActionPerformed
    //Metodo de modificacion conexion con el PHP para la modificacion:
    public void modificarUsuarios(String idUsuario, String idrol) {

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        //Recogemos el JSON de PHP:
                        JSONObject jsonObject = new JSONObject(status.getResult());
                        int resp = jsonObject.getInt("res");

                        if (resp == 1) {
                            //Si todo es correcto realiza el cambio ha administrador: 
                            if (idrol.equals("1")) {

                                System.out.println("Cambio realizado ha admin ");
                            //Si todo es correcto realiza el cambio ha usuario: 
                            } else if (idrol.equals("2")) {

                                System.out.println("Cambio realizado ha usuario");
                            }
                         //Problema con la base de datos:
                        } else if (resp == 0) {
                            JOptionPane.showMessageDialog(null, "Problema en la base de datos intentelo mas tarde", "Cambio Denegado", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Url Login, mismo PHP que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/modificarAdmin.php?idUsuario=" + idUsuario + "&rol=" + idrol);

    }

    //Boton eliminar Usuario:
    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        int fila = Tabla.getSelectedRow();
        if (fila >= 0) {
            String idUsuario = Tabla.getValueAt(fila, 0).toString();
            //llamar al metodo eliminar pandole el idUsuario a eliminar:
            
            eliminar(idUsuario);
            listaUsuarios();
           
        } else {
            JOptionPane.showMessageDialog(null, "Seleciona una fila primero");
        }
    }//GEN-LAST:event_EliminarActionPerformed
    //TextFile buascador para hacer mas facil la busqueda de Usuarios:
    private void buscadorCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_buscadorCaretUpdate

        String valor = buscador.getText();

        if (valor.isEmpty()) {
            Tabla.clearSelection();
        } else {
            for (int i = 0; i < Tabla.getRowCount(); i++) {
                if (Tabla.getValueAt(i, 1).equals(valor)) {

                    Tabla.changeSelection(i, 1, false, false);
                }

            }
        }

    }//GEN-LAST:event_buscadorCaretUpdate

    //Metodo para eliminar 
    public void eliminar(String idUsuario) {
         JOptionPane.showMessageDialog(null, "Usuario eliminado", "INFORMACION", JOptionPane.ERROR_MESSAGE);
        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    
                    try {
                        //Recogemos el JSON de PHP:
                        JSONObject jsonObject = new JSONObject(status.getResult());
                        int resp = jsonObject.getInt("respuesta");

                        if (resp == 1) {
                            //Si el admin elige un usuario y le da al boton de eliminar, se eliminará completamente:
                            System.out.println("Usuario eliminado.");

                        } else if (resp == 0) {
                            //Problema en la conexion de MySql:
                            JOptionPane.showMessageDialog(null, "Problema en la base de datos intentelo mas tarde", "Cambio Denegado", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Url eliminar, mismo PHP que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/eliminarUsuario.php?idUsuario=" + idUsuario);

    }

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
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Atras;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Guardar;
    private javax.swing.JButton Modificar;
    private javax.swing.JTable Tabla;
    private javax.swing.JTextField buscador;
    private javax.swing.JTextField email;
    public static javax.swing.JLabel id;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nuevoUsu;
    private javax.swing.JPasswordField password;
    // End of variables declaration//GEN-END:variables
}
