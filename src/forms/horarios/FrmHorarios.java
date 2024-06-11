/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package forms.horarios;

import datos.Horarios;
import entidades.Horario;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import miselaneos.FrmIntern;

/**
 *
 * @author cesar
 */
public class FrmHorarios extends FrmIntern {
    
    public static int id = 13;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;

    /**
     * Creates new form FrmHorarios
     */
    public FrmHorarios() {
        initComponents();
        _loadHorarios();
    }
    
    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Horario");
        modelo.addColumn("Orden");
        jTable.setModel(modelo);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(200);
    }
    
    private void _loadHorarios() {
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

                progressEspecialidades.setValue(0);
                ArrayList<Horario> horariosList = new ArrayList<Horario>();
                Horarios cnx = new Horarios();
                if (cnx.isOkConexion()) {

                    horariosList = cnx.list("SELECT * FROM " + cnx.getTabla());

                    cnx.isCloseConexion();
                }
                progressEspecialidades.setMaximum(horariosList.size());
                progressEspecialidades.setVisible(true);

                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < horariosList.size(); index++) {
                    Horario h = horariosList.get(index);
                    modelo.addRow(h.toObject());
                    progressEspecialidades.setValue(index);
                }

                progressEspecialidades.setVisible(false);
                progressEspecialidades.setValue(0);

                setTitle("Horarios...cantidad:" + horariosList.size());
                
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });
        t.start();
    }
    
    private boolean _isValidate(Horario hor) {
        boolean isOk = false;

        if (hor.getDescripcion().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlCentroEspecialidad, "No Ha Ingresado un horario.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(hor.getOrden() == 0){
            JOptionPane.showMessageDialog(pnlCentroEspecialidad, "No Ha Ingresado una orden.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }
        isOk = true;
        return isOk;
    }

    private Horario _getHorarioForm() {
        Horario hor = new Horario(0, "", 0);

        hor.setId(txtId.toEntero());
        hor.setDescripcion(txtHorario.getText().trim());
        hor.setOrden(txtOrden.toEntero());

        return hor;
    }

    private void _setForm(Horario hor) {
        txtId.setText(String.valueOf(hor.getId()));
        txtHorario.setText(hor.getDescripcion().trim());
        txtOrden.setText(String.valueOf(hor.getOrden()));
        txtHorario.requestFocus();
    }

    private void _selectionRow() {
        int indexRow = jTable.getSelectedRow();
        int indexModel = jTable.convertRowIndexToModel(indexRow);

        int id = (int) modelo.getValueAt(indexModel, 0);
        String descripcion = (String) modelo.getValueAt(indexModel, 1);
        int orden = (int) modelo.getValueAt(indexModel, 2);
        Horario hor = new Horario(id, descripcion, orden);
        _setForm(hor);

    }

    private boolean _isNew() {
        boolean isOk = false;
        Horario hor = _getHorarioForm();
        if (_isValidate(hor)) {
            //Esta en codiciones
            Horarios cnx = new Horarios();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(hor);
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
        Horario hor = _getHorarioForm();
        if (_isValidate(hor)) {
            //Esta en codiciones
            Horarios cnx = new Horarios();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(hor);
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
        Horario hor = _getHorarioForm();
        if (_isValidate(hor)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlCentroEspecialidad, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Horarios cnx = new Horarios();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(hor);
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

        pnlEspecialidad = new javax.swing.JPanel();
        pnlCentroEspecialidad = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        txtId = new texto.TxtNro();
        labelEsp = new etiquetas.Lbl();
        txtHorario = new texto.TxtMayusculas();
        labelEsp1 = new etiquetas.Lbl();
        txtOrden = new texto.TxtNro();
        pnlButton = new javax.swing.JPanel();
        btnNew = new botones.BtnNew();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();
        pnlCentroGrid = new javax.swing.JPanel();
        pnlFiltrar = new javax.swing.JPanel();
        lblFilter1 = new etiquetas.LblFilter();
        txtFilter = new texto.TxtMayusculas();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        progressEspecialidades = new javax.swing.JProgressBar();

        pnlEspecialidad.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlEspecialidad.setPreferredSize(new java.awt.Dimension(320, 279));
        pnlEspecialidad.setLayout(new java.awt.BorderLayout());

        txtId.setEditable(false);

        labelEsp.setText("Horario");

        labelEsp1.setText("Orden");

        javax.swing.GroupLayout pnlCentroEspecialidadLayout = new javax.swing.GroupLayout(pnlCentroEspecialidad);
        pnlCentroEspecialidad.setLayout(pnlCentroEspecialidadLayout);
        pnlCentroEspecialidadLayout.setHorizontalGroup(
            pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroEspecialidadLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEsp, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(labelEsp1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHorario, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addGroup(pnlCentroEspecialidadLayout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtOrden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCentroEspecialidadLayout.setVerticalGroup(
            pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroEspecialidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelEsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEsp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(256, 256, 256))
        );

        pnlEspecialidad.add(pnlCentroEspecialidad, java.awt.BorderLayout.CENTER);

        pnlButton.setPreferredSize(new java.awt.Dimension(290, 40));

        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        pnlButton.add(btnNew);

        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnlButton.add(btnEdit);

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlButton.add(btnDelete);

        pnlEspecialidad.add(pnlButton, java.awt.BorderLayout.SOUTH);

        pnlCentroGrid.setLayout(new java.awt.BorderLayout());

        pnlFiltrar.setPreferredSize(new java.awt.Dimension(451, 30));

        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlFiltrarLayout = new javax.swing.GroupLayout(pnlFiltrar);
        pnlFiltrar.setLayout(pnlFiltrarLayout);
        pnlFiltrarLayout.setHorizontalGroup(
            pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrarLayout.createSequentialGroup()
                .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        pnlFiltrarLayout.setVerticalGroup(
            pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrarLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCentroGrid.add(pnlFiltrar, java.awt.BorderLayout.NORTH);

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

        pnlCentroGrid.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        progressEspecialidades.setVisible(false);
        pnlCentroGrid.add(progressEspecialidades, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlCentroGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(pnlEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pnlCentroGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnlEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if (_isNew()) {
            _setForm(new Horario());
            _loadHorarios();
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadHorarios();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _loadHorarios();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        orden = new TableRowSorter<TableModel>(modelo);
        this.jTable.setRowSorter(orden);

        RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(txtFilter.getText().trim());
        orden.setRowFilter(filtro);
    }//GEN-LAST:event_txtFilterKeyReleased

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        _selectionRow();
    }//GEN-LAST:event_jTableMouseClicked

    private void jTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyReleased
        _selectionRow();
    }//GEN-LAST:event_jTableKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNew;
    private etiquetas.Id id1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private etiquetas.Lbl labelEsp;
    private etiquetas.Lbl labelEsp1;
    private etiquetas.LblFilter lblFilter1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlCentroEspecialidad;
    private javax.swing.JPanel pnlCentroGrid;
    private javax.swing.JPanel pnlEspecialidad;
    private javax.swing.JPanel pnlFiltrar;
    private javax.swing.JProgressBar progressEspecialidades;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtMayusculas txtHorario;
    private texto.TxtNro txtId;
    private texto.TxtNro txtOrden;
    // End of variables declaration//GEN-END:variables
}
