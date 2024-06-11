package forms.turnos;

import datos.Especialidades;
import datos.Profesionales;
import datos.Profesionales_Especialidades;
import entidades.Especialidad;
import entidades.Profesional;
import entidades.ProfesionalEspecialidad;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author cesar
 */
public class pnlComboProf_Esp extends javax.swing.JPanel {

    private TreeMap<Integer, Profesional> listprof; //lista de profesionales
    private TreeMap<Integer, Especialidad> listesp; //lista de especialidades
    private TreeMap<Integer, ProfesionalEspecialidad> listprofesp; //lista de especialidades de los profesionales
    private DefaultComboBoxModel modeloComboEsp;
    private DefaultComboBoxModel modeloComboProf;

    /**
     * Creates new form pnlComboProf_Esp
     */
    public pnlComboProf_Esp() {
        initComponents();
        _loadProfesionales();
        _loadEspecialidades();
        _loadProfEsp();
    }

    /*==============================================================*/
    //traemos los datos de las especialidades desde la base de datos
    private void _loadEspecialidades() {
        ArrayList<Especialidad> list = new ArrayList<Especialidad>();
        Especialidades cnx = new Especialidades();
        if (cnx.isOkConexion()) {
            list = cnx.list("SELECT * FROM " + cnx.getTabla());
            cnx.isCloseConexion();
        }
        listesp = new TreeMap<Integer, Especialidad>();
        modeloComboEsp = new DefaultComboBoxModel<String>();
        this.jComboEsp.setModel(modeloComboEsp);

        modeloComboEsp.addElement("Seleccionar...");
        for (int index = 0; index < list.size(); index++) {
            Especialidad esp = list.get(index);
            modeloComboEsp.addElement(esp.getDescripcion().trim());
            listesp.put(index, esp);
        }
    }

    public Especialidad _getEspecialidadSelected() {
        return listesp.get(jComboEsp.getSelectedIndex());
    }

    public void _setEspecialidadSelected(int idEsp) {
        int indexSelectedCombo = 0;
        for (int index = 1; index < listesp.size(); index++) {
            Especialidad esp = listesp.get(index);
            if (esp.getId() == idEsp) {
                indexSelectedCombo = index;
            }
        }
        //devuelvo el elemento seleccionado y le pongo + 1 por que el primer elemento del JCombo
        //es "Seleccionar..." con el ID 0
        jComboEsp.setSelectedIndex(indexSelectedCombo + 1);
    }

    /*=============================================================*/
    //traemos los datos de los profesionales desde la base de datos
    private void _loadProfesionales() {

        ArrayList<Profesional> list = new ArrayList<Profesional>();
        Profesionales cnx = new Profesionales();
        if (cnx.isOkConexion()) {
            list = cnx.list("SELECT * FROM " + cnx.getTabla());
            cnx.isCloseConexion();
        }
        listprof = new TreeMap<Integer, Profesional>();
        modeloComboProf = new DefaultComboBoxModel<String>();
        this.jComboProf.setModel(modeloComboProf);

        modeloComboProf.addElement("Seleccionar...");
        for (int index = 0; index < list.size(); index++) {
            Profesional prof = list.get(index);
            modeloComboProf.addElement(prof.getApellido().trim() + ", " + prof.getNombre().trim());
            listprof.put(index, prof);
        }
    }

    public Profesional _getProfesionalSelected() {
        return listprof.get(jComboProf.getSelectedIndex());
    }

    public void _setProfesionalSelected(int idProf) {
        int indexSelectedCombo = 0;
        for (int index = 1; index < listprof.size(); index++) {
            Profesional prof = listprof.get(index);
            if (prof.getId() == idProf) {
                indexSelectedCombo = index;
            }
        }
        //devuelvo el elemento seleccionado y le pongo + 1 por que el primer elemento del JCombo
        //es "Seleccionar..." con el ID 0
        jComboProf.setSelectedIndex(indexSelectedCombo + 1);
    }

    /*===================================================================================*/
    //traemos los datos de las especialidades de los profesionales desde la base de datos
    private void _loadProfEsp() {
        ArrayList<ProfesionalEspecialidad> list = new ArrayList<ProfesionalEspecialidad>();
        Profesionales_Especialidades cnx = new Profesionales_Especialidades();
        if (cnx.isOkConexion()) {
            list = cnx.list("SELECT * FROM " + cnx.getTabla());
            cnx.isCloseConexion();
        }
        listprofesp = new TreeMap<Integer, ProfesionalEspecialidad>();
        for (int index = 0; index < list.size(); index++) {
            ProfesionalEspecialidad profesp = list.get(index);
            listprofesp.put(index, profesp);
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

        jComboEsp = new javax.swing.JComboBox<>();
        jComboProf = new javax.swing.JComboBox<>();

        jComboEsp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboProf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jComboEsp, 0, 400, Short.MAX_VALUE)
            .addComponent(jComboProf, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jComboEsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboProf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboEsp;
    private javax.swing.JComboBox<String> jComboProf;
    // End of variables declaration//GEN-END:variables
}
