/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiproyecto;

import static apiproyecto.LoginAdmin.setIdUsu;
import com.devazt.networking.HttpClient;
import com.devazt.networking.OnHttpRequestComplete;
import com.devazt.networking.Response;
import com.google.gson.JsonElement;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author vidal
 */
public class Grupos extends javax.swing.JFrame {

    //Variables que vamos a necesitar
    Scanner teclado = new Scanner(System.in);
    DefaultTableModel modelo;
    DefaultTableModel modelo2;
    //Recogemos el usuario con el que nos hemos registrado:
    String idUsuario = LoginAdmin.getIdUsu();
    String nombreG;
    String nombreLabel;
    String idAdmin;
    String nomAdmin;

    int idGrupal = 0;

    /**
     * Creamos las pestañas de los Grupos:
     */
    public Grupos() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.CYAN);

        //De primeras solo dejamos que la pestaña de grupos se vea y desconectamos las otras:
        jTabbedPane1.setEnabledAt(1, false);
        jTabbedPane1.setEnabledAt(2, false);

        //Nada mas empezar llamanmos al metodo de listar grupos para que se vea en el Jtable:
        ListaGrupos();

    }

    //Metodo listarGrupos:
    public void ListaGrupos() {
        String Datos[] = new String[4];
        modelo = new DefaultTableModel();
        //Cabecera del Jtable:
        modelo.addColumn("idGrupal");
        modelo.addColumn("Nombre Grupo");
        modelo.addColumn("idAdmin");
        modelo.addColumn("NombAdmin");
        listaGrupos.setModel(modelo);

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        JSONObject jsonObject = new JSONObject(status.getResult());
                        JSONArray respuesta = jsonObject.getJSONArray("listAdmin");
                        int booleano = jsonObject.getInt("booleano");
                        if (booleano == 1) {

                            for (int i = 0; i < respuesta.length(); i++) {
                                //Sacamos el nombre  del Grupo, la fecha de creación y 
                                //el nombre del Administtrador al conectar con el servidor PHP:
                                JSONObject object = respuesta.getJSONObject(i);
                                String idGrupal = object.getString("idGrupal");
                                String nombre = object.getString("nombreG");
                                String idAdmin = object.getString("idAdmind");

                                String admin = object.getString("nombreAdmin");

                                Datos[0] = idGrupal;
                                Datos[1] = nombre;
                                Datos[2] = idAdmin;
                                Datos[3] = admin;

                                modelo.addRow(Datos);
                            }

                        } else if (booleano == 0) {
                            //Si no hubiera grupos:
                          
                        }
                    } catch (JSONException e) {
                             JOptionPane.showMessageDialog(null, "No hay grupos en la aplicación, crea alguno.", "¡Atención!", JOptionPane.ERROR_MESSAGE);   
                    }

                }
            }
        });

        //Url Login, mismo PHP que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminGrupal1.php");

    }

    //Este metodo es para listar los amigos que tiene el administrador del grupo solo:
    public void listaAmigos(String idAdmin) {

        String Datos[] = new String[2];
        modelo = new DefaultTableModel();
        //Cabecera del Jtable:
        modelo.addColumn("idUsuario");
        modelo.addColumn("Nombre");

        listaNombres.setModel(modelo);

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        JSONObject jsonObject = new JSONObject(status.getResult());

                        JSONArray respuesta = jsonObject.getJSONArray("amigos");
                        int booleano = jsonObject.getInt("booleano");
                        if (booleano == 1) {

                            for (int i = 0; i < respuesta.length(); i++) {
                                //Sacamos el idUsuario y el nombre del PHP y los insertamos en el Jtable
                                JSONObject object = respuesta.getJSONObject(i);
                                String idUsuario2 = String.valueOf(object.getInt("idUsuario"));
                                String nombre = object.getString("nombre");

                                Datos[0] = idUsuario2;
                                Datos[1] = nombre;

                                modelo.addRow(Datos);

                            }

                        } else if (booleano == 0) {
                           
                        }
                    } catch (JSONException e) {
                         JOptionPane.showMessageDialog(null, "Este administrador no tines amigos, escoge otro del grupo", "¡Atención!", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        //Url Login, mismo PHP que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminGrupalListaAmigos3.php?idUsuario=" + idAdmin);

    }
    //Metodo para listar los integrantes de un grupo determinado:
    public void listaIntegrantes() {
        String Datos[] = new String[2];
        modelo = new DefaultTableModel();
        //Cabecera del Jtable:
        modelo.addColumn("idUsuario");
        modelo.addColumn("Nombre");

        listaIntegrantes.setModel(modelo);

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {

                    try {

                        JSONObject jsonObject = new JSONObject(status.getResult());
                        JSONArray respuesta = jsonObject.getJSONArray("usuarios");
                        
                        //Boollenao que controla si hay usuarios:
                        int booleano = jsonObject.getInt("booleano");
                        if (booleano == 1) {

                            for (int i = 0; i < respuesta.length(); i++) {
                                //Sacamos el idUsuario y el nombre del PHP y los insertamos en el Jtable:
                                JSONObject object = respuesta.getJSONObject(i);
                                String idUsuario2 = String.valueOf(object.getInt("idUsuario"));
                                String nombre = object.getString("nombre");

                                Datos[0] = idUsuario2;
                                Datos[1] = nombre;

                                modelo.addRow(Datos);
                            }
                        } else if (booleano == 0) {
                            //Si no hubiera integrantes manda el siguiente mensaje y dice que regreses a la pantalla principal:
                            JOptionPane.showMessageDialog(null, "No hay integrantes, vuelve a la principal.", "¡Atención!", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Url Login, mismo PHP que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminGrupalIntegrantes6.php?idGrupal=" + idGrupal);

    }

    //Metodo para Modificar el administrador del grupo:
    public void modifiAdmin(int idGrupal, String idUsuario2, String nombreAdminis) {
        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        JSONObject jsonObject = new JSONObject(status.getResult());

                        //Se lee la respuesta y se realiza el cambio en el servidor segun el idUsuario que le llega:
                        int booleano = jsonObject.getInt("res");

                        if (booleano == 1) {

                            JOptionPane.showMessageDialog(null, "El administrador ahora es " + nombreAdminis, "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
                            //Se realiza el setText en el Label para que ahora se vea el nuevo nombre de administrador:
                            nombreAdmin.setText(nombreAdminis);
                            //Si no se puede conectar al servidor:
                        } else if (booleano == 0) {
                            JOptionPane.showMessageDialog(null, " Error de conexion con la base de datos, intentelo mas tarde", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Url mismo que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminGrupalModAdmin7.php?idGrupal=" + idGrupal + "&idAdmin=" + idUsuario2);

    }
    //Metodo para insertar un grupo
    public void insertarGrupo() {

        nombreG = grupoNombre.getText();

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        JSONObject jsonObject = new JSONObject(status.getResult());
                        int booleano = jsonObject.getInt("respuesta");

                        if (booleano == 1) {
                            //Si todo ba bien se inserta en la tabla grupos el nombre y recoge el idUsuario como admin el resto automatico:
                            JOptionPane.showMessageDialog(null, "Se ha creado el grupo: " + nombreG, "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);

                        } else if (booleano == 0) {
                            //Si no se puede conectar al servidor:
                            JOptionPane.showMessageDialog(null, " Error de conexion con la base de datos, intentelo mas tarde", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Url mismo que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminGrupalInsert2.php?idUsuario=" + idUsuario + "&nombreG=" + nombreG);

    }

    //Metodo para insertar Amigos al grupo elegido antes:
    public void insertarAmigos(String idUsuario2, int idGrupal) {

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        JSONObject jsonObject = new JSONObject(status.getResult());

                        int respuesta = jsonObject.getInt("respuesta");
                        int booleano = jsonObject.getInt("booleano");

                        if (booleano == 1) {

                            if (respuesta == 1) {
                                //Si todo es correcto y el usuario no esta ya en el grupo se inserta y se lanza este mensaje:
                                JOptionPane.showMessageDialog(null, "Se ha insertado correctamente ", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);

                            } else if (respuesta == 2) {
                                JOptionPane.showMessageDialog(null, " Error de conexion con la base de datos, intentelo mas tarde", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);

                            }
                            //Si el usuario ya esta en el grupo no lo mete y lanza este mensaje:
                        } else if (booleano == 0) {
                            JOptionPane.showMessageDialog(null, "Ya ha insertado a ese usuario", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Url mismo que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminGrupalInsertAmigos4.php?idGrupal=" + idGrupal + "&idUsuario2=" + idUsuario2);
    }

    //Eliminar grupo, elimna por completo el grupo sus participantes tambien:
    public void eliminarGrupo(int idGrupal) {
        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        JSONObject jsonObject = new JSONObject(status.getResult());
                        int booleano = jsonObject.getInt("respuesta");

                        if (booleano == 1) {
                            JOptionPane.showMessageDialog(null, "Se ha eliminado el grupo ", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);

                        } else if (booleano == 0) {
                            JOptionPane.showMessageDialog(null, " Error de conexion con la base de datos, intentelo mas tarde", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Url mismo que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminGrupalEliminarG5.php?idGrupal=" + idGrupal);

    }

    //Metodo para eliminar integrantes del grupo:
    public void eliminarIntegrantes(String idUsuario3, int idGrupal) {

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {
                    try {
                        System.out.println(status.getResult());
                        JSONObject jsonObject = new JSONObject(status.getResult());
                        int respuesta = jsonObject.getInt("respuesta");
                        int booleano = jsonObject.getInt("booleano");

                        //El booleano controla si es administrador del grupo no se puede eliminar, 
                        //antes tendrá que hacer un modificación y elegir a otro usuario como administrador:(Para controlar esto decido mirar si esta en la tabla de grupo como admin,
                        // para ello comprubeo si tiene fecha de creacion)
                        if (booleano == 1) {
                            if (respuesta == 1) {
                                JOptionPane.showMessageDialog(null, "No lo puedes eliminar es el admin", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);

                            }
                            //Si no es administrador lo eliminará sin problemas:
                        } else if (booleano == 0) {
                            if (respuesta == 2) {
                                JOptionPane.showMessageDialog(null, "Usuario elimninado", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
                            } else if (respuesta == 3) {
                                JOptionPane.showMessageDialog(null, " Error de conexion con la base de datos, intentelo mas tarde", "INFORMACIÓN", JOptionPane.ERROR_MESSAGE);

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        //Url mismo que Android estudio:
        cliente.excecute("http://192.168.1.131/ProyectoNuevo/Administrador/AdminGrupalEliminarPertenecen7.php?idGrupal=" + idGrupal + "&idUsuario=" + idUsuario3);
        System.out.println(idUsuario3);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pestaña1 = new javax.swing.JPanel();
        AtrasGrupo = new javax.swing.JButton();
        InsertUsuarios = new javax.swing.JButton();
        VerIntegrantes = new javax.swing.JButton();
        EliminarGrupo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaGrupos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        grupoNombre = new javax.swing.JTextField();
        pestaña2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        grupoNombre2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaIntegrantes = new javax.swing.JTable();
        ModificarAdmin = new javax.swing.JButton();
        eliminarUsu = new javax.swing.JButton();
        AtrasIntegrantes = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombreAdmin = new javax.swing.JLabel();
        pestaña3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        grupoNombre3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaNombres = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        InsertAmigos = new javax.swing.JButton();
        AtrasAmigos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(153, 153, 0));
        jTabbedPane1.setBorder(new javax.swing.border.MatteBorder(null));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));

        pestaña1.setBackground(new java.awt.Color(0, 204, 204));

        AtrasGrupo.setText("Atras");
        AtrasGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtrasGrupoActionPerformed(evt);
            }
        });

        InsertUsuarios.setText("Insertar Amigos");
        InsertUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertUsuariosActionPerformed(evt);
            }
        });

        VerIntegrantes.setText("Ver integrantes");
        VerIntegrantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerIntegrantesActionPerformed(evt);
            }
        });

        EliminarGrupo.setText("Eliminar");
        EliminarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarGrupoActionPerformed(evt);
            }
        });

        listaGrupos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowTndex, int colIndex){
                return false;
            }
        };
        listaGrupos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        listaGrupos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaGruposMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaGrupos);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Nombre Grupo:");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        grupoNombre.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        grupoNombre.setForeground(new java.awt.Color(0, 0, 255));

        javax.swing.GroupLayout pestaña1Layout = new javax.swing.GroupLayout(pestaña1);
        pestaña1.setLayout(pestaña1Layout);
        pestaña1Layout.setHorizontalGroup(
            pestaña1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestaña1Layout.createSequentialGroup()
                .addGroup(pestaña1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pestaña1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(AtrasGrupo))
                    .addGroup(pestaña1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnNuevo))
                    .addGroup(pestaña1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(grupoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pestaña1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(pestaña1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pestaña1Layout.createSequentialGroup()
                                .addComponent(VerIntegrantes)
                                .addGap(18, 18, 18)
                                .addComponent(EliminarGrupo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(InsertUsuarios))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pestaña1Layout.setVerticalGroup(
            pestaña1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestaña1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pestaña1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(grupoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pestaña1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EliminarGrupo)
                    .addComponent(VerIntegrantes)
                    .addComponent(InsertUsuarios))
                .addGap(34, 34, 34)
                .addComponent(AtrasGrupo)
                .addGap(61, 61, 61))
        );

        jTabbedPane1.addTab("Grupos", pestaña1);

        pestaña2.setBackground(new java.awt.Color(0, 204, 204));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("Nombre Grupo:");

        grupoNombre2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        grupoNombre2.setForeground(new java.awt.Color(0, 0, 255));
        grupoNombre2.setText("nombreG");

        listaIntegrantes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowTndex, int colIndex){
                return false;
            }
        };
        listaIntegrantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(listaIntegrantes);

        ModificarAdmin.setText("Modificar");
        ModificarAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarAdminActionPerformed(evt);
            }
        });

        eliminarUsu.setText("Eliminar");
        eliminarUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarUsuActionPerformed(evt);
            }
        });

        AtrasIntegrantes.setText("Atras");
        AtrasIntegrantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtrasIntegrantesActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("Integrantes:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Administrador del grupo");

        nombreAdmin.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        nombreAdmin.setForeground(new java.awt.Color(0, 0, 255));
        nombreAdmin.setText("nombre");

        javax.swing.GroupLayout pestaña2Layout = new javax.swing.GroupLayout(pestaña2);
        pestaña2.setLayout(pestaña2Layout);
        pestaña2Layout.setHorizontalGroup(
            pestaña2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestaña2Layout.createSequentialGroup()
                .addGroup(pestaña2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pestaña2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(pestaña2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestaña2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addGroup(pestaña2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ModificarAdmin, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(AtrasIntegrantes, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(eliminarUsu, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pestaña2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(grupoNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pestaña2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(nombreAdmin)))
                .addGap(0, 46, Short.MAX_VALUE))
        );
        pestaña2Layout.setVerticalGroup(
            pestaña2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestaña2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pestaña2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(grupoNombre2))
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestaña2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pestaña2Layout.createSequentialGroup()
                        .addComponent(ModificarAdmin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(eliminarUsu)))
                .addGap(18, 18, 18)
                .addGroup(pestaña2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreAdmin))
                .addGap(18, 18, 18)
                .addComponent(AtrasIntegrantes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Integrantes", pestaña2);

        pestaña3.setBackground(new java.awt.Color(0, 204, 204));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("Amigos:");

        grupoNombre3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        grupoNombre3.setForeground(new java.awt.Color(0, 0, 255));
        grupoNombre3.setText("nombreG");

        listaNombres = new javax.swing.JTable(){
            public boolean isCellEditable(int rowTndex, int colIndex){
                return false;
            }
        };
        listaNombres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(listaNombres);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 255));
        jLabel8.setText("Nombre Grupo:");

        InsertAmigos.setText("Insertar");
        InsertAmigos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertAmigosActionPerformed(evt);
            }
        });

        AtrasAmigos.setText("Atras");
        AtrasAmigos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtrasAmigosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pestaña3Layout = new javax.swing.GroupLayout(pestaña3);
        pestaña3.setLayout(pestaña3Layout);
        pestaña3Layout.setHorizontalGroup(
            pestaña3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestaña3Layout.createSequentialGroup()
                .addGroup(pestaña3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pestaña3Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(pestaña3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pestaña3Layout.createSequentialGroup()
                                .addComponent(InsertAmigos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AtrasAmigos))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pestaña3Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(pestaña3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grupoNombre3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 49, Short.MAX_VALUE))
        );
        pestaña3Layout.setVerticalGroup(
            pestaña3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestaña3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pestaña3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(grupoNombre3))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pestaña3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AtrasAmigos)
                    .addComponent(InsertAmigos))
                .addGap(54, 54, 54))
        );

        jTabbedPane1.addTab("Amigos", pestaña3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 327, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Boton de insertar amigos al grupo.
    private void InsertUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertUsuariosActionPerformed
        int seleccion = listaGrupos.getSelectedRow();
        //Al clicar recogemos el admin y se lo pasamos a la pestaña de amigos
        if (seleccion >= 0) {
            idAdmin = listaGrupos.getValueAt(seleccion, 2).toString();
            ButtonHandler b = new ButtonHandler();
            if (idGrupal != 0) {
                b.actionPerformed(evt);
                //le pasamos el admin al metodo para que solo salgan los amigos de este administrador:
                listaAmigos(idAdmin);
            }
        } else {
            //Tiene que seleccionar primero un grupo:
            JOptionPane.showMessageDialog(null, "Tiene que seleccionar un Grupo", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_InsertUsuariosActionPerformed
    //CLASES PARA CONECTAR Y DESCONECTAR PESTAÑAS:

    class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            jTabbedPane1.setEnabledAt(0, false);
            jTabbedPane1.setEnabledAt(2, true);
            jTabbedPane1.setSelectedIndex(2);
        }
    }

    class ButtonHandler2 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            jTabbedPane1.setEnabledAt(0, false);
            jTabbedPane1.setEnabledAt(1, true);
            jTabbedPane1.setSelectedIndex(1);
        }
    }

    class ButtonHandler3 implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            jTabbedPane1.setEnabledAt(1, false);
            jTabbedPane1.setEnabledAt(2, false);
            jTabbedPane1.setEnabledAt(0, true);
            jTabbedPane1.setSelectedIndex(0);
        }
    }

    //Inserta a un amigo al grupo seleccionado 
    private void InsertAmigosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertAmigosActionPerformed
        int seleccion = listaNombres.getSelectedRow();
        if (seleccion >= 0) {
            String idUsuario2 = listaNombres.getValueAt(seleccion, 0).toString();
            //Al clicar le pasamos el idUsuario y el idGrupal ya guardado en la pestaña grupos:
            insertarAmigos(idUsuario2, idGrupal);

        } else {
            //Tiene que seleccionar primero un Usuario
            JOptionPane.showMessageDialog(null, "Seleciona un Usuario primero");
        }
    }//GEN-LAST:event_InsertAmigosActionPerformed
    //Boton para crear un nuevo gurpo: 
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        if (!grupoNombre.getText().isEmpty()) {
            //LLamamos al metodo insertar grupo:
            insertarGrupo();
            ListaGrupos();
        } else {
            //Tienen que estar todos los campos rellenos:
            JOptionPane.showMessageDialog(null, "Tiene que rellenar el nombre", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnNuevoActionPerformed
    //Al clicar en la tabla misma de grupos, recogemos el idGrupal y el nombre.
    private void listaGruposMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaGruposMouseClicked
        int seleccion = listaGrupos.getSelectedRow();
        if (seleccion >= 0) {
            idGrupal = Integer.parseInt(listaGrupos.getValueAt(seleccion, 0).toString());
            //Pasamos el nombre a todos los label de las pestañas:
            nombreLabel = listaGrupos.getValueAt(seleccion, 1).toString();
            nomAdmin = (String) listaGrupos.getValueAt(seleccion, 3);
            nombreAdmin.setText(nomAdmin);
            grupoNombre.setText(nombreLabel);
            grupoNombre2.setText(nombreLabel);
            grupoNombre3.setText(nombreLabel);
        }
    }//GEN-LAST:event_listaGruposMouseClicked
    //Boton de eliminar grupo:
    private void EliminarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarGrupoActionPerformed
        int seleccion = listaGrupos.getSelectedRow();
        if (seleccion >= 0) {
            idGrupal = Integer.parseInt(listaGrupos.getValueAt(seleccion, 0).toString());
            //Al clicar uno cogemos su idGrupal y se lo pasamos al metodo eliminarGrupo:
            eliminarGrupo(idGrupal);
            //Volvemos a listar lista grupos para ver que ha sido borrado:
            ListaGrupos();
        } else {
            //Selecionar siempre un grupo primero
            JOptionPane.showMessageDialog(null, "Seleciona un grupo primero");
        }
    }//GEN-LAST:event_EliminarGrupoActionPerformed
    //Boton ver integrantes:
    private void VerIntegrantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerIntegrantesActionPerformed
        ButtonHandler2 b2 = new ButtonHandler2();
        if (idGrupal != 0) {
            //Esta opcion nos obliga primero a clicar en un grupo,
            //despues nos lleva a la pestañas de integrantes del grupo. Y cierra la de grupos.
            b2.actionPerformed(evt);
            listaIntegrantes();
        } else {
            //Hay que selecionar un grupo primero:
            JOptionPane.showMessageDialog(null, "Tiene que seleccionar un Grupo", "Permiso Denegado", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_VerIntegrantesActionPerformed
    //Atras desde la pestaña Grupos:
    private void AtrasGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtrasGrupoActionPerformed
        Configuracion menu = new Configuracion();
        menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_AtrasGrupoActionPerformed
    //Atras desde la pestaña integrantes:
    private void AtrasIntegrantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtrasIntegrantesActionPerformed
        ButtonHandler3 b3 = new ButtonHandler3();
        b3.actionPerformed(evt);
        ListaGrupos();

    }//GEN-LAST:event_AtrasIntegrantesActionPerformed
    //Atras desde la pestaña Amigos:
    private void AtrasAmigosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtrasAmigosActionPerformed
        ButtonHandler3 b3 = new ButtonHandler3();
        b3.actionPerformed(evt);

    }//GEN-LAST:event_AtrasAmigosActionPerformed
    //Boton de modificar Administraor en la pestaña de integrantes:
    private void ModificarAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarAdminActionPerformed
        int seleccion = listaIntegrantes.getSelectedRow();
        if (seleccion >= 0) {
            String idUsuario2 = (String) listaIntegrantes.getValueAt(seleccion, 0);
            String nAdmind = (String) listaIntegrantes.getValueAt(seleccion, 1);
            //Al clicar le pasamos a modificarAdmin: el idUsuario y el nombre, el idGrupal el que ya picmos en la pestaña de grupo
            modifiAdmin(idGrupal, idUsuario2, nAdmind);
            //Volvemos a listar la lista grupos para ver el cambio
            ListaGrupos();
        } else {
            //Seleccionar primero un usuario
            JOptionPane.showMessageDialog(null, "Seleciona un usuario primero");
        }
    }//GEN-LAST:event_ModificarAdminActionPerformed
    //Boton para Eliminar Usuario del grupo:
    private void eliminarUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarUsuActionPerformed
        int seleccion = listaIntegrantes.getSelectedRow();
        if (seleccion >= 0) {
            String idUsuario2 = (String) listaIntegrantes.getValueAt(seleccion, 0);
            //metodo eliminar con idUSuario y id grupal:

            eliminarIntegrantes(idUsuario2, idGrupal);
            //Se vuelve a cargar la lista para ver el resultado.
            listaIntegrantes();

        } else {
            JOptionPane.showMessageDialog(null, "Seleciona un usuario primero");
        }
    }//GEN-LAST:event_eliminarUsuActionPerformed

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
            java.util.logging.Logger.getLogger(Grupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Grupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Grupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Grupos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Grupos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AtrasAmigos;
    private javax.swing.JButton AtrasGrupo;
    private javax.swing.JButton AtrasIntegrantes;
    private javax.swing.JButton EliminarGrupo;
    private javax.swing.JButton InsertAmigos;
    private javax.swing.JButton InsertUsuarios;
    private javax.swing.JButton ModificarAdmin;
    private javax.swing.JButton VerIntegrantes;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton eliminarUsu;
    private javax.swing.JTextField grupoNombre;
    private javax.swing.JLabel grupoNombre2;
    private javax.swing.JLabel grupoNombre3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable listaGrupos;
    private javax.swing.JTable listaIntegrantes;
    private javax.swing.JTable listaNombres;
    private javax.swing.JLabel nombreAdmin;
    private javax.swing.JPanel pestaña1;
    private javax.swing.JPanel pestaña2;
    private javax.swing.JPanel pestaña3;
    // End of variables declaration//GEN-END:variables
}
