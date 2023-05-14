package pantallas;

import datos.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class menu extends javax.swing.JFrame {

    public menu() {
        initComponents();
        this.setLocationRelativeTo(null);
        llenarCampos();
        llenarTipo();
        btnAdministrarUsuario.setEnabled(false);
        btnAdministrarPersona.setEnabled(false);
        btnCaracteristicasComponente.setEnabled(false);
        btnCaracteristicasActivo.setEnabled(false);
        permisos();

    }

    private void llenarCampos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String filtro = login.txtCodigo.getText();;
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT nombre, apellido FROM persona WHERE COD_PERSONA = '" + filtro + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                txtNombre.setText(nombre);
                String apellido = rs.getString("apellido");
                txtApellido.setText(apellido);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void llenarTipo() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String filtro = login.txtCodigo.getText();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT tipo FROM usuario WHERE fkpersona = '" + filtro + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("tipo");
                txtTipo.setText(nombre);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);

        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void permisos() {
        if (txtTipo.getText().equals("administrador")) {
            btnAdministrarPersona.setEnabled(true);
            btnAdministrarUsuario.setEnabled(true);
            btnCaracteristicasComponente.setEnabled(true);
            btnCaracteristicasActivo.setEnabled(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTipo = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnExit = new javax.swing.JPanel();
        txtExit = new javax.swing.JLabel();
        btnRegistroActivo = new javax.swing.JButton();
        btnAdministrarPersona = new javax.swing.JButton();
        btnAdministrarUsuario = new javax.swing.JButton();
        btnVerComponente = new javax.swing.JButton();
        btnRegistroComponente = new javax.swing.JButton();
        btnCaracteristicasComponente = new javax.swing.JButton();
        btnVerActivo = new javax.swing.JButton();
        btnCaracteristicasActivo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(getPreferredSize());

        jPanel1.setBackground(new java.awt.Color(218, 215, 205));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 800));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(108, 88, 76));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(218, 215, 205));
        jLabel2.setText("SISTEMA DE REGISTRO");

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(218, 215, 205));
        jLabel1.setText("DE INVENTARIO");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(218, 215, 205));
        jLabel5.setText("Codigo de Usuario:");

        txtUsuario.setBackground(new java.awt.Color(218, 215, 205));
        txtUsuario.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtUsuario.setBorder(null);
        txtUsuario.setPreferredSize(new java.awt.Dimension(7, 25));
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(218, 215, 205));
        jLabel6.setText("Nombre de Usuario:");

        txtNombre.setBackground(new java.awt.Color(218, 215, 205));
        txtNombre.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtNombre.setBorder(null);
        txtNombre.setPreferredSize(new java.awt.Dimension(7, 25));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(218, 215, 205));
        jLabel7.setText("Apellido de Usuario:");

        txtApellido.setBackground(new java.awt.Color(218, 215, 205));
        txtApellido.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtApellido.setBorder(null);
        txtApellido.setPreferredSize(new java.awt.Dimension(7, 25));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(218, 215, 205));
        jLabel8.setText("Tipo de Usuario:");

        txtTipo.setBackground(new java.awt.Color(218, 215, 205));
        txtTipo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtTipo.setBorder(null);
        txtTipo.setPreferredSize(new java.awt.Dimension(7, 25));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(427, 427, 427))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 430, 740));

        jPanel5.setBackground(new java.awt.Color(218, 215, 205));
        jPanel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel5MouseDragged(evt);
            }
        });
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel5MousePressed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(218, 215, 205));
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        txtExit.setBackground(new java.awt.Color(73, 79, 99));
        txtExit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtExit.setText("x");
        txtExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtExitMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnExitLayout = new javax.swing.GroupLayout(btnExit);
        btnExit.setLayout(btnExitLayout);
        btnExitLayout.setHorizontalGroup(
            btnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnExitLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btnExitLayout.setVerticalGroup(
            btnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnExitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 1160, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 40));

        btnRegistroActivo.setBackground(new java.awt.Color(42, 62, 52));
        btnRegistroActivo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnRegistroActivo.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistroActivo.setText("REALIZAR REGISTRO DE ACTIVOS");
        btnRegistroActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroActivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistroActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 440, 280, 50));

        btnAdministrarPersona.setBackground(new java.awt.Color(42, 62, 52));
        btnAdministrarPersona.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnAdministrarPersona.setForeground(new java.awt.Color(255, 255, 255));
        btnAdministrarPersona.setText("ADMINISTRAR PERSONAS");
        btnAdministrarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdministrarPersonaActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdministrarPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 280, 280, 50));

        btnAdministrarUsuario.setBackground(new java.awt.Color(42, 62, 52));
        btnAdministrarUsuario.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnAdministrarUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnAdministrarUsuario.setText("ADMINISTRAR USUARIOS");
        btnAdministrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdministrarUsuarioActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdministrarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 360, 280, 50));

        btnVerComponente.setBackground(new java.awt.Color(42, 62, 52));
        btnVerComponente.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnVerComponente.setForeground(new java.awt.Color(255, 255, 255));
        btnVerComponente.setText("VER REGISTRO DE COMPONENTES");
        btnVerComponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerComponenteActionPerformed(evt);
            }
        });
        jPanel1.add(btnVerComponente, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 680, 280, 50));

        btnRegistroComponente.setBackground(new java.awt.Color(42, 62, 52));
        btnRegistroComponente.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnRegistroComponente.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistroComponente.setText("REALIZAR REGISTRO DE COMPONENTES");
        btnRegistroComponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroComponenteActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistroComponente, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 520, 280, 50));

        btnCaracteristicasComponente.setBackground(new java.awt.Color(42, 62, 52));
        btnCaracteristicasComponente.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCaracteristicasComponente.setForeground(new java.awt.Color(255, 255, 255));
        btnCaracteristicasComponente.setText("AGREGAR CARACTERISTICAS DE COMPONENTE");
        btnCaracteristicasComponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaracteristicasComponenteActionPerformed(evt);
            }
        });
        jPanel1.add(btnCaracteristicasComponente, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 120, 280, 50));

        btnVerActivo.setBackground(new java.awt.Color(42, 62, 52));
        btnVerActivo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnVerActivo.setForeground(new java.awt.Color(255, 255, 255));
        btnVerActivo.setText("VER REGISTRO DE ACTIVOS");
        btnVerActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnVerActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 600, 280, 50));

        btnCaracteristicasActivo.setBackground(new java.awt.Color(42, 62, 52));
        btnCaracteristicasActivo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnCaracteristicasActivo.setForeground(new java.awt.Color(255, 255, 255));
        btnCaracteristicasActivo.setText("AGREGAR CARACTERISTICAS DE ACTIVO");
        btnCaracteristicasActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaracteristicasActivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnCaracteristicasActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 200, 280, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int xMouse, yMouse, opc;

    private void txtExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_txtExitMouseClicked

    private void txtExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseEntered
        btnExit.setBackground(Color.red);
        txtExit.setForeground(Color.white);
    }//GEN-LAST:event_txtExitMouseEntered

    private void txtExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseExited
        btnExit.setBackground(new Color(218, 215, 205));
        txtExit.setForeground(new Color(73, 79, 99));
    }//GEN-LAST:event_txtExitMouseExited

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void jPanel5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel5MouseDragged

    private void jPanel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel5MousePressed

    private void btnRegistroActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActivoActionPerformed
        registroActivo ra;
        ra = new registroActivo();
        ra.setVisible(true);
//        dispose();
    }//GEN-LAST:event_btnRegistroActivoActionPerformed

    private void btnAdministrarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdministrarPersonaActionPerformed
        try {
            administrarPersona ap;
            ap = new administrarPersona();
            ap.setVisible(true);
//            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAdministrarPersonaActionPerformed

    private void btnAdministrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdministrarUsuarioActionPerformed
        try {
            administrarUsuario ap;
            ap = new administrarUsuario();
            ap.setVisible(true);
//            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAdministrarUsuarioActionPerformed

    private void btnVerComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerComponenteActionPerformed
        try {
            verComponente vc = new verComponente();
            vc.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }//GEN-LAST:event_btnVerComponenteActionPerformed

    private void btnRegistroComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroComponenteActionPerformed
        registroComponente rc;
        rc = new registroComponente();
        rc.setVisible(true);
//        dispose();
    }//GEN-LAST:event_btnRegistroComponenteActionPerformed

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnCaracteristicasComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaracteristicasComponenteActionPerformed
        componente cc;
        try {
            cc = new componente();
            cc.setVisible(true);
//            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnCaracteristicasComponenteActionPerformed

    private void btnVerActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActivoActionPerformed
        try {
            verActivo va = new verActivo();
            va.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnVerActivoActionPerformed

    private void btnCaracteristicasActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaracteristicasActivoActionPerformed
        activo aa;
        try {
            aa = new activo();
            aa.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } 
    }//GEN-LAST:event_btnCaracteristicasActivoActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdministrarPersona;
    private javax.swing.JButton btnAdministrarUsuario;
    private javax.swing.JButton btnCaracteristicasActivo;
    private javax.swing.JButton btnCaracteristicasComponente;
    private javax.swing.JPanel btnExit;
    private javax.swing.JButton btnRegistroActivo;
    private javax.swing.JButton btnRegistroComponente;
    private javax.swing.JButton btnVerActivo;
    private javax.swing.JButton btnVerComponente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public static javax.swing.JTextField txtApellido;
    private javax.swing.JLabel txtExit;
    public static javax.swing.JTextField txtNombre;
    public static javax.swing.JTextField txtTipo;
    public static javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
