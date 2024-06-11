package forms.Profesionales;

import datos.Profesionales;
import entidades.Profesional;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import miselaneos.FrmIntern;
import static sistema_clinica_obregon.JFrame_Sistema.iconos;

/**
 *
 * @author OBREGON
 */
public class FrmProfesionales extends FrmIntern {
    
    public static int id = 4;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;

    /**
     * Creates new form FrmProfesionales
     */
    public FrmProfesionales() {
        initComponents();
        _loadProfesionales();
    }
    
    //columnas de la tabla
    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Usuario");
        modelo.addColumn("Clave");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nombre");
        modelo.addColumn("Matricula");
        modelo.addColumn("Titulo");
        modelo.addColumn("Email");
        modelo.addColumn("Celular");
        modelo.addColumn("Foto");
        modelo.addColumn("Estado");
        jTable.setRowSorter(null); //Elimino Filtro
        jTable.setModel(modelo);
    }
    
    //cargar los profesionales desde la base de datos
    private void _loadProfesionales() {
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

                progressProfesionales.setValue(0);
                ArrayList<Profesional> profesionalesList = new ArrayList<Profesional>();
                Profesionales cnx = new Profesionales();
                if (cnx.isOkConexion()) {
                    profesionalesList = cnx.list("SELECT * FROM " + cnx.getTabla());
                    cnx.isCloseConexion();
                }
                progressProfesionales.setMaximum(profesionalesList.size());
                progressProfesionales.setVisible(true);

                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < profesionalesList.size(); index++) {
                    Profesional p = profesionalesList.get(index);
                    modelo.addRow(p.toObject());
                    progressProfesionales.setValue(index);
                }

                progressProfesionales.setVisible(false);
                progressProfesionales.setValue(0);

                setTitle("Profesionales...cantidad:" + profesionalesList.size());
                
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });
        t.start();
    }
    
    //validar ficha del profesional
    private boolean _isValidate(Profesional p) {
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
        }else if(p.getMatricula() == 0) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Matricula.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getTitulo().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Titulo.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getCelular().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Celular.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getEmail().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Email.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getFoto().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Foto.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getEstado() == 0) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Estado.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }
        isOk = true;
        return isOk;
    }
    
    //se crea un objeto tipo Profesional y le asignamos los valores cargados en la ficha del profesional
    private Profesional _getProfesionalForm() {
        Profesional p = new Profesional(0, "", "", "", "", 0, "", "", "", "", 0);
        p.setId(txtId.toEntero());
        p.setUsuario(txtUsuario.getText().trim());
        p.setClave(txtClave.getText().trim());
        p.setApellido(txtApellido.getText().trim());
        p.setNombre(txtNombre.getText().trim());
        p.setMatricula(intMatricula.toEntero());
        p.setTitulo(txtTitulo.getText().trim());
        p.setCelular(txtCel.getText().trim());
        p.setEmail(txtEmail.getText().trim());
        p.setFoto(txtFoto.getText().trim());
        p.setEstado(intEstado.toEntero());
        return p;
    }
    
    //se recibe un objeto de tipo Profesional y le asignamos los datos del mismo a los campos de la ficha
    private void _setForm(Profesional p) {
        txtId.setText(String.valueOf(p.getId()));
        txtUsuario.setText(String.valueOf(p.getUsuario()));
        txtClave.setText(String.valueOf(p.getClave()));
        txtApellido.setText(p.getApellido().trim());
        txtNombre.setText(p.getNombre().trim());
        intMatricula.setText(String.valueOf(p.getMatricula()));
        txtTitulo.setText(p.getTitulo().trim());
        txtEmail.setText(p.getEmail().trim());
        txtCel.setText(p.getCelular().trim());
        txtFoto.setText(p.getCelular().trim());
        intEstado.setText(String.valueOf(p.getEstado()));

        txtId.requestFocus();
        txtUsuario.requestFocus();
        txtClave.requestFocus();
        txtApellido.requestFocus();
        txtNombre.requestFocus();
        intMatricula.requestFocus();
        txtTitulo.requestFocus();
        txtCel.requestFocus();
        txtEmail.requestFocus();
        txtFoto.requestFocus();
        intEstado.requestFocus();
    }
    
    //se selecciona un registro de la tabla y se crea un objeto de tipo Profesional, a este le asignamos los valores del registro
    //seleccionado y lo cargamos en la ficha
    private void _selectionRow() {
        int indexRow = jTable.getSelectedRow();
        int indexModel = jTable.convertRowIndexToModel(indexRow);

        int id = (int) modelo.getValueAt(indexModel, 0);
        String usuario = (String) modelo.getValueAt(indexModel, 1);
        String clave = (String) modelo.getValueAt(indexModel, 2);
        String apellido = (String) modelo.getValueAt(indexModel, 3);
        String nombre = (String) modelo.getValueAt(indexModel, 4);
        int matricula = (int) modelo.getValueAt(indexModel, 5);
        String titulo = (String) modelo.getValueAt(indexModel, 6);
        String email = (String) modelo.getValueAt(indexModel, 7);
        String celular = (String) modelo.getValueAt(indexModel, 8);
        String foto = (String) modelo.getValueAt(indexModel, 9);
        int estado = (int) modelo.getValueAt(indexModel, 10);
        
        Profesional p = new Profesional(id, usuario, clave, apellido, nombre, matricula, titulo, email, celular, foto, estado);
        _setForm(p);
    }
    
    //filtrar un registro en la tabla
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
    
    //metodo para crear un nuevo registro en la Base de Datos
    private boolean _isNew() {
        boolean isOk = false;
        Profesional p = _getProfesionalForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            Profesionales cnx = new Profesionales();
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

    //metodo para actualizar un registro en la Base de Datos
    private boolean _isUpdate() {
        boolean isOk = false;
        Profesional p = _getProfesionalForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            Profesionales cnx = new Profesionales();
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

    //metodo para eliminar un registro en la Base de Datos
    private boolean _isDelete() {
        boolean isOk = false;
        Profesional p = _getProfesionalForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlFicha, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Profesionales cnx = new Profesionales();
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

        jTabbedPane = new javax.swing.JTabbedPane();
        pnlLista = new javax.swing.JPanel();
        pnlFilList = new javax.swing.JPanel();
        lblFilter1 = new etiquetas.LblFilter();
        txtFilter = new texto.TxtMayusculas();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        progressProfesionales = new javax.swing.JProgressBar();
        pnlFichaProfesional = new javax.swing.JPanel();
        pnlFicha = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        lblCheck1 = new etiquetas.LblCheck();
        lblCheck2 = new etiquetas.LblCheck();
        lblCheck3 = new etiquetas.LblCheck();
        lblCheck4 = new etiquetas.LblCheck();
        txtApellido = new texto.TxtMayusculas();
        txtNombre = new texto.TxtMayusculas();
        txtTitulo = new texto.TxtMayusculas();
        txtId = new texto.TxtNro();
        lblCheck5 = new etiquetas.LblCheck();
        txtFoto = new texto.TxtMayusculas();
        lblCheck7 = new etiquetas.LblCheck();
        txtCel = new texto.TxtMayusculas();
        lblCheck8 = new etiquetas.LblCheck();
        lblCheck9 = new etiquetas.LblCheck();
        txtEmail = new texto.TxtMayusculas();
        lblCheck6 = new etiquetas.LblCheck();
        txtUsuario = new texto.TxtMayusculas();
        txtClave = new texto.TxtMayusculas();
        lblCheck10 = new etiquetas.LblCheck();
        intMatricula = new texto.TxtNro();
        intEstado = new texto.TxtNro();
        pnlButtons = new javax.swing.JPanel();
        btnNew = new botones.BtnNew();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();

        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        pnlLista.setLayout(new java.awt.BorderLayout());

        pnlFilList.setPreferredSize(new java.awt.Dimension(762, 40));

        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlFilListLayout = new javax.swing.GroupLayout(pnlFilList);
        pnlFilList.setLayout(pnlFilListLayout);
        pnlFilListLayout.setHorizontalGroup(
            pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(505, Short.MAX_VALUE))
        );
        pnlFilListLayout.setVerticalGroup(
            pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilListLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pnlLista.add(pnlFilList, java.awt.BorderLayout.NORTH);

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

        progressProfesionales.setVisible(false);
        pnlList.add(progressProfesionales, java.awt.BorderLayout.PAGE_START);

        pnlLista.add(pnlList, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Lista", iconos.getList(16)
            , pnlLista);

        pnlFichaProfesional.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlFicha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlFicha.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnlFicha.add(id1, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 15, -1, -1));

        lblCheck1.setText("Apellido:");
        pnlFicha.add(lblCheck1, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 106, 88, -1));

        lblCheck2.setText("Nombre(s):");
        pnlFicha.add(lblCheck2, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 135, 88, -1));

        lblCheck3.setText("Matricula");
        pnlFicha.add(lblCheck3, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 164, 88, -1));

        lblCheck4.setText("Titulo");
        pnlFicha.add(lblCheck4, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 188, 88, -1));

        txtApellido.setLenghtText(25);
        pnlFicha.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 103, 342, -1));

        txtNombre.setLenghtText(30);
        pnlFicha.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 132, 342, -1));

        txtTitulo.setLenghtText(60);
        pnlFicha.add(txtTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 190, 342, -1));

        txtId.setEditable(false);
        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pnlFicha.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 10, 70, -1));

        lblCheck5.setText("Foto");
        pnlFicha.add(lblCheck5, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 223, 88, -1));

        txtFoto.setLenghtText(60);
        pnlFicha.add(txtFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 221, 342, -1));

        lblCheck7.setText("Nro. Celular");
        pnlFicha.add(lblCheck7, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 286, -1, -1));

        txtCel.setLenghtText(60);
        pnlFicha.add(txtCel, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 283, 342, -1));

        lblCheck8.setText("Estado");
        pnlFicha.add(lblCheck8, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 320, 88, -1));

        lblCheck9.setText("Email");
        pnlFicha.add(lblCheck9, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 250, 88, -1));

        txtEmail.setLenghtText(60);
        pnlFicha.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 250, 342, -1));

        lblCheck6.setText("Clave");
        pnlFicha.add(lblCheck6, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 77, 88, -1));

        txtUsuario.setLenghtText(25);
        pnlFicha.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 45, 342, -1));

        txtClave.setLenghtText(30);
        pnlFicha.add(txtClave, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 74, 342, -1));

        lblCheck10.setText("Usuario");
        pnlFicha.add(lblCheck10, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 48, 88, -1));

        intMatricula.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pnlFicha.add(intMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 161, 342, -1));

        intEstado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pnlFicha.add(intEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 314, 342, -1));

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

        pnlFichaProfesional.add(pnlButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 140, 140));

        jTabbedPane.addTab("Ficha Profesional", pnlFichaProfesional);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 765, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            _setForm(new Profesional());
            _loadProfesionales();
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadProfesionales();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _loadProfesionales();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged

    }//GEN-LAST:event_jTabbedPaneStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNew;
    private etiquetas.Id id1;
    private texto.TxtNro intEstado;
    private texto.TxtNro intMatricula;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTable;
    private etiquetas.LblCheck lblCheck1;
    private etiquetas.LblCheck lblCheck10;
    private etiquetas.LblCheck lblCheck2;
    private etiquetas.LblCheck lblCheck3;
    private etiquetas.LblCheck lblCheck4;
    private etiquetas.LblCheck lblCheck5;
    private etiquetas.LblCheck lblCheck6;
    private etiquetas.LblCheck lblCheck7;
    private etiquetas.LblCheck lblCheck8;
    private etiquetas.LblCheck lblCheck9;
    private etiquetas.LblFilter lblFilter1;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlFicha;
    private javax.swing.JPanel pnlFichaProfesional;
    private javax.swing.JPanel pnlFilList;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlLista;
    private javax.swing.JProgressBar progressProfesionales;
    private texto.TxtMayusculas txtApellido;
    private texto.TxtMayusculas txtCel;
    private texto.TxtMayusculas txtClave;
    private texto.TxtMayusculas txtEmail;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtMayusculas txtFoto;
    private texto.TxtNro txtId;
    private texto.TxtMayusculas txtNombre;
    private texto.TxtMayusculas txtTitulo;
    private texto.TxtMayusculas txtUsuario;
    // End of variables declaration//GEN-END:variables
}
