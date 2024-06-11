package sistema_clinica_obregon;
import com.formdev.flatlaf.FlatIntelliJLaf;
import datos.TestConexion;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
/**
 *
 * @author OBREGON
 */
public class Sistema_Clinica_OBREGON {
    
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Toolkit.getDefaultToolkit().setDynamicLayout(true);
                    System.setProperty("sun.awt.noerasebackground", "true");
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                } catch (Exception ex) {
                } finally {
                    JFrame_Sistema frm = new JFrame_Sistema();
                    frm.setVisible(true);
                    TestConexion test = new TestConexion();
                    if(!test.isOkFileCofigConexion()){
                        frm._loadSetting();
                    }
                }
            }
        });
    }
}