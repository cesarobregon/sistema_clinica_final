package forms.Profesional;

import datos.Pacientes;
import entidades.Paciente;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.swing.JFileChooser;
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
public class FrmPacientesProfesional extends FrmIntern {
    
    public static int id = 18;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;
    JFileChooser jf = new JFileChooser();
    File archivo = null;

    
    /**
     * Creates new form Frm_Pacientes
     */
    public FrmPacientesProfesional() {
        initComponents();
        _loadPacientes();
    }
    
    //inicializar tabla
    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("Id");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nombre");
        modelo.addColumn("Domicilio");
        modelo.addColumn("Dni");
        modelo.addColumn("Localidad");
        modelo.addColumn("Provincia");
        modelo.addColumn("Celular");
        modelo.addColumn("Telefono");
        modelo.addColumn("Email");
        
        jTable.setRowSorter(null); //Elimino Filtro
        jTable.setModel(modelo);
    }
    
    //cargar pacientes a la lista
    private void _loadPacientes() {
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

                ArrayList<Paciente> pacientesList = new ArrayList<Paciente>();
                Pacientes cnx = new Pacientes();
                if (cnx.isOkConexion()) {
                    pacientesList = cnx.list("SELECT * FROM " + cnx.getTabla());
                    cnx.isCloseConexion();
                }

                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < pacientesList.size(); index++) {
                    Paciente p = pacientesList.get(index);
                    modelo.addRow(p.toObject());
                }
                
                setTitle("Pacientes...cantidad:" + pacientesList.size());
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });
        t.start();
    }

    //validar paciente
    private boolean _isValidate(Paciente p) {
        boolean isOk = false;
        if (p.getApellido().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Apellido.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getNombre().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Nombre.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getDomicilio().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Domicilio.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getNroDocumento().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado DNI.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getLocalidad().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Localidad.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getProvincia().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Provincia.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getCelular().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Celular.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getTelefono().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Telefono.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }else if(p.getEmail().trim().isEmpty()){
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Email.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }
        isOk = true;
        return isOk;
    }
    
    //seleccionar historia clinica del paciente
    private void _selectHC(){
        //para seleccionar solo archivos
        jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //para seleccionar un solo archivo a la vez
        jf.setMultiSelectionEnabled(false);
        //mostrar el selector de archivos
        jf.showOpenDialog(this);
        //si se ha seleccionado un archivo, se cambia el texto del boton por el nombre del archivo seleccionado
        if (jf.getSelectedFile() != null){
            BtnHistoriaClinica.setText(jf.getSelectedFile().getName());
            archivo = jf.getSelectedFile();
        }
    }
    
    //guardar en el proyecto la historia clinica seleccionada
    private void _copyHC(){
        //se obtiene el directorio de origen del archivo
        Path origenPath = FileSystems.getDefault().getPath(archivo.getAbsolutePath());
        //y se obtiene el directorio de destino, que es donde se va a guardar el archivo
        String destino = System.getProperty("user.dir") + "/src/historias_clinicas/"+archivo.getName();
        Path destinoPath = Paths.get(destino);

        //mediante un try-catch hacemos la copia, pasamos como parametro los directorios de origen y destino
        try {
            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            //en caso de existir un error mostramos por consola el mensaje
            System.out.println("Error en la copia de la historia clinica: " + e.getMessage());
        }
    }

    private Paciente _getPacienteForm() {
        Paciente p = new Paciente(0, "", "", "", "", "", "", "", "", "");

        p.setId(txtId.toEntero());
        p.setApellido(txtApellido.getText().trim());
        p.setNombre(txtNombre.getText().trim());
        p.setDomicilio(txtProvincia.getText().trim());
        p.setNroDocumento(txtDoc.getText().trim());
        p.setLocalidad(txtLocalidad.getText().trim());
        p.setProvincia(txtProvincia.getText().trim());
        p.setCelular(txtCel.getText().trim());
        p.setTelefono(txtTel.getText().trim());
        p.setEmail(txtEmail.getText().trim());

        return p;
    }

    private void _setForm(Paciente p) {
        txtId.setText(String.valueOf(p.getId()));
        txtNombre.setText(p.getApellido().trim());
        txtNombre.setText(p.getNombre().trim());
        txtProvincia.setText(p.getDomicilio().trim());
        txtDoc.setText(p.getNroDocumento().trim());
        txtLocalidad.setText(p.getLocalidad().trim());
        txtProvincia.setText(p.getProvincia().trim());
        txtCel.setText(p.getCelular().trim());
        txtTel.setText(p.getTelefono().trim());
        txtEmail.setText(p.getEmail().trim());

        txtNombre.requestFocus();
        txtNombre.requestFocus();
        txtProvincia.requestFocus();
        txtDoc.requestFocus();
        txtLocalidad.requestFocus();
        txtProvincia.requestFocus();
        txtCel.requestFocus();
        txtTel.requestFocus();
        txtEmail.requestFocus();
    }

    private void _selectionRow() {
        int indexRow = jTable.getSelectedRow();
        int indexModel = jTable.convertRowIndexToModel(indexRow);

        int id = (int) modelo.getValueAt(indexModel, 0);
        String apellido = (String) modelo.getValueAt(indexModel, 1);
        String nombre = (String) modelo.getValueAt(indexModel, 2);
        String domicilio = (String) modelo.getValueAt(indexModel, 3);
        String documento = (String) modelo.getValueAt(indexModel, 4);
        String localidad = (String) modelo.getValueAt(indexModel, 5);
        String provincia = (String) modelo.getValueAt(indexModel, 6);
        String celular = (String) modelo.getValueAt(indexModel, 7);
        String telefono = (String) modelo.getValueAt(indexModel, 8);
        String email = (String) modelo.getValueAt(indexModel, 9);

        Paciente p = new Paciente(id, apellido, nombre, domicilio, documento, localidad, provincia, celular, telefono, email);
        _setForm(p);
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
        Paciente p = _getPacienteForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            //copiar historia clinica
            if (archivo != null){
                _copyHC();
            }
            Pacientes cnx = new Pacientes();
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
        Paciente p = _getPacienteForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            Pacientes cnx = new Pacientes();
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
        Paciente p = _getPacienteForm();
        if (_isValidate(p)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlFicha, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Pacientes cnx = new Pacientes();
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
        pnlListaPacientes = new javax.swing.JPanel();
        pnlFilList = new javax.swing.JPanel();
        lblFilter1 = new etiquetas.LblFilter();
        txtFilter = new texto.TxtMayusculas();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        pnlFichaPaciente = new javax.swing.JPanel();
        pnlFicha = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        lblCheck1 = new etiquetas.LblCheck();
        lblCheck2 = new etiquetas.LblCheck();
        lblCheck3 = new etiquetas.LblCheck();
        lblCheck4 = new etiquetas.LblCheck();
        txtId = new texto.TxtNro();
        lblCheck6 = new etiquetas.LblCheck();
        txtDoc = new javax.swing.JTextField();
        lblCheck7 = new etiquetas.LblCheck();
        lblCheck8 = new etiquetas.LblCheck();
        lblCheck9 = new etiquetas.LblCheck();
        lblCheck5 = new etiquetas.LblCheck();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtProvincia = new javax.swing.JTextField();
        txtDomicilio = new javax.swing.JTextField();
        txtLocalidad = new javax.swing.JTextField();
        txtCel = new javax.swing.JTextField();
        txtTel = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        BtnHistoriaClinica = new javax.swing.JButton();
        pnlButtons = new javax.swing.JPanel();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();
        btnNew = new botones.BtnNew();

        setTitle("Pacientes");
        setFrameIcon(iconos.getPaciente(16));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        pnlListaPacientes.setLayout(new java.awt.BorderLayout());

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
                .addContainerGap(569, Short.MAX_VALUE))
        );
        pnlFilListLayout.setVerticalGroup(
            pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFilListLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlListaPacientes.add(pnlFilList, java.awt.BorderLayout.NORTH);

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

        pnlListaPacientes.add(pnlList, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Listado de Pacientes", iconos.getList(16)
            , pnlListaPacientes);

        pnlFichaPaciente.setLayout(new java.awt.BorderLayout());

        lblCheck1.setText("Apellido:");

        lblCheck2.setText("Nombre(s):");

        lblCheck3.setText("Documento:");

        lblCheck4.setText("Domicilio:");

        txtId.setEditable(false);
        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblCheck6.setText("Localidad");

        lblCheck7.setText("Nro. Celular");

        lblCheck8.setText("Nro. Telefono");

        lblCheck9.setText("Email");

        lblCheck5.setText("Provincia:");

        BtnHistoriaClinica.setText("Seleccionar Historia Clinica");
        BtnHistoriaClinica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHistoriaClinicaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFichaLayout = new javax.swing.GroupLayout(pnlFicha);
        pnlFicha.setLayout(pnlFichaLayout);
        pnlFichaLayout.setHorizontalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCheck9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCheck4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addComponent(txtApellido)
                    .addComponent(txtProvincia)
                    .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDomicilio)
                    .addComponent(txtLocalidad)
                    .addComponent(txtCel)
                    .addComponent(txtDoc)
                    .addComponent(txtTel)
                    .addComponent(txtEmail)
                    .addComponent(BtnHistoriaClinica))
                .addGap(66, 66, 66))
        );
        pnlFichaLayout.setVerticalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(id1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblCheck1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtApellido))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblCheck2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNombre))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblCheck3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDoc))
                .addGap(12, 12, 12)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblCheck4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDomicilio))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtProvincia)
                    .addComponent(lblCheck5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtLocalidad)
                    .addComponent(lblCheck6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtCel)
                    .addComponent(lblCheck7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtTel, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(lblCheck8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(lblCheck9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnHistoriaClinica, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        pnlFichaPaciente.add(pnlFicha, java.awt.BorderLayout.CENTER);

        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );

        pnlFichaPaciente.add(pnlButtons, java.awt.BorderLayout.LINE_END);

        jTabbedPane.addTab("Ficha del Paciente", pnlFichaPaciente);

        getContentPane().add(jTabbedPane);

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
            _setForm(new Paciente());
            _loadPacientes();
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadPacientes();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _loadPacientes();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void BtnHistoriaClinicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHistoriaClinicaActionPerformed
        _selectHC();
    }//GEN-LAST:event_BtnHistoriaClinicaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnHistoriaClinica;
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNew;
    private etiquetas.Id id1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTable;
    private etiquetas.LblCheck lblCheck1;
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
    private javax.swing.JPanel pnlFichaPaciente;
    private javax.swing.JPanel pnlFilList;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlListaPacientes;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtEmail;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtNro txtId;
    private javax.swing.JTextField txtLocalidad;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtProvincia;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
