/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package forms.obrasocial;

import datos.ObrasSociales;
import entidades.ObraSocial;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import miselaneos.FrmIntern;
import static sistema_clinica_obregon.JFrame_Sistema.iconos;

//  id descripcion

/**
 *
 * @author OBREGON
 */
public class FrmObraSocial extends FrmIntern {
    
    public static int id = 3;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;

    /**
     * Creates new form Frm_Pacientes
     */
    public FrmObraSocial() {
        initComponents();
        _loadobrasSociales();
    }
    
    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Descripcion");
        jTable.setModel(modelo);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(200);
    }
    
    private void _loadobrasSociales() {
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

                progressObras.setValue(0);
                ArrayList<ObraSocial> obrasList = new ArrayList<ObraSocial>();
                ObrasSociales cnx = new ObrasSociales();
                if (cnx.isOkConexion()) {

                    obrasList = cnx.list("SELECT * FROM " + cnx.getTabla());

                    cnx.isCloseConexion();
                }
                progressObras.setMaximum(obrasList.size());
                progressObras.setVisible(true);

                //modelo.getDataVector().clear();
                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < obrasList.size(); index++) {
                    ObraSocial o = obrasList.get(index);
                    modelo.addRow(o.toObject());
                    progressObras.setValue(index);
                }

                progressObras.setVisible(false);
                progressObras.setValue(0);

                
                setTitle("Obras Sociales...cantidad:" + obrasList.size());
                
                
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });
        t.start();

    }

    private boolean _isValidate(ObraSocial o) {
        boolean isOk = false;

        if (o.getDescripcion().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlCentroObraSocial, "No Ha Ingresado Localidad.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }

        isOk = true;

        return isOk;
    }

    private ObraSocial _getObrasForm() {
        ObraSocial o = new ObraSocial(0, "");

        o.setId(txtId.toEntero());
        o.setDescripcion(txtObraSocial.getText().trim());

        return o;
    }

    private void _setForm(ObraSocial o) {
        txtId.setText(String.valueOf(o.getId()));
        txtObraSocial.setText(o.getDescripcion().trim());

        txtObraSocial.requestFocus();
    }

    private void _selectionRow() {
        int indexRow = jTable.getSelectedRow();
        int indexModel = jTable.convertRowIndexToModel(indexRow);

        int id = (int) modelo.getValueAt(indexModel, 0);
        String descripcion = (String) modelo.getValueAt(indexModel, 1);

        ObraSocial o = new ObraSocial(id, descripcion);

        _setForm(o);

    }

    private boolean _isNew() {
        boolean isOk = false;
        ObraSocial o = _getObrasForm();
        if (_isValidate(o)) {
            //Esta en codiciones
            ObrasSociales cnx = new ObrasSociales();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(o);
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
        ObraSocial o = _getObrasForm();
        if (_isValidate(o)) {
            //Esta en codiciones
            ObrasSociales cnx = new ObrasSociales();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(o);
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
        ObraSocial o = _getObrasForm();
        if (_isValidate(o)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlCentroObraSocial, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            ObrasSociales cnx = new ObrasSociales();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(o);
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

        pnlCentroGrid = new javax.swing.JPanel();
        pnlFiltrar = new javax.swing.JPanel();
        lblFilter1 = new etiquetas.LblFilter();
        txtFilter = new texto.TxtMayusculas();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        progressObras = new javax.swing.JProgressBar();
        pnlObraSocial = new javax.swing.JPanel();
        pnlCentroObraSocial = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        txtId = new texto.TxtNro();
        labelObraSocial = new etiquetas.Lbl();
        txtObraSocial = new texto.TxtMayusculas();
        pnlButton = new javax.swing.JPanel();
        btnNew = new botones.BtnNew();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();

        setTitle("Obra Social");
        setFrameIcon(iconos.getObrasocial(16));

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

        progressObras.setVisible(false);
        pnlCentroGrid.add(progressObras, java.awt.BorderLayout.PAGE_END);

        pnlObraSocial.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlObraSocial.setPreferredSize(new java.awt.Dimension(320, 279));
        pnlObraSocial.setLayout(new java.awt.BorderLayout());

        txtId.setEditable(false);

        labelObraSocial.setText("Obra Social");

        javax.swing.GroupLayout pnlCentroObraSocialLayout = new javax.swing.GroupLayout(pnlCentroObraSocial);
        pnlCentroObraSocial.setLayout(pnlCentroObraSocialLayout);
        pnlCentroObraSocialLayout.setHorizontalGroup(
            pnlCentroObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroObraSocialLayout.createSequentialGroup()
                .addGroup(pnlCentroObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCentroObraSocialLayout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 167, Short.MAX_VALUE))
                    .addComponent(txtObraSocial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCentroObraSocialLayout.setVerticalGroup(
            pnlCentroObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroObraSocialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCentroObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        pnlObraSocial.add(pnlCentroObraSocial, java.awt.BorderLayout.CENTER);

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

        pnlObraSocial.add(pnlButton, java.awt.BorderLayout.SOUTH);

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
                    .addComponent(pnlObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(pnlObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if (_isNew()) {
            _setForm(new ObraSocial());
            _loadobrasSociales();
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadobrasSociales();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _loadobrasSociales();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNew;
    private etiquetas.Id id1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private etiquetas.Lbl labelObraSocial;
    private etiquetas.LblFilter lblFilter1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlCentroGrid;
    private javax.swing.JPanel pnlCentroObraSocial;
    private javax.swing.JPanel pnlFiltrar;
    private javax.swing.JPanel pnlObraSocial;
    private javax.swing.JProgressBar progressObras;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtNro txtId;
    private texto.TxtMayusculas txtObraSocial;
    // End of variables declaration//GEN-END:variables
}
