package sistema_clinica_obregon;

import forms.admin.FrmLoginAdmin;
import forms.personal.FrmLoginPersonal;
import forms.Profesional.FrmLoginProfesional;
import forms.settings.FrmSetting;
import javax.swing.JOptionPane;
import miselaneos.Fecha;
import miselaneos.GUI;
import miselaneos.Iconos;
import miselaneos.Imagenes;

/**
 *
 * @author OBREGON
 */
public class JFrame_Sistema extends javax.swing.JFrame {

    public static Iconos iconos = new Iconos();//Declaro public y static para poder acceder luego desde cualquier lugar del proyecto en cualquier objeto
    public static Imagenes imgs = new Imagenes(); //Misma situacion que Iconos
    public static Fecha fe = new Fecha(); //Misma situacion que Iconos

    private GUI gui;

    /**
     * Creates new form JFrame_Sistema
     */
    public JFrame_Sistema() {
        initComponents();
        setExtendedState(JFrame_Sistema.MAXIMIZED_BOTH);
        gui = new GUI();
        _initClock();
    }

    /** 
     * 
     * ID de cada Frm del proyecto
     * 
     *Frm                           id
     * especialidades               1
     * pacientes                    2
     * obra social                  3
     * profesionales                4
     * setting                      5
     * Administrador                6
     * profesional                  7
     * personal                     8
     * Inicio de Sesion Admin       9
     * Inicio de Sesion Personal    10
     * Inicio de Sesion prof.       11
     * cargos                       12
     * horarios                     13
     * Prof_Esp                     14
     * personales                   15
     * turno                        16
     * Frmprof                      17
     * FrmPacientesProfesional      18
     * FrmSetAdmin                  19
     * 
     */
    
    
    private void _loadLoginAdmin() {
        gui.loadLoginAdmin(FrmLoginAdmin.id, true);
    }
    
    private void _loadLoginPersonal() {
        gui.loadLoginPersonal(FrmLoginPersonal.id, true);
    }
    
    private void _loadLoginProfesional() {
        gui.loadLoginProfesional(FrmLoginProfesional.id, true);
    }

    public void _loadSetting() {
        gui.loadSetting(FrmSetting.id, true);
    }
    
    private void _initClock() {
        Thread t = new Thread(lblFechaHora);
        t.start();
    }

    private void salirSistema() {
        if (JOptionPane.showConfirmDialog(null, "Desea salir del Sistema?", "Atención!!!",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDeskTopSis = new miselaneos.JDeskTopSis();
        jStatusToolBar = new javax.swing.JToolBar();
        lblFechaHora = new etiquetas.LblFechaHora();
        jMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemProfesional = new javax.swing.JMenuItem();
        jMenuItemPersonal = new javax.swing.JMenuItem();
        jMenuItemAdmin = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenSalir = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Clinica");
        setIconImage(iconos.getSys(16).getImage()
        );

        jDeskTopSis.setPreferredSize(new java.awt.Dimension(1024, 800));
        getContentPane().add(jDeskTopSis, java.awt.BorderLayout.CENTER);

        jStatusToolBar.setRollover(true);
        jStatusToolBar.add(lblFechaHora);

        getContentPane().add(jStatusToolBar, java.awt.BorderLayout.SOUTH);

        jMenu1.setText("Usuario");

        jMenuItemProfesional.setIcon(iconos.getProfesional(32));
        jMenuItemProfesional.setText("Profesional");
        jMenuItemProfesional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProfesionalActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemProfesional);

        jMenuItemPersonal.setIcon(iconos.getPersonal(32));
        jMenuItemPersonal.setText("Secretario/a");
        jMenuItemPersonal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPersonalActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemPersonal);

        jMenuItemAdmin.setIcon(iconos.getAdmin(32));
        jMenuItemAdmin.setText("Administrador");
        jMenuItemAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAdminActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemAdmin);

        jMenuBar.add(jMenu1);

        jMenu2.setText("Salir");

        jMenSalir.setIcon(iconos.getExit(32));
        jMenSalir.setText("Salir del Sistema");
        jMenSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenSalirActionPerformed(evt);
            }
        });
        jMenu2.add(jMenSalir);

        jMenuBar.add(jMenu2);

        setJMenuBar(jMenuBar);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenSalirActionPerformed
        salirSistema();
    }//GEN-LAST:event_jMenSalirActionPerformed

    private void jMenuItemProfesionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProfesionalActionPerformed
        _loadLoginProfesional();
    }//GEN-LAST:event_jMenuItemProfesionalActionPerformed

    private void jMenuItemPersonalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPersonalActionPerformed
        _loadLoginPersonal();
    }//GEN-LAST:event_jMenuItemPersonalActionPerformed

    private void jMenuItemAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAdminActionPerformed
        _loadLoginAdmin();
    }//GEN-LAST:event_jMenuItemAdminActionPerformed

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
            java.util.logging.Logger.getLogger(JFrame_Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame_Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame_Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame_Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static miselaneos.JDeskTopSis jDeskTopSis;
    private javax.swing.JMenuItem jMenSalir;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemAdmin;
    private javax.swing.JMenuItem jMenuItemPersonal;
    private javax.swing.JMenuItem jMenuItemProfesional;
    private javax.swing.JToolBar jStatusToolBar;
    private etiquetas.LblFechaHora lblFechaHora;
    // End of variables declaration//GEN-END:variables
}
