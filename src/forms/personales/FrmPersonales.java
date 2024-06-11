/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package forms.personales;

import datos.Personales;
import entidades.Personal;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import miselaneos.FrmIntern;
import static sistema_clinica_obregon.JFrame_Sistema.fe;
import static sistema_clinica_obregon.JFrame_Sistema.iconos;

/**
 *
 * @author cesar
 */
public class FrmPersonales extends FrmIntern {
    
    public static int id = 15;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;
    

    /**
     * Creates new form FrmPersonales
     */
    public FrmPersonales() {
        initComponents();
        _loadPersonales();
    }
    
    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Usuario");
        modelo.addColumn("Clave");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nombre");
        modelo.addColumn("ID Cargo");
        jTable.setRowSorter(null); //Elimino Filtro
        jTable.setModel(modelo);
    }
    
    private void _loadPersonales() {
        if (!btnNew.isEnabled()) {
            return;
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
                
                _initTable();

                progressPersonales.setValue(0);
                ArrayList<Personal> personalesList = new ArrayList<Personal>();
                Personales cnx = new Personales();
                if (cnx.isOkConexion()) {
                    personalesList = cnx.list("SELECT * FROM " + cnx.getTabla());
                    cnx.isCloseConexion();
                }
                progressPersonales.setMaximum(personalesList.size());
                progressPersonales.setVisible(true);

                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < personalesList.size(); index++) {
                    Personal p = personalesList.get(index);
                    
                    modelo.addRow(p.toObject());
                    progressPersonales.setValue(index);
                }

                progressPersonales.setVisible(false);
                progressPersonales.setValue(0);

                setTitle("Personales...cantidad:" + personalesList.size());
                
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });
        t.start();
    }
    
    private boolean _isValidate(Personal p) {
        boolean isOk = false;
        if (p.getUsuario().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Usuario.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getClave().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Clave.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getApellido().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Apellido.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getNombre().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Nombre.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getCargo_id() == 0){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Cargo.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getFecha_inicio()== null){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Fecha de Inicio.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getFecha_fin()== null){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Fecha de Finalizacion.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }
        isOk = true;
        return isOk;
    }
    
    private Personal _getPersonalForm() {
        Personal p = new Personal(0, "", "", "", "", 0, null, null);
        p.setId(txtId.toEntero());
        p.setUsuario(txtUsuario.getText().trim());
        p.setClave(txtClave.getText().trim());
        p.setApellido(txtApellido.getText().trim());
        p.setNombre(txtNombre.getText().trim());
        p.setCargo_id(intCargo.toEntero());
        p.setFecha_inicio(fe.getFechaACalendar(txtInicio.getDate()));
        p.setFecha_fin(fe.getFechaACalendar(txtFin.getDate()));
        return p;
    }
    
    private void _setForm(Personal p) {
        txtId.setText(String.valueOf(p.getId()));
        txtUsuario.setText(String.valueOf(p.getUsuario()));
        txtClave.setText(String.valueOf(p.getClave()));
        txtApellido.setText(p.getApellido().trim());
        txtNombre.setText(p.getNombre().trim());
        intCargo.setText(String.valueOf(p.getCargo_id()));
        if (p.getFecha_inicio()== null) {
            p.setFecha_inicio(Calendar.getInstance());
        }
        if (p.getFecha_fin()== null) {
            p.setFecha_fin(Calendar.getInstance());
        }
        txtInicio.setDate(p.getFecha_inicio().getTime());
        txtFin.setDate(p.getFecha_fin().getTime());

        txtId.requestFocus();
        txtUsuario.requestFocus();
        txtClave.requestFocus();
        txtApellido.requestFocus();
        txtNombre.requestFocus();
        intCargo.requestFocus();
        txtInicio.requestFocus();
        txtFin.requestFocus();
    }
    
    private void _selectionRow() {
        try {
            int indexRow = jTable.getSelectedRow();
            int indexModel = jTable.convertRowIndexToModel(indexRow);

            int id = (int) modelo.getValueAt(indexModel, 0);

            Personal p = new Personal();
            Personales cnx = new Personales();
            if (cnx.isOkConexion()) {
                p = cnx.getPersonal(id);
                cnx.isCloseConexion();
            }

            _setForm(p);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    
    private void _filter(String texto){
        try{
            orden = new TableRowSorter<TableModel>(modelo);
            this.jTable.setRowSorter(orden);
            RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(texto.trim());
            orden.setRowFilter(filtro);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }    
    }
    
    private boolean _isNew() {
        boolean isOk = false;
        Personal p = _getPersonalForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            Personales cnx = new Personales();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(p);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }

    private boolean _isUpdate() {
        boolean isOk = false;
        Personal p = _getPersonalForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            Personales cnx = new Personales();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(p);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }

    private boolean _isDelete() {
        boolean isOk = false;
        Personal p = _getPersonalForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlFicha, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Personales cnx = new Personales();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(p);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        pnlLista = new javax.swing.JPanel();
        pnlFilList2 = new javax.swing.JPanel();
        lblFilter3 = new etiquetas.LblFilter();
        txtFilter = new texto.TxtMayusculas();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        progressPersonales = new javax.swing.JProgressBar();
        pnlFichaProfesional = new javax.swing.JPanel();
        pnlFicha = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        lblCheck1 = new etiquetas.LblCheck();
        lblCheck2 = new etiquetas.LblCheck();
        lblCheck3 = new etiquetas.LblCheck();
        txtApellido = new texto.TxtMayusculas();
        txtNombre = new texto.TxtMayusculas();
        txtId = new texto.TxtNro();
        lblCheck5 = new etiquetas.LblCheck();
        lblCheck9 = new etiquetas.LblCheck();
        lblCheck6 = new etiquetas.LblCheck();
        txtUsuario = new texto.TxtMayusculas();
        txtClave = new texto.TxtMayusculas();
        lblCheck10 = new etiquetas.LblCheck();
        intCargo = new texto.TxtNro();
        txtFin = new com.toedter.calendar.JDateChooser();
        txtInicio = new com.toedter.calendar.JDateChooser();
        pnlButtons = new javax.swing.JPanel();
        btnNew = new botones.BtnNew();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();

        jTabbedPane2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane2StateChanged(evt);
            }
        });

        pnlLista.setLayout(new java.awt.BorderLayout());

        pnlFilList2.setPreferredSize(new java.awt.Dimension(762, 40));

        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlFilList2Layout = new javax.swing.GroupLayout(pnlFilList2);
        pnlFilList2.setLayout(pnlFilList2Layout);
        pnlFilList2Layout.setHorizontalGroup(
            pnlFilList2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilList2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(505, Short.MAX_VALUE))
        );
        pnlFilList2Layout.setVerticalGroup(
            pnlFilList2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFilList2Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(pnlFilList2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFilter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlLista.add(pnlFilList2, java.awt.BorderLayout.NORTH);

        pnlList.setLayout(new java.awt.BorderLayout());

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        pnlList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        progressPersonales.setVisible(false);
        pnlList.add(progressPersonales, java.awt.BorderLayout.PAGE_START);

        pnlLista.add(pnlList, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Lista", iconos.getList(16)
            , pnlLista);

        pnlFichaProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlFicha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        id1.setText("ID:");

        lblCheck1.setText("Apellido:");

        lblCheck2.setText("Nombre(s):");

        lblCheck3.setText("ID Cargo");

        txtApellido.setLenghtText(25);

        txtNombre.setLenghtText(30);

        txtId.setEditable(false);
        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblCheck5.setText("Fecha Inicio:");

        lblCheck9.setText("Fecha Fin:");

        lblCheck6.setText("Clave");

        txtUsuario.setLenghtText(25);

        txtClave.setLenghtText(30);

        lblCheck10.setText("Usuario");

        intCargo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout pnlFichaLayout = new javax.swing.GroupLayout(pnlFicha);
        pnlFicha.setLayout(pnlFichaLayout);
        pnlFichaLayout.setHorizontalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCheck5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck10, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65))
        );
        pnlFichaLayout.setVerticalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(id1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCheck10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(lblCheck6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(lblCheck1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(lblCheck2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(lblCheck3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11)
                        .addComponent(lblCheck5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(lblCheck9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(txtClave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)
                        .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(intCargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(159, 159, 159))
        );

        pnlFichaProfesional.add(pnlFicha, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 560, 456));

        pnlButtons.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        pnlButtons.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));

        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnlButtons.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, -1));

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlButtons.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 120, -1));

        pnlFichaProfesional.add(pnlButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 140, 140));

        jTabbedPane2.addTab("Ficha Personal", pnlFichaProfesional);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 765, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        _filter(txtFilter.getText().trim());
    }//GEN-LAST:event_txtFilterKeyReleased

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        _selectionRow();
    }//GEN-LAST:event_jTableMouseClicked

    private void jTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyReleased
        _selectionRow();
    }//GEN-LAST:event_jTableKeyReleased

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if (_isNew()) {
            _setForm(new Personal());
            _loadPersonales();
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadPersonales();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _loadPersonales();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jTabbedPane2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane2StateChanged

    }//GEN-LAST:event_jTabbedPane2StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNew;
    private etiquetas.Id id1;
    private texto.TxtNro intCargo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable;
    private etiquetas.LblCheck lblCheck1;
    private etiquetas.LblCheck lblCheck10;
    private etiquetas.LblCheck lblCheck2;
    private etiquetas.LblCheck lblCheck3;
    private etiquetas.LblCheck lblCheck5;
    private etiquetas.LblCheck lblCheck6;
    private etiquetas.LblCheck lblCheck9;
    private etiquetas.LblFilter lblFilter3;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlFicha;
    private javax.swing.JPanel pnlFichaProfesional;
    private javax.swing.JPanel pnlFilList2;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlLista;
    private javax.swing.JProgressBar progressPersonales;
    private texto.TxtMayusculas txtApellido;
    private texto.TxtMayusculas txtClave;
    private texto.TxtMayusculas txtFilter;
    private com.toedter.calendar.JDateChooser txtFin;
    private texto.TxtNro txtId;
    private com.toedter.calendar.JDateChooser txtInicio;
    private texto.TxtMayusculas txtNombre;
    private texto.TxtMayusculas txtUsuario;
    // End of variables declaration//GEN-END:variables
}
